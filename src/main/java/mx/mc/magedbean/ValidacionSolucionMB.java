package mx.mc.magedbean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.mc.commons.SolucionUtils;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.dto.ValidacionSolucionMezclaDetalleDTO;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusDiagnostico_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.lazy.DispensacionSolucionLazy;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.MotivoCancelacionRechazoSolucion_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoImpresora_Enum;
import mx.mc.enums.TipoJustificacion_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.TipoTemplateEtiqueta_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import mx.mc.model.Capsula;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.Estructura;
import mx.mc.model.EtiquetaInsumo;
import mx.mc.model.Fabricante;
import mx.mc.model.Frecuencia;
import mx.mc.model.HorarioEntrega;
import mx.mc.model.Impresora;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Protocolo;
import mx.mc.model.ProtocoloExtended;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TemplateEtiqueta;
import mx.mc.model.TipoJustificacion;
import mx.mc.model.TipoSolucion;
import mx.mc.model.TipoUsuario;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Turno;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
import mx.mc.service.CamaService;
import mx.mc.service.CantidadRazonadaService;
import mx.mc.service.CapsulaService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ConfigService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.FabricanteService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.EnvioNeumaticoService;
import mx.mc.service.EquipoService;
import mx.mc.service.EstabilidadService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FrecuenciaService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.HorarioEntregaService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PlantillaCorreoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ProtocoloService;
import mx.mc.service.ReaccionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SolucionService;
import mx.mc.service.SurtimientoEnviadoService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TemplateEtiquetaService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.service.TipoSolucionService;
import mx.mc.service.TipoUsuarioService;
import mx.mc.service.TransaccionService;
import mx.mc.service.TurnoService;
import mx.mc.service.UsuarioService;
import mx.mc.service.ViaAdministracionService;
import mx.mc.service.VisitaService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.EnviaCorreoUtil;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import mx.mc.ws.farmacovigilancia.service.ReaccionesAdversas;
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
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class ValidacionSolucionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidacionSolucionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private boolean editable;
    private boolean rechazable;

    private boolean huboError;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private Date fechaActual;
    private Usuario usuarioSelected;
    private String userResponsable;
    private String passResponsable;
    private String idResponsable;
    private String nombreCompleto;
    private boolean authorization;
    private boolean authorizado;
    private boolean exist;
    private boolean msjMdlSurtimiento;
    private String msjAlert;

    private DispensacionSolucionLazy dispensacionSolucionLazy;
    private ParamBusquedaReporte paramBusquedaReporte;
    private boolean activaAutoCompleteInsumos;
    private String surSinAlmacen;

    private transient List<Estructura> listServiciosQueSurte;
    private Medicamento_Extended medicamento;
    private transient List<Medicamento_Extended> medicamentoList;

    private Surtimiento_Extend surtimientoExtendedSelected;
    private transient List<Surtimiento_Extend> surtimientoExtendedList;

    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private transient List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;

    private String tipoPrescripcion;
    private transient List<String> tipoPrescripcionSelectedList;

    private transient List<TemplateEtiqueta> templateList;
    private String template;
    private Integer numTemp;
    private String itemPirnt;
    private Boolean activeBtnPrint;
    private String idPrintSelect;
    private Integer cantPrint;

    private Impresora impresoraSelect;
    private transient List<Impresora> listaImpresoras;

    private String codigoBarras;
    private boolean eliminaCodigoBarras;
    private String codigoBarrasAutorizte;
    private Integer xcantidadAutorizte;

    private Integer estatusSurtimiento;
    private String programada;
    private String validada;
    private String rechazada;
    private String surtida;
    private String cancelada;
    private String folioSolucion;
    private String loteSolucion;
    private Date caducidadSolucion;
    private transient List<Integer> listEstatusSurtimiento;
    private Surtimiento surtim;
    private boolean mostrarImpresionEtiqueta;
    private String tipoArchivoImprimir;
    private boolean imprimeReporte;
    private boolean imprimeEtiqueta;
    private String docReporte;
    private String docEtiqueta;
    private String paramEstatus;
    private String errRegistroIncorrecto;
    private String surIncorrecto;
    private String surCaducidadvencida;
    private String surLoteIncorrecto;
    private String surInvalido;
    private PermisoUsuario permiso;
    private boolean funValidarFarmacoVigilancia;
    private transient RespuestaAlertasDTO alertasDTO;
    private transient List<Diagnostico> diagnosticosList;
    private String alergias;
    private transient List<EnvaseContenedor> envaseList;
    private Integer idEnvase;
    private boolean muestreo;
    private String conservacion;
    private BigDecimal volumenTotal;
    private String observaciones;
    private String diagnosticos;
    private transient List<ValidacionSolucionMezclaDetalleDTO> listaDetalleProtocolos;
    private ProtocoloExtended protocoloSelected;

    private String claveProtocolo;

    @Autowired
    private transient EstructuraService estructuraService;
    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient FabricanteService fabricanteService;
    @Autowired
    private transient CantidadRazonadaService cantidadRazonadaService;
    @Autowired
    private transient PacienteService pacienteService;
    private transient List<TipoJustificacion> justificacionList;
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;
    @Autowired
    private transient InventarioService inventarioService;
    @Autowired
    private transient ReportesService reportesService;
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    @Autowired
    private transient CapsulaService capsulaService;
    @Autowired
    private transient EnvioNeumaticoService envioNeumaticoService;
    @Autowired
    private transient MedicamentoService medicamentoService;
    @Autowired
    private transient UsuarioService usuarioService;
    @Autowired
    private transient TransaccionService transaccionService;
    @Autowired
    private transient TemplateEtiquetaService templateService;
    @Autowired
    private transient ImpresoraService impresoraService;
    @Autowired
    private transient VisitaService visitaService;
    @Autowired
    private transient ServletContext servletContext;
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    @Autowired
    private transient PrescripcionService prescripcionService;
    @Autowired
    private transient EnvaseContenedorService envaseService;
    @Autowired
    private transient SurtimientoEnviadoService enviadoService;
    @Autowired
    private transient SolucionService solucionService;
    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    @Autowired
    private transient TipoSolucionService tipoSolucionService;
    @Autowired
    private transient ViaAdministracionService viaAdministracionService;
    @Autowired
    private transient FrecuenciaService frecuenciaService;
    @Autowired
    private transient MotivosRechazoService motivosRechazoService;
    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    @Autowired
    private transient TurnoService turnoService;
    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;
    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;
    @Autowired
    private transient EnviaCorreoUtil enviaCorreoUtil;
    @Autowired
    private transient PlantillaCorreoService plantillaCorreoService;
    @Autowired
    private transient ConfigService configService;
    @Autowired
    private transient CamaService camaService;
    @Autowired
    private transient EquipoService equipoService;
    @Autowired
    private transient SurtimientoEnviadoService surtimientoEnviadoService;
    @Autowired
    private transient EstabilidadService estabilidadService;

    private BigDecimal dosis;
    private Integer frecuencia;
    private Integer duracion;

    private ViaAdministracion viaAdministracion;
    private String idTipoSolucion;
    private boolean perfusionContinua;
    private transient List<ViaAdministracion> viaAdministracionList;
    private transient List<Protocolo> listaProtocolos;
    private transient List<Frecuencia> listaFrecuencias;
    private transient List<CatalogoGeneral> listaTipoPacientes;
    private transient List<TipoUsuario> tipoUserList;
    private transient List<CamaExtended> listaCamas;
    private boolean verAutorizacion;
    
    boolean esSolucion;
    private transient List<Integer> estatusSolucionLista;
    private Integer xcantidad;
    private Integer noDiasCaducidad;

    private SesionMB sesion;
    private SolucionUtils solUtils;
    private transient List<HorarioEntrega> horarioEntregaLista;
    private HorarioEntrega horarioEntrega;
    private boolean enviaCorreoalValidarMezcla;

    @Autowired
    private transient HorarioEntregaService horarioEntregaService;
    @Autowired
    private transient HipersensibilidadService hipersensibilidadService;
    @Autowired
    private transient ReaccionService reaccionService;

    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.init()");

        inicializa();
        limpia();

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        this.enviaCorreoalValidarMezcla = this.sesion.isEnviaCorreoalValidarMezcla();
        this.activaAutoCompleteInsumos = this.sesion.isActivaAutocompleteQrClave();
        this.capsulaDisponible = this.sesion.isCapsulaDisponible();
        this.funValidarFarmacoVigilancia = this.sesion.isFunValidarFarmacoVigilancia();
        this.usuarioSelected = this.sesion.getUsuarioSelected();
        this.noDiasCaducidad = this.sesion.getNoDiasCaducidad();

        this.paramBusquedaReporte = new ParamBusquedaReporte();
        this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.VALIDACIONDESOLUCIONES.getSufijo());

        this.userResponsable = this.usuarioSelected.getNombreUsuario();
        this.passResponsable = this.usuarioSelected.getClaveAcceso();

        this.nombreCompleto = (this.usuarioSelected.getNombre() + " " + this.usuarioSelected.getApellidoPaterno() + " " + this.usuarioSelected.getApellidoMaterno()).toUpperCase();

        this.viaAdministracion = new ViaAdministracion();

        this.templateList = new ArrayList<>();

        try {
            this.solUtils = new SolucionUtils(estructuraService, surtimientoService, templateService, impresoraService, motivosRechazoService, prescripcionService, surtimientoInsumoService, solucionService, reportesService, entidadHospitalariaService, diagnosticoService, envaseService, viaAdministracionService, tipoSolucionService, protocoloService, visitaService, camaService, frecuenciaService, catalogoGeneralService, turnoService, tipoUsuarioService, pacienteServicioService, pacienteUbicacionService, enviaCorreoUtil, plantillaCorreoService, configService, tipoJustificacionService, medicamentoService, usuarioService, pacienteService, hipersensibilidadService, reaccionService, equipoService, estabilidadService, inventarioService);
            obtenerCamas();
            obtenerProtocolos();
            obtenerFrecuencias();
            obtenerTiposSolucion();
            obtenerViasAdministracion();
            obtenerEnvases();
            obtenerTurnos();
            obtenerTipoUsuarios();
            obtenerTipoPacientes();
//            obtenerMedico();

            obtenerServiciosQuePuedeSurtir();
            obtenerTemplatesEtiquetas();
            obtenerJustificacion();
            obtenerSurtimientos();
            obtienelistaCapsulas();
            this.templateList.addAll(this.solUtils.obtenerListaTemplates(TipoTemplateEtiqueta_Enum.E.getValue()));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        this.horarioEntregaLista = new ArrayList<>();
    }

    private void inicializa() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.inicializa()");
        this.surSinAlmacen = "sur.sin.almacen";
        this.paramEstatus = "estatus";
        this.errRegistroIncorrecto = "err.registro.incorrecto";
        this.surIncorrecto = "sur.incorrecto";
        this.surCaducidadvencida = "sur.caducidadvencida";
        this.surLoteIncorrecto = "sur.loteincorrecto";
        this.surInvalido = "sur.invalido";

        this.listEstatusSurtimiento = null;
//        this.listEstatusSurtimiento = new ArrayList<>();
//        this.estatusSurtimiento = EstatusSurtimiento_Enum.PROGRAMADO.getValue();
//        this.listEstatusSurtimiento.add(estatusSurtimiento);

        this.esSolucion = true;
        this.estatusSolucionLista = new ArrayList<>();
        this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue());
//        this.estatusSolucionLista.add(EstatusSolucion_Enum.ORDEN_CREADA.getValue());

        this.docEtiqueta = TipoImpresora_Enum.ETIQUETA.getValue();
        this.docReporte = TipoImpresora_Enum.NORMAL.getValue();
        this.mostrarImpresionEtiqueta = false;
        this.tipoArchivoImprimir = StringUtils.EMPTY;

        this.programada = Constantes.PROGRAMADA;
        this.validada = Constantes.VALIDADA;
        this.rechazada = Constantes.RECHAZADA;
        this.surtida = Constantes.SURTIDA;
        this.cancelada = Constantes.CANCELADA;

        this.template = StringUtils.EMPTY;
        this.templateList = new ArrayList<>();
        this.diagnosticosList = new ArrayList<>();
        this.envaseList = new ArrayList<>();
        this.idEnvase = -1;
        this.alergias = "";
        this.numTemp = 0;
        this.folioSolucion = "";
        this.loteSolucion = "";
        this.caducidadSolucion = new Date();
        this.surtim = new Surtimiento();
        this.imprimeReporte = true;
        this.isOncologica = Constantes.INACTIVO;
        this.claveProtocolo = null;
        this.viaAdministracion = new ViaAdministracion();
        this.dosis = BigDecimal.ZERO;
        this.frecuencia = null;
        this.duracion = null;
        this.diagnosticoSelected = null;
        this.medico = new Usuario();
        this.msjWarning = "";

        this.solucion = new Solucion();
        this.horarioEntregaLista = new ArrayList<>();
        this.verAutorizacion = false;
    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.limpia()");
        this.elementoSeleccionado = false;
        this.huboError = false;
        this.cadenaBusqueda = null;
        this.fechaActual = new java.util.Date();
        this.codigoBarras = null;
        this.eliminaCodigoBarras = false;
        this.xcantidad = 1;
        this.nombreCompleto = "";
        this.userResponsable = "";
        this.tipoPrescripcion = "";
        this.passResponsable = "";
        this.idResponsable = "";
        this.msjMdlSurtimiento = true;
        this.exist = false;
        this.msjAlert = "";
        this.isOncologica = Constantes.INACTIVO;
        this.tipoPrescripcionSelectedList = new ArrayList<>();
        this.surtimientoExtendedSelected = new Surtimiento_Extend();
    }

    public void obtenerCamas() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerCamas()");
        this.listaCamas = new ArrayList<>();
        String idServicio = null;
        this.listaCamas.addAll(this.solUtils.obtenerListaCamas(idServicio));
    }

    public void obtenerProtocolos() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerProtocolos()");
        this.listaProtocolos = new ArrayList<>();
        this.listaProtocolos.addAll(this.solUtils.obtenerProtocolos());
    }

    public void obtenerFrecuencias() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerFrecuencias()");
        this.listaFrecuencias = new ArrayList<>();
        listaFrecuencias.addAll(this.solUtils.obtenerListaFrecuencias());
    }

    public void obtenerTiposSolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerTiposSolucion()");
        List<String> listaClaves = new ArrayList<>();
        listaClaves.add("ANT");
        listaClaves.add("ONC");
// TODO: agregar el método requerido
        this.tipoSolucionList = new ArrayList<>();
        this.tipoSolucionList.addAll(this.solUtils.obtenerListaTiposSolucion(listaClaves));
    }

    public void obtenerViasAdministracion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerViasAdministracion()");
        this.viaAdministracionList = new ArrayList<>();
        this.viaAdministracionList.addAll(this.solUtils.obtenerListaViaAdministracion());
    }

    public void obtenerEnvases() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerEnvases()");
        this.envaseList = new ArrayList<>();
        this.envaseList.addAll(this.solUtils.obtenerListaEnvases());
    }

    public void obtenerTurnos() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerTurnos()");
        this.listaTurnos = new ArrayList<>();
        this.listaTurnos.addAll(this.solUtils.obtenerListaTurnos());
    }

    public void obtenerTipoUsuarios() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerTipoUsuarios()");
        this.tipoUserList = new ArrayList<>();
        this.tipoUserList.addAll(this.solUtils.obtenerListaTipoUsuario());
    }

    public void obtenerTipoPacientes() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerTipoPacientes()");
        this.listaTipoPacientes = new ArrayList<>();
        this.listaTipoPacientes.addAll(this.solUtils.obtenerListaTipoPacientes());
    }

    public void obtenerMedico() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerTipoPacientes()");
        this.medico = this.solUtils.obtenerMedico(true, this.usuarioSelected);
    }

    /**
     * Obtiene la lista de templates de impresión de etiquetas
     */
    private void obtenerTemplatesEtiquetas() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerTemplatesEtiquetas()");
        String tipoTemplate = TipoTemplateEtiqueta_Enum.E.getValue();
        this.templateList = this.solUtils.obtenerListaTemplates(tipoTemplate);
        this.numTemp = templateList.size();
        if (this.numTemp == 1) {
            this.template = templateList.get(0).getContenido();
        }
    }

    /**
     * Obtener listado de Justificación
     */
    private void obtenerJustificacion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerJustificacion()");
        this.justificacionList = new ArrayList<>();
        boolean activa = Constantes.ACTIVO;
        List<Integer> listTipoJustificacionSol = null;
        this.justificacionList.addAll(this.solUtils.obtenerJustificacion(activa, listTipoJustificacionSol));
    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario queF
     * puede surtir
     */
    private void obtenerServiciosQuePuedeSurtir() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerServiciosQuePuedeSurtir()");
        this.listServiciosQueSurte = new ArrayList<>();

        Estructura estSol = null;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(this.surSinAlmacen), null);

            } else if (this.usuarioSelected.getIdEstructura() == null || this.usuarioSelected.getIdEstructura().trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(this.surSinAlmacen), null);

            } else {
                estSol = estructuraService.obtener(new Estructura(this.usuarioSelected.getIdEstructura()));

                if (estSol == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(this.surSinAlmacen), null);

                } else if (estSol.getIdTipoAreaEstructura() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(this.surSinAlmacen), null);

                } else if (!Objects.equals(estSol.getIdTipoAreaEstructura(), TipoAreaEstructura_Enum.ALMACEN.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

                } else {
//                    this.listServiciosQueSurte.addAll(
//                            estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura()));
                    this.listServiciosQueSurte = new ArrayList<>();
                    this.listServiciosQueSurte.addAll(this.solUtils.obtieneAreasPorTipo(this.usuarioSelected));
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", ex.getMessage());
        }
    }

    /**
     * Obtiene la lista de surtimientos de prescripcines de solucion pendientes
     */
    public void obtenerSurtimientos() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerSurtimientos()");

        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        this.surtimientoExtendedList = new ArrayList<>();

        if (!this.permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (this.usuarioSelected == null || this.usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (this.listServiciosQueSurte.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.almacen.incorrectado"), null);

        } else {
            try {

                if (this.cadenaBusqueda != null && this.cadenaBusqueda.trim().isEmpty()) {
                    this.cadenaBusqueda = null;
                }
// Regla: filtro de tipo de prescripción: Normal, Dósis Única, Normal o todas
                if (this.tipoPrescripcionSelectedList != null && this.tipoPrescripcionSelectedList.isEmpty()) {
                    this.tipoPrescripcionSelectedList = null;
                }

// Regla: Listar prescripciones donde el paciente tiene estatus: Asignado a Servicio o Asignación a Cama
                List<Integer> listEstatusPacienteSol = new ArrayList<>();
                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                listEstatusPacienteSol.add(EstatusPaciente_Enum.ASIGNADO_A_CAMA.getValue());
// TODO: HHRC 13ENE2023 SE DEBE AGREGAR UN PARÁMETRO DE CONFIGURACIÓN PARA VALIDAR EL ESTATUS DE PACIENETE
//                      Se elimina la restricción del estatus paciente, debido a reglas de HJM no esta definido el proceso de admision hospitalaria
                listEstatusPacienteSol = null;

// Regla: Listar prescripciones solo con estatus de PROGRAMADA, FINALIZADA O CANCELADA
                List<Integer> listEstatusPrescripcion = null;
//                List<Integer> listEstatusPrescripcion = new ArrayList<>();
//                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
//                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
//                listEstatusPrescripcion.add(EstatusPrescripcion_Enum.CANCELADA.getValue());

// Regla: Listar prescripciones solo con estatus de surtimiento programado
//                this.listEstatusSurtimiento.add(this.estatusSurtimiento);
// Regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                Date fechaProgramada = new java.util.Date();

                // TODO:    agregar reglas de Negocio
                // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                // 2.- Solo surten los insumos que tienen activos
                // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna
                this.paramBusquedaReporte.setNuevaBusqueda(true);
                this.paramBusquedaReporte.setCadenaBusqueda(this.cadenaBusqueda);

                int tipoProceso = Constantes.TIPO_PROCESO_QUIMICO;
                
                this.dispensacionSolucionLazy = new DispensacionSolucionLazy(
                        surtimientoService,
                        this.paramBusquedaReporte,
                        fechaProgramada,
                        this.tipoPrescripcionSelectedList,
                        listEstatusPacienteSol,
                        listEstatusPrescripcion,
                        this.listEstatusSurtimiento,
                        this.listServiciosQueSurte,
                        esSolucion,
                        estatusSolucionLista,
                         null, tipoProceso, false
                );

                LOGGER.trace("Resultados: {}", dispensacionSolucionLazy.getTotalReg());
                status = Constantes.ACTIVO;

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam("status", status);
    }

    /**
     * Búsqueda de médicamentos para seleccionar los lotes, fabricantes y estabilidades
     * La búsqueda se realiza en los subalmacenes asépticos
     *
     * @param cadena
     * @return
     */
    public List<Medicamento_Extended> autoCompleteMedicamento(String cadena) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.autoCompleteMedicamento()");
        this.medicamentoList = new ArrayList<>();
        try {
            List<String> idEstructuraList = new ArrayList<>();
            idEstructuraList.addAll(solUtils.obtieneAlmacenesPorIdTipoSolucion(idTipoSolucion)) ;
            this.medicamentoList.addAll(medicamentoService.searchMedicamentoAutoCompleteMultipleAlm(cadena.trim(), idEstructuraList, this.activaAutoCompleteInsumos));

        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicamentos: {}", ex.getMessage());
        }
        return this.medicamentoList;
    }

    public void handleSelect(SelectEvent e) {
        this.medicamento = (Medicamento_Extended) e.getObject();
    }

    public void handleUnSelect() {
        this.medicamento = new Medicamento_Extended();
    }

    /**
     * Actualiza la lista de surtimiento de prescripción de soluciones con base
     * en el tab seleccionado
     *
     * @param evt
     */
    public void onTabChange(TabChangeEvent evt) {
// regla: Listar prescripciones solo con estatus de surtimiento surtido
        this.estatusSolucionLista = new ArrayList<>();
        String valorStatus = evt.getTab().getId();

        if (valorStatus.equalsIgnoreCase(Constantes.PROGRAMADA)) {
            this.mostrarImpresionEtiqueta = false;
            this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue());

        } else if (valorStatus.equalsIgnoreCase(Constantes.VALIDADA)) {
            this.mostrarImpresionEtiqueta = true;
            this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_VALIDADA.getValue());

        } else if (valorStatus.equalsIgnoreCase(Constantes.RECHAZADA)) {
            this.mostrarImpresionEtiqueta = false;
//            this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_RECHAZADA.getValue());
//            this.estatusSolucionLista.add(EstatusSolucion_Enum.CANCELADA.getValue());
            this.estatusSolucionLista.add(EstatusSolucion_Enum.MEZCLA_ERROR_AL_PREPARAR.getValue());
            this.estatusSolucionLista.add(EstatusSolucion_Enum.MEZCLA_RECHAZADA.getValue());
            this.estatusSolucionLista.add(EstatusSolucion_Enum.INSPECCION_NO_CONFORME.getValue());
            this.estatusSolucionLista.add(EstatusSolucion_Enum.ACONDICIONAMIENTO_NO_CONFORME.getValue());

        }
        obtenerSurtimientos();
    }

    public void onRowSelectSurtimiento(SelectEvent event) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.onRowSelectSurtimiento()");
        this.surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (this.surtimientoInsumoExtendedSelected != null) {
            this.elementoSeleccionado = true;
        }
    }

    public void onRowUnselectSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.onRowUnselectSurtimiento()");
        this.surtimientoInsumoExtendedSelected = null;
        this.elementoSeleccionado = false;
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción 13ENE2023
     * HHRC Se eliminan rutinas duplicadas Se agregan los datos nuevos
     * requeridos
     */
    public void verSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.verSurtimiento()");
        this.verAutorizacion = false;
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        this.xcantidad = 1;

        if (!this.permiso.isPuedeVer()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

        } else if (this.surtimientoExtendedSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (this.surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (this.surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (this.surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

//        } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

        } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())
                && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())) {
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

    public void obtenerDatosSurtimiento(String idSurtimiento) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerDatosSurtimiento()");
        existePrescripcion = false;
        diagnosticosList = new ArrayList<>();
        try {
            surtimientoExtendedSelected = this.solUtils.obtenerSurtimiento(idSurtimiento);
            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla no encontrado.", null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla no encontrado.", null);

            } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción de Mezcla no encontrad1.", null);

            } else {
                surtimientoExtendedSelected.setEstatusSurtimiento(EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento()).name());

                prescripcionSelected = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected.getIdPrescripcion());
                if (prescripcionSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción iválida. ", null);

                } else if (prescripcionSelected.getIdPacienteServicio() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Asignación de paciente a servicio no encontrado. ", null);

// RN para validar una prescripción debe estar programada
                } else if (prescripcionSelected.getIdEstatusPrescripcion().equals(EstatusPrescripcion_Enum.CANCELADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus de la prescripción no válido", null);

                } else {
                    existePrescripcion = true;
                    idTipoSolucion = prescripcionSelected.getIdTipoSolucion();
                    surtimientoExtendedSelected.setFolioPrescripcion(prescripcionSelected.getFolio());
                    surtimientoExtendedSelected.setTipoPrescripcion(prescripcionSelected.getTipoPrescripcion());
                    surtimientoExtendedSelected.setTipoConsulta(prescripcionSelected.getTipoConsulta());
                    surtimientoExtendedSelected.setIdEstructura(prescripcionSelected.getIdEstructura());

                    if (idTipoSolucion != null) {
                        surtimientoExtendedSelected.setIdTipoSolucion(idTipoSolucion);
                        TipoSolucion ts = this.solUtils.obtenerTipoSolucion(idTipoSolucion);
                        if (ts != null) {
                            surtimientoExtendedSelected.setTipoSolucion(ts.getClave());
                        }
                    }

                    PacienteUbicacion pu = this.solUtils.obtenerUbicacionRegistrada(prescripcionSelected.getIdPacienteUbicacion());
                    if (pu != null) {
                        surtimientoExtendedSelected.setIdCama(pu.getIdCama());
                        surtimientoExtendedSelected.setCama(pu.getIdCama());
                    }

                    PacienteServicio ps = this.solUtils.obtenerServicioRegistrada(prescripcionSelected.getIdPacienteServicio());
                    Visita visita = null;
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

                            this.diagnosticosList.addAll(
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
                                    surtimientoExtendedSelected.getIdEstatusSolucion().equals(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())) {
                                this.verAutorizacion = true;
                            }
                            viaAdministracion = this.solUtils.obtenerViaAdministracion(prescripcionSelected.getIdViaAdministracion());

                            surtimientoInsumoExtendedList = surtimientoInsumoService.obtenerListaByIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());

                            List<String> idEstructuraList = this.solUtils.obtieneAlmacenesPorIdTipoSolucion(idTipoSolucion);

                            try {
                                List<SurtimientoEnviado_Extend> seList;
                                for (SurtimientoInsumo_Extend item : this.surtimientoInsumoExtendedList) {
                                    boolean mayorCero = false;
                                    seList = surtimientoEnviadoService.detalleSurtimientoEnviadoXIdSurtimientoInsumo(item.getIdSurtimientoInsumo(), mayorCero);
                                    item.setSurtimientoEnviadoExtendList(seList);
                                    if (item.getIdInsumo() != null) {
                                        Inventario i = inventarioService.obtenerLoteSugerido(idEstructuraList, item.getIdInsumo());
                                        if (i != null) {
                                            item.setLoteSugerido(i.getLote());
                                        }
                                    }
                                }
                            } catch (Exception ex1) {
                                LOGGER.error("Error al buscar lote sugerido :: {} ", ex1.getMessage());
                            }

                            medico = this.solUtils.obtenerUsuarioPorId(prescripcionSelected.getIdMedico());
                            if (medico != null) {
                                surtimientoExtendedSelected.setNombreMedico(
                                        medico.getNombre() + " "
                                        + medico.getApellidoPaterno() + " "
                                        + medico.getApellidoMaterno());
                            }

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
     * Busca la clave delprotocolo
     *
     * @param idProtocolo
     * @return
     */
    private String obtenClaveProtocolo(Integer idProtocolo) {
        String clave = null;
        for (Protocolo proto : listaProtocolos) {
            if (proto.getIdProtocolo().equals(idProtocolo)) {
                clave = proto.getClaveProtocolo();
                break;
            }
        }
        return clave;
    }

    /**
     * Busca los Diagnósticos previamente registrados del paciente
     *
     * @param idPaciente
     * @param idVisita
     * @param idPrecripcion
     * @return
     */
    private List<Diagnostico> obtenerDisgnosticosPorIdPaciente(String idPaciente, String idVisita, String idPrecripcion) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerDisgnosticosPorIdPaciente()");
        List<Diagnostico> diagnList = new ArrayList<>();
        try {
            if (idPaciente != null && idVisita != null && idPrecripcion != null
                    && !idPaciente.isEmpty() && !idVisita.isEmpty() && !idPrecripcion.isEmpty()) {
                diagnList = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(idPaciente, idVisita, idPrecripcion);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener diagnósticos de paciente {} ", e.getMessage());
        }
        return diagnList;
    }

    /**
     * Consulta Diagnosticos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param cadena
     * @return
     */
    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.autocompleteDiagnostico()");
        List<Diagnostico> diagnList = new ArrayList<>();
        try {
            diagnList.addAll(diagnosticoService.obtenerListaAutoComplete(cadena));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener lista de diagnósticos {} ", ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagnList;
    }

    /**
     * Lee el codigo de barras de un medicamento y confirma la cantidad escaneda
     * para enviarse en el surtimento de prescripción
     *
     * @param e
     */
    public void validaLecturaPorCodigo(SelectEvent e) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.validaLecturaPorCodigo()");
// TODO : corregir el escaneo de 2 piezas
        boolean status = false;
        this.authorization = false;
        this.medicamento = (Medicamento_Extended) e.getObject();
        this.codigoBarras = CodigoBarras.generaCodigoDeBarras(this.medicamento.getClaveInstitucional(), this.medicamento.getLote(), this.medicamento.getFechaCaducidad(), null);
        try {
            if (this.codigoBarras == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                this.codigoBarras = "";
                this.medicamento = new Medicamento_Extended();
                return;
            } else if (this.codigoBarras.trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.codigoincorrecto"), null);
                this.codigoBarras = "";
                this.medicamento = new Medicamento_Extended();
                return;

            } else if (this.surtimientoExtendedSelected == null || this.surtimientoInsumoExtendedList == null
                    || this.surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surIncorrecto), null);
                this.codigoBarras = "";
                this.medicamento = new Medicamento_Extended();

            } else if (this.eliminaCodigoBarras) {
                status = eliminarLotePorCodigo();
                this.codigoBarras = "";
                this.medicamento = new Medicamento_Extended();

            } else {

                status = agregarLotePorCodigo();
            }

            if (this.eliminaCodigoBarras){
                surtimientoExtendedSelected.setInstruccionPreparacion(StringUtils.EMPTY);
                surtimientoExtendedSelected.setTempAmbiente(false);
                surtimientoExtendedSelected.setTempRefrigeracion(false);
                surtimientoExtendedSelected.setNoAgitar(false);
                surtimientoExtendedSelected.setProteccionLuz(false);
                
            } else {
                if (this.medicamento.getTipo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()
                        && !this.medicamento.isDiluyente()) {
// RN: Genera Instrucciones de Preparación
                    surtimientoExtendedSelected.setInstruccionPreparacion(this.solUtils.generaInstruccionPreparacion(surtimientoExtendedSelected, surtimientoInsumoExtendedList, viaAdministracion));

// RN: Valida estabilidad de diluyente
                    List<String> msjEstabilidades = this.solUtils.evaluaEstabilidadDiluyente(surtimientoInsumoExtendedList, viaAdministracion.getIdViaAdministracion());
                    for (String item : msjEstabilidades) {
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, item, null);
                    }
                    Integer idContenedor = null;
                    if(surtimientoExtendedSelected.getIdContenedor() != null)
                        idContenedor = surtimientoExtendedSelected.getIdContenedor();
// RN: Valida estabilidad de Reconsitución, condiciones de conservacion, almaceamiento y observaciones de preparación
                    msjEstabilidades = this.solUtils.evaluaEstabilidadReconstitucion(surtimientoInsumoExtendedList, viaAdministracion.getIdViaAdministracion(), idContenedor);
                    StringBuilder indicaciones = new StringBuilder();
                    for (String item : msjEstabilidades) {
    //                    Mensaje.showMessage(Constantes.MENSAJE_WARN, item, null);
                        if (indicaciones.length() > 0) {
                            indicaciones.append(StringUtils.LF);
                        }
                        indicaciones.append(item);

                        if (item.contains("Es fotosensible.")){
                            surtimientoExtendedSelected.setProteccionLuz(true);
                        }
                        if (item.contains("en temperatura ambiente.")){
                            surtimientoExtendedSelected.setTempAmbiente(true);
                        }
                        if (item.contains("en red fría.")){
                            surtimientoExtendedSelected.setTempRefrigeracion(true);
                        }
                        if (item.contains("No agitar.")){
                            surtimientoExtendedSelected.setNoAgitar(true);
                        }
                    }
                    if (indicaciones.length() > 0) {
                        surtimientoExtendedSelected.setIndicaciones(indicaciones.toString());
                    }
                }
            }
            
            this.codigoBarras = "";
            this.xcantidad = 1;
            this.eliminaCodigoBarras = false;
            this.medicamento = new Medicamento_Extended();

        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validaLecturaPorCodigo :: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private SurtimientoInsumo_Extend medicamentoEstaEnPrescritos(String claveInsumo) {
        for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
            if (item.getClaveInstitucional().trim().equals(claveInsumo)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Agrega un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean agregarLotePorCodigo() throws Exception {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.agregarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        Integer cantidadEscaneada = 0;
        Integer cantidadEnviada = 0;
        List<SurtimientoEnviado_Extend> surtimientoEnviadoExtenList;
        SurtimientoEnviado_Extend surtimientoEnviadoExtend;

        CodigoInsumo codigoInsumo = CodigoBarras.parsearCodigoDeBarras(this.codigoBarras);
        if (codigoInsumo == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);
            return res;

// Regla: No Permitir caducidades vencidas
        } else {

            Medicamento medto = null;
            try {
                medto = medicamentoService.obtenerMedicaByClave(codigoInsumo.getClave());
            } catch (Exception ex) {
                LOGGER.error("Error al buscar el medicamento por clave {} ", ex.getMessage());
            }

            if (medto == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Insumo no definido", null);

// Regla: Si es Medicamento valida fecha de caducidad
            } else if (medto.getTipo().equals(Constantes.MEDI) && new java.util.Date().after(codigoInsumo.getFecha())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.caducidadvencida")
                        + " " + codigoInsumo.getClave() + " - " + FechaUtil.formatoFecha(codigoInsumo.getFecha(), "dd/MM/yyyy"), null);

            } else {

// Alerta: Si es Medicamento notifica caducidad por vencer
                if (medto.getTipo().equals(Constantes.MEDI)
                        && FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(codigoInsumo.getFecha())) {
                    Mensaje.showMessage(Constantes.MENSAJE_WARN, RESOURCES.getString("sur.caducidadPorvencer")
                            + " " + codigoInsumo.getClave() + " - " + FechaUtil.formatoFecha(codigoInsumo.getFecha(), "dd/MM/yyyy"), null);
                }

                SurtimientoInsumo_Extend surInsumo = null;
                surInsumo = medicamentoEstaEnPrescritos(medto.getClaveInstitucional());
                if (surInsumo == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Insumo no definido", null);

                } else if (surInsumo != null) {

                    boolean insumoRegistrado = false;
// TODO revisar eliminación de asignación de otro idestructura
//                    this.surtimientoExtendedSelected.setIdEstructuraAlmacen(this.usuarioSelected.getIdEstructura());
                    for (SurtimientoInsumo_Extend surtimientoInsumo : this.surtimientoInsumoExtendedList) {
                        if (surInsumo.getIdInsumo().equals(surtimientoInsumo.getIdInsumo())) {
                            insumoRegistrado = true;
                            cantidadEscaneada = (this.xcantidad == null) ? 1 : xcantidad;

// Regla: factor multiplicador debe ser 1 o mayor
                            if (cantidadEscaneada < 1) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);

// Regla: solo acepta insumos si la clave escaneada existe en el detalle solicitado
                            } else if (!surtimientoInsumo.getClaveInstitucional().contains(codigoInsumo.getClave())) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.claveincorrecta"), null);

                            } else {
// Regla: solo acepta insumos si no esta bloqueado a nivel catálogo
                                if (!surtimientoInsumo.isMedicamentoActivo()) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.clavebloqueada")
                                            + " Insumo: " + codigoInsumo.getClave(), null);

                                } else {
                                    cantidadEnviada = (surtimientoInsumo.getCantidadEnviada() == null) ? 0 : surtimientoInsumo.getCantidadEnviada();
                                    cantidadEnviada = cantidadEnviada + cantidadEscaneada;

// Regla: no puede surtir insumos con cantidades de surtimiento mayor a la solicitada
                                    if (cantidadEnviada > surtimientoInsumo.getCantidadSolicitada()) {
                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.exedido")
                                                + " Insumo: " + codigoInsumo.getClave()
                                                + " Cant. Requerida: " + surtimientoInsumo.getCantidadSolicitada(), null);

                                    } else {
                                        Inventario inventarioSurtido = null;
                                        Integer cantidadXCaja = null;
                                        String claveProveedor = null;
                                        try {
// TODO: 01OCT2023 se modifica la consulta y se obtiene algo que ya se trae
                                            Inventario invTmp = new Inventario(medicamento.getIdInventario());
                                            inventarioSurtido = inventarioService.obtener(invTmp);
//                                            inventarioSurtido = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
//                                                    surtimientoInsumo.getIdInsumo(), this.surtimientoExtendedSelected.getIdEstructuraAlmacen(),
//                                                    codigoInsumo.getLote(), cantidadXCaja, claveProveedor, codigoInsumo.getFecha());
                                        } catch (Exception ex) {
                                            LOGGER.error("Error al obtener el inventario del insumo escaneado {} ", ex.getMessage());
                                        }

// Regla: Valida que el insumo a surtirse este disponible en el invetario en el almacen corresponndiente
                                        if (inventarioSurtido == null) {
                                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(this.surLoteIncorrecto)
                                                    + " " + codigoInsumo.getClave(), null);

                                        } else if (inventarioSurtido.getActivo() == 0) {
                                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado")
                                                    + " Insumo: " + codigoInsumo.getClave()
                                                    + " Lot: " + codigoInsumo.getLote(), null);

                                        } else if (inventarioSurtido.getCantidadActual() < cantidadEscaneada) {
                                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadinsuficiente")
                                                    + " Insumo: " + codigoInsumo.getClave()
                                                    + " Lot: " + codigoInsumo.getLote(), null);

                                        } else {
                                            Fabricante fabricante = new Fabricante();
                                            fabricante.setIdFabricante(inventarioSurtido.getIdFabricante());
                                            Fabricante fabricanteSelect = null;
                                            try {
                                                fabricanteSelect = fabricanteService.obtener(fabricante);
                                            } catch (Exception e) {
                                                LOGGER.error("Error al obtener el fabricante del insumo seleccionado del inventario {} ", e.getMessage());
                                            }

// Regla: Valida si se aplica la cantidad razonada
                                            if (this.sesion.isCantidadRazonada()) {
                                                this.authorization = validaCantidadRazonada(codigoInsumo, surtimientoInsumo, this.surtimientoExtendedSelected.getPacienteNumero());
                                                if (this.authorizado) {
                                                    surtimientoInsumo.setIdUsuarioAutCanRazn(this.usuarioSelected.getIdUsuario());
                                                }
                                            } else {
                                                this.authorization = true;
                                            }

                                            if (this.authorization) {
                                                surtimientoEnviadoExtenList = new ArrayList<>();
                                                if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
                                                    surtimientoEnviadoExtenList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());
                                                }

// Regla: si es primer Lote pistoleado solo muestra una linea en subdetalle
                                                if (surtimientoEnviadoExtenList.isEmpty()) {
                                                    surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                                    surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                                    surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                                    surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                                    surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                                    surtimientoEnviadoExtend.setLote(codigoInsumo.getLote());
                                                    surtimientoEnviadoExtend.setCaducidad(codigoInsumo.getFecha());
                                                    if (fabricanteSelect != null) {
                                                        surtimientoEnviadoExtend.setNombreFabricante(fabricanteSelect.getNombreFabricante());
                                                    }
                                                    surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());

                                                    surtimientoEnviadoExtend.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                                    surtimientoEnviadoExtend.setInsertFecha(new java.util.Date());
                                                    surtimientoEnviadoExtend.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

                                                    surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);

                                                } else {

                                                    boolean agrupaLoteSol = false;
                                                    for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtenList) {
// Regla: si se pistolea mas de un medicmento con el mismo lote se agrupan por lotes sumarizando las cantidades
                                                        if (surtimientoEnviadoRegistado.getLote().equals(codigoInsumo.getLote())
                                                                && surtimientoEnviadoRegistado.getCaducidad().equals(codigoInsumo.getFecha())
                                                                && surtimientoEnviadoRegistado.getIdInsumo().equals(inventarioSurtido.getIdInsumo())) {
                                                            Integer cantidadEnviadoSol = surtimientoEnviadoRegistado.getCantidadEnviado() + cantidadEscaneada;
                                                            surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadoSol);
                                                            agrupaLoteSol = true;
                                                            break;
                                                        }
                                                        if (inventarioSurtido.getIdInventario() != null
                                                                && surtimientoEnviadoRegistado.getIdInventarioSurtido() != null) {
                                                            Inventario invPrev = inventarioService.obtener(new Inventario(surtimientoEnviadoRegistado.getIdInventarioSurtido()));
                                                            Inventario invPost = inventarioService.obtener(new Inventario(inventarioSurtido.getIdInventario()));
                                                            
                                                            if (invPrev != null && invPost != null) {
                                                                if (!Objects.equals(invPrev.getIdFabricante(), invPost.getIdFabricante())) {
                                                                    Fabricante fabPrev = fabricanteService.obtener(new Fabricante(invPrev.getIdFabricante()));
                                                                    Fabricante fabPost = fabricanteService.obtener(new Fabricante(invPost.getIdFabricante()));
                                                                    String fab1 = (fabPrev != null) ? fabPrev.getNombreFabricante() : null;
                                                                    String fab2 = (fabPost != null) ? fabPost.getNombreFabricante() : null;
                                                                    
                                                                    Mensaje.showMessage(Constantes.MENSAJE_WARN, "Los fabricantes no pueden ser diferenntes : "
                                                                            + " " + fab1 + " " + fab2, null);
                                                                }
                                                            }
                                                        }
                                                    }

// Regla: si es el único Lote pistoleado agrega una linea nueva en subdetalle
                                                    if (!agrupaLoteSol) {
                                                        surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                                        surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                                        surtimientoEnviadoExtend.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                                        surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                                        surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);

                                                        surtimientoEnviadoExtend.setLote(codigoInsumo.getLote());
                                                        surtimientoEnviadoExtend.setCaducidad(codigoInsumo.getFecha());
                                                        surtimientoEnviadoExtend.setNombreFabricante(fabricanteSelect.getNombreFabricante());
                                                        surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());

                                                        surtimientoEnviadoExtend.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                                        surtimientoEnviadoExtend.setInsertFecha(new java.util.Date());
                                                        surtimientoEnviadoExtend.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

                                                        surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);
                                                    }
                                                }

//                                              surtimientoInsumo.setFechaEnviada(new java.util.Date());
//                                              surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                                surtimientoInsumo.setCantidadEnviada(cantidadEnviada);
                                                surtimientoInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtenList);
                                                if (Objects.equals(surtimientoInsumo.getCantidadSolicitada(), cantidadEnviada)) {
                                                    surtimientoInsumo.setRequiereJustificante(Constantes.INACTIVO);
                                                    surtimientoInsumo.setIdTipoJustificante(TipoJustificacion_Enum.COMPLETA.getValue());
                                                } else {
                                                    surtimientoInsumo.setRequiereJustificante(Constantes.ACTIVO);
                                                    surtimientoInsumo.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                                }
                                                res = Constantes.ACTIVO;
                                                this.msjMdlSurtimiento = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!insumoRegistrado) {
                        PrescripcionInsumo presIns = new PrescripcionInsumo();
                        presIns.setIdPrescripcionInsumo(Comunes.getUUID());
                        presIns.setIdPrescripcion(this.surtimientoExtendedSelected.getIdPrescripcion());
                        presIns.setIdInsumo(medto.getIdMedicamento());
                        presIns.setFechaInicio(this.surtimientoExtendedSelected.getFechaProgramada());
                        presIns.setDosis(new BigDecimal(cantidadEscaneada.toString()));
                        presIns.setFrecuencia(1);
                        presIns.setDuracion(1);
                        presIns.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
                        presIns.setIdEstatusMirth(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
                        presIns.setInsertFecha(new Date());
                        presIns.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

                        surInsumo = new SurtimientoInsumo_Extend();
                        surInsumo.setIdSurtimientoInsumo(Comunes.getUUID());
                        surInsumo.setIdSurtimiento(this.surtimientoExtendedSelected.getIdSurtimiento());
                        surInsumo.setIdPrescripcionInsumo(presIns.getIdPrescripcionInsumo());
                        surInsumo.setFechaProgramada(this.surtimientoExtendedSelected.getFechaProgramada());
                        surInsumo.setCantidadSolicitada(cantidadEscaneada);
                        surInsumo.setFechaEnviada(new java.util.Date());
                        surInsumo.setClaveInstitucional(medto.getClaveInstitucional());
                        surInsumo.setNombreCorto(medto.getNombreCorto());
                        surInsumo.setLoteSugerido(codigoInsumo.getLote());
                        surInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
//                        surInsumo.setIdEstatusMirth(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                        surInsumo.setIdUsuarioEnviada(this.usuarioSelected.getIdUsuario());
                        surInsumo.setCantidadEnviada(cantidadEscaneada);
                        surInsumo.setMedicamentoActivo(medto.getActivo() == Constantes.ACTIVOS ? true : false);
                        surInsumo.setIdInsumo(medto.getIdMedicamento());
                        surInsumo.setActivo(true);
                        surInsumo.setInsertFecha(new Date());
                        surInsumo.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

                        Inventario inventarioSurtido = null;
                        Integer cantidadXCaja = null;
                        String claveProveedor = null;
                        try {
                            inventarioSurtido = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                                    surInsumo.getIdInsumo(), usuarioSelected.getIdEstructura(),
                                    codigoInsumo.getLote(), cantidadXCaja, claveProveedor, codigoInsumo.getFecha());
                        } catch (Exception ex) {
                            LOGGER.error("Error al encontrar imvenntario surtido de material {} ", ex);
                        }
                        if (inventarioSurtido == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, medto.getClaveInstitucional() + ": Insumo sin existencias", null);

                        } else {
                            Fabricante fabricante = new Fabricante();
                            fabricante.setIdFabricante(inventarioSurtido.getIdFabricante());
                            Fabricante fabricanteSelect = null;
                            try {
                                fabricanteSelect = fabricanteService.obtener(fabricante);
                            } catch (Exception exce) {
                                LOGGER.error("Error al buscar el fabricante {} ", exce.getMessage());
                            }

                            try {
                                boolean insert = surtimientoInsumoService.insertarInsumo(presIns, surInsumo);
                                if (insert) {

                                    surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                                    surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                                    surtimientoEnviadoExtend.setIdSurtimientoInsumo(surInsumo.getIdSurtimientoInsumo());
                                    surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
                                    surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);
                                    surtimientoEnviadoExtend.setLote(codigoInsumo.getLote());
                                    surtimientoEnviadoExtend.setCaducidad(codigoInsumo.getFecha());
                                    surtimientoEnviadoExtend.setNombreFabricante(fabricanteSelect.getNombreFabricante());

                                    surtimientoEnviadoExtend.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                                    surtimientoEnviadoExtend.setInsertFecha(new java.util.Date());
                                    surtimientoEnviadoExtend.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

                                    surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());

                                    surtimientoEnviadoExtenList = new ArrayList<>();
                                    surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);

                                    surInsumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtenList);

                                    surtimientoInsumoExtendedList.add(surInsumo);

                                    msjMdlSurtimiento = true;
                                    res = Constantes.ACTIVO;
                                } else {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se pudo insertar el insumo: ", null);
                                }
                            } catch (Exception ex) {
                                LOGGER.error("Error al insertar el insumo.", ex);
                            }

//                            boolean agrupaLoteSol = false;
//                            for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtenList) {
//// Regla: si se pistolea mas de un medicmento con el mismo lote se agrupan por lotes sumarizando las cantidades
//                                if (surtimientoEnviadoRegistado.getLote().equals(codigoInsumo.getLote())
//                                        && surtimientoEnviadoRegistado.getCaducidad().equals(codigoInsumo.getFecha())
//                                        && surtimientoEnviadoRegistado.getIdInsumo().equals(inventarioSurtido.getIdInsumo())) {
//                                    Integer cantidadEnviadoSol = surtimientoEnviadoRegistado.getCantidadEnviado() + cantidadEscaneada;
//                                    surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadoSol);
//                                    agrupaLoteSol = true;
//                                    break;
//                                }
//                            }
//// Regla: si es el único Lote pistoleado agrega una linea nueva en subdetalle
//                            if (!agrupaLoteSol) {
//                                surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
//                                surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
//                                surtimientoEnviadoExtend.setIdSurtimientoInsumo(insumo.getIdSurtimientoInsumo());
//                                surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioSurtido.getIdInventario());
//                                surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);
//
//                                surtimientoEnviadoExtend.setLote(codigoInsumo.getLote());
//                                surtimientoEnviadoExtend.setCaducidad(codigoInsumo.getFecha());
//                                surtimientoEnviadoExtend.setNombreFabricante(fabricanteSelect.getNombreFabricante());
//                                surtimientoEnviadoExtend.setIdInsumo(inventarioSurtido.getIdInsumo());
//                                surtimientoEnviadoExtenList.add(surtimientoEnviadoExtend);
//                            }
//                        }
//                        insumo.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtenList);
                        }
                    }
                }
// Si es material de curación
            }
//            else if (medto.getTipo().equals(Constantes.MATC)) {
//                cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;
//                boolean insumoRegistrado = false;
//                
//                for (SurtimientoInsumo_Extend surtimientoInsumo : this.surtimientoInsumoExtendedList) {
//                    if (medto.getIdMedicamento().equals(surtimientoInsumo.getIdInsumo())) {
//                        insumoRegistrado = true;
//                        surtimientoEnviadoExtenList = new ArrayList<>();
//                        if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
//                            surtimientoEnviadoExtenList.addAll(surtimientoInsumo.getSurtimientoEnviadoExtendList());
//                        }
//                    }
//                }
//            }
        }

        return res;
    }

    /**
     * Método que valida la cantidad razonada
     *
     * @param codigoInsumo
     * @param surtimientoInsumo
     * @return
     */
    private boolean validaCantidadRazonada(CodigoInsumo codigoInsumo, SurtimientoInsumo_Extend surtimientoInsumo, String pacienteNumero) {
        LOGGER.error("mx.mc.magedbean.ValidacionSolucionMB.validaCantidadRazonada()");
        this.authorization = false;
        Integer cantidadEscaneada = 0;
        Integer cantidadEnviada = 0;

// Regla: cantidadRazonada Se verifica que el parametro de cantidad razonada este activo
        CantidadRazonada cantidadRazonadaSol = null;
        try {
            cantidadRazonadaSol = cantidadRazonadaService.cantidadRazonadaInsumo(codigoInsumo.getClave());
        } catch (Exception e) {
            LOGGER.error("Error al obtener la cantidad razonada del insumo seleccionado del inventario {} ", e.getMessage());
        }
        if (cantidadRazonadaSol != null && !authorization) {
            int totalDiaSol = 0;
            int totalMesSol = 0;
            int diasRestantes = 0;
            String ultimoSurtimiento = "";

            CantidadRazonadaExtended cantRaz = null;
            try {
                cantRaz = cantidadRazonadaService.cantidadRazonadaInsumoPaciente(pacienteNumero, surtimientoInsumo.getIdInsumo());
            } catch (Exception e) {
                LOGGER.error("Error al obtener el paciente {} ", e.getMessage());
            }
            if (cantRaz != null) {
                totalDiaSol = cantRaz.getTotalSurtDia();
                totalMesSol = cantRaz.getTotalSurtMes();
                diasRestantes = cantRaz.getDiasRestantes();
                ultimoSurtimiento = cantRaz.getUltimoSurtimiento().toString();
            }

//Consulta Interna
            if (this.surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_INTERNA.getValue())) {
                cantidadEnviada = cantidadEnviada + totalDiaSol;
                if (cantidadEnviada > cantidadRazonadaSol.getCantidadDosisUnitaria()) {
                    exist = false;
                    msjMdlSurtimiento = false;
                    msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/> <b>"
                            + codigoInsumo.getClave() + "</b> La cantidad del Medicamento debe ser menor o igual a <b>" + cantidadRazonadaSol.getCantidadDosisUnitaria() + "</b>, se solicita <b>" + cantidadEnviada + "</b>";

                    PrimeFaces.current().executeScript("PF('modalAlertaAutorizacion').show();");
                    codigoBarrasAutorizte = codigoBarras;
                    xcantidadAutorizte = cantidadEscaneada;
                    codigoBarras = "";
                    medicamento = new Medicamento_Extended();
                } else {
                    authorization = true;
                }

//Consulta Externa
            } else if (this.surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue())) {
                cantidadEnviada = cantidadEnviada + totalMesSol;
                if (cantidadEnviada > cantidadRazonadaSol.getCantidadPresentacionComercial()) {
                    exist = false;
                    msjMdlSurtimiento = false;
                    msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta\n"
                            + codigoInsumo.getClave() + " El Medicamento solo puede surtirse cada " + cantidadRazonadaSol.getPeriodoPresentacionComercial() + " días, faltan " + diasRestantes + ", ultimo surtimiento: " + ultimoSurtimiento;

                    PrimeFaces.current().executeScript("PF('modalAlertaAutorizacion').show();");
                    codigoBarrasAutorizte = codigoBarras;
                    xcantidadAutorizte = cantidadEscaneada;
                    codigoBarras = "";
                    medicamento = new Medicamento_Extended();
                } else {
                    authorization = true;
                }
            }
        }
        return authorization;
    }

    /**
     * elimina un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean eliminarLotePorCodigo() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.eliminarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigoBarras);
            Medicamento meto = medicamentoService.obtenerMedicaByClave(ci.getClave());
            if (ci == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.parser"), null);

            } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);

            } else {

                List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
                Integer cantidadEscaneada = 0;
                Integer cantidadEnviada = 0;
                for (SurtimientoInsumo_Extend surtimientoInsumoSol : surtimientoInsumoExtendedList) {

// Regla: puede escanear medicamentos mientras la clave escaneada exista en el detalle solicitado
                    if (surtimientoInsumoSol.getClaveInstitucional().contains(ci.getClave())) {
                        encontrado = Constantes.ACTIVO;

                        cantidadEscaneada = (xcantidad == null) ? 1 : xcantidad;

// Regla: factor multiplicador debe ser 1 o mayor
                        if (cantidadEscaneada < 1) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadincorrecta"), null);

// Regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                        } else {

                            cantidadEnviada = (surtimientoInsumoSol.getCantidadEnviada() == null) ? 0 : surtimientoInsumoSol.getCantidadEnviada();
                            cantidadEnviada = cantidadEnviada - cantidadEscaneada;
                            cantidadEnviada = (cantidadEnviada < 0) ? 0 : cantidadEnviada;

                            surtimientoEnviadoExtendList = new ArrayList<>();
                            if (surtimientoInsumoSol.getSurtimientoEnviadoExtendList() == null) {
                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);

                            } else {
                                surtimientoEnviadoExtendList.addAll(surtimientoInsumoSol.getSurtimientoEnviadoExtendList());

// Regla: el lote a eliminar del surtimiento ya debió ser escaneado para eliminaro
                                if (surtimientoEnviadoExtendList.isEmpty()) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotesinescanear"), null);

                                } else {

                                    Integer cantidadEnviadaPorLote = 0;
                                    for (SurtimientoEnviado_Extend surtimientoEnviadoRegistado : surtimientoEnviadoExtendList) {

// Regla: si el lote escaneado ya ha sido agregado se descuentan las cantidades
                                        if (surtimientoEnviadoRegistado.getLote().equals(ci.getLote())
                                                && surtimientoEnviadoRegistado.getCaducidad().equals(ci.getFecha())) {

                                            cantidadEnviadaPorLote = surtimientoEnviadoRegistado.getCantidadEnviado() - cantidadEscaneada;
                                            cantidadEnviadaPorLote = (cantidadEnviadaPorLote < 0) ? 0 : cantidadEnviadaPorLote;
                                            if (cantidadEnviadaPorLote < 1) {
                                                surtimientoEnviadoExtendList.remove(surtimientoEnviadoRegistado);
                                            } else {
                                                surtimientoEnviadoRegistado.setCantidadEnviado(cantidadEnviadaPorLote);
                                            }
                                            break;
                                        }
                                    }

                                    surtimientoInsumoSol.setFechaEnviada(new java.util.Date());
                                    surtimientoInsumoSol.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                    surtimientoInsumoSol.setCantidadEnviada(cantidadEnviada);
                                    surtimientoInsumoSol.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                    if (Objects.equals(surtimientoInsumoSol.getCantidadSolicitada(), surtimientoInsumoSol.getCantidadEnviada())) {
                                        surtimientoInsumoSol.setRequiereJustificante(Constantes.INACTIVO);
                                        surtimientoInsumoSol.setIdTipoJustificante(null);
                                    } else {
                                        surtimientoInsumoSol.setRequiereJustificante(Constantes.ACTIVO);
                                        surtimientoInsumoSol.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                                    }

                                    if (surtimientoEnviadoExtendList.isEmpty() && meto.getTipo() == Constantes.MATC) {
                                        boolean delete = false;
                                        SurtimientoInsumo insumo = new SurtimientoInsumo();
                                        insumo.setIdSurtimientoInsumo(surtimientoInsumoSol.getIdSurtimientoInsumo());
                                        PrescripcionInsumo item = new PrescripcionInsumo();
                                        item.setIdPrescripcionInsumo(surtimientoInsumoSol.getIdPrescripcionInsumo());

                                        delete = surtimientoInsumoService.eliminarInsumo(item, insumo);
                                        if (delete) {
                                            delete = surtimientoInsumoExtendedList.remove(surtimientoInsumoSol);
                                        }
                                    }

                                    res = Constantes.ACTIVO;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo eliminarLotePorCodigo :: ", ex);
        }
        if (!encontrado) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.claveincorrecta"), null);
        }
        return res;
    }

    /**
     * Obtener la clave de la solución que es un nuevo insumo
     *
     * @param surtimientoExtendedSelected
     * @return
     */
    private String obtenerClaveSolucion(Surtimiento_Extend surtimientoExtendedSelected) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.obtenerClaveSolucion()");
        String res = null;
        try {
            String folio = surtimientoExtendedSelected.getFolio();
            String complementoLote = "LT";
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
// Es la fecha de cuando se surte y no de la prescripcion/surtimiento
            String fechaProg = dateFormat.format(new Date());
            String lote = complementoLote.concat(fechaProg);

//Genera caducidad de clve Agrupada, la cual se le agrega 1 dia mas.
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(surtimientoExtendedSelected.getFechaProgramada());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            res = CodigoBarras.generaCodigoDeBarras(folio, lote, calendar.getTime(), null);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerClaveSolucion {} ", e.getMessage());
        }
        return res;
    }

    /**
     * Valida el surtimiento al momento de validar
     */
    public void validaSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.validaSurtimiento()");
        boolean status = Constantes.INACTIVO;
        try {

            if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else if (surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surInvalido), null);

            } else {

                Integer numeroMedicamentosSurtidos = 0;
                List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();

                Inventario inventarioAfectadoSol;
                List<Inventario> inventarioList = new ArrayList<>();

                List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
                MovimientoInventario movimientoInventarioAfectadoSol;

                for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientoInsumoExtendedList) {
                    if (surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList() != null && !surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList().isEmpty()) {
                        if (surtimientoInsumo_Ext.getCantidadEnviada().intValue()
                                != surtimientoInsumo_Ext.getCantidadSolicitada().intValue() && surtimientoInsumo_Ext.getIdTipoJustificante() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("dispensacion.err.surtmedicamento"), null);
                            return;
                        }
                        Inventario inventarioSurtido;
                        for (SurtimientoEnviado_Extend surtimientoEnviado_Ext : surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList()) {
                            if (surtimientoEnviado_Ext.getIdInventarioSurtido() != null) {

                                inventarioSurtido = inventarioService.obtener(new Inventario(surtimientoEnviado_Ext.getIdInventarioSurtido()));

                                if (inventarioSurtido == null) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surLoteIncorrecto), null);
                                    return;

                                } else if (inventarioSurtido.getActivo() != 1) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.lotebloqueado"), null);
                                    return;

                                } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(inventarioSurtido.getFechaCaducidad())) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surCaducidadvencida), null);
                                    return;

                                } else if (inventarioSurtido.getCantidadActual() < surtimientoEnviado_Ext.getCantidadEnviado()) {
                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.cantidadinsuficiente"), null);
                                    return;

                                } else {

                                    numeroMedicamentosSurtidos++;

                                    inventarioAfectadoSol = new Inventario();
                                    inventarioAfectadoSol.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    inventarioAfectadoSol.setCantidadActual(surtimientoEnviado_Ext.getCantidadEnviado());
                                    inventarioList.add(inventarioAfectadoSol);

                                    movimientoInventarioAfectadoSol = new MovimientoInventario();
                                    movimientoInventarioAfectadoSol.setIdMovimientoInventario(Comunes.getUUID());
                                    Integer idTipoMotivo = TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue();
                                    movimientoInventarioAfectadoSol.setIdTipoMotivo(idTipoMotivo);
                                    movimientoInventarioAfectadoSol.setFecha(new java.util.Date());
                                    movimientoInventarioAfectadoSol.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
                                    movimientoInventarioAfectadoSol.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                    movimientoInventarioAfectadoSol.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                    movimientoInventarioAfectadoSol.setIdInventario(surtimientoEnviado_Ext.getIdInventarioSurtido());
                                    movimientoInventarioAfectadoSol.setCantidad(surtimientoEnviado_Ext.getCantidadEnviado());
                                    movimientoInventarioAfectadoSol.setFolioDocumento(surtimientoExtendedSelected.getFolio());
                                    movimientoInventarioList.add(movimientoInventarioAfectadoSol);

                                }
                            }

                            surtimientoEnviado_Ext.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtimientoEnviado_Ext.setInsertFecha(new java.util.Date());
                            surtimientoEnviado_Ext.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                            if (surtimientoEnviado_Ext.getCantidadRecibido() == null) {
                                surtimientoEnviado_Ext.setCantidadRecibido(0);
                            }
                            surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviado_Ext);
                        }
                    }
                    SurtimientoInsumo surtimientoInsumoSol = new SurtimientoInsumo();
                    surtimientoInsumoSol.setIdSurtimientoInsumo(surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                    surtimientoInsumoSol.setIdUsuarioAutCanRazn(surtimientoInsumo_Ext.getIdUsuarioAutCanRazn());
                    surtimientoInsumoSol.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    surtimientoInsumoSol.setUpdateFecha(new java.util.Date());
                    surtimientoInsumoSol.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    surtimientoInsumoSol.setFechaEnviada(surtimientoInsumo_Ext.getFechaEnviada());
                    surtimientoInsumoSol.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                    surtimientoInsumoSol.setCantidadEnviada(surtimientoInsumo_Ext.getCantidadEnviada());
                    surtimientoInsumoSol.setCantidadVale(0);
                    surtimientoInsumoSol.setIdTipoJustificante(surtimientoInsumo_Ext.getIdTipoJustificante());
                    surtimientoInsumoSol.setNotas(surtimientoInsumo_Ext.getNotas());
                    surtimientoInsumoList.add(surtimientoInsumoSol);

                }
                //Se Valida que se deban surtir cada uno de los Insumos que conforman la solución
                if (numeroMedicamentosSurtidos != surtimientoInsumoExtendedList.size()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error"), null);
                    return;

                } else {
                    surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                    surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
                    surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    Surtimiento surtimiento = (Surtimiento) surtimientoExtendedSelected;

                    String claveAgrupada = obtenerClaveSolucion(surtimientoExtendedSelected);

                    status = surtimientoService.surtirPrescripcion(surtimiento, surtimientoInsumoList, surtimientoEnviadoList, inventarioList, movimientoInventarioList);

                    if (status) {
                        status = surtimientoService.actualizarClaveSolucionSurtimietoByFolio(surtimientoExtendedSelected.getFolio(), claveAgrupada);
                    }

                    if (status) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("sur.exitoso"), "");
                        for (Surtimiento_Extend item : surtimientoExtendedList) {
                            if (item.getFolio().equals(surtimientoExtendedSelected.getFolio())) {
                                surtimientoExtendedList.remove(item);
                                break;
                            }
                        }
                        imprimirSurtimiento();
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("sur.error"), null);
                    }
                    vincularPrescripcionCapsula();
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Valida que los surtimientos estén llenos
     *
     * @param listaSurtimientos
     * @return
     */
    private boolean isSurtimientoCompleto(List<SurtimientoInsumo_Extend> listaSurtimientos) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.isSurtimientoCompleto()");
        boolean res = false;
        try {
            if (listaSurtimientos != null) {
                if (!listaSurtimientos.isEmpty()) {
                    Integer cantSolicitada;
                    Integer cantSurtidaTotal;
                    for (SurtimientoInsumo_Extend se : listaSurtimientos) {
                        cantSurtidaTotal = 0;
                        if (se.getSurtimientoEnviadoExtendList() == null) {
                            return false;

                        } else {
                            if (se.getSurtimientoEnviadoExtendList().isEmpty()) {
                                return false;

                            } else {
                                cantSolicitada = se.getCantidadSolicitada();
                                for (SurtimientoEnviado_Extend see : se.getSurtimientoEnviadoExtendList()) {
                                    cantSurtidaTotal += see.getCantidadEnviado();
                                }
                                if (cantSolicitada != cantSurtidaTotal) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                return true;
            }

        } catch (Exception e) {
            LOGGER.error("Error al validar los insumos de la solución. {} ", e.getMessage());
        }
        return res;
    }

    /**
     * Valida los campos mandatorios de una orden de preparación para
     * posteriormente mostrar un dialogo para validación de; Eventos adversos,
     * Indicación terapéutica, Protocolos Oncológicos, entre otras
     */
    public void validaSolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.validaSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (!permiso.isPuedeCrear() && !permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

            } else if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surSinAlmacen), null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else if (surtimientoExtendedSelected.getFolioPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errRegistroIncorrecto), null);

            } else if (idTipoSolucion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione el Tipo de Solución", null);

            } else if (viaAdministracion == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione la vía de Adminitración", null);

            } else if (viaAdministracion.getIdViaAdministracion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione la vía de Adminitración", null);

            } else if (surtimientoExtendedSelected.getIdContenedor() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione el contenedor de la mezcla", null);

            } else if (surtimientoExtendedSelected.getVolumenTotal() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el volumen total de la mezcla", null);

            } else if (surtimientoExtendedSelected.getVolumenTotal().compareTo(BigDecimal.ZERO) == -1
                    || surtimientoExtendedSelected.getVolumenTotal().compareTo(BigDecimal.ZERO) == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el volumen total de la mezcla", null);

            } else if (surtimientoExtendedSelected.isPerfusionContinua()
                    && (surtimientoExtendedSelected.getVelocidad() == null || surtimientoExtendedSelected.getVelocidad() <= 0 ) ) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese la velocidad de infusión", null);
//
//            } else if (surtimientoExtendedSelected.isPerfusionContinua()
//                    && surtimientoExtendedSelected.getRitmo() <= 0) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el ritmo de infusión", null);
//                return;

            } else if (isOncologica && surtimientoExtendedSelected.getIdProtocolo() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un protocolo oncológico", null);

// TODO : corregir la validación de protocolo
//            } else if (isOncologica && diagnosticoSelected == null) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione el diagnóstico relacionado al protocolo oncológico", null);
//
//            } else if (isOncologica && diagnosticoSelected.getIdDiagnostico() == null) {
//                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione el diagnóstico relacionado al protocolo oncológico", null);

            } else if (surtimientoInsumoExtendedList == null || surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se requieren insumos para elaboración de la mezcla", null);

            } else if (!isSurtimientoCompleto(surtimientoInsumoExtendedList)) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Indicar cantidad, lote y fabricante de los insumos de la mezcla", null);

            } else if (surtimientoExtendedSelected.getInstruccionPreparacion() == null
                    || surtimientoExtendedSelected.getInstruccionPreparacion().trim().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Capture las Instrucciones de preparación", null);

            } else if (validaDiagnosticoProtocolo()) {
                /*
                for (SurtimientoInsumo_Extend insumo : surtimientoInsumoExtendedList) {
                    if (insumo.getSurtimientoEnviadoExtendList() == null || insumo.getSurtimientoEnviadoExtendList().isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Faltan insumos por confirmar", null);
                        return;
                    } else {
                        int sum = 0;
                        for (SurtimientoEnviado_Extend item : insumo.getSurtimientoEnviadoExtendList()) {
                            sum += item.getCantidadEnviado();
                        }
                        if (sum < insumo.getCantidadSolicitada() && insumo.getIdTipoJustificante() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Faltan insumos por confirmar", null);
                            return;
                        }
                    }

                }*/

                Visita visita = new Visita();
                if (surtimientoExtendedSelected.getIdPaciente() != null
                        && !surtimientoExtendedSelected.getIdPaciente().equals("")) {
                    Paciente patient = new Paciente();
                    patient.setIdPaciente(surtimientoExtendedSelected.getIdPaciente());
                    Paciente paciente = pacienteService.obtener(patient);
                    surtimientoExtendedSelected.setPacienteNumero(paciente.getPacienteNumero());

                    Visita visit = new Visita();
                    visit.setIdPaciente(surtimientoExtendedSelected.getIdPaciente());
                    visita = visitaService.obtenerVisitaAbierta(visit);
                }

                ParamBusquedaAlertaDTO alertaDTO = new ParamBusquedaAlertaDTO();
                alertaDTO.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
                alertaDTO.setNumeroPaciente(surtimientoExtendedSelected.getPacienteNumero());
                alertaDTO.setNumeroVisita(visita.getNumVisita());
                alertaDTO.setNumeroMedico(surtimientoExtendedSelected.getCedProfesional());
                if (surtimientoExtendedSelected.getTipoSolucion().equals("ONC")) {
                    alertaDTO.setEsSolucionOncologica(Constantes.ACTIVO);
                }
                List<Diagnostico> listDiagnostico = diagnosticoService.obtenerPorIdPacienteIdVisitaIdPrescripcion(visita.getIdPaciente(), visita.getIdVisita(), surtimientoExtendedSelected.getIdPrescripcion());
                List<String> diagnosticosIdLista = new ArrayList<>();
                for (Diagnostico diag : listDiagnostico) {
                    diagnosticosIdLista.add(diag.getIdDiagnostico());
                }
                alertaDTO.setListaDiagnosticos(diagnosticosIdLista);

                List<MedicamentoDTO> listaMedicametos = new ArrayList<>();
                for (SurtimientoInsumo_Extend insumo : surtimientoInsumoExtendedList) {
                    if (insumo.getTipoInsumo() == CatalogoGeneral_Enum.MEDICAMENTO.getValue()) {
                        MedicamentoDTO item = new MedicamentoDTO();
                        item.setClaveMedicamento(insumo.getClaveInstitucional());
                        item.setDosis(insumo.getDosis());
                        item.setDuracion(insumo.getDuracion());
                        item.setFrecuencia(insumo.getFrecuencia());

                        listaMedicametos.add(item);
                    }
                }
                alertaDTO.setListaMedicametos(listaMedicametos);
                alertaDTO.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
                ObjectMapper Obj = new ObjectMapper();
                String json = Obj.writeValueAsString(alertaDTO);
                ReaccionesAdversas cs = new ReaccionesAdversas(servletContext);
                Response respMus = cs.validaFarmacoVigilancia(json);

                if (respMus != null) {
                    if (respMus.getStatus() == 200) {
                        alertasDTO = parseResponseDTO(respMus.getEntity().toString());
                        status = true;
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);
                }
            }
            
// RN: Valida estabilidad de diluyente
            List<String> msjEstabilidades = this.solUtils.evaluaEstabilidadDiluyente(surtimientoInsumoExtendedList, viaAdministracion.getIdViaAdministracion());
            for (String item : msjEstabilidades) {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, item, null);
            }
            Integer idContenedor = null;
            if(surtimientoExtendedSelected.getIdContenedor() != null)
                idContenedor = surtimientoExtendedSelected.getIdContenedor();
// RN: Valida estabilidades
            msjEstabilidades = this.solUtils.evaluaEstabilidadReconstitucion(
                    surtimientoInsumoExtendedList, viaAdministracion.getIdViaAdministracion(), idContenedor);
            for (String item : msjEstabilidades) {
                Mensaje.showMessage(Constantes.MENSAJE_WARN, item, null);
                
            }            

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al validar la solucion:", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Obtiene el detalle del protocolo
     *
     * @param protocoloSelected
     */
    public void obtenerDetalle(ProtocoloExtended protocoloSelected) {
        try {

            if (protocoloSelected != null) {
                if (protocoloSelected.getListaDetalleValidacionSolucion() != null) {
                    if (!protocoloSelected.getListaDetalleValidacionSolucion().isEmpty()) {
                        listaDetalleProtocolos = protocoloSelected.getListaDetalleValidacionSolucion();
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar el detalle del registro de protocolo  " + ex.getMessage());
        }
    }

    public void continuarValidacionSolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.continuarValidacionSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (surtimientoExtendedSelected != null) {
                surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                surtimientoExtendedSelected.setUpdateFecha(new Date());
                surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                if (surtimientoExtendedSelected.getIdCama() != null){
                    Cama c = camaService.obtenerCama(surtimientoExtendedSelected.getIdCama());
                    if (c != null) {
                        surtimientoExtendedSelected.setCama(c.getNombreCama());
                    }
                }
                Solucion o = new Solucion();
                o.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                Solucion solucion = solucionService.obtener(o);
                boolean solicionRegistrada = false;
                if (solucion == null) {
                    solucion.setIdSolucion(Comunes.getUUID());
                    solucion.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                    solucion.setInsertFecha(new Date());
                    solucion.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                } else {
                    solucion.setUpdateFecha(new Date());
                    solucion.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    solicionRegistrada = true;
                }
                solucion.setIdEnvaseContenedor(surtimientoExtendedSelected.getIdContenedor());
                solucion.setMuestreo(muestreo ? 1 : 0);

                solucion.setObservaciones(surtimientoExtendedSelected.getObservaciones());
                solucion.setVolumen(surtimientoExtendedSelected.getVolumenTotal());
//                solucion.setObservaciones(surtimientoExtendedSelected.getInstruccionPreparacion());

                solucion.setIdEstatusSolucion(EstatusSolucion_Enum.OP_VALIDADA.getValue());
                solucion.setProteccionLuz((surtimientoExtendedSelected.isProteccionLuz()) ? 1 : 0);
                solucion.setProteccionTemp((surtimientoExtendedSelected.isTempAmbiente()) ? 1 : 0);
                solucion.setProteccionTempRefrig((surtimientoExtendedSelected.isTempRefrigeracion()) ? 1 : 0);
                solucion.setNoAgitar((surtimientoExtendedSelected.isNoAgitar()) ? 1 : 0);
                if (viaAdministracion != null) {
                    solucion.setIdViaAdministracion(viaAdministracion.getIdViaAdministracion());
                }
                solucion.setEdadPaciente(surtimientoExtendedSelected.getEdadPaciente());
                solucion.setPesoPaciente(surtimientoExtendedSelected.getPesoPaciente());
                solucion.setTallaPaciente(surtimientoExtendedSelected.getTallaPaciente());
                solucion.setAreaCorporal(surtimientoExtendedSelected.getAreaCorporal());
                solucion.setPerfusionContinua((surtimientoExtendedSelected.isPerfusionContinua()) ? 1 : 0);
                solucion.setVelocidad(surtimientoExtendedSelected.getVelocidad());
                solucion.setRitmo(surtimientoExtendedSelected.getRitmo());
                solucion.setIdProtocolo(surtimientoExtendedSelected.getIdProtocolo());
                solucion.setIdDiagnostico(surtimientoExtendedSelected.getIdDiagnostico());
                solucion.setUnidadConcentracion(surtimientoExtendedSelected.getUnidadConcentracion());
                solucion.setIdUsuarioValida(this.usuarioSelected.getIdUsuario());
                solucion.setInstruccionesPreparacion(surtimientoExtendedSelected.getInstruccionPreparacion());
                solucion.setObservaciones(surtimientoExtendedSelected.getObservaciones());
                
                solucion.setProteccionLuz((surtimientoExtendedSelected.isProteccionLuz()) ? 1 :0);
                solucion.setProteccionTemp((surtimientoExtendedSelected.isTempAmbiente() )? 1 :0);
                solucion.setProteccionTempRefrig((surtimientoExtendedSelected.isTempRefrigeracion() )? 1 :0);
                solucion.setNoAgitar((surtimientoExtendedSelected.isNoAgitar() ) ? 1 :0);
                
                solucion.setFechaValida(new java.util.Date());
                solucion.setIndicaciones(surtimientoExtendedSelected.getIndicaciones());

                List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();

                for (SurtimientoInsumo_Extend insumo : surtimientoInsumoExtendedList) {
                    insumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                    insumo.setInsertFecha(new java.util.Date());
                    insumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    insumo.setUpdateFecha(new java.util.Date());
                    insumo.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                    if (insumo.getSurtimientoEnviadoExtendList() != null && !insumo.getSurtimientoEnviadoExtendList().isEmpty()) {
                        for (SurtimientoEnviado_Extend surtimientoEnviado : insumo.getSurtimientoEnviadoExtendList()) {
                            surtimientoEnviado.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                            surtimientoEnviado.setUpdateFecha(new java.util.Date());
                            surtimientoEnviado.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                            if (surtimientoEnviado.getCantidadRecibido() == null) {
                                surtimientoEnviado.setCantidadRecibido(0);
                            }
                            surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviado);
                        }
                    }
                    surtimientoInsumoList.add(insumo);
                }
                List<DiagnosticoPaciente> listaDiagPaciente = new ArrayList<>();
                DiagnosticoPaciente diagnosticoPaciente;
                if (!diagnosticosList.isEmpty()) {
                    for (Diagnostico diagnostico : diagnosticosList) {
                        diagnosticoPaciente = new DiagnosticoPaciente();
                        diagnosticoPaciente.setIdDiagnosticoPaciente(Comunes.getUUID());
                        diagnosticoPaciente.setIdPrescripcion(surtimientoExtendedSelected.getIdPrescripcion());
                        diagnosticoPaciente.setFechaDiagnostico(new Date());
                        diagnosticoPaciente.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                        diagnosticoPaciente.setIdDiagnostico(diagnostico.getIdDiagnostico());
                        diagnosticoPaciente.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                        diagnosticoPaciente.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                        diagnosticoPaciente.setInsertFecha(new Date());

                        listaDiagPaciente.add(diagnosticoPaciente);
                    }
                }

                status = surtimientoService.actualizarInsumosValidacion(solucion, surtimientoExtendedSelected,
                        surtimientoInsumoList, surtimientoEnviadoList, listaDiagPaciente, solicionRegistrada);
                if (status) {
                    if (this.enviaCorreoalValidarMezcla) {
                        Prescripcion p = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
                        Usuario u = null;
                        if (p != null) {
                            String IdUsuario = p.getIdMedico();
                            u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                            if (u != null) {
                                String asunto = "Orden de preparación " + surtimientoExtendedSelected.getFolio() + " Validada. ";
                                String msj = "Orden de preparación validada. ";
                                this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                            }
                        }
                    }
                    msjMdlSurtimiento = Constantes.INACTIVO;
                    previewPrint(surtimientoExtendedSelected);
                    idEnvase = null;
                    muestreo = Constantes.INACTIVO;
                    conservacion = "";
                    volumenTotal = BigDecimal.ZERO;
                    observaciones = "";
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ocurrio un error en: continuarValidacionSolucion ", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Permite rezchazar una mezcla
     */
    public void rechazarValidacionSolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.rechazarValidacionSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

            } else if (!this.permiso.isPuedeEditar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Mezcla inválido.", null);

            } else  if(solucion == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla inválida.", null);

            } else  if(solucion.getIdMotivoRechazo() == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre motivo de rechazo.", null);

            } else  if(solucion.getComentariosRechazo() == null || solucion.getComentariosRechazo().trim().isEmpty()){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre comentarios de rechazo.", null);
                
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
                if (o == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);
                
                } else if (!Objects.equals(o.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Mezcla inválido.", null);
                
                } else {
                    Solucion s = new Solucion();
                    s.setIdSolucion(o.getIdSolucion());
                    s.setUpdateFecha(new java.util.Date());
                    s.setFechaValPrescr(new java.util.Date());
                    s.setIdEstatusSolucion(EstatusSolucion_Enum.OP_RECHAZADA.getValue());
                    s.setIdUsuarioValOrdenPrep(usuarioSelected.getIdUsuario());
                    s.setComentariosRechazo(solucion.getComentariosRechazo());
                    s.setIdMotivoRechazo(solucion.getIdMotivoRechazo());
//                    s.setProteccionLuz(surtimientoExtendedSelected.isProteccionLuz() ? 1 : null);
//                    s.setProteccionTemp((surtimientoExtendedSelected.isTempAmbiente() ) ? 0: null);
//                    s.setProteccionTempRefrig((surtimientoExtendedSelected.isTempRefrigeracion()) ? 1 : null);
//                    s.setNoAgitar((surtimientoExtendedSelected.isNoAgitar()) ? 1 : null);
                    status = solucionService.rechazarMezcla(s, surtimiento, surtimientoInsumoExtendedList, false);
                }

                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Orden de Preparación Rechazada.", null);

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
                        String IdUsuario = o.getIdUsuarioValPrescr();
                        u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                        if (u != null) {
                            String asunto = "Orden de Preparación de mezcla " + surtimientoExtendedSelected.getFolio() + " Rechazada. ";
                            String mensajeRechazo = "\n\n<br/><br/>Usuario Rechaza: " + usuarioSelected.getNombre() + " " + usuarioSelected.getApellidoPaterno() + " " + usuarioSelected.getApellidoMaterno();
                            mensajeRechazo = mensajeRechazo + "\n\n<br/><br/>Comentarios: " + solucion.getComentariosRechazo() ;
                            String msj = mensajeRechazo;
                            this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ocurrio un error en: rechazarValidacionSolucion", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    /**
     * Permite cancelar una mezcla
     */
    public void cancelarSolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.cancelarSolucion()");
        boolean status = Constantes.INACTIVO;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

            } else if (!this.permiso.isPuedeAutorizar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de Mezcla inválido.", null);

            } else if (!Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())
                && !Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Estatus de Mezcla inválido.", null);

            } else  if(solucion == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla inválida.", null);

            } else  if(solucion.getIdMotivoCancelacion() == null){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre motivo de cancelación.", null);

            } else  if(Objects.equals(solucion.getIdMotivoCancelacion(), MotivoCancelacionRechazoSolucion_Enum.OTRO.getValue())
                    && solucion.getMotivoCancela().trim().isEmpty()){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre cuál motivo de cancelación.", null);

            } else  if(solucion.getComentariosCancelacion() == null || solucion.getComentariosCancelacion().trim().isEmpty()){
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Registre comentarios de cancelación.", null);
            
            } else {
                Surtimiento surtimiento = new Surtimiento();
                surtimiento.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                
                if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSurtimiento(), EstatusSurtimiento_Enum.PROGRAMADO.getValue())) {
                    
                    surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                    
                    for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList){
                        item.setUpdateFecha(new java.util.Date());
                        item.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                        item.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.CANCELADO.getValue());
                        item.setFechaCancela(new java.util.Date());
                        item.setIdUsuarioCancela(usuarioSelected.getIdUsuario());
                        
                    }
                } else {
                    surtimiento.setIdEstatusSurtimiento(surtimientoExtendedSelected.getIdEstatusSurtimiento());
                }
                
                surtimiento.setUpdateFecha(new Date());
                surtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                Solucion o = solucionService.obtenerSolucion(null, this.surtimientoExtendedSelected.getIdSurtimiento());
                if (o == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla inválida.", null);
                    
                    
                } else {
                    Solucion s = new Solucion();
                    s.setIdSolucion(o.getIdSolucion());
                    s.setUpdateFecha(new java.util.Date());
                    s.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                    
                    s.setIdEstatusSolucion(EstatusSolucion_Enum.CANCELADA.getValue());
                    
                    s.setIdUsuarioValOrdenPrep(usuarioSelected.getIdUsuario());
                    s.setIdUsuarioCancela(usuarioSelected.getIdUsuario());
                    s.setIdMotivoCancelacion(solucion.getIdMotivoCancelacion());
                    s.setComentariosCancelacion(solucion.getComentariosCancelacion());
                    
                    status = solucionService.rechazarMezcla(s, surtimiento, surtimientoInsumoExtendedList, false);
                }

                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reacciones.error.guardar"), null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Orden de Preparación Cancelada.", null);

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
                            String asunto = "Orden de Preparación de mezcla " + surtimientoExtendedSelected.getFolio() + " Cancelada. ";
                            String msj = solucion.getComentariosCancelacion();
                            this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("ocurrio un error en: rechazarValidacionSolucion", ex);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private RespuestaAlertasDTO parseResponseDTO(String request) {
        RespuestaAlertasDTO dto = new RespuestaAlertasDTO();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode params = mapper.readTree(request);

            dto.setCodigo(params.hasNonNull("codigo") ? params.get("codigo").asText() : null);
            dto.setDescripcion(params.hasNonNull("descripcion") ? params.get("descripcion").asText() : null);

            List<AlertaFarmacovigilancia> listaAlerta = new ArrayList<>();
            if (params.hasNonNull("listaAlertaFarmacovigilancia")) {
                for (Iterator<JsonNode> iter = params.get("listaAlertaFarmacovigilancia").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    AlertaFarmacovigilancia item = new AlertaFarmacovigilancia();
                    item.setNumero(node.hasNonNull("numero") ? node.get("numero").asInt() : null);
                    item.setFactor1(node.hasNonNull("factor1") ? node.get("factor1").asText() : null);
                    item.setFactor2(node.hasNonNull("factor2") ? node.get("factor2").asText() : null);
                    item.setRiesgo(node.hasNonNull("riesgo") ? node.get("riesgo").asText() : null);
                    item.setTipo(node.hasNonNull("tipo") ? node.get("tipo").asText() : null);
                    item.setOrigen(node.hasNonNull("origen") ? node.get("origen").asText() : null);
                    item.setClasificacion(node.hasNonNull("clasificacion") ? node.get("clasificacion").asText() : null);
                    item.setMotivo(node.hasNonNull("motivo") ? node.get("motivo").asText() : null);

                    listaAlerta.add(item);
                }
            }
            dto.setListaAlertaFarmacovigilancia(listaAlerta);

            List<ValidacionNoCumplidaDTO> listaValidacion = new ArrayList<>();
            if (params.hasNonNull("ValidacionNoCumplidas")) {
                for (Iterator<JsonNode> iter = params.get("ValidacionNoCumplidas").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    ValidacionNoCumplidaDTO item = new ValidacionNoCumplidaDTO();
                    item.setCodigo(node.hasNonNull("codigo") ? node.get("codigo").asText() : null);
                    item.setDescripcion(node.hasNonNull("descripcion") ? node.get("descripcion").asText() : null);

                    listaValidacion.add(item);
                }
            }
            dto.setValidacionNoCumplidas(listaValidacion);

            List<ProtocoloExtended> listaProtocolo = new ArrayList<>();
            if (params.hasNonNull("listaAlertaProtocolo")) {
                for (Iterator<JsonNode> iter = params.get("listaAlertaProtocolo").elements(); iter.hasNext();) {
                    JsonNode node = iter.next();
                    ProtocoloExtended item = new ProtocoloExtended();
                    item.setClaveProtocolo(node.hasNonNull("claveProtocolo") ? node.get("claveProtocolo").asText() : null);
                    item.setDiagnostico(node.hasNonNull("diagnostico") ? node.get("diagnostico").asText() : null);
                    item.setClaveMedicamento(node.hasNonNull("claveMedicamento") ? node.get("claveMedicamento").asText() : null);
                    item.setNombreMedicamento(node.hasNonNull("nombreMedicamento") ? node.get("nombreMedicamento").asText() : null);
                    item.setDosis(node.hasNonNull("dosis") ? node.get("dosis").asText() : null);
                    item.setFrecuencia(node.hasNonNull("frecuencia") ? node.get("frecuencia").asText() : null);
                    item.setCiclos(node.hasNonNull("ciclos") ? node.get("ciclos").asInt() : null);
                    item.setEstabilidad(node.hasNonNull("estabilidad") ? node.get("estabilidad").asText() : null);
                    item.setArea(node.hasNonNull("area") ? node.get("area").asText() : null);
                    item.setPeso(node.hasNonNull("peso") ? node.get("peso").asText() : null);

                    List<ValidacionSolucionMezclaDetalleDTO> listDetalleProtocolo = new ArrayList<>();
                    if (node.hasNonNull("listaAlertaDetalleProtocolo")) {
                        for (Iterator<JsonNode> iter1 = node.get("listaAlertaDetalleProtocolo").elements(); iter1.hasNext();) {
                            JsonNode node1 = iter1.next();
                            ValidacionSolucionMezclaDetalleDTO item1 = new ValidacionSolucionMezclaDetalleDTO();
                            item1.setNombre(node1.hasNonNull("nombre") ? node1.get("nombre").asText() : null);
                            item1.setIndicada(node1.hasNonNull("indicada") ? node1.get("indicada").asText() : null);
                            item1.setCausa(node1.hasNonNull("comentarios") ? node1.get("comentarios").asText() : null);
                            item1.setPrescrita(node1.hasNonNull("prescrita") ? node1.get("prescrita").asText() : null);
                            listDetalleProtocolo.add(item1);
                        }
                        item.setListaDetalleValidacionSolucion(listDetalleProtocolo);
                    }
                    listaProtocolo.add(item);
                }
            }
            dto.setListaProtocolos(listaProtocolo);

        } catch (IOException ex) {
            LOGGER.error("Ocurrio un error al parsear el request:", ex);
        }
        return dto;
    }

    /**
     * Preimpresión de order de preparación
     *
     * @param surtimiento
     */
    public void previewPrint(Surtimiento_Extend surtimiento) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.previewPrint()");
        boolean status = false;
        byte[] buffer = null;

        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario es inválido.", null);

            } else if (!permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso para realizar esta acción.", null);

            } else if (surtimiento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la prescripción de mezcla es inválido.", null);

            } else if (surtimiento.getIdPrescripcion() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la prescripción de mezcla es inválido.", null);

            } else if (surtimiento.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la prescripción de mezcla es inválido.", null);

            } else {

                this.surtimientoExtendedSelected = surtimientoService.obtenerByIdSurtimiento(surtimiento.getIdSurtimiento());
                if (this.surtimientoExtendedSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El surtimiento de la prescripción de mezcla es inválido.", null);

                } else {
                    String nombreUsuario = usuarioSelected.getNombre() + " " + usuarioSelected.getApellidoPaterno() + " " + usuarioSelected.getApellidoMaterno();
                    Integer idTipoReporte = 3;
                    buffer = this.solUtils.imprimeDocumento(surtimiento, nombreUsuario, idTipoReporte);
                    if (buffer == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar la impresión de la orden de preparación.", null);

                    } else {
                        sesion.setReportStream(buffer);
                        sesion.setReportName(String.format("OrdenPreparacion_%s.pdf", surtimiento.getFolio()));
                        status = Constantes.ACTIVO;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener impresión de solución previewPrint : {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar la impresión de la orden de preparación.", null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void cancelImprimirEtiqueta() {
        try {
            idPrintSelect = null;
            cantPrint = 1;
            activeBtnPrint = Constantes.INACTIVO;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al cancelar la impresión: {}", ex.getMessage());
        }
    }

    /**
     * Imprime la etiqueta con la ClaveAgrupada despues de surtirse la solucion.
     *
     * @throws Exception
     */
    public void imprimirEtiquetaItem() throws Exception {
        boolean status = false;

        try {
            if (surtimientoExtendedSelected != null) {

                Impresora print = impresoraService.obtenerPorIdImpresora(idPrintSelect);
                if (print != null && !"".equals(template)) {
                    EtiquetaInsumo etiqueta = new EtiquetaInsumo();
                    etiqueta.setCaducidad(caducidadSolucion);
                    etiqueta.setOrigen("Origen");
                    etiqueta.setLaboratorio("");
                    etiqueta.setFotosencible("");
                    etiqueta.setTextoInstitucional("");
                    etiqueta.setDescripcion("Nombre Solución");

                    etiqueta.setLote(loteSolucion);
                    etiqueta.setClave(folioSolucion);
                    etiqueta.setTemplate(template);
                    etiqueta.setCantiad(cantPrint);
                    etiqueta.setIpPrint(print.getIpImpresora());
                    etiqueta.setCodigoQR(CodigoBarras.generaCodigoDeBarras(folioSolucion, loteSolucion, caducidadSolucion, null));

                    status = reportesService.imprimirEtiquetaItem(etiqueta);
                    if (status) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("paciente.info.impCorrectamente"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.genimpresion"), null);
                    }
                }
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("paciente.err.conect"), null);
            LOGGER.error("Error en imprimirEtiquetaItem: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void changePrint() {
        try {
            activeBtnPrint = Constantes.INACTIVO;
            if (idPrintSelect != null && !idPrintSelect.equals("") && cantPrint > 0) {
                activeBtnPrint = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al cambiar la impresora: {}", ex.getMessage());
        }
    }

    public void changeDoctoImpresora() {
        try {
            if (tipoArchivoImprimir != null && !tipoArchivoImprimir.equals("")) {
                if (tipoArchivoImprimir.equals(TipoImpresora_Enum.NORMAL.getValue())) {
                    imprimeReporte = false;
                    imprimeEtiqueta = true;
                } else if (tipoArchivoImprimir.equals(TipoImpresora_Enum.ETIQUETA.getValue())) {
                    imprimeEtiqueta = false;
                    imprimeReporte = true;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error: {}", ex.getMessage());
        }
    }

    public void authorization() {
        try {

            Usuario usr = new Usuario();
            usr.setClaveAcceso(passResponsable);
            usr.setNombreUsuario(userResponsable);
            usr.setActivo(Constantes.ACTIVO);
            usr.setUsuarioBloqueado(Constantes.INACTIVO);
            msjMdlSurtimiento = false;

            Usuario user = usuarioService.obtener(usr);
            if (user != null) {
                List<TransaccionPermisos> permisosAutorizaListSol = transaccionService.permisosUsuarioTransaccion(user.getIdUsuario(), Transaccion_Enum.DISPENSA_PRESCRIPCION.getSufijo());
                if (permisosAutorizaListSol != null) {
                    permisosAutorizaListSol.stream().forEach(item -> {
                        if (item.getAccion().equals("AUTORIZAR")) {
                            exist = true;
                        }
                    });

                    if (exist) {
                        authorization = true;
                        authorizado = true;
                        idResponsable = user.getIdUsuario();
                        codigoBarras = codigoBarrasAutorizte;
                        xcantidad = xcantidadAutorizte;
                        agregarLotePorCodigo();
                        if (permiso.isPuedeAutorizar()) {
                            userResponsable = usuarioSelected.getNombreUsuario();
                            passResponsable = usuarioSelected.getClaveAcceso();
                        } else {
                            userResponsable = "";
                            passResponsable = "";
                        }
                        codigoBarras = "";
                        idResponsable = "";
                        authorizado = false;
                        xcantidad = 1;
                        eliminaCodigoBarras = false;
                        msjMdlSurtimiento = true;
                        PrimeFaces.current().executeScript("PF('statusProcess').hide(); PF('modalAlertaAutorizacion').hide(); ");
                    } else {
                        userResponsable = "";
                        passResponsable = "";
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario no tiene permisos para Autorizar", null);
                    }
                } else {
                    userResponsable = "";
                    passResponsable = "";
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El usuario no tiene permisos para está transacción", null);
                }
            } else {
                passResponsable = "";
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El nombre de usuario o contraseña no son validos ", null);
            }
            PrimeFaces.current().executeScript("PF('statusProcess').hide(); ");
        } catch (Exception ex) {
            LOGGER.error("Error en Authorization: {}", ex.getMessage());
        }
    }

    public void cancelAuthorization() {
        authorization = false;
        msjMdlSurtimiento = true;
        PrimeFaces.current().executeScript("PF('statusProcess').hide(); PF('modalAlertaAutorizacion').hide(); ");
    }

    /**
     *
     * @param idSurtimiento
     * @throws Exception
     */
    public void imprimirPorId(String idSurtimiento) throws Exception {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.imprimirPorId()");
        surtimientoExtendedSelected = (Surtimiento_Extend) surtimientoService.obtener(new Surtimiento(idSurtimiento));
        imprimir();
    }

    /**
     *
     * @throws Exception
     */
    public void imprimir() throws Exception {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte();
            EstatusSurtimiento_Enum statusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());
            byte[] buffer = reportesService.imprimeSurtimientoPrescInt(repSurtimientoPresc, statusSurtimiento.name(),
                    surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);

            if (buffer != null) {
                status = Constantes.ACTIVO;
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket%s.pdf", surtimientoExtendedSelected.getFolio()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void imprimirSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.imprimirSurtimiento()");
        boolean status = Constantes.INACTIVO;
        try {
            RepSurtimientoPresc repSurtimientoPresc = obtenerDatosReporte();
            EstatusSurtimiento_Enum statusSurtimiento = EstatusSurtimiento_Enum.getStatusFromId(surtimientoExtendedSelected.getIdEstatusSurtimiento());

            byte[] buffer = reportesService.imprimeSurtimientoPrescInt(
                    repSurtimientoPresc, statusSurtimiento.name(),
                    surtimientoExtendedSelected.getDetalle() != null ? surtimientoExtendedSelected.getDetalle().size() : 0);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket%s.pdf", surtimientoExtendedSelected.getFolio()));
                obtenerSurtimientos();
            }
        } catch (Exception e) {
            LOGGER.error("Error en imprimirSurtimiento: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    private RepSurtimientoPresc obtenerDatosReporte() {
        RepSurtimientoPresc repSurtimientoPresc = new RepSurtimientoPresc();
        repSurtimientoPresc.setUnidadHospitalaria("");
        repSurtimientoPresc.setClasificacionPresupuestal("");
        try {
            Estructura estr = estructuraService.obtenerEstructura(surtimientoExtendedSelected.getIdEstructura());
            repSurtimientoPresc.setClasificacionPresupuestal(estr.getClavePresupuestal() == null ? "" : estr.getClavePresupuestal());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(estr.getIdEntidadHospitalaria());
            if (entidad != null) {
                repSurtimientoPresc.setUnidadHospitalaria(entidad.getNombre());
            }
            repSurtimientoPresc.setPiso(estr.getUbicacion());

            Surtimiento surt = surtimientoService.obtenerPorFolio(surtimientoExtendedSelected.getFolio());
            repSurtimientoPresc.setFechaSolicitado(surt.getFechaProgramada());

            Estructura valorServicio = estructuraService.obtenerEstructura(usuarioSelected.getIdEstructura());
            if (valorServicio != null) {
                repSurtimientoPresc.setNombreCopia(valorServicio.getNombre());
            }

            SurtimientoInsumo surIns = new SurtimientoInsumo();
            surIns.setIdSurtimiento(surt.getIdSurtimiento());
            List<SurtimientoInsumo> lsi = surtimientoInsumoService.obtenerLista(surIns);
            if (lsi != null && !lsi.isEmpty()) {
                surIns = lsi.get(0);
                repSurtimientoPresc.setFechaAtendido(surIns.getFechaEnviada());
            }
        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
        }

        repSurtimientoPresc.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
        repSurtimientoPresc.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
        repSurtimientoPresc.setFechaActual(new Date());
        repSurtimientoPresc.setNombrePaciente(surtimientoExtendedSelected.getNombrePaciente());
        repSurtimientoPresc.setClavePaciente(surtimientoExtendedSelected.getClaveDerechohabiencia());
        repSurtimientoPresc.setServicio(surtimientoExtendedSelected.getNombreEstructura());
        repSurtimientoPresc.setCama(surtimientoExtendedSelected.getCama());
        repSurtimientoPresc.setTurno(surtimientoExtendedSelected.getTurno());
        repSurtimientoPresc.setIdEstatusSurtimiento(surtimientoExtendedSelected.getIdEstatusSurtimiento());
        return repSurtimientoPresc;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public void setHuboError(boolean huboError) {
        this.huboError = huboError;
    }

    public boolean isHuboError() {
        return huboError;
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

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public Integer getSizeSurtimientoExtendList() {
        if (surtimientoExtendedList != null) {
            return surtimientoExtendedList.size();
        } else {
            return 0;
        }
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

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isRechazable() {
        return rechazable;
    }

    public void setRechazable(boolean rechazable) {
        this.rechazable = rechazable;
    }

    public Medicamento_Extended getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento_Extended medicamento) {
        this.medicamento = medicamento;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarra) {
        this.codigoBarras = codigoBarra;
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

    public void onRowSelectInsumo(SelectEvent event) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.onRowSelectInsumo()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectInsumo() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.onRowUnselectInsumo()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    public Integer getSizeSurtimientoInsumoExtendedList() {
        if (surtimientoInsumoExtendedList != null) {
            return surtimientoInsumoExtendedList.size();
        } else {
            return 0;
        }
    }

    public boolean isEliminaCodigoBarras() {
        return eliminaCodigoBarras;
    }

    public void setEliminaCodigoBarras(boolean eliminaCodigoBarras) {
        this.eliminaCodigoBarras = eliminaCodigoBarras;
    }

    public List<TipoJustificacion> getJustificacionList() {
        return justificacionList;
    }

    public void setJustificacionList(List<TipoJustificacion> justificacionList) {
        this.justificacionList = justificacionList;
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

    /**
     * Oliver Submodulo Capsulas
     */
    private int activo;
    private String capsula;
    private String codigoCapsula;
    private String estructura;
    private List<Capsula> idCapsula;
    private List<Capsula> listaCapsulas;
    private String codigoCpausla;
    private int idenvioNeumatico;
    private boolean capsulaDisponible;
    private String nombre;

    /**
     * Obtiene lista de capsulas que existen en la Tabla Capsula y estan activas
     */
    private void obtienelistaCapsulas() {
        try {
            this.listaCapsulas = capsulaService.obtieneListaCapsulasActivas(Constantes.ACTIVOS, this.surtimientoExtendedSelected.getIdEstructura());
        } catch (Exception ex) {
            LOGGER.info("Error al buscar la lista de Capsulas: {}", ex.getMessage());
        }

    }

    /**
     * Obtiene el id De la capsula seleccionada
     */
    private void obteneridCapsula() {
        try {
            this.idCapsula = capsulaService.obteneridCapsula(this.capsula);
        } catch (Exception ex) {
            LOGGER.info("Error al buscar la lista de Capsulas: {}", ex.getMessage());
        }
    }

    /**
     * Inserta el Id de la Capsula seleccionada en la Tabla surtimiento
     */
    public void vincularPrescripcionCapsula() {
        try {

            Surtimiento surtircapsula = new Surtimiento();
            EnvioNeumatico neumaticap = new EnvioNeumatico();
            if (this.capsula != null) {
                //objeto para insertar datos en tabla EnvioNeumatico
                obteneridCapsula();
                surtircapsula.setIdSurtimiento(this.surtimientoExtendedSelected.getIdSurtimiento());
                surtimientoService.ligaridCapsulaconSurtimiento(surtircapsula);

                neumaticap.setIdenvioNeumatico(getIdenvioNeumatico());
                neumaticap.setIdCapsula(this.idCapsula.get(0).getIdCapsula());
                neumaticap.setNombreCap(this.idCapsula.get(0).getNombre());
                neumaticap.setFolioSurtimiento(this.surtimientoExtendedSelected.getFolio());
                neumaticap.setUsuario(this.usuarioSelected.getNombre());
                neumaticap.setFechaHoraSalida(new java.util.Date());
                neumaticap.setEstacionSalida(this.surtimientoExtendedSelected.getNombreEstructura());
                neumaticap.setFechaHoraLlegada(new java.util.Date());
                neumaticap.setEstacionLlegada(this.surtimientoExtendedSelected.getNombreEstructura());
                neumaticap.setFolioPrescripcion(this.surtimientoExtendedSelected.getFolioPrescripcion());
                envioNeumaticoService.insertNeumaticTable(neumaticap);
            }

        } catch (Exception ex) {
            LOGGER.info("Error al insertar id de Capsula: {}", ex.getMessage());
        }
    }

    public List<Capsula> getListaCapsulas() {
        return listaCapsulas;
    }

    public void setListaCapsulas(List<Capsula> listaCapsulas) {
        this.listaCapsulas = listaCapsulas;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getCapsula() {
        return capsula;
    }

    public void setCapsula(String capsula) {
        this.capsula = capsula;
    }

    public String getCodigoCapsula() {
        return codigoCapsula;
    }

    public void setCodigoCapsula(String codigoCapsula) {
        this.codigoCapsula = codigoCapsula;
    }

    public String getEstructura() {
        return estructura;
    }

    public void setEstructura(String estructura) {
        this.estructura = estructura;
    }

    public List<Capsula> getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(List<Capsula> idCapsula) {
        this.idCapsula = idCapsula;
    }

    public String getCodigoCpausla() {
        return codigoCpausla;
    }

    public void setCodigoCpausla(String codigoCpausla) {
        this.codigoCpausla = codigoCpausla;
    }

    public int getIdenvioNeumatico() {
        return idenvioNeumatico;
    }

    public void setIdenvioNeumatico(int idenvioNeumatico) {
        this.idenvioNeumatico = idenvioNeumatico;
    }

    public boolean isCapsulaDisponible() {
        return capsulaDisponible;
    }

    public void setCapsulaDisponible(boolean capsulaDisponible) {
        this.capsulaDisponible = capsulaDisponible;
    }

    public String getUserResponsable() {
        return userResponsable;
    }

    public void setUserResponsable(String userResponsable) {
        this.userResponsable = userResponsable;
    }

    public String getPassResponsable() {
        return passResponsable;
    }

    public void setPassResponsable(String passResponsable) {
        this.passResponsable = passResponsable;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isMsjMdlSurtimiento() {
        return msjMdlSurtimiento;
    }

    public void setMsjMdlSurtimiento(boolean msjMdlSurtimiento) {
        this.msjMdlSurtimiento = msjMdlSurtimiento;
    }

    public String getMsjAlert() {
        return msjAlert;
    }

    public void setMsjAlert(String msjAlert) {
        this.msjAlert = msjAlert;
    }

    public List<Impresora> getListaImpresoras() {
        return listaImpresoras;
    }

    public void setListaImpresoras(List<Impresora> listaImpresoras) {
        this.listaImpresoras = listaImpresoras;
    }

    public List<TemplateEtiqueta> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<TemplateEtiqueta> templateList) {
        this.templateList = templateList;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(Integer numTemp) {
        this.numTemp = numTemp;
    }

    public Impresora getImpresoraSelect() {
        return impresoraSelect;
    }

    public void setImpresoraSelect(Impresora impresoraSelect) {
        this.impresoraSelect = impresoraSelect;
    }

    public String getItemPirnt() {
        return itemPirnt;
    }

    public void setItemPirnt(String itemPirnt) {
        this.itemPirnt = itemPirnt;
    }

    public Boolean getActiveBtnPrint() {
        return activeBtnPrint;
    }

    public void setActiveBtnPrint(Boolean activeBtnPrint) {
        this.activeBtnPrint = activeBtnPrint;
    }

    public String getIdPrintSelect() {
        return idPrintSelect;
    }

    public void setIdPrintSelect(String idPrintSelect) {
        this.idPrintSelect = idPrintSelect;
    }

    public Integer getCantPrint() {
        return cantPrint;
    }

    public void setCantPrint(Integer cantPrint) {
        this.cantPrint = cantPrint;
    }

    public Integer getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(Integer estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getSurtida() {
        return surtida;
    }

    public void setSurtida(String surtida) {
        this.surtida = surtida;
    }

    public String getProgramada() {
        return programada;
    }

    public void setProgramada(String programada) {
        this.programada = programada;
    }

    public String getCancelada() {
        return cancelada;
    }

    public void setCancelada(String cancelada) {
        this.cancelada = cancelada;
    }

    public String getLoteSolucion() {
        return loteSolucion;
    }

    public void setLoteSolucion(String loteSolucion) {
        this.loteSolucion = loteSolucion;
    }

    public String getFolioSolucion() {
        return folioSolucion;
    }

    public void setFolioSolucion(String folioSolucion) {
        this.folioSolucion = folioSolucion;
    }

    public Date getCaducidadSolucion() {
        return caducidadSolucion;
    }

    public void setCaducidadSolucion(Date caducidadSolucion) {
        this.caducidadSolucion = caducidadSolucion;
    }

    public boolean isMostrarImpresionEtiqueta() {
        return mostrarImpresionEtiqueta;
    }

    public void setMostrarImpresionEtiqueta(boolean mostrarImpresionEtiqueta) {
        this.mostrarImpresionEtiqueta = mostrarImpresionEtiqueta;
    }

    public String getTipoArchivoImprimir() {
        return tipoArchivoImprimir;
    }

    public void setTipoArchivoImprimir(String tipoArchivoImprimir) {
        this.tipoArchivoImprimir = tipoArchivoImprimir;
    }

    public boolean isImprimeReporte() {
        return imprimeReporte;
    }

    public void setImprimeReporte(boolean imprimeReporte) {
        this.imprimeReporte = imprimeReporte;
    }

    public boolean isImprimeEtiqueta() {
        return imprimeEtiqueta;
    }

    public void setImprimeEtiqueta(boolean imprimeEtiqueta) {
        this.imprimeEtiqueta = imprimeEtiqueta;
    }

    public String getDocReporte() {
        return docReporte;
    }

    public void setDocReporte(String docReporte) {
        this.docReporte = docReporte;
    }

    public String getDocEtiqueta() {
        return docEtiqueta;
    }

    public void setDocEtiqueta(String docEtiqueta) {
        this.docEtiqueta = docEtiqueta;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
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

    public RespuestaAlertasDTO getAlertasDTO() {
        return alertasDTO;
    }

    public void setAlertasDTO(RespuestaAlertasDTO alertasDTO) {
        this.alertasDTO = alertasDTO;
    }

    public List<Diagnostico> getDiagnosticosList() {
        return diagnosticosList;
    }

    public void setDiagnosticosList(List<Diagnostico> diagnosticosList) {
        this.diagnosticosList = diagnosticosList;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getConservacion() {
        return conservacion;
    }

    public void setConservacion(String conservacion) {
        this.conservacion = conservacion;
    }

    public BigDecimal getVolumenTotal() {
        return volumenTotal;
    }

    public void setVolumenTotal(BigDecimal volumenTotal) {
        this.volumenTotal = volumenTotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<EnvaseContenedor> getEnvaseList() {
        return envaseList;
    }

    public void setEnvaseList(List<EnvaseContenedor> envaseList) {
        this.envaseList = envaseList;
    }

    public Integer getIdEnvase() {
        return idEnvase;
    }

    public void setIdEnvase(Integer idEnvase) {
        this.idEnvase = idEnvase;
    }

    public boolean getMuestreo() {
        return muestreo;
    }

    public void setMuestreo(boolean muestreo) {
        this.muestreo = muestreo;
    }

    public List<ValidacionSolucionMezclaDetalleDTO> getListaDetalleProtocolos() {
        return listaDetalleProtocolos;
    }

    public void setListaDetalleProtocolos(List<ValidacionSolucionMezclaDetalleDTO> listaDetalleProtocolos) {
        this.listaDetalleProtocolos = listaDetalleProtocolos;
    }

    public ProtocoloExtended getProtocoloSelected() {
        return protocoloSelected;
    }

    public void setProtocoloSelected(ProtocoloExtended protocoloSelected) {
        this.protocoloSelected = protocoloSelected;
    }

    public String getClaveProtocolo() {
        return claveProtocolo;
    }

    public void setClaveProtocolo(String claveProtocolo) {
        this.claveProtocolo = claveProtocolo;
    }

    public boolean isIsOncologica() {
        return isOncologica;
    }

    public void setIsOncologica(boolean isOncologica) {
        this.isOncologica = isOncologica;
    }

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
    }

    private boolean isOncologica;
    private Diagnostico diagnosticoSelected;
    @Autowired
    private transient ProtocoloService protocoloService;

    private boolean validaDiagnosticoProtocolo() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.validaDiagnosticoProtocolo()");
        boolean valid = false;
        this.isOncologica = obtenClaveTipoSolucion(idTipoSolucion).equalsIgnoreCase("ONC");
        if (!this.isOncologica) {
            this.claveProtocolo = null;
            this.diagnosticoSelected = null;
            valid = true;

        } else {
            try {
                Protocolo p = new Protocolo();
                p.setClaveProtocolo(this.claveProtocolo);
                if (this.claveProtocolo != null) {
                    p = protocoloService.obtener(p);

                    if (p != null) {
                        if (!p.isValidaProtocolo()) {
                            valid = true;
                        }
                    }
                    if (!valid) {
                        this.msjWarning = "El Diagnóstico no coincide con el Protocolo<br/> <b>" + this.diagnosticoSelected.getDescripcion() + "</b>";
                        PrimeFaces.current().executeScript("PF('modalWarning').show();");
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Error al validar el protocolo {} ", ex.getMessage());
            }
        }
        return valid;
    }

    private transient List<TipoSolucion> tipoSolucionList;

    private String obtenClaveTipoSolucion(String idTipoSolucion) {
        String clave = "";
        for (TipoSolucion tipoSol : this.tipoSolucionList) {
            if (tipoSol.getIdTipoSolucion().equals(idTipoSolucion)) {
                clave = tipoSol.getClave();
                break;
            }
        }
        return clave;
    }

    public String getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(String idResponsable) {
        this.idResponsable = idResponsable;
    }

    public List<Integer> getListEstatusSurtimiento() {
        return listEstatusSurtimiento;
    }

    public void setListEstatusSurtimiento(List<Integer> listEstatusSurtimiento) {
        this.listEstatusSurtimiento = listEstatusSurtimiento;
    }

    public boolean isFunValidarFarmacoVigilancia() {
        return funValidarFarmacoVigilancia;
    }

    public void setFunValidarFarmacoVigilancia(boolean funValidarFarmacoVigilancia) {
        this.funValidarFarmacoVigilancia = funValidarFarmacoVigilancia;
    }

    public String getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(String diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public Integer getNoDiasCaducidad() {
        return noDiasCaducidad;
    }

    public void setNoDiasCaducidad(Integer noDiasCaducidad) {
        this.noDiasCaducidad = noDiasCaducidad;
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

    public List<TipoSolucion> getTipoSolucionList() {
        return tipoSolucionList;
    }

    public void setTipoSolucionList(List<TipoSolucion> tipoSolucionList) {
        this.tipoSolucionList = tipoSolucionList;
    }

    public List<Protocolo> getListaProtocolos() {
        return listaProtocolos;
    }

    public void setListaProtocolos(List<Protocolo> listaProtocolos) {
        this.listaProtocolos = listaProtocolos;
    }

    public List<Frecuencia> getListaFrecuencias() {
        return listaFrecuencias;
    }

    public void setListaFrecuencias(List<Frecuencia> listaFrecuencias) {
        this.listaFrecuencias = listaFrecuencias;
    }

    public List<ViaAdministracion> getViaAdministracionList() {
        return viaAdministracionList;
    }

    public void setViaAdministracionList(List<ViaAdministracion> viaAdministracionList) {
        this.viaAdministracionList = viaAdministracionList;
    }

    public List<CamaExtended> getListaCamas() {
        return listaCamas;
    }

    public void setListaCamas(List<CamaExtended> listaCamas) {
        this.listaCamas = listaCamas;
    }

    public List<Usuario> autoCompleteMedicos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.autoCompleteMedicos()");
        List<Usuario> listMedicos = new ArrayList<>();
        try {
            listMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                    cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompleteMedicos :{}", ex.getMessage());
        }
        return listMedicos;
    }

    public List<Estructura> getListServiciosQueSurte() {
        return listServiciosQueSurte;
    }

    public void setListServiciosQueSurte(List<Estructura> listServiciosQueSurte) {
        this.listServiciosQueSurte = listServiciosQueSurte;
    }

    private Usuario medico;

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    private Prescripcion prescripcionSelected;

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

    private Usuario_Extended medicoExtended;
    private transient List<Turno> listaTurnos;
    private transient List<String> listaIdTurnos;
    private boolean existePrescripcion;

    public void mostrarModalAgregarMedico() {
        this.medicoExtended = new Usuario_Extended();
        for (Turno item : listaTurnos) {
            this.listaIdTurnos.add("" + item.getIdTurno());
        }
    }

    public Usuario_Extended getMedicoExtended() {
        return medicoExtended;
    }

    public void setMedicoExtended(Usuario_Extended medicoExtended) {
        this.medicoExtended = medicoExtended;
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

    public boolean isExistePrescripcion() {
        return existePrescripcion;
    }

    public void setExistePrescripcion(boolean existePrescripcion) {
        this.existePrescripcion = existePrescripcion;
    }

    public boolean isVerAutorizacion() {
        return verAutorizacion;
    }

    public void setVerAutorizacion(boolean verAutorizacion) {
        this.verAutorizacion = verAutorizacion;
    }

    private Paciente_Extended pacienteExtended;

    public void selectPaciente(SelectEvent evt) {
        Paciente_Extended pacienteSelected = (Paciente_Extended) evt.getObject();
        try {
            pacienteExtended = pacienteService.obtenerPacienteCompletoPorId(pacienteSelected.getIdPaciente());
            if (pacienteExtended != null) {
                this.surtimientoExtendedSelected.setEdadPaciente(this.pacienteExtended.getEdadPaciente());
                this.surtimientoExtendedSelected.setPesoPaciente(this.pacienteExtended.getPesoPaciente());
                this.surtimientoExtendedSelected.setTallaPaciente(this.pacienteExtended.getTallaPaciente());
                this.surtimientoExtendedSelected.setAreaCorporal(this.pacienteExtended.getAreaCorporal());
            }
            this.diagnosticosList = diagnosticoService.obtenerDiagnosticoByIdPaciente(pacienteSelected.getIdPaciente());
        } catch (Exception ex) {
            LOGGER.error("selectPaciente: Error al obtener paciente : {}", ex.getMessage());
        }
    }

    private Paciente paciente;

    public void mostrarModalAgregarPaciente() {
        this.paciente = new Paciente();
        Turno item = this.listaTurnos.get(0);
        this.listaIdTurnos = new ArrayList<>();
        this.listaIdTurnos.add("" + item.getIdTurno());
    }

    public Prescripcion getPrescripcionSelected() {
        return prescripcionSelected;
    }

    public void setPrescripcionSelected(Prescripcion prescripcionSelected) {
        this.prescripcionSelected = prescripcionSelected;
    }

    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void seleccionaCama() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.seleccionaCama()");
        try {
            this.surtimientoExtendedSelected.setCama(this.surtimientoExtendedSelected.getCama());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public List<Diagnostico> autocompleteDiagnosticos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.autocompleteDiagnostico()");
        List<Diagnostico> diagnListFromDB;
        List<Diagnostico> diagnList = new ArrayList<>();
        try {
            diagnListFromDB = this.diagnosticoService.obtenerListaAutoComplete(cadena);
            for (Diagnostico diag : diagnListFromDB) {
                if (!existeEnListaDiag(this.diagnosticosList, diag.getIdDiagnostico())) {
                    diagnList.add(diag);
                }
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagnList;
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

    public boolean existeEnListaDiag(List<Diagnostico> listaDiagnostico, String idDiagnostico) {
        boolean existe = false;
        for (Diagnostico diag : listaDiagnostico) {
            if (diag.getIdDiagnostico().equalsIgnoreCase(idDiagnostico)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    public boolean isPerfusionContinua() {
        return perfusionContinua;
    }

    public void setPerfusionContinua(boolean perfusionContinua) {
        this.perfusionContinua = perfusionContinua;
    }

    public ViaAdministracion getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(ViaAdministracion viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    private String msjWarning;

    public String getMsjWarning() {
        return msjWarning;
    }

    public void setMsjWarning(String msjWarning) {
        this.msjWarning = msjWarning;
    }

    public void activaPerfusion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.activaPerfusion()");
    }
    
    public void calculaVelocidadPerfusion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.calculaVelocidadPerfusion");
        asignaVelocidad();
    }
    
    public void asignaVelocidad() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.asignaVelocidad()");
        try {
            if (this.surtimientoExtendedSelected != null) {
                if (surtimientoExtendedSelected.getHorasInfusion() != null || !surtimientoExtendedSelected.getHorasInfusion().isEmpty()) {
                    String cadenaHora[] = surtimientoExtendedSelected.getHorasInfusion().split(":");
                    Integer hora = Integer.valueOf(cadenaHora[0]);
                    Integer minut = Integer.valueOf(cadenaHora[1]);
                    //Calendar cal = Calendar.getInstance();
                    //cal.setTime(surtimientoExtendedSelected.getHorasInfusion());
                    //Integer hora = cal.get(Calendar.HOUR);
                    //Integer minut = cal.get(Calendar.MINUTE);
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
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al asignar velocidad de infusión :: {} ", e.getMessage());
        }
    }

    public void seleccionaTiposolucion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.seleccionaTiposolucion()");
        this.claveProtocolo = null;
        this.diagnosticoSelected = null;
        this.isOncologica = obtenClaveTipoSolucion(idTipoSolucion).equalsIgnoreCase("ONC");
        if (this.isOncologica) {
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

    private void evaluaEdicion() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.evaluaEdicion()");
        this.editable = false;
        if (surtimientoExtendedSelected != null) {
            if (surtimientoExtendedSelected.getIdEstatusSolucion() != null) {
                if (permiso.isPuedeAutorizar()) {
                    this.editable = true;
                    this.rechazable = true;

                } else {
                    if (permiso.isPuedeEditar()) {
                        if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())) {
                            this.editable = true;
                        }
                    }

                    if (permiso.isPuedeEliminar()) {
                        if (Objects.equals(surtimientoExtendedSelected.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())) {
                            this.rechazable = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Obtiene la lista de horarios de entrega
     *
     * @param idTipoSolucionSelected
     */
    public void obtenerListaHorarios(String idTipoSolucionSelected) {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.obtenerListaHorarios()");
        this.horarioEntregaLista = new ArrayList<>();
        try {
            HorarioEntrega he = new HorarioEntrega();
            he.setIdTipoSolucion(idTipoSolucionSelected);
            he.setActiva(1);
            this.horarioEntregaLista.addAll(this.horarioEntregaService.obtenerLista(he));
        } catch (Exception e) {
            LOGGER.error("Error al obtener lista de horarios de entrega :: {} ", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener lista de horarios de entrega. ", null);
        }
    }

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

    private Solucion solucion;

    /**
     * Realiza las acciones de validación y para rechazo de orden de preparación
     * prescripción
     */
    public void validaRechazo() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.validaRechazo()");
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

            } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite cancelación.", null);

            } else {
                
                solucion.setIdMotivoRechazo(null);
                solucion.setComentariosRechazo(null);
                status = true;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    /**
     * Realiza las acciones de validación y para cancelación de orden de preparación
     */
    public void validaCancelar() {
        LOGGER.trace("mx.mc.magedbean.ValidacionSolucionMB.validaCancelar()");
        boolean status = false;
        this.solucion = new Solucion();
        this.otro = false;

        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (!permiso.isPuedeAutorizar()) {
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

            } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())
                && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite cancelación.", null);

            } else {
                status = true;
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }

    private boolean otro;

    public void changeMotivo() {
        if (solucion != null) {
            if (Objects.equals(solucion.getIdMotivoCancelacion(), MotivoCancelacionRechazoSolucion_Enum.OTRO.getValue())) {
                otro = Constantes.ACTIVO;
            } else {
                otro = Constantes.INACTIVO;
            }
            solucion.setMotivoCancela("");
        }
    }

    public boolean isOtro() {
        return otro;
    }

    public void setOtro(boolean otro) {
        this.otro = otro;
    }
   
    public void generaInstruPrep() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.generaInstruccionesCondiciones()");
        try {
//            if (validaPrescripcion) {
                if (this.surtimientoExtendedSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla inválida.", null);

                } else if (this.surtimientoInsumoExtendedList == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No hay insumos registrados.", null);

                } else if (this.surtimientoInsumoExtendedList.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No hay insumos registrados.", null);

                } else if (this.surtimientoExtendedSelected.getIdContenedor() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Contenedor inválido.", null);

                } else if (this.surtimientoExtendedSelected.getIdContenedor() == 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Contenedor inválido.", null);

                } else {
                    String cont = this.solUtils.generaInstruccionPreparacion(surtimientoExtendedSelected, surtimientoInsumoExtendedList, viaAdministracion);
                    this.surtimientoExtendedSelected.setInstruccionPreparacion(cont);
                }
//            }
        } catch (Exception e) {
            LOGGER.trace(e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, e.getMessage(), null);
        }
    }

}
