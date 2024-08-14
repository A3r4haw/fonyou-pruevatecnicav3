package mx.mc.magedbean;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import mx.mc.dto.ValidacionNoCumplidaDTO;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estabilidad;
import mx.mc.model.Estructura;
import mx.mc.model.Fabricante;
import mx.mc.model.FabricanteInsumo;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Proveedor;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Usuario;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstabilidadService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FabricanteService;
import mx.mc.service.FabricanteInsumoService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ProveedorService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoService;
import mx.mc.service.ReportesService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.primefaces.event.SelectEvent;

/**
 * @author AORTIZ
 */
@Controller
@Scope(value = "view")
public class RecepcionManualMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionManualMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final ResourceBundle RESOURCESMESSAGE = ResourceBundle.getBundle(Constantes.GLOBAL_PARAM);

    private transient List<Estructura> listaEstructuras;
    private transient List<ReabastoExtended> listaReabasto;
    private transient List<ReabastoInsumoExtended> listaReabastoInsumo;
    private ReabastoExtended reabastoSelect;
    private String idEstructura;
    private String textoBusqueda;
    private Usuario usuarioSession;
    private String codigoBarras;
    private boolean eliminarCodigo;
    private boolean administrador;
    private String xcantidad;
    private String loteRecibido;
    private Date caducidadRecibida;

    private String almacenOrigen;
    private String almacenDestino;
    private Estructura estructuraSelect;
    private transient List<Estructura> estructuraList;
    private transient List<Estructura> listaAuxiliar;
    private String estrErrPermisos;
    private String errorSurtir;
    private String surtimientoErrorOperacion;
    private String orepcioErrMedNoEncontrado;
    private String formatDate;
    private PermisoUsuario permiso;
    private boolean surtirProveedor;
    private SesionMB sesion;
    private transient List<ReabastoInsumoExtended> listaReabastoInsumoNews;
    private transient List<ReabastoInsumo> listaReabastoInsumoAgreg;
    private transient List<ReabastoInsumoExtended> skuSapList;
    private ReabastoInsumoExtended skuSap;
    private boolean activaAutoCompleteInsumos;
    private boolean recepcionManualPrellenada;
    private transient List<Proveedor> listaProveedor;
    private transient List<Fabricante> listaFabricante;
    private transient List<FabricanteInsumo> listaFabricanteInsumo;

    @Autowired
    private transient ReabastoService reabastoService;

    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient FabricanteService fabricanteService;

    @Autowired
    private transient FabricanteInsumoService fabricanteInsumoService;

    @Autowired
    private transient ProveedorService proveedorService;

    @Autowired
    private transient EstabilidadService estabilidadService;

    private String archivo;
    boolean eventSeleccionar;
    boolean codigoGS1;
    boolean codigoQR;
    private Date fechaActual;
    private boolean capturaLoteCaducidadManual;
    private boolean moduloCentralMezclas;
    private transient List<ValidacionNoCumplidaDTO> listaValidacion;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            estrErrPermisos = "estr.err.permisos";
            errorSurtir = "errorSurtir";
            surtimientoErrorOperacion = "surtimiento.error.operacion";
            orepcioErrMedNoEncontrado = "orepcio.err.medNoEncontrado";
            formatDate = "yyyy-MM-dd";
            initialize();
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.RECEPCIONMANUAL.getSufijo());
            validarUsuarioAdministrador();
            alimentarComboAlmacen();
            obtenerOrdenesReabasto();
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            if (sesion.getSurtimientoProveedorAutomatico().equals(1)) {
                this.surtirProveedor = true;
            }
            moduloCentralMezclas = sesion.isModuloCentralMezclas();
            capturaLoteCaducidadManual = sesion.isCapturaLoteCaducidadManual();

            activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
            recepcionManualPrellenada = (sesion.getRecepcionManualPrellenada() == 1);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.listaEstructuras = new ArrayList<>();
        this.listaReabasto = new ArrayList<>();
        this.listaReabastoInsumo = new ArrayList<>();
        this.listaReabastoInsumoNews = new ArrayList<>();
        this.reabastoSelect = new ReabastoExtended();
        this.idEstructura = "0";
        this.textoBusqueda = "";
        this.usuarioSession = new Usuario();
        this.codigoBarras = "";
        this.eliminarCodigo = false;
        this.administrador = false;
        this.surtirProveedor = false;
        this.xcantidad = "1";
        this.fechaActual = new java.util.Date();
        eventSeleccionar = false;
        codigoGS1 = false;
        codigoQR = false;
        skuSap = new ReabastoInsumoExtended();

        this.listaProveedor = obtenerListaProveedor();
        this.listaFabricante = obtenerListaFabricante();
        this.listaValidacion = new ArrayList<>();
    }

    public void limpiar() {
        this.xcantidad = "1";
        this.codigoBarras = "";
        this.eliminarCodigo = false;
        eventSeleccionar = false;
        codigoGS1 = false;
        codigoQR = false;
    }

    private void validarUsuarioAdministrador() {
        try {
            this.administrador = Comunes.isAdministrador();
            if (!this.administrador) {
                Estructura estPadre = estructuraService.getEstructuraPadreIdEstructura(usuarioSession.getIdEstructura());
                this.idEstructura = estPadre.getIdEstructura();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para poblar el combo almacen
     */
    public void alimentarComboAlmacen() {
        try {

            Estructura est = new Estructura();
            est.setActiva(Constantes.ESTATUS_ACTIVO);

            if (!this.administrador) {
                est.setIdEstructura(usuarioSession.getIdEstructura());
                this.listaEstructuras = this.estructuraService.obtenerLista(est);
                this.idEstructura = listaEstructuras.get(0).getIdEstructura();
            } else {
                est.setIdTipoAreaEstructura(Constantes.TIPO_AREA_ALMACEN);
                this.listaAuxiliar = this.estructuraService.obtenerLista(est);
                this.idEstructura = listaAuxiliar.get(0).getIdEstructura();
                this.listaEstructuras.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null
                            && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        listaEstructuras.add(listaAuxiliar.get(i));
                        ordenarListaEstructura(listaAuxiliar.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }

    /**
     * Metodo utilizado para llenar la tabla de Ordenes de Reabasto que se
     * muestra en pantalla
     */
    public void obtenerOrdenesReabasto() {
        try {
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            Reabasto reabasto = new Reabasto();
            reabasto.setIdEstructura(this.idEstructura);
            Integer idTipoAlmacen = null;
            estructuraSelect = estructuraService.obtenerEstructura(idEstructura);
            this.listaReabasto = this.reabastoService.obtenerReabastoExtends(reabasto, listEstatusReabasto, idTipoAlmacen);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarTablaOrdenesReabasto :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para cambiar el estatus de una orden a cancelado
     *
     * @param idReabasto
     */
    public void cancelarOrdenReabasto(String idReabasto) {
        try {
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), "");
                return;
            }
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            Reabasto reabasto = new Reabasto();
            reabasto = reabastoService.obtenerReabastoPorID(idReabasto);
            reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.CANCELADA.getValue());
            List<ReabastoInsumoExtended> listReabastoInsumo = reabastoInsumoService.obtenerReabastoInsumoProveedorFarmacia(
                    idReabasto, idEstructura, listEstatusReabasto);
            boolean resp = reabastoService.cancelarReabastoyReabastoInsumo(reabasto, listReabastoInsumo);
            if (resp) {
                obtenerOrdenesReabasto();
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("surtimiento.ok.cancelar"), "");
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cancelar"), "");
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo cancelarOrdenReabasto :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que se utiliza para mostrar el detalle de la orden de surtimiento
     */
    public void mostrarModalSurtimiento() {
        try {
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listaReabastoInsumoNews = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            this.listaReabastoInsumo = reabastoInsumoService.
                    obtenerReabastoInsumoProveedorFarmacia(
                            this.reabastoSelect.getIdReabasto(),
                            this.idEstructura, listEstatusReabasto);
            if (recepcionManualPrellenada) {
                for (ReabastoInsumoExtended rie : listaReabastoInsumo) {
                    ReabastoEnviadoExtended detalleReabasto = new ReabastoEnviadoExtended();
                    detalleReabasto.setClaveInstitucional(rie.getClaveInstitucional());
                    detalleReabasto.setIdEstructura(this.idEstructura);
                    detalleReabasto.setIdMedicamento(rie.getIdInsumo());
                    detalleReabasto.setLote(Constantes.LOTE_GENERICO);
                    detalleReabasto.setFechaCaducidad(FechaUtil.formatoFecha(formatDate, Constantes.CADUCIDAD_GENERICA));
                    if (rie.getIdProveedor() != null) {
                        detalleReabasto.setIdProveedor(rie.getIdProveedor());
                    }
                    Medicamento m = medicamentoService.obtenerMedicamento(rie.getIdInsumo());
                    if (m != null) {
                        if (m.getOsmolaridad() != null) {
                            detalleReabasto.setOsmolaridad(m.getOsmolaridad());
                        }
                        if (m.getDensidad() != null) {
                            detalleReabasto.setDensidad(m.getDensidad());
                        }
                        if (m.getCalorias() != null) {
                            detalleReabasto.setCalorias(m.getCalorias());
                        }
                        if (m.getNoHorasEstabilidad() != null) {
                            detalleReabasto.setNoHorasEstabilidad(m.getNoHorasEstabilidad());
                        }
                    }
                    Integer cantidadXcaja = 1;
                    detalleReabasto.setCantidadXCaja(cantidadXcaja);
                    detalleReabasto.setCantidadEnviado(rie.getCantidadSolicitada());
                    detalleReabasto.setIdReabastoInsumo(rie.getIdReabastoInsumo());
                    rie.setCantidadSurtida(rie.getCantidadSolicitada());
                    List<ReabastoEnviadoExtended> detalleReabastoLista = new ArrayList<>();
                    detalleReabastoLista.add(detalleReabasto);
                    rie.setListaDetalleReabIns(detalleReabastoLista);
                }
            }
            this.almacenOrigen = listaReabastoInsumo.get(0).getNombreProveedor();
            this.almacenDestino = listaReabastoInsumo.get(0).getAlmacen();

        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalSurtimiento :: {}", e.getMessage());
        }
    }

    /**
     *
     */
    public void surtirReabastoInsumo() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), "");
                return;
            }
            int registrosSurtir = 0;
            //Validar que los surtimientos no sean cero o el medicamento no este bloqueado
            for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                if (item.getActivo() == Constantes.ESTATUS_ACTIVO && item.getCantidadSurtida() < 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantCero"), "");
                    return;
                }
                if (item.getActivo() == Constantes.ESTATUS_INACTIVO && item.getCantidadSurtida() > 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medInactivo"), "");
                    return;
                }
                if (item.getCantidadSurtida() > 0) {
                    registrosSurtir++;
                }
            }
            if (registrosSurtir == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimineto.error.surtirUno"), "");
                return;
            }
            Reabasto reabasto = new Reabasto();
            reabasto.setIdReabasto(this.reabastoSelect.getIdReabasto());
            reabasto.setFechaSurtida(new Date());
            reabasto.setIdUsuarioSurtida(this.usuarioSession.getIdUsuario());
            reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
            reabasto.setIdProveedor(this.reabastoSelect.getIdProveedor());
            reabasto.setUpdateFecha(new Date());
            reabasto.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                item.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
            }
            boolean resp = this.reabastoService.
                    surtirOrdenReabastoProveedorFarmacia(
                            reabasto, this.listaReabastoInsumo,
                            this.usuarioSession.getIdUsuario());
            if (resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("surtimiento.ok.surtimiento"), "");
                obtenerOrdenesReabasto();
                PrimeFaces.current().ajax().addCallbackParam(errorSurtir, resp);
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
            LOGGER.error("Error en el metodo surtirReabastoInsumo :: {}", e.getMessage());
        }
    }

    public void validarRecepcion() {
        LOGGER.trace("mx.mc.magedbean.RecepcionManualMB.validarRecepcion()");
        boolean resp = false;

        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), "");

            } else if (reabastoSelect == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Orden de reabasto inválida.", "");

            } else if (listaReabastoInsumo == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Orden de reabasto con insumos inválidos.", "");

            } else if (listaReabastoInsumo.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Orden de reabasto con insumos inválidos.", "");

            } else if (reabastoSelect.getIdEstructura() == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Almacén o área de reabasto inválida.", "");

            } else {
                int registrosSurtir = 0;
                Estructura unaEstructura = estructuraService.obtenerEstructura(reabastoSelect.getIdEstructura());
                Integer tipoAlmacen = unaEstructura.getIdTipoAlmacen();
                //Validar que los surtimientos no sean cero o el medicamento no este bloqueado
                boolean error = false;
                listaValidacion = new ArrayList<>();
                ValidacionNoCumplidaDTO v = new ValidacionNoCumplidaDTO();
                for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                    
                    if (item.getActivo() == Constantes.ESTATUS_ACTIVO && item.getCantidadSurtida() < 0) {
                        v = new ValidacionNoCumplidaDTO();
                        v.setCodigo(item.getNombreCorto());
                        v.setDescripcion(RESOURCES.getString("surtimiento.error.cantCero"));
                        listaValidacion.add(v);
                        error = true;
                    }
                    if (item.getActivo() == Constantes.ESTATUS_INACTIVO && item.getCantidadSurtida() > 0) {
                        v = new ValidacionNoCumplidaDTO();
                        v.setCodigo(item.getNombreCorto());
                        v.setDescripcion(RESOURCES.getString("surtimiento.error.medInactivo"));
                        v.setMandatoria("Si");
                        listaValidacion.add(v);
                        error = true;
                    }

                    if (item.getCantidadSurtida() > 0) {
                        registrosSurtir++;
                    }

                    if (item.getListaDetalleReabIns() != null) {
                        for (ReabastoEnviadoExtended itemDetalle : item.getListaDetalleReabIns()) {
                            String idInsumo = item.getIdInsumo();
                            String idEstructura = reabastoSelect.getIdEstructura();
                            String lote = itemDetalle.getLote();
                            Integer cantidadXCaja = 1;
                            String claveProveedor = "";
                            
                            Inventario i = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
                                    idInsumo, idEstructura, lote, cantidadXCaja, claveProveedor);
                            
                            if (i != null) {
                                String fechaCad = "";
                                if (itemDetalle.getFechaCaducidad() != null) {
                                    fechaCad = FechaUtil.formatoFecha(itemDetalle.getFechaCaducidad(), "dd/MM/yyy");
                                }
                                String fechaCad2 = "";
                                if (i.getFechaCaducidad() != null) {
                                    fechaCad2 = FechaUtil.formatoFecha(i.getFechaCaducidad(), "dd/MM/yyy");
                                }
                                if (!fechaCad.equals(fechaCad2)) {
                                    v = new ValidacionNoCumplidaDTO();
                                    v.setCodigo(item.getNombreCorto());
                                    v.setDescripcion("Lote: " + itemDetalle.getLote() + " con fecha " + fechaCad + ", difiere la fecha de caducidad previamente registrada: " + fechaCad2);
                                    v.setMandatoria("Si");
                                    listaValidacion.add(v);
                                    error = true;
                                }

                                if (itemDetalle.getIdFabricante() == null) {
                                    v = new ValidacionNoCumplidaDTO();
                                    v.setCodigo(item.getNombreCorto());
                                    v.setDescripcion("No seleccionó Fabricante.");
                                    listaValidacion.add(v);
                                }
                                
                                if (!Objects.equals(itemDetalle.getIdFabricante(), i.getIdFabricante())) {
                                    String nombreF1 = "";
                                    Fabricante f1 = fabricanteService.obtener(new Fabricante(itemDetalle.getIdFabricante()));
                                    if (f1 != null) {
                                        nombreF1 = f1.getNombreFabricante();
                                    }
                                    String nombreF2 = " Ninguno ";
                                    if (i.getIdFabricante() != null){
                                        Fabricante f2 = fabricanteService.obtener(new Fabricante(i.getIdFabricante()));
                                        if (f2 != null) {
                                            nombreF2 = f2.getNombreFabricante();
                                        }
                                    }
                                    
                                    v = new ValidacionNoCumplidaDTO();
                                    v.setCodigo(item.getNombreCorto());
                                    v.setDescripcion("Fabricante seleccionado : " + nombreF1 + ", difiere del Fabricante previamente registrado: " + nombreF2 + ", del mismo medicamento y mismo lote.");
                                    v.setMandatoria("Si");
                                    listaValidacion.add(v);
                                }
                                
//                                Mensaje.showMessage(Constantes.MENSAJE_FATAL, "No puede existir un insumo con el mismo lote y diferente caducidad.", "");
//                                Mensaje.showMessage(Constantes.MENSAJE_FATAL, "No puede registrar un insumo con el mismo lote, misma caducidad y diferente fabricante.", "");
                            }
                        }
                    }

                }

                if (registrosSurtir == 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimineto.error.surtirUno"), "");

                } else if (!error) {
                    resp = true;

                }
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
            LOGGER.error("Error en el metodo surtirReabastoInsumo :: {}", e.getMessage());
        }

        PrimeFaces.current().ajax().addCallbackParam(errorSurtir, resp);
    }

    /**
     * GCR 30/05/2019 Metodo utilizado para surtimiento de proveedor automatico,
     * para dejarlo en estatus recibido listo para ser ingresado a inventario
     */
    public void surtirReabastoProveedor() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), "");
                return;
            }
            int registrosSurtir = 0;
            Estructura unaEstructura = estructuraService.obtenerEstructura(reabastoSelect.getIdEstructura());
            Integer tipoAlmacen = unaEstructura.getIdTipoAlmacen();
            //Validar que los surtimientos no sean cero o el medicamento no este bloqueado
            for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                if (item.getActivo() == Constantes.ESTATUS_ACTIVO
                        && item.getCantidadSurtida() < 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantCero"), "");
                    return;
                }
                if (item.getActivo() == Constantes.ESTATUS_INACTIVO
                        && item.getCantidadSurtida() > 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medInactivo"), "");
                    return;
                }
                if (item.getCantidadSurtida() > 0) {
                    registrosSurtir++;
                }
            }
            if (registrosSurtir == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimineto.error.surtirUno"), "");
                return;
            }
            //Se crea objeto de reabasto y se actualizan valores para quedar en estatus recibido
            Reabasto reabasto = new Reabasto();
            reabasto.setIdReabasto(this.reabastoSelect.getIdReabasto());
            reabasto.setFechaSurtida(new Date());
            reabasto.setIdUsuarioSurtida(this.usuarioSession.getIdUsuario());
            reabasto.setFechaRecepcion(new Date());
            reabasto.setIdUsuarioRecepcion(this.usuarioSession.getIdUsuario());
            reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.RECIBIDA.getValue());
            reabasto.setIdProveedor(this.reabastoSelect.getIdProveedor());
            reabasto.setUpdateFecha(new Date());
            reabasto.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            reabasto.setIdEstructura(this.reabastoSelect.getIdEstructura()); //GCR.- Se coloca idEstructura para que no se busque mas adelante para puntos de control
            List<ReabastoEnviado> listReabastoEnviado = new ArrayList<>();
            ReabastoEnviado unReabastoEnviado = null;
            for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                //Se actualizan valores de reabastoInsumo
                item.setIdEstatusReabasto(EstatusReabasto_Enum.RECIBIDA.getValue());
                item.setCantidadComprometida(item.getCantidadSurtida());
                item.setCantidadRecibida(item.getCantidadSurtida());
                //  Solo se ingresa en ingreso de Insumos
                item.setUpdateFecha(new Date());
                item.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                if (item.getListaDetalleReabIns() != null) {
                    for (ReabastoEnviadoExtended itemDetalle : item.getListaDetalleReabIns()) {
                        //Se crea objeto de reabastoEnviado y se llenan valores para quedar en estatus recibido
                        unReabastoEnviado = new ReabastoEnviado();
                        unReabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                        unReabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                        unReabastoEnviado.setCantidadEnviado(itemDetalle.getCantidadEnviado());
                        unReabastoEnviado.setCantidadRecibida(itemDetalle.getCantidadEnviado());
                        //  Solo se ingresa en ingreso de Insumos
                        unReabastoEnviado.setIdEstatusReabasto(EstatusReabasto_Enum.RECIBIDA.getValue());
                        unReabastoEnviado.setIdInsumo(item.getIdInsumo());
                        unReabastoEnviado.setLoteEnv(itemDetalle.getLote());
                        unReabastoEnviado.setFechaCad(itemDetalle.getFechaCaducidad());
                        unReabastoEnviado.setCantidadXCaja(item.getCantidadXCaja());
                        unReabastoEnviado.setIdEstructura(this.reabastoSelect.getIdProveedor());// Se pone la estructura del proveedor debido a que es automatico
                        unReabastoEnviado.setPresentacionComercial((tipoAlmacen == 2 ? 1 : 0));
                        unReabastoEnviado.setInsertFecha(new Date());
                        unReabastoEnviado.setInsertIdUsuario(this.usuarioSession.getIdUsuario());

                        unReabastoEnviado.setOsmolaridad(itemDetalle.getOsmolaridad());
                        unReabastoEnviado.setDensidad(itemDetalle.getDensidad());
                        unReabastoEnviado.setCalorias(itemDetalle.getCalorias());
                        unReabastoEnviado.setNoHorasEstabilidad(itemDetalle.getNoHorasEstabilidad());
                        unReabastoEnviado.setIdFabricante(itemDetalle.getIdFabricante());
                        unReabastoEnviado.setIdProveedor(itemDetalle.getIdProveedor());
                        unReabastoEnviado.setClaveProveedor(itemDetalle.getClaveProveedor());
                        unReabastoEnviado.setNoRegistro(itemDetalle.getNoRegistro());

                        listReabastoEnviado.add(unReabastoEnviado);
                    }
                }
            }
            if (listaReabastoInsumoNews != null) {
                listaReabastoInsumoAgreg = new ArrayList<>();
                for (ReabastoInsumoExtended item : listaReabastoInsumoNews) {

                    ReabastoInsumo reabastoInsumoAgregar = new ReabastoInsumo();
                    reabastoInsumoAgregar.setIdReabasto(item.getIdReabasto());
                    reabastoInsumoAgregar.setIdReabastoInsumo(item.getIdReabastoInsumo());
                    reabastoInsumoAgregar.setIdInsumo(item.getIdInsumo());
                    reabastoInsumoAgregar.setCantidadSolicitada(0);
                    reabastoInsumoAgregar.setInsertFecha(new java.util.Date());
                    reabastoInsumoAgregar.setCantidadComprometida(0);
                    reabastoInsumoAgregar.setCantidadSurtida(item.getCantidadSurtida());
                    reabastoInsumoAgregar.setCantidadRecibida(item.getCantidadRecibida());
                    reabastoInsumoAgregar.setIdEstatusReabasto(item.getIdEstatusReabasto());
                    reabastoInsumoAgregar.setInsertIdUsuario(item.getInsertIdUsuario());
                    listaReabastoInsumoAgreg.add(reabastoInsumoAgregar);
                }
            }

            boolean resp = this.reabastoService.surtirReabastoProveedorAutomatico(reabasto, listaReabastoInsumo, listaReabastoInsumoAgreg, listReabastoEnviado);

            if (resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("surtimiento.ok.surtimiento"), "");
                obtenerOrdenesReabasto();
                PrimeFaces.current().ajax().addCallbackParam(errorSurtir, resp);
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
            LOGGER.error("Error en el metodo surtirReabastoInsumo :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para guardar la informacion sin realizar el surtimiento
     */
    public void guardarRegistros() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), "");
                return;
            }

            Reabasto reabasto = new Reabasto();
            reabasto.setIdReabasto(this.reabastoSelect.getIdReabasto());
            reabasto.setIdEstructura(this.reabastoSelect.getIdEstructura());
            boolean resp = this.reabastoService.
                    guardarOrdenReabasto(reabasto, this.listaReabastoInsumo);
            if (resp) {
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ok.guardar"), "");
                PrimeFaces.current().ajax().addCallbackParam(errorSurtir, resp);
                obtenerOrdenesReabasto();
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
            LOGGER.error("Error en el metodo guardarRegistros :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para buscar solicitudes de acuerdo a un paramentro de
     * busqueda
     */
    public void buscarRegistros() {
        try {

            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            Integer idTipoAlmacen = obtenerIdTipoAlmacen(this.idEstructura);

            Pattern pat = Pattern.compile(Constantes.PATRON_CAD_BUS);
            Matcher mat = pat.matcher(this.textoBusqueda);
            if (mat.matches()) {
                for (ReabastoExtended item : this.listaReabasto) {
                    if (item.getFolio().equalsIgnoreCase(this.textoBusqueda)) {
                        this.reabastoSelect = item;
                        break;
                    }
                }
                if (this.reabastoSelect != null) {
                    List<Integer> listEstatusReabasto = new ArrayList<>();
                    listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
                    this.listaReabasto = reabastoService
                            .obtenerRegistrosPorCriterioDeBusqueda(this.textoBusqueda,
                                    Constantes.REGISTROS_PARA_MOSTRAR,
                                    listEstatusReabasto,
                                    this.idEstructura, null, idTipoAlmacen);
                    this.listaReabastoInsumo = reabastoInsumoService.
                            obtenerReabastoInsumoExtends(
                                    this.reabastoSelect.getIdReabasto(),
                                    listEstatusReabasto);
                    PrimeFaces.current().ajax().addCallbackParam("estatusModal", true);
                }
            } else {
                List<Integer> listEstatusReabasto = new ArrayList<>();
                listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
                this.listaReabasto = reabastoService
                        .obtenerRegistrosPorCriterioDeBusqueda(this.textoBusqueda,
                                Constantes.REGISTROS_PARA_MOSTRAR,
                                listEstatusReabasto,
                                this.idEstructura, null, idTipoAlmacen);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para obtener el id del tipo almacen
     *
     * @param idEstructura
     * @return Integer
     */
    private Integer obtenerIdTipoAlmacen(String idEstructura) {
        Integer idTipoAlmacen = 0;
        try {
            if (!this.administrador) {
                idTipoAlmacen = this.listaEstructuras.get(0).getIdTipoAlmacen();
            } else {
                for (Estructura estructura : this.listaEstructuras) {
                    if (estructura.getIdEstructura().equals(idEstructura)) {
                        idTipoAlmacen = estructura.getIdTipoAlmacen();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
        return idTipoAlmacen;
    }

    /**
     * Método para obtener el medicamento por autoComplete
     *
     * @param cadena
     * @return
     */
    public List<ReabastoInsumoExtended> autocompleteInsumo(String cadena) {
        try {
            this.codigoBarras = cadena;
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            //Separa la cadena por si es un GS1
            String sepGS1[] = cadena.split("\\(");
            if (sepGS1.length > 1) {
                cadena = sepGS1[1].substring(3);
                eventSeleccionar = true;
                codigoGS1 = true;
            } else {
                //Separa la cadena por si es un QR
                String sepQR[] = cadena.split(Constantes.SEPARADOR_CODIGO);
                if (sepQR.length > 1) {
                    cadena = sepQR[0];
                    codigoQR = true;
                    eventSeleccionar = true;
                }
            }
            //Se agrega el parametro de autocomplete por si esta permitido
            this.skuSapList = reabastoInsumoService.
                    obtenerValorReabastoInsumoProveedorFarmacia(this.reabastoSelect.getIdReabasto(),
                            this.idEstructura, listEstatusReabasto, cadena, activaAutoCompleteInsumos);
            if (sesion.isActivaTransformacionClaves()
                    && this.skuSapList.isEmpty()) {
                this.skuSapList = reabastoInsumoService.obtenerListaMaxMinReorCantActual(idEstructura, cadena);
            }
            //Si es un codigo GS1 o QR se envia un click para llamar al metodo de selección
            if (codigoGS1 || codigoQR) {
                String componentId = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getClientId();
                String panel = componentId.replace(":", "\\\\:") + "_panel";
                PrimeFaces.current().executeScript("$('#" + panel + " .ui-autocomplete-item').trigger('click');");
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el Insumo : {}", ex.getMessage());
        }

        return skuSapList;
    }

    public void handleSelectMedicamento(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.RecepcionManualMB.handleSelectMedicamento()");
        ReabastoInsumoExtended reInsumo;
        reInsumo = (ReabastoInsumoExtended) e.getObject();
        String lote = null;
        Date fechaCaducidad = null;
        String claveInstitucional = reInsumo.getIdInventario();
        //Se valida si se ingreso un codigo GS1 o QR
        if (codigoGS1) {
            agregarInsumosPorCodigo();
        } else {
            if (codigoQR) {
                String separQR[] = codigoBarras.split(Constantes.SEPARADOR_CODIGO);
                lote = separQR[1];
                try {
                    String caducidad = separQR[2].substring(4) + "-" + separQR[2].substring(2, 4) + "-" + separQR[2].substring(0, 2);
                    fechaCaducidad = FechaUtil.formatoFecha(formatDate, caducidad);
                } catch (Exception ex) {
                    LOGGER.error("Error al parsear la fecha caducidad: {}", ex.getMessage());
                }
                this.codigoBarras = CodigoBarras.generaCodigoDeBarras(separQR[0], lote, fechaCaducidad, null);
                agregarInsumosPorCodigo();
            } else {
                for (ReabastoInsumoExtended item : this.skuSapList) {
                    if (item.getClaveInstitucional().equalsIgnoreCase(claveInstitucional)) {

                        if (item.getLote() != null) {
                            lote = item.getLote();
                        } else if (capturaLoteCaducidadManual && this.loteRecibido != null) {
                            lote = this.loteRecibido;
                        } else {
                            lote = Constantes.LOTE_GENERICO;
                        }

                        if (item.getFechaCaducidad() != null) {
                            fechaCaducidad = item.getFechaCaducidad();

                        } else if (capturaLoteCaducidadManual && this.caducidadRecibida != null) {
                            LocalDate localDate = FechaUtil.obtenerUltimoDiaMes(this.caducidadRecibida);
                            fechaCaducidad = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

                        } else {
                            try {
                                fechaCaducidad = FechaUtil.formatoFecha(formatDate, Constantes.CADUCIDAD_GENERICA);
                            } catch (Exception ex) {
                                LOGGER.error("Error al parsear la fecha caducidad: {}", ex.getMessage());
                            }
                        }
                        this.codigoBarras = CodigoBarras.generaCodigoDeBarras(item.getClaveInstitucional(), lote, fechaCaducidad, null);
                        agregarInsumosPorCodigo();
                        break;
                    }
                }
            }
        }
    }

    public void handleUnSelectMedicamento() {
        skuSap = new ReabastoInsumoExtended();
    }

    /**
     * Metodo utilizado para crear el sub detalle del medicamento por escaneo de
     * codigo de barras
     */
    public void agregarInsumosPorCodigo() {
        try {
            if (this.codigoBarras.isEmpty()) {
                return;
            }
            boolean existeClave = false;
            //Validación para saber cuando se usará la Transformación de Claves
            //Separar codigo de barras 
            ReabastoEnviadoExtended medicamentoDetalle = tratarCodigoDeBarras(this.codigoBarras);

            if (this.eliminarCodigo) {
                String msjError = eliminarLotePorCodigo(medicamentoDetalle);
                if (msjError.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("surtimiento.ok.eliminar"), "");
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
                }
                this.codigoBarras = "";
                this.xcantidad = "1";
                this.eliminarCodigo = false;
                this.loteRecibido = null;
                this.caducidadRecibida = null;
                codigoGS1 = false;
                codigoQR = false;
                return;
            } else {
                if (sesion.isActivaTransformacionClaves()) {
                    for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                        if (item.getClaveInstitucional().equalsIgnoreCase(medicamentoDetalle.getClaveInstitucional())) {
                            existeClave = true;
                            break;
                        }
                    }

                    if (!existeClave && agregarInsumoNecesarioSurtir(this.codigoBarras)) {
                        boolean resp = true;
                        PrimeFaces.current().ajax().addCallbackParam("formDetalleSurtimiento", resp);
                    }
                }
                //Filtrar registros
                ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoDetalle);
                //Validar si el medicamento esta en la lista
                if (medicamento == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(orepcioErrMedNoEncontrado), "");
                    this.codigoBarras = "";
                    this.xcantidad = "1";

                    codigoGS1 = false;
                    codigoQR = false;
                    return;
                }
                //Validar fecha de caducidad 
                if (medicamentoDetalle.getFechaCaducidad().compareTo(new Date()) < 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medCaducado"), "");
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    codigoGS1 = false;
                    codigoQR = false;
                    return;
                }
                int cantidadXCaja;
                int cantidadSurtida;
                int cantidadEnviada;

                cantidadXCaja = medicamentoDetalle.getCantidadXCaja() != null ? medicamentoDetalle.getCantidadXCaja() : medicamento.getFactorTransformacion();

                if (estructuraSelect.getIdTipoAlmacen() < Constantes.SUBALMACEN) {
                    cantidadSurtida = medicamento.getCantidadSurtida() + (cantidadXCaja * Integer.valueOf(this.xcantidad));
                    cantidadEnviada = cantidadXCaja * Integer.valueOf(this.xcantidad);
                } else {
                    cantidadSurtida = medicamento.getCantidadSurtida() + Integer.valueOf(this.xcantidad);
                    cantidadEnviada = Integer.valueOf(this.xcantidad);
                }

                //Validar si el detalle del meticamento existe
                if (medicamento.getListaDetalleReabIns() == null) {
                    medicamento.setListaDetalleReabIns(new ArrayList<>());
                    if (this.xcantidad.isEmpty()) {
                        this.xcantidad = "1";
                    }

                    if (sesion.isActivaTransformacionClaves() && !existeClave) {
                        int cantidad = Integer.valueOf(this.xcantidad);
                        medicamento.setCantidadSolicitada(cantidad * medicamento.getFactorTransformacion());
                    }

                    if (cantidadSurtida > medicamento.getCantidadSolicitada()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantSolMayor"), "");
                        this.codigoBarras = "";
                        this.xcantidad = "1";
                        codigoGS1 = false;
                        codigoQR = false;
                        return;
                    }
                    medicamento.setCantidadXCaja(cantidadXCaja);
                    medicamentoDetalle.setCantidadXCaja(cantidadXCaja);
                    medicamentoDetalle.setCantidadEnviado(cantidadSurtida);
                    medicamentoDetalle.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                    medicamentoDetalle.setIdMedicamento(medicamento.getIdInsumo());
                    medicamentoDetalle.setIdEstructura(this.idEstructura);
                    medicamentoDetalle.setIdInventarioSurtido(
                            inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                                    medicamento.getIdInsumo(), this.idEstructura,
                                    medicamentoDetalle.getLote()));

                    if (moduloCentralMezclas) {
                        Estabilidad estTmp = new Estabilidad();
                        estTmp.setIdInsumo(medicamento.getIdInsumo());

                        Estabilidad e = estabilidadService.obtener(estTmp);
                        if (e != null) {
                            medicamentoDetalle.setIdFabricante(e.getIdFabricante());
                        }
                    }

                    medicamento.getListaDetalleReabIns().add(medicamentoDetalle);
                    medicamento.setCantidadSurtida(cantidadSurtida);

                    // 2022AGO20 HHRC SE ACTUALIZA LA LISTA PARA INCLUIR DATOS ESPECIFICOS DE MEDICAMENTOS PARA SOLUCIONES
                    for (ReabastoInsumoExtended rie : this.listaReabastoInsumo) {
                        if (rie.getClaveInstitucional().equals(medicamento.getClaveInstitucional())) {
                            rie.setListaDetalleReabIns(medicamento.getListaDetalleReabIns());
                        }
                    }

                } else {
                    if (this.xcantidad.isEmpty()) {
                        this.xcantidad = "1";
                    }
                    if (cantidadSurtida > medicamento.getCantidadSolicitada()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantSolMayor"), "");
                        this.codigoBarras = "";
                        this.xcantidad = "1";
                        codigoGS1 = false;
                        codigoQR = false;
                        return;
                    }
                    for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                        if (item.getLote().equalsIgnoreCase(medicamentoDetalle.getLote())) {
                            item.setCantidadEnviado(item.getCantidadEnviado() + cantidadEnviada);
                            medicamento.setCantidadSurtida(cantidadSurtida);
                            this.codigoBarras = "";
                            this.xcantidad = "1";
                            codigoGS1 = false;
                            codigoQR = false;
                            return;
                        }
                    }
                    medicamento.setCantidadXCaja(cantidadXCaja);
                    medicamentoDetalle.setCantidadXCaja(cantidadXCaja);
                    medicamentoDetalle.setCantidadEnviado(cantidadEnviada);
                    medicamentoDetalle.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                    medicamentoDetalle.setIdMedicamento(medicamento.getIdInsumo());
                    medicamentoDetalle.setIdEstructura(this.idEstructura);
                    medicamentoDetalle.setIdInventarioSurtido(
                            inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                                    medicamento.getIdInsumo(), this.idEstructura,
                                    medicamentoDetalle.getLote()));
                    medicamento.getListaDetalleReabIns().add(medicamentoDetalle);
                    medicamento.setCantidadSurtida(cantidadSurtida);

                    // 2022AGO20 HHRC SE ACTUALIZA LA LISTA PARA INCLUIR DATOS ESPECIFICOS DE MEDICAMENTOS PARA SOLUCIONES
                    for (ReabastoInsumoExtended rie : this.listaReabastoInsumo) {
                        if (rie.getClaveInstitucional().equals(medicamento.getClaveInstitucional())) {
                            rie.setListaDetalleReabIns(medicamento.getListaDetalleReabIns());
                        }
                    }
                }
                this.codigoBarras = "";
                this.xcantidad = "1";
                this.loteRecibido = null;
                this.caducidadRecibida = null;
                codigoGS1 = false;
                codigoQR = false;
            }

        } catch (Exception e) {
            codigoGS1 = false;
            codigoQR = false;
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que valida si existe en la lista el elemento seleccionado
     *
     * @param medicamentoCodigo
     * @return String
     */
    private String validaMedicamentoEscaneadoEliminar(ReabastoEnviadoExtended medicamentoCodigo) {
        String msgError = "";
        for (ReabastoInsumoExtended reabastoInsumo : this.listaReabastoInsumo) {
            if (reabastoInsumo.getClaveInstitucional().contains(medicamentoCodigo.getClaveInstitucional())) {
                for (ReabastoEnviadoExtended reabastoEnviado : reabastoInsumo.getListaDetalleReabIns()) {
                    if (reabastoEnviado.getLote().equalsIgnoreCase(medicamentoCodigo.getLote())
                            && reabastoEnviado.getFechaCaducidad().equals(medicamentoCodigo.getFechaCaducidad())) {
                        msgError = "";
                        return msgError;
                    } else {
                        msgError = RESOURCES.getString(orepcioErrMedNoEncontrado);
                    }
                }
            } else {
                msgError = RESOURCES.getString(orepcioErrMedNoEncontrado);
            }
        }
        return msgError;
    }

    private String eliminarLotePorCodigo(ReabastoEnviadoExtended medicamentoCodigo) {
        String msgError = "";
        try {
            //Se llama metodo para validar si el codigo ingresado existe en la lista a eliminar
            msgError = validaMedicamentoEscaneadoEliminar(medicamentoCodigo);
            if (!msgError.isEmpty()) {
                return msgError;
            }

            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoCodigo);
            if (medicamento == null) {
                msgError = RESOURCES.getString("sur.error.noExisteInsumoEliminar");
                return msgError;
            }
            ReabastoEnviadoExtended medicamentoDetalle = obtenerDetalleMedicamento(
                    medicamento.getListaDetalleReabIns(), medicamentoCodigo);
            if (medicamentoDetalle == null) {
                msgError = RESOURCES.getString("surtimiento.error.noLote");
                return msgError;
            }
            int factorConversion = medicamento.getFactorTransformacion();
            int cantidadSurtida = medicamento.getCantidadSurtida()
                    - factorConversion * Integer.valueOf(this.xcantidad);
            int cantidadEnviado = medicamentoDetalle.getCantidadEnviado()
                    - factorConversion * Integer.valueOf(this.xcantidad);
            if (cantidadEnviado < 0) {
                msgError = RESOURCES.getString("surtimiento.error.cantElimMayor");
                return msgError;
            }
            medicamento.setCantidadSurtida(cantidadSurtida);
            medicamentoDetalle.setCantidadEnviado(cantidadEnviado);
            if (medicamentoDetalle.getCantidadEnviado() == 0) {
                eliminarDetalleByFolio(medicamento.getListaDetalleReabIns(), medicamentoCodigo);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
        }
        return msgError;
    }

    /**
     * Método donde se agrega un insumo que no se tenia contemplado en la
     * solicitud.
     *
     * @param cadena
     * @return
     */
    private boolean agregarInsumoNecesarioSurtir(String cadena) throws Exception {
        CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(cadena);
        boolean respuesta = false;
        String clave = ci.getClave();

        Medicamento_Extended medicamentoNuevo;
        medicamentoNuevo = medicamentoService.obtenerMedicamentoPresentacion(clave);

        if (medicamentoNuevo != null) {
            ReabastoInsumo datosPC = reabastoInsumoService.obtenerMaxMinReorCantActual(idEstructura, medicamentoNuevo.getIdMedicamento());
            if (datosPC != null) {
                ReabastoInsumoExtended reabInsumo = new ReabastoInsumoExtended();
                reabInsumo.setIdReabastoInsumo(Comunes.getUUID());
                reabInsumo.setIdReabasto(reabastoSelect.getIdReabasto());
                reabInsumo.setIdInsumo(medicamentoNuevo.getIdMedicamento());
                reabInsumo.setInsertFecha(new java.util.Date());
                reabInsumo.setCantidadComprometida(0);
                reabInsumo.setCantidadSurtida(0);
                reabInsumo.setCantidadRecibida(0);
                reabInsumo.setIdEstatusReabasto(EstatusReabasto_Enum.SOLICITADA.getValue());
                //no se agregara la cantidadSolicitada, ya que este no paso por ese proceso.
                reabInsumo.setCantidadSolicitada(0);
                reabInsumo.setInsertIdUsuario(usuarioSession.getIdUsuario());
                reabInsumo.setClaveInstitucional(medicamentoNuevo.getClaveInstitucional());
                reabInsumo.setNombreCorto(medicamentoNuevo.getNombreCorto());
                reabInsumo.setNombrePresentacion(medicamentoNuevo.getNombrePresentacion());
                reabInsumo.setFactorTransformacion(medicamentoNuevo.getFactorTransformacion());
                reabInsumo.setActivo(Constantes.ESTATUS_ACTIVO);
                listaReabastoInsumo.add(reabInsumo);

                listaReabastoInsumoNews.add(reabInsumo);
                respuesta = true;
            }
        }
        return respuesta;
    }

    /**
     * Metodo utilizado para obtener el medicamento por la clave
     *
     * @param detalle
     * @return ReabastoInsumoExtended
     */
    private ReabastoInsumoExtended obtenerInsumoPorClaveMedicamento(ReabastoEnviadoExtended detalle) {
        ReabastoInsumoExtended registro = null;
        try {
            for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                if (item.getClaveInstitucional().equalsIgnoreCase(detalle.getClaveInstitucional())) {
                    return item;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerInsumoPorClaveMedicamento :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
        }
        return registro;
    }

    /**
     * Metodo que obtiene el detalle del medicamento por lote
     *
     * @param listaDetalle
     * @param medicamentoCodigo
     * @return ReabastoEnviadoExtended
     */
    private ReabastoEnviadoExtended obtenerDetalleMedicamento(
            List<ReabastoEnviadoExtended> listaDetalle,
            ReabastoEnviadoExtended medicamentoCodigo) {

        ReabastoEnviadoExtended registro = null;
        try {
            if (listaDetalle != null) {
                for (ReabastoEnviadoExtended item : listaDetalle) {
                    if (item.getLote().equalsIgnoreCase(medicamentoCodigo.getLote())) {
                        return item;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerDetalleMedicamento :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
        }
        return registro;
    }

    private void eliminarDetalleByFolio(
            List<ReabastoEnviadoExtended> listaDetalle,
            ReabastoEnviadoExtended medicamentoCodigo) {
        try {
            for (short i = 0; i < listaDetalle.size(); i++) {
                ReabastoEnviadoExtended item = listaDetalle.get(i);
                if (item.getLote().equalsIgnoreCase(medicamentoCodigo.getLote())) {
                    listaDetalle.remove(i);
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo eliminarDetalleByFolio :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
        }
    }

    /**
     * Metodo utilizado para convertir el codigo de barras en un objeto de tipo
     * ReabastoEnviadoExtended
     *
     * @param codigo String
     * @return ReabastoEnviadoExtended
     */
    private ReabastoEnviadoExtended tratarCodigoDeBarras(String codigo) {
        ReabastoEnviadoExtended detalleReabasto = new ReabastoEnviadoExtended();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (this.surtirProveedor && !codigoGS1 && !codigoQR) {
                if (ci != null) {
                    asignarValoresCodigoEscaneado(detalleReabasto, ci);
                } else {
                    String[] parts = codigo.split(Constantes.SEPARADOR_CODIGO);
                    detalleReabasto.setClaveInstitucional(parts[0]);
                    detalleReabasto.setIdMedicamento(parts[0]);
                    detalleReabasto.setLote(Constantes.LOTE_GENERICO);
                    detalleReabasto.setFechaCaducidad(FechaUtil.formatoFecha(formatDate, Constantes.CADUCIDAD_GENERICA));
                    Integer cantidadXcaja = 1;
                    cantidadXcaja = obtenerCantidadXCaja(detalleReabasto, cantidadXcaja);
                    detalleReabasto.setCantidadXCaja(cantidadXcaja);
                }
            } else {
                if (ci != null) {
                    asignarValoresCodigoEscaneado(detalleReabasto, ci);
                } else {
                    ClaveProveedorBarras_Extend claveProveedorBarras = claveProveedorBarrasService.obtenerClave(codigo);
                    if (claveProveedorBarras != null) {
                        detalleReabasto.setIdMedicamento(claveProveedorBarras.getClaveInstitucional());
                        detalleReabasto.setCantidadXCaja(claveProveedorBarras.getCantidadXCaja());
                        detalleReabasto.setLote(claveProveedorBarras.getClaveProveedor());
                        detalleReabasto.setFechaCaducidad(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Valor no valido", null);
                    }
                }
            }
            return detalleReabasto;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return detalleReabasto;
    }

    private void asignarValoresCodigoEscaneado(ReabastoEnviadoExtended detalleReabasto, CodigoInsumo ci) throws Exception {
        detalleReabasto.setIdMedicamento(ci.getClave());
        detalleReabasto.setClaveInstitucional(ci.getClave());
        detalleReabasto.setLote(ci.getLote());
        detalleReabasto.setFechaCaducidad(ci.getFecha());
        // 15ago2022 - HHRC
        // Agregar valores para estabilidad, osmolaridad, fabricante

        Medicamento medicamento = medicamentoService.obtenerMedicaByClave(ci.getClave());
        if (medicamento != null) {
            detalleReabasto.setIdMedicamento(medicamento.getIdMedicamento());
            detalleReabasto.setOsmolaridad((medicamento.getOsmolaridad() != null) ? Double.valueOf(medicamento.getOsmolaridad()) : Double.valueOf(0.0));
            detalleReabasto.setDensidad((medicamento.getDensidad() != null) ? Double.valueOf(medicamento.getDensidad()) : Double.valueOf(0.0));
            detalleReabasto.setCalorias((medicamento.getCalorias() != null) ? Double.valueOf(medicamento.getCalorias()) : Double.valueOf(0.0));
            detalleReabasto.setNoHorasEstabilidad(medicamento.getNoHorasEstabilidad());
        }

//        detalleReabasto.setIdFabricante("123");
        if (detalleReabasto.getClaveInstitucional() == null) {
            detalleReabasto.setClaveInstitucional(ci.getClave());
        }
        if (ci.getCantidad() != null) {
            detalleReabasto.setCantidadXCaja(ci.getCantidad());
        } else {
            Integer cantidadXcaja = 1;
            cantidadXcaja = obtenerCantidadXCaja(detalleReabasto, cantidadXcaja);
            detalleReabasto.setCantidadXCaja(cantidadXcaja);
        }

    }

    private List<Proveedor> obtenerListaProveedor() {
        List<Proveedor> listaProveedor = new ArrayList<>();
        try {
            Proveedor p = new Proveedor();
            listaProveedor = proveedorService.obtenerLista(p);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener lista de proveedores; {}", ex.getMessage());
        }
        return listaProveedor;
    }

    private List<Fabricante> obtenerListaFabricante() {
        List<Fabricante> listaFabricante = new ArrayList<>();
        try {
            Fabricante f = new Fabricante();
            f.setIdEstatus(Constantes.ACTIVOS);
            listaFabricante = fabricanteService.obtenerLista(f);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener lista de Fabricantes; {}", ex.getMessage());
        }
        return listaFabricante;
    }

//    /**
//     * Busca lista de fabricantes que proveen el insumo
//     * @param cadena
//     * @return 
//     */
//    public List<Fabricante> buscarFabricante(String idInsumo) {
//        LOGGER.debug("mx.mc.magedbean.RecepcionManualMB.buscarFabricante()");
//        List<Fabricante> fabricantelist = null;
//        if (idInsumo != null && !idInsumo.trim().equals("")){
//            try {
//                fabricantelist = new ArrayList<>();
//                fabricantelist.addAll(fabricanteService.obtenerListaPorIdInsumo(idInsumo));
//            } catch (Exception ex) {
//                LOGGER.error("Error al obtener el Insumo : {}", ex.getMessage());
//            }
//        }
//        return fabricantelist;
//    }
    public List<Fabricante> obtenerFabricante(AjaxBehaviorEvent event) {
//    public List<Fabricante> obtenerFabricante(SelectEvent event){
        LOGGER.debug("mx.mc.magedbean.RecepcionManualMB.obtenerFabricante()");
        String x = ((HtmlSelectOneMenu) event.getSource()).getClientId();
        System.out.println(x.split(":")[2]);
        HtmlSelectOneMenu collectorTypeSelectMenu = (HtmlSelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("editForm:collectorType");
        String collectorType = (String) collectorTypeSelectMenu.getValue();
//formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt101
//formDetalleSurtimiento:tablaDetalleSurt:1:j_idt99:0:j_idt101
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt101
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt105
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt109
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt111
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt113
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt115
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt117
//      formDetalleSurtimiento:tablaDetalleSurt:0:j_idt99:0:j_idt119

        List<Fabricante> fabricanteList = null;
        try {
            fabricanteList = new ArrayList<>();
            fabricanteList.addAll(fabricanteService.obtenerLista(new Fabricante()));
        } catch (Exception ex) {
            LOGGER.error("Error al obtener al fabricante del insumo: {}", ex.getMessage());
        }
        return fabricanteList;
    }

    /**
     * Actualiza datos de una fila con base en campos encontrados de fabricante
     * y provveedor
     *
     * @param idFabricante
     * @param idInsumo
     * @param indiceFila
     * @return
     */
    public FabricanteInsumo obtenerFabricanteInsumo(String idFabricante, String idInsumo, String indiceFila) {
        LOGGER.debug("mx.mc.magedbean.RecepcionManualMB.obtenerFabricanteInsumo()");
        FabricanteInsumo fabricanteInsumo = null;
        try {
            FabricanteInsumo fbtemp = new FabricanteInsumo();
            fbtemp.setIdFabricante(idFabricante);
            fbtemp.setIdFabricanteInsumo(idInsumo);
            fabricanteInsumo = fabricanteInsumoService.obtener(fbtemp);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener al fabricanteInsumo: {}", ex.getMessage());
        }
        return fabricanteInsumo;
    }

    private Integer obtenerCantidadXCaja(ReabastoEnviadoExtended detalleReabasto, Integer cantidadXcaja) throws Exception {
        Medicamento medicamento = medicamentoService.obtenerMedicaByClave(detalleReabasto.getIdMedicamento());
        if (medicamento != null
                && medicamento.getFactorTransformacion() != null
                && medicamento.getFactorTransformacion() > 0) {
            cantidadXcaja = medicamento.getFactorTransformacion();
        }
        return cantidadXcaja;
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            reabastoSelect.setNombreEstructura(this.almacenDestino);
            reabastoSelect.setNombreProveedor(this.almacenOrigen);
            if (usuarioSession.getMatriculaPersonal() != null) {
                reabastoSelect.setMatriculaPersonal(usuarioSession.getMatriculaPersonal());
            }
            Estructura est;
            if (this.reabastoSelect.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(reabastoSelect.getIdEstructura());
            }

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            String usuarioRecibe = usuarioSession.getNombre().concat(" ").concat(usuarioSession.getApellidoPaterno()).concat(" ").concat(usuarioSession.getApellidoMaterno());
            if (usuarioRecibe != null) {
                reabastoSelect.setNombreUsrRecibe(usuarioRecibe);
            }

            byte[] buffer = reportesService.imprimirOrdenSurtir(reabastoSelect, entidad, RESOURCESMESSAGE.getString("TITULO1_SURTIMIENTO_MANUAL"));
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("recepcionOrden_%s.pdf", reabastoSelect.getFolio()));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public List<ReabastoExtended> getListaReabasto() {
        return listaReabasto;
    }

    public void setListaReabasto(List<ReabastoExtended> listaReabasto) {
        this.listaReabasto = listaReabasto;
    }

    public ReabastoExtended getReabastoSelect() {
        return reabastoSelect;
    }

    public void setReabastoSelect(ReabastoExtended reabastoSelect) {
        this.reabastoSelect = reabastoSelect;
    }

    public List<ReabastoInsumoExtended> getListaReabastoInsumo() {
        return listaReabastoInsumo;
    }

    public void setListaReabastoInsumo(List<ReabastoInsumoExtended> listaReabastoInsumo) {
        this.listaReabastoInsumo = listaReabastoInsumo;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public String getXcantidad() {
        return xcantidad;
    }

    public void setXcantidad(String xcantidad) {
        this.xcantidad = xcantidad;
    }

    public boolean isSurtirProveedor() {
        return surtirProveedor;
    }

    public void setSurtirProveedor(boolean surtirProveedor) {
        this.surtirProveedor = surtirProveedor;
    }

    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }

    public List<Estructura> getEstructuraList() {
        return estructuraList;
    }

    public void setEstructuraList(List<Estructura> estructuraList) {
        this.estructuraList = estructuraList;
    }

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public SesionMB getSesion() {
        return sesion;
    }

    public void setSesion(SesionMB sesion) {
        this.sesion = sesion;
    }

    public ReabastoInsumoExtended getSkuSap() {
        return skuSap;
    }

    public void setSkuSap(ReabastoInsumoExtended skuSap) {
        this.skuSap = skuSap;
    }

    public List<ReabastoInsumoExtended> getSkuSapList() {
        return skuSapList;
    }

    public void setSkuSapList(List<ReabastoInsumoExtended> skuSapList) {
        this.skuSapList = skuSapList;
    }

    public boolean isActivaAutoCompleteInsumos() {
        return activaAutoCompleteInsumos;
    }

    public void setActivaAutoCompleteInsumos(boolean activaAutoCompleteInsumos) {
        this.activaAutoCompleteInsumos = activaAutoCompleteInsumos;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public List<Proveedor> getListaProveedor() {
        return listaProveedor;
    }

    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }

    public List<Fabricante> getListaFabricante() {
        return listaFabricante;
    }

    public void setListaFabricante(List<Fabricante> listaFabricante) {
        this.listaFabricante = listaFabricante;
    }

    public List<FabricanteInsumo> getListaFabricanteInsumo() {
        return listaFabricanteInsumo;
    }

    public void setListaFabricanteInsumo(List<FabricanteInsumo> listaFabricanteInsumo) {
        this.listaFabricanteInsumo = listaFabricanteInsumo;
    }

    public String getLoteRecibido() {
        return loteRecibido;
    }

    public void setLoteRecibido(String loteRecibido) {
        this.loteRecibido = loteRecibido;
    }

    public Date getCaducidadRecibida() {
        return caducidadRecibida;
    }

    public void setCaducidadRecibida(Date caducidadRecibida) {
        this.caducidadRecibida = caducidadRecibida;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isCapturaLoteCaducidadManual() {
        return capturaLoteCaducidadManual;
    }

    public void setCapturaLoteCaducidadManual(boolean capturaLoteCaducidadManual) {
        this.capturaLoteCaducidadManual = capturaLoteCaducidadManual;
    }

    public void estableceLoteRecibido(AjaxBehaviorEvent event) {
        try {
            this.loteRecibido = (String) ((UIOutput) event.getSource()).getValue();
        } catch (Exception e) {
            LOGGER.error("Error al registrar lote recibido :: {} ", e.getMessage());
        }
    }

    public void estableceCaducidadRecibido(AjaxBehaviorEvent event) {
        try {
            this.caducidadRecibida = (Date) ((UIOutput) event.getSource()).getValue();
        } catch (Exception e) {
            LOGGER.error("Error al registrar caducidad recibida :: {} ", e.getMessage());
        }
    }

    public void eliminaInsumoDeLista(ReabastoInsumoExtended insumo, ReabastoEnviadoExtended insumoEnviado) {
        LOGGER.trace("mx.mc.magedbean.RecepcionManualMB.eliminaInsumoDeLista()");
        if (insumo != null && insumoEnviado != null) {
            List<ReabastoEnviadoExtended> listaDetalleReabInsTmp = new ArrayList<>();
            Integer cantidadDescontar = 0;
            if (this.listaReabastoInsumo != null && !this.listaReabastoInsumo.isEmpty()) {
                for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                    if (item.getIdReabastoInsumo().equals(insumo.getIdReabastoInsumo())) {
                        if (item.getListaDetalleReabIns() != null && !item.getListaDetalleReabIns().isEmpty()) {
                            for (ReabastoEnviadoExtended item2 : item.getListaDetalleReabIns()) {
                                if (!item2.getIdReabastoEnviado().equals(insumoEnviado.getIdReabastoEnviado())) {
                                    listaDetalleReabInsTmp.add(item2);
                                } else {
                                    cantidadDescontar = item2.getCantidadEnviado();
                                }
                            }
                        }
                    }
                    item.setListaDetalleReabIns(listaDetalleReabInsTmp);
                    item.setCantidadSurtida(item.getCantidadSurtida() - cantidadDescontar);
                }
            }
        }
    }

    public List<ValidacionNoCumplidaDTO> getListaValidacion() {
        return listaValidacion;
    }

    public void setListaValidacion(List<ValidacionNoCumplidaDTO> listaValidacion) {
        this.listaValidacion = listaValidacion;
    }

}
