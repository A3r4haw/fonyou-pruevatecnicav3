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
import mx.mc.lazy.ReporteRefri_5000Lazy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReporteEstatusInsumo;
import mx.mc.model.Usuario;
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
 * @author mcalderon
 *
 */
@Controller
@Scope(value = "view")
public class Reporte_5000_RefrigeracionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Reporte_5000_RefrigeracionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private List<ReporteEstatusInsumo> listEstatusInsumo;
    private ParamLibMedControlados paramLibMedControlados;

    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private String pathPdf;

    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Estructura estructuraUsuario;
    private String refrigeracion;
    private String clave5000;
    private String valorRefri5000;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReportesService reportesService;

    private ReporteRefri_5000Lazy reporteRefri5000Lazy;

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
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTREFRIGE5000.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {
            fechaActualInicio = FechaUtil.obtenerFechaInicio();
            fechaActual = FechaUtil.obtenerFechaFin();
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
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento. {}", ex.getMessage());
        }
        paramLibMedControlados = new ParamLibMedControlados();
        paramLibMedControlados.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramLibMedControlados.setFechaFin(FechaUtil.obtenerFechaFin());
        refrigeracion = "R";
        clave5000 = "C";
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
            paramLibMedControlados.setNuevaBusqueda(true);
            paramLibMedControlados.setValorRefri5000(valorRefri5000);
            if (!administrador) {
                this.paramLibMedControlados.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramLibMedControlados.setIdEstructura(idEstructura);
            }

            reporteRefri5000Lazy = new ReporteRefri_5000Lazy(
                    reporteMovimientosService, paramLibMedControlados);

            LOGGER.debug("Resultados: {}", reporteRefri5000Lazy.getTotalReg());

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

    public void imprimeReporteRefriger5000() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtMinistrado.imprimeReporteEstatusInsumos()");
        boolean status = Constantes.INACTIVO;
        try {
            paramLibMedControlados.setValorRefri5000(valorRefri5000);

            if (!administrador) {
                this.paramLibMedControlados.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramLibMedControlados.setIdEstructura(idEstructura);
            }

            byte[] buffer = reportesService.imprimeReporteRefri5000(paramLibMedControlados);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ReporteRefriger_5000_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void obtenerFechaFinal() {
        paramLibMedControlados.setFechaFin(FechaUtil.obtenerFechaFinal(paramLibMedControlados.getFechaFin()));
    }
    
    public ParamLibMedControlados getParamLibMedControlados() {
        return paramLibMedControlados;
    }

    public void setParamLibMedControlados(ParamLibMedControlados paramLibMedControlados) {
        this.paramLibMedControlados = paramLibMedControlados;
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

    public ReporteRefri_5000Lazy getReporteRefri5000Lazy() {
        return reporteRefri5000Lazy;
    }

    public void setReporteRefri5000Lazy(ReporteRefri_5000Lazy reporteRefri5000Lazy) {
        this.reporteRefri5000Lazy = reporteRefri5000Lazy;
    }

    public List<ReporteEstatusInsumo> getListEstatusInsumo() {
        return listEstatusInsumo;
    }

    public void setListEstatusInsumo(List<ReporteEstatusInsumo> listEstatusInsumo) {
        this.listEstatusInsumo = listEstatusInsumo;
    }

    public String getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(String refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public String getClave5000() {
        return clave5000;
    }

    public void setClave5000(String clave5000) {
        this.clave5000 = clave5000;
    }

    public String getValorRefri5000() {
        return valorRefri5000;
    }

    public void setValorRefri5000(String valorRefri5000) {
        this.valorRefri5000 = valorRefri5000;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
