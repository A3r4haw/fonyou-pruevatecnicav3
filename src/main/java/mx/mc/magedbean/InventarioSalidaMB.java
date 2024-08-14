/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.InventarioSalida;
import mx.mc.model.Medicamento;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.TipoMotivo;
import mx.mc.model.TipoMovimiento;
import mx.mc.model.Usuario;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioSalidaService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.TipoMotivoService;
import mx.mc.service.TipoMovimientoService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import java.util.regex.Pattern;
import mx.mc.enums.TipoMovimiento_Enum;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.PresentacionMedicamento;
import mx.mc.model.Proveedor;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.service.PresentacionMedicamentoService;
import mx.mc.service.ProveedorService;
import mx.mc.util.CodigoBarras;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author bbautista
 */
@Controller
@Scope(value = "view")
public class InventarioSalidaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(InventarioSalidaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    private String tipoServicio;
    private String cadenaBusqueda;
    private int numeroRegistros;
    private int idTipoMotivo;
    private int idTipoMovimiento;
    private boolean isAdmin;
    private boolean isJefeArea;
    private int idAlmacen;
    private int activeRow;
    private String codigoBarras;
    private String idEstructura;
    private boolean deleteRow;
    private String firmaMedico;
    private boolean firmaValida;
    private String tipoMotivo;
    private boolean message;
    private int cantidadNueva;
    private String idProveedor;
    private int idPresentacion;
    private int presentacionComercial;
    private int activo;
    private int tipo;
    private int unidosis;
    private int caja;
    private String loteGenerico;
    private Date caducidadGenerica;
    private String errServicio;
    private PermisoUsuario permiso;
    private ClaveProveedorBarras_Extend skuSap;
    private List<ClaveProveedorBarras_Extend> skuSapList;
    private Medicamento medic;
    private List<PresentacionMedicamento> presentacionMedicamentoList;
    private Usuario currentUser;
    private String tipoAlmacen;

    //Regex
    private Pattern regexLote;
    private Pattern regexCantidad;
    private Pattern regexCosto;
    private Inventario inv;

    @Autowired
    private transient InventarioSalidaService inventarioSalidaService;
    private InventarioSalida inventarioSalidaSelect;
    private List<InventarioSalida> ajusteInvList;
    private List<Medicamento> listTipoMedic;

    @Autowired
    private transient EstructuraService estructuraService;
    private Estructura estructuraSelect;
    private List<Estructura> estructuraAux;
    private List<Estructura> estructuraList;

    @Autowired
    private transient InventarioService inventarioService;
    private Inventario inventarioSelect;
    private List<Inventario> inventarioList;

    @Autowired
    private transient MedicamentoService mediService;
    private Medicamento medicamentoSelect;
    private InventarioExtended inventarioExtendedSelect;
    private Medicamento me;
    private List<Medicamento> medicamentoList;
    private MovimientoInventario movimientoInventario;

    @Autowired
    private transient TipoMotivoService tipoMotivoService;
    private List<TipoMotivo> tipoMotivoList;
    private List<TipoMotivo> tipoMotivoListActivos;
    @Autowired
    private transient TipoMovimientoService tipoMovimientoService;
    private List<TipoMovimiento> tipoMovimientoList;
    private ClaveProveedorBarras claveProveedorBarrasSelect;

    @Autowired
    private transient ProveedorService proveedorService;
    private List<Proveedor> proveedorList;

    @Autowired
    private transient PresentacionMedicamentoService presentacionMedicamentoService;
    
    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;
    @Autowired
    private transient MedicamentoService medicamentoService;



    public List<SurtimientoInsumo_Extend> getSurtimientoInsumoExtendedList() {
        return surtimientoInsumoExtendedList;
    }

    public void setSurtimientoInsumoExtendedList(List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList) {
        this.surtimientoInsumoExtendedList = surtimientoInsumoExtendedList;
    }

    private List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList;

    @PostConstruct
    public void init() {
        errServicio = "merma.err.servicio";
        initialize();
        validarUsuarioAdministrador();
        obtieneAlmacenes();
        obtieneListaMovimiento();
        obtenerListaProveedor();
        obtenerPresentacionMedicamento();
        obtieneListaMotivosActivos();
        
    }

    //Private Method
    private void initialize() {

        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
        currentUser = sesion.getUsuarioSelected();
        inventarioSalidaSelect = new InventarioSalida();
        estructuraSelect = new Estructura();
        inventarioSelect = new Inventario();
        claveProveedorBarrasSelect = new ClaveProveedorBarras();
        medicamentoSelect = new Medicamento();
        me = new Medicamento();
        unidosis = 0;
        caja = 1;
        isAdmin = Constantes.INACTIVO;
        isJefeArea = Constantes.INACTIVO;
        tipoAlmacen = "";
        regexLote = Constantes.regexLote;
        movimientoInventario = new MovimientoInventario();
        ajusteInvList = new ArrayList<>();
        estructuraAux = new ArrayList<>();
        estructuraList = new ArrayList<>();
        inventarioList = new ArrayList<>();
        medicamentoList = new ArrayList<>();
        tipoMotivoList = new ArrayList<>();
        tipoMovimientoList = new ArrayList<>();
        proveedorList = new ArrayList<>();
        presentacionMedicamentoList = new ArrayList<>();
        tipoMotivoListActivos = new ArrayList<>();
        listTipoMedic = new ArrayList<>();

        deleteRow = Constantes.INACTIVO;
        firmaMedico = "";
        tipoMotivo = "";
        numeroRegistros = 0;
        idTipoMotivo = 0;
        tipoServicio = "";
        activeRow = -1;
        message = Constantes.ACTIVO;
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.AJUSTEINVENTARIO.getSufijo());
        cantidadNueva = 1;
        activo = 1;
        loteGenerico = "";
        caducidadGenerica = null;
    }

    private void validarUsuarioAdministrador() {
        try {
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            this.isAdmin = sesion.isAdministrador();
            this.isJefeArea = sesion.isJefeArea();
            if (!this.isAdmin && !this.isJefeArea) {
                this.idEstructura = currentUser.getIdEstructura();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }
    
    private void obtieneAlmacenes() {
//        try {
//            boolean almacenes = true;
//            List<String> listEstructuras = estructuraService.obtenerIdsEstructuraJerarquica(currentUser.getIdEstructura(), almacenes);
//            listEstructuras.add(currentUser.getIdEstructura());
//            if(listEstructuras.size() > 0){
//                estructuraList = estructuraService.obtenerEstructurasActivosPorId(listEstructuras);
//            }
//        } catch (Exception ex) {
//            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
//        }
        try {
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(currentUser.getIdEstructura());
            estructuraSelect = estructuraService.obtener(estruct);
            if (estructuraSelect != null) {
                idAlmacen = estructuraSelect.getIdTipoAlmacen();
                if (this.isAdmin || this.isJefeArea) {
                    if(this.isAdmin) {
                        estructuraAux = estructuraService.obtenerAlmacenes();
                    } else {
                        estructuraList = estructuraService.obtenerAlmacenesQueSurtenServicio(currentUser.getIdEstructura());
                        if(estructuraList.isEmpty()) {
                            //Se cambia valor para que no muestre combo y pueda visualizar el almacen
                            isJefeArea = false;
                            idEstructura = estructuraSelect.getIdEstructura();
                            estructuraList.add(estructuraSelect);
                            tipoAlmacen = currentUser.getIdEstructura();
                        }
                    }                
                    if (estructuraAux != null) {
                        for (Estructura est : estructuraAux) {
                            if (est.getIdTipoAreaEstructura() == 1) {
                                buscaEnList(est);
                                break;
                            }
                        }
                    }
                } else {
                    idEstructura = estructuraSelect.getIdEstructura();
                    estructuraList.add(estructuraSelect);
                    tipoAlmacen = currentUser.getIdEstructura();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
        }
    }

    public void obtieneListaMotivo() {
        try {
            tipoMotivoList = tipoMotivoService.getListaByIdTipoMovimiento(idTipoMovimiento);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtieneListaMotivo: {}", ex.getMessage());
        }
    }

    private void obtieneListaMotivosActivos() {
        try {
            tipoMotivoListActivos = tipoMotivoService.obtieneListaMotivosActivos(Constantes.ACTIVOS);
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtieneListaMotivosActivos: {}", ex.getMessage());
        }
    }
    
    private void obtieneListaMotivosActivosEntrada() {
        try {
            tipoMotivoListActivos = tipoMotivoService.obtieneListaMotivosActivosEntrada();
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtieneListaMotivosActivosEntrada: {}", ex.getMessage());
        }
    }

    private void obtieneListaMovimiento() {
        try {
            tipoMovimientoList = tipoMovimientoService.obtenerLista(new TipoMovimiento());
        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtieneListaMovimiento: {}", ex.getMessage());
        }
    }

    private void obtenerListaProveedor() {
        try {
            proveedorList = proveedorService.obtenerListaProveedores();
        } catch (Exception e) {
            LOGGER.error("Error al obtener la Lista de Proveedores");
        }
    }

    private void obtenerPresentacionMedicamento() {
        try {
            presentacionMedicamentoList = presentacionMedicamentoService.obtenerTodo();
        } catch (Exception e) {
            LOGGER.error("Error al Obtener la Presentación");
        }
    }


    private void buscaEnList(Estructura padre) {
        try {
            if (padre != null) {
                for (int i = 0; i < estructuraAux.size(); i++) {
                    if (estructuraAux.get(i).getIdEstructuraPadre() != null && estructuraAux.get(i).getIdEstructuraPadre().toLowerCase().contains(padre.getIdEstructura().toLowerCase())) {
                        estructuraList.add(estructuraAux.get(i));
                        buscaEnList(estructuraAux.get(i));
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al buscar: {}", ex.getMessage());
        }
    }

    private boolean existsMedtoEnLista(InventarioSalida clv, String tMotivo) {
        boolean exits = Constantes.INACTIVO;
        int i = 0;
        for (InventarioSalida aux : ajusteInvList) {
            if (aux.getClave().contains(clv.getClave()) && aux.getLote().contains(clv.getLote()) && aux.getTipoMotivo().contains(tMotivo)) {
                exits = Constantes.ACTIVO;
                activeRow = i;
                break;
            }
            i++;
        }
        return exits;
    }

    private boolean valida() {
        boolean next = Constantes.ACTIVO;
        if (tipoServicio.isEmpty()) {
            next = Constantes.ACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errServicio), null);
        }
        if (idTipoMotivo == 0) {
            next = Constantes.ACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("merma.err.movimiento"), null);
        }
        if (cantidadNueva <= 0) {
            next = Constantes.ACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("inventarios.err.cantMedicamMenor"), null);
        }
        return next;
    }

    public void agregarInsumosPorCodigo() {
        int existencia = 0;
        int cantidad = 0;
        try {

            if (this.codigoBarras == null) {
                return;
            }
            if (permiso.isPuedeCrear() && valida()) {
                //Separar codigo de barras 

                TipoMovimiento tipoMovimiento = tipoMovimientoService.obtenerTipoMovmientoById(idTipoMovimiento);

                String desMovimiento = null;

                if (tipoMovimiento != null) {
                    desMovimiento = tipoMovimiento.getTipoMovimiento();
                }

                InventarioSalida invDetalle = tratarCodigoDeBarras(this.codigoBarras);
                inventarioSalidaSelect = inventarioSalidaService.obtenerDetalleInsumo(invDetalle.getClave(), invDetalle.getLote(), tipoServicio, estructuraSelect.getIdTipoAlmacen());
                tipoMotivo = asignaTipoMovimiento(idTipoMotivo);
                //Validar si el medicamento esta en la lista            
                if (!existsMedtoEnLista(invDetalle, tipoMotivo)) {
                    Estructura estruct = new Estructura();
                    estruct.setIdEstructura(currentUser.getIdEstructura());
                    estructuraSelect = estructuraService.obtener(estruct);

                    if (inventarioSalidaSelect != null && !deleteRow) {
                        inventarioSalidaSelect.setIdInventarioSalida(Comunes.getUUID());
                        inventarioSalidaSelect.setIdEstructura(tipoServicio);
                        inventarioSalidaSelect.setTipoMovimiento(desMovimiento);
                        inventarioSalidaSelect.setLote(Constantes.LOTE_GENERICO);
                        inventarioSalidaSelect.setFechaCaducidad(invDetalle.getFechaCaducidad());
                        inventarioSalidaSelect.setInsertFecha(new Date());
                        inventarioSalidaSelect.setInsertIdUsuario(currentUser.getIdUsuario());
                        inventarioSalidaSelect.setIdTipoMotivo(idTipoMotivo);
                        inventarioSalidaSelect.setTipoMotivo(tipoMotivo);

                        existencia = inventarioSalidaSelect.getExistencia();//-cantidad
                        Inventario inventario = new Inventario();
                        if (idTipoMotivo != Constantes.ID_MERMA) {
                            ///se valida si es entrada o salida
                            inventario = inventarioService.obtener(new Inventario(inventarioSalidaSelect.getIdInventario()));

                            if (desMovimiento.equals(TipoMovimiento_Enum.ENTRADA.getValue())) {
                                if (inventario.getPresentacionComercial() == 0) {
                                    cantidad += cantidadNueva;
                                } else if (inventario.getPresentacionComercial() == 1) {
                                    cantidad = cantidad + (cantidadNueva * inventario.getCantidadXCaja());
                                }
                            } else if (desMovimiento.equals(TipoMovimiento_Enum.SALIDA.getValue())) {

                                if (inventario.getPresentacionComercial() == 0) {
                                    if (existencia < cantidadNueva) {
                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La Cantidad es a quitar es mayor a la del Inventario", null);
                                        return;
                                    } else {
                                        cantidad = cantidad - cantidadNueva;
                                    }
                                } else if (inventario.getPresentacionComercial() == 1) {
                                    cantidadNueva = cantidadNueva * inventario.getCantidadXCaja();
                                    if (existencia < cantidadNueva) {
                                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La catidad es mayor que la Actual", null);
                                        return;
                                    } else {
                                        cantidad = cantidad - cantidadNueva;
                                    }
                                }
                            }
                        }
                        if (inventario.getPresentacionComercial() != null) {
                            inventarioSalidaSelect.setExistencia(existencia);
                            inventarioSalidaSelect.setCantidad(cantidad);
                        } 
                        if (existencia >= 0 && idTipoMotivo != Constantes.ID_MERMA) {
                            ajusteInvList.add(inventarioSalidaSelect);
                        } else if (idTipoMotivo == Constantes.ID_MERMA) {
                            ajusteInvList.add(inventarioSalidaSelect);
                        } else {
                            message = Constantes.ACTIVO;
                            Mensaje.showMessage("Warn", RESOURCES.getString("merma.err.sinventario"), null);
                            return;
                        }
                        numeroRegistros = ajusteInvList.size();

                    } else {
                        this.codigoBarras = "";
                        message = Constantes.ACTIVO;
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.folio"), null);
                    }
                } else {
                    int i = -1;
                    boolean delete = Constantes.INACTIVO;

                    for (InventarioSalida invSali : ajusteInvList) {
                        i++;
                        if (invSali.getClave().equals(invDetalle.getClave()) && invSali.getLote().equals(invDetalle.getLote()) && invSali.getTipoMotivo().contains(tipoMotivo)) {

                            existencia = invSali.getExistencia();
                            cantidad = invSali.getCantidad();

                            if (deleteRow) {
                                if (idTipoMotivo != Constantes.ID_MERMA) {

                                    if (desMovimiento.equals(TipoMovimiento_Enum.ENTRADA.getValue())) {
                                        if (inv.getPresentacionComercial() == null) {
                                            cantidad -= cantidadNueva;
                                        } else if (inv.getPresentacionComercial() == 1) {
                                            cantidad = cantidad - (cantidadNueva * inv.getCantidadXCaja());
                                        }
                                    } else if (desMovimiento.equals(TipoMovimiento_Enum.SALIDA.getValue())) {
                                        if (inv.getPresentacionComercial() == null) {
                                            cantidad = cantidad + (cantidadNueva);
                                        } else if (inv.getPresentacionComercial() == 1) {
                                            cantidad = cantidad - (cantidadNueva * cantidad);
                                        }
                                    }
                                }
                                if (desMovimiento.equals(TipoMovimiento_Enum.ENTRADA.getValue())) {
                                    if (cantidad <= 0) {
                                        delete = Constantes.ACTIVO;
                                    }
                                } else if (desMovimiento.equals(TipoMovimiento_Enum.SALIDA.getValue()) && cantidad >= 0) {
                                    delete = Constantes.ACTIVO;
                                }

                            } else {
                                if (idTipoMotivo != Constantes.ID_MERMA) {

                                    if (desMovimiento.equals(TipoMovimiento_Enum.ENTRADA.getValue())) {
                                        if (inv.getPresentacionComercial() == null) {
                                            cantidad += cantidadNueva;
                                        } else if (inv.getPresentacionComercial() == 1) {
                                            cantidad = cantidad + (cantidadNueva * inv.getCantidadXCaja());
                                        }
                                    } else if (desMovimiento.equals(TipoMovimiento_Enum.SALIDA.getValue())) {
                                        if (inv.getPresentacionComercial() == null) {
                                            if (existencia < cantidadNueva) {
                                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La Cantidad es a quitar es mayor a la del Inventario", null);
                                            } else {
                                                cantidad = cantidad - cantidadNueva;
                                            }

                                        } else if (inv.getPresentacionComercial() == 1) {
                                            cantidadNueva = cantidadNueva * inv.getCantidadXCaja();
                                            if (existencia < cantidadNueva) {
                                                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "La catidad es mayor que la Actual", null);
                                            } else {
                                                cantidad = cantidad - cantidadNueva;
                                            }
                                        }
                                    }
                                }
                            }

                            if (existencia >= 0) {
                                invSali.setExistencia(existencia);
                                invSali.setCantidad(cantidad);
                            } else {
                                message = Constantes.ACTIVO;
                                Mensaje.showMessage("Warn", RESOURCES.getString("merma.err.sinventario"), null);
                                break;
                            }
                            break;
                        }
                    }
                    if (delete) {
                        ajusteInvList.remove(i);
                        numeroRegistros = ajusteInvList.size();
                    }
                }
            }
            deleteRow = Constantes.INACTIVO;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo surtirInsumos :: {}", e.getMessage());
        }
        this.codigoBarras = "";
    }

    private InventarioSalida tratarCodigoDeBarras(String codigo) {
        InventarioSalida invMin = new InventarioSalida();
        try {
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(codigo);
            if (ci != null) {
                invMin.setClave(ci.getClave());
                invMin.setLote(ci.getLote());
                invMin.setFechaCaducidad(ci.getFecha());
            }
            return invMin;
        } catch (Exception e) {
            LOGGER.error("Error en el metodo tratarCodigoDeBarras :: {}", e.getMessage());
        }
        return invMin;
    }

    // todo: cambiar objeto de rabasto por inventario
    // todo: Constantes.ID_MERMA debe estar en un enum
    public List<InventarioExtended> obtenerInsumosDescontar(List<InventarioSalida> list) {
        List<InventarioExtended> newList = new ArrayList<>();

        list.stream().filter(insumo -> (!insumo.getIdTipoMotivo().equals(Constantes.ID_MERMA))).forEachOrdered(insumo -> {
            InventarioExtended inventario = new InventarioExtended();
            inventario.setCantidadAjuste(insumo.getCantidad());
            inventario.setLote(insumo.getLote());
            inventario.setIdEstructura(insumo.getIdEstructura());
            newList.add(inventario);
        });

        return newList;
    }
// */
    //Public Method

    public String asignaTipoMovimiento(int idMotivo) {
        String motivo = "";
        for (TipoMotivo moti : tipoMotivoList) {
            if (moti.getIdTipoMotivo().equals(idMotivo)) {
                motivo = moti.getDescripcion();
            }
        }
        return motivo;
    }

    public boolean guardarRegistros() {
        boolean resp = false;
        try {
            if (!permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.warn.cantSoliMayor"), null);

            } else {

                List<InventarioExtended> listInventarioActualizar = new ArrayList<>();
                List<MovimientoInventario> listaMovimientos = new ArrayList<>();

                Inventario inventario = null;
                InventarioExtended inventarioExt = null;

                for (InventarioSalida item : ajusteInvList) {

                    if (item.getTipoMovimiento().equals(TipoMovimiento_Enum.SALIDA.getValue())) {
                        inventario = inventarioService.obtener(new Inventario(item.getIdInventario()));
                        if (inventario == null) {
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.warn.cantSoliMayor"), null);
                            return false;

                        } else {
                            inventarioExt = new InventarioExtended();
                            inventarioExt.setIdTipoMotivo(item.getIdTipoMotivo());                            
                            inventarioExt.setDescMovimiento(item.getTipoMovimiento());
                            inventarioExt.setIdInventario(inventario.getIdInventario());
                            inventarioExt.setCantidadAjuste(item.getExistencia() - item.getCantidad());
                            inventarioExt.setUpdateFecha(new java.util.Date());
                            inventarioExt.setUpdateIdUsuario(currentUser.getIdUsuario());
                            listInventarioActualizar.add(inventarioExt);

                            //setear los valores  para insertar en movimientoInventario
                            MovimientoInventario moviInventario = new MovimientoInventario();

                            moviInventario.setIdMovimientoInventario(Comunes.getUUID());
                            moviInventario.setIdInventario(item.getIdInventario());
                            moviInventario.setIdTipoMotivo(item.getIdTipoMotivo());
                            moviInventario.setFecha(new java.util.Date());
                            moviInventario.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                            moviInventario.setIdEstrutcuraOrigen(item.getIdEstructura());
                            moviInventario.setIdEstrutcuraDestino(item.getIdEstructura());
                            moviInventario.setCantidad(item.getCantidad());

                            listaMovimientos.add(moviInventario);
                        }

                    } else if (item.getTipoMovimiento().equals(TipoMovimiento_Enum.ENTRADA.getValue())) {
                        inventario = inventarioService.obtener(new Inventario(item.getIdInventario()));
                        if (inventario == null) {
                            inventarioExtendedSelect.setIdInventario(Comunes.getUUID());
                            inventarioExtendedSelect.setIdInsumo(medic.getIdMedicamento());
                            inventarioExtendedSelect.setIdEstructura(item.getIdEstructura());
                            inventarioExtendedSelect.setFechaIngreso(new java.util.Date());
                            inventarioExtendedSelect.setPresentacionComercial(medic.getIdPresentacionEntrada());

                            if (claveProveedorBarrasSelect != null) {
                                claveProveedorBarrasSelect.setClaveProveedor(Comunes.getUUID());
                                claveProveedorBarrasSelect.setInsertUsuario(currentUser.getIdUsuario());
                                claveProveedorBarrasSelect.setInsertFecha(new java.util.Date());
                            }

                            //movimiento inventario
                            MovimientoInventario moviInventario = new MovimientoInventario();

                            moviInventario.setIdTipoMotivo(item.getIdTipoMotivo());
                            moviInventario.setFecha(new java.util.Date());
                            moviInventario.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                            moviInventario.setIdEstrutcuraOrigen(idEstructura);
                            moviInventario.setIdEstrutcuraDestino(idEstructura);
                            moviInventario.setIdInventario(item.getIdInventario());
                            moviInventario.setCantidad(item.getCantidad());

                            //movimientoInventario.set. ...
                            listaMovimientos.add(moviInventario);

                        } else {
                            inventarioExt = new InventarioExtended();
                            inventarioExt.setIdTipoMotivo(item.getIdTipoMotivo());
                            inventarioExt.setDescMovimiento(item.getTipoMovimiento());
                            inventarioExt.setIdInventario(inventario.getIdInventario());
                            inventarioExt.setCantidadAjuste(item.getExistencia() + item.getCantidad());
                            inventarioExt.setUpdateFecha(new java.util.Date());
                            inventarioExt.setUpdateIdUsuario(currentUser.getIdUsuario());

                            listInventarioActualizar.add(inventarioExt);

                            //movimientoInventario
                            MovimientoInventario moviInventario = new MovimientoInventario();
                            //movimientoInventario.set. ...
                            moviInventario.setIdMovimientoInventario(Comunes.getUUID());
                            moviInventario.setIdTipoMotivo(item.getIdTipoMotivo());
                            moviInventario.setFecha(new java.util.Date());
                            moviInventario.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                            moviInventario.setIdEstrutcuraOrigen(item.getIdEstructura());
                            moviInventario.setIdEstrutcuraDestino(item.getIdEstructura());
                            moviInventario.setIdInventario(item.getIdInventario());
                            moviInventario.setCantidad(item.getCantidad());

                            listaMovimientos.add(moviInventario);
                        }

                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(""), null);
                        return false;

                    }
                }

                resp = inventarioService.actualizarEntradaInventario(listInventarioActualizar, listaMovimientos);
                if (!resp) {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El ajuste no se Realizó con Exito", null);
                } else {
                    Mensaje.showMessage("Info", "Ajuste de Inventario Exitoso", null);
                    ajusteInvList = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Guardar el ajuste de inventario: {}", ex.getMessage());
        }
        return resp;
    }

    public void buscarAjusteInventario() {            
        numeroRegistros = ajusteInvList.size();
    }

    public boolean validarDatosInv() {
        boolean next = Constantes.ACTIVO;
        if (tipoServicio == null || tipoServicio.isEmpty()) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errServicio), null);
        }
        if (idTipoMotivo == 0) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "El Motivo es Requerido", null);
        }
        if (loteGenerico.isEmpty() || loteGenerico == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el Lote", null);
        }
        if (idPresentacion == 0) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese  la Presentacion", null);
        }
        if (caducidadGenerica == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese la fecha de Caducidad ", null);
        }
        if (inventarioSelect.getCosto() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el Costo ", null);
        }
        if (inventarioSelect.getCostoUnidosis() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese  el Costo Unidosis", null);
        }
        if (inventarioSelect.getActivo() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese si es Activo/Inactivo", null);
        }
        if (inventarioSelect.getExistenciaInicial() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese la Existencia Inicial", null);
        }
        return next;
    }

    public boolean validarDatosInvAgregar() {
        boolean next = Constantes.ACTIVO;
        if (tipoServicio == null || tipoServicio.isEmpty()) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errServicio), null);
        }
        if (inventarioSelect.getLote().isEmpty()) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el Lote", null);
        }
        if (idPresentacion == 0) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese  la Presentacion", null);
        }
        if (inventarioSelect.getFechaCaducidad() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese la fecha de Caducidad ", null);
        }
        if (inventarioSelect.getCosto() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese el Costo ", null);
        }
        if (inventarioSelect.getCostoUnidosis() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese  el Costo Unidosis", null);
        }
        if (inventarioSelect.getActivo() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese si es Activo/Inactivo", null);
        }
        if (inventarioSelect.getExistenciaInicial() == null) {
            next = Constantes.INACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Ingrese la Existencia Inicial", null);
        }
        return next;
    }

    public void limpiarValores() {
        inventarioSelect = new Inventario();
        listTipoMedic = new ArrayList<>();
        loteGenerico = "";
        caducidadGenerica = null;
        idPresentacion = 0;
        obtieneListaMotivosActivosEntrada();
        obtenerPresentacionComercial();
    }
    
    public void obtenerPresentacionComercial(){
        if(presentacionComercial == 0){
            inventarioSelect.setCantidadXCaja(1);
        }else{
            inventarioSelect.setCantidadXCaja(null);
        }
    }

    public void guardarNuevoRegistroInventario() {

        boolean resp = false;
        try {
            if (!permiso.isPuedeCrear()) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, "No tiene permisos para Crear", null);
            } else {

                if (validarDatosInv()) {
                    inventarioSelect.setIdInventario(Comunes.getUUID());
                    inventarioSelect.setFechaIngreso(new java.util.Date());
                    inventarioSelect.setIdEstructura(tipoServicio);
                    inventarioSelect.setIdInsumo(medic.getIdMedicamento());
                    inventarioSelect.setIdPresentacion(idPresentacion);
                    inventarioSelect.getExistenciaInicial();
                    inventarioSelect.getCantidadActual();
                    inventarioSelect.setLote(loteGenerico);
                    inventarioSelect.getCantidadXCaja();
                    inventarioSelect.setFechaCaducidad(caducidadGenerica);
                    inventarioSelect.getCosto();
                    inventarioSelect.getCostoUnidosis();
                    inventarioSelect.setIdProveedor(idProveedor);
                    inventarioSelect.getIdDictamenMedico();
                    inventarioSelect.setPresentacionComercial(presentacionComercial);

                    inventarioSelect.getActivo();
                    inventarioSelect.setInsertFecha(new java.util.Date());
                    inventarioSelect.setInsertIdUsuario(currentUser.getIdUsuario());

                    movimientoInventario = new MovimientoInventario();

                    movimientoInventario.setIdMovimientoInventario(Comunes.getUUID());
                    movimientoInventario.setIdTipoMotivo(idTipoMotivo);
                    movimientoInventario.setFecha(new java.util.Date());
                    movimientoInventario.setIdUsuarioMovimiento(currentUser.getIdUsuario());
                    movimientoInventario.setIdEstrutcuraOrigen(tipoServicio);
                    movimientoInventario.setIdEstrutcuraDestino(tipoServicio);
                    movimientoInventario.setIdInventario(inventarioSelect.getIdInventario());
                    movimientoInventario.setCantidad(inventarioSelect.getCantidadActual());

                    resp = inventarioService.insertaNuevoInventario(inventarioSelect, movimientoInventario);

                    if (!resp) {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, "Error al intentar agregar el nuevo Inventario", null);
                    } else {
                        message = Constantes.ACTIVO;
                        tipoServicio = "";
                        Mensaje.showMessage("Info", "El Inventario se Registró Correctamente", null);
                    }
                } else {
                    message = Constantes.ACTIVO;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al Guardar el Nuevo Registro: {}", e.getMessage());
        }
           PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, resp);
     
    }

    public void insertarLoteyFechaCaducidadGenerico(SelectEvent event) throws Exception {
        try {

            medic = (Medicamento) event.getObject();
            if (this.medic.getIdMedicamento() != null) {
                this.listTipoMedic = medicamentoService.obtenerTipoMedicamento(this.medic.getIdMedicamento());
                tipo = this.listTipoMedic.get(0).getTipo();
                if (tipo == 39 || tipo == 38) {
                    loteGenerico = Constantes.LOTE_GENERICO;
                    caducidadGenerica = (FechaUtil.formatoFecha("yyyy-MM-dd", Constantes.CADUCIDAD_GENERICA));
                } else {
                    loteGenerico = "";

                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo fecha y lote generico :: {}", e.getMessage());
        }

    }

    
    public void validaFirma() {
        LOGGER.debug("mx.mc.magedbean.InventarioSalida.validaFirma()");

        boolean status = false;

        if (!permiso.isPuedeCrear()) {
            message = Constantes.ACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.transaccion"), null);

        } else if (firmaMedico == null || firmaMedico.trim().isEmpty()) {
            message = Constantes.ACTIVO;
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.firma"), null);

        } else {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(firmaMedico, currentUser.getClaveAcceso())) {
                message = Constantes.ACTIVO;
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("prc.firma.incorrecta"), null);
            } else {
                status = guardarRegistros();
                cantidadNueva = 1;
                tipoServicio = "";
                idTipoMovimiento = 0;
                idTipoMotivo = 0;
                if (status) {
                    buscarAjusteInventario();                    
                }
            }
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);

    }



    /**
     *
     * @param cadena
     * @return
     * @throws java.lang.Exception
     */
    public List<ClaveProveedorBarras_Extend> autoComplete(String cadena) throws Exception {
        LOGGER.trace("mx.mc.magedbean.InventarioSalidaMB.autoComplete()");
        skuSapList = new ArrayList<>();
        boolean buscaCantCero = false;
        for(TipoMovimiento item : tipoMovimientoList){
            if(item.getIdTipoMovimiento().equals(idTipoMovimiento)){
                if(item.getTipoMovimiento().equals(TipoMovimiento_Enum.ENTRADA.getValue())){
                    buscaCantCero = true;
                }
            }
        }        
        idEstructura = "";
        if (!this.isAdmin) {
            idEstructura = currentUser.getIdEstructura();
        } else {
            idEstructura = tipoServicio;
        }
        estructuraSelect.setTipoAlmacen(idEstructura);

        if (valida()) {
            try {
                Matcher mat;
                CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(cadena);
                if (ci != null) {
                    String claveInstitucional = ci.getClave();
                    String lote = ci.getLote();
                    Date fechaCaducidad = ci.getFecha();
                    skuSapList = claveProveedorBarrasService.obtenerListaByQR(claveInstitucional, lote, fechaCaducidad, idEstructura, currentUser.getIdUsuario(),buscaCantCero);
                } else if (!cadena.equals("")) {
                    mat = Constantes.REGEX_NAME_MEDICAM.matcher(cadena);
                    if (!mat.find()) {
                        skuSapList = claveProveedorBarrasService.obtenerListaClavesByBarras(cadena, idEstructura, currentUser.getIdUsuario(),buscaCantCero);
                    } else {
                        skuSapList = claveProveedorBarrasService.obtenerListaByName(cadena, idEstructura, currentUser.getIdUsuario(),buscaCantCero);
                    }
                }
                return skuSapList;
            } catch (Exception ex) {
                LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
            }
        }
        return skuSapList;
    }

    public List<Medicamento> autocompleteMedicamento(String valor) {
        medicamentoList = new ArrayList<>();
        idEstructura = "";

        if (tipoServicio == null || tipoServicio.isEmpty()) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString(errServicio), null);
            if (idTipoMotivo == 0) {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("merma.err.movimiento"), null);
            }
        } else {

            try {
                if (valor != null && !valor.isEmpty() && !valor.equals("")) {
                    medicamentoList = medicamentoService.obtenerMedicamentoByName(valor);
                }
                return medicamentoList;
            } catch (Exception ex) {
                LOGGER.error("Error al obtenerMedicamento: {}", ex.getMessage());
            }

        }
        return medicamentoList;

    }

    public void handleSelect(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.InventarioSalidaMB.handleSelect()");
        inv = null;
        try {
            skuSap = (ClaveProveedorBarras_Extend) e.getObject();
            String idInventario = skuSap.getIdInventario();

            inv = inventarioService.obtener(new Inventario(idInventario));

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

        if (inv != null && inv.getIdInsumo() != null) {
            try {
                Medicamento m = medicamentoService.obtenerMedicamento(inv.getIdInsumo());
                codigoBarras = CodigoBarras.generaCodigoDeBarras(m.getClaveInstitucional(), inv.getLote(), inv.getFechaCaducidad(), inv.getCantidadXCaja());
                agregarInsumosPorCodigo();
                skuSap = new ClaveProveedorBarras_Extend();
                cantidadNueva = 1;                
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
            }
        }
    }

    public void handleUnSelect() {
        skuSap = new ClaveProveedorBarras_Extend();

    }

    public void handleSelectMedicamento(SelectEvent e) {
        try {
            medic = (Medicamento) e.getObject();
            medic = mediService.obtener(medic);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }

    }

    public void handleUnSelectMedicamento() {
        medic = new Medicamento();
    }

    public Medicamento getMe() {
        return me;
    }

    public Pattern getRegexLote() {
        return regexLote;
    }

    public void setRegexLote(Pattern regexLote) {
        this.regexLote = regexLote;
    }

    public Pattern getRegexCantidad() {
        return regexCantidad;
    }

    public void setRegexCantidad(Pattern regexCantidad) {
        this.regexCantidad = regexCantidad;
    }

    public Pattern getRegexCosto() {
        return regexCosto;
    }

    public void setRegexCosto(Pattern regexCosto) {
        this.regexCosto = regexCosto;
    }

    public void setMe(Medicamento me) {
        this.me = me;
    }

    public int getActiveRow() {
        return activeRow;
    }

    public void setActiveRow(int activeRow) {
        this.activeRow = activeRow;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public boolean isFirmaValida() {
        return firmaValida;
    }

    public void setFirmaValida(boolean firmaValida) {
        this.firmaValida = firmaValida;
    }

    public String getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(String tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public boolean isDeleteRow() {
        return deleteRow;
    }

    public String getFirmaMedico() {
        return firmaMedico;
    }

    public int getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(int idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public void setFirmaMedico(String firmaMedico) {
        this.firmaMedico = firmaMedico;
    }

    public void setDeleteRow(boolean deleteRow) {
        this.deleteRow = deleteRow;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public List<Estructura> getEstructuraAux() {
        return estructuraAux;
    }

    public void setEstructuraAux(List<Estructura> estructuraAux) {
        this.estructuraAux = estructuraAux;
    }

    public Medicamento getMedicamentoSelect() {
        return medicamentoSelect;
    }

    public void setMedicamentoSelect(Medicamento medicamentoSelect) {
        this.medicamentoSelect = medicamentoSelect;
    }

    public TipoMotivoService getTipoMotivoService() {
        return tipoMotivoService;
    }

    public void setTipoMotivoService(TipoMotivoService tipoMotivoService) {
        this.tipoMotivoService = tipoMotivoService;
    }

    public List<TipoMotivo> getTipoMotivoList() {
        return tipoMotivoList;
    }

    public void setTipoMotivoList(List<TipoMotivo> tipoMotivoList) {
        this.tipoMotivoList = tipoMotivoList;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public InventarioSalidaService getInventarioSalidaService() {
        return inventarioSalidaService;
    }

    public void setInventarioSalidaService(InventarioSalidaService inventarioSalidaService) {
        this.inventarioSalidaService = inventarioSalidaService;
    }

    public InventarioSalida getInventarioSalidaSelect() {
        return inventarioSalidaSelect;
    }

    public void setInventarioSalidaSelect(InventarioSalida inventarioSalidaSelect) {
        this.inventarioSalidaSelect = inventarioSalidaSelect;
    }

    public List<InventarioSalida> getAjusteInvList() {
        return ajusteInvList;
    }

    public void setAjusteInvList(List<InventarioSalida> ajusteInvList) {
        this.ajusteInvList = ajusteInvList;
    }

    public EstructuraService getEstructuraService() {
        return estructuraService;
    }

    public void setEstructuraService(EstructuraService estructuraService) {
        this.estructuraService = estructuraService;
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

    public InventarioService getInventarioService() {
        return inventarioService;
    }

    public void setInventarioService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public Inventario getInventarioSelect() {
        return inventarioSelect;
    }

    public void setInventarioSelect(Inventario inventarioSelect) {
        this.inventarioSelect = inventarioSelect;
    }

    public ClaveProveedorBarras getClaveProveedorBarrasSelect() {
        return claveProveedorBarrasSelect;
    }

    public void setClaveProveedorBarrasSelect(ClaveProveedorBarras claveProveedorBarrasSelect) {
        this.claveProveedorBarrasSelect = claveProveedorBarrasSelect;
    }

    public List<Inventario> getInventarioList() {
        return inventarioList;
    }

    public void setInventarioList(List<Inventario> inventarioList) {
        this.inventarioList = inventarioList;
    }

    public MedicamentoService getMediService() {
        return mediService;
    }

    public void setMediService(MedicamentoService mediService) {
        this.mediService = mediService;
    }

    public List<Medicamento> getMedicamentoList() {
        return medicamentoList;
    }

    public void setMedicamentoList(List<Medicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    public int getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(int idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public List<TipoMovimiento> getTipoMovimientoList() {
        return tipoMovimientoList;
    }

    public void setTipoMovimientoList(List<TipoMovimiento> tipoMovimientoList) {
        this.tipoMovimientoList = tipoMovimientoList;
    }

    public ClaveProveedorBarras_Extend getSkuSap() {
        return skuSap;
    }

    public void setSkuSap(ClaveProveedorBarras_Extend skuSap) {
        this.skuSap = skuSap;
    }

    public List<ClaveProveedorBarras_Extend> getSkuSapList() {
        return skuSapList;
    }

    public void setSkuSapList(List<ClaveProveedorBarras_Extend> skuSapList) {
        this.skuSapList = skuSapList;
    }

    public Medicamento getMedic() {
        return medic;
    }

    public void setMedic(Medicamento medic) {
        this.medic = medic;
    }

    public InventarioExtended getInventarioExtendedSelect() {
        return inventarioExtendedSelect;
    }

    public void setInventarioExtendedSelect(InventarioExtended inventarioExtendedSelect) {
        this.inventarioExtendedSelect = inventarioExtendedSelect;
    }

    public int getCantidadNueva() {
        return cantidadNueva;
    }

    public void setCantidadNueva(int cantidadNueva) {
        this.cantidadNueva = cantidadNueva;
    }

    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public List<PresentacionMedicamento> getPresentacionMedicamentoList() {
        return presentacionMedicamentoList;
    }

    public void setPresentacionMedicamentoList(List<PresentacionMedicamento> presentacionMedicamentoList) {
        this.presentacionMedicamentoList = presentacionMedicamentoList;
    }

    public int getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(int idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public int getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(int presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }

    public MovimientoInventario getMovimientoInventario() {
        return movimientoInventario;
    }

    public void setMovimientoInventario(MovimientoInventario movimientoInventario) {
        this.movimientoInventario = movimientoInventario;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public List<TipoMotivo> getTipoMotivoListActivos() {
        return tipoMotivoListActivos;
    }

    public void setTipoMotivoListActivos(List<TipoMotivo> tipoMotivoListActivos) {
        this.tipoMotivoListActivos = tipoMotivoListActivos;
    }

    public List<Medicamento> getListTipoMedic() {
        return listTipoMedic;
    }

    public void setListTipoMedic(List<Medicamento> listTipoMedic) {
        this.listTipoMedic = listTipoMedic;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getLoteGenerico() {
        return loteGenerico;
    }

    public void setLoteGenerico(String loteGenerico) {
        this.loteGenerico = loteGenerico;
    }

    public Date getCaducidadGenerica() {
        return caducidadGenerica;
    }

    public void setCaducidadGenerica(Date caducidadGenerica) {
        this.caducidadGenerica = caducidadGenerica;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public int getUnidosis() {
        return unidosis;
    }

    public void setUnidosis(int unidosis) {
        this.unidosis = unidosis;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }
    
}
