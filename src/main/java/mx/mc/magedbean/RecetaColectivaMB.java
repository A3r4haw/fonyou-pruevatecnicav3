package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.EstructuraAlmacenServicio;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Prescripcion;
import mx.mc.model.PresentacionMedicamento;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.model.Usuario;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraAlmacenServicioService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.PresentacionMedicamentoService;
import mx.mc.service.ReabastoEnviadoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author AORTIZ
 */
@Controller
@Scope(value = "view")
public class RecetaColectivaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(RecetaColectivaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private List<Estructura> listaEstructuras;
    private List<ReabastoExtended> listaReabasto;
    private List<ReabastoInsumoExtended> listaReabastoInsumo;
    private ReabastoExtended reabastoSelect;
    private String idEstructura;
    private int idTipoAlmacenUsuario;
    private String textoBusqueda;
    private Usuario usuarioSession;
    private String codigoBarras;
    private boolean eliminarCodigo;
    private boolean administrador;
    private String xcantidad;
    private String comentarios;
    private String cantidadSolicitada;
    private String cantidadSurtida;
    private SesionMB sesion;
    private boolean editarOrden;
    private List<ReabastoInsumoExtended> insumoList;
    private ClaveProveedorBarras_Extend skuSap;
    private ClaveProveedorBarras_Extend medicamentoSelect;
    private List<Usuario> listaMedicos;
    private String idMedico;    
    private String estrErrPermisos;
    private String surtimientoErrorOperacion;
    private PermisoUsuario permiso;
    private boolean mostrarModalInsumos;
    private boolean generaReporte;
    private boolean bandera;
    private boolean crearReceta;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient ReabastoService reabastoService;

    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReabastoEnviadoService reabastoEnviadoService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient UsuarioService usuarioService;

    private EntidadHospitalaria entidad;
    private int tipoOrden;
    private int tipoInsumo;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    private List<CatalogoGeneral> tipoInsumoList;

    private String tipoAlmacen;
    private Estructura estructuraSelect;
    private String idPadre;

    private boolean activo;
    private boolean isAdmin;
    private boolean farmacia;
    @Autowired
    private transient EstructuraAlmacenServicioService estructuraAlmacenServicioService;

    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;

    private List<ClaveProveedorBarras_Extend> skuSapList;
    private boolean activaColectivoServicios;
    private Date fechaActual;
    private ReabastoInsumoExtended reabastoInsumoExtend;
    private List<ReabastoInsumoExtended> listaReabastoInsumoElimiminar;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {            
            estrErrPermisos = "estr.err.permisos";
            surtimientoErrorOperacion = "surtimiento.error.operacion";
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.RECETACOLECTIVA.getSufijo());
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            generaReporte = Constantes.INACTIVO;
            bandera = false;
            this.activaColectivoServicios = this.sesion.isActivaColectivoServicios();
            validarUsuarioAdministrador();
            obtenerEntidadHospitalaria();
            alimentarComboAlmacen();
            obtenerOrdenesReabasto();
            fechaActual = new java.util.Date();  
            obtenerTipoInsumos();
            esAdministrador();

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
        this.reabastoSelect = new ReabastoExtended();
        this.idEstructura = "";
        this.textoBusqueda = "";
        this.usuarioSession = new Usuario();
        this.codigoBarras = "";
        this.eliminarCodigo = false;
        this.administrador = false;
        this.xcantidad = "1";
        this.comentarios = "";
        this.cantidadSolicitada = "1";
        this.cantidadSurtida = "1";
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.tipoInsumoList = new ArrayList<>();
        this.insumoList = new ArrayList<>();
        this.listaMedicos = new ArrayList<>();
        this.editarOrden = false;
        this.mostrarModalInsumos = false;
        this.listaReabastoInsumoElimiminar = new ArrayList<>();
        this.idTipoAlmacenUsuario = 0;
        this.crearReceta = false;
    }

    public void limpiar() {
        this.xcantidad = "1";
        this.cantidadSolicitada = "1";
        this.cantidadSurtida = "1";
        this.comentarios = "";
        this.codigoBarras = "";
        this.eliminarCodigo = false;
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.editarOrden = false;
        this.insumoList = new ArrayList<>();
        this.mostrarModalInsumos = false;
        this.idTipoAlmacenUsuario = 0;
        this.crearReceta = false;
    }

    /**
     * Obtiene el nombre de la unidad Hospitalaria
     */
    public void obtenerEntidadHospitalaria() {
        LOGGER.debug("RecetaColectivaMB.obtenerEntidadHospitalaria()");
        entidad = new EntidadHospitalaria();
        entidad = new EntidadHospitalaria();
        entidad.setDomicilio("");
        entidad.setNombre("");
        try {
            Estructura estruct = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            this.idTipoAlmacenUsuario = estruct.getIdTipoAlmacen();
            entidad = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (entidad == null) {
                entidad = new EntidadHospitalaria();
                entidad.setDomicilio("");
                entidad.setNombre("");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

    }

    /**
     * obtiene la lista de tipos de insumo que puede solicitar
     */
    private void obtenerTipoInsumos() {
        LOGGER.trace("Load Insumos");
        try {
            tipoInsumoList = catalogoGeneralService.obtenerCatalogosPorGrupo(Constantes.TIPO_INSUMO);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
        }
    }

    private void obtenerListaMedicosActivos() {
        try {
            listaMedicos = usuarioService.obtenerListaMedicosActivos();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicos Activos: {}", ex.getMessage());
        }
    }

    private void esAdministrador() {
        isAdmin = Constantes.INACTIVO;
        try {
            if (usuarioSession != null
                    && usuarioSession.getIdEstructura() != null) {
                Estructura e = estructuraService.obtener(new Estructura(usuarioSession.getIdEstructura()));
                if (e.getIdTipoAreaEstructura() == 1 && e.getIdTipoAlmacen().equals(1)) {
                    isAdmin = Constantes.ACTIVO;
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    /**
     * Crea la orden de reabasto
     */
    public void createOrdenReabasto() {
        boolean status = Constantes.INACTIVO;
        try {
            this.listaReabastoInsumo = new ArrayList<>();
            obtenerListaMedicosActivos();
            this.idMedico = "";
            if (permiso.isPuedeCrear()
                    && tipoOrden > 0) {
                Estructura e = estructuraService.obtener(new Estructura(idEstructura));
                reabastoSelect = new ReabastoExtended();
                reabastoSelect.setIdReabasto(Comunes.getUUID());

                reabastoSelect.setIdEstructura(e.getIdEstructura());

                // Se busca la estructura almacen para encontrar el padre
                EstructuraAlmacenServicio eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(idEstructura, null));
                if (eas == null) {
                    eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(null, e.getIdEstructuraPadre()));
                    if (eas == null) {
                        e = estructuraService.obtener(new Estructura(e.getIdEstructuraPadre()));
                        eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(e.getIdEstructura(), null));
                        if (eas == null) {
                            e = estructuraService.obtener(new Estructura(e.getIdEstructuraPadre()));
                            eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(e.getIdEstructura(), null));
                        }
                    }
                }
                Estructura ea = estructuraService.obtener(new Estructura(eas.getIdEstructuraAlmacen()));
                reabastoSelect.setAlmacen(ea.getNombre());
                reabastoSelect.setIdEstructuraPadre(ea.getIdEstructura());

                reabastoSelect.setIdTipoOrden(tipoOrden);
                reabastoSelect.setFechaSolicitud(new Date());
                reabastoSelect.setIdUsuarioSolicitud(usuarioSession.getIdUsuario());
                reabastoSelect.setInsertFecha(new java.util.Date());
                reabastoSelect.setInsertIdUsuario(usuarioSession.getIdUsuario());
                status = Constantes.ACTIVO;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Obtener los datos: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    /**
     * Metodo utilizado para poblar el combo almacen / Servicios
     */
    public void alimentarComboAlmacen() {
        try {

            Estructura est = new Estructura();
            est.setActiva(Constantes.ESTATUS_ACTIVO);
            listaEstructuras = new ArrayList<>();
            //GCR Se agrega validación de para metro para mostrar los servicios o almacenes
            if (this.activaColectivoServicios) {
                if (!this.administrador) {
                    est.setIdTipoArea(TipoAreaEstructura_Enum.AREA.getValue());
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));

                    est.setIdTipoArea(TipoAreaEstructura_Enum.SERVICIO.getValue());
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));

                    est.setIdTipoArea(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));

                } else {
                    est.setIdTipoArea(Constantes.TIPO_AREA_ALMACEN);
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));

                    est.setIdTipoArea(TipoAreaEstructura_Enum.AREA.getValue());
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));

                    est.setIdTipoArea(TipoAreaEstructura_Enum.SERVICIO.getValue());
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));

                    est.setIdTipoArea(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));
                }
                this.crearReceta = true;
            } else {
                //GCR Solo los usuarios que esten asignados a un nivel de almacen podran generar receta colectiva
                if (this.idTipoAlmacenUsuario == Constantes.ALMACEN) {
                    est.setIdTipoAlmacen(Constantes.ALMACEN);
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));

                    est.setIdTipoAlmacen(Constantes.SUBALMACEN);

                    this.crearReceta = true;
                } else {
                    est.setIdEstructura(idEstructura);
                    this.listaEstructuras.addAll(this.estructuraService.obtenerLista(est));
                    this.crearReceta = false;
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para llenar la tabla de Ordenes de Reabasto que se
     * muestra en pantalla
     */
    public void obtenerOrdenesReabasto() {
        try {
            List<Integer> listEstatusReabasto = null;
            Reabasto reabasto = new Reabasto();
            reabasto.setIdEstructura(this.idEstructura);
            Integer idTipoAlmacen = null;
            this.listaReabasto = this.reabastoService.obtenerReabastoExtends(reabasto, listEstatusReabasto, idTipoAlmacen);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarTablaOrdenesReabasto :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que valida si el usuario que se logeo es de tipo administrador si
     * no es adinistrador obtiene el id de estructura del usuario
     */
    public void validarUsuarioAdministrador() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesionn = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.administrador = sesionn.isAdministrador();
            if (!this.administrador) {
                this.idEstructura = usuarioSession.getIdEstructura();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para cambiar el estatus de una orden a cancelado
     *
     * @param reabastoExtended
     */
    public void cancelarOrdenReabasto(ReabastoExtended reabastoExtended) {
        try {
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), null);
                return;
            }

            List<ReabastoInsumo> listReabastoInsumo = null;
            List<ReabastoEnviado> listaReabastoEnviado = null;

            List<Inventario> listaInventarios = new ArrayList<>();
            List<MovimientoInventario> listaMovimientos = new ArrayList<>();
            List<ReabastoEnviado> listaReabastoEnviadoUpdate = new ArrayList<>();

            Reabasto reabasto = new Reabasto();
            reabasto.setIdReabasto(reabastoExtended.getIdReabasto());
            reabasto.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_CANCELADA);

            ReabastoInsumo reabastoInsumo = new ReabastoInsumo();
            reabastoInsumo.setIdReabasto(reabasto.getIdReabasto());

            listReabastoInsumo = reabastoInsumoService.obtenerLista(reabastoInsumo);
            if (listReabastoInsumo != null) {
                listaReabastoEnviado = reabastoEnviadoService.
                        obtenerReabastoEnviadoByListaReabastoInsumo(listReabastoInsumo);
            }

            for (ReabastoEnviado item : listaReabastoEnviado) {
                ReabastoEnviado reabastoEnviado = new ReabastoEnviado();
                reabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_CANCELADA);
                reabastoEnviado.setUpdateFecha(new Date());
                reabastoEnviado.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                listaReabastoEnviadoUpdate.add(reabastoEnviado);

                Inventario inventario = new Inventario();
                inventario.setIdInventario(item.getIdInventarioSurtido());
                inventario.setCantidadActual(item.getCantidadEnviado());
                inventario.setUpdateFecha(new Date());
                inventario.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                listaInventarios.add(inventario);

                MovimientoInventario movimiento = new MovimientoInventario();
                movimiento.setIdMovimientoInventario(Comunes.getUUID());
                movimiento.setIdTipoMotivo(TipoMotivo_Enum.DEV_DEVOLUCION_POR_CANCELACION_DE_RECETA.getMotivoValue());
                movimiento.setFecha(new Date());
                movimiento.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                movimiento.setIdEstrutcuraOrigen(item.getIdEstructura());
                movimiento.setIdEstrutcuraDestino(item.getIdEstructura());
                movimiento.setIdInventario(item.getIdInventarioSurtido());
                movimiento.setCantidad(item.getCantidadEnviado());
                movimiento.setFolioDocumento(reabastoExtended.getFolio());
                listaMovimientos.add(movimiento);
            }
            reabastoInsumo.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_CANCELADA);
            reabastoInsumo.setUpdateFecha(new Date());
            reabastoInsumo.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            this.reabastoService.cancelarOrdenReabastoColectivo(reabasto, reabastoInsumo,
                    listaReabastoEnviadoUpdate, listaInventarios, listaMovimientos);
            obtenerOrdenesReabasto();
            this.mostrarModalInsumos = false;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cancelarOrdenReabasto :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que se utiliza para mostrar el detalle de la orden de surtimiento
     */
    public void mostrarModalSurtimiento() {
        try {
            this.listaReabastoInsumoElimiminar = new ArrayList<>();
            if (this.reabastoSelect.getIdEstatusReabasto() == 1) {
                this.mostrarModalInsumos = true;
                obtenerListaMedicosActivos();
                this.idMedico = reabastoSelect.getIdMedico();
                this.editarOrden = true;
                this.insumoList = new ArrayList<>();
                List<Integer> listEstatusReabasto = new ArrayList<>();
                listEstatusReabasto.add(EstatusReabasto_Enum.REGISTRADA.getValue());
                this.idMedico = reabastoSelect.getIdMedico();
                this.listaReabastoInsumo = reabastoInsumoService.
                        obtenerReabastoInsumoExtendsSurt(
                                this.reabastoSelect.getIdReabasto(),
                                this.reabastoSelect.getIdEstructuraPadre(), listEstatusReabasto);

                for (short i = 0; i < this.listaReabastoInsumo.size(); i++) {
                    ReabastoInsumo item = this.listaReabastoInsumo.get(i);
                    this.listaReabastoInsumo.get(i).setListaDetalleReabIns(
                            reabastoEnviadoService.obtenerListaReabastoEnviadoInv(
                                    item.getIdReabastoInsumo(), EstatusReabasto_Enum.REGISTRADA.getValue(), this.reabastoSelect.getIdEstructuraPadre()));
                }
            } else {
                this.mostrarModalInsumos = false;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo se pueden mostrar ordenes con Estatus 'Registrada' ", null);
            }
            PrimeFaces.current().ajax().addCallbackParam("mostrarModalInsumos", mostrarModalInsumos);
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
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), null);
                return;
            }
            if (this.reabastoSelect.getFolio().isEmpty() || this.reabastoSelect.getFolio().equals("")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe colocar un folio ", null);
                return;
            }
            if (this.idMedico == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un Médico ", null);
                return;
            }
            if (!this.editarOrden) {
                if (this.activaColectivoServicios) {
                    Pattern patron = Constantes.folioRecetaColectiva;
                    Matcher folio = patron.matcher(reabastoSelect.getFolio());
                    if (!folio.matches() || reabastoSelect.getFolio().length() > 6
                            || reabastoSelect.getFolio().length() < 6) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El formato de folio no es correcto.     Ejem: 18/123", null);
                        return;
                    }
                }
                Reabasto reabastoEncontrado = reabastoService.getReabastoByFolio(this.reabastoSelect.getFolio());
                if (reabastoEncontrado != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Este folio ya existe!! " + this.reabastoSelect.getFolio(), null);
                    return;
                }
            }

            if (this.listaReabastoInsumo != null && !this.listaReabastoInsumo.isEmpty()) {
                //Se comenta linea para poder realizar surtimiento en cero
                //Validar que los surtimientos no sean cero o el medicamento no este bloqueado
                for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                    if (item.getActivo() == Constantes.ESTATUS_ACTIVO
                            && item.getCantidadSurtida() < 0) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantCero"), null);
                        return;
                    }
                    if (item.getActivo() == Constantes.ESTATUS_INACTIVO
                            && item.getCantidadSurtida() > 0) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medInactivo"), null);
                        return;
                    }
                }
                Reabasto reabasto = new Reabasto();
                reabasto.setIdReabasto(this.reabastoSelect.getIdReabasto());
                reabasto.setIdUsuarioSolicitud(this.usuarioSession.getIdUsuario());
                reabasto.setFechaSurtida(new Date());
                reabasto.setIdUsuarioSurtida(this.usuarioSession.getIdUsuario());
                reabasto.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                reabasto.setFolio(this.reabastoSelect.getFolio());
                reabasto.setIdEstructuraPadre(this.reabastoSelect.getIdEstructuraPadre());
                reabasto.setIdEstructura(this.reabastoSelect.getIdEstructura());
                reabasto.setFechaSolicitud(this.reabastoSelect.getFechaSolicitud());
                for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                    item.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                }
                boolean resp = true;
                if (!this.editarOrden) {
                    reabasto.setInsertFecha(new Date());
                    reabasto.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                    reabasto.setIdTipoOrden(this.reabastoSelect.getIdTipoOrden());
                    reabasto.setIdMedico(this.idMedico);
                    resp = this.reabastoService.guardarReabastoColectivo(reabasto, this.listaReabastoInsumo, this.editarOrden, this.listaReabastoInsumoElimiminar);
                } else {
                    reabasto.setUpdateFecha(new Date());
                    reabasto.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                    reabasto.setInsertFecha(new Date());
                    reabasto.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                    if (this.insumoList != null && !this.insumoList.isEmpty()) {
                        resp = this.reabastoService.guardarReabastoColectivo(reabasto, this.insumoList, this.editarOrden, this.listaReabastoInsumoElimiminar);
                    }
                }
                if (resp) {
                    resp = this.reabastoService.
                            surtirOrdenReabastoColectivo(reabasto, this.listaReabastoInsumo);
                }
                if (generaReporte) {
                    imprimirReporte();
                    PrimeFaces.current().ajax().addCallbackParam("generaReporte", generaReporte);
                }
                if (resp) {
                    Mensaje.showMessage("Info", RESOURCES.getString("surtimiento.ok.surtimiento"), null);
                    obtenerOrdenesReabasto();
                    PrimeFaces.current().ajax().addCallbackParam("errorSurtir", resp);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se tiene ningun medicamento para guardar la receta colectiva ", null);
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), null);
            LOGGER.error("Error en el metodo surtirReabastoInsumo :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para guardar la informacion sin realizar el surtimiento
     */
    public void guardarRegistros() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), null);
                return;
            }
            if (this.reabastoSelect.getFolio().isEmpty() || this.reabastoSelect.getFolio().equals("")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe colocar un folio ", null);
                return;
            }
            if (!this.editarOrden) {
                Reabasto reabastoEncontrado = reabastoService.getReabastoByFolio(this.reabastoSelect.getFolio());
                if (reabastoEncontrado != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Este folio ya existe!! " + this.reabastoSelect.getFolio(), null);
                    return;
                }
            }
            if (this.activaColectivoServicios) {
                Pattern patron = Constantes.folioRecetaColectiva;
                Matcher folio = patron.matcher(reabastoSelect.getFolio());
                if (!folio.matches() || reabastoSelect.getFolio().length() > 6
                        || reabastoSelect.getFolio().length() < 6) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El formato de folio no es correcto.     Ejem: 18/123", null);
                    return;
                }
            }
            boolean resp = false;
            if (this.listaReabastoInsumo != null && !this.listaReabastoInsumo.isEmpty()) {
                Reabasto reabasto = new Reabasto();

                reabasto.setIdReabasto(this.reabastoSelect.getIdReabasto());
                reabasto.setIdEstructura(this.reabastoSelect.getIdEstructura());
                reabasto.setIdEstructuraPadre(this.reabastoSelect.getIdEstructuraPadre());
                reabasto.setIdTipoOrden(this.reabastoSelect.getIdTipoOrden());
                reabasto.setFolio(this.reabastoSelect.getFolio());
                reabasto.setFechaSolicitud(this.reabastoSelect.getFechaSolicitud());
                reabasto.setIdUsuarioSolicitud(this.reabastoSelect.getIdUsuarioSolicitud());
                reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                reabasto.setInsertFecha(this.reabastoSelect.getInsertFecha());
                reabasto.setInsertIdUsuario(this.reabastoSelect.getInsertIdUsuario());
                reabasto.setIdMedico(this.idMedico);
                if (!this.editarOrden) {
                    resp = this.reabastoService.guardarReabastoColectivo(reabasto, this.listaReabastoInsumo, this.editarOrden, this.listaReabastoInsumoElimiminar);
                } else {
                    resp = this.reabastoService.guardarReabastoColectivo(reabasto, this.insumoList, this.editarOrden, this.listaReabastoInsumoElimiminar);
                }

                if (resp) {
                    Mensaje.showMessage("Info", RESOURCES.getString("ok.guardar"), null);
                    PrimeFaces.current().ajax().addCallbackParam("errorSurtir", resp);
                    obtenerOrdenesReabasto();
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se tiene ningun medicamento para guardar la receta colectiva ", null);
            }

        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), null);
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
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
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
     * Metodo utilizado para crear el sub detalle del medicamento por escaneo de
     * codigo de barras
     */
    public void agregarInsumosPorCodigo() {
        try {
            if (this.codigoBarras.isEmpty()) {
                return;
            }
            if (this.eliminarCodigo) {
                String msjError = eliminarLotePorCodigo();
                if (msjError.isEmpty()) {
                    Mensaje.showMessage("Info", RESOURCES.getString("surtimiento.ok.eliminar"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, null);
                }
                this.codigoBarras = "";
                this.xcantidad = "1";
                this.cantidadSolicitada = "1";
                this.cantidadSurtida = "1";
                this.eliminarCodigo = false;
                return;
            }
            //Separar codigo de barras 
            ReabastoEnviadoExtended medicamentoDetalle = tratarCodigoDeBarras(this.codigoBarras);
            //Filtrar registros
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoDetalle);
            //Validar si el medicamento esta en la lista
            if (medicamento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.medNoEncontrado"), null);
                this.codigoBarras = "";
                this.xcantidad = "1";
                return;
            }
            //Validar fecha de caducidad 
            if (medicamentoDetalle.getFechaCaducidad().compareTo(new Date()) < 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medCaducado"), null);
                this.codigoBarras = "";
                this.xcantidad = "1";
                return;
            }

            //Validar que la cantidad del lote no sea insuficiente
            Inventario inventario = inventarioService.obtenerInventariosPorInsumoEstructuraYLote(
                    medicamento.getIdInsumo(), this.idEstructura,
                    medicamentoDetalle.getLote());
            if (inventario == null) {
                inventario = new Inventario();
            }

            //Validar si el detalle del meticamento existe
            if (medicamento.getListaDetalleReabIns() == null) {
                medicamento.setListaDetalleReabIns(new ArrayList<>());
                if (this.xcantidad.isEmpty()) {
                    this.xcantidad = "1";
                }

                int cantidadXCaja = 0;
                if (medicamentoDetalle.getCantidadXCaja() != null) {
                    cantidadXCaja = medicamentoDetalle.getCantidadXCaja();
                } else {
                    cantidadXCaja = medicamento.getFactorTransformacion();
                }

                int cantSurtida = medicamento.getCantidadSurtida() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                if (cantSurtida > medicamento.getCantidadSolicitada()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR,
                            RESOURCES.getString("surtimiento.error.cantSolMayor"), null);
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                medicamentoDetalle.setCantidadXCaja(cantidadXCaja);
                medicamentoDetalle.setCantidadEnviado(cantSurtida);
                medicamentoDetalle.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                medicamentoDetalle.setIdMedicamento(medicamento.getIdInsumo());
                medicamentoDetalle.setIdEstructura(this.idEstructura);
                String idInventario = inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                        medicamento.getIdInsumo(), this.idEstructura, medicamentoDetalle.getLote());
                if (idInventario == null || idInventario.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El lote no existe", null);
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                medicamentoDetalle.setIdInventarioSurtido(idInventario);
                medicamento.getListaDetalleReabIns().add(medicamentoDetalle);
                medicamento.setCantidadSurtida(cantSurtida);
            } else {
                if (this.xcantidad.isEmpty()) {
                    this.xcantidad = "1";
                }

                int cantidadXCaja = 0;
                if (medicamentoDetalle.getCantidadXCaja() != null) {
                    cantidadXCaja = medicamentoDetalle.getCantidadXCaja();
                } else {
                    cantidadXCaja = medicamento.getFactorTransformacion();
                }

                int cantSurtida = medicamento.getCantidadSurtida() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                int cantidadEnviada = cantidadXCaja * Integer.valueOf(this.xcantidad);
                if (inventario.getCantidadActual() < cantidadEnviada) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo hay " + inventario.getCantidadActual() + " existencias en el lote " + inventario.getLote(), null);
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                if (cantSurtida > medicamento.getCantidadSolicitada()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantSolMayor"), null);
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                    if (item.getLote().equalsIgnoreCase(medicamentoDetalle.getLote())) {
                        if (inventario.getCantidadActual() < (item.getCantidadEnviado() + cantidadXCaja * Integer.valueOf(this.xcantidad))) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo hay " + inventario.getCantidadActual() + " existencias en el lote " + inventario.getLote(), null);
                            this.codigoBarras = "";
                            this.xcantidad = "1";
                            return;
                        }
                        item.setCantidadEnviado(item.getCantidadEnviado() + cantidadXCaja * Integer.valueOf(this.xcantidad));
                        medicamento.setCantidadSurtida(cantSurtida);
                        this.codigoBarras = "";
                        this.xcantidad = "1";
                        return;
                    }
                }
                medicamentoDetalle.setCantidadXCaja(cantidadXCaja);
                medicamentoDetalle.setCantidadEnviado(cantidadEnviada);
                medicamentoDetalle.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                medicamentoDetalle.setIdMedicamento(medicamento.getIdInsumo());
                medicamentoDetalle.setIdEstructura(this.idEstructura);
                medicamentoDetalle.setIdInventarioSurtido(
                        inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                                medicamento.getIdInsumo(), this.idEstructura, medicamentoDetalle.getLote()));
                medicamento.getListaDetalleReabIns().add(medicamentoDetalle);
                medicamento.setCantidadSurtida(cantSurtida);
            }
            this.codigoBarras = "";
            this.xcantidad = "1";
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reabastSurtimiento.err.agregar"), null);
        }
    }

    private String eliminarLotePorCodigo() {
        String msgError = "";
        try {
            ReabastoEnviadoExtended medicamentoCodigo = tratarCodigoDeBarras(this.codigoBarras);
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoCodigo);
            if (medicamento == null) {
                msgError = RESOURCES.getString("orepcio.err.medNoEncontrado");
                return msgError;
            }
            ReabastoEnviadoExtended medicamentoDetalle = obtenerDetalleMedicamento(
                    medicamento.getListaDetalleReabIns(), medicamentoCodigo);
            if (medicamentoDetalle == null) {
                msgError = RESOURCES.getString("surtimiento.error.noLote");
                return msgError;
            }
            if (this.xcantidad.isEmpty()) {
                this.xcantidad = "1";
            }
            int factorConversion = obtenerCandtidadMedicamento(medicamento.getIdInsumo());
            int cantSurtida = medicamento.getCantidadSurtida()
                    - factorConversion * Integer.valueOf(this.xcantidad);
            int cantidadEnviado = medicamentoDetalle.getCantidadEnviado()
                    - factorConversion * Integer.valueOf(this.xcantidad);
            if (cantidadEnviado < 0) {
                msgError = RESOURCES.getString("surtimiento.error.cantElimMayor");
                return msgError;
            }
            medicamento.setCantidadSurtida(cantSurtida);
            medicamentoDetalle.setCantidadEnviado(cantidadEnviado);
            if (medicamentoDetalle.getCantidadEnviado() == 0) {
                eliminarDetalleByFolio(medicamento.getListaDetalleReabIns(), medicamentoCodigo);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), null);
        }
        return msgError;
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
                if (item.getClaveInstitucional().equalsIgnoreCase(detalle.getIdMedicamento())) {
                    return item;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerInsumoPorClaveMedicamento :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), null);
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
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), null);
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
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), null);
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
        CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
        if (ci != null) {
            detalleReabasto.setIdMedicamento(ci.getClave());
            detalleReabasto.setLote(ci.getLote());
            try {
                detalleReabasto.setFechaCaducidad(ci.getFecha());
            } catch (Exception ex) {
                LOGGER.error("Error en el metodo tratarCodigoDeBarras :: Al parsear fecha: {}", ex.getMessage());
            }
            if (ci.getCantidad() != null) {
                detalleReabasto.setCantidadXCaja(ci.getCantidad());
            } else {
                Integer cantidadXcaja = 1;
                Inventario parametros = new Inventario();
                parametros.setIdInsumo(detalleReabasto.getIdMedicamento());
                parametros.setLote(detalleReabasto.getLote());
                parametros.setIdEstructura(this.idEstructura);
                Inventario inventario = null;
                try {
                    inventario = inventarioService.
                            obtenerInventarioPorClveInstEstructuraYLote(parametros);
                } catch (Exception ex) {
                    LOGGER.error("Error en el metodo tratarCodigoDeBarras :: Al obtener Inventario: {}", ex.getMessage());
                }
                if (inventario != null
                        && inventario.getCantidadXCaja() != null
                        && inventario.getCantidadXCaja() > 0) {
                    cantidadXcaja = inventario.getCantidadXCaja();
                }
                detalleReabasto.setCantidadXCaja(cantidadXcaja);
            }
        } else {
            ClaveProveedorBarras_Extend claveProveedorBarras = null;
            try {
                claveProveedorBarras = claveProveedorBarrasService.obtenerClave(codigo);
            } catch (Exception ex) {
                LOGGER.error("Error en el metodo tratarCodigoDeBarras :: Al obtener ClavaProveedorBarras: {}", ex.getMessage());
            }
            //TODO modificar con autocompleate
            if (claveProveedorBarras != null) {
                detalleReabasto.setIdMedicamento(claveProveedorBarras.getClaveInstitucional());
                detalleReabasto.setCantidadXCaja(claveProveedorBarras.getCantidadXCaja());
                detalleReabasto.setLote(claveProveedorBarras.getClaveProveedor());
                detalleReabasto.setFechaCaducidad(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Valor no valido", null);
            }
        }
        return detalleReabasto;
    }

    private Integer obtenerCandtidadMedicamento(String idMedicamento) {
        int cantidad = 0;
        try {
            cantidad = reabastoInsumoService.
                    obtenerCantidadMedicamento(idMedicamento);

        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarMedicamento :: {}", e.getMessage());
        }
        return cantidad;
    }

    public void imprimirReporte() throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            Estructura estructura = this.estructuraService.
                    obtenerEstructura(this.reabastoSelect.getIdEstructura());

            Surtimiento_Extend itemSelect = new Surtimiento_Extend();
            itemSelect.setNombrePaciente(estructura.getNombre());
            itemSelect.setFolio(this.reabastoSelect.getFolio());
            Usuario user = new Usuario();
            user.setIdUsuario(this.idMedico);
            Usuario medico = usuarioService.obtenerUsuarioPorId(idMedico);

            itemSelect.setNombreMedico(medico.getNombre() + " " + medico.getApellidoPaterno() + " " + medico.getApellidoMaterno());

            Prescripcion prescripcion = new Prescripcion();
            prescripcion.setFolio(this.reabastoSelect.getFolio());
            prescripcion.setFechaPrescripcion(this.reabastoSelect.getFechaSolicitud());
            EntidadHospitalaria entidadHospitalaria = this.entidadHospitalariaService.obtener(new EntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeReporteColectivaChiconcuac(
                    prescripcion,
                    entidadHospitalaria,
                    itemSelect,
                    this.usuarioSession.getNombreUsuario());

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket_%s.pdf", itemSelect.getFolio().substring(3)));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public void imprimirArchivos() throws Exception {
        if (generaReporte) {
            imprimirReporte();
        }
        imprimir();
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            Estructura estructura = this.estructuraService.
                    obtenerEstructura(this.reabastoSelect.getIdEstructura());

            Surtimiento_Extend itemSelect = new Surtimiento_Extend();
            itemSelect.setNombrePaciente(estructura.getNombre());
            itemSelect.setFolio(this.reabastoSelect.getFolio());
            itemSelect.setNombreMedico("");

            Prescripcion prescripcion = new Prescripcion();
            prescripcion.setFolio(this.reabastoSelect.getFolio());
            prescripcion.setFechaPrescripcion(this.reabastoSelect.getFechaSolicitud());
            EntidadHospitalaria entidadHospitalaria = this.entidadHospitalariaService.obtener(new EntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeSurtimientoPrescColectivaChiconcuac(
                    prescripcion,
                    entidadHospitalaria,
                    itemSelect,
                    this.usuarioSession.getNombreUsuario());

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket_%s.pdf", itemSelect.getFolio().substring(3)));
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }

    public List<ClaveProveedorBarras_Extend> autocompleteMedicamento(String query) {
        this.skuSapList = new ArrayList<>();
        boolean rManual = false;
        try {
            if (this.activaColectivoServicios) {
                List<Integer> listaSubCategorias = new ArrayList<>();
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExtChiconcuac(
                        query, reabastoSelect.getIdEstructuraPadre(), this.usuarioSession.getIdUsuario(), listaSubCategorias, rManual);
            } else {
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExt(
                        query, reabastoSelect.getIdEstructura(), this.usuarioSession.getIdUsuario());
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return skuSapList;
    }

    public void handleSelectMedicamento(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtPrescripcionExtMB.handleSelect()");
        this.skuSap = (ClaveProveedorBarras_Extend) e.getObject();
        String idInventario = skuSap.getIdInventario();
        for (ClaveProveedorBarras_Extend item : this.skuSapList) {
            if (item.getIdInventario().equalsIgnoreCase(idInventario)) {
                this.medicamentoSelect = item;
                if (medicamentoSelect.getCantidadActual() == 0) {
                    String valTemp = Integer.toString(medicamentoSelect.getCantidadActual());
                    cantidadSurtida = valTemp;
                    bandera = true;
                } else {
                    bandera = false;
                    cantidadSurtida = "1";
                }
                break;
            }
        }
    }

    public void handleUnSelectMedicamento() {
        skuSap = new ClaveProveedorBarras_Extend();

    }

    public void insertaCantidadSurtida() {
        this.cantidadSurtida = this.cantidadSolicitada;
    }

    public void addMedicamento() {
        try {
            if (permiso.isPuedeCrear()) {
                if (this.medicamentoSelect == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe buscar y seleccionar medicamento para poder agregarlo!! ", null);
                    return;
                }
                if (this.eliminarCodigo) {
                    String msjError = eliminarMedicamentoColectivo();
                    if (msjError.isEmpty()) {
                        Mensaje.showMessage("Info", RESOURCES.getString("surtimiento.ok.eliminar"), null);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, null);
                    }
                    this.codigoBarras = "";
                    this.cantidadSolicitada = "1";
                    this.cantidadSurtida = "1";
                    this.comentarios = "";
                    this.eliminarCodigo = false;
                    return;
                }

                if (!existsMedtoEnLista()) {
                    String msjError = validarMedicamento();
                    if (msjError != null) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, null);
                        PrimeFaces.current().ajax().addCallbackParam("error", true);
                        return;
                    }
                    //Se crean uuid de idReabastoInsumo                   
                    String idReabastoInsumo = Comunes.getUUID();
                    //Se llena objeto de Reabasto Insumo 
                    ReabastoInsumoExtended unInsumo = new ReabastoInsumoExtended();
                    unInsumo.setActivo(Constantes.ES_ACTIVO);
                    unInsumo.setIdReabastoInsumo(idReabastoInsumo);
                    unInsumo.setIdReabasto(this.reabastoSelect.getIdReabasto());
                    unInsumo.setIdInsumo(medicamentoSelect.getIdMedicamento());
                    unInsumo.setClave(medicamentoSelect.getClaveInstitucional());
                    unInsumo.setNombreComercial(medicamentoSelect.getNombreCorto());
                    unInsumo.setPiezasCaja(medicamentoSelect.getCantidadXCaja());
                    //ToDO Revisar si esta parte es necesaria
                    /*ReabastoInsumo datosPC = insumoService.obtenerMaxMinReorCantActual(idEstructura, medicamentoSelect.getIdMedicamento());
                       if(datosPC != null) {
                            unInsumo.setMinimo(datosPC.getMinimo());
                            unInsumo.setMaximo(datosPC.getMaximo());
                            unInsumo.setReorden(datosPC.getReorden());
                            unInsumo.setCantidadActual(datosPC.getCantidadActual());
                            unInsumo.setIdAlmacenPuntosControl(datosPC.getIdAlmacenPuntosControl());
                       }*/

                    //Se obtiene su presentacion comercial de salida
                    PresentacionMedicamento pm = presentacionMedicamentoService.obtenerPorId(medicamentoSelect.getIdPresentacionSalida());
                    unInsumo.setCantidadRecibida(0);
                    unInsumo.setInsertFecha(new Date());
                    unInsumo.setInsertIdUsuario(usuarioSession.getIdUsuario());
                    unInsumo.setPresentacion(pm.getNombrePresentacion());
                    unInsumo.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                    unInsumo.setClaveInstitucional(medicamentoSelect.getClaveInstitucional());
                    unInsumo.setNombreCorto(medicamentoSelect.getNombreCorto());
                    unInsumo.setNombrePresentacion(pm.getNombrePresentacion());
                    unInsumo.setFactorTransformacion(medicamentoSelect.getFactorTransformacion());
                    unInsumo.setObservaciones(this.comentarios);
                    unInsumo.setCantidadActual(medicamentoSelect.getCantidadActual());

                    List<ReabastoEnviadoExtended> listaDetalleReabIns = new ArrayList<>();
                    ReabastoEnviadoExtended reabastoEnviado = new ReabastoEnviadoExtended();
                    String idReabastoEnviado = Comunes.getUUID();
                    reabastoEnviado.setIdReabastoEnviado(idReabastoEnviado);
                    reabastoEnviado.setIdReabastoInsumo(idReabastoInsumo);
                    if (medicamentoSelect.getPresentacionComercial() == 1) {
                        reabastoEnviado.setCantidadEnviado(Integer.parseInt(this.cantidadSurtida) * medicamentoSelect.getCantidadXCaja());
                        unInsumo.setCantidadSolicitada(Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja());
                        unInsumo.setCantidadComprometida(Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja());
                        unInsumo.setCantidadSurtida(Integer.parseInt(this.cantidadSurtida) * medicamentoSelect.getCantidadXCaja());
                    } else {
                        reabastoEnviado.setCantidadEnviado(Integer.parseInt(this.cantidadSurtida));
                        unInsumo.setCantidadSolicitada(Integer.parseInt(this.cantidadSolicitada));
                        unInsumo.setCantidadComprometida(Integer.parseInt(this.cantidadSolicitada));
                        unInsumo.setCantidadSurtida(Integer.parseInt(this.cantidadSurtida));
                    }
                    reabastoEnviado.setIdInsumo(medicamentoSelect.getIdMedicamento());
                    reabastoEnviado.setIdEstructura(reabastoSelect.getIdEstructuraPadre());
                    reabastoEnviado.setLoteEnv(medicamentoSelect.getLote());
                    reabastoEnviado.setFechaCad(medicamentoSelect.getFechaCaducidad());
                    reabastoEnviado.setCantidadXCaja(medicamentoSelect.getCantidadXCaja());
                    reabastoEnviado.setInsertFecha(new Date());
                    reabastoEnviado.setInsertIdUsuario(usuarioSession.getIdUsuario());
                    reabastoEnviado.setLote(medicamentoSelect.getLote());
                    reabastoEnviado.setFechaCaducidad(medicamentoSelect.getFechaCaducidad());
                    reabastoEnviado.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                    reabastoEnviado.setIdInventarioSurtido(medicamentoSelect.getIdInventario());
                    reabastoEnviado.setIdMedicamento(medicamentoSelect.getIdMedicamento());
                    listaDetalleReabIns.add(reabastoEnviado);
                    unInsumo.setListaDetalleReabIns(listaDetalleReabIns);
                    this.listaReabastoInsumo.add(unInsumo);
                    if (this.editarOrden) {
                        this.insumoList.add(unInsumo);
                    }
                }
                this.comentarios = "";
                this.cantidadSolicitada = "1";
                this.cantidadSurtida = "1";
                bandera = false;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Agregar Insumo: {}", ex.getMessage());
        }
    }

    private boolean existsMedtoEnLista() {
        boolean exits = Constantes.INACTIVO;
        boolean existEnviado = Constantes.INACTIVO;
        int i = 0;
        Integer cantSurt = 0;
        Integer cantSol = 0;
        if (medicamentoSelect.getPresentacionComercial() == 1) {
            cantSurt = Integer.parseInt(this.cantidadSurtida) * medicamentoSelect.getCantidadXCaja();
            cantSol = Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja();
        } else {
            cantSurt = Integer.parseInt(this.cantidadSurtida);
            cantSol = Integer.parseInt(this.cantidadSolicitada);
        }

        for (ReabastoInsumoExtended aux : listaReabastoInsumo) {
            String clave = "";
            if (this.editarOrden) {
                clave = aux.getClaveInstitucional();
            } else {
                clave = aux.getClave();
            }
            if (clave.contains(medicamentoSelect.getClaveInstitucional())) {
                exits = Constantes.ACTIVO;
                for (ReabastoEnviadoExtended reabastoEnviado : aux.getListaDetalleReabIns()) {
                    if (reabastoEnviado.getLote().trim().toUpperCase().equals(medicamentoSelect.getLote().trim().toUpperCase())
                            && reabastoEnviado.getFechaCaducidad().equals(medicamentoSelect.getFechaCaducidad())) {
                        int catTotal = reabastoEnviado.getCantidadEnviado() + cantSurt;
                        if (medicamentoSelect.getCantidadActual() >= catTotal) {
                            aux.setCantidadSolicitada(aux.getCantidadSolicitada() + cantSol);
                            aux.setCantidadSurtida(aux.getCantidadSurtida() + cantSurt);
                            aux.setCantidadComprometida(aux.getCantidadComprometida() + cantSol);
                            reabastoEnviado.setCantidadEnviado(catTotal);
                            reabastoEnviado.setCantidadRecibida(reabastoEnviado.getCantidadRecibida() + cantSurt);
                            existEnviado = Constantes.ACTIVO;
                            break;
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantInventario"), null);
                            this.cantidadSolicitada = "1";
                            this.cantidadSurtida = "1";
                            existEnviado = Constantes.ACTIVO;
                            break;
                        }
                    }
                }
                if (!existEnviado) {
                    if (this.medicamentoSelect.getCantidadActual() < cantSurt) {
                        this.cantidadSolicitada = "1";
                        this.cantidadSurtida = "1";
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantInventario"), null);
                        existEnviado = Constantes.ACTIVO;
                    } else {
                        aux.setCantidadSolicitada(aux.getCantidadSolicitada() + cantSol);
                        aux.setCantidadSurtida(aux.getCantidadSurtida() + cantSurt);
                        aux.setCantidadComprometida(aux.getCantidadComprometida() + cantSol);
                        ReabastoEnviadoExtended reabastoEnviado = new ReabastoEnviadoExtended();
                        reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                        reabastoEnviado.setIdReabastoInsumo(aux.getIdReabastoInsumo());
                        reabastoEnviado.setCantidadEnviado(cantSurt);
                        reabastoEnviado.setIdInsumo(medicamentoSelect.getIdMedicamento());
                        reabastoEnviado.setIdEstructura(reabastoSelect.getIdEstructuraPadre());
                        reabastoEnviado.setLoteEnv(medicamentoSelect.getLote());
                        reabastoEnviado.setFechaCad(medicamentoSelect.getFechaCaducidad());
                        reabastoEnviado.setCantidadXCaja(medicamentoSelect.getCantidadXCaja());
                        reabastoEnviado.setInsertFecha(new Date());
                        reabastoEnviado.setInsertIdUsuario(usuarioSession.getIdUsuario());
                        reabastoEnviado.setLote(medicamentoSelect.getLote());
                        reabastoEnviado.setFechaCaducidad(medicamentoSelect.getFechaCaducidad());
                        reabastoEnviado.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                        reabastoEnviado.setIdInventarioSurtido(medicamentoSelect.getIdInventario());
                        reabastoEnviado.setIdMedicamento(medicamentoSelect.getIdMedicamento());
                        aux.getListaDetalleReabIns().add(reabastoEnviado);
                    }
                }
                break;
            }
            i++;
        }
        return exits;
    }

    private String validarMedicamento() {
        String resp = null;
        if (this.activaColectivoServicios) {
            if (this.medicamentoSelect.getPresentacionComercial() == Constantes.ES_ACTIVO) {
                if (this.medicamentoSelect.getCantidadActual() < (Integer.parseInt(this.cantidadSurtida) * this.medicamentoSelect.getCantidadXCaja())) {
                    resp = "No puede surtir una cantidad mayor a la existente.";
                    this.cantidadSolicitada = "1";
                    this.cantidadSurtida = "1";
                    return resp;
                }
            } else {
                if (this.medicamentoSelect.getCantidadActual() < Integer.parseInt(this.cantidadSurtida)) {
                    resp = "No puede surtir una cantidad mayor a la existente.";
                    this.cantidadSolicitada = "1";
                    this.cantidadSurtida = "1";
                    return resp;
                }
            }
        }
        return resp;
    }

    public void eliminarInsumo(String idMedicamento) {
        for (short i = 0; i < this.listaReabastoInsumo.size(); i++) {
            ReabastoInsumoExtended insumoExtended = this.listaReabastoInsumo.get(i);
            if (insumoExtended.getIdInsumo().equalsIgnoreCase(idMedicamento)) {
                if (this.editarOrden) {
                    this.listaReabastoInsumoElimiminar.add(insumoExtended);
                    this.listaReabastoInsumo.remove(i);
                    break;
                } else {
                    this.listaReabastoInsumo.remove(i);
                    break;
                }
            }
        }
        if (!this.insumoList.isEmpty()) {
            for (short i = 0; i < this.insumoList.size(); i++) {
                ReabastoInsumoExtended insumoExtended = this.insumoList.get(i);
                if (insumoExtended.getIdInsumo().equalsIgnoreCase(idMedicamento)) {
                    this.insumoList.remove(i);
                    break;
                }
            }
        }
    }

    private String eliminarMedicamentoColectivo() {
        String msgError = "";
        Integer cantSurtida = 0;
        String idReabastoEnviado = "";
        String idReabastoInsumo = "";
        try {
            if (permiso.isPuedeCrear()) {
                for (ReabastoInsumoExtended unInsumo : this.listaReabastoInsumo) {
                    for (short i = 0; i < unInsumo.getListaDetalleReabIns().size(); i++) {
                        ReabastoEnviadoExtended item = unInsumo.getListaDetalleReabIns().get(i);
                        if (item.getLote().equalsIgnoreCase(this.medicamentoSelect.getLote())
                                && item.getIdMedicamento().equals(medicamentoSelect.getIdMedicamento())) {
                            if (medicamentoSelect.getPresentacionComercial() == 1) {
                                cantSurtida = item.getCantidadEnviado() - Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja();
                            } else {
                                cantSurtida = item.getCantidadEnviado() - Integer.parseInt(this.cantidadSolicitada);
                            }
                            if (cantSurtida == 0) {
                                idReabastoEnviado = unInsumo.getListaDetalleReabIns().get(i).getIdReabastoEnviado();
                                unInsumo.getListaDetalleReabIns().remove(i);
                                msgError = "El medicamento elimino correctamente";
                                if (unInsumo.getListaDetalleReabIns().isEmpty()) {
                                    this.listaReabastoInsumo.remove(unInsumo);
                                    idReabastoInsumo = unInsumo.getIdReabastoInsumo();
                                }
                                if (this.editarOrden) {
                                    //Eliminar de base de datos reabastoEnviado y reabastoInsumo
                                    if (!idReabastoEnviado.isEmpty()) {
                                        reabastoEnviadoService.eliminar(new ReabastoEnviado(idReabastoEnviado));
                                    }
                                    if (!idReabastoInsumo.isEmpty()) {
                                        reabastoInsumoService.eliminar(new ReabastoInsumo(idReabastoInsumo));
                                    }
                                    eliminarMedicamentoListaAuxiliar(cantSurtida);
                                }
                                break;
                            } else {
                                if (cantSurtida > 0) {
                                    unInsumo.getListaDetalleReabIns().get(i).setCantidadEnviado(cantSurtida);
                                    unInsumo.setCantidadSurtida(cantSurtida);
                                    msgError = "El medicamento elimino correctamente";
                                } else {
                                    msgError = "No se puede eliminar mas de lo que se tiene a surtir";
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Eliminar Insumo: {}", ex.getMessage());
        }
        return msgError;
    }

    private void eliminarMedicamentoListaAuxiliar(Integer cantSurtida) {
        // Eliminar de Lista creada como auxiliar para editar
        for (ReabastoInsumoExtended reabastoInsumo : this.insumoList) {
            for (short j = 0; j < reabastoInsumo.getListaDetalleReabIns().size(); j++) {
                ReabastoEnviadoExtended itemj = reabastoInsumo.getListaDetalleReabIns().get(j);
                if (itemj.getLote().equalsIgnoreCase(this.medicamentoSelect.getLote())
                        && itemj.getIdMedicamento().equals(medicamentoSelect.getIdMedicamento())) {
                    if (cantSurtida == 0) {
                        reabastoInsumo.getListaDetalleReabIns().remove(j);
                        this.insumoList.remove(reabastoInsumo);
                    }
                    if (cantSurtida > 0) {
                        reabastoInsumo.getListaDetalleReabIns().get(j).setCantidadEnviado(cantSurtida);
                        reabastoInsumo.setCantidadSurtida(cantSurtida);
                    }
                }
            }
        }
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

    public EntidadHospitalaria getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadHospitalaria entidad) {
        this.entidad = entidad;
    }

    public int getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(int tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public int getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(int tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public List<CatalogoGeneral> getTipoInsumoList() {
        return tipoInsumoList;
    }

    public void setTipoInsumoList(List<CatalogoGeneral> tipoInsumoList) {
        this.tipoInsumoList = tipoInsumoList;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isFarmacia() {
        return farmacia;
    }

    public void setFarmacia(boolean farmacia) {
        this.farmacia = farmacia;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(String cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public String getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(String cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    public SesionMB getSesion() {
        return sesion;
    }

    public void setSesion(SesionMB sesion) {
        this.sesion = sesion;
    }

    public ClaveProveedorBarras_Extend getSkuSap() {
        return skuSap;
    }

    public void setSkuSap(ClaveProveedorBarras_Extend skuSap) {
        this.skuSap = skuSap;
    }

    public ClaveProveedorBarras_Extend getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(ClaveProveedorBarras_Extend medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }

    public List<ClaveProveedorBarras_Extend> getSkuSapList() {
        return skuSapList;
    }

    public void setSkuSapList(List<ClaveProveedorBarras_Extend> skuSapList) {
        this.skuSapList = skuSapList;
    }

    public boolean isActivaColectivoServicios() {
        return activaColectivoServicios;
    }

    public void setActivaColectivoServicios(boolean activaColectivoServicios) {
        this.activaColectivoServicios = activaColectivoServicios;
    }

    public boolean isEditarOrden() {
        return editarOrden;
    }

    public void setEditarOrden(boolean editarOrden) {
        this.editarOrden = editarOrden;
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

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public ReabastoInsumoExtended getReabastoInsumoExtend() {
        return reabastoInsumoExtend;
    }

    public void setReabastoInsumoExtend(ReabastoInsumoExtended reabastoInsumoExtend) {
        this.reabastoInsumoExtend = reabastoInsumoExtend;
    }

    public List<ReabastoInsumoExtended> getListaReabastoInsumoElimiminar() {
        return listaReabastoInsumoElimiminar;
    }

    public void setListaReabastoInsumoElimiminar(List<ReabastoInsumoExtended> listaReabastoInsumoElimiminar) {
        this.listaReabastoInsumoElimiminar = listaReabastoInsumoElimiminar;
    }

    public boolean isGeneraReporte() {
        return generaReporte;
    }

    public void setGeneraReporte(boolean generaReporte) {
        this.generaReporte = generaReporte;
    }

    public boolean isBandera() {
        return bandera;
    }

    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }

    public boolean isCrearReceta() {
        return crearReceta;
    }

    public void setCrearReceta(boolean crearReceta) {
        this.crearReceta = crearReceta;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
