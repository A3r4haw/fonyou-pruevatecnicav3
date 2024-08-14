/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import mx.mc.lazy.ReporteSurtidoServicioLazy;
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
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReporteSurtidoServicio;
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
 * @author unava
 */
@Controller
@Scope(value = "view")
public class ReporteSurtidoServicioMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteConcentracionArticuloMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private Usuario medico;
    private List<ReporteSurtidoServicio> listEstatusInsumo;
    private ParamBusquedaReporte paramBusquedaReporte;

    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private String pathPdf;

    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Estructura estructuraUsuario;
    private String tdAll;
    private String disponible;
    private String tdDirecto;
    private String tdSustituto;
    private String tdDenegado;
    private String estateInsumo;
    private int tipoInsumo;
    private int insumoMedicamento;
    private int insumoMaterial;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReportesService reportesService;    

    private ReporteSurtidoServicioLazy reporteSurtidoServicioLazy;

    /**
     * *******************************************************************
     ** Metodo que se ejecuta despues de cargar la pantalla de pacientes**
     ********************************************************************
     */
    @PostConstruct
    public void init() {
        try {
            listAlmacenesSubAlm = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            this.insumoMedicamento = 38;
            this.insumoMaterial = 39;
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTSURTSERVIC.getSufijo());
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
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento: {}", ex.getMessage());
        }
        paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
        paramBusquedaReporte.setIntervalDate(30);

        tdDirecto = "DIRECTO";
        tdSustituto = "SUSTITUTO";
        tdDenegado = "DENEGADO";
        tdAll = "all";
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
            paramBusquedaReporte.setTipoInsumo(tipoInsumo);
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            reporteSurtidoServicioLazy = new ReporteSurtidoServicioLazy(
                    reporteMovimientosService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", reporteSurtidoServicioLazy.getTotalReg());

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
        LOGGER.debug("mx.mc.magedbean.ReporteSurtidoServicioMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    
    public void imprimeReporteEstatusInsumos() throws Exception {
        LOGGER.debug("mx.mc.magedbean.ReporteSurtidoServicioMB.imprimeReporteEstatusInsumos()");
        boolean status = Constantes.INACTIVO;
        try {
            paramBusquedaReporte.setEstatusCantidadInsumo(estateInsumo);
            paramBusquedaReporte.setTipoInsumo(tipoInsumo);
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            Long total = reporteMovimientosService.consultarSurtidoServicioTotal(paramBusquedaReporte);

            paramBusquedaReporte.setTotal(total);
            
            byte[] buffer = reportesService.imprimeSurtidosServicio(paramBusquedaReporte);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteSurtidoServicio_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelSurtidosServicio() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtMinistrado.imprimeExcelSurtidosServicio()");
        boolean status = Constantes.INACTIVO;
        try {
            paramBusquedaReporte.setEstatusCantidadInsumo(estateInsumo);
            paramBusquedaReporte.setTipoInsumo(tipoInsumo);
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            Long total = reporteMovimientosService.consultarSurtidoServicioTotal(paramBusquedaReporte);

            paramBusquedaReporte.setTotal(total);
            boolean res = reportesService.generaExcelSurtidosServicio(this.paramBusquedaReporte);
            if (res) {
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public ParamBusquedaReporte getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
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

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public String getDisponible() {
        return disponible;
    }

    public String getTdAll() {
        return tdAll;
    }

    public void setTdAll(String tdAll) {
        this.tdAll = tdAll;
    }

    public String getTdDirecto() {
        return tdDirecto;
    }

    public void setTdDirecto(String tdDirecto) {
        this.tdDirecto = tdDirecto;
    }

    public String getTdSustituto() {
        return tdSustituto;
    }

    public void setTdSustituto(String tdSustituto) {
        this.tdSustituto = tdSustituto;
    }

    public String getTdDenegado() {
        return tdDenegado;
    }

    public void setTdDenegado(String tdDenegado) {
        this.tdDenegado = tdDenegado;
    }

    public String getEstateInsumo() {
        return estateInsumo;
    }

    public void setEstateInsumo(String estateInsumo) {
        this.estateInsumo = estateInsumo;
    }

    public ReporteSurtidoServicioLazy getReporteSurtidoServicioLazy() {
        return reporteSurtidoServicioLazy;
    }

    public void setReporteSurtidoServicioLazy(ReporteSurtidoServicioLazy reporteSurtidoServicioLazy) {
        this.reporteSurtidoServicioLazy = reporteSurtidoServicioLazy;
    }

    public List<ReporteSurtidoServicio> getListEstatusInsumo() {
        return listEstatusInsumo;
    }

    public void setListEstatusInsumo(List<ReporteSurtidoServicio> listEstatusInsumo) {
        this.listEstatusInsumo = listEstatusInsumo;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public int getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(int tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public int getInsumoMedicamento() {
        return insumoMedicamento;
    }

    public void setInsumoMedicamento(int insumoMedicamento) {
        this.insumoMedicamento = insumoMedicamento;
    }

    public int getInsumoMaterial() {
        return insumoMaterial;
    }

    public void setInsumoMaterial(int insumoMaterial) {
        this.insumoMaterial = insumoMaterial;
    }

}
