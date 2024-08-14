package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.TipoAreaEstructura_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.Devolucion;
import mx.mc.model.DevolucionDetalleExtended;
import mx.mc.model.DevolucionExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReportInventarioExistencias;
import mx.mc.model.TipoMotivo;
import mx.mc.model.Usuario;
import mx.mc.service.DevolucionDetalleService;
import mx.mc.service.DevolucionService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReporteMovimientosService;
import mx.mc.service.TipoMotivoService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 * @author AORTIZ
 */
@Controller
@Scope(value = "view")
public class DevolucionInsumoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DevolucionInsumoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private Estructura estructuraOrigen;
    private List<Estructura> listaEstructuras;
    private List<String> almacenesList;
    private List<String> serviciosList;
    private List<String> listaEst;
    private List<Estructura> listaEstructurasOrigen;
    private List<Estructura> listaAuxiliar2;
    private List<Estructura> listaAuxiliar;
    private List<DevolucionExtended> listDevolucion;
    private DevolucionExtended devolucionSelect;
    private DevolucionDetalleExtended devolucionDetalleSelect;
    private List<DevolucionDetalleExtended> listDevolucionDetalle;
    private List<TipoMotivo> listTipoMotivos;
    private List<ReportInventarioExistencias> rptInventarioExisList;
    private ReportInventarioExistencias rptInventarioExis;

    private String idEstructura;
    private String idEstructuraOrigen;
    private String idEstructuraDestino;
    private String textoBusqueda;
    private Usuario usuarioSession;
    private String codigoBarras;
    private boolean eliminarCodigo;
    private boolean administrador;
    private boolean isJefeArea;
    private boolean status;
    private boolean crearDevolucion;
    private boolean devolucionCreada;
    private String almacenOrigen;
    private String idAlmacenOrigen;
    private String almacenDestino;
    private String idAlmacenDestino;
    private Integer idTipoMotivo;
    private int xCantidad;
    private String idDevolucion;
    private boolean devoSinPistoleo;
    private String errAccion;
    private PermisoUsuario permiso;
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient TipoMotivoService tipoMotivoService;

    @Autowired
    private transient DevolucionService devolucionService;

    @Autowired
    private transient DevolucionDetalleService devolucionDetalleService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient MedicamentoService medicamentoService;

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            errAccion = "err.accion";
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.DEVOLUCION_INSUMOS.getSufijo());
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.usuarioSession = sesion.getUsuarioSelected();
            devoSinPistoleo = sesion.isDevolucionSinPistoleo();
            validarUsuarioAdministrador();
            alimentarComboAlmacenServicio();//origen            
            //alimentarComboAlmacen();            // destino
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
        this.almacenesList = new ArrayList<>();
        this.listaEstructurasOrigen = new ArrayList<>();
        this.listaEst = new ArrayList<>();
        this.serviciosList = new ArrayList<>();
        this.listDevolucion = new ArrayList<>();
        this.listDevolucionDetalle = new ArrayList<>();
        this.listTipoMotivos = new ArrayList<>();
        this.idEstructura = "";
        this.textoBusqueda = "";
        this.usuarioSession = new Usuario();
        this.codigoBarras = "";
        this.xCantidad = 1;
        this.eliminarCodigo = false;
        this.administrador = false;
        this.isJefeArea = false;
        this.almacenOrigen = "";
        this.idAlmacenOrigen = "";
        this.almacenDestino = "";
        this.idAlmacenDestino = "";
    }

    public void crearOrdenDevolucion() {
        try {
            LOGGER.trace("crearOrdenDevolucion...");
            crearDevolucion = true;
            devolucionCreada = false;
            devolucionSelect = new DevolucionExtended();
            listDevolucionDetalle = new ArrayList<>();
            devolucionSelect.setListDetalleDevolucion(listDevolucionDetalle);
            idDevolucion = Comunes.getUUID();
            //idEstructuraOrigen = listaEstructurasOrigen.get(0).getIdEstructura();
            //idEstructuraDestino = listaEstructuras.get(0).getIdEstructura();
            obtenerOrigen();
            obtenerDestinoByOrigen();
            idAlmacenOrigen = idEstructuraOrigen;
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo crearDEvolucion :: {}", ex.getMessage());
        }
    }

    public void obtenerOrigen() {
        for (Estructura unaEstructura : listaEstructurasOrigen) {
            if (idEstructuraOrigen.equals(unaEstructura.getIdEstructura())) {
                estructuraOrigen = unaEstructura;
                almacenOrigen = unaEstructura.getNombre();
                idAlmacenOrigen = unaEstructura.getIdEstructura();
                break;
            }
        }
    }

    public void obtenerDestino() {
        for (Estructura unaEstructura : listaEstructuras) {
            if (idEstructuraDestino.equals(unaEstructura.getIdEstructura())) {
                almacenDestino = unaEstructura.getNombre();
                idAlmacenDestino = unaEstructura.getIdEstructura();
            }
        }
    }

    public void obtenerDestinoByOrigen() {
        listaEstructuras = new ArrayList<>();
        try {
            List<Estructura> destinos = new ArrayList<>();
            estructuraOrigen = estructuraService.obtenerEstructura(idEstructuraOrigen);
            idAlmacenOrigen = estructuraOrigen.getIdEstructura();
            if (estructuraOrigen.getIdTipoAreaEstructura().equals(TipoAreaEstructura_Enum.CONSULTAINTERNA.getValue())) {
                destinos = estructuraService.obtenerAlmacenesQueSurtenServicio(idEstructuraOrigen);
                if (destinos != null) {
                    almacenDestino = destinos.get(0).getNombre();
                    idAlmacenDestino = destinos.get(0).getIdEstructura();
                    listaEstructuras.add(destinos.get(0));
                }
            } else {
                Estructura dest = estructuraService.getEstructuraPadreIdEstructura(idEstructuraOrigen);
                this.idAlmacenOrigen = idEstructuraOrigen;
                if (dest != null) {
                    almacenDestino = dest.getNombre();
                    idAlmacenDestino = dest.getIdEstructura();
                }
                listaEstructuras.add(dest);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de obtenerDestinoByOrigen: {}", ex.getMessage());
        }
    }

    public void obtenerOrdenesDevolucion() {
        Devolucion devolucion = new Devolucion();
        devolucion.setIdAlmacenOrigen(this.idEstructura);
        devolucion.setIdEstatusDevolucion(Constantes.DEV_REGISTRADA);
        try {
            this.listDevolucion = devolucionService.obtenerListDevExtended(devolucion);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de listar las devoluciones: {}", ex.getMessage());
        }
    }

    /**
     * Metodo que se utiliza para mostrar el detalle de la orden de surtimiento
     */
    public void mostrarModalDevolucion() {
        try {
            listDevolucionDetalle = devolucionDetalleService.obtenerListDetalleExtended(devolucionSelect.getIdDevolucion());
            if (devolucionSelect.getIdEstatusDevolucion().equals(Constantes.DEV_REGISTRADA)) {
                for (DevolucionDetalleExtended unDetalle : listDevolucionDetalle) {
                    unDetalle.setNombreMotivo("");
                }
            }
            this.almacenOrigen = devolucionSelect.getNombreOrigen();
            this.almacenDestino = devolucionSelect.getNombreDestino();
            this.crearDevolucion = false;
            this.devolucionCreada = true;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalDevolucion :: {}", e.getMessage());
        }
    }

    public void buscarDevolucion() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
                return;
            }
            Pattern pat = Pattern.compile(Constantes.PATRON_CAD_BUS);
            Matcher mat = pat.matcher(this.textoBusqueda);
            if (mat.matches()) {
                for (DevolucionExtended unaDevolucion : listDevolucion) {
                    if (unaDevolucion.getFolio().equals(this.textoBusqueda)) {
                        this.devolucionSelect = unaDevolucion;
                        break;
                    }
                }
                Devolucion devolucion = new Devolucion();
                devolucion.setIdAlmacenOrigen(this.idEstructura);
                devolucion.setIdEstatusDevolucion(Constantes.DEV_REGISTRADA);
                devolucion.setFolio(this.textoBusqueda);
                this.listDevolucion = devolucionService.obtenerListDevExtended(devolucion);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al realizar la busqueda por folio: {}", ex.getMessage());
        }

    }

    public void asignarMotivoDevolucion() {
        for (DevolucionDetalleExtended unDetalle : listDevolucionDetalle) {
            unDetalle.setIdMotivoDevolucion(this.idTipoMotivo);
            for (TipoMotivo unTipo : listTipoMotivos) {
                if (this.idTipoMotivo.equals(unTipo.getIdTipoMotivo())) {
                    unDetalle.setNombreMotivo(unTipo.getDescripcion());
                }
            }
        }
    }

    /**
     * Metodo utilizado para poblar el combo almacen Destino
     */
    public void alimentarComboAlmacen() {
        try {

            Estructura est = new Estructura();
            est.setActiva(Constantes.ESTATUS_ACTIVO);
            //Normal
            if (!this.administrador && !this.isJefeArea) {
                //est.setIdEstructura(usuarioSession.getIdEstructura());
                //this.listaEstructuras = this.estructuraService.obtenerAlmacenPadreDesc(est);
                est = estructuraService.obtenerEstructura(idEstructura);
                this.listaEstructuras.add(est);

                //Administrador    
            } else if (this.administrador) {
                est.setIdTipoAreaEstructura(Constantes.TIPO_AREA_ALMACEN);
                this.listaAuxiliar = this.estructuraService.obtenerAlmacenPadreDesc(est);
                idEstructuraDestino = listaAuxiliar.get(0).getIdEstructura();
                this.idEstructura = listaAuxiliar.get(0).getIdEstructura();
                this.listaEstructuras.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
                //Jefe Área    
            } else {
                //agregar los 2 de getchildrens tanto false y true a la lista , en false service filtrar solo los que tengan mostrarPC = 1
                // true = almacenes
                this.almacenesList = estructuraService.obtenerIdsEstructuraJerarquica(idEstructura, true);
                almacenesList.add(idEstructura);
                this.listaEstructuras = estructuraService.obtenerEstructurasActivosPorId(almacenesList);
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    // Origen
    public void alimentarComboAlmacenServicio() {
        try {

            Estructura est = new Estructura();
            est.setActiva(Constantes.ESTATUS_ACTIVO);

            // Normal
            if (!this.administrador && !this.isJefeArea) {
                est.setIdEstructura(usuarioSession.getIdEstructura());
                //this.listaEstructurasOrigen = this.estructuraService.obtenerAlmacenServicio();
                this.serviciosList = estructuraService.obtenerIdsEstructuraJerarquica(idEstructura, false);
                if (serviciosList != null) {
                    //listaEstructurasOrigen = estructuraService.obtenerEstructurasActivosPorId(serviciosList);
                    List<Estructura> origenList = estructuraService.obtenerEstructurasActivosPorId(serviciosList);
                    origenList.forEach(et -> {
                        listaEstructurasOrigen.add(et);
                    });
                }
                // Administrador    
            } else if (this.administrador) {
                est.setIdTipoAreaEstructura(Constantes.TIPO_AREA_ALMACEN);
                this.listaEstructurasOrigen = this.estructuraService.obtenerAlmacenServicio();
                //idEstructuraOrigen = this.listaEstructurasOrigen.get(0).getIdEstructura();

                // Jefe Área    
            } else {
                // true = almacenes
                this.almacenesList = estructuraService.obtenerIdsEstructuraJerarquica(idEstructura, true);
                if (almacenesList != null) {
                    List<Estructura> origenList = estructuraService.obtenerEstructurasActivosPorId(almacenesList);
                    origenList.forEach(et -> {
                        listaEstructurasOrigen.add(et);
                    });
                }
                this.serviciosList = estructuraService.obtenerIdsEstructuraJerarquica(idEstructura, false);
                if (serviciosList != null) {
                    //listaEstructurasOrigen = estructuraService.obtenerEstructurasActivosPorId(serviciosList);
                    List<Estructura> origenList = estructuraService.obtenerEstructurasActivosPorId(serviciosList);
                    origenList.forEach(et -> {
                        if (et.isMostrarPC()) {
                            listaEstructurasOrigen.add(et);
                        }
                    });
                }
            }
            idEstructuraOrigen = this.listaEstructurasOrigen.get(0).getIdEstructura();

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }

    public void ordenarListaEstructura2(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar2.size(); i++) {
                    if (listaAuxiliar2.get(i).getIdEstructuraPadre() != null && listaAuxiliar2.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
                        listaEstructurasOrigen.add(listaAuxiliar2.get(i));
                        ordenarListaEstructura(listaAuxiliar2.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al ordenar la lista :: {}", ex.getMessage());
        }
    }

    public void ordenarListaEstructura(Estructura estrucPrincipal) {
        try {
            if (estrucPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estrucPrincipal.getIdEstructura().toLowerCase())) {
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
            listTipoMotivos = tipoMotivoService.getListaByIdTipoMovimiento(Constantes.SALIDA_DEVOLUCION);
            idTipoMotivo = listTipoMotivos.get(0).getIdTipoMotivo();
        } catch (Exception ex) {
            LOGGER.info("Error al buscar la lista de Motivos: {}", ex.getMessage());
        }
    }

    /**
     * Metodo utilizado para crear el sub detalle del medicamento por escaneo de
     * codigo de barras
     */
    public void agregarDevolucionPorCodigo() {
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
            if (this.eliminarCodigo) {
                String msjError = eliminarLotePorCodigo();
                if (msjError.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("devolucion.info.eliminarInsunmo"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, msjError, null);
                }
                this.codigoBarras = "";
                this.xCantidad = 1;
                this.eliminarCodigo = false;
                return;
            }
            //Separar codigo de barras 
            ReabastoEnviadoExtended medicamentoDetalle = tratarCodigoDeBarras(this.codigoBarras);
            medicamentoDetalle.setIdEstructura(this.idEstructuraDestino);
            List<DevolucionDetalleExtended> temp = null;
            if (devolucionSelect != null) {
                if (devolucionSelect.getListDetalleDevolucion() != null) {
                    temp = devolucionSelect.getListDetalleDevolucion();
                } else {
                    temp = new ArrayList<>();
                }
            }            
            //Busqueda de inventario a devolver
            DevolucionDetalleExtended medicamento = obtenerInventarioByInsumo(medicamentoDetalle);
            //Validar si el medicamento se encuentra en inventario
            if (medicamento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.insumoSinInventario"), null);
                this.codigoBarras = "";
                this.xCantidad = 1;
                return;
            }
            // Si el origen es un Almacen, validamos que exista igual ya que se le tiene que restar
            if(estructuraOrigen.getIdTipoAreaEstructura().equals(Constantes.TIPO_AREA_ALMACEN)){
                medicamentoDetalle.setIdEstructura(this.idEstructuraOrigen);
                DevolucionDetalleExtended inventarioOrigen = obtenerInventarioOrigen(medicamentoDetalle);
                if (inventarioOrigen == null) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.insumoSinInventarioOrigen"), null);
                    this.codigoBarras = "";
                    this.xCantidad = 1;
                    return;
                }else{
                    medicamento.setIdInventarioOrigen(inventarioOrigen.getIdInventario());
                    medicamento.setCantidadActualOrigen(inventarioOrigen.getCantidadActual());
                }
            }
            
            
            boolean existe = false;
            int cantidadCalculada = 0;
            if (medicamento.getPresentacionComercial() == null) {
                int valor = 1;
                medicamento.setPresentacionComercial(valor);
            }
            if (medicamento.getIdTipoAlmacen() == Constantes.ALMACEN_FARMACIA && medicamento.getPresentacionComercial() == 1) {
                cantidadCalculada = medicamento.getCantidadXCaja() * this.xCantidad;
            } else {
                cantidadCalculada = this.xCantidad;
            }

            for (DevolucionDetalleExtended unMedicmento : temp) {
                if (unMedicmento.getClaveInstitucional().equals(medicamentoDetalle.getIdMedicamento())
                        && unMedicmento.getIdMotivoDevolucion().equals(this.idTipoMotivo)) {
                    unMedicmento.setCantidad(unMedicmento.getCantidad() + cantidadCalculada);
                    unMedicmento.setLote(medicamentoDetalle.getLote());
                    unMedicmento.setFechaCaducidad(medicamentoDetalle.getFechaCaducidad());
                    this.codigoBarras = "";
                    this.xCantidad = 1;
                    return;
                }
            }
            if (!existe) {
                medicamento.setIdMotivoDevolucion(this.idTipoMotivo);
                medicamento.setCantidad(cantidadCalculada);
                medicamento.setIdDevolucionDetalle(Comunes.getUUID());
                medicamento.setIdDevolucion(this.idDevolucion);
                medicamento.setLote(medicamentoDetalle.getLote());
                medicamento.setFechaCaducidad(medicamentoDetalle.getFechaCaducidad());
                for (TipoMotivo unMotivo : listTipoMotivos) {
                    if (this.idTipoMotivo.equals(unMotivo.getIdTipoMotivo())) {
                        medicamento.setNombreMotivo(unMotivo.getDescripcion());
                    }
                }
                temp.add(medicamento);
                devolucionSelect.setListDetalleDevolucion(temp);
            }
            this.codigoBarras = "";
            this.xCantidad = 1;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
    }

    public void actualizarDevolucion() {
        if (!this.permiso.isPuedeProcesar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
            status = Constantes.INACTIVO;
            return;
        }
        for (DevolucionDetalleExtended unDetalle : this.listDevolucionDetalle) {
            if (unDetalle.getNombreMotivo().equals("") || unDetalle.getNombreMotivo().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.asignarMotivo"), null);
                return;
            }
        }
        devolucionSelect.setIdEstatusDevolucion(Constantes.DEV_EN_TRANSITO);
        devolucionSelect.setFechaDevolucion(new Date());
        devolucionSelect.setIdUsrDevolucion(this.usuarioSession.getIdUsuario());
        try {
            devolucionSelect.setListDetalleDevolucion(this.listDevolucionDetalle);
            if (devolucionService.actualizarDevolucion(devolucionSelect)) {
                status = Constantes.ACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("devolucion.info.devolucionExito"), null);
                obtenerOrdenesDevolucion();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.devolucionError"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de actualizar la devolucion: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("status", status);
    }

    public void registrarDevolucion() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
                status = Constantes.INACTIVO;
                return;
            }

            devolucionSelect.setIdDevolucion(this.idDevolucion);
            devolucionSelect.setIdAlmacenOrigen(this.idAlmacenOrigen);
            devolucionSelect.setIdAlmacenDestino(this.idAlmacenDestino);
            devolucionSelect.setFechaDevolucion(new Date());
            devolucionSelect.setIdUsrDevolucion(this.usuarioSession.getIdUsuario());
            devolucionSelect.setIdEstatusDevolucion(Constantes.DEV_EN_TRANSITO);
            List<Inventario> listInventario = new ArrayList<>();
            List<MovimientoInventario> listMovimientos = new ArrayList<>();
            for (DevolucionDetalleExtended detalle : devolucionSelect.getListDetalleDevolucion()) {
                Inventario unInventario = new Inventario();
                MovimientoInventario movInv = new MovimientoInventario();
                if (detalle.getIdMotivoDevolucion().equals(TipoMotivo_Enum.SAL_DEV_MEDICAMENTO_CADUCADO.getMotivoValue())
                        || detalle.getIdMotivoDevolucion().equals(TipoMotivo_Enum.SAL_DEV_MEDICAMENTO_DANIADO.getMotivoValue())) {

                    // decrementa en el rigen de las piezas devuletas SI Y SOLO  ES detipo almacen
                    if (estructuraOrigen.getIdTipoAreaEstructura().equals(Constantes.TIPO_AREA_ALMACEN)) {
                        unInventario = new Inventario();
                        unInventario.setIdInventario(detalle.getIdInventarioOrigen());
                        unInventario.setIdInsumo(detalle.getIdInsumo());
                        unInventario.setIdEstructura(this.idAlmacenOrigen); // Se altera el almacenOrigen de su inventario
                        unInventario.setCantidadActual(detalle.getCantidadActualOrigen() - detalle.getCantidad());  // Se resta ya que sale
                        listInventario.add(unInventario);

                        //Salida del origen por devolucion dañado o caducado
                        movInv = new MovimientoInventario();
                        movInv.setIdMovimientoInventario(Comunes.getUUID());
                        movInv.setIdTipoMotivo(TipoMotivo_Enum.SAL_AJU_POR_MERMA.getMotivoValue());
                        movInv.setFecha(new java.util.Date());
                        movInv.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                        movInv.setIdEstrutcuraOrigen(this.idAlmacenOrigen);
                        movInv.setIdEstrutcuraDestino(this.idAlmacenDestino);
                        movInv.setIdInventario(detalle.getIdInventarioOrigen());
                        movInv.setCantidad(detalle.getCantidad());
                        listMovimientos.add(movInv);

                    }
                    
//                        unInventario = new Inventario();
//                        unInventario.setIdInventario(detalle.getIdInventario());
//                        unInventario.setIdInsumo(detalle.getIdInsumo());
//                        unInventario.setIdEstructura(this.idAlmacenDestino); // Se altera el almacenDestino de su inventario
//                        unInventario.setCantidadActual(detalle.getCantidadActual() + detalle.getCantidad());  // Se suma ya que regresa
//                        listInventario.add(unInventario);
//                        
                        
                        //Entrada por Ajuste al destino
                        movInv = new MovimientoInventario();
                        movInv.setIdMovimientoInventario(Comunes.getUUID());
                        movInv.setIdTipoMotivo(TipoMotivo_Enum.ENT_AJUSTEINVENTARIO.getMotivoValue());
                        movInv.setFecha(new java.util.Date());
                        movInv.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                        movInv.setIdEstrutcuraOrigen(this.idAlmacenOrigen);
                        movInv.setIdEstrutcuraDestino(this.idAlmacenDestino);
                        movInv.setIdInventario(detalle.getIdInventario());
                        movInv.setCantidad(detalle.getCantidad());
                        listMovimientos.add(movInv);

                        //Salida del destino por merma
                        movInv = new MovimientoInventario();
                        movInv.setIdMovimientoInventario(Comunes.getUUID());
                        movInv.setIdTipoMotivo(TipoMotivo_Enum.SAL_AJU_POR_MERMA.getMotivoValue());
                        movInv.setFecha(new java.util.Date());
                        movInv.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                        movInv.setIdEstrutcuraOrigen(this.idAlmacenDestino); // solo es al Destino, ya que es caducado
                        //movInv.setIdEstrutcuraDestino(this.idAlmacenDestino);
                        movInv.setIdInventario(detalle.getIdInventario());
                        movInv.setCantidad(detalle.getCantidad());
                        listMovimientos.add(movInv);                    

                } else {

                    // decrementa en el rigen de las piezas devuletas SI Y SOLO  ES detipo almacen
                    if (estructuraOrigen.getIdTipoAreaEstructura().equals(Constantes.TIPO_AREA_ALMACEN)) {
                        unInventario = new Inventario();
                        unInventario.setIdInventario(detalle.getIdInventarioOrigen());
                        unInventario.setIdInsumo(detalle.getIdInsumo());
                        unInventario.setIdEstructura(this.idAlmacenOrigen); // Se cambio a almacen Destino, ya que el que se afecta es el destino
                        unInventario.setCantidadActual(detalle.getCantidadActualOrigen()- detalle.getCantidad());  // Se resta ya que se le quita al origen para sumarle al destino, el cual se hace la devolución
                        listInventario.add(unInventario);

                        // sale del origen por devulucion de incorrecto o excedente
                        movInv = new MovimientoInventario();
                        movInv.setIdMovimientoInventario(Comunes.getUUID());
                        movInv.setIdTipoMotivo(detalle.getIdMotivoDevolucion());
                        movInv.setFecha(new java.util.Date());
                        movInv.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                        movInv.setIdEstrutcuraOrigen(this.idAlmacenOrigen);
                        movInv.setIdEstrutcuraDestino(this.idAlmacenDestino);
                        movInv.setIdInventario(detalle.getIdInventarioOrigen());
                        movInv.setCantidad(detalle.getCantidad());
                        listMovimientos.add(movInv);

                    }

                    // incrementa en el destino de las piezas devuletas
                    unInventario = new Inventario();
                    unInventario.setIdInventario(detalle.getIdInventario());
                    unInventario.setIdInsumo(detalle.getIdInsumo());
                    unInventario.setIdEstructura(this.idAlmacenDestino); // Se cambio a almacen Destino, ya que el que se afecta es el destino
                    unInventario.setCantidadActual(detalle.getCantidadActual() + detalle.getCantidad());  // Se suma ya que regresa
                    listInventario.add(unInventario);

                    // entra al destino ajuste de inv
                    movInv = new MovimientoInventario();
                    movInv.setIdMovimientoInventario(Comunes.getUUID());
                    movInv.setIdTipoMotivo(TipoMotivo_Enum.ENT_AJUSTEINVENTARIO.getMotivoValue());
                    movInv.setFecha(new java.util.Date());
                    movInv.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                    movInv.setIdEstrutcuraOrigen(this.idAlmacenOrigen);
                    movInv.setIdEstrutcuraDestino(this.idAlmacenDestino);
                    movInv.setIdInventario(detalle.getIdInventario());
                    movInv.setCantidad(detalle.getCantidad());
                    listMovimientos.add(movInv);
                }

            }
            obtenerOrigen();
            if (devolucionService.insertarDevolucionInsumoAlmacenServicio(devolucionSelect, listInventario, listMovimientos, estructuraOrigen)) {
                status = Constantes.ACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("devolucion.info.devolucionexito"), null);
                obtenerOrdenesDevolucion();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.devolucionError"), null);
            }

        } catch (Exception ex) {
            LOGGER.error("Error al guardar la devolucion: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("status", status);
    }

    public List<ReportInventarioExistencias> autocompleteMedicamento(String cadena) {
        this.rptInventarioExisList = new ArrayList<>();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(cadena);
            if (ci != null) {
                codigoBarras = cadena;
                agregarDevolucionPorCodigo();
                this.rptInventarioExis = null;
            } else {
                this.rptInventarioExisList = reporteMovimientosService.listaInsumosInventario(this.idEstructuraDestino, cadena);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return rptInventarioExisList;
    }

    public void handleSelectMedicamento(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.DevolucionReabastoMB.handleSelect()");
        this.rptInventarioExis = (ReportInventarioExistencias) e.getObject();
        String idReabastoEnviado = rptInventarioExis.getIdInventario();
        for (ReportInventarioExistencias item : this.rptInventarioExisList) {
            if (item.getIdInventario().equalsIgnoreCase(idReabastoEnviado)) {
                this.rptInventarioExis = item;
                codigoBarras = CodigoBarras.generaCodigoDeBarras(rptInventarioExis.getClaveMedicamento(), rptInventarioExis.getLote(), rptInventarioExis.getFechaCaducidad(), null);
                agregarDevolucionPorCodigo();
                this.rptInventarioExis = null;
                break;
            }
        }
    }

    public void handleUnSelectMedicamento() {
        rptInventarioExis = new ReportInventarioExistencias();

    }

    public DevolucionDetalleExtended obtenerInventarioByInsumo(ReabastoEnviadoExtended detalle) {
        Medicamento insumo;
        DevolucionDetalleExtended devolucionDetalle = null;
        try {
            devolucionDetalle = inventarioService.getInventarioForDevolucion(detalle);
            if (devolucionDetalle == null) {
                insumo = medicamentoService.obtenerMedicaByClave(detalle.getIdMedicamento());
                if (insumo != null) {
                    Inventario invent = new Inventario();
                    invent.setIdInventario(Comunes.getUUID());
                    invent.setFechaIngreso(new Date());
                    invent.setIdEstructura(idEstructuraDestino);
                    invent.setIdInsumo(insumo.getIdMedicamento());
                    invent.setIdPresentacion(insumo.getIdPresentacionSalida());
                    invent.setLote(detalle.getLote());
                    invent.setCantidadXCaja(insumo.getFactorTransformacion());
                    invent.setFechaCaducidad(detalle.getFechaCaducidad());
                    invent.setCosto(0.0);
                    invent.setCostoUnidosis(0.0);
                    invent.setCantidadActual(0);
                    invent.setExistenciaInicial(0);
                    invent.setActivo(1);
                    invent.setInsertFecha(new Date());
                    invent.setInsertIdUsuario(Comunes.obtenerUsuarioSesion().getIdUsuario());
                    if (inventarioService.insertar(invent)) {
                        devolucionDetalle = inventarioService.getInventarioForDevolucion(detalle);
                    }

                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener inventario a devolcer: {}", ex.getMessage());
        }
        return devolucionDetalle;
    }
    
    
    public DevolucionDetalleExtended obtenerInventarioOrigen(ReabastoEnviadoExtended detalle) {
        DevolucionDetalleExtended devolucionDetalle = null;
        try {
            devolucionDetalle = inventarioService.getInventarioForDevolucion(detalle);            
        } catch (Exception ex) {
            LOGGER.error("Error al obtener inventario a devolcer: {}", ex.getMessage());
        }
        return devolucionDetalle;
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
            if (ci != null) {
                detalleReabasto.setIdMedicamento(ci.getClave());
                detalleReabasto.setLote(ci.getLote());
                detalleReabasto.setFechaCaducidad(ci.getFecha());
            } else {
                ClaveProveedorBarras clave = CodigoBarras.buscaClaveSKU(codigo);
                if (clave != null) {
                    detalleReabasto.setIdMedicamento(clave.getClaveInstitucional());
                    detalleReabasto.setLote(clave.getClaveProveedor());
                    detalleReabasto.setFechaCaducidad(Mensaje.generaCaducidadSKU(clave.getCodigoBarras()));
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordeningreso.err.valor"), null);
                }
            }
            return detalleReabasto;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return detalleReabasto;
    }

    private String eliminarLotePorCodigo() {
        try {
            ReabastoEnviadoExtended medicamentoCodigo = tratarCodigoDeBarras(this.codigoBarras);
            for (DevolucionDetalleExtended devolucion : listDevolucionDetalle) {
                if (devolucion.getClaveInstitucional().equals(medicamentoCodigo.getIdMedicamento())
                        && devolucion.getIdMotivoDevolucion().equals(this.idTipoMotivo)) {
                    int result = 0;
                    if (devolucion.getIdTipoAlmacen() == Constantes.ALMACEN_FARMACIA) {
                        result = devolucion.getCantidad() - (xCantidad * devolucion.getFactorTransformacion());
                    } else {
                        result = devolucion.getCantidad() - xCantidad;
                    }
                    if (result < 0) {
                        return RESOURCES.getString("devolucion.error.eliminarInsumo");
                    } else {
                        devolucion.setCantidad(result);
                        this.codigoBarras = "";
                        this.xCantidad = 0;
                        if (result == 0) {
                            listDevolucionDetalle.remove(devolucion);
                        }
                        return "";
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
        return RESOURCES.getString("devolucion.error.insumoNoEncontrado");
    }

    public void validarUsuarioAdministrador() {
        try {
            this.administrador = Comunes.isAdministrador();
            this.isJefeArea = Comunes.isJefeArea();
            this.idEstructura = usuarioSession.getIdEstructura();
//            if (!this.administrador && !this.isJefeArea) {
//                this.idEstructura = usuarioSession.getIdEstructura();
//            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
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

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public void setListaEstructuras(List<Estructura> listaEstructuras) {
        this.listaEstructuras = listaEstructuras;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public List<DevolucionExtended> getListDevolucion() {
        return listDevolucion;
    }

    public List<DevolucionDetalleExtended> getListDevolucionDetalle() {
        return listDevolucionDetalle;
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

    public Integer getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(Integer idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public List<TipoMotivo> getListTipoMotivos() {
        return listTipoMotivos;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getxCantidad() {
        return xCantidad;
    }

    public void setxCantidad(int xCantidad) {
        this.xCantidad = xCantidad;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isCrearDevolucion() {
        return crearDevolucion;
    }

    public void setCrearDevolucion(boolean crearDevolucion) {
        this.crearDevolucion = crearDevolucion;
    }

    public boolean isDevolucionCreada() {
        return devolucionCreada;
    }

    public void setDevolucionCreada(boolean devolucionCreada) {
        this.devolucionCreada = devolucionCreada;
    }

    public DevolucionExtended getDevolucionSelect() {
        return devolucionSelect;
    }

    public void setDevolucionSelect(DevolucionExtended devolucionSelect) {
        this.devolucionSelect = devolucionSelect;
    }

    public DevolucionDetalleExtended getDevolucionDetalleSelect() {
        return devolucionDetalleSelect;
    }

    public void setDevolucionDetalleSelect(DevolucionDetalleExtended devolucionDetalleSelect) {
        this.devolucionDetalleSelect = devolucionDetalleSelect;
    }

    public List<ReportInventarioExistencias> getRptInventarioExisList() {
        return rptInventarioExisList;
    }

    public void setRptInventarioExisList(List<ReportInventarioExistencias> rptInventarioExisList) {
        this.rptInventarioExisList = rptInventarioExisList;
    }

    public ReportInventarioExistencias getRptInventarioExis() {
        return rptInventarioExis;
    }

    public void setRptInventarioExis(ReportInventarioExistencias rptInventarioExis) {
        this.rptInventarioExis = rptInventarioExis;
    }

    public boolean isDevoSinPistoleo() {
        return devoSinPistoleo;
    }

    public void setDevoSinPistoleo(boolean devoSinPistoleo) {
        this.devoSinPistoleo = devoSinPistoleo;
    }

    public String getIdEstructuraOrigen() {
        return idEstructuraOrigen;
    }

    public void setIdEstructuraOrigen(String idEstructuraOrigen) {
        this.idEstructuraOrigen = idEstructuraOrigen;
    }

    public String getIdEstructuraDestino() {
        return idEstructuraDestino;
    }

    public void setIdEstructuraDestino(String idEstructuraDestino) {
        this.idEstructuraDestino = idEstructuraDestino;
    }

    public List<Estructura> getListaEstructurasOrigen() {
        return listaEstructurasOrigen;
    }

    public void setListaEstructurasOrigen(List<Estructura> listaEstructurasOrigen) {
        this.listaEstructurasOrigen = listaEstructurasOrigen;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

}
