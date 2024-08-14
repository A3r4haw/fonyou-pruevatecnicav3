package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.mc.lazy.SurtimientoReabastoLazy;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Fabricante;
import mx.mc.model.FabricanteInsumo;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Proveedor;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Usuario;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FabricanteInsumoService;
import mx.mc.service.FabricanteService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ProveedorService;
import mx.mc.service.ReabastoEnviadoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoService;
import mx.mc.service.ReportesService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
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
public class ReabastoSurtiminetoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReabastoSurtiminetoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private static final ResourceBundle RESOURCESMESSAGE = ResourceBundle.getBundle(Constantes.GLOBAL_PARAM);
    
    private PermisoUsuario permiso;
    private List<Estructura> listaEstructuras;
    private List<ReabastoExtended> listaReabasto;
    private List<ReabastoInsumoExtended> listaReabastoInsumo;
    private ReabastoExtended reabastoSelect;
    private String idEstructura;
    private boolean administrador;
    private String xcantidad;
    private String textoBusqueda;
    private Usuario usuarioSession;
    private String codigoBarras;
    private boolean eliminarCodigo;    
    private File dirTmp;
    private String almacenOrigen;
    private String almacenDestino;
    private boolean surtirSinPistoleo;
    private String estrErrPermisos;
    private String surtimientoErrorOperacion;
    private String archivo;
    private SurtimientoReabastoLazy surtimientoReabastoLazy;
    private Estructura estructura;

    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    @Autowired
    private transient ReabastoService reabastoService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;
    
    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient ReportesService reportesService;
        
    @Autowired
    private transient ReabastoEnviadoService reabastoEnviadoService;
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient FabricanteService fabricanteService;

    @Autowired
    private transient FabricanteInsumoService fabricanteInsumoService;
    
    @Autowired
    private transient ProveedorService proveedorService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            estrErrPermisos = "estr.err.permisos";
            surtimientoErrorOperacion = "surtimiento.error.operacion";
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.SURTIMIENTO.getSufijo());
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            validarUsuarioAdministrador();
            alimentarComboAlmacen();
            buscarRegistros();
            dirTmp = new File(Comunes.getPath());
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
        obtieneParametrosConfiguracion();
        this.listaProveedor = obtenerListaProveedor();
        this.listaFabricante = obtenerListaFabricante();
    }
    
    private List<Proveedor> listaProveedor;
    private List<Fabricante> listaFabricante;
    private List<FabricanteInsumo> listaFabricanteInsumo;
    
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

    private void obtieneParametrosConfiguracion() {
        LOGGER.trace("mx.mc.magedbean.ReabastoSurtiminetoMB.obtieneParametrosConfiguracion()");
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        surtirSinPistoleo = sesion.isSurtimientoSinPistoleo();
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.listaEstructuras = new ArrayList<>();
        this.listaReabasto = new ArrayList<>();       
        this.textoBusqueda = "";
        this.usuarioSession = new Usuario();
        this.codigoBarras = "";
        this.listaReabastoInsumo = new ArrayList<>();
        this.reabastoSelect = new ReabastoExtended();
        this.idEstructura = "";
        this.eliminarCodigo = false;
        this.administrador = false;
        this.xcantidad = "1";
        this.estructura = new Estructura();
    }

    public void limpiar() {
        this.xcantidad = "1";
        this.codigoBarras = "";
        this.eliminarCodigo = false;
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
            } else {
                est.setIdTipoAreaEstructura(Constantes.TIPO_AREA_ALMACEN);
                this.listaEstructuras = this.estructuraService.obtenerLista(est);
                this.idEstructura = this.listaEstructuras.get(0).getIdEstructura();
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
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            Reabasto reabasto = new Reabasto();
            reabasto.setIdEstructuraPadre(this.idEstructura);
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
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.administrador = sesion.isAdministrador();
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
     * @param idReabasto
     */
    public void cancelarOrdenReabasto(String idReabasto) {
        try {
            if (!this.permiso.isPuedeEliminar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(estrErrPermisos), "");
                return;
            }
            Estructura estruct = new Estructura();
            Reabasto reabasto = reabastoService.obtenerReabastoPorID(idReabasto);
            estruct.setIdEstructura(reabasto.getIdEstructura());
            Estructura estructuraSelect = estructuraService.obtener(estruct);

            List<ReabastoInsumo> insumoList = reabastoInsumoService.obtenerReabastosInsumos(reabasto.getIdReabasto(), estructuraSelect.getIdTipoAlmacen());
            reabasto.setIdReabasto(idReabasto);
            reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.CANCELADA.getValue());
            reabasto.setUpdateFecha(new Date());
            reabasto.setUpdateIdUsuario(usuarioSession.getIdUsuario());

            for (ReabastoInsumo ri : insumoList) {
                ri.setIdReabasto(idReabasto);
                ri.setIdEstatusReabasto(EstatusReabasto_Enum.CANCELADA.getValue());
                ri.setUpdateFecha(new Date());
                ri.setUpdateIdUsuario(reabasto.getUpdateIdUsuario());
            }

            boolean resp = reabastoService.cancelarSolicitud(reabasto, insumoList, estructuraSelect.getIdTipoAlmacen());
            if (resp) {
                obtenerOrdenesReabasto();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo cancelarOrdenReabasto :: {}", e.getMessage());
        }
    }

    public void obtenerDetalleInsumos() {
        try {
            List<Integer> listEstatusReabastoSurt = new ArrayList<>();
            listEstatusReabastoSurt.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            this.almacenDestino = reabastoSelect.getNombreEstructura();
            Estructura estructuraOrigen = estructuraService.obtenerEstructura(this.idEstructura);
            this.almacenOrigen = estructuraOrigen.getNombre();
            this.listaReabastoInsumo = reabastoInsumoService.
                    obtenerReabastoInsumoExtendsSurt(
                            this.reabastoSelect.getIdReabasto(),
                            this.idEstructura, listEstatusReabastoSurt);

            for (short i = 0; i < this.listaReabastoInsumo.size(); i++) {
                List<ReabastoEnviadoExtended> listaReabastoEnviado = new ArrayList<>();
                ReabastoInsumo item = this.listaReabastoInsumo.get(i);
                this.listaReabastoInsumo.get(i).setListaDetalleReabIns(
                        reabastoEnviadoService.obtenerListaReabastoEnviado(
                                item.getIdReabastoInsumo(), null));

                if (surtirSinPistoleo) {
                    Integer cantidadSolicitadaSur = item.getCantidadSolicitada();
                    Integer cantidadSurtida = 0;
                    String loteSurtido = Constantes.LOTE_GENERICO;
                    Date caducidadSurtida;
                    String idInventario = null;

                    List<Inventario> listaInventarioConExistencia = obtenerInventarioExistencia(this.idEstructura, item.getIdInsumo());
                    if (!listaInventarioConExistencia.isEmpty()) {

                        Integer cantidadSurtidaPorLote;
                        for (Inventario item2 : listaInventarioConExistencia) {

                            Integer cantidadFaltantePorSurtir = cantidadSolicitadaSur;
                            loteSurtido = item2.getLote();
                            caducidadSurtida = item2.getFechaCaducidad();
                            idInventario = item2.getIdInventario();

                            if (item2.getCantidadActual() >= cantidadFaltantePorSurtir) {
                                cantidadSurtidaPorLote = cantidadFaltantePorSurtir;
                            } else {
                                cantidadSurtidaPorLote = item2.getCantidadActual();
                            }
                            cantidadSurtida = cantidadSurtida + cantidadSurtidaPorLote;
                            cantidadSolicitadaSur = cantidadSolicitadaSur - cantidadSurtidaPorLote;
                            item.setCantidadSurtida(cantidadSurtida);
                            ReabastoEnviadoExtended reabastoEnviado = new ReabastoEnviadoExtended();
                            reabastoEnviado.setIdReabastoEnviado(Comunes.getUUID());
                            reabastoEnviado.setIdReabastoInsumo(item.getIdReabastoInsumo());
                            reabastoEnviado.setIdInsumo(item.getIdInsumo());
                            reabastoEnviado.setIdMedicamento(item.getIdInsumo());
                            reabastoEnviado.setIdEstructura(this.idEstructura);
                            reabastoEnviado.setLote(loteSurtido);
                            reabastoEnviado.setFechaCaducidad(caducidadSurtida);
                            reabastoEnviado.setFechaCad(caducidadSurtida);
                            reabastoEnviado.setLoteEnv(loteSurtido);
                            reabastoEnviado.setIdInventarioSurtido(idInventario);
                            reabastoEnviado.setCantidadEnviado(cantidadSurtidaPorLote);
                            reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                            reabastoEnviado.setInsertFecha(new Date());
                            reabastoEnviado.setInsertIdUsuario(item.getInsertIdUsuario());
                            reabastoEnviado.setCantidadCaja(item2.getCantidadXCaja());
                            reabastoEnviado.setOsmolaridad(item2.getOsmolaridad());
                            reabastoEnviado.setDensidad(item2.getDensidad());
                            reabastoEnviado.setCalorias(item2.getCalorias());
                            reabastoEnviado.setIdFabricante(item2.getIdFabricante());
                            reabastoEnviado.setIdProveedor(item2.getIdProveedor());
                            reabastoEnviado.setNoHorasEstabilidad(item2.getNoHorasEstabilidad());
                            
                            listaReabastoEnviado.add(reabastoEnviado);
                            this.listaReabastoInsumo.get(i).setListaDetalleReabIns(listaReabastoEnviado);
                            if (cantidadSolicitadaSur <= 0) {
                                break;
                            }
                        }
                    } else {
                        item.setObservaciones("No hay lotes con Existencias.");
                    }
                    if (idInventario == null) {
                        inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                                item.getIdInsumo(), this.idEstructura, loteSurtido);
                    }
                    if (cantidadSolicitadaSur > 0) {
                        item.setObservaciones("Insuficiencia para surtir: " + cantidadSolicitadaSur + " piezas.");
                    }
                    item.setCantidadSurtida(cantidadSurtida);

                } //Termina
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalSurtimiento :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que se utiliza para mostrar el detalle de la orden de surtimiento
     * @param idReabasto
     */
    public void mostrarModalSurtimiento(String idReabasto) {
        try {
            List<Integer> listEstatusReabasto = new ArrayList<>();
            reabastoSelect = reabastoService.obtenerReabastoExtendedPorID(idReabasto);
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            this.almacenDestino = reabastoSelect.getNombreEstructura();
            Estructura estructuraOrigen = estructuraService.obtenerEstructura(this.idEstructura);
            this.almacenOrigen = estructuraOrigen.getNombre();
            this.listaReabastoInsumo = reabastoInsumoService.
                    obtenerReabastoInsumoExtendsSurt(
                            this.reabastoSelect.getIdReabasto(),
                            this.idEstructura, listEstatusReabasto);

            for (short i = 0; i < this.listaReabastoInsumo.size(); i++) {
                List<ReabastoEnviadoExtended> listaReabastoEnviado = new ArrayList<>();
                ReabastoInsumo item = this.listaReabastoInsumo.get(i);
                this.listaReabastoInsumo.get(i).setListaDetalleReabIns(
                        reabastoEnviadoService.obtenerListaReabastoEnviado(
                                item.getIdReabastoInsumo(), null));
                if (surtirSinPistoleo) {

                    Integer cantidadSolicitada = item.getCantidadSolicitada();
                    Integer cantidadSurtida = 0;
                    String loteSurtido = Constantes.LOTE_GENERICO;
                    Date caducidadSurtida;
                    String idInventarioSur = null;

                    List<Inventario> listaInventarioConExistencia = obtenerInventarioExistencia(this.idEstructura, item.getIdInsumo());
                    if (!listaInventarioConExistencia.isEmpty()) {

                        Integer cantidadSurtidaPorLote = 0;
                        for (Inventario item2 : listaInventarioConExistencia) {

                            Integer cantidadFaltantePorSurtir = cantidadSolicitada;
                            loteSurtido = item2.getLote();
                            caducidadSurtida = item2.getFechaCaducidad();
                            idInventarioSur = item2.getIdInventario();

                            if (item2.getCantidadActual() >= cantidadFaltantePorSurtir) {
                                cantidadSurtidaPorLote = cantidadFaltantePorSurtir;
                            } else {
                                cantidadSurtidaPorLote = item2.getCantidadActual();
                            }
                            cantidadSurtida = cantidadSurtida + cantidadSurtidaPorLote;
                            cantidadSolicitada = cantidadSolicitada - cantidadSurtidaPorLote;
                            item.setCantidadSurtida(cantidadSurtida);
                            ReabastoEnviadoExtended reabastoEnvi = new ReabastoEnviadoExtended();
                            reabastoEnvi.setIdReabastoEnviado(Comunes.getUUID());
                            reabastoEnvi.setIdReabastoInsumo(item.getIdReabastoInsumo());
                            reabastoEnvi.setIdInsumo(item.getIdInsumo());
                            reabastoEnvi.setIdMedicamento(item.getIdInsumo());
                            reabastoEnvi.setIdEstructura(this.idEstructura);
                            reabastoEnvi.setLote(loteSurtido);
                            reabastoEnvi.setFechaCaducidad(caducidadSurtida);
                            reabastoEnvi.setFechaCad(caducidadSurtida);
                            reabastoEnvi.setLoteEnv(loteSurtido);
                            reabastoEnvi.setIdInventarioSurtido(idInventarioSur);
                            reabastoEnvi.setCantidadEnviado(cantidadSurtidaPorLote);
                            reabastoEnvi.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
                            reabastoEnvi.setInsertFecha(new Date());
                            reabastoEnvi.setInsertIdUsuario(item.getInsertIdUsuario());
                            reabastoEnvi.setCantidadCaja(item2.getCantidadXCaja());
                            listaReabastoEnviado.add(reabastoEnvi);
                            this.listaReabastoInsumo.get(i).setListaDetalleReabIns(listaReabastoEnviado);
                            if (cantidadSolicitada <= 0) {
                                break;
                            }
                        }
                    } else {
                        item.setObservaciones("No hay lotes con Existencias.");
                    }
                    if (idInventarioSur == null) {
                        inventarioService.obtenerIdiventarioPorInsumoEstructuraYLote(
                                item.getIdInsumo(), this.idEstructura, loteSurtido);
                    }
                    if (cantidadSolicitada > 0) {
                        item.setObservaciones("Insuficiencia para surtir: " + cantidadSolicitada + " piezas.");
                    }
                    item.setCantidadSurtida(cantidadSurtida);
                } //Termina
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalSurtimiento :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que busca inventario con existencias por estrutura e insumo
     * ordenado por Lote mas proximo a vencer
     *
     * @param reabastoEnviado
     * @return
     */
    private List<Inventario> obtenerInventarioExistencia(String idEstructura, String idInsumo) {
        List<Inventario> result = new ArrayList<>();
        try {
            result = inventarioService.obtenerExistenciasPorIdEstructuraIdInsumo(idEstructura, idInsumo);
        } catch (Exception ex) {
            LOGGER.error("Error al obtrener Inventario con existencias del insumo: {}. En la estructura: {}", idInsumo, ex.getMessage());
        }
        return result;
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
            boolean respuesta = this.reabastoService.
                    guardarOrdenReabasto(reabasto, this.listaReabastoInsumo);
            if (respuesta) {
                Mensaje.showMessage("Info", RESOURCES.getString("ok.guardar"), "");
                PrimeFaces.current().ajax().addCallbackParam("errorSurtir", respuesta);
                obtenerOrdenesReabasto();
            }
        } catch (Exception e) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
            LOGGER.error("Error en el metodo guardarRegistros :: {}", e.getMessage());
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
                if (item.getCantidadSurtida() > item.getCantidadActual()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantInventario"), "");
                    return;
                }
            }
            Reabasto reabasto = new Reabasto();
            reabasto.setIdReabasto(this.reabastoSelect.getIdReabasto());
            reabasto.setIdUsuarioSolicitud(this.usuarioSession.getIdUsuario());
            reabasto.setFechaSurtida(new Date());
            reabasto.setIdUsuarioSurtida(this.usuarioSession.getIdUsuario());
            reabasto.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
            reabasto.setUpdateFecha(new Date());
            reabasto.setUpdateIdUsuario(this.usuarioSession.getIdUsuario());
            reabasto.setFolio(this.reabastoSelect.getFolio());
            reabasto.setIdEstructuraPadre(this.reabastoSelect.getIdEstructuraPadre());
            reabasto.setIdEstructura(this.reabastoSelect.getIdEstructura());
            
            for (ReabastoInsumoExtended item : this.listaReabastoInsumo) {
                item.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_ENTRANS);
            }
            boolean respuesta = this.reabastoService.
                    surtirOrdenReabasto(reabasto, this.listaReabastoInsumo);
            if (respuesta) {
                Mensaje.showMessage("Info", RESOURCES.getString("surtimiento.ok.surtimiento"), "");
                obtenerOrdenesReabasto();
                PrimeFaces.current().ajax().addCallbackParam("errorSurtir", respuesta);
            }
        } catch (Exception ec) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
            LOGGER.error("Error en el metodo surtirReabastoInsumo :: {}", ec.getMessage());
        }
    }

    public void obtenerEstructura() {
        try {
            estructura = estructuraService.obtenerEstructura(this.idEstructura);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de buscar la estructura: {}", ex.getMessage());
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
            if (textoBusqueda != null
                    && textoBusqueda.trim().isEmpty()) {
                textoBusqueda = null;
            }

            Integer idTipoAlmacen = obtenerIdTipoAlmacen(this.idEstructura);
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.SOLICITADA.getValue());
            Reabasto reabasto = new Reabasto();
            reabasto.setIdEstructuraPadre(this.idEstructura);

            surtimientoReabastoLazy = new SurtimientoReabastoLazy(reabastoService, textoBusqueda, reabasto, listEstatusReabasto, idTipoAlmacen);

            textoBusqueda = null;
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
        Integer idTipoAlmacenSurt = 0;
        try {
            if (!this.administrador) {
                idTipoAlmacenSurt = this.listaEstructuras.get(0).getIdTipoAlmacen();
            } else {
                for (Estructura estructura : this.listaEstructuras) {
                    if (estructura.getIdEstructura().equals(idEstructura)) {
                        idTipoAlmacenSurt = estructura.getIdTipoAlmacen();
                        break;
                    }
                }
            }
        } catch (Exception ecp) {
            LOGGER.error("Error en el metodo buscarRegistros :: {}", ecp.getMessage());
        }
        return idTipoAlmacenSurt;
    }

    /**
     * Metodo utilizado para crear el sub detalle del medicamento por escaneo de
     * codigo de barras
     */
    public void agregarInsumosPorCodigo() {
        try {
            if (this.codigoBarras.isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.insum"), "");
                return;
            }
            if (this.eliminarCodigo) {
                String msjError = eliminarLotePorCodigo();
                if (msjError.isEmpty()) {
                    Mensaje.showMessage("Info", RESOURCES.getString("surtimiento.ok.eliminar"), "");
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, "");
                }
                this.codigoBarras = "";
                this.xcantidad = "1";
                this.eliminarCodigo = false;
                return;
            }
            //Separar codigo de barras 
            ReabastoEnviadoExtended medicamentoDetalle = tratarCodigoDeBarras(this.codigoBarras);
            //Filtrar registros
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoDetalle);
            //Validar si el medicamento esta en la lista
            if (medicamento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.medNoEncontrado"), "");
                this.codigoBarras = "";
                this.xcantidad = "1";
                return;
            }
            //Validar fecha de caducidad 
            if (medicamentoDetalle.getFechaCaducidad().compareTo(new Date()) < 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.medCaducado"), "");
                this.codigoBarras = "";
                this.xcantidad = "1";
                return;
            }

            //Validar que la cantidad del lote no sea insuficiente
            //GCR se cambia metodo de busqueda para descontar del inentario correcto tomando en cuenta la fecha de caducidad y la cantidad por caja
            Inventario inventario = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                    medicamento.getIdInsumo(), idEstructura, medicamentoDetalle.getLote(), 
                    medicamentoDetalle.getCantidadXCaja(), null, medicamentoDetalle.getFechaCaducidad());
                    
            String idInventario;
            if (inventario == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.noExisteInventario"), "");
                this.codigoBarras = "";
                this.xcantidad = "1";
                return;
            } else {
                idInventario = inventario.getIdInventario();
            }
            int cantidadXCaja = medicamentoDetalle.getCantidadXCaja();
            
            //Validar si el detalle del meticamento existe
            if (medicamento.getListaDetalleReabIns() == null || medicamento.getListaDetalleReabIns().isEmpty()) {
                medicamento.setListaDetalleReabIns(new ArrayList<>());
                if (this.xcantidad.isEmpty()) {
                    this.xcantidad = "1";
                }

                int cantidadSurtida = 0;
                if (reabastoSelect.getIdTipoAlmacen().equals(Constantes.SUBALMACEN)) {
                    cantidadSurtida = medicamento.getCantidadSurtida() + Integer.valueOf(this.xcantidad);
                } else if (reabastoSelect.getIdTipoAlmacen().equals(Constantes.ALMACEN)) {
                    cantidadSurtida = medicamento.getCantidadSurtida() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                }

                if (cantidadSurtida > medicamento.getCantidadSolicitada()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR,
                            RESOURCES.getString("surtimiento.error.cantSolMayor"), "");
                    this.codigoBarras = "";
                    this.xcantidad = "1";
                    return;
                }
                medicamentoDetalle.setCantidadXCaja(cantidadXCaja);
                medicamentoDetalle.setCantidadEnviado(cantidadSurtida);
                medicamentoDetalle.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                medicamentoDetalle.setIdMedicamento(medicamento.getIdInsumo());
                medicamentoDetalle.setIdEstructura(this.idEstructura);
                medicamentoDetalle.setIdInventarioSurtido(idInventario);
                medicamento.getListaDetalleReabIns().add(medicamentoDetalle);
                medicamento.setCantidadSurtida(cantidadSurtida);
            } else {
                if (this.xcantidad.isEmpty()) {
                    this.xcantidad = "1";
                }

                int cantidadSurtida = 0;
                int cantidadEnviada = 0;

                for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                    if (item.getLote().equalsIgnoreCase(medicamentoDetalle.getLote())) {
                        if (reabastoSelect.getIdTipoAlmacen().equals(Constantes.SUBALMACEN)) {
                            cantidadSurtida = medicamento.getCantidadSurtida() + Integer.valueOf(this.xcantidad);
                            cantidadEnviada = item.getCantidadEnviado() + Integer.valueOf(this.xcantidad);

                        } else if (reabastoSelect.getIdTipoAlmacen().equals(Constantes.ALMACEN)) {
                            cantidadSurtida = medicamento.getCantidadSurtida() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                            cantidadEnviada = item.getCantidadEnviado() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                        }
                        if (inventario.getCantidadActual() < cantidadEnviada) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo hay " + inventario.getCantidadActual() + " existencias en el lote " + inventario.getLote(), "");
                            this.codigoBarras = "";
                            this.xcantidad = "1";
                            return;
                        }
                        if (cantidadSurtida > medicamento.getCantidadSolicitada()) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.cantSolMayor"), "");
                            this.codigoBarras = "";
                            this.xcantidad = "1";
                            return;
                        }
                        if (inventario.getCantidadActual() < (item.getCantidadEnviado() + cantidadXCaja * Integer.valueOf(this.xcantidad))) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Solo hay " + inventario.getCantidadActual() + " existencias en el lote " + inventario.getLote(), "");
                            this.codigoBarras = "";
                            this.xcantidad = "1";
                            return;
                        }
                        item.setCantidadEnviado(cantidadEnviada);
                        medicamento.setCantidadSurtida(cantidadSurtida);
                        this.codigoBarras = "";
                        this.xcantidad = "1";
                        return;
                    } else {
                        if (reabastoSelect.getIdTipoAlmacen().equals(Constantes.SUBALMACEN)) {
                            cantidadSurtida = medicamento.getCantidadSurtida() + Integer.valueOf(this.xcantidad);
                            cantidadEnviada = Integer.valueOf(this.xcantidad);
                        } else if (reabastoSelect.getIdTipoAlmacen().equals(Constantes.ALMACEN)) {
                            cantidadSurtida = medicamento.getCantidadSurtida() + cantidadXCaja * Integer.valueOf(this.xcantidad);
                            cantidadEnviada = cantidadXCaja * Integer.valueOf(this.xcantidad);
                        }
                    }
                }
                medicamentoDetalle.setCantidadXCaja(cantidadXCaja);
                medicamentoDetalle.setCantidadEnviado(cantidadEnviada);
                medicamentoDetalle.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
                medicamentoDetalle.setIdMedicamento(medicamento.getIdInsumo());
                medicamentoDetalle.setIdEstructura(this.idEstructura);
                //GCR se cambia para no repetir la consulta que se hizo arriba donde se asigno el idInventario
                medicamentoDetalle.setIdInventarioSurtido(idInventario);
                medicamento.getListaDetalleReabIns().add(medicamentoDetalle);
                medicamento.setCantidadSurtida(cantidadSurtida);
            }
            this.codigoBarras = "";
            this.xcantidad = "1";
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("reabastSurtimiento.err.agregar"), null);
        }
    }

    private String eliminarLotePorCodigo() {
        String mnsgError = "";
        try {
            ReabastoEnviadoExtended medicamentoCodigo = tratarCodigoDeBarras(this.codigoBarras);
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoCodigo);
            if (medicamento == null) {
                mnsgError = RESOURCES.getString("sur.error.noExisteInsumoEliminar");
                return mnsgError;
            }
            ReabastoEnviadoExtended medicamentoDetalle = obtenerDetalleMedicamento(
                    medicamento.getListaDetalleReabIns(), medicamentoCodigo);
            if (medicamentoDetalle == null) {
                mnsgError = RESOURCES.getString("surtimiento.error.noLote");
                return mnsgError;
            }
            if (this.xcantidad.isEmpty()) {
                this.xcantidad = "1";
            }
            int factorConversion = obtenerCandtidadMedicamento(medicamento.getIdInsumo());

            int cantidadSurtida = 0;
            int cantidadEnviado = 0;
            if (reabastoSelect.getIdTipoAlmacen() == Constantes.SUBALMACEN) {
                cantidadSurtida = medicamento.getCantidadSurtida() - Integer.valueOf(this.xcantidad);
                cantidadEnviado = medicamentoDetalle.getCantidadEnviado() - Integer.valueOf(this.xcantidad);
            } else if (reabastoSelect.getIdTipoAlmacen() == Constantes.ALMACEN) {
                cantidadSurtida = medicamento.getCantidadSurtida()
                        - factorConversion * Integer.valueOf(this.xcantidad);
                cantidadEnviado = medicamentoDetalle.getCantidadEnviado()
                        - factorConversion * Integer.valueOf(this.xcantidad);
            }

            if (cantidadEnviado < 0) {
                mnsgError = RESOURCES.getString("surtimiento.error.cantElimMayor");
                return mnsgError;
            }
            medicamento.setCantidadSurtida(cantidadSurtida);
            medicamentoDetalle.setCantidadEnviado(cantidadEnviado);
            if (medicamentoDetalle.getCantidadEnviado() == 0) {
                eliminarDetalleByFolio(medicamento.getListaDetalleReabIns(), medicamentoCodigo);
            }
        } catch (NumberFormatException exc) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", exc.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
        }
        return mnsgError;
    }

    /**
     * Metodo utilizado para obtener el medicamento por la clave
     *
     * @param detalle
     * @return ReabastoInsumoExtended
     */
    private ReabastoInsumoExtended obtenerInsumoPorClaveMedicamento(ReabastoEnviadoExtended detalle) {
        ReabastoInsumoExtended regist = null;
        try {
            for (ReabastoInsumoExtended ite : this.listaReabastoInsumo) {
                if (ite.getClaveInstitucional().equalsIgnoreCase(detalle.getIdMedicamento())) {
                    return ite;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo obtenerInsumoPorClaveMedicamento :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
        }
        return regist;
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
                for (ReabastoEnviadoExtended it : listaDetalle) {
                    if (it.getLote().equalsIgnoreCase(medicamentoCodigo.getLote())) {
                        return it;
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
                ReabastoEnviadoExtended itemR = listaDetalle.get(i);
                if (itemR.getLote().equalsIgnoreCase(medicamentoCodigo.getLote())) {
                    listaDetalle.remove(i);
                    return;
                }
            }
        } catch (Exception exp) {
            LOGGER.error("Error en el metodo eliminarDetalleByFolio :: {}", exp.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(surtimientoErrorOperacion), "");
        }
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
    
    /**
     * Metodo utilizado para convertir el codigo de barras en un objeto de tipo
     * ReabastoEnviadoExtended
     *
     * @param codigo String
     * @return ReabastoEnviadoExtended
     */
    private ReabastoEnviadoExtended tratarCodigoDeBarras(String codigo) {
        ReabastoEnviadoExtended detalleReabastoEnv = new ReabastoEnviadoExtended();

        CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
        if (ci != null) {
            detalleReabastoEnv.setIdMedicamento(ci.getClave());
            detalleReabastoEnv.setLote(ci.getLote());
            try {
                detalleReabastoEnv.setFechaCaducidad(ci.getFecha());
            } catch (Exception ex) {
                LOGGER.error("Error en el metodo tratarCodigoDeBarras :: Al parsear fecha: {}", ex.getMessage());
            }
            if (ci.getCantidad() != null) {
                detalleReabastoEnv.setCantidadXCaja(ci.getCantidad());
            } else {
                Integer cantidadXcaja = 1;
                try {
                    if(estructura != null) {
                        if(estructura.getIdTipoAlmacen().equals(TipoAlmacen_Enum.FARMACIA.getValue())) {
                            Medicamento unMedicamento = medicamentoService.obtenerMedicaByClave(ci.getClave());
                            if(unMedicamento != null) {
                                cantidadXcaja = unMedicamento.getFactorTransformacion();
                            }
                        }
                    }                     
                } catch(Exception ex) {
                    LOGGER.error("Ocurrio un error al momento de buscar el medicamento por clave:  ", ex.getMessage());
                }               
                detalleReabastoEnv.setCantidadXCaja(cantidadXcaja);
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
                detalleReabastoEnv.setIdMedicamento(claveProveedorBarras.getClaveInstitucional());
                detalleReabastoEnv.setCantidadXCaja(claveProveedorBarras.getCantidadXCaja());
                detalleReabastoEnv.setLote(claveProveedorBarras.getClaveProveedor());
                detalleReabastoEnv.setFechaCaducidad(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Valor no valido", null);
            }
        }
        return detalleReabastoEnv;
    }


    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean status = Constantes.INACTIVO;
        try {
            reabastoSelect.setNombreEstructura(this.almacenDestino);
            reabastoSelect.setNombreProveedor(this.almacenOrigen);
            
            Estructura est;
            if (this.reabastoSelect.getIdEstructura() == null) {
                est = estructuraService.obtenerEstructura(usuarioSession.getIdEstructura());
            } else {
                est = estructuraService.obtenerEstructura(reabastoSelect.getIdEstructura());
            }

           // EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());

            Estructura estruct = estructuraService.obtenerEstructura(est.getIdEstructura());

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (entidad == null) {
                entidad = new EntidadHospitalaria();
                entidad.setDomicilio("");
                entidad.setNombre("");

            }

            reabastoSelect.setDomicilio(entidad.getDomicilio());
            reabastoSelect.setNombreEntidad(entidad.getNombre()); 
            String usuarioSurte = usuarioSession.getNombre().concat(" ").concat(usuarioSession.getApellidoPaterno()).concat(" ").concat(usuarioSession.getApellidoMaterno());
            if (usuarioSurte != null) {
                reabastoSelect.setNombreUsuarioSurte(usuarioSurte);
            }
            byte[] buffer = reportesService.imprimirOrdenSurtir(reabastoSelect, entidad, RESOURCESMESSAGE.getString("TITULO1_SURTIMIENTO_REABASTO"));
            if (buffer != null) {
                status = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("surtimientoOrden_%s.pdf", reabastoSelect.getFolio()));
                
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }
    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public void setListaReabasto(List<ReabastoExtended> listaReabasto) {
        this.listaReabasto = listaReabasto;
    }

    public List<ReabastoExtended> getListaReabasto() {
        return listaReabasto;
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

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public void setXcantidad(String xcantidad) {
        this.xcantidad = xcantidad;
    }

    public String getXcantidad() {
        return xcantidad;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public SurtimientoReabastoLazy getSurtimientoReabastoLazy() {
        return surtimientoReabastoLazy;
    }

    public void setSurtimientoReabastoLazy(SurtimientoReabastoLazy surtimientoReabastoLazy) {
        this.surtimientoReabastoLazy = surtimientoReabastoLazy;
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

    
    
}
