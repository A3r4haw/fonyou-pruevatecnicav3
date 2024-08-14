package mx.mc.magedbean;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import mx.mc.commons.SolucionUtils;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.enums.EstatusDiagnostico_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoCalculoNpt_Enum;
import mx.mc.enums.TipoCalorias_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoDocumento_Enum;
import mx.mc.enums.TipoPrescripcion_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.enums.ViaAdministracion_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.lazy.OrdenMezclaLazy;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.CalculoNpt;
import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvaseContenedor;
import mx.mc.model.Estructura;
import mx.mc.model.Folios;
import mx.mc.model.HipersensibilidadExtended;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.Medicamento;
import mx.mc.model.NutricionParenteralDetalle;
import mx.mc.model.NutricionParenteralDetalleExtended;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.ReaccionExtend;
import mx.mc.model.Sobrellenado;
import mx.mc.model.Solucion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoSolucion;
import mx.mc.model.TipoUsuario;
import mx.mc.model.Turno;
import mx.mc.model.TurnoMedico;
import mx.mc.model.UnidadConcentracion;
import mx.mc.model.Usuario;
import mx.mc.model.Usuario_Extended;
import mx.mc.model.ViaAdministracion;
import mx.mc.model.Visita;
import mx.mc.service.CalculoNptService;
import mx.mc.service.CamaService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ConfigService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvaseContenedorService;
import mx.mc.service.EquipoService;
import mx.mc.service.EstabilidadService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FoliosService;
import mx.mc.service.FrecuenciaService;
import mx.mc.service.HipersensibilidadService;
import mx.mc.service.ImpresoraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.MotivosRechazoService;
import mx.mc.service.NutricionParenteralDetalleService;
import mx.mc.service.NutricionParenteralService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PacienteUbicacionService;
import mx.mc.service.PlantillaCorreoService;
import mx.mc.service.PrescripcionInsumoService;
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
import mx.mc.ws.farmacovigilancia.service.ReaccionesAdversas;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
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
public class PrescribirMezclaNPTMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrescribirMezclaNPTMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final ResourceBundle RESOURCES2 = ResourceBundle.getBundle(Constantes.GLOBAL_PARAM);

    private String cadenaBusqueda;
    private SesionMB sesion;
    private PermisoUsuario permiso;
    private Usuario usuarioSelected;
    private NutricionParenteralExtended nutricionParenteralSelect;
    private OrdenMezclaLazy ordenMezclaLazy;
    private transient List<Estructura> listaEstructuras;
    private boolean administrador;
    private String idEstructura;
    private boolean contEdit;
    private transient List<Medicamento> listaMedicamentos;
    private transient List<NutricionParenteralDetalleExtended> listaMedicamentosNPT;
    private BigDecimal oldValue;
    private int position;
    private Folios folioOrden;
    private Medicamento caloriaAminoacido;
    private Paciente_Extended pacienteSelect;
    private Usuario medicoSelect;
    private boolean status;
    private boolean editar;
    private transient RespuestaAlertasDTO alertasDTO;
    private String idNutricionParente;
    private Prescripcion prescripcionPaciente;
    private transient List<PrescripcionInsumo> listPrescripcionInsumos;
    private String hipersensibilidades;
    private String reacciones;
    private transient List<Turno> listaTurnos;
    private transient List<String> listaIdTurnos;
    private Date fechaActual;
    private String paramError;
    private transient List<CatalogoGeneral> listaTipoPacientes;
    private transient List<TipoUsuario> tipoUserList;
    private Paciente pacienteNuevo;
    private Usuario_Extended medicoExtended;
    private boolean mostrarBotones;
    private PacienteServicio pacienteServ;
    private Visita visitaAbierta;
    private String validaFormulario;
    private boolean deshabilitaValidar;
    private transient List<Estructura> estructuraList;
//    private transient List<Estructura> listaAuxiliar;
    private String idAlmacen;
    private boolean isJefeArea;
    private BigDecimal volumenPres;
//    private BigDecimal cantidadPres; 
    private Integer posicion;

    private transient List<CamaExtended> listaCamas;
    private SolucionUtils solUtils;

//    private transient List<Diagnostico> listaDiagnosticos;
    private transient List<Estructura> listServiciosQueSurte;

    private String surSinAlmacen;
    private String paramEstatus;
//    private String errRegistroIncorrecto;
//    private String surIncorrecto;
//    private String surInvalido;

    private transient List<EnvaseContenedor> envaseList;

    @Autowired
    private transient UsuarioService usuarioService;
    @Autowired
    private transient PacienteService pacienteService;
    @Autowired
    private transient EstructuraService estructuraService;
    @Autowired
    private transient MedicamentoService medicamentoService;
    @Autowired
    private transient FoliosService foliosService;
    @Autowired
    private transient NutricionParenteralService nutricionParenteralService;
    @Autowired
    private transient NutricionParenteralDetalleService nutricionParenteralDetalleService;
    @Autowired
    private transient VisitaService visitaService;
    @Autowired
    private transient PacienteServicioService pacienteServicioService;
    @Autowired
    private transient DiagnosticoService diagnosticosService;
    @Autowired
    private transient PrescripcionService prescripcionService;
    @Autowired
    private transient CamaService camaService;
    @Autowired
    private transient HipersensibilidadService hipersensibilidadService;
    @Autowired
    private transient ReaccionService reaccionService;
    @Autowired
    private transient PrescripcionInsumoService prescripcionInsumoService;
    @Autowired
    private transient TurnoService turnoService;
    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;
    @Autowired
    private transient PacienteUbicacionService pacienteUbicacionService;
    @Autowired
    private transient ServletContext servletContext;
    @Autowired
    private transient InventarioService inventarioService;
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    @Autowired
    private transient SurtimientoService surtimientoService;
    @Autowired
    private transient ReportesService reportesService;
    @Autowired
    private transient SolucionService solucionService;
    @Autowired
    private transient EnvaseContenedorService envaseService;
    @Autowired
    private transient EnviaCorreoUtil enviaCorreoUtil;
    @Autowired
    private transient PlantillaCorreoService plantillaCorreoService;
    @Autowired
    private transient ConfigService configService;
    @Autowired
    private transient DiagnosticoService diagnosticoService;
    @Autowired
    private transient ProtocoloService protocoloService;
    @Autowired
    private transient TipoSolucionService tipoSolucionService;
    @Autowired
    private transient FrecuenciaService frecuenciaService;
    @Autowired
    private transient ViaAdministracionService viaAdministracionService;
    @Autowired
    private transient TemplateEtiquetaService templateService;
    @Autowired
    private transient ImpresoraService impresoraService;
    @Autowired
    private transient MotivosRechazoService motivosRechazoService;
//    @Autowired
//    private transient TipoSolucionService tiposolucionService;
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private transient TipoJustificacionService tipoJustificacionService;

//    private transient List<HorarioEntrega> horarioEntregaLista;
//    private HorarioEntrega horarioEntrega;
//    @Autowired
//    private transient HorarioEntregaService horarioEntregaService;
    @Autowired
    private transient EquipoService equipoService;
    @Autowired
    private transient UnidadConcentracionService unidadService;
    @Autowired
    private transient EstabilidadService estabilidadService;

    private boolean existePrescripcion;
    private boolean validaPrescripcion;
    private ViaAdministracion viaAdministracion;
    private transient List<ViaAdministracion> viaAdministracionList;

    private String idTipoSolucion;
    private transient List<TipoSolucion> tipoSolucionList;

    private transient List<Integer> estatusSolucionLista;
    private String porValidar;
    private String validada;
    private String rechazada;
    private Integer estatusSurtimiento;
    private Date fechaMinPrescrMezclaValida;
    private Date fechaMinPrescrMezcla;
    private Integer numMinutosEntregaMezcla;
    private Integer numDiasMaxPresMezcla;

    private Double numMaxCompatibilidad;
    private Double factorMolMonovaPotasio;
    private Double factorMolDivalZinc;
    private Double factorMolesDivalEstabilidad;
    private Double numMaxEstabilidad;
    private Double numMaxOsmolaridad;
    @Autowired
    private transient CalculoNptService calculoNptService;

    private transient List<CalculoNpt> listClavesCalcCompatibilidad;
    private transient List<CalculoNpt> listClavesCalcEstabMMonovalP1;
    private transient List<CalculoNpt> listClavesCalcEstabMolDivalP1;
    private transient List<CalculoNpt> listClavesCalcEstabMMolDivalP1;
    private transient List<CalculoNpt> listClavesCalcEstabMolDivalP2;

    private boolean editable;
    private boolean cancelable;
    private boolean rechazable;

    private String nptDefaultViaAdministracion;
    private String nptDefaultEnvaseContenedor;
    private transient List<Integer> idViaAdmontList;
    private transient List<Integer> idEnvaseContList;
    private transient List<Sobrellenado> sobrelleadoList;
    private Solucion solucion;
    
    
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.init()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        initialize();

        permiso = Comunes.obtenerPermisos(Transaccion_Enum.PRESCRIPCIONPT.getSufijo());
        usuarioSelected = sesion.getUsuarioSelected();
        this.administrador = Comunes.isAdministrador();
        this.isJefeArea = Comunes.isJefeArea();

        try {
                
            this.solUtils = new SolucionUtils(estructuraService, surtimientoService, templateService, impresoraService, motivosRechazoService, prescripcionService, surtimientoInsumoService, solucionService, reportesService, entidadHospitalariaService, diagnosticoService, envaseService, viaAdministracionService, tipoSolucionService, protocoloService, visitaService, camaService, frecuenciaService, catalogoGeneralService, turnoService, tipoUsuarioService, pacienteServicioService, pacienteUbicacionService, enviaCorreoUtil, plantillaCorreoService, configService, tipoJustificacionService, medicamentoService, usuarioService, pacienteService, hipersensibilidadService, reaccionService, equipoService, estabilidadService, inventarioService);
//            alimentarComboAlmacen();
//            obtieneServiciosQuePuedeSurtir();
            obtenerCamas();            
            
            idViaAdmontList = new ArrayList();
            idEnvaseContList = new ArrayList();
            obtenerValoresNptPredeterminados();
            viaAdministracion = new ViaAdministracion();
            obtenerEnvases();
            obtenerViasAdministracion();
            
            buscarOrdenesPreparacion();

// TODO 10AGO23 HHRC -  eliminar hardcode
//            String idTipoSolucion = "e695144c-0a63-11eb-a03a-000c29750049";
//            obtenerListaHorarios(idTipoSolucion);
            //Se busca la lista de calorias de mediamento
            listaMedicamentos = new ArrayList<>();
            listaMedicamentos.addAll(medicamentoService.obtenerCaloriasMedicamentos());

            //Se buscan los medicamentos parenterales
            listaMedicamentosNPT = new ArrayList<>();
            listaMedicamentosNPT.addAll(medicamentoService.obtenerMedicamentosParentales());

            obtenerTiposSolucion();

            this.numMaxCompatibilidad = sesion.getNumMaxCompatibilidad();
            this.factorMolMonovaPotasio = sesion.getFactorMolMonovaPotasio();
            this.factorMolDivalZinc = sesion.getFactorMolDivalZinc();
            this.factorMolesDivalEstabilidad = sesion.getFactorMolesDivalEstabilidad();
            this.numMaxEstabilidad = sesion.getNumMaxEstabilidad();
            this.numMaxOsmolaridad = sesion.getNumMaxOsmolaridad();

            this.listClavesCalcCompatibilidad = obtenerClavesParaCalculos(TipoCalculoNpt_Enum.COMPATIBILIDAD.getValue());
            this.listClavesCalcEstabMMonovalP1 = obtenerClavesParaCalculos(TipoCalculoNpt_Enum.ESTABILIDAD_ELECT_MONOVAL_P1.getValue());
            this.listClavesCalcEstabMolDivalP1 = obtenerClavesParaCalculos(TipoCalculoNpt_Enum.ESTABILIDAD_ELECT_DIVAL_P1.getValue());
            this.listClavesCalcEstabMMolDivalP1 = obtenerClavesParaCalculos(TipoCalculoNpt_Enum.ESTABILIDAD_ELECT_MONOVAL_P2.getValue());
            this.listClavesCalcEstabMolDivalP2 = obtenerClavesParaCalculos(TipoCalculoNpt_Enum.ESTABILIDAD_ELECT_DIVAL_P2.getValue());
            
// TODO: Crear catalogo de sobrellenado en y hacerlo diámico por edad
            this.sobrelleadoList = new ArrayList<>();
            Sobrellenado s = new Sobrellenado();
//            s.setIdSobrellenado(20);
//            s.setDescripcion("20");
//            s.setActivo(true);
//            this.sobrelleadoList.add(s);
            s.setIdSobrellenado(20);
            s.setDescripcion("20");
            s.setActivo(true);
            this.sobrelleadoList.add(s);
            s = new Sobrellenado();
            s.setIdSobrellenado(30);
            s.setDescripcion("30");
            s.setActivo(true);
            this.sobrelleadoList.add(s);
            
            this.solucion = new Solucion();

        } catch (Exception ex) {
            LOGGER.error("Error al inicializar valores :: {} ", ex.getMessage());
        }
    }

    public void obtenerCamas() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerCamas()");
        this.listaCamas = new ArrayList<>();
        String idServicio = null;
        this.listaCamas.addAll(this.solUtils.obtenerListaCamas(idServicio));
    }

    public void obtenerTipoPacientes() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerTipoPacientes()");
        this.listaTipoPacientes = new ArrayList<>();
        this.listaTipoPacientes.addAll(this.solUtils.obtenerListaTipoPacientes());
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.initialize()");
        paramError = "error";
        surSinAlmacen = "sur.sin.almacen";
        paramEstatus = "estatus";
//        errRegistroIncorrecto = "err.registro.incorrecto";
//        surIncorrecto = "sur.incorrecto";
//        surInvalido = "sur.invalido";
        this.cadenaBusqueda = "";
        this.usuarioSelected = new Usuario();
        this.administrador = Constantes.INACTIVO;
        this.listaEstructuras = new ArrayList<>();
//        this.idEstructura = "";
        this.contEdit = Constantes.ACTIVO;
        this.editar = Constantes.INACTIVO;
        this.listaMedicamentos = new ArrayList<>();
        this.pacienteSelect = new Paciente_Extended();
        this.medicoSelect = new Usuario();
        this.nutricionParenteralSelect = new NutricionParenteralExtended();
        this.caloriaAminoacido = new Medicamento();
        this.prescripcionPaciente = new Prescripcion();
        this.listPrescripcionInsumos = new ArrayList<>();
        this.hipersensibilidades = "";
        this.reacciones = "";
        this.fechaActual = new Date();
        this.pacienteNuevo = new Paciente();
        this.medicoExtended = new Usuario_Extended();
        this.mostrarBotones = true;
        this.pacienteServ = new PacienteServicio();
        this.visitaAbierta = new Visita();
        this.validaFormulario = "";
        this.deshabilitaValidar = Constantes.ACTIVO;
        this.isJefeArea = Constantes.INACTIVO;
        this.estructuraList = new ArrayList<>();
        this.idAlmacen = "";
        this.volumenPres = BigDecimal.ZERO;
        this.posicion = 0;
        this.listaEstructuras = obtenerEstructuras();
        this.estructuraList = new ArrayList<>();
        this.nutricionParenteralSelect.setVolumenTotal(BigDecimal.ZERO);
        this.nutricionParenteralSelect.setPesoTotal(BigDecimal.ZERO);
        this.nutricionParenteralSelect.setCaloriasTotal(BigDecimal.ZERO);

        this.estatusSolucionLista = new ArrayList<>();
        this.estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
        this.estatusSolucionLista.add(EstatusSolucion_Enum.ORDEN_CREADA.getValue());
        this.estatusSolucionLista.add(EstatusSolucion_Enum.OP_RECHAZADA.getValue());

        this.porValidar = EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.name();
        this.validada = EstatusSolucion_Enum.OP_VALIDADA.name();
        this.rechazada = EstatusSolucion_Enum.OP_RECHAZADA.name();

        this.numMinutosEntregaMezcla = this.sesion.getNumMinutosEntregaMezcla();
        this.numDiasMaxPresMezcla = this.sesion.getNumDiasMaxPrescMezcla();
        calculaFechaMinimaSolicitud();
    }

    private List<Estructura> obtenerEstructuras() {
        List<Estructura> listEstructuras = null;
        try {
            List<Integer> listaTipos = new ArrayList<>();
            listaTipos.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
            listEstructuras = estructuraService.obtenerEstructurasPorTipo(listaTipos);
//            idEstructura = listEstructuras.get(0).getIdEstructura();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo PrescribirMezclaNPTMB :: obtenerEstructuras{}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("Error al obtener las estructuras o servicios!!"), null);
        }
        return listEstructuras;
    }

    public void nuevaMezcla() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.nuevaMezcla()");
        boolean res = false;
        if (usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);
        } else if (!permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permiso de esta acción.", null);
        } else {
            try {
                this.volumenPres = BigDecimal.ZERO;
//                this.cantidadPres = BigDecimal.ZERO;
                this.posicion = 0;
                this.validaFormulario = "";
                this.editar = Constantes.INACTIVO;
                this.mostrarBotones = Constantes.ACTIVO;
                caloriaAminoacido = new Medicamento();
                medicoSelect = usuarioSelected;
                pacienteSelect = null;
                nutricionParenteralSelect = new NutricionParenteralExtended();
                nutricionParenteralSelect.setIdNutricionParenteral(Comunes.getUUID());
                //            nutricionParenteralSelect.setIdEstructura(idEstructura);            
                nutricionParenteralSelect.setVolumenTotal(BigDecimal.ZERO);
                nutricionParenteralSelect.setPesoTotal(BigDecimal.ZERO);
                nutricionParenteralSelect.setCaloriasTotal(BigDecimal.ZERO);
                nutricionParenteralSelect.setMonovalentes(BigDecimal.ZERO);
                nutricionParenteralSelect.setDivalentes(BigDecimal.ZERO);
                nutricionParenteralSelect.setCalcMonovalente(BigDecimal.ZERO);
                nutricionParenteralSelect.setCalcDivalente(BigDecimal.ZERO);
                nutricionParenteralSelect.setPrecipitacion(BigDecimal.ZERO);
                nutricionParenteralSelect.setEstabilidad(BigDecimal.ZERO);
                nutricionParenteralSelect.setCalcPesoVolumen(BigDecimal.ZERO);
                nutricionParenteralSelect.setPorcenEnergiaTotal(BigDecimal.ZERO);
                nutricionParenteralSelect.setPrecipitacion(BigDecimal.ZERO);
                nutricionParenteralSelect.setResultPrecipitacion("");
                nutricionParenteralSelect.setResultEstabilidad("");
                nutricionParenteralSelect.setTotalOsmolaridad(BigDecimal.ZERO);
                nutricionParenteralSelect.setResulOsmolaridad("");
                
                //Se obtienen los medicamentos que se utilizaran para generar la orden de preparación
                List<NutricionParenteralDetalleExtended> listaMedicamentosParenterales = medicamentoService.obtenerMedicamentosParentales();
                nutricionParenteralSelect.setListaMezclaMedicamentos(listaMedicamentosParenterales);
                for (NutricionParenteralDetalleExtended nutricionPar : listaMedicamentosParenterales) {
                    nutricionPar.setOsmolaridad(BigDecimal.ZERO);
                    nutricionPar.setDensidad(BigDecimal.ZERO);
                    nutricionPar.setVolPrescrito(BigDecimal.ZERO);
                    nutricionPar.setCantCalculada(BigDecimal.ZERO);
                    List<InventarioExtended> listInventario = inventarioService.obtenerInventarioPorIdEstructurayporIdInsumo(idAlmacen, nutricionPar.getIdMedicamento());
                    if (listInventario != null) {
                        if (!listInventario.isEmpty()) {
                            nutricionPar.setListaInventarios(listInventario);
                        }
                    }
                }

//                calculaFechaMinimaSolicitud();
                nutricionParenteralSelect.setFechaProgramada(fechaMinPrescrMezcla);
                //            nutricionParenteralSelect.setFechaPrepara(new Date());
//                nutricionParenteralSelect.setFechaParaEntregar(fechaMinPrescrMezcla);

                // Se obtiene el tipo de documento
                int tipoDocumento = TipoDocumento_Enum.ORDENPREPARACIONMEZCLA.getValue();
                // Se obtiene el objeto folio
                folioOrden = foliosService.obtenerPrefixPorDocument(tipoDocumento);
                // se obtien el numero de folio
                String folioPreparacion = Comunes.generaFolio(folioOrden);
                nutricionParenteralSelect.setFolioPreparacion(folioPreparacion);
                // Se actualiza el objeto para actualizar la secuencia
                folioOrden.setUpdateFecha(new Date());
                folioOrden.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                folioOrden.setSecuencia(Comunes.separaFolio(folioPreparacion));
                //Se obtiene la lista de medicamentos de calorias
                listaMedicamentos = medicamentoService.obtenerCaloriasMedicamentos();
                //TODO : 10AGO2023  HHRC - Revisar como se va a quedar este valor para el aminoacido
                for (Medicamento medicamento : listaMedicamentos) {
                    if (medicamento.getClaveInstitucional().equals("010.000.0024.00")) {
                        caloriaAminoacido = medicamento;
                        break;
                    }
                }
                //            Estructura estructura = estructuraService.obtenerEstructura(idEstructura);
                //            nutricionParenteralSelect.setNombreEstructura(estructura.getNombre());
                //this.listaDiagnosticos  = new ArrayList<>();
                hipersensibilidades = "";
                reacciones = "";
                deshabilitaValidar = Constantes.ACTIVO;
                nutricionParenteralSelect.setFolio("0");

                viaAdministracion = new ViaAdministracion();
                viaAdministracion.setIdViaAdministracion(ViaAdministracion_Enum.INTRAVENOSA.getValue());
                nutricionParenteralSelect.setIdViaAdministracion(ViaAdministracion_Enum.INTRAVENOSA.getValue());
                nutricionParenteralSelect.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                nutricionParenteralSelect.setEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.toString().replace("_", " "));
                nutricionParenteralSelect.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
                nutricionParenteralSelect.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
                nutricionParenteralSelect.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                nutricionParenteralSelect.setListaDiagnosticos(new ArrayList<>());
                
                nutricionParenteralSelect.setPerfusionContinua(true);
                
                editable = true;
                res = true;

                
                
            } catch (Exception ex) {
                LOGGER.error("Error en el metodo nuevaMezcla :: {}", ex.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, res);
    }

    public void buscarPrescripcion() {
        try {//Se busca prescripcion por folio al momento de escribir y cambiarse del texto folio
            if (nutricionParenteralSelect.getFolio() != null || !"".equals(nutricionParenteralSelect.getFolio())) {
                prescripcionPaciente = prescripcionService.obtenerByFolioPrescripcion(nutricionParenteralSelect.getFolio());
                if (prescripcionPaciente != null) {
                    nutricionParenteralSelect.setFechaProgramada(prescripcionPaciente.getFechaPrescripcion());
                    medicoSelect = usuarioService.obtenerUsuarioByIdUsuario(prescripcionPaciente.getIdMedico());
                    if (medicoSelect != null) {
                        nutricionParenteralSelect.setIdMedico(medicoSelect.getIdUsuario());
                    }
                    pacienteSelect = pacienteService.obtenerPacienteByIdPrescripcion(prescripcionPaciente.getIdPrescripcion());
                    if (pacienteSelect != null) {
                        nutricionParenteralSelect.setIdPaciente(pacienteSelect.getIdPaciente());
                        nutricionParenteralSelect.setPacienteNumero(pacienteSelect.getPacienteNumero());
                        nutricionParenteralSelect.setClaveDerechoHabiencia(pacienteSelect.getClaveDerechohabiencia());
                        Visita visita = new Visita();
                        visita.setIdPaciente(pacienteSelect.getIdPaciente());
                        visitaAbierta = visitaService.obtenerVisitaAbierta(visita);
                        if (visitaAbierta != null) {
                            nutricionParenteralSelect.setNumeroVisita(visitaAbierta.getNumVisita());
                            nutricionParenteralSelect.setFechaIngreso(visitaAbierta.getFechaIngreso());
                            PacienteServicio pacienteServicio = new PacienteServicio();
                            pacienteServicio.setIdVisita(visitaAbierta.getIdVisita());
                            pacienteServ = pacienteServicioService.obtenerPacienteServicioAbierto(pacienteServicio);
                            if (pacienteServ != null) {
                                Estructura servicio = estructuraService.obtenerEstructura(pacienteServ.getIdEstructura());
                                if (servicio != null) {
                                    idEstructura = servicio.getIdEstructura();
                                    nutricionParenteralSelect.setIdEstructura(idEstructura);
                                    nutricionParenteralSelect.setNombreEstructura(servicio.getNombre());
                                }
                            }
                        }
                        CamaExtended cama = camaService.obtenerCamaNombreEstructura(pacienteSelect.getIdPaciente());
                        if (cama != null) {
                            nutricionParenteralSelect.setUbicacion(cama.getNombreCama());
                        }
                        //Se buscan los diagnosticos del paciente
                        List<Diagnostico> listaDiagnosticos = diagnosticosService.obtenerPorIdPacienteIdVisitaIdPrescripcion(pacienteSelect.getIdPaciente(),
                                visitaAbierta.getIdVisita(), prescripcionPaciente.getIdPrescripcion());
                        nutricionParenteralSelect.setListaDiagnosticos(listaDiagnosticos);
                        /*for(Diagnostico diagnostico : listaDiagnosticos) {
                            if(diagnosticos.isEmpty()) {
                                diagnosticos = diagnostico.getClave() + ": " + diagnostico.getNombre();
                            } else {
                                diagnosticos = diagnosticos + ", " + diagnostico.getClave() + ": " + diagnostico.getDescripcion();
                            }                            
                        }*/
                        //Se buscan las prescripciones insumo para no generarlas al momento de validar
                        listPrescripcionInsumos = prescripcionInsumoService.obtenerPrescripcionInsumosPorIdPrescripcion(prescripcionPaciente.getIdPrescripcion());
                        List<NutricionParenteralDetalleExtended> listaDetalleNutricion = new ArrayList<>();
                        if (!listPrescripcionInsumos.isEmpty()) {
                            //Se realiza recorrido para ver los medicamentos que unicamente se mostraran para realizar la mezcla
                            for (PrescripcionInsumo prescripcionInsumo : listPrescripcionInsumos) {
                                for (NutricionParenteralDetalleExtended nutricionParenteralDeta : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                                    if (prescripcionInsumo.getIdInsumo().equals(nutricionParenteralDeta.getIdMedicamento())) {
                                        listaDetalleNutricion.add(nutricionParenteralDeta);
                                        break;
                                    }
                                }
                            }
                            nutricionParenteralSelect.setListaMezclaMedicamentos(listaDetalleNutricion);
                            //Se buscan las hipersensibilidades del paciente
                            List<HipersensibilidadExtended> listaHipersensibilidad = obtenerListaHipersensibilidad(pacienteSelect.getIdPaciente());
                            if (!listaHipersensibilidad.isEmpty()) {
                                nutricionParenteralSelect.setListaReaccionesHipersensibilidad(listaHipersensibilidad);
                                for (HipersensibilidadExtended hipersensibilidad : listaHipersensibilidad) {
                                    if (hipersensibilidades.isEmpty()) {
                                        hipersensibilidades = hipersensibilidad.getNombreComercial() + ": " + hipersensibilidad.getTipoAlergia();
                                    } else {
                                        hipersensibilidades = hipersensibilidades + hipersensibilidad.getNombreComercial() + ": " + hipersensibilidad.getTipoAlergia();
                                    }
                                }
                            }
                            //Se buscan las reacciones adversas del paciente
                            List<ReaccionExtend> listaReaccionesRAM = obtenerListaRAM(pacienteSelect.getIdPaciente());
                            if (!listaReaccionesRAM.isEmpty()) {
                                nutricionParenteralSelect.setListaReacciones(listaReaccionesRAM);
                                for (ReaccionExtend reaccion : listaReaccionesRAM) {
                                    if (reacciones.isEmpty()) {
                                        reacciones = reaccion.getMedicamento() + ": " + reaccion.getTipo();
                                    } else {
                                        reacciones = reacciones + reaccion.getMedicamento() + ": " + reaccion.getTipo();
                                    }
                                }
                            }
                        }
                    }
                } else {
                    medicoSelect = null;
                    nutricionParenteralSelect.setPacienteNumero("");
                    nutricionParenteralSelect.setClaveDerechoHabiencia("");
                    pacienteSelect = null;
                    nutricionParenteralSelect.setEdad(null);
                    nutricionParenteralSelect.setPeso(null);
                    nutricionParenteralSelect.setNumeroVisita("");
                    nutricionParenteralSelect.setFechaIngreso(fechaActual);
                    nutricionParenteralSelect.setUbicacion("");
                    //this.listaDiagnosticos  = new ArrayList<>();
                    reacciones = "";
                    hipersensibilidades = "";
                    //Se obtienen los medicamentos que se utilizaran para generar la orden de preparación
                    List<NutricionParenteralDetalleExtended> listaMedicamentosParenterales = medicamentoService.obtenerMedicamentosParentales();
                    nutricionParenteralSelect.setListaMezclaMedicamentos(listaMedicamentosParenterales);
                    for (NutricionParenteralDetalleExtended nutricionPar : listaMedicamentosParenterales) {
                        nutricionPar.setOsmolaridad(BigDecimal.ZERO);
                        nutricionPar.setDensidad(BigDecimal.ZERO);
                        nutricionPar.setVolPrescrito(BigDecimal.ZERO);
                        List<InventarioExtended> listInventario = inventarioService.obtenerInventarioPorIdEstructurayporIdInsumo(idAlmacen, nutricionPar.getIdMedicamento());
                        if (listInventario != null) {
                            if (!listInventario.isEmpty()) {
                                nutricionPar.setListaInventarios(listInventario);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar la prescripción:    " + ex.getMessage());
        }
    }

    /**
     * Se buscan ordenes de preparacion por idEstructura o por busqueda
     */
    public void buscarOrdenesPreparacion() {
        try {
            //Se buscan las ordenes de preparación
            List<Integer> estatusSolucionLista = new ArrayList<>();
            estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue());
            estatusSolucionLista.add(EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue());
            if (cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }
            ordenMezclaLazy = new OrdenMezclaLazy(
                    nutricionParenteralService,
                    cadenaBusqueda,
                    idEstructura,
                    estatusSolucionLista);
            LOGGER.debug("Resultados {} ", ordenMezclaLazy.getTotalReg());
        } catch (Exception ex) {
            LOGGER.error("Error al buscar ordenes de preparacion   " + ex.getMessage());
        }
    }

    /**
     * Se realia la busqueda del detalle de la orden de preparación
     *
     * @param nutricionParenteral
     */
    public void obtenerDetalleOrdenPreparacion(NutricionParenteralExtended nutricionParenteral) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerDetalleOrdenPreparacion()");
        nutricionParenteralSelect = nutricionParenteral;
        obtenerDetalleOrdenPreparacion();
    }

    /**
     * Se realia la busqueda del detalle de la orden de preparación
     */
    public void obtenerDetalleOrdenPreparacion() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerDetalleOrdenPreparacion()");
        this.validaFormulario = "";
        this.editar = Constantes.ACTIVO;
        if (nutricionParenteralSelect == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla de nutrición inválida. ", null);

        } else {
            //Se valida si se muestran los botones o no
            if (permiso.isPuedeAutorizar()) {
                this.mostrarBotones = Constantes.ACTIVO;
            } else if (Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue())) {
                this.mostrarBotones = Constantes.ACTIVO;
            } else {
                this.mostrarBotones = Constantes.INACTIVO;
            }
            try {
                //Se busca la nutricion parenteral que se selecciono
                nutricionParenteralSelect = nutricionParenteralService.obtenerNutricionParenteralById(nutricionParenteralSelect.getIdNutricionParenteral());
                if (nutricionParenteralSelect == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla de nutrición inválida. ", null);

                } else {

                    nutricionParenteralSelect.setListaMezclaMedicamentos(this.listaMedicamentosNPT);
                    for (NutricionParenteralDetalleExtended nutricionPar : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                        nutricionPar.setOsmolaridad(BigDecimal.ZERO);
                        nutricionPar.setDensidad(BigDecimal.ZERO);
                        nutricionPar.setVolPrescrito(BigDecimal.ZERO);
                        List<InventarioExtended> listInventario = inventarioService.obtenerInventarioPorIdEstructurayporIdInsumo(idAlmacen, nutricionPar.getIdMedicamento());
                        if (listInventario != null) {
                            if (!listInventario.isEmpty()) {
                                nutricionPar.setListaInventarios(listInventario);
                            }
                        }
                    }
                    if (nutricionParenteralSelect.getIdMedico() != null) {
                        medicoSelect = usuarioService.obtenerUsuarioByIdUsuario(nutricionParenteralSelect.getIdMedico());
                    }
                    if (nutricionParenteralSelect.getIdPaciente() != null) {
                        pacienteSelect = pacienteService.obtenerPacienteCompletoPorId(nutricionParenteralSelect.getIdPaciente());
                    }
                    visitaAbierta = visitaService.obtenerVisitaPorNumero(nutricionParenteralSelect.getNumeroVisita());
                    if (visitaAbierta != null) {
                        PacienteServicio pacienteServicio = new PacienteServicio();
                        pacienteServicio.setIdVisita(visitaAbierta.getIdVisita());
                        pacienteServ = pacienteServicioService.obtenerPacienteServicioAbierto(pacienteServicio);
                        if (pacienteServ != null) {
                            PacienteUbicacion pacienteUbica = new PacienteUbicacion();
                            pacienteUbica.setIdPacienteServicio(pacienteServ.getIdPacienteServicio());
                            PacienteUbicacion ubicacion = pacienteUbicacionService.obtener(pacienteUbica);
                            if (ubicacion != null) {
                                Cama cama = camaService.obtenerCama(ubicacion.getIdCama());
                                if (cama != null) {
                                    nutricionParenteralSelect.setUbicacion(cama.getNombreCama());
                                }
                            }
                        }
                    }
                    //Se busca la prescripción en caso que exista    
                    if (nutricionParenteralSelect.getFolio() != null) {
                        prescripcionPaciente = prescripcionService.obtenerByFolioPrescripcion(nutricionParenteralSelect.getFolio());
                    }
                    //Se busca las prescripciones inumos si existe la prescripcion
                    if (prescripcionPaciente != null) {
                        listPrescripcionInsumos = prescripcionInsumoService.obtenerPrescripcionInsumosPorIdPrescripcion(prescripcionPaciente.getIdPrescripcion());
                    }
                    //se buscan los medicamentos guardados
                    List<NutricionParenteralDetalleExtended> listaMedicamentosGuardados = nutricionParenteralDetalleService.obtenerNutricionParenteralDetalleById(nutricionParenteralSelect.getIdNutricionParenteral());
                    if (prescripcionPaciente == null) {
                        //Se realiza recorrido para solo dejar los medicamentos que tiene la prescripcion en caso de que exista
                        for (NutricionParenteralDetalleExtended nutricionGuardada : listaMedicamentosGuardados) {
                            for (NutricionParenteralDetalleExtended nutricionParenteral : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                                if (nutricionParenteral.getIdMedicamento().equals(nutricionGuardada.getIdMedicamento())) {
                                    nutricionParenteral.setIdNutricionParenteralDetalle(nutricionGuardada.getIdNutricionParenteralDetalle());
                                    nutricionParenteral.setIdNutricionParenteral(nutricionGuardada.getIdNutricionParenteral());
                                    nutricionParenteral.setVolPrescrito(nutricionGuardada.getVolPrescrito());
                                    nutricionParenteral.setCantCalculada(nutricionGuardada.getCantCalculada());
                                    nutricionParenteral.setIdInventario(nutricionGuardada.getIdInventario());
                                    if (nutricionGuardada.getPorcentajeConcentracion() != null) {
                                        nutricionParenteral.setPorcentajeConcentracion(nutricionGuardada.getPorcentajeConcentracion());
                                    }
                                    if (nutricionGuardada.getPorcentajeCalculado() != null) {
                                        nutricionParenteral.setPorcentajeCalculado(nutricionGuardada.getPorcentajeCalculado());
                                    }
                                    nutricionParenteral.setOsmolaridad(nutricionGuardada.getOsmolaridad());
                                    nutricionParenteral.setDensidad(nutricionGuardada.getDensidad());
                                    nutricionParenteral.setPeso(nutricionGuardada.getPeso());
                                    if (nutricionGuardada.getCalorias() != null) {
                                        nutricionParenteral.setCalorias(nutricionGuardada.getCalorias());
                                    }
                                    if (nutricionGuardada.getPorcentajeEnergia() != null) {
                                        nutricionParenteral.setPorcentajeEnergia(nutricionGuardada.getPorcentajeEnergia());
                                    }
                                    if (nutricionGuardada.getNitrogeno() != null) {
                                        nutricionParenteral.setNitrogeno(nutricionGuardada.getNitrogeno());
                                    }
                                    nutricionParenteral.setCalculoOsmolaridad(nutricionGuardada.getCalculoOsmolaridad());
                                    break;
                                }
                            }
                        }
                    } else {
                        nutricionParenteralSelect.setListaMezclaMedicamentos(listaMedicamentosGuardados);
                    }
                    if (nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
                        deshabilitaValidar = Constantes.INACTIVO;
                    } else {
                        deshabilitaValidar = Constantes.ACTIVO;
                    }
                    if (nutricionParenteralSelect.getDiagnosticos() != null) {
                        if (!nutricionParenteralSelect.getDiagnosticos().isEmpty()) {
                            String[] lisDiagnosticos = nutricionParenteralSelect.getDiagnosticos().split("; ");
                            List<Diagnostico> diagnosticosPaciente = new ArrayList<>();
                            if (lisDiagnosticos.length > 0) {
                                for (int i = 0; i < lisDiagnosticos.length; i++) {
                                    if (!lisDiagnosticos[i].trim().equals(StringUtils.EMPTY)) {
                                        Diagnostico unDiagnostico = diagnosticosService.obtenerDiagnosticoPorNombre(lisDiagnosticos[i]);
                                        if (unDiagnostico != null) {
                                            diagnosticosPaciente.add(unDiagnostico);
                                        }
                                    }
                                }
                                nutricionParenteralSelect.setListaDiagnosticos(diagnosticosPaciente);
                            }
                        }
                    }
                    if (nutricionParenteralSelect.getIdViaAdministracion() != null) {
                        ViaAdministracion vad = viaAdministracionService.obtenerPorId(nutricionParenteralSelect.getIdViaAdministracion());
                        if (vad != null) {
                            nutricionParenteralSelect.setViaAdministracion(vad.getNombreViaAdministracion());
                        }
                    }
                    evaluaEdicion();
                }
            } catch (Exception ex) {
                LOGGER.error("Error al buscar el detalle de la orden de preparación :: {} ", ex.getMessage());
            }
        }
    }

    /**
     * Se obtiene la lista de diagnosticos del paciente
     *
     * @param idPaciente
     * @return Lista
     */
    public List<Diagnostico> obtenerListaDiagnosticos(String idPaciente) {
        List<Diagnostico> listaDiagnosticos = new ArrayList<>();
        try {
            listaDiagnosticos = diagnosticosService.obtenerDiagnosticoByIdPaciente(idPaciente);
        } catch (Exception ex) {
            LOGGER.error("Error al buscar los diagnosticos del paciente" + ex.getMessage());
        }
        return listaDiagnosticos;
    }

    /**
     * Se obtienen la lista de reacciones de hipersensibilidad del paciente
     *
     * @param idPaciente
     * @return Lista
     */
    public List<HipersensibilidadExtended> obtenerListaHipersensibilidad(String idPaciente) {
        List<HipersensibilidadExtended> listaHipersensibilidades = new ArrayList<>();
        try {
            List<MedicamentoDTO> listaMedicametos = new ArrayList<>();
            if (!nutricionParenteralSelect.getListaMezclaMedicamentos().isEmpty() || nutricionParenteralSelect.getListaMezclaMedicamentos() != null) {
                getListaMedicamentosDTO(listaMedicametos);
                listaHipersensibilidades = hipersensibilidadService.obtenerListaReacHiperPorIdPaciente(idPaciente, listaMedicametos);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar las Hipersensibilidades del paciente" + ex.getMessage());
        }
        return listaHipersensibilidades;
    }

    /**
     * Obtener lista de reacciones
     *
     * @param idPaciente
     * @return Lista
     */
    public List<ReaccionExtend> obtenerListaRAM(String idPaciente) {
        List<ReaccionExtend> listaReacciones = new ArrayList<>();
        try {
            List<String> listaInsumos = new ArrayList<>();
            if (!nutricionParenteralSelect.getListaMezclaMedicamentos().isEmpty() || nutricionParenteralSelect.getListaMezclaMedicamentos() != null) {
                nutricionParenteralSelect.getListaMezclaMedicamentos().forEach((nutricionDetalleParental) -> {
                    listaInsumos.add(nutricionDetalleParental.getIdMedicamento());
                });
                listaReacciones = reaccionService.obtenerReaccionesByIdPacienteIdInsumos(idPaciente, listaInsumos);
            }

        } catch (Exception ex) {
            LOGGER.error("Error al buscar las RAM del paciente" + ex.getMessage());
        }
        return listaReacciones;
    }

    /**
     * Para realizar calculo al momento de ingresar un volumen prescrito
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.onCellEdit()");
        if (event.getNewValue() == null){
            return;
        }
        boolean res = false;
        try {
            FacesMessage msg = new FacesMessage("Calcula", String.valueOf("Calcula"));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            BigDecimal newValue = BigDecimal.ZERO;
            if (event == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Valor o evento inválido.", null);
            } else {
                newValue = (BigDecimal) event.getNewValue();
                if (newValue == null) {
                    newValue = BigDecimal.ZERO;
                }
                oldValue = (BigDecimal) event.getOldValue();
                if (oldValue == null) {
                    oldValue = BigDecimal.ZERO;
                }
                position = event.getRowIndex();
            }

            BigDecimal dosisPrescrita = newValue;
            BigDecimal volumenCalculado = BigDecimal.ZERO;
            BigDecimal volumenAntCalculado = BigDecimal.ZERO;

//            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position);
//            NutricionParenteralExtended np = nutricionParenteralSelect;
            if (nutricionParenteralSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripcion NPT inválida.", null);

            } else if (nutricionParenteralSelect.getListaMezclaMedicamentos() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Lista de medicamentos inválida.", null);

            } else if (nutricionParenteralSelect.getListaMezclaMedicamentos().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Lista de medicamentos inválida.", null);

            } else if (nutricionParenteralSelect.getListaMezclaMedicamentos().get(position) == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamento prescrito inválido.", null);

            } else if (nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).getIdMedicamento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamento prescrito inválido.", null);

            } else {
                String idMedicamento = nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).getIdMedicamento();
                Medicamento m = medicamentoService.obtenerMedicamento(idMedicamento);
                if (m == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamento prescrito inválido.", null);

                } else if (m.getIdMedicamento() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamento prescrito inválido.", null);

                } else if (m.getClaveInstitucional() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamento prescrito inválido.", null);

                } else if (m.getConcentracion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Concentración de medicamento prescrito inválido.", null);

                } else if (m.getConcentracion().compareTo(BigDecimal.ZERO) == 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Concentración de medicamento prescrito inválido.", null);

                } else if (m.getConcentracion().compareTo(BigDecimal.ZERO) == -1) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Concentración de medicamento prescrito inválido.", null);

                } else if (m.getIdUnidadConcentracion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Unidad de concentración de medicamento prescrito inválida.", null);

                } else if (m.getIdUnidadConcentracion() == 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Unidad de concentración de medicamento prescrito inválida.", null);

                } else if (m.getCantPorEnvase() == null ) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Unidad de concentración de medicamento prescrito inválida.", null);

                } else if (m.getCantPorEnvase() <= 0 ) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Unidad de concentración de medicamento prescrito inválida.", null);

                } else {
                    UnidadConcentracion uc = unidadService.obtener(new UnidadConcentracion(m.getIdUnidadConcentracion()));

                    if (uc == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Unidad de concentración de medicamento prescrito inválida.", null);
                    } else {
                        NutricionParenteralDetalleExtended nutricionParenteral = nutricionParenteralSelect.getListaMezclaMedicamentos().get(position);
                        if (nutricionParenteral == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Medicamento prescrito inválido.", null);

                        } else if (nutricionParenteral.getVolPrescrito() == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Dosis Prescrita inválida.", null);

                        } else if (nutricionParenteral.getVolPrescrito().compareTo(BigDecimal.ZERO) == 0) {
                            volumenAntCalculado = oldValue.divide(m.getConcentracion(), 3, RoundingMode.HALF_UP);
                            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setCantCalculada(BigDecimal.ZERO);

                        } else if (nutricionParenteral.getVolPrescrito().compareTo(BigDecimal.ZERO) == 1) {
                            if (uc.getIdUnidadConcentracion() == 2) {
// RN : si la unidad del medicamento es de (mL), el valor no requiere cálculo
                                volumenCalculado = dosisPrescrita;
                                volumenAntCalculado = oldValue;

                            } else {
// RN : si la unidad del medicamento es de masa, el valor requiere cálculo
                                volumenCalculado = dosisPrescrita.divide(m.getConcentracion(), 3, RoundingMode.HALF_UP);
                                volumenAntCalculado = oldValue.divide(m.getConcentracion(), 3, RoundingMode.HALF_UP);
                            }
                            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setCantCalculada(volumenCalculado);

                        } else {
                            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setCantCalculada(BigDecimal.ZERO);
                        }
// RN: Se resta a volumen Total el valor anterior de volumen prescrito       
                        nutricionParenteralSelect.setVolumenTotal((nutricionParenteralSelect.getVolumenTotal().subtract(volumenAntCalculado)).setScale(3, RoundingMode.HALF_UP));
// RN: Se suma a volumen Total el valor nuevo de volumen prescrito
                        nutricionParenteralSelect.setVolumenTotal((nutricionParenteralSelect.getVolumenTotal().add(volumenCalculado)).setScale(3, RoundingMode.HALF_UP));

// RN: Se busca la osmolaridad, Calorias y densidad de cada insumo
// TODO: 13AGO2023 HHRC - Obtener calorias, densidad y osmolaridad por fabricante de inventario
                        if (m.getOsmolaridad() != null && m.getOsmolaridad() > 0) {
                            nutricionParenteral.setOsmolaridad(new BigDecimal(m.getOsmolaridad()).setScale(4, RoundingMode.HALF_UP));
                        } else {
                            nutricionParenteral.setOsmolaridad(BigDecimal.ZERO);
                        }

                        if (m.getDensidad() != null && m.getDensidad() > 0) {
                            nutricionParenteral.setDensidad(new BigDecimal(m.getDensidad()).setScale(4, RoundingMode.HALF_UP));
                        } else {
                            nutricionParenteral.setDensidad(BigDecimal.ZERO);
                        }
                        if (m.getCalorias() != null && m.getCalorias() > 0) {
                            nutricionParenteral.setCalorias(new BigDecimal(m.getCalorias()).setScale(4, RoundingMode.HALF_UP));
                        } else {
                            nutricionParenteral.setCalorias(BigDecimal.ZERO);
                        }

// RN: Si el insumo tiene densidad, se calcula el peso multiplicando el nuevo valor de volumen prescrito por la densidad del medicamento
                        if (nutricionParenteral.getDensidad().compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal densidad = nutricionParenteral.getDensidad();
                            BigDecimal pesoInsumo = (volumenCalculado.multiply(densidad)).setScale(4, RoundingMode.HALF_UP);
                            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setPeso(pesoInsumo);
                        }

// RN: Si tiene calorias, se calculan 
                        BigDecimal calorias = BigDecimal.ZERO;
                        BigDecimal caloriasAnteriores = BigDecimal.ZERO;
                        if (nutricionParenteral.getCalorias().compareTo(BigDecimal.ZERO) == 1) {
//Se obtiene las calorias anteriores para restar al total de calorias                    
                            if (oldValue != null) {
                                if (oldValue.compareTo(BigDecimal.ZERO) == 1) {
                                    BigDecimal val2 = new BigDecimal(m.getCalorias()).multiply(oldValue);
                                    caloriasAnteriores = val2.divide(new BigDecimal(1), 4, RoundingMode.HALF_UP);
//Se resta el total de calorias 
                                    nutricionParenteralSelect.setCaloriasTotal((nutricionParenteralSelect.getCaloriasTotal().subtract(caloriasAnteriores)).setScale(3, RoundingMode.HALF_UP));
                                }
                            }
// RN: Calorias se calcula X = (kcalInsumo * dosisPrescrita ) / gr proteinas
                            BigDecimal val1 = new BigDecimal(m.getCalorias()).multiply(dosisPrescrita);
                            calorias = val1.divide(new BigDecimal(1), 4, RoundingMode.HALF_UP);
// Se suman las nuevas calorias
                            nutricionParenteralSelect.setCaloriasTotal((nutricionParenteralSelect.getCaloriasTotal().add(calorias)).setScale(3, RoundingMode.HALF_UP));
//                            if (nutricionParenteral.getCalorias() != null) {
//                                caloriasAnteriores = nutricionParenteral.getCalorias();
//                            }
//Se guardan las nuevas calorias en registro
                            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setCalorias(calorias.setScale(3, RoundingMode.HALF_UP));

////Se calcula nitrogeno multiplicandp la cantidad calculada po 6.25
//                            if (cantCalculada.compareTo(BigDecimal.ZERO) == 1) {
////TODO Revisar de que manera tratar estos valores para que no queden en duro FALTA F11, 
//                                if (nutricionParenteral.getClaveMedicamento().equals("010.000.2512.01") || nutricionParenteral.getClaveMedicamento().equals("010.000.0001.00")
//                                        || nutricionParenteral.getClaveMedicamento().equals("010.000.2737.00") || nutricionParenteral.getClaveMedicamento().equals("010.000.0002.00")
//                                        || nutricionParenteral.getClaveMedicamento().equals("010.000.0003.00")) {
//                                    nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setNitrogeno(cantCalculada.divide(new BigDecimal("6.25"), 3, RoundingMode.HALF_UP));
//                                }
//                            } else {
//                                nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setNitrogeno(BigDecimal.ZERO);
//                            }
//                            //Se obtiene el porcntaje calculado multiplicando el nuevo valor de volumen prescrito por la concentracion de aminoacidos
//                            BigDecimal porcentajeCalculado = newValue.multiply(caloriaAminoacido.getConcentracion());
//                            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setPorcentajeCalculado(porcentajeCalculado.setScale(3, RoundingMode.HALF_UP));
                        }

                        BigDecimal pesoTotal = BigDecimal.ZERO;
                        BigDecimal porcentEnergiaTotal = BigDecimal.ZERO;
                        BigDecimal sumGlucFosfa = BigDecimal.ZERO;
                        BigDecimal monoPotaSodio = BigDecimal.ZERO;
                        BigDecimal cantCalFosfato = BigDecimal.ZERO;
                        BigDecimal concenFosfato = BigDecimal.ZERO;
                        BigDecimal divalGlucoSulfa = BigDecimal.ZERO;
                        BigDecimal osmolaridadTotal = BigDecimal.ZERO;
                        BigDecimal concentraZincCloruro = BigDecimal.ZERO;

// RN: si el insumo tiene osmolaridad, se calcula y se suma la Osmolaridad        
                        if (nutricionParenteral.getOsmolaridad().compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal val1 = volumenCalculado.multiply(nutricionParenteral.getOsmolaridad());
                            BigDecimal val2 = val1 ; //.divide(nutricionParenteralSelect.getVolumenTotal()).setScale(2, RoundingMode.HALF_UP);
                            nutricionParenteralSelect.getListaMezclaMedicamentos().get(position).setCalculoOsmolaridad(val2);
                        }
                        
                        BigDecimal kcalProt = BigDecimal.ZERO;
                        BigDecimal kcalNoProt = BigDecimal.ZERO;
                        BigDecimal kcalTotales = BigDecimal.ZERO;
        
                        calculosDeOrdenDePreparacion(
                                porcentEnergiaTotal, pesoTotal, sumGlucFosfa
                                , monoPotaSodio, divalGlucoSulfa, cantCalFosfato
                                , concenFosfato, osmolaridadTotal, concentraZincCloruro
                                , kcalProt, kcalNoProt, kcalTotales);
                        
                        asignaVelocidad();
                        res = true;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al realizar calculos onCellEdit : {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, res);
    }

    /*
    * Método que realiza los cálculos de Osmolaridad total, NCA, estabilida, compatibilidad
     */
    private void calculosDeOrdenDePreparacion(
            BigDecimal porcentEnergiaTotal, BigDecimal pesoTotal, BigDecimal sumGlucFosfa
            , BigDecimal monoPotaSodio, BigDecimal divalGlucoSulfa, BigDecimal cantCalFosfato
            , BigDecimal concenFosfato, BigDecimal osmolaridadTotal, BigDecimal concentraZincCloruro
            , BigDecimal kcalProteicas, BigDecimal kcalNoProteicas, BigDecimal kcalTotales) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.calculosDeOrdenDePreparacion()");
        int cont = 1;
        
        for (NutricionParenteralDetalleExtended nutricion : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
            LOGGER.debug(cont + " " + nutricion.getClaveMedicamento());
            cont++;
            nutricion.setPorcentajeConcentracion(BigDecimal.ZERO);
            nutricion.setPorcentajeEnergia(BigDecimal.ZERO);

//Se valida si el registro ya existe informacion de volumen prescrito
            if (nutricion.getVolPrescrito() != null && nutricion.getVolPrescrito().compareTo(BigDecimal.ZERO) == 1) {
                if (nutricion.isCalculoMezcla()) {
                    if (nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
//Se divide la cantidad calculada entre el volumen total
                        BigDecimal result = (nutricion.getCantCalculada().divide(nutricionParenteralSelect.getVolumenTotal(), 3, RoundingMode.HALF_UP));
//Se multiplica el resultado de la división por cien para sacar el porentaje de concentración
                        BigDecimal porcentajeConcentracion = result.multiply(new BigDecimal("100"));
                        nutricion.setPorcentajeConcentracion(porcentajeConcentracion);
                    }

//Se calcula el porcentaje de energia multiplicando las calorias por cien entre la suma total de las calorias
                    if (nutricionParenteralSelect.getCaloriasTotal().compareTo(BigDecimal.ZERO) == 1) {
                        BigDecimal caloriasXCien = nutricion.getCalorias().multiply(new BigDecimal("100"));
                        nutricion.setPorcentajeEnergia(caloriasXCien.divide(nutricionParenteralSelect.getCaloriasTotal(), 3, RoundingMode.HALF_UP));

                    } else {
                        nutricion.setPorcentajeEnergia(BigDecimal.ZERO);
                    }

                    porcentEnergiaTotal = porcentEnergiaTotal.add(nutricion.getPorcentajeEnergia());
                }

//Si ya tiene peso se va sumando
                if (nutricion.getPeso() != null) {
                    pesoTotal = pesoTotal.add(nutricion.getPeso());
                }
            }
            
            
            if (nutricion.getVolPrescrito() != null && nutricion.getVolPrescrito().compareTo(BigDecimal.ZERO) != 0) {
                
                Medicamento m = solUtils.obtenerInsumoPorId(nutricion.getIdMedicamento());
                
                if (m != null) {
                    if (m.getCalorias() != null && m.getCalorias() > 0) {
                        BigDecimal kcp = nutricion.getVolPrescrito().multiply(new BigDecimal(m.getCalorias()));
// RN: Cálculo de kcal no proteicas
                        if (Objects.equals(m.getTipoCalorias(), TipoCalorias_Enum.NO_PROTEICAS.getValue())) {
                            kcalNoProteicas = kcalNoProteicas.add(kcp).setScale(2, RoundingMode.HALF_UP);
// RN: Cálculo de kcal proteicas
                        } else if (Objects.equals(m.getTipoCalorias(), TipoCalorias_Enum.PROTEICAS.getValue())) {
                            kcalProteicas = kcalProteicas.add(kcp).setScale(2, RoundingMode.HALF_UP);
                        }
                    }
                }

//Se valida si es gluconato o potasio fosfato para realizar suma de cantidad calculada de estos dos medicamentos
                for (CalculoNpt cla : listClavesCalcCompatibilidad) {
                    if (nutricion.getClaveMedicamento().trim().equalsIgnoreCase(cla.getClaveInstitucional().trim())) {
                        sumGlucFosfa = sumGlucFosfa.add(nutricion.getVolPrescrito());
                    }
                }

//Se valida si es potasio o sodio para realizar suma de cantidad calculada de estos dos medicamentos
                for (CalculoNpt cla : listClavesCalcEstabMMonovalP1) {
                    if (nutricion.getClaveMedicamento().trim().equalsIgnoreCase(cla.getClaveInstitucional().trim())) {
                        monoPotaSodio = monoPotaSodio.add(nutricion.getVolPrescrito());
                    }
                }

//Se valida si es SULFATO de magnesio o GLUCONATO de calcio para realizar suma de cantidad calculada de estos dos medicamentos
                for (CalculoNpt cla : listClavesCalcEstabMolDivalP1) {
                    if (nutricion.getClaveMedicamento().trim().equalsIgnoreCase(cla.getClaveInstitucional().trim())) {
                        divalGlucoSulfa = divalGlucoSulfa.add(nutricion.getVolPrescrito());
                    }
                }

// Se valida si es fosfato potasio para obtener su cantidad calculada
                for (CalculoNpt cla : listClavesCalcEstabMMolDivalP1) {
                    if (nutricion.getClaveMedicamento().trim().equalsIgnoreCase(cla.getClaveInstitucional().trim())) {
                        cantCalFosfato = nutricion.getVolPrescrito();
                        concenFosfato = nutricion.getConcentracion();
                    }
                }

// Se valida si es Zinc para obtener su cantidad calculada
                concentraZincCloruro = BigDecimal.ZERO;
                for (CalculoNpt cla : listClavesCalcEstabMolDivalP2) {
                    if (nutricion.getClaveMedicamento().trim().equalsIgnoreCase(cla.getClaveInstitucional().trim())) {
                        concentraZincCloruro = nutricion.getVolPrescrito();
                    }
                }
            }


//Se valida si tiene volumen prescrito y osmolaridad para obtener el producto de estos y realizar la sumatoria total
            if (nutricion.getCalculoOsmolaridad() != null) {
                osmolaridadTotal = osmolaridadTotal.add(nutricion.getCalculoOsmolaridad());
            }

// RN agregar cálculo de sobrellenado
            if (nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
                if (nutricion.getCantCalculada().compareTo(BigDecimal.ZERO) == 1) {
                    BigDecimal ov1 = nutricionParenteralSelect.getVolumenTotal().add(BigDecimal.valueOf(30));
                    BigDecimal ov2 = nutricion.getCantCalculada().multiply(BigDecimal.valueOf(100));
                    BigDecimal ov3 = ov2.divide(nutricionParenteralSelect.getVolumenTotal(), 1, RoundingMode.HALF_UP);
                    BigDecimal ov4 = ov1.multiply(ov3);
                    BigDecimal ov5 = ov4.divide(BigDecimal.valueOf(100), 1, RoundingMode.HALF_UP);
                    BigDecimal ov6 = ov5.subtract(nutricion.getCantCalculada()).setScale(1, RoundingMode.HALF_UP);
                    nutricion.setSobrellenado(ov6);
                }
            }

        }

// RN: Kcalorias proteicas y no proteicas
        nutricionParenteralSelect.setKcalProteicas(kcalProteicas);
        nutricionParenteralSelect.setKcalNoProteicas(kcalNoProteicas);
        kcalTotales = kcalNoProteicas.add(kcalProteicas).setScale(2, RoundingMode.HALF_UP);
        nutricionParenteralSelect.setCaloriasTotal(kcalTotales);

//Se divide la sumatoria total de osmolaridad entre el volumen total
        if (osmolaridadTotal.compareTo(BigDecimal.ZERO) == 1
                && nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
            nutricionParenteralSelect.setTotalOsmolaridad(osmolaridadTotal.divide(nutricionParenteralSelect.getVolumenTotal(), 3, RoundingMode.HALF_UP));
//            nutricionParenteralSelect.setTotalOsmolaridad(osmolaridadTotal);
        } else {
            nutricionParenteralSelect.setTotalOsmolaridad(BigDecimal.ZERO);
        }

//para obtener el valor de la compatibilidad se divide la suma de GLUCOnato de calcio y FOSFATO potasio entre el volumen total para despues multiplicar por mil
        if (sumGlucFosfa.compareTo(BigDecimal.ZERO) == 1
                && nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal resulGlucFos = sumGlucFosfa.divide(nutricionParenteralSelect.getVolumenTotal(), 5, RoundingMode.HALF_UP);
            BigDecimal resulGlucFos2 = (resulGlucFos.multiply(new BigDecimal("1000"))).setScale(4, RoundingMode.HALF_UP);
            nutricionParenteralSelect.setPrecipitacion(resulGlucFos2);
        }

//Se compara el valor de comptabilidad para saber si es COMPATILE O PRECIPITA
        if (new BigDecimal(numMaxCompatibilidad).compareTo(nutricionParenteralSelect.getPrecipitacion()) == 1
                || new BigDecimal(numMaxCompatibilidad).compareTo(nutricionParenteralSelect.getPrecipitacion()) == 0) {
//            nutricionParenteralSelect.setResultPrecipitacion("COMPATIBLE");
            nutricionParenteralSelect.setResultPrecipitacion(RESOURCES2.getString("mez.npt.compatible"));
        } else {
//            nutricionParenteralSelect.setResultPrecipitacion("PRECIPITA");
            nutricionParenteralSelect.setResultPrecipitacion(RESOURCES2.getString("mez.npt.incompatible"));
        }

//Despues se asignan la suma de los moles monovalentes Cloruro de sodio y cloruro de Potasio
        nutricionParenteralSelect.setMonovalentes(monoPotaSodio);
        
        BigDecimal resultMonov = BigDecimal.ZERO;
        if (concenFosfato.compareTo(BigDecimal.ZERO) == 1
                && cantCalFosfato.compareTo(BigDecimal.ZERO) == 1) {    
//Se obtiene calculo de monovalentes Fosfato Potasio multiplicandp cantidad calculada de fosfato por 1.123 y dividirlo entre la concentración de fosfato
//Despues se suma este resultado con la suma de cantidad calculada de potasio y sodio
            resultMonov = (cantCalFosfato.multiply(new BigDecimal(factorMolMonovaPotasio))).divide(concenFosfato, 3, RoundingMode.HALF_UP);
            nutricionParenteralSelect.setMonovalentes(nutricionParenteralSelect.getMonovalentes().add(resultMonov));
        }

//Se obtiene el divalente dividiendo la suma de cantidad calculada de GLUCOSA y SULFATO entre 2
        nutricionParenteralSelect.setDivalentes(BigDecimal.ZERO);
        if (divalGlucoSulfa.compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal resultDivale = divalGlucoSulfa.divide(new BigDecimal("2"), 3, RoundingMode.HALF_UP);
            if (concentraZincCloruro.compareTo(BigDecimal.ZERO) == 1) {
                BigDecimal zincCloruro = concentraZincCloruro.multiply(new BigDecimal(factorMolDivalZinc).setScale(4, RoundingMode.HALF_UP));
                nutricionParenteralSelect.setDivalentes(resultDivale.add(zincCloruro));
            } else {
                nutricionParenteralSelect.setDivalentes(resultDivale); //TODO Solo falta ver la suma que dice que es N55 pero aparece como cero el valor
            }
        }

//Se realiza calculo de monovalentes dividiendo los monovalentes entre el volumen total para despues multiplicar el resultado po mil        
        if (nutricionParenteralSelect.getMonovalentes().compareTo(BigDecimal.ZERO) == 1
                && nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal equivalMono = nutricionParenteralSelect.getMonovalentes().divide(nutricionParenteralSelect.getVolumenTotal(), 5, RoundingMode.HALF_UP);
            nutricionParenteralSelect.setCalcMonovalente((equivalMono.multiply(new BigDecimal("1000"))).setScale(3, RoundingMode.HALF_UP));
        }

//Se realiza calculo de divalentes multiplicando los divalentes por 64 y despues dividirlo entre el volumen total para despues multiplicar el resultado por mil
        if (nutricionParenteralSelect.getDivalentes().compareTo(BigDecimal.ZERO) == 1
                && nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
            BigDecimal equivalDival = (nutricionParenteralSelect.getDivalentes().multiply(new BigDecimal(factorMolesDivalEstabilidad))).divide(nutricionParenteralSelect.getVolumenTotal(), 5, RoundingMode.HALF_UP);
            nutricionParenteralSelect.setCalcDivalente((equivalDival.multiply(new BigDecimal("1000"))).setScale(3, RoundingMode.HALF_UP));
        }

//Se calcula la estabilidad sumando los calculos de monovalentes y divallentes
        nutricionParenteralSelect.setEstabilidad(nutricionParenteralSelect.getCalcMonovalente().add(nutricionParenteralSelect.getCalcDivalente()));

//Se compara la estabilidad para saber si es NCA O INESTABILIDAD
        if (new BigDecimal(numMaxEstabilidad).compareTo(nutricionParenteralSelect.getEstabilidad()) == 1) {
//            nutricionParenteralSelect.setResultEstabilidad("ESTABLE");
            nutricionParenteralSelect.setResultEstabilidad(RESOURCES2.getString("mez.npt.estable"));
        } else {
//            nutricionParenteralSelect.setResultEstabilidad("INESTABLE");
            nutricionParenteralSelect.setResultEstabilidad(RESOURCES2.getString("mez.npt.inestable"));
        }

//Se realiza la sumatoria de peso total
        pesoTotal = pesoTotal.add(nutricionParenteralSelect.getCalcPesoVolumen());
        nutricionParenteralSelect.setPesoTotal(pesoTotal);
        nutricionParenteralSelect.setPorcenEnergiaTotal(porcentEnergiaTotal);
        if (nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
            deshabilitaValidar = Constantes.INACTIVO;
        } else {
            deshabilitaValidar = Constantes.ACTIVO;
        }

        if (new BigDecimal(numMaxOsmolaridad).compareTo(nutricionParenteralSelect.getTotalOsmolaridad()) < 1) {
            nutricionParenteralSelect.setViaAdministracion(RESOURCES2.getString("mez.npt.osmolaridadViaCentral"));
        } else {
            nutricionParenteralSelect.setViaAdministracion(RESOURCES2.getString("mez.npt.osmolaridadviaPeriferica"));
        }

    }


    private String determinarViaAdmonn(Integer tipoEdadPaciente) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.determinaViaAdmonn()");
        String res = "";
        try {
            // Neonatos / Pediátricos         Adultos  Vía Periférica      150 a 800         150 a 700    (mOsm/L)  Vía Central         800 a 1800       700 a 1800   (mOsm/L)
        } catch (Exception e) {
            LOGGER.error("Error al determinarViaAdmonn :: {} ", e.getMessage());
        }
        return res;
    }

    /**
     * Para cambiar valor de medicamentos de calorias
     *
     * @param event
     */
    public void onCellEdita(CellEditEvent event) {
        try {
            BigDecimal newValue = (BigDecimal) event.getNewValue();
            /*if(event.getOldValue() != null) {
                oldValue = (BigDecimal) event.getOldValue();
            } else {
                oldValue = BigDecimal.ZERO;
            }*/

            position = (int) event.getRowIndex();
            listaMedicamentos.get(position).setConcentracion(newValue);
            boolean actualizaMedica = medicamentoService.actualizar(listaMedicamentos.get(position));
            if (actualizaMedica) {
                //nutricionParenteralSelect.setVolumenTotal(BigDecimal.ZERO);
                nutricionParenteralSelect.setCaloriasTotal(BigDecimal.ZERO);
                BigDecimal porcentEnergiaTotal = BigDecimal.ZERO;
                for (NutricionParenteralDetalleExtended nutricion : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                    BigDecimal calorias = BigDecimal.ZERO;
                    if (nutricion.isCalculoMezcla()) {// TODO Falta revisar como obtener este valor devido a que se multiplica por diferente valor en algunos insumos
                        //Se calcula las calorias multiplicando la cantidad calculada por la cantidad de calorias  
                        Medicamento mediCaloria = medicamentoService.obtenerMedicamentoCaloria(nutricion.getIdMedicamento());
                        if (mediCaloria != null) {
                            //Revisar si solo los lipidos cambian para sacar las calorias y se toma el volumen
                            if (nutricion.getClaveMedicamento().equals("010.000.0006.00")) {
                                if (nutricion.getVolPrescrito() != null) {
                                    calorias = nutricion.getVolPrescrito().multiply(mediCaloria.getConcentracion());
                                    nutricion.setPorcentajeCalculado(nutricion.getVolPrescrito().multiply(caloriaAminoacido.getConcentracion()));
                                }
                            } else {
                                if (nutricion.getCantCalculada() != null) {
                                    calorias = nutricion.getCantCalculada().multiply(mediCaloria.getConcentracion());
                                }
                            }
                        }
                        nutricion.setCalorias(calorias);
                        nutricionParenteralSelect.setCaloriasTotal(nutricionParenteralSelect.getCaloriasTotal().add(calorias));
                    }
                }
                for (NutricionParenteralDetalleExtended nutricionParen : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                    if (nutricionParen.isCalculoMezcla()) {
                        //Se calcula el porcentaje de energia multiplicando las calorias por cien entre la suma total de las calorias
                        if (nutricionParen.getCalorias() != null) {
                            BigDecimal caloriasXCien = nutricionParen.getCalorias().multiply(new BigDecimal(100));
                            //Validar que sea mayor a cero para no generar error
                            if (nutricionParenteralSelect.getCaloriasTotal().compareTo(BigDecimal.ZERO) == 1) {
                                nutricionParen.setPorcentajeEnergia(caloriasXCien.divide(nutricionParenteralSelect.getCaloriasTotal(), 4, RoundingMode.HALF_UP));
                                porcentEnergiaTotal = porcentEnergiaTotal.add(nutricionParen.getPorcentajeEnergia());
                            }

                        }
                    }
                }
                nutricionParenteralSelect.setPorcenEnergiaTotal(porcentEnergiaTotal);

            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al actualiza la concentraciÃ³n de medicamento" + ex.getMessage());
        }
    }

    public void selectInventario(SelectEvent itemSelectEvent) {
        try {
            String idInventario = (String) itemSelectEvent.getObject();
            Inventario inventario = new Inventario();
            inventario.setIdInventario(idInventario);
            Inventario inventarioSelec = inventarioService.obtener(inventario);
            if (inventarioSelec != null) {
                posicion = 0;
                for (NutricionParenteralDetalleExtended nutricionDetalle : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                    if (nutricionDetalle.getIdMedicamento().equals(inventarioSelec.getIdInsumo())) {
                        nutricionDetalle.setOsmolaridad(new BigDecimal(inventarioSelec.getOsmolaridad()).setScale(2, RoundingMode.HALF_UP));
                        nutricionDetalle.setDensidad(new BigDecimal(inventarioSelec.getDensidad()).setScale(4, RoundingMode.HALF_UP));
                        nutricionDetalle.setIdInventario(idInventario);
                        volumenPres = nutricionDetalle.getVolPrescrito();
                        break;
                    }
                    posicion++;
                }
                CellEditEvent event = null;
                if (volumenPres.compareTo(BigDecimal.ZERO) == 1) {
                    onCellEdit(event);
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al seleccionar el inventario del combo " + ex.getMessage());
        }
    }

    /**
     * Autocomplete de medicos
     *
     * @param cadena
     * @return
     */
    public List<Usuario> autoCompleteMedicos(String cadena) {
        List<Usuario> listaMedicos = new ArrayList<>();
        try {
            listaMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(cadena, TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los Médicos: {}", ex.getMessage());
        }
        return listaMedicos;
    }

    /**
     * Autocomplete de pacientes
     *
     * @param cadena
     * @return
     */
    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        List<Paciente_Extended> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompletePacientes : {}", ex.getMessage());
        }
        return listaPacientes;
    }

    /**
     * Al seleccionar un paciente de la lista se buscan datos del paciente
     *
     * @param e
     */
    public void selectPaciente(SelectEvent e) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.selectPaciente()");
        nutricionParenteralSelect.setDivalentes(null);
        try {
            Paciente p = (Paciente_Extended) e.getObject();
            if (p == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente seleccionado inválido. ", null);

            } else {
                pacienteSelect = pacienteService.obtenerPacienteCompletoPorId(p.getIdPaciente());
                if (pacienteSelect == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Paciente seleccionado inválido. ", null);

                } else {
                    nutricionParenteralSelect.setIdPaciente(pacienteSelect.getIdPaciente());
                    nutricionParenteralSelect.setClaveDerechoHabiencia(pacienteSelect.getClaveDerechohabiencia());
                    nutricionParenteralSelect.setPacienteNumero(pacienteSelect.getPacienteNumero());
                    nutricionParenteralSelect.setFechaNacimiento(pacienteSelect.getFechaNacimiento());
                    nutricionParenteralSelect.setEdad(pacienteSelect.getEdadPaciente());

//                    Visita visita = new Visita();
//                    visita.setIdPaciente(pacienteSelect.getIdPaciente());
//                    visitaAbierta = visitaService.obtenerVisitaAbierta(visita);
//                    if (visitaAbierta != null) {
//                        nutricionParenteralSelect.setNumeroVisita(visitaAbierta.getNumVisita());
//                        nutricionParenteralSelect.setFechaIngreso(visitaAbierta.getFechaIngreso());
//                        PacienteServicio pacienteServicio = new PacienteServicio();
//                        pacienteServicio.setIdVisita(visitaAbierta.getIdVisita());
//                        pacienteServ = pacienteServicioService.obtenerPacienteServicioAbierto(pacienteServicio);
//                        if (pacienteServ != null) {
//                            if (idEstructura.equals(pacienteServ.getIdEstructura())) {
//                                Estructura servicio = estructuraService.obtenerEstructura(pacienteServ.getIdEstructura());
//                                if (servicio != null) {
//                                    nutricionParenteralSelect.setNombreEstructura(servicio.getNombre());
//                                    idEstructura = servicio.getIdEstructura();
//                                }
//                                CamaExtended cama = camaService.obtenerCamaNombreEstructura(pacienteSelect.getIdPaciente());
//                                if (cama != null) {
//                                    nutricionParenteralSelect.setUbicacion(cama.getNombreCama());
//                                }
//                            }
//                        }
//                    }
//                    buscarDiagnosticoYReaccionesPaciente();
                    nutricionParenteralSelect.setListaDiagnosticos(this.solUtils.obtenerDiagnosticos(pacienteSelect.getIdPaciente()));
                    List<MedicamentoDTO> medicamentos = null;
                    List<HipersensibilidadExtended> hiperlista = this.solUtils.obtenerReaccHiper(pacienteSelect.getIdPaciente(), medicamentos);
                    nutricionParenteralSelect.setListaReaccionesHipersensibilidad(hiperlista);
                    hipersensibilidades = this.solUtils.reaccHiperEnTexto(hiperlista);
                    List<String> insumos = null;
                    List<ReaccionExtend> reaccLista = this.solUtils.obteneReacciones(pacienteSelect.getIdPaciente(), insumos);
                    nutricionParenteralSelect.setListaReacciones(reaccLista);
                    reacciones = this.solUtils.reaccionesEnTexto(reaccLista);
                    
// TODO: cosultar información de base de datos
                    if (pacienteSelect.getEdadPaciente() != null) {
                        if (pacienteSelect.getEdadPaciente() >= 0 && pacienteSelect.getEdadPaciente() < 1) {
                            nutricionParenteralSelect.setSobrellenado(20);
                        } else if (pacienteSelect.getEdadPaciente() >= 1 && pacienteSelect.getEdadPaciente() < 18) {
                            nutricionParenteralSelect.setSobrellenado(20);
                        } else if (pacienteSelect.getEdadPaciente() >= 18 ) {
                            nutricionParenteralSelect.setSobrellenado(30);    
                       }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el paciente :: {} ", ex.getMessage());
        }
    }

    public void selectMedico(SelectEvent e) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.selectPaciente()");
        status = Constantes.INACTIVO;
        try {
            medicoSelect = usuarioService.obtenerUsuarioByIdUsuario(((Usuario) e.getObject()).getIdUsuario());
            if (medicoSelect != null) {
                nutricionParenteralSelect.setIdMedico(medicoSelect.getIdUsuario());
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el usuario medico ", ex.getMessage());
        }
    }

    /**
     * Para cambiar al paciente de servicio debido a que se encuentra en otro
     * para generar la orden de preparaciÃ³n
     */
    public void cambiarServicioPaciente() {
        status = Constantes.INACTIVO;
        try {
            pacienteServ.setFechaAsignacionFin(new Date());
            pacienteServ.setIdUsuarioAsignacionFin(usuarioSelected.getIdUsuario());
            pacienteServ.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue());
            pacienteServ.setUpdateFecha(new Date());
            pacienteServ.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

            PacienteServicio nuevoPaceinteServicio = new PacienteServicio();
            nuevoPaceinteServicio.setIdPacienteServicio(Comunes.getUUID());
            nuevoPaceinteServicio.setIdEstructura(idEstructura);
            nuevoPaceinteServicio.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            nuevoPaceinteServicio.setInsertFecha(new Date());
            boolean resp = Constantes.INACTIVO;
            if (medicoSelect != null) {
                nuevoPaceinteServicio.setIdMedico(medicoSelect.getIdUsuario());
                nuevoPaceinteServicio.setIdVisita(visitaAbierta.getIdVisita());
                nuevoPaceinteServicio.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue());
                nuevoPaceinteServicio.setFechaAsignacionInicio(new Date());
                nuevoPaceinteServicio.setIdUsuarioAsignacionInicio(usuarioSelected.getIdUsuario());

                resp = nutricionParenteralService.cerrarServicioYAsignarNuevoServicio(pacienteServ, nuevoPaceinteServicio);
            }
            if (resp) {
                status = Constantes.ACTIVO;
                pacienteServ = nuevoPaceinteServicio;
                buscarDiagnosticoYReaccionesPaciente();
            } else {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ord.nutr.parenteral.err.medico"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al cambiar de servicio a paciente   ", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Limpiar algunos campos
     */
    public void limpiarDatosPaciente() {
        nutricionParenteralSelect.setClaveDerechoHabiencia("");
        nutricionParenteralSelect.setPacienteNumero("");
        pacienteSelect = null;
        nutricionParenteralSelect.setIdPaciente("");
        nutricionParenteralSelect.setNumeroVisita("");
        nutricionParenteralSelect.setFechaIngreso(null);
    }

    @Deprecated
    public void buscarDiagnosticoYReaccionesPaciente() {
        try {

            /* List<Diagnostico> listaDiagnosticos = obtenerListaDiagnosticos(pacienteSelect.getIdPaciente());
            if (!listaDiagnosticos.isEmpty()) {
                nutricionParenteralSelect.setListaDiagnosticos(listaDiagnosticos);
                /*for (Diagnostico diagnostico : listaDiagnosticos) {
                    if (diagnosticos.isEmpty()) {
                        diagnosticos = diagnostico.getClave() + ": " + diagnostico.getNombre();
                    } else {
                        diagnosticos = diagnosticos + ", " + diagnostico.getClave() + ": " + diagnostico.getNombre();
                    }
                }
            }*/
            List<HipersensibilidadExtended> listaHipersensibilidad = obtenerListaHipersensibilidad(pacienteSelect.getIdPaciente());
            if (!listaHipersensibilidad.isEmpty()) {
                nutricionParenteralSelect.setListaReaccionesHipersensibilidad(listaHipersensibilidad);
                for (HipersensibilidadExtended hipersensibilidad : listaHipersensibilidad) {
                    if (hipersensibilidades.isEmpty()) {
                        hipersensibilidades = hipersensibilidad.getNombreComercial() + ": " + hipersensibilidad.getTipoAlergia();
                    } else {
                        hipersensibilidades = hipersensibilidades + hipersensibilidad.getNombreComercial() + ": " + hipersensibilidad.getTipoAlergia();
                    }
                }
            }
            List<ReaccionExtend> listaReaccionesRAM = obtenerListaRAM(pacienteSelect.getIdPaciente());
            if (!listaReaccionesRAM.isEmpty()) {
                nutricionParenteralSelect.setListaReacciones(listaReaccionesRAM);
                for (ReaccionExtend reaccion : listaReaccionesRAM) {
                    if (reacciones.isEmpty()) {
                        reacciones = reaccion.getMedicamento() + ": " + reaccion.getTipo();
                    } else {
                        reacciones = reacciones + reaccion.getMedicamento() + ": " + reaccion.getTipo();
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al momento de revisar el servicio del paciente  ", ex.getMessage());
        }
    }

    public void mostrarModalAgregarPaciente() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.mostrarModalAgregarPaciente()");
        boolean status = false;
        try {
            if (this.usuarioSelected == null) {
                Mensaje.showMessage("Error", "Usuario inválido.", "");

            } else if (!this.permiso.isPuedeCrear()) {
                Mensaje.showMessage("Error", "No tiene permisos de esta acción.", "");

            } else if (nutricionParenteralSelect.getIdEstructura() == null
                    || nutricionParenteralSelect.getIdEstructura().trim().isEmpty()) {
                Mensaje.showMessage("Error", RESOURCES.getString("prc.manual.servicio.req"), "");

            } else if (nutricionParenteralSelect.getIdCama() == null
                    || nutricionParenteralSelect.getIdCama().trim().isEmpty()) {
                Mensaje.showMessage("Error", RESOURCES.getString("prc.manual.cama.req"), "");

            } else {
                //limpiarDatosPaciente();
                pacienteNuevo = new Paciente();
                this.listaTurnos = this.turnoService.obtenerLista(new Turno());
                Turno item = this.listaTurnos.get(0);
                this.listaIdTurnos = new ArrayList<>();
                this.listaIdTurnos.add("" + item.getIdTurno());
                status = true;
            }
        } catch (Exception exp) {
            LOGGER.error("Error al mostrar vista de agregar paciente :: {} ", exp.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }

    public void agregarPaciente() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.agregarPaciente()");
        String msjError = validarDatosPaciente();
        boolean resp = false;

        if (msjError != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
            return;
        }

        Paciente pacieteTemp = solUtils.obtenerDatosPaciente(pacienteNuevo, usuarioSelected);
        pacieteTemp.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
        PacienteDomicilio pacienteDomTem = this.solUtils.obtenerDatosPacienteDomicilioGenerico(pacieteTemp, usuarioSelected);
        PacienteResponsable pacienteRespTemp = new PacienteResponsable();
        List<TurnoMedico> listaTurnoTemp = obtenerTurnoGenericoPaciente(pacieteTemp);

        try {
            Visita visitaGenericaRecMan = new Visita();
            visitaGenericaRecMan.setIdVisita(Comunes.getUUID());
            visitaGenericaRecMan.setIdPaciente(pacieteTemp.getIdPaciente());
            visitaGenericaRecMan.setFechaIngreso(new Date());
            visitaGenericaRecMan.setIdUsuarioIngresa(this.usuarioSelected.getIdUsuario());
            visitaGenericaRecMan.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            visitaGenericaRecMan.setMotivoConsulta("");
            if (nutricionParenteralSelect.getNumeroVisita() != null) {
                visitaGenericaRecMan.setNumVisita(nutricionParenteralSelect.getNumeroVisita());
            }
            visitaGenericaRecMan.setInsertFecha(new Date());
            visitaGenericaRecMan.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

            PacienteServicio pacienteServicioGenerico = new PacienteServicio();
            pacienteServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
            pacienteServicioGenerico.setIdVisita(visitaGenericaRecMan.getIdVisita());
            pacienteServicioGenerico.setIdEstructura(nutricionParenteralSelect.getIdEstructura());
            pacienteServicioGenerico.setFechaAsignacionInicio(new Date());
            pacienteServicioGenerico.setIdUsuarioAsignacionInicio(this.usuarioSelected.getIdUsuario());
            pacienteServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            pacienteServicioGenerico.setInsertFecha(new Date());
            pacienteServicioGenerico.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

//            resp = visitaService.insertarVisitaYServicioGenericos(visitaGenericaRecMan, pacienteServicioGenerico);
//            if (resp) {
//                pacienteServ = pacienteServicioGenerico;
//                pacieteTemp.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
//                pacienteService.actualizar(pacieteTemp);
//            }
            resp = pacienteService.insertarPacienteVisitaServicio(
                    pacieteTemp, pacienteDomTem, pacienteRespTemp, listaTurnoTemp,
                    visitaGenericaRecMan, pacienteServicioGenerico);
            //        resp = generarVisitaYServicioGenerico(pacieteTemp);

            if (!resp) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.registro"), "");

            } else {
                Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.paciente"), "");

            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.registro"), "");
        }

        PrimeFaces.current().ajax().addCallbackParam(paramError, !resp);
    }

    private String validarDatosPaciente() {
        if (this.nutricionParenteralSelect.getIdEstructura() == null
                || this.nutricionParenteralSelect.getIdEstructura().isEmpty()) {
            return RESOURCES.getString("prc.manual.servicio.req");

        } else if (this.nutricionParenteralSelect.getIdCama() == null
                || this.nutricionParenteralSelect.getIdCama().isEmpty()) {
            return RESOURCES.getString("prc.manual.cama.req");

        } else if (this.pacienteNuevo == null) {
            return RESOURCES.getString("prc.manual.numeroPaciente.paciente");

        } else if (this.pacienteNuevo.getClaveDerechohabiencia() == null
                || this.pacienteNuevo.getClaveDerechohabiencia().isEmpty()) {
            return RESOURCES.getString("prc.manual.numeroPaciente.paciente");

        } else if (this.pacienteNuevo.getNombreCompleto() == null
                || this.pacienteNuevo.getNombreCompleto().isEmpty()) {
            return RESOURCES.getString("prc.manual.nombre.paciente");

        } else if (this.pacienteNuevo.getApellidoPaterno() == null
                || this.pacienteNuevo.getApellidoPaterno().isEmpty()) {
            return RESOURCES.getString("prc.manual.app.paciente");

        } else if (this.pacienteNuevo.getSexo() == '\0' || this.pacienteNuevo.getSexo() == '\u0000') {
            return RESOURCES.getString("prc.manual.sex.paciente");

        } else if (this.pacienteNuevo.getFechaNacimiento() == null) {
            return RESOURCES.getString("prc.manual.fechaNac.paciente");
//            
//        } else if (this.listaIdTurnos.isEmpty()) {
//            return RESOURCES.getString("prc.manual.turno.paciente");

        } else {
            return validarClavePaciente();
        }
    }

    private String validarClavePaciente() {
        String resp = null;
        Paciente pacienteTem = new Paciente();
        pacienteTem.setPacienteNumero(this.pacienteNuevo.getClaveDerechohabiencia());
        Paciente pacienteResp = null;
        try {
            pacienteResp = pacienteService.obtener(pacienteTem);
            if (pacienteResp != null) {
                resp = RESOURCES.getString("prc.manual.clave.paciente");
            }
        } catch (Exception ex) {
            LOGGER.error("error en el metodo validarClavePaciente :: {}", ex.getMessage());
        }
        return resp;
    }
    
    private List<TurnoMedico> obtenerTurnoGenericoPaciente(Paciente patient) {
        List<TurnoMedico> listaTemp = new ArrayList<>();
        if (this.listaIdTurnos != null && !this.listaIdTurnos.isEmpty()) {
            for (String item : this.listaIdTurnos) {
                TurnoMedico turnoMedico = new TurnoMedico();
                turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                turnoMedico.setIdTurno(Integer.valueOf(item));
                turnoMedico.setIdMedico(patient.getIdPaciente());
                listaTemp.add(turnoMedico);
            }
        }
        return listaTemp;
    }

    private boolean generarVisitaYServicioGenerico(Paciente paciente) {
        boolean resp = false;
        try {
            Visita visitaGenericaRecMan = new Visita();
            visitaGenericaRecMan.setIdVisita(Comunes.getUUID());
            visitaGenericaRecMan.setIdPaciente(paciente.getIdPaciente());
            visitaGenericaRecMan.setFechaIngreso(new Date());
            visitaGenericaRecMan.setIdUsuarioIngresa(this.usuarioSelected.getIdUsuario());
            visitaGenericaRecMan.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            visitaGenericaRecMan.setMotivoConsulta("");
            if (nutricionParenteralSelect.getNumeroVisita() != null) {
                visitaGenericaRecMan.setNumVisita(nutricionParenteralSelect.getNumeroVisita());
            }
            visitaGenericaRecMan.setInsertFecha(new Date());
            visitaGenericaRecMan.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

            PacienteServicio pacienteServicioGenerico = new PacienteServicio();
            pacienteServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
            pacienteServicioGenerico.setIdVisita(visitaGenericaRecMan.getIdVisita());
            pacienteServicioGenerico.setIdEstructura(nutricionParenteralSelect.getIdEstructura());
            pacienteServicioGenerico.setFechaAsignacionInicio(new Date());
            pacienteServicioGenerico.setIdUsuarioAsignacionInicio(this.usuarioSelected.getIdUsuario());
            pacienteServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            pacienteServicioGenerico.setInsertFecha(new Date());
            pacienteServicioGenerico.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());

            resp = visitaService.insertarVisitaYServicioGenericos(visitaGenericaRecMan, pacienteServicioGenerico);
            if (resp) {
                pacienteServ = pacienteServicioGenerico;
                paciente.setIdEstatusPaciente(EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
                pacienteService.actualizar(paciente);
            }

        } catch (Exception ex) {
            resp = false;
            LOGGER.error(ex.getMessage());
        }
        return resp;
    }

    public void mostrarModalAgregarMedico() {
        LOGGER.trace("mostrarModalAgregarMedico");
        try {
            this.listaIdTurnos = new ArrayList<>();
            this.tipoUserList = tipoUsuarioService.obtenerLista(new TipoUsuario());
            this.listaTurnos = this.turnoService.obtenerLista(new Turno());
            for (Turno item : this.listaTurnos) {
                this.listaIdTurnos.add("" + item.getIdTurno());
            }

        } catch (Exception excp) {
            LOGGER.error("Error al obtener Insumos: {}", excp.getMessage());
        }
    }

    public void agregarMedicos() {
        boolean status = false;
        String resp = validarDatosMedico();
        if (resp != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, resp, "");
            PrimeFaces.current().ajax().addCallbackParam("errorModalMedicos", true);
            return;
        }
        if (this.medicoExtended != null) {
            Usuario medicoTemp = new Usuario();
            medicoTemp.setIdUsuario(Comunes.getUUID());
            medicoTemp.setNombreUsuario(this.medicoExtended.getNombreUsuario());
            medicoTemp.setClaveAcceso(CustomWebSecurityConfigurerAdapter.argon2Encode(Constantes.CLAVE_GENERICA));
            medicoTemp.setCorreoElectronico(this.medicoExtended.getCorreoElectronico());
            medicoTemp.setNombre(this.medicoExtended.getNombre());
            medicoTemp.setApellidoPaterno(this.medicoExtended.getApellidoPaterno());
            medicoTemp.setApellidoMaterno(this.medicoExtended.getApellidoMaterno());
            medicoTemp.setActivo(Constantes.ACTIVO);
            medicoTemp.setUsuarioBloqueado(Constantes.INACTIVO);
            medicoTemp.setFechaVigencia(new Date());
            medicoTemp.setIdEstructura(Constantes.ESTRUCTURA_MEDICO_CONSEXT);
            medicoTemp.setIdTipoUsuario(TipoUsuario_Enum.MEDICO.getValue());
            medicoTemp.setCedProfesional(this.medicoExtended.getCedProfesional());
            medicoTemp.setCedEspecialidad(this.medicoExtended.getCedEspecialidad());
            medicoTemp.setInsertFecha(new Date());
            medicoTemp.setInsertIdUsuario(this.usuarioSelected.getIdUsuario());
            List<TurnoMedico> listaTemp = new ArrayList<>();
            if (this.listaIdTurnos != null && !this.listaIdTurnos.isEmpty()) {
                for (String item : this.listaIdTurnos) {
                    TurnoMedico turnoMedico = new TurnoMedico();
                    turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                    turnoMedico.setIdTurno(Integer.valueOf(item));
                    turnoMedico.setIdMedico(medicoTemp.getIdUsuario());
                    listaTemp.add(turnoMedico);
                }
            }
            try {
                status = usuarioService.insertarMedicoYTurno(medicoTemp, listaTemp);
            } catch (Exception ex) {
                status = false;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.registro"), "");
                LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
            }
        }

        if (status) {
            Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.medico"), "");
            PrimeFaces.current().ajax().addCallbackParam("errorModalMedicos", false);
        }

    }

    private String validarDatosMedico() {
        String resul = null;
        if (this.medicoExtended != null) {
            if (this.medicoExtended.getNombre().isEmpty()) {
                resul = RESOURCES.getString("prc.manual.nombre.medico");
                return resul;
            } else if (this.medicoExtended.getApellidoPaterno().isEmpty()) {
                resul = RESOURCES.getString("prc.manual.app.medico");
                return resul;
            } else if (this.medicoExtended.getApellidoMaterno().isEmpty()) {
                resul = RESOURCES.getString("prc.manual.apm.medico");
                return resul;
            } else if (this.medicoExtended.getNombreUsuario().isEmpty()) {
                resul = RESOURCES.getString("prc.manual.nomUsuario.med");
                return resul;
            } else if (this.medicoExtended.getCorreoElectronico().isEmpty()) {
                resul = RESOURCES.getString("prc.manual.correo.medico");
                return resul;
            } else if (this.medicoExtended.getCedProfesional().isEmpty()) {
                resul = RESOURCES.getString("prc.manual.cedula");
                return resul;
            }
        }
        return resul;
    }

    public void validaOrdenPreparacion() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.validaOrdenPreparacion()");
        boolean res = false;
        if (usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario Inválido", null);

        } else if (!permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción.", null);

        } else {
            validaFormulario = solUtils.validaDatosOrdenPreparacion(nutricionParenteralSelect, medicoSelect, idTipoSolucion, viaAdministracion);
            if (!validaFormulario.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, validaFormulario, null);

            } else {
                if (nutricionParenteralSelect.getFechaProgramada().before(fechaMinPrescrMezclaValida)){
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La fecha hora programada de la prescripción debe ser mayor a " + FechaUtil.formatoCadena(fechaMinPrescrMezclaValida, "yyyy/MM/dd HH:mm") , null);
                } else {
//                  
                    if (new BigDecimal(numMaxCompatibilidad).compareTo(nutricionParenteralSelect.getPrecipitacion()) == -1) {
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, "Valor Compatibilidad = "
                                + nutricionParenteralSelect.getPrecipitacion()
                                + " > " + numMaxCompatibilidad
                                + " . " + RESOURCES2.getString("mez.npt.validacionCompat"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Valor Compatibilidad = "
                                + nutricionParenteralSelect.getPrecipitacion()
                                + " <= " + numMaxCompatibilidad
                                + " . Mezcla " + RESOURCES2.getString("mez.npt.compatible"), null);
                    }
                    if (new BigDecimal(numMaxEstabilidad).compareTo(nutricionParenteralSelect.getEstabilidad()) < 1) {
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, "Valor Estabilidad = "
                                + nutricionParenteralSelect.getEstabilidad()
                                + " > " + numMaxEstabilidad
                                + " . " + RESOURCES2.getString("mez.npt.validacionEstab"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Valor Estabilidad = "
                                + nutricionParenteralSelect.getEstabilidad()
                                + " <= " + numMaxEstabilidad
                                + " . Mezcla " + RESOURCES2.getString("mez.npt.estable"), null);
                    }
                    if (nutricionParenteralSelect.getTotalOsmolaridad().compareTo(BigDecimal.ZERO) == 1) {
                        if (new BigDecimal(numMaxOsmolaridad).compareTo(nutricionParenteralSelect.getTotalOsmolaridad()) < 1) {
                            Mensaje.showMessage(Constantes.MENSAJE_WARN, "Osmolaridad Total = "
                                    + nutricionParenteralSelect.getTotalOsmolaridad()
                                    + " > " + numMaxOsmolaridad
                                    + " . Vía de Administración: " + RESOURCES2.getString("mez.npt.osmolaridadViaCentral"), null);
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_INFO, "Osmolaridad Total = "
                                    + nutricionParenteralSelect.getTotalOsmolaridad()
                                    + " <= " + numMaxOsmolaridad
                                    + " . Vía de Administración: " + RESOURCES2.getString("mez.npt.osmolaridadviaPeriferica"), null);
                        }
                    }
                    Integer count = 0;
                    for (NutricionParenteralDetalleExtended item : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                        if (item.getVolPrescrito().compareTo(BigDecimal.ZERO) == 1) {
                            count++;
                        }
                    }
                    if (count < 3) {
                        Mensaje.showMessage(Constantes.MENSAJE_WARN, "Mezcla con " + nutricionParenteralSelect.getListaMezclaMedicamentos().size() + " insumo(s), es correcta? ", null);
                    }
                    res = true;
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, res);
    }

    /**
     * Registrar la prescripción de NPT
     *
     * @param idEstatusNutricion
     */
    public void guardarOrdenPreparacion(Integer idEstatusNutricion) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.guardarOrdenPreparacion()");
        boolean estatus = Constantes.INACTIVO;

        try {
            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido ", null);

            } else if (!permiso.isPuedeEditar() && !permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción ", null);

            } else if (nutricionParenteralSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Precripción inválida.", null);

            } else if (nutricionParenteralSelect.getListaMezclaMedicamentos() == null
                    || nutricionParenteralSelect.getListaMezclaMedicamentos().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No ha seleccionado insumos por prescribir.", null);

            } else {
                validaFormulario = solUtils.validaDatosOrdenPreparacion(nutricionParenteralSelect, medicoSelect, idTipoSolucion, viaAdministracion);
                if (!validaFormulario.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, validaFormulario, null);

                } else if (pacienteSelect == null){
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un paciente.", null);
                } else if (pacienteSelect.getIdPaciente() == null){
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un paciente.", null);
                } else if (medicoSelect == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Médico inválido.", null);
                } else if (medicoSelect.getIdUsuario() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Médico inválido.", null);
                } else {

                    nutricionParenteralSelect.setIdPaciente(pacienteSelect.getIdPaciente());
                    nutricionParenteralSelect.setIdPaciente(pacienteSelect.getIdPaciente());
                    nutricionParenteralSelect.setIdMedico(medicoSelect.getIdUsuario());
                    nutricionParenteralSelect.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
                    nutricionParenteralSelect.setIdTipoSolucion(idTipoSolucion);
                    nutricionParenteralSelect.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                    nutricionParenteralSelect.setIdRegistro(usuarioSelected.getIdUsuario());
                    nutricionParenteralSelect.setInsertFecha(new Date());
                    nutricionParenteralSelect.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                    String diagosticosPaciente = solUtils.concatenaDiagnosticos(nutricionParenteralSelect);
                    nutricionParenteralSelect.setDiagnosticos(diagosticosPaciente);
                    idNutricionParente = nutricionParenteralSelect.getIdNutricionParenteral();

                    List<NutricionParenteralDetalleExtended> listaNutricionCopia = new ArrayList<>();
                    List<NutricionParenteralDetalleExtended> listaNutricionActua = new ArrayList<>();
                    List<NutricionParenteralDetalleExtended> listaNutricionBorrar = new ArrayList<>();

                    for (NutricionParenteralDetalleExtended nutricionParenteralDetalle : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
//                        if (nutricionParenteralDetalle.getVolPrescrito() != null) {
//                            if (nutricionParenteralDetalle.getVolPrescrito().compareTo(BigDecimal.ZERO) == 1) {
                                if (editar) {
                                    nutricionParenteralDetalle.setUpdateFecha(new Date());
                                    nutricionParenteralDetalle.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                                    if (nutricionParenteralDetalle.getIdNutricionParenteralDetalle() == null) {
                                        nutricionParenteralDetalle.setIdNutricionParenteralDetalle(Comunes.getUUID());
                                        nutricionParenteralDetalle.setIdNutricionParenteral(nutricionParenteralSelect.getIdNutricionParenteral());
                                        nutricionParenteralDetalle.setInsertFecha(new Date());
                                        nutricionParenteralDetalle.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                        listaNutricionCopia.add(nutricionParenteralDetalle);
                                    } else {
                                        listaNutricionActua.add(nutricionParenteralDetalle);
                                    }
                                } else {
                                    nutricionParenteralDetalle.setIdNutricionParenteralDetalle(Comunes.getUUID());
                                    nutricionParenteralDetalle.setIdNutricionParenteral(nutricionParenteralSelect.getIdNutricionParenteral());
                                    nutricionParenteralDetalle.setInsertFecha(new Date());
                                    nutricionParenteralDetalle.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    listaNutricionCopia.add(nutricionParenteralDetalle);
                                }
//                            }
//                        }
                    }
                    listaNutricionActua.addAll(listaNutricionCopia);
                    nutricionParenteralSelect.setListaMezclaMedicamentos(listaNutricionActua);

                    Visita v = solUtils.recuperaVisita(nutricionParenteralSelect.getIdPaciente(), usuarioSelected.getIdUsuario());
                    PacienteServicio pacSer = solUtils.recuperaPacienteServicio(v.getIdVisita(), nutricionParenteralSelect.getIdEstructura(), usuarioSelected.getIdUsuario(), medicoSelect.getIdUsuario(), nutricionParenteralSelect.getIdPaciente());
                    PacienteUbicacion pacUbi = solUtils.recuperaPacienteUbicacion(pacSer.getIdPacienteServicio(), usuarioSelected.getIdUsuario(), nutricionParenteralSelect.getIdCama(), nutricionParenteralSelect.getIdPaciente());

                    prescripcionPaciente = null;
                    if (nutricionParenteralSelect.getFolio() != null) {
                        prescripcionPaciente = prescripcionService.obtenerByFolioPrescripcion(nutricionParenteralSelect.getFolio());
                    }

                    Prescripcion prescripcion;
                    Surtimiento surtimiento;
                    String idPrescripcion;
                    String idSurtimiento;

                    if (!editar) {
                        prescripcion = solUtils.creaPrescripcion(pacSer.getIdPacienteServicio(), pacUbi.getIdPacienteUbicacion(), nutricionParenteralSelect, usuarioSelected);
                        idPrescripcion = prescripcion.getIdPrescripcion();

                        surtimiento = solUtils.creaSurtimiento(idPrescripcion, nutricionParenteralSelect, usuarioSelected);
                        idSurtimiento = surtimiento.getIdSurtimiento();

                    } else {
                        idPrescripcion = prescripcionPaciente.getIdPrescripcion();
                        prescripcion = prescripcionPaciente;
                        prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                        prescripcion.setUpdateFecha(new Date());
                        prescripcion.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

                        surtimiento = surtimientoService.obtenerByIdPrescripcion(idPrescripcion);
                        idSurtimiento = surtimiento.getIdSurtimiento();
                    }

                    PrescripcionInsumo prescripcionInsumo;
                    String idPrescripcionInsumo;
                    List<PrescripcionInsumo> listaPrescripcionInsumos = new ArrayList<>();
                    List<SurtimientoInsumo> listaSurtimientoInsumos = new ArrayList<>();
                    SurtimientoInsumo surtimientoInsumo;
                    List<SurtimientoEnviado> listaSurtimientoEnviado = new ArrayList<>();
                    Integer cuenta = 1;
                    Integer horas;
                    BigDecimal mls;
                    BigDecimal vel = BigDecimal.ZERO;
                    for (NutricionParenteralDetalle nutricionDetalleParental : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                        if (cuenta == 1) {
                            horas = nutricionParenteralSelect.getHorasInfusion();
                            mls = nutricionParenteralSelect.getVolumenTotal();
                            try {
                                if (horas > 0 && mls.compareTo(BigDecimal.ZERO) == 1) {
                                    BigDecimal val1 = new BigDecimal(horas);
                                    vel = mls.divide(val1, 2, RoundingMode.HALF_EVEN);
                                    nutricionParenteralSelect.setVelocidad(vel);
                                }
                            } catch (Exception ex1) {
                                LOGGER.error("Error al obtener la velocidad :: {} ", ex1.getMessage());
                            }

                        }
                        prescripcionInsumo = solUtils.creaPrescripcionInsumo(idPrescripcion, nutricionDetalleParental, nutricionParenteralSelect, usuarioSelected);
                        listaPrescripcionInsumos.add(prescripcionInsumo);

                        idPrescripcionInsumo = prescripcionInsumo.getIdPrescripcionInsumo();
                        surtimientoInsumo = solUtils.creaSurtimientoInsumo(idSurtimiento, idPrescripcionInsumo, nutricionDetalleParental, nutricionParenteralSelect, usuarioSelected);
                        surtimientoInsumo.setNumeroMedicacion(cuenta);
                        cuenta++;
                        listaSurtimientoInsumos.add(surtimientoInsumo);
                    }

//TODO Revisar como obtener estos valores y a quien seran asignados
//                    if (!listaPrescripcionInsumos.isEmpty()) {
//                        listaPrescripcionInsumos.get(0).setIdTipoIngrediente(1);
//                        listaPrescripcionInsumos.get(0).setVelocidad(2);
//                        listaPrescripcionInsumos.get(0).setRitmo(0.033);
//                        listaPrescripcionInsumos.get(0).setPerfusionContinua(1);
//                        listaPrescripcionInsumos.get(0).setIdUnidadConcentracion(2);
//                    }
                    List<DiagnosticoPaciente> listDiagnosticosPaciente = new ArrayList<>();
                    DiagnosticoPaciente diagnosticoPaciente;
                    if (!nutricionParenteralSelect.getListaDiagnosticos().isEmpty()) {
                        for (Diagnostico diagnostico : nutricionParenteralSelect.getListaDiagnosticos()) {
                            diagnosticoPaciente = solUtils.creaDiagnosticoPaciente(idPrescripcion, diagnostico, usuarioSelected);
                            listDiagnosticosPaciente.add(diagnosticoPaciente);
                        }
                    }

                    boolean result;
                    if (editar) {
                        LOGGER.trace("Editar solUtils.creaSolucion");
                        idSurtimiento = nutricionParenteralSelect.getIdSurtimiento();
                        String idSolucion = null;
                        solucion = solUtils.obtenerSolucion(idSolucion, idSurtimiento);
                        solucion.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue());
                        result = nutricionParenteralService.actualizarPrecripcionSurtimientoSolucionNPT(
                                prescripcion,
                                listaPrescripcionInsumos,
                                surtimiento,
                                listaSurtimientoInsumos,
                                listaSurtimientoEnviado,
                                solucion,
                                nutricionParenteralSelect,
                                listDiagnosticosPaciente);
                    } else {
                        LOGGER.trace("Crear solUtils.creaSolucion");
                        Solucion sol = solUtils.creaSolucion(idSurtimiento, nutricionParenteralSelect, usuarioSelected);
                        sol.setIdEnvaseContenedor(nutricionParenteralSelect.getIdContenedor());
                        sol.setVolumen(nutricionParenteralSelect.getVolumenTotal());
                        sol.setVolumenFinal(nutricionParenteralSelect.getVolumenTotal().toString());
                        sol.setProteccionLuz(nutricionParenteralSelect.isProteccionLuz() ? 1 : 0 );
                        sol.setProteccionTemp(nutricionParenteralSelect.isTempAmbiente() ? 1 : 0 );
                        sol.setProteccionTempRefrig(nutricionParenteralSelect.isTempRefrigeracion() ? 1 : 0);
                        sol.setNoAgitar(nutricionParenteralSelect.isNoAgitar() ? 1 : 0);
                        sol.setEdadPaciente(nutricionParenteralSelect.getEdad().doubleValue());
                        sol.setPesoPaciente(nutricionParenteralSelect.getPeso().doubleValue());
                        sol.setTallaPaciente(nutricionParenteralSelect.getTallaPaciente().doubleValue());
                        sol.setAreaCorporal(nutricionParenteralSelect.getAreaCorporal().doubleValue());
                        sol.setPerfusionContinua(1);                        
                        sol.setVelocidad(nutricionParenteralSelect.getVelocidad().doubleValue());
                        sol.setIdViaAdministracion(nutricionParenteralSelect.getIdViaAdministracion());
                        sol.setMinutosInfusion(nutricionParenteralSelect.getHorasInfusion()*60);
                        sol.setSobrellenado(nutricionParenteralSelect.getSobrellenado());
                                                
                        result = nutricionParenteralService.registrarPrecripcionSurtimientoSolucionNPT(
                                prescripcion,
                                listaPrescripcionInsumos,
                                surtimiento,
                                listaSurtimientoInsumos,
                                listaSurtimientoEnviado,
                                sol,
                                nutricionParenteralSelect,
                                listDiagnosticosPaciente);
                    }
                    if (result) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, "Registro guardado exitosamente.", null);
                        estatus = Constantes.ACTIVO;
                        buscarOrdenesPreparacion();

                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ord.nutr.parenteral.err.guardar"), null);

                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en metodo guardarOrdenPreparacion en clase PrescribirMezclaNPTMB :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ord.nutr.parenteral.excep.guardar"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void validarOrdenPreparacion() {
        try {
            guardarOrdenPreparacion(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue());
            if (validaFormulario.isEmpty()) {
                status = Constantes.INACTIVO;
                nutricionParenteralSelect = nutricionParenteralService.obtenerNutricionParenteralById(idNutricionParente);
                nutricionParenteralSelect.setListaMezclaMedicamentos(nutricionParenteralDetalleService.obtenerNutricionParenteralDetalleById(idNutricionParente));
                List<MedicamentoDTO> listaMedicametos = new ArrayList<>();
                ParamBusquedaAlertaDTO alertaDTO = new ParamBusquedaAlertaDTO();
                getListaMedicamentosDTO(listaMedicametos);
                alertaDTO.setListaMedicametos(listaMedicametos);

                //pacienteSelect = pacienteService.obtenerPacienteByIdPaciente(nutricionParenteralSelect.getIdPaciente());
                nutricionParenteralSelect.setPacienteNumero(pacienteSelect.getPacienteNumero());
                //medicoSelect = usuarioService.obtenerUsuarioPorId(nutricionParenteralSelect.getIdMedico());
                //nutricionParenteralSelect.setIdMedico();
                if (nutricionParenteralSelect.getDiagnosticos() != null) {
                    if (!nutricionParenteralSelect.getDiagnosticos().isEmpty()) {
                        String[] lisDiagnosticos = nutricionParenteralSelect.getDiagnosticos().split("; ");
                        List<Diagnostico> diagnosticosPaciente = new ArrayList<>();
                        if (lisDiagnosticos.length > 0) {
                            for (int i = 0; i < lisDiagnosticos.length; i++) {
                                if (lisDiagnosticos[i].toString() != "") {
                                    Diagnostico unDiagnostico = diagnosticosService.obtenerDiagnosticoPorNombre(lisDiagnosticos[i].toString());
                                    if (unDiagnostico != null) {
                                        diagnosticosPaciente.add(unDiagnostico);
                                    }
                                }

                            }
                            nutricionParenteralSelect.setListaDiagnosticos(diagnosticosPaciente);
                        }
                    }
                }
                String numeroVisita = "";
                if (visitaAbierta != null) {
                    numeroVisita = visitaAbierta.getNumVisita();
                    verificarUbicacion();
                } else {
                    //Generar la visita nueva del paciente                
                    if (nutricionParenteralSelect.getNumeroVisita() != null || !nutricionParenteralSelect.getNumeroVisita().isEmpty()) {
                        numeroVisita = nutricionParenteralSelect.getNumeroVisita();
                        visitaAbierta = new Visita();
                        visitaAbierta.setIdVisita(Comunes.getUUID());
                        visitaAbierta.setIdPaciente(pacienteSelect.getIdPaciente());
                        visitaAbierta.setFechaIngreso(new Date());
                        visitaAbierta.setIdUsuarioIngresa(usuarioSelected.getIdUsuario());
                        visitaAbierta.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
                        visitaAbierta.setMotivoConsulta("");
                        visitaAbierta.setInsertFecha(new Date());
                        visitaAbierta.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                        pacienteServ = new PacienteServicio();
                        pacienteServ.setIdPacienteServicio(Comunes.getUUID());
                        pacienteServ.setIdEstructura(idEstructura);
                        pacienteServ.setIdMedico(medicoSelect.getIdUsuario());
                        pacienteServ.setIdVisita(visitaAbierta.getIdVisita());
                        pacienteServ.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.TRANS_SERV.getValue());
                        pacienteServ.setFechaAsignacionInicio(new Date());
                        pacienteServ.setIdUsuarioAsignacionInicio(usuarioSelected.getIdUsuario());

                        boolean resp = nutricionParenteralService.generarNuevaVisitaYPacienteServicio(visitaAbierta, pacienteServ);
                        if (resp) {
                            verificarUbicacion();
                        }
                    }
                }

                //visitaAbierta = new Visita();
                /*if(pacienteSelect.getIdPaciente() != null) {                
                    visita.setIdPaciente(pacienteSelect.getIdPaciente());
                    visitaAsignada = visitaService.obtenerVisitaASignada(visita);

                }*/
                alertaDTO.setFolioPrescripcion(nutricionParenteralSelect.getFolio());
                alertaDTO.setNumeroPaciente(nutricionParenteralSelect.getPacienteNumero());
                alertaDTO.setNumeroVisita(numeroVisita);
                alertaDTO.setNumeroMedico(medicoSelect.getCedProfesional());
                List<String> diagnosticos = new ArrayList<>();
                if (nutricionParenteralSelect.getListaDiagnosticos().isEmpty()) {
                    for (Diagnostico diag : nutricionParenteralSelect.getListaDiagnosticos()) {
                        diagnosticos.add(diag.getClave());
                    }
                    alertaDTO.setListaDiagnosticos(diagnosticos);
                }

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
        } catch (Exception ex) {
            LOGGER.error("Error en metodo validarOrdenPreparacion en clase PrescribirMezclaNPTMB");
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ord.nutr.parenteral.excep.validar"), null);
        }

        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    private void verificarUbicacion() throws Exception {
        if (nutricionParenteralSelect.getIdCama() == null
                || nutricionParenteralSelect.getIdCama().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Cama inválida", null);

        } else {
            String idCama = nutricionParenteralSelect.getIdCama();
            Cama cama = camaService.obtenerCama(idCama);
            if (cama == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Cama inválida", null);
            } else {
                PacienteUbicacion ubicacion = new PacienteUbicacion();
                ubicacion.setIdCama(idCama);
                PacienteUbicacion ubicacionPac = pacienteUbicacionService.obtener(ubicacion);
                if (ubicacionPac == null) {
                    ubicacion.setIdPacienteUbicacion(Comunes.getUUID());
                    ubicacion.setIdPacienteServicio(pacienteServ.getIdPacienteServicio());
                    ubicacion.setFechaUbicaInicio(new Date());
                    ubicacion.setIdUsuarioUbicaInicio(usuarioSelected.getIdUsuario());
                    ubicacion.setIdEstatusUbicacion(4);
                    ubicacion.setInsertFecha(new Date());
                    ubicacion.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                    pacienteUbicacionService.insertar(ubicacion);
                }
            }
        }
    }

    private void getListaMedicamentosDTO(List<MedicamentoDTO> listaMedicametos) {
        for (NutricionParenteralDetalleExtended nutricionDetalleParental : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
            MedicamentoDTO item = new MedicamentoDTO();
            item.setClaveMedicamento(nutricionDetalleParental.getClaveMedicamento());
            item.setDosis(nutricionDetalleParental.getVolPrescrito());
            item.setDuracion(Constantes.PRESCRIPCION_DURACION);
            item.setFrecuencia(Constantes.SURT_MANUAL_HOSP_NOHORAS_POST_FECHA_PRESC);

            listaMedicametos.add(item);
        }
    }

    public void continuarValidacionOrdenPreparada() {
        try {
            status = Constantes.INACTIVO;
            String idPrescripcion = "";
            Prescripcion prescripcion = new Prescripcion();
            if (prescripcionPaciente != null) {
                idPrescripcion = prescripcionPaciente.getIdPrescripcion();
                prescripcion = prescripcionPaciente;
                prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                prescripcion.setUpdateFecha(new Date());
                prescripcion.setUpdateIdUsuario(usuarioSelected.getIdUsuario());

            } else {
                idPrescripcion = Comunes.getUUID();
                prescripcion.setIdPrescripcion(idPrescripcion);
                prescripcion.setIdEstructura(idEstructura);
                //Visita visita = new Visita();
                if (pacienteServ != null) {
                    prescripcion.setIdPacienteServicio(pacienteServ.getIdPacienteServicio());
                }
                /*if(pacienteSelect.getIdPaciente() != null) {                
                    //visita.setIdPaciente(pacienteSelect.getIdPaciente());
                    //visitaAbierta = visitaService.obtenerVisitaAbierta(visita);
                    if(visitaAbierta != null) {
                        PacienteServicio PacienteServicio = new PacienteServicio();
                        PacienteServicio.setIdVisita(visitaAbierta.getIdVisita());
                        PacienteServicio pacienteServicioActual = pacienteServicioService.obtenerPacienteServicioAbierto(PacienteServicio);
                        if(pacienteServicioActual != null) {
                            prescripcion.setIdPacienteServicio(pacienteServicioActual.getIdPacienteServicio());
                        }
                    }                                
                }*/
                prescripcion.setFolio(nutricionParenteralSelect.getFolio());
                prescripcion.setFechaPrescripcion(new Date());
                prescripcion.setFechaFirma(new Date());
                prescripcion.setTipoPrescripcion(Constantes.PRESC_NORMAL);
                prescripcion.setTipoConsulta("I");
                prescripcion.setIdMedico(nutricionParenteralSelect.getIdMedico());
                prescripcion.setRecurrente(Constantes.INACTIVO);
                prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
                prescripcion.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
                prescripcion.setInsertFecha(new Date());
                prescripcion.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                prescripcion.setIdTipoSolucion("e695144c-0a63-11eb-a03a-000c29750049");//TODO Revisar el tippo de soluciÃ³n
            }

            Surtimiento surtimiento = new Surtimiento();
            String idSurtimiento = Comunes.getUUID();
            nutricionParenteralSelect.setIdSurtimiento(idSurtimiento);
            surtimiento.setIdSurtimiento(idSurtimiento);
            surtimiento.setIdEstructuraAlmacen("e39332fa-9344-49e7-b588-6a3353f86bd6");//("82ee10e6-3cbf-4988-a1c0-40f017fc068c");//TODO Revisar el almacen que surte
            surtimiento.setIdPrescripcion(idPrescripcion);
            surtimiento.setFechaProgramada(new Date());
            // Se obtiene el tipo de documento
            int tipoDocumento = TipoDocumento_Enum.SURTIMIENTO_DE_PRESCRIPCION.getValue();
            // Se obtiene el objeto folio
            Folios folioSurtimiento = foliosService.obtenerPrefixPorDocument(tipoDocumento);
            // se obtien el numero de folio
            String folioSurti = Comunes.generaFolio(folioSurtimiento);
            surtimiento.setFolio(folioSurti);
            // Se actualiza el objeto para actualizar la secuencia
            folioSurtimiento.setUpdateFecha(new Date());
            folioSurtimiento.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
            folioSurtimiento.setSecuencia(Comunes.separaFolio(folioSurti));

            surtimiento.setFolio(folioSurti);
            surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            surtimiento.setIdEstatusMirth(EstatusGabinete_Enum.PENDIENTE.getValue());
            surtimiento.setInsertFecha(new Date());
            surtimiento.setInsertIdUsuario(usuarioSelected.getIdUsuario());

            PrescripcionInsumo prescripcionInsumo = null;
            List<PrescripcionInsumo> listaPrescripcionInsumos = new ArrayList<>();
            List<SurtimientoInsumo> listaSurtimientoInsumos = new ArrayList<>();
            List<SurtimientoEnviado> listaSurtimientoEnviado = new ArrayList<>();
            for (NutricionParenteralDetalle nutricionDetalleParental : nutricionParenteralSelect.getListaMezclaMedicamentos()) {
                prescripcionInsumo = new PrescripcionInsumo();
                String idPrescripcionInsumo = "";
                if (listPrescripcionInsumos.isEmpty()) {
                    idPrescripcionInsumo = Comunes.getUUID();
                    prescripcionInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
                    prescripcionInsumo.setIdPrescripcion(idPrescripcion);
                    prescripcionInsumo.setIdInsumo(nutricionDetalleParental.getIdMedicamento());
                    prescripcionInsumo.setFechaInicio(nutricionParenteralSelect.getFechaPrepara());
                    prescripcionInsumo.setDosis(nutricionDetalleParental.getVolPrescrito());
                    prescripcionInsumo.setFrecuencia(Constantes.SURT_MANUAL_HOSP_NOHORAS_POST_FECHA_PRESC);
                    prescripcionInsumo.setDuracion(Constantes.PRESCRIPCION_DURACION);
                    prescripcionInsumo.setComentarios(nutricionParenteralSelect.getFolio());
                    prescripcionInsumo.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.REGRISTRADA.getValue());
                    prescripcionInsumo.setIdEstatusMirth(EstatusGabinete_Enum.PENDIENTE.getValue());
                    prescripcionInsumo.setInsertFecha(new Date());
                    prescripcionInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    prescripcionInsumo.setEnvioJV(0);
                    listaPrescripcionInsumos.add(prescripcionInsumo);
                }
                for (PrescripcionInsumo prescInsumo : listPrescripcionInsumos) {
                    if (nutricionDetalleParental.getIdMedicamento().equals(prescInsumo.getIdInsumo())) {
                        idPrescripcionInsumo = prescInsumo.getIdPrescripcionInsumo();
                        break;
                    }
                }
                SurtimientoInsumo surtimientoInsumo = new SurtimientoInsumo();
                String idSurtimientoInsumo = Comunes.getUUID();
                surtimientoInsumo.setIdSurtimientoInsumo(idSurtimientoInsumo);
                surtimientoInsumo.setIdSurtimiento(idSurtimiento);
                surtimientoInsumo.setIdPrescripcionInsumo(idPrescripcionInsumo);
                surtimientoInsumo.setFechaProgramada(new Date());
                Integer cantidad = 1;
                if (nutricionDetalleParental.getVolPrescrito() != null && nutricionDetalleParental.getCantCalculada() != null) {
                    if (nutricionDetalleParental.getCantCalculada().compareTo(BigDecimal.ZERO) == 1) {
                        cantidad = nutricionDetalleParental.getVolPrescrito().divide(nutricionDetalleParental.getCantCalculada(), 2, RoundingMode.HALF_UP).intValue();
                    }
                }

                surtimientoInsumo.setCantidadSolicitada(cantidad);
                surtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                surtimientoInsumo.setFolioOrdenAVG(nutricionParenteralSelect.getFolio());
                surtimientoInsumo.setNumeroMedicacion(1);
                surtimientoInsumo.setInsertFecha(new Date());
                surtimientoInsumo.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                surtimientoInsumo.setCantidadEnviada(cantidad);
                surtimientoInsumo.setFechaEnviada(new Date());
                surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                listaSurtimientoInsumos.add(surtimientoInsumo);

                SurtimientoEnviado surtimientoEnviado = new SurtimientoEnviado();
                surtimientoEnviado.setIdSurtimientoEnviado(Comunes.getUUID());
                surtimientoEnviado.setIdSurtimientoInsumo(idSurtimientoInsumo);
                surtimientoEnviado.setIdInventarioSurtido(nutricionDetalleParental.getIdInventario());
                surtimientoEnviado.setCantidadEnviado(cantidad);
                surtimientoEnviado.setCantidadRecibido(0);
                surtimientoEnviado.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                surtimientoEnviado.setInsertFecha(new Date());
                surtimientoEnviado.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                listaSurtimientoEnviado.add(surtimientoEnviado);

            }

            Solucion solucion = new Solucion();
            solucion.setIdSolucion(Comunes.getUUID());
            solucion.setIdSurtimiento(idSurtimiento);
            solucion.setIdEnvaseContenedor(1);
            solucion.setMuestreo(0);
            //solucion.setCondiciones(conservacion);
//            solucion.setVolumen(nutricionParenteralSelect.getVolumenTotal());
            solucion.setVolumenFinal(nutricionParenteralSelect.getVolumenTotal().toString());
            //solucion.setObservaciones(observaciones);
            solucion.setInsertFecha(new Date());
            solucion.setInsertIdUsuario(usuarioSelected.getIdUsuario());
            solucion.setIdEstatusSolucion(EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue());
            //TODO Revisar como obtener estos valores y a quien seran asignados
            if (!listaPrescripcionInsumos.isEmpty()) {
                listaPrescripcionInsumos.get(0).setIdTipoIngrediente(1);
                listaPrescripcionInsumos.get(0).setVelocidad(2d);
                listaPrescripcionInsumos.get(0).setRitmo(0.033d);
                listaPrescripcionInsumos.get(0).setPerfusionContinua(1);
                listaPrescripcionInsumos.get(0).setIdUnidadConcentracion(2);
            }
            List<DiagnosticoPaciente> listDiagnosticosPaciente = new ArrayList<>();
            DiagnosticoPaciente diagnosticoPaciente = null;
            if (!nutricionParenteralSelect.getListaDiagnosticos().isEmpty()) {
                for (Diagnostico diagnostico : nutricionParenteralSelect.getListaDiagnosticos()) {
                    diagnosticoPaciente = new DiagnosticoPaciente();
                    diagnosticoPaciente.setIdDiagnosticoPaciente(Comunes.getUUID());
                    diagnosticoPaciente.setIdPrescripcion(idPrescripcion);
                    diagnosticoPaciente.setFechaDiagnostico(fechaActual);
                    diagnosticoPaciente.setIdUsuarioDiagnostico(usuarioSelected.getIdUsuario());
                    diagnosticoPaciente.setIdDiagnostico(diagnostico.getIdDiagnostico());
                    diagnosticoPaciente.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
                    diagnosticoPaciente.setInsertFecha(fechaActual);
                    diagnosticoPaciente.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                    listDiagnosticosPaciente.add(diagnosticoPaciente);
                }
            }
            boolean resp = false;
            if (prescripcionPaciente != null) {
                resp = nutricionParenteralService.generarSolucionNutricionParenteral(true, prescripcion, listaPrescripcionInsumos, surtimiento,
                        listaSurtimientoInsumos, listaSurtimientoEnviado, solucion, folioSurtimiento, nutricionParenteralSelect, listDiagnosticosPaciente);
            } else {
                resp = nutricionParenteralService.generarSolucionNutricionParenteral(false, prescripcion, listaPrescripcionInsumos, surtimiento,
                        listaSurtimientoInsumos, listaSurtimientoEnviado, solucion, folioSurtimiento, nutricionParenteralSelect, listDiagnosticosPaciente);
            }

            if (resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ord.nutr.parenteral.exito.validar"), null);
                status = Constantes.ACTIVO;
                previewPrint(nutricionParenteralSelect);
                buscarOrdenesPreparacion();
            }

        } catch (Exception ex) {
            LOGGER.error("Error en metodo continuarValidacionOrdenPreparacion en clase PrescribirMezclaNPTMB     " + ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ord.nutr.parenteral.excep.continuar"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Consulta Diagnosticos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param cadena
     * @return
     */
    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.autocompleteDiagnostico()");
        List<Diagnostico> diagnList = new ArrayList<>();

        try {
            diagnList.addAll(diagnosticosService.obtenerListaAutoComplete(cadena));

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.dia.lista"), null);
        }
        return diagnList;
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

        } catch (IOException ex) {
            LOGGER.error("Ocurrio un error al parsear el request:", ex);
        }
        return dto;
    }

    /**
     * Impresion de prescripción de NPT
     *
     * @param item
     */
    public void previewPrint(NutricionParenteralExtended item) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.previewPrint()");
        boolean estatus = false;
        byte[] buffer = null;
        try {
            nutricionParenteralSelect = (NutricionParenteralExtended) ordenMezclaLazy.getRowKey(item);

            if (usuarioSelected == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

            } else if (!permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para esta acción.", null);

            } else if (nutricionParenteralSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Orden de prescripción NPT inválida.", null);

            } else if (nutricionParenteralSelect.getIdSurtimiento() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción NPT inválida.", null);

            } else {
                Surtimiento_Extend surtimiento;
                surtimiento = surtimientoService.obtenerSurtimientoExtendedByIdSurtimiento(nutricionParenteralSelect.getIdSurtimiento());
                if (surtimiento == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surimtiento de prescripción NPT inválida.", null);

                } else {
                    Prescripcion_Extended prescripcion = prescripcionService.obtenerByFolioPrescripcion(nutricionParenteralSelect.getFolio());
                    if (prescripcion == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción NPT inválida.", null);

                    } else {
                        String idSolucion = null;
                        String idSurtimiento = nutricionParenteralSelect.getIdSurtimiento();
                        Solucion solucion = solucionService.obtenerSolucion(idSolucion, idSurtimiento);
                        if (solucion == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución de prescripción NPT inválida.", null);

                        } else {
                            EntidadHospitalaria enti = new EntidadHospitalaria();
                            enti.setDomicilio("");
                            enti.setNombre("");

                            if (nutricionParenteralSelect.getIdEstructura() != null) {
                                Estructura estruct = estructuraService.obtenerEstructura(nutricionParenteralSelect.getIdEstructura());
                                if (estruct != null && estruct.getIdEntidadHospitalaria() != null) {
                                    enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
                                }
                            }

//todo :: agregar las alergias del paciente
                            String alergias = "";
                            String diagnosticos = "";
                            List<Diagnostico> diagList = diagnosticosService.obtenerByIdPrescripcion(prescripcion.getIdPrescripcion());
                            nutricionParenteralSelect.setListaDiagnosticos(diagList);
                            for (Diagnostico diagnostico : nutricionParenteralSelect.getListaDiagnosticos()) {
                                if (diagnosticos.isEmpty()) {
                                    diagnosticos = diagnostico.getClave() + ": " + diagnostico.getNombre();
                                } else {
                                    diagnosticos = diagnosticos + "; " + diagnostico.getClave() + ": " + diagnostico.getNombre();
                                }
                            }

                            if (nutricionParenteralSelect.getIdContenedor() != null) {
                                EnvaseContenedor contenedor = envaseService.obtenerXidEnvase(nutricionParenteralSelect.getIdContenedor());
                                if (contenedor != null) {
                                    surtimiento.setNombreEnvaseContenedor(contenedor.getDescripcion());
                                }
                            }
                            if (nutricionParenteralSelect.getIdPaciente() != null) {
                                Paciente_Extended p = pacienteService.obtenerPacienteCompletoPorId(nutricionParenteralSelect.getIdPaciente());
                                if (p != null) {
                                    nutricionParenteralSelect.setFechaNacimiento(p.getFechaNacimiento());
                                }
                            }

                            if (nutricionParenteralSelect.getIdViaAdministracion() != null) {
                                ViaAdministracion vad = viaAdministracionService.obtenerPorId(nutricionParenteralSelect.getIdViaAdministracion());
                                if (vad != null) {
                                    nutricionParenteralSelect.setViaAdministracion(vad.getNombreViaAdministracion());
                                }
                            }

                            if (nutricionParenteralSelect.getIdTipoSolucion() != null) {
                                TipoSolucion ts = tipoSolucionService.obtener(new TipoSolucion(nutricionParenteralSelect.getIdTipoSolucion()));
                                if (ts != null) {
                                    surtimiento.setTipoSolucion(ts.getClave());
                                }
                            }
                            prescripcionPaciente = prescripcionService.obtener(new Prescripcion(surtimiento.getIdPrescripcion()));

                            buffer = reportesService.imprimePrescripcionMezclaNPT(
                                    enti,
                                    nutricionParenteralSelect,
                                    surtimiento,
                                    solucion,
                                    prescripcionPaciente,
                                    alergias,
                                    diagnosticos,
                                    usuarioSelected
                            );

                            if (buffer != null) {
                                sesion.setReportStream(buffer);
                                sesion.setReportName(String.format("PrescNPT_%s.pdf", item.getFolio()));
                                estatus = Constantes.ACTIVO;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error imprimir orden de nutrición parenteral: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Usuario getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuario usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public NutricionParenteralExtended getNutricionParenteralSelect() {
        return nutricionParenteralSelect;
    }

    public void setNutricionParenteralSelect(NutricionParenteralExtended nutricionParenteralSelect) {
        this.nutricionParenteralSelect = nutricionParenteralSelect;
    }

    public OrdenMezclaLazy getOrdenMezclaLazy() {
        return ordenMezclaLazy;
    }

    public void setOrdenMezclaLazy(OrdenMezclaLazy ordenMezclaLazy) {
        this.ordenMezclaLazy = ordenMezclaLazy;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public boolean isContEdit() {
        return contEdit;
    }

    public void setContEdit(boolean contEdit) {
        this.contEdit = contEdit;
    }

    public List<Medicamento> getListaMedicamentos() {
        return listaMedicamentos;
    }

    public void setListaMedicamentos(List<Medicamento> listaMedicamentos) {
        this.listaMedicamentos = listaMedicamentos;
    }

    public RespuestaAlertasDTO getAlertasDTO() {
        return alertasDTO;
    }

    public void setAlertasDTO(RespuestaAlertasDTO alertasDTO) {
        this.alertasDTO = alertasDTO;
    }

    /*public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }*/
    public String getHipersensibilidades() {
        return hipersensibilidades;
    }

    public void setHipersensibilidades(String hipersensibilidades) {
        this.hipersensibilidades = hipersensibilidades;
    }

    public String getReacciones() {
        return reacciones;
    }

    public void setReacciones(String reacciones) {
        this.reacciones = reacciones;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public Paciente getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente_Extended pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public Usuario getMedicoSelect() {
        return medicoSelect;
    }

    public void setMedicoSelect(Usuario medicoSelect) {
        this.medicoSelect = medicoSelect;
    }

    public Paciente getPacienteNuevo() {
        return pacienteNuevo;
    }

    public void setPacienteNuevo(Paciente pacienteNuevo) {
        this.pacienteNuevo = pacienteNuevo;
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

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
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

    public Usuario_Extended getMedicoExtended() {
        return medicoExtended;
    }

    public void setMedicoExtended(Usuario_Extended medicoExtended) {
        this.medicoExtended = medicoExtended;
    }

    public boolean isMostrarBotones() {
        return mostrarBotones;
    }

    public void setMostrarBotones(boolean mostrarBotones) {
        this.mostrarBotones = mostrarBotones;
    }

    public boolean isDeshabilitaValidar() {
        return deshabilitaValidar;
    }

    public void setDeshabilitaValidar(boolean deshabilitaValidar) {
        this.deshabilitaValidar = deshabilitaValidar;
    }

    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public boolean isIsJefeArea() {
        return isJefeArea;
    }

    public void setIsJefeArea(boolean isJefeArea) {
        this.isJefeArea = isJefeArea;
    }

    public List<CamaExtended> getListaCamas() {
        return listaCamas;
    }

    public void setListaCamas(List<CamaExtended> listaCamas) {
        this.listaCamas = listaCamas;
    }

    public void seleccionaCama() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.seleccionaCama()");
        try {
            nutricionParenteralSelect.setIdCama(nutricionParenteralSelect.getIdCama());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public List<Estructura> getListServiciosQueSurte() {
        return listServiciosQueSurte;
    }

    public void setListServiciosQueSurte(List<Estructura> listServiciosQueSurte) {
        this.listServiciosQueSurte = listServiciosQueSurte;
    }

    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario queF
     * puede surtir
     */
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtieneServiciosQuePuedeSurtir()");
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
                    this.listServiciosQueSurte.addAll(estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura()));
//                    TODO: revisar el listado de las estructuras
//                    Estructura estructuraServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(usuarioSelected.getIdEstructura());
//                    estructuraServicio = estructuraService.obtenerEstructura(estructuraServicio.getIdEstructura());
//                    listServiciosQueSurte.add(estructuraServicio);
//                    List<String> idsEstructura = estructuraService.obtenerIdsEstructuraJerarquica(estructuraServicio.getIdEstructura(), true);
//                    for (String item : idsEstructura) {
//                        listServiciosQueSurte.add(estructuraService.obtenerEstructura(item));
//                    }
                }
            } catch (Exception excp) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", excp.getMessage());
            }
        }
    }

    public void generaInstruccionesCondiciones() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.generaInstruccionesCondiciones()");
        if (this.nutricionParenteralSelect == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Mezcla inválida.", null);

        } else if (this.nutricionParenteralSelect.getListaMezclaMedicamentos() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No hay insumos registrados.", null);

        } else if (this.nutricionParenteralSelect.getListaMezclaMedicamentos().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No hay insumos registrados.", null);

        } else if (this.nutricionParenteralSelect.getIdContenedor() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Contenedor inválido.", null);

        } else if (this.nutricionParenteralSelect.getIdContenedor() == 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Contenedor inválido.", null);

        } else {
            try {
                EnvaseContenedor ec = this.envaseService.obtenerXidEnvase(this.nutricionParenteralSelect.getIdContenedor());
                if (ec == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No hay insumos registrados.", null);

                } else {
                    if (ec.getInstruccionPreparacion() == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Plantilla de instrucciones vacía.", null);

                    } else if (ec.getInstruccionPreparacion().trim().isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Plantilla de instrucciones vacía.", null);

                    } else {
                        String cont = ec.getInstruccionPreparacion();

                        cont = cont.replace("#NOMBRE_CONTENEDOR#",
                                (ec.getDescripcion() == null) ? ""
                                : eliminaCarEspe(ec.getDescripcion()));

                        int noInsumos = this.nutricionParenteralSelect.getListaMezclaMedicamentos().size();
                        Double volPorDrenar = 0.0;
                        for (int i = 0; i < noInsumos; i++) {
                            String num = String.format("%02d", i + 1);
                            if (this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).isTempAmbiente()) {
                                this.nutricionParenteralSelect.setTempAmbiente(true);
                            }
                            if (this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).isRefrigeracion()) {
                                this.nutricionParenteralSelect.setTempRefrigeracion(true);
                            }
                            if (this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).isFotosensible()) {
                                this.nutricionParenteralSelect.setProteccionLuz(true);
                            }
                            Double concentracion = 0.0;
                            Double cantidadEnvase = 0.0;
                            Double dosisPrescrita = 0.0;
                            Double volumenPorEnvase = 0.0;
                            Double volumenRequerido = 0.0;
                            Double volSobrellenado = 0.0;
                            try {
                                concentracion = this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getConcentracion().doubleValue();
                                cantidadEnvase = this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getCantPorEnvase();
                                dosisPrescrita = this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getDosis().doubleValue();
                                volSobrellenado = this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getSobrellenado().doubleValue();
                                volumenPorEnvase = concentracion / cantidadEnvase;
                                volumenRequerido = (dosisPrescrita + volSobrellenado) / volumenPorEnvase;
                                if (!this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).isDiluyente()) {
                                    volPorDrenar = volPorDrenar + volumenRequerido;
                                }
                            } catch (Exception e) {
                                LOGGER.error("Error al obtener valores de instrucciones de preparacion :: {}", e.getMessage());
                            }

                            if (this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).isDiluyente()) {
                                cont = cont.replace("#NOMBRE_INSUMO_DILUYENTE#",
                                        (this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getNombreCorto() == null) ? ""
                                        : this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getNombreCorto());
                            } else {

                                cont = cont.replace("#VOLUMEN_PRESCRITO_" + num + "#",
                                        (this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getDosis() == null) ? ""
                                        : volumenRequerido.toString());

                                cont = cont.replace("#NOMBRE_INSUMO_PRESCRITO_" + num + "#",
                                        (this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getNombreCorto() == null) ? ""
                                        : this.nutricionParenteralSelect.getListaMezclaMedicamentos().get(i).getNombreCorto());
                            }
                        }
//                        Double sumaVolPorDrenar = this.surtimientoExtendedSelected.getVolumenTotal() - vol
                        cont = cont.replace("#CANTIDAD_TOTAL_A_DRENAR#",
                                (volPorDrenar <= 0) ? ""
                                        : volPorDrenar.toString());

                        cont = cont.replace("#VOLUMEN_INDICADO_MEDICO#",
                                (this.nutricionParenteralSelect.getVolumenTotal() == null) ? ""
                                : this.nutricionParenteralSelect.getVolumenTotal().toString());

                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_01# mL de #NOMBRE_INSUMO_PRESCRITO_01#", "");
                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_02# mL de #NOMBRE_INSUMO_PRESCRITO_02#", "");
                        cont = cont.replace("Tomar #VOLUMEN_PRESCRITO_03# mL de #NOMBRE_INSUMO_PRESCRITO_03#", "");

                        this.nutricionParenteralSelect.setInstruccionPreparacion(cont);
                        StringBuilder instrucionPreparacion = solUtils.generarInstrucPreparaNPT(nutricionParenteralSelect);
                        nutricionParenteralSelect.setInstruccionPreparacion(instrucionPreparacion.toString());
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Error al generar instrucciones de preparación: {} ", e);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar instrucciones de preparación. ", null);
            }
        }
    }

    public List<EnvaseContenedor> getEnvaseList() {
        return envaseList;
    }

    public void setEnvaseList(List<EnvaseContenedor> envaseList) {
        this.envaseList = envaseList;
    }

    private String eliminaCarEspe(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
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
                            LOGGER.debug("Error al obtener la Via de Administración predeterminada :: {} ", ex.getMessage());
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al obtener la Via de Administración predeterminad1.", null);
                        }
                    }
                }
            }
            
        } catch (Exception e){
            LOGGER.error("Error al obtener valores NPT predeterminados :: {} ", e.getMessage());
        }
    }
    
    public void obtenerEnvases() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerEnvases()");
        this.envaseList = new ArrayList<>();
        if (idEnvaseContList!= null && !idEnvaseContList.isEmpty()) {
            List<EnvaseContenedor> listEnvCon = this.solUtils.obtenerListaEnvases();
            for (EnvaseContenedor item : listEnvCon){
                for (Integer item2 : idEnvaseContList){
                    if (Objects.equals(item.getIdEnvaseContenedor(), item2)){
                        this.envaseList.add(item);
                        break;
                    }
                }
            }
        } else {
            this.envaseList.addAll(this.solUtils.obtenerListaEnvases());
        }
    }

    public boolean isExistePrescripcion() {
        return existePrescripcion;
    }

    public void setExistePrescripcion(boolean existePrescripcion) {
        this.existePrescripcion = existePrescripcion;
    }

    public void activaPerfusion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.activaPerfusion()");
        asignaVelocidad();
    }

    public void asignaVelocidad() {
            LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.asignaVelocidad()");
        try {
            this.nutricionParenteralSelect.setVelocidad(BigDecimal.ZERO);
            if (this.nutricionParenteralSelect != null) {
                if (this.nutricionParenteralSelect.isPerfusionContinua()) {
                    if (this.nutricionParenteralSelect.getVolumenTotal() != null) {
                        if (this.nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
                            BigDecimal vel = BigDecimal.ZERO;
                            try {
                                if (this.nutricionParenteralSelect.getHorasInfusion() != null
                                        && this.nutricionParenteralSelect.getHorasInfusion() > 0
                                        && this.nutricionParenteralSelect.getVolumenTotal().compareTo(BigDecimal.ZERO) == 1) {
                                    vel = this.nutricionParenteralSelect.getVolumenTotal().divide(new BigDecimal(this.nutricionParenteralSelect.getHorasInfusion() ) , 3, RoundingMode.HALF_UP);
                                }
                            } catch (Exception e){
                                LOGGER.error("Error al calcular velocidad :: {} ", e.getMessage() );
                            }
                            this.nutricionParenteralSelect.setVelocidad(vel);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al asignar velocidad de infusión :: {} ", e.getMessage());
        }
    }

    public BigDecimal getVolumenPres() {
        return volumenPres;
    }

    public void setVolumenPres(BigDecimal volumenPres) {
        this.volumenPres = volumenPres;
    }

    public boolean isValidaPrescripcion() {
        return validaPrescripcion;
    }

    public void setValidaPrescripcion(boolean validaPrescripcion) {
        this.validaPrescripcion = validaPrescripcion;
    }

    public void obtenerSuperficieCorporal() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerSuperficieCorporal()");
        Double peso = this.nutricionParenteralSelect.getPeso().doubleValue();
        Double altura;
        if (this.nutricionParenteralSelect.getTallaPaciente() == null) {
            altura = 0d;
        } else {
            altura = this.nutricionParenteralSelect.getTallaPaciente().doubleValue();
        }
        if (peso > 0 && altura > 0) {
            Double supCorp = this.solUtils.obtenerSuperficieCorporal(peso, altura);
            this.nutricionParenteralSelect.setAreaCorporal(BigDecimal.valueOf(supCorp));
        }
    }

    public void agregarDiagostico(SelectEvent e) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.agregarDiagostico()");
        try {
            Diagnostico d = (Diagnostico) e.getObject();
            if (d != null) {
                if (this.nutricionParenteralSelect.getListaDiagnosticos() == null) {
                    this.nutricionParenteralSelect.setListaDiagnosticos(new ArrayList<>());
                }
                List<Diagnostico> ld = new ArrayList<>();
                ld.addAll(this.nutricionParenteralSelect.getListaDiagnosticos());
                ld.add(d);
                this.nutricionParenteralSelect.setListaDiagnosticos(ld);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al agregar diagnóstico :: {} ", ex.getMessage());
        }
    }

    public void cancelarSolucionPreparada(String idNutricionParenteral) {

    }

    public ViaAdministracion getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(ViaAdministracion viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
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

    public List<TipoSolucion> getTipoSolucionList() {
        return tipoSolucionList;
    }

    public void setTipoSolucionList(List<TipoSolucion> tipoSolucionList) {
        this.tipoSolucionList = tipoSolucionList;
    }

    private void obtenerViasAdministracion() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerViasAdministracion()");
        this.viaAdministracionList = new ArrayList<>();
        
        if (idViaAdmontList != null && !idViaAdmontList.isEmpty()) {
            List<ViaAdministracion> listViaAdmon = this.solUtils.obtenerListaViaAdministracion();
            for (ViaAdministracion item : listViaAdmon){
                for (Integer item2 : idViaAdmontList){
                    if (Objects.equals(item.getIdViaAdministracion(), item2)){
                        this.viaAdministracionList.add(item);
                        break;
                    }
                }
            }
        } else {
            this.viaAdministracionList.addAll(this.solUtils.obtenerListaViaAdministracion());
        }
        
    }

    public void obtenerTiposSolucion() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerTiposSolucion()");
        List<String> listaClaves = new ArrayList<>();
        listaClaves.add("NPT");
// TODO: agregar el método requerido
        this.tipoSolucionList = new ArrayList<>();
        this.tipoSolucionList.addAll(this.solUtils.obtenerListaTiposSolucion(listaClaves));
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

    public void onTabChange(TabChangeEvent evt) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.onTabChange()");
// RN: Listar prescripciones solo con estatus de surtimiento surtido
        this.estatusSolucionLista = new ArrayList<>();
        String valorStatus = evt.getTab().getId();
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

        buscarOrdenesPreparacion();
    }

    private void calculaFechaMinimaSolicitud() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        if (numMinutosEntregaMezcla != null && numMinutosEntregaMezcla > 0) {
            cal.add(Calendar.MINUTE, numMinutosEntregaMezcla);
        }
        fechaMinPrescrMezcla = cal.getTime();
        cal.add(Calendar.MINUTE, -5);
        fechaMinPrescrMezclaValida = cal.getTime();
    }

    public Date getFechaMinPrescrMezclaValida() {
        return fechaMinPrescrMezclaValida;
    }

    public void setFechaMinPrescrMezclaValida(Date fechaMinPrescrMezclaValida) {
        this.fechaMinPrescrMezclaValida = fechaMinPrescrMezclaValida;
    }

    public Date getFechaMinPrescrMezcla() {
        return fechaMinPrescrMezcla;
    }

    public void setFechaMinPrescrMezcla(Date fechaMinPrescrMezcla) {
        this.fechaMinPrescrMezcla = fechaMinPrescrMezcla;
    }

    private List<CalculoNpt> obtenerClavesParaCalculos(String tipo) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.obtenerClavesParaCalculos()");
        List<CalculoNpt> lista = new ArrayList<>();
        try {
            CalculoNpt o = new CalculoNpt();
            o.setTipo(tipo);
            lista.addAll(calculoNptService.obtenerLista(o));
        } catch (Exception e) {
            LOGGER.error("Error al obtener Lista de insumos para cálculo de NPT :: {} ", e.getMessage());
        }
        return lista;
    }

    /**
     * Genera la impresión de la prescripción de mezclas.
     *
     * @param se
     */
    public void imprimeDocumento(Surtimiento_Extend se) {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.imprimeDocumento()");
        boolean estatus = false;
        try {
            String nombreUsuario = usuarioSelected.getNombre() + ' ' + usuarioSelected.getApellidoPaterno() + ' ' + usuarioSelected.getApellidoMaterno();
            Integer idTipoReporte = 1;
            byte[] buffer = this.solUtils.imprimeDocumento(se, nombreUsuario, idTipoReporte);
            if (buffer == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar impresión. ", null);
            } else {
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("PRE_%s.pdf", se.getFolioPrescripcion()));
                estatus = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al generar la impresión :: {} ", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al generar impresión. " + ex.getMessage(), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, estatus);
    }

    /**
     * REaliza las acciones de validación y preparación de cancelación de
     * prescripción
     */
    public void validaCancelacion() {
        LOGGER.trace("mx.mc.magedbean.RegistroSolucionMB.validaCancelacion()");
        boolean status = false;
        
        if (this.usuarioSelected == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (this.usuarioSelected.getIdUsuario() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Usuario inválido.", null);

        } else if (!permiso.isPuedeEliminar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos de esta acción.", null);

        } else if (nutricionParenteralSelect == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else if (nutricionParenteralSelect.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de prescripción inválido.", null);

        } else {
            try {
                Surtimiento_Extend surtimiento = surtimientoService.obtenerSurtimientoExtendedByIdSurtimiento(nutricionParenteralSelect.getIdSurtimiento());

                if (surtimiento.getIdPrescripcion() == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción inválida.", null);
                } else {
                    String idSurtimiento = nutricionParenteralSelect.getIdSurtimiento();
                    String idSolucion = null;
                    solucion = this.solUtils.obtenerSolucion(idSolucion, idSurtimiento);

                    if (solucion == null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

                    } else if (!Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue())
                            && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                            && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue())
                            && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())
                            && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())
                            && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_VALIDADA.getValue())
                            && !Objects.equals(solucion.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_RECHAZADA.getValue())) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El estatus no permite cancelación.", null);
                    } else {
//                        solucion = new Solucion();
                        status = true;
                    }
                }
            } catch (Exception ex){
                LOGGER.error("Error al evaluar estatus de la prescripción :: {} ",ex.getMessage() );
            }
        }
        PrimeFaces.current().ajax().addCallbackParam("estatus", status);
    }

    private void evaluaEdicion() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.evaluaEdicion()");
        this.editable = false;
        this.cancelable = false;
        this.rechazable = false;
        if (nutricionParenteralSelect != null) {
            if (nutricionParenteralSelect.getIdEstatusSolucion() != null) {

                if (permiso.isPuedeAutorizar()) {
                    this.editable = true;
                    this.cancelable = true;
                    this.rechazable = true;

                } else {

                    if (permiso.isPuedeEditar()) {
                        if (validaPrescripcion) {
                            if (Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                                    || Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())
                                    || Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.OP_RECHAZADA.getValue())
                                    ) {
                                long minutos = FechaUtil.diferenciaFechasEnMinutos(nutricionParenteralSelect.getFechaProgramada(), new java.util.Date());
                                if (minutos <= 90) {
                                    this.editable = true;
                                }
                            }
                        } else {
                            if (Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue())
                                    || Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())) {
                                long minutos = FechaUtil.diferenciaFechasEnMinutos(nutricionParenteralSelect.getFechaProgramada(), new java.util.Date());
                                if (minutos <= 90) {
                                    this.editable = true;
                                }
                            }
                        }
                    }

                    if (permiso.isPuedeEliminar()) {
                        if (validaPrescripcion) {
                            if (Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.ORDEN_CREADA.getValue())) {
                                long minutos = FechaUtil.diferenciaFechasEnMinutos(nutricionParenteralSelect.getFechaProgramada(), new java.util.Date());
                                if (minutos <= 90) {
                                    this.editable = true;
                                }
                            }
                        } else {
                            if (Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_REGISTRADA.getValue())
                                    || Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                                    || Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_RECHAZADA.getValue())
                                    || Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_VALIDADA.getValue())) {
                                long minutos = FechaUtil.diferenciaFechasEnMinutos(nutricionParenteralSelect.getFechaProgramada(), new java.util.Date());
                                if (minutos <= 90) {
                                    this.cancelable = true;
                                }
                            }
                        }
                    }

                    if (permiso.isPuedeProcesar()) {
                        if (validaPrescripcion) {
                            if (!Objects.equals(nutricionParenteralSelect.getIdEstatusSolucion(), EstatusSolucion_Enum.PRESCRIPCION_SOLICITADA.getValue())
                                    && nutricionParenteralSelect.getIdEstatusSolucion() != EstatusSolucion_Enum.ORDEN_CREADA.getValue()) {
                            } else {
                                this.rechazable = true;
                            }
                        }
                    }

                }
            }
        }
    }
    
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
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

    public List<Sobrellenado> getSobrelleadoList() {
        return sobrelleadoList;
    }

    public void setSobrelleadoList(List<Sobrellenado> sobrelleadoList) {
        this.sobrelleadoList = sobrelleadoList;
    }

    public Solucion getSolucion() {
        return solucion;
    }

    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }

    
    /**
     * Realiza la cancelación de una prescripción, surtimiento y solucion cada
     * uno con sus respectivos detalles
     */
    public void ejecutaCancelacion() {
        LOGGER.trace("mx.mc.magedbean.PrescribirMezclaNPTMB.ejecutaCancelacion()");
        boolean status = false;
        if (this.solucion == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

        } else if (this.solucion.getIdSolucion() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solución inválida.", null);

        } else if (this.solucion.getIdSurtimiento() == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);

//        } else if (this.surtimientoExtendedSelected == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);
//
//        } else if (this.surtimientoExtendedSelected.getIdSurtimiento() == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Surtimiento de solución inválido.", null);
//
//        } else if (this.surtimientoExtendedSelected.getIdPrescripcion() == null) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Prescripción de solución inválida.", null);

        } else if (this.solucion.getComentariosCancelacion() == null
                || this.solucion.getComentariosCancelacion().trim().isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Comentarios de canncelación requeridos.", null);

//        } else if (this.solucion.getIdMotivoCancelacion() < 1) {
//            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Motivo de cancelación requerido.", null);
        } else {
//            String idPrescripcion = this.surtimientoExtendedSelected.getIdPrescripcion();
            try {
                this.solucion.setIdEstatusSolucion(EstatusSolucion_Enum.CANCELADA.getValue());
                this.solucion.setIdUsuarioCancela(this.usuarioSelected.getIdUsuario());
                this.solucion.setUpdateFecha(new java.util.Date());
                this.solucion.setUpdateIdUsuario(this.usuarioSelected.getIdUsuario());
                status = this.surtimientoService.cancelarPrescripcionNptSurtimientoSolucion(this.solucion.getIdSurtimiento(), this.solucion);
                if (!status) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al cancelar la prescripción.", null);

                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, "Prescripción cancelada.", null);
//                    Prescripcion p = this.solUtils.obtenerPrescripcion(surtimientoExtendedSelected);
                    Usuario u = null;
//                    if (p != null) {
//                        String IdUsuario = p.getIdMedico();
//                        u = this.solUtils.obtenerUsuarioPorId(IdUsuario);
                        if (u != null) {
//                            String asunto = "Mezcla " + surtimientoExtendedSelected.getFolio() + " Cancelada. ";
                            String msj = "";
                            if (solucion != null) {
                                msj = solucion.getComentariosCancelacion();

                            }
                            // TODO: obtener Usuarios de centro de mezclas para nnontificar la canncelación
                            // TODO: obtener el nombre, numero de Paciente y comentaruis de cancelación
//                            this.solUtils.enviarCorreo(u, this.usuarioSelected, asunto, msj, surtimientoExtendedSelected);
                        }
                    }
//                    obtenerSurtimientos();
                    this.solucion = new Solucion();
//                }
            } catch (Exception e) {
                LOGGER.error("Error al cancelar la Prescripcion :: ", e.getMessage());
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al cancelar la prescripción.", null);
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(paramEstatus, status);
    }
    
    
}
