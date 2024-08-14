package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.EnvioNeumaticoLazy;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Capsula;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.CapsulaService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EnvioNeumaticoService;
import mx.mc.service.EstructuraService;
import mx.mc.service.ReportesService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author olozada
 */
@Controller
@Scope(value = "view")
public class EnvioNeumaticoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvioNeumaticoMB.class);
    private boolean administrador;
    private Usuario usuarioSession;
    private Date fechaHoraSalida;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<EnvioNeumatico> listDataResultReport;

    private Date fechaActual;
    private String idEstructura;
    private List<Estructura> listaEstructuras;
    private String pathPdf;
    private String idCapsula;
    private Date fechahoy;
    private boolean status;
    private PermisoUsuario permiso;
    @Autowired
    private transient CapsulaService capsulaService;

    @Autowired
    private transient EnvioNeumaticoService envioNeumaticoService;

    private EnvioNeumaticoLazy envioNeumaticoLazy;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    /**
     * Reporte Envios Neumaticos
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
        fechahoy = new java.util.Date();
        this.paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());

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
     * Auto Completa busqueda de Capsulas
     *
     * @param query
     * @return
     */
    public List<Capsula> autocompleteCapsula(String query) {
        LOGGER.debug("mx.mc.magedbean.EnvioNeumaticoMB.autocompleteCapsula()");
        List<Capsula> listaCapsulas = new ArrayList<>();
        try {
            listaCapsulas.addAll(capsulaService.obtenerCapsulasponombre(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener las Capsulas: {}", ex.getMessage());
        }
        return listaCapsulas;
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

            this.envioNeumaticoLazy = new EnvioNeumaticoLazy(this.envioNeumaticoService, this.paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", envioNeumaticoLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }

    
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    public void imprimeReporteEnvioNeumatico() throws Exception {
        LOGGER.debug("mx.mc.magedbean.EnvioNeumaticoMB.imprimir()");
        boolean estatus = Constantes.INACTIVO;
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

            byte[] buffer = reportesService.imprimeReporteEnvioNeumatico(paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteEnvioNeumatico_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
            PrimeFaces.current().ajax().addCallbackParam("status", estatus);
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public Date getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(Date fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
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

    public List<EnvioNeumatico> getListDataResultReport() {
        return listDataResultReport;
    }

    public void setListDataResultReport(List<EnvioNeumatico> listDataResultReport) {
        this.listDataResultReport = listDataResultReport;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public EnvioNeumaticoLazy getEnvioNeumaticoLazy() {
        return envioNeumaticoLazy;
    }

    public void setEnvioNeumaticoLazy(EnvioNeumaticoLazy envioNeumaticoLazy) {
        this.envioNeumaticoLazy = envioNeumaticoLazy;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public String getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(String idCapsula) {
        this.idCapsula = idCapsula;
    }

    public Date getFechahoy() {
        return fechahoy;
    }

    public void setFechahoy(Date fechahoy) {
        this.fechahoy = fechahoy;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
