/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.mc.lazy.ReporteConcentracionArticulosLazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReporteConcentracionArticulos;
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
 * @author unava
 */
@Controller
@Scope(value = "view")
public class ReporteConcentracionArticuloMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteConcentracionArticuloMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private Usuario medico;
    private List<ReporteConcentracionArticulos> listEstatusInsumo;
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
    private String disponible;
    private String caducado;
    private String deteriorado;
    private String suspendido;
    private String estateInsumo;
    private ReporteConcentracionArticulosLazy reporteConcentracionLazy;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReportesService reportesService;

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
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPORTECONCENTRACIONES.getSufijo());
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
        paramBusquedaReporte.setFechaInicio(this.fechaActualInicio);
        paramBusquedaReporte.setFechaFin(this.fechaActual);
        paramBusquedaReporte.setIntervalDate(30);

        disponible = "DISPONIBLES";
        caducado = "CADUCOS";
        deteriorado = "DETERIORADOS";
        suspendido = "SUSPENDIDOS";
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

            reporteConcentracionLazy = new ReporteConcentracionArticulosLazy(
                    reporteMovimientosService, paramBusquedaReporte);

            LOGGER.debug("Resultados: {}", reporteConcentracionLazy.getTotalReg());

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

    public void imprimeReporteConcentracionArticulos() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtMinistrado.imprimeReporteEstatusInsumosConce()");
        boolean status = Constantes.INACTIVO;
        try {
            paramBusquedaReporte.setEstatusCantidadInsumo(estateInsumo);
            Estructura est;
            EntidadHospitalaria entidad = null;
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            Long total = reporteMovimientosService.obtenerTotalEstatusInsumosConce(paramBusquedaReporte);

            paramBusquedaReporte.setTotal(total);
            
            est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            if(est != null){
                entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());                
            }
            byte[] buffer = reportesService.imprimeReporteEstatusInsumosConce(paramBusquedaReporte, entidad, est);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteConcentracionArt_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelConcentracionArticulos() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtMinistrado.imprimeReporteEstatusInsumosConce()");
        boolean status = Constantes.INACTIVO;
        try {
            Estructura est;
            EntidadHospitalaria entidad = null;
            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
             if(est != null){
                entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());                
             }
            
            paramBusquedaReporte.setEstatusCantidadInsumo(estateInsumo);
            Long total = reporteMovimientosService.obtenerTotalEstatusInsumos(paramBusquedaReporte);

            paramBusquedaReporte.setTotal(total);
            boolean res = reportesService.generaExcelEstatusInsumosConce(this.paramBusquedaReporte, entidad,est);
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

    public void setDisponible(String disponible) {
        this.disponible = disponible;
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

    public ReporteConcentracionArticulosLazy getReporteConcentracionLazy() {
        return reporteConcentracionLazy;
    }

    public void setReporteConcentracionLazy(ReporteConcentracionArticulosLazy reporteConcentracionLazy) {
        this.reporteConcentracionLazy = reporteConcentracionLazy;
    }

    public List<ReporteConcentracionArticulos> getListEstatusInsumo() {
        return listEstatusInsumo;
    }

    public void setListEstatusInsumo(List<ReporteConcentracionArticulos> listEstatusInsumo) {
        this.listEstatusInsumo = listEstatusInsumo;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
