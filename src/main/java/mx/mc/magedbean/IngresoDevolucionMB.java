package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import mx.mc.enums.TipoMotivo_Enum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.DetalleReabastoInsumo;
import mx.mc.model.Devolucion;
import mx.mc.model.DevolucionDetalleExtended;
import mx.mc.model.DevolucionExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.TipoMotivo;
import mx.mc.model.Usuario;
import mx.mc.service.DevolucionDetalleService;
import mx.mc.service.DevolucionService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.TipoMotivoService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;

/**
 *
 * @author gcruz
 *
 */
@Controller
@Scope(value = "view")
public class IngresoDevolucionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(IngresoDevolucionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private List<Estructura> listaEstructuras;
    private List<Estructura> listaEstructurasOrigen;
    private List<Estructura> listaAuxiliar;
    private List<DevolucionExtended> listDevolucion;
    private DevolucionExtended devolucionSelect;
    private List<DevolucionDetalleExtended> listDevolucionDetalle;
    private List<TipoMotivo> listTipoMotivos;

    private String idEstructura;
    private String textoBusqueda;
    private Usuario usuarioSession;
    private String codigoBarras;
    private boolean administrador;
    private boolean status;
    private String almacenOrigen;
    private String idAlmacenOrigen;
    private String almacenDestino;
    private String idAlmacenDestino;
    private Integer idTipoMotivo;
    private int xCantidad;
    private PermisoUsuario permiso;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient DevolucionService devolucionService;

    @Autowired
    private transient DevolucionDetalleService devolucionDetalleService;

    @Autowired
    private transient TipoMotivoService tipoMotivoService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.INGRESODEVOLUCION.getSufijo());
            this.usuarioSession = Comunes.obtenerUsuarioSesion();
            validarUsuarioAdministrador();
            alimentarComboAlmacen();
            alimentarComboTipoMotivo();
            obtenerOrdenesDevolucion();
        } catch (Exception e) {
            LOGGER.error("Error en el metodo init :: {}", e.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {
        this.listaEstructuras = new ArrayList<>();
        this.listDevolucion = new ArrayList<>();
        this.listDevolucionDetalle = new ArrayList<>();
        this.idEstructura = "";
        this.textoBusqueda = "";
        this.usuarioSession = new Usuario();
        this.codigoBarras = "";
        this.xCantidad = 1;
        this.administrador = false;
        this.almacenOrigen = "";
        this.almacenDestino = "";
    }

    public void validarUsuarioAdministrador() {
        try {
            this.administrador = Comunes.isAdministrador();
            if (!this.administrador) {
                this.idEstructura = usuarioSession.getIdEstructura();
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
            this.listaEstructurasOrigen = this.estructuraService.obtenerAlmacenServicio();
            if (!this.administrador) {
                est.setIdEstructura(usuarioSession.getIdEstructura());
                this.listaEstructuras = this.estructuraService.obtenerLista(est);

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

    public void alimentarComboTipoMotivo() {
        try {
            listTipoMotivos = tipoMotivoService.getListaByIdTipoMovimiento(Constantes.ENTRADA_DEVOLUCION);
        } catch (Exception ex) {
            LOGGER.info("Error al buscar la lista de Motivos: {}", ex.getMessage());
        }
    }

    public void obtenerOrdenesDevolucion() {
        Devolucion devolucion = new Devolucion();
        devolucion.setIdAlmacenDestino(this.idEstructura);
        devolucion.setIdEstatusDevolucion(Constantes.DEV_EN_TRANSITO);
        try {
            this.listDevolucion = devolucionService.obtenerListDevExtended(devolucion);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de listar las devoluciones: {}", ex.getMessage());
        }
    }

    public void mostrarModalDevolucion() {
        try {
            listDevolucionDetalle = devolucionDetalleService.obtenerListDetalleExtended(devolucionSelect.getIdDevolucion());
            for (DevolucionDetalleExtended unDetalle : listDevolucionDetalle) {
                unDetalle.setNombreMotivo(null);
                unDetalle.setIdMotivoDevolucion(null);
                
                Inventario unInventario = null;
                unInventario = inventarioService.obtenerInventariosPorInsumoLoteFechaCadIdInsumoClProv(unDetalle.getLote(), unDetalle.getIdInsumo(), devolucionSelect.getIdAlmacenDestino(), unDetalle.getFechaCaducidad());
                if (unInventario != null) {
                    unDetalle.setIdInventario(unInventario.getIdInventario());
                }

            }
            this.almacenOrigen = devolucionSelect.getNombreOrigen();
            this.almacenDestino = devolucionSelect.getNombreDestino();
            for (Estructura unaEstructura : listaEstructuras) {

                if (unaEstructura.getNombre().equalsIgnoreCase(this.almacenDestino)) {
                    this.idAlmacenDestino = unaEstructura.getIdEstructura();
                }
            }
            for (Estructura unaEstructura : listaEstructurasOrigen) {
                if (unaEstructura.getNombre().equalsIgnoreCase(this.almacenOrigen)) {
                    this.idAlmacenOrigen = unaEstructura.getIdEstructura();
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalDevolucion :: {}", e.getMessage());
        }
    }

    public void asignarMotivoDevolucion() {
        for (DevolucionDetalleExtended unDetalle : listDevolucionDetalle) {
            for (TipoMotivo unTipo : listTipoMotivos) {
                if (this.idTipoMotivo.equals(unTipo.getIdTipoMotivo())) {
                    unDetalle.setNombreMotivo(unTipo.getDescripcion());
                }
            }
        }
    }

    public void ingresarDevolucionPorCodigo() {
        try {
            if (this.codigoBarras.isEmpty()) {
                return;
            }
            if (this.xCantidad == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.xCantidadCero"), null);
                this.codigoBarras = "";
                this.xCantidad = 1;
                return;
            }
            boolean cantidadMayor = false;
            boolean medEncontrado = false;
            boolean motivoIncorrecto = false;
            int cantidadCalculada = 0;
            
            DetalleReabastoInsumo medicamentoDetalle = tratarCodigoDeBarras(this.codigoBarras);
            List<DevolucionDetalleExtended> listInsumosEncontrada = new ArrayList<>();
            for (DevolucionDetalleExtended devolucion : listDevolucionDetalle) {                
                if (medicamentoDetalle.getIdMedicamento().equals(devolucion.getClaveInstitucional())) {
                    listInsumosEncontrada.add(devolucion);
                }
            }
            DevolucionDetalleExtended insumo = new DevolucionDetalleExtended();
            if(!listInsumosEncontrada.isEmpty()) {
                for(DevolucionDetalleExtended unaDevolucion : listInsumosEncontrada) {
                    if (devolucionSelect.getIdTipoAlmacenDestino() == Constantes.ALMACEN_FARMACIA //||  devolucionSelect.getIdTipoAlmacenDestino() == Constantes.ALMACEN
                      ) {
                        cantidadCalculada = unaDevolucion.getFactorTransformacion() * this.xCantidad;
                    } else {
                        cantidadCalculada = this.xCantidad;
                    }
                    if(unaDevolucion.getIdMotivoDevolucion() != null) {
                        if(unaDevolucion.getIdMotivoDevolucion().equals(this.idTipoMotivo)) {
                            motivoIncorrecto = false;
                            insumo = unaDevolucion;
                            if(unaDevolucion.getCantidad() < (unaDevolucion.getCantidadIngresar() + cantidadCalculada)) {
                                cantidadMayor = true;                                
                                break;
                            } break; 
                        } else {
                            motivoIncorrecto = true;
                        }                        
                    } else {
                        motivoIncorrecto = false;
                        insumo = unaDevolucion;
                        if(unaDevolucion.getCantidad() < (unaDevolucion.getCantidadIngresar() + cantidadCalculada)) {
                            cantidadMayor = true;
                        } else{
                            cantidadMayor = false;
                            break;
                        }
                    }
                } 
                medEncontrado = true;
                if(cantidadMayor) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ingresoDev.error.mayorDevo"), null);
                    this.codigoBarras = "";
                    this.xCantidad = 1;
                    return;
                }
                if(motivoIncorrecto) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ingresoDev.error.errorMotivo"), null);
                    this.codigoBarras = "";
                    this.xCantidad = 1;
                    return;
                }
                if(medEncontrado) {
                    for (DevolucionDetalleExtended unaDevolucion : listDevolucionDetalle) { 
                        if (insumo.equals(unaDevolucion)) {
                            unaDevolucion.setIdMotivoDevolucion(idTipoMotivo);
                            for(TipoMotivo motivo : this.listTipoMotivos) {
                                if(motivo.getIdTipoMotivo().equals(idTipoMotivo)) {
                                    unaDevolucion.setNombreMotivo(motivo.getDescripcion());
                                    break;
                                }
                            }                        
                            unaDevolucion.setLote(medicamentoDetalle.getLote());
                            unaDevolucion.setCantidadIngresar(unaDevolucion.getCantidadIngresar() + cantidadCalculada);
                            break;
                        }
                    }                    
                }
                
                
            }
            if (!medEncontrado) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ingresoDev.error.noInsumoIngreso"), null);
                this.codigoBarras = "";
                this.xCantidad = 1;
                return;
            }
            this.codigoBarras = "";
            this.xCantidad = 1;
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al ingresar el insumo por codigo: {}", ex.getMessage());
        }
    }

    public void registrarIngresoDevolucion() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
                status = Constantes.INACTIVO;
                return;
            }
            List<Inventario> listInventario = new ArrayList<>();
            List<MovimientoInventario> listMovimientoInve = new ArrayList<>();            
            for (DevolucionDetalleExtended unDetalle : this.listDevolucionDetalle) {
                if (unDetalle.getNombreMotivo() == null || unDetalle.getNombreMotivo().isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.asignarMotivo"), null);
                    return;
                }
                if (unDetalle.getCantidadIngresar() == 0) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ingresoDev.error.ceroCantidadIngr"), null);
                    status = Constantes.INACTIVO;
                    return;
                }
                if (!unDetalle.getCantidadIngresar().equals(unDetalle.getCantidad())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ingreDev.err.cantiDif"), null);
                    status = Constantes.INACTIVO;
                    return;
                }

                Inventario unInventario = new Inventario();
                if (unDetalle.getIdInventario() != null) {
                    unInventario.setIdInventario(unDetalle.getIdInventario());
                    unInventario = inventarioService.obtener(unInventario);
                } else {
                    unInventario = inventarioService.obtenerInventariosPorInsumoEstructuraYLote(
                            unDetalle.getIdInsumo(), this.idEstructura, unDetalle.getLote());
                }
                if(unDetalle.getIdMotivoDevolucion().equals(TipoMotivo_Enum.DEV_REABA_MEDICAMENTO_INCORRECTO.getMotivoValue()) || 
                    unDetalle.getIdMotivoDevolucion().equals(TipoMotivo_Enum.DEV_ENTRADA_MEDICAMENTO_EXCEDENTE.getMotivoValue())) {
                    boolean inventarioEncontrado = false;
                    if(!listInventario.isEmpty()) {
                        for(Inventario inventario : listInventario) {
                            if(inventario.getIdInventario().equals(unInventario.getIdInventario())) {
                                inventario.setCantidadActual(inventario.getCantidadActual() + unDetalle.getCantidadIngresar());
                                inventarioEncontrado = true;
                                break;
                            }                        
                        }                                 
                    }
                    if(!inventarioEncontrado) {
                        unInventario.setCantidadActual(unInventario.getCantidadActual() + unDetalle.getCantidadIngresar());
                        listInventario.add(unInventario);
                    }
                                        
                } else {
                    MovimientoInventario movimientoInvMerma = new MovimientoInventario();
                    String idMovimientoInventario = Comunes.getUUID();
                    movimientoInvMerma.setIdMovimientoInventario(idMovimientoInventario);
                    movimientoInvMerma.setIdTipoMotivo(TipoMotivo_Enum.SAL_AJU_POR_MERMA.getMotivoValue());
                    movimientoInvMerma.setFecha(new Date());
                    movimientoInvMerma.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                    //El origen y destino seria el mismo debido a que ya no se ingresa en ningun almacén
                    movimientoInvMerma.setIdEstrutcuraOrigen(this.idAlmacenDestino);
                    movimientoInvMerma.setIdEstrutcuraDestino(this.idAlmacenDestino);
                    movimientoInvMerma.setIdInventario(unInventario.getIdInventario());
                    movimientoInvMerma.setCantidad(unDetalle.getCantidadIngresar());
                    listMovimientoInve.add(movimientoInvMerma);
                }
                
                MovimientoInventario unMovimientoInv = new MovimientoInventario();
                String idMovimientoInventario = Comunes.getUUID();
                unMovimientoInv.setIdMovimientoInventario(idMovimientoInventario);
                unMovimientoInv.setIdTipoMotivo(unDetalle.getIdMotivoDevolucion());
                unMovimientoInv.setFecha(new Date());
                unMovimientoInv.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                unMovimientoInv.setIdEstrutcuraOrigen(this.idAlmacenOrigen);
                unMovimientoInv.setIdEstrutcuraDestino(this.idAlmacenDestino);
                unMovimientoInv.setIdInventario(unInventario.getIdInventario());
                unMovimientoInv.setCantidad(unDetalle.getCantidadIngresar());

                listMovimientoInve.add(unMovimientoInv);
            }
            devolucionSelect.setIdEstatusDevolucion(Constantes.DEV_INGRESADA);
            devolucionSelect.setListDetalleDevolucion(this.listDevolucionDetalle);
            if (devolucionService.ingresarDevolucion(devolucionSelect, listInventario, listMovimientoInve)) {
                obtenerOrdenesDevolucion();
                status = Constantes.ACTIVO;
                Mensaje.showMessage("Info", RESOURCES.getString("ingresoDev.info.exitoIngresoDev"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ingresoDev.error.errorIngresoDev"), null);
            }

        } catch (Exception ex) {
            LOGGER.info("Ocurrio un error al momento de ingresar al inventario: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("status", status);
    }

    /**
     * Metodo utilizado para convertir el codigo de barras en un objeto de tipo
     * DetalleReabastoInsumo
     *
     * @param codigo String
     * @return DetalleReabastoInsumo
     */
    private DetalleReabastoInsumo tratarCodigoDeBarras(String codigo) {
        DetalleReabastoInsumo detalleReabasto = new DetalleReabastoInsumo();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            detalleReabasto.setIdMedicamento(ci.getClave());
            detalleReabasto.setLote(ci.getLote());
            detalleReabasto.setFecha(ci.getFecha());
            if (ci.getCantidad() != null) {
                detalleReabasto.setCantidadCaja(ci.getCantidad());
            }
            return detalleReabasto;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return detalleReabasto;
    }

    public DevolucionExtended getDevolucionSelect() {
        return devolucionSelect;
    }

    public void setDevolucionSelect(DevolucionExtended devolucionSelect) {
        this.devolucionSelect = devolucionSelect;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public int getxCantidad() {
        return xCantidad;
    }

    public void setxCantidad(int xCantidad) {
        this.xCantidad = xCantidad;
    }

    public Integer getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(Integer idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public List<TipoMotivo> getListTipoMotivos() {
        return listTipoMotivos;
    }

    public List<Estructura> getListaEstructuras() {
        return listaEstructuras;
    }

    public List<Estructura> getListaEstructurasOrigen() {
        return listaEstructurasOrigen;
    }

    public void setListaEstructurasOrigen(List<Estructura> listaEstructurasOrigen) {
        this.listaEstructurasOrigen = listaEstructurasOrigen;
    }

    public List<DevolucionExtended> getListDevolucion() {
        return listDevolucion;
    }

    public List<DevolucionDetalleExtended> getListDevolucionDetalle() {
        return listDevolucionDetalle;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }
    
}
