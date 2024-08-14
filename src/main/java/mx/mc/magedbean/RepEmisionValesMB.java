package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.lazy.ReporteValesLazy;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DataResultVales;
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
public class RepEmisionValesMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepEmisionValesMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private ParamBusquedaReporte paramBusquedaReporte;

    private List<DataResultVales> listdDataResultVales;

    private List<TipoMovimiento> listTipoMovimiento;
    private int idTipoMovimiento;
    private List<Integer> tipoMovimientoSelects;
    private PermisoUsuario permiso;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;

    @Autowired
    private transient TipoMovimientoService tipoMovimientoService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient ReportesService reportesService;

    private ReporteValesLazy reporteValesLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            initialize();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.administrador = Comunes.isAdministrador();
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
            listTipoMovimiento = tipoMovimientoService.obtenerLista(new TipoMovimiento());
            fechaActual = new java.util.Date();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento: {}", ex.getMessage());
        }
        paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());

    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);
            paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }

            reporteValesLazy = new ReporteValesLazy(
                    reporteMovimientosService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", reporteValesLazy.getTotalReg());

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
        LOGGER.debug("mx.mc.magedbean.ReporteEmisionValesMB.autocompleteInsumo()");
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
        LOGGER.debug("mx.mc.magedbean.ReporteEmisionValesMB.autocompleteUsuario()");
        List<Usuario> listUsuarios = new ArrayList<>();
        try {
            listUsuarios.addAll(usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listUsuarios;
    }
    
    
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    

    public void imprimeReporteEmisionVales() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
           
            if (!this.administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            Estructura est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            byte[] buffer = reportesService.imprimeReporteEmisionVales(paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repEmisionVales_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelVales() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {

            if (!this.administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            Estructura est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            boolean res = reportesService.imprimeExcelVales(paramBusquedaReporte, entidad);
            if (res) {
                status = Constantes.ACTIVO;                
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public List<DataResultVales> getListDataResultReport() {
        return listdDataResultVales;
    }

    public void setListDataResaultReport() {
        this.listdDataResultVales = listdDataResultVales;
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

    public ReporteValesLazy getReporteValesLazy() {
        return reporteValesLazy;
    }

    public void setReporteValesLazy(ReporteValesLazy reporteValesLazy) {
        this.reporteValesLazy = reporteValesLazy;
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

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
