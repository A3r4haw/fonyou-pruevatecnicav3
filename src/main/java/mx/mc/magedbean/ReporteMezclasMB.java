package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusSolucion_Enum;
import mx.mc.lazy.MezclasLazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EstatusSolucion;
import mx.mc.model.Mezcla;
import mx.mc.model.Paciente;
import mx.mc.model.Usuario;
import mx.mc.service.DiagnosticoService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;

/**
 *
 * @author cervanets
 */
@Controller
@Scope(value = "view")
public class ReporteMezclasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteMezclasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private PermisoUsuario permiso;
    private transient List<Mezcla> listaMezclas;
    private ParamBusquedaReporte paramBusquedaReporte;

    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private String pathPdf;

    private int tipoInsumo;
    private int insumoMedicamento;
    private int insumoMaterial;
    private Paciente pacienteSelect;
    private Usuario medicoSelect;
    
    private boolean visiblePrescripcion;
    private boolean visibleRechazo;
    private boolean visibleRegValidacion;
    private boolean visibleValidacion;
    private boolean visibleDispensacion;
    private boolean visiblePreparacion;
    private boolean visibleInspecciona;
    private boolean visibleAcondiciona;
    private boolean visibleDistribuye;
    private boolean visibleEntrega;
    private boolean visibleAutorizacion;

    private transient List<Estructura> listaEstructuras;
    private transient List<EstatusSolucion> listaIdEstatusSolucion;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient DiagnosticoService diagosticoService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadService;

    private MezclasLazy mezclasLazy;

    /**
     *
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.init()");
        try {
            initialize();
            obtenerEstructuras();
            obtenerEstatusSolucion();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {} ", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.initialize()");
        try {
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTEMEZCLAS.getSufijo());

            this.fechaInicio = FechaUtil.obtenerFechaInicio();
            this.fechaFin = FechaUtil.obtenerFechaFin();
            this.fechaActual = FechaUtil.getFechaActual();

            this.insumoMedicamento = 38;
            this.insumoMaterial = 39;

            this.paramBusquedaReporte = new ParamBusquedaReporte();
            this.paramBusquedaReporte.setFechaInicio(fechaInicio);
            this.paramBusquedaReporte.setFechaFin(fechaFin);

            this.listaMezclas = new ArrayList<>();
            this.listaEstructuras = new ArrayList<>();
            pacienteSelect = new Paciente();
            medicoSelect = new Usuario();
            this.visiblePrescripcion = Constantes.ACTIVO;    
            this.visibleRechazo = Constantes.INACTIVO;
            this.visibleRegValidacion = Constantes.INACTIVO;
            this.visibleValidacion = Constantes.INACTIVO;
            this.visibleDispensacion = Constantes.INACTIVO;
            this.visiblePreparacion = Constantes.INACTIVO;
            this.visibleInspecciona = Constantes.INACTIVO;
            this.visibleAcondiciona = Constantes.INACTIVO;
            this.visibleDistribuye = Constantes.INACTIVO;
            this.visibleEntrega = Constantes.INACTIVO;
            this.visibleAutorizacion = Constantes.INACTIVO;

        } catch (Exception ex) {
            LOGGER.error("Error al obtener inicializar :: {}", ex.getMessage());
        }

    }

    /**
     *
     */
    public void obtenerEstructuras() {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.obtenerEstructuras()");
        try {
            this.listaEstructuras = estructuraService.obtenerServicios();
        } catch (Exception e) {
            LOGGER.error("Error al buscar servicios :: {} ", e.getMessage());
        }
    }

    public void obtenerEstatusSolucion() {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.obtenerEstatusSolucion()");
        try {
            this.listaIdEstatusSolucion = new ArrayList<>();
//            this.listaIdEstatusSolucion.add(new EstatusSolucion(1, "PRESCRIPCION REGISTRADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(2, "PRESCRIPCION SOLICITADA"));
//            this.listaIdEstatusSolucion.add(new EstatusSolucion(3, "ORDEN CREADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(4, "PRESCRIPCION RECHAZADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(5, "PRESCRIPCION VALIDADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(6, "OP RECHAZADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(7, "OP VALIDADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(8, "MEZCLA RECHAZADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(9, "MEZCLA ERROR AL PREPARAR"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(10, "MEZCLA PREPARADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(11, "INSPECCION NO CONFORME"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(12, "INSPECCION CONFORME"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(13, "ACONDICIONAMIENTO NO CONFORME"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(14, "ACONDICIONAMIENTO CONFORME"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(15, "MEZCLA EN DISTRIBUCIÓN"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(16, "MEZCLA ENTREGADA"));
//            this.listaIdEstatusSolucion.add(new EstatusSolucion(17, "MEZCLA NO ENTREGADA"));
//            this.listaIdEstatusSolucion.add(new EstatusSolucion(18, "MEZCLA NO ACEPTADA"));
//            this.listaIdEstatusSolucion.add(new EstatusSolucion(19, "MEZCLA CADUCADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(20, "CANCELADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(21, "RETIRADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(22, "DEVUELTA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(23, "RECIBIDA"));
//            this.listaIdEstatusSolucion.add(new EstatusSolucion(24, "MINISTRADA"));
//            this.listaIdEstatusSolucion.add(new EstatusSolucion(25, "NO MINISTRADA"));
            this.listaIdEstatusSolucion.add(new EstatusSolucion(26, "POR AUTORIZAR"));
        } catch (Exception e) {
            LOGGER.error("Error al buscar servicios :: {} ", e.getMessage());
        }
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.consultar()");
        try {
            this.paramBusquedaReporte.setNuevaBusqueda(true);
            this.paramBusquedaReporte.setIntervalDate(30);

            if (this.tipoInsumo > 0) {
                this.paramBusquedaReporte.setTipoInsumo(this.tipoInsumo);
            } else {
                this.paramBusquedaReporte.setTipoInsumo(0);
            }

            if (this.paramBusquedaReporte.getIdEstructura() != null
                    && !this.paramBusquedaReporte.getIdEstructura().isEmpty()) {
                List<String> l = new ArrayList<>();
                l.add(this.paramBusquedaReporte.getIdEstructura());
                this.paramBusquedaReporte.setListaEstructuras(l);
            }

            if (this.paramBusquedaReporte.getListInsumos() != null
                    && !this.paramBusquedaReporte.getListInsumos().isEmpty()) {
                List<String> l = new ArrayList<>();
                for (Medicamento o : this.paramBusquedaReporte.getListInsumos()) {
                    l.add(o.getIdMedicamento());
                }
                this.paramBusquedaReporte.setListaInsumos(l);
            } else {
                this.paramBusquedaReporte.setListaInsumos(null);
            }

            if(medicoSelect != null && medicoSelect.getIdUsuario() != null) {
                List<String> l = new ArrayList<>();
                l.add(medicoSelect.getIdUsuario());
                this.paramBusquedaReporte.setListaMedicos(l);
            } else {
                this.paramBusquedaReporte.setListaMedicos(null);
            }
            /*
            if (this.paramBusquedaReporte.getListMedicos() != null
                    && !this.paramBusquedaReporte.getListMedicos().isEmpty()) {
                List<String> l = new ArrayList<>();
                for (Usuario o : this.paramBusquedaReporte.getListMedicos()) {
                    l.add(o.getIdUsuario());
                }
                this.paramBusquedaReporte.setListaMedicos(l);
            } else {
                this.paramBusquedaReporte.setListaMedicos(null);
            }*/

                if(pacienteSelect != null && pacienteSelect.getIdPaciente() != null) {
                List<String> l = new ArrayList<>();
                l.add(pacienteSelect.getIdPaciente());
                this.paramBusquedaReporte.setListaPaciente(l);
            } else {
                 this.paramBusquedaReporte.setListaPaciente(null);
            }
                        
            /*if (this.paramBusquedaReporte.getPacienteList() != null
                    && !this.paramBusquedaReporte.getPacienteList().isEmpty()) {
                List<String> l = new ArrayList<>();
                for (Paciente o : this.paramBusquedaReporte.getPacienteList()) {
                    l.add(o.getIdPaciente());
                }
                this.paramBusquedaReporte.setListaPaciente(l);
            } else {
                this.paramBusquedaReporte.setListaPaciente(null);
            }*/

            if (this.paramBusquedaReporte.getListUsuarios() != null
                    && !this.paramBusquedaReporte.getListUsuarios().isEmpty()) {
                List<String> l = new ArrayList<>();
                for (Usuario o : this.paramBusquedaReporte.getListUsuarios()) {
                    l.add(o.getIdUsuario());
                }
                this.paramBusquedaReporte.setListaUsuarios(l);
            } else {
                this.paramBusquedaReporte.setListaUsuarios(null);
            }

            if (this.paramBusquedaReporte.getListDiagosticos()!= null
                    && !this.paramBusquedaReporte.getListDiagosticos().isEmpty()) {
                List<String> l = new ArrayList<>();
                for (Diagnostico o : this.paramBusquedaReporte.getListDiagosticos()) {
                    l.add(o.getIdDiagnostico());
                }
                this.paramBusquedaReporte.setListaDiagostico(l);
            } else {
                this.paramBusquedaReporte.setListaDiagostico(null);
            }

            this.mezclasLazy = new MezclasLazy(
                    this.reporteMovimientosService,
                    this.paramBusquedaReporte);

            LOGGER.trace("Resultados: {}", this.mezclasLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar :: {} ", e1.getMessage());
        }
    }

    /**
     * Consulta de insumos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));
        } catch (Exception ex) {
            LOGGER.error("Error al consultar el medicamento :: {} ", ex.getMessage());
            Mensaje.showMessage("Error", "Error al consultar el medicamento", null);
        }
        return insumosList;
    }

    /**
     * Consulta de pacientes por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Paciente> autocompletePacientes(String query) {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.autocompletePacientes()");
        List<Paciente> pacientesList = new ArrayList<>();
        try {
            pacientesList.addAll(this.pacienteService.obtenerPacientes(query));
        } catch (Exception ex) {
            LOGGER.error("Error al consultar el paciente :: {} ", ex.getMessage());
            Mensaje.showMessage("Error", "Error al consultar Paciente.", null);
        }
        return pacientesList;
    }

    /**
     * Consulta de usuarios por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Usuario> autocompleteUsuarios(String query) {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.autocompleteUsuarios()");
        List<Usuario> usuariosList = new ArrayList<>();
        try {
            usuariosList.addAll(this.usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Error al consultar el usuario :: {} ", ex.getMessage());
            Mensaje.showMessage("Error", "Error al consultar usuario.", null);
        }
        return usuariosList;
    }
    
    /**
     * Consulta de diagnósticos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     * @param query
     * @return 
     */
    public List<Diagnostico> autocompleteDiagnostico(String query) {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.autocompleteDiagnostico()");
        List<Diagnostico> diagnosticosList = new ArrayList<>();
        try {
            diagnosticosList.addAll(this.diagosticoService.obtenerListaAutoComplete(query));
        } catch (Exception ex) {
            LOGGER.error("Error al consultar el diagnostico :: {} ", ex.getMessage());
            Mensaje.showMessage("Error", "Error al consultar diagnóstico .", null);
        }
        return diagnosticosList;
    }

    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }

    /**
     *
     * @throws java.lang.Exception
     */
    public void generaPDF()  throws Exception {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.generaPDF()");
        boolean status = Constantes.INACTIVO;
        try {
            EntidadHospitalaria e = this.entidadService.obtenerEntidadHospital();
            if (paramBusquedaReporte.getIdEstructura() != null){
                Estructura est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
                if (est != null) {
                    paramBusquedaReporte.setNombreEstructura(est.getNombre());
                }
            }
            if (paramBusquedaReporte.getIdEstatusSolucion() != null){
                paramBusquedaReporte.setEstatusSolucion(EstatusSolucion_Enum.getStatusFromId(paramBusquedaReporte.getIdEstatusSolucion()).name().replace("_", " "));
            }
            byte[] buffer = reportesService.generaMezclasPdf(paramBusquedaReporte, e);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteMezclas_%s.pdf", FechaUtil.formatoFecha(new Date(), "_yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión PDF :: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     *
     * @throws Exception
     */
    public void generaExcel() throws Exception {
        LOGGER.trace("mx.mc.magedbean.ReporteMezclasMB.generaExcel()");
        boolean status = Constantes.INACTIVO;
        try {
            EntidadHospitalaria e = this.entidadService.obtenerEntidadHospital();
            if (paramBusquedaReporte.getIdEstructura() != null){
                Estructura est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
                if (est != null) {
                    paramBusquedaReporte.setNombreEstructura(est.getNombre());
                }
            }
            if (paramBusquedaReporte.getIdEstatusSolucion() != null){
                paramBusquedaReporte.setEstatusSolucion(EstatusSolucion_Enum.getStatusFromId(paramBusquedaReporte.getIdEstatusSolucion()).name().replace("_", " "));
            }
            boolean res = reportesService.generaMezclasExcel(this.paramBusquedaReporte, e);
            if (res) {
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel :: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void mostrarColumnas() {
        switch (paramBusquedaReporte.getIdEstatusSolucion()) {
            
            case 2:
                this.visiblePrescripcion = Constantes.ACTIVO;   
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;
            case 4:
            case 6:
            case 8:    
            case 9:    
            case 20:    
                this.visiblePrescripcion = Constantes.INACTIVO; 
                this.visibleRechazo = Constantes.ACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;
            case 5:
                this.visiblePrescripcion = Constantes.INACTIVO;   
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.ACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;
                
            case 7:
                this.visiblePrescripcion = Constantes.INACTIVO;   
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.ACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;   
                
            case 10:
                this.visiblePrescripcion = Constantes.INACTIVO; 
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.ACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break; 
            case 11:    
            case 12:
                this.visiblePrescripcion = Constantes.INACTIVO;  
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.ACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;   
            case 13:    
            case 14:
                this.visiblePrescripcion = Constantes.INACTIVO;   
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.ACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;
                
            case 15: 
                this.visiblePrescripcion = Constantes.INACTIVO; 
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.ACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;    
            
            case 16:
                this.visiblePrescripcion = Constantes.INACTIVO; 
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.ACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;  
            
            case 26:
                this.visiblePrescripcion = Constantes.INACTIVO; 
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.ACTIVO;
                break;      
                
            default:
                this.visiblePrescripcion = Constantes.ACTIVO;    
                this.visibleRechazo = Constantes.INACTIVO;
                this.visibleRegValidacion = Constantes.INACTIVO;
                this.visibleValidacion = Constantes.INACTIVO;
                this.visibleDispensacion = Constantes.INACTIVO;
                this.visiblePreparacion = Constantes.INACTIVO;
                this.visibleInspecciona = Constantes.INACTIVO;
                this.visibleAcondiciona = Constantes.INACTIVO;
                this.visibleDistribuye = Constantes.INACTIVO;
                this.visibleEntrega = Constantes.INACTIVO;
                this.visibleAutorizacion = Constantes.INACTIVO;
                break;
        }
    }
    
    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public MezclasLazy getMezclasLazy() {
        return mezclasLazy;
    }

    public void setMezclasLazy(MezclasLazy mezclasLazy) {
        this.mezclasLazy = mezclasLazy;
    }

    public List<Mezcla> getListaMezclas() {
        return listaMezclas;
    }

    public void setListaMezclas(List<Mezcla> listaMezclas) {
        this.listaMezclas = listaMezclas;
    }
    
    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public int getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(int tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public int getInsumoMedicamento() {
        return insumoMedicamento;
    }

    public void setInsumoMedicamento(int insumoMedicamento) {
        this.insumoMedicamento = insumoMedicamento;
    }

    public int getInsumoMaterial() {
        return insumoMaterial;
    }

    public void setInsumoMaterial(int insumoMaterial) {
        this.insumoMaterial = insumoMaterial;
    }

    public List<EstatusSolucion> getListaIdEstatusSolucion() {
        return listaIdEstatusSolucion;
    }

    public void setListaIdEstatusSolucion(List<EstatusSolucion> listaIdEstatusSolucion) {
        this.listaIdEstatusSolucion = listaIdEstatusSolucion;
    }

    public Paciente getPacienteSelect() {
        return pacienteSelect;
    }

    public void setPacienteSelect(Paciente pacienteSelect) {
        this.pacienteSelect = pacienteSelect;
    }

    public Usuario getMedicoSelect() {
        return medicoSelect;
    }

    public void setMedicoSelect(Usuario medicoSelect) {
        this.medicoSelect = medicoSelect;
    }

    public boolean isVisiblePrescripcion() {
        return visiblePrescripcion;
    }

    public void setVisiblePrescripcion(boolean visiblePrescripcion) {
        this.visiblePrescripcion = visiblePrescripcion;
    }

    public boolean isVisibleRechazo() {
        return visibleRechazo;
    }

    public void setVisibleRechazo(boolean visibleRechazo) {
        this.visibleRechazo = visibleRechazo;
    }

    public boolean isVisibleRegValidacion() {
        return visibleRegValidacion;
    }

    public void setVisibleRegValidacion(boolean visibleRegValidacion) {
        this.visibleRegValidacion = visibleRegValidacion;
    }

    public boolean isVisibleValidacion() {
        return visibleValidacion;
    }

    public void setVisibleValidacion(boolean visibleValidacion) {
        this.visibleValidacion = visibleValidacion;
    }

    public boolean isVisibleDispensacion() {
        return visibleDispensacion;
    }

    public void setVisibleDispensacion(boolean visibleDispensacion) {
        this.visibleDispensacion = visibleDispensacion;
    }

    public boolean isVisiblePreparacion() {
        return visiblePreparacion;
    }

    public void setVisiblePreparacion(boolean visiblePreparacion) {
        this.visiblePreparacion = visiblePreparacion;
    }

    public boolean isVisibleInspecciona() {
        return visibleInspecciona;
    }

    public void setVisibleInspecciona(boolean visibleInspecciona) {
        this.visibleInspecciona = visibleInspecciona;
    }

    public boolean isVisibleAcondiciona() {
        return visibleAcondiciona;
    }

    public void setVisibleAcondiciona(boolean visibleAcondiciona) {
        this.visibleAcondiciona = visibleAcondiciona;
    }

    public boolean isVisibleDistribuye() {
        return visibleDistribuye;
    }

    public void setVisibleDistribuye(boolean visibleDistribuye) {
        this.visibleDistribuye = visibleDistribuye;
    }

    public boolean isVisibleEntrega() {
        return visibleEntrega;
    }

    public void setVisibleEntrega(boolean visibleEntrega) {
        this.visibleEntrega = visibleEntrega;
    }

    public boolean isVisibleAutorizacion() {
        return visibleAutorizacion;
    }

    public void setVisibleAutorizacion(boolean visibleAutorizacion) {
        this.visibleAutorizacion = visibleAutorizacion;
    }
    
}
