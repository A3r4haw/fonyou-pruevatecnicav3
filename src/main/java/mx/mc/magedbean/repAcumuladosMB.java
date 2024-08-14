package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.ReporteAcumuladosLazy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.Acumulados_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.DataResultReport;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Medicamento;
import mx.mc.model.Paciente;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoMovimiento;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
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
public class repAcumuladosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(repAcumuladosMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private ParamBusquedaReporte paramBusquedaReporte;

    private List<DataResultReport> listDataResultReporteAcumulado;

    private List<TipoMovimiento> listTipoMovimiento;
    private int idTipoMovimiento;
    private List<Integer> tipoMovimientoSelects;

    private Date fechaActualInicio;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;   
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Integer acumulado;
    private Estructura estructuraUsuario;
    private List<Usuario> listaMedicos;
    private String idMedico;
    private String idServicio;
    private List<Estructura> listaServicios;
    private boolean activaCamposReporteAcumulados;
    private PermisoUsuario permiso;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient PacienteService pacienteService;

    @Autowired
    private transient TipoMovimientoService tipoMovimientoService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient ReportesService reportesService;

    private ReporteAcumuladosLazy reporteAcumuladosLazy;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            listAlmacenesSubAlm = new ArrayList<>();
            listaServicios = new ArrayList<>();
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            initialize();      
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
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            activaCamposReporteAcumulados = sesion.isActivaCamposReporteAcumulados();
            listTipoMovimiento = tipoMovimientoService.obtenerLista(new TipoMovimiento());
            acumulado = Acumulados_Enum.ACUMULADO_CLAVE.getValue();
            fechaActualInicio = FechaUtil.getFechaActual();
            obtenerListaMedicosActivos();
            fechaActual = new java.util.Date();
            obtenerServicios();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener la lista de tipos de Movimiento: {}", ex.getMessage());
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

    public void obtenerServicios() {
        List<Integer> listaTipoArea = new ArrayList<>();
        listaTipoArea.add(TipoAreaEstructura_Enum.CONSULTAEXTERNA.getValue());
        try {
            this.listaServicios = this.estructuraService.obtenerEstructurasPorTipo(listaTipoArea);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
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

    private void obtenerListaMedicosActivos() {
        try {
            listaMedicos = usuarioService.obtenerListaMedicosActivos();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicos Activos: {}", ex.getMessage());
        }
    }

    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {
            int valTipoReceta = 0;
            if (paramBusquedaReporte.getTipoReceta() != null) {
                if (paramBusquedaReporte.getTipoReceta().equals("E") || paramBusquedaReporte.getTipoReceta().equals("F") || paramBusquedaReporte.getTipoReceta().equals("N")) {
                    valTipoReceta = 1;
                    paramBusquedaReporte.setValTipoReceta(valTipoReceta);
                    if (paramBusquedaReporte.getTipoReceta().equals("N")) {
                        paramBusquedaReporte.setTipoReceta(null);
                    }
                } else {
                    paramBusquedaReporte.setValTipoReceta(valTipoReceta);
                }

            } else {
                paramBusquedaReporte.setTipoReceta(null);
                paramBusquedaReporte.setValTipoReceta(valTipoReceta);
            }

            if (idServicio != null) {
                paramBusquedaReporte.setIdEstructura(idServicio);
            }
            if (idMedico != null) {
                paramBusquedaReporte.setIdMedico(idMedico);
            }

            if (acumulado == Acumulados_Enum.ACUMULADO_CLAVE.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_CLAVE.getValue());
            } else if (acumulado == Acumulados_Enum.ACUMULADO_PACIENTE.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_PACIENTE.getValue());
            } else if (acumulado == Acumulados_Enum.ACUMULADO_MEDICO.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_MEDICO.getValue());
            } else if (acumulado == Acumulados_Enum.ACUMULADO_COLECTIVO.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_COLECTIVO.getValue());
            }

            paramBusquedaReporte.setNuevaBusqueda(true);
            reporteAcumuladosLazy = new ReporteAcumuladosLazy(reporteMovimientosService, paramBusquedaReporte);
            LOGGER.debug("Resultados: {}", reporteAcumuladosLazy.getTotalReg());

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

    public void imprimeReporteMovAcumulados() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {            
            int valTipoReceta = 0;
            if (paramBusquedaReporte.getTipoReceta() != null) {
                if (paramBusquedaReporte.getTipoReceta().equals("E") || paramBusquedaReporte.getTipoReceta().equals("F") || paramBusquedaReporte.getTipoReceta().equals("N")) {
                    valTipoReceta = 1;
                    paramBusquedaReporte.setValTipoReceta(valTipoReceta);
                    if (paramBusquedaReporte.getTipoReceta().equals("N")) {
                        paramBusquedaReporte.setTipoReceta(null);
                    }
                } else {
                    paramBusquedaReporte.setValTipoReceta(valTipoReceta);
                }

            } else {
                paramBusquedaReporte.setTipoReceta(null);
                paramBusquedaReporte.setValTipoReceta(valTipoReceta);
            }

            if (idServicio != null) {
                paramBusquedaReporte.setIdEstructura(idServicio);
            }
            if (idMedico != null) {
                paramBusquedaReporte.setIdMedico(idMedico);
            }

            if (acumulado == Acumulados_Enum.ACUMULADO_CLAVE.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_CLAVE.getValue());
            } else if (acumulado == Acumulados_Enum.ACUMULADO_PACIENTE.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_PACIENTE.getValue());
            } else if (acumulado == Acumulados_Enum.ACUMULADO_MEDICO.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_MEDICO.getValue());
            } else if (acumulado == Acumulados_Enum.ACUMULADO_COLECTIVO.getValue()) {
                paramBusquedaReporte.setTipoAcumulado(Acumulados_Enum.ACUMULADO_COLECTIVO.getValue());
            }
            Estructura est;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            byte[] buffer = reportesService.imprimeReporteAcumulados(paramBusquedaReporte, entidad);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repAcumulados_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    public void generaExcelGeneral() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {

            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            this.paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);

            Estructura est;
            if (this.paramBusquedaReporte.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(paramBusquedaReporte.getIdEstructura());
            }

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            boolean res = reportesService.imprimeExcelGeneral(this.paramBusquedaReporte, entidad);
            if (res) {
                status = Constantes.ACTIVO;
            }

        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public List<DataResultReport> getListDataResultReport() {
        return listDataResultReporteAcumulado;
    }

    public void setListDataResaultReport(List<DataResultReport> listDataResultReporteAcumulado) {
        this.listDataResultReporteAcumulado = listDataResultReporteAcumulado;
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

    public ReporteAcumuladosLazy getReporteAcumuladosLazy() {
        return reporteAcumuladosLazy;
    }

    public void setReporteAcumuladosLazy(ReporteAcumuladosLazy reporteAcumuladosLazy) {
        this.reporteAcumuladosLazy = reporteAcumuladosLazy;
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

    public Integer getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(Integer acumulado) {
        this.acumulado = acumulado;
    }

    public List<Usuario> getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(List<Usuario> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public List<Estructura> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<Estructura> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public boolean isActivaCamposReporteAcumulados() {
        return activaCamposReporteAcumulados;
    }

    public void setActivaCamposReporteAcumulados(boolean activaCamposReporteAcumulados) {
        this.activaCamposReporteAcumulados = activaCamposReporteAcumulados;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
}
