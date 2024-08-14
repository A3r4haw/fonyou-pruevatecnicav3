package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.mc.lazy.ConsumosLazy;
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
import mx.mc.model.Consumo;
import mx.mc.model.Diagnostico;
import mx.mc.model.EntidadHospitalaria;
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
public class ReporteConsumoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteConsumoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private PermisoUsuario permiso;
    private transient List<Consumo> listaConsumos;
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

    private transient List<Estructura> listaEstructuras;

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

    private ConsumosLazy consumosLazy;

    /**
     *
     */
    @PostConstruct
    public void init() {
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.init()");
        try {
            initialize();
            obtenerEstructuras();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {} ", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.initialize()");
        try {
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTECONSUMOS.getSufijo());

            this.fechaInicio = FechaUtil.obtenerFechaInicio();
            this.fechaFin = FechaUtil.obtenerFechaFin();
            this.fechaActual = FechaUtil.getFechaActual();

            this.insumoMedicamento = 38;
            this.insumoMaterial = 39;

            this.paramBusquedaReporte = new ParamBusquedaReporte();
            this.paramBusquedaReporte.setFechaInicio(fechaInicio);
            this.paramBusquedaReporte.setFechaFin(fechaFin);

            this.listaConsumos = new ArrayList<>();
            this.listaEstructuras = new ArrayList<>();

        } catch (Exception ex) {
            LOGGER.error("Error al obtener inicializar :: {}", ex.getMessage());
        }

    }

    /**
     *
     */
    public void obtenerEstructuras() {
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.obtenerEstructuras()");
        try {
            this.listaEstructuras = estructuraService.obtenerServicios();
        } catch (Exception e) {
            LOGGER.error("Error al buscar servicios :: {} ", e.getMessage());
        }
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.consultar()");
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

            if (this.paramBusquedaReporte.getListMedicos() != null
                    && !this.paramBusquedaReporte.getListMedicos().isEmpty()) {
                List<String> l = new ArrayList<>();
                for (Usuario o : this.paramBusquedaReporte.getListMedicos()) {
                    l.add(o.getIdUsuario());
                }
                this.paramBusquedaReporte.setListaMedicos(l);
            } else {
                this.paramBusquedaReporte.setListaMedicos(null);
            }

            if (this.paramBusquedaReporte.getPacienteList() != null
                    && !this.paramBusquedaReporte.getPacienteList().isEmpty()) {
                List<String> l = new ArrayList<>();
                for (Paciente o : this.paramBusquedaReporte.getPacienteList()) {
                    l.add(o.getIdPaciente());
                }
                this.paramBusquedaReporte.setListaPaciente(l);
            } else {
                this.paramBusquedaReporte.setListaPaciente(null);
            }

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

            this.consumosLazy = new ConsumosLazy(
                    this.reporteMovimientosService,
                    this.paramBusquedaReporte);

            LOGGER.trace("Resultados: {}", this.consumosLazy.getTotalReg());

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
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.autocompleteInsumo()");
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
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.autocompletePacientes()");
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
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.autocompleteUsuarios()");
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
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.autocompleteDiagnostico()");
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
     * @throws Exception
     */
    public void generaPDF() {
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.generaPDF()");
        boolean status = Constantes.INACTIVO;
        try {
            EntidadHospitalaria e = this.entidadService.obtenerEntidadHospital();
            byte[] buffer = reportesService.generConsumosPdf(paramBusquedaReporte, e);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteConsumo_%s.pdf", FechaUtil.formatoFecha(new Date(), "_yyyyMMdd")));
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
        LOGGER.trace("mx.mc.magedbean.ReporteConsumoMB.generaExcel()");
        boolean status = Constantes.INACTIVO;
        try {
            EntidadHospitalaria e = this.entidadService.obtenerEntidadHospital();
            boolean res = reportesService.generaConsumosExcel(this.paramBusquedaReporte, e);
            if (res) {
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel :: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
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

    public ConsumosLazy getConsumosLazy() {
        return consumosLazy;
    }

    public void setConsumosLazy(ConsumosLazy consumosLazy) {
        this.consumosLazy = consumosLazy;
    }

    public List<Consumo> getListaConsumos() {
        return listaConsumos;
    }

    public void setListaConsumos(List<Consumo> listaConsumos) {
        this.listaConsumos = listaConsumos;
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

}
