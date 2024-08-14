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
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.lazy.RepExistenciasConsolidadasLazy;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReporteEstatusInsumo;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.RepExistenciasConsolidadasService;
import mx.mc.service.ReportesService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;

/**
 *
 * @author olozada
 *
 */
@Controller
@Scope(value = "view")
public class RepExistenciasConsolidadasMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepExistenciasConsolidadasMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private List<ReporteEstatusInsumo> listEstatusInsumo;
    private Medicamento_Extended medicamento_Extended;


    private Usuario usuarioSession;
    private boolean administrador;
    private String pathPdf;
    private String nombreUsuario;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Estructura estructuraUsuario;


    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient RepExistenciasConsolidadasService repExistenciasConsolidadasService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ReportesService reportesService;
    
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    private RepExistenciasConsolidadasLazy repExistenciasConsolidadasLazy;

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
            nombreUsuario = sb.toString();
            initialize();            
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.EXISTENCIACONSILIDADA.getSufijo());
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
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento. {}", ex.getMessage());
        }
        medicamento_Extended = new Medicamento_Extended();
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
                medicamento_Extended.setNuevaBusqueda(true);
                if (!administrador) {
                    this.medicamento_Extended.setIdEstructura(usuarioSession.getIdEstructura());
                } else {
                    this.medicamento_Extended.setIdEstructura(idEstructura);
                }

                repExistenciasConsolidadasLazy = new RepExistenciasConsolidadasLazy(repExistenciasConsolidadasService, medicamento_Extended);

                LOGGER.debug("Resultados: {}", repExistenciasConsolidadasLazy.getTotalReg());

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
        LOGGER.debug("mx.mc.magedbean.RepExistenciasConsolidadasMB.autocompleteInsumo()");
        List<Medicamento> insumosList = new ArrayList<>();
        try {
            insumosList.addAll(medicamentoService.obtenerInsumos(query));

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al consultar el medicamento");
            Mensaje.showMessage("Error", RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    public void imprimeReporteExist_Consolidadas() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepExistenciasConsolidadasMB.imprimeReporteExist_Consolidadas()");
        boolean status = Constantes.INACTIVO;
        try {
            if (!administrador) {
                this.medicamento_Extended.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.medicamento_Extended.setIdEstructura(idEstructura);
            }
            
            Estructura est;
            if (this.medicamento_Extended.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(medicamento_Extended.getIdEstructura());
            }

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeReporteExist_Consolidadas(medicamento_Extended, entidad,nombreUsuario);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ReporteExistenciasConsolidadas_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    
    public void imprimeExcel_ReporteExist_Consolidadas() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepExistenciasConsolidadasMB.imprimeExcel_ReporteExist_Consolidadas()");
        boolean status = Constantes.INACTIVO;
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            File dirTmp = new File(servletContext.getRealPath("/resources/tmp/"));
            String pathTmp = dirTmp.getPath() + "/reporteExistenciasConsolidadas.xlsx";
            FacesContext ctxt = FacesContext.getCurrentInstance();
            ExternalContext ext = ctxt.getExternalContext();
            URI uri = new URI(ext.getRequestScheme(),
                    null,
                    ext.getRequestServerName(),
                    ext.getRequestServerPort(),
                    ext.getRequestContextPath(),
                    null,
                    null);
            String url = uri.toASCIIString();

            if (!administrador) {
                this.medicamento_Extended.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.medicamento_Extended.setIdEstructura(idEstructura);
            }
            
                        Estructura est;
            if (this.medicamento_Extended.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(medicamento_Extended.getIdEstructura());
            }

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            
            boolean res = reportesService.imprimeExcel_ReporteExist_Consolidadas(medicamento_Extended, pathTmp, url, entidad);
          
            if (res) {
                status = Constantes.ACTIVO;
                this.pathPdf = url + "/resources/tmp/reporteExistenciasConsolidadas.xlsx";
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
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

    public List<ReporteEstatusInsumo> getListEstatusInsumo() {
        return listEstatusInsumo;
    }

    public void setListEstatusInsumo(List<ReporteEstatusInsumo> listEstatusInsumo) {
        this.listEstatusInsumo = listEstatusInsumo;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public Medicamento_Extended getMedicamento_Extended() {
        return medicamento_Extended;
    }

    public void setMedicamento_Extended(Medicamento_Extended medicamento_Extended) {
        this.medicamento_Extended = medicamento_Extended;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
    }

    public RepExistenciasConsolidadasService getRepExistenciasConsolidadasService() {
        return repExistenciasConsolidadasService;
    }

    public void setRepExistenciasConsolidadasService(RepExistenciasConsolidadasService repExistenciasConsolidadasService) {
        this.repExistenciasConsolidadasService = repExistenciasConsolidadasService;
    }

    public MedicamentoService getMedicamentoService() {
        return medicamentoService;
    }

    public void setMedicamentoService(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    public ReportesService getReportesService() {
        return reportesService;
    }

    public void setReportesService(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    public RepExistenciasConsolidadasLazy getRepExistenciasConsolidadasLazy() {
        return repExistenciasConsolidadasLazy;
    }

    public void setRepExistenciasConsolidadasLazy(RepExistenciasConsolidadasLazy repExistenciasConsolidadasLazy) {
        this.repExistenciasConsolidadasLazy = repExistenciasConsolidadasLazy;
    }

    
}
