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

import mx.mc.lazy.ReporteSurtMinistradoLazy;
import mx.mc.enums.EstatusMinistracion_Enum;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.TipoUsuario_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PacienteService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
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
public class RepSurtMinistradoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RepSurtMinistradoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private PermisoUsuario permiso;
    private Usuario medico;
    private boolean mostrarComboServicio;

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
    private Paciente_Extended pacienteExtended;    
    private int ministrado;
    private int noMinistrado;
    private int ministradoPendiente;    
    private List<String> estructuraList;
    
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient ReportesService reportesService;

    private ReporteSurtMinistradoLazy reporteSurtMinistradoLazy;

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
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.REPMINISTRACION.getSufijo());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        try {          
            mostrarComboServicio = false;            
            List<Integer> listTipoAlmacen = new ArrayList<>();
            
            listTipoAlmacen.add(TipoAlmacen_Enum.NO_APLICA.getValue());

            usuarioSession = usuarioService.obtenerUsuarioPorId(usuarioSession.getIdUsuario());

            if (this.administrador) {            
                estructuraList = null;
                this.listaAuxiliar = estructuraService.getEstructuraServicios(listTipoAlmacen, estructuraList);
            } else if (usuarioSession.getIdTipoPerfil().equals(Constantes.JEFE_AREA)) {

                estructuraList = estructuraService.obtenerIdsEstructuraJerarquica(usuarioSession.getIdEstructura(), false);
                estructuraList.add(usuarioSession.getIdEstructura());

                this.listaAuxiliar = estructuraService.getEstructuraServicios(listTipoAlmacen, estructuraList);

            } else {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
                mostrarComboServicio = true;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de Servicios: {}", ex.getMessage());
        }
        paramBusquedaReporte = new ParamBusquedaReporte();
        paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
        ministrado = EstatusMinistracion_Enum.MINISTRADO.getValue();
        noMinistrado = EstatusMinistracion_Enum.NO_MINISTRADO.getValue();
        ministradoPendiente = EstatusMinistracion_Enum.PENDIENTE.getValue();
    }
     
    public List<Paciente_Extended> autoCompletePacientes(String cadena) {
        List<Paciente_Extended> listaPacientes = new ArrayList<>();
        try {
            listaPacientes = this.pacienteService.obtenerRegistrosPorCriterioDeBusqueda(
                    cadena.trim(), Constantes.REGISTROS_PARA_MOSTRAR);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return listaPacientes;
    } 
        
    public List<Usuario> autoCompleteMedicos(String cadena) {
        List<Usuario> listaMedicos = new ArrayList<>();
        try {                            
            listaMedicos = this.usuarioService.obtenerMedicosPorCriteriosBusqueda(cadena, TipoUsuario_Enum.MEDICO.getValue(), Constantes.REGISTROS_PARA_MOSTRAR, null);                        
        } catch (Exception ex) {
            LOGGER.error("Error al obtener los Médicos: {}", ex.getMessage());
        }
        return listaMedicos;
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {         
            paramBusquedaReporte.setNuevaBusqueda(true);                   
            if (administrador) {
                estructuraList = new ArrayList<>();
                estructuraList = null;
            } else if (usuarioSession.getIdTipoPerfil().equals(Constantes.JEFE_AREA)) {
                List<Usuario> lisUser = new ArrayList<>();
                lisUser.add(usuarioSession);
                paramBusquedaReporte.setListUsuarios(lisUser);
                if (idEstructura == null) {
                    paramBusquedaReporte.setListaEstructuras(estructuraList);
                } else {
                    estructuraList = null;
                    estructuraList = new ArrayList<>();
                    estructuraList.add(idEstructura);
                    paramBusquedaReporte.setListaEstructuras(estructuraList);
                }
            } else {
                estructuraList = new ArrayList<>();
                estructuraList.add(usuarioSession.getIdEstructura());
            }

            reporteSurtMinistradoLazy = new ReporteSurtMinistradoLazy(
                    reporteMovimientosService, paramBusquedaReporte, estructuraList);

            LOGGER.debug("Resultados: {}", reporteSurtMinistradoLazy.getTotalReg());

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
    
    
    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
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

    public void imprimeReporteMinistracion() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtMinistrado.imprimeReporteMinistracion()");
        boolean status = Constantes.INACTIVO;
        try {
            if(paramBusquedaReporte.getFolio().equals("")){                
                paramBusquedaReporte.setFolio(null);
            }
            if (administrador) {
                estructuraList = new ArrayList<>();
                estructuraList = null;
            } else if (usuarioSession.getIdTipoPerfil().equals(Constantes.JEFE_AREA)) {
                List<Usuario> lisUser = new ArrayList<>();
                lisUser.add(usuarioSession);
                paramBusquedaReporte.setListUsuarios(lisUser);
                if (idEstructura == null) {
                    paramBusquedaReporte.setListaEstructuras(estructuraList);
                } else {
                    estructuraList = null;
                    estructuraList = new ArrayList<>();
                    estructuraList.add(idEstructura);
                    paramBusquedaReporte.setListaEstructuras(estructuraList);
                }
            } else {
                estructuraList.add(usuarioSession.getIdEstructura());
            }

            Estructura est;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }

            EntidadHospitalaria entidad;            
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            byte[] buffer = reportesService.imprimeReporteMinistracion(this.paramBusquedaReporte, entidad, estructuraList);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("reporteMinistraciones_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelMinistracion() throws Exception {
        LOGGER.debug("mx.mc.magedbean.RepSurtMinistrado.generaExcelMinistracion()");
        boolean status = Constantes.INACTIVO;
        try {            
            if (administrador) {
                estructuraList = new ArrayList<>();
                estructuraList = null;
            } else if (usuarioSession.getIdTipoPerfil().equals(Constantes.JEFE_AREA)) {
                List<Usuario> lisUser = new ArrayList<>();
                lisUser.add(usuarioSession);
                paramBusquedaReporte.setListUsuarios(lisUser);
                if (idEstructura == null) {
                    paramBusquedaReporte.setListaEstructuras(estructuraList);
                } else {
                    estructuraList = null;
                    estructuraList = new ArrayList<>();
                    estructuraList.add(idEstructura);
                    paramBusquedaReporte.setListaEstructuras(estructuraList);
                }
            } else {
                estructuraList.add(usuarioSession.getIdEstructura());
            }

            if(paramBusquedaReporte.getFolio().equals("")){                
                paramBusquedaReporte.setFolio(null);
            }
            
            Estructura est;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }

            EntidadHospitalaria entidad;
            entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            
            boolean res = reportesService.imprimeExcelMinistracion(this.paramBusquedaReporte,entidad,estructuraList);            
            if(res){
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

    public ReporteSurtMinistradoLazy getReporteSurtMinistradoLazy() {
        return reporteSurtMinistradoLazy;
    }

    public void setReporteSurtMinistradoLazy(ReporteSurtMinistradoLazy reporteSurtMinistradoLazy) {
        this.reporteSurtMinistradoLazy = reporteSurtMinistradoLazy;
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

    public Paciente_Extended getPacienteExtended() {
        return pacienteExtended;
    }

    public void setPacienteExtended(Paciente_Extended pacienteExtended) {
        this.pacienteExtended = pacienteExtended;
    }    

    public int getMinistrado() {
        return ministrado;
    }

    public void setMinistrado(int ministrado) {
        this.ministrado = ministrado;
    }

    public int getNoMinistrado() {
        return noMinistrado;
    }

    public void setNoMinistrado(int noMinistrado) {
        this.noMinistrado = noMinistrado;
    }

    public int getMinistradoPendiente() {
        return ministradoPendiente;
    }

    public void setMinistradoPendiente(int ministradoPendiente) {
        this.ministradoPendiente = ministradoPendiente;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public boolean isMostrarComboServicio() {
        return mostrarComboServicio;
    }

    public void setMostrarComboServicio(boolean mostrarComboServicio) {
        this.mostrarComboServicio = mostrarComboServicio;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
