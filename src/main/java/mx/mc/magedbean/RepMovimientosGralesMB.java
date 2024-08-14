package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.lazy.ReporteMovimientosLazy;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DataResultReport;
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
import mx.mc.service.TipoMovimientoService;
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
public class RepMovimientosGralesMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepMovimientosGralesMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<DataResultReport> listDataResultReport;
    private List<TipoMovimiento> listTipoMovimiento;
    private int idTipoMovimiento;
    private List<Integer> tipoMovimientoSelects;
    private PermisoUsuario permiso;
    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private boolean jefeArea;
    private String pathPdf;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Estructura estructuraUsuario;
    private boolean activaCamposReporteMovimientosGenerales;
    private boolean mostrarOrigenInsumos;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient TipoMovimientoService tipoMovimientoService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient ReportesService reportesService;

    private ReporteMovimientosLazy reporteMovimientosLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.jefeArea = Comunes.isJefeArea();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTMOVGENERALES.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {
            boolean noEncontroRegistro = false;
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            activaCamposReporteMovimientosGenerales = sesion.isActivaCamposReporteMovimientosGenerales();
            mostrarOrigenInsumos = sesion.isMostrarOrigenDeInsumo();
            listTipoMovimiento = tipoMovimientoService.obtenerLista(new TipoMovimiento());
            fechaActualInicio = FechaUtil.getFechaActual();
            fechaActual = new java.util.Date();
            List<Integer> listTipoAlmacen = new ArrayList<>();
            listTipoAlmacen.add(TipoAlmacen_Enum.FARMACIA.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.ALMACEN.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.SUBALMACEN.getValue());
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
                this.listAlmacenesSubAlm.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
            } else if(jefeArea) {
                listAlmacenesSubAlm = estructuraService.obtenerAlmacenesQueSurtenServicio(this.usuarioSession.getIdEstructura());
                if(listAlmacenesSubAlm.isEmpty()) {
                    noEncontroRegistro = true;
                    this.jefeArea = false;
                } else {
                    this.estructuraUsuario = listAlmacenesSubAlm.get(0);
                }
            } else {
                 noEncontroRegistro = true;                
            }
            if(noEncontroRegistro) {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento: {}", ex.getMessage());
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
     * Metodo para obtener la fecha fin con el formato 23:59 en lugar de 00:00
     */
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.transaccion"), null);
        } else {
            try {
                if (activaCamposReporteMovimientosGenerales) {
                    paramBusquedaReporte.setActivaCamposReporteMovimientosGenerales(activaCamposReporteMovimientosGenerales);
                }
                paramBusquedaReporte.setNuevaBusqueda(true);
                if (this.tipoMovimientoSelects == null) {
                    tipoMovimientoSelects = new ArrayList<>();
                }
                paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);
                if (!administrador && !jefeArea) {
                    this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
                } else {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                }
                reporteMovimientosLazy = new ReporteMovimientosLazy(
                        reporteMovimientosService, paramBusquedaReporte);

                LOGGER.debug("Resultados: {}", reporteMovimientosLazy.getTotalReg());

            } catch (Exception e1) {
                LOGGER.error("Error al consultar", e1);
            }
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

    public void imprimeReporteMovGenerales() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            if (activaCamposReporteMovimientosGenerales) {
                paramBusquedaReporte.setActivaCamposReporteMovimientosGenerales(activaCamposReporteMovimientosGenerales);
            }
            if (!administrador && !jefeArea) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }

            this.paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);

            Estructura est;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }
			paramBusquedaReporte.setUsuarioGenera(usuarioSession);
			
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            byte[] buffer = reportesService.imprimeReporteMovGenerales(paramBusquedaReporte, entidad, mostrarOrigenInsumos);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repMovGenerales_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelGeneral() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            if (activaCamposReporteMovimientosGenerales) {
                paramBusquedaReporte.setActivaCamposReporteMovimientosGenerales(activaCamposReporteMovimientosGenerales);
            }
            if (!administrador && !jefeArea) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            this.paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);

            Estructura est;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            boolean res = reportesService.imprimeExcelGenerales(this.paramBusquedaReporte, entidad, mostrarOrigenInsumos);
            if (res) {
                status = Constantes.ACTIVO;
            }

        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public List<DataResultReport> getListDataResultReport() {
        return listDataResultReport;
    }

    public void setListDataResaultReport(List<DataResultReport> listDataResultReport) {
        this.listDataResultReport = listDataResultReport;
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

    public ReporteMovimientosLazy getReporteMovimientosLazy() {
        return reporteMovimientosLazy;
    }

    public void setReporteMovimientosLazy(ReporteMovimientosLazy reporteMovimientosLazy) {
        this.reporteMovimientosLazy = reporteMovimientosLazy;
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

    public boolean isJefeArea() {
        return jefeArea;
    }

    public void setJefeArea(boolean jefeArea) {
        this.jefeArea = jefeArea;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
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

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Estructura getEstructuraUsuario() {
        return estructuraUsuario;
    }

    public void setEstructuraUsuario(Estructura estructuraUsuario) {
        this.estructuraUsuario = estructuraUsuario;
    }

    public Date getFechaActualInicio() {
        return fechaActualInicio;
    }

    public void setFechaActualInicio(Date fechaActualInicio) {
        this.fechaActualInicio = fechaActualInicio;
    }

    public boolean isActivaCamposReporteMovimientosGenerales() {
        return activaCamposReporteMovimientosGenerales;
    }

    public void setActivaCamposReporteMovimientosGenerales(boolean activaCamposReporteMovimientosGenerales) {
        this.activaCamposReporteMovimientosGenerales = activaCamposReporteMovimientosGenerales;
    }

    public boolean isMostrarOrigenInsumos() {
        return mostrarOrigenInsumos;
    }

    public void setMostrarOrigenInsumos(boolean mostrarOrigenInsumos) {
        this.mostrarOrigenInsumos = mostrarOrigenInsumos;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
