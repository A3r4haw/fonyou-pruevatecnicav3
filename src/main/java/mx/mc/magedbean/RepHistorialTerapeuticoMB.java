package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.ReporteHistorialTerapeuticoLazy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Paciente;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReporteTerapeutico;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.PacienteService;
import mx.mc.service.ReporteHistorialTerapeuticoService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author olozada
 */
@Controller
@Scope(value = "view")
public class RepHistorialTerapeuticoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepHistorialTerapeuticoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    

    private List<ReporteTerapeutico> listDataResultReport;
    private ParamBusquedaReporte paramBusquedaReporte;
    private PermisoUsuario permiso;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private String idEstructura;
    private List<Estructura> listaEstructuras;
    private String pathPdf;
    private String medico;
    private String paciente;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient ReporteHistorialTerapeuticoService reporteHistorialTerapeuticoService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    private ReporteHistorialTerapeuticoLazy reporteHistorialTerapeuticoLazy;

    private ReporteTerapeutico reporteTerapeuticoSelect;

    /**
     * Reporte Historial Terapeutico
     */
    @PostConstruct
    public void init() {
        try {
            this.setAdministrador(Comunes.isAdministrador());
            this.setUsuarioSession(Comunes.obtenerUsuarioSesion());
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTESBASICOS.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Inicializar variables
     */
    public void initialize() {

        this.fechaActual = FechaUtil.getFechaActual();
        this.paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
        paramBusquedaReporte.setListUsuarios(null);
        paramBusquedaReporte.setPacienteList(null);

    }

    /**
     * Obtiene Sesion de Usuario
     *
     * @return
     */
    public Usuario obtenerUsuarioSesion() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        return sesion.getUsuarioSelected();
    }

    /**
     * Auto Completa busqueda de Paciente
     *
     * @param query
     * @return
     */
    public List<Paciente> autocompletePaciente(String query) {
        LOGGER.debug("mx.mc.magedbean.RepHistorialTerapeuticoMB.autocompletePaciente()");
        List<Paciente> pacienteList = new ArrayList<>();
        try {
            pacienteList = pacienteService.obtenerPacientes(query);
            paciente = pacienteList.get(0).getNombreCompleto();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener Pacientes");
            Mensaje.showMessage("Error", RESOURCES.getString("paciente.err.autocomplete"), null);
        }
        return pacienteList;
    }

    
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    /**
     * Auto Completa busqueda de Medico
     *
     * @param query
     * @return
     */
    public List<Usuario> autocompleteUsuario(String query) {
        LOGGER.debug("mx.mc.magedbean.RepHistorialTerapeuticoMB.autocompleteUsuario()");
        List<Usuario> listUsuarios = new ArrayList<>();
        try {
            listUsuarios = usuarioService.obtenerUsuarios(query);
            medico = listUsuarios.get(0).getNombre();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listUsuarios;
    }

    /**
     * Metodo Consuta Busqueda
     */
    public void consultar() {
        try {
            if (administrador) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }

            this.reporteHistorialTerapeuticoLazy = new ReporteHistorialTerapeuticoLazy(this.reporteHistorialTerapeuticoService, this.paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", reporteHistorialTerapeuticoLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }

    /**
     * Muestra y exporta Query a formato PDF
     *
     * @throws Exception
     */
    public void imprimeReporteTerapeutico() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepHistorialTerapeuticoMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            if (administrador) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                } else {
                    this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }

            Estructura est = new Estructura();
            est.setIdEstructura(this.paramBusquedaReporte.getIdEstructura());
            Estructura e = estructuraService.obtenerEstructura(est.getIdEstructura());
            this.paramBusquedaReporte.setNombreEstructura(e.getNombre());

            EntidadHospitalaria ent = new EntidadHospitalaria();
            ent.setIdEntidadHospitalaria(e.getIdEntidadHospitalaria());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtener(ent);

            if (administrador && this.idEstructura == null) {
                this.paramBusquedaReporte.setIdEstructura(null);
            }

            byte[] buffer = reportesService.imprimeReporteTerapeutico(paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repTerapeutico_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
            PrimeFaces.current().ajax().addCallbackParam("status", status);
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }

    }

    public void validarMedicoyPaciente() {
        try {
            if (this.paciente != null || this.medico != null) {
                consultar();

            } else {
                Mensaje.showMessage("Error", RESOURCES.getString("rephistterapeutico.req.pacienteOmedico"), null);
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo validar medico y paciente: {}", e.getMessage());
        }
    }
    
    /**
     * @return the listDataResultReport
     */
    public List<ReporteTerapeutico> getListDataResultReport() {
        return listDataResultReport;
    }

    /**
     * @param listDataResultReport the listDataResultReport to set
     */
    public void setListDataResultReport(List<ReporteTerapeutico> listDataResultReport) {
        this.listDataResultReport = listDataResultReport;
    }

    /**
     * @return the paramBusquedaReporte
     */
    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    /**
     * @param paramBusquedaReporte the paramBusquedaReporte to set
     */
    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    /**
     * @return the fechaActual
     */
    public Date getFechaActual() {
        return fechaActual;
    }

    /**
     * @param fechaActual the fechaActual to set
     */
    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    /**
     * @return the usuarioSession
     */
    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    /**
     * @param usuarioSession the usuarioSession to set
     */
    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    /**
     * @return the administrador
     */
    public boolean isAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the idEstructura
     */
    public String getIdEstructura() {
        return idEstructura;
    }

    /**
     * @param idEstructura the idEstructura to set
     */
    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    /**
     * @return the listaEstructuras
     */
    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    /**
     * @param listaEstructuras the listaEstructuras to set
     */
    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    /**
     * @return the pacienteService
     */
    public PacienteService getPacienteService() {
        return pacienteService;
    }

    /**
     * @param pacienteService the pacienteService to set
     */
    public void setPacienteService(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    /**
     * @return the reporteHistorialTerapeuticoService
     */
    public ReporteHistorialTerapeuticoService getReporteHistorialTerapeuticoService() {
        return reporteHistorialTerapeuticoService;
    }

    /**
     * @param reporteHistorialTerapeuticoService the
     * reporteHistorialTerapeuticoService to set
     */
    public void setReporteHistorialTerapeuticoService(ReporteHistorialTerapeuticoService reporteHistorialTerapeuticoService) {
        this.reporteHistorialTerapeuticoService = reporteHistorialTerapeuticoService;
    }

    /**
     * @return the usuarioService
     */
    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    /**
     * @param usuarioService the usuarioService to set
     */
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * @return the reporteHistorialTerapeuticoLazy
     */
    public ReporteHistorialTerapeuticoLazy getReporteHistorialTerapeuticoLazy() {
        return reporteHistorialTerapeuticoLazy;
    }

    /**
     * @param reporteHistorialTerapeuticoLazy the
     * reporteHistorialTerapeuticoLazy to set
     */
    public void setReporteHistorialTerapeuticoLazy(ReporteHistorialTerapeuticoLazy reporteHistorialTerapeuticoLazy) {
        this.reporteHistorialTerapeuticoLazy = reporteHistorialTerapeuticoLazy;
    }

    /**
     * @return the pathPdf
     */
    public String getPathPdf() {
        return pathPdf;
    }

    /**
     * @param pathPdf the pathPdf to set
     */
    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public void handleSelect(SelectEvent e) {
        reporteTerapeuticoSelect = (ReporteTerapeutico) e.getObject();
    }

    public void handleUnSelect(UnselectEvent e) {
        reporteTerapeuticoSelect = (ReporteTerapeutico) e.getObject();
    }

    public ReporteTerapeutico getReporteTerapeuticoSelect() {
        return reporteTerapeuticoSelect;
    }

    public void setReporteTerapeuticoSelect(ReporteTerapeutico reporteTerapeuticoSelect) {
        this.reporteTerapeuticoSelect = reporteTerapeuticoSelect;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
