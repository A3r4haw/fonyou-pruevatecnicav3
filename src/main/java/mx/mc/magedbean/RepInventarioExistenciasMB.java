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
import mx.mc.lazy.ReporteInventarioExistenciaLazy;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.ReportesService;
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
public class RepInventarioExistenciasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepInventarioExistenciasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private int sem1;
    private int sem2;
    private int cantidadCero;
    private Boolean buscaCantidadCero;
    private boolean parametrosemaforo;
    private boolean muestraCantidadPorCaja;

    private ParamBusquedaReporte paramBusquedaReporte;

    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Usuario usuarioSession;
    private boolean administrador;
    private boolean jefeArea;
    private Estructura estructuraUsuario;
    private boolean mostrarOrigenInsumos;
    private boolean mostrarCostePorLote;
    private boolean mostrarClaveProveedor;
    private boolean mostrarUnidosis;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    private ReporteInventarioExistenciaLazy reporteInventarioExistenciaLazy;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    private String pathPdf;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            listAlmacenesSubAlm = new ArrayList<>();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.administrador = Comunes.isAdministrador();
            this.jefeArea = Comunes.isJefeArea();
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPINVENTARIOEXISTENCIAS.getSufijo());
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

            sem1 = sesion.getParametroSemaforo1();
            sem2 = sesion.getParametroSemaforo2();
            //GCR: Se quita parametro de configuracion permiteAjusteInventarioGlobal que se cambio de chiconcuac            
            parametrosemaforo = sesion.isParametrosemaforo();
            cantidadCero = 0;    //sesion.getCantidadCero();
            mostrarOrigenInsumos = sesion.isMostrarOrigenDeInsumo();
            mostrarClaveProveedor = sesion.isMostrarClaveProveedor();
            mostrarCostePorLote = sesion.isMostrarCostePorLote();
            mostrarUnidosis = sesion.isMostrarUnidosis();
            muestraCantidadPorCaja = sesion.isMuestraCantidadPorCaja();
            List<Integer> listTipoAlmacen = new ArrayList<>();
            listTipoAlmacen.add(TipoAlmacen_Enum.FARMACIA.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.ALMACEN.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.SUBALMACEN.getValue());
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
                this.listAlmacenesSubAlm.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
                this.idEstructura = null;
            } else if(this.jefeArea) {
                this.listAlmacenesSubAlm = estructuraService.obtenerAlmacenesQueSurtenServicio(this.usuarioSession.getIdEstructura());
                if(listAlmacenesSubAlm.isEmpty()) {
                    noEncontroRegistro = true;
                    this.jefeArea = false;
                } else {
                    this.estructuraUsuario = listAlmacenesSubAlm.get(0);
                }
            }  else {
                noEncontroRegistro = true;
            }
            if(noEncontroRegistro) {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de almacenes y subalmacenes: {}", ex.getMessage());
        }
        this.paramBusquedaReporte = new ParamBusquedaReporte();

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
        if (!permiso.isPuedeVer()) {
            Mensaje.showMessage("Error", RESOURCES.getString("err.transaccion"), null);
        } else {
            try {
                if (administrador || jefeArea) {
                    if (this.idEstructura != null) {
                        this.paramBusquedaReporte.setIdEstructura(idEstructura);
                    } else {
                        this.paramBusquedaReporte.setIdEstructura(null);
                    }
                } else {
                    this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
                }
                this.paramBusquedaReporte.setCantidadCero(cantidadCero);

                this.reporteInventarioExistenciaLazy = new ReporteInventarioExistenciaLazy(this.reporteMovimientosService, this.paramBusquedaReporte);

                LOGGER.debug("Resultados: {}", reporteInventarioExistenciaLazy.getTotalReg());

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

    public void imprimeReporteExistencias() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepInventarioExistenciasMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            if (administrador || jefeArea) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                } else {
                    this.paramBusquedaReporte.setIdEstructura(null);
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            EntidadHospitalaria entidad;
            if (this.paramBusquedaReporte.getIdEstructura() != null) {
                Estructura est = new Estructura();
                est.setIdEstructura(this.paramBusquedaReporte.getIdEstructura());
                Estructura e = estructuraService.obtenerEstructura(est.getIdEstructura());
                this.paramBusquedaReporte.setNombreEstructura(e.getNombre());

                EntidadHospitalaria ent = new EntidadHospitalaria();
                ent.setIdEntidadHospitalaria(e.getIdEntidadHospitalaria());
                entidad = entidadHospitalariaService.obtener(ent);
            } else {
                this.paramBusquedaReporte.setNombreEstructura(Constantes.ALMACENES_TODOS);
                entidad = entidadHospitalariaService.obtenerEntidadHospital();
            }
            this.paramBusquedaReporte.setCantidadCero(cantidadCero);
            this.paramBusquedaReporte.setUsuarioGenera(usuarioSession);
            byte[] buffer = reportesService.imprimeReporteExistenciasMostarndoColumnas(paramBusquedaReporte, entidad,
                    mostrarOrigenInsumos, mostrarClaveProveedor, mostrarUnidosis, mostrarCostePorLote);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("existenciasInventario_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelExistencia() {
        LOGGER.debug("mx.mc.magedbean.RepInventarioExistenciasMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            if (administrador || jefeArea) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                } else {
                    this.paramBusquedaReporte.setIdEstructura(null);
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            EntidadHospitalaria entidad;
            if (this.paramBusquedaReporte.getIdEstructura() != null) {
                Estructura est = new Estructura();
                est.setIdEstructura(this.paramBusquedaReporte.getIdEstructura());
                Estructura e = estructuraService.obtenerEstructura(est.getIdEstructura());
                this.paramBusquedaReporte.setNombreEstructura(e.getNombre());

                EntidadHospitalaria ent = new EntidadHospitalaria();
                ent.setIdEntidadHospitalaria(e.getIdEntidadHospitalaria());
                entidad = entidadHospitalariaService.obtener(ent);
            } else {
                this.paramBusquedaReporte.setNombreEstructura(Constantes.ALMACENES_TODOS);
                entidad = entidadHospitalariaService.obtenerEntidadHospital();
            }
            this.paramBusquedaReporte.setCantidadCero(cantidadCero);
            this.paramBusquedaReporte.setUsuarioGenera(usuarioSession);
            boolean res = reportesService.imprimeExcelExistenciasMostarndoColumnas(paramBusquedaReporte, entidad,
                    mostrarOrigenInsumos, mostrarClaveProveedor, mostrarUnidosis, mostrarCostePorLote);
            if (res) {
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void cambiarBusquedaCantidadCero() {
        if (buscaCantidadCero) {
            cantidadCero = 1;
        } else {
            cantidadCero = 0;
        }
    }
    
    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
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

    public ReporteInventarioExistenciaLazy getReporteInventarioExistenciaLazy() {
        return reporteInventarioExistenciaLazy;
    }

    public void setReporteInventarioExistenciaLazy(ReporteInventarioExistenciaLazy reporteInventarioExistenciaLazy) {
        this.reporteInventarioExistenciaLazy = reporteInventarioExistenciaLazy;
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

    public Estructura getEstructuraUsuario() {
        return estructuraUsuario;
    }

    public void setEstructuraUsuario(Estructura estructuraUsuario) {
        this.estructuraUsuario = estructuraUsuario;
    }

    public int getSem1() {
        return sem1;
    }

    public void setSem1(int sem1) {
        this.sem1 = sem1;
    }

    public int getSem2() {
        return sem2;
    }

    public void setSem2(int sem2) {
        this.sem2 = sem2;
    }

    public Boolean getBuscaCantidadCero() {
        return buscaCantidadCero;
    }

    public void setBuscaCantidadCero(Boolean buscaCantidadCero) {
        this.buscaCantidadCero = buscaCantidadCero;
    }

    public int getCantidadCero() {
        return cantidadCero;
    }

    public void setCantidadCero(int cantidadCero) {
        this.cantidadCero = cantidadCero;
    }

    public boolean isParametrosemaforo() {
        return parametrosemaforo;
    }

    public void setParametrosemaforo(boolean parametrosemaforo) {
        this.parametrosemaforo = parametrosemaforo;
    }

    public boolean isMostrarOrigenInsumos() {
        return mostrarOrigenInsumos;
    }

    public void setMostrarOrigenInsumos(boolean mostrarOrigenInsumos) {
        this.mostrarOrigenInsumos = mostrarOrigenInsumos;
    }

    public boolean isMostrarCostePorLote() {
        return mostrarCostePorLote;
    }

    public void setMostrarCostePorLote(boolean mostrarCostePorLote) {
        this.mostrarCostePorLote = mostrarCostePorLote;
    }

    public boolean isMostrarClaveProveedor() {
        return mostrarClaveProveedor;
    }

    public void setMostrarClaveProveedor(boolean mostrarClaveProveedor) {
        this.mostrarClaveProveedor = mostrarClaveProveedor;
    }

    public boolean isMostrarUnidosis() {
        return mostrarUnidosis;
    }

    public void setMostrarUnidosis(boolean mostrarUnidosis) {
        this.mostrarUnidosis = mostrarUnidosis;
    }

    public boolean isMuestraCantidadPorCaja() {
        return muestraCantidadPorCaja;
    }

    public void setMuestraCantidadPorCaja(boolean muestraCantidadPorCaja) {
        this.muestraCantidadPorCaja = muestraCantidadPorCaja;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
