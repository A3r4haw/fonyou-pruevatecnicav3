package mx.mc.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewMetadata;
import mx.mc.commons.SolucionUtils;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusDiagnostico_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.lazy.DispensacionSolucionLazy;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.Frecuencia_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoPrescripcion_Enum;
import mx.mc.enums.TipoSurtimiento_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.enums.ViaAdministracion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CamaExtended;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Estructura;
import mx.mc.model.Frecuencia;
import mx.mc.model.HorarioEntrega;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MotivoCancelaPrescripcion;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Protocolo;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoSolucion;
import mx.mc.model.TipoSurtimiento;
import mx.mc.model.TipoUsuario;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Turno;
import mx.mc.model.TurnoMedico;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
import mx.mc.service.CamaService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ConfigService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.EquipoService;
import mx.mc.service.EstabilidadService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FrecuenciaService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.HorarioEntregaService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.MotivoCancelaPrescripcionService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PlantillaCorreoService;
//import mx.mc.service.PrescripcionInsumoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReaccionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.TipoSurtimientoService;
import mx.mc.service.TipoUsuarioService;
import mx.mc.service.TurnoService;
import mx.mc.service.UnidadConcentracionService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.EnviaCorreoUtil;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

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
public class RegistroSolucionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistroSolucionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private Date fechaActual;

    private Date fechaMinPrescrMezclaValida;
    private Date fechaMinPrescrMezcla;

//    private Date fechaActualMas12;
    private Usuario usuarioSelected;
    private boolean msjMdlSurtimiento;
    private String msjWarning;
    private DispensacionSolucionLazy dispensacionSolucionLazy;
    private ParamBusquedaReporte paramBusquedaReporte;
    private String surSinAlmacen;
    private transient List<Estructura> listServiciosQueSurte;
    private Medicamento_Extended medicamento;
    private transient List<Medicamento_Extended> medicamentoList;
    private String paramError;

    private Surtimiento_Extend surtimientoExtendedSelected;
    private transient List<Surtimiento_Extend> surtimientoExtendedList;
    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedListEliminar;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedListAnterior;
    private transient List<TransaccionPermisos> permisosList;
    private String tipoPrescripcion;
    private transient List<String> tipoPrescripcionSelectedList;
    private Integer estatusSurtimiento;
    private String porValidar;
    private String validada;
    private String rechazada;
    private transient List<Integer> listEstatusSurtimiento;
    private String paramEstatus;
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String surInvalido;
    private PermisoUsuario permiso;
    private Usuario medico;
    private Usuario_Extended medicoExtended;
    private Paciente_Extended pacienteExtended;
    private Paciente paciente;
    private transient List<CamaExtended> listaCamas;
    private transient List<ViaAdministracion> viaAdministracionList;
    private ViaAdministracion viaAdministracion;
    private String idTipoSolucion;
    private transient List<TipoSolucion> tipoSolucionList;
    private Prescripcion prescripcionSelected;
    private transient List<PrescripcionInsumo> listaPrescripcionInsumo;
    private boolean existePrescripcion;
    private Diagnostico diagnosticoSelected;
    private transient List<Diagnostico> listaDiagnosticos;
    private String claveProtocolo;
    private transient List<Protocolo> listaProtocolos;
    private boolean isOncologica;
    private transient List<Turno> listaTurnos;
    private transient List<String> listaIdTurnos;
    private transient List<CatalogoGeneral> listaTipoPacientes;
    private transient List<TipoUsuario> tipoUserList;
    private transient List<EnvaseContenedor> envaseList;
    private Integer xcantidad;
    private BigDecimal dosis;
    private Integer frecuencia;
    private transient List<Frecuencia> listaFrecuencias;
    private Integer duracion;
    private Integer noDiasCaducidad;
    private Integer protocoloVacioDefault;
    private boolean autorizar;
    private String comentarioAutoriza;
    private String motivoNoAutoriza;
    private boolean disabledEditAddInsumo;
    private transient List<SolucionExtended> listaSolucionesExtend;
    private boolean botonGuardarMultiple;
    private transient List<Surtimiento> surtimientoExtListMultiple;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtListMultiple;
    private transient List<Prescripcion> listaPrescripcionesMultiple;
    private transient List<PrescripcionInsumo> listaPrescripcionInsumoMultiple;
    private Surtimiento_Extend surtimientoExtendedNew;
    private transient List<DiagnosticoPaciente> listaDiagnosticoPacienteMultiple;

    @Autowired
    private transient EstructuraService estructuraService;
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient MedicamentoService medicamentoService;
    @Autowired
    private transient UsuarioService usuarioService;
    @Autowired
    private transient CamaService camaService;
    @Autowired
    private transient ViaAdministracionService viaAdministracionService;
    @Autowired
    private transient PrescripcionService prescripcionService;
//    @Autowired
//    private transient PrescripcionInsumoService prescripcionInsumoService;
    @Autowired
    private transient VisitaService visitaService;
    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    @Autowired
    private transient ProtocoloService protocoloService;
    @Autowired
    private transient TipoSolucionService tipoSolucionService;
    @Autowired
    private transient FrecuenciaService frecuenciaService;
    @Autowired
    private transient TurnoService turnoService;
    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;
    @Autowired
    private transient SolucionService solucionService;
    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient TemplateEtiquetaService templateService;
    @Autowired
    private transient ImpresoraService impresoraService;
    @Autowired
    private transient MotivosRechazoService motivosRechazoService;
    @Autowired
    private transient ReportesService reportesService;
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    @Autowired
    private transient EnviaCorreoUtil enviaCorreoUtil;
    @Autowired
    private transient PlantillaCorreoService plantillaCorreoService;
    @Autowired
    private transient ConfigService configService;
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;
    @Autowired
    private transient EnvaseContenedorService envaseService;
    @Autowired
    private transient TipoSurtimientoService tipoSurtimientoService;
    @Autowired
    private transient MotivoCancelaPrescripcionService motivoCancelaPrescService;
    @Autowired
    private transient HipersensibilidadService hipersensibilidadService;
    @Autowired
    private transient EquipoService equipoService;
    @Autowired
    private transient ReaccionService reaccionService;
    @Autowired
    private transient UnidadConcentracionService unidadService;
    @Autowired
    private transient InventarioService inventarioService;

    private BigDecimal volumenTotal;
    private boolean validaPrescripcion;
    private SesionMB sesion;
    boolean esSolucion;
    private transient List<Integer> estatusSolucionLista;
    private SolucionUtils solUtils;
    private boolean editable;
    private boolean cancelable;
    private boolean rechazable;
    private Solucion solucion;
    private String idTipoSolucionSelected;
    private transient List<String> listaTipoSurtimiento;
    private transient List<MotivoCancelaPrescripcion> motivoCancelaPresLista;

    private Visita visita;
    private PacienteServicio ps;
    private PacienteUbicacion pu;
    private boolean listarDiagnosticos;

    private boolean ordenRechazada;
    private boolean editarHoraInfusion;

    /**
     * Historial: 10ENE2023 Autor : HHRC Desc : Se modifica la funcionalidad del
     * registro de Orden * Se reutiliza programaciónn gnérica para prescripción
     * del médico * Se agregan campos requeridos por los Químicos * Se agrega RN
     * cálculo de SUP CORP * Se agrega RN número de piezas requeridas * Se
     * agrega RN tratamieto duplicado * Se agrega RN validaciones de estabilidad
     * * Impresión de Prescripción * Cancelación de Prescripción por parte del
     * médico * Notificación vía correo electrónico de cancelación * Rechazo de
     * prescripción * Notificación vía correo electrónico de rechazo *
     * Modificación de impresión de Orden de Preparación * Se agrega RN se
     * consideran nuevos estatusSolucion Historial: 10AGO2023 Autor : HHRC Desc
     * : Se elimina CC de dispensación colectiva por horario definido en tablas
     * Se eliminan campos de velocidad y ritmo Se agrega numero de minutos
     * mínimo para entrega de mezcla Se permite configuración de prescripción
     * por máximo 3 días
     */
    private Integer numMinutosEntregaMezcla;
    private Integer numDiasMaxPresMezcla;
    private Integer numMinutosModificCancela;
    private Integer tipoInsumoValidaTratDuplicado;
    private Integer noHrsPrevValidaTratDuplicado;
    private Integer noHrsPostValidaTratDuplicado;
    private Integer noFarmacosPermitidos;
    private Integer noDiluyentesPermitidos;
    
    private String nptDefaultViaAdministracion;
    private String nptDefaultEnvaseContenedor;
    private transient List<Integer> idViaAdmontList;
    private transient List<Integer> idEnvaseContList;
    private boolean agruparXPrescripcionAutorizar;
    private boolean validaExistencias1erToma;
    private boolean validaExistencias1erDia;
    private boolean validaExistenciasTomasTotales;
    private boolean validaExistenciasRestrictiva;
    private boolean verAutorizacion;
    
    @Autowired
    private transient EstabilidadService estabilidadService;

    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.init()");
        this.autorizar = Constantes.INACTIVO;
        obtenerParametroAutorizar();
        inicializa();
        obtenerParametros();
        limpia();
        try {
            this.solUtils = new SolucionUtils(estructuraService, surtimientoService, templateService, impresoraService, motivosRechazoService, prescripcionService, surtimientoInsumoService, solucionService, reportesService, entidadHospitalariaService, diagnosticoService, envaseService, viaAdministracionService, tipoSolucionService, protocoloService, visitaService, camaService, frecuenciaService, catalogoGeneralService, turnoService, tipoUsuarioService, pacienteServicioService, pacienteUbicacionService, enviaCorreoUtil, plantillaCorreoService, configService, tipoJustificacionService, medicamentoService, usuarioService, pacienteService, hipersensibilidadService, reaccionService, equipoService, estabilidadService, inventarioService);
            obtenerCamas();
            obtenerProtocolos();
            obtenerFrecuencias();
            obtenerTiposSolucion();
            
            obtenerValoresNptPredeterminados();
            obtenerViasAdministracion();
            obtenerEnvases();
            obtenerTurnos();
            obtenerTipoUsuarios();
            obtenerTipoPacientes();
            obtenerMedico();

            if (this.validaPrescripcion) {
                this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.REGISTROSOLUCION.getSufijo());
                obtieneServiciosQuePuedeSurtir();
            } else {
                if(this.autorizar) {
                    this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.AUTORIZACIONSOLUCION.getSufijo());
                }else {
                    this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.PRESCRIBESOLUCION.getSufijo());
                }
                obtieneAreasPorTipo();
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        obtenerSurtimientos();
        obtieneMotivoCancelacion();
        ordenRechazada = false;
        editarHoraInfusion = false;
    }

    private void inicializa() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.inicializa()");
        this.tipoPrescripcionSelectedList = new ArrayList<>();
        this.usuarioSelected = new Usuario();
        this.permisosList = new ArrayList<>();
        this.usuarioSelected.setPermisosList(this.permisosList);
        this.surtimientoExtendedSelected = new Surtimiento_Extend();

        this.surSinAlmacen = "sur.sin.almacen";
        this.paramEstatus = "estatus";
        this.errRegistroIncorrecto = "err.registro.incorrecto";
        this.surIncorrecto = "sur.incorrecto";
        this.surInvalido = "sur.invalido";
        this.paramError = "error";

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        this.paramBusquedaReporte = new ParamBusquedaReporte();
        this.usuarioSelected = this.sesion.getUsuarioSelected();
        this.noDiasCaducidad = this.sesion.getNoDiasCaducidad();
        this.protocoloVacioDefault = this.sesion.getProtocoloVacioDefault();
        this.numMinutosModificCancela = (this.sesion.getNumMinEditaCancela() <= 0) ? 30 : this.sesion.getNumMinEditaCancela();

        this.tipoInsumoValidaTratDuplicado = this.sesion.getTipoInsumoValidaTratDuplicado();
        this.noHrsPrevValidaTratDuplicado = this.sesion.getNoHrsPrevValidaTratDuplicado();
        this.noHrsPostValidaTratDuplicado = this.sesion.getNoHrsPrevValidaTratDuplicado();
        this.noFarmacosPermitidos = this.sesion.getNoFarmacosPermitidos();
        this.noDiluyentesPermitidos = this.sesion.getNoDiluyentesPermitidos();

        this.existePrescripcion = false;
        this.listaDiagnosticos = null;
        this.claveProtocolo = null;
        this.dosis = BigDecimal.ZERO;
        this.frecuencia = null;
        this.duracion = null;
        this.diagnosticoSelected = null;
        this.isOncologica = true;
        this.paciente = new Paciente();
        this.medicoExtended = new Usuario_Extended();
        this.volumenTotal = BigDecimal.ZERO;
        this.viaAdministracion = new ViaAdministracion();
        this.listaSolucionesExtend = new ArrayList<>();
        this.surtimientoExtListMultiple = new ArrayList<>();
        this.surtimientoInsumoExtListMultiple = new ArrayList<>();
        this.listaPrescripcionInsumoMultiple = new ArrayList<>();
        this.listaPrescripcionesMultiple = new ArrayList<>();
        this.botonGuardarMultiple = false;
        this.listaDiagnosticoPacienteMultiple = new ArrayList<>();
        this.listEstatusSurtimiento = null;
        this.esSolucion = true;
        this.estatusSolucionLista = new ArrayList<>();
        if (this.validaPrescripcion) {
            this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
            this.estatusSolucionLista.add(EstatusSolucion_Enum.ORDEN_CREADA.getValue());
            this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_RECHAZADA.getValue());

            this.porValidar = EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.name();
            this.validada = EstatusSolucion_Enum.OP_VALIDADA.name();
            this.rechazada = EstatusSolucion_Enum.OP_RECHAZADA.name();
        } else {
             //Esta validación es para la funcionalidad de autorización
            if(this.autorizar) {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.POR_AUTORIZAR.getValue());
            } else {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue());

                this.porValidar = EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.name();
                this.validada = EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.name();
                this.rechazada = EstatusSolucion_Enum.CANCELADA.name();
            }
        }
        this.solucion = new Solucion();
        this.horarioEntregaLista = new ArrayList<>();

        this.numMinutosEntregaMezcla = this.sesion.getNumMinutosEntregaMezcla();
        this.numDiasMaxPresMezcla = this.sesion.getNumDiasMaxPrescMezcla();
        calculaFechaMinimaSolicitud();
        this.listarDiagnosticos = this.sesion.isListaDiagnostico();
        this.agruparXPrescripcionAutorizar = this.sesion.isAgruparXPrescripcionAutorizar();
        this.validaExistencias1erToma = this.sesion.isValidaExistencias1erToma();
        this.validaExistencias1erDia = this.sesion.isValidaExistencias1erDia();
        this.validaExistenciasTomasTotales = this.sesion.isValidaExistenciasTomasTotales();
        this.validaExistenciasRestrictiva = this.sesion.isValidaExistenciasRestrictiva();
        this.verAutorizacion = false;
    }

    private void obtenerParametroAutorizar() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerParametroAutorizar()");
        Collection<UIViewParameter> viewParams = ViewMetadata.getViewParameters(FacesContext.getCurrentInstance().getViewRoot());
        for (UIViewParameter viewParam : viewParams) {
            
            if (viewParam.getName().equals("tipoProc")) {
                if (viewParam.getValue().equals("1")) { // valpidar presc
                    this.validaPrescripcion = Constantes.ACTIVO;
            
                } else if (viewParam.getValue().equals("2")) {  // autorizar pres
                    this.autorizar = Constantes.ACTIVO;
                }
            }
            LOGGER.trace(viewParam.getName() + " = " + viewParam.getValue());
        }
    }
    
    private void obtenerParametros() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerParametros()");
        Collection<UIViewParameter> viewParams = ViewMetadata.getViewParameters(FacesContext.getCurrentInstance().getViewRoot());
        for (UIViewParameter viewParam : viewParams) {
            if (viewParam.getName().equals("tipoProc")) {
                this.validaPrescripcion = (viewParam.getValue().equals("1"));
            }
            LOGGER.info(viewParam.getName() + " = " + viewParam.getValue());
        }
    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.limpia()");
        elementoSeleccionado = false;
        cadenaBusqueda = null;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        fechaActual = cal.getTime();
//        cal.add(Calendar.HOUR_OF_DAY, 12);
//        fechaActualMas12 = cal.getTime();
        xcantidad = 1;
        tipoPrescripcion = "";
        msjMdlSurtimiento = true;
        msjWarning = "";
    }

    public void obtenerCamas() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerCamas()");
        this.listaCamas = new ArrayList<>();
        String idServicio = null;
        this.listaCamas.addAll(this.solUtils.obtenerListaCamas(idServicio));
    }

    public void obtenerProtocolos() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerProtocolos()");
        this.listaProtocolos = new ArrayList<>();
        this.listaProtocolos.addAll(this.solUtils.obtenerProtocolos());
    }

    public void obtenerFrecuencias() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerFrecuencias()");
        this.listaFrecuencias = new ArrayList<>();
        listaFrecuencias.addAll(this.solUtils.obtenerListaFrecuencias());
    }

    public void obtenerTiposSolucion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerTiposSolucion()");
        List<String> listaClaves = new ArrayList<>();
        listaClaves.add("ANT");
        listaClaves.add("ONC");
// TODO: agregar el método requerido
        this.tipoSolucionList = new ArrayList<>();
        if(Objects.equals(usuarioSelected.getIdTipoUsuario(), TipoUsuario_Enum.MEDICO.getValue())) {            
            String listaSolucionesUser[] = {};
            if(usuarioSelected.getIdTipoSolucion() != null && !usuarioSelected.getIdTipoSolucion().equals("") ) {
                listaSolucionesUser = usuarioSelected.getIdTipoSolucion().split(",");
            }            
            List<TipoSolucion> listaTiposSolucion = this.solUtils.obtenerListaTiposSolucion(listaClaves);
            for(TipoSolucion tipoSol : listaTiposSolucion) {
                for(String idTipoSol : listaSolucionesUser) {
                    if(tipoSol.getIdTipoSolucion().equals(idTipoSol.trim())) {
                        this.tipoSolucionList.add(tipoSol);
                    }
                }
            }
        } else {
        this.tipoSolucionList.addAll(this.solUtils.obtenerListaTiposSolucion(listaClaves));
    }
    }

    
    private void obtenerValoresNptPredeterminados(){
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerValoresNptPredeterminados()");
        try{
            nptDefaultViaAdministracion = sesion.getNptDefaultViaAdministracion();
            nptDefaultEnvaseContenedor = sesion.getNptDefaultEnvaseContenedor();
            
            if (nptDefaultEnvaseContenedor != null && !nptDefaultEnvaseContenedor.isEmpty()){
                String[] idList = nptDefaultEnvaseContenedor.split(Constantes.PARAM_SYSTEM_CARACTER_SEPARADOR);
                if (idList.length>0){
                    idEnvaseContList = new ArrayList<>();
                    for (String id: idList ){
                        try {
                            Integer valId = Integer.valueOf(id);
                            idEnvaseContList.add(valId);
                        } catch (NumberFormatException ex) {
                            LOGGER.trace("Error al obtener envase contenedor predeterminado :: {} ", ex.getMessage());
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener envase contenedor predeterminado.", null);
                        }
                    }
                }
            }
            
            if (nptDefaultViaAdministracion != null && !nptDefaultViaAdministracion.isEmpty()){
                String[] idList = nptDefaultViaAdministracion.split(Constantes.PARAM_SYSTEM_CARACTER_SEPARADOR);
                if (idList.length>0){
                    idViaAdmontList = new ArrayList<>();
                    for (String id: idList ){
                        try {
                            Integer valId = Integer.valueOf(id);
                            idViaAdmontList.add(valId);
                        } catch (NumberFormatException ex) {
                            LOGGER.trace("Error al obtener la Via de Administración predeterminada :: {} ", ex.getMessage());
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener la Via de Administración predeterminad1.", null);
                        }
                    }
                }
            }
            
        } catch (Exception e){
            LOGGER.error("Error al obtener valores NPT predeterminados :: {} ", e.getMessage());
        }
    }
    
    
    public void obtenerViasAdministracion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerViasAdministracion()");
        this.viaAdministracionList = new ArrayList<>();
        
        if (idViaAdmontList != null && !idViaAdmontList.isEmpty()) {
            List<ViaAdministracion> listViaAdmon = this.solUtils.obtenerListaViaAdministracion();
            for (ViaAdministracion item : listViaAdmon){
                for (Integer item2 : idViaAdmontList){
                    if (!Objects.equals(item.getIdViaAdministracion(), item2)
                            && !this.viaAdministracionList.contains(item)){
                        this.viaAdministracionList.add(item);
                    }
                }
            }
        } else  {
            this.viaAdministracionList.addAll(this.solUtils.obtenerListaViaAdministracion());
        }
    }

    public void obtenerEnvases() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerEnvases()");
        this.envaseList = new ArrayList<>();
        
        this.envaseList = new ArrayList<>();
        if (idEnvaseContList!= null && !idEnvaseContList.isEmpty()) {
            List<EnvaseContenedor> listEnvCon = this.solUtils.obtenerListaEnvases();
            for (EnvaseContenedor item : listEnvCon){
                for (Integer item2 : idEnvaseContList){
                    if (!Objects.equals(item.getIdEnvaseContenedor(), item2)){
                        this.envaseList.add(item);
                    }
                }
            }
        } else {
            this.envaseList.addAll(this.solUtils.obtenerListaEnvases());
        }
    }

    public void obtenerTurnos() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerTurnos()");
        this.listaTurnos = new ArrayList<>();
        this.listaTurnos.addAll(this.solUtils.obtenerListaTurnos());
    }

    public void obtenerTipoUsuarios() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerTipoUsuarios()");
        this.tipoUserList = new ArrayList<>();
        this.tipoUserList.addAll(this.solUtils.obtenerListaTipoUsuario());
    }

    public void obtenerTipoPacientes() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerTipoPacientes()");
        this.listaTipoPacientes = new ArrayList<>();
        this.listaTipoPacientes.addAll(this.solUtils.obtenerListaTipoPacientes());
    }

    public void obtenerMedico() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerTipoPacientes()");
        this.medico = this.solUtils.obtenerMedico(this.validaPrescripcion, this.usuarioSelected);
    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario queF
     * puede surtir
     */
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtieneServiciosQuePuedeSurtir()");
        listServiciosQueSurte = new ArrayList<>();
        permiso.setPuedeVer(false);
        Estructura estSol = null;
        try {
            estSol = estructuraService.obtener(new Estructura(usuarioSelected.getIdEstructura()));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        if (estSol == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (estSol.getIdTipoAreaEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (!Objects.equals(estSol.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

        } else {
            permiso.setPuedeVer(true);
            try {
                if (usuarioSelected.getIdEstructura() != null) {
//                    this.listServiciosQueSurte.addAll(estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura()));
                    this.listServiciosQueSurte = new ArrayList<>();
                    this.listServiciosQueSurte.addAll(estructuraService.obtenerPorTipoAreayTipoAreaEstructura(null, TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue()));
                    this.listServiciosQueSurte.addAll(estructuraService.obtenerPorTipoAreayTipoAreaEstructura(null, TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue()));
//TODO: revisar el listado de las estructuras
                }
            } catch (Exception excp) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", excp.getMessage());
            }
        }
    }

    /**
     * Obtiene los almacenes (Centro de Mezclas) que pueden surtir soluciones
     */
    private void obtieneAreasPorTipo() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtieneAreasPorTipo()");
        this.listServiciosQueSurte = new ArrayList<>();
        this.listServiciosQueSurte.addAll(this.solUtils.obtieneAreasPorTipo(this.usuarioSelected));
    }

    /**
     * Método que busca los insumos por autocomplete y por tipo de mezcla
     *
     * @param cadena
     * @return
     */
// TODO : 10AGO2023 HHRC - LIMITAR INSUMOS POR TIPO DE MEZCLA
    public List<Medicamento_Extended> autoCompleteMedicamentos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.autoCompleteMedicamentos()");
        List<Medicamento_Extended> listaMedicamentos = new ArrayList<>();
        boolean status = false;
        if (surtimientoExtendedSelected == null){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución Inválida.", null);
            
        } else if (surtimientoExtendedSelected.getIdEstructura() == null){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un servicio/área.", null);
            
        } else if (idTipoSolucion == null || idTipoSolucion.trim().isEmpty()){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione tipo de Mezcla.", null);
            
        } else if (viaAdministracion == null){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Vía de administración.", null);
            
        } else if (viaAdministracion.getIdViaAdministracion() == null){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Vía de administración.", null);
            
        } else if (surtimientoExtendedSelected.getVolumenTotal() == null){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture el volumen total de la mezcla.", null);
            
        } else if (surtimientoExtendedSelected.getIdContenedor() == null){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Contenedor.", null);
        
        } else if (surtimientoExtendedSelected.getHorasInfusion() == null 
                || surtimientoExtendedSelected.getHorasInfusion().trim().isEmpty()){
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture tiempo de infusion.", null);
            
        } else {
            try {
                List<Medicamento_Extended> lista = new ArrayList<>();
                lista.addAll(this.solUtils.autoCompleteMedicamentosPorTipoMezcla(
                        cadena,
                        idTipoSolucion,
                        this.surtimientoExtendedSelected.getIdEstructura()) );
                
                for (Medicamento_Extended item : lista) {
                    Double volReq = surtimientoExtendedSelected.getVolumenTotal().doubleValue();
                    Double volContenedor = item.getCantPorEnvase();
                    if (!item.isDiluyente() || 
                            (item.isDiluyente() && (volReq <= volContenedor) ) ) {
                        listaMedicamentos.add(item);
                    }
                }
                status = true;
            } catch (Exception e) {
                LOGGER.error("Error al buscar insumos :: {} ", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, e.getMessage(), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
        return listaMedicamentos;
    }

    public void onTabChange(TabChangeEvent evt) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.onTabChange()");
// RN: Listar prescripciones solo con estatus de surtimiento surtido
        this.estatusSolucionLista = new ArrayList<>();
        String valorStatus = evt.getTab().getId();

        if (validaPrescripcion) {
            if (valorStatus.equalsIgnoreCase(this.porValidar)) {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                this.estatusSolucionLista.add(EstatusSolucion_Enum.ORDEN_CREADA.getValue());
                this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_RECHAZADA.getValue());

            } else if (valorStatus.equalsIgnoreCase(this.validada)) {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue());

            } else if (valorStatus.equalsIgnoreCase(this.rechazada)) {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue());

            } else {
                estatusSurtimiento = 0;
            }
        } else {
            if (valorStatus.equalsIgnoreCase(this.porValidar)) {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue());
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue());

            } else if (valorStatus.equalsIgnoreCase(this.validada)) {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue());

            } else if (valorStatus.equalsIgnoreCase(this.rechazada)) {
                this.estatusSolucionLista.add(EstatusSolucion_Enum.CANCELADA.getValue());

            } else {
                estatusSurtimiento = 0;
            }
        }
        obtenerSurtimientos();
    }

    /**
     * Obtiene la lista de pacientes registrados
     */
    public void obtenerSurtimientos() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerSurtimientos()");
        boolean status = Constantes.INACTIVO;
        this.surtimientoExtendedList = new ArrayList<>();
        this.surtimientoExtendedSelected = new Surtimiento_Extend();

        if (!this.permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
            status = Constantes.ACTIVO;

        } else if (this.usuarioSelected == null
                || this.usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);
            status = Constantes.ACTIVO;

        } else if (this.listServiciosQueSurte.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

        } else {
            try {
                if (this.cadenaBusqueda != null && this.cadenaBusqueda.trim().isEmpty()) {
                    this.cadenaBusqueda = null;
                }

// RN: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                List<Integer> listEstatusPacienteSol = new ArrayList<>();
                listEstatusPacienteSol.add(EstatusPaciente_Enum.REGISTRADO.getValue());
                listEstatusPacienteSol.add(EstatusPaciente_Enum.EN_VISITA.getValue());
                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
// TODO: HHRC 13ENE2023 SE DEBE AGREGAR UN PARÁMETRO DE CONFIGURACIÓN PARA VALIDAR EL ESTATUS DE PACIENETE
//                      Se elimina la restricción del estatus paciente, debido a reglas de HJM no esta definido el proceso de admision hospitalaria
                listEstatusPacienteSol = null;

// RN: Listar prescripciones solo con estatus de PROGRAMADA
                List<Integer> listEstatusPrescripcion = new ArrayList<>();
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.CANCELADA.getValue());

// RN: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                if (this.tipoPrescripcionSelectedList != null
                        && this.tipoPrescripcionSelectedList.isEmpty()) {
                    this.tipoPrescripcionSelectedList = null;
                }

                Date fechaProgramada = null;
                if (validaPrescripcion) {
// RN: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
// El médico puede ver todas las recetas sin restricción de fecha, pero el validador y en adelate
// Sólo visualizan las ordenes de mezcla del dia actal hacia atrás
                    fechaProgramada = new java.util.Date();
                }

                // TODO:    agregar reglas de Negocio
                // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                // 2.- Solo surten los insumos que tienen activos
                // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                // 4.- 
                this.paramBusquedaReporte.setNuevaBusqueda(true);
                this.paramBusquedaReporte.setCadenaBusqueda(this.cadenaBusqueda);
                int tipoProceso = Constantes.TIPO_PROCESO_MEDICO;
                this.dispensacionSolucionLazy = new DispensacionSolucionLazy(
                        this.surtimientoService,
                        this.paramBusquedaReporte,
                        fechaProgramada,
                        this.tipoPrescripcionSelectedList,
                        listEstatusPacienteSol,
                        listEstatusPrescripcion,
                        this.listEstatusSurtimiento,
                        this.listServiciosQueSurte,
                        this.esSolucion,
                        this.estatusSolucionLista,
                        null, tipoProceso, agruparXPrescripcionAutorizar
                );
                LOGGER.debug("Resultados: {}", dispensacionSolucionLazy.getTotalReg());
                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void obtenerDatosSurtimiento(String idSurtimiento) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerDatosSurtimiento()");
        existePrescripcion = false;
        listaDiagnosticos = new ArrayList<>();
        try {
            if (idSurtimiento == null || idSurtimiento.trim().isEmpty()) {
                surtimientoExtendedSelected = new Surtimiento_Extend();
            } else {
                surtimientoExtendedSelected = this.solUtils.obtenerSurtimiento(idSurtimiento);
            }
            
            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla no encontrado.", null);
            
            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla no encontrado.", null);

            } else if (surtimientoExtendedSelected.getIdPrescripcion()== null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción de Mezcla no encontrada.", null);

            } else {
                surtimientoExtendedSelected.setEstatusSurtimiento(EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento()).name());
                
                prescripcionSelected = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected.getIdPrescripcion());
                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción iválida. ", null);
                    
                } else if (prescripcionSelected.getIdPacienteServicio() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Asignación de paciente a servicio no encontrado. ", null);

// RN para validar una prescripción debe estar programada
                } else if (!prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.PROGRAMADA.getValue())
                        && !prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.CANCELADA.getValue())
                        && !prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.FINALIZADA.getValue())
                        ) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus de la prescripción no válido", null);
                    
                } else {
                    existePrescripcion = true;
                    surtimientoExtendedSelected.setFolioPrescripcion(prescripcionSelected.getFolio());
                    surtimientoExtendedSelected.setTipoPrescripcion(prescripcionSelected.getTipoPrescripcion());
                    surtimientoExtendedSelected.setTipoConsulta(prescripcionSelected.getTipoConsulta());
                    surtimientoExtendedSelected.setIdEstructura(prescripcionSelected.getIdEstructura());

                    pu = this.solUtils.obtenerUbicacionRegistrada(prescripcionSelected.getIdPacienteUbicacion());
                    if (pu != null) {
                        surtimientoExtendedSelected.setIdCama(pu.getIdCama());
                        surtimientoExtendedSelected.setCama(pu.getIdCama());
                    }
                
                    ps = this.solUtils.obtenerServicioRegistrada(prescripcionSelected.getIdPacienteServicio());
                    if (ps != null) {
                        visita = this.solUtils.obtenerVisitaRegistrada(ps.getIdVisita());
                    }

                    if (visita == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Visita de paciente no encontrada. ", null);

                    } else if (visita.getIdPaciente() == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Visita de paciente no encontrado. ", null);

                    } else {
                    
                        pacienteExtended = pacienteService.obtenerPacienteCompletoPorId(visita.getIdPaciente());
                        if (pacienteExtended == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente en visita no encontrado. ", null);

                        } else if (pacienteExtended.getIdPaciente() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente en visita no encontrado. ", null);

                        } else {
                        
                            listaDiagnosticos.addAll(
                                    diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(
                                            pacienteExtended.getIdPaciente(),
                                            visita.getIdVisita(),
                                            prescripcionSelected.getIdPrescripcion()));

                            surtimientoExtendedSelected.setPacienteNumero(pacienteExtended.getPacienteNumero());
                            surtimientoExtendedSelected.setIdPaciente(pacienteExtended.getIdPaciente());
                            surtimientoExtendedSelected.setNombrePaciente(
                                    pacienteExtended.getNombreCompleto() + " "
                                    + pacienteExtended.getApellidoPaterno() + " "
                                    + pacienteExtended.getApellidoMaterno());

                            surtimientoExtendedSelected = this.solUtils.llenarSurtimientoConSolucion(surtimientoExtendedSelected);
                            if(surtimientoExtendedSelected.getIdUsuarioAutoriza() != null && 
                                    surtimientoExtendedSelected.getIdEstatusSolucion().equals(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())) {
                                this.verAutorizacion = true;
                            }
                            if (surtimientoExtendedSelected.getIdUsuarioRechaza() != null){
                                Usuario u = usuarioService.obtenerUsuarioByIdUsuario(surtimientoExtendedSelected.getIdUsuarioRechaza());
                                if (u!= null){
                                    surtimientoExtendedSelected.setUsuarioRechazo(u.getNombre() +" " + u.getApellidoPaterno() + " " + u.getApellidoMaterno());
                                }
                            }
                            viaAdministracion = this.solUtils.obtenerViaAdministracion(prescripcionSelected.getIdViaAdministracion());
                            if (viaAdministracion != null) {
                                surtimientoExtendedSelected.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());
                            }
                            
                            surtimientoInsumoExtendedListAnterior = new ArrayList<>();
                            
                            surtimientoInsumoExtendedList = surtimientoInsumoService.obtenerListaByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                            if (surtimientoInsumoExtendedList != null) {
                                surtimientoInsumoExtendedListAnterior.addAll(surtimientoInsumoExtendedList);
                            }
                            medico = this.solUtils.obtenerUsuarioPorId(prescripcionSelected.getIdMedico());
                            if (medico != null) {
                                surtimientoExtendedSelected.setNombreMedico(
                                        medico.getNombre() + " "
                                        + medico.getApellidoPaterno() + " "
                                        + medico.getApellidoMaterno());
                            }
                            idTipoSolucion = prescripcionSelected.getIdTipoSolucion();
                            isOncologica = obtenerClaveTipoSolucion(idTipoSolucion).equalsIgnoreCase("ONC");
                            if (isOncologica) {
                                
                                claveProtocolo = this.solUtils.obtenerClaveProtocolo(this.listaProtocolos, prescripcionSelected.getIdProtocolo());
                                if (prescripcionSelected.getIdDiagnostico() != null) {
                                    diagnosticoSelected = diagnosticoService.obtenerDiagnosticoPorIdDiag(prescripcionSelected.getIdDiagnostico());
                                }
                            } else {
                                claveProtocolo = null;
                                diagnosticoSelected = null;
                            }
                        }
                    }
                    if (!existePrescripcion) {
                        String folPresc = surtimientoExtendedSelected.getFolioPrescripcion();
                        setNuevaOrden();
                        surtimientoExtendedSelected.setFolioPrescripcion(folPresc);
                        isOncologica = false;
                        claveProtocolo = null;
                        diagnosticoSelected = null;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.debug("Error en el metodo obtenerDatosSurtimiento :: {}", ex.getMessage());
        }
    }

    private void evaluaEdicion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.evaluaEdicion()");
        this.editable = false;
         this.disabledEditAddInsumo = false;
        this.cancelable = false;
        this.rechazable = false;
        if (surtimientoExtendedSelected != null) {
            if (surtimientoExtendedSelected.getIdEstatusSolucion() != null) {

                if (permiso.isPuedeAutorizar()) {
                    
                    if(Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.POR_AUTORIZAR.getValue())) {
                        this.disabledEditAddInsumo = true;
                        dosis = BigDecimal.ZERO;
                        frecuencia = Frecuencia_Enum.OD.getValue();
                        duracion = 1;
                    }else {
                    this.editable = true;
                    this.cancelable = true;
                    this.rechazable = true;
                    }           


                } else {
// RN: Se permite cancelar siempre y cuando está en horario permitido
                    boolean fechaHoraCanncelacionValida = this.solUtils.isFechaHotaCancelaValida(surtimientoExtendedSelected.getFechaProgramada(), new java.util.Date(), numMinutosModificCancela);
// RN: Se permite editar siempre y cuando no haya una mezcla de la prescripcion ya procesada
                    Integer noMezclaProcesadas = solUtils.obtenerNumeroSolucionesProcesadas(prescripcionSelected.getIdPrescripcion());
                    
                    if (permiso.isPuedeEditar()) {
                        if (validaPrescripcion) {
                            if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                                    || Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())
                                    || Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_RECHAZADA.getValue())) {
                                this.editable = true;
                                this.rechazable = true;
                            }
                        } else {
//RN: Solo el mismo usuario que prescribe puede editar
                            if (usuarioSelected.getIdUsuario().equalsIgnoreCase(surtimientoExtendedSelected.getInsertIdUsuario())) {
                                if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue())
                                        || Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue())) {
                                    this.editable = true;

// RN: EL MEDICO PUEDE CANCELAR LA MEZCLA AUNQUE YA ESTE SOLICITADA O VALIDADA, SIEMPRE Y CUANDO NO LLEGUE AL LIMITE                                
                                } else if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())) {
                                    if (fechaHoraCanncelacionValida && noMezclaProcesadas == 0) {
                                        this.editable = true;
                                    }
                                    if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.POR_AUTORIZAR.getValue())) {
                                        this.disabledEditAddInsumo = true;
                                        dosis = BigDecimal.ZERO;
                                        frecuencia = Frecuencia_Enum.OD.getValue();
                                        duracion = 1;
                                    }
                                }
                        }
                    }
                }

                    if (permiso.isPuedeEliminar()) {
                        if (validaPrescripcion) {
                            if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())) {
                                this.cancelable = true;
                            }
                        } else {
                            if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue())
                                    || Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue())) {
                                this.cancelable = true;

// RN: SI EL QUIMICO YA LA VALIDO, NO SE PERMITEN EDICIONES
                            } else if ((Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                                    || Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue()))
                                    && fechaHoraCanncelacionValida) {
                                this.cancelable = true;
                            }
                        }
                    }

                    if (permiso.isPuedeProcesar()) {
                        if (validaPrescripcion) {
                            if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                                    && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())) {
                            } else {
                                this.rechazable = true;
                            }
                        }
                    }

                }
            }
        }
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.verSurtimiento()");
        this.verAutorizacion = false;
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;

        xcantidad = 1;
        this.surtimientoInsumoExtendedListEliminar = new ArrayList<>();
        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if(!this.solUtils.permisoVerTipoSolucion(usuarioSelected, surtimientoExtendedSelected.getIdTipoSolucion())) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario no esta asignado a este tipo de solución", null);
        } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())
                && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.CANCELADO.getValue())
                && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.SURTIDO.getValue())
                ) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);
        
        } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                    && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue())
                    && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())
                    && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_RECHAZADA.getValue())
                    && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())
                    && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.CANCELADA.getValue())
                    && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.POR_AUTORIZAR.getValue())
                ) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Solución inválido.", null);
            
        } else {
            try {
                obtenerDatosSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                evaluaEdicion();
                status = Constantes.ACTIVO;
                
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(surIncorrecto), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Inicializa datos para nueva orden o prescripción
     */
    public void setNuevaOrden() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.setNuevaOrden()");
        boolean status = false;
        ordenRechazada = false;
        editarHoraInfusion = false;
        
        if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido. ", null);

        } else if (!permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso de esta acción. ", null);

        } else if(tipoSolucionList.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Este usuario no puede crear este tipo de soluciones", null); 
            surtimientoExtendedSelected = new Surtimiento_Extend();
        } else {
            listaDiagnosticoPacienteMultiple = new ArrayList<>();
            pacienteExtended = new Paciente_Extended();
            pacienteExtended.setIdPaciente(null);
            obtenerMedico();
            medicoExtended = new Usuario_Extended();
            idTipoSolucion = "";
            viaAdministracion = new ViaAdministracion();
            viaAdministracion.setIdViaAdministracion(ViaAdministracion_Enum.INTRAVENOSA.getValue());

            surtimientoExtendedSelected = new Surtimiento_Extend();
            surtimientoExtendedNew = new Surtimiento_Extend();
            surtimientoExtendedSelected.setInsertFecha(new Date());
            calculaFechaMinimaSolicitud();
            surtimientoExtendedSelected.setFechaProgramada(fechaMinPrescrMezcla);

            surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            surtimientoExtendedSelected.setEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.toString().replace("_", " "));
            surtimientoExtendedSelected.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
            surtimientoExtendedSelected.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
            surtimientoExtendedSelected.setEdadPaciente(0);

            medicamento = new Medicamento_Extended();
            cadenaBusqueda = "";
            surtimientoInsumoExtendedList = new ArrayList<>();
            listaSolucionesExtend = new ArrayList<>();
            botonGuardarMultiple = false;
            surtimientoExtListMultiple = new ArrayList<>();
            surtimientoInsumoExtListMultiple = new ArrayList<>();
            listaPrescripcionInsumoMultiple = new ArrayList<>();
            listaPrescripcionesMultiple = new ArrayList<>();
            listaDiagnosticos = null;
            claveProtocolo = null;
            dosis = BigDecimal.ZERO;
            frecuencia = Frecuencia_Enum.OD.getValue();
            duracion = 1;
            diagnosticoSelected = null;
            existePrescripcion = false;
            status = true;
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Obtiene usuarios por tipo de usuario médico
     *
     * @param cadena
     * @return
     */
    public List<Usuario> autoCompleteMedicos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.autoCompleteMedicos()");
        Integer tipoUsuario = TipoUsuario_Enum.MEDICO.getValue();
        Integer cantRegistros = Constantes.REGISTROS_PARA_MOSTRAR;
        Integer prescribeControlados = null;
        List<Usuario> listMedicos = new ArrayList<>();
        listMedicos.addAll(this.solUtils.obtenerUsuarioPorCoincidencia(cadena, tipoUsuario, cantRegistros, prescribeControlados));
        return listMedicos;
    }

    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        List<Paciente_Extended> listPacientes = new ArrayList<>();
        try {
            listPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompletePacientes : {}", ex.getMessage());
        }
        return listPacientes;
    }

    /**
     * Selecciona paciente para obtener sus datos, calcular edad y diagnósticos
     *
     * @param evt
     */
    public void selectPaciente(SelectEvent evt) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.selectPaciente()");
        this.pacienteExtended = new Paciente_Extended();
        this.listaDiagnosticos = new ArrayList<>();
        try {
            Paciente_Extended p = (Paciente_Extended) evt.getObject();
            if (p == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un paciente.", null);

            } else {
                this.pacienteExtended = this.pacienteService.obtenerPacienteCompletoPorId(p.getIdPaciente());
                if (this.pacienteExtended == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido.", null);

                } else {
                    this.surtimientoExtendedSelected.setEdadPaciente(this.pacienteExtended.getEdadPaciente());
                    this.surtimientoExtendedSelected.setPesoPaciente(this.pacienteExtended.getPesoPaciente());
                    this.surtimientoExtendedSelected.setTallaPaciente(this.pacienteExtended.getTallaPaciente());
                    this.surtimientoExtendedSelected.setAreaCorporal(this.pacienteExtended.getAreaCorporal());
                    this.surtimientoExtendedSelected.setIdPaciente(this.pacienteExtended.getIdPaciente());
                    this.surtimientoExtendedSelected.setPacienteNumero(this.pacienteExtended.getPacienteNumero());
                    if(listarDiagnosticos) {
                    this.listaDiagnosticos.addAll(this.diagnosticoService.obtenerDiagnosticoByIdPaciente(p.getIdPaciente()));
                    } else {
                        this.listaDiagnosticos = new ArrayList<>();
                }
            }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener paciente y Diagnósticos :: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener datos de Paciente y Diagnósticos.", null);
        }
    }

    /**
     * Selecciona medicamento para obtener prescribirlo
     *
     * @param evt
     */
    public void selectMedicamento(SelectEvent evt) {
            LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.selectMedicamento()");
        try {
            Medicamento_Extended m = (Medicamento_Extended) evt.getObject();
            if (m != null) {
                if (m.getIdUnidadConcentracion() != null) {
                    UnidadConcentracion uc = unidadService.obtener(new UnidadConcentracion(m.getIdUnidadConcentracion()));
                    if (uc == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Unidad de concentración de insumo inválida.", null);
                    } else {
                        if (medicamento != null) {
                            medicamento.setNombreUnidadConcentracion(uc.getNombreUnidadConcentracion());
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
     * Agrega un medicamento a la prescripción o a la orde
     */
    private boolean agregaMedicamento() throws Exception {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.agregaMedicamento()");
        SurtimientoInsumo_Extend surtInsumo = new SurtimientoInsumo_Extend();
        surtInsumo.setMedicamentoActivo(true);
        surtInsumo.setCantidadSolicitada(xcantidad);
        surtInsumo.setDosis(dosis);
        surtInsumo.setFrecuencia(frecuencia);
        surtInsumo.setDuracion(duracion);
        surtInsumo.setClaveInstitucional(medicamento.getClaveInstitucional());
        surtInsumo.setIdInsumo(medicamento.getIdMedicamento());
        surtInsumo.setNombreCorto(medicamento.getNombreCorto());
        surtInsumo.setUnidadConcentracion(medicamento.getNombreUnidadConcentracion());
        surtInsumo.setDiluyente(medicamento.isDiluyente());
        surtInsumo.setTipoInsumo(medicamento.getTipo());
        surtimientoInsumoExtendedList.add(surtInsumo);
        return true;
    }

    private boolean existeMedicamentoEnLista() {
        boolean res = false;
        for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
            if (surtimientoInsumo.getClaveInstitucional().contains(medicamento.getClaveInstitucional())) {
                res = true;
                break;
            }
        }
        return res;
    }

    private void buscarCantidadesExtremas(BigDecimal dosis, BigDecimal concentracion) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.buscarCantidadesExtremas()");
        try {
            BigDecimal envasesRequeridos = dosis.divide(concentracion, 2, RoundingMode.HALF_UP);
            BigDecimal porcenCalculado = envasesRequeridos.multiply(new BigDecimal(100));
            if (porcenCalculado.compareTo(new BigDecimal(200)) == 1) {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, "Dosis requiere " + envasesRequeridos.toString() + "  envases, revise posología y presentación de insumo.", null);

            } else if (porcenCalculado.compareTo(new BigDecimal(50)) == -1) {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, "Dosis requiere menos del 50% de 1 envase, revise posología y presentación de insumo.", null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al calcular cantidad sospechosas :: {} ", ex.getMessage());
        }
    }

    public boolean agregarMedicamento() throws Exception {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.agregarMedicamento()");
        boolean res = Constantes.INACTIVO;
        if (medicamento == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.nulo"), null);

        } else if (medicamento.getActivo() == 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamento inactivo, revise el catálogo.", null);

        } else if (medicamento.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()
                && medicamento.getConcentracion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Concentración de medicamento inválida, revise el catálogo.", null);

        } else if (medicamento.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()
                && medicamento.getConcentracion().equals(BigDecimal.ZERO)) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Concentración de medicamento inválida, revise el catálogo.", null);

        } else if (medicamento.getClaveInstitucional() == null || medicamento.getClaveInstitucional().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.nulo"), null);

        } else if (dosis == null || dosis.compareTo(BigDecimal.ZERO) == 0 || dosis.compareTo(BigDecimal.ZERO) == -1) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.dosis"), null);

        } else if (frecuencia == null ) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.frec"), null);

        } else if (duracion == null || duracion <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.ins.duracion"), null);

        } else if (duracion > numDiasMaxPresMezcla) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Duración máxima permitida: " + numDiasMaxPresMezcla + " días.", null);

        } else if (existeMedicamentoEnLista()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Insumo previamente registrado.", null);

        } else {

            xcantidad = calculaPiezasPorDosis(medicamento, dosis, frecuencia);

            if (!validaExistenciasSuficientes(medicamento, xcantidad, dosis, frecuencia, duracion)) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Revise posología de tratamiento y consulte inventario .", null);

            } else if (!agregaMedicamento()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al calcular no piezas requeridas, revise posología.", null);

            } else {

                if (medicamento.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()) {
                    buscarCantidadesExtremas(dosis, medicamento.getConcentracion());
                }

                Integer cantidadEscaneada;
                for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {
                    cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;
// RN: factor multiplicador debe ser 1 o mayor
                    if (cantidadEscaneada < 1) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);
                        medicamento = new Medicamento_Extended();
// RN: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                    } else if (surtimientoInsumo.getClaveInstitucional().contains(medicamento.getClaveInstitucional())) {

// RN: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                        if (!surtimientoInsumo.isMedicamentoActivo()) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.clavebloqueada"), null);

                        } else {
// RN: Valida duplicidad de tratamiento
                            List<String> surtIdInsumoLista = new ArrayList<>();
                            if (medicamento != null) {
                                if (medicamento.getIdMedicamento() != null) {
                                    surtIdInsumoLista.add(medicamento.getIdMedicamento());
                                    String tratamientoDuplicado = this.solUtils.validaTratamientoDuplicado(surtimientoExtendedSelected, surtIdInsumoLista, tipoInsumoValidaTratDuplicado, noHrsPrevValidaTratDuplicado, noHrsPostValidaTratDuplicado);
                                    if (tratamientoDuplicado != null && !tratamientoDuplicado.isEmpty()) {
                                        String[] lineasTratamiento = tratamientoDuplicado.split(StringUtils.LF);
                                        for (String s : lineasTratamiento) {
                                            Mensaje.showMessage(Constantes.MENSAJE_WARN, s, null);
                                        }
                                    }
                                }
// RN: Valida el volumen total contra el volumen del diluyente
                                if (medicamento.isDiluyente()) {
                                    String volumenDiferente = solUtils.validaVolTotalVolDiluyente(dosis, surtimientoExtendedSelected.getVolumenTotal());
                                    if (volumenDiferente != null) {
                                        Mensaje.showMessage(Constantes.MENSAJE_WARN, volumenDiferente, null);
                                    }
                                }
                            }

                            res = Constantes.ACTIVO;
                            msjMdlSurtimiento = true;
                            xcantidad = 1;
                            dosis = BigDecimal.ZERO;
                            frecuencia = Frecuencia_Enum.OD.getValue();
                            duracion = 1;
                        }
                        medicamento = new Medicamento_Extended();
                        if (!surtimientoInsumoExtListMultiple.isEmpty()) {
                            String existeInsumo = validarDuplicidadEnListaMultiple();
                            if (existeInsumo != null) {
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, "Posible duplicidad en esta prescripción múltiple : " + existeInsumo, null);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
    
    private Integer calculaPiezasPorDosis (Medicamento m, BigDecimal dosisPrescrita, Integer frecuenciaDosis){
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.calculaPiezasPorDosis()");
        Integer cantidadPiezas = 0;
        try {
            double noTomasAlDia = 1d;
            if (frecuenciaDosis > 0){
                noTomasAlDia = (24 / frecuenciaDosis.doubleValue());
            }
            
            if (m.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()) {
                BigDecimal noPiezasPorDosis = dosisPrescrita.divide(m.getConcentracion(), 2, RoundingMode.HALF_EVEN);
                cantidadPiezas = (int) Math.ceil(noPiezasPorDosis.doubleValue());
            } else {
                BigDecimal noPiezasPorDosis = dosisPrescrita;
                cantidadPiezas = (int) Math.ceil(noPiezasPorDosis.doubleValue());
            }
        } catch (Exception e) {
            LOGGER.error("Error al cálcular la cantidad necesaria de piezas :: {} ", e.getMessage());
        }
        return cantidadPiezas;
    }
    
    private boolean validaExistenciasSuficientes (Medicamento_Extended m, Integer cantidadCalculada, BigDecimal dosisPrescrita, Integer frecuenciaDosis, Integer duracionDosis) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaExistenciasSuficientes()");
        boolean res = true;
        String disponibilidad = validarExistenciasDisponibles(m, cantidadCalculada, dosisPrescrita, frecuenciaDosis, duracionDosis);
        if (disponibilidad != null) {
            if (validaExistenciasRestrictiva) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, disponibilidad, null);
                res = false;
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, disponibilidad, null);
            }
        }
        return res;
    }
    
    public String validarExistenciasDisponibles(Medicamento_Extended m, Integer cantidadCalculada, BigDecimal dosisPrescrita, Integer frecuenciaDosis, Integer duracionDosis) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validarExistenciasDisponibles()");
        String res = null;
        try {
            if (m!= null){
                if (cantidadCalculada > 0){
                    Integer tomasPorDia = 0;
                    
                    if (frecuenciaDosis > 0 && frecuenciaDosis <= 24 ) {
                        tomasPorDia = (24 / frecuenciaDosis);
                    } else if (frecuenciaDosis >= 13) {
                        tomasPorDia = 1;
                    }

                    if (validaExistencias1erToma){
                        Integer pzasPrimerDosis = cantidadCalculada;
                        if (pzasPrimerDosis.doubleValue() > m.getCantidadDisponible()) {
                            res = "Cantidad insuficiente para surtir la primer mezcla.";
                        }
                    }
                    
                    if (validaExistencias1erDia) {
                        Integer pzasPrimerDia = cantidadCalculada * tomasPorDia;
                        if (pzasPrimerDia.doubleValue() > m.getCantidadDisponible()) {
                            res = "Cantidad insuficiente para surtir las mezclas del primer día.";
                        }
                    }
                    if (validaExistenciasTomasTotales) {
                        Integer pzasTotales = 0;
                        if(frecuenciaDosis <= 12) {
                            Integer tomasTotales = tomasPorDia * duracionDosis;
                            pzasTotales = cantidadCalculada * tomasTotales;
                        } else {
                            pzasTotales = (duracionDosis * 24) / frecuenciaDosis + 1;
                        }
                        
                        if (pzasTotales.doubleValue() > m.getCantidadDisponible()) {
                            res = "Cantidad insuficiente para surtir las mezclas del tratamiento completo.";
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validar existencias :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
        }
        return res;
    }

    private Integer obtenerIdProtocolo(String claveProtocolo) {
        Integer idProtocolo = null;
        for (Protocolo proto : listaProtocolos) {
            if (proto.getClaveProtocolo().equals(claveProtocolo)) {
                idProtocolo = proto.getIdProtocolo();
                break;
            }
        }
        return idProtocolo;
    }

    private boolean obtenerPrescripcion() {
        boolean exists = false;
        prescripcionSelected = null;
        try {
            if (surtimientoExtendedSelected.getFolioPrescripcion() != null) {
                if (!surtimientoExtendedSelected.getFolioPrescripcion().trim().isEmpty()) {
                    Prescripcion presc = new Prescripcion();
                    presc.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
                    prescripcionSelected = prescripcionService.obtener(presc);
                }
            }

            if (prescripcionSelected == null) {
                prescripcionSelected = new Prescripcion();
                prescripcionSelected.setIdPrescripcion(Comunes.getUUID());
                prescripcionSelected.setIdEstructura(surtimientoExtendedSelected.getIdEstructura());
// el idPacienteServicio no es la estructura
//                prescripcionSelected.setIdPacienteServicio(surtimientoExtendedSelected.getIdEstructura());
                if (surtimientoExtendedSelected.getFolioPrescripcion() != null) {
                    if (!surtimientoExtendedSelected.getFolioPrescripcion().trim().isEmpty()) {
                        prescripcionSelected.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
                    }
                }
                prescripcionSelected.setTipoPrescripcion(surtimientoExtendedSelected.getTipoPrescripcion());
                prescripcionSelected.setTipoConsulta(surtimientoExtendedSelected.getTipoConsulta());
                prescripcionSelected.setIdMedico(medico.getIdUsuario());
                prescripcionSelected.setRecurrente(false);
                prescripcionSelected.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                prescripcionSelected.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
                Date fechaPresc = new Date();
                prescripcionSelected.setFechaPrescripcion(fechaPresc);
                prescripcionSelected.setFechaFirma(fechaPresc);
                prescripcionSelected.setInsertFecha(fechaPresc);
                prescripcionSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                prescripcionSelected.setIdTipoSolucion(idTipoSolucion);
                prescripcionSelected.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());
                if (claveProtocolo != null && isOncologica) {
                    prescripcionSelected.setIdProtocolo(obtenerIdProtocolo(claveProtocolo));
                    if (diagnosticoSelected != null) {
                        prescripcionSelected.setIdDiagnostico(diagnosticoSelected.getIdDiagnostico());
                    }
                }
            } else {
                exists = true;
                if (claveProtocolo != null && isOncologica) {
                    prescripcionSelected.setIdProtocolo(obtenerIdProtocolo(claveProtocolo));
                    if (diagnosticoSelected != null) {
                        prescripcionSelected.setIdDiagnostico(diagnosticoSelected.getIdDiagnostico());
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("mx.mc.magedbean.RegistroSolucionMB.obtenerPrescripcion()", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.obtener"), null);
        }
        return exists;
    }

    private void generarSurtimiento() {
        surtimientoExtendedSelected.setIdSurtimiento(Comunes.getUUID());
// TODO: 01OCT2023-01 HRC - Buscar la asignación del almacen surtidor al servicio que gennera prescripción        
        surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
        surtimientoExtendedSelected.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
        surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        Date fechaSurt = new Date();
        surtimientoExtendedSelected.setInsertFecha(fechaSurt);
        surtimientoExtendedSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        surtimientoExtendedSelected.setIdEstatusMirth(1);
    }

    private void generarListaPrescripcionInsumo(List<SurtimientoInsumo_Extend> sel, Prescripcion pr, Surtimiento_Extend se ) {
        boolean isFirst = true;
        Date fechaPresc = new Date();
        listaPrescripcionInsumo = new ArrayList<>();
        for (SurtimientoInsumo_Extend surtInsumo : sel) {
            PrescripcionInsumo prescripcionInsumo = new PrescripcionInsumo();
            prescripcionInsumo.setIdPrescripcionInsumo(Comunes.getUUID());
            surtInsumo.setIdPrescripcionInsumo(prescripcionInsumo.getIdPrescripcionInsumo());
            prescripcionInsumo.setIdPrescripcion(pr.getIdPrescripcion());
            prescripcionInsumo.setIdInsumo(surtInsumo.getIdInsumo());
            if (isFirst) {
                isFirst = false;
                prescripcionInsumo.setIdTipoIngrediente(1);
                prescripcionInsumo.setPerfusionContinua(se.isPerfusionContinua() ? 1 : 0);
            }
            prescripcionInsumo.setDosis(surtInsumo.getDosis());
            prescripcionInsumo.setFrecuencia(surtInsumo.getFrecuencia());
            prescripcionInsumo.setDuracion(surtInsumo.getDuracion());

            prescripcionInsumo.setFechaInicio(fechaPresc);
            prescripcionInsumo.setInsertFecha(fechaPresc);
            prescripcionInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            prescripcionInsumo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
            prescripcionInsumo.setVelocidad(se.getVelocidad());
            prescripcionInsumo.setRitmo(se.getRitmo());
            prescripcionInsumo.setIdEstatusMirth(1);
            /**
             * No se captura el dato en pantalla, para soluciones, se setea a 2
             * = ml *
             */
            prescripcionInsumo.setIdUnidadConcentracion(2);
            listaPrescripcionInsumo.add(prescripcionInsumo);
        }
    }

    public boolean existeEnListaDiagPac(List<DiagnosticoPaciente> listaDiagnosticoPaciente, String idDiagnostico) {
        boolean existe = false;
        for (DiagnosticoPaciente diagPac : listaDiagnosticoPaciente) {
            if (diagPac.getIdDiagnostico().equalsIgnoreCase(idDiagnostico)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    public void nuevaSolucion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.nuevaSolucion");
        boolean status = false;
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if ((!validaPrescripcion && !permiso.isPuedeCrear())
                    || (validaPrescripcion && !permiso.isPuedeCrear()) && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);
            } else if (this.surtimientoExtendedSelected.getIdPaciente() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido. ", null);

            } else if (surtimientoExtendedSelected.getPesoPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre peso. ", null);

            } else if (surtimientoExtendedSelected.getTallaPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre estatura. ", null);

            } else if (surtimientoExtendedSelected.getAreaCorporal() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre superficie corporal. ", null);

            } else {
                status = true;
                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoExtendedSelected.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
                if (!listaSolucionesExtend.isEmpty()) {
                    idTipoSolucion = "";
                    surtimientoExtendedSelected.setVolumenTotal(BigDecimal.ZERO);
                    surtimientoExtendedSelected.setIdContenedor(0);
                    surtimientoExtendedSelected.setPerfusionContinua(false);
                    surtimientoExtendedSelected.setHorasInfusion("");
                    surtimientoExtendedSelected.setVelocidad(null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo nuevaSolucion :: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error en metodo nuevaSolucion ", null); //RESOURCES.getString("cm.registro.error"), null);
        }

        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void validaAgregarSolucionMult() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaAgregarSolucionMult()");
        boolean status = false;

        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if ((!validaPrescripcion && !permiso.isPuedeCrear())
                    || (validaPrescripcion && !permiso.isPuedeCrear()) && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (this.surtimientoExtendedSelected.getIdPaciente() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido. ", null);

            } else if (surtimientoExtendedSelected.getPesoPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre peso. ", null);

            } else if (surtimientoExtendedSelected.getTallaPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre estatura. ", null);

            } else if (surtimientoExtendedSelected.getAreaCorporal() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre superficie corporal. ", null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado los insumos por Registrar", null);
                
            } else if (idTipoSolucion == null || idTipoSolucion.trim().isEmpty()){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione tipo de Mezcla.", null);
            
            } else if (viaAdministracion == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Vía de administración.", null);

            } else if (viaAdministracion.getIdViaAdministracion() == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Vía de administración.", null);

            } else if (surtimientoExtendedSelected.getVolumenTotal() == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture el volumen total de la mezcla.", null);

            } else if (surtimientoExtendedSelected.getIdContenedor() == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Contenedor.", null);

            } else if (surtimientoExtendedSelected.getHorasInfusion() == null 
                    || surtimientoExtendedSelected.getHorasInfusion().trim().isEmpty()){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture tiempo de infusion.", null);

            } else if (validaDiagnosticoProtocolo()) {

                Paciente p = pacienteService.obtenerPacienteByIdPaciente(pacienteExtended.getIdPaciente());

                if (p == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido. ", null);

                } else if (listaDiagnosticos == null || listaDiagnosticos.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre Diagnósticos. ", null);

                } else {
                    
                    // RN: valida periodo y frecuencia de insumos diferente
                    List<String> surtIdInsumoLista = new ArrayList<>();
                    String freDurDistinto = this.solUtils.periodoFrecuenciaDistinto(surtimientoInsumoExtendedList, surtIdInsumoLista);
                    if (freDurDistinto != null ) {
                        status = false;
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, freDurDistinto + " de los insumos diferente.", null);

                    } else {
// RN: Valida duplicidad de tratamiento
                        String tratamientoDuplicado = this.solUtils.validaTratamientoDuplicado(surtimientoExtendedSelected, surtIdInsumoLista, tipoInsumoValidaTratDuplicado, noHrsPrevValidaTratDuplicado, noHrsPostValidaTratDuplicado);
                        if (tratamientoDuplicado != null && !tratamientoDuplicado.isEmpty()) {
                            String[] lineasTratamiento = tratamientoDuplicado.split(StringUtils.LF);
                            for (String s : lineasTratamiento) {
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, s, null);
                            }
                        }
                        boolean existePresc = obtenerPrescripcion();
                        status = validarEstabilidades();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar agregar la order de solucion multiple :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    public void agregarSolucion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.agregarSolucion");        
        boolean status = false;
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if (!permiso.isPuedeCrear() || !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado los insumos por Registrar", null);

            } else if (validaDiagnosticoProtocolo()) {

                Paciente p = pacienteService.obtenerPacienteByIdPaciente(pacienteExtended.getIdPaciente());
//                HorarioEntrega he = obtenerHorarioEntrega(surtimientoExtendedSelected.getIdHorarioParaEntregar());
                if (p == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido. ", null);

                } else if (listaDiagnosticos == null || listaDiagnosticos.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre Diagnósticos. ", null);
                    
                } else {
// RN: valida periodo y frecuencia de insumos diferente
                    List<String> surtIdInsumoLista = new ArrayList<>();
                    String freDurDistinto = this.solUtils.periodoFrecuenciaDistinto(surtimientoInsumoExtendedList, surtIdInsumoLista);
                    if (freDurDistinto != null ) {
                        status = false;
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, freDurDistinto + " de los insumos diferente.", null);

                    } else {
// RN: Valida duplicidad de tratamiento
                        String tratamientoDuplicado = this.solUtils.validaTratamientoDuplicado(surtimientoExtendedSelected, surtIdInsumoLista, tipoInsumoValidaTratDuplicado, noHrsPrevValidaTratDuplicado, noHrsPostValidaTratDuplicado);
                        if (tratamientoDuplicado != null && !tratamientoDuplicado.isEmpty()) {
                            String[] lineasTratamiento = tratamientoDuplicado.split(StringUtils.LF);
                            for (String s : lineasTratamiento) {
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, s, null);
                            }
                        }
                        boolean existePresc = obtenerPrescripcion();
                        generarListaPrescripcionInsumo(surtimientoInsumoExtendedList, prescripcionSelected, surtimientoExtendedSelected );
                        
                        prescripcionSelected.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());

// TODO: 09SEP2023-01 HRC - Buscar la asignación del almacen surtidor al servicio que gennera prescripción
//                            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
//                            surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
//                            surtimientoExtendedSelected.setUpdateFecha(fechaSurt);
//                            surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
//                        surtimientoExtendedSelected.setFechaParaEntregar(fechaEntrega);
                        status = generaListaSoluciones(surtimientoInsumoExtendedList, surtimientoExtendedSelected, prescripcionSelected);

                        if (!status) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al agrear solución.", null);
                        } else {
                            listaPrescripcionesMultiple.add(prescripcionSelected);
                            listaPrescripcionInsumoMultiple.addAll(listaPrescripcionInsumo);
                            Date fechaSurt = new Date();
                            List<DiagnosticoPaciente> listaDiagnosticoPaciente = new ArrayList<>();
                            if (listaDiagnosticos != null) {
                                for (Diagnostico diag : listaDiagnosticos) {
                                    if (!existeEnListaDiagPac(listaDiagnosticoPaciente, diag.getIdDiagnostico())) {
                                        DiagnosticoPaciente diagPac = new DiagnosticoPaciente();
                                        diagPac.setIdDiagnosticoPaciente(Comunes.getUUID());
                                        diagPac.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                                        diagPac.setFechaDiagnostico(fechaSurt);
                                        diagPac.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                                        diagPac.setIdDiagnostico(diag.getIdDiagnostico());
                                        diagPac.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                                        diagPac.setInsertFecha(fechaSurt);
                                        diagPac.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                        listaDiagnosticoPaciente.add(diagPac);
                                    }
                                }
                            }
                            for (DiagnosticoPaciente diagnosticoPac : listaDiagnosticoPaciente) {
                                listaDiagnosticoPacienteMultiple.add(diagnosticoPac);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo generaListaSoluciones :: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public String validarDuplicidadEnListaMultiple() {
        StringBuilder sb = new StringBuilder();
        String existeDuplicidad = null;
        Integer cont = 0;
        if (!surtimientoInsumoExtListMultiple.isEmpty()) {
            for (SurtimientoInsumo_Extend surtInsumoLista : surtimientoInsumoExtListMultiple) {
                for (SurtimientoInsumo_Extend surtInsumo : surtimientoInsumoExtendedList) {
                    if (!surtInsumo.isDiluyente()) {
                        if (surtInsumoLista.getClaveInstitucional().equalsIgnoreCase(surtInsumo.getClaveInstitucional())) {
                            sb.append(" Insumo: ");
                            sb.append(surtInsumo.getNombreCorto());
                            sb.append(" Fecha: ");
                            sb.append(FechaUtil.formatoFecha(surtInsumoLista.getFechaProgramada(), "dd/MM/yyyy HH:mm:ss"));
                            sb.append(" Posología: ");
                            sb.append(surtInsumoLista.getDosis());
                            sb.append(StringUtils.SPACE);
                            sb.append(surtInsumoLista.getUnidadConcentracion());
                            sb.append("|");
                            sb.append(surtInsumoLista.getFrecuencia());
                            sb.append("|");
                            sb.append(surtInsumoLista.getDuracion());
                            cont++;
                        }
                    }
                }
            }
        }
        if (cont > 0) {
            existeDuplicidad = sb.toString();
        }
        return existeDuplicidad;
    }

    public void generaNuevoSurtimiento() {
        if (!surtimientoExtListMultiple.isEmpty()) {
            nuevoSurtimiento();
        } else {
            generarSurtimiento();
        }
    }

    /**
     * Se genera nuevo surtimiento para prescripcion multiple ya que es
     * necesario para poder modificar el idPrescripcion y el idSurtimiento y
     * agregarlo a la lista y no se modifique el que ya esta en la lista ya que
     * se modifica por referencia al objeto
     */
    private void nuevoSurtimiento() {
        Integer numHoras = surtimientoInsumoExtendedList.get(0).getFrecuencia();
        Date fechaProg = FechaUtil.sumarRestarHorasFecha(surtimientoExtendedSelected.getFechaProgramada(), numHoras);  
        surtimientoExtendedNew = surtimientoExtendedSelected;
        surtimientoExtendedSelected = new Surtimiento_Extend();
        surtimientoExtendedSelected.setIdSurtimiento(Comunes.getUUID());
        surtimientoExtendedSelected.setIdEstructuraAlmacen(surtimientoExtendedNew.getIdEstructuraAlmacen());
        surtimientoExtendedSelected.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
        surtimientoExtendedSelected.setIdEstatusSurtimiento(surtimientoExtendedNew.getIdEstatusSurtimiento());
        surtimientoExtendedSelected.setInsertFecha(surtimientoExtendedNew.getInsertFecha());
        surtimientoExtendedSelected.setInsertIdUsuario(surtimientoExtendedNew.getInsertIdUsuario());
        surtimientoExtendedSelected.setIdEstatusMirth(1);

        surtimientoExtendedSelected.setIdEstructura(surtimientoExtendedNew.getIdEstructura());
        surtimientoExtendedSelected.setCama(surtimientoExtendedNew.getCama());
        surtimientoExtendedSelected.setTipoConsulta(surtimientoExtendedNew.getTipoConsulta());
        surtimientoExtendedSelected.setPesoPaciente(surtimientoExtendedNew.getPesoPaciente());
        surtimientoExtendedSelected.setTallaPaciente(surtimientoExtendedNew.getTallaPaciente());
        surtimientoExtendedSelected.setAreaCorporal(surtimientoExtendedNew.getAreaCorporal());
        surtimientoExtendedSelected.setDiabetes(surtimientoExtendedNew.isDiabetes());
        surtimientoExtendedSelected.setProblemasRenales(surtimientoExtendedNew.isProblemasRenales());
        surtimientoExtendedSelected.setHipertension(surtimientoExtendedNew.isHipertension());
        surtimientoExtendedSelected.setPadecimientoCronico(surtimientoExtendedNew.isPadecimientoCronico());
        surtimientoExtendedSelected.setIdPaciente(surtimientoExtendedNew.getIdPaciente());
        surtimientoExtendedSelected.setIdContenedor(surtimientoExtendedNew.getIdContenedor());
        surtimientoExtendedSelected.setVolumenTotal(surtimientoExtendedNew.getVolumenTotal());
        surtimientoExtendedSelected.setVelocidad(surtimientoExtendedNew.getVelocidad());
        surtimientoExtendedSelected.setPerfusionContinua(surtimientoExtendedNew.isPerfusionContinua());
        surtimientoExtendedSelected.setUnidadConcentracion(surtimientoExtendedNew.getUnidadConcentracion());
        surtimientoExtendedSelected.setRitmo(surtimientoExtendedNew.getRitmo());
        surtimientoExtendedSelected.setFechaParaEntregar(surtimientoExtendedNew.getFechaParaEntregar());
        surtimientoExtendedSelected.setIdHorarioParaEntregar(surtimientoExtendedNew.getIdHorarioParaEntregar());
        surtimientoExtendedSelected.setFechaProgramada(fechaProg);
        surtimientoExtendedSelected.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
        surtimientoExtendedSelected.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
    }
    
    public Surtimiento_Extend duplicarSurtimiento(Surtimiento_Extend s){
        Surtimiento_Extend o = s;
        return o;
    }

    public boolean generaListaSoluciones(List<SurtimientoInsumo_Extend> siel, Surtimiento_Extend se, Prescripcion pr) {
        boolean estatus = false;
        boolean porAutorizar = Constantes.INACTIVO;
        try {
            Integer numDias = 1;
            Integer numTomas = 1;
            Integer numHoras = siel.get(0).getFrecuencia();
            Integer numMezclas = 1;
            if(numHoras >= 13) {
                numDias = siel.get(0).getDuracion();
                numMezclas = (numDias * 24) / numHoras + 1;
            } else {
                if (numHoras > 0){
                    numDias = siel.get(0).getDuracion();
                    numTomas = (24 / numHoras);
                    numMezclas = numDias * numTomas;
                }
            }            
            
            Integer cantidad1 = 0;
            String insumoMayorConcent = "";
            StringBuilder descripcionFarmaco = new StringBuilder();
            StringBuilder descripcionDiluyente = new StringBuilder();
            for (SurtimientoInsumo_Extend surtInsumo : siel) {
                if (!surtInsumo.isDiluyente()) {
//                    if (cantidad1 > 0) {
//                        if (cantidad1 < surtInsumo.getCantidadSolicitada()) {
//                            cantidad1 = surtInsumo.getCantidadSolicitada();
//                            insumoMayorConcent = surtInsumo.getCantidadSolicitada() + " : " + surtInsumo.getClaveInstitucional() + " - " + surtInsumo.getNombreCorto();
//                        }
//                    } else {
//                        cantidad1 = surtInsumo.getCantidadSolicitada();
//                        insumoMayorConcent = surtInsumo.getCantidadSolicitada() + " : " + surtInsumo.getClaveInstitucional() + " - " + surtInsumo.getNombreCorto();
//                    }
                    descripcionFarmaco.append(surtInsumo.getNombreCorto());
                    if (surtInsumo.getDosis() != null ){
                        descripcionFarmaco.append(StringUtils.SPACE);
                        descripcionFarmaco.append(":");
                        descripcionFarmaco.append(StringUtils.SPACE);
                        descripcionFarmaco.append(surtInsumo.getDosis().toString());
                        descripcionFarmaco.append(StringUtils.SPACE);
                        descripcionFarmaco.append(surtInsumo.getUnidadConcentracion());
                    }
                } else {
                    descripcionDiluyente.append(surtInsumo.getNombreCorto());
                    if (surtInsumo.getDosis() != null ){
                        descripcionDiluyente.append(StringUtils.SPACE);
                        descripcionDiluyente.append(":");
                        descripcionDiluyente.append(StringUtils.SPACE);
                        descripcionDiluyente.append(surtInsumo.getDosis().toString());
                        descripcionDiluyente.append(StringUtils.SPACE);
                        descripcionDiluyente.append(surtInsumo.getUnidadConcentracion());
                    }    
                }
            }
            
            Date fechaSurt = new Date();
                    
            Surtimiento_Extend seTmp;
            Date fechaProgramada = se.getFechaProgramada();
            for (int i = 0; i < numMezclas; i++) {
                if (i > 0) {
                    Date fechaProg = FechaUtil.sumarRestarHorasFecha(fechaProgramada, numHoras);
                    fechaProgramada = fechaProg;
                }
                seTmp = new Surtimiento_Extend();
                seTmp.setFechaProgramada(fechaProgramada);
                seTmp.setIdSurtimiento(Comunes.getUUID());
                
                String idEstructuraalmacen = this.solUtils.obtieneAlmacenesPorIdTipoSolucion(pr.getIdTipoSolucion()).get(0);
                
                seTmp.setIdEstructuraAlmacen(idEstructuraalmacen);
                
                seTmp.setIdPrescripcion(pr.getIdPrescripcion());
                seTmp.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                seTmp.setInsertFecha(fechaSurt);
                seTmp.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                seTmp.setIdEstatusMirth(1);

                seTmp.setIdEstructura(se.getIdEstructura());
                seTmp.setCama(se.getCama());
                seTmp.setTipoConsulta(se.getTipoConsulta());
                seTmp.setPesoPaciente(se.getPesoPaciente());
                seTmp.setTallaPaciente(se.getTallaPaciente());
                seTmp.setAreaCorporal(se.getAreaCorporal());
                seTmp.setDiabetes(se.isDiabetes());
                seTmp.setProblemasRenales(se.isProblemasRenales());
                seTmp.setHipertension(se.isHipertension());
                seTmp.setPadecimientoCronico(se.isPadecimientoCronico());
                seTmp.setIdPaciente(se.getIdPaciente());
                seTmp.setIdContenedor(se.getIdContenedor());
                seTmp.setVolumenTotal(se.getVolumenTotal());
                seTmp.setVelocidad(se.getVelocidad());
                seTmp.setPerfusionContinua(se.isPerfusionContinua());
                seTmp.setUnidadConcentracion(se.getUnidadConcentracion());
                seTmp.setRitmo(se.getRitmo());
                seTmp.setFechaParaEntregar(se.getFechaParaEntregar());
                seTmp.setIdHorarioParaEntregar(se.getIdHorarioParaEntregar());
                seTmp.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
                seTmp.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
                seTmp.setMinutosInfusion(se.getMinutosInfusion());
                seTmp.setIdProtocolo(se.getIdProtocolo());
                seTmp.setIdDiagnostico(se.getIdDiagnostico());
                
                SurtimientoInsumo_Extend sie;
                List<SurtimientoInsumo_Extend> sieList = new ArrayList<>();
                Integer conta = 1;
                for (SurtimientoInsumo_Extend si : siel) {
                    sie = new SurtimientoInsumo_Extend();
                    
                    sie.setIdSurtimientoInsumo(Comunes.getUUID());
                    sie.setIdSurtimiento(seTmp.getIdSurtimiento());
                    sie.setIdPrescripcionInsumo(si.getIdPrescripcionInsumo());
                    sie.setClaveInstitucional(si.getClaveInstitucional());
                    sie.setDosis(si.getDosis());
                    sie.setUnidadConcentracion(si.getUnidadConcentracion());
                    sie.setFrecuencia(si.getFrecuencia());
                    sie.setDuracion(si.getDuracion());
                    
                    sie.setFechaProgramada(seTmp.getFechaProgramada());
                    sie.setCantidadSolicitada(si.getCantidadSolicitada());
                    sie.setIdUsuarioAutCanRazn(si.getIdUsuarioAutCanRazn());
                    sie.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    sie.setInsertFecha(fechaSurt);
                    sie.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    sie.setNumeroMedicacion(conta);
                    sie.setCantidadVale(0);
                    sie.setIdEstatusMirth(1);
                    sieList.add(sie);
                    Medicamento medicamentoAutorizar =  medicamentoService.obtener(new Medicamento(si.getIdInsumo()));
                    if(medicamentoAutorizar != null){
                        if(medicamentoAutorizar.isAutorizar()) {
                            porAutorizar = Constantes.ACTIVO;                                
                        }                                   
                    } 
                    conta++;
                }
                surtimientoInsumoExtListMultiple.addAll(sieList);

                surtimientoExtListMultiple.add(seTmp);

                SolucionExtended s = new SolucionExtended();
                s.setIdSolucion(Comunes.getUUID());
                s.setIdSurtimiento(seTmp.getIdSurtimiento());
                if(porAutorizar) {
                    s.setIdEstatusSolucion(EstatusSolucion_Enum.POR_AUTORIZAR.getValue());
                } else {
                    s.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                }                
                s.setIdEnvaseContenedor(se.getIdContenedor());
//                s.setDescripcion(se.getObservaciones());
                s.setInstruccionesPreparacion(se.getInstruccionPreparacion());
                s.setProteccionLuz((se.isProteccionLuz()) ? 1 : 0);
                s.setProteccionTemp((se.isTempAmbiente()) ? 1 : 0);
                s.setProteccionTempRefrig((se.isTempRefrigeracion()) ? 1 : 0);
                s.setNoAgitar((se.isNoAgitar()) ? 1 : 0);
                s.setEdadPaciente(se.getEdadPaciente());
                s.setPesoPaciente(se.getPesoPaciente());
                s.setTallaPaciente(se.getTallaPaciente());
                s.setAreaCorporal(se.getAreaCorporal());
                s.setIdViaAdministracion(pr.getIdViaAdministracion());
                s.setPerfusionContinua((se.isPerfusionContinua()) ? 1 : 0);
                s.setVelocidad(se.getVelocidad());
                s.setRitmo(se.getRitmo());
                s.setIdProtocolo(se.getIdProtocolo());
                s.setIdDiagnostico(se.getIdDiagnostico());
                s.setVolumenFinal((se.getVolumenTotal() != null) ? se.getVolumenTotal().toString() : "0");
                s.setVolumen((se.getVolumenTotal() != null) ? BigDecimal.valueOf(se.getVolumenTotal().doubleValue()) : BigDecimal.ZERO);
                s.setUnidadConcentracion(se.getUnidadConcentracion());
                s.setInsertFecha(new java.util.Date());
                s.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                s.setFechaProgramada(fechaProgramada);
                s.setFechaParaEntregar(fechaProgramada);
                s.setMinutosInfusion(se.getMinutosInfusion());
                
                if(se.getHorasInfusion() != null || !se.getHorasInfusion().isEmpty()) {
                    String cadenaHora[] = se.getHorasInfusion().split(":");
                    Integer hora = Integer.valueOf(cadenaHora[0]);
                    Integer minut = Integer.valueOf(cadenaHora[1]);
                    Integer minutosTotales = (hora * 60) + minut;
                    s.setMinutosInfusion(minutosTotales);
                } else {
                    s.setMinutosInfusion(0);
                }
                
//                s.setDescripcion(se.getObservaciones());
                s.setObservaciones(insumoMayorConcent);
                StringBuilder descripcionMezcla = new StringBuilder();
                descripcionMezcla.append(descripcionFarmaco);
                descripcionMezcla.append(StringUtils.SPACE);
                descripcionMezcla.append("|");
                descripcionMezcla.append(StringUtils.SPACE);
                descripcionMezcla.append(descripcionDiluyente);
                s.setDescripcion(descripcionMezcla.toString());
                
                for (TipoSolucion tipoSol : tipoSolucionList) {
                    if (tipoSol.getIdTipoSolucion().equalsIgnoreCase(pr.getIdTipoSolucion())) {
                        s.setTipoSolucion(tipoSol.getClave());
                    }
                }

                listaSolucionesExtend.add(s);
            }
            
            estatus = true;
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo generaListaSoluciones :: {}", ex.getMessage());
        }
        return estatus;
    }

    public void validarNuevaOrdenMultiple() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validarNuevaOrdenMultiple");
        boolean status = false;
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if ((!validaPrescripcion && !permiso.isPuedeCrear())
                    || (validaPrescripcion && !permiso.isPuedeCrear()) && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (this.surtimientoExtendedSelected.getIdEstructura()== null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione Area/Servicio. ", null);
            
            } else if (this.surtimientoExtendedSelected.getCama() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione cama. ", null);
            
            } else if (this.surtimientoExtendedSelected.getIdPaciente() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido. ", null);

            } else if (surtimientoExtendedSelected.getPesoPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre peso. ", null);

            } else if (surtimientoExtendedSelected.getTallaPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre estatura. ", null);

            } else if (surtimientoExtendedSelected.getAreaCorporal() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre superficie corporal. ", null);

            } else if (listaSolucionesExtend == null || listaSolucionesExtend.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe de generar por lo menos una solución", null);//Usuario inválido ", null);
            } else {
                status = validarEstabilidades();
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validarNuevaOrdenMultiple :: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error en metodo validarNuevaOrdenMultiple ", null); //RESOURCES.getString("cm.registro.error"), null);
        }

        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void validarNuevaOrden() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validarNuevaOrden()");
        boolean status = false;

        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if ((!validaPrescripcion && !permiso.isPuedeCrear())
                    || (validaPrescripcion && !permiso.isPuedeCrear()) && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (this.surtimientoExtendedSelected.getIdEstructura() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione servicio. ", null);

            } else if (this.surtimientoExtendedSelected.getCama() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione cama. ", null);

            } else if (this.surtimientoExtendedSelected.getIdPaciente() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido. ", null);

            } else if (surtimientoExtendedSelected.getPesoPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture peso. ", null);

            } else if (surtimientoExtendedSelected.getTallaPaciente() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture estatura. ", null);

            } else if (surtimientoExtendedSelected.getAreaCorporal() <= 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture superficie corporal. ", null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Debe registrar al menos 1 insumos", null);
            
            } else if (surtimientoExtendedSelected.getVolumenTotal() == null || surtimientoExtendedSelected.getVolumenTotal().compareTo(BigDecimal.ZERO) != 1 ) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture volumen total", null);
            
            } else if (surtimientoExtendedSelected.getIdContenedor() == null || surtimientoExtendedSelected.getIdContenedor() < 1 ) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione contenedor", null);
            
            } else if (surtimientoExtendedSelected.getHorasInfusion() == null || surtimientoExtendedSelected.getHorasInfusion().trim().equals(StringUtils.EMPTY) ) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture tiempo de infusión", null);

            } else if (validaDiagnosticoProtocolo()) {

                Paciente p = pacienteService.obtenerPacienteByIdPaciente(pacienteExtended.getIdPaciente());

                if (p == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente inválido. ", null);

                } else if (listaDiagnosticos == null || listaDiagnosticos.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre Diagnósticos. ", null);

                } else {

                    status = validarEstabilidades();
                    
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo registrarOrden :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    
    public boolean validarEstabilidades() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validarEstabilidades()");
// RN: valida periodo de insumos identico
        boolean res = false;
        try {
            List<String> surtIdInsumoLista = new ArrayList<>();
            String freDurDistinto = this.solUtils.periodoFrecuenciaDistinto(surtimientoInsumoExtendedList, surtIdInsumoLista);
            if (freDurDistinto != null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, freDurDistinto + " de los insumos diferente.", null);

            } else {

                Integer noFarmacos = 0;
                Integer noDiluyentes = 0;
                Integer noMateriales = 0;

                for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                    surtIdInsumoLista.add(item.getIdInsumo());

// RN: Valida el volumen total contra el volumen del diluyente
                    
                    if (item.getTipoInsumo().equals(CatalogoGeneral_Enum.MEDICAMENTO.getValue())){
                        if (item.isDiluyente()) {
                            noDiluyentes++;
// RN: Valida el volumen total contra el volumen del diluyente
                            String volumenDiferente = solUtils.validaVolTotalVolDiluyente(item.getDosis(), surtimientoExtendedSelected.getVolumenTotal());
                            if (volumenDiferente != null) {
                                Mensaje.showMessage(Constantes.MENSAJE_WARN, volumenDiferente, null);
                            }
                            if (surtimientoExtendedSelected.getIdContenedor() != null) {
                                EnvaseContenedor ec = envaseService.obtenerXidEnvase(surtimientoExtendedSelected.getIdContenedor());
                                if (ec != null) {
                                    switch (ec.getDescripcion()) {
                                        case "Cloruro sodio 0.9%":
                                            if (!item.getNombreCorto().contains("Cloruro sodio 0.9%")) {
                                                Mensaje.showMessage(Constantes.MENSAJE_WARN, "El contenedor seleccionado " + ec.getDescripcion() + ", difiere del diluyente prescrito " + item.getNombreCorto() + ".", null);
                                            }
                                            break;
                                        case "Glucosa 5%":
                                            if (!item.getNombreCorto().contains("Glucosa 5%")
                                                    && !item.getNombreCorto().contains("Dextrosa 5%")) {
                                                Mensaje.showMessage(Constantes.MENSAJE_WARN, "El contenedor seleccionado " + ec.getDescripcion() + ", difiere del diluyente prescrito " + item.getNombreCorto() + ".", null);
                                            }
                                            break;
                                        case "Dextrosa 5%":
                                            if (!item.getNombreCorto().contains("Glucosa 5%")
                                                    && !item.getNombreCorto().contains("Dextrosa 5%")) {
                                                Mensaje.showMessage(Constantes.MENSAJE_WARN, "El contenedor seleccionado " + ec.getDescripcion() + ", difiere del diluyente prescrito " + item.getNombreCorto() + ".", null);
                                            }
                                            break;
                                        case "Solución hartmann":
                                            if (!item.getNombreCorto().contains("Solución hartmann")) {
                                                Mensaje.showMessage(Constantes.MENSAJE_WARN, "El contenedor seleccionado " + ec.getDescripcion() + ", difiere del diluyente prescrito " + item.getNombreCorto() + ".", null);
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        } else {
                            noFarmacos++;
                        }
                    } else {
                        noMateriales++;
                    }
                }
                if (noFarmacosPermitidos > 0 && noFarmacos > noFarmacosPermitidos) {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, "La Mezcla debe contener " + noFarmacosPermitidos + " medicametos(s).", null);
                }
                if (noDiluyentesPermitidos > 0 && noDiluyentes > noDiluyentesPermitidos) {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, "La Mezcla debe contener " + noDiluyentesPermitidos + " diluyente(s).", null);
                }
                if (noMateriales > 1 ) {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, "Revise los materiales registrados, se detectan " + noMateriales + " materiales.", null);
                }

// RN: Valida duplicidad de tratamiento
                String tratamientoDuplicado = this.solUtils.validaTratamientoDuplicado(surtimientoExtendedSelected, surtIdInsumoLista, tipoInsumoValidaTratDuplicado, noHrsPrevValidaTratDuplicado, noHrsPostValidaTratDuplicado);
                if (tratamientoDuplicado != null && !tratamientoDuplicado.isEmpty()) {
                    String[] lineasTratamiento = tratamientoDuplicado.split(StringUtils.LF);
                    for (String s : lineasTratamiento) {
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, s, null);
                    }
                }

// RN: Valida estabilidades
                List<String> msjEstabilidades = this.solUtils.evaluaEstabilidadDiluyente(surtimientoInsumoExtendedList, viaAdministracion.getIdViaAdministracion());
                for (String item : msjEstabilidades) {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, item, null);
                }
                res = true;
            }
        }catch(Exception e){
            LOGGER.error("Error en el metodo validar Estabilidades :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
        }
        return res;
    }

    /**
     * REgistra en base de datos las mezclas prescritas desde la pantalla de prescripción múltiple
     */
    public void registrarOrdenesSolucion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.registrarOrdenesSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if (!permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (listaSolucionesExtend == null || listaSolucionesExtend.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha registrado por lo menos una solución", null);
            } else if (validaDiagnosticoProtocolo()) {
                String idPacienteServicio = null;
                String idPacienteUbicacion = null;

// RN: Las prescripciones multiples deben ser asignadas al mismo servicio

                Visita v = solUtils.recuperaVisita(pacienteExtended.getIdPaciente(), usuarioSelected.getIdUsuario());
                if (v == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al crear la visita.", null);
                    
                } else {
                    PacienteServicio pacSer = solUtils.recuperaPacienteServicio(v.getIdVisita(), surtimientoExtendedSelected.getIdEstructura(), usuarioSelected.getIdUsuario(), medico.getIdUsuario(), pacienteExtended.getIdPaciente());
                    if (pacSer == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al asignar servicio.", null);
                        
                    } else {
                        idPacienteServicio = pacSer.getIdPacienteServicio();
                        PacienteUbicacion pacUbi = solUtils.recuperaPacienteUbicacion(pacSer.getIdPacienteServicio(), usuarioSelected.getIdUsuario(), surtimientoExtendedSelected.getCama(), pacienteExtended.getIdPaciente());
                        if (pacUbi != null) {
                            idPacienteUbicacion = pacUbi.getIdPacienteUbicacion();
                        }
                        if (listaPrescripcionesMultiple == null || listaPrescripcionesMultiple.isEmpty()) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Lista de prescripciones por generar inválida.", null);
                            
                        } else {
                            for (Prescripcion p : listaPrescripcionesMultiple) {
                                p.setIdPacienteServicio(idPacienteServicio);
                                p.setIdPacienteUbicacion(idPacienteUbicacion);
                            }
                            status = prescripcionService.registrarOrdenesSoluciones(listaPrescripcionesMultiple, listaPrescripcionInsumoMultiple, surtimientoExtListMultiple, surtimientoInsumoExtListMultiple, listaSolucionesExtend, listaDiagnosticoPacienteMultiple);
                            if (status) {
                                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("cm.registro.exitoso"), "");
// TODO: enviar correo al correo genérico
                                
                            } else {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo registrarOrdenesSolucion :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * REgistra una orden de soluciones ligada a una prescripción y a un
     * surtimiento
     */
    public void registrarOrden() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.registrarOrden()");
        boolean status = Constantes.INACTIVO;
        boolean porAutorizar = Constantes.INACTIVO;
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if (!permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado insumos por prescribir.", null);

            } else if (validaDiagnosticoProtocolo()) {
// RN: Valida duplicidad de tratamiento
                List<String> surtIdInsumoLista = new ArrayList<>();
                for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                    surtIdInsumoLista.add(item.getIdInsumo());
                           Medicamento medicamentoAutorizar =  medicamentoService.obtener(new Medicamento(item.getIdInsumo()));
                           if(medicamentoAutorizar != null){
                               if(medicamentoAutorizar.isAutorizar()) {
                                   porAutorizar = Constantes.ACTIVO;
                                   break;
                            }
                        }   
                }
                this.solUtils.validaTratamientoDuplicado(surtimientoExtendedSelected, surtIdInsumoLista, tipoInsumoValidaTratDuplicado, noHrsPrevValidaTratDuplicado, noHrsPostValidaTratDuplicado);

                boolean existePresc = obtenerPrescripcion();
                generarListaPrescripcionInsumo(surtimientoInsumoExtendedList, prescripcionSelected, surtimientoExtendedSelected );
                generarSurtimiento();
                Date fechaSurt = new Date();
                for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientoInsumoExtendedList) {
                    surtimientoInsumo_Ext.setIdSurtimientoInsumo(Comunes.getUUID());
                    surtimientoInsumo_Ext.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                    surtimientoInsumo_Ext.setFechaProgramada(surtimientoExtendedSelected.getFechaProgramada());
                    surtimientoInsumo_Ext.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    surtimientoInsumo_Ext.setCantidadVale(0);
                    surtimientoInsumo_Ext.setInsertFecha(fechaSurt);
                    surtimientoInsumo_Ext.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    surtimientoInsumo_Ext.setIdEstatusMirth(1);
                }

                if (!existePresc) {
                    Visita v = solUtils.recuperaVisita(pacienteExtended.getIdPaciente(), usuarioSelected.getIdUsuario());
                    if (v == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Visita de paciente no encontrada. ", null);

                    } else if (v.getIdVisita() == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Visita de paciente no encontrado. ", null);

                    } else {
                        PacienteServicio pacSer = solUtils.recuperaPacienteServicio(v.getIdVisita(), surtimientoExtendedSelected.getIdEstructura(), usuarioSelected.getIdUsuario(), medico.getIdUsuario(), pacienteExtended.getIdPaciente());
                        if (pacSer == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Asignación de paciente a servicio no encontrada. ", null);
                            
                        } else {
                            prescripcionSelected.setIdPacienteServicio(pacSer.getIdPacienteServicio());
                            PacienteUbicacion pacUbi = solUtils.recuperaPacienteUbicacion(pacSer.getIdPacienteServicio(), usuarioSelected.getIdUsuario(), surtimientoExtendedSelected.getCama(), pacienteExtended.getIdPaciente());
                            if (pacUbi != null) {
                                surtimientoExtendedSelected.setIdCama(pacUbi.getIdCama());
                                surtimientoExtendedSelected.setCama(pacUbi.getIdCama());
                                prescripcionSelected.setIdPacienteUbicacion(pacUbi.getIdPacienteUbicacion());
                            }
                        }
                    }
                    
                } else {
                    PacienteUbicacion pacUbi = this.solUtils.obtenerUbicacionRegistrada(prescripcionSelected.getIdPacienteUbicacion());
                    if (pacUbi != null) {
                        surtimientoExtendedSelected.setIdCama(pacUbi.getIdCama());
                        surtimientoExtendedSelected.setCama(pacUbi.getIdCama());
                    }
                    PacienteServicio pacSer = this.solUtils.obtenerServicioRegistrada(prescripcionSelected.getIdPacienteServicio());
                    Visita v = null;
                    if (pacSer != null) {
                        v = this.solUtils.obtenerVisitaRegistrada(pacSer.getIdVisita());
                    }
                }

                prescripcionSelected.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());

// TODO: 01OCT2023-01 HRC - Buscar la asignación del almacen surtidor al servicio que gennera prescripción
                String idEstrucAlmac;
                if (idTipoSolucion.equalsIgnoreCase("e68ac222-0a63-11eb-a03a-000c29750049")) {
                    idEstrucAlmac = "c6365aac-5424-4632-9382-8e6c4a4bbe72";
                } else { // if (idTipoSolucion == 'e695144c-0a63-11eb-a03a-000c29750049'){
                    idEstrucAlmac = "e783588a-7076-4bc0-afd2-b1067cd8d38a";
                }
                surtimientoExtendedSelected.setIdEstructuraAlmacen(idEstrucAlmac);
                surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                surtimientoExtendedSelected.setUpdateFecha(fechaSurt);
                surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
//                        surtimientoExtendedSelected.setFechaParaEntregar(fechaEntrega);

                List<DiagnosticoPaciente> listaDiagnosticoPaciente = new ArrayList<>();
                if (listaDiagnosticos != null) {
                    for (Diagnostico diag : listaDiagnosticos) {
                        if (!existeEnListaDiagPac(listaDiagnosticoPaciente, diag.getIdDiagnostico())) {
                            DiagnosticoPaciente diagPac = new DiagnosticoPaciente();
                            diagPac.setIdDiagnosticoPaciente(Comunes.getUUID());
                            diagPac.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                            diagPac.setFechaDiagnostico(fechaSurt);
                            diagPac.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                            diagPac.setIdDiagnostico(diag.getIdDiagnostico());
                            diagPac.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                            diagPac.setInsertFecha(fechaSurt);
                            diagPac.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                            listaDiagnosticoPaciente.add(diagPac);
                        }
                    }
                }
                status = prescripcionService.registrarOrdenSolucion(existePresc, prescripcionSelected, listaPrescripcionInsumo, surtimientoExtendedSelected, surtimientoInsumoExtendedList, listaDiagnosticoPaciente, porAutorizar);
                if (status) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("cm.registro.exitoso"), "");
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
                }

            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo registrarOrden :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.registro.error"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public boolean existeEnListaDiag(List<Diagnostico> listaDiagnostico, String idDiagnostico) {
        boolean existe = false;
        if (listaDiagnostico != null) {
            for (Diagnostico diag : listaDiagnostico) {
                if (diag.getIdDiagnostico().equalsIgnoreCase(idDiagnostico)) {
                    existe = true;
                    break;
                }
            }
        }
        return existe;
    }

    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.autocompleteDiagnostico()");
        List<Diagnostico> diagnListFromDB;
        List<Diagnostico> diagnList = new ArrayList<>();
        try {
            diagnListFromDB = diagnosticoService.obtenerListaAutoComplete(cadena);
            for (Diagnostico diag : diagnListFromDB) {
                diagnList.add(diag);
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagnList;
    }

    public List<Diagnostico> autocompleteDiagnosticos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.autocompleteDiagnostico()");
        List<Diagnostico> diagnListFromDB;
        List<Diagnostico> diagnList = new ArrayList<>();
        try {
            diagnListFromDB = diagnosticoService.obtenerListaAutoComplete(cadena);
            for (Diagnostico diag : diagnListFromDB) {
                if (!existeEnListaDiag(listaDiagnosticos, diag.getIdDiagnostico())) {
                    diagnList.add(diag);
                }
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagnList;
    }

    private String obtenerClaveTipoSolucion(String idTipoSolucion) {
        String clave = "";
        for (TipoSolucion tipoSol : tipoSolucionList) {
            if (tipoSol.getIdTipoSolucion().equals(idTipoSolucion)) {
                clave = tipoSol.getClave();
                break;
            }
        }
        return clave;
    }

    public void obtenerProtocoloSeleccionado() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerProtocoloSeleccionado()");
        this.isOncologica = obtenerClaveTipoSolucion(idTipoSolucion).equalsIgnoreCase("ONC");
        if (!this.isOncologica) {
            this.claveProtocolo = null;
            this.diagnosticoSelected = null;

        } else {
//            boolean valid = false;
            try {
                Protocolo p = new Protocolo();
                p.setClaveProtocolo(this.claveProtocolo);
                if (this.claveProtocolo != null) {
                    p = protocoloService.obtener(p);

                    if (p != null) {
                        diagnosticoSelected = diagnosticoService.obtenerDiagnosticoPorIdDiag(p.getIdDiagnostico());
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Error al validar el protocolo {} ", ex.getMessage());
            }
        }
    }

    /**
     * Prellena protocolo y disgnóstico de protocolo con base en el tipo de
     * solución
     */
    public void seleccionaTiposolucion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.seleccionaTiposolucion()");
        this.claveProtocolo = null;
        this.diagnosticoSelected = null;
        if (this.idTipoSolucion != null && !this.idTipoSolucion.isEmpty()) {
            this.isOncologica = obtenerClaveTipoSolucion(idTipoSolucion).equalsIgnoreCase("ONC");
            if (this.isOncologica) {
                if (this.protocoloVacioDefault == 1) {
                    try {
                        Protocolo p = protocoloService.obtener(new Protocolo("NA"));
                        if (p != null) {
                            this.claveProtocolo = p.getClaveProtocolo();
                            this.diagnosticoSelected = diagnosticoService.obtenerDiagnosticoPorIdDiag(p.getIdDiagnostico());
                        }
                    } catch (Exception e) {
                        LOGGER.error("Error al seleccionar el protocolo predeterminado :: {}  ", e.getMessage());
                    }
                }
            }
//            obtenerListaHorarios(idTipoSolucion);
            estableceTiposSurtimiento();
        }
    }

    private void estableceTiposSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.estableceTiposSurtimiento()");
        try {
            listaTipoSurtimiento = new ArrayList<>();
            TipoSurtimiento tsTemp = new TipoSurtimiento();
            tsTemp.setClave(TipoSurtimiento_Enum.MATERIAL.getValue());
            TipoSurtimiento o = this.tipoSurtimientoService.obtener(tsTemp);
            if (o != null) {
                listaTipoSurtimiento.add(o.getIdTipoSurtimiento());
            }
            tsTemp.setClave(TipoSurtimiento_Enum.MEDICAMENTO.getValue());
            o = this.tipoSurtimientoService.obtener(tsTemp);
            if (o != null) {
                listaTipoSurtimiento.add(o.getIdTipoSurtimiento());
            }

            TipoSolucion tsoTemp = new TipoSolucion();
            tsoTemp.setIdTipoSolucion(this.idTipoSolucion);
            TipoSolucion tso = this.tipoSolucionService.obtener(tsoTemp);
            if (tso != null) {
                tsTemp.setClave(tso.getClave());
                o = this.tipoSurtimientoService.obtener(tsTemp);
                if (o != null) {
                    listaTipoSurtimiento.add(o.getIdTipoSurtimiento());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener los tipos de surtimiento :: {} ", e.getMessage());
        }
    }

    public boolean validaDiagnosticoProtocolo() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaDiagnosticoProtocolo()");
        boolean valid = false;
        this.isOncologica = obtenerClaveTipoSolucion(idTipoSolucion).equalsIgnoreCase("ONC");
        if (!this.isOncologica) {
            this.claveProtocolo = null;
            this.diagnosticoSelected = null;
            valid = true;

        } else {
            try {
                if (this.claveProtocolo == null) {
                    this.msjWarning = "Debe seleccionar un protocolo oncológico y diagnóstico asociado.";
                    this.msjWarning = this.msjWarning + "<br/>O en su defecto debe seleccionar: Protocolo: NA y Diagnóstico: No Definido.";
                    PrimeFaces.current().executeScript("PF('modalWarning').show();");
                } else {
                    Protocolo p = new Protocolo();
                    p.setClaveProtocolo(this.claveProtocolo);
                    p = protocoloService.obtener(p);
                    if (p != null) {
                        if (p.getIdDiagnostico() != null){
                            this.diagnosticoSelected = diagnosticoService.obtenerDiagnosticoPorIdDiag(p.getIdDiagnostico());
                            if (this.diagnosticoSelected != null) {
                                valid = true;
                            }
                        }
                    }
                    if (!valid) {
                        this.msjWarning = "El Diagnóstico <b>" + this.diagnosticoSelected.getDescripcion() + "</b> no coincide con el Protocolo oncológico.";
                        this.msjWarning = this.msjWarning + "<br/>O en su defecto debe seleccionar: Protocolo: NA y Diagnóstico: No Definido.";
                        PrimeFaces.current().executeScript("PF('modalWarning').show();");
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Error al validar el protocolo {} ", ex.getMessage());
                this.msjWarning = "Debe seleccionar un protocolo oncológico y diagnóstico asociado.";
                this.msjWarning = this.msjWarning + "<br/>O en su defecto debe seleccionar: Protocolo: NA y Diagnóstico: No Definido.";
                PrimeFaces.current().executeScript("PF('modalWarning').show();");
            }
        }
        return valid;
    }

    public void mostrarModalAgregarPaciente() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.mostrarModalAgregarPaciente()");
        boolean status = false;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage("Error", "Usuario inválido.", "");

            } else if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage("Error", "No tiene permisos de esta acción.", "");

            } else if (surtimientoExtendedSelected.getIdEstructura() == null
                    || surtimientoExtendedSelected.getIdEstructura().trim().isEmpty()) {
                Mensaje.showMessage("Error", RESOURCES.getString("prc.manual.servicio.req"), "");

            } else if (surtimientoExtendedSelected.getCama() == null
                    || surtimientoExtendedSelected.getCama().trim().isEmpty()) {
                Mensaje.showMessage("Error", RESOURCES.getString("prc.manual.cama.req"), "");

            } else {
                paciente = new Paciente();
                Turno item = listaTurnos.get(0);
                listaIdTurnos = new ArrayList<>();
                listaIdTurnos.add("" + item.getIdTurno());
                status = true;
            }

        } catch (Exception e) {
            LOGGER.error("Error al mostrar vista de agregar paciente :: {} ", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
   

    private List<TurnoMedico> obtenerTurnoGenericoPaciente(Paciente patient) {
        List<TurnoMedico> listaTemp = new ArrayList<>();
        if (listaTurnos != null && !listaTurnos.isEmpty()) {
            TurnoMedico tm;
            for (Turno item : listaTurnos) {
                tm = new TurnoMedico();
                tm.setIdTurnoMedico(Comunes.getUUID());
                tm.setIdTurno(item.getIdTurno());
                tm.setIdMedico(patient.getIdPaciente());
                listaTemp.add(tm);
            }
        }
        return listaTemp;
    }

    /**
     * Valida y registra un paciente
     */
    public void agregarPaciente() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.agregarPaciente()");
        String msjError = solUtils.validarDatosPaciente(paciente, surtimientoExtendedSelected.getIdEstructura(), surtimientoExtendedSelected.getCama());
        boolean res = false;
        if (msjError != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
            return;
        }
        if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage("Info", RESOURCES.getString("estr.err.servicioCama"), "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, false);
            return;
        }
        Paciente pacienteTemp = this.solUtils.obtenerDatosPaciente(paciente, usuarioSelected);
//        pacienteTemp.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
        pacienteTemp.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
        pacienteTemp.setIdEstructura(surtimientoExtendedSelected.getIdEstructura());
        PacienteDomicilio pacienteDomTem = this.solUtils.obtenerDatosPacienteDomicilioGenerico(pacienteTemp, usuarioSelected);
        PacienteResponsable pacienteRespTemp = new PacienteResponsable();
        List<TurnoMedico> listaTurnoTemp = obtenerTurnoGenericoPaciente(pacienteTemp);

        Date fechaAct = new Date();
        Visita v = solUtils.generaVisita(pacienteTemp.getIdPaciente(), usuarioSelected.getIdUsuario(), fechaAct);
        PacienteServicio pacSer = solUtils.generaPacienteServicio(v.getIdVisita(), surtimientoExtendedSelected.getIdEstructura(), usuarioSelected.getIdUsuario(), fechaAct);
        PacienteUbicacion pacUbi = solUtils.generaPacienteUbicacion(pacSer.getIdPacienteServicio(), surtimientoExtendedSelected.getCama(), usuarioSelected.getIdUsuario(), fechaAct);

        try {
            res = pacienteService.insertarPacienteVisitaServicioUbicacion(
                    pacienteTemp, pacienteDomTem, pacienteRespTemp, listaTurnoTemp, v, pacSer, pacUbi);
        } catch (Exception ex) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.registro"), "");
            LOGGER.error(ex.getMessage());
        }

        if (res) {
            Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.paciente"), "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, false);
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.visita"), "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
        }
    }

    public void mostrarModalAgregarMedico() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.mostrarModalAgregarMedico()");
        boolean status = false;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage("Error", "Usuario inválido.", "");

            } else if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage("Error", "No tiene permisos de esta acción.", "");

            } else if (surtimientoExtendedSelected.getIdEstructura() == null
                    || surtimientoExtendedSelected.getIdEstructura().trim().isEmpty()) {
                Mensaje.showMessage("Error", RESOURCES.getString("prc.manual.servicio.req"), "");

            } else if (surtimientoExtendedSelected.getCama() == null
                    || surtimientoExtendedSelected.getCama().trim().isEmpty()) {
                Mensaje.showMessage("Error", RESOURCES.getString("prc.manual.cama.req"), "");

            } else {
                medicoExtended = new Usuario_Extended();
                for (Turno item : listaTurnos) {
                    listaIdTurnos.add("" + item.getIdTurno());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al mostrar vista de agregar médico :: {} ", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private String validarDatosMedico() {
        String resul = null;
        if (medicoExtended == null) {
            resul = RESOURCES.getString("prc.manual.req.");

        } else if (medicoExtended.getNombre().isEmpty()) {
            resul = RESOURCES.getString("prc.manual.nombre.medico");

        } else if (medicoExtended.getApellidoPaterno().isEmpty()) {
            resul = RESOURCES.getString("prc.manual.app.medico");

        } else if (medicoExtended.getCedProfesional().isEmpty()) {
            resul = RESOURCES.getString("prc.manual.cedula");

        } else if (surtimientoExtendedSelected == null) {
            resul = RESOURCES.getString("prc.manual.servicio.req");

        } else if (surtimientoExtendedSelected.getIdEstructura() == null) {
            resul = RESOURCES.getString("prc.manual.servicio.req");

        } else if (surtimientoExtendedSelected.getIdEstructura().trim().isEmpty()) {
            resul = RESOURCES.getString("prc.manual.servicio.req");

        } else {
            Usuario medico = new Usuario();
            medico.setNombre(medicoExtended.getNombre());
            medico.setApellidoPaterno(medicoExtended.getApellidoPaterno());
            medico.setApellidoMaterno(medicoExtended.getApellidoMaterno());
            medico.setCedProfesional(medicoExtended.getCedProfesional());
            medico.setCedEspecialidad(medicoExtended.getCedEspecialidad());

            try {
                Usuario u = usuarioService.obtener(medico);
                if (u != null) {
                    resul = RESOURCES.getString("prc.manual.medico.err.duplicado");
                }
            } catch (Exception e) {
                LOGGER.error("Error al buscar médico registrado: {}", e.getMessage());
            }

        }
        return resul;
    }

    public void agregarMedicos() {
        boolean status = false;
        String resp = validarDatosMedico();
        if (resp != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, resp, "");
            PrimeFaces.current().ajax().addCallbackParam("errorModalMedicos", true);
            return;
        } else {
            Usuario medicoTemp = new Usuario();
            medicoTemp.setIdUsuario(Comunes.getUUID());
            medicoTemp.setNombre(medicoExtended.getNombre());
            medicoTemp.setApellidoPaterno(medicoExtended.getApellidoPaterno());
            medicoTemp.setApellidoMaterno(medicoExtended.getApellidoMaterno());
            medicoTemp.setActivo(Constantes.ACTIVO);
            medicoTemp.setUsuarioBloqueado(Constantes.INACTIVO);
            medicoTemp.setFechaVigencia(new Date());
            // todo: probar el id del médico
            medicoTemp.setIdEstructura(surtimientoExtendedSelected.getIdEstructura());
            medicoTemp.setIdTipoUsuario(TipoUsuario_Enum.MEDICO.getValue());
            medicoTemp.setCedProfesional(medicoExtended.getCedProfesional());
            medicoTemp.setCedEspecialidad(medicoExtended.getCedEspecialidad());
            medicoTemp.setInsertFecha(new Date());
            medicoTemp.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            List<TurnoMedico> listaTemp = new ArrayList<>();
            if (listaTurnos != null && !listaTurnos.isEmpty()) {
                TurnoMedico tm;
                for (Turno item : listaTurnos) {
                    tm = new TurnoMedico();
                    tm.setIdTurnoMedico(Comunes.getUUID());
                    tm.setIdTurno(item.getIdTurno());
                    tm.setIdMedico(medicoTemp.getIdUsuario());
                    listaTemp.add(tm);
                }
            }

            try {
                status = usuarioService.insertarMedicoYTurno(medicoTemp, listaTemp);
            } catch (Exception ex) {
                status = false;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.registro"), "");
                LOGGER.error(RESOURCES.getString("prc.manual.error.registro") + ": {}", ex.getMessage());
            }
            if (status) {
                Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.medico"), "");
                PrimeFaces.current().ajax().addCallbackParam("errorModalMedicos", false);
            }
        }
    }

    
    public void validaModificarOrden() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaModificarOrden()");

        boolean status = Constantes.INACTIVO;
        boolean porAutorizar = Constantes.INACTIVO;
        String val = this.solUtils.validaOrden(
                prescripcionSelected, surtimientoExtendedSelected, medico, pacienteExtended,
                listaDiagnosticos, viaAdministracion, isOncologica, claveProtocolo,
                diagnosticoSelected, surtimientoInsumoExtendedList, idTipoSolucion);
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if (!permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (val != null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, val, null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de surtimiento incorrecto. ", null);

            } else if (surtimientoExtendedSelected.getFolioPrescripcion() == null || surtimientoExtendedSelected.getFolioPrescripcion().trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado los insumos por registrar ", null);

            } else {

                Prescripcion presc = new Prescripcion();
                presc.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
                prescripcionSelected = prescripcionService.obtener(presc);

// RN Validar existencia de prescripcionn
                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

                } else if (prescripcionSelected.getIdEstatusPrescripcion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

                } else if (Objects.equals(prescripcionSelected.getIdEstatusPrescripcion(), EstatusPrescripcion_Enum.CANCELADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Prescripción inválida. ", null);

                } else {
                                        
                    status = validarEstabilidades();
                                    
                }
                
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo autorizarMezcla :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al autorizar la prescripción", "");
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);    
    }
    
    /**
     * 
     */
    public void modificarOrden() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.modificarOrden()");

        boolean status = Constantes.INACTIVO;
        boolean porAutorizar = Constantes.INACTIVO;
        String val = this.solUtils.validaOrden(
                prescripcionSelected, surtimientoExtendedSelected, medico, pacienteExtended,
                listaDiagnosticos, viaAdministracion, isOncologica, claveProtocolo,
                diagnosticoSelected, surtimientoInsumoExtendedList, idTipoSolucion);
        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if (!permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (val != null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, val, null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de surtimiento incorrecto. ", null);

            } else if (surtimientoExtendedSelected.getFolioPrescripcion() == null || surtimientoExtendedSelected.getFolioPrescripcion().trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado los insumos por registrar ", null);

            } else {

                Prescripcion presc = new Prescripcion();
                presc.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
                prescripcionSelected = prescripcionService.obtener(presc);

// RN Validar existencia de prescripcionn
                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

                } else if (prescripcionSelected.getIdEstatusPrescripcion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

                } else if (Objects.equals(prescripcionSelected.getIdEstatusPrescripcion(), EstatusPrescripcion_Enum.CANCELADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Prescripción inválida. ", null);

                } else {

                    if (surtimientoInsumoExtendedListAnterior == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamentos Prescritos inválidos. ", null);

                    } else {

                        
                        Date fechaSurt = new Date();
                        prescripcionSelected.setUpdateFecha(fechaSurt);
                        prescripcionSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                        List<PrescripcionInsumo> listaPrescripcionInsumoAnt = new ArrayList<>();
                        List<SurtimientoInsumo> listaSurtimientoInsumoAnt = new ArrayList<>();
                        List<PrescripcionInsumo> listaPrescripcionInsumoNue = new ArrayList<>();
                        List<SurtimientoInsumo> listaSurtimientoInsumoNue = new ArrayList<>();
                        PrescripcionInsumo presInsumoAnt;
                        if (surtimientoInsumoExtendedList != null) {
                            for (SurtimientoInsumo_Extend si : surtimientoInsumoExtendedList) {
                                if (si.getIdSurtimientoInsumo() != null) {
                                    presInsumoAnt = new PrescripcionInsumo();
                                    presInsumoAnt.setIdPrescripcionInsumo(si.getIdPrescripcionInsumo());
                                    presInsumoAnt.setIdPrescripcion(si.getIdPrescripcion());
                                    presInsumoAnt.setIdInsumo(si.getIdInsumo());
                                    presInsumoAnt.setFechaInicio(si.getFechaInicio());
                                    presInsumoAnt.setDosis(si.getDosis());
                                    presInsumoAnt.setFrecuencia(si.getFrecuencia());
                                    presInsumoAnt.setDuracion(si.getDuracion());
                                    presInsumoAnt.setIdTipoIngrediente(si.getIdTipoIngrediente());
                                    presInsumoAnt.setVelocidad(si.getVelocidad());
                                    presInsumoAnt.setRitmo(si.getRitmo());
                                    presInsumoAnt.setPerfusionContinua(si.getPerfusionContinua());
                                    presInsumoAnt.setIdUnidadConcentracion(si.getIdUnidadConcentracion());
                                    presInsumoAnt.setIdEstatusPrescripcion(si.getIdEstatusPrescripcion());
                                    boolean encontrado = false;
                                    for (SurtimientoInsumo_Extend sinew : surtimientoInsumoExtendedList) {
                                        if (si.getIdSurtimientoInsumo() != null
                                                && si.getIdSurtimientoInsumo().equalsIgnoreCase(sinew.getIdSurtimientoInsumo())) {
                                            encontrado = true;
                                            break;
                                        }
                                    }
                                    if (!encontrado) {
                                        presInsumoAnt.setIdEstatusPrescripcion(EstatusSurtimiento_Enum.CANCELADO.getValue());
                                        si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                                    }
                                    presInsumoAnt.setUpdateFecha(new java.util.Date());
                                    presInsumoAnt.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                                    listaPrescripcionInsumoAnt.add(presInsumoAnt);
                                    si.setUpdateFecha(new java.util.Date());
                                    si.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                                    listaSurtimientoInsumoAnt.add(si);
                                    Medicamento medicamentoAutorizar =  medicamentoService.obtener(new Medicamento(si.getIdInsumo()));
                                    if(medicamentoAutorizar != null){
                                        if(medicamentoAutorizar.isAutorizar()) {
                                            porAutorizar = Constantes.ACTIVO;                                    
                                        }
                                    }
                                } else {
                                    presInsumoAnt = new PrescripcionInsumo();
                                    presInsumoAnt.setIdPrescripcionInsumo(Comunes.getUUID());
                                    presInsumoAnt.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                                    presInsumoAnt.setIdInsumo(si.getIdInsumo());
                                    presInsumoAnt.setFechaInicio(si.getFechaInicio());
                                    presInsumoAnt.setDosis(si.getDosis());
                                    presInsumoAnt.setFrecuencia(si.getFrecuencia());
                                    presInsumoAnt.setDuracion(si.getDuracion());
                                    presInsumoAnt.setInsertFecha(new java.util.Date());
                                    presInsumoAnt.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    if (si.getIdTipoIngrediente()!= null) {
                                        presInsumoAnt.setIdTipoIngrediente(si.getIdTipoIngrediente());
                                    }
                                    presInsumoAnt.setVelocidad(si.getVelocidad());
                                    presInsumoAnt.setRitmo(si.getRitmo());
                                    if (si.getPerfusionContinua()!= null){
                                        presInsumoAnt.setPerfusionContinua(si.getPerfusionContinua());
                                    }
                                    if (si.getIdUnidadConcentracion()!= null) {
                                        presInsumoAnt.setIdUnidadConcentracion(si.getIdUnidadConcentracion());
                                    }
                                    presInsumoAnt.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                                    presInsumoAnt.setInsertFecha(new java.util.Date());
                                    presInsumoAnt.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    listaPrescripcionInsumoNue.add(presInsumoAnt);

                                    si.setIdSurtimientoInsumo(Comunes.getUUID());
                                    si.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                                    si.setIdPrescripcionInsumo(presInsumoAnt.getIdPrescripcionInsumo());
                                    si.setFechaProgramada(surtimientoExtendedSelected.getFechaProgramada());
                                    si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                    si.setInsertFecha(new java.util.Date());
                                    si.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    si.setCantidadVale(0);
                                    si.setIdEstatusMirth(1);
                                    listaSurtimientoInsumoNue.add(si);
                                    Medicamento medicamentoAutorizar =  medicamentoService.obtener(new Medicamento(si.getIdInsumo()));
                                    if(medicamentoAutorizar != null){
                                        if(medicamentoAutorizar.isAutorizar()) {
                                            porAutorizar = Constantes.ACTIVO;                                    
                                        }
                                    }
                                }
                            }
                        }
                        
                        PrescripcionInsumo presInsumoEli;
                        List<PrescripcionInsumo> listaPrescripcionInsumoEli = new ArrayList<>();
                        List<SurtimientoInsumo> listaSurtimientoInsumoEli = new ArrayList<>();
                        if (surtimientoInsumoExtendedListEliminar != null) {
                            for (SurtimientoInsumo_Extend si : surtimientoInsumoExtendedListEliminar) {
//                                if (si.getIdSurtimientoInsumo() == null ) {
                                    presInsumoEli = new PrescripcionInsumo();
                                    presInsumoEli.setIdPrescripcionInsumo(si.getIdPrescripcionInsumo());
                                    presInsumoEli.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                                    presInsumoEli.setIdInsumo(si.getIdInsumo());
                                    presInsumoEli.setFechaInicio(si.getFechaInicio());
                                    presInsumoEli.setDosis(si.getDosis());
                                    presInsumoEli.setFrecuencia(si.getFrecuencia());
                                    presInsumoEli.setDuracion(si.getDuracion());
                                    if (si.getIdTipoIngrediente()!= null) {
                                        presInsumoEli.setIdTipoIngrediente(si.getIdTipoIngrediente());
                                    }
                                    presInsumoEli.setVelocidad(si.getVelocidad());
                                    presInsumoEli.setRitmo(si.getRitmo());
                                    if (si.getPerfusionContinua()!= null){
                                        presInsumoEli.setPerfusionContinua(si.getPerfusionContinua());
                                    }
                                    if (si.getIdUnidadConcentracion()!= null) {
                                        presInsumoEli.setIdUnidadConcentracion(si.getIdUnidadConcentracion());
                                    }
                                    presInsumoEli.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                                    presInsumoEli.setInsertFecha(new java.util.Date());
                                    presInsumoEli.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    listaPrescripcionInsumoEli.add(presInsumoEli);

                                    si.setIdSurtimientoInsumo(si.getIdSurtimientoInsumo());
                                    si.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                                    si.setIdPrescripcionInsumo(presInsumoEli.getIdPrescripcionInsumo());
                                    si.setFechaProgramada(surtimientoExtendedSelected.getFechaProgramada());
                                    si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                    si.setInsertFecha(new java.util.Date());
                                    si.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    si.setCantidadVale(0);
                                    si.setIdEstatusMirth(1);
                                    listaSurtimientoInsumoEli.add(si);

//                                }
                            }
                        }

                        List<String> surtIdInsumoLista = new ArrayList<>();
                        for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                            surtIdInsumoLista.add(item.getIdInsumo());
                        }
// RN: Valida duplicidad de tratamiento
                        String tratDuplicado = this.solUtils.validaTratamientoDuplicado(surtimientoExtendedSelected, surtIdInsumoLista, tipoInsumoValidaTratDuplicado, noHrsPrevValidaTratDuplicado, noHrsPostValidaTratDuplicado);
                        if (tratDuplicado != null && !tratDuplicado.isEmpty()){
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, tratDuplicado, null);
                        }
                        surtimientoExtendedSelected.setUpdateFecha(fechaSurt);
                        surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

// En caso de ser una modificación de una captura de prescripción por parte del químico, se actualizan datos de paciente
// Se deben crear nuevos registros si es nuevo paciente y o servicio
                        Visita v = solUtils.recuperaVisita(pacienteExtended.getIdPaciente(), usuarioSelected.getIdUsuario());
                        PacienteServicio pacSer = solUtils.recuperaPacienteServicio(v.getIdVisita(), surtimientoExtendedSelected.getIdEstructura(), usuarioSelected.getIdUsuario(), medico.getIdUsuario(), pacienteExtended.getIdPaciente());
                        PacienteUbicacion pacUbi = solUtils.recuperaPacienteUbicacion(ps.getIdPacienteServicio(), usuarioSelected.getIdUsuario(), surtimientoExtendedSelected.getCama(), pacienteExtended.getIdPaciente());

                        prescripcionSelected.setIdPacienteServicio(pacSer.getIdPacienteServicio());
                        prescripcionSelected.setIdPacienteUbicacion(pacUbi.getIdPacienteUbicacion());
                        prescripcionSelected.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());
                        //TODO GCR.- Revisar por que no debe de sel la estructura del usuario
                        surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                        surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        surtimientoExtendedSelected.setUpdateFecha(fechaSurt);
                        surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                        if (listaDiagnosticos == null) {
                            listaDiagnosticos = new ArrayList<>();
                        }
                        List<DiagnosticoPaciente> listaDiagnosticoPaciente = new ArrayList<>();
                        for (Diagnostico diag : listaDiagnosticos) {
                            if (!existeEnListaDiagPac(listaDiagnosticoPaciente, diag.getIdDiagnostico())) {
                                DiagnosticoPaciente diagPac = new DiagnosticoPaciente();
                                diagPac.setIdDiagnosticoPaciente(Comunes.getUUID());
                                diagPac.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                                diagPac.setFechaDiagnostico(fechaSurt);
                                diagPac.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                                diagPac.setIdDiagnostico(diag.getIdDiagnostico());
                                diagPac.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                                diagPac.setInsertFecha(fechaSurt);
                                diagPac.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                listaDiagnosticoPaciente.add(diagPac);
                            }
                        }

                        String idsolucion = null;
                        String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                        Solucion s = solUtils.obtenerSolucion(idsolucion, idSurtimiento);
// RN debe haber una solucion
                        if (s == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida ", null);

                        } else {
                            s.setInstruccionesPreparacion(surtimientoExtendedSelected.getInstruccionPreparacion());
                            s.setNoAgitar((surtimientoExtendedSelected.isNoAgitar()) ? Constantes.ES_ACTIVO : Constantes.ES_INACTIVO);
                            s.setProteccionTemp((surtimientoExtendedSelected.isTempAmbiente()) ? Constantes.ES_ACTIVO : Constantes.ES_INACTIVO);
                            s.setProteccionTempRefrig((surtimientoExtendedSelected.isTempRefrigeracion()) ? Constantes.ES_ACTIVO : Constantes.ES_INACTIVO);
                            s.setProteccionLuz((surtimientoExtendedSelected.isProteccionLuz()) ? Constantes.ES_ACTIVO : Constantes.ES_INACTIVO);
                            s.setObservaciones(surtimientoExtendedSelected.getObservaciones());
                            s.setUpdateFecha(new java.util.Date());
                            s.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                            s.setIdUsuarioValPrescr(usuarioSelected.getIdUsuario());
                            s.setFechaValPrescr(new java.util.Date());
                            s.setEdadPaciente(surtimientoExtendedSelected.getEdadPaciente());
                            s.setPesoPaciente(surtimientoExtendedSelected.getPesoPaciente());
                            s.setTallaPaciente(surtimientoExtendedSelected.getTallaPaciente());
                            s.setAreaCorporal(surtimientoExtendedSelected.getAreaCorporal());
                            s.setVolumen(surtimientoExtendedSelected.getVolumenTotal());
                            s.setVolumenFinal((surtimientoExtendedSelected.getVolumenTotal() != null) ? surtimientoExtendedSelected.getVolumenTotal().toString() : "0");
                            s.setVelocidad(surtimientoExtendedSelected.getVelocidad());                           
                            s.setPerfusionContinua((surtimientoExtendedSelected.isPerfusionContinua()) ? Constantes.ES_ACTIVO : Constantes.ES_INACTIVO);                         
                            s.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());
                            s.setIdEnvaseContenedor(surtimientoExtendedSelected.getIdContenedor());
                            if (surtimientoExtendedSelected.getHorasInfusion() != null && !surtimientoExtendedSelected.getHorasInfusion().isEmpty()) {
                                String cadenaHora[] = surtimientoExtendedSelected.getHorasInfusion().split(":");
                                Integer hora = Integer.valueOf(cadenaHora[0]);
                                Integer minut = Integer.valueOf(cadenaHora[1]);
                                //Calendar cal = Calendar.getInstance();
                                //cal.setTime(surtimientoExtendedSelected.getHorasInfusion());
                                //Integer hora = cal.get(Calendar.HOUR);
                                //Integer minut = cal.get(Calendar.MINUTE);
                                Integer minutosTotales = (hora * 60) + minut;
                                surtimientoExtendedSelected.setMinutosInfusion(minutosTotales);
                                s.setMinutosInfusion(minutosTotales);
                                if (this.surtimientoExtendedSelected.isPerfusionContinua()) {
                                    if (this.surtimientoExtendedSelected.getVolumenTotal() != null) {
                                        if (this.surtimientoExtendedSelected.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
                                                BigDecimal result = (this.surtimientoExtendedSelected.getVolumenTotal().multiply(
                                                        new BigDecimal(60))).divide(new BigDecimal(minutosTotales), 3, RoundingMode.HALF_UP);
                                                s.setVelocidad(result.doubleValue());
                                            }                           
                                    }
                                }
                            }
                            
                            if (validaPrescripcion) {
                                s.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue());
                            } else {
                                if(porAutorizar) {
                                    s.setIdEstatusSolucion(EstatusSolucion_Enum.POR_AUTORIZAR.getValue());
                                } else {
                                    s.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                                }
                            }

                            status = prescripcionService.modificarOrdenSolucion(
                                    prescripcionSelected,
                                     listaPrescripcionInsumoNue,
                                     listaPrescripcionInsumoAnt,
                                     listaPrescripcionInsumoEli,
                                     surtimientoExtendedSelected,
                                     listaSurtimientoInsumoNue,
                                     listaSurtimientoInsumoAnt,
                                     listaSurtimientoInsumoEli,
                                     listaDiagnosticoPaciente,
                                     s);

                            if (status) {
                                if (validaPrescripcion) {
                                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Se ha valiadado la prescripción de solución. ", null);
                                } else {
                                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("cm.edicion.exitoso"), null);
                                }
                                
                            } else {
                                if (validaPrescripcion) {
                                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Error al valiadar la prescripción de solución. ", null);
                                } else {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.edicion.error"), "");
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo modificarOrden :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.edicion.error"), "");
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    public void autorizarPrescripcion() throws Exception {
        boolean status = Constantes.INACTIVO;
        try {
            //String idSolucion = null;
             LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.autorizarPrescripcion()");
            String val = this.solUtils.validaOrden(
                    prescripcionSelected, surtimientoExtendedSelected, medico, pacienteExtended
                    , listaDiagnosticos, viaAdministracion, isOncologica, claveProtocolo
                    , diagnosticoSelected, surtimientoInsumoExtendedList, idTipoSolucion);
            
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if (!permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if(!permiso.isPuedeAutorizar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para autorizar ", null);

            } else if(this.comentarioAutoriza == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Comentarios para autorizar es requerido ", null);
                
            } else if (val != null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, val, null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado los insumos por registrar ", null);

            } else {
                //TODO GCR.- Checar por que se vuelve a obtener la prescripcion
                Prescripcion presc = new Prescripcion();
                presc.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
                prescripcionSelected = prescripcionService.obtener(presc);

// RN Validar existencia de prescripcionn
                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

                } else if (prescripcionSelected.getIdEstatusPrescripcion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida. ", null);

                } else if (Objects.equals(prescripcionSelected.getIdEstatusPrescripcion(), EstatusPrescripcion_Enum.CANCELADA.getValue())
                        || Objects.equals(prescripcionSelected.getIdEstatusPrescripcion(), EstatusPrescripcion_Enum.FINALIZADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Prescripción inválida. ", null);

                } else {
                    if (surtimientoInsumoExtendedListAnterior == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamentos Prescritos inválidos. ", null);

                    } else {

                        Date fechaSurt = new Date();
                        prescripcionSelected.setUpdateFecha(fechaSurt);
                        prescripcionSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                        List<PrescripcionInsumo> listaPrescripcionInsumoAnt = new ArrayList<>();
                        List<SurtimientoInsumo> listaSurtimientoInsumoAnt = new ArrayList<>();
                        PrescripcionInsumo presInsumoAnt;
                        for (SurtimientoInsumo_Extend si : surtimientoInsumoExtendedListAnterior) {
                            presInsumoAnt = new PrescripcionInsumo();
                            presInsumoAnt.setIdPrescripcionInsumo(si.getIdPrescripcionInsumo());
                            presInsumoAnt.setIdPrescripcion(si.getIdPrescripcion());
                            presInsumoAnt.setIdInsumo(si.getIdInsumo());
                            presInsumoAnt.setFechaInicio(si.getFechaInicio());
                            presInsumoAnt.setDosis(si.getDosis());
                            presInsumoAnt.setFrecuencia(si.getFrecuencia());
                            presInsumoAnt.setDuracion(si.getDuracion());
                            presInsumoAnt.setIdTipoIngrediente(si.getIdTipoIngrediente());
                            presInsumoAnt.setVelocidad(si.getVelocidad());
                            presInsumoAnt.setRitmo(si.getRitmo());
                            presInsumoAnt.setPerfusionContinua(si.getPerfusionContinua());
                            presInsumoAnt.setIdUnidadConcentracion(si.getIdUnidadConcentracion());
                            presInsumoAnt.setIdEstatusPrescripcion(si.getIdEstatusPrescripcion());
                            boolean encontrado = false;
                            for (SurtimientoInsumo_Extend sinew : surtimientoInsumoExtendedList) {
                                if (si.getIdSurtimientoInsumo().equalsIgnoreCase(sinew.getIdSurtimientoInsumo())) {
                                    encontrado = true;
                                    break;
                                }
                            }
                            if (!encontrado) {
                                presInsumoAnt.setIdEstatusPrescripcion(EstatusSurtimiento_Enum.CANCELADO.getValue());
                                si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                            }
                            presInsumoAnt.setUpdateFecha(new java.util.Date());
                            presInsumoAnt.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                            
                            listaPrescripcionInsumoAnt.add(presInsumoAnt);
                            si.setUpdateFecha(new java.util.Date());
                            si.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                            listaSurtimientoInsumoAnt.add(si);
                        }

                        List<PrescripcionInsumo> listaPrescripcionInsumoNue = new ArrayList<>();
                        List<SurtimientoInsumo> listaSurtimientoInsumoNue = new ArrayList<>();
                        for (SurtimientoInsumo_Extend si : surtimientoInsumoExtendedList) {
                            if (si.getIdSurtimientoInsumo() == null ) {
                                presInsumoAnt = new PrescripcionInsumo();
                                presInsumoAnt.setIdPrescripcionInsumo(Comunes.getUUID());
                                presInsumoAnt.setIdPrescripcion(prescripcionSelected.getIdPrescripcion());
                                presInsumoAnt.setIdInsumo(si.getIdInsumo());
                                presInsumoAnt.setFechaInicio(si.getFechaInicio() != null ? si.getFechaInicio() : prescripcionSelected.getFechaPrescripcion());
                                presInsumoAnt.setDosis(si.getDosis());
                                presInsumoAnt.setFrecuencia(si.getFrecuencia());
                                presInsumoAnt.setDuracion(si.getDuracion());                                
                                if (si.getIdTipoIngrediente()!= null) {
                                    presInsumoAnt.setIdTipoIngrediente(si.getIdTipoIngrediente());
                                }
                                presInsumoAnt.setVelocidad(si.getVelocidad());
                                presInsumoAnt.setRitmo(si.getRitmo());
                                if (si.getPerfusionContinua()!= null){
                                    presInsumoAnt.setPerfusionContinua(si.getPerfusionContinua());
                                }
                                if (si.getIdUnidadConcentracion()!= null) {
                                    presInsumoAnt.setIdUnidadConcentracion(si.getIdUnidadConcentracion());
                                }
                                presInsumoAnt.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                                presInsumoAnt.setInsertFecha(new java.util.Date());
                                presInsumoAnt.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                listaPrescripcionInsumoNue.add(presInsumoAnt);
                                
                                si.setIdSurtimientoInsumo(Comunes.getUUID());
                                si.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                                si.setIdPrescripcionInsumo(presInsumoAnt.getIdPrescripcionInsumo());
                                si.setFechaProgramada(surtimientoExtendedSelected.getFechaProgramada());
                                si.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                si.setInsertFecha(new java.util.Date());
                                si.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                si.setCantidadVale(0);
                                si.setIdEstatusMirth(1);
                                listaSurtimientoInsumoNue.add(si);
                            }
                        }
                        
                        List<String> surtIdInsumoLista = new ArrayList<>();
                        for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                            surtIdInsumoLista.add(item.getIdInsumo());
                        }
// RN: Valida duplicidad de tratamiento
                        String tratDuplicado = this.solUtils.validaTratamientoDuplicado(surtimientoExtendedSelected, surtIdInsumoLista, tipoInsumoValidaTratDuplicado, noHrsPrevValidaTratDuplicado, noHrsPostValidaTratDuplicado);
                        if (tratDuplicado != null && !tratDuplicado.isEmpty()){
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, tratDuplicado, null);
                        }
                        surtimientoExtendedSelected.setUpdateFecha(fechaSurt);
                        surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                        surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        
                        String idsolucion = null;
                        String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                        Solucion s = solUtils.obtenerSolucion(idsolucion, idSurtimiento);
// RN debe haber una solucion
                        if (s == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida ", null);

                        } else {
                            s.setIdUsuarioAutoriza(usuarioSelected.getIdUsuario());
                            s.setFechaAutoriza(new java.util.Date());
                            s.setComentarioAutoriza(comentarioAutoriza);
                            s.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                            s.setUpdateFecha(new Date());
                            s.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                            
                             status = prescripcionService.autorizarOrdenSolucion(prescripcionSelected, listaPrescripcionInsumoNue, listaPrescripcionInsumoAnt, surtimientoExtendedSelected, listaSurtimientoInsumoNue, listaSurtimientoInsumoAnt, s, agruparXPrescripcionAutorizar);

                            if (status) {
                                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("cm.edicion.exitoso"), null);
                                obtenerSurtimientos();
                                this.solucion = new Solucion();
                            } else {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("cm.edicion.error"), "");
                            }
                        }
                    }    
                }                               
            }                    
        } catch (Exception e) {
            LOGGER.error("Error en el metodo autorizarMezcla :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al autorizar la prescripción", "");
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);    
    }

    public void noAutorizarPrescripcion() throws Exception {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.noAutorizarPrescripcion()");
        boolean status = false;
        String idSolucion = null;
        if (this.motivoNoAutoriza == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Motivo de NO Autorización requerido.", null);

        } else if (this.surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);

        } else if (this.surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);

        } else if (this.surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción de solución inválida.", null);

        } else {
            String idPrescripcion = this.surtimientoExtendedSelected.getIdPrescripcion();
            try {
                Surtimiento surtimiento = new Surtimiento();
                surtimiento.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                surtimiento.setUpdateFecha(new Date());
                surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                surtimiento.setIdPrescripcion(idPrescripcion);

                Solucion o = this.solUtils.obtenerSolucion(idSolucion, surtimientoExtendedSelected.getIdSurtimiento()); 
                if (o != null) {
                    Solucion solucion = new Solucion();
                    solucion.setIdSolucion(o.getIdSolucion());
                    solucion.setUpdateFecha(new java.util.Date());
                    solucion.setFechaValPrescr(new java.util.Date());
                    solucion.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue());
                    solucion.setIdUsuarioNoAutoriza(usuarioSelected.getIdUsuario());
                    solucion.setMotivoNoAutoriza(motivoNoAutoriza);
                    solucion.setComentariosRechazo(motivoNoAutoriza);
                    status = solucionService.rechazarMezcla(solucion, surtimiento, surtimientoInsumoExtendedList, agruparXPrescripcionAutorizar);
                }
                
                if (status) {
                    Prescripcion p = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
                    Usuario u = null;
                    if (p != null) {
                        String IdUsuario = p.getIdMedico();
                        u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                        if (u != null) {
                            String asunto = "Prescripción de mezcla " + surtimientoExtendedSelected.getFolio() + " no autorizada. ";
                            String msj = motivoNoAutoriza;
                            Usuario usuarioAutorizador = usuarioService.obtener(usuarioSelected);
                            if (usuarioAutorizador != null){
                                msj = msj + "\n\n Rechazado por: " + usuarioAutorizador.getNombre() + " " + usuarioAutorizador.getApellidoPaterno() + " " + usuarioAutorizador.getApellidoMaterno();
                            }

                            this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                        }
                    }
                obtenerSurtimientos();
                this.solucion = new Solucion();
                }                   

            } catch (Exception e) {
                LOGGER.error("Error en el metodo noAutorizarMezcla :: {}", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al no autorizar la prescripción", "");
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
        
    }
    
    public void seleccionaCama() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.seleccionaCama()");
        try {
            surtimientoExtendedSelected.setCama(surtimientoExtendedSelected.getCama());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     *
     */
    public void obtenerSuperficieCorporal() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerSuperficieCorporal()");
        Double peso = this.surtimientoExtendedSelected.getPesoPaciente();
        Double altura = this.surtimientoExtendedSelected.getTallaPaciente();
        if (peso > 0 && altura > 0) {
            Double supCorp = this.solUtils.obtenerSuperficieCorporal(peso, altura);
            this.surtimientoExtendedSelected.setAreaCorporal(supCorp);
        }
    }

    private void seleccionaContenedor() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.seleccionaContenedor()");

    }

    private void calculaVolumenPorInsumo() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.calculaVolumenPorInsumo()");

    }

    private void calculaVolumenPorDrenar() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.calculaVolumenPorDrenar()");
    }



    /**
     * REaliza las acciones de validación y preparación de cancelación de
     * prescripción
     */
    public void validaCancelacion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaCancelacion()");
        boolean status = false;
        this.solucion = new Solucion();

        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (!permiso.isPuedeEliminar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción.", null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida.", null);

        } else {
            String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
            String idSolucion = null;
            this.solucion = this.solUtils.obtenerSolucion(idSolucion, idSurtimiento);

            if (this.solucion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

            } else if (solucion.getIdEstatusSolucion() != EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue()
                    && solucion.getIdEstatusSolucion() != EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue()
                    && solucion.getIdEstatusSolucion() != EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue()
                    && solucion.getIdEstatusSolucion() != EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue()
                    && solucion.getIdEstatusSolucion() != EstatusSolucion_Enum.ORDEN_CREADA.getValue()
                    && solucion.getIdEstatusSolucion() != EstatusSolucion_Enum.OP_VALIDADA.getValue()
                    && solucion.getIdEstatusSolucion() != EstatusSolucion_Enum.OP_RECHAZADA.getValue()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite cancelación.", null);
            } else {
                if (!this.solUtils.isFechaHotaCancelaValida(surtimientoExtendedSelected.getFechaProgramada(), new java.util.Date(), numMinutosModificCancela)) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La fecha hora de programacion no permite cancelación.", null);
                } else {
                    status = true;
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    /**
     * Realiza la cancelación de una prescripción, surtimiento y solucion cada
     * uno con sus respectivos detalles
     */
    public void ejecutaCancelacion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.ejecutaCancelacion()");
        boolean status = false;
        if (this.solucion == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

        } else if (this.solucion.getIdSolucion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

        } else if (this.solucion.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);

        } else if (this.surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);

        } else if (this.surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);

        } else if (this.surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción de solución inválida.", null);

        } else if (this.solucion.getComentariosCancelacion() == null
                || this.solucion.getComentariosCancelacion().trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Comentarios de canncelación requeridos.", null);

//        } else if (this.solucion.getIdMotivoCancelacion() < 1) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Motivo de cancelación requerido.", null);
        } else {
            String idPrescripcion = this.surtimientoExtendedSelected.getIdPrescripcion();
            try {
                this.solucion.setIdEstatusSolucion(EstatusSolucion_Enum.CANCELADA.getValue());
                this.solucion.setIdUsuarioCancela(this.usuarioSelected.getIdUsuario());
                this.solucion.setUpdateFecha(new java.util.Date());
                this.solucion.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());
                status = this.surtimientoService.cancelarPrescripcionSurtimientoSolucion(idPrescripcion, this.solucion);
                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al cancelar la prescripción.", null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Prescripción cancelada.", null);
                    Prescripcion p = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
                    Usuario u = null;
                    if (p != null) {
                        String IdUsuario = p.getIdMedico();
                        u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                        if (u != null) {
                            String asunto = "Mezcla " + surtimientoExtendedSelected.getFolio() + " Cancelada. ";
                            String msj = "";
                            if (solucion != null) {
                                msj = solucion.getComentariosCancelacion();

                            }
                            // TODO: obtener Usuarios de centro de mezclas para nnontificar la canncelación
                            // TODO: obtener el nombre, numero de Paciente y comentaruis de cancelación
                            this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                        }
                    }
                    obtenerSurtimientos();
                    this.solucion = new Solucion();
                }
            } catch (Exception e) {
                LOGGER.error("Error al cancelar la Prescripcion :: ", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al cancelar la prescripción.", null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private void obtieneMotivoCancelacion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtieneMotivoCancelacion()");
        this.motivoCancelaPresLista = new ArrayList<>();
        try {
            MotivoCancelaPrescripcion o = new MotivoCancelaPrescripcion();
            o.setActiva(1);
            if (this.validaPrescripcion) {
                o.setValidaPrescripcion(1);
                o.setValidaMezcla(0);
            } else {
                o.setValidaPrescripcion(1);
                o.setValidaMezcla(0);
            }
            this.motivoCancelaPresLista.addAll(this.motivoCancelaPrescService.obtenerLista(o));
        } catch (Exception e) {
            LOGGER.error("Error al obtener Motivo de cancelación de prescripción :: {} ", e.getMessage());
        }
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public List<String> getTipoPrescripcionSelectedList() {
        return tipoPrescripcionSelectedList;
    }

    public void setTipoPrescripcionSelectedList(List<String> tipoPrescripcionSelectedList) {
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
    }

    public Medicamento_Extended getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento_Extended medicamento) {
        this.medicamento = medicamento;
    }

    public Surtimiento_Extend getSurtimientoExtendedSelected() {
        return surtimientoExtendedSelected;
    }

    public void setSurtimientoExtendedSelected(Surtimiento_Extend surtimientoExtendedSelected) {
        this.surtimientoExtendedSelected = surtimientoExtendedSelected;
    }

    public List<Medicamento_Extended> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento_Extended> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public SurtimientoInsumo_Extend getSurtimientoInsumoExtendedSelected() {
        return surtimientoInsumoExtendedSelected;
    }

    public void setSurtimientoInsumoExtendedSelected(SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected) {
        this.surtimientoInsumoExtendedSelected = surtimientoInsumoExtendedSelected;
    }

    public List<Surtimiento_Extend> getSurtimientoExtendedList() {
        return surtimientoExtendedList;
    }

    public void setSurtimientoExtendedList(List<Surtimiento_Extend> surtimientoExtendedList) {
        this.surtimientoExtendedList = surtimientoExtendedList;
    }

    public void eliminaInsumoDeLista(SurtimientoInsumo_Extend surtInsumo) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaInsumoDeLista()");
        if (surtimientoInsumoExtendedList.contains(surtInsumo)) {
            List<SurtimientoInsumo_Extend> temp = new ArrayList<>();
            temp.addAll(surtimientoInsumoExtendedList);
            surtimientoInsumoExtendedList = new ArrayList<>();
            this.surtimientoInsumoExtendedListEliminar = new ArrayList<>();
            for (SurtimientoInsumo_Extend item : temp) {
                if (!item.getIdInsumo().equalsIgnoreCase(surtInsumo.getIdInsumo())) {
                    surtimientoInsumoExtendedList.add(item);
                } else {
                    surtimientoInsumoExtendedListEliminar.add(item);
                }
            }
        }
    }
    
    
//    public void eliminaInsumoDeLista(SurtimientoInsumo_Extend surtInsumo) {
//        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaInsumoDeLista()");
//        if (surtimientoInsumoExtendedList.contains(surtInsumo)) {
//            List<SurtimientoInsumo_Extend> temp = new ArrayList<>();
//            temp.addAll(surtimientoInsumoExtendedList);
//            surtimientoInsumoExtendedList = new ArrayList<>();
//            this.surtimientoInsumoExtendedListEliminar = new ArrayList<>();
//            for (SurtimientoInsumo_Extend item : temp) {
//                if (!item.getIdInsumo().equalsIgnoreCase(surtInsumo.getIdInsumo())) {
//                    surtimientoInsumoExtendedList.add(item);
//                } else {
//                    surtimientoInsumoExtendedListEliminar.add(item);
//                }
//            }
//        }
//    }
    
    /**
     * Elimina un mezcla de la lista de prescripción múltiple
     * @param sol 
     */
    public void eliminaMezclaDeLista(SolucionExtended sol) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaMezclaDeLista()");
        if (sol != null) {
            if (listaSolucionesExtend != null && !listaSolucionesExtend.isEmpty()
                    && surtimientoInsumoExtListMultiple != null && !surtimientoInsumoExtListMultiple.isEmpty()
                    && surtimientoExtListMultiple != null && !surtimientoExtListMultiple.isEmpty() ) {

                String idSolucion = sol.getIdSolucion();
                String idSurtimiento = sol.getIdSurtimiento();
                String idPrescripcion = null;
                if (idSurtimiento != null){
                    idPrescripcion = solUtils.obtienePrescripcionDeLista(surtimientoExtListMultiple, idSurtimiento);
                }

                if ( idSolucion != null && idSurtimiento != null && idPrescripcion != null ) {
                    listaSolucionesExtend = solUtils.eliminaSolucionDeLista(listaSolucionesExtend, idSolucion);
                    surtimientoInsumoExtListMultiple = solUtils.eliminaSurtimientoInsumoDeLista(surtimientoInsumoExtListMultiple, idSurtimiento);
                    surtimientoExtListMultiple = solUtils.eliminaSurtimientoDeLista(surtimientoExtListMultiple, idSurtimiento);
                    
                    if(surtimientoInsumoExtListMultiple != null && surtimientoInsumoExtListMultiple.isEmpty()
                        && listaPrescripcionInsumo != null && !listaPrescripcionInsumo.isEmpty()
                        && listaPrescripcionesMultiple != null && !listaPrescripcionesMultiple.isEmpty() ) {
                        listaPrescripcionInsumo = solUtils.eliminaPrescripcionInsumoDeLista(listaPrescripcionInsumo, idPrescripcion);
                        listaPrescripcionesMultiple = solUtils.eliminaPrescripcionDeLista(listaPrescripcionesMultiple, idPrescripcion);
                    }
                }
            }
        }
    }
    
    /**
     * Elimina un mezcla de la lista de prescripción múltiple
     * @param sol 
     */
//    public void eliminaMezclaDeLista(SolucionExtended sol) {
//        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.eliminaMezclaDeLista()");
//        if (listaSolucionesExtend != null && !listaSolucionesExtend.isEmpty()){
//            String idSurtimiento = sol.getIdSurtimiento();
//            String idPrescripcion = null;
//            
//            for (SolucionExtended item: listaSolucionesExtend){
//                if (sol.getIdSolucion().equalsIgnoreCase(sol.getIdSolucion())){
//                    listaSolucionesExtend.remove(item);
//                    break;
//                }
//            }
//            
//            if (surtimientoInsumoExtListMultiple != null && !surtimientoInsumoExtListMultiple.isEmpty() ) {
//                for (SurtimientoInsumo_Extend sie : surtimientoInsumoExtListMultiple){
//                    if (sie.getIdSurtimiento().equals(idSurtimiento)){
//                        surtimientoInsumoExtListMultiple.remove(sie);
//                    }
//                }
//            }
//            
//            if (surtimientoExtListMultiple != null && !surtimientoExtListMultiple.isEmpty()) {
//                for (Surtimiento sur : surtimientoExtListMultiple) {
//                    if (sur.getIdSurtimiento().equals(idSurtimiento)) {
//                        idPrescripcion = sur.getIdPrescripcion();
//                        surtimientoExtListMultiple.remove(sur);
//                    }
//                }
//            }            
//            
//            if (idPrescripcion != null) {
//                if (listaPrescripcionInsumo != null && !listaPrescripcionInsumo.isEmpty()){
//                    for (PrescripcionInsumo pins : listaPrescripcionInsumo){
//                        if (idPrescripcion.equals(pins.getIdPrescripcion())) {
//                            listaPrescripcionInsumo.remove(pins);
//                        }
//                    }
//                }
//                if (listaPrescripcionesMultiple != null && !listaPrescripcionesMultiple.isEmpty()){
//                    for (Prescripcion p : listaPrescripcionesMultiple){
//                        if (idPrescripcion.equals(p.getIdPrescripcion())) {
//                            listaPrescripcionesMultiple.remove(p);
//                        }
//                    }
//                }
//                
//            }
//        }
//    }

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    public DispensacionSolucionLazy getDispensacionSolucionLazy() {
        return dispensacionSolucionLazy;
    }

    public void setDispensacionSolucionLazy(DispensacionSolucionLazy dispensacionSolucionLazy) {
        this.dispensacionSolucionLazy = dispensacionSolucionLazy;
    }

    public Integer getXcantidad() {
        return xcantidad;
    }

    public void setXcantidad(Integer xcantidad) {
        this.xcantidad = xcantidad;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public boolean isMsjMdlSurtimiento() {
        return msjMdlSurtimiento;
    }

    public void setMsjMdlSurtimiento(boolean msjMdlSurtimiento) {
        this.msjMdlSurtimiento = msjMdlSurtimiento;
    }

    public String getMsjWarning() {
        return msjWarning;
    }

    public void setMsjWarning(String msjWarning) {
        this.msjWarning = msjWarning;
    }

    public Integer getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(Integer estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getPorValidar() {
        return porValidar;
    }

    public void setPorValidar(String porValidar) {
        this.porValidar = porValidar;
    }

    public String getValidada() {
        return validada;
    }

    public void setValidada(String validada) {
        this.validada = validada;
    }

    public String getRechazada() {
        return rechazada;
    }

    public void setRechazada(String rechazada) {
        this.rechazada = rechazada;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public List<Estructura> getListServiciosQueSurte() {
        return listServiciosQueSurte;
    }

    public void setListServiciosQueSurte(List<Estructura> listServiciosQueSurte) {
        this.listServiciosQueSurte = listServiciosQueSurte;
    }

    public List<CamaExtended> getListaCamas() {
        return listaCamas;
    }

    public void setListaCamas(List<CamaExtended> listaCamas) {
        this.listaCamas = listaCamas;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public List<ViaAdministracion> getViaAdministracionList() {
        return viaAdministracionList;
    }

    public void setViaAdministracionList(List<ViaAdministracion> viaAdministracionList) {
        this.viaAdministracionList = viaAdministracionList;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public ViaAdministracion getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(ViaAdministracion viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }

    public boolean isExistePrescripcion() {
        return existePrescripcion;
    }

    public void setExistePrescripcion(boolean existePrescripcion) {
        this.existePrescripcion = existePrescripcion;
    }

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
    }

    public BigDecimal getDosis() {
        return dosis;
    }

    public void setDosis(BigDecimal dosis) {
        this.dosis = dosis;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getClaveProtocolo() {
        return claveProtocolo;
    }

    public void setClaveProtocolo(String claveProtocolo) {
        this.claveProtocolo = claveProtocolo;
    }

    public List<Protocolo> getListaProtocolos() {
        return listaProtocolos;
    }

    public void setListaProtocolos(List<Protocolo> listaProtocolos) {
        this.listaProtocolos = listaProtocolos;
    }

    public List<TipoSolucion> getTipoSolucionList() {
        return tipoSolucionList;
    }

    public void setTipoSolucionList(List<TipoSolucion> tipoSolucionList) {
        this.tipoSolucionList = tipoSolucionList;
    }

    public boolean isIsOncologica() {
        return isOncologica;
    }

    public void setIsOncologica(boolean isOncologica) {
        this.isOncologica = isOncologica;
    }

    public List<Frecuencia> getListaFrecuencias() {
        return listaFrecuencias;
    }

    public void setListaFrecuencias(List<Frecuencia> listaFrecuencias) {
        this.listaFrecuencias = listaFrecuencias;
    }

    public Usuario_Extended getMedicoExtended() {
        return medicoExtended;
    }

    public void setMedicoExtended(Usuario_Extended medicoExtended) {
        this.medicoExtended = medicoExtended;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public List<String> getListaIdTurnos() {
        return listaIdTurnos;
    }

    public void setListaIdTurnos(List<String> listaIdTurnos) {
        this.listaIdTurnos = listaIdTurnos;
    }

    public List<CatalogoGeneral> getListaTipoPacientes() {
        return listaTipoPacientes;
    }

    public void setListaTipoPacientes(List<CatalogoGeneral> listaTipoPacientes) {
        this.listaTipoPacientes = listaTipoPacientes;
    }

    public List<TipoUsuario> getTipoUserList() {
        return tipoUserList;
    }

    public void setTipoUserList(List<TipoUsuario> tipoUserList) {
        this.tipoUserList = tipoUserList;
    }

    public List<EnvaseContenedor> getEnvaseList() {
        return envaseList;
    }

    public void setEnvaseList(List<EnvaseContenedor> envaseList) {
        this.envaseList = envaseList;
    }

    public BigDecimal getVolumenTotal() {
        return volumenTotal;
    }

    public void setVolumenTotal(BigDecimal volumenTotal) {
        this.volumenTotal = volumenTotal;
    }

    public boolean isValidaPrescripcion() {
        return validaPrescripcion;
    }

    public void setValidaPrescripcion(boolean validaPrescripcion) {
        this.validaPrescripcion = validaPrescripcion;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isOrdenRechazada() {
        return ordenRechazada;
    }

    public void setOrdenRechazada(boolean ordenRechazada) {
        this.ordenRechazada = ordenRechazada;
    }

    public boolean isEditarHoraInfusion() {
        return editarHoraInfusion;
    }

    public void setEditarHoraInfusion(boolean editarHoraInfusion) {
        this.editarHoraInfusion = editarHoraInfusion;
    }
        
    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isRechazable() {
        return rechazable;
    }

    public void setRechazable(boolean rechazable) {
        this.rechazable = rechazable;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }

    private transient List<HorarioEntrega> horarioEntregaLista;
    private HorarioEntrega horarioEntrega;

    @Autowired
    private transient HorarioEntregaService horarioEntregaService;

    public List<HorarioEntrega> getHorarioEntregaLista() {
        return horarioEntregaLista;
    }

    public void setHorarioEntregaLista(List<HorarioEntrega> horarioEntregaLista) {
        this.horarioEntregaLista = horarioEntregaLista;
    }

    public HorarioEntrega getHorarioEntrega() {
        return horarioEntrega;
    }

    public void setHorarioEntrega(HorarioEntrega horarioEntrega) {
        this.horarioEntrega = horarioEntrega;
    }

    public String getIdTipoSolucionSelected() {
        return idTipoSolucionSelected;
    }

    public void setIdTipoSolucionSelected(String idTipoSolucionSelected) {
        this.idTipoSolucionSelected = idTipoSolucionSelected;
    }

    public void activaPerfusion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.activaPerfusion()");
        editarHoraInfusion = true;
        calculaVelocidadPerfusion();
    }
    
    public void calculaVelocidadPerfusion() {
            LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.calculaVelocidadPerfusion");
        asignaVelocidad();
    }
    
    public void setIdCotenedor() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.setIdCotenedor");
    }

    public List<MotivoCancelaPrescripcion> getMotivoCancelaPresLista() {
        return motivoCancelaPresLista;
    }

    public void setMotivoCancelaPresLista(List<MotivoCancelaPrescripcion> motivoCancelaPresLista) {
        this.motivoCancelaPresLista = motivoCancelaPresLista;
    }

    public List<SolucionExtended> getListaSolucionesExtend() {
        return listaSolucionesExtend;
    }

    public void setListaSolucionesExtend(List<SolucionExtended> listaSolucionesExtend) {
        this.listaSolucionesExtend = listaSolucionesExtend;
    }

    public boolean isBotonGuardarMultiple() {
        return botonGuardarMultiple;
    }

    public void setBotonGuardarMultiple(boolean botonGuardarMultiple) {
        this.botonGuardarMultiple = botonGuardarMultiple;
    }

    public String getComentarioAutoriza() {
        return comentarioAutoriza;
    }

    public void setComentarioAutoriza(String comentarioAutoriza) {
        this.comentarioAutoriza = comentarioAutoriza;
    }

    public String getMotivoNoAutoriza() {
        return motivoNoAutoriza;
    }

    public void setMotivoNoAutoriza(String motivoNoAutoriza) {
        this.motivoNoAutoriza = motivoNoAutoriza;
    }

    public boolean isDisabledEditAddInsumo() {
        return disabledEditAddInsumo;
    }

    public void setDisabledEditAddInsumo(boolean disabledEditAddInsumo) {
        this.disabledEditAddInsumo = disabledEditAddInsumo;
    }

    public boolean isVerAutorizacion() {
        return verAutorizacion;
    }

    public void setVerAutorizacion(boolean verAutorizacion) {
        this.verAutorizacion = verAutorizacion;
    }
    
    public void asignaVelocidad() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.asignaVelocidad()");
        try {
            if (this.surtimientoExtendedSelected != null) {
                if (surtimientoExtendedSelected.getHorasInfusion() != null && !surtimientoExtendedSelected.getHorasInfusion().isEmpty()) {
                    String cadenaHora[] = surtimientoExtendedSelected.getHorasInfusion().split(":");
                    Integer hora = Integer.valueOf(cadenaHora[0]);
                    Integer minut = Integer.valueOf(cadenaHora[1]);
                    Integer minutosTotales = (hora * 60) + minut;
                    surtimientoExtendedSelected.setMinutosInfusion(minutosTotales);
                    if (this.surtimientoExtendedSelected.isPerfusionContinua()) {
                        if (this.surtimientoExtendedSelected.getVolumenTotal() != null) {
                            if (this.surtimientoExtendedSelected.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
                                    BigDecimal result = (this.surtimientoExtendedSelected.getVolumenTotal().multiply(
                                            new BigDecimal(60))).divide(new BigDecimal(minutosTotales), 3, RoundingMode.HALF_UP);
                                    this.surtimientoExtendedSelected.setVelocidad(result.doubleValue());
                                }                           
                        }
                    } else {
                        this.surtimientoExtendedSelected.setVelocidad(Double.valueOf("0"));
                    }
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Error al asignar velocidad de infusión :: {} ", e.getMessage());
        }
    }

    /**
     *
     * @param s
     * @param idTipoSol
     */
    @Deprecated
    private String validaFechaCancelacion(Surtimiento_Extend s, String idTipoSol) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaFechaCancelacion()");
        String res = null;
        try {
            if (s != null) {
                if (s.getIdHorarioParaEntregar() != null) {
                    HorarioEntrega o = new HorarioEntrega();
                    o.setIdHorarioEntrega(s.getIdHorarioParaEntregar());
                    o.setIdTipoSolucion(idTipoSol);
                    o.setActiva(-1);
                    HorarioEntrega he = this.horarioEntregaService.obtener(o);
                    if (he == null) {
                        res = "Horario de cancelación inválido. ";
                    } else {

                        String fechaCancel = FechaUtil.formatoCadena(s.getFechaParaEntregar(), "yyyy-MM-dd");
                        String horaCancel = FechaUtil.formatoCadena(he.getHoraMaximaCancelacion(), "HH:mm");
                        Date fechaCancelacion = FechaUtil.formatoFecha("yyyy-MM-dd HH:mm", fechaCancel + " " + horaCancel);
                        String validaFecha = this.solUtils.fechaHoraCancelacionValida(fechaCancelacion);
                        if (validaFecha != null) {
                            res = validaFecha;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al validar fecha de cancelación :: {} ", e.getMessage());
            res = "Error al evaluar fecha y hora de cacelación. ";
        }
        return res;
    }

    public void sugerirPosologia() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.sugerirPosologia()");
        try {
            if (this.medicamento != null) {
                if (!this.medicamento.isDiluyente()) {
                    this.dosis = BigDecimal.ZERO;
                    this.frecuencia = Frecuencia_Enum.OD.getValue();
                    this.duracion = 1;
                } else {
                    this.dosis = this.medicamento.getConcentracion();
                    this.frecuencia = Frecuencia_Enum.OD.getValue();
                    this.duracion = 1;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al definir sugerirPosologia :: {} ", e.getMessage());
        }
    }

    /**
     * Genera la impresión de la prescripción de mezclas.
     *
     * @param se
     */
    public void imprimeDocumento(Surtimiento_Extend se) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.imprimeDocumento()");
        boolean status = false;
        try {
            String nombreUsuario = usuarioSelected.getNombre() + ' ' + usuarioSelected.getApellidoPaterno() + ' ' + usuarioSelected.getApellidoMaterno();
// TODO generar tipos de reporte mediante enums
            Integer idTipoReporte = 1;
            byte[] buffer = this.solUtils.imprimeDocumento(se, nombreUsuario, idTipoReporte);
            if (buffer == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar impresión. ", null);
            } else {
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("PRE_%s.pdf", se.getFolioPrescripcion()));
                status = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al generar la impresión :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar impresión. " + ex.getMessage(), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public Date getFechaMinPrescrMezcla() {
        return fechaMinPrescrMezcla;
    }

    public void setFechaMinPrescrMezcla(Date fechaMinPrescrMezcla) {
        this.fechaMinPrescrMezcla = fechaMinPrescrMezcla;
    }

    public Date getFechaMinPrescrMezclaValida() {
        return fechaMinPrescrMezclaValida;
    }

    public void setFechaMinPrescrMezclaValida(Date fechaMinPrescrMezclaValida) {
        this.fechaMinPrescrMezclaValida = fechaMinPrescrMezclaValida;
    }

    private void calculaFechaMinimaSolicitud() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        if (numMinutosEntregaMezcla != null && numMinutosEntregaMezcla > 0) {
            cal.add(Calendar.MINUTE, 5);
            cal.add(Calendar.MINUTE, numMinutosEntregaMezcla);
        }
        fechaMinPrescrMezcla = cal.getTime();
        cal.add(Calendar.MINUTE, -3);
        fechaMinPrescrMezclaValida = cal.getTime();
    }

    /**
     * Realiza las acciones de validación y para rechazo de orden de preparación
     * prescripción
     */
    public void validaRechazo() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaRechazo()");
        boolean status = false;
        this.solucion = new Solucion();

        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (!permiso.isPuedeEliminar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción.", null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida.", null);

        } else {
            String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
            String idSolucion = null;
            this.solucion = this.solUtils.obtenerSolucion(idSolucion, idSurtimiento);

            if (this.solucion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

            } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                    && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())
                    && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_RECHAZADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite cancelación.", null);

            } else {
                solucion.setComentariosRechazo(StringUtils.EMPTY); 
                solucion.setIdMotivoRechazo(null); 
                status = true;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void rechazarValidacionSolucion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.rechazarValidacionSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);
            } else if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de MEzcla inválido.", null);

            } else {
                Surtimiento surtimiento = new Surtimiento();
                surtimiento.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                surtimiento.setUpdateFecha(new Date());
                surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                surtimiento.setDiabetes(surtimientoExtendedSelected.isDiabetes());
                surtimiento.setPadecimientoCronico(surtimientoExtendedSelected.isPadecimientoCronico());
                surtimiento.setHipertension(surtimientoExtendedSelected.isHipertension());
                surtimiento.setProblemasRenales(surtimientoExtendedSelected.isProblemasRenales());

                Solucion o = solucionService.obtenerSolucion(null, this.surtimientoExtendedSelected.getIdSurtimiento());
                if (o != null) {
                    Solucion s = new Solucion();
                    s.setIdSolucion(o.getIdSolucion());
                    s.setUpdateFecha(new java.util.Date());
                    s.setFechaValPrescr(new java.util.Date());
                    s.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue());
                    s.setIdUsuarioValOrdenPrep(usuarioSelected.getIdUsuario());
                    s.setIdUsuarioRechaza(usuarioSelected.getIdUsuario());
                    s.setComentariosRechazo(solucion.getComentariosRechazo());
                    status = solucionService.rechazarMezcla(s, surtimiento, surtimientoInsumoExtendedList, false);
                }

                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Prescripción Rechazada.", null);

                    StringBuilder nombrePaciente = new StringBuilder();
                    String pacienteNumero = "";
                    if (this.surtimientoExtendedSelected != null) {
                        Paciente pac = pacienteService.obtenerPacienteByIdPaciente(this.surtimientoExtendedSelected.getIdPaciente());
                        if (pac != null) {
                            nombrePaciente.append(pac.getNombreCompleto());
                            nombrePaciente.append(StringUtils.SPACE);
                            nombrePaciente.append(pac.getApellidoPaterno());
                            nombrePaciente.append(StringUtils.SPACE);
                            nombrePaciente.append(pac.getApellidoMaterno());
                            pacienteNumero = pac.getPacienteNumero();
                        }
                    }
                    this.surtimientoExtendedSelected.setNombrePaciente(nombrePaciente.toString());
                    this.surtimientoExtendedSelected.setPacienteNumero(pacienteNumero);
                    Prescripcion p = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
                    Usuario u = null;
                    if (p != null) {
                        String IdUsuario = p.getIdMedico();
                        u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                        if (u != null) {
                            String asunto = "Prescripción de mezcla " + surtimientoExtendedSelected.getFolioPrescripcion() + " Rechazada. ";
                            StringBuilder msj = new StringBuilder();
                            msj.append(solucion.getComentariosRechazo());
                            msj.append("<br/><br/>Usuario Rechaza: ");
                            msj.append(usuarioSelected.getNombre()).append(StringUtils.SPACE); 
                            msj.append(usuarioSelected.getApellidoPaterno()).append(StringUtils.SPACE); 
                            msj.append(usuarioSelected.getApellidoMaterno()); 
                            msj.append("<br/><br/>Horario de Rechazo: ");
                            msj.append(FechaUtil.formatoCadena(new java.util.Date(), "dd/MM/yyyy HH:mm"));
                            this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj.toString(), surtimientoExtendedSelected);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ocurrio un error en: rechazarValidacionSolucion", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }
    
    
    public void estableceFechaPrgramada(AjaxBehaviorEvent event) {
        try {
            Date fechaProg = (Date) ((UIOutput) event.getSource()).getValue();
            if (fechaProg!= null){
                surtimientoExtendedSelected.setFechaProgramada(fechaProg);
            }
        } catch (Exception e) {
            LOGGER.error("Error al registrar lote recibido :: {} ", e.getMessage());
        }
    }
    
    public void estableceViaAdministracion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.estableceViaAdministracion()");
        try {
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar via de administracion :: {} ", e.getMessage());
        }
    }
    
   public void estableceContenedor () {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.estableceContenedor()");
        try {
        } catch (Exception e) {
            LOGGER.error("Error al seleccionar via de administracion :: {} ", e.getMessage());
        }
    }

}
