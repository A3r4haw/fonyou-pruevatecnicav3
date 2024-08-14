package mx.mc.magedbean;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import mx.mc.lazy.RecepcionReabastoLazy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.DetalleReabastoInsumo;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Fabricante;
import mx.mc.model.FabricanteInsumo;
import mx.mc.model.InventarioExtended;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Proveedor;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoExtended;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Usuario;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.FabricanteInsumoService;
import mx.mc.service.FabricanteService;
import mx.mc.service.InventarioService;
import mx.mc.service.ProveedorService;
import mx.mc.service.ReabastoEnviadoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoService;
import mx.mc.service.ReportesService;
import mx.mc.service.UsuarioService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author gcruz
 *
 */
@Controller
@Scope(value = "view")
public class OrdenRecepcionMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenRecepcionMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private Usuario currentUser;

    private String idEstructura;
    private String busquedaSolicitud;
    private int numeroRegistros;
    private int idAlmacen;

    private List<Estructura> listaEstructuras;
    private List<Estructura> listaAuxiliar;
    private List<ClaveProveedorBarras_Extend> skuSapList;
    private ClaveProveedorBarras_Extend skuSap;
    private RecepcionReabastoLazy recepcionReabastoLazy;
    private boolean activaAutoCompleteInsumos;
    private boolean recibirSinPistoleo;
    private PermisoUsuario permiso;
    @Autowired
    private transient ReabastoService reabastoService;
    private ReabastoExtended reabastoSelect;
    private List<ReabastoExtended> listOrdenesRecibir;

    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;
    private List<ReabastoInsumoExtended> listaInsumoRecibir;

    @Autowired
    private transient ReabastoEnviadoService reabastoEnviadoService;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;
    
    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;

    private String codigoBarras;
    private int xCantidad;
    private boolean status;
    private boolean administrador;
    private boolean jefeArea;

    private String almacenOrigen;
    private String almacenDestino;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructura;

    @Autowired
    private transient ProveedorService proveedorService;

    @Autowired
    private transient InventarioService inventarioService;
    private List<InventarioExtended> listInventarioInactivo;

    @Autowired
    private transient ReportesService reportesService;
    
    @Autowired
    private transient UsuarioService usuarioService;

    private StreamedContent archivo;
    private boolean eliminarCodigo;
    
    @Autowired
    private transient FabricanteService fabricanteService;

    @Autowired
    private transient FabricanteInsumoService fabricanteInsumoService;
    

    @PostConstruct
    public void init() {
        initialize();
        buscarReabasto();
    }

    private void initialize() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
        recibirSinPistoleo = sesion.isRecibeSinPistoleo();
        currentUser = Comunes.obtenerUsuarioSesion();
        numeroRegistros = 0;
        skuSap = new ClaveProveedorBarras_Extend();
        listOrdenesRecibir = new ArrayList<>();
        listaInsumoRecibir = new ArrayList<>();
        listaEstructuras = new ArrayList<>();
        this.reabastoSelect = new ReabastoExtended();
        estructura = new Estructura();
        this.codigoBarras = "";
        this.xCantidad = 1;
        this.administrador = false;
        this.jefeArea = false;
        this.almacenOrigen = "";
        this.almacenDestino = "";
        busquedaSolicitud = "";
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ORDENRECEPCION.getSufijo());
        validarUsuarioAdministrador();
        alimentarComboAlmacen();
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
    

    public void obtenerEstructura() {
        try {
            estructura = estructuraService.obtenerEstructura(this.idEstructura);
            this.almacenDestino = estructura.getNombre();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de buscar la estructura: {}", ex.getMessage());
        }
    }

    public void obtenerOrdenesRecibir() {
        try {
            obtenerEstructura();
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.ENTRANSITO.getValue());
            Reabasto reabasto = new Reabasto();
            reabasto.setIdEstructura(this.idEstructura);
            listOrdenesRecibir = reabastoService.obtenerReabastoExtends(reabasto, listEstatusReabasto, estructura.getIdTipoAlmacen());
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de bustar la lista: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.lista"), null);
        }
    }

    /**
     * Metodo utilizado para poblar el combo almacen
     */
    public void alimentarComboAlmacen() {
        try {
            boolean noEncontroRegistro = false;
            Estructura est = new Estructura();
            est.setActiva(Constantes.ESTATUS_ACTIVO);

            if (!this.administrador) {
                if(this.jefeArea) {
                    listaEstructuras = estructuraService.obtenerAlmacenesQueSurtenServicio(currentUser.getIdEstructura());
                    if(!listaEstructuras.isEmpty()) {
                        this.idAlmacen = listaEstructuras.get(0).getIdTipoAlmacen();
                        this.idEstructura = listaEstructuras.get(0).getIdEstructura();
                    } else {
                        jefeArea = false;
                        noEncontroRegistro = true;
                    }
                    
                } else {
                    noEncontroRegistro = true;
                }
                if(noEncontroRegistro) {
                    est.setIdEstructura(currentUser.getIdEstructura());
                    this.listaEstructuras = this.estructuraService.obtenerLista(est);
                    idEstructura = listaEstructuras.get(0).getIdEstructura();
                    this.idAlmacen = listaEstructuras.get(0).getIdTipoAlmacen();
                }                

            } else {
                est.setIdTipoAreaEstructura(Constantes.TIPO_AREA_ALMACEN);
                this.listaAuxiliar = this.estructuraService.obtenerLista(est);
                this.idAlmacen = listaAuxiliar.get(0).getIdTipoAlmacen();
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

    public void validarUsuarioAdministrador() {
        try {
            this.administrador = Comunes.isAdministrador();
            this.jefeArea = Comunes.isJefeArea();
            if (!this.administrador && !this.jefeArea) {
                this.idEstructura = currentUser.getIdEstructura();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }

    public void limpiarCampos() {
        this.xCantidad = 1;
        this.codigoBarras = "";
        this.eliminarCodigo = false;
        if (this.skuSap != null) {
            this.skuSap.setNombreLargo("");
            this.skuSap.setNombreCorto("");
        }
    }

    public void buscarReabasto() {
        LOGGER.trace("Buscando conincidencias de: {}", busquedaSolicitud);
        try {
            if (busquedaSolicitud != null
                    && busquedaSolicitud.trim().isEmpty()) {
                busquedaSolicitud = null;
            }
            if (idEstructura != null) {
                estructura = estructuraService.obtenerEstructura(idEstructura);
            } else {
                estructura = listaEstructuras.get(0);
                idEstructura = listaEstructuras.get(0).getIdEstructura();
            }

            recepcionReabastoLazy = new RecepcionReabastoLazy(reabastoService, busquedaSolicitud, estructura, idAlmacen);

            LOGGER.debug("Resultados: {}", recepcionReabastoLazy.getTotalReg());
            busquedaSolicitud = "";

        } catch (Exception ex) {
            LOGGER.error("Error al buscar reabastos: {}", ex.getMessage());
        }

    }

    public void buscarSolicitud() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
                return;
            }
            Pattern pat = Pattern.compile(Constantes.PATRON_CAD_BUS);
            Matcher mat = pat.matcher(this.busquedaSolicitud);
            if (mat.matches()) {
                for (ReabastoExtended reabasto : listOrdenesRecibir) {
                    if (reabasto.getFolio().equalsIgnoreCase(this.busquedaSolicitud)) {
                        this.reabastoSelect = reabasto;
                        break;
                    }
                }
                List<Integer> listEstatusReabasto = new ArrayList<>();
                listEstatusReabasto.add(EstatusReabasto_Enum.ENTRANSITO.getValue());
                listOrdenesRecibir = reabastoService.obtenerRegistrosPorCriterioDeBusqueda(this.getBusquedaSolicitud(), Constantes.REGISTROS_PARA_MOSTRAR,
                        listEstatusReabasto, null, this.idEstructura, estructura.getIdTipoAlmacen());
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de bustar el folio: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.folio"), null);
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.reabastoSelect = (ReabastoExtended) event.getObject();
        obtenerDetalleInsumos();
    }

    public void obtenerDetalleInsumos(String idReabasto) {
        for (ReabastoExtended reabasto : listOrdenesRecibir) {
            if (reabasto.getIdReabasto().equals(idReabasto)) {
                this.reabastoSelect = reabasto;
                break;
            }
        }
        obtenerDetalleInsumos();
    }

    public void obtenerDetalleInsumos() {
        try {
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.ENTRANSITO.getValue());
            listEstatusReabasto.add(EstatusReabasto_Enum.SURTIDAPARCIAL.getValue());
            List<String> listIdInsumos = new ArrayList<>();
            this.almacenDestino = reabastoSelect.getNombreEstructura();
            if(this.reabastoSelect.getIdProveedor() != null) {
                if (this.estructura.getIdTipoAlmacen() != Constantes.ALMACEN_FARMACIA) {
                    Estructura estructuraOrigen = estructuraService.obtenerEstructura(this.reabastoSelect.getIdProveedor());
                    this.almacenOrigen = estructuraOrigen.getNombre();
                } else {
                    Proveedor unProveedor = proveedorService.obtenerProvedor(this.reabastoSelect.getIdProveedor());
                    this.almacenOrigen = unProveedor.getNombreProveedor();
                }
            }            
            this.listaInsumoRecibir = reabastoInsumoService.obtenerReabastoInsumoExtends(this.reabastoSelect.getIdReabasto(), listEstatusReabasto);
            for (ReabastoInsumoExtended reabastoInsumo : listaInsumoRecibir) {
                List<ReabastoEnviadoExtended> listaEnviado = reabastoEnviadoService.obtenerListaReabastoEnviado(reabastoInsumo.getIdReabastoInsumo(),
                        listEstatusReabasto);
                //Se llena el campo cantidadRecibida automaticamente cuando esta habilitado el campo de recibir sin pistoleo
                if (recibirSinPistoleo) {
                    reabastoInsumo.setCantidadRecibida(reabastoInsumo.getCantidadSurtida());
                    for (ReabastoEnviado reaEnviado : listaEnviado) {
                        reaEnviado.setCantidadRecibida(reaEnviado.getCantidadEnviado());
                    }
                }

                reabastoInsumo.setListaDetalleReabIns(listaEnviado);
                listIdInsumos.add(reabastoInsumo.getIdInsumo());
            }
            
            listInventarioInactivo = inventarioService.obtenerListaInactivosByIdInsumos(listIdInsumos);

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de obtener los insumos: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.lista"), null);
        }
    }

    public List<ClaveProveedorBarras_Extend> autocompleteInsumo(String cadena) {
        this.skuSapList = new ArrayList<>();
        try {
            this.skuSapList = claveProveedorBarrasService.obtenerListaByNameClaveSkuQr(reabastoSelect.getFolio(), cadena, activaAutoCompleteInsumos);
            if (skuSapList.size() == 1) {
                String componentId = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getClientId();
                String panel = componentId.replace(":", "\\\\:") + "_panel";
                PrimeFaces.current().executeScript("$('#" + panel + " .ui-autocomplete-item').trigger('click');");
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return skuSapList;
    }

    public void handleSelectMedicamento(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtPrescripcionExtMB.handleSelect()");
        this.skuSap = (ClaveProveedorBarras_Extend) e.getObject();
        String idReabastoEnviado = skuSap.getIdInventario();
        for (ClaveProveedorBarras_Extend item : this.skuSapList) {
            if (item.getIdReabastoEnviado().equalsIgnoreCase(idReabastoEnviado)) {
                this.skuSap = item;
                if (skuSap.getFechaCaducidad() == null && skuSap.getLote() == null) {
                    limpiarCampos();
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordenrecepcion.err.agregarInsumo"), null);
                } else {
                    codigoBarras = CodigoBarras.generaCodigoDeBarras(skuSap.getClaveInstitucional(), skuSap.getLote(), skuSap.getFechaCaducidad(), null);

                    recibirInsumosPorCodigo();
                    break;
                }
            }
        }
    }

    public void handleUnSelectMedicamento() {
        skuSap = new ClaveProveedorBarras_Extend();
    }

    public void recibirInsumosPorCodigo() {
        try {
            if (this.codigoBarras.isEmpty()) {
                return;
            }
            DetalleReabastoInsumo medicamentoDetalle = tratarCodigoDeBarras(this.codigoBarras, this.reabastoSelect.getIdReabasto());
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(medicamentoDetalle);
            if (medicamento == null) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.medNoEncontrado"), null);
                limpiarCampos();
                return;
            }

            if (this.eliminarCodigo) {
                String res = eliminarLotePorCodigo();
                if (res == "") {
                    Mensaje.showMessage("Info", RESOURCES.getString("surtimiento.ok.eliminar"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, res, null);
                }
                limpiarCampos();
                return;
            }

            int cantidadMedicamento = 1;
            int cantidadRecibida = 0;
            if (this.estructura.getIdTipoAlmacen() == Constantes.SUBALMACEN) {
                //int cantCaja = 1;
                //cantidadMedicamento = 1;
                cantidadRecibida = medicamento.getCantidadRecibida() + (cantidadMedicamento * xCantidad);
            } else {
                cantidadMedicamento = medicamentoDetalle.getCantidadCaja();
                cantidadRecibida = medicamento.getCantidadRecibida() + (cantidadMedicamento * xCantidad);
            }
            if (cantidadRecibida > medicamento.getCantidadSurtida()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.cantidadMayor"), null);
                limpiarCampos();
                return;
            }
            boolean medEncontrado = true;
            for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                if (item.getActivo() != 1) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.bloqueado"), null);
                    limpiarCampos();
                    return;
                }
                if (FechaUtil.isFechaMayorIgualQue(new Date(), medicamentoDetalle.getFecha())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("oingre.err.caducado"), null);
                    limpiarCampos();
                    return;
                }
                if (listInventarioInactivo != null && !listInventarioInactivo.isEmpty()) {
                    for (InventarioExtended inventario : listInventarioInactivo) {
                        if (inventario.getClaveInstitucional().equals(medicamentoDetalle.getIdMedicamento())
                                && inventario.getLote().equalsIgnoreCase(medicamentoDetalle.getLote())) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.lote.bloqueado"), null);
                            limpiarCampos();
                            return;
                        }
                    }
                }
                String lote = obtenerLoteComparar(item);
                if (lote.equalsIgnoreCase(medicamentoDetalle.getLote())) {
                    Integer cantCalculada = item.getCantidadRecibida() + (cantidadMedicamento * xCantidad);;
                    if (cantCalculada > item.getCantidadEnviado()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.loteMayor"), null);
                        limpiarCampos();
                        return;
                    }
                    item.setCantidadRecibida(cantCalculada);
                    medicamento.setCantidadRecibida(cantidadRecibida);
                    medicamento.setCantidadIngresada(Constantes.ID_VACIO);
                    limpiarCampos();
                    return;
                }
            }
            if (medEncontrado) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.loteNoEncontrado"), null);
                limpiarCampos();
                return;
            }
            medicamentoDetalle.setIdReabastoInsumo(medicamento.getIdReabastoInsumo());
            medicamentoDetalle.setIdMedicamento(medicamento.getIdInsumo());
            medicamento.setCantidadRecibida(cantidadRecibida);
            medicamento.setCantidadIngresada(Constantes.ID_VACIO);
            limpiarCampos();
        } catch (Exception e) {
            LOGGER.error("Error al buscar por codigo de barras :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.codigo"), null);
        }
    }

    private String eliminarLotePorCodigo() {
        LOGGER.error("mx.mc.magedbean.OrdenRecepcionMB.eliminarLotePorCodigo()");
        String res = "";
        try {
            DetalleReabastoInsumo codiPorEliminar = tratarCodigoDeBarras(this.codigoBarras, this.reabastoSelect.getIdReabasto());

            if (this.estructura.getIdTipoAlmacen() == Constantes.SUBALMACEN) {
                codiPorEliminar.setCantidadCaja(1);
            }
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(codiPorEliminar);
            if (medicamento == null) {
                //Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.medNoEncontrado"), null);
                res = RESOURCES.getString("orepcio.err.medNoEncontrado");
                limpiarCampos();

            } else {
                for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                    String lote = obtenerLoteComparar(item);
                    if (codiPorEliminar.getLote().equals(lote) && codiPorEliminar.getFecha().equals(item.getFechaCaducidad())) {                        
                        if(xCantidad * codiPorEliminar.getCantidadCaja() > item.getCantidadRecibida()) {                            
                            res = RESOURCES.getString("surtimiento.error.insumoDetalleMayor");
                            break;
                        } else {
                            item.setCantidadRecibida(item.getCantidadRecibida() - xCantidad * codiPorEliminar.getCantidadCaja());
                        }
                        
                        /*if (item.getCantidadRecibida() < 0) {
                            item.setCantidadRecibida(0);
                        }*/
                        if(xCantidad * codiPorEliminar.getCantidadCaja() > medicamento.getCantidadRecibida()) {
                            res = RESOURCES.getString("surtimiento.error.insumoCabeceraMayor");
                            break;
                        } else {
                            medicamento.setCantidadRecibida(medicamento.getCantidadRecibida() - xCantidad * codiPorEliminar.getCantidadCaja());
                        }
                        
                        /*if (medicamento.getCantidadRecibida() < 0) {
                            medicamento.setCantidadRecibida(0);
                        }*/
                        //res = true;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("surtimiento.error.operacion"), null);
        }
        return res;
    }

    public String obtenerLoteComparar(ReabastoEnviadoExtended item) {
        if (item.getLote() != null) {
            return item.getLote();
        } else {
            if (item.getLoteEnv() != null) {
                return item.getLoteEnv();
            } else {
                return null;
            }
        }
    }

    /**
     * Metodo utilizado para convertir el codigo de barras en un objeto de tipo
     * DetalleReabastoInsumo
     *
     * @param codigo String
     * @return DetalleReabastoInsumo
     */
    private DetalleReabastoInsumo tratarCodigoDeBarras(String codigo, String idReabasto) {
        DetalleReabastoInsumo detalleReabasto = new DetalleReabastoInsumo();
        try {
            CodigoInsumo codInsumo = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (codInsumo != null) {
                detalleReabasto.setIdMedicamento(codInsumo.getClave());
                detalleReabasto.setLote(codInsumo.getLote());
                detalleReabasto.setFecha(codInsumo.getFecha());
                if (codInsumo.getCantidad() != null) {
                    detalleReabasto.setCantidadCaja(codInsumo.getCantidad());
                } else {
                    Integer cantidadXcaja = 1;
                    ReabastoEnviadoExtended parametros = new ReabastoEnviadoExtended();
                    parametros.setIdInsumo(detalleReabasto.getIdMedicamento());
                    parametros.setLoteEnv(detalleReabasto.getLote());
                    parametros.setIdEstructura(this.estructura.getIdEstructuraPadre());
                    parametros.setIdReabasto(idReabasto);
                    ReabastoEnviadoExtended reabastoEnviadoExtended = reabastoEnviadoService.
                            obtenerInventarioPorClveInstEstructuraYLote(parametros);
                    if (reabastoEnviadoExtended != null
                            && reabastoEnviadoExtended.getCantidadXCaja() != null
                            && reabastoEnviadoExtended.getCantidadXCaja() > 0) {
                        cantidadXcaja = reabastoEnviadoExtended.getCantidadXCaja();
                    }
                    detalleReabasto.setCantidadCaja(cantidadXcaja);
                }
            } else {
                ClaveProveedorBarras_Extend claveProveedorBarras = claveProveedorBarrasService.obtenerClave(codigo);
                if (claveProveedorBarras != null) {
                    detalleReabasto.setIdMedicamento(claveProveedorBarras.getClaveInstitucional());
                    detalleReabasto.setCantidadCaja(claveProveedorBarras.getCantidadXCaja());
                    detalleReabasto.setLote(claveProveedorBarras.getClaveProveedor());
                    detalleReabasto.setFecha(Mensaje.generaCaducidadSKU(claveProveedorBarras.getCodigoBarras()));
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordenrecpcion.err.invalido"), null);
                }
            }

            return detalleReabasto;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return detalleReabasto;
    }

    public ReabastoInsumoExtended obtenerInsumoPorClaveMedicamento(DetalleReabastoInsumo detalle) {

        ReabastoInsumoExtended registro = null;
        for (ReabastoInsumoExtended item : this.listaInsumoRecibir) {
            if (item.getClaveInstitucional().equalsIgnoreCase(detalle.getIdMedicamento())) {
                return item;
            }
        }
        return registro;
    }

    public void guardarRecepcion() {
        try {
            for (ReabastoInsumoExtended reabastoInsumo : listaInsumoRecibir) {
                reabastoInsumo.setUpdateIdUsuario(currentUser.getIdUsuario());
                reabastoInsumo.setUpdateFecha(new Date());
            }
            if (reabastoInsumoService.actualizarListaReabastoInsumo(listaInsumoRecibir)) {
                Mensaje.showMessage("Info", RESOURCES.getString("ok.guardar"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.guardar"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al guardar la recepcion: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.guardar"), null);
        }
    }

    public void recibirReabastoInsumo() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.accion"), null);
                status = Constantes.INACTIVO;
                return;
            }
            for (ReabastoInsumoExtended item : this.listaInsumoRecibir) {
                for (ReabastoEnviadoExtended reabastoEnviadoExtended : item.getListaDetalleReabIns()) {
                    if (reabastoEnviadoExtended.getCantidadEnviado() > 0) {
                        boolean bloqueadoLote = false;
                        String loteComparar = obtenerLoteComparar(reabastoEnviadoExtended);
                        if (listInventarioInactivo != null || !listInventarioInactivo.isEmpty()) {
                            for (InventarioExtended inventario : listInventarioInactivo) {
                                if (inventario.getClaveInstitucional().equals(item.getClaveInstitucional())
                                        && inventario.getLote().equalsIgnoreCase(loteComparar)) {
                                    bloqueadoLote = true;
                                }
                            }
                        }
                        Date fechaCad = obtenerFechaCaducidad(reabastoEnviadoExtended);
                        if (reabastoEnviadoExtended.getActivo() != 0
                                && !FechaUtil.isFechaMayorIgualQue(new Date(), fechaCad)
                                && !bloqueadoLote
                                && item.getCantidadRecibida() == 0) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.cantidadCero"), null);
                            status = Constantes.INACTIVO;
                            return;
                        }
                    }
                }
            }
            boolean parcial = false;
            List<ReabastoEnviado> listReabastoEnviado = new ArrayList<>();
            for (ReabastoInsumoExtended reabastoInsumo : listaInsumoRecibir) {
                if (reabastoInsumo.getCantidadRecibida() < reabastoInsumo.getCantidadSolicitada()) {
                    reabastoInsumo.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_RECIBIDA_PARCIAL);
                    parcial = true;
                } else {
                    reabastoInsumo.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_RECIBIDA);
                }
                reabastoInsumo.setUpdateIdUsuario(currentUser.getIdUsuario());
                reabastoInsumo.setUpdateFecha(new Date());
                reabastoInsumo.setCantidadIngresada(0);
                listReabastoEnviado(reabastoInsumo, listReabastoEnviado);
            }
            cambiarEstadoReabasto(parcial);
            Usuario usuarioRecibe = usuarioService.obtenerUsuarioByIdUsuario(currentUser.getIdUsuario());
            reabastoSelect.setNombreUsrRecibe(usuarioRecibe.getNombre() + " " + usuarioRecibe.getApellidoPaterno() +
                                                usuarioRecibe.getApellidoMaterno());
            if (reabastoInsumoService.actulizaRecibirOrdenReabasto(reabastoSelect, listaInsumoRecibir, listReabastoEnviado)) {
                obtenerOrdenesRecibir();
                status = Constantes.ACTIVO;
                Mensaje.showMessage("Info", RESOURCES.getString("orepcio.info.recibiOrden"), null);
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.recibiOrden"), null);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al recibir reabasto Insumo: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.recibiOrden"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public Date obtenerFechaCaducidad(ReabastoEnviadoExtended extended) {
        if (extended.getFechaCaducidad() != null) {
            return extended.getFechaCaducidad();
        } else {
            if (extended.getFechaCad() != null) {
                return extended.getFechaCad();
            } else {
                return null;
            }
        }
    }

    public void cambiarEstadoReabasto(boolean parcial) {
        if (parcial) {
            this.reabastoSelect.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_RECIBIDA_PARCIAL);

        } else {
            this.reabastoSelect.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_RECIBIDA);
        }
        reabastoSelect.setUpdateIdUsuario(currentUser.getIdUsuario());
        reabastoSelect.setUpdateFecha(new Date());
    }

    public List<ReabastoEnviado> listReabastoEnviado(ReabastoInsumoExtended reabastoInsumo, List<ReabastoEnviado> listReabastoEnviado) {
        for (ReabastoEnviado reabastoEnviado : reabastoInsumo.getListaDetalleReabIns()) {
            reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_RECIBIDA);
            reabastoEnviado.setUpdateIdUsuario(currentUser.getIdUsuario());
            reabastoEnviado.setUpdateFecha(new Date());
            listReabastoEnviado.add(reabastoEnviado);
        }
        return listReabastoEnviado;
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean estatus = Constantes.INACTIVO;
        try {
            reabastoSelect.setNombreEstructura(this.almacenDestino);
            reabastoSelect.setNombreProveedor(this.almacenOrigen);
            Estructura est = estructuraService.obtenerEstructura(reabastoSelect.getIdEstructura());
            
            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            
            byte[] buffer = reportesService.imprimirOrdenRecibir(reabastoSelect, entidad);
            if (buffer != null) {
                estatus = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("recepcionOrden_%s.pdf", reabastoSelect.getFolio()));                
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public String getBusquedaSolicitud() {
        return busquedaSolicitud;
    }

    public void setBusquedaSolicitud(String busquedaSolicitud) {
        this.busquedaSolicitud = busquedaSolicitud;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public ReabastoService getReabastoService() {
        return reabastoService;
    }

    public void setReabastoService(ReabastoService reabastoService) {
        this.reabastoService = reabastoService;
    }

    public Reabasto getReabastoSelect() {
        return reabastoSelect;
    }

    public void setReabastoSelect(ReabastoExtended reabastoSelect) {
        this.reabastoSelect = reabastoSelect;
    }

    public List<ReabastoExtended> getListOrdenesRecibir() {
        return listOrdenesRecibir;
    }

    public List<ReabastoInsumoExtended> getListaInsumoRecibir() {
        return listaInsumoRecibir;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public StreamedContent getArchivo() {
        return archivo;
    }

    public void setArchivo(StreamedContent archivo) {
        this.archivo = archivo;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isJefeArea() {
        return jefeArea;
    }

    public void setJefeArea(boolean jefeArea) {
        this.jefeArea = jefeArea;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
    }

    public RecepcionReabastoLazy getRecepcionReabastoLazy() {
        return recepcionReabastoLazy;
    }

    public void setRecepcionReabastoLazy(RecepcionReabastoLazy recepcionReabastoLazy) {
        this.recepcionReabastoLazy = recepcionReabastoLazy;
    }

    public Estructura getEstructura() {
        return estructura;
    }

    public void setEstructura(Estructura estructura) {
        this.estructura = estructura;
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

    public boolean isActivaAutoCompleteInsumos() {
        return activaAutoCompleteInsumos;
    }

    public void setActivaAutoCompleteInsumos(boolean activaAutoCompleteInsumos) {
        this.activaAutoCompleteInsumos = activaAutoCompleteInsumos;
    }

    public boolean isRecibirSinPistoleo() {
        return recibirSinPistoleo;
    }

    public void setRecibirSinPistoleo(boolean recibirSinPistoleo) {
        this.recibirSinPistoleo = recibirSinPistoleo;
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

    
}
