package mx.mc.magedbean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import mx.mc.lazy.IngresoReabastoLazy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.DetalleReabastoInsumo;
import mx.mc.model.DevolucionDetalleExtended;
import mx.mc.model.DevolucionExtended;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.Fabricante;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
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
import mx.mc.service.DevolucionService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
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

/**
 * @author gcruz
 */
@Controller
@Scope(value = "view")
public class OrdenIngresoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenIngresoMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private Usuario currentUser;

    private String idEstructura;
    private String busquedaSolicitud;
    private int numeroRegistros;
    private int idAlmacen;

    private List<Estructura> listaEstructuras;
    private List<Estructura> listaAuxiliar;

    @Autowired
    private transient ReabastoService reabastoService;
    private ReabastoExtended reabastoSelect;
    private List<ReabastoExtended> listOrdenesIngresar;

    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;
    private List<ReabastoInsumoExtended> listaInsumoIngresar;

    @Autowired
    private transient ReabastoEnviadoService reabastoEnviadoService;

    @Autowired
    private transient InventarioService inventarioService;

    @Autowired
    private transient FabricanteService fabricanteService;
    
    
    private List<InventarioExtended> listInventarioInactivo;
    private List<ClaveProveedorBarras_Extend> skuSapList;
    private ClaveProveedorBarras_Extend skuSap;

    private String codigoBarras;
    private int porCantidad;
    private boolean status;
    private boolean administrador;
    private boolean jefeArea;
    private String archivo;
    private boolean eliminarCodigo;
    private boolean funcionesOrdenReabasto;
    private boolean activaAutoCompleteInsumos;
    private boolean ingresoSinPistoleo;
    private String errAccion;
    private PermisoUsuario permiso;
    private Date fechaActual;
    
    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructura;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient DevolucionService devolucionService;

    @Autowired
    private transient ReportesService reportesService;

    @Autowired
    private transient EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired transient ProveedorService proveedorService;

    private IngresoReabastoLazy ingresoReabastoLazy;
    
    private List<Fabricante> listaFabricante;
    
    private List<Proveedor> listaProveedor;

    @PostConstruct
    public void init() {
        errAccion = "err.accion";
        initialize();

    }

    private void initialize() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = Comunes.obtenerUsuarioSesion();
        activaAutoCompleteInsumos = sesion.isActivaAutocompleteQrClave();
        ingresoSinPistoleo = sesion.isIngresoSinPistoleo();
        numeroRegistros = 0;
        this.fechaActual = new java.util.Date();
        
        listOrdenesIngresar = new ArrayList<>();
        listaInsumoIngresar = new ArrayList<>();
        listaEstructuras = new ArrayList<>();
        this.reabastoSelect = new ReabastoExtended();
        funcionesOrdenReabasto = sesion.isFuncionesOrdenReabasto();
        estructura = new Estructura();
        this.codigoBarras = "";
        this.porCantidad = 1;
        skuSap = new ClaveProveedorBarras_Extend();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.ORDENINGRESO.getSufijo());
        validarUsuarioAdministrador();
        alimentarComboAlmacen();
        buscarOrdenesIngresar();
        
        this.listaProveedor = obtenerListaProveedor();
        this.listaFabricante = obtenerListaFabricante();
    }

    public void obtenerEstructura() {
        try {
            estructura = estructuraService.obtenerEstructura(this.idEstructura);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de buscar la estructura: {}", ex.getMessage());
        }
    }

    public void obtenerOrdenesIngresar() {
        try {
            obtenerEstructura();
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());
            Reabasto reabasto = new Reabasto();
            reabasto.setIdEstructura(this.idEstructura);
            listOrdenesIngresar = reabastoService.obtenerReabastoExtends(reabasto, listEstatusReabasto, estructura.getIdTipoAlmacen());

            numeroRegistros = listOrdenesIngresar.size();
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
                    
                }  else {
                    noEncontroRegistro = true;
                }
                if(noEncontroRegistro) {
                    est.setIdEstructura(currentUser.getIdEstructura());
                    this.listaEstructuras = this.estructuraService.obtenerLista(est);
                    this.idAlmacen = listaEstructuras.get(0).getIdTipoAlmacen();
                    this.idEstructura = listaEstructuras.get(0).getIdEstructura();
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
    
    private List<Proveedor> obtenerListaProveedor(){
        List<Proveedor> listaProveedor = new ArrayList<>();
        try {
            Proveedor p = new Proveedor();
            listaProveedor = proveedorService.obtenerLista(p);
        } catch (Exception ex){
            LOGGER.error("Error al obtener lista de proveedores; {}", ex.getMessage());
        }
        return listaProveedor;
    }
    
    private List<Fabricante> obtenerListaFabricante(){
        List<Fabricante> listaFabricante = new ArrayList<>();
        try {
            Fabricante f = new Fabricante();
            f.setIdEstatus(Constantes.ACTIVOS);
            listaFabricante = fabricanteService.obtenerLista(f);
        } catch (Exception ex){
            LOGGER.error("Error al obtener lista de Fabricantes; {}", ex.getMessage());
        }
        return listaFabricante;
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
        this.porCantidad = 1;
        this.codigoBarras = "";
        this.eliminarCodigo = false;
        this.skuSap.setNombreLargo("");
        this.skuSap.setNombreCorto("");
    }

    public void buscarOrdenesIngresar() {
        LOGGER.trace("Buscando conincidencias de: {}", busquedaSolicitud);
        try {
            if (busquedaSolicitud != null && busquedaSolicitud.trim().isEmpty()) {
                busquedaSolicitud = null;
            }
            if (idEstructura != null) {
                estructura = estructuraService.obtenerEstructura(idEstructura);
            } else {
                estructura = listaEstructuras.get(0);
                idEstructura = listaEstructuras.get(0).getIdEstructura();
            }
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());
            ingresoReabastoLazy = new IngresoReabastoLazy(reabastoService, busquedaSolicitud, estructura, idAlmacen, listEstatusReabasto);

            LOGGER.debug("Resultados: {}", ingresoReabastoLazy.getTotalReg());
            busquedaSolicitud = "";

        } catch (Exception ex) {
            LOGGER.error("Error al buscar reabastos: {}", ex.getMessage());
        }
    }

    public void buscarSolicitudFolio() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
                return;
            }
            if (!this.busquedaSolicitud.equals("")) {
                List<Integer> listEstatusReabasto = new ArrayList<>();
                listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
                listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());

                listOrdenesIngresar = reabastoService.obtenerRegistrosPorCriterioDeBusquedas(this.getBusquedaSolicitud(), Constantes.REGISTROS_PARA_MOSTRAR,
                        listEstatusReabasto, null, this.idEstructura, estructura.getIdTipoAlmacen());
                this.busquedaSolicitud = "";
                if (listOrdenesIngresar.size() == 1) {
                    numeroRegistros = listOrdenesIngresar.size();

                    obtenerDetalleInsumos(listOrdenesIngresar.get(0).getIdReabasto());
                    PrimeFaces.current().ajax().addCallbackParam("estatusModal", true);
                    this.busquedaSolicitud = "";

                } else {
                    numeroRegistros = listOrdenesIngresar.size();
                }
            } else {
                List<Integer> listEstatusReabasto = new ArrayList<>();
                listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
                listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());
                listOrdenesIngresar = reabastoService.obtenerRegistrosPorCriterioDeBusquedas(this.getBusquedaSolicitud(), Constantes.REGISTROS_PARA_MOSTRAR,
                        listEstatusReabasto, null, this.idEstructura, estructura.getIdTipoAlmacen());
                numeroRegistros = listOrdenesIngresar.size();
            }

        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al momento de bustar el folio: {}", e.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.folio"), null);
        }
    }

    @Deprecated
    public void buscarSolicitud() {
        try {
            if (!this.permiso.isPuedeVer()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
                return;
            }

            Pattern pat = Pattern.compile(Constantes.PATRON_CAD_BUS);
            Matcher mat = pat.matcher(this.busquedaSolicitud);
            if (!funcionesOrdenReabasto) {
                if (mat.matches()) {
                    for (ReabastoExtended reabasto : listOrdenesIngresar) {
                        if (reabasto.getFolio().equalsIgnoreCase(this.busquedaSolicitud)) {
                            this.reabastoSelect = reabasto;
                            break;
                        }
                    }
                    List<Integer> listEstatusReabasto = new ArrayList<>();
                    listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
                    listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());
                    listOrdenesIngresar = reabastoService.obtenerRegistrosPorCriterioDeBusqueda(this.getBusquedaSolicitud(), Constantes.REGISTROS_PARA_MOSTRAR,
                            listEstatusReabasto, null, this.idEstructura, estructura.getIdTipoAlmacen());
                }
            } else {
                if (!this.busquedaSolicitud.equals("")) {
                    for (ReabastoExtended reabasto : listOrdenesIngresar) {
                        if (reabasto.getFolio().equalsIgnoreCase(this.busquedaSolicitud)) {
                            this.reabastoSelect = reabasto;
                            break;
                        }
                    }
                    List<Integer> listEstatusReabasto = new ArrayList<>();
                    listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
                    listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());
                    listOrdenesIngresar = reabastoService.obtenerRegistrosPorCriterioDeBusqueda(this.getBusquedaSolicitud(), Constantes.REGISTROS_PARA_MOSTRAR,
                            listEstatusReabasto, null, this.idEstructura, estructura.getIdTipoAlmacen());
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de bustar el folio: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.folio"), null);
        }
    }

    public void obtenerDetalleInsumos(String idReabasto) {
        for (ReabastoExtended reabasto : listOrdenesIngresar) {
            if (reabasto.getIdReabasto().equals(idReabasto)) {
                this.reabastoSelect = reabasto;
            }
        }
        obtenerDetalleInsumos();
    }

    public void eliminarOrden(String idReabasto) {
        if (!this.permiso.isPuedeEliminar()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
            return;
        }
        Reabasto reabasto = new Reabasto();
        reabasto.setIdReabasto(idReabasto);
        ReabastoInsumo reabastoInsumo = new ReabastoInsumo();
        reabastoInsumo.setIdReabasto(idReabasto);
        boolean resp;
        try {
            List<ReabastoInsumo> listReabastoInsumo = reabastoInsumoService.
                    obtenerLista(reabastoInsumo);
            resp = reabastoService.eliminarOrdenReabasto(reabasto, listReabastoInsumo);
            if (resp) {
                Mensaje.showMessage("Info", RESOURCES.getString("ordeningreso.info.eliminado"), null);
                obtenerOrdenesIngresar();
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordeningreso.err.eliminar"), null);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(OrdenIngresoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerDetalleInsumos() {
        try {
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());
            List<String> listIdInsumos = new ArrayList<>();
            this.listaInsumoIngresar = reabastoInsumoService.obtenerReabastoInsumoExtends(this.reabastoSelect.getIdReabasto(), listEstatusReabasto);
            for (ReabastoInsumoExtended reabastoInsumo : listaInsumoIngresar) {
                //Se cambió para que no hiciera el calculo mal del ingreso
                if (ingresoSinPistoleo) {
                    reabastoInsumo.setCantidadIngresada(reabastoInsumo.getCantidadRecibida());
                }
                List<ReabastoEnviadoExtended> listaEnviado = reabastoEnviadoService.obtenerListaReabastoEnviado(reabastoInsumo.getIdReabastoInsumo(),
                        listEstatusReabasto);
                List<ReabastoEnviadoExtended> listaEnviadoNueva = new ArrayList<>();
                for (ReabastoEnviadoExtended unReabastoEnviado : listaEnviado) {
                    if (ingresoSinPistoleo) {
                        unReabastoEnviado.setCantidadIngresada(unReabastoEnviado.getCantidadRecibida());
                    }
                    List<Inventario> listInventario = null;
                    if(reabastoSelect.getIdEstructuraPadre() != null) {
                        listInventario = inventarioService.obtenerExistenciasPorIdEstructuraIdInsumo(reabastoSelect.getIdEstructuraPadre(), reabastoInsumo.getIdInsumo());
                        //obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor(reabastoInsumo.getIdInsumo(),
                          //  reabastoSelect.getIdEstructuraPadre(), unReabastoEnviado.getCantidadXCaja(), unReabastoEnviado.getClaveProveedor(), unReabastoEnviado.getLoteEnv());
                    }
                    if (listInventario != null) {
                        if(listInventario.isEmpty()) {
                            listInventario = inventarioService.obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor(reabastoInsumo.getIdInsumo(),
                                currentUser.getIdEstructura(), unReabastoEnviado.getCantidadXCaja(), unReabastoEnviado.getClaveProveedor(), unReabastoEnviado.getLoteEnv());
                        }
                    }
                    
                    if (listInventario != null && !listInventario.isEmpty()) {
                        reabastoInsumo.setCosto(listInventario.get(0).getCosto());
                    } else {
                        Inventario i = new Inventario();
                        i.setIdInventario(Comunes.getUUID());
                        i.setFechaIngreso(new java.util.Date());
                        i.setIdEstructura(unReabastoEnviado.getIdEstructura());
                        i.setIdInsumo(unReabastoEnviado.getIdInsumo());
                        i.setIdPresentacion(reabastoInsumo.getIdPresentacion());
                        i.setLote(unReabastoEnviado.getLoteEnv());
                        i.setCantidadXCaja(unReabastoEnviado.getCantidadCaja());
                        i.setFechaCaducidad(unReabastoEnviado.getFechaCad());
                        i.setCosto(reabastoInsumo.getCosto());
                        Double costoUnidosis = 0d;
                        try{
                            if (reabastoInsumo.getCosto() > 0 && unReabastoEnviado.getCantidadCaja()!= null){
                                costoUnidosis = reabastoInsumo.getCosto()/unReabastoEnviado.getCantidadCaja();
                            }
                        } catch(Exception e){
                            LOGGER.error("Error al obtener costo unidosis {} " + e.getMessage() );
                        }
                        i.setCostoUnidosis(costoUnidosis);
                        i.setIdProveedor(unReabastoEnviado.getIdProveedor());
                        i.setIdDictamenMedico(null);
                        i.setAccesorios(null);
                        i.setCantidadActual(unReabastoEnviado.getCantidadRecibida());
                        i.setExistenciaInicial(0);
                        i.setClaveProveedor(unReabastoEnviado.getClaveProveedor());
                        i.setPresentacionComercial(unReabastoEnviado.getPresentacionComercial());
                        i.setActivo(1);
                        i.setInsertFecha(new java.util.Date());
                        i.setInsertIdUsuario(currentUser.getIdUsuario());
                        i.setUpdateFecha(new java.util.Date());
                        i.setUpdateIdUsuario(currentUser.getIdUsuario());
                        i.setIdTipoOrigen(null);
                        i.setEnviarAVG(0);
                        i.setOsmolaridad(unReabastoEnviado.getOsmolaridad());
                        i.setDensidad(unReabastoEnviado.getDensidad());
                        i.setCalorias(unReabastoEnviado.getCalorias());
                        i.setNoHorasEstabilidad(unReabastoEnviado.getNoHorasEstabilidad());
                        i.setIdFabricante(unReabastoEnviado.getIdFabricante());
                        listInventario.add(i);
                    }
                    listaEnviadoNueva.add(unReabastoEnviado);
                }
                reabastoInsumo.setListaDetalleReabIns(listaEnviadoNueva);
                listIdInsumos.add(reabastoInsumo.getIdInsumo());
            }
            listInventarioInactivo = inventarioService.obtenerListaInactivosByIdInsumos(listIdInsumos);

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de obtener los insumos: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.lista"), null);
        }
    }

    public List<ClaveProveedorBarras_Extend> autocompleteMedicamento(String cadena) {
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
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordeningreso.err.agregarInsumo"), null);
                } else {
                    codigoBarras = CodigoBarras.generaCodigoDeBarras(skuSap.getClaveInstitucional(), skuSap.getLote(), skuSap.getFechaCaducidad(), null);
                    ingresarInsumosPorCodigo();
                    break;
                }
            }
        }
    }

    public void handleUnSelectMedicamento() {
        skuSap = new ClaveProveedorBarras_Extend();

    }

    public void ingresarInsumosPorCodigo() {
        try {
            if (!funcionesOrdenReabasto && this.codigoBarras.isEmpty()) {
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

            //Se efectua cambio para que se multiplique por la cantidad por caja en objeto enviado y ya no se va a consultar
            int cantidadMedicamento = medicamentoDetalle.getCantidadCaja();
            int cantidadIngresada = 0;
            if (medicamento.getCantidadIngresada() != null) {
                cantidadIngresada = medicamento.getCantidadIngresada();
            }
            
            if (estructura.getIdTipoAlmacen() == TipoAlmacen_Enum.SUBALMACEN.getValue()) { 
            //Como es un subalmacen es por unidosis
                cantidadMedicamento = 1;
            } else {
                cantidadMedicamento = medicamento.getFactorTransformacion();
            }
            cantidadIngresada = cantidadIngresada + (cantidadMedicamento * porCantidad);
            
            if (cantidadIngresada > medicamento.getCantidadRecibida()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.cantidadMayor"), null);
                limpiarCampos();
                return;
            }
            boolean medEncontrado = true;
            for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                if (item.getActivo() != 1) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("oingre.err.bloqueado"), null);
                    limpiarCampos();
                    return;
                }
                if (FechaUtil.isFechaMayorIgualQue(new Date(), medicamentoDetalle.getFecha())) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("oingre.err.caducado"), null);
                    limpiarCampos();
                    return;
                }
                if (listInventarioInactivo != null) {
                    for (InventarioExtended inventario : listInventarioInactivo) {
                        if (inventario.getClaveInstitucional().equals(medicamentoDetalle.getIdMedicamento()) && inventario.getLote().equalsIgnoreCase(medicamentoDetalle.getLote())) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.lote.bloqueado"), null);
                            limpiarCampos();
                            return;
                        }
                    }
                }
                String lote = obtenerLoteComparar(item);
                if (lote.equalsIgnoreCase(medicamentoDetalle.getLote())) {
                    Integer cantCalculada = 0;                   
                    cantCalculada = item.getCantidadIngresada() + (porCantidad * cantidadMedicamento);
                    if (cantCalculada > item.getCantidadRecibida()) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("orepcio.err.loteMayor"), null);
                        limpiarCampos();
                        return;
                    }
                    item.setCantidadIngresada(cantCalculada);
                    medicamento.setCantidadIngresada(cantidadIngresada);
                    limpiarCampos();
                    if (funcionesOrdenReabasto) {
                        this.skuSap.setNombreLargo("");
                        this.skuSap.setNombreCorto("");
                        this.skuSapList = new ArrayList<>();
                    }

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
            medicamento.setCantidadIngresada(cantidadIngresada);
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

            if (estructura.getIdTipoAlmacen() == Constantes.SUBALMACEN) {
                int cantCaja = 1;
                codiPorEliminar.setCantidadCaja(cantCaja);
            }
            ReabastoInsumoExtended medicamento = obtenerInsumoPorClaveMedicamento(codiPorEliminar);
            if (medicamento == null) {               
                res = RESOURCES.getString("orepcio.err.medNoEncontrado");                

            } else {
                for (ReabastoEnviadoExtended item : medicamento.getListaDetalleReabIns()) {
                    String lote = obtenerLoteComparar(item);
                    if (codiPorEliminar.getLote().equals(lote) && codiPorEliminar.getFecha().equals(item.getFechaCaducidad())) {
                        if(porCantidad * codiPorEliminar.getCantidadCaja() > item.getCantidadIngresada()) {
                            res = RESOURCES.getString("surtimiento.error.insumoDetalleMayor");
                            break;
                        } else {
                            item.setCantidadIngresada(item.getCantidadIngresada() - (porCantidad * codiPorEliminar.getCantidadCaja()));
                        }
                                               
                        if(porCantidad * codiPorEliminar.getCantidadCaja() > medicamento.getCantidadIngresada()) {
                            res = res = RESOURCES.getString("surtimiento.error.insumoCabeceraMayor");;
                            break;
                        } else {
                            medicamento.setCantidadIngresada(medicamento.getCantidadIngresada() - porCantidad * codiPorEliminar.getCantidadCaja());
                        }
                        
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
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("ordeningreso.err.valor"), null);
                }
            }

            return detalleReabasto;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return detalleReabasto;
    }

    public String obtenerLoteComparar(ReabastoEnviadoExtended item) {
        if (item.getLote() != null) {
            return item.getLote();
        } else if (item.getLoteEnv() != null) {
            return item.getLoteEnv();
        } else {
            return null;
        }
    }

    public ReabastoInsumoExtended obtenerInsumoPorClaveMedicamento(DetalleReabastoInsumo detalle) {

        ReabastoInsumoExtended registro = null;
        for (ReabastoInsumoExtended item : this.listaInsumoIngresar) {
            if (item.getClaveInstitucional().equalsIgnoreCase(detalle.getIdMedicamento())) {
                return item;
            }
        }
        return registro;
    }

    public void ingresarReabastoInsumo() {
        try {
            if (!this.permiso.isPuedeProcesar()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errAccion), null);
                status = Constantes.INACTIVO;
                return;
            }
            
            List<ReabastoEnviado> listReabastoEnviado = new ArrayList<>();
            List<Inventario> listInventarioInsert = new ArrayList<>();
            List<Inventario> listInventarioUpdate = new ArrayList<>();
            List<MovimientoInventario> listMovInventario = new ArrayList<>();
            List<DevolucionDetalleExtended> listDetalleDevolucion = new ArrayList<>();
            String result = "";
            for (ReabastoInsumoExtended reabastoInsumo : listaInsumoIngresar) {
                reabastoInsumo.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_INGRESADA);
                reabastoInsumo.setUpdateIdUsuario(currentUser.getIdUsuario());
                reabastoInsumo.setUpdateFecha(new Date());
                result = llenarListReabastoIngresado(reabastoSelect,reabastoInsumo, listReabastoEnviado, listInventarioInsert,
                        listInventarioUpdate, listMovInventario);
                
                if (!(result.equals(""))) {
                    status = Constantes.INACTIVO;
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, result, null);
                    return;
                }
                
                if (reabastoInsumo.getCantidadRecibida() != 0
                        && !reabastoInsumo.getCantidadRecibida().equals(reabastoInsumo.getCantidadIngresada())) {
                    registrarDevolucion(reabastoInsumo, listDetalleDevolucion);
                }                
            }
            if (!(result.equals(""))) {
                status = Constantes.INACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, result, null);
            } else {
                actualizarEstadoReabasto();
                Usuario usuarioRecibe = usuarioService.obtenerUsuarioByIdUsuario(currentUser.getIdUsuario());
                reabastoSelect.setNombreUsrRecibe(usuarioRecibe.getNombre() + " " + usuarioRecibe.getApellidoPaterno() +
                                                usuarioRecibe.getApellidoMaterno());
                if (reabastoInsumoService.actulizaIngresoOrdenReabasto(reabastoSelect, listaInsumoIngresar,
                        listReabastoEnviado, listInventarioInsert, listInventarioUpdate, listMovInventario)) {
                    obtenerOrdenesIngresar();
                    if (!listDetalleDevolucion.isEmpty()) {
                        insertarDevolucion(listDetalleDevolucion);
                    }
                    status = Constantes.ACTIVO;
                    Mensaje.showMessage("Info", RESOURCES.getString("oingre.info.recibiOrden"), null);
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("oingre.err.recibiOrden"), null);
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al recibir reabasto Insumo: {}", ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("oingre.err.recibiOrden"), null);
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    private void registrarDevolucion(ReabastoInsumoExtended reabastoInsumo,
            List<DevolucionDetalleExtended> listDetalleDevolucion) {
        DevolucionDetalleExtended unDetalle = new DevolucionDetalleExtended();
        unDetalle.setIdDevolucionDetalle(Comunes.getUUID());
        unDetalle.setIdInsumo(reabastoInsumo.getIdInsumo());
        unDetalle.setCantidad(reabastoInsumo.getCantidadRecibida() - reabastoInsumo.getCantidadIngresada());
        unDetalle.setIdMotivoDevolucion(Constantes.INSUMO_DANIADO);
        listDetalleDevolucion.add(unDetalle);
    }

    private void insertarDevolucion(List<DevolucionDetalleExtended> listDetalleDevolucion) {
        DevolucionExtended unaDevolucion = new DevolucionExtended();
        String idDevolucion = Comunes.getUUID();
        try {
            unaDevolucion.setIdDevolucion(idDevolucion);
            unaDevolucion.setFolio(idDevolucion.substring(1, 8));
            unaDevolucion.setIdEstatusDevolucion(Constantes.DEV_REGISTRADA);
            unaDevolucion.setIdAlmacenOrigen(this.reabastoSelect.getIdEstructura());
            unaDevolucion.setIdAlmacenDestino(this.reabastoSelect.getIdEstructuraPadre());
            for (DevolucionDetalleExtended unDetalle : listDetalleDevolucion) {
                unDetalle.setIdDevolucion(idDevolucion);
            }
            unaDevolucion.setListDetalleDevolucion(listDetalleDevolucion);
            devolucionService.insertarDevolucionAndDetalle(unaDevolucion);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de registrar la devolucion!!");
        }
    }

    public Date obtenerFechaCaducidad(ReabastoEnviadoExtended extended) {
        if (extended.getFechaCaducidad() != null) {
            return extended.getFechaCaducidad();
        } else if (extended.getFechaCad() != null) {
            return extended.getFechaCad();
        } else {
            return null;
        }
    }

    public void actualizarEstadoReabasto() {
        reabastoSelect.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_INGRESADA);
        reabastoSelect.setUpdateIdUsuario(currentUser.getIdUsuario());
        reabastoSelect.setUpdateFecha(new Date());
        reabastoSelect.setFechaIngresoInventario(new Date());
        reabastoSelect.setIdUsuarioIngresoInventario(currentUser.getIdUsuario());
    }

    public String llenarListReabastoIngresado(Reabasto reabasto, ReabastoInsumoExtended reabastoInsumo,
            List<ReabastoEnviado> listReabastoEnviado, List<Inventario> listInventarioInsert,
            List<Inventario> listInventarioUpdate, List<MovimientoInventario> listMovInventario) {
        try {
            if (reabastoInsumo.getCantidadIngresada() > 0) {
                if(reabastoInsumo.getCosto() == null || reabastoInsumo.getCosto().equals(0.0)) {
                    return RESOURCES.getString("orepcio.err.costoCero");
                }

                for (ReabastoEnviadoExtended reabastoEnviado : reabastoInsumo.getListaDetalleReabIns()) {
                    reabastoEnviado.setClaveInstitucional(reabastoInsumo.getClaveInstitucional());
                    Date fechaCad = obtenerFechaCaducidad(reabastoEnviado);
                    String uuId;
                    String idInventario;
                    reabastoEnviado.setCantidadIngresada(reabastoEnviado.getCantidadRecibida());
                    reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_INGRESADA);
                    reabastoEnviado.setUpdateIdUsuario(currentUser.getIdUsuario());
                    reabastoEnviado.setUpdateFecha(new Date());
                    listReabastoEnviado.add(reabastoEnviado);
                    if (reabastoEnviado.getCantidadIngresada() > 0) {
                        //boolean bloqueadoLote = false;
                        String loteComparar = obtenerLoteComparar(reabastoEnviado);
                        if (listInventarioInactivo != null && !listInventarioInactivo.isEmpty()) {
                            for (InventarioExtended inventario : listInventarioInactivo) {
                                if (reabastoEnviado.getClaveInstitucional().equals(inventario.getClaveInstitucional())
                                        && inventario.getLote().equalsIgnoreCase(loteComparar)
                                        && inventario.getFechaCaducidad().equals(fechaCad)) {
                                    //bloqueadoLote = true;
                                    return RESOURCES.getString("oingre.err.inventario") + " " + reabastoEnviado.getClaveInstitucional() + ","
                                            + inventario.getLote() + "," + new SimpleDateFormat("dd/MM/yyyy").format(inventario.getFechaCaducidad());
                                }
                            }
                        }
                        if (reabastoEnviado.getActivo() != 0 && !FechaUtil.isFechaMayorIgualQue(new Date(), fechaCad)) {
                            String loteInv;
                            if (reabastoEnviado.getLoteEnv() != null && reabastoEnviado.getLote() == null) {
                                loteInv = reabastoEnviado.getLoteEnv();
                            } else {
                                loteInv = reabastoEnviado.getLote();
                            }
                            Integer cantidadCaja = reabastoEnviado.getCantidadXCaja();
//                                Inventario unInventario = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
//                                        reabastoInsumo.getIdInsumo(), this.idEstructura, loteInv, cantidadCaja, reabastoEnviado.getClaveProveedor(), fechaCad);
// TODO:06may2024 HHRC -> Agregar validacion de caducidad diferente
                            Inventario unInventario = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCaja(
                                    reabastoInsumo.getIdInsumo(), this.idEstructura, loteInv, cantidadCaja, reabastoEnviado.getClaveProveedor());
                            if (unInventario != null) {
                                String fechaCad1 = "";
                                if (fechaCad != null) {
                                    fechaCad1 = FechaUtil.formatoFecha(fechaCad, "dd/MM/yyy");
                                }
                                String fechaCad2 = "";
                                if (unInventario.getFechaCaducidad() != null) {
                                    fechaCad2 = FechaUtil.formatoFecha(unInventario.getFechaCaducidad(), "dd/MM/yyy");
                                }
                                if (!fechaCad1.equals(fechaCad2)) {
                                    return RESOURCES.getString("oingre.err.recibiOrden")
                                            + "  " + reabastoInsumo.getNombreCorto()
                                            + " Lote  " + loteInv
                                            + " con caducidad: " + fechaCad1
                                            + ", difiere la fecha de caducidad previamente registrada: " + fechaCad2;

                                } else {
                                    idInventario = unInventario.getIdInventario();
                                    unInventario.setCantidadActual(unInventario.getCantidadActual() + (reabastoEnviado.getCantidadIngresada()));
                                    unInventario.setUpdateIdUsuario(currentUser.getIdUsuario());
                                    unInventario.setCosto(reabastoInsumo.getCosto());
                                    unInventario.setCostoUnidosis(reabastoInsumo.getCosto() / cantidadCaja);
                                    unInventario.setUpdateFecha(new Date());
                                    unInventario.setUpdateIdUsuario(currentUser.getIdUsuario());

                                    unInventario.setOsmolaridad(reabastoEnviado.getOsmolaridad());
                                    unInventario.setCalorias(reabastoEnviado.getCalorias());
                                    unInventario.setNoHorasEstabilidad(reabastoEnviado.getNoHorasEstabilidad());
                                    
                                    unInventario.setIdProveedor(reabastoInsumo.getIdProveedor());
                                    unInventario.setIdFabricante(reabastoInsumo.getIdFabricante());

                                    reabastoEnviado.setIdInventarioSurtido(idInventario);
                                    //TODO: revisar si no existe su cveProvedor actualizarla unInventario.setClaveProveedor(loteInv);
                                    listInventarioUpdate.add(unInventario);
                                }
                            } else {
                                Inventario inventario = new Inventario();
                                uuId = Comunes.getUUID();
                                idInventario = uuId;
                                inventario.setIdInventario(uuId);
                                inventario.setFechaIngreso(new Date());
                                inventario.setIdEstructura(this.idEstructura);
                                inventario.setIdInsumo(reabastoInsumo.getIdInsumo());
                                inventario.setIdPresentacion(reabastoInsumo.getIdPresentacion());

                                if (reabastoEnviado.getLote() != null) {
                                    inventario.setLote(reabastoEnviado.getLote());
                                } else {
                                    inventario.setLote(reabastoEnviado.getLoteEnv());
                                }
                                if (reabastoEnviado.getFechaCaducidad() != null) {
                                    inventario.setFechaCaducidad(reabastoEnviado.getFechaCaducidad());
                                } else {
                                    inventario.setFechaCaducidad(reabastoEnviado.getFechaCad());
                                }
                                inventario.setCosto(reabastoInsumo.getCosto());
                                Double costoUnidosis = 0d;
                                if (reabastoInsumo.getCosto() > 0 && cantidadCaja != null && cantidadCaja > 0) {
                                    costoUnidosis = reabastoInsumo.getCosto() / cantidadCaja;
                                }
                                inventario.setCostoUnidosis(costoUnidosis);
                                inventario.setCantidadActual(reabastoEnviado.getCantidadIngresada());
                                inventario.setExistenciaInicial(reabastoEnviado.getCantidadIngresada());

                                inventario.setClaveProveedor(reabastoEnviado.getClaveProveedor());
                                inventario.setOsmolaridad(reabastoEnviado.getOsmolaridad());
                                inventario.setCalorias(reabastoEnviado.getCalorias());
                                inventario.setNoHorasEstabilidad(reabastoEnviado.getNoHorasEstabilidad());
                                inventario.setIdFabricante(reabastoInsumo.getIdFabricante());
                                inventario.setIdProveedor(reabastoInsumo.getIdProveedor());

                                if (estructura.getIdTipoAlmacen() == TipoAlmacen_Enum.SUBALMACEN.getValue()) {
                                    inventario.setPresentacionComercial(Constantes.ES_INACTIVO);
                                    inventario.setCantidadXCaja(Constantes.ES_ACTIVO);
                                } else {
                                    inventario.setPresentacionComercial(Constantes.ES_ACTIVO);
                                    inventario.setCantidadXCaja(reabastoEnviado.getCantidadXCaja());
                                }

                                inventario.setActivo(Constantes.ESTATUS_ACTIVO);
                                inventario.setInsertFecha(new Date());
                                inventario.setInsertIdUsuario(currentUser.getIdUsuario());
                                inventario.setIdTipoOrigen(reabasto.getIdTipoOrigen());

                                listInventarioInsert.add(inventario);
                                reabastoEnviado.setIdInventarioSurtido(inventario.getIdInventario());
                            }

                            MovimientoInventario unMovimientoInventario = new MovimientoInventario();
                            unMovimientoInventario.setIdMovimientoInventario(Comunes.getUUID());
                            unMovimientoInventario.setIdTipoMotivo(Constantes.TIPO_MOV_SURT_REABASTO);
                            unMovimientoInventario.setFecha(new Date());
                            unMovimientoInventario.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                            unMovimientoInventario.setIdEstrutcuraOrigen(reabastoSelect.getIdEstructuraPadre());
                            unMovimientoInventario.setIdEstrutcuraDestino(reabastoSelect.getIdEstructura());
                            unMovimientoInventario.setIdInventario(idInventario);
                            unMovimientoInventario.setCantidad(reabastoEnviado.getCantidadIngresada());
                            if (funcionesOrdenReabasto) {
                                unMovimientoInventario.setFolioDocumento(reabastoSelect.getFolio());
                            } else {
                                unMovimientoInventario.setFolioDocumento(Comunes.getUUID().substring(1, 8));
                            }
                            listMovInventario.add(unMovimientoInventario);

                        } else {
                            return RESOURCES.getString("oingre.err.caducado") + " " + reabastoEnviado.getClaveInstitucional() + ","
                                    + reabastoEnviado.getLote() + "," + new SimpleDateFormat("dd/MM/yyyy").format(fechaCad);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de llenar las listas a ingresar o actualizar: {}", ex.getMessage());
        }
        return "";
    }

    public void imprimir() throws Exception {
        LOGGER.debug("mx.mc.magedbean.PrescripcionMB.imprimir()");
        boolean estatus = Constantes.INACTIVO;
        try {            
            Estructura est = estructuraService.obtenerEstructura(reabastoSelect.getIdEstructura());

            EntidadHospitalaria entidad = entidadHospitalariaService.obtenerEntidadById(est.getIdEntidadHospitalaria());
            // todo: REVISAR UN ISSUE
            if(this.reabastoSelect.getIdProveedor() != null) {
                if (this.estructura.getIdTipoAlmacen() != Constantes.ALMACEN_FARMACIA) {
                    Estructura estructuraOrigen = estructuraService.obtenerEstructura(this.reabastoSelect.getIdEstructuraPadre());
                    reabastoSelect.setNombreProveedor(estructuraOrigen.getNombre());                    
                } else {
                    Proveedor unProveedor = proveedorService.obtenerProvedor(this.reabastoSelect.getIdProveedor());
                    reabastoSelect.setNombreProveedor(unProveedor.getNombreProveedor());                    
                }
            }   
            byte[] buffer = reportesService.imprimirOrdenIngresar(reabastoSelect, entidad);
            if (buffer != null) {
                estatus = Constantes.ACTIVO;
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("ingresoInsumos_%s.pdf", reabastoSelect.getFolio()));                
            }
        } catch (Exception e) {
            LOGGER.error("Error al generar la Impresión: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
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

    public ReabastoExtended getReabastoSelect() {
        return reabastoSelect;
    }

    public void setReabastoSelect(ReabastoExtended reabastoSelect) {
        this.reabastoSelect = reabastoSelect;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getPorCantidad() {
        return porCantidad;
    }

    public void setPorCantidad(int porCantidad) {
        this.porCantidad = porCantidad;
    }

    public List<ReabastoExtended> getListOrdenesIngresar() {
        return listOrdenesIngresar;
    }

    public List<ReabastoInsumoExtended> getListaInsumoIngresar() {
        return listaInsumoIngresar;
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
    
    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public boolean isEliminarCodigo() {
        return eliminarCodigo;
    }

    public void setEliminarCodigo(boolean eliminarCodigo) {
        this.eliminarCodigo = eliminarCodigo;
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

    public boolean isFuncionesOrdenReabasto() {
        return funcionesOrdenReabasto;
    }

    public void setFuncionesOrdenReabasto(boolean funcionesOrdenReabasto) {
        this.funcionesOrdenReabasto = funcionesOrdenReabasto;
    }

    public IngresoReabastoLazy getIngresoReabastoLazy() {
        return ingresoReabastoLazy;
    }

    public void setIngresoReabastoLazy(IngresoReabastoLazy ingresoReabastoLazy) {
        this.ingresoReabastoLazy = ingresoReabastoLazy;
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

    public List<Fabricante> getListaFabricante() {
        return listaFabricante;
    }

    public void setListaFabricante(List<Fabricante> listaFabricante) {
        this.listaFabricante = listaFabricante;
    }

    public List<Proveedor> getListaProveedor() {
        return listaProveedor;
    }

    public void setListaProveedor(List<Proveedor> listaProveedor) {
        this.listaProveedor = listaProveedor;
    }
    
    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }
    
}
