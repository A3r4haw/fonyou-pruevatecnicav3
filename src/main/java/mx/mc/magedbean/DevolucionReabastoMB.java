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
public class DevolucionReabastoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DevolucionReabastoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private List<Estructura> listaEstructuras;
    private String codigoBarras;
    private boolean eliminarCodigo;    
    private boolean devolucionCreada;
    private List<Estructura> listaAuxiliar;
    private List<DevolucionExtended> listDevolucion;    
    private DevolucionDetalleExtended devolucionDetalleSelect;
    private DevolucionExtended devolucionSelected;
    private List<DevolucionDetalleExtended> listDevolucionDetalle;    
    private List<ReportInventarioExistencias> rptInventarioExisList;
    private ReportInventarioExistencias rptInventarioExis;
    private String almacenDestino;
    private String idAlmacenDestino;
    private Integer idTipoMotivo;
    private String idEstructura;
    private List<TipoMotivo> listTipoMotivos;
    private boolean administrador;
    private boolean status;
    private boolean crearDevolucion;
    private String textoBusqueda;
    private Usuario usuarioSession;
    private String almacenOrigen;
    private String idAlmacenOrigen;    
    private boolean devoSinPistoleo;
    private PermisoUsuario permiso;
    private int xCantidad;
    private String idDevolucion;    
    
    @Autowired
    private transient DevolucionDetalleService devolucionDetalleService;

    @Autowired
    private transient ReporteMovimientosService reporteMovimientosService;

    @Autowired
    private transient InventarioService inventarioService;
    
    @Autowired
    private transient EstructuraService estructuraService;

    @Autowired
    private transient TipoMotivoService tipoMotivoService;

    @Autowired
    private transient DevolucionService devolucionService;
    
    @Autowired
    private transient MedicamentoService medicamentoService; 

    /**
     * Metodo que se ejecuta despues de cargar la pantalla de pacientes
     */
    @PostConstruct
    public void init() {
        try {
            initialize();
            permiso = Comunes.obtenerPermisos(Transaccion_Enum.DEVOLUCION.getSufijo());
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.usuarioSession = sesion.getUsuarioSelected();
            devoSinPistoleo = sesion.isDevolucionSinPistoleo();
            validarUsuarioAdministrador();
            alimentarComboAlmacen();
            alimentarComboTipoMotivo();
            obtenerOrdenesDevolucion();
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo init :: {}", ex.getMessage());
        }
    }

    /**
     * Metodo que inicializa todos los atributos de la clase
     */
    public void initialize() {        
        this.idEstructura = "";
        this.textoBusqueda = "";
        this.almacenOrigen = "";
        this.idAlmacenOrigen = "";
        this.almacenDestino = "";
        this.idAlmacenDestino = "";
        this.codigoBarras = "";                
        this.xCantidad = 1;
        this.usuarioSession = new Usuario();
        this.eliminarCodigo = false;
        this.administrador = false;        
        this.listaEstructuras = new ArrayList<>();
        this.listDevolucion = new ArrayList<>();
        this.listDevolucionDetalle = new ArrayList<>();
        this.listTipoMotivos = new ArrayList<>();
    }

    public void crearOrdenDevolucion() {
        LOGGER.trace("crearOrdenDevolucion...");
        crearDevolucion = true;
        devolucionCreada = false;
        devolucionSelected = new DevolucionExtended();
        listDevolucionDetalle = new ArrayList<>();
        devolucionSelected.setListDetalleDevolucion(listDevolucionDetalle);
        idDevolucion = Comunes.getUUID();
        for (Estructura unaEstructura : listaEstructuras) {
            if (idEstructura.equals(unaEstructura.getIdEstructura())) {
                almacenOrigen = unaEstructura.getNombre();
                idAlmacenOrigen = unaEstructura.getIdEstructura();
                try {
                    Estructura destino = estructuraService.getEstructuraPadreIdEstructura(
                            unaEstructura.getIdEstructura());
                    if (destino != null) {
                        almacenDestino = destino.getNombre();
                        idAlmacenDestino = destino.getIdEstructura();
                    }
                } catch (Exception ex) {
                    LOGGER.info("Ocurrio un error al obtener la estructura padre del almacen: {}", ex.getMessage());
                }
                return;
            }
        }
    }

    /**
     * Metodo que se utiliza para mostrar el detalle de la orden de surtimiento
     */
    public void mostrarModalDevolucion() {
        try {
            listDevolucionDetalle = devolucionDetalleService.obtenerListDetalleExtended(devolucionSelected.getIdDevolucion());
            if (devolucionSelected.getIdEstatusDevolucion().equals(Constantes.DEV_REGISTRADA)) {
                for (DevolucionDetalleExtended unDetalleExt : listDevolucionDetalle) {
                    unDetalleExt.setNombreMotivo("");
                }
            }
            this.almacenOrigen = devolucionSelected.getNombreOrigen();
            this.almacenDestino = devolucionSelected.getNombreDestino();
            this.crearDevolucion = false;
            this.devolucionCreada = true;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo mostrarModalDevolucion :: {}", e.getMessage());
        }
    }
        
    public void obtenerOrdenesDevolucion() {
        Devolucion devoluc = new Devolucion();
        devoluc.setIdAlmacenOrigen(this.idEstructura);
        devoluc.setIdEstatusDevolucion(Constantes.DEV_REGISTRADA);
        try {
            this.listDevolucion = devolucionService.obtenerListDevExtended(devoluc);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de listar las devoluciones: {}", ex.getMessage());
        }
    }

    public void asignarMotivoDevolucion() {
        for (DevolucionDetalleExtended detalle : listDevolucionDetalle) {
            detalle.setIdMotivoDevolucion(this.idTipoMotivo);
            for (TipoMotivo unTipo : listTipoMotivos) {
                if (this.idTipoMotivo.equals(unTipo.getIdTipoMotivo())) {
                    detalle.setNombreMotivo(unTipo.getDescripcion());
                }
            }
        }
    }
    
    public void buscarDevolucion() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
                return;
            }
            Pattern patte = Pattern.compile(Constantes.PATRON_CAD_BUS);
            Matcher mat = patte.matcher(this.textoBusqueda);
            if (mat.matches()) {
                for (DevolucionExtended unaDevolucion : listDevolucion) {
                    if (unaDevolucion.getFolio().equals(this.textoBusqueda)) {
                        this.devolucionSelected = unaDevolucion;
                        break;
                    }
                }
                Devolucion dev = new Devolucion();
                dev.setIdAlmacenOrigen(this.idEstructura);
                dev.setIdEstatusDevolucion(Constantes.DEV_REGISTRADA);
                dev.setFolio(this.textoBusqueda);
                this.listDevolucion = devolucionService.obtenerListDevExtended(dev);
            }
        } catch (Exception exc) {
            LOGGER.error("Ocurrio un error al realizar la busqueda por folio: {}", exc.getMessage());
        }
    }

    public void ordenarListaEstructura(Estructura estPrincipal) {
        try {
            if (estPrincipal != null) {
                for (int i = 1; i < listaAuxiliar.size(); i++) {
                    if (listaAuxiliar.get(i).getIdEstructuraPadre() != null
                            && listaAuxiliar.get(i).getIdEstructuraPadre().toLowerCase().contains(estPrincipal.getIdEstructura().toLowerCase())) {
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
     * Metodo utilizado para poblar el combo almacen
     */
    public void alimentarComboAlmacen() {
        try {

            Estructura estAlm = new Estructura();
            estAlm.setActiva(Constantes.ESTATUS_ACTIVO);

            if (!this.administrador) {
                estAlm.setIdEstructura(usuarioSession.getIdEstructura());
                this.listaEstructuras = this.estructuraService.obtenerLista(estAlm);

            } else {
                estAlm.setIdTipoAreaEstructura(Constantes.TIPO_AREA_ALMACEN);
                this.listaAuxiliar = this.estructuraService.obtenerLista(estAlm);

                this.idEstructura = listaAuxiliar.get(0).getIdEstructura();
                this.listaEstructuras.add(listaAuxiliar.get(0));
                ordenarListaEstructura(listaAuxiliar.get(0));
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo alimentarComboAlmacen :: {}", e.getMessage());
        }
    }


    public void alimentarComboTipoMotivo() {
        try {
            listTipoMotivos = tipoMotivoService.getListaByIdTipoMovimiento(Constantes.SALIDA_DEVOLUCION);
        } catch (Exception exe) {
            LOGGER.info("Error al buscar la lista de Motivos: {}", exe.getMessage());
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
                this.xCantidad = 1;
                this.codigoBarras = "";
                return;
            }
            if (this.eliminarCodigo) {
                String mnsjError = eliminarLotePorCodigo();
                if (mnsjError.isEmpty()) {
                    Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("devolucion.info.eliminarInsunmo"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, mnsjError, null);
                }
                this.codigoBarras = "";                
                this.eliminarCodigo = false;
                this.xCantidad = 1;
                return;
            }
            //Separar codigo de barras 
            ReabastoEnviadoExtended medicaDetalle = tratarCodigoDeBarras(this.codigoBarras);
            medicaDetalle.setIdEstructura(this.idEstructura);
            List<DevolucionDetalleExtended> temp = null;
            if (devolucionSelected != null) {
                if (devolucionSelected.getListDetalleDevolucion() != null) {
                    temp = devolucionSelected.getListDetalleDevolucion();
                } else {
                    temp = new ArrayList<>();
                }
            }
            //Busqueda de inventario a devolver
            DevolucionDetalleExtended medicamentoD = obtenerInventarioByInsumo(medicaDetalle);
            //Validar si el medicamento se encuentra en inventario
            if (medicamentoD == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.insumoSinInventario"), null);
                this.codigoBarras = "";
                this.xCantidad = 1;
                return;
            }
            boolean existe = false;
            int cantidadCalculada = 0;
            if (medicamentoD.getPresentacionComercial() == null) {
                int valor = 1;
                medicamentoD.setPresentacionComercial(valor);
            }
            if (medicamentoD.getIdTipoAlmacen() == Constantes.ALMACEN_FARMACIA
                    || medicamentoD.getIdTipoAlmacen() == Constantes.ALMACEN) {
                cantidadCalculada = medicamentoD.getCantidadXCaja() * this.xCantidad;
            } else {
                cantidadCalculada = this.xCantidad;
            }
            for (DevolucionDetalleExtended unMedicmento : temp) {
                if (unMedicmento.getClaveInstitucional().equals(medicaDetalle.getIdMedicamento())
                        && unMedicmento.getIdMotivoDevolucion().equals(this.idTipoMotivo)) {
                    if (medicamentoD.getCantidadActual() < (unMedicmento.getCantidad() + cantidadCalculada)) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.mayorComprometida"), null);
                        this.codigoBarras = "";
                        this.xCantidad = 1;
                        return;
                    }
                    unMedicmento.setCantidad(unMedicmento.getCantidad() + cantidadCalculada);
                    this.codigoBarras = "";
                    this.xCantidad = 1;
                    return;
                }
            }
            if (!existe) {
                if (medicamentoD.getCantidadActual() < (cantidadCalculada)) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.mayorComprometida"), null);
                    this.codigoBarras = "";
                    this.xCantidad = 1;
                    return;
                }
                medicamentoD.setIdMotivoDevolucion(this.idTipoMotivo);
                medicamentoD.setCantidad(cantidadCalculada);
                medicamentoD.setIdDevolucionDetalle(Comunes.getUUID());
                medicamentoD.setIdDevolucion(this.idDevolucion);
                for (TipoMotivo unMotivo : listTipoMotivos) {
                    if (this.idTipoMotivo.equals(unMotivo.getIdTipoMotivo())) {
                        medicamentoD.setNombreMotivo(unMotivo.getDescripcion());
                        break;
                    }
                }
                temp.add(medicamentoD);
                devolucionSelected.setListDetalleDevolucion(temp);
            }
            this.codigoBarras = "";
            this.xCantidad = 1;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
    }

    public void actualizarDevolucion() {
        if (!this.permiso.isPuedeProcesar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
            status = Constantes.INACTIVO;
            return;
        }
        for (DevolucionDetalleExtended unDetalleE : this.listDevolucionDetalle) {
            if (unDetalleE.getNombreMotivo() == null || unDetalleE.getNombreMotivo().isEmpty()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.asignarMotivo"), null);
                return;
            }
        }
        devolucionSelected.setIdEstatusDevolucion(Constantes.DEV_EN_TRANSITO);
        devolucionSelected.setFechaDevolucion(new Date());
        devolucionSelected.setIdUsrDevolucion(this.usuarioSession.getIdUsuario());
        try {
            devolucionSelected.setListDetalleDevolucion(this.listDevolucionDetalle);
            if (devolucionService.actualizarDevolucion(devolucionSelected)) {
                status = Constantes.ACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("devolucion.info.devolucionExito"), null);
                obtenerOrdenesDevolucion();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.devolucionError"), null);
            }
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al momento de actualizar la devolucion: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("status", status);
    }

    public void registrarDevolucion() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
                status = Constantes.INACTIVO;
                return;
            }

            devolucionSelected.setIdDevolucion(this.idDevolucion);
            devolucionSelected.setIdAlmacenOrigen(this.idAlmacenOrigen);
            devolucionSelected.setIdAlmacenDestino(this.idAlmacenDestino);
            devolucionSelected.setFechaDevolucion(new Date());
            devolucionSelected.setIdUsrDevolucion(this.usuarioSession.getIdUsuario());
            devolucionSelected.setIdEstatusDevolucion(Constantes.DEV_EN_TRANSITO);
            List<Inventario> inventarioList = new ArrayList<>();
            List<MovimientoInventario> listMovimientos = new ArrayList<>();
            for (DevolucionDetalleExtended detalle : devolucionSelected.getListDetalleDevolucion()) {
                boolean inventarioEncontrado = false;
                if(!inventarioList.isEmpty()) {
                    for(Inventario inventario : inventarioList) {
                        if(inventario.getIdInventario().equals(detalle.getIdInventario())) {
                            inventario.setCantidadActual(inventario.getCantidadActual() - detalle.getCantidad());
                            inventarioEncontrado = true;
                            break;
                        }
                    }                    
                }
                if(!inventarioEncontrado) {
                    Inventario unInventario = new Inventario();
                    unInventario.setIdInventario(detalle.getIdInventario());
                    unInventario.setIdInsumo(detalle.getIdInsumo());
                    unInventario.setIdEstructura(this.idAlmacenOrigen);
                    unInventario.setCantidadActual(detalle.getCantidadActual() - detalle.getCantidad());
                    inventarioList.add(unInventario);
                }

                MovimientoInventario moviInv = new MovimientoInventario();

                moviInv.setIdMovimientoInventario(Comunes.getUUID());
                moviInv.setIdTipoMotivo(detalle.getIdMotivoDevolucion());
                moviInv.setFecha(new java.util.Date());
                moviInv.setIdUsuarioMovimiento(usuarioSession.getIdUsuario());
                moviInv.setIdEstrutcuraOrigen(this.idAlmacenOrigen);
                moviInv.setIdEstrutcuraDestino(this.idAlmacenDestino);
                moviInv.setIdInventario(detalle.getIdInventario());
                moviInv.setCantidad(detalle.getCantidad());
                listMovimientos.add(moviInv);
            }
            if (devolucionService.insertarDevolucionRea(devolucionSelected, inventarioList, listMovimientos)) {
                status = Constantes.ACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_INFO, RESOURCES.getString("devolucion.info.devolucionExito"), null);
                obtenerOrdenesDevolucion();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("devolucion.error.devolucionError"), null);
            }

        } catch (Exception exp) {
            LOGGER.error("Error al guardar la devolucion: {}", exp.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("status", status);
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
                break;
            }
        }
    }
        
    public List<ReportInventarioExistencias> autocompleteMedicamento(String cadena) {
        this.rptInventarioExisList = new ArrayList<>();
        try {
            this.rptInventarioExisList = reporteMovimientosService.listaInsumosInventario(this.idEstructura, cadena);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener autocompleteMedicamento : {}", ex.getMessage());
        }
        return rptInventarioExisList;
    }

    public DevolucionDetalleExtended obtenerInventarioByInsumo(ReabastoEnviadoExtended detalle) {

        DevolucionDetalleExtended devolucionDetalle = null;
        try {
            devolucionDetalle = inventarioService.getInventarioForDevolucion(detalle);
        } catch (Exception ex) {
            LOGGER.error("Error al obtenerInventarioByInsumo: {}", ex.getMessage());
        }
        return devolucionDetalle;
    }
    
    public void handleUnSelectMedicamento() {
        rptInventarioExis = new ReportInventarioExistencias();

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
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                detalleReabastoEnv.setIdMedicamento(ci.getClave());
                detalleReabastoEnv.setLote(ci.getLote());
                detalleReabastoEnv.setFechaCaducidad(ci.getFecha());
                if(ci.getCantidad() != null) {
                    detalleReabastoEnv.setCantidadCaja(ci.getCantidad());
                } else {
                    Medicamento medicamento = medicamentoService.obtenerMedicaByClave(ci.getClave());
                    if(medicamento.getFactorTransformacion() != null) {
                        detalleReabastoEnv.setCantidadCaja(medicamento.getFactorTransformacion());
                    }
                }
            } else {
                ClaveProveedorBarras clave = CodigoBarras.buscaClaveSKU(codigo);
                if (clave != null) {
                    detalleReabastoEnv.setIdMedicamento(clave.getClaveInstitucional());
                    detalleReabastoEnv.setLote(clave.getClaveProveedor());
                    detalleReabastoEnv.setFechaCaducidad(Mensaje.generaCaducidadSKU(clave.getCodigoBarras()));
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordeningreso.err.valor"), null);
                }
            }
            return detalleReabastoEnv;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return detalleReabastoEnv;
    }

    
    public void validarUsuarioAdministrador() {
        try {
            this.administrador = Comunes.isAdministrador();
            if (!this.administrador) {
                this.idEstructura = usuarioSession.getIdEstructura();
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", ex.getMessage());
        }
    }
    
    private String eliminarLotePorCodigo() {
        try {
            ReabastoEnviadoExtended medicamentCodigo = tratarCodigoDeBarras(this.codigoBarras);
            for (DevolucionDetalleExtended devolucion : listDevolucionDetalle) {
                if (devolucion.getClaveInstitucional().equals(medicamentCodigo.getIdMedicamento())
                        && devolucion.getIdMotivoDevolucion().equals(this.idTipoMotivo)) {
                    int resultado = 0;
                    if (devolucion.getIdTipoAlmacen() == Constantes.ALMACEN_FARMACIA
                            || devolucion.getIdTipoAlmacen() == Constantes.ALMACEN) {
                        resultado = devolucion.getCantidad() - (xCantidad * devolucion.getFactorTransformacion());
                    } else {
                        resultado = devolucion.getCantidad() - xCantidad;
                    }
                    if (resultado < 0) {
                        return RESOURCES.getString("devolucion.error.eliminarInsumo");
                    } else {
                        devolucion.setCantidad(resultado);
                        this.codigoBarras = "";
                        this.xCantidad = 0;
                        if (resultado == 0) {
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
    
    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }

    public List<DevolucionExtended> getListDevolucion() {
        return listDevolucion;
    }

    public List<DevolucionDetalleExtended> getListDevolucionDetalle() {
        return listDevolucionDetalle;
    }

    public void setUsuarioSession(Usuario usuarioSession) {
        this.usuarioSession = usuarioSession;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }
    
    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Usuario getUsuarioSession() {
        return usuarioSession;
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
    
    
    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public List<TipoMotivo> getListTipoMotivos() {
        return listTipoMotivos;
    }

  
    public int getxCantidad() {
        return xCantidad;
    }

    public void setxCantidad(int xCantidad) {
        this.xCantidad = xCantidad;
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
    
    
    public boolean isStatus() {
        return status;
    }
    
    public boolean isCrearDevolucion() {
        return crearDevolucion;
    }

    public void setCrearDevolucion(boolean crearDevolucion) {
        this.crearDevolucion = crearDevolucion;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    public void setDevolucionCreada(boolean devolucionCreada) {
        this.devolucionCreada = devolucionCreada;
    }    
    
    public boolean isDevolucionCreada() {
        return devolucionCreada;
    }

    public DevolucionDetalleExtended getDevolucionDetalleSelect() {
        return devolucionDetalleSelect;
    }

    public void setDevolucionDetalleSelect(DevolucionDetalleExtended devolucionDetalleSelect) {
        this.devolucionDetalleSelect = devolucionDetalleSelect;
    }

    public DevolucionExtended getDevolucionSelected() {
        return devolucionSelected;
    }

    public void setDevolucionSelected(DevolucionExtended devolucionSelected) {
        this.devolucionSelected = devolucionSelected;
    }       

    public List<ReportInventarioExistencias> getRptInventarioExisList() {
        return rptInventarioExisList;
    }

    public void setRptInventarioExisList(List<ReportInventarioExistencias> rptInventarioExisList) {
        this.rptInventarioExisList = rptInventarioExisList;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
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
        
}
