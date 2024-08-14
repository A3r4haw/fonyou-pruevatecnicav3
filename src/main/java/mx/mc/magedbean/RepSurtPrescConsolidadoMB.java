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

import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.SurtPrescripcionConsolidadoLazy;
import mx.mc.model.DataResultReport;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.ReportesService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import org.primefaces.PrimeFaces;

/**
 *
 * @author mcalderon
 *
 */
@Controller
@Scope(value = "view")
public class RepSurtPrescConsolidadoMB implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepSurtPrescConsolidadoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<DataResultReport> listDataResultReport; 
    private PermisoUsuario permiso;
    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Estructura estructuraUsuario;
    private String datosUsuario;
    private String matrPers;

    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient ReportesService reportesService;

    private SurtPrescripcionConsolidadoLazy surtPrescripcionConsolidadoLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            StringBuilder sb = new StringBuilder(usuarioSession.getNombre());            
            sb.append(" ").append(usuarioSession.getApellidoPaterno()).append(" ").append(usuarioSession.getApellidoMaterno());
            datosUsuario = sb.toString();
            matrPers = usuarioSession.getMatriculaPersonal();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTEPRESCRIPCIONCONSOLIDADA.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {            
            fechaActualInicio = FechaUtil.getFechaActual();
            fechaActual = new java.util.Date();
            List<Integer> listTipoAreaEstructura = new ArrayList<>();
            listTipoAreaEstructura.add(TipoAreaEstructura_Enum.ALMACEN.getValue());
            listTipoAreaEstructura.add(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue());
            /*listTipoAreaEstructura.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
            */
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAreaEstructura(listTipoAreaEstructura);
                this.listAlmacenesSubAlm = listaAuxiliar;
                //ordenarListaEstructura(listaAuxiliar.get(0));
            } else {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de Almacenes: {}", ex.getMessage());
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
        try {            
            paramBusquedaReporte.setNuevaBusqueda(true);            
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            surtPrescripcionConsolidadoLazy = new SurtPrescripcionConsolidadoLazy(
                    reporteMovimientosService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", surtPrescripcionConsolidadoLazy.getTotalReg());

        } catch (Exception e1) {
            LOGGER.error("Error al consultar", e1);
        }
    }

 

    public void imprimeReporteConsolidado() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtPrescConsolidadoMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {                        
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }                                   
            Estructura estCons;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                estCons = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                estCons = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }
            EntidadHospitalaria entiHospConsol = entidadHospitalariaService.obtenerEntidadById(estCons.getIdEntidadHospitalaria());
            
            
            byte[] buffer = reportesService.SurtPrescripcionConsolidado(paramBusquedaReporte, entiHospConsol, datosUsuario, matrPers);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repSurtPrescrpConsolidado_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelConsolidado() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtPrescConsolidadoMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {            
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }                                           
            
            Estructura estConEx;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                estConEx = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                estConEx = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }
            EntidadHospitalaria entiHospConsolExc = entidadHospitalariaService.obtenerEntidadById(estConEx.getIdEntidadHospitalaria());

            boolean res = reportesService.generaExcelPrescConsolidado(paramBusquedaReporte, entiHospConsolExc, datosUsuario, matrPers);
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
    
    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public SurtPrescripcionConsolidadoLazy getSurtPrescripcionConsolidadoLazy() {
        return surtPrescripcionConsolidadoLazy;
    }

    public void setSurtPrescripcionConsolidadoLazy(SurtPrescripcionConsolidadoLazy surtPrescripcionConsolidadoLazy) {
        this.surtPrescripcionConsolidadoLazy = surtPrescripcionConsolidadoLazy;
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

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
