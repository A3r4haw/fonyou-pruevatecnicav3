package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Estabilidad_Extended;
import mx.mc.service.EstabilidadService;
import mx.mc.lazy.EstabilidadLazy;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Fabricante;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.PresentacionMedicamento;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.Usuario;
import mx.mc.model.ViaAdministracion;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.FabricanteService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PresentacionMedicamentoService;
import mx.mc.service.SustanciaActivaService;
import mx.mc.service.UnidadConcentracionService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class EstabilidadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EstabilidadMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private PermisoUsuario permiso;
    private SesionMB sesion;
    private boolean isAdmin;
    private boolean isJefeArea;
    private String cadenaBusqueda;
    private boolean status;
    private int idFabricante;

    @Autowired
    private transient FabricanteService fabricanteService;
    private transient List<Fabricante> listaFabricantes;
    private Fabricante fabricante;

    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;
    private transient List<PresentacionMedicamento> listaPresentacionMedicamento;

    @Autowired
    private transient UnidadConcentracionService unidadConcentracionService;
    private transient List<UnidadConcentracion> listaUnidadConcentracion;

    @Autowired
    private transient ViaAdministracionService viaAdmistracionService;
    private transient List<ViaAdministracion> listaViasAdmon;

    @Autowired
    private transient EstabilidadService estabilidadService;
    private EstabilidadLazy estabilidadLazy;
    //private List<Estabilidad_Extended> listaEstabilidades;
    private Estabilidad_Extended estabilidadSelect;

    @Autowired
    private transient MedicamentoService medicamentoService;
    private Medicamento_Extended medicamento;
    private Usuario usuarioSelected;        

    /**
     * Consulta los permisos del usuario
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.init()");
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ESTABILIDADINSUMO.getSufijo());
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        usuarioSelected = sesion.getUsuarioSelected();
        initialize();
        validarUsuarioAdministrador();
        buscarFabricantes();
        buscarPresentaciones();
        buscarUnidadesConcentracion();
        buscarViasAdministracion();
        obtenerEnvases();
        consultar();
        estabilidadSelect = new Estabilidad_Extended();
        fabricante = new Fabricante();
    }

    private void initialize() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.initialize()");
        isAdmin = Constantes.INACTIVO;
        isJefeArea = Constantes.INACTIVO;
        status = false;

    }

    public void validarUsuarioAdministrador() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.validarUsuarioAdministrador()");
        try {

            this.isAdmin = sesion.isAdministrador();
            this.isJefeArea = sesion.isJefeArea();

        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador: ", ex.getMessage());
        }
    }

    public void buscarFabricantes() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.buscarFabricantes()");
        try {
            Fabricante unFabricante = new Fabricante();
            unFabricante.setIdEstatus(Constantes.ACTIVOS);
            this.listaFabricantes = fabricanteService.obtenerLista(unFabricante);
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo buscarFabricantes: ", ex.getMessage());
        }
    }

    public void buscarPresentaciones() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.buscarPresentaciones()");
        try {
            PresentacionMedicamento unaPresentacion = new PresentacionMedicamento();
            unaPresentacion.setActiva(Constantes.ACTIVOS);
            listaPresentacionMedicamento = presentacionMedicamentoService.obtenerLista(unaPresentacion);
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo buscarPresentaciones: ", ex.getMessage());
        }
    }

    public void buscarUnidadesConcentracion() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.buscarUnidadesConcentracion()");
        try {
            listaUnidadConcentracion = unidadConcentracionService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo buscarUnidadesConcentracion: ", ex.getMessage());
        }
    }

    public void buscarViasAdministracion() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.buscarViasAdministracion()");
        try {
            listaViasAdmon = viaAdmistracionService.obtenerTodo();
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo buscarViasAdministracion: ", ex.getMessage());
        }
    }

    public void buscarEstabilidades() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.buscarEstabilidades()");
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            if (cadenaBusqueda != null
                    && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            estabilidadLazy = new EstabilidadLazy(estabilidadService, cadenaBusqueda);

            cadenaBusqueda = null;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al buscarEstabilidades:  " + ex.getMessage());
        }
    }

    public void consultar() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.consultar()");
        try {
            this.estabilidadLazy = new EstabilidadLazy(estabilidadService, cadenaBusqueda);

            LOGGER.trace("Resultados: {}", this.estabilidadLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar :: {} ", e1.getMessage());
        }
    }

    public List<Medicamento_Extended> autocomplete(String query) {
        LOGGER.debug("mx.mc.magedbean.EstabilidadMB.autocomplete()");
        List<Medicamento_Extended> listaInsumo = new ArrayList<>();
        try {
            listaInsumo.addAll(medicamentoService.searchMedicamentAutoComplete(query, Constantes.MEDI));

        } catch (Exception ex) {
            LOGGER.error("La busqueda de medicamento causo un error", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La busqueda de medicamento causo un error", null);
        }
        return listaInsumo;
    }

    public void handleSelect(SelectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        medicamento = (Medicamento_Extended) e.getObject();
    }

    public void borrarEstabilidad(String idEstabilidad) {

    }

    public void nuevaEstabilidad() {
        estabilidadSelect = new Estabilidad_Extended();
        medicamento = new Medicamento_Extended();
    }

    /**
     *
     * @param idEstabilidad
     */
    public void obtenerEstabilidad(String idEstabilidad) {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.obtenerEstabilidad()");
        status = false;
        try {
            estabilidadSelect = estabilidadService.obtenerEstabilidadPorId(idEstabilidad);
            if (estabilidadSelect != null) {
                medicamento = medicamentoService.obtenerMedicamentoByIdMedicamento(estabilidadSelect.getIdInsumo());
                if (medicamento == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener datos de estabilidad.", null);
                } else {
                    status = true;
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener datos de estabilidad.", null);
            }

        } catch (Exception e) {
            LOGGER.error("Error al obtener la información de estabilidad a editar :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener datos de estabilidad.", null);
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }
    
    public void nuevoFabricante() {
        fabricante = new Fabricante();
    }
    
    public void guardarFabricante() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.guardarFabricante()");
        status = false;
        try {
            Integer idFabrica = fabricanteService.obtenerSiguienteId();
            fabricante.setIdFabricante(idFabrica);
            fabricante.setInsertFecha(new java.util.Date());
            fabricante.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            fabricante.setIdEstatus(Constantes.ACTIVOS);
            status = fabricanteService.insertar(fabricante);
        } catch (Exception e) {
            LOGGER.error("Error al guardar el fabricante :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al guardar el nuevo fabricante. ", null);
        }
        if(status)
            Mensaje.showMessage(Constantes.MENSAJE_INFO, "El fabricante se guardo correctamente.  ", null);
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsJefeArea() {
        return isJefeArea;
    }

    public void setIsJefeArea(boolean isJefeArea) {
        this.isJefeArea = isJefeArea;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public List<Fabricante> getListaFabricantes() {
        return listaFabricantes;
    }

    public void setListaFabricantes(List<Fabricante> listaFabricantes) {
        this.listaFabricantes = listaFabricantes;
    }

    public List<PresentacionMedicamento> getListaPresentacionMedicamento() {
        return listaPresentacionMedicamento;
    }

    public void setListaPresentacionMedicamento(List<PresentacionMedicamento> listaPresentacionMedicamento) {
        this.listaPresentacionMedicamento = listaPresentacionMedicamento;
    }

    public List<UnidadConcentracion> getListaUnidadConcentracion() {
        return listaUnidadConcentracion;
    }

    public void setListaUnidadConcentracion(List<UnidadConcentracion> listaUnidadConcentracion) {
        this.listaUnidadConcentracion = listaUnidadConcentracion;
    }

    public List<ViaAdministracion> getListaViasAdmon() {
        return listaViasAdmon;
    }

    public void setListaViasAdmon(List<ViaAdministracion> listaViasAdmon) {
        this.listaViasAdmon = listaViasAdmon;
    }

    public EstabilidadLazy getEstabilidadLazy() {
        return estabilidadLazy;
    }

    public void setEstabilidadLazy(EstabilidadLazy estabilidadLazy) {
        this.estabilidadLazy = estabilidadLazy;
    }

    public Estabilidad_Extended getEstabilidadSelect() {
        return estabilidadSelect;
    }

    public void setEstabilidadSelect(Estabilidad_Extended estabilidadSelect) {
        this.estabilidadSelect = estabilidadSelect;
    }

    public Medicamento_Extended getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento_Extended medicamento) {
        this.medicamento = medicamento;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    private transient List<EnvaseContenedor> envaseList;
    @Autowired
    private transient EnvaseContenedorService envaseConService;

    public void obtenerEnvases() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.obtenerEnvases()");
        this.envaseList = new ArrayList<>();
        try {
            envaseList.addAll(this.envaseConService.obtenerLista(new EnvaseContenedor()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de envases :: {} ", ex.getMessage());
        }
    }

    public List<EnvaseContenedor> getEnvaseList() {
        return envaseList;
    }

    public void setEnvaseList(List<EnvaseContenedor> envaseList) {
        this.envaseList = envaseList;
    }

    public List<Medicamento_Extended> autoCompleteMedicamentos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.autoCompleteMedicamentos()");
        List<Medicamento_Extended> listaMedicamentos = new ArrayList<>();
        try {
            Integer tipo = CatalogoGeneral_Enum.MEDICAMENTO.getValue();
            listaMedicamentos.addAll(medicamentoService.searchMedicamentAutoComplete(cadena, tipo));
        } catch (Exception e) {
            LOGGER.error("Error al buscar insumos :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, e.getMessage(), null);
        }
        return listaMedicamentos;
    }

    @Autowired
    private transient UnidadConcentracionService unidadService;

    /**
     *
     * @param evt
     */
    public void selectMedicamento(SelectEvent evt) {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.selectMedicamento()");
        try {
            Medicamento_Extended m = (Medicamento_Extended) evt.getObject();
            if (m != null) {
                if (m.getIdUnidadConcentracion() != null) {
                    UnidadConcentracion uc = unidadService.obtener(new UnidadConcentracion(m.getIdUnidadConcentracion()));
                    if (uc == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Unidad de concentración de insumo inválida.", null);
                    } else {
                        if (m.getSustanciaActiva() != null) {
                            if (estabilidadSelect != null) {
                                estabilidadSelect.setIdInsumo(m.getIdMedicamento());
                                estabilidadSelect.setNombreGenerico(m.getNombreCorto());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener unidad de concetración de insumo :: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener unidad de concetración de insumo.", null);
        }
    }

    /**
     *
     * @param evt
     */
    public void unselectMedicamento(SelectEvent evt) {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.unselectMedicamento()");
        medicamento = new Medicamento_Extended();
    }

    public void validaEstabilidad() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.validaEstabilidad()");
        status = false;
        try {
            if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción.", null);

            } else if (estabilidadSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Datos de estabilidad incorrectos.", null);

            } else if (medicamento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione medicamento.", null);

            } else if (medicamento.getIdMedicamento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione medicamento.", null);

            } else if (estabilidadSelect.getIdInsumo() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione medicamento.", null);

            } else if (estabilidadSelect.getIdFabricante() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Fabricante.", null);

            } else if (estabilidadSelect.getConcentracionReconst() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture concentración de reconstitucion.", null);

            } else if (estabilidadSelect.getIdUnidadReconst() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture unidad de reconstitucion.", null);

            } else if (estabilidadSelect.getIdViaAdministracion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Vía de Administración.", null);

            } else if (estabilidadSelect.getConcentMinMezcla() == null || estabilidadSelect.getConcentMinMezcla() == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture concentración mínima de mezcla.", null);

            } else if (estabilidadSelect.getConcentMaxMezclat() == null || estabilidadSelect.getConcentMaxMezclat() == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture concentración máxima de mezcla.", null);

            } else if (estabilidadSelect.getReglasDePreparacion() == null || estabilidadSelect.getReglasDePreparacion().trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture reglas de preparación.", null);

            } else {
                status = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar los datos de la estabilidad :: {} ", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void guardaEstabilidad() {
        LOGGER.trace("mx.mc.magedbean.EstabilidadMB.guardaEstabilidad()");
        status = false;
        try {
            if(estabilidadSelect.getSolucionesCompatibles().equals("")) {
                    estabilidadSelect.setSolucionesCompatibles(null);
            }
            if (estabilidadSelect.getIdEstabilidad() == null) {
                estabilidadSelect.setIdEstabilidad(Comunes.getUUID());
                estabilidadSelect.setInsertFecha(new java.util.Date());
                estabilidadSelect.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                estabilidadSelect.setIdEstatus(1);
                estabilidadSelect.setIdPresentacion(40);
                estabilidadSelect.setIdUnidadMezcla(2);
                status = estabilidadService.insertar(estabilidadSelect);
            } else {
                estabilidadSelect.setUpdateFecha(new java.util.Date());
                estabilidadSelect.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                estabilidadSelect.setIdInsumo(medicamento.getIdMedicamento());
                if (estabilidadSelect.getIdContenedor() != null){
                    if (estabilidadSelect.getIdContenedor() != 2 && estabilidadSelect.getIdContenedor() != 32 ) {
                        estabilidadSelect.setIdContenedor(null);
                    }
                }
                status = estabilidadService.actualizar(estabilidadSelect);
            }

            if (!status) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al registrar datos de estabilidad.", null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, "Los datos de estabilidad se guardaron correctamente.", null);
            }

        } catch (Exception e) {
            LOGGER.error("Error al guardar los datos de la estabilidad :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al registrar datos de estabilidad.", null);
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

}
