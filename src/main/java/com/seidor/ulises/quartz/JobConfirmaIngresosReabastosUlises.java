package com.seidor.ulises.quartz;

import com.seidor.ulises.model.ExpEntradas;
import com.seidor.ulises.service.ExpEntradasService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import mx.mc.enums.EstatusReabasto_Enum;
import mx.mc.enums.TipoAlmacen_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.DevolucionDetalleExtended;
import mx.mc.model.DevolucionExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.InventarioExtended;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoEnviadoExtended;
import mx.mc.model.ReabastoInsumoExtended;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.DevolucionService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.ReabastoEnviadoService;
import mx.mc.service.ReabastoInsumoService;
import mx.mc.service.ReabastoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
import mx.mc.util.Mensaje;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author apalacios
 */
public class JobConfirmaIngresosReabastosUlises extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobConfirmaIngresosReabastosUlises.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private transient ExpEntradasService expEntradasService;
    
    @Autowired
    private transient ReabastoService reabastoService;
    
    @Autowired
    private transient ReabastoInsumoService reabastoInsumoService;
    
    @Autowired
    private transient ReabastoEnviadoService reabastoEnviadoService;
    
    @Autowired
    private transient InventarioService inventarioService;
    
    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired
    private transient ConfigService configService;
    
    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private transient DevolucionService devolucionService;

    private boolean funcionesOrdenReabasto;
    private List<Config> configList;
    private List<InventarioExtended> listInventarioInactivo;
    private String idEstructura;
    private Estructura estructura;
    
    private void obtenerDatosSistema() {
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex.getMessage());
        }
    }

    /**
     * Clave de instanciación de Cron
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.trace("com.seidor.ulises.quartz.JobConfirmaIngresosReabastosUlises.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);    
        ejecutaJob();
    }
    
    private int obtenerCantidadTotalConfirmada(String folio, ReabastoInsumoExtended reabastoInsumo, List<ExpEntradas> lineas) {
        int cantidad = 0;
        for (ExpEntradas entrada : lineas) {
            if (entrada.getAlbaran().equalsIgnoreCase(folio) && entrada.getArticulo().equalsIgnoreCase(reabastoInsumo.getClaveInstitucional())) {
                cantidad += entrada.getCantidadConfirmada().intValue();
                entrada.setTratado("S");
                entrada.setFechaTratado(new Date());
            }
        }
        return cantidad;
    }
    
    private int obtenerCantidadConfirmadaPorLote(String folio, ReabastoInsumoExtended reabastoInsumo, List<ExpEntradas> lineas, String loteEnv) {
        int cantidad = 0;
        for (ExpEntradas entrada : lineas) {
            if (entrada.getAlbaran().equalsIgnoreCase(folio) && entrada.getLote().equalsIgnoreCase(loteEnv)
                    && entrada.getArticulo().equalsIgnoreCase(reabastoInsumo.getClaveInstitucional())) {
                cantidad = entrada.getCantidadConfirmada().intValue();
                break;
            }
        }
        return cantidad;
    }
    
    public List<ReabastoInsumoExtended> obtenerDetalleInsumos(Reabasto reabastoSelect, Usuario usuarioRecibe, List<ExpEntradas> lineas) {
        List<ReabastoInsumoExtended> listaInsumoIngresar = new ArrayList<>();
        try {
            List<Integer> listEstatusReabasto = new ArrayList<>();
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue());
            listEstatusReabasto.add(EstatusReabasto_Enum.RECIBIDA.getValue());
            List<String> listIdInsumos = new ArrayList<>();
//            listaInsumoIngresar = reabastoInsumoService.obtenerReabastoInsumoExtends(reabastoSelect.getIdReabasto(), listEstatusReabasto,true);
            for (ReabastoInsumoExtended reabastoInsumo : listaInsumoIngresar) {
                int cantidadConfirmada = obtenerCantidadTotalConfirmada(reabastoSelect.getFolio(), reabastoInsumo, lineas);
                reabastoInsumo.setCantidadIngresada(cantidadConfirmada);
                List<ReabastoEnviadoExtended> listaEnviado = reabastoEnviadoService.obtenerListaReabastoEnviado(reabastoInsumo.getIdReabastoInsumo(), listEstatusReabasto);
                for (ReabastoEnviadoExtended unReabastoEnviado : listaEnviado) {
                    int cantidadConfirmadaPorLote = obtenerCantidadConfirmadaPorLote(reabastoSelect.getFolio(), reabastoInsumo, lineas, unReabastoEnviado.getLoteEnv());
                    unReabastoEnviado.setCantidadIngresada(cantidadConfirmadaPorLote);
                    List<Inventario> listInventario = inventarioService.obtenerInventariosPorInsumoEstructuraCantidadXCajaYProveedor(reabastoInsumo.getIdInsumo(),
                            idEstructura, unReabastoEnviado.getCantidadXCaja(), unReabastoEnviado.getClaveProveedor(), unReabastoEnviado.getLoteEnv());
                    if (!listInventario.isEmpty()) {
                        reabastoInsumo.setCosto(listInventario.get(0).getCosto());
                    }
                }
                reabastoInsumo.setListaDetalleReabIns(listaEnviado);
                listIdInsumos.add(reabastoInsumo.getIdInsumo());
            }
            if (!listIdInsumos.isEmpty())
                listInventarioInactivo = inventarioService.obtenerListaInactivosByIdInsumos(listIdInsumos);
            else
                LOGGER.error("La lista de insumos del Reabasto {} está vacía", reabastoSelect.getFolio());
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de obtener los insumos del reabasto: {} : {}", reabastoSelect.getFolio(), ex.getMessage());
            Mensaje.showMessage(Constantes.MENSAJE_ERROR, RESOURCES.getString("err.lista"), null);
        }
        return listaInsumoIngresar;
    }
    
    private DevolucionDetalleExtended registrarDevolucion(ReabastoInsumoExtended reabastoInsumo, String idInventarioDev) {
        DevolucionDetalleExtended unDetalle = new DevolucionDetalleExtended();
        unDetalle.setIdDevolucionDetalle(Comunes.getUUID());
        unDetalle.setIdInsumo(reabastoInsumo.getIdInsumo());
        unDetalle.setCantidad(reabastoInsumo.getCantidadRecibida() - reabastoInsumo.getCantidadIngresada());
        unDetalle.setIdMotivoDevolucion(Constantes.INSUMO_DANIADO);
        unDetalle.setIdInventario(idInventarioDev);
        return unDetalle;
    }

    public void actualizarEstadoReabasto(Reabasto reabastoSelect) {
        reabastoSelect.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_INGRESADA);
        reabastoSelect.setUpdateIdUsuario(reabastoSelect.getIdUsuarioRecepcion());
        reabastoSelect.setUpdateFecha(new Date());
        reabastoSelect.setFechaIngresoInventario(new Date());
        reabastoSelect.setIdUsuarioIngresoInventario(reabastoSelect.getIdUsuarioRecepcion());
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
    
    public String obtenerLoteComparar(ReabastoEnviadoExtended item) {
        if (item.getLote() != null) {
            return item.getLote();
        } else if (item.getLoteEnv() != null) {
            return item.getLoteEnv();
        } else {
            return null;
        }
    }
    
    public String llenarListReabastoIngresado(Reabasto reabasto, ReabastoInsumoExtended reabastoInsumo,
            List<ReabastoEnviado> listReabastoEnviado, List<Inventario> listInventarioInsert,
            List<Inventario> listInventarioUpdate, List<MovimientoInventario> listMovInventario) {
        try {
            if (reabastoInsumo.getCantidadIngresada() > 0) {
                if (reabastoInsumo.getCosto() == null || reabastoInsumo.getCosto().equals(0.0)) {
                    return RESOURCES.getString("orepcio.err.costoCero");                
                }
                for (ReabastoEnviadoExtended reabastoEnviado : reabastoInsumo.getListaDetalleReabIns()) {
                    reabastoEnviado.setClaveInstitucional(reabastoInsumo.getClaveInstitucional());
                    Date fechaCad = obtenerFechaCaducidad(reabastoEnviado);
                    String uuId;
                    String idInventario;
                    //reabastoEnviado.setCantidadIngresada(reabastoEnviado.getCantidadRecibida());
                    reabastoEnviado.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_INGRESADA);
                    reabastoEnviado.setUpdateIdUsuario(reabasto.getIdUsuarioRecepcion());
                    reabastoEnviado.setUpdateFecha(new Date());
                    listReabastoEnviado.add(reabastoEnviado);                           
                    if (reabastoEnviado.getCantidadIngresada() > 0) {    
                        String loteComparar = obtenerLoteComparar(reabastoEnviado);
                        if (listInventarioInactivo != null && !listInventarioInactivo.isEmpty()) {
                            for (InventarioExtended inventario : listInventarioInactivo) {
                                if (reabastoEnviado.getClaveInstitucional().equals(inventario.getClaveInstitucional())
                                        && inventario.getLote().equalsIgnoreCase(loteComparar) 
                                            && inventario.getFechaCaducidad().equals(fechaCad)) {
                                        return  RESOURCES.getString("oingre.err.inventario") + " " + reabastoEnviado.getClaveInstitucional() + "," +
                                                inventario.getLote() + "," + new SimpleDateFormat("dd/MM/yyyy").format(inventario.getFechaCaducidad());
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
                                Inventario unInventario = inventarioService.obtenerInventariosPorInsumoEstructuraLoteYCantidadXCajaYFechaCad(
                                        reabastoInsumo.getIdInsumo(), idEstructura, loteInv, cantidadCaja, reabastoEnviado.getClaveProveedor(), fechaCad);                                
                                if (unInventario != null) {
                                    idInventario = unInventario.getIdInventario();
                                    unInventario.setCantidadActual(unInventario.getCantidadActual() + (reabastoEnviado.getCantidadIngresada()));
                                    unInventario.setUpdateIdUsuario(reabasto.getIdUsuarioRecepcion());
                                    unInventario.setCosto(reabastoInsumo.getCosto());
                                    unInventario.setCostoUnidosis(reabastoInsumo.getCosto() / cantidadCaja);
                                    unInventario.setUpdateFecha(new Date());
                                    
                                    unInventario.setOsmolaridad(reabastoEnviado.getOsmolaridad());
//                                    unInventario.setMargenOsmolaridad(reabastoEnviado.getMargenOsmolaridad());
                                    unInventario.setNoHorasEstabilidad(reabastoEnviado.getNoHorasEstabilidad());
                                    unInventario.setIdFabricante(reabastoEnviado.getIdFabricante());
                                    unInventario.setIdProveedor(reabastoEnviado.getIdProveedor());
                                    
                                    reabastoEnviado.setIdInventarioSurtido(idInventario);
                                    //TODO: revisar si no existe su cveProvedor actualizarla unInventario.setClaveProveedor(loteInv);
                                    listInventarioUpdate.add(unInventario);
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
                                    inventario.setCostoUnidosis((reabastoInsumo.getCosto() / cantidadCaja));
                                    inventario.setCantidadActual(reabastoEnviado.getCantidadIngresada());
                                    inventario.setExistenciaInicial(reabastoEnviado.getCantidadIngresada());                                    
                                    
                                    inventario.setClaveProveedor(reabastoEnviado.getClaveProveedor());
                                    inventario.setOsmolaridad(reabastoEnviado.getOsmolaridad());
//                                    inventario.setMargenOsmolaridad(reabastoEnviado.getMargenOsmolaridad());
                                    inventario.setNoHorasEstabilidad(reabastoEnviado.getNoHorasEstabilidad());
                                    inventario.setIdFabricante(reabastoEnviado.getIdFabricante());
                                    inventario.setIdProveedor(reabastoEnviado.getIdProveedor());
                                    
                                    if (estructura.getIdTipoAlmacen().equals(TipoAlmacen_Enum.SUBALMACEN.getValue())) { 
                                        inventario.setPresentacionComercial(Constantes.ES_INACTIVO);
                                        inventario.setCantidadXCaja(Constantes.ES_ACTIVO);
                                    } else {
                                        inventario.setPresentacionComercial(Constantes.ES_ACTIVO);
                                        inventario.setCantidadXCaja(reabastoEnviado.getCantidadXCaja());
                                    }                                    
                                    inventario.setActivo(Constantes.ESTATUS_ACTIVO);
                                    inventario.setInsertFecha(new Date());
                                    inventario.setInsertIdUsuario(reabasto.getIdUsuarioRecepcion());
                                    inventario.setIdTipoOrigen(reabasto.getIdTipoOrigen());
                                    
                                    listInventarioInsert.add(inventario);
                                    reabastoEnviado.setIdInventarioSurtido(inventario.getIdInventario());
                                }

                                MovimientoInventario unMovimientoInventario = new MovimientoInventario();
                                unMovimientoInventario.setIdMovimientoInventario(Comunes.getUUID());
                                unMovimientoInventario.setIdTipoMotivo(Constantes.TIPO_MOV_SURT_REABASTO);
                                unMovimientoInventario.setFecha(new Date());
                                unMovimientoInventario.setIdUsuarioMovimiento(reabasto.getIdUsuarioRecepcion());
                                unMovimientoInventario.setIdEstrutcuraOrigen(reabasto.getIdEstructuraPadre());
                                unMovimientoInventario.setIdEstrutcuraDestino(reabasto.getIdEstructura());
                                unMovimientoInventario.setIdInventario(idInventario);
                                unMovimientoInventario.setCantidad(reabastoEnviado.getCantidadIngresada());
                                if (funcionesOrdenReabasto) {
                                    unMovimientoInventario.setFolioDocumento(reabasto.getFolio());
                                } else {
                                    unMovimientoInventario.setFolioDocumento(Comunes.getUUID().substring(1, 8));
                                }
                                listMovInventario.add(unMovimientoInventario);
                                
                            } else {
                                return RESOURCES.getString("oingre.err.caducado")+ " " + reabastoEnviado.getClaveInstitucional() + "," +
                                       reabastoEnviado.getLote() + "," + new SimpleDateFormat("dd/MM/yyyy").format(fechaCad);
                            }
                        }                              
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de llenar las listas a ingresar o actualizar: {}", ex.getMessage());
        }
        return "";
    }
    
    private boolean insertarDevolucion(Reabasto reabastoSelect, List<DevolucionDetalleExtended> listDetalleDevolucion) {
        boolean res = false;
        DevolucionExtended unaDevolucion = new DevolucionExtended();
        String idDevolucion = Comunes.getUUID();
        try {
            unaDevolucion.setIdDevolucion(idDevolucion);
            unaDevolucion.setFolio(idDevolucion.substring(1, 8));
            unaDevolucion.setIdEstatusDevolucion(Constantes.DEV_REGISTRADA);
            unaDevolucion.setIdAlmacenOrigen(reabastoSelect.getIdEstructura());
            unaDevolucion.setIdAlmacenDestino(reabastoSelect.getIdEstructuraPadre());
            for (DevolucionDetalleExtended unDetalle : listDetalleDevolucion) {
                unDetalle.setIdDevolucion(idDevolucion);
            }
            unaDevolucion.setListDetalleDevolucion(listDetalleDevolucion);
            res = devolucionService.insertarDevolucionAndDetalle(unaDevolucion);
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al momento de registrar la devolucion!!");
        }
        return res;
    }
    
    private void actualizaExpEntradas(List<ExpEntradas> lineas) throws Exception {
        List<ExpEntradas> lineasUpdate = new ArrayList<>();
        for (ExpEntradas entrada : lineas) {
            if (entrada.getTratado().equalsIgnoreCase("S"))
                lineasUpdate.add(entrada);
        }
        if (!lineasUpdate.isEmpty())
            expEntradasService.actualizarLista(lineasUpdate);
    }
    
    public void ingresarReabastoInsumo(Reabasto reabastoSelect, List<ExpEntradas> lineas) {
        try {
            Usuario usuarioRecibe;
            if (reabastoSelect.getIdUsuarioRecepcion() != null)
                usuarioRecibe = usuarioService.obtenerUsuarioByIdUsuario(reabastoSelect.getIdUsuarioRecepcion());
            else {
                reabastoSelect.setIdUsuarioRecepcion(reabastoSelect.getIdUsuarioSolicitud());
                usuarioRecibe = usuarioService.obtenerUsuarioByIdUsuario(reabastoSelect.getIdUsuarioSolicitud());
            }
            idEstructura = reabastoSelect.getIdEstructura();
            estructura = estructuraService.obtenerEstructura(this.idEstructura);
            List<ReabastoInsumoExtended> listaInsumoIngresar = obtenerDetalleInsumos(reabastoSelect, usuarioRecibe, lineas);
            List<ReabastoEnviado> listReabastoEnviado = new ArrayList<>();
            List<Inventario> listInventarioInsert = new ArrayList<>();
            List<Inventario> listInventarioUpdate = new ArrayList<>();
            List<MovimientoInventario> listMovInventario = new ArrayList<>();
            List<DevolucionDetalleExtended> listDetalleDevolucion = new ArrayList<>();
            String result = "";    
            for (ReabastoInsumoExtended reabastoInsumo : listaInsumoIngresar) {
                reabastoInsumo.setIdEstatusReabasto(Constantes.ESTATUS_REABASTO_INGRESADA);
                reabastoInsumo.setUpdateIdUsuario(usuarioRecibe.getIdUsuario());
                reabastoInsumo.setUpdateFecha(new Date());
                result = llenarListReabastoIngresado(reabastoSelect, reabastoInsumo, listReabastoEnviado, listInventarioInsert, listInventarioUpdate, listMovInventario);
                if (!(result.equals(""))) {
                    LOGGER.error("JobConfirmaIngresosReabastosUlises {}", result);
                    return;
                }
                if (reabastoInsumo.getCantidadRecibida() != 0 && !reabastoInsumo.getCantidadRecibida().equals(reabastoInsumo.getCantidadIngresada())) {
                    for (ReabastoEnviado reabEnv : listReabastoEnviado) {
                        if (reabEnv.getCantidadRecibida() != reabEnv.getCantidadIngresada())
                            listDetalleDevolucion.add(registrarDevolucion(reabastoInsumo, reabEnv.getIdInventarioSurtido()));
                    }
                }
            }
            if (!(result.equals(""))) {
                LOGGER.error("JobConfirmaIngresosReabastosUlises {}", result);
            } else {
                actualizarEstadoReabasto(reabastoSelect);
                boolean estatus = reabastoInsumoService.actulizaIngresoOrdenReabasto(reabastoSelect, listaInsumoIngresar, listReabastoEnviado, listInventarioInsert, listInventarioUpdate, listMovInventario);
                if (estatus) {
                    if (!listDetalleDevolucion.isEmpty()) {
                        estatus = insertarDevolucion(reabastoSelect, listDetalleDevolucion);
                    }
                    if (estatus) {
                        actualizaExpEntradas(lineas);
                    }
                    LOGGER.debug("JobConfirmaIngresosReabastosUlises {}", RESOURCES.getString("oingre.info.recibiOrden"));
                } else {
                    LOGGER.error("JobConfirmaIngresosReabastosUlises {}", RESOURCES.getString("oingre.err.recibiOrden"));
                }
            }
        } catch (Exception ex) {
            LOGGER.error("JobConfirmaIngresosReabastosUlises {}", ex.getMessage());
        }
    }

    /**
     * Llama la ejecución del Job
     */
    private void ejecutaJob() {
        LOGGER.trace("com.seidor.ulises.quartz.JobConfirmaIngresosReabastosUlises.ejecutaJob()");
        obtenerDatosSistema();
        boolean interfaseUlises = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_ULISES) == 1;
        if (!interfaseUlises) return;
        funcionesOrdenReabasto = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_ACTIVA_ELIMINAR_CODIGOMEDICAMENTO) == Constantes.ESTATUS_ACTIVO;
        try {
            List<String> folios = new ArrayList<>();
            List<ExpEntradas> listaExpEntradas = expEntradasService.obtenerExpEntradas();
            for (ExpEntradas expEntradas : listaExpEntradas)
            {
                if (expEntradas.getAlbaran() != null && !folios.contains(expEntradas.getAlbaran()))
                    folios.add(expEntradas.getAlbaran());
            }
            for (String folio : folios) {
                Reabasto reabastoSelect = reabastoService.getReabastoByFolio(folio);
                if (reabastoSelect != null) {
                    List<ExpEntradas> lineas = new ArrayList<>();
                    for (ExpEntradas expEntradas : listaExpEntradas) {
                        if (expEntradas.getAlbaran().equalsIgnoreCase(folio))
                            lineas.add(expEntradas);
                    }
                    if (reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.RECIBIDAPARCIAL.getValue()) || reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.RECIBIDA.getValue())) {
                        LOGGER.debug("Ingresando Reabasto con folio: {}", folio);                      
                        ingresarReabastoInsumo(reabastoSelect, lineas);
                    }
                    else {
                        for (ExpEntradas expEntradas : lineas) {
                            expEntradas.setTratado("S");
                            expEntradas.setFechaTratado(new Date());
                        }
                        if (reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.CANCELADA.getValue()) || reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.CANCELADA_VIGENCIA.getValue())) {
                            actualizaExpEntradas(lineas);
                            LOGGER.error("La Orden con folio: {} está cancelada", folio);
                        }
                        else if (reabastoSelect.getIdEstatusReabasto().equals(EstatusReabasto_Enum.INGRESADA.getValue())) {
                            actualizaExpEntradas(lineas);
                            LOGGER.error("La Orden con folio: {} ya fue ingresada", folio);
                        }
                        else
                            LOGGER.error("La Orden con folio: {} tiene un estatus no válido", folio);
                    }
                } else {
                    LOGGER.error("No se encontró la Orden con folio: {}", folio);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en JobConfirmaIngresosReabastosUlises.ejecutaJob(): {}", e.getMessage());
        }
    }
}
