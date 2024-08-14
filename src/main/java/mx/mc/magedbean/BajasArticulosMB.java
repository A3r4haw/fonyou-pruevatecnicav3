package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.mc.lazy.BajasArticulosLazy;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.BajasArticulos;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.BajasArticulosService;
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
public class BajasArticulosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BajasArticulosMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private ParamBusquedaReporte paramBusquedaReporte;
    private List<BajasArticulos> listEstatusInsumo;

    private List<Estructura> listAlmacenesSubAlm;
    private boolean administrador;
    private Usuario usuarioSession;
    private Date fechaActualInicio;
    private Date fechaActual;
    private List<Estructura> listaAuxiliar;
    private Estructura estructuraUsuario;
    private List<Usuario> listUsuario;

    private String idEstructura;
    private String pathPdf;
    private String caducado;
    private String deteriorado;
    private String suspendido;
    private String estateInsumo;
    private PermisoUsuario permiso;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient BajasArticulosService bajasArticulosService;

    private BajasArticulosLazy bajasArticulosLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            initialize();

//            obtienePermisos();
            permiso = new PermisoUsuario();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTESBASICOS.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    public void initialize() {
        try {           
            List<Integer> listTipoAlmacen = new ArrayList<>();

            listTipoAlmacen.add(TipoAlmacen_Enum.ALMACEN.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.SUBALMACEN.getValue());
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
            } else if (usuarioSession.getIdEstructura().equals(Constantes.IDESTRUCTURA_CADIT)) {
                //Se muestran todas las estructuras con estructuraPadre = CAdit
                this.listaAuxiliar = estructuraService.obtenerEstructurasPadreCadit(Constantes.IDESTRUCTURA_CADIT);
            } else {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());

            }

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista {}", ex.getMessage());
        }
        paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
        caducado = "N";
        deteriorado = "D";
        suspendido = "O";
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
            paramBusquedaReporte.setEstatusCantidadInsumo(estateInsumo);
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }

            bajasArticulosLazy = new BajasArticulosLazy(
                    bajasArticulosService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", bajasArticulosLazy.getTotalReg());

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

    public void imprimeReporteBajasInsumos() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtMinistrado.imprimeReporteBajasInsumos()");
        boolean status = Constantes.INACTIVO;
        try {
            paramBusquedaReporte.setEstatusCantidadInsumo(estateInsumo);
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }

            Estructura estr;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                estr = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                estr = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }

            EntidadHospitalaria entidad;
            entidad = entidadHospitalariaService.obtenerEntidadById(estr.getIdEntidadHospitalaria());
            Long total = bajasArticulosService.obtenerTotalRegistros(paramBusquedaReporte);
            paramBusquedaReporte.setTotal(total);

            byte[] buffer = reportesService.imprimeReporteBajasInsumos(paramBusquedaReporte, entidad, estr);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteBajasInsumos_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresion reporte: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelBajaArticulos() {
        LOGGER.debug("mx.mc.magedbean.RepInventarioExistenciasMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            if (administrador) {
                if (this.idEstructura != null) {
                    this.paramBusquedaReporte.setIdEstructura(idEstructura);
                } else {
                    this.paramBusquedaReporte.setIdEstructura(null);
                }
            } else {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            }
            EntidadHospitalaria entidad;
            Estructura e = new Estructura();
            if (this.paramBusquedaReporte.getIdEstructura() != null) {
                Estructura est = new Estructura();
                est.setIdEstructura(this.paramBusquedaReporte.getIdEstructura());
                e = estructuraService.obtenerEstructura(est.getIdEstructura());
                this.paramBusquedaReporte.setNombreEstructura(e.getNombre());

                EntidadHospitalaria ent = new EntidadHospitalaria();
                ent.setIdEntidadHospitalaria(e.getIdEntidadHospitalaria());
                entidad = entidadHospitalariaService.obtener(ent);
            } else {
                this.paramBusquedaReporte.setNombreEstructura(Constantes.ALMACENES_TODOS);
                entidad = entidadHospitalariaService.obtenerEntidadHospital();
            }
            boolean res = reportesService.generaExcelBajaArticulos(paramBusquedaReporte, entidad, e);
            if (res) {
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
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

    public BajasArticulosService getBajasArticulosService() {
        return bajasArticulosService;
    }

    public void setBajasArticulosService(BajasArticulosService bajasArticulosService) {
        this.bajasArticulosService = bajasArticulosService;
    }

    public BajasArticulosLazy getBajasArticulosLazy() {
        return bajasArticulosLazy;
    }

    public void setBajasArticulosLazy(BajasArticulosLazy bajasArticulosLazy) {
        this.bajasArticulosLazy = bajasArticulosLazy;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public String getCaducado() {
        return caducado;
    }

    public void setCaducado(String caducado) {
        this.caducado = caducado;
    }

    public String getDeteriorado() {
        return deteriorado;
    }

    public void setDeteriorado(String deteriorado) {
        this.deteriorado = deteriorado;
    }

    public String getSuspendido() {
        return suspendido;
    }

    public void setSuspendido(String suspendido) {
        this.suspendido = suspendido;
    }

    public String getEstateInsumo() {
        return estateInsumo;
    }

    public void setEstateInsumo(String estateInsumo) {
        this.estateInsumo = estateInsumo;
    }

    public List<BajasArticulos> getListEstatusInsumo() {
        return listEstatusInsumo;
    }

    public void setListEstatusInsumo(List<BajasArticulos> listEstatusInsumo) {
        this.listEstatusInsumo = listEstatusInsumo;
    }

    public List<Usuario> getListUsuario() {
        return listUsuario;
    }

    public void setListUsuario(List<Usuario> listUsuario) {
        this.listUsuario = listUsuario;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
        
}
