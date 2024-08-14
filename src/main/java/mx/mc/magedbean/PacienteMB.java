package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.PacientesLazy;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.GrupoCatalogoGeneral_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.Folios;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.Pais;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Sepomex;
import mx.mc.model.Turno;
import mx.mc.model.TurnoMedico;
import mx.mc.model.Usuario;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.FoliosService;
import mx.mc.service.PacienteDomicilioService;
import mx.mc.service.PacienteResponsableService;
import mx.mc.service.PacienteService;
import mx.mc.service.PaisService;
import mx.mc.service.SepomexService;
import mx.mc.service.TurnoMedicoService;
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
 * @author AORTIZ
 */
@Controller
@Scope(value = "view")
public class PacienteMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String textoBusqueda;
    private List<Paciente_Extended> listaPacientes;
    private Paciente_Extended pacienteSelect;
    private Date fechaNac;
    private List<CatalogoGeneral> listaTipoPacientes;
    private int idTipoPaciente;
    private int idTipoPacienteGenerico;
    private List<CatalogoGeneral> listaUnidadesMedicas;
    private int idUnidadMedica;
    private int idUnidadMedicaGenerico;
    private String claveDerechohabiencia;
    private String nombres;
    private String appPaterno;
    private String appMaterno;
    private char sexo;
    private char pacienteParticular;
    private String rfc;
    private String curp;
    private int idEstadoCivil;
    private List<CatalogoGeneral> listaEstadoCivil;
    private int idEstadoCivilGenerico;
    private int idEscolaridad;
    private List<CatalogoGeneral> listaEscolaridad;
    private int idEscolaridadGenerico;
    private int idOcupacion;
    private List<CatalogoGeneral> listaOcupacion;
    private int idOcupacionGenerico;
    private int idGrupoEtnico;
    private List<CatalogoGeneral> listaGrupEtnico;
    private int idGrupoEtnicoGenerico;
    private int idReligion;
    private List<CatalogoGeneral> listaReligion;
    private int idReligionGenerico;
    private int idGpoSanguineo;
    private List<CatalogoGeneral> listaGpoSanguineo;
    private int idGpoSanguineoGenerico;
    private int idNivSocEconom;
    private List<CatalogoGeneral> listaNivSocEconom;
    private int idNivSocEconomGenerico;
    private int idTipoVivienda;
    private List<CatalogoGeneral> listaTipoVivienda;
    private int idTipoViviendaGenerico;
    private int idParentesco;
    private List<CatalogoGeneral> listaParentesco;
    private String calle;
    private String noExt;
    private String noInt;
    private String codigoPostal;
    private Sepomex codigoPostalSepomex;
    private List<Sepomex> listaCodigosPost;
    private int idPais;
    private List<Pais> listaPaises;
    private boolean show;
    private String idEstado;
    private List<Sepomex> listaEstados;
    private String idMunicipio;
    private List<Sepomex> listaMunicipios;
    private String idColonia;
    private List<Sepomex> listaColonias;
    private String telPrincipal;
    private String telSecundario;
    private String extencion;
    private String telCelular;
    private String correo;
    private String nombRespFamiliar;
    private String appRespFamiliar;
    private String apmRespFamiliar;
    private String parentesco;
    private String rfcRespFamiliar;
    private String curpRespFamiliar;
    private String telPrincRespFamiliar;
    private String telSecRespFamiliar;
    private String correoRespFamiliar;
    private char respLegal;
    private String pacienteNumero;
    private String cuentaFacebook;
    private boolean desabilitado;
    private boolean habilitarCampoPacienteNumero;
    private boolean renderBotonActualizar;
    private boolean renderBotonGuardar;
    private String idPaciente;
    private String idPacienteDomicilio;
    private String idPacienteResponsable;
    private boolean tabCheckPersonales;
    private boolean tabCheckDireccion;
    private boolean tabCheckResponsable;
    private boolean polizaSeguroDisabled;
    private boolean unidadMedicaDisabled;
    private int tabIndex;
    private String pacienteNumeroGenerico;
    private String claveDerechohabienciaGenerico;
    private String nombresGenerico;
    private String appPaternoGenerico;
    private String appMaternoGenerico;
    private Date fechaNacGenerico;
    private String rfcGenerico;
    private String curpGenerico;
    private char sexoGenerico;
    private List<Integer> listaIdTurnos;
    private List<Turno> listaTurnos;
    private List<String> listaIdTurnosGenerico;
    
    private String lugarNacimiento;
    private String paisResidencia;
    private Integer presentaDiscapacidad;
    private String descripcionDiscapacidad;

    private String errPermisos;
    private String estatusModal;
    private String errorOperacion;
    private Usuario usuarioSelect;
    private PermisoUsuario permiso;
    private PacientesLazy pacientesLazy;
    private ParamBusquedaReporte paramBusquedaReporte;

    private Pattern regexNomAp;

    private Date fechaActual;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;

    @Autowired
    private transient PaisService paisService;

    @Autowired
    private transient SepomexService sepomexService;

    @Autowired
    private transient PacienteResponsableService pacienteResponsableService;

    @Autowired
    private transient PacienteDomicilioService pacienteDomicilioService;

    @Autowired
    private transient TurnoService turnoService;

    @Autowired
    private transient TurnoMedicoService turnoMedicoService;

    @Autowired
    private transient FoliosService foliosService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            errPermisos = "estr.err.permisos";
            estatusModal = "estatusModal";
            errorOperacion = "surtimiento.error.operacion";
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.PACIENTES.getSufijo());
            buscarRegistros();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicaliza los datos en el modal de la pantalla de Pacientes
     *
     * @throws Exception
     */
    public void cargarDatosCombosModal() throws Exception {
        this.listaTipoPacientes = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_DE_PACIENTE.getValue());
        this.listaUnidadesMedicas = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.UNIDAD_MEDICA.getValue());
        this.listaEstadoCivil = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.ESTADO_CIVIL.getValue());
        this.listaEscolaridad = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.ESCOLARIDAD.getValue());
        this.listaOcupacion = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.OCUPACION.getValue());
        this.listaGrupEtnico = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.GPO_ETNICO.getValue());
        this.listaReligion = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.RELIGION.getValue());
        this.listaGpoSanguineo = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.GRUPO_SANGUINEO.getValue());
        this.listaNivSocEconom = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.NVEL_SOC_ECONOM.getValue());
        this.listaTipoVivienda = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_VIVIENDA.getValue());
        this.listaParentesco = this.catalogoGeneralService
                .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_PARENTESCO.getValue());
        this.listaPaises = this.paisService.obtenerLista(null);
        this.listaEstados = this.sepomexService.obtenerEstados();
        this.listaTurnos = this.turnoService.obtenerLista(new Turno());
    }

    /**
     * Metodo que inicaliza los datos en el modal de la pantalla de Pacientes
     *
     * @throws Exception
     */
    public void cargarDatosCombosModalEmergencia() throws Exception {
        cargarDatosCombosModal();
        idTipoPacienteGenerico = CatalogoGeneral_Enum.TIPO_PACIENTE_NO_DEFINIDO.getValue();
        idUnidadMedicaGenerico = CatalogoGeneral_Enum.UNIDAD_MEDICA_OTRA.getValue();
        idOcupacionGenerico = CatalogoGeneral_Enum.OCUPACION_NO_DEFINIDA.getValue();
        idGrupoEtnicoGenerico = CatalogoGeneral_Enum.GRUPO_ETNICO_NO_DEFINIDO.getValue();
        idEstadoCivilGenerico = CatalogoGeneral_Enum.ESTADO_CIVIL_NO_DEFINIDO.getValue();
        idEscolaridadGenerico = CatalogoGeneral_Enum.ESCOLARIDAD_NO_DEFINIDA.getValue();
        idReligionGenerico = CatalogoGeneral_Enum.RELIGION_NO_DEFINIDA.getValue();
        idGpoSanguineoGenerico = CatalogoGeneral_Enum.GRUPO_SANGUINEO_NO_DEFINIDO.getValue();
        idNivSocEconomGenerico = CatalogoGeneral_Enum.NIVEL_SOCIOECONOMICO_NO_DEFINIDO.getValue();
        idTipoViviendaGenerico = CatalogoGeneral_Enum.TIPO_VIVIENDA_NO_DEFINIDA.getValue();
        habilitarCampoPacienteNumero = true;
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.fechaActual = new java.util.Date();
        this.textoBusqueda = "";
        this.listaPacientes = new ArrayList<>();
        this.pacienteSelect = new Paciente_Extended();
        this.fechaNac = new Date();
        this.listaTipoPacientes = new ArrayList<>();
        this.idTipoPaciente = 0;
        this.listaUnidadesMedicas = new ArrayList<>();
        this.idUnidadMedica = 0;
        this.claveDerechohabiencia = "";
        this.nombres = "";
        this.appPaterno = "";
        this.appMaterno = "";
        this.sexo = ' ';
        this.pacienteParticular = ' ';
        this.rfc = "";
        this.curp = "";
        
        this.lugarNacimiento = "";
        this.paisResidencia = "";
        this.presentaDiscapacidad = 0;
        this.descripcionDiscapacidad = "";
        
        this.habilitarCampoPacienteNumero = false;
        this.idEstadoCivil = 0;
        this.listaEstadoCivil = new ArrayList<>();
        this.idEscolaridad = 0;
        this.listaEscolaridad = new ArrayList<>();
        this.idOcupacion = 0;
        this.listaOcupacion = new ArrayList<>();
        this.idGrupoEtnico = 0;
        this.listaGrupEtnico = new ArrayList<>();
        this.idReligion = 0;
        this.listaReligion = new ArrayList<>();
        this.idGpoSanguineo = 0;
        this.listaGpoSanguineo = new ArrayList<>();
        this.idNivSocEconom = 0;
        this.listaNivSocEconom = new ArrayList<>();
        this.idTipoVivienda = 0;
        this.listaTipoVivienda = new ArrayList<>();
        this.calle = "";
        this.noExt = "";
        this.noInt = "";
        this.codigoPostal = "";
        this.idPais = 0;
        this.listaPaises = new ArrayList<>();
        this.show = false;
        this.idEstado = "";
        this.listaEstados = new ArrayList<>();
        this.idMunicipio = "";
        this.listaMunicipios = new ArrayList<>();
        this.idColonia = "";
        this.listaColonias = new ArrayList<>();
        this.telPrincipal = "";
        this.telSecundario = "";
        this.extencion = "";
        this.telCelular = "";
        this.correo = "";
        this.nombRespFamiliar = "";
        this.appRespFamiliar = "";
        this.apmRespFamiliar = "";
        this.parentesco = "";
        this.rfcRespFamiliar = "";
        this.curpRespFamiliar = "";
        this.telPrincRespFamiliar = "";
        this.telSecRespFamiliar = "";
        this.correoRespFamiliar = "";
        this.respLegal = ' ';
        this.idParentesco = 0;
        this.listaParentesco = new ArrayList<>();
        this.respLegal = ' ';
        this.pacienteNumero = "";
        this.cuentaFacebook = "";
        this.desabilitado = false;
        this.renderBotonActualizar = false;
        this.renderBotonGuardar = true;
        this.idPaciente = "";
        this.idPacienteDomicilio = "";
        this.idPacienteResponsable = "";
        this.tabCheckPersonales = false;
        this.tabCheckDireccion = false;
        this.tabCheckResponsable = false;
        this.polizaSeguroDisabled = false;
        this.unidadMedicaDisabled = false;
        this.tabIndex = 0;
        regexNomAp = Constantes.regexNombApell;
        paramBusquedaReporte = new ParamBusquedaReporte();
    }

    /**
     * Metodo utilizado para insertar un pasiente en la base de datos.
     */
    public void insertarPaciente() {
        try {
            if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            Usuario usuarioSesion = obtenerUsuarioSesion();
            if (validarDatosModal()) {
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, false);
                return;
            }
            Paciente paciente = obtenerDatosPacienteIntroducidosPorUsuario();
            paciente.setIdPaciente(Comunes.getUUID());
            paciente.setIdEstatusPaciente(EstatusPaciente_Enum.REGISTRADO.getValue());
            paciente.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
            paciente.setInsertFecha(new Date());
            paciente.setInsertIdUsuario(usuarioSesion.getIdUsuario());

            PacienteDomicilio pacienteDomicilio = obtenerDatosPacienteDomicilioIntroducidosPorUsuario();
            pacienteDomicilio.setIdPacienteDomicilio(Comunes.getUUID());
            pacienteDomicilio.setIdPaciente(paciente.getIdPaciente());
            pacienteDomicilio.setInsertFecha(new Date());
            pacienteDomicilio.setInsertIdUsuario(usuarioSesion.getIdUsuario());

            PacienteResponsable pacienteResponsable = obtenerDatosPacienteResponsableIntroducidosPorUsuario();
            pacienteResponsable.setIdPacienteResponsable(Comunes.getUUID());
            pacienteResponsable.setIdPaciente(paciente.getIdPaciente());
            pacienteResponsable.setInsertIdUsuario(usuarioSesion.getIdUsuario());
            pacienteResponsable.setInsertFecha(new Date());

            List<TurnoMedico> listaTemp = new ArrayList<>();
            if (this.listaIdTurnos != null && !this.listaIdTurnos.isEmpty()) {
                for (int item : this.listaIdTurnos) {
                    TurnoMedico turnoMedico = new TurnoMedico();
                    turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                    turnoMedico.setIdTurno(Integer.valueOf(item));
                    turnoMedico.setIdMedico(paciente.getIdPaciente());
                    listaTemp.add(turnoMedico);
                }

            }

            if (pacienteService.insertarPaciente(
                    paciente, pacienteDomicilio, pacienteResponsable, listaTemp)) {
                init();
                Mensaje.showMessage("Info", RESOURCES.getString("paciente.ok.agregar"), "");
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, true);
            } else {
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, false);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.error.agregar"), "");
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo insertarPaciente :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
        }
    }

    /**
     * Metodo utilizado para insertar un pasiente en la base de datos.
     */
    public void insertarPacienteGenerico() {
        try {
            if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            Usuario usuarioSesion = obtenerUsuarioSesion();
            String res = validarDatosModalPacienteGenerico();
            if (!res.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, res, "");
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, false);
                return;
            }
            Paciente paciente = obtenerDatosPacienteGenericoIntroducidosPorUsuario();
            paciente.setIdPaciente(Comunes.getUUID());
            paciente.setIdEstatusPaciente(EstatusPaciente_Enum.REGISTRADO.getValue());
            paciente.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
            paciente.setInsertFecha(new Date());
            paciente.setInsertIdUsuario(usuarioSesion.getIdUsuario());

            paciente.setIdTipoPaciente(CatalogoGeneral_Enum.TIPO_PACIENTE_NO_DEFINIDO.getValue());
            paciente.setIdOcupacion(CatalogoGeneral_Enum.OCUPACION_NO_DEFINIDA.getValue());
            paciente.setIdGrupoEtnico(CatalogoGeneral_Enum.GRUPO_ETNICO_NO_DEFINIDO.getValue());
            paciente.setIdEstadoCivil(CatalogoGeneral_Enum.ESTADO_CIVIL_NO_DEFINIDO.getValue());
            paciente.setIdEscolaridad(CatalogoGeneral_Enum.ESCOLARIDAD_NO_DEFINIDA.getValue());
            paciente.setIdReligion(CatalogoGeneral_Enum.RELIGION_NO_DEFINIDA.getValue());
            paciente.setIdGrupoSanguineo(CatalogoGeneral_Enum.GRUPO_SANGUINEO_NO_DEFINIDO.getValue());
            paciente.setIdNivelSocioEconomico(CatalogoGeneral_Enum.NIVEL_SOCIOECONOMICO_NO_DEFINIDO.getValue());
            paciente.setIdTipoVivienda(CatalogoGeneral_Enum.TIPO_VIVIENDA_NO_DEFINIDA.getValue());

            PacienteDomicilio pacienteDomicilio = new PacienteDomicilio();
            pacienteDomicilio.setIdPaciente(paciente.getIdPaciente());
            pacienteDomicilio.setIdPacienteDomicilio(Comunes.getUUID());
            pacienteDomicilio.setCalle(Constantes.NO_DEFINIDO);
            pacienteDomicilio.setNumeroExterior(Constantes.NO_DEFINIDO);
            pacienteDomicilio.setTelefonoCasa(Constantes.NO_DEFINIDO);
            pacienteDomicilio.setIdPais(Constantes.ID_PAIS_MEXICO);
            pacienteDomicilio.setInsertFecha(new Date());
            pacienteDomicilio.setInsertIdUsuario(usuarioSesion.getIdUsuario());

            List<TurnoMedico> listaTemp = new ArrayList<>();
            if (this.listaIdTurnos != null && !this.listaIdTurnos.isEmpty()) {
                for (int item : this.listaIdTurnos) {
                    TurnoMedico turnoMedico = null;
                    turnoMedico = new TurnoMedico();
                    turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                    turnoMedico.setIdTurno(Integer.valueOf(item));
                    turnoMedico.setIdMedico(paciente.getIdPaciente());
                    listaTemp.add(turnoMedico);
                }

            }

            if (pacienteService.insertarPaciente(
                    paciente, pacienteDomicilio, new PacienteResponsable(), listaTemp)) {
                init();
                Mensaje.showMessage("Info", RESOURCES.getString("paciente.ok.agregar"), "");
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, true);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.error.agregar"), "");
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, false);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo insertarPacienteGenerico :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
        }
    }

    public String validarDatosModalPacienteGenerico() {
        String respuesta = validarDatosPersonalesGenerico();
        if (!respuesta.isEmpty()) {
            this.tabCheckPersonales = false;
            return respuesta;
        } else {
            this.tabCheckPersonales = true;
        }
        return "";
    }

    public boolean validarDatosModal() {
        String respuesta = validarDatosPersonales();
        if (!respuesta.isEmpty()) {
            this.tabCheckPersonales = false;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, respuesta, "");
            return true;
        } else {
            this.tabCheckPersonales = true;
        }
        respuesta = validarDatosDireccion();
        if (!respuesta.isEmpty()) {
            this.tabCheckDireccion = false;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, respuesta, "");
            return true;
        } else {
            this.tabCheckDireccion = true;
        }
        respuesta = validarDatosResponsable();
        if (!respuesta.isEmpty()) {
            this.tabCheckResponsable = false;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, respuesta, "");
            return true;
        } else {
            this.tabCheckResponsable = true;
        }
        return false;
    }

    /**
     * Metodo que obtiene los datos del usuario en la sesion
     *
     * @return Usuario
     */
    public Usuario obtenerUsuarioSesion() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        return sesion.getUsuarioSelected();
    }

    /**
     * Metodo utilizado para actualizar un pasiente en la base de datos.
     */
    public void actualizarPaciente() {
        try {
            if (!this.permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errPermisos), "");
                return;
            }
            Usuario usuarioSesion = obtenerUsuarioSesion();
            changeRender();
            if (validarDatosModal()) {
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, false);
                return;
            }

            Paciente paciente = obtenerDatosPacienteIntroducidosPorUsuario();
            paciente.setEstatusGabinete(EstatusGabinete_Enum.ACTUALIZAR.getValue());
            paciente.setIdPaciente(this.idPaciente);
            paciente.setUpdateFecha(new Date());
            paciente.setUpdateIdUsuario(usuarioSesion.getIdUsuario());

            PacienteDomicilio pacienteDomicilio = obtenerDatosPacienteDomicilioIntroducidosPorUsuario();
            pacienteDomicilio.setIdPaciente(this.idPaciente);
            pacienteDomicilio.setIdPacienteDomicilio(this.idPacienteDomicilio);
            pacienteDomicilio.setCodigoPostal(codigoPostalSepomex.getdAsenta());
            pacienteDomicilio.setUpdateFecha(new Date());
            pacienteDomicilio.setUpdateIdUsuario(usuarioSesion.getIdUsuario());

            PacienteResponsable pacienteResponsable = obtenerDatosPacienteResponsableIntroducidosPorUsuario();
            pacienteResponsable.setIdPaciente(this.idPaciente);
            pacienteResponsable.setIdPacienteResponsable(this.idPacienteResponsable);
            pacienteResponsable.setUpdateFecha(new Date());
            pacienteResponsable.setUpdateIdUsuario(usuarioSesion.getIdUsuario());

            List<TurnoMedico> listaTemp = new ArrayList<>();
            if (this.listaIdTurnos != null && !this.listaIdTurnos.isEmpty()) {
                for (int item : this.listaIdTurnos) {
                    TurnoMedico turnoMedico = new TurnoMedico();
                    turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                    turnoMedico.setIdTurno(Integer.valueOf(item));
                    turnoMedico.setIdMedico(paciente.getIdPaciente());
                    listaTemp.add(turnoMedico);
                }

            }

            if (pacienteService.actualizarPaciente(
                    paciente, pacienteDomicilio, pacienteResponsable, listaTemp)) {
                limpiar();
                Mensaje.showMessage("Info", RESOURCES.getString("paciente.ok.update"), "");
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, true);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.error.update"), "");
                PrimeFaces.current().ajax().addCallbackParam(estatusModal, false);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo actualizarPaciente  :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
        }
    }

    /**
     * Metodo que obtiene los datos del responsable del paciente introducidos
     * por el usuario
     *
     * @return PacienteResponsable
     */
    public PacienteResponsable obtenerDatosPacienteResponsableIntroducidosPorUsuario() {
        PacienteResponsable pacienteResponsable = new PacienteResponsable();
        pacienteResponsable.setNombreCompleto(this.nombRespFamiliar);
        pacienteResponsable.setApellidoPaterno(this.appRespFamiliar);
        pacienteResponsable.setApellidoMaterno(this.apmRespFamiliar);
        pacienteResponsable.setTelefonoCasa(this.telPrincRespFamiliar);
        pacienteResponsable.setTelefonoCelular(this.telSecRespFamiliar);
        pacienteResponsable.setCorreoElectronico(this.correoRespFamiliar);
        pacienteResponsable.setIdParentesco(this.idParentesco);
        pacienteResponsable.setResponsableLegal(this.respLegal);
        pacienteResponsable.setRfc(this.rfcRespFamiliar);
        pacienteResponsable.setCurp(this.curpRespFamiliar);
        pacienteResponsable.setDomicilio("");
        pacienteResponsable.setComentarios("");
        return pacienteResponsable;
    }

    /**
     * Metodo que obtiene los datos del domicilio del paciente introducidos por
     * el usuario
     *
     * @return PacienteDomicilio
     */
    public PacienteDomicilio obtenerDatosPacienteDomicilioIntroducidosPorUsuario() {
        PacienteDomicilio pacienteDomicilio = new PacienteDomicilio();
        pacienteDomicilio.setIdPais(this.idPais);
        if (this.idPais == Constantes.ID_PAIS_MEXICO) {
            pacienteDomicilio.setIdEstado(this.idEstado);
            pacienteDomicilio.setIdMunicipio(this.idMunicipio);
            pacienteDomicilio.setIdColonia(this.idColonia);
            pacienteDomicilio.setCodigoPostal(this.codigoPostal);
        }
        pacienteDomicilio.setCalle(this.calle);
        pacienteDomicilio.setNumeroExterior(this.noExt);
        pacienteDomicilio.setNumeroInterior(this.noInt);
        pacienteDomicilio.setTelefonoCasa(this.telPrincipal);
        pacienteDomicilio.setTelefonoOficina(this.telSecundario);
        pacienteDomicilio.setExtencion(this.extencion);
        pacienteDomicilio.setTelefonoCelular(this.telCelular);
        pacienteDomicilio.setCorreoElectronico(this.correo);
        pacienteDomicilio.setCuentaFacebook(this.cuentaFacebook);
        pacienteDomicilio.setDomicilioActual(Constantes.ESTATUS_ACTIVO);
        return pacienteDomicilio;
    }

    /**
     * Metodo que obtiene los datos del paciente introducidos por el usuario
     *
     * @return Paciente
     */
    public Paciente obtenerDatosPacienteIntroducidosPorUsuario() {
        Paciente paciente = new Paciente();
        paciente.setNombreCompleto(this.nombres);
        paciente.setApellidoPaterno(this.appPaterno);
        paciente.setApellidoMaterno(this.appMaterno);
        paciente.setSexo(this.sexo);
        paciente.setFechaNacimiento(this.fechaNac);
        paciente.setRfc(this.rfc);
        paciente.setCurp(this.curp);
        
        paciente.setLugarNacimiento(this.lugarNacimiento);
        paciente.setPaisResidencia(this.paisResidencia);
        paciente.setPresentaDiscapacidad(this.presentaDiscapacidad);
        paciente.setDescripcionDiscapacidad(this.descripcionDiscapacidad);
        
        paciente.setIdTipoPaciente(this.idTipoPaciente);
        if (this.idTipoPaciente != CatalogoGeneral_Enum.DERECHOHABIENTE.getValue()) {
            paciente.setClaveDerechohabiencia(this.claveDerechohabiencia);
            paciente.setIdUnidadMedica(this.idUnidadMedica);
        }
        paciente.setPacienteNumero(this.pacienteNumero);
        paciente.setIdEstadoCivil(this.idEstadoCivil);
        paciente.setIdEscolaridad(this.idEscolaridad);
        paciente.setIdGrupoEtnico(this.idGrupoEtnico);
        paciente.setIdGrupoSanguineo(this.idGpoSanguineo);
        paciente.setIdReligion(this.idReligion);
        paciente.setIdNivelSocioEconomico(this.idNivSocEconom);
        paciente.setIdTipoVivienda(this.idTipoVivienda);
        paciente.setIdOcupacion(this.idOcupacion);
        return paciente;
    }

    /**
     * Metodo que obtiene los datos del paciente introducidos por el usuario
     *
     * @return Paciente
     */
    public Paciente obtenerDatosPacienteGenericoIntroducidosPorUsuario() {
        Paciente paciente = new Paciente();
        paciente.setNombreCompleto(this.nombresGenerico);
        paciente.setApellidoPaterno(this.appPaternoGenerico);
        paciente.setApellidoMaterno(this.appMaternoGenerico);
        paciente.setSexo(this.sexoGenerico);
        paciente.setFechaNacimiento(this.fechaNacGenerico);
        paciente.setRfc(this.rfcGenerico);
        paciente.setCurp(this.curpGenerico);
        paciente.setIdTipoPaciente(this.idTipoPaciente);
        if (this.idTipoPaciente != CatalogoGeneral_Enum.DERECHOHABIENTE.getValue()) {
            paciente.setClaveDerechohabiencia(this.claveDerechohabienciaGenerico);
            paciente.setIdUnidadMedica(this.idUnidadMedica);
        }
        paciente.setPacienteNumero(this.pacienteNumeroGenerico);
        paciente.setIdEstadoCivil(this.idEstadoCivil);
        paciente.setIdEscolaridad(this.idEscolaridad);
        paciente.setIdGrupoEtnico(this.idGrupoEtnico);
        paciente.setIdGrupoSanguineo(this.idGpoSanguineo);
        paciente.setIdReligion(this.idReligion);
        paciente.setIdNivelSocioEconomico(this.idNivSocEconom);
        paciente.setIdTipoVivienda(this.idTipoVivienda);
        paciente.setIdOcupacion(this.idOcupacion);
        return paciente;
    }

    /**
     * Metodo que se encarga de cambiar la visualizacion de los controles a
     * ocultos o mostrados
     */
    public void changeRender() {
        this.show = false;
        if (this.idPais == Constantes.ID_PAIS_MEXICO) {
            this.show = true;
        }
    }

    /**
     * Metodo que se encarga de poblar el combo de municipios
     */
    public void onChangeEstados() {
        try {
            this.idMunicipio = "";
            this.idColonia = "";
            this.codigoPostal = "";
            this.listaMunicipios = sepomexService.obtenerMunicipios(this.idEstado);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo onChangeEstados :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que se encarga de poblar el combo de colonias
     */
    public void onChangeMunicipios() {
        try {
            this.idColonia = "";
            this.codigoPostal = "";
            this.listaColonias = sepomexService.obtenerColonias(this.idEstado, this.idMunicipio);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo onChangeMunicipios :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que se encarga de poblar el campo de codigo postal
     */
    public void onChangeColonias() {
        try {
            Sepomex sepomex = sepomexService.obtenerCodPost(
                    this.idEstado, this.idMunicipio, this.idColonia);
            this.codigoPostal = sepomex.getdCodigo();
            Sepomex item = new Sepomex();
            item.setdAsenta(sepomex.getdCodigo());
            item.setdCodigo(sepomex.getdCodigo());
            this.codigoPostalSepomex = item;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo onChangeMunicipios :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que valida que los datos del paciente sean correctos retorna true
     * si algun dato es incorrecto
     *
     * @return boolean
     */
    public String validarDatosPersonales() {
        Matcher mat;
        if (this.pacienteNumero.isEmpty()) {
            return RESOURCES.getString("paciente.error.validarClave");
        }
        
        if (this.claveDerechohabiencia.isEmpty()) {
            return RESOURCES.getString("paciente.error.ns");
        }

        if (this.idTipoPaciente == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.tipoPaciente");
        }

        if (this.idUnidadMedica == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.umedica");
        }
        
//        if (this.idUnidadMedica == UnidadMedicaRef_Enum.SEGURO_POPULAR.getValue()
//                && this.claveDerechohabiencia != null
//                && this.claveDerechohabiencia.isEmpty()) {
//            return RESOURCES.getString("paciente.error.ns");
//        }
        
        if (!nombres.equals("") && !this.nombres.isEmpty()) {
            mat = Constantes.regexNomAp.matcher(nombres);
            if (!mat.find()) {
                return RESOURCES.getString("paciente.error.nombreInvalido");
            }
        } else {
            return RESOURCES.getString("paciente.error.nombre");
        }

        if (!appPaterno.equals("") && !this.appPaterno.isEmpty()) {
            mat = Constantes.regexNomAp.matcher(appPaterno);
            if (!mat.find()) {
                return RESOURCES.getString("paciente.app.invalido");
            }
        } else {
            return RESOURCES.getString("paciente.error.appvacio");
        }

        if (!this.appMaterno.equals("")) {
            mat = Constantes.regexNomAp.matcher(appMaterno);
            if (!mat.find()) {
                return RESOURCES.getString("paciente.apm.invalido");
            }
        }
        if (this.sexo == '\0' || this.sexo == '\u0000') {
            return RESOURCES.getString("paciente.error.sexoVacio");
        }
        if (!this.rfc.isEmpty()) {
            String regexp = "([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$";
            if (!Pattern.matches(regexp, this.rfc.toUpperCase())) {
                return RESOURCES.getString("paciente.error.formatoRfc");
            }
        }
        if (!this.curp.isEmpty()) {
            StringBuilder regexp = new StringBuilder();
            regexp.append("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}")
                    .append("(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])")
                    .append("[HM]{1}")
                    .append("(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)")
                    .append("[B-DF-HJ-NP-TV-Z]{3}")
                    .append("[0-9A-Z]{1}[0-9]{1}$");
            if (!Pattern.matches(regexp.toString(), this.curp.toUpperCase())) {
                return RESOURCES.getString("paciente.error.fromatoCurp");
            }
        }
        if (this.idOcupacion == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.ocupacion");
        }
        if (this.idGrupoEtnico == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.gpoEtnico");
        }
        if (this.idEstadoCivil == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.estadoCiil");
        }
        if (this.idEscolaridad == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.escolaridad");
        }                
        if (this.idReligion == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.religion");
        }
        if (this.idGpoSanguineo == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.gpoSang");
        }
        if (this.idNivSocEconom == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.nvelSocEcon");
        }
        if (this.idTipoVivienda == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.tipoVivienda");
        }
        return "";
    }

    /**
     * Metodo que valida que los datos del paciente sean correctos retorna true
     * si algun dato es incorrecto
     *
     * @return boolean
     */
    public String validarDatosPersonalesGenerico() {
        String res = "";
        if (this.pacienteNumeroGenerico.isEmpty()) {
            res = RESOURCES.getString("paciente.error.validarClave");
            
        } else if (this.claveDerechohabienciaGenerico.isEmpty()) {
            res = RESOURCES.getString("paciente.error.ns");
            
        } else if (this.idTipoPacienteGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.tipoPaciente");
            
        } else if (this.idUnidadMedicaGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.umedica");
            
        } else if (this.nombresGenerico.isEmpty()) {
            res = RESOURCES.getString("paciente.error.nombre");
            
        } else if (this.appPaternoGenerico.isEmpty()) {
            res = RESOURCES.getString("paciente.error.appVacio");
            
        } else if (this.idEstadoCivilGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.estadoCiil");
            
        } else if (this.idEscolaridadGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.escolaridad");
            
        } else if (this.idOcupacionGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.ocupacion");
            
        } else if (this.idGrupoEtnicoGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.gpoEtnico");
            
        } else if (this.idReligionGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.religion");
            
        } else if (this.idGpoSanguineoGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.gpoSang");
            
        } else if (this.idNivSocEconomGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.nvelSocEcon");
            
        } else if (this.idTipoViviendaGenerico == Constantes.ID_VACIO) {
            res = RESOURCES.getString("paciente.error.tipoVivienda");

        } else if (this.nombresGenerico.isEmpty()) {
            return RESOURCES.getString("paciente.error.nombre");
            
        } else if (this.appPaternoGenerico.isEmpty()) {
            return RESOURCES.getString("paciente.error.appVacio");
            
        } else {

            try {
                if (this.sexoGenerico == '\u0000') {
                    res = RESOURCES.getString("paciente.error.sexoVacio");
                }
            } catch (Exception ex) {
                LOGGER.error("Error al validar el sexo de paciente {} ", ex.getMessage());
                res = RESOURCES.getString("paciente.error.sexoVacio");
            }
            
            if (!this.rfcGenerico.isEmpty()) {
                String regexp = "([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$";
                if (!Pattern.matches(regexp, this.rfcGenerico.toUpperCase())) {
                    res = RESOURCES.getString("paciente.error.formatoRfc");
                }
            }
            
            if (!this.curpGenerico.isEmpty()) {
                StringBuilder regexp = new StringBuilder();
                regexp.append("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}")
                        .append("(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])")
                        .append("[HM]{1}")
                        .append("(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)")
                        .append("[B-DF-HJ-NP-TV-Z]{3}")
                        .append("[0-9A-Z]{1}[0-9]{1}$");
                if (!Pattern.matches(regexp.toString(), this.curpGenerico.toUpperCase())) {
                    res = RESOURCES.getString("paciente.error.fromatoCurp");
                }
            }
            
        }
        return res;
    }

    /**
     * Metodo que valida si los datos correspondientes a la direccion del
     * paciente son validos
     *
     * @return boolean
     */
    public String validarDatosDireccion() {
        if (this.calle.isEmpty()) {
            return RESOURCES.getString("paciente.error.calle");
        }

        if (this.noExt.isEmpty()) {
            return RESOURCES.getString("paciente.error.numExt");
        }

        if (this.telPrincipal.isEmpty()) {
            return RESOURCES.getString("paciente.error.telPrinc");
        }

        if (this.idPais == Constantes.ID_VACIO) {
            return RESOURCES.getString("paciente.error.pais");
        }

        if (this.idPais == Constantes.ID_PAIS_MEXICO) {
            if (this.idEstado == null || this.idEstado.equalsIgnoreCase(Constantes.TXT_VACIO)) {
                return RESOURCES.getString("paciente.error.estado");
            }
            if (this.idMunicipio == null || this.idMunicipio.equalsIgnoreCase(Constantes.TXT_VACIO)) {
                return RESOURCES.getString("paciente.error.municipio");
            }
            if (this.idColonia == null || this.idColonia.equalsIgnoreCase(Constantes.TXT_VACIO)) {
                return RESOURCES.getString("paciente.error.colonia");
            }
            if (this.codigoPostalSepomex == null
                    || this.codigoPostalSepomex.getdAsenta().equalsIgnoreCase(Constantes.TXT_VACIO)) {
                return RESOURCES.getString("paciente.error.cp");
            }
        }
        if (!this.correo.isEmpty()) {
            Pattern plantilla = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
            Matcher resultado = plantilla.matcher(this.correo);
            if (!resultado.find()) {
                return RESOURCES.getString("paciente.error.correo");
            }
        }

        return "";
    }

    /**
     * Metodo que valida si los datos correspondientes del responsable del
     * paciente son validos
     *
     * @return boolean
     */
    public String validarDatosResponsable() {

        if (!this.nombRespFamiliar.isEmpty() || !this.appRespFamiliar.isEmpty()
                || !this.telPrincRespFamiliar.isEmpty()
                || this.idParentesco != Constantes.ID_VACIO) {

            if (this.nombRespFamiliar.isEmpty()) {
                this.nombRespFamiliar = "";
            }
            if (this.appRespFamiliar.isEmpty()) {
                this.appRespFamiliar = "";
            }

            if (this.idParentesco == Constantes.ID_VACIO) {
                this.idParentesco = 60; //Hace referencia ninguno
            }

            if (!this.curpRespFamiliar.isEmpty()) {
                StringBuilder regexp = new StringBuilder();
                regexp.append("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}")
                        .append("(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])")
                        .append("[HM]{1}")
                        .append("(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)")
                        .append("[B-DF-HJ-NP-TV-Z]{3}")
                        .append("[0-9A-Z]{1}[0-9]{1}$");
                if (!Pattern.matches(regexp.toString(), this.curpRespFamiliar.toUpperCase())) {
                    return RESOURCES.getString("paciente.error.fromatoCurp");
                }
            }

            if (this.telPrincRespFamiliar.isEmpty()) {
                this.telPrincRespFamiliar = "";
            }

            if (!this.correoRespFamiliar.isEmpty()) {
                Pattern plantilla = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
                Matcher resultado = plantilla.matcher(this.correoRespFamiliar);
                if (!resultado.find()) {
                    return RESOURCES.getString("paciente.error.correo");
                }
            }

            if (!this.rfcRespFamiliar.isEmpty()) {
                String regexp = "([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1]))((-)?([A-Z\\d]{3}))?$";
                if (!Pattern.matches(regexp, this.rfcRespFamiliar.toUpperCase())) {
                    return RESOURCES.getString("paciente.error.formatoRfcResp");
                }
            }

            if (this.respLegal == '\0' || this.respLegal == '\u0000') {
                this.respLegal = '0';
            }
        } else {
            this.idParentesco = 60; //Toma el valor de Ninguno
        }
        return "";
    }

    /**
     * Metodo utilizado para obener los datos del detalle
     */
    public void mostrarDetallePacienteSelect() {
        try {
            limpiar();
            cargarDatosCombosModal();
//            cargarDatosPacienteEnModal(this.pacienteSelect);
            Paciente paciente = pacienteService.obtenerPacienteByIdPaciente(this.pacienteSelect.getIdPaciente());
            PacienteDomicilio pacienteDomicilio = pacienteDomicilioService
                    .obtenerPacienteDomicilioByIdPaciente(this.pacienteSelect.getIdPaciente());
            cargarDatosPacienteDomicilioEnModal(pacienteDomicilio);
            PacienteResponsable pacienteResponsable = pacienteResponsableService.
                    obtenerPacienteResponsableByIdPaciente(this.pacienteSelect.getIdPaciente());
            cargarDatosPacienteResponsableEnModal(pacienteResponsable);
            changeRender();
            if (this.idPais == Constantes.ID_PAIS_MEXICO) {
                this.listaEstados = sepomexService.obtenerEstados();
                this.listaMunicipios = sepomexService.obtenerMunicipios(this.idEstado);
                this.listaColonias = sepomexService.obtenerColonias(this.idEstado, this.idMunicipio);
            }
            this.renderBotonGuardar = false;
            this.renderBotonActualizar = false;
            this.desabilitado = true;
            this.polizaSeguroDisabled = true;
            this.unidadMedicaDisabled = true;
            this.tabCheckResponsable = true;
            this.tabCheckPersonales = true;
            this.tabCheckDireccion = true;
            this.tabIndex = 0;

        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarDetallePaciente :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para obener los datos del detalle al pulsar el boton en
     * el registro
     */
    public void mostrarDetallePacienteBotonRegistro() {
        try {
            limpiar();
            cargarDatosCombosModal();
            String idPatient
                    = obtenerIdPacienteRegistroTablaPacientes();
            Paciente paciente = pacienteService
                    .obtenerPacienteByIdPaciente(idPatient);
            cargarDatosPacienteEnModal(paciente);
            PacienteDomicilio pacienteDomicilio = pacienteDomicilioService
                    .obtenerPacienteDomicilioByIdPaciente(idPatient);
            cargarDatosPacienteDomicilioEnModal(pacienteDomicilio);
            PacienteResponsable pacienteResponsable = pacienteResponsableService.
                    obtenerPacienteResponsableByIdPaciente(idPatient);
            cargarDatosPacienteResponsableEnModal(pacienteResponsable);
            changeRender();
            if (this.idPais == Constantes.ID_PAIS_MEXICO) {
                this.listaEstados = sepomexService.obtenerEstados();
                this.listaMunicipios = sepomexService.obtenerMunicipios(this.idEstado);
                this.listaColonias = sepomexService.obtenerColonias(this.idEstado, this.idMunicipio);
            }
            this.renderBotonGuardar = false;
            this.renderBotonActualizar = false;
            this.desabilitado = true;
            this.polizaSeguroDisabled = true;
            this.unidadMedicaDisabled = true;
            this.tabCheckResponsable = true;
            this.tabCheckPersonales = true;
            this.tabCheckDireccion = true;
            this.tabIndex = 0;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarDetallePaciente :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que muestra el modal para insertar pacientes en la pantalla de
     * pacientes
     */
    public void mostrarModalInsertarPaciente() {
        try {
            limpiar();
            cargarDatosCombosModal();
            this.idPais = Constantes.ID_PAIS_MEXICO;
            this.show = true;
            this.renderBotonActualizar = false;
            this.renderBotonGuardar = true;
            this.desabilitado = false;
            this.pacienteNumero = obtenerClavePaciente();
            this.habilitarCampoPacienteNumero = true;
            this.tabIndex = 0;
            this.listaTurnos = turnoService.obtenerLista(new Turno());
            desabilitarCamposPorTipoPaciente();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalInsertarPaciente :: {}", e.getMessage());
        }
    }

    /**
     * Obtiene el consecuente del numeroPaciente, este sustituye el UUID
     * anterior.
     *
     * @return
     */
    public String obtenerClavePaciente() {
        String numeroPaciente = "";
        Folios folio;
        int tipoDoc = TipoDocumento_Enum.PACIENTE_MANUAL.getValue();
        try {
            folio = foliosService.obtenerPrefixPorDocument(tipoDoc);
            numeroPaciente = Comunes.generaFolio(folio);
        } catch (Exception e) {
            LOGGER.error("Error al obtener la clave del Paciente: {}", e.getMessage());
        }
        return numeroPaciente;
    }

    /**
     * Metodo que muestra el modal para insertar pacientes en la pantalla de
     * pacientes
     */
    public void mostrarModalInsertarPacienteGenerico() {
        try {
            limpiar();
            cargarDatosCombosModalEmergencia();
            this.nombresGenerico = Constantes.PACIENTE_EMERG_NOMBRE;
            this.listaTipoPacientes = this.catalogoGeneralService
                    .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_DE_PACIENTE.getValue());
            this.appMaternoGenerico = "";
            this.appPaternoGenerico = Constantes.PACIENTE_EMERG_APEPAT;
            this.claveDerechohabienciaGenerico = Constantes.NO_DEFINIDO;
            this.pacienteNumeroGenerico = obtenerClavePaciente();
            this.habilitarCampoPacienteNumero = true;
            this.fechaNacGenerico = new Date();
            this.sexoGenerico = 'M';
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalInsertarPacienteGenerico :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que muestra el modal para actualizar los datos de los pacientes
     */
    public void mostrarModalActualizarPaciente() {
        try {
            limpiar();
            habilitarCampoPacienteNumero = false;
            String idPatient
                    = obtenerIdPacienteRegistroTablaPacientes();
            cargarDatosCombosModal();
            Paciente paciente = pacienteService.obtenerPacienteByIdPaciente(idPatient);
            cargarDatosPacienteEnModal(paciente);
            PacienteDomicilio pacienteDomicilio = pacienteDomicilioService.
                    obtenerPacienteDomicilioByIdPaciente(idPatient);
            cargarDatosPacienteDomicilioEnModal(pacienteDomicilio);
            PacienteResponsable pacienteResponsable = pacienteResponsableService.
                    obtenerPacienteResponsableByIdPaciente(idPatient);

            cargarDatosPacienteResponsableEnModal(pacienteResponsable);
            List<Integer> listaTemp = new ArrayList<>();
            List<TurnoMedico> listaIdTurno = turnoMedicoService.obtenerLista(
                    new TurnoMedico(null, null, paciente.getIdPaciente()));
            for (TurnoMedico item : listaIdTurno) {
                listaTemp.add(item.getIdTurno());
            }
            this.listaIdTurnos = listaTemp;
            changeRender();
            if (this.idPais == Constantes.ID_PAIS_MEXICO) {
                this.listaEstados = sepomexService.obtenerEstados();
                this.listaMunicipios = sepomexService.obtenerMunicipios(this.idEstado);
                this.listaColonias = sepomexService.obtenerColonias(this.idEstado, this.idMunicipio);
            }
            this.renderBotonGuardar = false;
            this.renderBotonActualizar = true;
            this.desabilitado = false;
            this.tabCheckPersonales = true;
            this.tabCheckDireccion = true;
            this.tabCheckResponsable = true;
            this.tabIndex = 0;
            desabilitarCamposPorTipoPaciente();

        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalInsertarPaciente :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que carga los datos de un paciente en el modal de la pantalla de
     * pacientes
     *
     * @param paciente
     */
    public void cargarDatosPacienteEnModal(Paciente paciente) {
        this.idPaciente = paciente.getIdPaciente();
        this.claveDerechohabiencia = paciente.getClaveDerechohabiencia();
        this.pacienteNumero = paciente.getPacienteNumero();
        this.idTipoPaciente = paciente.getIdTipoPaciente();
        this.idUnidadMedica = paciente.getIdUnidadMedica();
        this.nombres = paciente.getNombreCompleto();
        this.appPaterno = paciente.getApellidoPaterno();
        this.appMaterno = paciente.getApellidoMaterno();
        this.sexo = paciente.getSexo();
        this.fechaNac = paciente.getFechaNacimiento();
        this.rfc = paciente.getRfc();
        this.curp = paciente.getCurp();
        this.idOcupacion = paciente.getIdOcupacion();
        this.idGrupoEtnico = paciente.getIdGrupoEtnico();
        this.idEstadoCivil = paciente.getIdEstadoCivil();
        this.idEscolaridad = paciente.getIdEscolaridad();
        this.idReligion = paciente.getIdReligion();
        this.idGpoSanguineo = paciente.getIdGrupoSanguineo();
        this.idNivSocEconom = paciente.getIdNivelSocioEconomico();
        this.idTipoVivienda = paciente.getIdTipoVivienda();
        
        this.lugarNacimiento = paciente.getLugarNacimiento();
        this.paisResidencia = paciente.getPaisResidencia();
        this.presentaDiscapacidad = paciente.getPresentaDiscapacidad();
        this.descripcionDiscapacidad = paciente.getDescripcionDiscapacidad();
    }

    /**
     * Metodo que carga los datos de pacienteDomicilio en el modal de la
     * pantalla de pacientes
     *
     * @param pacienteDomicilio PacienteDomicilio
     */
    public void cargarDatosPacienteDomicilioEnModal(PacienteDomicilio pacienteDomicilio) {
        try {
            this.idPacienteDomicilio = pacienteDomicilio.getIdPacienteDomicilio();
            this.calle = pacienteDomicilio.getCalle();
            this.noExt = pacienteDomicilio.getNumeroExterior();
            this.noInt = pacienteDomicilio.getNumeroInterior();
            this.telPrincipal = pacienteDomicilio.getTelefonoCasa();
            this.telSecundario = pacienteDomicilio.getTelefonoOficina();
            this.extencion = pacienteDomicilio.getExtencion();
            this.telCelular = pacienteDomicilio.getTelefonoCelular();
            this.correo = pacienteDomicilio.getCorreoElectronico();
            this.cuentaFacebook = pacienteDomicilio.getCuentaFacebook();
            this.idPais = pacienteDomicilio.getIdPais();
            this.idEstado = pacienteDomicilio.getIdEstado();
            this.idMunicipio = pacienteDomicilio.getIdMunicipio();
            this.idColonia = pacienteDomicilio.getIdColonia();
            Sepomex sepomex = new Sepomex();
            sepomex.setdAsenta(pacienteDomicilio.getCodigoPostal());
            this.codigoPostalSepomex = sepomex;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cargarDatosPacienteDomicilioEnModal :: {}", e.getMessage());
        }

    }

    /**
     * Metodo que carga los datos del paciente responsable en el modal de la
     * pantalla de pacientes
     *
     * @param pacienteResponsable PacienteResponsable
     */
    public void cargarDatosPacienteResponsableEnModal(PacienteResponsable pacienteResponsable) {
        if (pacienteResponsable != null) {
            this.idPacienteResponsable = pacienteResponsable.getIdPacienteResponsable();
            this.nombRespFamiliar = pacienteResponsable.getNombreCompleto();
            this.appRespFamiliar = pacienteResponsable.getApellidoPaterno();
            this.apmRespFamiliar = pacienteResponsable.getApellidoMaterno();
            this.idParentesco = pacienteResponsable.getIdParentesco();
            this.rfcRespFamiliar = pacienteResponsable.getRfc();
            this.curpRespFamiliar = pacienteResponsable.getCurp();
            this.telPrincRespFamiliar = pacienteResponsable.getTelefonoCasa();
            this.telSecRespFamiliar = pacienteResponsable.getTelefonoCelular();
            this.correoRespFamiliar = pacienteResponsable.getCorreoElectronico();
            this.respLegal = pacienteResponsable.getResponsableLegal();
        }
    }

    /**
     * Metodo que obtiene el id del paciente del registro seleccionado
     *
     * @return
     */
    public String obtenerIdPacienteRegistroTablaPacientes() {
        Map<String, String> params
                = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params.get("idPacienteParam");
    }

    /**
     * Metodo que desabilita los campos que dependen del tipo de paciente
     */
    public void desabilitarCamposPorTipoPaciente() {
//        if (this.idTipoPaciente == 1) {
//            this.polizaSeguroDisabled = false;
//            this.unidadMedicaDisabled = false;
//        } else {
//            this.polizaSeguroDisabled = true;
//            this.unidadMedicaDisabled = true;
//        }
    }

    /**
     * Metodo que se encarga de limpiar los campos
     */
    public void limpiar() {
        this.textoBusqueda = "";
        this.fechaNac = new Date();
        this.idTipoPaciente = 0;
        this.idUnidadMedica = 0;
        this.claveDerechohabiencia = "";
        this.nombres = "";
        this.appPaterno = "";
        this.appMaterno = "";
        this.sexo = ' ';
        this.pacienteParticular = ' ';
        this.rfc = "";
        this.curp = "";
        this.idEstadoCivil = 0;
        this.idEscolaridad = 0;
        this.idOcupacion = 0;
        this.idGrupoEtnico = 0;
        this.idReligion = 0;
        this.idGpoSanguineo = 0;
        this.idNivSocEconom = 0;
        this.idTipoVivienda = 0;
        this.calle = "";
        this.noExt = "";
        this.noInt = "";
        this.codigoPostal = "";
        this.idPais = 0;
        this.show = false;
        this.idEstado = "";
        this.idMunicipio = "";
        this.idColonia = "";
        this.telPrincipal = "";
        this.telSecundario = "";
        this.extencion = "";
        this.telCelular = "";
        this.correo = "";
        this.nombRespFamiliar = "";
        this.appRespFamiliar = "";
        this.apmRespFamiliar = "";
        this.parentesco = "";
        this.rfcRespFamiliar = "";
        this.curpRespFamiliar = "";
        this.telPrincRespFamiliar = "";
        this.telSecRespFamiliar = "";
        this.correoRespFamiliar = "";
        this.respLegal = ' ';
        this.idParentesco = 0;
        this.respLegal = ' ';
        this.pacienteNumero = "";
        this.cuentaFacebook = "";
        this.desabilitado = false;
        this.tabCheckPersonales = false;
        this.tabCheckDireccion = false;
        this.tabCheckResponsable = false;
        this.polizaSeguroDisabled = false;
        this.unidadMedicaDisabled = false;
        this.listaIdTurnos = new ArrayList<>();
        this.listaIdTurnosGenerico = new ArrayList<>();
        
        this.lugarNacimiento = "";
        this.paisResidencia = "";
        this.presentaDiscapacidad = 0;
        this.descripcionDiscapacidad = "";
    }

    /**
     * Metodo utilizado para buscar pacientes de acuerdo a un paramentro de
     * busqueda
     */
    public void buscarRegistros() {
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
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
    }
    
    public List<Sepomex> autoCompletarCodigoPostal(String valor) {
        try {
            this.listaCodigosPost = sepomexService.obtenerEstadoMunYColByCodPost(valor);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo autoCompletarCodigoPostal :: {}", e.getMessage());
        }
        return this.listaCodigosPost;
    }

    public void onItemSelect(SelectEvent event) {
        try {
            Sepomex sepomex = (Sepomex) event.getObject();
            this.idEstado = sepomex.getcEstado();
            this.idMunicipio = sepomex.getcMnpio();
            this.idColonia = sepomex.getIdAsentaCpcons();
            this.listaMunicipios = sepomexService.obtenerMunicipios(this.idEstado);
            this.listaColonias = sepomexService.obtenerColonias(this.idEstado, this.idMunicipio);
            this.codigoPostalSepomex.setdAsenta(sepomex.getdCodigo());
            this.codigoPostal = sepomex.getdCodigo();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo autoCompletarCodigoPostal :: {}", e.getMessage());
        }
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public List<Paciente_Extended> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Paciente_Extended> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public Paciente getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente_Extended pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public List<CatalogoGeneral> getListaTipoPacientes() {
        return listaTipoPacientes;
    }

    public void setListaTipoPacientes(List<CatalogoGeneral> listaTipoPacientes) {
        this.listaTipoPacientes = listaTipoPacientes;
    }

    public int getIdTipoPaciente() {
        return idTipoPaciente;
    }

    public void setIdTipoPaciente(int idTipoPaciente) {
        this.idTipoPaciente = idTipoPaciente;
    }

    public List<CatalogoGeneral> getListaUnidadesMedicas() {
        return listaUnidadesMedicas;
    }

    public void setListaUnidadesMedicas(List<CatalogoGeneral> listaUnidadesMedicas) {
        this.listaUnidadesMedicas = listaUnidadesMedicas;
    }

    public int getIdUnidadMedica() {
        return idUnidadMedica;
    }

    public void setIdUnidadMedica(int idUnidadMedica) {
        this.idUnidadMedica = idUnidadMedica;
    }

    public String getClaveDerechohabiencia() {
        return claveDerechohabiencia;
    }

    public void setClaveDerechohabiencia(String claveDerechohabiencia) {
        this.claveDerechohabiencia = claveDerechohabiencia;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getAppPaterno() {
        return appPaterno;
    }

    public void setAppPaterno(String appPaterno) {
        this.appPaterno = appPaterno;
    }

    public String getAppMaterno() {
        return appMaterno;
    }

    public void setAppMaterno(String appMaterno) {
        this.appMaterno = appMaterno;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public char getPacienteParticular() {
        return pacienteParticular;
    }

    public void setPacienteParticular(char pacienteParticular) {
        this.pacienteParticular = pacienteParticular;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public int getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(int idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public List<CatalogoGeneral> getListaEstadoCivil() {
        return listaEstadoCivil;
    }

    public void setListaEstadoCivil(List<CatalogoGeneral> listaEstadoCivil) {
        this.listaEstadoCivil = listaEstadoCivil;
    }

    public int getIdEscolaridad() {
        return idEscolaridad;
    }

    public void setIdEscolaridad(int idEscolaridad) {
        this.idEscolaridad = idEscolaridad;
    }

    public List<CatalogoGeneral> getListaEscolaridad() {
        return listaEscolaridad;
    }

    public void setListaEscolaridad(List<CatalogoGeneral> listaEscolaridad) {
        this.listaEscolaridad = listaEscolaridad;
    }

    public int getIdOcupacion() {
        return idOcupacion;
    }

    public void setIdOcupacion(int idOcupacion) {
        this.idOcupacion = idOcupacion;
    }

    public List<CatalogoGeneral> getListaOcupacion() {
        return listaOcupacion;
    }

    public void setListaOcupacion(List<CatalogoGeneral> listaOcupacion) {
        this.listaOcupacion = listaOcupacion;
    }

    public int getIdGrupoEtnico() {
        return idGrupoEtnico;
    }

    public void setIdGrupoEtnico(int idGrupoEtnico) {
        this.idGrupoEtnico = idGrupoEtnico;
    }

    public List<CatalogoGeneral> getListaGrupEtnico() {
        return listaGrupEtnico;
    }

    public void setListaGrupEtnico(List<CatalogoGeneral> listaGrupEtnico) {
        this.listaGrupEtnico = listaGrupEtnico;
    }

    public int getIdReligion() {
        return idReligion;
    }

    public void setIdReligion(int idReligion) {
        this.idReligion = idReligion;
    }

    public List<CatalogoGeneral> getListaReligion() {
        return listaReligion;
    }

    public void setListaReligion(List<CatalogoGeneral> listaReligion) {
        this.listaReligion = listaReligion;
    }

    public int getIdGpoSanguineo() {
        return idGpoSanguineo;
    }

    public void setIdGpoSanguineo(int idGpoSanguineo) {
        this.idGpoSanguineo = idGpoSanguineo;
    }

    public List<CatalogoGeneral> getListaGpoSanguineo() {
        return listaGpoSanguineo;
    }

    public void setListaGpoSanguineo(List<CatalogoGeneral> listaGpoSanguineo) {
        this.listaGpoSanguineo = listaGpoSanguineo;
    }

    public int getIdNivSocEconom() {
        return idNivSocEconom;
    }

    public void setIdNivSocEconom(int idNivSocEconom) {
        this.idNivSocEconom = idNivSocEconom;
    }

    public List<CatalogoGeneral> getListaNivSocEconom() {
        return listaNivSocEconom;
    }

    public void setListaNivSocEconom(List<CatalogoGeneral> listaNivSocEconom) {
        this.listaNivSocEconom = listaNivSocEconom;
    }

    public int getIdTipoVivienda() {
        return idTipoVivienda;
    }

    public void setIdTipoVivienda(int idTipoVivienda) {
        this.idTipoVivienda = idTipoVivienda;
    }

    public List<CatalogoGeneral> getListaTipoVivienda() {
        return listaTipoVivienda;
    }

    public void setListaTipoVivienda(List<CatalogoGeneral> listaTipoVivienda) {
        this.listaTipoVivienda = listaTipoVivienda;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoExt() {
        return noExt;
    }

    public void setNoExt(String noExt) {
        this.noExt = noExt;
    }

    public String getNoInt() {
        return noInt;
    }

    public void setNoInt(String noInt) {
        this.noInt = noInt;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public List<Pais> getListaPaises() {
        return listaPaises;
    }

    public void setListaPaises(List<Pais> listaPaises) {
        this.listaPaises = listaPaises;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String idEstado) {
        this.idEstado = idEstado;
    }

    public List<Sepomex> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<Sepomex> listaEstados) {
        this.listaEstados = listaEstados;
    }

    public String getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(String idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public List<Sepomex> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(List<Sepomex> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public String getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(String idColonia) {
        this.idColonia = idColonia;
    }

    public List<Sepomex> getListaColonias() {
        return listaColonias;
    }

    public void setListaColonias(List<Sepomex> listaColonias) {
        this.listaColonias = listaColonias;
    }

    public String getTelPrincipal() {
        return telPrincipal;
    }

    public void setTelPrincipal(String telPrincipal) {
        this.telPrincipal = telPrincipal;
    }

    public String getTelSecundario() {
        return telSecundario;
    }

    public void setTelSecundario(String telSecundario) {
        this.telSecundario = telSecundario;
    }

    public String getExtencion() {
        return extencion;
    }

    public void setExtencion(String extencion) {
        this.extencion = extencion;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombRespFamiliar() {
        return nombRespFamiliar;
    }

    public void setNombRespFamiliar(String nombRespFamiliar) {
        this.nombRespFamiliar = nombRespFamiliar;
    }

    public String getAppRespFamiliar() {
        return appRespFamiliar;
    }

    public void setAppRespFamiliar(String appRespFamiliar) {
        this.appRespFamiliar = appRespFamiliar;
    }

    public String getApmRespFamiliar() {
        return apmRespFamiliar;
    }

    public void setApmRespFamiliar(String apmRespFamiliar) {
        this.apmRespFamiliar = apmRespFamiliar;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getRfcRespFamiliar() {
        return rfcRespFamiliar;
    }

    public void setRfcRespFamiliar(String rfcRespFamiliar) {
        this.rfcRespFamiliar = rfcRespFamiliar;
    }

    public String getCurpRespFamiliar() {
        return curpRespFamiliar;
    }

    public void setCurpRespFamiliar(String curpRespFamiliar) {
        this.curpRespFamiliar = curpRespFamiliar;
    }

    public String getTelPrincRespFamiliar() {
        return telPrincRespFamiliar;
    }

    public void setTelPrincRespFamiliar(String telPrincRespFamiliar) {
        this.telPrincRespFamiliar = telPrincRespFamiliar;
    }

    public String getTelSecRespFamiliar() {
        return telSecRespFamiliar;
    }

    public void setTelSecRespFamiliar(String telSecRespFamiliar) {
        this.telSecRespFamiliar = telSecRespFamiliar;
    }

    public String getCorreoRespFamiliar() {
        return correoRespFamiliar;
    }

    public void setCorreoRespFamiliar(String correoRespFamiliar) {
        this.correoRespFamiliar = correoRespFamiliar;
    }

    public char getRespLegal() {
        return respLegal;
    }

    public void setRespLegal(char respLegal) {
        this.respLegal = respLegal;
    }

    public PaisService getPaisService() {
        return paisService;
    }

    public void setPaisService(PaisService paisService) {
        this.paisService = paisService;
    }

    public int getIdParentesco() {
        return idParentesco;
    }

    public void setIdParentesco(int idParentesco) {
        this.idParentesco = idParentesco;
    }

    public List<CatalogoGeneral> getListaParentesco() {
        return listaParentesco;
    }

    public void setListaParentesco(List<CatalogoGeneral> listaParentesco) {
        this.listaParentesco = listaParentesco;
    }

    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    public String getCuentaFacebook() {
        return cuentaFacebook;
    }

    public void setCuentaFacebook(String cuentaFacebook) {
        this.cuentaFacebook = cuentaFacebook;
    }

    public boolean isDesabilitado() {
        return desabilitado;
    }

    public void setDesabilitado(boolean desabilitado) {
        this.desabilitado = desabilitado;
    }

    public boolean isRenderBotonActualizar() {
        return renderBotonActualizar;
    }

    public void setRenderBotonActualizar(boolean renderBotonActualizar) {
        this.renderBotonActualizar = renderBotonActualizar;
    }

    public boolean isRenderBotonGuardar() {
        return renderBotonGuardar;
    }

    public void setRenderBotonGuardar(boolean renderBotonGuardar) {
        this.renderBotonGuardar = renderBotonGuardar;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdPacienteResponsable() {
        return idPacienteResponsable;
    }

    public void setIdPacienteResponsable(String idPacienteResponsable) {
        this.idPacienteResponsable = idPacienteResponsable;
    }

    public String getIdPacienteDomicilio() {
        return idPacienteDomicilio;
    }

    public void setIdPacienteDomicilio(String idPacienteDomicilio) {
        this.idPacienteDomicilio = idPacienteDomicilio;
    }

    public boolean isTabCheckPersonales() {
        return tabCheckPersonales;
    }

    public void setTabCheckPersonales(boolean tabCheckPersonales) {
        this.tabCheckPersonales = tabCheckPersonales;
    }

    public boolean isTabCheckDireccion() {
        return tabCheckDireccion;
    }

    public void setTabCheckDireccion(boolean tabCheckDireccion) {
        this.tabCheckDireccion = tabCheckDireccion;
    }

    public boolean isTabCheckResponsable() {
        return tabCheckResponsable;
    }

    public void setTabCheckResponsable(boolean tabCheckResponsable) {
        this.tabCheckResponsable = tabCheckResponsable;
    }

    public boolean isPolizaSeguroDisabled() {
        return polizaSeguroDisabled;
    }

    public void setPolizaSeguroDisabled(boolean polizaSeguroDisabled) {
        this.polizaSeguroDisabled = polizaSeguroDisabled;
    }

    public boolean isUnidadMedicaDisabled() {
        return unidadMedicaDisabled;
    }

    public void setUnidadMedicaDisabled(boolean unidadMedicaDisabled) {
        this.unidadMedicaDisabled = unidadMedicaDisabled;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public Usuario getUsuarioSelect() {
        return usuarioSelect;
    }

    public void setUsuarioSelect(Usuario usuarioSelect) {
        this.usuarioSelect = usuarioSelect;
    }

    public String getPacienteNumeroGenerico() {
        return pacienteNumeroGenerico;
    }

    public void setPacienteNumeroGenerico(String pacienteNumeroGenerico) {
        this.pacienteNumeroGenerico = pacienteNumeroGenerico;
    }

    public String getClaveDerechohabienciaGenerico() {
        return claveDerechohabienciaGenerico;
    }

    public void setClaveDerechohabienciaGenerico(String claveDerechohabienciaGenerico) {
        this.claveDerechohabienciaGenerico = claveDerechohabienciaGenerico;
    }

    public String getNombresGenerico() {
        return nombresGenerico;
    }

    public void setNombresGenerico(String nombresGenerico) {
        this.nombresGenerico = nombresGenerico;
    }

    public String getAppPaternoGenerico() {
        return appPaternoGenerico;
    }

    public void setAppPaternoGenerico(String appPaternoGenerico) {
        this.appPaternoGenerico = appPaternoGenerico;
    }

    public String getAppMaternoGenerico() {
        return appMaternoGenerico;
    }

    public void setAppMaternoGenerico(String appMaternoGenerico) {
        this.appMaternoGenerico = appMaternoGenerico;
    }

    public Date getFechaNacGenerico() {
        return fechaNacGenerico;
    }

    public void setFechaNacGenerico(Date fechaNacGenerico) {
        this.fechaNacGenerico = fechaNacGenerico;
    }

    public String getRfcGenerico() {
        return rfcGenerico;
    }

    public void setRfcGenerico(String rfcGenerico) {
        this.rfcGenerico = rfcGenerico;
    }

    public String getCurpGenerico() {
        return curpGenerico;
    }

    public void setCurpGenerico(String curpGenerico) {
        this.curpGenerico = curpGenerico;
    }

    public char getSexoGenerico() {
        return sexoGenerico;
    }

    public void setSexoGenerico(char sexoGenerico) {
        this.sexoGenerico = sexoGenerico;
    }

    public int getIdTipoPacienteGenerico() {
        return idTipoPacienteGenerico;
    }

    public void setIdTipoPacienteGenerico(int idTipoPacienteGenerico) {
        this.idTipoPacienteGenerico = idTipoPacienteGenerico;
    }

    public int getIdUnidadMedicaGenerico() {
        return idUnidadMedicaGenerico;
    }

    public void setIdUnidadMedicaGenerico(int idUnidadMedicaGenerico) {
        this.idUnidadMedicaGenerico = idUnidadMedicaGenerico;
    }

    public int getIdEstadoCivilGenerico() {
        return idEstadoCivilGenerico;
    }

    public void setIdEstadoCivilGenerico(int idEstadoCivilGenerico) {
        this.idEstadoCivilGenerico = idEstadoCivilGenerico;
    }

    public int getIdEscolaridadGenerico() {
        return idEscolaridadGenerico;
    }

    public void setIdEscolaridadGenerico(int idEscolaridadGenerico) {
        this.idEscolaridadGenerico = idEscolaridadGenerico;
    }

    public int getIdOcupacionGenerico() {
        return idOcupacionGenerico;
    }

    public void setIdOcupacionGenerico(int idOcupacionGenerico) {
        this.idOcupacionGenerico = idOcupacionGenerico;
    }

    public int getIdGrupoEtnicoGenerico() {
        return idGrupoEtnicoGenerico;
    }

    public void setIdGrupoEtnicoGenerico(int idGrupoEtnicoGenerico) {
        this.idGrupoEtnicoGenerico = idGrupoEtnicoGenerico;
    }

    public int getIdReligionGenerico() {
        return idReligionGenerico;
    }

    public void setIdReligionGenerico(int idReligionGenerico) {
        this.idReligionGenerico = idReligionGenerico;
    }

    public int getIdGpoSanguineoGenerico() {
        return idGpoSanguineoGenerico;
    }

    public void setIdGpoSanguineoGenerico(int idGpoSanguineoGenerico) {
        this.idGpoSanguineoGenerico = idGpoSanguineoGenerico;
    }

    public int getIdNivSocEconomGenerico() {
        return idNivSocEconomGenerico;
    }

    public void setIdNivSocEconomGenerico(int idNivSocEconomGenerico) {
        this.idNivSocEconomGenerico = idNivSocEconomGenerico;
    }

    public int getIdTipoViviendaGenerico() {
        return idTipoViviendaGenerico;
    }

    public void setIdTipoViviendaGenerico(int idTipoViviendaGenerico) {
        this.idTipoViviendaGenerico = idTipoViviendaGenerico;
    }

    public Sepomex getCodigoPostalSepomex() {
        return codigoPostalSepomex;
    }

    public void setCodigoPostalSepomex(Sepomex codigoPostalSepomex) {
        this.codigoPostalSepomex = codigoPostalSepomex;
    }

    public List<Sepomex> getListaCodigosPost() {
        return listaCodigosPost;
    }

    public void setListaCodigosPost(List<Sepomex> listaCodigosPost) {
        this.listaCodigosPost = listaCodigosPost;
    }

    public List<Integer> getListaIdTurnos() {
        return listaIdTurnos;
    }

    public void setListaIdTurnos(List<Integer> listaIdTurnos) {
        this.listaIdTurnos = listaIdTurnos;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public List<String> getListaIdTurnosGenerico() {
        return listaIdTurnosGenerico;
    }

    public void setListaIdTurnosGenerico(List<String> listaIdTurnosGenerico) {
        this.listaIdTurnosGenerico = listaIdTurnosGenerico;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Pattern getRegexNomAp() {
        return regexNomAp;
    }

    public void setRegexNomAp(Pattern regexNomAp) {
        this.regexNomAp = regexNomAp;
    }

    public PacientesLazy getPacientesLazy() {
        return pacientesLazy;
    }

    public void setPacientesLazy(PacientesLazy pacientesLazy) {
        this.pacientesLazy = pacientesLazy;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public boolean isHabilitarCampoPacienteNumero() {
        return habilitarCampoPacienteNumero;
    }

    public void setHabilitarCampoPacienteNumero(boolean habilitarCampoPacienteNumero) {
        this.habilitarCampoPacienteNumero = habilitarCampoPacienteNumero;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    public Integer getPresentaDiscapacidad() {
        return presentaDiscapacidad;
    }

    public void setPresentaDiscapacidad(Integer presentaDiscapacidad) {
        this.presentaDiscapacidad = presentaDiscapacidad;
    }

    public String getDescripcionDiscapacidad() {
        return descripcionDiscapacidad;
    }

    public void setDescripcionDiscapacidad(String descripcionDiscapacidad) {
        this.descripcionDiscapacidad = descripcionDiscapacidad;
    }
    
    

}
