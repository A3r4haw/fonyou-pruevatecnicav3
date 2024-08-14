/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.DispensacionMaterialLazy;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusDispensacionMaterial_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Cama;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.DispensacionMaterial;
import mx.mc.model.Estructura;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.Usuario;
import mx.mc.model.DispensacionMaterialExtended;
import mx.mc.model.DispensacionMaterialInsumo;
import mx.mc.model.DispensacionMaterialInsumoExtended;
import mx.mc.model.Folios;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.PermisoUsuario;
import mx.mc.service.CamaService;
import mx.mc.service.DispensacionMaterialInsumoService;
import mx.mc.service.DispensacionMaterialService;
import mx.mc.service.EstructuraService;
import mx.mc.service.PacienteService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author apalacios
 */
@Controller
@Scope(value = "view")
public class DispensacionMaterialMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionMaterialMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private List<Estructura> estructuraList;    
    private boolean isAdmin;
    private boolean isJefeArea;
    private Usuario currentUser;
    private String idEstructura;
    private DispensacionMaterialLazy dispensacionMaterialLazy;
    private int numeroRegistros;
    private String cadenaBusqueda;
    private Paciente_Extended pacienteExtended;
    private List<Paciente_Extended> listaPacienteCama;
    private Usuario medico;
    private List<Estructura> listaAuxiliar;
    private DispensacionMaterial dispensacion;
    private List<DispensacionMaterialExtended> listaDispensaciones;
    private DispensacionMaterialInsumoExtended dispensacionInsumoSelected;
    private List<DispensacionMaterialInsumoExtended> listaDispensacionInsumo;
    private DispensacionMaterialInsumoExtended dispensacionMaterialInsumo;
    private String idPaciente;
    private DispensacionMaterialExtended dispensacionSelected;
    private String errBuscar;
    private PermisoUsuario permiso;
    @Autowired
    private transient DispensacionMaterialService dispensacionMaterialService;

    @Autowired
    private transient DispensacionMaterialInsumoService dispensacionMaterialInsumoService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient CamaService camaService;

    @PostConstruct
    public void init() {
        errBuscar = "dispensacionMaterial.err.buscar";
        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.DISPENSMATCUR.getSufijo());
        validarUsuarioAdministrador();
        alimentarComboAlmacen();
        obtenerDispensaciones();
    }

    private void initialize() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = sesion.getUsuarioSelected();
        this.isAdmin = false;
        estructuraList = new ArrayList<>();
        listaDispensaciones = new ArrayList<>();
        numeroRegistros = 0;
    }

    public void validarUsuarioAdministrador() {
        try {
            this.isAdmin = Comunes.isAdministrador();
            this.isJefeArea = Comunes.isJefeArea();
            if (!this.isAdmin && !this.isJefeArea) {
                this.idEstructura = currentUser.getIdEstructura();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    public void alimentarComboAlmacen() {
        try {
            Estructura est = new Estructura();
            if (!this.isAdmin && !this.isJefeArea) {
                est.setIdEstructura(currentUser.getIdEstructura());
                this.estructuraList = this.estructuraService.obtenerLista(est);
            } else {
                if (this.isAdmin) {
                    List<Integer> listaTipoAreaAlmacen = new ArrayList<>();
                    listaTipoAreaAlmacen.add(Constantes.TIPO_AREA_ALMACEN);
                    listaTipoAreaAlmacen.add(Constantes.TIPO_AREA_SERVICIO);
                    this.listaAuxiliar = this.estructuraService.getEstructuraListTipoAreaEstructura(listaTipoAreaAlmacen);
                    this.idEstructura = listaAuxiliar.get(0).getIdEstructura();
                    this.estructuraList.add(listaAuxiliar.get(0));
                    est = listaAuxiliar.get(0);
                }
                if (this.isJefeArea) {
                    boolean noConsultar = false;
                    List<Estructura> listaTemp = new ArrayList<>();
                    this.listaAuxiliar = this.estructuraService.getEstructurDugthersYMe(currentUser.getIdEstructura());
                    for (Estructura unaEstructura : this.listaAuxiliar) {
                        if (currentUser.getIdEstructura().equals(unaEstructura.getIdEstructura())) {
                            this.idEstructura = unaEstructura.getIdEstructura();
                            this.estructuraList.add(unaEstructura);
                            est = unaEstructura;
                            noConsultar = true;
                        }
                        if (!noConsultar) {
                            List<Estructura> lista = estructuraService.obtenerEstructurasPadreCadit(unaEstructura.getIdEstructura());
                            for (Estructura estructura : lista) {
                                listaTemp.add(estructura);
                            }
                            noConsultar = false;
                        }
                    }
                    this.listaAuxiliar.addAll(listaTemp);
                }
                ordenarListaEstructura(est);
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null
                            && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        estructuraList.add(listaAuxiliar.get(i));
                        ordenarListaEstructura(listaAuxiliar.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }

    public void obtenerDispensaciones() {
        if (permiso.isPuedeVer()) {
            try {
                if (isAdmin || isJefeArea) {
                    dispensacionMaterialLazy = new DispensacionMaterialLazy(dispensacionMaterialService, idEstructura, null, cadenaBusqueda);
                } else {
                    dispensacionMaterialLazy = new DispensacionMaterialLazy(dispensacionMaterialService, idEstructura, currentUser.getIdUsuario(), cadenaBusqueda);
                }
                LOGGER.debug("Resultados: {}", dispensacionMaterialLazy.getTotalReg());
                numeroRegistros = listaDispensaciones.size();
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("dispensacionMaterial.err.listaDispensa"), ex);
            }
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionMaterial.err.permTransacc"), null);
        }
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

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public List<DispensacionMaterialExtended> getListaDispensaciones() {
        return listaDispensaciones;
    }

    public void setListaDispensaciones(List<DispensacionMaterialExtended> listaDispensaciones) {
        this.listaDispensaciones = listaDispensaciones;
    }

    public DispensacionMaterialLazy getDispensacionMaterialLazy() {
        return dispensacionMaterialLazy;
    }

    public void setDispensacionMaterialLazy(DispensacionMaterialLazy dispensacionMaterialLazy) {
        this.dispensacionMaterialLazy = dispensacionMaterialLazy;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<Paciente_Extended> getListaPacienteCama() {
        return listaPacienteCama;
    }

    public void setListaPacienteCama(List<Paciente_Extended> listaPacienteCama) {
        this.listaPacienteCama = listaPacienteCama;
    }

    public Paciente_Extended obtenerPorIdPaciente(String idPaciente) {
        Paciente_Extended item = null;
        if (listaPacienteCama != null) {
            for (Paciente_Extended pac : listaPacienteCama) {
                if (pac.getIdPaciente().equalsIgnoreCase(idPaciente)) {
                    item = pac;
                    break;
                }
            }
        }
        return item;
    }

    private void obtenerCamasPacientes() {
        List<Paciente_Extended> listaPacienteCamaTemp;
        listaPacienteCama = new ArrayList();
        listaPacienteCamaTemp = new ArrayList();
        try {
            listaPacienteCamaTemp = pacienteService.obtenerPacientesYCamas(idEstructura, 500, null);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerCamasPacientes :: {}", e.getMessage());
        }
        if (listaPacienteCamaTemp != null && !listaPacienteCamaTemp.isEmpty()) {
            for (Paciente_Extended pe : listaPacienteCamaTemp) {
                Paciente_Extended finded = listaPacienteCama.stream()
                        .filter(paciente -> pe.getNombreCama().equalsIgnoreCase(paciente.getNombreCama()))
                        .findAny()
                        .orElse(null);
                if (finded == null && !pe.getNombreCama().equalsIgnoreCase("Sin asignar")) {
                    listaPacienteCama.add(pe);
                }
            }
            Collections.sort(listaPacienteCama, (d1, d2) -> 
                d1.getNombreCama().compareToIgnoreCase(d2.getNombreCama())
            );
        }
    }

    public void crearDispensacion() {
        dispensacion = new DispensacionMaterial();
        dispensacionMaterialInsumo = new DispensacionMaterialInsumoExtended();
        idPaciente = null;
        pacienteExtended = null;
        listaDispensacionInsumo = new ArrayList();
        obtenerCamasPacientes();
        medico = null;
    }

    public DispensacionMaterial getDispensacion() {
        return dispensacion;
    }

    public void setDispensacion(DispensacionMaterial dispensacion) {
        this.dispensacion = dispensacion;
    }

    public List<DispensacionMaterialInsumoExtended> getListaDispensacionInsumo() {
        return listaDispensacionInsumo;
    }

    public void setListaDispensacionInsumo(List<DispensacionMaterialInsumoExtended> listaDispensacionInsumo) {
        this.listaDispensacionInsumo = listaDispensacionInsumo;
    }

    public DispensacionMaterialInsumoExtended getDispensacionInsumoSelected() {
        return dispensacionInsumoSelected;
    }

    public void setDispensacionInsumoSelected(DispensacionMaterialInsumoExtended dispensacionInsumoSelected) {
        this.dispensacionInsumoSelected = dispensacionInsumoSelected;
    }

    public DispensacionMaterialInsumoExtended getDispensacionMaterialInsumo() {
        return dispensacionMaterialInsumo;
    }

    public void setDispensacionMaterialInsumo(DispensacionMaterialInsumoExtended dispensacionMaterialInsumo) {
        this.dispensacionMaterialInsumo = dispensacionMaterialInsumo;
    }

    public void seleccionaCama() {
        pacienteExtended = obtenerPorIdPaciente(idPaciente);
    }

    public void seleccionaPaciente() {
        idPaciente = pacienteExtended.getIdPaciente();
    }

    public void seleccionaInsumo() {
        listaDispensacionInsumo.add(dispensacionMaterialInsumo);
        dispensacionMaterialInsumo = new DispensacionMaterialInsumoExtended();
    }

    public void eliminaInsumo(int index) {
        listaDispensacionInsumo.remove(index);
    }

    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }

    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        List<Paciente_Extended> lista = new ArrayList<>();
        try {
            lista = this.pacienteService.obtenerPacientesYCamasPorCriterioBusqueda(idEstructura, 500, cadena.trim());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return lista;
    }

    public List<Usuario> autoCompleteMedicos(String cadena) {
        List<Usuario> listaMedicos = new ArrayList<>();
        try {
            Integer prescribeControlados = 1;
            listaMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                    cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, prescribeControlados);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompleteMedicos : {}", ex.getMessage());
        }
        return listaMedicos;
    }

    public List<DispensacionMaterialInsumoExtended> autocompleteInsumo(String cadena) {
        LOGGER.debug("mx.mc.magedbean.DispensacionMaterialMB.autocompleteInsumo()");
        List<DispensacionMaterialInsumoExtended> insumosList = new ArrayList<>();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(cadena);
            if (ci != null) {
                String claveInstitucional = ci.getClave();
                String lote = ci.getLote();
                Date fechaCaducidad = ci.getFecha();
                DispensacionMaterialInsumoExtended matC = buscarPorCodigo(claveInstitucional, lote, fechaCaducidad);
                if (matC != null) {
                    insumosList.add(matC);
                }
            } else {
                insumosList.addAll(dispensacionMaterialInsumoService.obtenerInsumosDispensacion(cadena, idEstructura, CatalogoGeneral_Enum.MATERIAL.getValue()));
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(errBuscar));
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errBuscar), null);
        }
        return insumosList;
    }

    private DispensacionMaterialInsumoExtended buscarPorCodigo(String claveInstitucional, String lote, Date fechaCaducidad) {
        LOGGER.debug("mx.mc.magedbean.DispensacionMaterialMB.buscarPorCodigo()");
        try {
            return dispensacionMaterialInsumoService.obtenerInsumoPorQR(claveInstitucional, lote, fechaCaducidad, idEstructura, CatalogoGeneral_Enum.MATERIAL.getValue());
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(errBuscar));
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errBuscar), null);
        }
        return null;
    }

    public DispensacionMaterialExtended getDispensacionSelected() {
        return dispensacionSelected;
    }

    public void setDispensacionSelected(DispensacionMaterialExtended dispensacionSelected) {
        this.dispensacionSelected = dispensacionSelected;
    }

    public void obtenerDetalle(DispensacionMaterialExtended dispensacionSelected) {
        this.dispensacionSelected = dispensacionSelected;
        try {
            listaDispensacionInsumo = dispensacionMaterialInsumoService.obtenerInsumosPorIdDispensacion(dispensacionSelected.getIdDispensacion());
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dispensacionMaterial.err.detalle"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacionMaterial.err.detalle"), null);
        }
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void guardarDispensacion() {
        boolean estatus = false;
        try {
            String valida = validarCamposObligatorios();
            if (!valida.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, valida, "");
                return;
            }
            //pacienteExtended = obtenerPorIdPaciente(idPaciente);
            if (pacienteExtended != null) {
                Folios folio = dispensacionMaterialService.obtenerFolioDispensacion();
                dispensacion.setFolio(Comunes.generaFolio(folio));
                folio.setSecuencia(Comunes.separaFolio(dispensacion.getFolio()));

                dispensacion.setIdDispensacion(Comunes.getUUID());
                dispensacion.setFechaDispensacion(new Date());
                dispensacion.setIdEstructura(idEstructura);
                dispensacion.setIdPaciente(pacienteExtended.getIdPaciente());

                if (pacienteExtended.getNombreCama() != null && !pacienteExtended.getNombreCama().isEmpty()
                        && !pacienteExtended.getNombreCama().equalsIgnoreCase("Sin asignar")) {
                    Cama cama = new Cama();
                    cama.setNombreCama(pacienteExtended.getNombreCama());
                    cama = camaService.obtenerPorNombre(cama);
                    if (cama != null) {
                        dispensacion.setIdCama(cama.getIdCama());
                    }
                }

                dispensacion.setIdVisita(pacienteExtended.getIdVisita());

                if (medico != null) {
                    dispensacion.setIdMedico(medico.getIdUsuario());
                }
                dispensacion.setIdUsuarioDispensa(currentUser.getIdUsuario());
                dispensacion.setEstatus(EstatusDispensacionMaterial_Enum.REGISTRADA.getValue());

                dispensacion.setInsertFecha(dispensacion.getFechaDispensacion());
                dispensacion.setInsertIdUsuario(currentUser.getIdUsuario());

                Estructura estServicio = new Estructura();
                estServicio.setIdEstructura(idEstructura);
                estServicio = estructuraService.obtener(estServicio);

                List<DispensacionMaterialInsumo> listaInsumos = new ArrayList();
                List<Inventario> inventariosAfectados = new ArrayList();
                List<MovimientoInventario> movimientosInventario = new ArrayList();
                for (DispensacionMaterialInsumoExtended insumoExtended : listaDispensacionInsumo) {
                    DispensacionMaterialInsumo matInsumo = (DispensacionMaterialInsumo) insumoExtended;
                    matInsumo.setIdDispensacionInsumo(Comunes.getUUID());
                    matInsumo.setIdDispensacion(dispensacion.getIdDispensacion());
                    matInsumo.setInsertFecha(dispensacion.getFechaDispensacion());
                    matInsumo.setInsertIdUsuario(currentUser.getIdUsuario());
                    listaInsumos.add(matInsumo);

                    Inventario inv = new Inventario();
                    inv.setIdInventario(matInsumo.getIdInventario());
                    inv.setCantidadActual(matInsumo.getCantidad());
                    inventariosAfectados.add(inv);

                    MovimientoInventario movInv = new MovimientoInventario();
                    movInv.setIdMovimientoInventario(Comunes.getUUID());
                    Integer idTipoMotivo = TipoMotivo_Enum.DISPENSACION_DE_MATERIAL.getMotivoValue();
                    movInv.setIdTipoMotivo(idTipoMotivo);
                    movInv.setFecha(new java.util.Date());
                    movInv.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                    movInv.setIdEstrutcuraOrigen(estServicio.getIdAlmacenPeriferico());
                    movInv.setIdEstrutcuraDestino(idEstructura);
                    movInv.setIdInventario(matInsumo.getIdInventario());
                    movInv.setCantidad(matInsumo.getCantidad());
                    movInv.setFolioDocumento(dispensacion.getFolio());

                    movimientosInventario.add(movInv);
                }
                estatus = dispensacionMaterialService.registrarDispensacionMaterial(folio, dispensacion, listaInsumos, inventariosAfectados, movimientosInventario);
            } else {
                LOGGER.error(RESOURCES.getString("dispensacionMaterial.err.dispensar"));
                LOGGER.error("No se encontró el idPaciente en la lista");
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("dispensacionMaterial.err.dispensar"), ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public String validarCamposObligatorios() {
        if (pacienteExtended == null || pacienteExtended.getIdPaciente() == null || pacienteExtended.getIdPaciente().isEmpty()) {
            return RESOURCES.getString("dispensacionMaterial.err.paciente");
        }
        if (listaDispensacionInsumo.isEmpty()) {
            return RESOURCES.getString("dispensacionMaterial.err.insumos");
        }
        return "";
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
