package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import mx.mc.lazy.ReporteLibroControladosLazy;
import mx.mc.enums.SubCategoriaMedicamento_Enum;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReporteLibroControlados;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.TipoMovimiento;
import mx.mc.model.Usuario;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReportesService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.TipoMovimientoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author aortiz
 *
 */
@Controller
@Scope(value = "view")
public class LibControlMedControladosMB extends LazyDataModel<ReporteLibroControlados> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LibControlMedControladosMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private ParamLibMedControlados paramBusquedaReporte;
    private List<ReporteLibroControlados> listaControlados;
    private List<TipoMovimiento> listTipoMovimiento;
    private int idTipoMovimiento;
    private List<Integer> tipoMovimientoSelects;
    private Date fechaActual;
    private Usuario usuarioSession;
    private boolean administrador;
    private String pathPdf;
    private List<Estructura> listAlmacenesSubAlm;
    private List<Estructura> listaAuxiliar;
    private String idEstructura;
    private Estructura estructuraUsuario;
    private ReporteLibroControladosLazy reporteLibroControladosLazy;
    private PermisoUsuario permiso;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient TipoMovimientoService tipoMovimientoService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient PrescripcionService prescripcionService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;

    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            this.administrador = Comunes.isAdministrador();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            fechaActual = FechaUtil.obtenerFechaFin();
            this.listTipoMovimiento = tipoMovimientoService.obtenerLista(new TipoMovimiento());
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.LIBRMEDICAMECONTROL.getSufijo());
            cargarDatosComboAlmacen();
            obtenerMedicamentosControlados();
            obtenerLotesPorMedicamento();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    private void cargarDatosComboAlmacen() {
        try {
            List<Integer> listTipoAlmacen = new ArrayList<>();
            listTipoAlmacen.add(TipoAlmacen_Enum.FARMACIA.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.ALMACEN.getValue());
            listTipoAlmacen.add(TipoAlmacen_Enum.SUBALMACEN.getValue());
            if (this.administrador) {
                this.listaAuxiliar = estructuraService.getEstructuraByLisTipoAlmacen(listTipoAlmacen);
                this.listAlmacenesSubAlm.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
            } else {
                this.estructuraUsuario = estructuraService.obtenerEstructura(this.usuarioSession.getIdEstructura());
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.listAlmacenesSubAlm = new ArrayList<>();
        this.paramBusquedaReporte = new ParamLibMedControlados();
        this.paramBusquedaReporte.setFechaInicio(FechaUtil.obtenerFechaInicio());
        this.paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFin());
    }

    /**
     *
     * @param estrucPrincipal
     */
    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        listAlmacenesSubAlm.add(listaAuxiliar.get(i));
                        ordenarListaEstructura(listaAuxiliar.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }

    public void obtenerFechaFinal() {
        paramBusquedaReporte.setFechaFin(FechaUtil.obtenerFechaFinal(paramBusquedaReporte.getFechaFin()));
    }
    
    /**
     * Metodo para realizar la consulta desde la vista
     *
     */
    public void consultar() {
        try {
            this.paramBusquedaReporte.setNuevaBusqueda(true);
            this.paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);
            if (!this.administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(this.idEstructura);
            }
            List<Integer> listaSubCategorias = new ArrayList<>();
            listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G1.getValue());
            listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G2.getValue());
            listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G3.getValue());
            this.paramBusquedaReporte.setListaSubCategorias(listaSubCategorias);
            this.reporteLibroControladosLazy = new ReporteLibroControladosLazy(
                    this.prescripcionService, this.paramBusquedaReporte);
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
            LOGGER.error("Ocurrio un error al consultar el medicamento: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.err.autocomplete"), null);
        }
        return insumosList;
    }

    public void obtenerMedicamentosControlados() {
        List<Integer> listaSubCategorias = new ArrayList<>();
        listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G1.getValue());
        listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G2.getValue());
        listaSubCategorias.add(SubCategoriaMedicamento_Enum.CONTROLADA_G3.getValue());
        try {
            List<Medicamento> listaMedControlados = this.medicamentoService.obtenerMedicamentosControlados(listaSubCategorias);
            this.paramBusquedaReporte.setListInsumos(listaMedControlados);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error en el metodo :: obtenerMedicamentosControlados");
        }
    }

    public void obtenerLotesPorMedicamento() {
        try {
            List<Inventario> listaInventarios = null;
            if (this.paramBusquedaReporte.getIdMedicamento() == null) {
                listaInventarios = this.inventarioService.obtenerLotesPorMedicamento(
                        this.paramBusquedaReporte.getListInsumos().get(0).getIdMedicamento());
            } else {
                listaInventarios = this.inventarioService.obtenerLotesPorMedicamento(
                        this.paramBusquedaReporte.getIdMedicamento());
            }
            this.paramBusquedaReporte.setListaInventarios(listaInventarios);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error en el metodo :: obtenerLotesPorMedicamentoYEstructura");
        }
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

    public void imprimeReporte() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            EntidadHospitalaria entidadHospitalaria = this.entidadHospitalariaService.
                    obtener(new EntidadHospitalaria());
            byte[] buffer = reportesService.imprimeReporteLibroControlados(paramBusquedaReporte, entidadHospitalaria);
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("repLibroControlados_%s.pdf", FechaUtil.formatoFecha(new Date(), "yyyyMMdd")));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void generaExcelGeneral(){
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {

            if (!administrador) {
                this.paramBusquedaReporte.setIdEstructura(usuarioSession.getIdEstructura());
            } else {
                this.paramBusquedaReporte.setIdEstructura(idEstructura);
            }
            this.paramBusquedaReporte.setIdTipoMovimientos(this.tipoMovimientoSelects);
        } catch (Exception e) {
            LOGGER.error("Error al generar Excel: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void onRowEdit(RowEditEvent event) {
        ReporteLibroControlados registroSeleccionado = (ReporteLibroControlados) event.getObject();
        if (registroSeleccionado.getIdReabastoInsumo() == null || registroSeleccionado.getIdReabastoInsumo().isEmpty()) {
            firmarSurtimientoInsumo(registroSeleccionado);
        } else {
            firmarReabastoInsumo(registroSeleccionado);
        }
        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La observacion no puede estar vacia.", null);
    }

    private void firmarSurtimientoInsumo(ReporteLibroControlados registroSeleccionado) {
        try {
            if (registroSeleccionado.getObservaciones() == null
                    || registroSeleccionado.getObservaciones().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La observacion no puede estar vacia.", null);
                return;
            }
            SurtimientoInsumo parametro = new SurtimientoInsumo();
            parametro.setIdSurtimientoInsumo(registroSeleccionado.getIdSurtimientoInsumo());
            SurtimientoInsumo surtimientoInsumo = surtimientoInsumoService.obtener(parametro);
            if (surtimientoInsumo != null) {
                if (surtimientoInsumo.getFirmaControlados() == null || surtimientoInsumo.getFirmaControlados().isEmpty()) {
                    surtimientoInsumo = new SurtimientoInsumo();
                    surtimientoInsumo.setIdSurtimientoInsumo(registroSeleccionado.getIdSurtimientoInsumo());
                    surtimientoInsumo.setNotas(registroSeleccionado.getObservaciones());
                    surtimientoInsumo.setFirmaControlados(this.usuarioSession.getIdUsuario());
                    this.surtimientoInsumoService.actualizar(surtimientoInsumo);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La observacion ya fue capturada no es posible modificarla.", null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ocurrio un error al realizar la operacion.", null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo :: onRowEdit: {}", ex.getMessage());
        }
    }

    private void firmarReabastoInsumo(ReporteLibroControlados registroSeleccionado) {
        try {
            if (registroSeleccionado.getObservaciones() == null
                    || registroSeleccionado.getObservaciones().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La observacion no puede estar vacia.", null);
                return;
            }
            ReabastoInsumo parametro = new ReabastoInsumo();
            parametro.setIdReabastoInsumo(registroSeleccionado.getIdReabastoInsumo());
            ReabastoInsumo reabastoInsumo = reabastoInsumoService.obtener(parametro);
            if (reabastoInsumo != null) {
                if (reabastoInsumo.getFirmaControlados() == null || reabastoInsumo.getFirmaControlados().isEmpty()) {
                    reabastoInsumo = new ReabastoInsumo();
                    reabastoInsumo.setIdReabastoInsumo(registroSeleccionado.getIdReabastoInsumo());
                    reabastoInsumo.setObservaciones(registroSeleccionado.getObservaciones());
                    reabastoInsumo.setFirmaControlados(this.usuarioSession.getIdUsuario());
                    reabastoInsumoService.actualizar(reabastoInsumo);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La observacion ya fue capturada no es posible modificarla.", null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ocurrio un error al realizar la operacion.", null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo :: onRowEdit: {}", ex.getMessage());
        }
    }

    public List<TipoMovimiento> getListTipoMovimiento() {
        return listTipoMovimiento;
    }

    public void setListTipoMovimiento(List<TipoMovimiento> listTipoMovimiento) {
        this.listTipoMovimiento = listTipoMovimiento;
    }

    public ParamLibMedControlados getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamLibMedControlados paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
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

    public ReporteLibroControladosLazy getReporteLibroControladosLazy() {
        return reporteLibroControladosLazy;
    }

    public void setReporteLibroControladosLazy(ReporteLibroControladosLazy reporteLibroControladosLazy) {
        this.reporteLibroControladosLazy = reporteLibroControladosLazy;
    }

    public List<ReporteLibroControlados> getListaControlados() {
        return listaControlados;
    }

    public void setListaControlados(List<ReporteLibroControlados> listaControlados) {
        this.listaControlados = listaControlados;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
