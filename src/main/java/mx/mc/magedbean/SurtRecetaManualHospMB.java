package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.CatalogoGeneral_Enum;
import mx.mc.enums.EstatusDiagnostico_Enum;
import mx.mc.enums.EstatusGabinete_Enum;
import mx.mc.enums.EstatusPaciente_Enum;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.GrupoCatalogoGeneral_Enum;
import mx.mc.enums.MotivoPacienteMovimiento_Enum;
import mx.mc.enums.PrefijoPrescripcion_Enum;
import mx.mc.enums.SubCategoriaMedicamento_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoConsulta_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.TipoPrescripcion_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CamaExtended;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.PacienteServicio;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;
import mx.mc.model.Prescripcion_Extended;
import mx.mc.model.RepSurtimientoPresc;
import mx.mc.model.Rol;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.TipoUsuario;
import mx.mc.model.Turno;
import mx.mc.model.TurnoMedico;
import mx.mc.model.Usuario;
import mx.mc.model.UsuarioRol;
import mx.mc.model.Usuario_Extended;
import mx.mc.model.Visita;
import mx.mc.model.VistaUsuario;
import mx.mc.service.CamaService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.PacienteService;
import mx.mc.service.PacienteServicioService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.TipoUsuarioService;
import mx.mc.service.TurnoService;
import mx.mc.service.UsuarioService;
import mx.mc.service.VisitaService;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author aortiz
 */
@Controller
@Scope(value = "view")
public class SurtRecetaManualHospMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SurtRecetaManualHospMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private Date fechaReceta;
    private Date fechaActual;
    private String cadenaBusqueda;
    private Paciente_Extended pacienteExtended;
    private Paciente paciente;
    private Diagnostico diagnosticoSelected;
    private Usuario medico;
    private Usuario_Extended medicoExtended;
    private List<TipoUsuario> tipoUserList;
    private static TreeNode root;
    private static TreeNode selectNode;
    private String nameUnidad;
    private String pathNode;
    private List<String> listNode;
    private UsuarioRol usuarioRolSelect;
    private List<Estructura> listEstructura;
    private String uestructura;
    private List<VistaUsuario> usuarioList;
    private List<Rol> rolList;
    private Diagnostico diagnostico;
    private List<Diagnostico> diagnosticoList;
    private EntidadHospitalaria entidadHospitalaria;
    private List<EntidadHospitalaria> entidadList;
    private List<PrescripcionInsumo_Extended> prescripcionInsumoExtendedList;
    private PrescripcionInsumo_Extended prescripcionInsumoExtendedSelected;
    private List<ClaveProveedorBarras_Extend> skuSapList;
    private PrescripcionInsumo prescripcionInsumo;
    private Usuario usuarioSession;
    private SesionMB sesion;
    private List<String> listaIdTurnos;
    private List<Turno> listaTurnos;
    private String folioPrescripcion;
    private Visita visita;
    private PacienteServicio pacienteServicio;
    private Integer cantidadEntregada;
    private ClaveProveedorBarras_Extend skuSap;
    private ClaveProveedorBarras_Extend medicamentoSelect;
    private List<Prescripcion_Extended> listaRecetasManuales;
    private Prescripcion_Extended recetaManualSelect;
    private String pathTmp;
    private File dirTmp;
    private String archivo;
    private String prefijo;
    private String tipoConsulta;
    private boolean permiteSurtimientoManualHospitalizacion;
    private List<Estructura> listaServicios;
    private String idServicio;
    private String nameService;
    private List<SurtimientoInsumo_Extend> listaInsumosPendientes;
    private Prescripcion_Extended prescripcionSelected;
    private List<CatalogoGeneral> listaTipoPacientes;
    private List<CatalogoGeneral> listaUnidadesMedicas;
    private boolean bandera;
    private Integer numDuracion;
    private boolean activeDuracion;
    private String paramError;
    private String prcManualMedicamentoInvalido;
    private String prcManualErrorRegistro;    
    private String prcNopuedecancelar;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient DiagnosticoService diagnosticoService;

    @Autowired
    private transient PrescripcionService prescripcionService;

    @Autowired
    private transient TurnoService turnoService;

    @Autowired
    private transient VisitaService visitaService;

    @Autowired
    private transient PacienteServicioService pacienteServicioService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    @Autowired
    private transient SurtimientoService surtimientoService;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;

    @Autowired
    private transient CamaService camaservice;

    /**
     * Inicializa propiedades del controlador
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.init()");
        paramError = "error";        
        prcManualMedicamentoInvalido = "prc.manual.medicamento.invalido";
        prcManualErrorRegistro = "prc.manual.error.registro";
        prcNopuedecancelar = "prc.nopuedecancelar";
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        permiteSurtimientoManualHospitalizacion = sesion.isPermiteSurtimientoManualHospitalizacion();
        numDuracion = sesion.getNumDuracion();
        this.usuarioSession = sesion.getUsuarioSelected();

        initialize();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.SRMHOS.getSufijo());
        obtenerEntidadHospitalaria();
        obtenerServicios();
        obtieneListaTipoPacientes();
        obtieneListaTipoUnidadesMedicas();
        obtenerSurtimientoManualDePrescripcion();

    }

    /**
     * Limpia variables
     */
    public void initialize() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.initialize()");
        this.fechaReceta = new Date();
        this.fechaActual = new Date();
        this.diagnosticoList = new ArrayList<>();
        this.listaIdTurnos = new ArrayList<>();
        this.prescripcionInsumoExtendedList = new ArrayList<>();
        this.prescripcionInsumo = new PrescripcionInsumo();
        if (numDuracion > 0) {
            this.prescripcionInsumo.setDuracion(numDuracion);
            activeDuracion = Constantes.ACTIVO;
        }
        this.paciente = new Paciente();
        this.entidadHospitalaria = new EntidadHospitalaria();
        this.entidadList = new ArrayList<>();
        this.cadenaBusqueda = null;
        this.medicoExtended = new Usuario_Extended();
        this.prefijo = PrefijoPrescripcion_Enum.NORMAL.getValue();
        this.tipoConsulta = TipoConsulta_Enum.CONSULTA_INTERNA.getValue();
        this.bandera = false;
        this.listaPrescripcionInsumoExtendedValidacion = new ArrayList<>();
        numeroMedicamentosConDiferencias = 0;
        dirTmp = new File(Comunes.getPath());

    }

    /**
     * Obtiene la entidad hospitalaria
     */
    private void obtenerEntidadHospitalaria() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerEntidadHospitalaria()");
        EntidadHospitalaria entHosp = new EntidadHospitalaria();
        try {
            this.entidadList = this.entidadHospitalariaService.obtenerLista(entHosp);
            if (entidadList != null && !entidadList.isEmpty()) {
                for (EntidadHospitalaria item : entidadList) {
                    if (item.getEstatus()) {
                        this.entidadHospitalaria = item;
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener entidad hospitalaria: {}", ex.getMessage());
        }

    }

    /**
     * Obtiene el listado de servicios hospitalarios activos
     */
    private void obtenerServicios() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerServicios()");
        List<Integer> listaTipoArea = new ArrayList<>();
        listaTipoArea.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
        listaTipoArea.add(TipoAreaEstructura_Enum.SERVICIO.getValue());
        listaTipoArea.add(TipoAreaEstructura_Enum.PABELLO.getValue());
        listaTipoArea.add(TipoAreaEstructura_Enum.ALA.getValue());
        listaTipoArea.add(TipoAreaEstructura_Enum.AREA.getValue());

        try {
            this.listaServicios = this.estructuraService.obtenerEstructurasPorTipo(listaTipoArea);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener estructura de Servicios : {}", ex.getMessage());
        }
    }

    /**
     * Obtien el tipo de unidad médica donde se asigna al usuario
     */
    private void obtieneListaTipoUnidadesMedicas() {
        this.listaUnidadesMedicas = new ArrayList<>();
        try {
            this.listaUnidadesMedicas.addAll(this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.UNIDAD_MEDICA.getValue()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Tipos de Paciente: {}", ex.getMessage());
        }
    }

    /**
     * Obtiene listado del tipo de paciente
     */
    private void obtieneListaTipoPacientes() {
        this.listaTipoPacientes = new ArrayList<>();
        try {
            this.listaTipoPacientes.addAll(this.catalogoGeneralService.obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_DE_PACIENTE.getValue()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Unidades Medicas: {}", ex.getMessage());
        }
    }

    /**
     * Obtiene listado de surtimientoes manuales de prescripción
     */
    public void obtenerSurtimientoManualDePrescripcion() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerSurtimientoManualDePrescripcion()");
        try {
            List<Integer> listaEstatusPrescripcion = new ArrayList<>();
            listaEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
            listaEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());

            List<String> listaTipoPrescripcion = new ArrayList<>();
            listaTipoPrescripcion.add(TipoPrescripcion_Enum.MANUAL.getValue());

            this.listaRecetasManuales = prescripcionService.obtenerRecetasManuales(
                    listaTipoPrescripcion,
                    listaEstatusPrescripcion,
                    this.cadenaBusqueda);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
    }

    /**
     * Limpia las propiedades del Medicmento
     */
    private void limpiarDatosMedicamento() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.limpiarDatosMedicamento()");
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.cantidadEntregada = null;
        this.diagnostico = new Diagnostico();
        this.prescripcionInsumo = new PrescripcionInsumo();
        if (numDuracion > 0) {
            this.prescripcionInsumo.setDuracion(numDuracion);
            activeDuracion = Constantes.ACTIVO;
        }
        this.pacienteExtended = new Paciente_Extended();
        this.entidadHospitalaria = new EntidadHospitalaria();
        this.medico = new Usuario();
        this.cantidadEntregada = null;
    }

    /**
     * Limpia datos de paciente
     */
    private void limpiarDatosPaciente() {
        this.paciente = new Paciente();
    }

    /**
     * Limpia todas las propiedades de controlador
     */
    public void limpiarTodo() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.limpiarTodo()");
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.cantidadEntregada = null;
        this.diagnostico = new Diagnostico();
        this.prescripcionInsumo = new PrescripcionInsumo();
        if (numDuracion > 0) {
            this.prescripcionInsumo.setDuracion(numDuracion);
            activeDuracion = Constantes.ACTIVO;
        }
        this.pacienteExtended = new Paciente_Extended();
        this.medico = new Usuario();
        this.cantidadEntregada = null;
        this.folioPrescripcion = null;
        this.fechaReceta = new Date();
        this.pacienteExtended = null;
        this.medico = null;
        this.diagnosticoList = new ArrayList<>();
        this.entidadList = new ArrayList<>();
        this.entidadHospitalaria = new EntidadHospitalaria();
        this.skuSap = null;
        this.prescripcionInsumoExtendedList = new ArrayList<>();
        this.idServicio = null;
        this.paciente = new Paciente();
        this.cadenaBusqueda = null;
        this.medicoExtended = new Usuario_Extended();
        this.prefijo = PrefijoPrescripcion_Enum.NORMAL.getValue();
        this.tipoConsulta = TipoConsulta_Enum.CONSULTA_INTERNA.getValue();
        this.listaPrescripcionInsumoExtendedValidacion = new ArrayList<>();
        this.bandera = false;
        numeroMedicamentosConDiferencias = 0;
    }

    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.autoCompletePacientes()");
        List<Paciente_Extended> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener pacientes : {}", ex.getMessage());
        }
        return listaPacientes;
    }

    public List<Usuario> autoCompleteMedicos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.autoCompleteMedicos()");
        List<Usuario> listaMedicos = new ArrayList<>();
        try {
            Integer prescribeControlados = 1;
            if (this.prefijo.equalsIgnoreCase(PrefijoPrescripcion_Enum.CONTROLADA.getValue())) {
                listaMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                        cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, prescribeControlados);
            } else {
                listaMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                        cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompleteMedicos : {}", ex.getMessage());
        }
        return listaMedicos;
    }

    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.autocompleteDiagnostico()");
        List<Diagnostico> diagList = new ArrayList<>();
        try {
            Diagnostico d = new Diagnostico();
            d.setActivo(Constantes.ACTIVO);
            d.setNombre(cadena.trim());
            d.setDescripcion(cadena.trim());
            diagList.addAll(this.diagnosticoService.obtenerListaChiconcuac(d, Constantes.REGISTROS_PARA_MOSTRAR));

        } catch (Exception ex) {
            LOGGER.error("Error al obtener autocompleteDiagnostico : {}", ex.getMessage());
        }
        return diagList;
    }

    public List<EntidadHospitalaria> autocompleteDatos(String cadena) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.autocompleteDatos()");
        List<EntidadHospitalaria> entiList = new ArrayList<>();
        try {
            EntidadHospitalaria e = new EntidadHospitalaria();
            e.setNombre(cadena);
            entiList.addAll(this.entidadHospitalariaService.obtenerLista(e));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autocompleteDatos de Entidad Hospitalaria: {}", ex.getMessage());
        }
        return entiList;
    }

    public List<ClaveProveedorBarras_Extend> autocompleteMedicamento(String query) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.autocompleteMedicamento()");
        boolean rManual = false;
        this.skuSapList = new ArrayList<>();
        try {
            String idEstructura = this.usuarioSession.getIdEstructura();
            if (this.permiteSurtimientoManualHospitalizacion) {
                List<Integer> listaSubCategorias = new ArrayList<>();
                if (this.prefijo.equalsIgnoreCase(PrefijoPrescripcion_Enum.CONTROLADA.getValue())) {
                    listaSubCategorias = new ArrayList<>();
                    listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G1.getValue());
                    listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G2.getValue());
                    listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G3.getValue());
                }
                rManual = true;
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExtChiconcuac(
                        query, idEstructura, this.usuarioSession.getIdUsuario(), listaSubCategorias, rManual);
            } else {
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExt(
                        query, idEstructura, this.usuarioSession.getIdUsuario());
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autocompleteMedicamento : {}", ex.getMessage());
        }
        return skuSapList;
    }

    public void handleSelectDiagnostico(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtRecetaManualHospMB.handleSelectDiagnostico()");
        Diagnostico diag = (Diagnostico) e.getObject();
        if (diag != null) {
            diag.setActivo(Constantes.ACTIVO);
            Diagnostico item = new Diagnostico();
            try {
                item = diagnosticoService.obtener(diag);
            } catch (Exception ex) {
                LOGGER.error("Error al obtener handleSelectDiagnostico: {}", ex.getMessage());
            }
            this.diagnosticoList.add(item);
            this.diagnostico = new Diagnostico();
        }

    }

    public void handleSelectEntidadHospitalaria(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtRecetaManualHospMB.handleSelectEntidadHospitalaria()");
        EntidadHospitalaria ent = (EntidadHospitalaria) e.getObject();
        if (ent != null) {
            EntidadHospitalaria item = new EntidadHospitalaria();
            try {
                item = entidadHospitalariaService.obtener(ent);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
            this.entidadHospitalaria = item;
            this.entidadList.add(item);

            this.entidadHospitalaria = new EntidadHospitalaria();

        }

    }

    public void handleSelectMedicamento(SelectEvent e) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.handleSelectMedicamento()");
        this.skuSap = (ClaveProveedorBarras_Extend) e.getObject();
        String idInventario = skuSap.getIdInventario();
        for (ClaveProveedorBarras_Extend item : this.skuSapList) {
            if (item.getIdInventario().equalsIgnoreCase(idInventario)) {
                if (item.getCantidadActual() == 0) {
                    cantidadEntregada = item.getCantidadActual();
                    bandera = true;
                } else {
                    bandera = false;
                }
                this.medicamentoSelect = item;
                break;
            }
        }
    }

    public void handleUnSelectMedicamento() {
        skuSap = new ClaveProveedorBarras_Extend();

    }

    public void agregarPrescripcionInsumo() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.agregarPrescripcionInsumo()");
        try {
            String msjError = validarMedicamento();
            if (msjError != null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
                PrimeFaces.current().ajax().addCallbackParam(paramError, true);
                limpiarDatosMedicamento();
                return;
            }
            boolean insertado = false;
            PrescripcionInsumo_Extended prescInsumoExtended = generarPrescripcionInsumoExtended();
            BigDecimal dosis = prescripcionInsumo.getDosis().setScale(0, RoundingMode.CEILING);
            if (this.prescripcionInsumoExtendedList.isEmpty()) {
                prescInsumoExtended.setIdSurtimientoInsumo(Comunes.getUUID());
                Integer cantidadSolicitada = 0;                
                float temp = (24f / prescripcionInsumo.getFrecuencia());
                int numeroDosisPorDia = (int) temp;
                cantidadSolicitada = (numeroDosisPorDia * dosis.intValue()) * prescripcionInsumo.getDuracion();                
                prescInsumoExtended.setCantidadSolicitada(cantidadSolicitada);
                insertarNuevoPrescripcionInsumoExtended(prescInsumoExtended);
                insertado = true;
            } else {
                for (short i = 0; i < this.prescripcionInsumoExtendedList.size(); i++) {
                    PrescripcionInsumo_Extended item = this.prescripcionInsumoExtendedList.get(i);
                    if (item.getIdInsumo().equalsIgnoreCase(prescInsumoExtended.getIdInsumo())) {
                        for (SurtimientoEnviado_Extend item2 : item.getSurtimientoEnviadoExtendedList()) {
                            if (item2.getLote().equalsIgnoreCase(this.medicamentoSelect.getLote())) {
                                item.setCantidadEntregada(item.getCantidadEntregada() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                item.setCantidadSolicitada(item.getCantidadSolicitada() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                item2.setCantidadEnviado(item2.getCantidadEnviado() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                item2.setCantidadRecibido(item2.getCantidadRecibido() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                insertado = true;
                                break;
                            }
                        }
                        if (!insertado) {
                            SurtimientoEnviado_Extend surtimientoEnviadoExtended = new SurtimientoEnviado_Extend();
                            surtimientoEnviadoExtended.setIdSurtimientoEnviado(Comunes.getUUID());
                            surtimientoEnviadoExtended.setIdSurtimientoInsumo(item.getIdSurtimientoInsumo());
                            surtimientoEnviadoExtended.setIdInventarioSurtido(this.medicamentoSelect.getIdInventario());
                            surtimientoEnviadoExtended.setLote(prescInsumoExtended.getLote());
                            surtimientoEnviadoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                            surtimientoEnviadoExtended.setCantidadEnviado(prescInsumoExtended.getCantidadEntregada());
                            surtimientoEnviadoExtended.setCantidadRecibido(prescInsumoExtended.getCantidadEntregada());
                            surtimientoEnviadoExtended.setInsertFecha(new Date());
                            surtimientoEnviadoExtended.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                            item.getSurtimientoEnviadoExtendedList().add(surtimientoEnviadoExtended);
                            item.setCantidadEntregada(item.getCantidadEntregada() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                            Integer cantidadSolicitada = 0;
                            
                            float temp = (24f / prescripcionInsumo.getFrecuencia());
                            int numeroDosisPorDia = (int) temp;
                            cantidadSolicitada = numeroDosisPorDia * dosis.intValue() * prescripcionInsumo.getDuracion();
                            
                            item.setCantidadSolicitada(item.getCantidadSolicitada() + cantidadSolicitada * this.medicamentoSelect.getCantidadXCaja());
                            insertado = true;
                        }
                        break;
                    }
                }
            }

            if (!insertado) {
                prescInsumoExtended.setIdSurtimientoInsumo(Comunes.getUUID());
                Integer cantidadSolicitada = 0;
                
                float temp = (24f / prescripcionInsumo.getFrecuencia());
                int numeroDosisPorDia = (int) temp;
                cantidadSolicitada = numeroDosisPorDia * dosis.intValue() * prescripcionInsumo.getDuracion();
                
                prescInsumoExtended.setCantidadSolicitada(cantidadSolicitada);
                insertarNuevoPrescripcionInsumoExtended(prescInsumoExtended);
            }

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcManualMedicamentoInvalido), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcManualMedicamentoInvalido), null);
        }
        obtenerSurtimientoManualDePrescripcion();
        limpiarDatosMedicamento();
        bandera = false;
    }

    public void agregarMedicamentoDetalle() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.agregarMedicamentoDetalle()");
        Inventario inventario = new Inventario();
        try {
            inventario = inventarioService.obtener(new Inventario(this.skuSap.getIdInventario()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el agregarMedicamentoDetalle: {}", ex.getMessage());
        }

        if (this.cantidadEntregada == null) {
            this.cantidadEntregada = 1;
        }
        for (SurtimientoInsumo_Extend item : this.listaInsumosPendientes) {
            if (item.getIdInsumo().equalsIgnoreCase(inventario.getIdInsumo())) {
                if (item.getSurtimientoEnviadoExtendList() == null) {
                    Integer cantSurtir = this.cantidadEntregada
                            * ((this.medicamentoSelect.getPresentacionComercial() == Constantes.ES_ACTIVO)
                            ? this.medicamentoSelect.getCantidadXCaja() : 1);

                    if (cantSurtir > (item.getCantidadSolicitada() - item.getCantidadEnviada())) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No puede sobrepasar la cantidad solicitada.", null);
                        limpiarDatosMedicamento();
                        return;
                    }
                    item.setSurtimientoEnviadoExtendList(new ArrayList<>());
                    SurtimientoEnviado_Extend surtimientoEnviadoExtended = new SurtimientoEnviado_Extend();
                    surtimientoEnviadoExtended.setIdSurtimientoEnviado(Comunes.getUUID());
                    surtimientoEnviadoExtended.setIdSurtimientoInsumo(item.getIdSurtimientoInsumo());
                    surtimientoEnviadoExtended.setIdInventarioSurtido(this.medicamentoSelect.getIdInventario());
                    surtimientoEnviadoExtended.setLote(inventario.getLote());
                    surtimientoEnviadoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                    surtimientoEnviadoExtended.setCantidadEnviado(cantSurtir);
                    surtimientoEnviadoExtended.setCantidadRecibido(cantSurtir);
                    surtimientoEnviadoExtended.setInsertFecha(new Date());
                    surtimientoEnviadoExtended.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                    item.getSurtimientoEnviadoExtendList().add(surtimientoEnviadoExtended);
                    item.setCantidadRecepcion(cantSurtir);
                } else {
                    boolean encontrado = false;
                    Integer cantSurtir = this.cantidadEntregada
                            * ((this.medicamentoSelect.getPresentacionComercial() == Constantes.ES_ACTIVO)
                            ? this.medicamentoSelect.getCantidadXCaja() : 1);

                    if ((item.getCantidadRecepcion() + cantSurtir)
                            > (item.getCantidadSolicitada() - item.getCantidadEnviada())) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No puede sobrepasar la cantidad solicitada.", null);
                        limpiarDatosMedicamento();
                        return;
                    }
                    for (SurtimientoEnviado_Extend item2 : item.getSurtimientoEnviadoExtendList()) {
                        if (item2.getLote().equalsIgnoreCase(inventario.getLote())) {
                            item2.setCantidadEnviado(cantSurtir + item2.getCantidadEnviado());
                            item2.setCantidadRecibido(cantSurtir + item2.getCantidadRecibido());
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        SurtimientoEnviado_Extend surtimientoEnviadoExtended = new SurtimientoEnviado_Extend();
                        surtimientoEnviadoExtended.setIdSurtimientoEnviado(Comunes.getUUID());
                        surtimientoEnviadoExtended.setIdSurtimientoInsumo(item.getIdSurtimientoInsumo());
                        surtimientoEnviadoExtended.setIdInventarioSurtido(item.getIdInventario());
                        surtimientoEnviadoExtended.setLote(inventario.getLote());
                        surtimientoEnviadoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                        surtimientoEnviadoExtended.setCantidadEnviado(cantSurtir);
                        surtimientoEnviadoExtended.setCantidadRecibido(cantSurtir);
                        surtimientoEnviadoExtended.setInsertFecha(new Date());
                        surtimientoEnviadoExtended.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                        item.getSurtimientoEnviadoExtendList().add(surtimientoEnviadoExtended);
                    }
                    item.setCantidadRecepcion(item.getCantidadRecepcion() + cantSurtir);
                }
                break;
            }
        }
        limpiarDatosMedicamento();
    }

    private SurtimientoEnviado_Extend obtenerSurtimientoEnviadoExtended(PrescripcionInsumo_Extended prescInsumoExtended) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerSurtimientoEnviadoExtended()");
        SurtimientoEnviado_Extend surtimientoEnviadoExtended = new SurtimientoEnviado_Extend();
        surtimientoEnviadoExtended.setIdSurtimientoEnviado(Comunes.getUUID());
        surtimientoEnviadoExtended.setIdSurtimientoInsumo(prescInsumoExtended.getIdSurtimientoInsumo());
        surtimientoEnviadoExtended.setIdInventarioSurtido(prescInsumoExtended.getIdInventario());
        surtimientoEnviadoExtended.setLote(prescInsumoExtended.getLote());
        surtimientoEnviadoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
        surtimientoEnviadoExtended.setCantidadEnviado(prescInsumoExtended.getCantidadEntregada());
        surtimientoEnviadoExtended.setCantidadRecibido(prescInsumoExtended.getCantidadEntregada());
        surtimientoEnviadoExtended.setInsertFecha(new Date());
        surtimientoEnviadoExtended.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        return surtimientoEnviadoExtended;
    }

    private PrescripcionInsumo_Extended generarPrescripcionInsumoExtended() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.generarPrescripcionInsumoExtended()");
        PrescripcionInsumo_Extended prescInsumoExtended = new PrescripcionInsumo_Extended();
        prescInsumoExtended.setIdPrescripcionInsumo(Comunes.getUUID());
        prescInsumoExtended.setIdInventario(this.medicamentoSelect.getIdInventario());
        prescInsumoExtended.setIdInsumo(this.medicamentoSelect.getIdMedicamento());
        prescInsumoExtended.setDosis(this.prescripcionInsumo.getDosis());
        prescInsumoExtended.setFrecuencia(this.prescripcionInsumo.getFrecuencia());
        prescInsumoExtended.setDuracion(this.prescripcionInsumo.getDuracion());
        prescInsumoExtended.setNombreCorto(this.medicamentoSelect.getNombreCorto());
        prescInsumoExtended.setClaveInstitucional(this.medicamentoSelect.getClaveInstitucional());
        prescInsumoExtended.setIndicaciones(this.prescripcionInsumo.getComentarios());
        prescInsumoExtended.setCantidadEntregada(this.cantidadEntregada);
        prescInsumoExtended.setCantidadXCaja(this.medicamentoSelect.getCantidadXCaja());
        prescInsumoExtended.setIdInsumo(this.medicamentoSelect.getIdMedicamento());
        prescInsumoExtended.setIdEstructura(this.medicamentoSelect.getIdEstructura());
        prescInsumoExtended.setLote(this.medicamentoSelect.getLote());
        return prescInsumoExtended;
    }

    private void insertarNuevoPrescripcionInsumoExtended(PrescripcionInsumo_Extended prescInsumoExtended) {
        this.prescripcionInsumoExtendedList.add(prescInsumoExtended);
        prescInsumoExtended.setSurtimientoEnviadoExtendedList(new ArrayList<>());
        SurtimientoEnviado_Extend surtimientoEnviadoExtended = obtenerSurtimientoEnviadoExtended(prescInsumoExtended);
        prescInsumoExtended.getSurtimientoEnviadoExtendedList().add(surtimientoEnviadoExtended);
    }

    /**
     * Valida que el medicamento que se surtirá de forma manual tenga todos los
     * campos requeridos y que no sobre pase la existencia actual
     *
     * @return
     */
    private String validarMedicamento() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validarMedicamento()");
        if (prescripcionInsumo == null) {
            return RESOURCES.getString(prcManualMedicamentoInvalido);

        } else if (prescripcionInsumo.getDosis() == null) {
            return RESOURCES.getString("prc.manual.dosis.invalido");

        } else if (prescripcionInsumo.getDosis().compareTo(BigDecimal.ONE) < 0) {
            return RESOURCES.getString("prc.manual.dosis.invalido");

        } else if (prescripcionInsumo.getFrecuencia() == null) {
            return RESOURCES.getString("prc.manual.frecuencia.invalido");

        } else if (prescripcionInsumo.getFrecuencia() < 1) {
            return RESOURCES.getString("prc.manual.frecuencia.invalido");

        } else if (prescripcionInsumo.getDuracion() == null) {
            return RESOURCES.getString("prc.manual.duracion.invalido");

        } else if (prescripcionInsumo.getDuracion() < 1) {
            return RESOURCES.getString("prc.manual.duracion.invalido");

        } else if (this.cantidadEntregada == null || this.cantidadEntregada < 0) {
            return RESOURCES.getString("prc.manual.cantEnt.invalido");

        } else if (this.cantidadEntregada > this.medicamentoSelect.getCantidadActual()) {
            return RESOURCES.getString("prc.manual.error.inventario");

        }
        return null;
    }

    public void agregarPaciente() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.agregarPaciente()");
        String msjError = validarDatosPaciente();
        boolean resp;

        if (msjError != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);

        } else {
            try {
                Paciente pacieteTemp = obtenerDatosPaciente();
                PacienteDomicilio pacienteDomTem = obtenerDatosPacienteDomicilioGenerico(pacieteTemp);
                PacienteResponsable pacienteRespTemp = new PacienteResponsable();
                List<TurnoMedico> listaTurnoTemp = obtenerTurnoGenericoPaciente(pacieteTemp);
                resp = pacienteService.insertarPaciente(pacieteTemp, pacienteDomTem, pacienteRespTemp, listaTurnoTemp);
                if (!resp) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcManualErrorRegistro), "");
                    PrimeFaces.current().ajax().addCallbackParam(paramError, true);

                } else {
                    resp = generarVisitaYServicioGenerico(pacieteTemp);
                    if (!resp) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.visita"), "");
                        PrimeFaces.current().ajax().addCallbackParam(paramError, true);

                    } else {
                        Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.paciente"), "");
                        PrimeFaces.current().ajax().addCallbackParam(paramError, false);

                    }
                }
            } catch (Exception ex) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcManualErrorRegistro), "");
                LOGGER.error(ex.getMessage());
            }
        }
    }

    private boolean generarVisitaYServicioGenerico(Paciente paciente) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.generarVisitaYServicioGenerico()");
        boolean resp = false;
        try {
// TODO: validar si paciente ya tiene una visita abierta el día actual en el servicio
            Visita visitGeneric = new Visita();
            visitGeneric.setIdVisita(Comunes.getUUID());
            visitGeneric.setIdPaciente(paciente.getIdPaciente());
            visitGeneric.setFechaIngreso(new Date());
            visitGeneric.setIdUsuarioIngresa(this.usuarioSession.getIdUsuario());
            visitGeneric.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            visitGeneric.setMotivoConsulta("");
            visitGeneric.setInsertFecha(new Date());
            visitGeneric.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

            PacienteServicio pacienteServicioGenerico = new PacienteServicio();
            pacienteServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
            pacienteServicioGenerico.setIdVisita(visitGeneric.getIdVisita());
            pacienteServicioGenerico.setIdEstructura(this.idServicio);
            pacienteServicioGenerico.setFechaAsignacionInicio(new Date());
            pacienteServicioGenerico.setIdUsuarioAsignacionInicio(this.usuarioSession.getIdUsuario());
            pacienteServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            pacienteServicioGenerico.setInsertFecha(new Date());
            pacienteServicioGenerico.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            resp = visitaService.insertarVisitaYServicioGenericos(visitGeneric, pacienteServicioGenerico);
        } catch (Exception ex) {
            resp = false;
            LOGGER.error("Error al registrar Visita y Servicio: {}", ex.getMessage());
        }
        return resp;
    }

    public void agregarMedicos() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.agregarMedicos()");
        boolean status = false;
        String resp = validarDatosMedico();
        String errorModalMedicos = "errorModalMedicos";

        if (resp != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, resp, "");
            PrimeFaces.current().ajax().addCallbackParam(errorModalMedicos, true);

        } else {
            if (this.medicoExtended == null) {
                Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.medico"), "");
                PrimeFaces.current().ajax().addCallbackParam(errorModalMedicos, false);

            } else {
                try {
                    Usuario medicoTemp = new Usuario();
                    medicoTemp.setIdUsuario(Comunes.getUUID());
                    medicoTemp.setNombre(this.medicoExtended.getNombre());
                    medicoTemp.setApellidoPaterno(this.medicoExtended.getApellidoPaterno());
                    medicoTemp.setApellidoMaterno(this.medicoExtended.getApellidoMaterno());

                    medicoTemp.setActivo(true);
                    medicoTemp.setUsuarioBloqueado(true);
                    medicoTemp.setFechaVigencia(new Date());

                    medicoTemp.setIdEstructura(this.idServicio);
                    medicoTemp.setIdTipoUsuario(TipoUsuario_Enum.MEDICO.getValue());
                    medicoTemp.setCedProfesional(this.medicoExtended.getCedProfesional());
                    medicoTemp.setCedEspecialidad(this.medicoExtended.getCedEspecialidad());
                    medicoTemp.setInsertFecha(new Date());
                    medicoTemp.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                    List<TurnoMedico> listaTemp = new ArrayList<>();

                    this.listaTurnos = this.turnoService.obtenerLista(new Turno());
                    if (this.listaIdTurnos != null && !this.listaIdTurnos.isEmpty()) {
                        for (String item : this.listaIdTurnos) {
                            TurnoMedico turnoMedico = new TurnoMedico();
                            turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                            turnoMedico.setIdTurno(Integer.valueOf(item));
                            turnoMedico.setIdMedico(medicoTemp.getIdUsuario());
                            listaTemp.add(turnoMedico);
                        }
                    }

                    status = usuarioService.insertarMedicoYTurno(medicoTemp, listaTemp);

                } catch (Exception ex) {
                    status = false;
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcManualErrorRegistro), "");
                    LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
                }
            }
        }

        if (status) {
            Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.medico"), "");
            PrimeFaces.current().ajax().addCallbackParam(errorModalMedicos, false);
        }
    }

    private String validarDatosMedico() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validarDatosMedico()");
        String resp = null;
        if (this.medicoExtended != null) {
            if (this.medicoExtended.getNombre().isEmpty()) {
                resp = RESOURCES.getString("prc.manual.nombre.medico");
                return resp;
            } else if (this.medicoExtended.getApellidoPaterno().isEmpty()) {
                resp = RESOURCES.getString("prc.manual.app.medico");
                return resp;
            } else if (this.medicoExtended.getCedProfesional().isEmpty()) {
                resp = RESOURCES.getString("prc.manual.cedula");
                return resp;
            }
        }

        return resp;
    }

    public void mostrarModalAgregarMedico() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.mostrarModalAgregarMedico()");
        try {
            this.listaIdTurnos = new ArrayList<>();
            this.tipoUserList = tipoUsuarioService.obtenerLista(new TipoUsuario());
            this.listaTurnos = this.turnoService.obtenerLista(new Turno());
            for (Turno item : this.listaTurnos) {
                this.listaIdTurnos.add("" + item.getIdTurno());
            }

        } catch (Exception ex) {
            LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
        }
    }

    public void mostrarModalEditarPrescripcion(Prescripcion_Extended prescripcion) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.mostrarModalEditarPrescripcion()");
        try {
            limpiarDatosMedicamento();
            this.listaInsumosPendientes = surtimientoInsumoService.
                    obtenerSurtimientoInsumoExtendedByIdPrescripcion(prescripcion.getIdPrescripcion());
            this.prescripcionSelected = prescripcion;
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Insumos : mostrarModalEditarPrescripcion: {}", ex.getMessage());
        }
    }

    private String validarDatosPaciente() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validarDatosPaciente()");
        String resp = null;
        if (this.idServicio == null) {
            resp = RESOURCES.getString("prc.manual.servicio.req");
            return resp;
        } else if (this.paciente.getClaveDerechohabiencia().isEmpty()) {
            resp = RESOURCES.getString("prc.manual.cve.paciente");
            return resp;
        } else if (this.paciente.getNombreCompleto().isEmpty()) {
            resp = RESOURCES.getString("prc.manual.nombre.paciente");
            return resp;
        } else if (this.paciente.getApellidoPaterno().isEmpty()) {
            resp = RESOURCES.getString("prc.manual.app.paciente");
            return resp;
        } else if (this.paciente.getSexo() == '\0' || this.paciente.getSexo() == '\u0000') {
            resp = RESOURCES.getString("prc.manual.sex.paciente");
            return resp;
        } else if (this.paciente.getFechaNacimiento() == null) {
            resp = RESOURCES.getString("prc.manual.fechaNac.paciente");
            return resp;
        } else if (this.listaIdTurnos.isEmpty()) {
            resp = RESOURCES.getString("prc.manual.turno.paciente");
            return resp;
        }
        resp = validarClavePaciente();
        return resp;
    }

    private String validarClavePaciente() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validarClavePaciente()");
        String resp = null;
        Paciente pacienteTemp = new Paciente();
        pacienteTemp.setPacienteNumero(this.paciente.getClaveDerechohabiencia());
        Paciente pacienteResp = null;
        try {
            pacienteResp = pacienteService.obtener(pacienteTemp);
        } catch (Exception ex) {
            LOGGER.error("error en el metodo :: validarClavePaciente: {}", ex.getMessage());
        }
        if (pacienteResp != null) {
            resp = RESOURCES.getString("prc.manual.clave.paciente");
        }
        return resp;
    }

    private String generarPacNumero() {
        Date fecha = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("_" + "HH:mm");
        return sdf.format(fecha);
    }

    private Paciente obtenerDatosPaciente() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerDatosPaciente()");
        Paciente pacienteTemp = new Paciente();
        pacienteTemp.setNombreCompleto(this.paciente.getNombreCompleto());
        pacienteTemp.setApellidoMaterno(this.paciente.getApellidoMaterno());
        pacienteTemp.setApellidoPaterno(this.paciente.getApellidoPaterno());
        if (this.permiteSurtimientoManualHospitalizacion) {
            pacienteTemp.setPacienteNumero(paciente.getClaveDerechohabiencia() + generarPacNumero());
        }
        pacienteTemp.setClaveDerechohabiencia(this.paciente.getClaveDerechohabiencia());
        pacienteTemp.setCurp(Constantes.TXT_VACIO);
        pacienteTemp.setFechaNacimiento(this.paciente.getFechaNacimiento());
        pacienteTemp.setIdEscolaridad(CatalogoGeneral_Enum.ESCOLARIDAD_NO_DEFINIDA.getValue());
        pacienteTemp.setIdEstadoCivil(CatalogoGeneral_Enum.ESTADO_CIVIL_NO_DEFINIDO.getValue());
        pacienteTemp.setIdEstatusPaciente(EstatusPaciente_Enum.REGISTRADO.getValue());
        pacienteTemp.setIdGrupoEtnico(CatalogoGeneral_Enum.GRUPO_ETNICO_NO_DEFINIDO.getValue());
        pacienteTemp.setIdGrupoSanguineo(CatalogoGeneral_Enum.GRUPO_SANGUINEO_NO_DEFINIDO.getValue());
        pacienteTemp.setIdNivelSocioEconomico(CatalogoGeneral_Enum.NIVEL_SOCIOECONOMICO_NO_DEFINIDO.getValue());
        pacienteTemp.setIdOcupacion(CatalogoGeneral_Enum.OCUPACION_NO_DEFINIDA.getValue());
        pacienteTemp.setIdPaciente(Comunes.getUUID());
        pacienteTemp.setIdReligion(CatalogoGeneral_Enum.RELIGION_NO_DEFINIDA.getValue());
        pacienteTemp.setIdTipoPaciente(this.paciente.getIdTipoPaciente());
        pacienteTemp.setIdTipoVivienda(CatalogoGeneral_Enum.TIPO_VIVIENDA_NO_DEFINIDA.getValue());
        pacienteTemp.setIdUnidadMedica(CatalogoGeneral_Enum.UNIDAD_MEDICA_OTRA.getValue());
        pacienteTemp.setInsertFecha(new Date());
        pacienteTemp.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        pacienteTemp.setSexo(this.paciente.getSexo());
        return pacienteTemp;
    }    

    private PacienteDomicilio obtenerDatosPacienteDomicilioGenerico(Paciente paciente) {
        PacienteDomicilio pacienteDomicilio = new PacienteDomicilio();
        pacienteDomicilio.setIdPaciente(paciente.getIdPaciente());
        pacienteDomicilio.setIdPacienteDomicilio(Comunes.getUUID());
        pacienteDomicilio.setCalle(Constantes.NO_DEFINIDO);
        pacienteDomicilio.setNumeroExterior(Constantes.NO_DEFINIDO);
        pacienteDomicilio.setTelefonoCasa(Constantes.NO_DEFINIDO);
        pacienteDomicilio.setIdPais(Constantes.ID_PAIS_MEXICO);
        pacienteDomicilio.setInsertFecha(new Date());
        pacienteDomicilio.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        return pacienteDomicilio;
    }

    private List<TurnoMedico> obtenerTurnoGenericoPaciente(Paciente paciente) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerTurnoGenericoPaciente()");
        List<TurnoMedico> listaTemp = new ArrayList<>();
        if (this.listaIdTurnos != null && !this.listaIdTurnos.isEmpty()) {
            for (String item : this.listaIdTurnos) {
                TurnoMedico turnoMedico = new TurnoMedico();
                turnoMedico.setIdTurnoMedico(Comunes.getUUID());
                turnoMedico.setIdTurno(Integer.valueOf(item));
                turnoMedico.setIdMedico(paciente.getIdPaciente());
                listaTemp.add(turnoMedico);
            }

        }
        return listaTemp;
    }

    public void mostrarModalAgregarPaciente() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.mostrarModalAgregarPaciente()");
        try {
            limpiarDatosPaciente();
            this.listaTurnos = this.turnoService.obtenerLista(new Turno());
            Turno item = this.listaTurnos.get(0);
            this.listaIdTurnos = new ArrayList<>();
            this.listaIdTurnos.add("" + item.getIdTurno());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    private List<PrescripcionInsumo_Extended> listaPrescripcionInsumoExtendedValidacion;
    private Integer numeroMedicamentosConDiferencias;

    public List<PrescripcionInsumo_Extended> getListaPrescripcionInsumoExtendedValidacion() {
        return listaPrescripcionInsumoExtendedValidacion;
    }

    public void setListaPrescripcionInsumoExtendedValidacion(List<PrescripcionInsumo_Extended> listaPrescripcionInsumoExtendedValidacion) {
        this.listaPrescripcionInsumoExtendedValidacion = listaPrescripcionInsumoExtendedValidacion;
    }

    public Integer getNumeroMedicamentosConDiferencias() {
        return numeroMedicamentosConDiferencias;
    }

    public void setNumeroMedicamentosConDiferencias(Integer numeroMedicamentosConDiferencias) {
        this.numeroMedicamentosConDiferencias = numeroMedicamentosConDiferencias;
    }

    public void validacionRecetaManual() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validacionRecetaManual()");
        boolean resp = false;
        listaPrescripcionInsumoExtendedValidacion = new ArrayList<>();
        for (PrescripcionInsumo_Extended item : prescripcionInsumoExtendedList) {
            if (!Objects.equals(item.getCantidadEntregada(), item.getCantidadSolicitada())) {
                listaPrescripcionInsumoExtendedValidacion.add(item);
                resp = true;
            }
        }
        this.numeroMedicamentosConDiferencias = listaPrescripcionInsumoExtendedValidacion.size();
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);
    }

    /**
     * REgistra un surtimiento manual ligado a una prescripción
     */
    public void insertarPrescripcion() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.insertarPrescripcion()");
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
            return;
        }
        boolean resp = false;
        String msjError = validarPrescripcion();
        if (msjError != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
            return;
        }
        Prescripcion prescripcionTemp = obtenerDatosPrescripcion();

        Surtimiento_Extend surtimientoTemp = obtenerDatosSurtimiento(prescripcionTemp);

        List<DiagnosticoPaciente> listaDiagnosticoPaciente = obtenerListaDiagnosticosPaciente(prescripcionTemp);

        List<PrescripcionInsumo> listaPrescripcionInsumo = obtenerListaPrescripcionInsumo(prescripcionTemp);

        List<SurtimientoInsumo_Extend> listaSurtimientoInsumo = obtenerListaSurtimientoInsumo(surtimientoTemp);

        List<InventarioExtended> listaInventario = obtenerListaInventarios(this.prescripcionInsumoExtendedList);

        List<MovimientoInventario> listaMovInventarios = obtenerListaMovInventarios(listaInventario);

        //List<SurtimientoEnviado> listaSurtimientoEnviado = obtenerListaSurtimientoEnviado(
        try {
            resp = this.prescripcionService.registrarPrescripcionManual(
                    prescripcionTemp,
                    surtimientoTemp,
                    listaDiagnosticoPaciente,
                    listaPrescripcionInsumo,
                    listaSurtimientoInsumo,
                    listaInventario,
                    this.prescripcionInsumoExtendedList,
                    listaMovInventarios);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        if (resp) {
            try {
                imprimirTcket(surtimientoTemp);
                Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.exito.surtimiento"), "");
                init();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);

    }

    /**
     * Valida los datos básicos del susrtimiento Manual de Prescripción
     *
     * @return
     */
    private String validarPrescripcion() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validarPrescripcion()");
        String resp;
        String prcVisita = "prc.visita";
        String prcServicio = "prc.servicio";

        if (this.fechaReceta == null) {
            return RESOURCES.getString("prc.manual.req.fecha");

        } else if (this.folioPrescripcion == null || this.folioPrescripcion.isEmpty()) {
            return RESOURCES.getString("prc.manual.req.folio");

        } else if (this.idServicio == null) {
            return RESOURCES.getString("prc.manual.servicio.req");

        } else if (this.pacienteExtended == null) {
            return RESOURCES.getString("prc.manual.req.paciente");

        } else if (this.medico == null) {
            return RESOURCES.getString("prc.manual.req.medico");

        } else if (this.diagnosticoList.isEmpty()) {
            return RESOURCES.getString("prc.manual.req.diagnostico");

        }

        try {
            this.visita = visitaService.obtener(new Visita(this.pacienteExtended.getIdPaciente()));
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcVisita), ex);
        }

        try {
            if (this.visita != null) {
                this.pacienteServicio = pacienteServicioService.obtener(new PacienteServicio(this.visita.getIdVisita()));
            } else {
                this.pacienteServicio = null;
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcServicio), ex);
        }

        if (this.visita == null) {
            crearVisitaGenericaYPacienteServicioGenerico();

        } else if (this.pacienteServicio == null) {
            crearPacienteServicioGenerico(this.visita);

        }

        if (this.visita == null) {
            return RESOURCES.getString(prcVisita);

        } else if (this.visita.getIdVisita() == null) {
            return RESOURCES.getString(prcVisita);

        } else if (this.pacienteServicio == null) {
            return RESOURCES.getString(prcServicio);

        } else if (this.pacienteServicio.getIdPacienteServicio() == null) {
            return RESOURCES.getString(prcServicio);

        } else if (this.prescripcionInsumoExtendedList.isEmpty()) {
            return RESOURCES.getString("prc.manual.req.medicamento");

        }

        resp = validarFolioReceta();
        return resp;
    }

    private void crearVisitaGenericaYPacienteServicioGenerico() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.crearVisitaGenericaYPacienteServicioGenerico()");
        Visita visiGenerica = new Visita();
        visiGenerica.setIdVisita(Comunes.getUUID());
        visiGenerica.setIdPaciente(this.pacienteExtended.getIdPaciente());
        visiGenerica.setFechaIngreso(new Date());
        visiGenerica.setIdUsuarioIngresa(this.usuarioSession.getIdUsuario());
        visiGenerica.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        visiGenerica.setMotivoConsulta("Visita Generica");
        visiGenerica.setInsertFecha(new Date());
        visiGenerica.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        PacienteServicio pacServicioGenerico = new PacienteServicio();
        pacServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
        pacServicioGenerico.setIdVisita(visiGenerica.getIdVisita());
        pacServicioGenerico.setIdEstructura(this.idServicio);
        pacServicioGenerico.setFechaAsignacionInicio(new Date());
        pacServicioGenerico.setIdUsuarioAsignacionInicio(this.usuarioSession.getIdUsuario());
        pacServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        pacServicioGenerico.setInsertFecha(new Date());
        pacServicioGenerico.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        Paciente pacienteGenerico = new Paciente();
        pacienteGenerico.setIdPaciente(this.pacienteExtended.getIdPaciente());
        pacienteGenerico.setIdEstatusPaciente(
                EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
        pacienteGenerico.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        pacienteGenerico.setIdEstructura(this.idServicio);
        pacienteGenerico.setUpdateFecha(new Date());
        pacienteGenerico.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
        try {
            boolean resp = visitaService.registrarVisitaPaciente(
                    visiGenerica, pacServicioGenerico, pacienteGenerico);
            if (resp) {
                this.visita = visiGenerica;
                this.pacienteServicio = pacServicioGenerico;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al crear la visita : {}", ex.getMessage());
        }
    }

    private void crearPacienteServicioGenerico(Visita visitaGenerica) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.crearPacienteServicioGenerico()");
        PacienteServicio pacienteServicioGenerico = new PacienteServicio();
        pacienteServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
        pacienteServicioGenerico.setIdVisita(visitaGenerica.getIdVisita());
        pacienteServicioGenerico.setIdEstructura(this.idServicio);
        pacienteServicioGenerico.setFechaAsignacionInicio(new Date());
        pacienteServicioGenerico.setIdUsuarioAsignacionInicio(this.usuarioSession.getIdUsuario());
        pacienteServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        pacienteServicioGenerico.setInsertFecha(new Date());
        pacienteServicioGenerico.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        Paciente pacienteGenerico = new Paciente();
        pacienteGenerico.setIdPaciente(this.pacienteExtended.getIdPaciente());
        pacienteGenerico.setIdEstatusPaciente(
                EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
        pacienteGenerico.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        pacienteGenerico.setIdEstructura(this.idServicio);
        pacienteGenerico.setUpdateFecha(new Date());
        pacienteGenerico.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
        try {
            boolean resp = this.pacienteServicioService.registrarPacienteServicio(
                    pacienteServicioGenerico, pacienteGenerico);
            if (resp) {
                this.pacienteServicio = pacienteServicioGenerico;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al crear la visita : {}", ex.getMessage());
        }
    }

    private Prescripcion obtenerDatosPrescripcion() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerDatosPrescripcion()");
        Prescripcion prescripcion = new Prescripcion();
        prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        prescripcion.setIdPrescripcion(Comunes.getUUID());
        //TODO De donde se saca la estructura de la prescripcion
        prescripcion.setIdEstructura(Constantes.IDESTRUCTURA_CONSULTA_EXTERNA_HOSPITAL);
        prescripcion.setIdPacienteServicio(this.pacienteServicio.getIdPacienteServicio());
        prescripcion.setFolio(this.folioPrescripcion);
        prescripcion.setFechaPrescripcion(this.fechaReceta);
        prescripcion.setFechaFirma(new java.util.Date());
        prescripcion.setTipoConsulta(TipoConsulta_Enum.CONSULTA_INTERNA.getValue());
        prescripcion.setTipoPrescripcion(TipoPrescripcion_Enum.MANUAL.getValue());
        prescripcion.setIdMedico(this.medico.getIdUsuario());
        prescripcion.setRecurrente(false);
        prescripcion.setComentarios(this.prescripcionInsumo.getComentarios());
        prescripcion.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        prescripcion.setInsertFecha(new java.util.Date());
        prescripcion.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        prescripcion.setIdEntidadHospitalaria(
                (this.entidadHospitalaria != null) ? this.entidadHospitalaria.getIdEntidadHospitalaria() : null);
        return prescripcion;
    }

    public Surtimiento_Extend obtenerDatosSurtimiento(Prescripcion prescripcion) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerDatosSurtimiento()");
        Surtimiento_Extend surtimientoExteded = new Surtimiento_Extend();
        Paciente pacienteTemp = null;
        Usuario medicoTemp = null;
        CamaExtended camaExtended = null;
        try {
            pacienteTemp = pacienteService.obtenerPacienteByIdPaciente(this.pacienteExtended.getIdPaciente());
            medicoTemp = usuarioService.obtenerUsuarioPorId(this.medico.getIdUsuario());
            camaExtended = camaservice.obtenerCamaNombreEstructura(this.pacienteExtended.getIdPaciente());
        } catch (Exception ex) {
            LOGGER.error("Error al obtenerDatosSurtimiento : {}", ex.getMessage());
        }
        surtimientoExteded.setIdSurtimiento(Comunes.getUUID());
        surtimientoExteded.setIdEstructuraAlmacen(this.usuarioSession.getIdEstructura());
        surtimientoExteded.setIdPrescripcion(prescripcion.getIdPrescripcion());
        surtimientoExteded.setFechaProgramada(new Date());
        surtimientoExteded.setFolio(Comunes.generaNumeroReceta());
        surtimientoExteded.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
        surtimientoExteded.setInsertFecha(new Date());
        surtimientoExteded.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        if (pacienteTemp != null) {
            switch (pacienteTemp.getSexo()) {
                case 'M':
                    surtimientoExteded.setPacienteSexo("Masculino");
                    break;
                case 'F':
                    surtimientoExteded.setPacienteSexo("Femenino");
                    break;
                default:
                    surtimientoExteded.setPacienteSexo("Otro");
            }
            surtimientoExteded.setClaveDerechohabiencia(pacienteTemp.getClaveDerechohabiencia());
            surtimientoExteded.setNombrePaciente(pacienteTemp.getNombreCompleto()
                    + " " + pacienteTemp.getApellidoPaterno()
                    + " " + pacienteTemp.getApellidoMaterno());
        }
        if (medicoTemp != null) {
            surtimientoExteded.setNombreMedico(medicoTemp.getNombre() + " " + medicoTemp.getApellidoPaterno() + " " + medicoTemp.getApellidoMaterno());
            surtimientoExteded.setCedProfesional(medicoTemp.getCedProfesional());
        }
        if (camaExtended != null) {
            surtimientoExteded.setCama(camaExtended.getNombreCama());
        }
        return surtimientoExteded;

    }

    public Surtimiento_Extend obtenerDatosSurtimientoChiconcuac(Prescripcion prescripcion) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerDatosSurtimientoChiconcuac()");
        Surtimiento_Extend surtimiento = new Surtimiento_Extend();
        Paciente pacienteTemp = null;
        Usuario medicoTemp = null;
        try {
            pacienteTemp = pacienteService.obtenerPacienteByIdPaciente(this.pacienteExtended.getIdPaciente());
            medicoTemp = usuarioService.obtenerUsuarioPorId(this.medico.getIdUsuario());
        } catch (Exception ex) {
            LOGGER.error("Error al obtenerDatosSurtimiento : {}", ex.getMessage());
        }
        surtimiento.setIdSurtimiento(Comunes.getUUID());
        surtimiento.setIdEstructuraAlmacen(this.usuarioSession.getIdEstructura());
        surtimiento.setIdPrescripcion(prescripcion.getIdPrescripcion());
        surtimiento.setFechaProgramada(new Date());
        surtimiento.setFolio(Comunes.generaNumeroReceta());
        surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());

        surtimiento.setInsertFecha(new Date());
        surtimiento.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        surtimiento.setFolioPrescripcion(this.folioPrescripcion);

        if (pacienteTemp != null) {
            surtimiento.setClaveDerechohabiencia(pacienteTemp.getClaveDerechohabiencia());
            surtimiento.setNombrePaciente(pacienteTemp.getNombreCompleto()
                    + " " + pacienteTemp.getApellidoPaterno()
                    + " " + pacienteTemp.getApellidoMaterno());
        }
        if (medicoTemp != null) {
            surtimiento.setNombreMedico(medicoTemp.getNombre() + " " + medicoTemp.getApellidoPaterno() + " " + medicoTemp.getApellidoMaterno());
        }
        return surtimiento;

    }

    private List<DiagnosticoPaciente> obtenerListaDiagnosticosPaciente(Prescripcion prescripcion) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerListaDiagnosticosPaciente()");
        List<DiagnosticoPaciente> diagnosticoPacienteList = new ArrayList<>();
        DiagnosticoPaciente diagnosticoPaciente;
        for (Diagnostico item : this.diagnosticoList) {
            diagnosticoPaciente = new DiagnosticoPaciente();
            diagnosticoPaciente.setIdDiagnosticoPaciente(Comunes.getUUID());
            diagnosticoPaciente.setIdPrescripcion(prescripcion.getIdPrescripcion());
            diagnosticoPaciente.setFechaDiagnostico(new java.util.Date());
            diagnosticoPaciente.setIdUsuarioDiagnostico(this.pacienteExtended.getIdPaciente());
            diagnosticoPaciente.setIdDiagnostico(item.getIdDiagnostico());
            diagnosticoPaciente.setFechaFinDiagnostico(null);
            diagnosticoPaciente.setIdUsuarioDiagnosticoTratado(null);
            diagnosticoPaciente.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
            diagnosticoPaciente.setInsertFecha(new java.util.Date());
            diagnosticoPaciente.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            diagnosticoPaciente.setUpdateFecha(null);
            diagnosticoPaciente.setUpdateIdUsuario(null);
            diagnosticoPacienteList.add(diagnosticoPaciente);
        }
        return diagnosticoPacienteList;
    }

    private List<PrescripcionInsumo> obtenerListaPrescripcionInsumo(Prescripcion prescripcion) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerListaPrescripcionInsumo()");
        List<PrescripcionInsumo> prescInsumoList = new ArrayList<>();
        PrescripcionInsumo prescInsumo;
        for (PrescripcionInsumo_Extended item : this.prescripcionInsumoExtendedList) {
            prescInsumo = new PrescripcionInsumo();
            prescInsumo.setIdPrescripcionInsumo(item.getIdPrescripcionInsumo());
            prescInsumo.setIdPrescripcion(prescripcion.getIdPrescripcion());
            prescInsumo.setIdInsumo(item.getIdInsumo());
            prescInsumo.setFechaInicio(new Date());
            prescInsumo.setDosis(item.getDosis());
            prescInsumo.setFrecuencia(item.getFrecuencia());
            prescInsumo.setDuracion(item.getDuracion());
            prescInsumo.setComentarios(null);
            prescInsumo.setIdEstatusPrescripcion(prescripcion.getIdEstatusPrescripcion());
            prescInsumo.setIndicaciones(item.getIndicaciones());
            prescInsumo.setInsertFecha(new Date());
            prescInsumo.setInsertIdUsuario(this.pacienteExtended.getIdPaciente());
            prescInsumo.setUpdateFecha(null);
            prescInsumo.setUpdateIdUsuario(null);
            prescInsumoList.add(prescInsumo);
        }
        return prescInsumoList;
    }

    private List<SurtimientoInsumo_Extend> obtenerListaSurtimientoInsumo(Surtimiento surtimiento) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerListaSurtimientoInsumo()");
        List<SurtimientoInsumo_Extend> listaSurtimientoInsumo = new ArrayList<>();
        for (PrescripcionInsumo_Extended item : this.prescripcionInsumoExtendedList) {
            SurtimientoInsumo_Extend surtimientoInsumo = new SurtimientoInsumo_Extend();
            surtimientoInsumo.setIdSurtimientoInsumo(item.getIdSurtimientoInsumo());
            surtimientoInsumo.setIdSurtimiento(surtimiento.getIdSurtimiento());
            surtimientoInsumo.setIdPrescripcionInsumo(item.getIdPrescripcionInsumo());
            surtimientoInsumo.setFechaEnviada(new Date());
            surtimientoInsumo.setIdUsuarioEnviada(this.usuarioSession.getIdUsuario());
            surtimientoInsumo.setCantidadEnviada(item.getCantidadEntregada());
            BigDecimal dosis = item.getDosis().setScale(0, RoundingMode.CEILING);
            surtimientoInsumo.setCantidadSolicitada((24 / item.getFrecuencia()) * dosis.intValue() * item.getDuracion());
            surtimientoInsumo.setFechaProgramada(new Date());
            surtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
            surtimientoInsumo.setInsertFecha(new Date());
            surtimientoInsumo.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            surtimientoInsumo.setIdInventario(item.getIdInventario());
            listaSurtimientoInsumo.add(surtimientoInsumo);
        }
        return listaSurtimientoInsumo;
    }

    private List<InventarioExtended> obtenerListaInventarios(List<PrescripcionInsumo_Extended> listaPrescripcionInsumoExtended) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerListaInventarios()");
        List<InventarioExtended> listaInvetario = new ArrayList<>();
        for (PrescripcionInsumo_Extended item : listaPrescripcionInsumoExtended) {
            for (SurtimientoEnviado_Extend item2 : item.getSurtimientoEnviadoExtendedList()) {
                InventarioExtended inventario = new InventarioExtended();
                inventario.setIdInventario(item2.getIdInventarioSurtido());
                inventario.setCantidadEntregada(item2.getCantidadEnviado());
                inventario.setIdInsumo(item.getIdInsumo());
                inventario.setIdEstructura(item.getIdEstructura());
                inventario.setLote(item2.getLote());
                inventario.setCantidadXCaja(item.getCantidadXCaja());
                inventario.setUpdateFecha(new Date());
                inventario.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                listaInvetario.add(inventario);
            }
        }
        return listaInvetario;
    }

    private List<MovimientoInventario> obtenerListaMovInventarios(List<InventarioExtended> listaInventario) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerListaMovInventarios()");
        List<MovimientoInventario> listaMovimientoInventario = new ArrayList<>();
        for (InventarioExtended item : listaInventario) {
            MovimientoInventario movimientoInve = new MovimientoInventario();
            movimientoInve.setIdMovimientoInventario(Comunes.getUUID());
            movimientoInve.setIdTipoMotivo(20);
            movimientoInve.setFecha(new Date());
            movimientoInve.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
            movimientoInve.setIdEstrutcuraOrigen(item.getIdEstructura());
            movimientoInve.setIdEstrutcuraDestino(item.getIdEstructura());
            movimientoInve.setIdInventario(item.getIdInventario());
            movimientoInve.setCantidad(item.getCantidadEntregada());
            movimientoInve.setFolioDocumento(this.folioPrescripcion);
            listaMovimientoInventario.add(movimientoInve);
        }
        return listaMovimientoInventario;
    }

    public void eliminarDiagnostico(String idDiagnostico) {
        for (short i = 0; i < this.diagnosticoList.size(); i++) {
            Diagnostico diagnosticoTemp = this.diagnosticoList.get(i);
            if (diagnosticoTemp.getIdDiagnostico().equalsIgnoreCase(idDiagnostico)) {
                this.diagnosticoList.remove(i);
                break;
            }
        }
    }

    public void eliminarEntidadHospitalaria(String idEntidadHospitalaria) {
        for (short i = 0; i < this.entidadList.size(); i++) {
            EntidadHospitalaria entidadHosp = this.entidadList.get(i);
            if (entidadHosp.getIdEntidadHospitalaria().equalsIgnoreCase(idEntidadHospitalaria)) {
                this.entidadList.remove(i);
                break;
            }
        }
    }

    public void eliminarMedicamento(String idMedicamento) {
        for (short i = 0; i < this.prescripcionInsumoExtendedList.size(); i++) {
            PrescripcionInsumo_Extended insumoExtended = this.prescripcionInsumoExtendedList.get(i);
            if (insumoExtended.getIdInsumo().equalsIgnoreCase(idMedicamento)) {
                this.prescripcionInsumoExtendedList.remove(i);
                break;
            }
        }
    }

    public void imprimirTcket(Surtimiento_Extend surtimientoExtendedSelected) throws Exception {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.imprimirTcket()");
        boolean status = Constantes.INACTIVO;
        try {
            RepSurtimientoPresc repSurtimientoPresc = new RepSurtimientoPresc();
            repSurtimientoPresc.setFolioPrescripcion(surtimientoExtendedSelected.getFolioPrescripcion());
            repSurtimientoPresc.setFolioSurtimiento(surtimientoExtendedSelected.getFolio());
            repSurtimientoPresc.setFechaActual(new Date());
            repSurtimientoPresc.setNombrePaciente(surtimientoExtendedSelected.getNombrePaciente());
            repSurtimientoPresc.setClavePaciente(surtimientoExtendedSelected.getClaveDerechohabiencia());
            repSurtimientoPresc.setSexo(surtimientoExtendedSelected.getPacienteSexo());
            listaServicios.forEach(serv -> {
                if (serv.getIdEstructura().equals(idServicio)) {
                    nameService = serv.getNombre();
                }
            });
            repSurtimientoPresc.setServicio(nameService);
            repSurtimientoPresc.setCama(surtimientoExtendedSelected.getCama());
            repSurtimientoPresc.setFechaSolicitado(new Date());
            repSurtimientoPresc.setFechaAtendido(new Date());
            repSurtimientoPresc.setTurno(surtimientoExtendedSelected.getTurno());
            repSurtimientoPresc.setNombreMedico(surtimientoExtendedSelected.getNombreMedico());
            repSurtimientoPresc.setCedulaMedico(surtimientoExtendedSelected.getCedProfesional());

            String rutaTicket = (System.getProperty("os.name").toLowerCase().contains("windows")) ? "\\ticket" : "/ticket";
            pathTmp = dirTmp.getPath() + rutaTicket + surtimientoExtendedSelected.getFolio() + ".pdf";
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null,
                    ext.getRequestServerName(),
                    ext.getRequestServerPort(),
                    ext.getRequestContextPath(),
                    null,
                    null);
            String url = uri.toASCIIString();
            boolean res = reportesService.imprimeSurtPresManualHosp(
                    repSurtimientoPresc,
                    pathTmp, url, EstatusSurtimiento_Enum.SURTIDO.toString(), prescripcionInsumoExtendedList.size());

            if (res) {
                status = Constantes.ACTIVO;
                archivo = url + "/resources/tmp/ticket" + surtimientoExtendedSelected.getFolio() + ".pdf";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void imprimirTcketSurtimientoManual(Prescripcion prescripcion,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.imprimirTcketSurtimientoManual()");
        boolean status = Constantes.INACTIVO;
        try {
            this.entidadHospitalaria = this.entidadHospitalariaService.obtener(new EntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeSurtimientoPrescManualChiconcuac(
                    prescripcion,
                    this.entidadHospitalaria,
                    surtimientoExtendedSelected,
                    nombreUsuario);

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket_%s.pdf", surtimientoExtendedSelected.getFolio()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void onNodeSelect(NodeSelectEvent event) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.onNodeSelect()");
        nameUnidad = event.getTreeNode().toString();
        listNode = new ArrayList<>();
        obtenerParentNode(event.getTreeNode());
        pathNode = "";
        String auxil = "";
        for (int i = listNode.size() - 1; i >= 0; i--) {
            auxil = i == 0 ? "" : "/";
            pathNode += listNode.get(i) + auxil;
        }
        if (pathNode.length() > 200) {
            pathNode = pathNode.substring(0, 200);
        }
        if (medicoExtended != null) {
            medicoExtended.setPathEstructura(pathNode);
        }
    }

    private void obtenerParentNode(TreeNode nodo) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.obtenerParentNode()");
        TreeNode node = nodo.getParent();
        if (node != null && node.getData() != "Root") {
            listNode.add(nodo.getParent().toString());
            obtenerParentNode(node);
        }
    }

    public void validarFolioRecetaOnBlur() {
        String resp = validarFolioReceta();
        if (resp != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, resp, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
        }
    }

    public String validarFolioReceta() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validarFolioReceta()");
        String resp = null;
        Prescripcion presc = new Prescripcion();
        presc.setFolio(this.folioPrescripcion);

        try {
            presc = prescripcionService.obtener(presc);
        } catch (Exception ex) {
            LOGGER.error("Error al validar el folio de la prescripción: {}", ex.getMessage());
        }
        if (presc != null) {
            resp = RESOURCES.getString("prc.manual.req.folio.exist");
        }

        return resp;
    }

    /**
     * Valida el proceso de cancelación
     *
     * @param idPrescripcion
     */
    public void validaCancelarPrescripcion(String idPrescripcion) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.validaCancelarPrescripcion()");
        boolean status = Constantes.INACTIVO;
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (idPrescripcion == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);

        } else {
            try {
                Prescripcion p = new Prescripcion();
                p.setIdPrescripcion(idPrescripcion);

                Prescripcion prescripSelected = prescripcionService.obtener(p);

                if (prescripSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);

                } else {
                    status = cancelarPrescripcion(idPrescripcion);
                }

            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
            }
        }
        obtenerSurtimientoManualDePrescripcion();
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Cancela una prescripción así como todos los surtimientos dependientes de
     * esta prescripción y todos medicamentos de la prescripción
     *
     * @return
     */
    private boolean cancelarPrescripcion(String idPrescripcion) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.cancelarPrescripcion()");
        boolean res = Constantes.INACTIVO;
        try {
            if (this.permiteSurtimientoManualHospitalizacion) {
                Prescripcion p = new Prescripcion();
                p.setIdPrescripcion(idPrescripcion);

                Prescripcion prescripSelected = prescripcionService.obtener(p);

                Prescripcion folioTemp = prescripcionService.verificarFolioCancelar(prescripSelected.getFolio() + "C");
                if (folioTemp == null) {
                    prescripSelected.setFolio(prescripSelected.getFolio() + "C");
                } else {
                    prescripSelected.setFolio(generaFolCancelacion(folioTemp.getFolio()));
                }

                res = prescripcionService.cancelarPrescripcionChiconcuac(prescripSelected,
                        this.usuarioSession.getIdUsuario(), new java.util.Date(),
                        EstatusPrescripcion_Enum.CANCELADA.getValue(),
                        EstatusGabinete_Enum.CANCELADA.getValue(),
                        EstatusSurtimiento_Enum.CANCELADO.getValue());
            } else {
                res = prescripcionService.cancelarPrescripcion(idPrescripcion,
                        this.usuarioSession.getIdUsuario(), new java.util.Date(),
                        EstatusPrescripcion_Enum.CANCELADA.getValue(),
                        EstatusGabinete_Enum.CANCELADA.getValue(),
                        EstatusSurtimiento_Enum.CANCELADO.getValue());
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
        }
        return res;
    }

    public String generaFolCancelacion(String folio) {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.generaFolCancelacion()");
        char[] array = folio.toCharArray();
        boolean cons = false;
        String aux0 = "0";
        String aux1 = "";
        for (int j = 1; j < folio.length(); j++) {
            if (cons) {
                aux0 += array[j];
            }
            if (!Character.isDigit(array[j])) {
                cons = true;
            } else if (!cons) {
                aux1 += array[j];
            }

        }
        BigInteger secuencia1 = new BigInteger(aux0);
        secuencia1 = secuencia1.add(BigInteger.ONE);
        return array[0] + aux1 + "C" + secuencia1;
    }

    public void surtirPrescripcionParcial() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualHospMB.surtirPrescripcionParcial()");
        boolean resp = false;
        boolean surtParcial = false;
        List<SurtimientoInsumo_Extend> listaSurtimientoInsumo = this.listaInsumosPendientes;
        List<MovimientoInventario> listaMovimientos = new ArrayList<>();
        List<Inventario> listaInv = new ArrayList<>();

        Paciente pacienteTemp = new Paciente();
        try {
            pacienteTemp = pacienteService.obtenerPacienteByIdPaciente(prescripcionSelected.getIdPaciente());
        } catch (Exception e) {
            LOGGER.error(RESOURCES.getString(prcNopuedecancelar), e);
        }

        Surtimiento_Extend surtimiento = new Surtimiento_Extend();
        surtimiento.setIdSurtimiento(Comunes.getUUID());
        surtimiento.setIdEstructuraAlmacen(this.usuarioSession.getIdEstructura());
        surtimiento.setIdPrescripcion(this.prescripcionSelected.getIdPrescripcion());
        surtimiento.setFechaProgramada(this.prescripcionSelected.getFechaPrescripcion());
        surtimiento.setFolio(Comunes.generaNumeroReceta());
        surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
        surtimiento.setInsertFecha(new Date());
        surtimiento.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        surtimiento.setClaveDerechohabiencia(pacienteTemp.getClaveDerechohabiencia());

        for (SurtimientoInsumo_Extend item : listaSurtimientoInsumo) {
            Integer duSolicitada = item.getCantidadSolicitada() - item.getCantidadEnviada();

            item.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            item.setIdSurtimiento(surtimiento.getIdSurtimiento());
            item.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
            item.setFechaEnviada(new Date());
            item.setIdUsuarioEnviada(this.usuarioSession.getIdUsuario());
            item.setIdUsuarioRecepcion(this.usuarioSession.getIdUsuario());
            item.setCantidadEnviada(item.getCantidadRecepcion());
            item.setCantidadSolicitada(duSolicitada);

            if (item.getSurtimientoEnviadoExtendList() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Tiene que surtir todos los medicamentos", null);
                PrimeFaces.current().ajax().addCallbackParam(paramError, true);
                return;
            }
            for (SurtimientoEnviado_Extend item2 : item.getSurtimientoEnviadoExtendList()) {
                Inventario inventario = new Inventario();
                inventario.setIdInventario(item2.getIdInventarioSurtido());
                inventario.setCantidadActual(item2.getCantidadRecibido());
                listaInv.add(inventario);

                MovimientoInventario movimientoInventario = new MovimientoInventario();
                movimientoInventario.setIdMovimientoInventario(Comunes.getUUID());                
                movimientoInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_AJUSTE_INVENTARIO.getMotivoValue());
                movimientoInventario.setFecha(new Date());
                movimientoInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                //TODO falta poner estructura origen con estructura de paciente
                movimientoInventario.setIdEstrutcuraOrigen(this.usuarioSession.getIdEstructura());
                movimientoInventario.setIdEstrutcuraDestino(this.usuarioSession.getIdEstructura());
                movimientoInventario.setIdInventario(item2.getIdInventarioSurtido());
                movimientoInventario.setCantidad(item2.getCantidadRecibido());
                movimientoInventario.setFolioDocumento(this.prescripcionSelected.getFolio());
                listaMovimientos.add(movimientoInventario);
            }
        }

        for (SurtimientoInsumo_Extend item : listaSurtimientoInsumo) {
            Integer duSolicitada = item.getCantidadSolicitada() - item.getCantidadEnviada();
            if (duSolicitada != 0) {
                surtParcial = true;
                break;
            }
        }

        Prescripcion prescripcion = new Prescripcion();
        prescripcion.setIdPrescripcion(this.prescripcionSelected.getIdPrescripcion());
        if (surtParcial) {
            prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
        } else {
            prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        }

        try {
            resp = surtimientoService.registrarSurtimientoParcial(
                    surtimiento, listaSurtimientoInsumo, prescripcion, listaInv, listaMovimientos);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
        }

        if (resp) {
            try {
                imprimirTcketSurtimientoManual(this.prescripcionSelected, surtimiento, usuarioSession.getNombreUsuario());
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
            }
            Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.exito.surtimiento"), "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, false);
        }
        obtenerSurtimientoManualDePrescripcion();
    }

    public Date getFechaReceta() {
        return fechaReceta;
    }

    public void setFechaReceta(Date fechaReceta) {
        this.fechaReceta = fechaReceta;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }

    public EntidadHospitalaria getEntidadHospitalaria() {
        return entidadHospitalaria;
    }

    public void setEntidadHospitalaria(EntidadHospitalaria entidadHospitalaria) {
        this.entidadHospitalaria = entidadHospitalaria;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<Diagnostico> getDiagnosticoList() {
        return diagnosticoList;
    }

    public void setDiagnosticoList(List<Diagnostico> diagnosticoList) {
        this.diagnosticoList = diagnosticoList;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public SesionMB getSesion() {
        return sesion;
    }

    public void setSesion(SesionMB sesion) {
        this.sesion = sesion;
    }

    public List<PrescripcionInsumo_Extended> getPrescripcionInsumoExtendedList() {
        return prescripcionInsumoExtendedList;
    }

    public void setPrescripcionInsumoExtendedList(List<PrescripcionInsumo_Extended> prescripcionInsumoExtendedList) {
        this.prescripcionInsumoExtendedList = prescripcionInsumoExtendedList;
    }

    public PrescripcionInsumo_Extended getPrescripcionInsumoExtendedSelected() {
        return prescripcionInsumoExtendedSelected;
    }

    public void setPrescripcionInsumoExtendedSelected(PrescripcionInsumo_Extended prescripcionInsumoExtendedSelected) {
        this.prescripcionInsumoExtendedSelected = prescripcionInsumoExtendedSelected;
    }

    public PrescripcionInsumo getPrescripcionInsumo() {
        return prescripcionInsumo;
    }

    public void setPrescripcionInsumo(PrescripcionInsumo prescripcionInsumo) {
        this.prescripcionInsumo = prescripcionInsumo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<String> getListaIdTurnos() {
        return listaIdTurnos;
    }

    public void setListaIdTurnos(List<String> listaIdTurnos) {
        this.listaIdTurnos = listaIdTurnos;
    }

    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }

    public PacienteServicio getPacienteServicio() {
        return pacienteServicio;
    }

    public void setPacienteServicio(PacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public List<Prescripcion_Extended> getListaRecetasManuales() {
        return listaRecetasManuales;
    }

    public void setListaRecetasManuales(List<Prescripcion_Extended> listaRecetasManuales) {
        this.listaRecetasManuales = listaRecetasManuales;
    }

    public Prescripcion_Extended getRecetaManualSelect() {
        return recetaManualSelect;
    }

    public void setRecetaManualSelect(Prescripcion_Extended recetaManualSelect) {
        this.recetaManualSelect = recetaManualSelect;
    }

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
    }

    public List<ClaveProveedorBarras_Extend> getSkuSapList() {
        return skuSapList;
    }

    public void setSkuSapList(List<ClaveProveedorBarras_Extend> skuSapList) {
        this.skuSapList = skuSapList;
    }

    public ClaveProveedorBarras_Extend getSkuSap() {
        return skuSap;
    }

    public void setSkuSap(ClaveProveedorBarras_Extend skuSap) {
        this.skuSap = skuSap;
    }

    public ClaveProveedorBarras_Extend getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(ClaveProveedorBarras_Extend medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }

    public String getPathTmp() {
        return pathTmp;
    }

    public void setPathTmp(String pathTmp) {
        this.pathTmp = pathTmp;
    }

    public File getDirTmp() {
        return dirTmp;
    }

    public void setDirTmp(File dirTmp) {
        this.dirTmp = dirTmp;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public Usuario_Extended getMedicoExtended() {
        return medicoExtended;
    }

    public void setMedicoExtended(Usuario_Extended medicoExtended) {
        this.medicoExtended = medicoExtended;
    }

    public List<TipoUsuario> getTipoUserList() {
        return tipoUserList;
    }

    public void setTipoUserList(List<TipoUsuario> tipoUserList) {
        this.tipoUserList = tipoUserList;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public String getPathNode() {
        return pathNode;
    }

    public void setPathNode(String pathNode) {
        this.pathNode = pathNode;
    }
    
    public TreeNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(TreeNode selectNode) {
        this.selectNode = selectNode;
    }

    public String getNameUnidad() {
        return nameUnidad;
    }

    public void setNameUnidad(String nameUnidad) {
        this.nameUnidad = nameUnidad;
    }

    public List<String> getListNode() {
        return listNode;
    }

    public void setListNode(List<String> listNode) {
        this.listNode = listNode;
    }

    public UsuarioRol getUsuarioRolSelect() {
        return usuarioRolSelect;
    }

    public void setUsuarioRolSelect(UsuarioRol usuarioRolSelect) {
        this.usuarioRolSelect = usuarioRolSelect;
    }

    public List<Estructura> getListEstructura() {
        return listEstructura;
    }

    public void setListEstructura(List<Estructura> listEstructura) {
        this.listEstructura = listEstructura;
    }

    public String getUestructura() {
        return uestructura;
    }

    public void setUestructura(String uestructura) {
        this.uestructura = uestructura;
    }

    public List<VistaUsuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<VistaUsuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public List<EntidadHospitalaria> getEntidadList() {
        return entidadList;
    }

    public void setEntidadList(List<EntidadHospitalaria> entidadList) {
        this.entidadList = entidadList;
    }

    public List<Estructura> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<Estructura> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public List<SurtimientoInsumo_Extend> getListaInsumosPendientes() {
        return listaInsumosPendientes;
    }

    public void setListaInsumosPendientes(List<SurtimientoInsumo_Extend> listaInsumosPendientes) {
        this.listaInsumosPendientes = listaInsumosPendientes;
    }

    public Prescripcion_Extended getPrescripcionSelected() {
        return prescripcionSelected;
    }

    public void setPrescripcionSelected(Prescripcion_Extended prescripcionSelected) {
        this.prescripcionSelected = prescripcionSelected;
    }

    public List<CatalogoGeneral> getListaTipoPacientes() {
        return listaTipoPacientes;
    }

    public void setListaTipoPacientes(List<CatalogoGeneral> listaTipoPacientes) {
        this.listaTipoPacientes = listaTipoPacientes;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public boolean isPermiteSurtimientoManualHospitalizacion() {
        return permiteSurtimientoManualHospitalizacion;
    }

    public void setPermiteSurtimientoManualHospitalizacion(boolean permiteSurtimientoManualHospitalizacion) {
        this.permiteSurtimientoManualHospitalizacion = permiteSurtimientoManualHospitalizacion;
    }

    public boolean isActiveDuracion() {
        return activeDuracion;
    }

    public void setActiveDuracion(boolean activeDuracion) {
        this.activeDuracion = activeDuracion;
    }

    public List<CatalogoGeneral> getListaUnidadesMedicas() {
        return listaUnidadesMedicas;
    }

    public void setListaUnidadesMedicas(List<CatalogoGeneral> listaUnidadesMedicas) {
        this.listaUnidadesMedicas = listaUnidadesMedicas;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
