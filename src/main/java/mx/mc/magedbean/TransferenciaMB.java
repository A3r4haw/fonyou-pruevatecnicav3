/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.magedbean;


import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.el.ELContext;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.AlmacenControl;
import mx.mc.model.CatalogoGeneral;
import mx.mc.model.ClaveProveedorBarras_Extend;
import mx.mc.model.CodigoInsumo;
import mx.mc.model.EntidadHospitalaria;
import mx.mc.model.Estructura;
import mx.mc.model.InventarioSalida;
import mx.mc.model.Medicamento;
import mx.mc.model.PermisoUsuario;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoInsumo;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Usuario;
import mx.mc.service.AlmacenControlService;
import mx.mc.service.CatalogoGeneralService;
import mx.mc.service.ClaveProveedorBarrasService;
import mx.mc.service.EntidadHospitalariaService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioSalidaService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoService;
import mx.mc.service.ReportesService;
import mx.mc.util.CodigoBarras;
import mx.mc.util.Comunes;
import mx.mc.util.Mensaje;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
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
public class TransferenciaMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferenciaMB.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private String cadenaBusqueda;
    private int numeroRegistros;
    private String ubicacion;
    private boolean btnNew;
    private boolean contEdit;
    private int tipoOrden;
    private int tipoInsumo;
    private String nombreInsumo;
    private boolean list;
    private String idEstructuraProv;
    private int cantTransfer;
    private Date fechaActual;
    private boolean status;
    private boolean insumo;
    private int activeRow;
    private boolean confirm1;
    private boolean confirm2;
    private boolean printSolicitud;
    private boolean isAdmin;
    private boolean exits;
    private int suma;
    private int result;
    private int cantTransferDU;
    private String codigoBarras;
    private int aux = 0;
    private Usuario currentUser;
    private String nombreUsuario;
    private PermisoUsuario permiso;
    private ClaveProveedorBarras_Extend skuSap;
    private List<ClaveProveedorBarras_Extend> skuSapList;
    private String archivo;    
    private String idEstructura;    
    private int idAlmacen;
    private List<Estructura> listaAuxiliar;
    
    @Autowired
    private transient ReabastoService reabastoService;
    private Reabasto reabasto;
    private List<Reabasto> reabastoList;
    private List<Reabasto> reabastoList2;

    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;
    private ReabastoInsumoExtended reabastoInsumoExt;
    private List<ReabastoInsumoExtended> reabastoInsumoExtList;
    private List<ReabastoInsumoExtended> reabastoInsumoExtListCopy;

    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private EntidadHospitalariaService entidadHospitalariaService;
    
    @Autowired
    private transient ReportesService reportesService;
    
    private Estructura estructuraSelect;
    private List<Estructura> estructuraList;
    private List<Estructura> estructuraList2;

    @Autowired
    private transient MedicamentoService mediService;
    private Medicamento medicamento;

    @Autowired
    private transient EstructuraService estrucService;
    private Estructura estrucSelect;
    private Estructura origen;
    private Estructura destino;

    @Autowired
    private transient InventarioSalidaService inventarioSalidaService;
    private InventarioSalida inventarioSalidaSelect;

    @Autowired
    private transient AlmacenControlService controlService;
    private AlmacenControl almacenCtrl;

    @Autowired
    private transient CatalogoGeneralService catalogoGeneralService;
    private List<CatalogoGeneral> tipoInsumoList;

    @Autowired
    private transient ClaveProveedorBarrasService claveProveedorBarrasService;

    @Autowired
    private transient InventarioService inventarioService;

    @PostConstruct
    public void init() {
        StringBuilder s = new StringBuilder();
        datosCurrentUser();
        permiso = Comunes.obtenerPermisos(Transaccion_Enum.TRANSFERENCIAINVENTARIO.getSufijo());
        initialize();
        s.append(currentUser.getNombre()).append(" ").append(currentUser.getApellidoPaterno()).append(" ").append(currentUser.getApellidoMaterno());
        nombreUsuario = s.toString();
        validarUsuarioAdministrador();
        obtieneAlmacen();        
        insumos();
        buscarTransferencia();        
    }

    private void datosCurrentUser() {
        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
        SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");

        currentUser = sesion.getUsuarioSelected();
    }

    private void initialize() {
        cantTransfer = 1;
        ubicacion = "Inventario\\Transferencia";
        fechaActual = new Date();
        contEdit = Constantes.ACTIVO;
        tipoInsumo = 0;
        nombreInsumo = "";
        nombreUsuario = "";
        insumo = Constantes.INACTIVO;
        reabasto = new Reabasto();
        reabastoInsumoExt = new ReabastoInsumoExtended();
        almacenCtrl = new AlmacenControl();
        origen = new Estructura();
        reabastoList = new ArrayList<>();
        reabastoInsumoExtList = new ArrayList<>();
        tipoInsumoList = new ArrayList<>();
        estructuraList = new ArrayList<>();        
        estructuraList2 = new ArrayList<>();
        isAdmin = Constantes.INACTIVO;
        confirm1 = Constantes.INACTIVO;
        confirm2 = Constantes.INACTIVO;
        list = Constantes.INACTIVO;
        if (permiso.isPuedeCrear()) {
            btnNew = Constantes.ACTIVO;
        }
    }

    public void validarUsuarioAdministrador() {
        try {
            this.isAdmin = Comunes.isAdministrador();            
            if (!this.isAdmin) {
                this.idEstructura = currentUser.getIdEstructura();
            }
        } catch (Exception e) {
            LOGGER.error("Error en el metodo validarUsuarioAdministrador :: {}", e.getMessage());
        }
    }
    
       
    private void obtieneAlmacen() {
        try {
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(currentUser.getIdEstructura());
            estructuraSelect = estructuraService.obtener(estruct);
            if (estructuraSelect != null) {
                idEstructuraProv = estructuraSelect.getIdEstructura();
                estructuraList = estructuraService.obtenerAlmacenesForTransfer(idEstructuraProv, estructuraSelect.getIdTipoAlmacen());
                estructuraList2 = estructuraList;
                if (!isAdmin) {
                    estructuraList = new ArrayList<>();
                    estructuraList.add(estructuraSelect);
                } else {
                    estructuraList.add(estructuraSelect);
                    idEstructura = estructuraList.get(0).getIdEstructura();
                }
                if (estructuraSelect.getIdTipoAreaEstructura() == 1 && estructuraSelect.getIdTipoAlmacen() == 1) {
                    isAdmin = Constantes.ACTIVO;
                    insumo = Constantes.ACTIVO;
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener Estructura: {}", ex.getMessage());
        }
    }

    private void insumos() {
        LOGGER.trace("Load Insumos");
        try {
            List<Medicamento> tiposInsumos;
            if (isAdmin) {
                tiposInsumos = mediService.obtenerTiposInsumoPorAlmacen(null);
            } else {
                tiposInsumos = mediService.obtenerTiposInsumoPorAlmacen(idEstructuraProv);
            }
            if (tiposInsumos != null) {
                if (tiposInsumos.size() == 1) {
                    tipoInsumo = tiposInsumos.get(0).getTipo();
                    nombreInsumo = catalogoGeneralService.obtenerPorIdCatalogoGrl(tipoInsumo, Constantes.TIPO_INSUMO);
                    list = Constantes.INACTIVO;
                } else if (tiposInsumos.size() > 1) {
                    insumo = Constantes.ACTIVO;
                    list = Constantes.ACTIVO;
                    tipoInsumoList = catalogoGeneralService.obtenerTiposInsumos(Constantes.TIPO_INSUMO, idEstructuraProv);
                    if (tipoInsumoList != null && tipoInsumoList.size() == 1) {
                        tipoInsumo = tipoInsumoList.get(0).getIdCatalogoGeneral();
                    }
                }
            }

        } catch (Exception ex) {
            LOGGER.error("Error al obtener Insumos: {}", ex.getMessage());
        }
    }

    private boolean existeLoteEnInsumo(String idInsumo, String lote) {
        exits = Constantes.INACTIVO;
        reabastoInsumoExtList.stream().filter(prdct -> prdct.getIdInsumo().equals(idInsumo)).forEach(cnsmr -> {
            if (cnsmr.getListaDetalleReabIns() != null) {
                cnsmr.getListaDetalleReabIns().stream().filter(prdct2 -> prdct2.getLoteEnv().equals(lote)).forEach(cnsmr2 -> {
                    exits = Constantes.ACTIVO;
                    activeRow = cnsmr.getListaDetalleReabIns().indexOf(cnsmr2);
                });
            }
        });
        return exits;
    }

    private boolean existsMedtoEnLista(String idInsumo) {
        exits = Constantes.INACTIVO;
        reabastoInsumoExtList.stream().filter(prdct -> prdct.getIdInsumo().equals(idInsumo)).forEach(cnsmr -> 
            exits = Constantes.ACTIVO
        );

        return exits;
    }

    private boolean validaDatos() {
        boolean sigue = Constantes.ACTIVO;
        if (reabasto.getProveedor().equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transferencia.err.selectorigen"), null);
            sigue = Constantes.INACTIVO;
        } else if (reabasto.getAlmacen().equals("")) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transferencia.err.selectDestino"), null);
            sigue = Constantes.INACTIVO;
        } else if (tipoOrden == 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transferencia.err.selectipoReabasto"), null);
            sigue = Constantes.INACTIVO;
        }
        return sigue;
    }

    private List<ClaveProveedorBarras_Extend> eliminarRepetidos(List<ClaveProveedorBarras_Extend> lista) {
        List<ClaveProveedorBarras_Extend> newlist = new ArrayList<>();
        //Declaramos un Map vacio
        HashMap<String, ClaveProveedorBarras_Extend> map = new HashMap<>();

        //Iteramos la Lista para meterlos al MAP
        for (ClaveProveedorBarras_Extend _item : lista) {
            map.put(_item.getIdInventario(), _item);
        }
        //Iteramos el MAP para meterlos a la nuevalista
        Set<Entry<String, ClaveProveedorBarras_Extend>> set = map.entrySet();
        for (Entry<String, ClaveProveedorBarras_Extend> entry : set) {
            newlist.add(entry.getValue());
        }

        return newlist;
    }

    /*  Methods Publics*/
    public void buscarTransferencia() {
        LOGGER.trace("Buscando conincidencias de: {}", cadenaBusqueda);
        try {
            if (cadenaBusqueda != null && cadenaBusqueda.trim().isEmpty()) {
                cadenaBusqueda = null;
            }

            reabastoList = reabastoService.obtenerTransferencias(cadenaBusqueda,idEstructura);
            reabastoList2 = reabastoList;
            numeroRegistros = reabastoList2.size();

            cadenaBusqueda = null;
        } catch (Exception e) {
            LOGGER.error("Error al buscar Medicamentos: {}", e.getMessage());
        }
    }

    public void newOrdenTransferencia() {
        try {
            status = Constantes.INACTIVO;
            if (validaDatos()) {
                reabastoInsumoExtList = new ArrayList<>();
                contEdit = Constantes.ACTIVO;
                cantTransfer = 1;
                if (permiso.isPuedeCrear()) {
                    tipoInsumoList.stream().filter((item) -> (tipoInsumo == item.getIdCatalogoGeneral())).forEachOrdered((item) -> {
                        nombreInsumo = item.getNombreCatalogo();
                    });                    
                    reabasto.setIdReabasto(Comunes.getUUID());
                    reabasto.setFechaSolicitud(new Date());
                    reabasto.setIdUsuarioSolicitud(currentUser.getIdUsuario());
                    reabasto.setIdTipoOrden(tipoOrden);
                    status = Constantes.ACTIVO;
                    if (tipoOrden == Constantes.TIPO_ORDEN_NORMAL) {
                        reabastoInsumoExtList = reabastoInsumoService.obtenerListaNormalTransfer(origen.getIdEstructura(), destino.getIdEstructura(), origen.getIdTipoAlmacen(), tipoInsumo);
                    }
                    numeroRegistros = reabastoInsumoExtList.size();
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error al Obtener los datos: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void obtenerTransferencia(Reabasto transfer) {
        try {
            Medicamento medi = new Medicamento();
            reabasto = transfer;
            reabastoInsumoExtList = reabastoInsumoService.obtenerDetalle(reabasto.getIdReabasto());            
            //List<ReabastoEnviadoExtended> listaDetalleReabIns;
            reabastoInsumoExtList.forEach((it) -> {
                ReabastoEnviadoExtended reaEnvExt = new ReabastoEnviadoExtended();
                reaEnvExt.setLoteEnv(it.getLote());
                reaEnvExt.setFechaCad(it.getFechaCaducidad());
                reaEnvExt.setCantidadEnviado(it.getCantidadSurtida() / it.getCantidadXCaja());                
                reaEnvExt.setCantidadCaja(it.getCantidadXCaja());
                it.setListaDetalleReabIns(new ArrayList<>());
                it.getListaDetalleReabIns().add(reaEnvExt);
            });
            if (!reabastoInsumoExtList.isEmpty()) {
                medi.setIdMedicamento(reabastoInsumoExtList.get(0).getIdInsumo());
                medi = mediService.obtenerPorIdMedicamento(medi.getIdMedicamento());
                tipoInsumo = medi.getTipo();
                tipoInsumoList.stream().filter((item) -> (item.getIdCatalogoGeneral() == tipoInsumo)).forEachOrdered((item) -> {
                    nombreInsumo = item.getNombreCatalogo();
                });
            }
            numeroRegistros = reabastoInsumoExtList.size();
            contEdit = Constantes.INACTIVO;
        } catch (Exception ex) {
            LOGGER.error("Error al buscar Medicamentos: {}", ex.getMessage());
        }
    }

    public void sendOrdenTransferencia() {
        try {
            if (permiso.isPuedeCrear()) {
                if (reabasto != null && reabastoInsumoExtList != null) {

                    reabasto.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                    reabasto.setEstatus(EstatusReabasto_Enum.ENTRANSITO.name());
                    reabasto.setSolicitante(currentUser.getNombre() + " " + currentUser.getApellidoPaterno() + " " + currentUser.getApellidoMaterno());
                    reabasto.setInsertFecha(new Date());
                    reabasto.setFechaSurtida(reabasto.getInsertFecha());
                    reabasto.setInsertIdUsuario(currentUser.getIdUsuario());
                    reabasto.setIdUsuarioSurtida(currentUser.getIdUsuario());
                    reabasto.setTransferencia(Constantes.POR_TRANSFER);

                    for (ReabastoInsumo _insumo : reabastoInsumoExtList) {
                        _insumo.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                    }

                    status = reabastoService.insertarSolicitudTransfer(reabasto, reabastoInsumoExtList);
                    if (status) {
                        numeroRegistros = reabastoList.size();
                        reabastoList.add(reabasto);
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.err.errorSend"), null);
                    }
                }
                if (printSolicitud) {
                    printTransferencia(reabasto);
                }

            }
        } catch (Exception ex) {
            LOGGER.error("Error al Enviar el reabasto: {}", ex.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam("print", printSolicitud);
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, status);
    }

    public void printTransferencia(Reabasto transfer) {        
    LOGGER.trace("TransferenciaMB.printTransferencia(reabasto)");
    boolean estatus = Constantes.INACTIVO;
        try {
            Estructura estruct = new Estructura();
            estruct.setIdEstructura(transfer.getIdEstructura());
            estruct = estructuraService.obtenerEstructura(transfer.getIdEstructura());
            if (estruct.getIdEntidadHospitalaria() == null) {
                estruct.setIdEntidadHospitalaria("");
            }

            EntidadHospitalaria enti = entidadHospitalariaService.obtenerEntidadById(estruct.getIdEntidadHospitalaria());
            if (enti == null) {
                enti = new EntidadHospitalaria();
                enti.setDomicilio("");
                enti.setNombre("");

            }
            transfer.setDomicilio(enti.getDomicilio());
            transfer.setNombreEntidad(enti.getNombre());
            
            byte[] buffer = reportesService.reporteTranferencia(transfer, enti, nombreUsuario);
            if (buffer != null) {
                estatus = Constantes.ACTIVO;  
                SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "sesionMB");
                sesion.setReportStream(buffer);
                sesion.setReportName(String.format("transferencia_%s.pdf", transfer.getFolio()));
            }
        }catch (Exception e) {
            LOGGER.error("Error al Imprimir Tranferencia: {}", e.getMessage());
        }
        PrimeFaces.current().ajax().addCallbackParam(Constantes.ESTATUS_VIEW, estatus);
    }

    public void addInsumoList() {
        String transferWarnCantMenorReorder = "transfer.warn.cantMenorReorder";
        cantTransferDU = 0;
        suma = 0;
        result = 0;
        try {
            if (permiso.isPuedeCrear()) {
                reabastoInsumoExt = new ReabastoInsumoExtended();
                ReabastoEnviadoExtended reabastoEnviadoExt = new ReabastoEnviadoExtended();
                if (inventarioSalidaSelect != null) {
                    if (inventarioSalidaSelect.getActivo() > 0) {
                        if (cantTransfer == 0) {
                            cantTransfer = 1;
                        }
                        cantTransferDU = cantTransfer * inventarioSalidaSelect.getCantidad();
                        result = inventarioSalidaSelect.getExistencia() - cantTransferDU;
                        if (result >= 0) {
                            AlmacenControl ACtrol = new AlmacenControl();
                            ACtrol.setIdMedicamento(inventarioSalidaSelect.getIdMedicamento());
                            ACtrol.setIdAlmacen(idEstructuraProv);
                            ACtrol.setActivo(Constantes.ACTIVO);
                            almacenCtrl = controlService.obtener(ACtrol);
                            if (almacenCtrl != null) {
                                if (!existsMedtoEnLista(inventarioSalidaSelect.getIdMedicamento())) {
                                    //El medicamento NO existe en la lista
                                    if (result < almacenCtrl.getReorden()) {
                                        Mensaje.showMessage("Warn", RESOURCES.getString(transferWarnCantMenorReorder), null);
                                    }

                                    reabastoInsumoExt.setIdInsumo(inventarioSalidaSelect.getIdMedicamento());
                                    reabastoInsumoExt.setCantidadXCaja(inventarioSalidaSelect.getCantidad());
                                    reabastoInsumoExt.setPresentacion(inventarioSalidaSelect.getPresentacion());
                                    reabastoInsumoExt.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                                    reabastoInsumoExt.setClave(inventarioSalidaSelect.getClave());
                                    reabastoInsumoExt.setNombreComercial(inventarioSalidaSelect.getNombreCorto());
                                    reabastoInsumoExt.setCantidadSurtida(cantTransferDU);//Dosis unitaria
                                    reabastoInsumoExt.setCantidadActual(cantTransfer);//se manejara como cajas

                                    reabastoInsumoExt.setCantidadSolicitada(cantTransfer);

                                    reabastoEnviadoExt.setIdReabastoEnviado(Comunes.getUUID());
                                    reabastoEnviadoExt.setIdInventarioSurtido(inventarioSalidaSelect.getIdInventario());
                                    reabastoEnviadoExt.setCantidadEnviado(cantTransferDU);
                                    reabastoEnviadoExt.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                                    reabastoEnviadoExt.setIdInsumo(inventarioSalidaSelect.getIdMedicamento());
                                    reabastoEnviadoExt.setIdEstructura(inventarioSalidaSelect.getIdEstructura());
                                    reabastoEnviadoExt.setLoteEnv(inventarioSalidaSelect.getLote());
                                    reabastoEnviadoExt.setFechaCad(inventarioSalidaSelect.getFechaCaducidad());
                                    reabastoEnviadoExt.setCantidadXCaja(inventarioSalidaSelect.getCantidad());
                                    reabastoEnviadoExt.setPresentacionComercial(inventarioSalidaSelect.getIdTipoMotivo());
                                    reabastoEnviadoExt.setCantidadCaja(cantTransfer);

                                    reabastoInsumoExt.setListaDetalleReabIns(new ArrayList<>());
                                    reabastoInsumoExt.getListaDetalleReabIns().add(reabastoEnviadoExt);

                                    reabastoInsumoExtList.add(reabastoInsumoExt);
                                    numeroRegistros = reabastoInsumoExtList.size();
                                } else {//El medicamento existe en la lista  

                                    if (!existeLoteEnInsumo(inventarioSalidaSelect.getIdMedicamento(), inventarioSalidaSelect.getLote())) {//Agregar el lote
                                        reabastoEnviadoExt.setIdReabastoEnviado(Comunes.getUUID());
                                        reabastoEnviadoExt.setIdInventarioSurtido(inventarioSalidaSelect.getIdInventario());
                                        reabastoEnviadoExt.setCantidadEnviado(cantTransferDU);
                                        reabastoEnviadoExt.setIdEstatusReabasto(EstatusReabasto_Enum.ENTRANSITO.getValue());
                                        reabastoEnviadoExt.setIdInsumo(inventarioSalidaSelect.getIdMedicamento());
                                        reabastoEnviadoExt.setIdEstructura(inventarioSalidaSelect.getIdEstructura());
                                        reabastoEnviadoExt.setLoteEnv(inventarioSalidaSelect.getLote());
                                        reabastoEnviadoExt.setFechaCad(inventarioSalidaSelect.getFechaCaducidad());
                                        reabastoEnviadoExt.setCantidadXCaja(inventarioSalidaSelect.getCantidad());
                                        reabastoEnviadoExt.setCantidadCaja(cantTransfer);

                                        reabastoInsumoExtList.stream().filter(prdct1 -> prdct1.getIdInsumo().equals(inventarioSalidaSelect.getIdMedicamento())).forEach(cnsmr1 -> {

                                            if (result < almacenCtrl.getReorden()) {
                                                Mensaje.showMessage("Warn", inventarioSalidaSelect.getClave() + " - " + RESOURCES.getString(transferWarnCantMenorReorder), null);
                                            }

                                            if (cnsmr1.getListaDetalleReabIns() == null) {
                                                cnsmr1.setListaDetalleReabIns(new ArrayList<>());
                                            }

                                            if (tipoOrden == Constantes.TIPO_ORDEN_NORMAL) {

                                                cnsmr1.getListaDetalleReabIns().forEach(item -> 
                                                    suma += item.getCantidadEnviado()
                                                );
                                                suma += cantTransferDU;
                                                if (suma > cnsmr1.getCantidadSolicitada()) {
                                                    Mensaje.showMessage("Warn", inventarioSalidaSelect.getClave() + " - " + RESOURCES.getString("transfer.err.cantMayorSolicitada"), null);
                                                }
                                            }
                                            cnsmr1.getListaDetalleReabIns().add(reabastoEnviadoExt);
                                            cnsmr1.setCantidadSurtida(cnsmr1.getCantidadSurtida() + cantTransferDU);
                                            cnsmr1.setCantidadActual(cnsmr1.getCantidadActual() + cantTransfer);

                                        });

                                    } else//buscar el lote y sumar
                                    {
                                        reabastoInsumoExtList.stream().filter(prdct1 -> prdct1.getIdInsumo().equals(inventarioSalidaSelect.getIdMedicamento())).forEach(cnsmr1 ->

                                            cnsmr1.getListaDetalleReabIns().stream().filter(prdct2 -> prdct2.getLoteEnv().equals(inventarioSalidaSelect.getLote())).forEach(cnsmr2 -> {
                                                int resul = inventarioSalidaSelect.getExistencia() - (cnsmr2.getCantidadEnviado() + cantTransferDU);

                                                if (resul > 0) {
                                                    if (resul < almacenCtrl.getReorden()) {
                                                        Mensaje.showMessage("Warn", RESOURCES.getString(transferWarnCantMenorReorder), null);
                                                    }

                                                    if (tipoOrden == Constantes.TIPO_ORDEN_NORMAL) {
                                                        cnsmr1.getListaDetalleReabIns().forEach(item -> 
                                                            suma += item.getCantidadEnviado()
                                                        );
                                                        suma += cantTransferDU;
//                                                        if (suma > cnsmr1.getCantidadSolicitada()) {
//                                                            Mensaje.showMessage("Warn", inventarioSalidaSelect.getClave() + " - " + RESOURCES.getString("transfer.err.cantMayorSolicitada"), null);
//                                                        }
                                                    } else {
                                                        cnsmr1.setCantidadSolicitada(cnsmr1.getCantidadSurtida());
                                                    }

                                                    cnsmr2.setCantidadEnviado(cnsmr2.getCantidadEnviado() + cantTransferDU);
                                                    cnsmr1.setCantidadSurtida(cnsmr1.getCantidadSurtida() + cantTransferDU);
                                                    cnsmr1.setCantidadActual(cnsmr1.getCantidadActual() + cantTransfer);
                                                } else {
                                                    cantTransfer = 1;
                                                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.err.cantMayorDisponible"), null);
                                                }
                                            })
                                        );
                                    }
                                }
                            } else {
                                Mensaje.showMessage("Warn", RESOURCES.getString("transfer.err.medInactAlm"), null);
                            }
                        } else {
                            cantTransfer = 1;
                            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.err.cantMayorDisponible"), null);
                        }
                    } else {
                        Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.err.medInacInv"), null);
                    }
                } else {
                    Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.err.medNoExiste"), null);
                }
            } else {
                Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("medicamento.warn.sinpermisosCrear"), null);
            }
            cantTransfer = 1;
            codigoBarras = "";
        } catch (Exception ex) {
            LOGGER.error("Error al Agregar el insumo a la Lista: {}", ex.getMessage());
        }
    }

    public void deleteInsumoOfList(String clv) {
        int index = -1;
        try {
            for (ReabastoInsumo reaInsu : reabastoInsumoExtList) {
                if (reaInsu.getIdInsumo().equals(clv)) {
                    reabastoInsumoService.eliminar(reaInsu);
                    index = reabastoInsumoExtList.indexOf(reaInsu);
                    break;
                }
            }
            if (index >= 0) {
                reabastoInsumoExtList.remove(index);
            }
            numeroRegistros = reabastoInsumoExtList.size();

        } catch (Exception ex) {
            LOGGER.error("Error al obtener el Insumo: {}", ex.getMessage());
        }
    }

    public void deleteLoteOfInsumo(String _idInsumo, String _lote) {
        activeRow = -1;
        reabastoInsumoExtList.stream().filter(prdct -> prdct.getIdInsumo().equals(_idInsumo)).forEach(cnsmr -> {
            cnsmr.getListaDetalleReabIns().stream().filter(prdct2 -> prdct2.getLoteEnv().equals(_lote)).forEach(cnsmr2 -> {
                exits = Constantes.ACTIVO;
                activeRow = cnsmr.getListaDetalleReabIns().indexOf(cnsmr2);
            });
            if (activeRow > -1) {
                cnsmr.setCantidadSolicitada(cnsmr.getCantidadSurtida() - cnsmr.getListaDetalleReabIns().get(activeRow).getCantidadEnviado());
                cnsmr.getListaDetalleReabIns().remove(activeRow);
                activeRow = cnsmr.getListaDetalleReabIns().isEmpty() ? reabastoInsumoExtList.indexOf(cnsmr) : -1;
            }            
        });

        if(activeRow > -1){
            reabastoInsumoExtList.remove(activeRow);
        }
        numeroRegistros = reabastoInsumoExtList.size();
    }

    public void onCellEdit(CellEditEvent event) {
        if ((int) event.getNewValue() <= 0) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transfer.err.cantMenorCero"), null);
        }
    }

    public void asignaOrigen() {
        try {
            origen = new Estructura();
            origen.setIdEstructura(idEstructuraProv);
            origen = estrucService.obtener(origen);
            reabasto.setProveedor(origen.getNombre());
            reabasto.setIdEstructuraPadre(idEstructuraProv);
            reabasto.setIdProveedor(idEstructuraProv);
            if (origen.getIdEntidadHospitalaria() != null) {
                estructuraList2 = estrucService.obtenerAlmacenesEntidad(origen.getIdEntidadHospitalaria(), origen.getIdTipoAlmacen());
                aux = -1;
                estructuraList2.stream().filter(item -> item.getIdEstructura().equals(origen.getIdEstructura())).forEach(resu ->
                    aux = estructuraList2.indexOf(resu)
                );
                if (aux >= 0) {
                    estructuraList2.remove(aux);
                }
            } else {
                estructuraList2.clear();
            }

            if (isAdmin) {
                contEdit = Constantes.INACTIVO;
            }
            tipoInsumoList.clear();
            insumos();
        } catch (Exception ex) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transferencia.err.obtenerOrigen"), null);
            LOGGER.error("Ocurrio un error al obtener el origen: {}", ex.getMessage());
        }
    }

    public void validaOrigenDestino() {
        try {//          Destino                             Origen
            if (reabasto.getIdEstructura() != null && reabasto.getIdProveedor() != null) {
                destino.setIdEstructura(reabasto.getIdEstructura());
                destino = estrucService.obtener(destino);
                reabasto.setIdEstructuraPadre(destino.getIdEstructuraPadre());
                reabasto.setAlmacen(destino.getNombre());
                if (reabasto.getIdEstructura().equals(reabasto.getIdProveedor())) {
                    Mensaje.showMessage("Warn", RESOURCES.getString("transferencia.warn.seleccionar"), null);
                    reabasto.setIdEstructura(null);
                }
            }
        } catch (Exception ex) {
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("transferencia.err.obteDestino"), null);
            LOGGER.error("Ocurrio un error al obtener el destino: {}", ex.getMessage());
        }
    }

    public void selections() {
        reabasto = new Reabasto();
        origen = new Estructura();
        destino = new Estructura();
        if (isAdmin) {
            idEstructuraProv = "";
            contEdit = Constantes.ACTIVO;
        } else {
            asignaOrigen();
            contEdit = Constantes.INACTIVO;
        }
        tipoOrden = 0;
        tipoInsumo = 0;
    }

    public List<ClaveProveedorBarras_Extend> autoComplete(String cadena) {
        LOGGER.trace("mx.mc.magedbean.SurtPrescripcionExtMB.autoComplete()");
        skuSapList = new ArrayList<>();
        try {
            String _idEstructura = origen.getIdEstructura();
            CodigoInsumo ci = CodigoBarras.parsearCodigoDeBarras(cadena);
            if (ci != null) {
                String claveInstitucional = ci.getClave();
                String lote = ci.getLote();
                Date fechaCaducidad = ci.getFecha();

                skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoQrTransfer(claveInstitucional, lote, fechaCaducidad, _idEstructura, destino.getIdEstructura());
            } else if (cadena.length() >= 10) {
                skuSapList = claveProveedorBarrasService.obtenerListaClavesCodigoBarrasTransfer(cadena, _idEstructura, destino.getIdEstructura());
            }
            if (skuSapList.isEmpty()) {
                skuSapList = claveProveedorBarrasService.obtenerListaClavesDescripcionTransfer(cadena, _idEstructura, destino.getIdEstructura());
            }

            //Eliminamos repetidos
            skuSapList = eliminarRepetidos(skuSapList);

        } catch (Exception ex) {
            LOGGER.error("Error al obtener obtenerListaClavesSku : {}", ex.getMessage());
        }
        return skuSapList;
    }

    public void handleSelect(SelectEvent e) {
        LOGGER.info("mx.mc.magedbean.SurtPrescripcionExtMB.handleSelect()");
        try {
            skuSap = (ClaveProveedorBarras_Extend) e.getObject();
            inventarioSalidaSelect = inventarioSalidaService.obtenerDetalleInsumo(skuSap.getIdInventario(), origen.getIdTipoAlmacen());
            addInsumoList();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void handleUnSelect() {
        skuSap = new ClaveProveedorBarras_Extend();

    }

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public int getNumeroRegistros() {
        return numeroRegistros;
    }

    public void setNumeroRegistros(int numeroRegistros) {
        this.numeroRegistros = numeroRegistros;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isBtnNew() {
        return btnNew;
    }

    public void setBtnNew(boolean btnNew) {
        this.btnNew = btnNew;
    }

    public boolean isContEdit() {
        return contEdit;
    }

    public void setContEdit(boolean contEdit) {
        this.contEdit = contEdit;
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

    public String getNombreInsumo() {
        return nombreInsumo;
    }

    public void setNombreInsumo(String nombreInsumo) {
        this.nombreInsumo = nombreInsumo;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public String getIdEstructuraProv() {
        return idEstructuraProv;
    }

    public void setIdEstructuraProv(String idEstructura) {
        this.idEstructuraProv = idEstructura;
    }

    public int getCantTransfer() {
        return cantTransfer;
    }

    public void setCantTransfer(int cantTransfer) {
        this.cantTransfer = cantTransfer;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isInsumo() {
        return insumo;
    }

    public void setInsumo(boolean insumo) {
        this.insumo = insumo;
    }

    public int getActiveRow() {
        return activeRow;
    }

    public void setActiveRow(int activeRow) {
        this.activeRow = activeRow;
    }

    public boolean isConfirm1() {
        return confirm1;
    }

    public void setConfirm1(boolean confirm1) {
        this.confirm1 = confirm1;
    }

    public boolean isConfirm2() {
        return confirm2;
    }

    public void setConfirm2(boolean confirm2) {
        this.confirm2 = confirm2;
    }

    public boolean isPrintSolicitud() {
        return printSolicitud;
    }

    public void setPrintSolicitud(boolean printSolicitud) {
        this.printSolicitud = printSolicitud;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
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

    public ReabastoService getReabastoService() {
        return reabastoService;
    }

    public void setReabastoService(ReabastoService reabastoService) {
        this.reabastoService = reabastoService;
    }

    public Reabasto getReabasto() {
        return reabasto;
    }

    public void setReabasto(Reabasto reabasto) {
        this.reabasto = reabasto;
    }

    public List<Reabasto> getReabastoList() {
        return reabastoList;
    }

    public void setReabastoList(List<Reabasto> reabastoList) {
        this.reabastoList = reabastoList;
    }

    public List<Reabasto> getReabastoList2() {
        return reabastoList2;
    }

    public void setReabastoList2(List<Reabasto> reabastoList2) {
        this.reabastoList2 = reabastoList2;
    }

    public ReabastoInsumoService getReabastoInsumoService() {
        return reabastoInsumoService;
    }

    public void setReabastoInsumoService(ReabastoInsumoService reabastoInsumoService) {
        this.reabastoInsumoService = reabastoInsumoService;
    }

    public ReabastoInsumoExtended getReabastoInsumoExt() {
        return reabastoInsumoExt;
    }

    public void setReabastoInsumoExt(ReabastoInsumoExtended reabastoInsumoExt) {
        this.reabastoInsumoExt = reabastoInsumoExt;
    }

    public List<ReabastoInsumoExtended> getReabastoInsumoExtList() {
        return reabastoInsumoExtList;
    }

    public void setReabastoInsumoExtList(List<ReabastoInsumoExtended> reabastoInsumoExtList) {
        this.reabastoInsumoExtList = reabastoInsumoExtList;
    }

    public List<ReabastoInsumoExtended> getReabastoInsumoExtListCopy() {
        return reabastoInsumoExtListCopy;
    }

    public void setReabastoInsumoExtListCopy(List<ReabastoInsumoExtended> reabastoInsumoExtListCopy) {
        this.reabastoInsumoExtListCopy = reabastoInsumoExtListCopy;
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

    public List<Estructura> getEstructuraList2() {
        return estructuraList2;
    }

    public void setEstructuraList2(List<Estructura> estructuraList2) {
        this.estructuraList2 = estructuraList2;
    }

    public MedicamentoService getMediService() {
        return mediService;
    }

    public void setMediService(MedicamentoService mediService) {
        this.mediService = mediService;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public EstructuraService getEstrucService() {
        return estrucService;
    }

    public void setEstrucService(EstructuraService estrucService) {
        this.estrucService = estrucService;
    }

    public Estructura getEstrucSelect() {
        return estrucSelect;
    }

    public void setEstrucSelect(Estructura estrucSelect) {
        this.estrucSelect = estrucSelect;
    }

    public Estructura getOrigen() {
        return origen;
    }

    public void setOrigen(Estructura origen) {
        this.origen = origen;
    }

    public Estructura getDestino() {
        return destino;
    }

    public void setDestino(Estructura destino) {
        this.destino = destino;
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

    public AlmacenControlService getControlService() {
        return controlService;
    }

    public void setControlService(AlmacenControlService controlService) {
        this.controlService = controlService;
    }

    public AlmacenControl getAlmacenCtrl() {
        return almacenCtrl;
    }

    public void setAlmacenCtrl(AlmacenControl almacenCtrl) {
        this.almacenCtrl = almacenCtrl;
    }

    public CatalogoGeneralService getCatalogoGeneralService() {
        return catalogoGeneralService;
    }

    public void setCatalogoGeneralService(CatalogoGeneralService catalogoGeneralService) {
        this.catalogoGeneralService = catalogoGeneralService;
    }

    public List<CatalogoGeneral> getTipoInsumoList() {
        return tipoInsumoList;
    }

    public void setTipoInsumoList(List<CatalogoGeneral> tipoInsumoList) {
        this.tipoInsumoList = tipoInsumoList;
    }

    public ClaveProveedorBarrasService getClaveProveedorBarrasService() {
        return claveProveedorBarrasService;
    }

    public void setClaveProveedorBarrasService(ClaveProveedorBarrasService claveProveedorBarrasService) {
        this.claveProveedorBarrasService = claveProveedorBarrasService;
    }

    public InventarioService getInventarioService() {
        return inventarioService;
    }

    public void setInventarioService(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getCantTransferDU() {
        return cantTransferDU;
    }

    public void setCantTransferDU(int cantTransferDU) {
        this.cantTransferDU = cantTransferDU;
    }

    public PermisoUsuario getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoUsuario permiso) {
        this.permiso = permiso;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public List<Estructura> getListaAuxiliar() {
        return listaAuxiliar;
    }

    public void setListaAuxiliar(List<Estructura> listaAuxiliar) {
        this.listaAuxiliar = listaAuxiliar;
    }
        
}
