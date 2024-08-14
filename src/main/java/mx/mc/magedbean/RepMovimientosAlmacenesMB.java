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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.lazy.ReporteAlmacenesLazy;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DataResultAlmacenes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoMovimiento;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;

/**
 *
 * @author gcruz
 *
 */
@Controller
@Scope(value = "view")
public class RepMovimientosAlmacenesMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepMovimientosAlmacenesMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private ParamBusquedaReporte paramBusquedaReporte;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private Estructura estructuraUsuario;
    private List<DataResultAlmacenes> listDataResultAlmacenes;
    private List<TipoMovimiento> listTipoMovimiento;
    private int idTipoMovimiento;
    private List<Integer> tipoMovimientoSelects;
    private String idEstructura;
    private PermisoUsuario permiso;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private String pathPdf;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    private ReporteAlmacenesLazy reporteAlmacenesLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            listAlmacenesSubAlm = new ArrayList<>();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.administrador = Comunes.isAdministrador();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTESBASICOS.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {
            List<Integer> listTipoAlmacen = new ArrayList<>();
            fechaActual = FechaUtil.obtenerFechaFin();
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

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);

            if (administrador) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            this.reporteAlmacenesLazy = new ReporteAlmacenesLazy(this.reporteMovimientosService, this.paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", reporteAlmacenesLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
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
        LOGGER.debug("mx.mc.magedbean.ReporteMovimientosGralesMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    public List<Usuario> autocompleteUsuario(String query) {
        LOGGER.debug("mx.mc.magedbean.ReporteMovimientosGralesMB.autocompleteUsuario()");
        List<Usuario> listUsuarios = new ArrayList<>();
        try {
            listUsuarios.addAll(usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listUsuarios;
    }

    public void imprimeReporteMovAlmacenes() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
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

            byte[] buffer = reportesService.imprimeReporteAlmacenes(this.paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repMovAlmacenes_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelAlmacen() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            /*ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File dirTmp = new File(servletContext.getRealPath("/resources/tmp/"));
            String pathTmp = dirTmp.getPath() + "/repMovAlmacenes.xlsx";
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
            boolean res = reportesService.generaExcelAlmacen(this.paramBusquedaReporte, entidad);

            if (res) {
                status = Constantes.ACTIVO;
               // this.pathPdf = url + "/resources/tmp/repMovAlmacenes.xlsx";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    public List<DataResultAlmacenes> getListDataResultAlmacenes() {
        return listDataResultAlmacenes;
    }

    public void setListDataResultAlmacenes(List<DataResultAlmacenes> listDataResultAlmacenes) {
        this.listDataResultAlmacenes = listDataResultAlmacenes;
    }

    public List<TipoMovimiento> getListTipoMovimiento() {
        return listTipoMovimiento;
    }

    public void setListTipoMovimiento(List<TipoMovimiento> listTipoMovimiento) {
        this.listTipoMovimiento = listTipoMovimiento;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public int getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(int idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public List<Integer> getTipoMovimientoSelects() {
        return tipoMovimientoSelects;
    }

    public void setTipoMovimientoSelects(List<Integer> tipoMovimientoSelects) {
        this.tipoMovimientoSelects = tipoMovimientoSelects;
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

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public void setListaAuxiliar(List<Estructura> listaAuxiliar) {
        this.listaAuxiliar = listaAuxiliar;
    }

    public ReporteAlmacenesLazy getReporteAlmacenesLazy() {
        return reporteAlmacenesLazy;
    }

    public void setReporteAlmacenesLazy(ReporteAlmacenesLazy reporteAlmacenesLazy) {
        this.reporteAlmacenesLazy = reporteAlmacenesLazy;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
