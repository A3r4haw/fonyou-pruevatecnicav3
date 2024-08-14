package mx.mc.magedbean;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoJustificacion;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoJustificacionService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.FileOutputStream;
import java.io.IOException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import mx.gob.issste.siam.client.RecetaSiamCL;
import mx.gob.issste.ws.model.InsumoRecetaSiam;
import mx.gob.issste.ws.model.RecetaSiamNO;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.lazy.ServSurtPrescripcionExtLazy;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoPrescripcion_Enum;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Visita;
import mx.mc.service.CantidadRazonadaService;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.PacienteService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import static mx.mc.util.FechaUtil.formatoCadena;
import mx.mc.util.MedicoUtil;
import mx.mc.util.PacienteDomicilioUtil;
import mx.mc.util.PacienteServicioUtil;
import mx.mc.util.PacienteUtil;
import mx.mc.util.VisitaUtil;
import mx.mc.model.CensoPaciente;
import mx.mc.model.CensoInsumo;
import mx.mc.model.CensoInsumoDetalleExtended;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Medicamento_Extended;
import mx.mc.service.CensoPacienteService;
import mx.mc.service.CensoInsumoService;
import mx.mc.service.CensoInsumoDetalleService;
import mx.mc.service.EntidadHospitalariaService;

import org.primefaces.PrimeFaces;
import org.primefaces.event.TabChangeEvent;
/**
 *
 * @author mcalderon
 */
@Controller
@Scope(value = "view")
public class ServicioSurtPrescripcionExtMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioSurtPrescripcionExtMB.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
   
    private boolean puedeCancelar;   
    private PermisoUsuario permiso; 
    private boolean editable;

    private boolean huboError;
    private boolean vales;
    private String cadenaBusqueda;
    private boolean elementoSeleccionado;
    private Pattern regexNumber;
    
    private Date fechaActual;
    private Usuario usuarioSelected;
    private Integer numHorPrevReceta;
    private Integer numHorPostReceta;   
    private boolean habilitaVales;
    private boolean valCantidadRazonada;
    private Integer maxNumDiasResurtible;
    private Integer numDiasResurtimiento;
    private boolean procederSurtimiento;
    private Integer numResurtimiento;
    private Integer cantResurt;
    private String programada;
    private String surtida;
    private String cancelada;
    List<Integer> listEstatusPrescripcion;
    List<Integer> listEstatusSurtimiento;
    private Paciente paciente;
    private boolean habilitaValidacionVale;
    private boolean existePaciente;
    private Integer xCantidadNueva;
    private boolean activaModal;
    private boolean puedeSurtirPrescr;
    private CensoInsumo cInsumo;
    private CensoPaciente cPaciente;

    @Autowired
    private EstructuraService estructuraService;
    private List<String> listServiciosQueSurte;

    @Autowired
    private SurtimientoService surtimientoService;
    @Autowired
    private SurtimientoInsumoService surtimientoInsumoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private CensoPacienteService censoPacienteService;
    @Autowired
    private CensoInsumoService censoInsumoService;
    @Autowired
    private CensoInsumoDetalleService censoInsumoDetalleService;
    @Autowired
    private EntidadHospitalariaService entidadHospitalariaService;
    

    private Surtimiento_Extend surtimientoExtendedSelected;
    private List<Surtimiento_Extend> surtimientoExtendedList;

    private SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected;
    private List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;

    private List<TransaccionPermisos> permisosList;
    private String tipoPrescripcion;
    private List<String> tipoPrescripcionSelectedList;
    private ServSurtPrescripcionExtLazy servSurtPrescripcionExtLazy;
    private int cajasSurtidas;
    private int cantidadTotalSurtir;

    private String codigoBarras;
    private boolean eliminaCodigoBarras;

    private List<TipoJustificacion> justificacionList;
    @Autowired
    private TipoJustificacionService tipoJustificacionService;    

    private Integer xcantidad;
    private Integer noDiasCaducidad;
    private boolean surtimientoMixto;
    private List<InventarioExtended> inventarioBloqueadoList;
    private List<TransaccionPermisos> permisosAutorizaList;
    private CantidadRazonada cantidadRazonada;
    private CantidadRazonadaExtended cantidadRazonadaExt;
    private boolean obetenerRegistroWS;
    private String urlSiamFolioReceta;
    
    @Autowired
    private CantidadRazonadaService cantidadRazonadaService;
    
    
    @Autowired
    private InventarioService inventarioService;
    
    @Autowired
    private MedicamentoService medicamentoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private TransaccionService transaccionService;
    
    @Autowired
    private ReportesService reportesService;
    
    @Autowired
    private ClaveProveedorBarrasService claveProveedorBarrasService;
        
    private String archivo;
    private String archivoVale;
    private String rutaPdfs;
    private String pathTmp;
    private String pathTmp2;
    private File dirTmp;
    private String urlf;
    private PdfReader reader1;
    private PdfReader reader2;
    private String msjAlert;
    private String userResponsable;
    private String passResponsable;
    private String nombreCompleto;
    private boolean msjMdlSurtimiento;
    private boolean authorization;
    private boolean authorizado;
    private boolean exist;
    private String idResponsable;
    private String codigoBarrasAutorizte;
    private Integer xcantidadAutorizte;
    private Integer cantidadTotalSurtirByVale;
    private SurtimientoInsumo_Extend surtimientoInsumoItem;
    private Integer auxValorVale;
    private boolean existePacientePepmae;
    private EntidadHospitalaria entHospUser;
    private PacienteDomicilio pacienteDomicilio;
    private PacienteServicio pacienteServicio;
    private Visita visita;
    /**
     * Consulta los permisos del usuario y obtiene lospacientes registrados
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.init()");
        cajasSurtidas = 0;
        cantidadTotalSurtir = 0;       
        xCantidadNueva = 0;
        obetenerRegistroWS = false;
        existePaciente = true;
        auxValorVale = 0;
        puedeSurtirPrescr = false;
        activaModal = false;
        habilitaValidacionVale = false;
        cInsumo = new CensoInsumo();
        cPaciente = new CensoPaciente();
        existePacientePepmae = false;
        procederSurtimiento = true;        
        cantResurt = 0;
        entHospUser = new EntidadHospitalaria();
        //surtimientoInsumo_item = new SurtimientoInsumo_Extend();
        cantidadTotalSurtirByVale = 0;
        regexNumber= Constantes.regexNumber;
        programada = Constantes.PROGRAMADA;
        surtida = Constantes.SURTIDA;
        cancelada = Constantes.CANCELADA;
        listEstatusPrescripcion = new ArrayList<>();
        listEstatusSurtimiento = new ArrayList<>();
        habilitaVales = false;
        listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
        listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
        listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());   
        limpia();         
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.SURTRECETASIAM.getSufijo());        
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        usuarioSelected = sesion.getUsuarioSelected();
        noDiasCaducidad = sesion.getNoDiasCaducidad();
        surtimientoMixto = sesion.isSurtimientoMixto();
        numHorPrevReceta = sesion.getHrsPrevReceta();
        numHorPostReceta = sesion.getHrsPostReceta();
        habilitaVales = sesion.isHabilitaVales();
        valCantidadRazonada = sesion.isCantidadRazonada();
        maxNumDiasResurtible = sesion.getActivaNumMaxDiasResurtible();
        numDiasResurtimiento = sesion.getNoDiasResurtimiento();
        nombreCompleto = (usuarioSelected.getNombre() + " " + usuarioSelected.getApellidoPaterno() + " " + usuarioSelected.getApellidoMaterno()).toUpperCase();
        urlSiamFolioReceta = sesion.getCon_siam_consultaFolioReceta();
        
        
        //consultaPermisosUsuario();
        entHospUser = obtenerEntidadHospUsuario(usuarioSelected.getIdUsuario());
        obtieneServiciosQuePuedeSurtir();
        obtenerSurtimientos();
        obtenerJustificacion();
        dirTmp = new File(Comunes.getPath());
    }

    
    
    private EntidadHospitalaria obtenerEntidadHospUsuario(String idUsuario){
        LOGGER.debug("mx.mc.magedbean.ServicioServicioSurtPrescripcionExtMB.obtenerEntidadHospUsuario()");        
        try {
            entHospUser = entidadHospitalariaService.obtenerEntidadHospByIdUsuaurio(idUsuario);
        } catch (Exception e) {
            LOGGER.error("Error al obtenerEntidadHospUsuario. " + e.getMessage());
        }
        return entHospUser;
    }
    
    /**
     * Obtiene los Servicios que puede surtir el usuario, dependiendo de la
     * estructura jerarquica a la que esté asignada el usuario en sesion
     * Adicionalmente El area a la que el usuario este asignada, debe ser de
     * tipo almacen y que tenga una asignación de servicio hospitalario que
     * puede surtir
     */
    private void obtieneServiciosQuePuedeSurtir() {
        LOGGER.debug("mx.mc.magedbean.ServicioServicioSurtPrescripcionExtMB.obtieneServiciosQuePuedeSurtir()");
        listServiciosQueSurte = new ArrayList<>();
        permiso.setPuedeVer(false);

        Estructura e = null;
        try {
            e = estructuraService.obtener(new Estructura(usuarioSelected.getIdEstructura()));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (e == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.sin.almacen"), null);

        } else if (e.getIdTipoAreaEstructura() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.sin.almacen"), null);

        } else {
            permiso.setPuedeVer(true);
            try {
                    if (usuarioSelected.getIdEstructura() != null) {
                        List<Estructura> estructuraServicio = estructuraService.obtenerServicioQueSurtePorIdEstructura(e.getIdEstructura());
                        for(Estructura servicio : estructuraServicio){
                            listServiciosQueSurte.addAll(estructuraService.obtenerIdsEstructuraJerarquica(servicio.getIdEstructura(), true));
                            listServiciosQueSurte.add(servicio.getIdEstructura());
                        }                        
                    }
            } catch (Exception ex) {
                LOGGER.error("Error al obtener Servicios que puede surtir el usuario: {}", ex.getMessage());
            }
        }

    }

    /**
     * Limpia las variables
     */
    private void limpia() {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.limpia()");       
        elementoSeleccionado = false;
        huboError = false;
        cadenaBusqueda = null;
        fechaActual = new java.util.Date();

        usuarioSelected = new Usuario();
        permisosList = new ArrayList<>();
        usuarioSelected.setPermisosList(permisosList);

        tipoPrescripcion = "";
        tipoPrescripcionSelectedList = new ArrayList<>();

        surtimientoExtendedSelected = new Surtimiento_Extend();

        codigoBarras = null;
        eliminaCodigoBarras = false;
        
        skuSap = new ClaveProveedorBarras_Extend();
        skuSapList = new ArrayList<>();
        xcantidad = 1;
    }

    public void onTabChange(TabChangeEvent evt) {        
        String valorStatus = evt.getTab().getId();
        listEstatusSurtimiento = new ArrayList<>();
        listEstatusPrescripcion = new ArrayList<>();
        if (valorStatus.equalsIgnoreCase(Constantes.PROGRAMADA)) {                        
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());          
            puedeSurtirPrescr = true;
        } else if (valorStatus.equalsIgnoreCase(Constantes.SURTIDA)) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());     
            listEstatusPrescripcion = null;
            puedeSurtirPrescr = false;
        } else if (valorStatus.equalsIgnoreCase(Constantes.CANCELADA)) {
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.CANCELADA.getValue());              
            puedeSurtirPrescr = false;
        }
        obtenerSurtimientos();        
    }
    
    
    public Paciente obtenerPaciente(Surtimiento_Extend surtimientoExtendedSelected){
        paciente = new Paciente();
        paciente.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
        paciente.setNombreCompleto(surtimientoExtendedSelected.getNombrePaciente());
        paciente.setClaveDerechohabiencia(surtimientoExtendedSelected.getClaveDerechohabiencia());
        paciente.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        paciente.setIdEstructura(surtimientoExtendedSelected.getIdEstructura());  // idEstructuraExterna
        paciente = PacienteUtil.crearPaciente(paciente);        
        return paciente;
    }
    
    public List<SurtimientoInsumo_Extend> llenarInsumosRecetaSiam(List<InsumoRecetaSiam> InsumoRecetaSiamList,Date fechaProgramada) throws Exception {
        // falta setear los valores correctos de los que su obtengan en el servicio desde el Cliente.
        for (InsumoRecetaSiam insumoReceta : InsumoRecetaSiamList) {
            
            Medicamento_Extended medicamento_Ext = medicamentoService.obtenerMedicamentoByClaveSiam(insumoReceta.getClaveInsumo());
            if(medicamento_Ext != null){
                SurtimientoInsumo_Extend surtInsumo_exT = new SurtimientoInsumo_Extend();
                surtInsumo_exT.setCantidadEnviada(0);  // supongo que es por defecto
                surtInsumo_exT.setCantidadSolicitada(insumoReceta.getCantidad());
                surtInsumo_exT.setCantidadVale(0);
                surtInsumo_exT.setActivo(true);
                surtInsumo_exT.setCajasSurtidas(BigDecimal.ZERO);
                surtInsumo_exT.setFechaProgramada(fechaProgramada);
                surtInsumo_exT.setInsertFecha(new Date());
                surtInsumo_exT.setFactorTransformacion(String.valueOf(medicamento_Ext.getFactorTransformacion()));
                surtInsumo_exT.setInsertIdUsuario(surtimientoExtendedSelected.getInsertIdUsuario());
                surtInsumo_exT.setIdEstatusSurtimiento(2);

                Integer factorTransfomacion = Integer.valueOf(surtInsumo_exT.getFactorTransformacion());
                double cantSolicitada = surtInsumo_exT.getCantidadSolicitada();
                double cantCajSoli = cantSolicitada / factorTransfomacion;

                if (surtimientoMixto) {
                    cantCajSoli = surtInsumo_exT.getCantidadSolicitada() / Double.valueOf(surtInsumo_exT.getFactorTransformacion());
                    cantCajSoli = Math.round(cantCajSoli * 100) / 100d;

                } else {
                    if (surtInsumo_exT.getCantidadSolicitada() % Double.valueOf(surtInsumo_exT.getFactorTransformacion()) > 0) {
                        cantCajSoli += 1;
                    }
                    cantCajSoli = Math.floor(cantCajSoli);
                    //cantCajSoli = surtInsumo_ext.getCantidadSolicitada() / Double.valueOf(surtInsumo_ext.getFactorTransformacion());
                }
                surtInsumo_exT.setCajasSolicitadas(BigDecimal.valueOf(cantCajSoli));
                surtInsumo_exT.setNumeroMedicacion(1);
                surtInsumo_exT.setClaveInstitucional(medicamento_Ext.getClaveInstitucional()); //insumoReceta.getClaveInsumo()
                surtInsumo_exT.setIdSurtimiento(surtimientoExtendedSelected.getIdSurtimiento());
                surtInsumo_exT.setIdSurtimientoInsumo(Comunes.getUUID());
                surtInsumo_exT.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
                surtInsumo_exT.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
                surtInsumo_exT.setLoteSugerido("LOTE-2020");
                xcantidad = 1;
                surtInsumo_exT.setNombrePresentacion(medicamento_Ext.getNombrePresentacion());

                surtInsumo_exT.setIdPrescripcionInsumo(Comunes.getUUID());
                surtInsumo_exT.setIdInsumo(medicamento_Ext.getIdMedicamento());                
                surtInsumo_exT.setNombreCorto(medicamento_Ext.getNombreCorto());
                surtInsumo_exT.setNombreLargo(medicamento_Ext.getNombreLargo());
                surtimientoInsumoExtendedList.add(surtInsumo_exT);
            }
//            else {
//                Mensaje.showMessage("Error", "No existe el Medicamento", null);
//                break;
//            }                        
        }
        return surtimientoInsumoExtendedList;
    }
   
    public void llenarEncabezadoRecetaSiam(RecetaSiamNO receta,Surtimiento_Extend surtimientoExtendedSelected,Usuario medico,Paciente patien) throws ParseException, Exception{
        //surtimientoExtendedSelected = new Surtimiento_Extend();
        surtimientoExtendedSelected.setFolio(receta.getFolio());
        surtimientoExtendedSelected.setCama("CAM01");
        surtimientoExtendedSelected.setCedProfesional(medico.getCedProfesional());
        surtimientoExtendedSelected.setClaveAgrupada("HD23HK");
        surtimientoExtendedSelected.setEstatusPrescripcion("NORMAL");
        
        SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date fechaProg = datef.parse(receta.getFecha());        
        surtimientoExtendedSelected.setFechaProgramada(fechaProg);
        
        surtimientoExtendedSelected.setNombreEstructura("CONSULTA EXTERNA");
        if(patien.getPacienteNumero() != null){  // Si no existe o es nulo se agregara den la transaccion
            surtimientoExtendedSelected.setPacienteNumero(patien.getPacienteNumero());
        }
        
        surtimientoExtendedSelected.setIdPrescripcion(Comunes.getUUID());
        String cadenaFolio = formatoCadena(new Date(), "mmss");
        surtimientoExtendedSelected.setFolio(surtimientoExtendedSelected.getFolio().concat(cadenaFolio));
        surtimientoExtendedSelected.setFolioPrescripcion("1803362273042R");
        String valorPresc = formatoCadena(new Date(), "mmss");
        surtimientoExtendedSelected.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion().concat(valorPresc));
        surtimientoExtendedSelected.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
        surtimientoExtendedSelected.setInsertIdUsuario(usuarioSelected.getIdUsuario());
        surtimientoExtendedSelected.setInsertFecha(new Date());
        surtimientoExtendedSelected.setClaveDerechohabiencia(patien.getClaveDerechohabiencia());
        surtimientoExtendedSelected.setIdSurtimiento(Comunes.getUUID());
        surtimientoExtendedSelected.setIdEstructura("655f4343-c03b-4204-bcf6-48b06618b42a"); // solo es de prueba el IdEstructura
        surtimientoExtendedSelected.setNombreMedico(receta.getNombreMedico());
        surtimientoExtendedSelected.setNombrePaciente(receta.getNombreCompletoDh());
        surtimientoExtendedSelected.setTipoConsulta(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue());        
        llenarInsumosRecetaSiam(receta.getInsumoRecetaSiamList(),fechaProg);
        
        
        
    }
    
    
    public void crearObjetosPaciente(){
        existePaciente = false;
        paciente = obtenerPaciente(surtimientoExtendedSelected);
        if (paciente != null) {
            pacienteDomicilio = new PacienteDomicilio();
            pacienteDomicilio = PacienteDomicilioUtil.crearPacienteDomicilioGenerico(paciente);
            if (pacienteDomicilio != null) {
                visita = new Visita();
                visita = VisitaUtil.crearVisita(paciente);
                if (visita != null) {
                    pacienteServicio = new PacienteServicio();
                    pacienteServicio = PacienteServicioUtil.crearPacienteServicio(paciente, visita);
                }
            }
        }
    }

    private boolean verificaPacientePepmae(Paciente paciente) {
        if (paciente == null) {
            existePacientePepmae = false;
            crearObjetosPaciente();
        } else {
            cPaciente = new CensoPaciente();
            cPaciente.setIdPaciente(paciente.getIdPaciente());
            try {
                cPaciente = censoPacienteService.obtener(cPaciente);
                if (cPaciente != null) {
                    existePacientePepmae = true;
                }
            } catch (Exception e) {
                LOGGER.error("" + e.getMessage());
            }
        }
        return existePacientePepmae;
    }

    
//    private Paciente obtenerPacienteNuevo(RecetaSiamNO receta){
//        Paciente pacienteMus = new Paciente();
//        pacienteMus.setNombreCompleto(receta.getNombreCompletoDh());
//        pacienteMus.setRfc(receta.getRfcDh());
//        pacienteMus.setCurp(receta.getCurpDh());
//        pacienteMus.setIdEstructura("655f4343-c03b-4204-bcf6-48b06618b42a");
//        // El pacienteNumero se generará hasta la transacción
//        pacienteMus = PacienteUtil.crearPaciente(pacienteMus);
//        return pacienteMus;
//    }
    
    
    public void obtenerRecetaSiamWS() throws JsonProcessingException, IOException, Exception {
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;
        obetenerRegistroWS = true;
        puedeSurtirPrescr = true;
        surtimientoExtendedSelected = new Surtimiento_Extend();
        surtimientoInsumoExtendedList = new ArrayList<>();
        try {
            // por ahora para seguir con algunas validaciones      
            if (permiso.isPuedeVer() && permiso.isPuedeCrear()) {
                if (cadenaBusqueda != null && !cadenaBusqueda.isEmpty()) {
                    if (urlSiamFolioReceta != null) {
                        // TODO: No1: agregar la validación de PuedeCrear o PuedeVer
                        RecetaSiamNO receta = RecetaSiamCL.getRecetaSiam(this.cadenaBusqueda, entHospUser.getClaveEntidad(),urlSiamFolioReceta);
                        if (receta != null) {
                            //agrega validacion que a la receta esta pendiente por surtir, por ahora es prueba
                            if (receta.getEstatus().contains("S")) {
                                // Verificamos si existe el doctor por medio de su matriculaPersonal, de lo contrario lo ingresamos a MUS                                
                                Usuario medico = usuarioService.obtenerUsuarioByMatriculaPersonal(receta.getClaveMedico());
                                if (medico == null) {
                                    // aqui es donde agregamos al medio en caso de no existir en MUS
                                    medico = new Usuario();
                                    medico.setNombre(receta.getNombreMedico());
                                    medico.setNombreUsuario(receta.getNombreMedico());
                                    medico.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    medico = MedicoUtil.crearUsuario(medico);
                                }
                                //paciente = pacienteService.obtenerPacienteByNumeroPaciente(surtimientoExtendedSelected.getPacienteNumero());
                                paciente = pacienteService.obtenerPacienteByRfcCvDehCurp(receta.getRfcDh(), receta.getNumIsssteDh(), receta.getCurpDh());                                
                                // generamos al objeto Paciente
                                //paciente = obtenerPacienteNuevo(receta);
                                existePacientePepmae = verificaPacientePepmae(paciente);                                
                                llenarEncabezadoRecetaSiam(receta, surtimientoExtendedSelected,medico,paciente);
                                
                                
                                //Obtenemos el dato de bloque ya que lo necesitamos
                                buscaMedicmanetosBloqueados();
                                status = Constantes.ACTIVO;
                                modal = Constantes.ACTIVO;
                            } else {
                                Mensaje.showMessage("Error", "El surtimiento ya fue Surtido",null);
                            }
                        } else {
                            Mensaje.showMessage("Error", "No se encontró ninguna receta con el folio ", this.cadenaBusqueda);
                        }
                    } else {
                        Mensaje.showMessage("Error", "Hubo algun error al consultar la Url de Siam en MUS",null);
                    }
                } else {
                    Mensaje.showMessage("Error", "Ingrese un Folio",null);
                }
            } else {
                Mensaje.showMessage("Error", RESOURCES.getString("estr.err.permisos"),null);
            }
        } catch (JsonGenerationException | JsonMappingException e) {
            Mensaje.showMessage("Error", "Error en obtenerRecetaSiamWS",null);
            LOGGER.error("Error al obtenerRecetaSiamWS{}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }


    /**
     * Obtiene la lista de pacientes registrados
     */
    public void obtenerSurtimientos() {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.obtenerSurtimientos()");     
        boolean status = Constantes.INACTIVO;
        surtimientoExtendedList = new ArrayList<>();

        if (!this.permiso.isPuedeVer()) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.transaccion"), null);
            status = Constantes.ACTIVO;

        } else if (usuarioSelected == null || usuarioSelected.getIdEstructura() == null || usuarioSelected.getIdEstructura().isEmpty()) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.sin.almacen"), null);
            status = Constantes.ACTIVO;

        } else {
            try {
                if (cadenaBusqueda != null) {
                    if (cadenaBusqueda.trim().isEmpty()) {
                        cadenaBusqueda = null;
                    }
                }
                if (tipoPrescripcionSelectedList != null) {
                    if (tipoPrescripcionSelectedList.isEmpty()) {
                        tipoPrescripcionSelectedList = null;
                    }
                }

                if (listServiciosQueSurte.isEmpty()) {
                    this.listServiciosQueSurte = null;
                }
// regla: listar prescripciones con fecha igual o menor a la fecha actual, nunca prescripciones futuras
                Date fechaProgramada = new java.util.Date();

                // TODO:    agregar reglas de Negocio
                // 1.- El surtimiento solo funciona en Almacenes (CEDIME, CEDIMAT, Farmacia Externa) y subalmacenes (Gabinetes o sin sistema)
                // 2.- Solo surten los insumos que tienen activos
                // 3.- CEDIME y CEDIMAT solo surten a Consulta Interna

                servSurtPrescripcionExtLazy = new ServSurtPrescripcionExtLazy(surtimientoService, fechaProgramada, cadenaBusqueda, tipoPrescripcionSelectedList,
                        numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);

                LOGGER.debug("Resultados: {}", servSurtPrescripcionExtLazy.getTotalReg());
                
                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("prc.pac.lista"), ex);
                Mensaje.showMessage("Error", RESOURCES.getString("prc.pac.lista"), null);
            }
        }
        cadenaBusqueda="";
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Obtener listado de Justificación
     */
    private void obtenerJustificacion() {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.obtenerJustificacion()");
        justificacionList = new ArrayList<>();
        try {
            boolean activa = Constantes.ACTIVO;
            List<Integer> listTipoJustificacion = null;
            justificacionList.addAll(tipoJustificacionService.obtenerActivosPorListId(activa, listTipoJustificacion));
        } catch (Exception ex) {
            LOGGER.error("Error en obtenerJustificacion: {}", ex.getMessage());
        }
    }

    public void onRowSelectSurtimiento(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectSurtimiento()");
        surtimientoExtendedSelected = (Surtimiento_Extend) event.getObject();
        if (surtimientoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectSurtimiento(UnselectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectSurtimiento()");
        surtimientoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSurtimiento() {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.verSurtimiento()");
        numResurtimiento = 0;
        boolean status = Constantes.INACTIVO;
        boolean modal = Constantes.INACTIVO;

        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.transaccion"), null);

        } else if (usuarioSelected == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.sin.almacen"), null);

        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);

        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);

        } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);

        } else {

            try {

                if (surtimientoExtendedSelected.getResurtimiento() != null) {
                    if (surtimientoExtendedSelected.getResurtimiento() > 0) {
                        Surtimiento_Extend surtExt = surtimientoExtendedSelected;
                        procederSurtimiento = validarResurtimiento(surtExt);
                    }
                }

                Date fechaProgramada = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                List<Integer> listEstatusSurtimiento = new ArrayList<>();
                //Este valor es cuando querramos mostrarlos en tabs
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.CANCELADO.getValue());
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.SUSPENDIDO.getValue());
                List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SURTIDO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.CANCELADO.getValue());
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.SUSPENDIDO.getValue());
                String idEstructura = usuarioSelected.getIdEstructura();

                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoInsumoExtendedList
                        .addAll(
                                surtimientoInsumoService
                                        .obtenerSurtimientosProgramadosExt(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                        );
//                      TODO: buscar forma de optimizar sin ciclo
                surtimientoInsumoExtendedList.forEach(item -> {
                    double noCajas = 0;
                    Integer factorTransfomacion = Integer.valueOf(item.getFactorTransformacion());
                    double cantSolicitada = item.getCantidadSolicitada();
                    noCajas = cantSolicitada / factorTransfomacion;
                    
                    if(!surtimientoMixto){
                        if (cantSolicitada % factorTransfomacion > 0) {
                            noCajas += 1;
                        }
                        noCajas = Math.floor(noCajas);
                    }
                    noCajas = Math.round(noCajas * 100) / 100d;
                    item.setCajasSolicitadas(BigDecimal.valueOf(noCajas).movePointLeft(0));
                    item.setCantidadVale(0);
                    
                    // para sacar las cajasSurtidas
                    double canEnviada = item.getCantidadEnviada();
                    double cantCajSurt = (canEnviada / factorTransfomacion);
                    cantCajSurt = Math.round(cantCajSurt * 100) / 100d;
                    item.setCajasSurtidas(BigDecimal.valueOf(cantCajSurt));
                    
                    int totalVale = (item.getTotalVale() * factorTransfomacion);
                    item.setCantidadEnviada(item.getTotalEnviado() + totalVale + item.getCantidadEnviada());
                });
                if (!surtimientoInsumoExtendedList.isEmpty()) {
                    buscaMedicmanetosBloqueados();
                }

                status = Constantes.ACTIVO;
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("sur.incorrecto"), ex);
                Mensaje.showMessage("Error", RESOURCES.getString("sur.incorrecto"), null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    /**
     * Muestra el Detalle de cada surtimiento de cada prescripción
     */
    public void verSurtimientoEscaneado() {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.verSurtimiento()");

        boolean modal = Constantes.ACTIVO;
        
        try {
            List<Integer> listEstatusPrescripcion = new ArrayList<>();
            listEstatusPrescripcion.add(EstatusPrescripcion_Enum.PROGRAMADA.getValue());
            List<Integer> listEstatusSurtimiento = new ArrayList<>();
            listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());            

            //List<Surtimiento_Extend> listaSurtimientos = new ArrayList<>();
                    /*surtimientoService
                    .obtenerPorFechaEstructuraPacientePrescripcionExt(
                            new Date(), cadenaBusqueda,
       
                            numHorPrevReceta,numHorPostReceta, listEstatusPrescripcion,
                                                 tipoPrescripcionSelectedList,listEstatusSurtimiento, listServiciosQueSurte);
            */
            Date fechaProgramada = new java.util.Date();
            servSurtPrescripcionExtLazy = new ServSurtPrescripcionExtLazy(surtimientoService, fechaProgramada, cadenaBusqueda, tipoPrescripcionSelectedList,
                        numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);            
            
            surtimientoExtendedSelected = null;
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sur.incorrecto"), ex);
            Mensaje.showMessage("Error", RESOURCES.getString("sur.incorrecto"), null);
        }
        
        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage("Error", RESOURCES.getString("prescripcionExt.err.noregistro"), null);

        } else if (usuarioSelected == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.sin.almacen"), null);
            
        } else if (usuarioSelected.getIdEstructura() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.sin.almacen"), null);
        } else if (surtimientoExtendedSelected == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);
            modal = Constantes.INACTIVO;
        } else if (surtimientoExtendedSelected.getIdPrescripcion() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);

        } else if (surtimientoExtendedSelected.getIdSurtimiento() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);

        } else if (surtimientoExtendedSelected.getIdEstatusSurtimiento() == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.registro.incorrecto"), null);

        } else {

            try {
                Date fechaProgramada = new java.util.Date();
                String idSurtimiento = surtimientoExtendedSelected.getIdSurtimiento();
                String idPrescripcion = surtimientoExtendedSelected.getIdPrescripcion();
                List<Integer> listEstatusSurtimiento = new ArrayList<>();
                listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
                listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
                String idEstructura = usuarioSelected.getIdEstructura();

                surtimientoInsumoExtendedList = new ArrayList<>();
                surtimientoInsumoExtendedList
                        .addAll(
                                surtimientoInsumoService
                                        .obtenerSurtimientosProgramados(fechaProgramada, idSurtimiento, idPrescripcion, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, surtimientoMixto)
                        );
// TODO: optimizar cantidadcajas sin ciclo
//                for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
//                    Integer noCajas = 0;
//                    Integer factorTransfomacion = Integer.valueOf(item.getFactorTransformacion());
//                    Integer cantSolicitada = item.getCantidadSolicitada();
//                    noCajas = cantSolicitada / factorTransfomacion;
//                    if (cantSolicitada % factorTransfomacion > 0) {
//                        noCajas += 1;
//                    }
//                    item.setCajasSolicitadas(noCajas);
//                }
                if (!surtimientoInsumoExtendedList.isEmpty()) {
                    buscaMedicmanetosBloqueados();
                }
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString("sur.incorrecto"), ex);
                Mensaje.showMessage("Error", RESOURCES.getString("sur.incorrecto"), null);
            }
        }

        PrimeFaces.current().ajax().addCallbackParam("modal", modal);
    }

    /**
     * Busca si algun medicamento de los generados en la prescripción tiene
     * algún Lote Bloqueado
     */
    private void buscaMedicmanetosBloqueados() {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.buscaMedicmanetosBloqueados()");
        try {
            List<String> idInsumoList = new ArrayList<>();
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                idInsumoList.add(item.getIdInsumo());
            }
            inventarioBloqueadoList = inventarioService.obtenerListaInactivosByIdInsumos(idInsumoList);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

    }

    private boolean validaPepmae(Inventario inv) {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.validaPepmae()");
        boolean isPepmae = false;
        try {
            if (existePacientePepmae) {   
                cInsumo = new CensoInsumo();
                cInsumo.setIdCensoPaciente(cPaciente.getIdCensoPaciente());
                cInsumo.setIdMedicamento(inv.getIdInsumo());
                cInsumo = censoInsumoService.obtenerCensoInsumo(cInsumo);
                //TODO : Diagnostico combo editabe dentro de presc. Ext
                if (cInsumo != null) {                        
                    isPepmae = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return isPepmae;
    }
    
    /**
     * Lee el codigo de barras de un medicamento y confirma la cantidad escaneda
     * para enviarse en el surtimento de prescripción
     * @param e
     */
    public void validaLecturaPorCodigo(SelectEvent e) {
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.validaLecturaPorCodigo()");
        skuSap = (ClaveProveedorBarras_Extend) e.getObject();
        Medicamento medicamento;
        activaModal = false;
        Inventario inv;        
        boolean status = true;
        authorization = false;        
        try {                        
            String idInventario = skuSap.getIdInventario();
            inv = inventarioService.obtener(new Inventario(idInventario));            
            if (inv != null) {
                medicamento = medicamentoService.obtenerMedicamento(inv.getIdInsumo());
                if (medicamento != null) {
                    if (inv.getIdInsumo() != null) {
                        codigoBarras = CodigoBarras.generaCodigoDeBarras(medicamento.getClaveInstitucional(), inv.getLote(), inv.getFechaCaducidad(), inv.getCantidadXCaja());
                    }
                }
            }
            
            boolean pepmae = validaPepmae(inv);
            
            if (codigoBarras == null) {
                return;

            } else if (codigoBarras.trim().isEmpty()) {
                return;

            } else if (surtimientoExtendedSelected == null) {
                Mensaje.showMessage("Error", RESOURCES.getString("sur.incorrecto"), null);

            } else if (surtimientoInsumoExtendedList == null) {
                Mensaje.showMessage("Error", RESOURCES.getString("sur.incorrecto"), null);

            } else if (surtimientoInsumoExtendedList.isEmpty()) {
                Mensaje.showMessage("Error", RESOURCES.getString("sur.incorrecto"), null);

            } else if (eliminaCodigoBarras) {
                status = eliminarLotePorCodigo();
            } else {
                if (pepmae) {
                    status = agregarLotePorCodigoPepmae();
                } else {
                    status = agregarLotePorCodigo();
                }
            }
            codigoBarras = "";
            xcantidad = 1;
            eliminaCodigoBarras = false;
            if(activaModal){
                status = true;
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    private boolean validarCantRazonadaVales(SurtimientoInsumo_Extend items, int cantidadTotalSurtirByVale) throws Exception{
        boolean res = Constantes.INACTIVO;
        habilitaValidacionVale = true;        
        cantidadRazonada = cantidadRazonadaService.cantidadRazonadaInsumo(items.getClaveInstitucional());
        // Se verifica que el parametro de cantidad razonada este activo
        if (cantidadRazonada != null && !authorization && valCantidadRazonada) {
            Paciente patient = new Paciente();
            patient.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
            paciente = pacienteService.obtener(patient);
            int diasRestantes = 0;
            String ultimoSurtimiento = "";

            cantidadRazonadaExt = cantidadRazonadaService.cantidadRazonadaInsumoPacienteExt(paciente.getIdPaciente(), items.getIdInsumo());
            if (cantidadRazonadaExt != null) {
                diasRestantes = cantidadRazonadaExt.getDiasRestantes();
                
                // En esta linea de código estamos indicando el nuevo formato que queremos para nuestra fecha.
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                // Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
                ultimoSurtimiento = formatter.format(cantidadRazonadaExt.getUltimoSurtimiento());
                                
            }
            
            //Consulta Externa
            if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue())) {
                cajasSurtidas = cantidadTotalSurtirByVale;
                if (cajasSurtidas > cantidadRazonada.getCantidadPresentacionComercial()) {
                    msjMdlSurtimiento = false;
                    msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/> <b>"
                            + items.getClaveInstitucional() + "</b> El Medicamento solo puede surtirse cada  <b>" + cantidadRazonada.getPeriodoPresentacionComercial() + "</b> días, faltan  <b>" + diasRestantes + "</b>, ultimo surtimiento: <b>" + ultimoSurtimiento + "</b>";

                    xcantidadAutorizte = items.getTotalVale();
                    codigoBarras = "";
                    return false;
                } else {
                    authorization = true;
                }
            }
        } else {
            authorization = true;
        }
        if(authorization){
            res = authorization;
        }

        return res;
    
    }
    
    private boolean mostrarModalPorCantidadRazonada(boolean authorization,Integer cantidadEnviada,CodigoInsumo ci,
            int totalDia,Integer cantidadEscaneada,int factorTransform, int totalMes,int diasRestantes, String ultimoSurtimiento) {

        //Consulta Interna o si el valor del inventario del objeto ci es igual a "1", el cual hace referencia a Unidosis
        if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_INTERNA.getValue()) || ci.getCantidad() == 1) {
            cantidadEnviada = cantidadEnviada + totalDia;
            if (cantidadEnviada > cantidadRazonada.getCantidadDosisUnitaria()) {
                exist = false;
                msjMdlSurtimiento = false;
                msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/> <b>"
                        + ci.getClave() + "</b> La cantidad del Medicamento debe ser menor o igual a <b>" + cantidadRazonada.getCantidadDosisUnitaria() + "</b>, se solicita <b>" + cantidadEnviada + "</b>";

                xcantidadAutorizte = cantidadEscaneada;
                codigoBarras = "";
                //medicamento = new Medicamento_Extended();
                return false;
            } else {
                authorization = true;
            }
            //Consulta Externa
        } else if (surtimientoExtendedSelected.getTipoConsulta().equals(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue())) {
            //cajasSurtidas = cajasSurtidas + totalMes;
            cajasSurtidas = (cantidadEnviada / factorTransform) + (totalMes / factorTransform);
            if (cajasSurtidas > cantidadRazonada.getCantidadPresentacionComercial()) {
                exist = false;
                msjMdlSurtimiento = false;
                msjAlert = "El siguiente Medicamento debe ser Autorizado para surtir la receta<br/> <b>"
                        + ci.getClave() + "</b> El Medicamento solo puede surtirse cada  <b>" + cantidadRazonada.getPeriodoPresentacionComercial() + "</b> días, faltan  <b>" + diasRestantes + "</b>, ultimo surtimiento: <b>" + ultimoSurtimiento + "</b>";

                xcantidadAutorizte = (cantidadEscaneada / factorTransform);
                codigoBarras = "";
                //medicamento = new Medicamento_Extended();
                return false;
            } else {
                authorization = true;
            }
        }
        return authorization;
    }
    
    private List<SurtimientoEnviado_Extend> getSurtimientoEnviadoExtList(List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList,CodigoInsumo ci,SurtimientoInsumo_Extend item,Inventario inventarioPorSurtir,int cantidadEscaneada){
        SurtimientoEnviado_Extend surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
        surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
        surtimientoEnviadoExtend.setIdSurtimientoInsumo(item.getIdSurtimientoInsumo());
        surtimientoEnviadoExtend.setLote(ci.getLote());
        surtimientoEnviadoExtend.setCaducidad(ci.getFecha());
        surtimientoEnviadoExtend.setCantidadEnviado(cantidadEscaneada);
        surtimientoEnviadoExtend.setIdInventarioSurtido(inventarioPorSurtir.getIdInventario());
        surtimientoEnviadoExtend.setFactorTransformacion(inventarioPorSurtir.getCantidadXCaja());
        surtimientoEnviadoExtend.setClaveProveedor(inventarioPorSurtir.getClaveProveedor());
        surtimientoEnviadoExtendList.add(surtimientoEnviadoExtend);
         return surtimientoEnviadoExtendList;
    }
    
    private boolean agregarLotePorCodigoPepmae() throws Exception {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.agregarLotePorCodigoPepmae()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;        
        habilitaValidacionVale = false;
        int cantidadPepmae = 0;
   
        CodigoInsumo ci = parsearCodigoDeBarrasConCantidad(codigoBarras);
        if (ci == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.parser"),null);

        }else if(ci.getCantidad().equals(1)){
            Mensaje.showMessage("Error", "En Pepmae Solo se pueden Surtir Cajas",null);
            activaModal = true;
        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.caducidadvencida"),null);

        } else {

            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;            
            Integer cantidadEscaneada = 0;
            Integer cantidadEnviada;
            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                
// regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                if (item.getClaveInstitucional().contains(ci.getClave())) {
                    encontrado = Constantes.ACTIVO;
   // Regla: si con censoPepmae con su cantidadMinima = minimo/maximo
                    CensoInsumoDetalleExtended cenInsDetalleExt = censoInsumoDetalleService.getCensoInsumoDetalleByMedicamentoCensoInsumo(cInsumo.getIdCensoInsumo(), cInsumo.getIdMedicamento(), 1);
                    if (cenInsDetalleExt != null) {
                        cantidadPepmae = cenInsDetalleExt.getCantidadMinima() == 0 ? cenInsDetalleExt.getMaximo() : cenInsDetalleExt.getMinimo();
                    }
                    if (cenInsDetalleExt == null) {
                        activaModal = true;
                        Mensaje.showMessage("Error", "No se encontro dicho Surtimiento en Pepmae",null);
                    } // regla: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                    else if (!item.isMedicamentoActivo()) {
                        Mensaje.showMessage("Error", RESOURCES.getString("sur.clavebloqueada"),null);
// regla: factor multiplicador debe ser 1 o mayor
                    } else if (xcantidad != null && xcantidad < 1) {
                        Mensaje.showMessage("Error", RESOURCES.getString("sur.cantidadincorrecta"),null);

                    }else {
                        boolean bloqueado = true;
                        if (inventarioBloqueadoList != null) {
                            List<Inventario> invList = inventarioBloqueadoList.stream()
                                    .filter(p -> p.getIdInsumo().equals(item.getIdInsumo()) && p.getLote().equals(ci.getClave()))
                                    .collect(Collectors.toList());
                            bloqueado = (invList == null || invList.isEmpty()) ? Constantes.INACTIVO : Constantes.ACTIVO;
                        }

                        xcantidad = (xcantidad != null) ? xcantidad : 1;                                          
                        cantidadEscaneada = ci.getCantidad() * xcantidad;
                        cantidadEnviada = (item.getCantidadEnviada() != null) ? item.getCantidadEnviada() : 0;
                        cantidadEnviada = cantidadEnviada + cantidadEscaneada;
                        int cajasSolicitadas = item.getCajasSolicitadas().intValue();
                        int factorTransform = Integer.valueOf(item.getFactorTransformacion());
                        cajasSurtidas = (cantidadEnviada / factorTransform);
                        
                        // para sacar las cajasSurtidas
                        double canEnviada = cantidadEnviada;
                        double cantCajSurt = (canEnviada / factorTransform);
                        cantCajSurt = Math.round(cantCajSurt * 100) / 100d;
                        
                                                
                        if (habilitaVales) {                            
                                cantidadTotalSurtir = cajasSurtidas + item.getCantidadVale();                                                        
                        }
                        
// regla: solo escanea medicamentos si no esta bloqueado a nivel Lote en el inventario
                        if (bloqueado) {
                            Mensaje.showMessage("Error", RESOURCES.getString("sur.lotebloqueado"),null);

// regla: No puedes surtir mas de la cantidadPepmae
                        }else if(cajasSurtidas > cantidadPepmae){
                            Mensaje.showMessage("Error", "No puedes Sutir mas de la Cantidad Pepmae",null);
                            activaModal = true;
// regla: no puede surtir mas medicamento que el solicitado
                        } else if (cantidadEnviada > item.getCantidadSolicitada() && Constantes.PARAMETRO_SURTIR_SOLO_SOLICITADO_MAX ) {
                            Mensaje.showMessage("Error", RESOURCES.getString("sur.exedido"),null);

                        } else if ( cajasSurtidas > cajasSolicitadas ) {                            
                            Mensaje.showMessage("Error", RESOURCES.getString("prc.cajas.exedida"),null);
                            activaModal = true;
// regla nueva: Si la cantidadtotal es mayor a lo solicitado, referente a la suma con los vales y lo surtido.
                        } else if( cantidadTotalSurtir > cajasSolicitadas ){
                            Mensaje.showMessage("Error", RESOURCES.getString("prc.cajas.exedida"),null);
                        } else if (surtimientoMixto && cantidadEnviada > item.getCantidadSolicitada()){
                            Mensaje.showMessage("Error", "Exediste la cantidad de Unidosis Solicitada",null);
                            activaModal = true;
                        } else {
                            surtimientoEnviadoExtendList = new ArrayList<>();
                            if (item.getSurtimientoEnviadoExtendList() != null) {
                                surtimientoEnviadoExtendList.addAll(item.getSurtimientoEnviadoExtendList());
                            }
                            Inventario inventarioPorSurtir = null;
                            String claveProveedor = null;
                            try {
                                inventarioPorSurtir = inventarioService
                                        .obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
                                                item.getIdInsumo()
                                                , surtimientoExtendedSelected.getIdEstructuraAlmacen()
                                                , ci.getLote()
                                                , ci.getCantidad()
                                                , claveProveedor );
                            } catch (Exception ex) {
                                LOGGER.error(RESOURCES.getString("sur.loteincorrecto") + ex.getMessage());
                            }
                            if (inventarioPorSurtir == null) {
                                Mensaje.showMessage("Error", RESOURCES.getString("sur.loteincorrecto"),null);

                            } else if (inventarioPorSurtir.getActivo() == 0) {
                                Mensaje.showMessage("Error", RESOURCES.getString("sur.lotebloqueado"),null);
                                codigoBarras = "";
                            } else if (inventarioPorSurtir.getCantidadActual() < cantidadEscaneada) {
                                Mensaje.showMessage("Error", RESOURCES.getString("sur.cantidadinsuficiente"),null);
                                codigoBarras = "";
                            } else {
                                //res = Constantes.INACTIVO;

                                if (surtimientoEnviadoExtendList.isEmpty()) {
                                    surtimientoEnviadoExtendList = getSurtimientoEnviadoExtList(surtimientoEnviadoExtendList, ci, item, inventarioPorSurtir, cantidadEscaneada);
                                } else {
                                    boolean bandera = false;
                                    for (SurtimientoEnviado_Extend lote : surtimientoEnviadoExtendList) {
                                        bandera = false;
                                        // regla: si se pistolea mas de un medicmento y con el mismo lote se agrupan por lotes sumarizando las cantidades
                                        if (lote.getLote().equals(ci.getLote()) && lote.getCaducidad().equals(ci.getFecha())) {
                                            lote.setCantidadEnviado(lote.getCantidadEnviado() + cantidadEscaneada);
                                            break;

                                        } else {
                                            bandera = true;
                                        }
                                    }
                                    if (bandera) {
                                        // regla: si es el único Lote pistoleado solo muestra una linea en subdetalle                                           
                                        surtimientoEnviadoExtendList = getSurtimientoEnviadoExtList(surtimientoEnviadoExtendList, ci, item, inventarioPorSurtir, cantidadEscaneada);
                                    }
                                }                                                                   
//                                TODO: en 
//                                Integer temp = cantidadEnviada/ Integer.parseInt( item.getFactorTransformacion() );
                                    item.setCantidadEnviada(cantidadEnviada);
                                    item.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                    item.setCajasSurtidas(BigDecimal.valueOf(cantCajSurt));
                                    res = Constantes.ACTIVO;
                                    xcantidad = 1;
                                
                            }                           
                        }
                    }
                }
            }
            codigoBarras = "";
        }
        if (!encontrado) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.claveincorrecta"),null);
        }
        return res;
    }   
    
    /**
     * Agrega un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean agregarLotePorCodigo() throws Exception {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.agregarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;        
        habilitaValidacionVale = false;
        //Respaldamos el valor de codigoBarras, por si se requiere Autorizacioon
        codigoBarrasAutorizte = codigoBarras;     
        if(authorizado){
            if(surtimientoMixto){
                xcantidad = xCantidadNueva;
            }            
        }

        CodigoInsumo ci = parsearCodigoDeBarrasConCantidad(codigoBarras);
        if (ci == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.parser"), null);

        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.caducidadvencida"), null);

        } else {

            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;            
            Integer cantidadEscaneada = 0;
            Integer cantidadEnviada;
            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
// regla: solo escanea medicamentos si la clave escaneada existe en el detalle solicitado
                if (item.getClaveInstitucional().contains(ci.getClave())) {
                    encontrado = Constantes.ACTIVO;
// regla: solo escanea medicamentos si no esta bloqueado a nivel catálogo
                    if (!item.isMedicamentoActivo()) {
                        Mensaje.showMessage("Error", RESOURCES.getString("sur.clavebloqueada"), null);

// regla: factor multiplicador debe ser 1 o mayor
                    } else if (xcantidad != null && xcantidad < 1) {
                        Mensaje.showMessage("Error", RESOURCES.getString("sur.cantidadincorrecta"), null);

                    } else {
                        boolean bloqueado = true;
                        if (inventarioBloqueadoList != null) {
                            List<Inventario> invList = inventarioBloqueadoList.stream()
                                    .filter(p -> p.getIdInsumo().equals(item.getIdInsumo()) && p.getLote().equals(ci.getClave()))
                                    .collect(Collectors.toList());
                            bloqueado = (invList == null || invList.isEmpty()) ? Constantes.INACTIVO : Constantes.ACTIVO;
                        }

                        xcantidad = (xcantidad != null) ? xcantidad : 1;                                          
                        cantidadEscaneada = ci.getCantidad() * xcantidad;
                        cantidadEnviada = (item.getCantidadEnviada() != null) ? item.getCantidadEnviada() : 0;
                        cantidadEnviada = cantidadEnviada + cantidadEscaneada;
                        int cajasSolicitadas = item.getCajasSolicitadas().intValue();
                        int factorTransform = Integer.valueOf(item.getFactorTransformacion());
                        cajasSurtidas = (cantidadEnviada / factorTransform);
                        
                        // para sacar las cajasSurtidas
                        double canEnviada = cantidadEnviada;
                        double cantCajSurt = (canEnviada / factorTransform);
                        cantCajSurt = Math.round(cantCajSurt * 100) / 100d;
                        
                                                
                        if (habilitaVales) {                            
                                cantidadTotalSurtir = cajasSurtidas + item.getCantidadVale();                                                        
                        }
                        
// regla: solo escanea medicamentos si no esta bloqueado a nivel Lote en el inventario
                        if (bloqueado) {
                            Mensaje.showMessage("Error", RESOURCES.getString("sur.lotebloqueado"), null);

// regla: no puede surtir mas medicamento que el solicitado
                        } else if (cantidadEnviada > item.getCantidadSolicitada() && Constantes.PARAMETRO_SURTIR_SOLO_SOLICITADO_MAX ) {
                            Mensaje.showMessage("Error", RESOURCES.getString("sur.exedido"), null);

                        } else if ( cajasSurtidas > cajasSolicitadas ) {                            
                            Mensaje.showMessage("Error", RESOURCES.getString("prc.cajas.exedida"), null);
                            activaModal = true;
// regla nueva: Si la cantidadtotal es mayor a lo solicitado, referente a la suma con los vales y lo surtido.
                        } else if( cantidadTotalSurtir > cajasSolicitadas ){
                            Mensaje.showMessage("Error", RESOURCES.getString("prc.cajas.exedida"), null);
                        } else if (surtimientoMixto && cantidadEnviada > item.getCantidadSolicitada()){
                            Mensaje.showMessage("Error", "Exediste la cantidad de Unidosis Solicitada", null);
                            activaModal = true;
                        } else {
                            surtimientoEnviadoExtendList = new ArrayList<>();
                            if (item.getSurtimientoEnviadoExtendList() != null) {
                                surtimientoEnviadoExtendList.addAll(item.getSurtimientoEnviadoExtendList());
                            }
                            Inventario inventarioPorSurtir = null;
                            String claveProveedor = null;
                            try {
                                inventarioPorSurtir = inventarioService
                                        .obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
                                                item.getIdInsumo()
                                                , surtimientoExtendedSelected.getIdEstructuraAlmacen()
                                                , ci.getLote()
                                                , ci.getCantidad()
                                                , claveProveedor );
                            } catch (Exception ex) {
                                LOGGER.error(RESOURCES.getString("sur.loteincorrecto"), ex);
                            }
                            if (inventarioPorSurtir == null) {
                                Mensaje.showMessage("Error", RESOURCES.getString("sur.loteincorrecto"), null);

                            } else if (inventarioPorSurtir.getActivo() == 0) {
                                Mensaje.showMessage("Error", RESOURCES.getString("sur.lotebloqueado"), null);
                                codigoBarras = "";
                            } else if (inventarioPorSurtir.getCantidadActual() < cantidadEscaneada) {
                                Mensaje.showMessage("Error", RESOURCES.getString("sur.cantidadinsuficiente"), null);
                                codigoBarras = "";
                            } else {
                                res = Constantes.INACTIVO;
                                cantidadRazonada = cantidadRazonadaService.cantidadRazonadaInsumo(ci.getClave());
                                // Se verifica que el parametro de cantidad razonada este activo                                
                                if (cantidadRazonada != null && !authorization && valCantidadRazonada) {
                                    if(!obetenerRegistroWS){
                                        Paciente patient = new Paciente();
                                        patient.setPacienteNumero(surtimientoExtendedSelected.getPacienteNumero());
                                        paciente = pacienteService.obtener(patient);
                                    }                                    
                                    xCantidadNueva = xcantidad;
                                    int totalDia = 0;
                                    int totalMes = 0;
                                    int diasRestantes = 0;
                                    String ultimoSurtimiento = "";

                                    cantidadRazonadaExt = cantidadRazonadaService.cantidadRazonadaInsumoPacienteExt(paciente.getIdPaciente(), item.getIdInsumo());
                                    if (cantidadRazonadaExt != null) {
                                        if(cantidadRazonadaExt.getTotalSurtMes() != null){
                                            totalDia = cantidadRazonadaExt.getTotalSurtDia();
                                            totalMes = cantidadRazonadaExt.getTotalSurtMes();
                                            diasRestantes = cantidadRazonadaExt.getDiasRestantes();
                                            // En esta linea de código estamos indicando el nuevo formato que queremos para nuestra fecha.
                                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                                            // Aqui usamos la instancia formatter para darle el formato a la fecha. Es importante ver que el resultado es un string.
                                            ultimoSurtimiento = formatter.format(cantidadRazonadaExt.getUltimoSurtimiento());
                                        }
                                        
                                    }
                                    //Consulta Interna o si el valor del inventario del objeto ci es igual a "1", el cual hace referencia a Unidosis
                                    authorization = mostrarModalPorCantidadRazonada(authorization, cantidadEnviada, ci, totalDia, cantidadEscaneada, factorTransform, totalMes, diasRestantes, ultimoSurtimiento);                                                                       
                                } else {
                                    authorization = true;
                                }
                                if (authorization) {
                                    if (surtimientoEnviadoExtendList.isEmpty()) {
                                        surtimientoEnviadoExtendList = getSurtimientoEnviadoExtList(surtimientoEnviadoExtendList, ci, item, inventarioPorSurtir, cantidadEscaneada);
                                    } else {
                                        boolean bandera = false;
                                        for (SurtimientoEnviado_Extend lote : surtimientoEnviadoExtendList) {
                                            bandera = false;
                                            // regla: si se pistolea mas de un medicmento y con el mismo lote se agrupan por lotes sumarizando las cantidades
                                            if (lote.getLote().equals(ci.getLote()) && lote.getCaducidad().equals(ci.getFecha())) {
                                                lote.setCantidadEnviado(lote.getCantidadEnviado() + cantidadEscaneada);
                                                break;

                                            } else {
                                                bandera = true;
                                            }
                                        }
                                        if (bandera) {
                                            // regla: si es el único Lote pistoleado solo muestra una linea en subdetalle
                                           surtimientoEnviadoExtendList = getSurtimientoEnviadoExtList(surtimientoEnviadoExtendList, ci, item, inventarioPorSurtir, cantidadEscaneada);
                                        }
                                    }
                                    if (valCantidadRazonada && authorizado) {
                                        item.setIdUsuarioAutCanRazn(idResponsable);
                                    }
                                    
//                                TODO: en 
//                                Integer temp = cantidadEnviada/ Integer.parseInt( item.getFactorTransformacion() );
                                    item.setCantidadEnviada(cantidadEnviada);
                                    item.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                    item.setCajasSurtidas(BigDecimal.valueOf(cantCajSurt));
                                    res = Constantes.ACTIVO;
                                    xcantidad = 1;
                                }
                            }                           
                        }
                    }
                }
            }
            codigoBarras = "";
        }
        if (!encontrado) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.claveincorrecta"), null);
        }
        return res;
    }

    /**
     * elimina un escaneo como subdetalle del resurtimiento
     *
     * @return
     */
    private boolean eliminarLotePorCodigo() {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.eliminarLotePorCodigo()");
        boolean res = Constantes.INACTIVO;
        boolean encontrado = Constantes.INACTIVO;

        CodigoInsumo ci = parsearCodigoDeBarrasConCantidad(codigoBarras);
        if (ci == null) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.parser"), null);

        } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(ci.getFecha())) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.caducidadvencida"), null);

        } else {

            List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList;
            SurtimientoEnviado_Extend surtimientoEnviadoExtend;
            Integer cantidadEscaneada = 0;
            Integer cantidadEnviada = 0;
            for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {

// regla: puede escanear medicamentos mientras la clave escaneada exista en el detalle solicitado
                if (item.getClaveInstitucional().contains(ci.getClave())) {
                    encontrado = Constantes.ACTIVO;
// regla: factor multiplicador debe ser 1 o mayor
                    if (xcantidad != null && xcantidad < 1) {
                        Mensaje.showMessage("Error", RESOURCES.getString("sur.cantidadincorrecta"), null);

                    } else {
                        cantidadEscaneada = ci.getCantidad() * xcantidad;
                        cantidadEnviada = (item.getCantidadEnviada() != null) ? item.getCantidadEnviada() : 0;
                        cantidadEnviada = cantidadEnviada - cantidadEscaneada;
                        cantidadEnviada = (cantidadEnviada < 0) ? 0 : cantidadEnviada;

                        surtimientoEnviadoExtendList = new ArrayList<>();
                        if (item.getSurtimientoEnviadoExtendList() == null) {
                            Mensaje.showMessage("Error", RESOURCES.getString("sur.lotesinescanear"), null);

                        } else {
                            surtimientoEnviadoExtendList.addAll(item.getSurtimientoEnviadoExtendList());

// regla: el lote aliminar del surtimiento ya debió ser escaneado para eliminaro
                            if (surtimientoEnviadoExtendList.isEmpty()) {
                                Mensaje.showMessage("Error", RESOURCES.getString("sur.lotesinescanear"), null);

                            } else {
// regla: si el lote escaneado ya ha sido agregado se descuentan las cantidades
                                Integer cantidadResultante = 0;
                                for (SurtimientoEnviado_Extend lote : surtimientoEnviadoExtendList) {
                                    if (lote.getLote().equals(ci.getLote()) && lote.getCaducidad().equals(ci.getFecha())) {
                                        cantidadResultante = lote.getCantidadEnviado() - cantidadEscaneada;
                                        if (cantidadResultante < 1) {
                                            surtimientoEnviadoExtendList.remove(lote);
                                        } else {
                                            lote.setCantidadEnviado(cantidadResultante);
                                        }
                                        break;
                                    }
                                }
                                item.setCantidadEnviada(cantidadEnviada);
                                item.setSurtimientoEnviadoExtendList(surtimientoEnviadoExtendList);
                                res = Constantes.ACTIVO;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!encontrado) {
            Mensaje.showMessage("Error", RESOURCES.getString("sur.claveincorrecta"), null);
        }
        return res;
    }
    
    public Surtimiento_Extend obtenerFechaAnterior(Surtimiento_Extend surtExt) throws Exception {
        Date date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(surtExt.getFechaProgramada());
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - numDiasResurtimiento);
        date = cal.getTime();
        
        surtExt = surtimientoService.obtenerByFechaAndPrescripcion(surtExt.getIdPrescripcion(), date);
        
        return surtExt;
    }
    
    public boolean validarResurtimiento(Surtimiento_Extend surtExt) throws Exception {
        boolean respuesta = true;        
        do {
            surtExt = obtenerFechaAnterior(surtExt);
            if (surtExt == null) {
                if (numResurtimiento == 0) {
                    numResurtimiento = 1;
                } else {
                    numResurtimiento += 1;
                }
            } else {
                if (numResurtimiento == 0) {
                    numResurtimiento++;
                } else {
                    numResurtimiento += 1;
                }
            }
        } while (surtExt != null);
        //Si la división de 90 / 30        
        cantResurt = (maxNumDiasResurtible / numDiasResurtimiento);
        if (numResurtimiento > cantResurt) {
            Mensaje.showMessage("Error", "No puedes surtir mas surtimientos  que los resurtimientos", null);
            respuesta = false;
        }
        return respuesta;
    }
    
    
    private Prescripcion obtenerPrescripcion(Surtimiento_Extend surtimientoExtendedSelected){
// TODO: PRIORIDAD-1  Definir los datos faltantes para eliminar código duro
        Prescripcion pres = new Prescripcion();
        pres.setIdPrescripcion(surtimientoExtendedSelected.getIdPrescripcion()); // Ya sea que nos la manden o la agregamos?
        pres.setIdEstructura(surtimientoExtendedSelected.getIdEstructura()); // verificar que sea este dato el correcto.

        pres.setFolio(surtimientoExtendedSelected.getFolioPrescripcion());
        pres.setFechaPrescripcion(surtimientoExtendedSelected.getFechaProgramada());
        pres.setFechaFirma(surtimientoExtendedSelected.getFechaProgramada());
        pres.setTipoPrescripcion(TipoPrescripcion_Enum.NORMAL.getValue());
        pres.setTipoConsulta(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue());
        pres.setIdPacienteServicio("64a843cf-bb1f-461e-b551-76178ef31a30");  // SOLO ES DE PRUEBA, SE DEBE VER SI SE INSERTARA  # 72f00f37-59d4-4ffc-9722-25bcb24530df
        pres.setIdMedico("3d6434a3-09ac-4b26-a1f6-914d4c37000a"); //  obtener
        pres.setRecurrente(false); // verificar
        pres.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue()); //verificar
// TODO:  Llenar desde un ENUM
        pres.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue()); // pendiente = 1, el cual tenia como Duro
        pres.setInsertFecha(new Date());
        pres.setInsertIdUsuario(surtimientoExtendedSelected.getInsertIdUsuario());
         return pres;
    }
    
    
    private List<PrescripcionInsumo> getPrescripcionInsumoList(PrescripcionInsumo prescInsumo,
            SurtimientoInsumo_Extend surtimientoInsumo, Prescripcion prescripcion, List<PrescripcionInsumo> prescripcionInsumoList) {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.getPrescripcionInsumoList()");
        prescInsumo.setIdPrescripcionInsumo(surtimientoInsumo.getIdPrescripcionInsumo());
        prescInsumo.setIdPrescripcion(prescripcion.getIdPrescripcion());
        prescInsumo.setIdInsumo(surtimientoInsumo.getIdInsumo());
        prescInsumo.setFechaInicio(surtimientoInsumo.getFechaProgramada());
// TODO: PRIORIDAD-1 La posología no está definida en SIAM de esta forma
        prescInsumo.setDosis(BigDecimal.ONE);
        prescInsumo.setFrecuencia(24);
        prescInsumo.setDuracion(1);
// TODO: PRIORIDAD-2 ENUMS
        prescInsumo.setIdEstatusPrescripcion(3); // 3 finalizada      verificar
        prescInsumo.setInsertFecha(new Date());
        prescInsumo.setInsertIdUsuario(surtimientoExtendedSelected.getInsertIdUsuario());
        prescripcionInsumoList.add(prescInsumo);
        
        return prescripcionInsumoList;
    }    
    
    public void validaSurtimiento() {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.validaSurtimiento()");
        limpiaVariables();
        boolean status = Constantes.INACTIVO;
        try {
            if (permiso.isPuedeAutorizar()) {
                //int estatusSurtimiento = 0;

                if (surtimientoExtendedSelected.getResurtimiento() != null) {
                    if (surtimientoExtendedSelected.getResurtimiento() > 0) {
                        Surtimiento_Extend surtExt = surtimientoExtendedSelected;
                        procederSurtimiento = validarResurtimiento(surtExt);
                    }
                }

                if (procederSurtimiento) {

                    if (surtimientoInsumoExtendedList == null) {
                        Mensaje.showMessage("Error", RESOURCES.getString("sur.claveincorrecta"), null);
                    } else if (surtimientoInsumoExtendedList.isEmpty()) {
                        Mensaje.showMessage("Error", RESOURCES.getString("sur.claveincorrecta"), null);
                    } else {
                        Integer cantidadEnviada = 0;
                        //surtimientoExtendedSelected.setIdEstatusSurtimiento(estatusSurtimiento);
// TODO: PRIORIDAD-2 modificar este idEstructura, si el Admin surtiera, debe seleccionr el ID de la estructura Farmacia Externa
                        surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                        Inventario inventarioActual;
                        boolean desabasto = false;
                        StringBuilder sb = new StringBuilder();

                        Prescripcion prescripcion = obtenerPrescripcion(surtimientoExtendedSelected);

                        List<PrescripcionInsumo> prescripcionInsumoList = new ArrayList<>();
                        List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
                        SurtimientoEnviado se;
                        List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();
                        List<SurtimientoInsumo> surtimientoInsumoListVale = new ArrayList<>();
                        List<Inventario> listIinventario = new ArrayList<>();
                        List<MovimientoInventario> listaMovInventario = new ArrayList<>();
                        int vale = 0;

                        for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoExtendedList) {

                            if (surtimientoInsumo.getCantidadEnviada() == null) {
                                surtimientoInsumo.setCantidadEnviada(0);
                            }
                            int factorTran = Integer.valueOf(surtimientoInsumo.getFactorTransformacion());
                            cajasSurtidas = (surtimientoInsumo.getCantidadEnviada() / factorTran);
                            cantidadTotalSurtir = cajasSurtidas + surtimientoInsumo.getCantidadVale();
                            if (surtimientoInsumo.getCajasSolicitadas().intValue() == cantidadTotalSurtir) {
                                surtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue()); // = EstatusSurtimiento_Enum.SURTIDO.getValue();
                            } else if (surtimientoInsumo.getCajasSolicitadas().intValue() > cantidadTotalSurtir) {
                                surtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue()); // estatusSurtimiento = EstatusSurtimiento_Enum.SUTIDO_PARCIAL.getValue();
                            }

                            PrescripcionInsumo prescInsumo = new PrescripcionInsumo();
                            cantidadEnviada = 0;
                            surtimientoInsumo.setTotalVale(surtimientoInsumo.getTotalVale() == null ? 0 : surtimientoInsumo.getTotalVale());
                            surtimientoInsumo.setTotalEnviado((surtimientoInsumo.getTotalEnviado() == null ? 0 : surtimientoInsumo.getTotalEnviado()));
                            int cantidTotalVale = Integer.valueOf(surtimientoInsumo.getFactorTransformacion()) * surtimientoInsumo.getTotalVale();
                            surtimientoInsumo.setCantidadEnviada((surtimientoInsumo.getCantidadEnviada() - surtimientoInsumo.getTotalEnviado()) - cantidTotalVale);
                            int valorTotalEnviado = surtimientoInsumo.getCantidadEnviada() + surtimientoInsumo.getTotalEnviado();
                            surtimientoInsumo.setTotalEnviado(valorTotalEnviado);

                            int valorTotalVale = surtimientoInsumo.getCantidadVale() + surtimientoInsumo.getTotalVale();
                            surtimientoInsumo.setTotalVale(valorTotalVale);

                            surtimientoInsumoList.add((SurtimientoInsumo) surtimientoInsumo);

                            if (surtimientoInsumo.getCantidadVale() != null) {
                                if (surtimientoInsumo.getCantidadVale() > 0) {
                                    surtimientoInsumoListVale.add(surtimientoInsumo);
                                    vale++;
                                }
                            }

                            // Agregamos las prescripcionesInsumos
                            prescripcionInsumoList = getPrescripcionInsumoList(prescInsumo, surtimientoInsumo, prescripcion, prescripcionInsumoList);

                            if (surtimientoInsumo.getSurtimientoEnviadoExtendList() != null) {
// regla: si el surtimiento enviado tiene al menos 1 medicamento, se puede enviar la orden
                                if (!surtimientoInsumo.getSurtimientoEnviadoExtendList().isEmpty()) {

                                    for (SurtimientoEnviado_Extend surtimientoEnviado : surtimientoInsumo.getSurtimientoEnviadoExtendList()) {

                                        if (surtimientoEnviado.getIdSurtimientoInsumo().equalsIgnoreCase(surtimientoInsumo.getIdSurtimientoInsumo())) {
                                            cantidadEnviada = cantidadEnviada + surtimientoEnviado.getCantidadEnviado();

// TODO: valida cantidad surtida contra existencias
                                            String claveProveedor = null;
                                            inventarioActual = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
                                                    surtimientoInsumo.getIdInsumo(),
                                                    usuarioSelected.getIdEstructura(),
                                                    surtimientoEnviado.getLote(),
                                                    surtimientoEnviado.getFactorTransformacion(),
                                                    claveProveedor);

                                            if (inventarioActual == null || inventarioActual.getCantidadActual() == null
                                                    || inventarioActual.getCantidadActual() < surtimientoEnviado.getCantidadEnviado()) {
                                                if (sb.length() > 0) {
                                                    sb.append("\n");
                                                }
                                                sb.append(" * Clave: ");
                                                sb.append(surtimientoInsumo.getClaveInstitucional());
                                                sb.append("  Requerida: ");
                                                sb.append(surtimientoEnviado.getCantidadEnviado());
                                                sb.append("  Existente: ");
                                                sb.append((inventarioActual != null) ? inventarioActual.getCantidadActual() : 0);
                                                desabasto = true;
                                                this.vales = false;
                                                break;
                                            }

                                            Inventario inventario = new Inventario();
                                            inventario.setCantidadActual(surtimientoEnviado.getCantidadEnviado());
                                            inventario.setIdInventario(inventarioActual.getIdInventario());
                                            listIinventario.add(inventario);

                                            MovimientoInventario movimientoInv = new MovimientoInventario();
                                            movimientoInv.setIdMovimientoInventario(Comunes.getUUID());
// TODO: PRIORIDAD-2  Enums
                                            movimientoInv.setIdTipoMotivo(20);
                                            movimientoInv.setFecha(new Date());
                                            movimientoInv.setIdUsuarioMovimiento(usuarioSelected.getIdUsuario());
// TODO: PRIORIDAD-1 la estructura Origen debe ser el almacen y no la estructura del usuario, posiblemente se requiere un combo
                                            movimientoInv.setIdEstrutcuraOrigen(usuarioSelected.getIdEstructura());
                                            movimientoInv.setIdEstrutcuraDestino(surtimientoExtendedSelected.getIdEstructura());
                                            movimientoInv.setIdInventario(surtimientoEnviado.getIdInventarioSurtido());
                                            movimientoInv.setCantidad(surtimientoEnviado.getCantidadEnviado());
                                            movimientoInv.setFolioDocumento(surtimientoExtendedSelected.getFolioPrescripcion());
                                            listaMovInventario.add(movimientoInv);

                                        }

// todo: setear el id del Inventario
                                        surtimientoEnviado.setIdEstatusSurtimiento(surtimientoInsumo.getIdEstatusSurtimiento());
                                        surtimientoEnviado.setInsertFecha(new java.util.Date());
                                        surtimientoEnviado.setInsertIdUsuario(usuarioSelected.getIdUsuario());

                                        surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviado);
                                    }

                                    //surtimientoInsumo.setIdEstatusSurtimiento(estatusSurtimiento);
                                    surtimientoInsumo.setCantidadEnviada(cantidadEnviada);
                                    surtimientoInsumo.setIdUsuarioEnviada(usuarioSelected.getIdUsuario());
                                    surtimientoInsumo.setFechaEnviada(new java.util.Date());

                                    //surtimientoExtendedSelected.setIdEstatusSurtimiento(estatusSurtimiento);
                                } else {

                                    cantidadEnviada = 0;
                                    se = new SurtimientoEnviado();
                                    se.setCantidadEnviado(cantidadEnviada);
                                    se.setIdEstatusSurtimiento(surtimientoInsumo.getIdEstatusSurtimiento());
                                    se.setIdSurtimientoEnviado(Comunes.getUUID());
                                    se.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                                    se.setInsertFecha(new java.util.Date());
                                    se.setInsertIdUsuario(usuarioSelected.getIdUsuario());
                                    se.setIdInsumo(surtimientoInsumo.getIdInsumo());

// TODO:    PRIORIDAD-2     Cuando el Inventario es insuficiente para surtir un medicamento prescrito, registrar en desabasto las cantidades faltantes
//                          Llenar la tabla de desabasto
//  OJOOOOOOOOO
                                    Inventario inventarioFaltante = inventarioService.obtenerIdiventarioPorInsumoEstructura(
                                            surtimientoInsumo.getIdInsumo(),
                                            usuarioSelected.getIdEstructura());
                                    se.setIdInventarioSurtido(inventarioFaltante.getIdInventario());
                                    se.setLote(inventarioFaltante.getLote());
                                    se.setFactorTransformacion(inventarioFaltante.getCantidadXCaja());

                                    surtimientoEnviadoList.add(se);

                                }

                            } else {
// TODO: PRIORIDAD-3    Considerar un dialogo que indique que no se esta surtiendo un medicamento
//                                if (Objects.equals(surtimientoInsumo.getIdEstatusSurtimiento(), surtimientoInsumo.getIdEstatusSurtimiento())) {
//                                    surtimientoExtendedSelected.setIdEstatusSurtimiento(surtimientoInsumo.getIdEstatusSurtimiento());
//                                }
                            Mensaje.showMessage("Error", "No se estan Surtiendo Medicamentos : \n" + sb.toString(), null);
                            }
                        }
                        if (desabasto) {
                            Mensaje.showMessage("Error", "Existencias Insuficientes: \n" + sb.toString(), null);

                        } else {

                            boolean res;
                            boolean res2;

//TODO: PRIORIDAD-2    El idEstructuraAlmacen debe ser el idEstructura del almacen no del usuario
                            surtimientoExtendedSelected.setIdEstructuraAlmacen(usuarioSelected.getIdEstructura());
                            surtimientoExtendedSelected.setUpdateFecha(new java.util.Date());
                            surtimientoExtendedSelected.setUpdateIdUsuario(usuarioSelected.getIdUsuario());
                            //surtimientoExtendedSelected.setIdEstatusSurtimiento(surti);

                            res = surtimientoService.registrarServSurtimientoPrescExt(paciente, pacienteDomicilio, visita, pacienteServicio,
                                    prescripcion, prescripcionInsumoList, surtimientoExtendedSelected, surtimientoInsumoList,
                                    surtimientoEnviadoList, listIinventario, listaMovInventario, existePaciente);

                            pathTmp = "";
                            pathTmp2 = "";
                            if (res) {
                                imprimirTcket(surtimientoExtendedSelected, surtimientoInsumoList, usuarioSelected.getNombreUsuario());
                                if (vale > 0) {
                                    res2 = surtimientoService
                                            .registrarSurtimientoVales(surtimientoExtendedSelected, surtimientoInsumoListVale, surtimientoEnviadoList);

                                    vales = Constantes.ACTIVO;
                                    if (res2) {
                                        imprimirTcketVale(surtimientoExtendedSelected, surtimientoInsumoListVale, usuarioSelected.getNombreUsuario());
                                    }


                                }

                                if (archivo.length() > 0) {
                                    String Ticket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\" : "/";
                                    rutaPdfs = dirTmp.getPath() + Ticket + surtimientoExtendedSelected.getFolio() + ".pdf";
                                    reader1 = new PdfReader(pathTmp);
                                    if (pathTmp2.length() > 0) {
                                        reader2 = new PdfReader(pathTmp2);
                                    }
                                    PdfCopyFields copy = new PdfCopyFields(new FileOutputStream(rutaPdfs));
                                    copy.addDocument(reader1);

                                    if (pathTmp2.length() > 0) {
                                        copy.addDocument(reader2);
                                    }

                                    String jsText1 = "this.print({bUI: true, bSilent: true, bShrinkToFit: true});";

                                    copy.addJavaScript(jsText1);

                                    copy.close();
                                    archivo = urlf + "/resources/tmp/" + surtimientoExtendedSelected.getFolio() + ".pdf";
                                }
                                status = true;
                            }
                            init();

                            if (status) {
                                Mensaje.showMessage("Info", RESOURCES.getString("sur.exitoso"), null);
// TODO: remover elemento de la lista de forma eficiente
                                surtimientoExtendedList.stream().filter(prdct -> prdct.getFolio().equals(surtimientoExtendedSelected.getFolio())).forEach(cnsmr
                                        -> surtimientoExtendedList.remove(cnsmr)
                                );

                            }
                        }
                    }
                }
            } else {
                Mensaje.showMessage("Error", RESOURCES.getString("solicRebasto.err.noAutorizar"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
        cadenaBusqueda = "";
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }


    private void limpiaVariables(){
        archivo="";
        pathTmp="";
        pathTmp2="";
        rutaPdfs="";
    }
    
    public void imprimirPorId(String idSurtimiento) throws Exception{
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.imprimirPorId()");
        surtimientoExtendedSelected = (Surtimiento_Extend) surtimientoService.obtener(new Surtimiento(idSurtimiento));
        imprimir();
    }
    
    public void imprimir() throws Exception{
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            String pathTmp = dirTmp.getPath() + "\\surtimiento.pdf";
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme()
                            , null
                            , ext.getRequestServerName()
                            , ext.getRequestServerPort()
                            , ext.getRequestContextPath()
                            , null
                            , null);
            String url = uri.toASCIIString();
            
            boolean res = reportesService.imprimeSurtimiento(surtimientoExtendedSelected, pathTmp, url);
            if(res) {
                status = Constantes.ACTIVO;
                archivo = url + "/resources/tmp/surtimiento.pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);           
    }
    
    public void actualizaCantidadVale(SurtimientoInsumo_Extend items){
        surtimientoInsumoExtendedList.stream().filter(insumo -> (insumo.getIdInsumo().equals(items.getIdInsumo()))).forEachOrdered(insumo -> {
            boolean valStatus = true;
            authorization = false;
            int cantSurt = items.getCantidadEnviada() / Integer.valueOf(items.getFactorTransformacion());//Aqui ya va incluido el valor de totalVale
            cantidadTotalSurtirByVale = items.getCantidadVale() + cantSurt;            
            if (items.getCajasSolicitadas().intValue() < cantidadTotalSurtirByVale) {                
                items.setCantidadVale(0);
                Mensaje.showMessage("Error",RESOURCES.getString("prc.cajasvales.exedida"), null);
            } else {
                try {
                    surtimientoInsumoItem = items;
                    auxValorVale = surtimientoInsumoItem.getCantidadVale();
                    surtimientoInsumoItem.setCantidadVale(0);
                    valStatus = validarCantRazonadaVales(items, cantidadTotalSurtirByVale);
                    if (valStatus) {
                        surtimientoInsumoItem.setCantidadVale(auxValorVale);
                    }
                } catch (Exception ex) {
                     LOGGER.error("Error al actualizar cantidad Vales: {}", ex.getMessage());
                }
            }
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, valStatus);
        });   
    }
    
    
    public void imprimirTcket(Surtimiento_Extend surtimientoExtendedSelected,List<SurtimientoInsumo> surtimientoInsumoList,String nombreUsuario) throws Exception{
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            //Se cambio las dos // por la diagonal 
            String rutaTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\ticket" : "/ticket" ;
            pathTmp = dirTmp.getPath() + rutaTicket + surtimientoExtendedSelected.getFolio() + ".pdf";
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme()
                            , null
                            , ext.getRequestServerName()
                            , ext.getRequestServerPort()
                            , ext.getRequestContextPath()
                            , null
                            , null);
            String url = uri.toASCIIString();
            urlf=url;
            boolean res = reportesService.imprimeSurtimientoPrescExt(
                    surtimientoExtendedSelected,
                    nombreUsuario, pathTmp, url);
            
            if(res) {
                status = Constantes.ACTIVO;
                archivo = url + "/resources/tmp/ticket"+ surtimientoExtendedSelected.getFolio() +".pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);  
        
    }
    
    public void imprimirTcketVale(Surtimiento_Extend surtimientoExtendedSelected,List<SurtimientoInsumo> surtimientoInsumoList,String nombreUsuario) throws Exception{
        LOGGER.debug("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        
        try {
            //Se cambio las dos // por la diagonal 
            String rutaTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\vale" : "/vale" ;
            pathTmp2 = dirTmp.getPath() + rutaTicket + surtimientoExtendedSelected.getFolio() + ".pdf";
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme()
                            , null
                            , ext.getRequestServerName()
                            , ext.getRequestServerPort()
                            , ext.getRequestContextPath()
                            , null
                            , null);
            String url = uri.toASCIIString();
            urlf=url;
            reportesService.imprimeSurtimientoVales(
                    surtimientoExtendedSelected,
                    nombreUsuario, pathTmp2, url);
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("vales", status);           
    }

    public CodigoInsumo parsearCodigoDeBarrasConCantidad(String codigo) {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.parsearCodigoDeBarrasConCantidad()");
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                return ci;
            } else {
                ClaveProveedorBarras_Extend claveProveedorBarras = claveProveedorBarrasService.obtenerClave(codigo);
                if(claveProveedorBarras != null){
                    ci = new CodigoInsumo();
                    ci.setClave(claveProveedorBarras.getClaveInstitucional());
                    ci.setLote(claveProveedorBarras.getClaveProveedor());
                    ci.setCantidad(claveProveedorBarras.getCantidadXCaja());
                    ci.setFecha(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
                    return ci;
                }else
                    Mensaje.showMessage("Error", RESOURCES.getString("prescripcionExt.err.valorInvalido"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Error al Parsear Código de Barras: {}", e.getMessage());
        }
        return null;
    }
    
    
        public Integer getSizeSurtimientoInsumoExtendedList() {
        if (surtimientoInsumoExtendedList != null) {
            return surtimientoInsumoExtendedList.size();
        } else {
            return 0;
        }
    }
 
    public List<ClaveProveedorBarras_Extend> autoComplete(String cadena) {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.autoComplete()");
        skuSapList = new ArrayList<>();
        try {
            String idEstructura = usuarioSelected.getIdEstructura();
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(cadena);
            if (ci != null) {
                String claveInstitucional = ci.getClave();
                String lote = ci.getLote();
                Date fechaCaducidad = ci.getFecha();
                Integer cantidadXcaja = null;
                if (ci.getCantidad() != null) {
                    cantidadXcaja = ci.getCantidad();
                }

                skuSapList = claveProveedorBarrasService
                        .obtenerListaClavesCodigoQrExt(claveInstitucional, lote, fechaCaducidad, idEstructura, cantidadXcaja);

            } else {
                skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExt(cadena, idEstructura, usuarioSelected.getIdUsuario());
            }

           medicamentoRecetado();

        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return skuSapList;
    }

    /**
     * Valida si un medicamento escaneado esta dentro de la receta
     */
    private void medicamentoRecetado() {
        LOGGER.trace("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.medicamentoRecetado()");
        List<ClaveProveedorBarras_Extend> tmp = new ArrayList<>();
        tmp.addAll(skuSapList);
        skuSapList = new ArrayList<>();
        if (!tmp.isEmpty()) {
                try {
                    for (SurtimientoInsumo_Extend item : surtimientoInsumoExtendedList) {
                        for (ClaveProveedorBarras_Extend item2 : tmp) {
                            if (item.getClaveInstitucional().equals(item2.getClaveInstitucional())) {
                                skuSapList.add(item2);
                            }
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
                }
        }
    }
    
    /**
     * 
     * @param e 
     */
    public void handleSelect(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.ServicioSurtPrescripcionExtMB.handleSelect()");
        Inventario inv = null;
        try {
            skuSap = (ClaveProveedorBarras_Extend) e.getObject();
            String idInventario = skuSap.getIdInventario();

            inv = inventarioService.obtener(new Inventario(idInventario));
            
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (inv != null) {
            if (inv.getIdInsumo() != null) {
                try {
                    Medicamento m = medicamentoService.obtenerMedicamento(inv.getIdInsumo());
                    codigoBarras = CodigoBarras.generaCodigoDeBarras(m.getClaveInstitucional(), inv.getLote(), inv.getFechaCaducidad(), inv.getCantidadXCaja());
                    skuSap = new ClaveProveedorBarras_Extend();
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage());
                }
            }
        }
    }
    
    
    public void authorization() {
        boolean status = false;
        try {

            Usuario usr = new Usuario();
            usr.setNombreUsuario(userResponsable);
            usr.setActivo(Constantes.ACTIVO);
            usr.setUsuarioBloqueado(Constantes.INACTIVO);            
            msjMdlSurtimiento = false;

            Usuario user = usuarioService.obtener(usr);
            if (user != null) {
                if (CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(passResponsable, user.getClaveAcceso())) {
                    permisosAutorizaList = new ArrayList<>();
                    permisosAutorizaList = transaccionService.permisosUsuarioTransaccion(user.getIdUsuario(), Transaccion_Enum.SURTPRESCEXT.getSufijo());
                    if (permisosAutorizaList != null) {
                        permisosAutorizaList.stream().forEach(item -> {
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
                            if (habilitaValidacionVale) {
                                status = validarCantRazonadaVales(surtimientoInsumoItem, cantidadTotalSurtirByVale);
                                if (status) {
                                    surtimientoInsumoItem.setCantidadVale(auxValorVale);
                                    surtimientoInsumoItem.setIdUsuarioAutCanRazn(idResponsable);
                                }
                            } else {
                                status = agregarLotePorCodigo();
                            }
                            if (status) {
                                if (permiso.isPuedeAutorizar()) {
                                    userResponsable = usuarioSelected.getNombreUsuario();
                                    passResponsable = usuarioSelected.getClaveAcceso();
                                } else {
                                    userResponsable = "";
                                    passResponsable = "";
                                }
                                status = Constantes.ACTIVO;
                                codigoBarras = "";
                                idResponsable = "";
                                authorizado = false;
                                xcantidad = 1;
                                eliminaCodigoBarras = false;
                                msjMdlSurtimiento = true;
                            }
                        } else {
                            userResponsable = "";
                            passResponsable = "";
                            Mensaje.showMessage("Error", "El usuario no tiene permisos para Autorizar", null);
                        }
                    } else {
                        userResponsable = "";
                        passResponsable = "";
                        Mensaje.showMessage("Error", "El usuario no tiene permisos para está transacción", null);
                    }
                }
            } else {
                userResponsable = "";
                passResponsable = "";
                Mensaje.showMessage("Error", "El nombre de usuariono es valido", null);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio una excepcion: ", ex);
            Mensaje.showMessage("Error", ex.getMessage(), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void cancelAuthorization() {
        authorization = false;
        msjMdlSurtimiento = true;
    }
           
    public void handleUnSelect(UnselectEvent e){
        skuSap = new ClaveProveedorBarras_Extend();                
    }            

    public void onRowSelectInsumo(SelectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowSelectInsumo()");
        surtimientoInsumoExtendedSelected = (SurtimientoInsumo_Extend) event.getObject();
        if (surtimientoInsumoExtendedSelected != null) {
            elementoSeleccionado = true;
        }
    }

    public void onRowUnselectInsumo(UnselectEvent event) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.onRowUnselectInsumo()");
        surtimientoInsumoExtendedSelected = null;
        elementoSeleccionado = false;
    }

    public boolean isVales() {
        return vales;
    }

    public void setVales(boolean vales) {
        this.vales = vales;
    }

    public String getArchivoVale() {
        return archivoVale;
    }

    public void setArchivoVale(String archivoVale) {
        this.archivoVale = archivoVale;
    }

    
    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }        

    public boolean isHuboError() {
        return huboError;
    }

    public void setHuboError(boolean huboError) {
        this.huboError = huboError;
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

    public boolean isElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public void setElementoSeleccionado(boolean elementoSeleccionado) {
        this.elementoSeleccionado = elementoSeleccionado;
    }

    public List<TransaccionPermisos> getPermisosList() {
        return permisosList;
    }

    public void setPermisosList(List<TransaccionPermisos> permisosList) {
        this.permisosList = permisosList;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isPuedeCancelar() {
        return puedeCancelar;
    }

    public void setPuedeCancelar(boolean puedeCancelar) {
        this.puedeCancelar = puedeCancelar;
    }

    public List<String> getTipoPrescripcionSelectedList() {
        return tipoPrescripcionSelectedList;
    }

    public void setTipoPrescripcionSelectedList(List<String> tipoPrescripcionSelectedList) {
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
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

    public List<Surtimiento_Extend> getSurtimientoExtendedList() {
        return surtimientoExtendedList;
    }

    public void setSurtimientoExtendedList(List<Surtimiento_Extend> surtimientoExtendedList) {
        this.surtimientoExtendedList = surtimientoExtendedList;
    }

    public SurtimientoInsumo_Extend getSurtimientoInsumoExtendedSelected() {
        return surtimientoInsumoExtendedSelected;
    }

    public void setSurtimientoInsumoExtendedSelected(SurtimientoInsumo_Extend surtimientoInsumoExtendedSelected) {
        this.surtimientoInsumoExtendedSelected = surtimientoInsumoExtendedSelected;
    }    

    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    public List<TipoJustificacion> getJustificacionList() {
        return justificacionList;
    }

    public void setJustificacionList(List<TipoJustificacion> justificacionList) {
        this.justificacionList = justificacionList;
    }

    public boolean isEliminaCodigoBarras() {
        return eliminaCodigoBarras;
    }

    public void setEliminaCodigoBarras(boolean eliminaCodigoBarras) {
        this.eliminaCodigoBarras = eliminaCodigoBarras;
    }

    public Integer getXcantidad() {
        return xcantidad;
    }

    public void setXcantidad(Integer xcantidad) {
        this.xcantidad = xcantidad;
    }

    
    private ClaveProveedorBarras_Extend skuSap;
    private List<ClaveProveedorBarras_Extend> skuSapList;

    public ClaveProveedorBarras_Extend getSkuSap() {
        return skuSap;
    }

    public void setSkuSap(ClaveProveedorBarras_Extend skuSap) {
        this.skuSap = skuSap;
    }

    public List<ClaveProveedorBarras_Extend> getSkuSapList() {
        return skuSapList;
    }

    public void setSkuSapList(List<ClaveProveedorBarras_Extend> skuSapList) {
        this.skuSapList = skuSapList;
    }

    public Pattern getRegexNumber() {
        return regexNumber;
    }

    public void setRegexNumber(Pattern regexNumber) {
        this.regexNumber = regexNumber;
    }
    
    public String getRutaPdfs() {
        return rutaPdfs;
    }

    public void setRutaPdfs(String rutaPdfs) {
        this.rutaPdfs = rutaPdfs;
    }

    public String getPathTmp() {
        return pathTmp;
    }

    public void setPathTmp(String pathTmp) {
        this.pathTmp = pathTmp;
    }

    public String getPathTmp2() {
        return pathTmp2;
    }

    public void setPathTmp2(String pathTmp2) {
        this.pathTmp2 = pathTmp2;
    }

    public String getUrlf() {
        return urlf;
    }

    public void setUrlf(String urlf) {
        this.urlf = urlf;
    }

    public ServSurtPrescripcionExtLazy getServSurtPrescripcionExtLazy() {
        return servSurtPrescripcionExtLazy;
    }

    public void setServSurtPrescripcionExtLazy(ServSurtPrescripcionExtLazy servSurtPrescripcionExtLazy) {
        this.servSurtPrescripcionExtLazy = servSurtPrescripcionExtLazy;
    }

    public String getProgramada() {
        return programada;
    }

    public void setProgramada(String programada) {
        this.programada = programada;
    }   

    public String getSurtida() {
        return surtida;
    }

    public void setSurtida(String surtida) {
        this.surtida = surtida;
    }

    public String getCancelada() {
        return cancelada;
    }

    public void setCancelada(String cancelada) {
        this.cancelada = cancelada;
    }

    public boolean isHabilitaVales() {
        return habilitaVales;
    }

    public void setHabilitaVales(boolean habilitaVales) {
        this.habilitaVales = habilitaVales;
    }

    public String getMsjAlert() {
        return msjAlert;
    }

    public void setMsjAlert(String msjAlert) {
        this.msjAlert = msjAlert;
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

    public boolean isAuthorization() {
        return authorization;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public boolean isProcederSurtimiento() {
        return procederSurtimiento;
    }

    public void setProcederSurtimiento(boolean procederSurtimiento) {
        this.procederSurtimiento = procederSurtimiento;
    }

    public Integer getNumResurtimiento() {
        return numResurtimiento;
    }

    public void setNumResurtimiento(Integer numResurtimiento) {
        this.numResurtimiento = numResurtimiento;
    }

    public Integer getCantResurt() {
        return cantResurt;
    }

    public void setCantResurt(Integer cantResurt) {
        this.cantResurt = cantResurt;
    }

    public boolean isObetenerRegistroWS() {
        return obetenerRegistroWS;
    }

    public void setObetenerRegistroWS(boolean obetenerRegistroWS) {
        this.obetenerRegistroWS = obetenerRegistroWS;
    }

    public boolean isPuedeSurtirPrescr() {
        return puedeSurtirPrescr;
    }

    public void setPuedeSurtirPrescr(boolean puedeSurtirPrescr) {
        this.puedeSurtirPrescr = puedeSurtirPrescr;
    }        

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public boolean isExistePacientePepmae() {
        return existePacientePepmae;
    }

    public void setExistePacientePepmae(boolean existePacientePepmae) {
        this.existePacientePepmae = existePacientePepmae;
    }
    
}
