package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.mc.lazy.RepInsumoNoMinistradoLazy;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.RepInsumoNoMinistrado;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.RepInsumoNoMinistradoService;
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
public class RepInsumoNoMinistradoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepInsumoNoMinistradoMB.class);
    private PermisoUsuario permiso;
    private boolean administrador;
    private Usuario usuarioSession;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<EnvioNeumatico> listDataResultReport;
    private String idEstructura;
    private List<Estructura> listaEstructuras;
    private String pathPdf;
    private Date fechaActual;
    private Date fechahoy;

    @Autowired
    private transient RepInsumoNoMinistradoService repInsumoNoMinistradoService;

    private RepInsumoNoMinistradoLazy repInsumoNoMinistradoLazy;

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
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.SURTNOMINISTADO.getSufijo());
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
     * Auto Completa busqueda de Insumos
     *
     * @param query
     * @return
     */
    public List<RepInsumoNoMinistrado> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.RepInsumoNoMinistradoMB.autocompleteInsumoNoMinistrado()");
        List<RepInsumoNoMinistrado> listaInsumo = new ArrayList<>();
        try {
            listaInsumo.addAll(repInsumoNoMinistradoService.obtenerListaInsumo(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener las Capsulas: {}", ex.getMessage());
        }
        return listaInsumo;
    }

    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
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

            this.repInsumoNoMinistradoLazy = new RepInsumoNoMinistradoLazy(this.repInsumoNoMinistradoService, this.paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", repInsumoNoMinistradoLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }

    public void imprimeReporteInsumoNoMiistrado() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepInsumoNoMinistradoMB.imprimir()");
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

            byte[] buffer = reportesService.imprimeReporteInsumoNoMiistrado(this.paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repInsumoNoMinistrado_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
            PrimeFaces.current().ajax().addCallbackParam("status", status);
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
    }

    public void generaExcelInsumoNoMinistrado() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepInsumoNoMinistradoMB.generaExcel()");
        boolean status = Constantes.INACTIVO;
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File dirTmp = new File(servletContext.getRealPath("/resources/tmp/"));
            String pathTmp = dirTmp.getPath() + "/repInsumoNoMinistrado.xlsx";
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
            Estructura et = estructuraService.obtenerEstructura(est.getIdEstructura());
            this.paramBusquedaReporte.setNombreEstructura(et.getNombre());

            EntidadHospitalaria e = new EntidadHospitalaria();
            e.setIdEntidadHospitalaria(et.getIdEntidadHospitalaria());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtener(e);

            if (administrador && this.idEstructura == null) {
                this.paramBusquedaReporte.setIdEstructura(null);
            }

            boolean res = reportesService.generaExcelInsumoNoMinistrado(this.paramBusquedaReporte, pathTmp, url, entidad);

            if (res) {
                status = Constantes.ACTIVO;
                this.pathPdf = url + "/resources/tmp/repControlCaducidad.xlsx";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
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

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
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

    public RepInsumoNoMinistradoLazy getRepInsumoNoMinistradoLazy() {
        return repInsumoNoMinistradoLazy;
    }

    public void setRepInsumoNoMinistradoLazy(RepInsumoNoMinistradoLazy repInsumoNoMinistradoLazy) {
        this.repInsumoNoMinistradoLazy = repInsumoNoMinistradoLazy;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public Date getFechahoy() {
        return fechahoy;
    }

    public void setFechahoy(Date fechahoy) {
        this.fechahoy = fechahoy;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
