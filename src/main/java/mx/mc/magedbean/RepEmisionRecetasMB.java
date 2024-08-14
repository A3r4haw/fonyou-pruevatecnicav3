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
import mx.mc.lazy.ReporteRecetasLazy;
import mx.mc.enums.EstatusPrescripcion_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoPrescripcion_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DataResultVales;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.EstatusPrescripcion;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoMovimiento;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstatusPrescripcionService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
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
public class RepEmisionRecetasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepEmisionRecetasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private ParamBusquedaReporte paramBusquedaReporte;

    private List<DataResultVales> listdDataResultVales;

    private List<TipoMovimiento> listTipoMovimiento;
    private int idTipoMovimiento;
    private List<Integer> tipoMovimientoSelects;
    private List<Integer> listIdAreaEstructura;
    private List<EstatusPrescripcion> lisEstatusPrescripcion;
    private PermisoUsuario permiso;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private boolean isJefeArea;
    private boolean hospChiconcuac;    
    private String recNormal;
    private String recManual;    
    private boolean bandera;
    private boolean activaCamposRepEmisionRecetas;
    private String idEstructura;
    private String servicio;
    private List<Estructura> listaEstructuras;

    @Autowired
    private transient TipoMovimientoService tipoMovimientoService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient PacienteService pacienteService;
    
    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired
    private transient EstatusPrescripcionService estatusPrescripcionService;

    @Autowired
    private transient ReportesService reportesService;

    private ReporteRecetasLazy reporteRecetasLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            initialize();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.administrador = Comunes.isAdministrador();
            this.isJefeArea = Comunes.isJefeArea();
            listIdAreaEstructura = new ArrayList<>();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTERECETAS.getSufijo());                        
            desabilitarCamposPorTipoPaciente();            
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            hospChiconcuac = sesion.isHospChiconcuac();
            activaCamposRepEmisionRecetas = sesion.isActivaCamposRepEmisionRecetas();
            listTipoMovimiento = tipoMovimientoService.obtenerLista(new TipoMovimiento());
            fechaActual = FechaUtil.obtenerFechaFin();
            recManual = TipoPrescripcion_Enum.MANUAL.getValue();
            recNormal = TipoPrescripcion_Enum.NORMAL.getValue();    
            listaEstructuras = new ArrayList<>();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento: {}", ex.getMessage());
        }        
        paramBusquedaReporte = new ParamBusquedaReporte();                
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
        obtenerEstatusPrescripcion();

    }


    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {
            paramBusquedaReporte.setNuevaBusqueda(true);                       
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }else{
                paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            paramBusquedaReporte.setActivaCamposRepEmisionRecetas(activaCamposRepEmisionRecetas);
            reporteRecetasLazy = new ReporteRecetasLazy(
                    reporteMovimientosService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", reporteRecetasLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }
    
            /**
     * Metodo para obtener la fecha fin con el formato 23:59 en lugar de 00:00
     */
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }       

    /**
     * Consulta de insumos por cadena de texto mayor a 3 caracteres y retorna
     * las coincidencias
     *
     * @param query
     * @return
     */
    public List<Medicamento> autocompleteInsumo(String query) {
        LOGGER.debug("mx.mc.magedbean.RepEmisionRecetasMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    public List<Paciente> autocompletePaciente(String query) {
        
        LOGGER.debug("mx.mc.magedbean.RepEmisionRecetasMB.autocompletePaciente()");
        List<Paciente> pacienteList = new ArrayList<>();
        try {            
            pacienteList.addAll(pacienteService.obtenerPacientes(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return pacienteList;
    }
    
    public void obtenerEstatusPrescripcion() {
        List<Integer> listaEstatusPrescripcion = new ArrayList<>();
        
        listaEstatusPrescripcion.add(EstatusPrescripcion_Enum.FINALIZADA.getValue());
        listaEstatusPrescripcion.add(EstatusPrescripcion_Enum.CANCELADA.getValue());
        listaEstatusPrescripcion.add(EstatusPrescripcion_Enum.SURTIDA_PARCIAL.getValue());
        try {
            this.lisEstatusPrescripcion = estatusPrescripcionService.getEstructuraByLisTipoAlmacen(listaEstatusPrescripcion);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener la lista de Estatus : {}", ex.getMessage());
        }
    }
    
    public List<Usuario> autocompleteUsuario(String query) {
        LOGGER.debug("mx.mc.magedbean.RepEmisionRecetasMB.autocompleteUsuario()");
        List<Usuario> listUsuarios = new ArrayList<>();
        try {
            listUsuarios.addAll(usuarioService.obtenerUsuarios(query));
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener los usuarios: {}", ex.getMessage());
        }
        return listUsuarios;
    }
    
    /**
     * Metodo que desabilita los campos
     */
    public void desabilitarCamposPorTipoPaciente() {
        if (activaCamposRepEmisionRecetas) {
            if (this.paramBusquedaReporte.getTipoPrescripcion() == null) {
                bandera = false;
            } else {
                bandera = true;
            }
        }
        obtenerServiciosAlmacenes();
    }

    public void obtenerServiciosAlmacenes() {
        listIdAreaEstructura.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
        try {
            if (administrador) {
                if (activaCamposRepEmisionRecetas) {
                    if (this.paramBusquedaReporte.getTipoPrescripcion() == null) {
                        // colectiva
                        listaEstructuras = estructuraService.obtenerAlmacenesActivos();
                        
                        //bandera = false;
                    } else {
                        // manual / normal
                        listaEstructuras = estructuraService.getEstructuraListTipoAreaEstructura(listIdAreaEstructura);
                        
                        //bandera = true;
                    }
                }              
            }else{
                Estructura est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());                
                    listaEstructuras.add(est);
                
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtenerServicios ", ex.getMessage());
        }

    }

    

    public void imprimeReporteEmisionRecetas() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepEmisionRecetasMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {           
            if (!this.administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            this.paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);
            
            Estructura est;            
            est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            

            EntidadHospitalaria entidad;            
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeReporteRecetas(paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repEmisionRecetas_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));                
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelRecetas() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepEmisionRecetasMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            Estructura est;            
            est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            

            EntidadHospitalaria entidad;            
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            
            boolean res = reportesService.imprimeExcelRecetas(this.paramBusquedaReporte,entidad);
            if (res) {
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al Generar el Excel: {} ", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public List<DataResultVales> getListDataResultReport() {
        return listdDataResultVales;
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

    public ReporteRecetasLazy getReporteRecetasLazy() {
        return reporteRecetasLazy;
    }

    public void setReporteRecetasLazy(ReporteRecetasLazy reporteRecetasLazy) {
        this.reporteRecetasLazy = reporteRecetasLazy;
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

    public List<EstatusPrescripcion> getLisEstatusPrescripcion() {
        return lisEstatusPrescripcion;
    }

    public void setLisEstatusPrescripcion(List<EstatusPrescripcion> lisEstatusPrescripcion) {
        this.lisEstatusPrescripcion = lisEstatusPrescripcion;
    }

    public boolean isHospChiconcuac() {
        return hospChiconcuac;
    }

    public void setHospChiconcuac(boolean hospChiconcuac) {
        this.hospChiconcuac = hospChiconcuac;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public String getRecNormal() {
        return recNormal;
    }

    public void setRecNormal(String recNormal) {
        this.recNormal = recNormal;
    }

    public String getRecManual() {
        return recManual;
    }

    public void setRecManual(String recManual) {
        this.recManual = recManual;
    }

    public boolean isActivaCamposRepEmisionRecetas() {
        return activaCamposRepEmisionRecetas;
    }

    public void setActivaCamposRepEmisionRecetas(boolean activaCamposRepEmisionRecetas) {
        this.activaCamposRepEmisionRecetas = activaCamposRepEmisionRecetas;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
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


    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }
    
}
