package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.mc.lazy.ControlCaducidadLazy;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.ControlCaducidadService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReportesService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
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
public class ControlCaducidadMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlCaducidadMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private List<Estructura> listAlmacenesSubAlm;
    private boolean administrador;
    private Usuario usuarioSession;
    private Date fechaActualInicio;
    private Date fechaActual;
    private List<Estructura> listaAuxiliar;
    private Estructura estructuraUsuario;
    private ParamBusquedaReporte paramBusquedaReporte;
    private String idEstructura;
    private String pathPdf;
    private PermisoUsuario permiso;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ControlCaducidadService controlCaducidadService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    private ControlCaducidadLazy controlCaducidadLazy;

    @PostConstruct
    public void init() {
        try {
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPCONTRCADUCIDADES.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init: {}", e.getMessage());
        }
    }

    public void initialize() {
        try {
            List<Integer> listTipoAlmacen = new ArrayList<>();            
            listTipoAlmacen.add(TipoAlmacen_Enum.FARMACIA.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.ALMACEN.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.SUBALMACEN.getValue());
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
                this.listAlmacenesSubAlm.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
                this.idEstructura = null;
            } else {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de almacenes y subalmacenes: {}", ex.getMessage());

        }
        paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());

    }

    public Usuario obtenerUsuarioSesion() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        return sesion.getUsuarioSelected();
    }  

    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null
                            && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        listAlmacenesSubAlm.add(listaAuxiliar.get(i));
                        ordenarListaEstructura(listaAuxiliar.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }

    public void consultar() {
        try {
            if (administrador) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            

            this.controlCaducidadLazy = new ControlCaducidadLazy(this.controlCaducidadService, this.paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", controlCaducidadLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }

    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.ControlCaducidadMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    public void imprimeReporteControlCaducidad() throws Exception {
        LOGGER.debug("mx.mc.magedbean.ControlCaducidadMB.imprimir()");
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
            Estructura et = estructuraService.obtenerEstructura(est.getIdEstructura());
            this.paramBusquedaReporte.setNombreEstructura(et.getNombre());

            EntidadHospitalaria e = new EntidadHospitalaria();
            e.setIdEntidadHospitalaria(et.getIdEntidadHospitalaria());
            EntidadHospitalaria entidad = entidadHospitalariaService.obtener(e);

            if (administrador && this.idEstructura == null) {
                this.paramBusquedaReporte.setIdEstructura(null);
            }

            byte[] buffer = reportesService.imprimeReporteControlCaducidad(this.paramBusquedaReporte, entidad);

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repControlCaducidad_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    
    public void obtenerFechaFinal() {        
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    public void generaExcelControlCaducidad() throws Exception {
        LOGGER.debug("mx.mc.magedbean.ControlCaducidadMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            /*ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File dirTmp = new File(servletContext.getRealPath("/resources/tmp/"));
            String pathTmp = dirTmp.getPath() + "/repControlCaducidad.xlsx";
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null,
                    ext.getRequestServerName(),
                    ext.getRequestServerPort(),
                    ext.getRequestContextPath(),
                    null,
                    null);
            String url = uri.toASCIIString();*/

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

            boolean res = reportesService.generaExcelControlCaducidad(this.paramBusquedaReporte, entidad);

            if (res) {
                status = Constantes.ACTIVO;
                //this.pathPdf = url + "/resources/tmp/repControlCaducidad.xlsx";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public Date getFechaActualInicio() {
        return fechaActualInicio;
    }

    public void setFechaActualInicio(Date fechaActualInicio) {
        this.fechaActualInicio = fechaActualInicio;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public Estructura getEstructuraUsuario() {
        return estructuraUsuario;
    }

    public void setEstructuraUsuario(Estructura estructuraUsuario) {
        this.estructuraUsuario = estructuraUsuario;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public List<Estructura> getListAlmacenesSubAlm() {
        return listAlmacenesSubAlm;
    }

    public void setListAlmacenesSubAlm(List<Estructura> listAlmacenesSubAlm) {
        this.listAlmacenesSubAlm = listAlmacenesSubAlm;
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

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public void setListaAuxiliar(List<Estructura> listaAuxiliar) {
        this.listaAuxiliar = listaAuxiliar;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public ControlCaducidadService getControlCaducidadService() {
        return controlCaducidadService;
    }

    public void setControlCaducidadService(ControlCaducidadService controlCaducidadService) {
        this.controlCaducidadService = controlCaducidadService;
    }

    public ControlCaducidadLazy getControlCaducidadLazy() {
        return controlCaducidadLazy;
    }

    public void setControlCaducidadLazy(ControlCaducidadLazy controlCaducidadLazy) {
        this.controlCaducidadLazy = controlCaducidadLazy;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
