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
public class CancelarSurtIntColectivoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CancelarSurtIntColectivoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    
    private String idEstructura;
    private String textoBusqueda;
    private Usuario usuarioSession;
    private String codigoBarras;
    private List<Estructura> listaEstructuras;
    private List<ReabastoExtended> listaReabasto;
    private List<ReabastoInsumoExtended> listaReabastoInsumo;
    private ReabastoExtended reabastoSelect;
    private boolean eliminarCodigo;
    private boolean administrador;    
    private SesionMB sesion;
    private List<Usuario> listaMedicos;
    private String idMedico;
    private String idEstructuraCombo;
    private boolean editarOrden;
    private List<ReabastoInsumoExtended> insumoList;
    private ClaveProveedorBarras_Extend skuSap;
    private ClaveProveedorBarras_Extend medicamentoSelect;
    private String xcantidad;
    private String comentarios;
    private String cantidadSolicitada;
    private String cantidadSurtida;
    private boolean mostrarModalInsumos;
    private boolean generaReporte;
    private String errorOperacion;    
    
    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;
        
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient ReabastoService reabastoService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReabastoEnviadoService reabastoEnviadoService;
        
    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
        
    private boolean activo;
    private boolean isAdmin;
    private boolean farmacia;
    private String archivo2;
    private EntidadHospitalaria entidad;
    private int tipoOrden;
    private String tipoAlmacen;
    private Estructura estructuraSelect;
    private String idPadre;
    private int tipoInsumo;
    private List<CatalogoGeneral> tipoInsumoList;    
    private PermisoUsuario permiso;    
    
    @Autowired
    private transient EstructuraAlmacenServicioService estructuraAlmacenServicioService;

    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;

    private List<ClaveProveedorBarras_Extend> skuSapList;
    private boolean activaCancelarSurtIntColectivo;
    private Date fechaActual;
    private ReabastoInsumoExtended reabastoInsumoExtend;
    private List<ReabastoInsumoExtended> listaReabastoInsumoElimiminar;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            errorOperacion = "surtimiento.error.operacion";
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.RECETACOLECTIVA.getSufijo());            
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            this.sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            generaReporte = Constantes.INACTIVO;
            validarUsuarioAdministrador();
            alimentarComboAlmacen();
            if (!listaEstructuras.isEmpty()) {
                idEstructuraCombo = listaEstructuras.get(0).getIdEstructura();
            }
            obtenerOrdenesReabasto();
            fechaActual = new java.util.Date();  
            activaCancelarSurtIntColectivo = sesion.isActivaCancelarSurtIntColectivo();
            obtenerEntidadHospitalaria();
            obtenerTipoInsumos();
            esAdministrador();
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo init :: {}", ex.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {        
        this.eliminarCodigo = false;
        this.administrador = false;
        this.idEstructura = "";
        this.textoBusqueda = "";        
        this.cantidadSurtida = "1";
        this.codigoBarras = "";
        this.xcantidad = "1";
        this.comentarios = "";
        this.cantidadSolicitada = "1";                
        this.editarOrden = false;
        this.mostrarModalInsumos = false;
        this.usuarioSession = new Usuario();
        this.listaReabastoInsumoElimiminar = new ArrayList<>();
        this.listaEstructuras = new ArrayList<>();
        this.listaReabasto = new ArrayList<>();
        this.listaReabastoInsumo = new ArrayList<>();
        this.reabastoSelect = new ReabastoExtended();
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.tipoInsumoList = new ArrayList<>();
        this.insumoList = new ArrayList<>();
        this.listaMedicos = new ArrayList<>();
    }

    public void limpiar() {
        this.skuSap = new ClaveProveedorBarras_Extend();
        this.insumoList = new ArrayList<>();
        this.eliminarCodigo = false;        
        this.editarOrden = false;        
        this.mostrarModalInsumos = false;        
        this.comentarios = "";
        this.codigoBarras = "";
        this.xcantidad = "1";
        this.cantidadSolicitada = "1";
        this.cantidadSurtida = "1";
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
            Estructura estructura = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            entidad = entidadHospitalariaService.obtenerEntidadById(estructura.getIdEntidadHospitalaria());
            if (entidad == null) {
                entidad = new EntidadHospitalaria();
                entidad.setDomicilio("");
                entidad.setNombre("");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }



    private void obtenerListaMedicosActivos() {
        try {
            listaMedicos = usuarioService.obtenerListaMedicosActivos();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Medicos Activos: {}", ex.getMessage());
        }
    }

     /**
     * obtiene la lista de tipos de insumo que puede solicitar
     */
    private void obtenerTipoInsumos() {
        LOGGER.trace("Load Insumos");
        try {
            tipoInsumoList = catalogoGeneralService.obtenerCatalogosPorGrupo(Constantes.TIPO_INSUMO);
        } catch (Exception e) {
            LOGGER.error("Error al obtener Insumos: {}", e.getMessage());
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
            if (permiso.isPuedeCrear() && tipoOrden > 0) {
                Estructura estr = estructuraService.obtener(new Estructura(idEstructura));
                reabastoSelect = new ReabastoExtended();
                reabastoSelect.setIdReabasto(Comunes.getUUID());

                // Estructura solicitante siempre es la seleccionada por el usuario
                reabastoSelect.setIdEstructura(estr.getIdEstructura());

                // Se busca la estructura almacen para encontrar el padre
                EstructuraAlmacenServicio eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(idEstructura, null));
                if (eas == null) {
                    eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(null, estr.getIdEstructuraPadre()));
                    if (eas == null) {
                        estr = estructuraService.obtener(new Estructura(estr.getIdEstructuraPadre()));
                        eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(estr.getIdEstructura(), null));
                        if (eas == null) {
                            estr = estructuraService.obtener(new Estructura(estr.getIdEstructuraPadre()));
                            eas = estructuraAlmacenServicioService.obtener(new EstructuraAlmacenServicio(estr.getIdEstructura(), null));
                        }
                    }
                }
                Estructura estru = estructuraService.obtener(new Estructura(eas.getIdEstructuraAlmacen()));
                reabastoSelect.setAlmacen(estru.getNombre());
                reabastoSelect.setIdEstructuraPadre(estru.getIdEstructura());

                reabastoSelect.setIdTipoOrden(tipoOrden);
                reabastoSelect.setFechaSolicitud(new Date());
                reabastoSelect.setIdUsuarioSolicitud(usuarioSession.getIdUsuario());
                reabastoSelect.setInsertFecha(new java.util.Date());
                reabastoSelect.setInsertIdUsuario(usuarioSession.getIdUsuario());
                status = Constantes.ACTIVO;
            }
        } catch (Exception e) {
            LOGGER.error("Error al Obtener los datos: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    private void esAdministrador() {
        isAdmin = Constantes.INACTIVO;
        try {
            if (usuarioSession != null && usuarioSession.getIdEstructura() != null) {
                Estructura es = estructuraService.obtener(new Estructura(usuarioSession.getIdEstructura()));
                if (es.getIdTipoAreaEstructura() == 1 && es.getIdTipoAlmacen().equals(1)) {
                    isAdmin = Constantes.ACTIVO;
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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

            reabasto.setIdEstructura(idEstructuraCombo);
            Integer idTipoAlmacen = null;
            this.listaReabasto = this.reabastoService.obtenerReabastoExtends(reabasto, listEstatusReabasto, idTipoAlmacen);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarTablaOrdenesReabasto :: {}", e.getMessage());
        }
    }
    
    /**
     * Metodo utilizado para poblar el combo almacen / Servicios
     */
    public void alimentarComboAlmacen() {
        try {

            Estructura estruct = new Estructura();
            estruct.setActiva(Constantes.ESTATUS_ACTIVO);
            listaEstructuras = new ArrayList<>();
            if (!this.administrador) {
                estruct.setIdTipoArea(TipoAreaEstructura_Enum.AREA.getValue());
                this.listaEstructuras.addAll(this.estructuraService.obtenerLista(estruct));

                estruct.setIdTipoArea(TipoAreaEstructura_Enum.SERVICIO.getValue());
                this.listaEstructuras.addAll(this.estructuraService.obtenerLista(estruct));

                estruct.setIdTipoArea(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                this.listaEstructuras.addAll(this.estructuraService.obtenerLista(estruct));

            } else {
                estruct.setIdTipoArea(Constantes.TIPO_AREA_ALMACEN);
                this.listaEstructuras.addAll(this.estructuraService.obtenerLista(estruct));

                estruct.setIdTipoArea(TipoAreaEstructura_Enum.AREA.getValue());
                this.listaEstructuras.addAll(this.estructuraService.obtenerLista(estruct));

                estruct.setIdTipoArea(TipoAreaEstructura_Enum.SERVICIO.getValue());
                this.listaEstructuras.addAll(this.estructuraService.obtenerLista(estruct));

                estruct.setIdTipoArea(TipoAreaEstructura_Enum.ESPECIALIDAD.getValue());
                this.listaEstructuras.addAll(this.estructuraService.obtenerLista(estruct));
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
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
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), "");
                return;
            }
            List<ReabastoInsumo> lisReabastoInsumo = null;
            List<ReabastoEnviado> lisReabastoEnviado = null;

            List<Inventario> listaInventarios = new ArrayList<>();
            List<MovimientoInventario> listaMovimientos = new ArrayList<>();
            List<ReabastoEnviado> listaReabastoEnviadoUpdate = new ArrayList<>();

            Reabasto reabasto = new Reabasto();
            reabasto.setIdReabasto(reabastoExtended.getIdReabasto());
            reabasto.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_CANCELADA);

            ReabastoInsumo reabasInsumo = new ReabastoInsumo();
            reabasInsumo.setIdReabasto(reabasto.getIdReabasto());

            lisReabastoInsumo = reabastoInsumoService.obtenerLista(reabasInsumo);
            if (lisReabastoInsumo != null) {
                lisReabastoEnviado = reabastoEnviadoService.
                        obtenerReabastoEnviadoByListaReabastoInsumo(lisReabastoInsumo);
            }

            for (ReabastoEnviado item : lisReabastoEnviado) {
                ReabastoEnviado reabastEnviado = new ReabastoEnviado();
                reabastEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                reabastEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_CANCELADA);
                reabastEnviado.setUpdateFecha(new Date());
                reabastEnviado.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                listaReabastoEnviadoUpdate.add(reabastEnviado);

                Inventario invent = new Inventario();
                invent.setIdInventario(item.getIdInventarioSurtido());
                invent.setCantidadActual(item.getCantidadEnviado());
                invent.setUpdateFecha(new Date());
                invent.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                listaInventarios.add(invent);

                MovimientoInventario movimientoInv = new MovimientoInventario();
                movimientoInv.setIdMovimientoInventario(Comunes.getUUID());
                movimientoInv.setIdTipoMotivo(TipoMotivo_Enum.DEV_DEVOLUCION_POR_CANCELACION_DE_RECETA.getMotivoValue());                
                movimientoInv.setFecha(new Date());
                movimientoInv.setIdUsuarioMovimiento(this.usuarioSession.getIdUsuario());
                movimientoInv.setIdEstrutcuraOrigen(item.getIdEstructura());
                movimientoInv.setIdEstrutcuraDestino(item.getIdEstructura());
                movimientoInv.setIdInventario(item.getIdInventarioSurtido());
                movimientoInv.setCantidad(item.getCantidadEnviado());
                movimientoInv.setFolioDocumento(reabastoExtended.getFolio());
                listaMovimientos.add(movimientoInv);
            }
            reabasInsumo.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_CANCELADA);
            reabasInsumo.setUpdateFecha(new Date());
            reabasInsumo.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            this.reabastoService.cancelarOrdenReabastoColectivo(reabasto, reabasInsumo,
                    listaReabastoEnviadoUpdate, listaInventarios, listaMovimientos);
            obtenerOrdenesReabasto();
            this.mostrarModalInsumos = false;
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo cancelarOrdenReabasto :: {}", ex.getMessage());
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
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", ex.getMessage());
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
                List<Integer> listaEstatusReabasto = new ArrayList<>();
                listaEstatusReabasto.add(EstatusReabasto_Enum.REGISTRADA.getValue());
                this.idMedico = reabastoSelect.getIdMedico();
                this.listaReabastoInsumo = reabastoInsumoService.
                        obtenerReabastoInsumoExtendsSurt(
                                this.reabastoSelect.getIdReabasto(),
                                this.reabastoSelect.getIdEstructuraPadre(), listaEstatusReabasto);
                for (short i = 0; i < this.listaReabastoInsumo.size(); i++) {
                    ReabastoInsumo item = this.listaReabastoInsumo.get(i);
                    this.listaReabastoInsumo.get(i).setListaDetalleReabIns(
                            reabastoEnviadoService.obtenerListaReabastoEnviadoInv(
                                    item.getIdReabastoInsumo(), EstatusReabasto_Enum.REGISTRADA.getValue(), this.reabastoSelect.getIdEstructuraPadre()));
                }
            } else {
                this.mostrarModalInsumos = false;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo se pueden mostrar ordenes con Estatus 'Regstrada' ", null);
            }
            PrimeFaces.current().ajax().addCallbackParam("mostrarModalInsumos", mostrarModalInsumos);
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo mostrarModalSurtimiento :: {}", ex.getMessage());
        }
    }

    /**
     *
     */
    public void surtirReabastoInsumo() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), "");
                return;
            }
            if (this.reabastoSelect.getFolio().isEmpty() || this.reabastoSelect.getFolio().equals("")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe colocar un folio ", "");
                return;
            }
            if (this.idMedico == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Seleccione un Médico ", "");
                return;
            }
            if (!this.editarOrden) {
                if (this.activaCancelarSurtIntColectivo) {
                    Pattern patron = Constantes.folioRecetaColectiva;
                    Matcher folio = patron.matcher(reabastoSelect.getFolio());
                    if (!folio.matches() || reabastoSelect.getFolio().length() > 6
                            || reabastoSelect.getFolio().length() < 6) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El formato de folio no es correcto.     Ejem: 18/123", "");
                        return;
                    }
                }
                Reabasto reabastoEncontrado = reabastoService.getReabastoByFolio(this.reabastoSelect.getFolio());
                if (reabastoEncontrado != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Este folio ya existe!! " + this.reabastoSelect.getFolio(), "");
                    return;
                }
            }

            if (this.listaReabastoInsumo != null && !this.listaReabastoInsumo.isEmpty()) {
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
                }
                Reabasto reabast = new Reabasto();
                reabast.setIdReabasto(this.reabastoSelect.getIdReabasto());
                reabast.setIdUsuarioSolicitud(this.usuarioSession.getIdUsuario());
                reabast.setFechaSurtida(new Date());
                reabast.setIdUsuarioSurtida(this.usuarioSession.getIdUsuario());
                reabast.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                reabast.setFolio(this.reabastoSelect.getFolio());
                reabast.setIdEstructuraPadre(this.reabastoSelect.getIdEstructuraPadre());
                reabast.setIdEstructura(this.reabastoSelect.getIdEstructura());
                reabast.setFechaSolicitud(this.reabastoSelect.getFechaSolicitud());
                for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                    item.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                }
                boolean resp = true;
                if (!this.editarOrden) {
                    reabast.setInsertFecha(new Date());
                    reabast.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                    reabast.setIdTipoOrden(this.reabastoSelect.getIdTipoOrden());
                    reabast.setIdMedico(this.idMedico);
                    resp = this.reabastoService.guardarReabastoColectivo(reabast, this.listaReabastoInsumo, this.editarOrden, this.listaReabastoInsumoElimiminar);
                } else {
                    reabast.setUpdateFecha(new Date());
                    reabast.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
                    reabast.setInsertFecha(new Date());
                    reabast.setInsertIdUsuario(this.usuarioSession.getIdUsuario());
                    if (this.insumoList != null && !this.insumoList.isEmpty()) {
                        resp = this.reabastoService.guardarReabastoColectivo(reabast, this.insumoList, this.editarOrden, this.listaReabastoInsumoElimiminar);
                    }
                }
                if (resp) {
                    resp = this.reabastoService.
                            surtirOrdenReabastoColectivo(reabast, this.listaReabastoInsumo);
                }
                if (generaReporte) {
                    PrimeFaces.current().ajax().addCallbackParam("generaReporte", generaReporte);
                }
                if (resp) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("surtimiento.ok.surtimiento"), "");
                    obtenerOrdenesReabasto();
                    PrimeFaces.current().ajax().addCallbackParam("errorSurtir", resp);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se tiene ningun medicamento para guardar la receta colectiva ", "");
                return;
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
            LOGGER.error("Error en el metodo surtirReabastoInsumo :: {}", e.getMessage());
        }
    }

    /**
     * Metodo utilizado para guardar la informacion sin realizar el surtimiento
     */
    public void guardarRegistros() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("estr.err.permisos"), "");
                return;
            }
            if (this.reabastoSelect.getFolio().isEmpty() || this.reabastoSelect.getFolio().equals("")) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe colocar un folio ", "");
                return;
            }
            if (!this.editarOrden) {
                Reabasto reabastoEncontrado = reabastoService.getReabastoByFolio(this.reabastoSelect.getFolio());
                if (reabastoEncontrado != null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Este folio ya existe!! " + this.reabastoSelect.getFolio(), "");
                    return;
                }
            }
            if (this.activaCancelarSurtIntColectivo) {
                Pattern patron = Constantes.folioRecetaColectiva;
                Matcher folio = patron.matcher(reabastoSelect.getFolio());
                if (!folio.matches() || reabastoSelect.getFolio().length() > 6
                        || reabastoSelect.getFolio().length() < 6) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El formato de folio no es correcto.     Ejem: 18/123", "");
                    return;
                }
            }
            boolean resp = false;
            if (this.listaReabastoInsumo != null && !this.listaReabastoInsumo.isEmpty()) {
                Reabasto reabast = new Reabasto();

                reabast.setIdReabasto(this.reabastoSelect.getIdReabasto());
                reabast.setIdEstructura(this.reabastoSelect.getIdEstructura());
                reabast.setIdEstructuraPadre(this.reabastoSelect.getIdEstructuraPadre());
                reabast.setIdTipoOrden(this.reabastoSelect.getIdTipoOrden());
                reabast.setFolio(this.reabastoSelect.getFolio());
                reabast.setFechaSolicitud(this.reabastoSelect.getFechaSolicitud());
                reabast.setIdUsuarioSolicitud(this.reabastoSelect.getIdUsuarioSolicitud());
                reabast.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                reabast.setInsertFecha(this.reabastoSelect.getInsertFecha());
                reabast.setInsertIdUsuario(this.reabastoSelect.getInsertIdUsuario());
                reabast.setIdMedico(this.idMedico);
                if (!this.editarOrden) {
                    resp = this.reabastoService.guardarReabastoColectivo(reabast, this.listaReabastoInsumo, this.editarOrden, this.listaReabastoInsumoElimiminar);
                } else {
                    resp = this.reabastoService.guardarReabastoColectivo(reabast, this.insumoList, this.editarOrden, this.listaReabastoInsumoElimiminar);
                }
                if (resp) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("ok.guardar"), "");
                    PrimeFaces.current().ajax().addCallbackParam("errorSurtir", resp);
                    obtenerOrdenesReabasto();
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No se tiene ningun medicamento para guardar la receta colectiva ", "");
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
            LOGGER.error("Error en el metodo guardarRegistros :: {}", e.getMessage());
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
                for (Estructura estruct : this.listaEstructuras) {
                    if (estruct.getIdEstructura().equals(idEstructura)) {
                        idTipoAlmacen = estruct.getIdTipoAlmacen();
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
     * Metodo utilizado para buscar solicitudes de acuerdo a un paramentro de
     * busqueda
     */
    public void buscarRegistros() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), "");
                return;
            }
            Integer idTipoAlmacen = obtenerIdTipoAlmacen(this.idEstructuraCombo);

            Pattern patt = Pattern.compile(Constantes.PATRON_CAD_BUS);
            Matcher matc = patt.matcher(this.textoBusqueda);
            if (matc.matches()) {
                for (ReabastoExtended it : this.listaReabasto) {
                    if (it.getFolio().equalsIgnoreCase(this.textoBusqueda)) {
                        this.reabastoSelect = it;
                        break;
                    }
                }
                if (this.reabastoSelect != null) {
                    List<Integer> listaEstatusReabasto = new ArrayList<>();
                    listaEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
                    this.listaReabasto = reabastoService
                            .obtenerRegistrosPorCriterioDeBusqueda(this.textoBusqueda,
                                    Constantes.REGISTROS_PARA_MOSTRAR,
                                    listaEstatusReabasto,
                                    this.idEstructura, null, idTipoAlmacen);
                    this.listaReabastoInsumo = reabastoInsumoService.
                            obtenerReabastoInsumoExtends(
                                    this.reabastoSelect.getIdReabasto(),
                                    listaEstatusReabasto);
                    PrimeFaces.current().ajax().addCallbackParam("estatusModal", true);
                }
            } else {
                reabastoSelect.setTextoBusqueda(textoBusqueda);
                List<Integer> listEstatusReabasto = null;
                idEstructura = idEstructuraCombo;
                this.listaReabasto = reabastoService
                        .obtenerRegistrosPorCriterioDeBusqueda(reabastoSelect.getTextoBusqueda(),
                                Constantes.REGISTROS_PARA_MOSTRAR,
                                listEstatusReabasto,
                                this.idEstructura, null, idTipoAlmacen);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", e.getMessage());
        }
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
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("surtimiento.ok.eliminar"), "");
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
                }
                this.codigoBarras = "";
                this.xcantidad = "1";
                this.cantidadSolicitada = "1";
                this.cantidadSurtida = "1";
                this.eliminarCodigo = false;
                return;
            }
            //Separar codigo de barras 
            ReabastoEnviadoExtended medicamentoDetail = tratarCodigoDeBarras(this.codigoBarras);
            //Filtrar registros
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoDetail);
            //Validar si el medicamento esta en la lista
            if (medicamento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.medNoEncontrado"), "");
                this.codigoBarras = "";
                this.xcantidad = "1";
                return;
            }
            //Validar fecha de caducidad 
            if (medicamentoDetail.getFechaCaducidad().compareTo(new Date()) < 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medCaducado"), "");
                this.codigoBarras = "";
                this.xcantidad = "1";
                return;
            }

            //Validar que la cantidad del lote no sea insuficiente
            Inventario inventario = inventarioService.obtenerInventariosPorInsumoEstructuraYLote(
                    medicamento.getIdInsumo(), this.idEstructura,
                    medicamentoDetail.getLote());
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
                if (medicamentoDetail.getCantidadXCaja() != null) {
                    cantidadXCaja = medicamentoDetail.getCantidadXCaja();
                } else {
                    cantidadXCaja = medicamento.getFactorTransformacion();
                }

                int cantSurtida = medicamento.getCantidadSurtida() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                if (cantSurtida > medicamento.getCantidadSolicitada()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR,
                            RESOURCES.getString("surtimiento.error.cantSolMayor"), "");
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                medicamentoDetail.setCantidadXCaja(cantidadXCaja);
                medicamentoDetail.setCantidadEnviado(cantSurtida);
                medicamentoDetail.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                medicamentoDetail.setIdMedicamento(medicamento.getIdInsumo());
                medicamentoDetail.setIdEstructura(this.idEstructura);
                String idInventario = inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                        medicamento.getIdInsumo(), this.idEstructura, medicamentoDetail.getLote());
                if (idInventario == null || idInventario.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El lote no existe", "");
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                medicamentoDetail.setIdInventarioSurtido(idInventario);
                medicamento.getListaDetalleReabIns().add(medicamentoDetail);
                medicamento.setCantidadSurtida(cantSurtida);
            } else {
                if (this.xcantidad.isEmpty()) {
                    this.xcantidad = "1";
                }

                int cantidadXCaja = 0;
                if (medicamentoDetail.getCantidadXCaja() != null) {
                    cantidadXCaja = medicamentoDetail.getCantidadXCaja();
                } else {
                    cantidadXCaja = medicamento.getFactorTransformacion();
                }

                int cantSurtida = medicamento.getCantidadSurtida() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                int cantidadEnviada = cantidadXCaja * Integer.valueOf(this.xcantidad);
                if (inventario.getCantidadActual() < cantidadEnviada) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo hay " + inventario.getCantidadActual() + " existencias en el lote " + inventario.getLote(), "");
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                if (cantSurtida > medicamento.getCantidadSolicitada()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantSolMayor"), "");
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                    if (item.getLote().equalsIgnoreCase(medicamentoDetail.getLote())) {
                        if (inventario.getCantidadActual() < (item.getCantidadEnviado() + cantidadXCaja * Integer.valueOf(this.xcantidad))) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo hay " + inventario.getCantidadActual() + " existencias en el lote " + inventario.getLote(), "");
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
                medicamentoDetail.setCantidadXCaja(cantidadXCaja);
                medicamentoDetail.setCantidadEnviado(cantidadEnviada);
                medicamentoDetail.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                medicamentoDetail.setIdMedicamento(medicamento.getIdInsumo());
                medicamentoDetail.setIdEstructura(this.idEstructura);
                medicamentoDetail.setIdInventarioSurtido(
                        inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                                medicamento.getIdInsumo(), this.idEstructura, medicamentoDetail.getLote()));
                medicamento.getListaDetalleReabIns().add(medicamentoDetail);
                medicamento.setCantidadSurtida(cantSurtida);
            }
            this.codigoBarras = "";
            this.xcantidad = "1";
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reabastSurtimiento.err.agregar"), null);
        }
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
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
        }
        return registro;
    }

        
    private String eliminarLotePorCodigo() {
        String msgError = "";
        try {
            ReabastoEnviadoExtended codigoMedicamento = tratarCodigoDeBarras(this.codigoBarras);
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(codigoMedicamento);
            if (medicamento == null) {
                msgError = RESOURCES.getString("orepcio.err.medNoEncontrado");
                return msgError;
            }
            ReabastoEnviadoExtended medicamentoDetalle = obtenerDetalleMedicamento(
                    medicamento.getListaDetalleReabIns(), codigoMedicamento);
            if (medicamentoDetalle == null) {
                msgError = RESOURCES.getString("surtimiento.error.noLote");
                return msgError;
            }
            if (this.xcantidad.isEmpty()) {
                this.xcantidad = "1";
            }
            int factConversion = obtenerCandtidadMedicamento(medicamento.getIdInsumo());
            int cantidSurtida = medicamento.getCantidadSurtida()
                    - factConversion * Integer.valueOf(this.xcantidad);
            int cantidadEnviado = medicamentoDetalle.getCantidadEnviado()
                    - factConversion * Integer.valueOf(this.xcantidad);
            if (cantidadEnviado < 0) {
                msgError = RESOURCES.getString("surtimiento.error.cantElimMayor");
                return msgError;
            }
            medicamento.setCantidadSurtida(cantidSurtida);
            medicamentoDetalle.setCantidadEnviado(cantidadEnviado);
            if (medicamentoDetalle.getCantidadEnviado() == 0) {
                eliminarDetalleByFolio(medicamento.getListaDetalleReabIns(), codigoMedicamento);
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
        }
        return msgError;
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

        ReabastoEnviadoExtended registroMed = null;
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
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
        }
        return registroMed;
    }

    private void eliminarDetalleByFolio(
            List<ReabastoEnviadoExtended> listaDetalle,
            ReabastoEnviadoExtended medicamentoCodigo) {
        try {
            for (short i = 0; i < listaDetalle.size(); i++) {
                ReabastoEnviadoExtended it = listaDetalle.get(i);
                if (it.getLote().equalsIgnoreCase(medicamentoCodigo.getLote())) {
                    listaDetalle.remove(i);
                    return;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo eliminarDetalleByFolio :: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errorOperacion), "");
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
        ReabastoEnviadoExtended reabastoDetalle = new ReabastoEnviadoExtended();
        CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
        if (ci != null) {
            reabastoDetalle.setIdMedicamento(ci.getClave());
            reabastoDetalle.setLote(ci.getLote());
            try {
                reabastoDetalle.setFechaCaducidad(ci.getFecha());
            } catch (Exception ex) {
                LOGGER.error("Error en el metodo tratarCodigoDeBarras :: Al parsear fecha: {}", ex.getMessage());
            }
            if (ci.getCantidad() != null) {
                reabastoDetalle.setCantidadXCaja(ci.getCantidad());
            } else {
                Integer cantidadXcaja = 1;
                Inventario parametros = new Inventario();
                parametros.setIdInsumo(reabastoDetalle.getIdMedicamento());
                parametros.setLote(reabastoDetalle.getLote());
                parametros.setIdEstructura(this.idEstructura);
                Inventario inventario = null;
                try {
                    inventario = inventarioService.
                            obtenerInventarioPorClveInstEstructuraYLote(parametros);
                } catch (Exception ex) {
                    LOGGER.error("Error en el metodo tratarCodigoDeBarras :: Al obtener Inventario: {}", ex.getMessage());
                }
                if (inventario != null && inventario.getCantidadXCaja() != null && inventario.getCantidadXCaja() > 0) {
                    cantidadXcaja = inventario.getCantidadXCaja();
                }
                reabastoDetalle.setCantidadXCaja(cantidadXcaja);
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
                reabastoDetalle.setIdMedicamento(claveProveedorBarras.getClaveInstitucional());
                reabastoDetalle.setCantidadXCaja(claveProveedorBarras.getCantidadXCaja());
                reabastoDetalle.setLote(claveProveedorBarras.getClaveProveedor());
                reabastoDetalle.setFechaCaducidad(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Valor no valido", null);
            }
        }
        return reabastoDetalle;
    }

    private Integer obtenerCandtidadMedicamento(String idMedicamento) {
        int cantidadMed = 0;
        try {
            cantidadMed = reabastoInsumoService.
                    obtenerCantidadMedicamento(idMedicamento);

        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarMedicamento :: {}", e.getMessage());
        }
        return cantidadMed;
    }

    public void imprimirReporte(ReabastoExtended reabastoExtended) throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            Surtimiento_Extend itemSelect = new Surtimiento_Extend();
            itemSelect.setNombrePaciente(reabastoExtended.getNombreEstructura());
            itemSelect.setFolio(reabastoExtended.getFolio());
            Usuario medico = usuarioService.obtenerUsuarioPorId(
                    (reabastoExtended.getIdMedico() != null) ? reabastoExtended.getIdMedico() : "");
            if (medico == null) {
                itemSelect.setNombreMedico("");
            } else {
                itemSelect.setNombreMedico(medico.getNombre() + " " + medico.getApellidoPaterno() + " " + medico.getApellidoMaterno());
            }
            Prescripcion prescripcion = new Prescripcion();
            prescripcion.setFolio(reabastoExtended.getFolio());
            prescripcion.setFechaPrescripcion(reabastoExtended.getFechaSolicitud());
            EntidadHospitalaria entHospitalaria = this.entidadHospitalariaService.obtener(new EntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeReporteColectivaChiconcuac(
                    prescripcion,
                    entHospitalaria,
                    itemSelect,
                    this.usuarioSession.getNombreUsuario());

            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ticket_%s.pdf", itemSelect.getFolio().substring(3)));
            }
        } catch (Exception ex) {
            LOGGER.error("Error al generar la Impresión: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void imprimir(ReabastoExtended reabastoExtended) throws Exception {
        LOGGER.debug("mx.mc.magedbean.DispensacionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            Surtimiento_Extend itemSelect = new Surtimiento_Extend();
            itemSelect.setNombrePaciente(reabastoExtended.getNombreEstructura());
            itemSelect.setFolio(reabastoExtended.getFolio());
            itemSelect.setNombreMedico("");

            Prescripcion prescripcion = new Prescripcion();
            prescripcion.setFolio(reabastoExtended.getFolio());
            prescripcion.setFechaPrescripcion(reabastoExtended.getFechaSolicitud());
            EntidadHospitalaria entidadHospital = this.entidadHospitalariaService.obtener(new EntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimeSurtimientoPrescColectivaChiconcuac( prescripcion, entidadHospital, itemSelect,
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

    
    public void handleSelectMedicamento(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtPrescripcionExtMB.handleSelect()");
        this.skuSap = (ClaveProveedorBarras_Extend) e.getObject();
        String idInventario = skuSap.getIdInventario();
        for (ClaveProveedorBarras_Extend it : this.skuSapList) {
            if (it.getIdInventario().equalsIgnoreCase(idInventario)) {
                this.medicamentoSelect = it;
                break;
            }
        }
    }
    
    public List<ClaveProveedorBarras_Extend> autocompleteMedicamento(String query) {
        this.skuSapList = new ArrayList<>();
        boolean rManual = false;
        try {
            if (this.activaCancelarSurtIntColectivo) {
                List<Integer> listaSubCategorias = new ArrayList<>();
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExtChiconcuac(
                        query, reabastoSelect.getIdEstructuraPadre(), this.usuarioSession.getIdUsuario(), listaSubCategorias, rManual);
            } else {
                this.skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasExt(
                        query, reabastoSelect.getIdEstructura(), this.usuarioSession.getIdUsuario());
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", e.getMessage());
        }
        return skuSapList;
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
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Se debe buscar y seleccionar medicamento para poder agregarlo!! ", "");
                    return;
                }
                if (this.eliminarCodigo) {
                    String msjError = eliminarMedicamentoColectivo();
                    if (msjError.isEmpty()) {
                        Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("surtimiento.ok.eliminar"), "");
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
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
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
                        PrimeFaces.current().ajax().addCallbackParam("error", true);
                        return;
                    }
                    //Se crean uuid de idReabastoInsumo                   
                    String idReabastoInsumo = Comunes.getUUID();
                    //Se llena objeto de Reabasto Insumo 
                    ReabastoInsumoExtended unInsumoExt = new ReabastoInsumoExtended();
                    unInsumoExt.setActivo(Constantes.ES_ACTIVO);
                    unInsumoExt.setIdReabastoInsumo(idReabastoInsumo);
                    unInsumoExt.setIdReabasto(this.reabastoSelect.getIdReabasto());
                    unInsumoExt.setIdInsumo(medicamentoSelect.getIdMedicamento());
                    unInsumoExt.setClave(medicamentoSelect.getClaveInstitucional());
                    unInsumoExt.setNombreComercial(medicamentoSelect.getNombreCorto());
                    unInsumoExt.setPiezasCaja(medicamentoSelect.getCantidadXCaja());
                    /*ToDO Revisar si esta parte es necesaria
                   ReabastoInsumo datosPC = insumoService.obtenerMaxMinReorCantActual(idEstructura, medicamentoSelect.getIdMedicamento());
                   if(datosPC != null) {
                        unInsumo.setMinimo(datosPC.getMinimo());
                        unInsumo.setMaximo(datosPC.getMaximo());
                        unInsumo.setReorden(datosPC.getReorden());
                        unInsumo.setCantidadActual(datosPC.getCantidadActual());
                        unInsumo.setIdAlmacenPuntosControl(datosPC.getIdAlmacenPuntosControl());
                   }*/

                    //Se obtiene su presentacion comercial de salida
                    PresentacionMedicamento pm = presentacionMedicamentoService.obtenerPorId(medicamentoSelect.getIdPresentacionSalida());
                    if (medicamentoSelect.getPresentacionComercial() == 1) {
                        unInsumoExt.setCantidadSolicitada(Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja());
                        unInsumoExt.setCantidadComprometida(Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja());
                        unInsumoExt.setCantidadSurtida(Integer.parseInt(this.cantidadSurtida) * medicamentoSelect.getCantidadXCaja());
                    } else {
                        unInsumoExt.setCantidadSolicitada(Integer.parseInt(this.cantidadSolicitada));
                        unInsumoExt.setCantidadComprometida(Integer.parseInt(this.cantidadSolicitada));
                        unInsumoExt.setCantidadSurtida(Integer.parseInt(this.cantidadSurtida));
                    }
                    unInsumoExt.setCantidadRecibida(0);
                    unInsumoExt.setInsertFecha(new Date());
                    unInsumoExt.setInsertIdUsuario(usuarioSession.getIdUsuario());
                    unInsumoExt.setPresentacion(pm.getNombrePresentacion());
                    unInsumoExt.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                    unInsumoExt.setClaveInstitucional(medicamentoSelect.getClaveInstitucional());
                    unInsumoExt.setNombreCorto(medicamentoSelect.getNombreCorto());
                    unInsumoExt.setNombrePresentacion(pm.getNombrePresentacion());
                    unInsumoExt.setFactorTransformacion(medicamentoSelect.getCantidadXCaja());
                    unInsumoExt.setObservaciones(this.comentarios);
                    unInsumoExt.setCantidadActual(medicamentoSelect.getCantidadActual());

                    List<ReabastoEnviadoExtended> listaDetalleReabIns = new ArrayList<>();
                    ReabastoEnviadoExtended reabastEnviado = new ReabastoEnviadoExtended();
                    String idReabastoEnviado = Comunes.getUUID();
                    reabastEnviado.setIdReabastoEnviado(idReabastoEnviado);
                    reabastEnviado.setIdReabastoInsumo(idReabastoInsumo);
                    if (medicamentoSelect.getPresentacionComercial() == 1) {
                        reabastEnviado.setCantidadEnviado(Integer.parseInt(this.cantidadSurtida) * medicamentoSelect.getCantidadXCaja());
                    } else {
                        reabastEnviado.setCantidadEnviado(Integer.parseInt(this.cantidadSurtida));
                    }
                    reabastEnviado.setIdInsumo(medicamentoSelect.getIdMedicamento());
                    reabastEnviado.setIdEstructura(reabastoSelect.getIdEstructuraPadre());
                    reabastEnviado.setLoteEnv(medicamentoSelect.getLote());
                    reabastEnviado.setFechaCad(medicamentoSelect.getFechaCaducidad());
                    reabastEnviado.setCantidadXCaja(medicamentoSelect.getCantidadXCaja());
                    reabastEnviado.setInsertFecha(new Date());
                    reabastEnviado.setInsertIdUsuario(usuarioSession.getIdUsuario());
                    reabastEnviado.setLote(medicamentoSelect.getLote());
                    reabastEnviado.setFechaCaducidad(medicamentoSelect.getFechaCaducidad());
                    reabastEnviado.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                    reabastEnviado.setIdInventarioSurtido(medicamentoSelect.getIdInventario());
                    reabastEnviado.setIdMedicamento(medicamentoSelect.getIdMedicamento());
                    listaDetalleReabIns.add(reabastEnviado);
                    unInsumoExt.setListaDetalleReabIns(listaDetalleReabIns);
                    this.listaReabastoInsumo.add(unInsumoExt);
                    if (this.editarOrden) {
                        this.insumoList.add(unInsumoExt);
                    }
                }
                this.cantidadSurtida = "1";                
                this.cantidadSolicitada = "1";                
                this.comentarios = "";
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Agregar Insumo: {}", ex.getMessage());
        }
    }

    private boolean existsMedtoEnLista() {
        boolean exits = Constantes.INACTIVO;
        boolean existEnviado = Constantes.INACTIVO;
        int i = 0;
        Integer cantSurt = Integer.parseInt(this.cantidadSurtida) * medicamentoSelect.getCantidadXCaja();
        Integer cantSol = Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja();
        for (ReabastoInsumoExtended aux : listaReabastoInsumo) {
            String clave = "";
            if (this.editarOrden) {
                clave = aux.getClaveInstitucional();
            } else {
                clave = aux.getClave();
            }
            if (clave.contains(medicamentoSelect.getClaveInstitucional())) {
                exits = Constantes.ACTIVO;
                List<ReabastoEnviadoExtended> listTemp = new ArrayList<>();
                listTemp.addAll(aux.getListaDetalleReabIns());
                for (ReabastoEnviadoExtended reabEnviado : aux.getListaDetalleReabIns()) {
                    if (reabEnviado.getLote().trim().toUpperCase().equals(medicamentoSelect.getLote().trim().toUpperCase())
                            && reabEnviado.getFechaCaducidad().equals(medicamentoSelect.getFechaCaducidad())) {
                        int catTotal = reabEnviado.getCantidadEnviado() + cantSurt;
                        if (medicamentoSelect.getCantidadActual() >= catTotal) {
                            aux.setCantidadSolicitada(aux.getCantidadSolicitada() + cantSol);
                            aux.setCantidadSurtida(aux.getCantidadSurtida() + cantSurt);
                            aux.setCantidadComprometida(aux.getCantidadComprometida() + cantSol);
                            reabEnviado.setCantidadEnviado(catTotal);
                            reabEnviado.setCantidadRecibida(reabEnviado.getCantidadRecibida() + cantSurt);
                            existEnviado = Constantes.ACTIVO;
                            break;
                        } else {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantInventario"), "");
                            this.cantidadSolicitada = "1";
                            this.cantidadSurtida = "1";
                            existEnviado = Constantes.ACTIVO;
                            break;
                        }
                    }
                }
                if (!existEnviado) {
                    if (this.medicamentoSelect.getCantidadActual() < (Integer.parseInt(this.cantidadSurtida) * this.medicamentoSelect.getCantidadXCaja())) {
                        this.cantidadSolicitada = "1";
                        this.cantidadSurtida = "1";
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantInventario"), "");
                        existEnviado = Constantes.ACTIVO;
                    } else {
                        aux.setCantidadSolicitada(aux.getCantidadSolicitada() + cantSol);
                        aux.setCantidadSurtida(aux.getCantidadSurtida() + cantSurt);
                        aux.setCantidadComprometida(aux.getCantidadComprometida() + cantSol);
                        ReabastoEnviadoExtended reaEnviado = new ReabastoEnviadoExtended();
                        reaEnviado.setIdReabastoEnviado(Comunes.getUUID());
                        reaEnviado.setIdReabastoInsumo(aux.getIdReabastoInsumo());
                        if (medicamentoSelect.getPresentacionComercial() == 1) {
                            reaEnviado.setCantidadEnviado(Integer.parseInt(this.cantidadSurtida) * medicamentoSelect.getCantidadXCaja());
                        } else {
                            reaEnviado.setCantidadEnviado(Integer.parseInt(this.cantidadSurtida));
                        }
                        reaEnviado.setIdInsumo(medicamentoSelect.getIdMedicamento());
                        reaEnviado.setIdEstructura(reabastoSelect.getIdEstructuraPadre());
                        reaEnviado.setLoteEnv(medicamentoSelect.getLote());
                        reaEnviado.setFechaCad(medicamentoSelect.getFechaCaducidad());
                        reaEnviado.setCantidadXCaja(medicamentoSelect.getCantidadXCaja());
                        reaEnviado.setInsertFecha(new Date());
                        reaEnviado.setInsertIdUsuario(usuarioSession.getIdUsuario());
                        reaEnviado.setLote(medicamentoSelect.getLote());
                        reaEnviado.setFechaCaducidad(medicamentoSelect.getFechaCaducidad());
                        reaEnviado.setIdEstatusReabasto(EstatusReabasto_Enum.REGISTRADA.getValue());
                        reaEnviado.setIdInventarioSurtido(medicamentoSelect.getIdInventario());
                        reaEnviado.setIdMedicamento(medicamentoSelect.getIdMedicamento());
                        aux.getListaDetalleReabIns().add(reaEnviado);
                    }
                }
                break;
            }
            i++;
        }
        return exits;
    }

    
    private String eliminarMedicamentoColectivo() {
        String msgError = "";
        Integer cantSurtida = 0;
        String idReabastoEnviado = "";
        String idReabastoInsumo = "";
        try {
            if (permiso.isPuedeCrear()) {
                for (ReabastoInsumoExtended unInsumoExtend : this.listaReabastoInsumo) {
                    for (short i = 0; i < unInsumoExtend.getListaDetalleReabIns().size(); i++) {
                        ReabastoEnviadoExtended item = unInsumoExtend.getListaDetalleReabIns().get(i);
                        if (item.getLote().equalsIgnoreCase(this.medicamentoSelect.getLote())
                                && item.getIdMedicamento().equals(medicamentoSelect.getIdMedicamento())) {
                            if (medicamentoSelect.getPresentacionComercial() == 1) {
                                cantSurtida = item.getCantidadEnviado() - Integer.parseInt(this.cantidadSolicitada) * medicamentoSelect.getCantidadXCaja();
                            } else {
                                cantSurtida = item.getCantidadEnviado() - Integer.parseInt(this.cantidadSolicitada);
                            }
                            if (cantSurtida == 0) {
                                idReabastoEnviado = unInsumoExtend.getListaDetalleReabIns().get(i).getIdReabastoEnviado();
                                unInsumoExtend.getListaDetalleReabIns().remove(i);
                                msgError = "El medicamento elimino correctamente";
                                if (unInsumoExtend.getListaDetalleReabIns().isEmpty()) {
                                    this.listaReabastoInsumo.remove(unInsumoExtend);
                                    idReabastoInsumo = unInsumoExtend.getIdReabastoInsumo();
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
                                    unInsumoExtend.getListaDetalleReabIns().get(i).setCantidadEnviado(cantSurtida);
                                    unInsumoExtend.setCantidadSurtida(cantSurtida);
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

    
    public void eliminarInsumo(String idMedicamento) {
        for (short i = 0; i < this.listaReabastoInsumo.size(); i++) {
            ReabastoInsumoExtended insumoExt = this.listaReabastoInsumo.get(i);
            if (insumoExt.getIdInsumo().equalsIgnoreCase(idMedicamento)) {
                if (this.editarOrden) {
                    this.listaReabastoInsumoElimiminar.add(insumoExt);
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
                ReabastoInsumoExtended insumoExtend = this.insumoList.get(i);
                if (insumoExtend.getIdInsumo().equalsIgnoreCase(idMedicamento)) {
                    this.insumoList.remove(i);
                    break;
                }
            }
        }
    }
        
    private String validarMedicamento() {
        String res = null;
        if (this.activaCancelarSurtIntColectivo) {
            if (this.medicamentoSelect.getPresentacionComercial() == Constantes.ES_ACTIVO) {
                if (this.medicamentoSelect.getCantidadActual() < (Integer.parseInt(this.cantidadSurtida) * this.medicamentoSelect.getCantidadXCaja())) {
                    res = "No puede surtir una cantidad mayor a la existente.";
                    this.cantidadSolicitada = "1";
                    this.cantidadSurtida = "1";
                    return res;
                }
            } else {
                if (this.medicamentoSelect.getCantidadActual() < Integer.parseInt(this.cantidadSurtida)) {
                    res = "No puede surtir una cantidad mayor a la existente.";
                    this.cantidadSolicitada = "1";
                    this.cantidadSurtida = "1";
                    return res;
                }
            }
        }
        return res;
    }

    private void eliminarMedicamentoListaAuxiliar(Integer canSurtida) {
        // Eliminar de Lista creada como auxiliar para editar
        for (ReabastoInsumoExtended reabastoInsumo : this.insumoList) {
            for (short j = 0; j < reabastoInsumo.getListaDetalleReabIns().size(); j++) {
                ReabastoEnviadoExtended itemj = reabastoInsumo.getListaDetalleReabIns().get(j);
                if (itemj.getLote().equalsIgnoreCase(this.medicamentoSelect.getLote())
                        && itemj.getIdMedicamento().equals(medicamentoSelect.getIdMedicamento())) {
                    if (canSurtida == 0) {
                        reabastoInsumo.getListaDetalleReabIns().remove(j);
                        this.insumoList.remove(reabastoInsumo);
                    }
                    if (canSurtida > 0) {
                        reabastoInsumo.getListaDetalleReabIns().get(j).setCantidadEnviado(canSurtida);
                        reabastoInsumo.setCantidadSurtida(canSurtida);
                    }
                }
            }
        }
    }

    public String getArchivo2() {
        return archivo2;
    }

    public void setArchivo2(String archivo2) {
        this.archivo2 = archivo2;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }
    
    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }


    public List<ReabastoExtended> getListaReabasto() {
        return listaReabasto;
    }

    public void setListaReabasto(List<ReabastoExtended> listaReabasto) {
        this.listaReabasto = listaReabasto;
    }

    public List<ReabastoInsumoExtended> getListaReabastoInsumo() {
        return listaReabastoInsumo;
    }

    public void setListaReabastoInsumo(List<ReabastoInsumoExtended> listaReabastoInsumo) {
        this.listaReabastoInsumo = listaReabastoInsumo;
    }
    
    public ReabastoExtended getReabastoSelect() {
        return reabastoSelect;
    }

    public void setReabastoSelect(ReabastoExtended reabastoSelect) {
        this.reabastoSelect = reabastoSelect;
    }    

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }
    
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
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

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public int getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(int tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public List<CatalogoGeneral> getTipoInsumoList() {
        return tipoInsumoList;
    }

    public void setTipoInsumoList(List<CatalogoGeneral> tipoInsumoList) {
        this.tipoInsumoList = tipoInsumoList;
    }
    
    public int getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(int tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }
    
    public Estructura getEstructuraSelect() {
        return estructuraSelect;
    }

    public void setEstructuraSelect(Estructura estructuraSelect) {
        this.estructuraSelect = estructuraSelect;
    }
    
    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public boolean isFarmacia() {
        return farmacia;
    }

    public void setFarmacia(boolean farmacia) {
        this.farmacia = farmacia;
    }

    public String getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(String cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }
    
    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public SesionMB getSesion() {
        return sesion;
    }

    public void setSesion(SesionMB sesion) {
        this.sesion = sesion;
    }
    
    public String getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(String cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    public List<ClaveProveedorBarras_Extend> getSkuSapList() {
        return skuSapList;
    }

    public void setSkuSapList(List<ClaveProveedorBarras_Extend> skuSapList) {
        this.skuSapList = skuSapList;
    }
    
    public ClaveProveedorBarras_Extend getSkuSap() {
        return skuSap;
    }

    public void setSkuSap(ClaveProveedorBarras_Extend skuSap) {
        this.skuSap = skuSap;
    }    
    
        public void setEditarOrden(boolean editarOrden) {
        this.editarOrden = editarOrden;
    }
        
    public boolean isEditarOrden() {
        return editarOrden;
    }    

    
    public ClaveProveedorBarras_Extend getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(ClaveProveedorBarras_Extend medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }


    public void setListaMedicos(List<Usuario> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    public String getIdMedico() {
        return idMedico;
    }
    
    public List<Usuario> getListaMedicos() {
        return listaMedicos;
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
    
    public void setListaReabastoInsumoElimiminar(List<ReabastoInsumoExtended> listaReabastoInsumoElimiminar) {
        this.listaReabastoInsumoElimiminar = listaReabastoInsumoElimiminar;
    }
    
    public List<ReabastoInsumoExtended> getListaReabastoInsumoElimiminar() {
        return listaReabastoInsumoElimiminar;
    }
    
    public ReabastoInsumoExtended getReabastoInsumoExtend() {
        return reabastoInsumoExtend;
    }

    public void setReabastoInsumoExtend(ReabastoInsumoExtended reabastoInsumoExtend) {
        this.reabastoInsumoExtend = reabastoInsumoExtend;
    }

    public void setGeneraReporte(boolean generaReporte) {
        this.generaReporte = generaReporte;
    }
    
    public boolean isGeneraReporte() {
        return generaReporte;
    }

    public boolean isActivaCancelarSurtIntColectivo() {
        return activaCancelarSurtIntColectivo;
    }

    public void setActivaCancelarSurtIntColectivo(boolean activaCancelarSurtIntColectivo) {
        this.activaCancelarSurtIntColectivo = activaCancelarSurtIntColectivo;
    }
    
    public String getIdEstructuraCombo() {
        return idEstructuraCombo;
    }

    public void setIdEstructuraCombo(String idEstructuraCombo) {
        this.idEstructuraCombo = idEstructuraCombo;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
