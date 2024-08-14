package mx.mc.magedbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
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
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
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
public class SurtRecetaManualMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final Logger LOGGER = LoggerFactory.getLogger(SurtRecetaManualMB.class);
    private Paciente_Extended pacienteExtended;
    private Paciente paciente;
    private PermisoUsuario permiso;
    private String cadenaBusqueda;
    private Usuario_Extended medicoExtended;
    private List<TipoUsuario> tipoUserList;
    private static TreeNode root;
    private Diagnostico diagnosticoSelected;
    private Usuario medico;
    private static TreeNode selectNode;
    private String nameUnidad;
    private String pathNode;
    private Date fechaReceta;
    private Date fechaActual;
    private List<String> listNode;
    private List<Rol> rolList;
    private Diagnostico diagnostico;
    private UsuarioRol usuarioRolSelect;
    private List<Estructura> listEstructura;
    private String uestructura;
    private List<PrescripcionInsumo_Extended> prescripcionInsumoExtendedList;
    private PrescripcionInsumo_Extended prescripcionInsumoExtendedSelected;
    private List<VistaUsuario> usuarioList;
    private List<Diagnostico> diagnosticoList;
    private EntidadHospitalaria entidadHospitalaria;
    private PrescripcionInsumo prescripcionInsumo;
    private Usuario usuarioSession;
    private SesionMB sesion;
    private List<EntidadHospitalaria> entidadList;
    private List<ClaveProveedorBarras_Extend> skuSapList;
    private List<String> listaIdTurnos;
    private List<Turno> listaTurnos;
    private String folioPrescripcion;
    private Visita visita;
    private Prescripcion_Extended recetaManualSelect;
    private String pathTmp;
    private PacienteServicio pacienteServicio;
    private Integer cantidadEntregada;
    private ClaveProveedorBarras_Extend skuSap;
    private ClaveProveedorBarras_Extend medicamentoSelect;
    private List<SurtimientoInsumo_Extend> listaInsumosPendientes;
    private List<Prescripcion_Extended> listaRecetasManuales;
    private Prescripcion_Extended prescripcionSelected;
    private List<CatalogoGeneral> listaTipoPacientes;
    private boolean bandera;
    private String prefijo;
    private String tipoConsulta;
    private String prcManualMedicamentoInvalido;
    private String prcManualExitoReceta;
    private String prcNopuedecancelar;
    private boolean habilitaEditaryCancelarSurtimientoManual;
    private List<Estructura> listaServicios;
    private String idServicio;
    private String paramError;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient PrescripcionService prescripcionService;

    @Autowired
    private transient DiagnosticoService diagnosticoService;

    @Autowired
    private transient TurnoService turnoService;

    @Autowired
    private transient VisitaService visitaService;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;

    @Autowired
    private transient PacienteServicioService pacienteServicioService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient TipoUsuarioService tipoUsuarioService;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient SurtimientoService surtimientoService;

    /**
     * Metodo que se ejecuta al inicializarce la pantalla
     */
    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.init()");
        paramError = "error";
        prcManualMedicamentoInvalido = "prc.manual.medicamento.invalido";
        prcManualExitoReceta = "prc.manual.exito.receta";
        prcNopuedecancelar = "prc.nopuedecancelar";
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        habilitaEditaryCancelarSurtimientoManual = sesion.isHabilitaEditaryCancelarSurtimientoManual();
        this.usuarioSession = sesion.getUsuarioSelected();
        try {
            this.listaTipoPacientes = this.catalogoGeneralService
                    .obtenerCatalogosPorGrupo(GrupoCatalogoGeneral_Enum.TIPO_DE_PACIENTE.getValue());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener listaTipopacientes: {}", ex.getMessage());
        }
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.SURRECM.getSufijo());
        obtenerRecetasManuales();
        initialize();
        if (this.habilitaEditaryCancelarSurtimientoManual) {
            EntidadHospitalaria entHosp = new EntidadHospitalaria();
            try {
                this.entidadList = this.entidadHospitalariaService.obtenerLista(entHosp);
                this.entidadHospitalaria = this.entidadList.get(0);
            } catch (Exception ex) {
                LOGGER.error("Error al obtener entidad hospitalaria: {}", ex.getMessage());
            }
        }
    }

    public void initialize() {
        this.fechaReceta = new Date();
        this.fechaActual = new Date();
        this.diagnosticoList = new ArrayList<>();
        this.listaIdTurnos = new ArrayList<>();
        this.prescripcionInsumoExtendedList = new ArrayList<>();
        this.prescripcionInsumo = new PrescripcionInsumo();
        this.paciente = new Paciente();
        this.entidadHospitalaria = new EntidadHospitalaria();
        this.entidadList = new ArrayList<>();
        this.cadenaBusqueda = null;
        this.medicoExtended = new Usuario_Extended();
        this.prefijo = PrefijoPrescripcion_Enum.NORMAL.getValue();
        this.tipoConsulta = TipoConsulta_Enum.CONSULTA_EXTERNA.getValue();
        this.bandera = false;
        this.listaPrescripcionInsumoExtendedValidacion = new ArrayList<>();
        numeroMedicamentosConDiferencias = 0;
    }

    public void limpiarDatosMedicamento() {
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.cantidadEntregada = null;
        this.diagnostico = new Diagnostico();
        this.prescripcionInsumo = new PrescripcionInsumo();
        this.pacienteExtended = new Paciente_Extended();
        this.entidadHospitalaria = new EntidadHospitalaria();
        this.medico = new Usuario();
        this.cantidadEntregada = null;
    }

    public void limpiarDatosPaciente() {
        this.paciente = new Paciente();
    }

    public void limpiarTodo() {
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.cantidadEntregada = null;
        this.diagnostico = new Diagnostico();
        this.prescripcionInsumo = new PrescripcionInsumo();
        this.pacienteExtended = new Paciente_Extended();
        this.medico = new Usuario();
        this.folioPrescripcion = null;
        this.cantidadEntregada = null;
        this.pacienteExtended = null;
        this.medico = null;
        this.fechaReceta = new Date();
        this.diagnosticoList = new ArrayList<>();
        this.entidadList = new ArrayList<>();
        this.skuSap = null;
        this.entidadHospitalaria = new EntidadHospitalaria();
        this.prescripcionInsumo = new PrescripcionInsumo();
        this.prescripcionInsumoExtendedList = new ArrayList<>();
        this.idServicio = null;
        obtenerServicios();
    }

    public void obtenerRecetasManuales() {
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
            LOGGER.error("Error al obtener obtenerRecetasManuales : {}", ex.getMessage());
        }
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

    public void obtenerServicios() {
        List<Integer> listaTipoArea = new ArrayList<>();
        listaTipoArea.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
        try {
            this.listaServicios = this.estructuraService.obtenerEstructurasPorTipo(listaTipoArea);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerServicios : {}", ex.getMessage());
        }
    }

    public List<Usuario> autoCompleteMedicos(String cadena) {
        List<Usuario> listMedicos = new ArrayList<>();
        try {
            Integer prescribeControlados = 1;
            if (this.prefijo.equalsIgnoreCase(PrefijoPrescripcion_Enum.CONTROLADA.getValue())) {
                listMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                        cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, prescribeControlados);
            } else {
                listMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(
                        cadena.trim(), TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autoCompleteMedicos :{}", ex.getMessage());
        }
        return listMedicos;
    }

    public List<Diagnostico> autocompleteDiagnostico(String cadena) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.autocompleteDiagnostico()");
        List<Diagnostico> diagList = new ArrayList<>();
        try {
            Diagnostico d = new Diagnostico();
            d.setActivo(Constantes.ACTIVO);
            d.setNombre(cadena.trim());
            d.setDescripcion(cadena.trim());
            if (this.habilitaEditaryCancelarSurtimientoManual) {
                diagList.addAll(this.diagnosticoService.obtenerListaChiconcuac(d, Constantes.REGISTROS_PARA_MOSTRAR));
            } else {
                diagList.addAll(this.diagnosticoService.obtenerLista(d));
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
        }
        return diagList;
    }

    public List<EntidadHospitalaria> autocompleteDatos(String cadena) {
        List<EntidadHospitalaria> entiList = new ArrayList<>();
        try {
            EntidadHospitalaria e = new EntidadHospitalaria();
            e.setNombre(cadena);
            entiList.addAll(this.entidadHospitalariaService.obtenerLista(e));
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("prc.dia.lista"), ex);
        }
        return entiList;
    }

    public List<ClaveProveedorBarras_Extend> autocompleteMedicamento(String query) {
        boolean rManual = false;
        this.skuSapList = new ArrayList<>();
        try {
            String idEstructura = this.usuarioSession.getIdEstructura();
            if (this.habilitaEditaryCancelarSurtimientoManual) {
                List<Integer> listaSubCategorias = new ArrayList<>();
                if (this.prefijo.equalsIgnoreCase(PrefijoPrescripcion_Enum.CONTROLADA.getValue())) {
                    listaSubCategorias = new ArrayList<>();
                    listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G2.getValue());
                    listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G3.getValue());
                    listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G1.getValue());
                }
                rManual = true;
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExtChiconcuac(
                        query, idEstructura, this.usuarioSession.getIdUsuario(), listaSubCategorias, rManual);
            } else {
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExt(
                        query, idEstructura, this.usuarioSession.getIdUsuario());
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener autocompleteMedicamento : {}", e.getMessage());
        }
        return skuSapList;
    }

    public void handleSelectEntidadHospitalaria(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtRecetaManualMB.handleSelectEntidadHospitalaria()");
        EntidadHospitalaria entHo = (EntidadHospitalaria) e.getObject();
        if (entHo != null) {
            EntidadHospitalaria item = new EntidadHospitalaria();
            try {
                item = entidadHospitalariaService.obtener(entHo);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
            this.entidadHospitalaria = item;
            this.entidadList.add(item);
            this.entidadHospitalaria = new EntidadHospitalaria();
        }
    }

    public void handleSelectDiagnostico(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtRecetaManualMB.handleSelectDiagnostico()");
        Diagnostico diagnost = (Diagnostico) e.getObject();
        if (diagnost != null) {
            diagnost.setActivo(Constantes.ACTIVO);
            Diagnostico item = new Diagnostico();
            try {
                item = diagnosticoService.obtener(diagnost);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
            this.diagnosticoList.add(item);
            this.diagnostico = new Diagnostico();
        }

    }

    public void handleUnSelectMedicamento() {
        skuSap = new ClaveProveedorBarras_Extend();
    }

    public void handleSelectMedicamento(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtRecetaManualMB.handleSelectMedicamento()");
        this.skuSap = (ClaveProveedorBarras_Extend) e.getObject();
        String idInventario = skuSap.getIdInventario();
        for (ClaveProveedorBarras_Extend ite : this.skuSapList) {
            if (ite.getIdInventario().equalsIgnoreCase(idInventario)) {
                if (ite.getCantidadActual() == 0) {
                    cantidadEntregada = ite.getCantidadActual();
                    bandera = true;
                } else {
                    bandera = false;
                }
                this.medicamentoSelect = ite;
                break;
            }
        }
    }

    public void agregarPrescripcionInsumo() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualMB.agregarPrescripcionInsumo()");
        try {
            String msajError = validarMedicamento();
            if (msajError != null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, msajError, "");
                PrimeFaces.current().ajax().addCallbackParam(paramError, true);
                limpiarDatosMedicamento();
                return;
            }
            boolean insertado = false;
            PrescripcionInsumo_Extended prescInsumoExten = generarPrescripcionInsumoExtended();
            BigDecimal dosis = prescripcionInsumo.getDosis().setScale(0, RoundingMode.CEILING);
            if (this.prescripcionInsumoExtendedList.isEmpty()) {
                prescInsumoExten.setIdSurtimientoInsumo(Comunes.getUUID());
                Integer cantidadSolicitada = 0;

                float temp = (24f / prescripcionInsumo.getFrecuencia());
                int numeroDosisPorDia = (int) temp;

                cantidadSolicitada = numeroDosisPorDia * dosis.intValue() * prescripcionInsumo.getDuracion();

                prescInsumoExten.setCantidadSolicitada(cantidadSolicitada);
                insertarNuevoPrescripcionInsumoExtended(prescInsumoExten);
                insertado = true;
            } else {
                for (short i = 0; i < this.prescripcionInsumoExtendedList.size(); i++) {
                    PrescripcionInsumo_Extended item = this.prescripcionInsumoExtendedList.get(i);
                    if (item.getIdInsumo().equalsIgnoreCase(prescInsumoExten.getIdInsumo())) {
                        for (SurtimientoEnviado_Extend ite2 : item.getSurtimientoEnviadoExtendedList()) {
                            if (ite2.getLote().equalsIgnoreCase(this.medicamentoSelect.getLote())) {
                                item.setCantidadEntregada(item.getCantidadEntregada() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                item.setCantidadSolicitada(item.getCantidadSolicitada() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                ite2.setCantidadEnviado(ite2.getCantidadEnviado() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                ite2.setCantidadRecibido(ite2.getCantidadRecibido() + this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
                                insertado = true;
                                break;
                            }
                        }
                        if (!insertado) {
                            SurtimientoEnviado_Extend surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
                            surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
                            surtimientoEnviadoExtend.setIdSurtimientoInsumo(item.getIdSurtimientoInsumo());
                            surtimientoEnviadoExtend.setIdInventarioSurtido(this.medicamentoSelect.getIdInventario());
                            surtimientoEnviadoExtend.setLote(prescInsumoExten.getLote());
                            surtimientoEnviadoExtend.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                            surtimientoEnviadoExtend.setCantidadEnviado(prescInsumoExten.getCantidadEntregada());
                            surtimientoEnviadoExtend.setCantidadRecibido(prescInsumoExten.getCantidadEntregada());
                            surtimientoEnviadoExtend.setInsertFecha(new Date());
                            surtimientoEnviadoExtend.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                            item.getSurtimientoEnviadoExtendedList().add(surtimientoEnviadoExtend);
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
                prescInsumoExten.setIdSurtimientoInsumo(Comunes.getUUID());
                Integer cantidadSolicitada = 0;

                float temp = (24f / prescripcionInsumo.getFrecuencia());
                int numeroDosisPorDia = (int) temp;
                cantidadSolicitada = numeroDosisPorDia * dosis.intValue() * prescripcionInsumo.getDuracion();

                prescInsumoExten.setCantidadSolicitada(cantidadSolicitada);
                insertarNuevoPrescripcionInsumoExtended(prescInsumoExten);
            }

        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcManualMedicamentoInvalido), ex);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcManualMedicamentoInvalido), null);
        }
        obtenerRecetasManuales();
        limpiarDatosMedicamento();
        bandera = false;
    }

    public void agregarMedicamentoDetalle() {
        LOGGER.trace("mx.mc.magedbean.SurtRecetaManualMB.agregarMedicamentoDetalle()");
        Inventario inventario = new Inventario();
        try {
            inventario = inventarioService.obtener(new Inventario(this.skuSap.getIdInventario()));
        } catch (Exception exc) {
            LOGGER.error("Error al obtener el inventario.");
        }
        if (this.cantidadEntregada == null) {
            this.cantidadEntregada = 1;
        }
        for (SurtimientoInsumo_Extend insumo : this.listaInsumosPendientes) {
            if (insumo.getIdInsumo().equalsIgnoreCase(inventario.getIdInsumo())) {
                if (insumo.getSurtimientoEnviadoExtendList() == null) {
                    Integer cantSurtir = this.cantidadEntregada
                            * ((this.medicamentoSelect.getPresentacionComercial() == Constantes.ES_ACTIVO)
                            ? this.medicamentoSelect.getCantidadXCaja() : 1);

                    if (cantSurtir > (insumo.getCantidadSolicitada() - insumo.getCantidadEnviada())) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No puede sobrepasar la cantidad solicitada.", null);
                        limpiarDatosMedicamento();
                        return;
                    }
                    insumo.setSurtimientoEnviadoExtendList(new ArrayList<>());
                    SurtimientoEnviado_Extend surtimientoEnviadoExtended = new SurtimientoEnviado_Extend();
                    surtimientoEnviadoExtended.setIdSurtimientoEnviado(Comunes.getUUID());
                    surtimientoEnviadoExtended.setIdSurtimientoInsumo(insumo.getIdSurtimientoInsumo());
                    surtimientoEnviadoExtended.setIdInventarioSurtido(this.medicamentoSelect.getIdInventario());
                    surtimientoEnviadoExtended.setLote(inventario.getLote());
                    surtimientoEnviadoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                    surtimientoEnviadoExtended.setCantidadEnviado(cantSurtir);
                    surtimientoEnviadoExtended.setCantidadRecibido(cantSurtir);
                    surtimientoEnviadoExtended.setInsertFecha(new Date());
                    surtimientoEnviadoExtended.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                    insumo.getSurtimientoEnviadoExtendList().add(surtimientoEnviadoExtended);
                    insumo.setCantidadRecepcion(cantSurtir);
                } else {
                    boolean encontrado = false;
                    Integer cantSurtir = this.cantidadEntregada
                            * ((this.medicamentoSelect.getPresentacionComercial() == Constantes.ES_ACTIVO)
                            ? this.medicamentoSelect.getCantidadXCaja() : 1);

                    if ((insumo.getCantidadRecepcion() + cantSurtir)
                            > (insumo.getCantidadSolicitada() - insumo.getCantidadEnviada())) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No puede sobrepasar la cantidad solicitada.", null);
                        limpiarDatosMedicamento();
                        return;
                    }
                    for (SurtimientoEnviado_Extend item2 : insumo.getSurtimientoEnviadoExtendList()) {
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
                        surtimientoEnviadoExtended.setIdSurtimientoInsumo(insumo.getIdSurtimientoInsumo());
                        surtimientoEnviadoExtended.setIdInventarioSurtido(insumo.getIdInventario());
                        surtimientoEnviadoExtended.setLote(inventario.getLote());
                        surtimientoEnviadoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
                        surtimientoEnviadoExtended.setCantidadEnviado(cantSurtir);
                        surtimientoEnviadoExtended.setCantidadRecibido(cantSurtir);
                        surtimientoEnviadoExtended.setInsertFecha(new Date());
                        surtimientoEnviadoExtended.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                        insumo.getSurtimientoEnviadoExtendList().add(surtimientoEnviadoExtended);
                    }
                    insumo.setCantidadRecepcion(insumo.getCantidadRecepcion() + cantSurtir);
                }
                break;
            }
        }
        limpiarDatosMedicamento();
    }

    private PrescripcionInsumo_Extended generarPrescripcionInsumoExtended() {
        PrescripcionInsumo_Extended prescInsumoExten = new PrescripcionInsumo_Extended();
        prescInsumoExten.setIdPrescripcionInsumo(Comunes.getUUID());
        prescInsumoExten.setIdInventario(this.medicamentoSelect.getIdInventario());
        prescInsumoExten.setIdInsumo(this.medicamentoSelect.getIdMedicamento());
        prescInsumoExten.setDosis(this.prescripcionInsumo.getDosis());
        prescInsumoExten.setFrecuencia(this.prescripcionInsumo.getFrecuencia());
        prescInsumoExten.setDuracion(this.prescripcionInsumo.getDuracion());
        prescInsumoExten.setNombreCorto(this.medicamentoSelect.getNombreCorto());
        prescInsumoExten.setClaveInstitucional(this.medicamentoSelect.getClaveInstitucional());
        prescInsumoExten.setIndicaciones(this.prescripcionInsumo.getComentarios());
        prescInsumoExten.setCantidadEntregada(this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja());
        prescInsumoExten.setCantidadXCaja(this.medicamentoSelect.getCantidadXCaja());
        prescInsumoExten.setIdInsumo(this.medicamentoSelect.getIdMedicamento());
        prescInsumoExten.setIdEstructura(this.medicamentoSelect.getIdEstructura());
        prescInsumoExten.setLote(this.medicamentoSelect.getLote());
        return prescInsumoExten;
    }

    private SurtimientoEnviado_Extend obtenerSurtimientoEnviadoExtended(PrescripcionInsumo_Extended prescInsumoExten) {
        SurtimientoEnviado_Extend surtimientoEnviadoExtended = new SurtimientoEnviado_Extend();
        surtimientoEnviadoExtended.setIdSurtimientoEnviado(Comunes.getUUID());
        surtimientoEnviadoExtended.setIdSurtimientoInsumo(prescInsumoExten.getIdSurtimientoInsumo());
        surtimientoEnviadoExtended.setIdInventarioSurtido(prescInsumoExten.getIdInventario());
        surtimientoEnviadoExtended.setLote(prescInsumoExten.getLote());
        surtimientoEnviadoExtended.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.RECIBIDO.getValue());
        surtimientoEnviadoExtended.setCantidadEnviado(prescInsumoExten.getCantidadEntregada());
        surtimientoEnviadoExtended.setCantidadRecibido(prescInsumoExten.getCantidadEntregada());
        surtimientoEnviadoExtended.setInsertFecha(new Date());
        surtimientoEnviadoExtended.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        return surtimientoEnviadoExtended;
    }

    private void insertarNuevoPrescripcionInsumoExtended(PrescripcionInsumo_Extended prescInsumoExt) {
        this.prescripcionInsumoExtendedList.add(prescInsumoExt);
        prescInsumoExt.setSurtimientoEnviadoExtendedList(new ArrayList<>());
        SurtimientoEnviado_Extend surtimientoEnviadoExtended = obtenerSurtimientoEnviadoExtended(prescInsumoExt);
        prescInsumoExt.getSurtimientoEnviadoExtendedList().add(surtimientoEnviadoExtended);
    }

    private String validarMedicamento() {
        String resp = null;
        if (this.habilitaEditaryCancelarSurtimientoManual) {
            if (this.medicamentoSelect.getPresentacionComercial() == Constantes.ES_ACTIVO) {
                if (this.medicamentoSelect.getCantidadActual() < (this.cantidadEntregada * this.medicamentoSelect.getCantidadXCaja())) {
                    resp = "No puede surtir una cantidad mayor a la existente.";
                    return resp;
                }
            } else {
                if (this.medicamentoSelect.getCantidadActual() < this.cantidadEntregada) {
                    resp = "No puede surtir una cantidad mayor a la existente.";
                    return resp;
                }
            }

            if (this.prefijo.equalsIgnoreCase("F")) {
                if (this.prescripcionInsumoExtendedList.size() == 2) {
                    resp = RESOURCES.getString("prc.manual.error.insuficiente");
                    return resp;
                }
            } else if (this.prefijo.equalsIgnoreCase("E") && this.prescripcionInsumoExtendedList.size() == 4) {
                resp = RESOURCES.getString("prc.manual.error.limiteMed");
                return resp;
            }
        } else if (prescripcionInsumo == null) {
            resp = RESOURCES.getString(prcManualMedicamentoInvalido);
            return resp;
        } else if (prescripcionInsumo.getDosis() == null || prescripcionInsumo.getDosis().compareTo(BigDecimal.ONE) < 0) {
            resp = RESOURCES.getString("prc.manual.dosis.invalido");
            return resp;
        } else if (prescripcionInsumo.getFrecuencia() == null || prescripcionInsumo.getFrecuencia() < 1) {
            resp = RESOURCES.getString("prc.manual.frecuencia.invalido");
            return resp;
        } else if (prescripcionInsumo.getDuracion() == null || prescripcionInsumo.getDuracion() < 1) {
            resp = RESOURCES.getString("prc.manual.duracion.invalido");
            return resp;
        } else if (this.cantidadEntregada == null || this.cantidadEntregada < 0) {
            resp = RESOURCES.getString("prc.manual.cantEnt.invalido");
            return resp;
        } else if (this.cantidadEntregada * medicamentoSelect.getCantidadXCaja() > this.medicamentoSelect.getCantidadActual()) {
            resp = RESOURCES.getString("prc.manual.error.inventario");
            return resp;
        }
        return resp;
    }

    public void agregarPaciente() {
        String msjError = validarDatosPaciente();
        boolean resp = false;

        if (msjError != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
            return;
        }

        Paciente pacieteTemp = obtenerDatosPaciente();
        PacienteDomicilio pacienteDomTem = obtenerDatosPacienteDomicilioGenerico(pacieteTemp);
        PacienteResponsable pacienteRespTemp = new PacienteResponsable();
        List<TurnoMedico> listaTurnoTemp = obtenerTurnoGenericoPaciente(pacieteTemp);

        try {
            pacienteService.insertarPaciente(
                    pacieteTemp, pacienteDomTem, pacienteRespTemp, listaTurnoTemp);
        } catch (Exception ex) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.registro"), "");
            LOGGER.error(ex.getMessage());
        }

        resp = generarVisitaYServicioGenerico(pacieteTemp);

        if (resp) {
            Mensaje.showMessage("Info", RESOURCES.getString("prc.manual.registro.paciente"), "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, false);
        } else {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.manual.error.visita"), "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
        }
    }

    private boolean generarVisitaYServicioGenerico(Paciente paciente) {
        boolean resp = false;
        try {
            Visita visitaGenericaRecMan = new Visita();
            visitaGenericaRecMan.setIdVisita(Comunes.getUUID());
            visitaGenericaRecMan.setIdPaciente(paciente.getIdPaciente());
            visitaGenericaRecMan.setFechaIngreso(new Date());
            visitaGenericaRecMan.setIdUsuarioIngresa(this.usuarioSession.getIdUsuario());
            visitaGenericaRecMan.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            visitaGenericaRecMan.setMotivoConsulta("");
            visitaGenericaRecMan.setInsertFecha(new Date());
            visitaGenericaRecMan.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

            PacienteServicio pacienteServicioGenerico = new PacienteServicio();
            pacienteServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
            pacienteServicioGenerico.setIdVisita(visitaGenericaRecMan.getIdVisita());
            if (this.habilitaEditaryCancelarSurtimientoManual) {
                List<Integer> listaTipoArea = new ArrayList<>();
                listaTipoArea.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
                List<Estructura> listaEstructuras = this.estructuraService.obtenerEstructurasPorTipo(listaTipoArea);
                pacienteServicioGenerico.setIdEstructura(listaEstructuras.get(0).getIdEstructura());
            } else {
                pacienteServicioGenerico.setIdEstructura(Constantes.IDESTRUCTURA_CONSULTA_EXTERNA_CLINICA);
            }

            pacienteServicioGenerico.setFechaAsignacionInicio(new Date());
            pacienteServicioGenerico.setIdUsuarioAsignacionInicio(this.usuarioSession.getIdUsuario());
            pacienteServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
            pacienteServicioGenerico.setInsertFecha(new Date());
            pacienteServicioGenerico.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

            resp = visitaService.insertarVisitaYServicioGenericos(visitaGenericaRecMan, pacienteServicioGenerico);
        } catch (Exception ex) {
            resp = false;
            LOGGER.error(ex.getMessage());
        }
        return resp;
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
            medicoTemp.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
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

    public void mostrarModalEditarPrescripcion(Prescripcion_Extended prescripcion) {
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
        String res = null;
        if (this.paciente.getClaveDerechohabiencia().isEmpty()) {
            res = RESOURCES.getString("prc.manual.cve.paciente");
            return res;
        } else if (this.paciente.getNombreCompleto().isEmpty()) {
            res = RESOURCES.getString("prc.manual.nombre.paciente");
            return res;
        } else if (this.paciente.getApellidoPaterno().isEmpty()) {
            res = RESOURCES.getString("prc.manual.app.paciente");
            return res;
        } else if (this.paciente.getSexo() == '\0' || this.paciente.getSexo() == '\u0000') {
            res = RESOURCES.getString("prc.manual.sex.paciente");
            return res;
        } else if (this.paciente.getFechaNacimiento() == null) {
            res = RESOURCES.getString("prc.manual.fechaNac.paciente");
            return res;
        } else if (this.listaIdTurnos.isEmpty()) {
            res = RESOURCES.getString("prc.manual.turno.paciente");
            return res;
        }
        res = validarClavePaciente();
        return res;
    }

    private String generarPacNumero() {
        Date fecha = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("_" + "HH:mm");
        return sdf.format(fecha);
    }

    private String validarClavePaciente() {
        String resp = null;
        Paciente pacienteTem = new Paciente();
        pacienteTem.setPacienteNumero(this.paciente.getClaveDerechohabiencia());
        Paciente pacienteResp = null;
        try {
            pacienteResp = pacienteService.obtener(pacienteTem);
        } catch (Exception ex) {
            LOGGER.error("error en el metodo :: validarClavePaciente: {}", ex.getMessage());
        }
        if (pacienteResp != null) {
            resp = RESOURCES.getString("prc.manual.clave.paciente");
        }
        return resp;
    }

    private Paciente obtenerDatosPaciente() {
        Paciente pacientTem = new Paciente();
        pacientTem.setNombreCompleto(this.paciente.getNombreCompleto());
        pacientTem.setApellidoMaterno(this.paciente.getApellidoMaterno());
        pacientTem.setApellidoPaterno(this.paciente.getApellidoPaterno());
        if (this.habilitaEditaryCancelarSurtimientoManual) {
            pacientTem.setPacienteNumero(paciente.getClaveDerechohabiencia() + generarPacNumero());
        }
        pacientTem.setClaveDerechohabiencia(this.paciente.getClaveDerechohabiencia());
        pacientTem.setCurp(Constantes.TXT_VACIO);
        pacientTem.setFechaNacimiento(this.paciente.getFechaNacimiento());
        pacientTem.setIdEscolaridad(CatalogoGeneral_Enum.ESCOLARIDAD_NO_DEFINIDA.getValue());
        pacientTem.setIdEstadoCivil(CatalogoGeneral_Enum.ESTADO_CIVIL_NO_DEFINIDO.getValue());
        pacientTem.setIdEstatusPaciente(EstatusPaciente_Enum.REGISTRADO.getValue());
        pacientTem.setIdGrupoEtnico(CatalogoGeneral_Enum.GRUPO_ETNICO_NO_DEFINIDO.getValue());
        pacientTem.setIdGrupoSanguineo(CatalogoGeneral_Enum.GRUPO_SANGUINEO_NO_DEFINIDO.getValue());
        pacientTem.setIdNivelSocioEconomico(CatalogoGeneral_Enum.NIVEL_SOCIOECONOMICO_NO_DEFINIDO.getValue());
        pacientTem.setIdOcupacion(CatalogoGeneral_Enum.OCUPACION_NO_DEFINIDA.getValue());
        pacientTem.setIdPaciente(Comunes.getUUID());
        pacientTem.setIdReligion(CatalogoGeneral_Enum.RELIGION_NO_DEFINIDA.getValue());
        pacientTem.setIdTipoPaciente(this.paciente.getIdTipoPaciente());
        pacientTem.setIdTipoVivienda(CatalogoGeneral_Enum.TIPO_VIVIENDA_NO_DEFINIDA.getValue());
        pacientTem.setIdUnidadMedica(CatalogoGeneral_Enum.UNIDAD_MEDICA_OTRA.getValue());
        pacientTem.setInsertFecha(new Date());
        pacientTem.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        pacientTem.setSexo(this.paciente.getSexo());
        return pacientTem;
    }

    private PacienteDomicilio obtenerDatosPacienteDomicilioGenerico(Paciente paciente) {
        PacienteDomicilio pacienteDom = new PacienteDomicilio();
        pacienteDom.setIdPaciente(paciente.getIdPaciente());
        pacienteDom.setIdPacienteDomicilio(Comunes.getUUID());
        pacienteDom.setCalle(Constantes.NO_DEFINIDO);
        pacienteDom.setNumeroExterior(Constantes.NO_DEFINIDO);
        pacienteDom.setTelefonoCasa(Constantes.NO_DEFINIDO);
        pacienteDom.setIdPais(Constantes.ID_PAIS_MEXICO);
        pacienteDom.setInsertFecha(new Date());
        pacienteDom.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        return pacienteDom;
    }

    public void mostrarModalAgregarPaciente() {
        try {
            limpiarDatosPaciente();
            this.listaTurnos = this.turnoService.obtenerLista(new Turno());
            Turno item = this.listaTurnos.get(0);
            this.listaIdTurnos = new ArrayList<>();
            this.listaIdTurnos.add("" + item.getIdTurno());
        } catch (Exception exp) {
            LOGGER.error(exp.getMessage());
        }
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
        boolean resp = false;
        LOGGER.debug("SurtRecetaManual.MB.validacion()");
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

    public void insertarPrescripcion() {
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
            return;
        }
        boolean resp = false;
        String msjeError = validarPrescripcion();
        if (msjeError != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjeError, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
            return;
        }
        Prescripcion prescripcionTemp = obtenerDatosPrescripcion();

        Surtimiento_Extend surtimientoTemp = obtenerDatosSurtimiento(prescripcionTemp);

        List<DiagnosticoPaciente> listaDiagnostPaciente
                = obtenerListaDiagnosticosPaciente(prescripcionTemp);

        List<PrescripcionInsumo> listaPrescripInsumo
                = obtenerListaPrescripcionInsumo(prescripcionTemp);

        List<SurtimientoInsumo_Extend> listaSurtimientInsumo
                = obtenerListaSurtimientoInsumo(surtimientoTemp);

        List<InventarioExtended> listaInventario
                = obtenerListaInventarios(this.prescripcionInsumoExtendedList);

        List<MovimientoInventario> listaMovInventarios
                = obtenerListaMovInventarios(listaInventario);
        try {
            resp = this.prescripcionService.registrarPrescripcionManual(
                    prescripcionTemp, surtimientoTemp, listaDiagnostPaciente,
                    listaPrescripInsumo, listaSurtimientInsumo,
                    listaInventario, this.prescripcionInsumoExtendedList, listaMovInventarios);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        if (resp) {
            try {
                imprimirTcket(surtimientoTemp, usuarioSession.getNombreUsuario());
                Mensaje.showMessage("Info", RESOURCES.getString(prcManualExitoReceta), "");
                init();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);

    }

    public void insertarPrescripcionChiconcuac() {
        if (!this.permiso.isPuedeCrear()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), null);
            return;
        }
        boolean resp = false;
        String msjError = validarPrescripcion();
        if (msjError != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
            PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, false);
            return;
        }
        Prescripcion prescripcionTemp = obtenerDatosPrescripcionHospChiconcuac();

        Surtimiento_Extend surtimientoTemp = obtenerDatosSurtimientoChiconcuac(prescripcionTemp);

        List<DiagnosticoPaciente> listDiagnosticoPaciente
                = obtenerListaDiagnosticosPaciente(prescripcionTemp);

        List<PrescripcionInsumo> listaPrescripcionInsumo
                = obtenerListaPrescripcionInsumo(prescripcionTemp);

        List<SurtimientoInsumo_Extend> listSurtimientoInsumo
                = obtenerListaSurtimientoInsumo(surtimientoTemp);

        List<InventarioExtended> listaInventario
                = obtenerListaInventarios(this.prescripcionInsumoExtendedList);

        List<MovimientoInventario> listaMovInventarios
                = obtenerListaMovInventarios(listaInventario);

        try {
            resp = this.prescripcionService.registrarPrescripcionManual(
                    prescripcionTemp, surtimientoTemp, listDiagnosticoPaciente,
                    listaPrescripcionInsumo, listSurtimientoInsumo,
                    listaInventario, this.prescripcionInsumoExtendedList, listaMovInventarios);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        if (resp) {
            try {
                imprimirTcketChiconcuac(prescripcionTemp,
                        surtimientoTemp, usuarioSession.getNombreUsuario());
                Mensaje.showMessage("Info", RESOURCES.getString(prcManualExitoReceta), "");
                init();
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);

    }

    private String validarPrescripcion() {
        String resp;
        String prcVisita = "prc.visita";
        String prcServicio = "prc.servicio";
        if (this.diagnosticoList.isEmpty()) {
            resp = RESOURCES.getString("prc.manual.req.diagnostico");
            return resp;
        }
        if (this.pacienteExtended == null) {
            resp = RESOURCES.getString("prc.manual.req.paciente");
            return resp;
        }
        try {
            this.visita = visitaService.obtener(new Visita(this.pacienteExtended.getIdPaciente()));
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcVisita), ex);
        }
        // regla: paciente debe tener visita abierta
        if (!this.habilitaEditaryCancelarSurtimientoManual) {
            if (this.visita == null || this.visita.getIdVisita() == null) {
                resp = RESOURCES.getString(prcVisita);
                return resp;
            }
        }

        try {
            if (this.visita != null) {
                this.pacienteServicio = pacienteServicioService.obtener(
                        new PacienteServicio(this.visita.getIdVisita()));
            } else {
                this.pacienteServicio = null;
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcServicio), ex);
        }

        if (!this.habilitaEditaryCancelarSurtimientoManual) {
            // regla: paciente debe estar asignado a un servicio
            if (this.pacienteServicio == null || this.pacienteServicio.getIdPacienteServicio() == null) {
                resp = RESOURCES.getString(prcServicio);
                return resp;
            }
        } else {
            if (this.visita == null) {
                crearVisitaGenericaYPacienteServicioGenerico();
            } else if (this.pacienteServicio == null) {
                crearPacienteServicioGenerico(this.visita);
            }
        }

        if (this.prescripcionInsumoExtendedList.isEmpty()) {
            resp = RESOURCES.getString("prc.manual.req.medicamento");
            return resp;
        } else if (this.diagnosticoList.isEmpty()) {
            resp = RESOURCES.getString("prc.manual.req.diagnostico");
            return resp;
        } else if (this.folioPrescripcion == null || this.folioPrescripcion.isEmpty()) {
            resp = RESOURCES.getString("prc.manual.req.folio");
            return resp;
        } else if (this.fechaReceta == null) {
            resp = RESOURCES.getString("prc.manual.req.fecha");
            return resp;
        } else if (this.medico == null) {
            resp = RESOURCES.getString("prc.manual.req.medico");
            return resp;
        }
        resp = validarFolioReceta();
        return resp;
    }

    private void crearVisitaGenericaYPacienteServicioGenerico() {

        Visita visGenerica = new Visita();
        visGenerica.setIdVisita(Comunes.getUUID());
        visGenerica.setIdPaciente(this.pacienteExtended.getIdPaciente());
        visGenerica.setFechaIngreso(new Date());
        visGenerica.setIdUsuarioIngresa(this.usuarioSession.getIdUsuario());
        visGenerica.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        visGenerica.setMotivoConsulta("Visita Generica");
        visGenerica.setInsertFecha(new Date());
        visGenerica.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        PacienteServicio pacServicioGenerico = new PacienteServicio();
        pacServicioGenerico.setIdPacienteServicio(Comunes.getUUID());
        pacServicioGenerico.setIdVisita(visGenerica.getIdVisita());
        pacServicioGenerico.setIdEstructura(this.idServicio);
        pacServicioGenerico.setFechaAsignacionInicio(new Date());
        pacServicioGenerico.setIdUsuarioAsignacionInicio(this.usuarioSession.getIdUsuario());
        pacServicioGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        pacServicioGenerico.setInsertFecha(new Date());
        pacServicioGenerico.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        Paciente pacientGenerico = new Paciente();
        pacientGenerico.setIdPaciente(this.pacienteExtended.getIdPaciente());
        pacientGenerico.setIdEstatusPaciente(
                EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
        pacientGenerico.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        pacientGenerico.setIdEstructura(this.idServicio);
        pacientGenerico.setUpdateFecha(new Date());
        pacientGenerico.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
        try {
            boolean resp = visitaService.registrarVisitaPaciente(
                    visGenerica, pacServicioGenerico, pacientGenerico);
            if (resp) {
                this.visita = visGenerica;
                this.pacienteServicio = pacServicioGenerico;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al crear la visita : {}", ex.getMessage());
        }
    }

    private void crearPacienteServicioGenerico(Visita visitaGenerica) {

        PacienteServicio pacientServicGenerico = new PacienteServicio();
        pacientServicGenerico.setIdPacienteServicio(Comunes.getUUID());
        pacientServicGenerico.setIdVisita(visitaGenerica.getIdVisita());
        pacientServicGenerico.setIdEstructura(this.idServicio);
        pacientServicGenerico.setFechaAsignacionInicio(new Date());
        pacientServicGenerico.setIdUsuarioAsignacionInicio(this.usuarioSession.getIdUsuario());
        pacientServicGenerico.setIdMotivoPacienteMovimiento(MotivoPacienteMovimiento_Enum.ADMISION.getValue());
        pacientServicGenerico.setInsertFecha(new Date());
        pacientServicGenerico.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        Paciente pacientGenerico = new Paciente();
        pacientGenerico.setIdPaciente(this.pacienteExtended.getIdPaciente());
        pacientGenerico.setIdEstatusPaciente(
                EstatusPaciente_Enum.ASIGNADO_A_SERVICIO.getValue());
        pacientGenerico.setEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        pacientGenerico.setIdEstructura(this.idServicio);
        pacientGenerico.setUpdateFecha(new Date());
        pacientGenerico.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
        try {
            boolean resp = this.pacienteServicioService.registrarPacienteServicio(
                    pacientServicGenerico, pacientGenerico);
            if (resp) {
                this.pacienteServicio = pacientServicGenerico;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al crear la visita : {}", ex.getMessage());
        }
    }

    private Prescripcion obtenerDatosPrescripcion() {
        Prescripcion prescripcion = new Prescripcion();
        prescripcion.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        prescripcion.setIdPrescripcion(Comunes.getUUID());
        //TODO De donde se saca la estructura de la prescripcion
        prescripcion.setIdEstructura(Constantes.IDESTRUCTURA_CONSULTA_EXTERNA_HOSPITAL);
        prescripcion.setIdPacienteServicio(this.pacienteServicio.getIdPacienteServicio());
        prescripcion.setFolio(this.folioPrescripcion);
        prescripcion.setFechaPrescripcion(this.fechaReceta);
        prescripcion.setFechaFirma(new java.util.Date());
        prescripcion.setTipoConsulta(TipoConsulta_Enum.CONSULTA_EXTERNA.getValue());
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

    private Prescripcion obtenerDatosPrescripcionHospChiconcuac() {
        Prescripcion prescripcionCh = new Prescripcion();
        prescripcionCh.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        for (PrescripcionInsumo_Extended item : this.listaPrescripcionInsumoExtendedValidacion) {
            if (item.getCantidadSolicitada() - item.getCantidadEntregada() != 0) {
                prescripcionCh.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
                break;
            }
        }
        prescripcionCh.setIdPrescripcion(Comunes.getUUID());
        //TODO De donde se saca la estructura de la prescripcion
        prescripcionCh.setIdEstructura(this.idServicio);
        prescripcionCh.setIdPacienteServicio(this.pacienteServicio.getIdPacienteServicio());
        prescripcionCh.setFolio(this.prefijo + this.folioPrescripcion);
        prescripcionCh.setFechaPrescripcion(this.fechaReceta);
        prescripcionCh.setFechaFirma(new java.util.Date());
        prescripcionCh.setTipoConsulta(this.tipoConsulta);
        prescripcionCh.setTipoPrescripcion(TipoPrescripcion_Enum.MANUAL.getValue());
        prescripcionCh.setIdMedico(this.medico.getIdUsuario());
        prescripcionCh.setRecurrente(false);
        prescripcionCh.setComentarios(this.prescripcionInsumo.getComentarios());
        prescripcionCh.setIdEstatusGabinete(EstatusGabinete_Enum.PENDIENTE.getValue());
        prescripcionCh.setInsertFecha(new java.util.Date());
        prescripcionCh.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        prescripcionCh.setIdEntidadHospitalaria(
                (this.entidadHospitalaria != null) ? this.entidadHospitalaria.getIdEntidadHospitalaria() : null);
        return prescripcionCh;
    }

    public Surtimiento_Extend obtenerDatosSurtimiento(Prescripcion prescripcion) {

        Surtimiento_Extend surtimientoEx = new Surtimiento_Extend();
        Paciente pacienteTemp = null;
        Usuario medicoTemp = null;
        try {
            pacienteTemp = pacienteService.obtenerPacienteByIdPaciente(this.pacienteExtended.getIdPaciente());
            medicoTemp = usuarioService.obtenerUsuarioPorId(this.medico.getIdUsuario());
        } catch (Exception ex) {
            LOGGER.error("Error al obtenerDatosSurtimiento : {}", ex.getMessage());
        }
        surtimientoEx.setIdSurtimiento(Comunes.getUUID());
        surtimientoEx.setIdEstructuraAlmacen(this.usuarioSession.getIdEstructura());
        surtimientoEx.setIdPrescripcion(prescripcion.getIdPrescripcion());
        surtimientoEx.setFechaProgramada(new Date());
        surtimientoEx.setFolio(Comunes.generaNumeroReceta());
        surtimientoEx.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
        surtimientoEx.setInsertFecha(new Date());
        surtimientoEx.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        surtimientoEx.setFolioPrescripcion(this.folioPrescripcion);

        if (pacienteTemp != null) {
            surtimientoEx.setClaveDerechohabiencia(pacienteTemp.getClaveDerechohabiencia());
            surtimientoEx.setNombrePaciente(pacienteTemp.getNombreCompleto()
                    + " " + pacienteTemp.getApellidoPaterno()
                    + " " + pacienteTemp.getApellidoMaterno());
        }
        if (medicoTemp != null) {
            surtimientoEx.setNombreMedico(medicoTemp.getNombre() + " " + medicoTemp.getApellidoPaterno() + " " + medicoTemp.getApellidoMaterno());
        }
        return surtimientoEx;

    }

    public Surtimiento_Extend obtenerDatosSurtimientoChiconcuac(Prescripcion prescripcion) {

        Surtimiento_Extend surtimientoExt = new Surtimiento_Extend();
        Paciente pacienteTemp = null;
        Usuario medicoTemp = null;
        try {
            pacienteTemp = pacienteService.obtenerPacienteByIdPaciente(this.pacienteExtended.getIdPaciente());
            medicoTemp = usuarioService.obtenerUsuarioPorId(this.medico.getIdUsuario());
        } catch (Exception ex) {
            LOGGER.error("Error al obtenerDatosSurtimiento : {}", ex.getMessage());
        }
        surtimientoExt.setIdSurtimiento(Comunes.getUUID());
        surtimientoExt.setIdEstructuraAlmacen(this.usuarioSession.getIdEstructura());
        surtimientoExt.setIdPrescripcion(prescripcion.getIdPrescripcion());
        surtimientoExt.setFechaProgramada(new Date());
        surtimientoExt.setFolio(Comunes.generaNumeroReceta());
        surtimientoExt.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
        surtimientoExt.setInsertFecha(new Date());
        surtimientoExt.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

        surtimientoExt.setFolioPrescripcion(this.folioPrescripcion);
        if (pacienteTemp != null) {
            surtimientoExt.setClaveDerechohabiencia(pacienteTemp.getClaveDerechohabiencia());
            surtimientoExt.setNombrePaciente(pacienteTemp.getNombreCompleto()
                    + " " + pacienteTemp.getApellidoPaterno()
                    + " " + pacienteTemp.getApellidoMaterno());
        }
        if (medicoTemp != null) {
            surtimientoExt.setNombreMedico(medicoTemp.getNombre() + " " + medicoTemp.getApellidoPaterno() + " " + medicoTemp.getApellidoMaterno());
        }
        return surtimientoExt;
    }

    private List<DiagnosticoPaciente> obtenerListaDiagnosticosPaciente(Prescripcion prescripcion) {
        List<DiagnosticoPaciente> diagnosticoPacienteList = new ArrayList<>();
        DiagnosticoPaciente diagnostPaciente;
        for (Diagnostico item : this.diagnosticoList) {
            diagnostPaciente = new DiagnosticoPaciente();
            diagnostPaciente.setIdDiagnosticoPaciente(Comunes.getUUID());
            diagnostPaciente.setIdPrescripcion(prescripcion.getIdPrescripcion());
            diagnostPaciente.setFechaDiagnostico(new java.util.Date());
            diagnostPaciente.setIdUsuarioDiagnostico(this.pacienteExtended.getIdPaciente());
            diagnostPaciente.setIdDiagnostico(item.getIdDiagnostico());
            diagnostPaciente.setFechaFinDiagnostico(null);
            diagnostPaciente.setIdUsuarioDiagnosticoTratado(null);
            diagnostPaciente.setIdEstatusDiagnostico(EstatusDiagnostico_Enum.DIAGNOSTICADO.getValue());
            diagnostPaciente.setInsertFecha(new java.util.Date());
            diagnostPaciente.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            diagnostPaciente.setUpdateFecha(null);
            diagnostPaciente.setUpdateIdUsuario(null);
            diagnosticoPacienteList.add(diagnostPaciente);
        }
        return diagnosticoPacienteList;
    }

    private List<PrescripcionInsumo> obtenerListaPrescripcionInsumo(Prescripcion prescripcion) {
        List<PrescripcionInsumo> prescInsumoList = new ArrayList<>();
        PrescripcionInsumo prescInsumo;
        for (PrescripcionInsumo_Extended it : this.prescripcionInsumoExtendedList) {
            prescInsumo = new PrescripcionInsumo();
            prescInsumo.setIdPrescripcionInsumo(it.getIdPrescripcionInsumo());
            prescInsumo.setIdPrescripcion(prescripcion.getIdPrescripcion());
            prescInsumo.setIdInsumo(it.getIdInsumo());
            prescInsumo.setFechaInicio(new Date());
            prescInsumo.setDosis(it.getDosis());
            prescInsumo.setFrecuencia(it.getFrecuencia());
            prescInsumo.setDuracion(it.getDuracion());
            prescInsumo.setComentarios(null);
            prescInsumo.setIdEstatusPrescripcion(prescripcion.getIdEstatusPrescripcion());
            prescInsumo.setIndicaciones(it.getIndicaciones());
            prescInsumo.setInsertFecha(new Date());
            prescInsumo.setInsertIdUsuario(this.pacienteExtended.getIdPaciente());
            prescInsumo.setUpdateFecha(null);
            prescInsumo.setUpdateIdUsuario(null);
            prescInsumoList.add(prescInsumo);
        }
        return prescInsumoList;
    }

    private List<SurtimientoInsumo_Extend> obtenerListaSurtimientoInsumo(Surtimiento surtimiento) {
        List<SurtimientoInsumo_Extend> listaSurtimientoInsumo = new ArrayList<>();
        for (PrescripcionInsumo_Extended ite : this.prescripcionInsumoExtendedList) {
            SurtimientoInsumo_Extend surtimientoInsumo = new SurtimientoInsumo_Extend();
            surtimientoInsumo.setIdSurtimientoInsumo(ite.getIdSurtimientoInsumo());
            surtimientoInsumo.setIdSurtimiento(surtimiento.getIdSurtimiento());
            surtimientoInsumo.setIdPrescripcionInsumo(ite.getIdPrescripcionInsumo());
            surtimientoInsumo.setFechaEnviada(new Date());
            surtimientoInsumo.setIdUsuarioEnviada(this.usuarioSession.getIdUsuario());
            surtimientoInsumo.setCantidadEnviada(ite.getCantidadEntregada());
            BigDecimal dosis = ite.getDosis().setScale(0, RoundingMode.CEILING);
            surtimientoInsumo.setCantidadSolicitada((24 / ite.getFrecuencia()) * dosis.intValue() * ite.getDuracion());
            surtimientoInsumo.setFechaRecepcion(new Date());
            surtimientoInsumo.setFechaProgramada(new Date());
            surtimientoInsumo.setIdUsuarioRecepcion(this.usuarioSession.getIdUsuario());
            surtimientoInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
            surtimientoInsumo.setInsertFecha(new Date());
            surtimientoInsumo.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            surtimientoInsumo.setIdInventario(ite.getIdInventario());
            listaSurtimientoInsumo.add(surtimientoInsumo);
        }
        return listaSurtimientoInsumo;
    }


    private List<MovimientoInventario> obtenerListaMovInventarios(
            List<InventarioExtended> listaInventario) {
        List<MovimientoInventario> listaMovimientoInventario = new ArrayList<>();
        for (InventarioExtended ite : listaInventario) {
            MovimientoInventario movimientoInve = new MovimientoInventario();
            movimientoInve.setIdMovimientoInventario(Comunes.getUUID());
            movimientoInve.setIdTipoMotivo(20);
            movimientoInve.setFecha(new Date());
            movimientoInve.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
            movimientoInve.setIdEstrutcuraOrigen(ite.getIdEstructura());
            movimientoInve.setIdEstrutcuraDestino(ite.getIdEstructura());
            movimientoInve.setIdInventario(ite.getIdInventario());
            movimientoInve.setCantidad(ite.getCantidadEntregada());
            movimientoInve.setFolioDocumento(this.prefijo + this.folioPrescripcion);
            listaMovimientoInventario.add(movimientoInve);
        }
        return listaMovimientoInventario;
    }

    
    private List<InventarioExtended> obtenerListaInventarios(
            List<PrescripcionInsumo_Extended> listaPrescripcionInsumoExtended) {
        List<InventarioExtended> listaInvetario = new ArrayList<>();
        for (PrescripcionInsumo_Extended ite : listaPrescripcionInsumoExtended) {
            for (SurtimientoEnviado_Extend item2 : ite.getSurtimientoEnviadoExtendedList()) {
                InventarioExtended inventario = new InventarioExtended();
                inventario.setIdInventario(item2.getIdInventarioSurtido());
                inventario.setCantidadEntregada(item2.getCantidadEnviado());
                inventario.setIdInsumo(ite.getIdInsumo());
                inventario.setIdEstructura(ite.getIdEstructura());
                inventario.setLote(item2.getLote());
                inventario.setCantidadXCaja(ite.getCantidadXCaja());
                inventario.setUpdateFecha(new Date());
                inventario.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                listaInvetario.add(inventario);
            }
        }
        return listaInvetario;
    }
        
    
    public void eliminarEntidadHospitalaria(String idEntidadHospitalaria) {
        for (short i = 0; i < this.entidadList.size(); i++) {
            EntidadHospitalaria entidadHospi = this.entidadList.get(i);
            if (entidadHospi.getIdEntidadHospitalaria().equalsIgnoreCase(idEntidadHospitalaria)) {
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
    
    public void eliminarDiagnostico(String idDiagnostico) {
        for (short i = 0; i < this.diagnosticoList.size(); i++) {
            Diagnostico diagnosticoTemp = this.diagnosticoList.get(i);
            if (diagnosticoTemp.getIdDiagnostico().equalsIgnoreCase(idDiagnostico)) {
                this.diagnosticoList.remove(i);
                break;
            }
        }
    }
    

    public void imprimirTcket(
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception {
        LOGGER.debug("mx.mc.magedbean.SurtRecetaManualMB.imprimirTcket()");
        boolean status = Constantes.INACTIVO;
        try {
            
            byte[] buffer = reportesService.imprimeSurtimientoPrescManual(
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

    public void imprimirTcketChiconcuac(Prescripcion prescripcion,
            Surtimiento_Extend surtimientoExtendedSelected,
            String nombreUsuario) throws Exception {

        LOGGER.debug("mx.mc.magedbean.SurtRecetaManualMB.imprimirTcketChiconcuac()");
        boolean status = Constantes.INACTIVO;
        try {
            this.entidadHospitalaria = this.entidadHospitalariaService.obtener(new EntidadHospitalaria());            
            byte[] buffer = reportesService.imprimeSurtimientoPrescManualChiconcuac(prescripcion,this.entidadHospitalaria,
                    surtimientoExtendedSelected,nombreUsuario);

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket_%s.pdf", surtimientoExtendedSelected.getFolio()));
            }
        } catch (Exception ex) {
            LOGGER.error("Error al generar la Impresión: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }


    private void obtenerParentNode(TreeNode nodo) {
        TreeNode node = nodo.getParent();
        if (node != null && node.getData() != "Root") {
            listNode.add(nodo.getParent().toString());
            obtenerParentNode(node);
        }
    }

    
    public void onNodeSelect(NodeSelectEvent event) {
        nameUnidad = event.getTreeNode().toString();
        listNode = new ArrayList<>();
        obtenerParentNode(event.getTreeNode());
        pathNode = "";
        String auxi = "";
        for (int i = listNode.size() - 1; i >= 0; i--) {
            auxi = i == 0 ? "" : "/";
            pathNode += listNode.get(i) + auxi;
        }
        if (pathNode.length() > 200) {
            pathNode = pathNode.substring(0, 200);
        }
        if (medicoExtended != null) {
            medicoExtended.setPathEstructura(pathNode);
        }
    }
    
    public void validarFolioRecetaOnBlur() {
        String res = validarFolioReceta();
        if (res != null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, res, "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, true);
        }
    }

    public String validarFolioReceta() {
        String resp = null;
        Prescripcion prescTemp = null;
        Prescripcion presc = new Prescripcion();
        if (this.habilitaEditaryCancelarSurtimientoManual) {
            presc.setFolio(this.prefijo + this.folioPrescripcion);
        } else {
            presc.setFolio(this.folioPrescripcion);
        }

        try {
            prescTemp = prescripcionService.obtener(presc);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(SurtRecetaManualMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (prescTemp != null) {
            resp = RESOURCES.getString("prc.manual.req.folio.exist");
        }
        if (this.habilitaEditaryCancelarSurtimientoManual && this.folioPrescripcion != null) {
            if (this.prefijo.equalsIgnoreCase(PrefijoPrescripcion_Enum.CONTROLADA.getValue())) {
                Pattern patron = Pattern.compile("[^0-9]");
                Matcher encaja = patron.matcher(this.folioPrescripcion);
                if (encaja.find() || this.folioPrescripcion.length() < 4) {
                    resp = "El formato del folio no es correcto. Ejemplo F001";
                }
            } else if (this.prefijo.equalsIgnoreCase(PrefijoPrescripcion_Enum.NORMAL.getValue())) {
                Pattern patron = Pattern.compile("[^0-9]");
                Matcher encaja = patron.matcher(this.folioPrescripcion);
                if (encaja.find() || this.folioPrescripcion.length() < 7) {
                    resp = "El formato del folio no es correcto. Ejemplo F0000001";
                }
            }
        }
        return resp;
    }

    public void validaCancelarPrescripcion(String idPrescripcion) {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.validaCancelarPrescripcion()");
        boolean estatus = Constantes.INACTIVO;
        if (!permiso.isPuedeEditar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);
        } else if (idPrescripcion == null) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
        } else {
            try {
                Prescripcion pre = new Prescripcion();
                pre.setIdPrescripcion(idPrescripcion);
                Prescripcion prescripSelected = prescripcionService.obtener(pre);
                if (prescripSelected == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
                } else {
                    estatus = cancelarPrescripcion(idPrescripcion);
                }
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
            }
        }
        obtenerRecetasManuales();
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    /**
     * Cancela una prescripción así como todos los surtimientos dependientes de
     * esta prescripción y todos medicamentos de la prescripción
     *
     * @return
     */
    private boolean cancelarPrescripcion(String idPrescripcion) {
        LOGGER.error("mx.mc.magedbean.PrescripcionMB.cancelarPrescripcion()");
        boolean res = Constantes.INACTIVO;
        try {
            if (this.habilitaEditaryCancelarSurtimientoManual) {
                Prescripcion pr = new Prescripcion();
                pr.setIdPrescripcion(idPrescripcion);
                Prescripcion prescripSelected = prescripcionService.obtener(pr);
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
        } catch (Exception exc) {
            LOGGER.error(RESOURCES.getString(prcNopuedecancelar), exc);
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(prcNopuedecancelar), null);
        }
        return res;
    }

    public String generaFolCancelacion(String folio) {
        char[] array = folio.toCharArray();
        boolean constant = false;
        String aux0 = "0";
        String aux1 = "";
        for (int j = 1; j < folio.length(); j++) {
            if (constant) {
                aux0 += array[j];
            }
            if (!Character.isDigit(array[j])) {
                constant = true;
            } else if (!constant) {
                aux1 += array[j];
            }

        }
        BigInteger secuencia1 = new BigInteger(aux0);
        secuencia1 = secuencia1.add(BigInteger.ONE);
        return array[0] + aux1 + "C" + secuencia1;
    }

    public void surtirPrescripcionParcial() {
        boolean resp = false;
        boolean surtParcial = false;
        List<SurtimientoInsumo_Extend> listaSurtimientoInsumoEx = this.listaInsumosPendientes;
        List<MovimientoInventario> listaMovimientos = new ArrayList<>();
        List<Inventario> listaInv = new ArrayList<>();

        Paciente pacienteTempo = new Paciente();
        try {
            pacienteTempo = pacienteService.obtenerPacienteByIdPaciente(prescripcionSelected.getIdPaciente());
        } catch (Exception e) {
            LOGGER.error(RESOURCES.getString(prcNopuedecancelar), e);
        }

        Surtimiento_Extend surtimientoE = new Surtimiento_Extend();
        surtimientoE.setIdSurtimiento(Comunes.getUUID());
        surtimientoE.setIdEstructuraAlmacen(this.usuarioSession.getIdEstructura());
        surtimientoE.setIdPrescripcion(this.prescripcionSelected.getIdPrescripcion());
        surtimientoE.setFechaProgramada(this.prescripcionSelected.getFechaPrescripcion());
        surtimientoE.setFolio(Comunes.generaNumeroReceta());
        surtimientoE.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
        surtimientoE.setInsertFecha(new Date());
        surtimientoE.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
        surtimientoE.setClaveDerechohabiencia(pacienteTempo.getClaveDerechohabiencia());

        for (SurtimientoInsumo_Extend it : listaSurtimientoInsumoEx) {
            Integer duSolicitada = it.getCantidadSolicitada() - it.getCantidadEnviada();

            it.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
            it.setIdSurtimiento(surtimientoE.getIdSurtimiento());
            it.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
            it.setFechaEnviada(new Date());
            it.setIdUsuarioEnviada(this.usuarioSession.getIdUsuario());
            it.setIdUsuarioRecepcion(this.usuarioSession.getIdUsuario());
            it.setCantidadEnviada(it.getCantidadRecepcion());
            it.setCantidadSolicitada(duSolicitada);

            if (it.getSurtimientoEnviadoExtendList() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Tiene que surtir todos los medicamentos", null);
                PrimeFaces.current().ajax().addCallbackParam(paramError, true);
                return;
            }
            for (SurtimientoEnviado_Extend item2 : it.getSurtimientoEnviadoExtendList()) {
                Inventario inventario = new Inventario();
                inventario.setIdInventario(item2.getIdInventarioSurtido());
                inventario.setCantidadActual(item2.getCantidadRecibido());
                listaInv.add(inventario);

                MovimientoInventario movInventario = new MovimientoInventario();
                movInventario.setIdMovimientoInventario(Comunes.getUUID());
                movInventario.setIdTipoMotivo(TipoMotivo_Enum.SAL_AJUSTE_INVENTARIO.getMotivoValue());
                movInventario.setFecha(new Date());
                movInventario.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                //TODO falta poner estructura origen con estructura de paciente
                movInventario.setIdEstrutcuraOrigen(this.usuarioSession.getIdEstructura());
                movInventario.setIdEstrutcuraDestino(this.usuarioSession.getIdEstructura());
                movInventario.setIdInventario(item2.getIdInventarioSurtido());
                movInventario.setCantidad(item2.getCantidadRecibido());
                movInventario.setFolioDocumento(this.prescripcionSelected.getFolio());
                listaMovimientos.add(movInventario);
            }
        }
        for (SurtimientoInsumo_Extend it : listaSurtimientoInsumoEx) {
            Integer dunSolicitada = it.getCantidadSolicitada() - it.getCantidadEnviada();
            if (dunSolicitada != 0) {
                surtParcial = true;
                break;
            }
        }

        Prescripcion prescr = new Prescripcion();
        prescr.setIdPrescripcion(this.prescripcionSelected.getIdPrescripcion());
        if (surtParcial) {
            prescr.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
        } else {
            prescr.setIdEstatusPrescripcion(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        }
        try {
            resp = surtimientoService.registrarSurtimientoParcial(
                    surtimientoE, listaSurtimientoInsumoEx, prescr, listaInv, listaMovimientos);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
        }

        if (resp) {
            try {
                imprimirTcketChiconcuac(this.prescripcionSelected, surtimientoE, usuarioSession.getNombreUsuario());
            } catch (Exception ex) {
                LOGGER.error(RESOURCES.getString(prcNopuedecancelar), ex);
            }
            Mensaje.showMessage("Info", RESOURCES.getString(prcManualExitoReceta), "");
            PrimeFaces.current().ajax().addCallbackParam(paramError, false);
        }
        obtenerRecetasManuales();
    }
            
    public Date getFechaReceta() {
        return fechaReceta;
    }

    public void setFechaReceta(Date fechaReceta) {
        this.fechaReceta = fechaReceta;
    }
    
    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }

    
    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }
    
    public EntidadHospitalaria getEntidadHospitalaria() {
        return entidadHospitalaria;
    }

    public void setEntidadHospitalaria(EntidadHospitalaria entidadHospitalaria) {
        this.entidadHospitalaria = entidadHospitalaria;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    
    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }    
    
       
    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }
   
    public List<Diagnostico> getDiagnosticoList() {
        return diagnosticoList;
    }

    public void setDiagnosticoList(List<Diagnostico> diagnosticoList) {
        this.diagnosticoList = diagnosticoList;
    }

    public SesionMB getSesion() {
        return sesion;
    }

    public void setSesion(SesionMB sesion) {
        this.sesion = sesion;
    }

    public PrescripcionInsumo_Extended getPrescripcionInsumoExtendedSelected() {
        return prescripcionInsumoExtendedSelected;
    }

    public void setPrescripcionInsumoExtendedSelected(PrescripcionInsumo_Extended prescripcionInsumoExtendedSelected) {
        this.prescripcionInsumoExtendedSelected = prescripcionInsumoExtendedSelected;
    }
    
    public List<PrescripcionInsumo_Extended> getPrescripcionInsumoExtendedList() {
        return prescripcionInsumoExtendedList;
    }

    public void setPrescripcionInsumoExtendedList(List<PrescripcionInsumo_Extended> prescripcionInsumoExtendedList) {
        this.prescripcionInsumoExtendedList = prescripcionInsumoExtendedList;
    }    

    public PrescripcionInsumo getPrescripcionInsumo() {
        return prescripcionInsumo;
    }

    public void setPrescripcionInsumo(PrescripcionInsumo prescripcionInsumo) {
        this.prescripcionInsumo = prescripcionInsumo;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<String> getListaIdTurnos() {
        return listaIdTurnos;
    }

    public void setListaIdTurnos(List<String> listaIdTurnos) {
        this.listaIdTurnos = listaIdTurnos;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }
    
    public List<Turno> getListaTurnos() {
        return listaTurnos;
    }

    public void setListaTurnos(List<Turno> listaTurnos) {
        this.listaTurnos = listaTurnos;
    }

    public Visita getVisita() {
        return visita;
    }

    public void setVisita(Visita visita) {
        this.visita = visita;
    }
    
    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public void setCantidadEntregada(Integer cantidadEntregada) {
        this.cantidadEntregada = cantidadEntregada;
    }

    public PacienteServicio getPacienteServicio() {
        return pacienteServicio;
    }

    public void setPacienteServicio(PacienteServicio pacienteServicio) {
        this.pacienteServicio = pacienteServicio;
    }

    public List<Prescripcion_Extended> getListaRecetasManuales() {
        return listaRecetasManuales;
    }

    public void setListaRecetasManuales(List<Prescripcion_Extended> listaRecetasManuales) {
        this.listaRecetasManuales = listaRecetasManuales;
    }

    public Diagnostico getDiagnosticoSelected() {
        return diagnosticoSelected;
    }

    public void setDiagnosticoSelected(Diagnostico diagnosticoSelected) {
        this.diagnosticoSelected = diagnosticoSelected;
    }
    
    public Prescripcion_Extended getRecetaManualSelect() {
        return recetaManualSelect;
    }

    public void setRecetaManualSelect(Prescripcion_Extended recetaManualSelect) {
        this.recetaManualSelect = recetaManualSelect;
    }
    
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

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }   

    public String getNameUnidad() {
        return nameUnidad;
    }

    public void setNameUnidad(String nameUnidad) {
        this.nameUnidad = nameUnidad;
    }
    
    public TreeNode getSelectNode() {
        return selectNode;
    }

    public void setSelectNode(TreeNode selectNode) {
        this.selectNode = selectNode;
    }
      
    public List<Estructura> getListEstructura() {
        return listEstructura;
    }

    public void setListEstructura(List<Estructura> listEstructura) {
        this.listEstructura = listEstructura;
    }        
    
    public List<String> getListNode() {
        return listNode;
    }

    public void setListNode(List<String> listNode) {
        this.listNode = listNode;
    } 
    public String getUestructura() {
        return uestructura;
    }

    public void setUestructura(String uestructura) {
        this.uestructura = uestructura;
    }
    
    public UsuarioRol getUsuarioRolSelect() {
        return usuarioRolSelect;
    }

    public void setUsuarioRolSelect(UsuarioRol usuarioRolSelect) {
        this.usuarioRolSelect = usuarioRolSelect;
    }

    public List<VistaUsuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<VistaUsuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public void setTipoConsulta(String tipoConsulta) {
        this.tipoConsulta = tipoConsulta;
    }

    public List<CatalogoGeneral> getListaTipoPacientes() {
        return listaTipoPacientes;
    }

    public void setListaTipoPacientes(List<CatalogoGeneral> listaTipoPacientes) {
        this.listaTipoPacientes = listaTipoPacientes;
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
       
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
    public void setListaServicios(List<Estructura> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public String getIdServicio() {
        return idServicio;
    }
    
    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public List<SurtimientoInsumo_Extend> getListaInsumosPendientes() {
        return listaInsumosPendientes;
    }

    public boolean isHabilitaEditaryCancelarSurtimientoManual() {
        return habilitaEditaryCancelarSurtimientoManual;
    }

    public void setHabilitaEditaryCancelarSurtimientoManual(boolean habilitaEditaryCancelarSurtimientoManual) {
        this.habilitaEditaryCancelarSurtimientoManual = habilitaEditaryCancelarSurtimientoManual;
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
}