package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.GrupoCatalogoGeneral_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.PacientesLazy;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.Pais;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Sepomex;
import mx.mc.model.Turno;
import mx.mc.model.Usuario;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.PacienteDomicilioService;
import mx.mc.service.PacienteResponsableService;
import mx.mc.service.PacienteService;
import mx.mc.service.PaisService;
import mx.mc.service.SepomexService;
import mx.mc.service.TurnoService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Cervanets
 */
@Controller
@Scope(value = "view")
public class VitalesAntropometricoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(VitalesAntropometricoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private Usuario usuarioEnSesion;
    private Date fechaActual;
    private PermisoUsuario permiso;

    private String errPermisos;
    private String estatusModal;
    private String textoBusqueda;

    private PacientesLazy pacientesLazy;
    private ParamBusquedaReporte paramBusquedaReporte;
    private Paciente_Extended pacienteSelect;

    private transient List<CatalogoGeneral> listaTipoPacientes;
    private transient List<CatalogoGeneral> listaUnidadesMedicas;
    private transient List<CatalogoGeneral> listaEstadoCivil;
    private transient List<CatalogoGeneral> listaEscolaridad;
    private transient List<CatalogoGeneral> listaOcupacion;
    private transient List<CatalogoGeneral> listaGrupEtnico;
    private transient List<CatalogoGeneral> listaReligion;
    private transient List<CatalogoGeneral> listaGpoSanguineo;
    private transient List<CatalogoGeneral> listaNivSocEconom;
    private transient List<CatalogoGeneral> listaTipoVivienda;
    private transient List<CatalogoGeneral> listaParentesco;
    private transient List<Pais> listaPaises;
    private transient List<Sepomex> listaEstados;
    private transient List<Sepomex> listaMunicipios;
    private transient List<Sepomex> listaColonias;
    private List<Sepomex> listaCodigosPost;
    private transient List<Turno> listaTurnos;

    private Paciente paciente;
    private PacienteDomicilio pacienteDomicilio;
    private PacienteResponsable pacienteResponsable;

    private boolean mostrarDatosUbicacion;
    private boolean renderBotonGuardar;
    private boolean renderBotonActualizar;
    private boolean desabilitado;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;

    @Autowired
    private transient PaisService paisService;

    @Autowired
    private transient SepomexService sepomexService;

    @Autowired
    private transient TurnoService turnoService;

    @Autowired
    private transient PacienteDomicilioService pacienteDomicilioService;

    @Autowired
    private transient PacienteResponsableService pacienteResponsableService;

    @Autowired
    private transient PacienteService pacienteService;

    /**
     * Metodo que se ejecutaposterio al constructuro
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.VitalesAntropometricoMB.init()");
        inicializaValores();
        buscaPacientes();
    }

    /**
     * Inicializa variables
     */
    private void inicializaValores() {
        LOGGER.trace("mx.mc.magedbean.VitalesAntropometricoMB.inicializaValores()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        this.usuarioEnSesion = sesion.getUsuarioSelected();
        this.fechaActual = new Date();
//        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.VITALESANTROPOMETRICO.getSufijo());
        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.PACIENTES_EGR_ING.getSufijo());

        this.errPermisos = "estr.err.permisos";
        this.estatusModal = "estatusModal";
        this.paramBusquedaReporte = new ParamBusquedaReporte();
    }

    /**
     * Metodo utilizado para buscar pacientes de acuerdo a un paramentro de
     * busqueda
     */
    public void buscaPacientes() {
        LOGGER.trace("mx.mc.magedbean.VitalesAntropometricoMB.buscaPacientes()");
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setCadenaBusqueda(textoBusqueda);
            pacientesLazy = new PacientesLazy(pacienteService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", pacientesLazy.getTotalReg());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscaPacientes :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicaliza los datos en el modal de la pantalla de Pacientes
     *
     * @throws Exception
     */
    public void cargarDatosCombosModal() throws Exception {
        LOGGER.trace("mx.mc.magedbean.VitalesAntropometricoMB.cargarDatosCombosModal()");
        this.listaTipoPacientes = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_DE_PACIENTE.getValue());
        this.listaUnidadesMedicas = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.UNIDAD_MEDICA.getValue());
        this.listaEstadoCivil = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.ESTADO_CIVIL.getValue());
        this.listaEscolaridad = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.ESCOLARIDAD.getValue());
        this.listaOcupacion = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.OCUPACION.getValue());
        this.listaGrupEtnico = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.GPO_ETNICO.getValue());
        this.listaReligion = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.RELIGION.getValue());
        this.listaGpoSanguineo = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.GRUPO_SANGUINEO.getValue());
        this.listaNivSocEconom = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.NVEL_SOC_ECONOM.getValue());
        this.listaTipoVivienda = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_VIVIENDA.getValue());
        this.listaParentesco = this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_PARENTESCO.getValue());
        this.listaPaises = this.paisService.obtenerLista(null);
        this.listaEstados = this.sepomexService.obtenerEstados();
        this.listaTurnos = this.turnoService.obtenerLista(new Turno());
    }

    /**
     *
     */
    public void evaluaMostrarUbicacion() {
        LOGGER.trace("mx.mc.magedbean.VitalesAntropometricoMB.evaluaMostrarUbicacion()");
        this.mostrarDatosUbicacion = (this.pacienteDomicilio.getIdPais() == Constantes.ID_PAIS_MEXICO);
    }

    /**
     * Metodo utilizado para obener los datos del detalle al pulsar el boton en
     * el registro
     *
     * @param idPaciente
     */
    public void mostrarDatosDePaciente(String idPaciente) {
        LOGGER.trace("mx.mc.magedbean.VitalesAntropometricoMB.mostrarDatosDePaciente()");
        boolean estatus = Constantes.INACTIVO;
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;

            } else if (idPaciente == null || idPaciente.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente incorrecto", "");
                return;

            } else {
                limpiar();
                cargarDatosCombosModal();
                this.pacienteSelect = (Paciente_Extended) pacienteService.obtenerPacienteByIdPaciente(idPaciente);

                if (this.pacienteSelect == null || this.pacienteSelect.getIdPaciente() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente no encontrado", "");
                    return;

                } else {
                    estatus = Constantes.ACTIVO;
                    this.pacienteDomicilio = pacienteDomicilioService.obtenerPacienteDomicilioByIdPaciente(idPaciente);
                    if (this.pacienteDomicilio.getIdPais() == Constantes.ID_PAIS_MEXICO) {
                        this.listaEstados = sepomexService.obtenerEstados();
                        this.listaMunicipios = sepomexService.obtenerMunicipios(this.pacienteDomicilio.getIdEstado());
                        this.listaColonias = sepomexService.obtenerColonias(this.pacienteDomicilio.getIdEstado(), this.pacienteDomicilio.getIdMunicipio());
                    }
                    evaluaMostrarUbicacion();
                    this.pacienteResponsable = pacienteResponsableService.obtenerPacienteResponsableByIdPaciente(idPaciente);
                    this.renderBotonGuardar = false;
                    this.renderBotonActualizar = false;
                    this.desabilitado = true;
//                        this.polizaSeguroDisabled = true;
//                        this.unidadMedicaDisabled = true;
//                        this.tabCheckResponsable = true;
//                        this.tabCheckPersonales = true;
//                        this.tabCheckDireccion = true;
//                        this.tabIndex = 0;
                }
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente no encontrado", "");
            LOGGER.error("Error al buscar el Paciente {}", e.getMessage());
        }

        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void limpiar() {
        this.textoBusqueda = "";
        this.pacienteSelect = new Paciente_Extended();
        this.pacienteDomicilio = new PacienteDomicilio();
        this.pacienteResponsable = new PacienteResponsable();
    }

    /**
     * Metodo que se encarga de poblar el combo de municipios
     */
    public void onChangeEstados() {
        this.listaMunicipios = new ArrayList<>();
        if (pacienteDomicilio != null) {
            try {
                this.listaMunicipios = sepomexService.obtenerMunicipios(this.pacienteDomicilio.getIdEstado());
            } catch (Exception e) {
                LOGGER.error("Error en el metodo onChangeEstados :: {}", e.getMessage());
            }
        }
    }

    /**
     * Metodo que se encarga de poblar el combo de colonias
     */
    public void onChangeMunicipios() {
        this.listaColonias = new ArrayList<>();
        if (pacienteDomicilio != null) {
            try {
                this.listaColonias = sepomexService.obtenerColonias(this.pacienteDomicilio.getIdEstado(), this.pacienteDomicilio.getIdMunicipio());
            } catch (Exception e) {
                LOGGER.error("Error en el metodo onChangeMunicipios :: {}", e.getMessage());
            }
        }
    }

    /**
     * Metodo que se encarga de poblar el campo de codigo postal
     */
    public void onChangeColonias() {
        if (pacienteDomicilio != null) {
            try {
                this.pacienteDomicilio.setCodigoPostal("");
                Sepomex sepomex = sepomexService.obtenerCodPost(this.pacienteDomicilio.getIdEstado(), this.pacienteDomicilio.getIdMunicipio(), this.pacienteDomicilio.getIdColonia());
                if (sepomex != null) {
                    this.pacienteDomicilio.setCodigoPostal(sepomex.getdCodigo());
                }
//                Sepomex item = new Sepomex();
//                item.setdAsenta(sepomex.getdCodigo());
//                item.setdCodigo(sepomex.getdCodigo());
//                this.codigoPostalSepomex = item;
            } catch (Exception e) {
                LOGGER.error("Error en el metodo onChangeMunicipios :: {}", e.getMessage());
            }
        }
    }

    /**
     *
     * @param valor
     * @return
     */
    public List<Sepomex> autoCompletarCodigoPostal(String valor) {
        try {
            this.listaCodigosPost = sepomexService.obtenerEstadoMunYColByCodPost(valor);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo autoCompletarCodigoPostal :: {}", e.getMessage());
        }
        return this.listaCodigosPost;
    }
    
    /**
     * 
     * @param event 
     */
    public void onItemSelect(SelectEvent event) {
        if (pacienteDomicilio != null){
            try {
                Sepomex sepomex = (Sepomex) event.getObject();
                if (sepomex!= null){
                    this.pacienteDomicilio.setIdEstado(sepomex.getcEstado());
                    this.pacienteDomicilio.setIdMunicipio(sepomex.getcMnpio());
                    this.pacienteDomicilio.setIdColonia(sepomex.getIdAsentaCpcons());
                    this.listaMunicipios = sepomexService.obtenerMunicipios(this.pacienteDomicilio.getIdEstado());
                    this.listaColonias = sepomexService.obtenerColonias(this.pacienteDomicilio.getIdEstado(), this.pacienteDomicilio.getIdMunicipio());
//                    this.codigoPostalSepomex.setdAsenta(sepomex.getdCodigo());
                    this.pacienteDomicilio.setCodigoPostal(sepomex.getdCodigo());
                }
            } catch (Exception e) {
                LOGGER.error("Error en el metodo autoCompletarCodigoPostal :: {}", e.getMessage());
            }
        }
    }

    public PacientesLazy getPacientesLazy() {
        return pacientesLazy;
    }

    public void setPacientesLazy(PacientesLazy pacientesLazy) {
        this.pacientesLazy = pacientesLazy;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public Paciente_Extended getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente_Extended pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public Usuario getUsuarioEnSesion() {
        return usuarioEnSesion;
    }

    public void setUsuarioEnSesion(Usuario usuarioEnSesion) {
        this.usuarioEnSesion = usuarioEnSesion;
    }

    public String getEstatusModal() {
        return estatusModal;
    }

    public void setEstatusModal(String estatusModal) {
        this.estatusModal = estatusModal;
    }

    public List<CatalogoGeneral> getListaTipoPacientes() {
        return listaTipoPacientes;
    }

    public void setListaTipoPacientes(List<CatalogoGeneral> listaTipoPacientes) {
        this.listaTipoPacientes = listaTipoPacientes;
    }

    public List<CatalogoGeneral> getListaUnidadesMedicas() {
        return listaUnidadesMedicas;
    }

    public void setListaUnidadesMedicas(List<CatalogoGeneral> listaUnidadesMedicas) {
        this.listaUnidadesMedicas = listaUnidadesMedicas;
    }

    public List<CatalogoGeneral> getListaEstadoCivil() {
        return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<CatalogoGeneral> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public List<CatalogoGeneral> getListaEscolaridad() {
        return listaEscolaridad;
    }

    public void setListaEscolaridad(List<CatalogoGeneral> listaEscolaridad) {
        this.listaEscolaridad = listaEscolaridad;
    }

    public List<CatalogoGeneral> getListaOcupacion() {
        return listaOcupacion;
    }

    public void setListaOcupacion(List<CatalogoGeneral> listaOcupacion) {
        this.listaOcupacion = listaOcupacion;
    }

    public List<CatalogoGeneral> getListaGrupEtnico() {
        return listaGrupEtnico;
    }

    public void setListaGrupEtnico(List<CatalogoGeneral> listaGrupEtnico) {
        this.listaGrupEtnico = listaGrupEtnico;
    }

    public List<CatalogoGeneral> getListaReligion() {
        return listaReligion;
    }

    public void setListaReligion(List<CatalogoGeneral> listaReligion) {
        this.listaReligion = listaReligion;
    }

    public List<CatalogoGeneral> getListaGpoSanguineo() {
        return listaGpoSanguineo;
    }

    public void setListaGpoSanguineo(List<CatalogoGeneral> listaGpoSanguineo) {
        this.listaGpoSanguineo = listaGpoSanguineo;
    }

    public List<CatalogoGeneral> getListaNivSocEconom() {
        return listaNivSocEconom;
    }

    public void setListaNivSocEconom(List<CatalogoGeneral> listaNivSocEconom) {
        this.listaNivSocEconom = listaNivSocEconom;
    }

    public List<CatalogoGeneral> getListaTipoVivienda() {
        return listaTipoVivienda;
    }

    public void setListaTipoVivienda(List<CatalogoGeneral> listaTipoVivienda) {
        this.listaTipoVivienda = listaTipoVivienda;
    }

    public List<CatalogoGeneral> getListaParentesco() {
        return listaParentesco;
    }

    public void setListaParentesco(List<CatalogoGeneral> listaParentesco) {
        this.listaParentesco = listaParentesco;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public List<Sepomex> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<Sepomex> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public List<Sepomex> getListaColonias() {
        return listaColonias;
    }

    public void setListaColonias(List<Sepomex> listaColonias) {
        this.listaColonias = listaColonias;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public PacienteDomicilio getPacienteDomicilio() {
        return pacienteDomicilio;
    }

    public void setPacienteDomicilio(PacienteDomicilio pacienteDomicilio) {
        this.pacienteDomicilio = pacienteDomicilio;
    }

    public PacienteResponsable getPacienteResponsable() {
        return pacienteResponsable;
    }

    public void setPacienteResponsable(PacienteResponsable pacienteResponsable) {
        this.pacienteResponsable = pacienteResponsable;
    }

    public boolean isMostrarDatosUbicacion() {
        return mostrarDatosUbicacion;
    }

    public void setMostrarDatosUbicacion(boolean mostrarDatosUbicacion) {
        this.mostrarDatosUbicacion = mostrarDatosUbicacion;
    }

    public boolean isRenderBotonGuardar() {
        return renderBotonGuardar;
    }

    public void setRenderBotonGuardar(boolean renderBotonGuardar) {
        this.renderBotonGuardar = renderBotonGuardar;
    }

    public boolean isRenderBotonActualizar() {
        return renderBotonActualizar;
    }

    public void setRenderBotonActualizar(boolean renderBotonActualizar) {
        this.renderBotonActualizar = renderBotonActualizar;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public void setDesabilitado(boolean desabilitado) {
        this.desabilitado = desabilitado;
    }

    public List<Sepomex> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Sepomex> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public List<Sepomex> getListaCodigosPost() {
        return listaCodigosPost;
    }

    public void setListaCodigosPost(List<Sepomex> listaCodigosPost) {
        this.listaCodigosPost = listaCodigosPost;
    }

}
