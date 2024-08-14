package com.seidor.ulises.quartz;

import com.seidor.ulises.model.ExpExpediciones;
import com.seidor.ulises.model.ExpLineasExpediciones;
import com.seidor.ulises.service.ExpExpedicionesService;
import com.seidor.ulises.service.ExpLineasExpedicionesService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import mx.mc.enums.EstatusSurtimiento_Enum;
import mx.mc.enums.TipoJustificacion_Enum;
import mx.mc.enums.TipoMotivo_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.MovimientoInventario;
import mx.mc.model.Prescripcion;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoEnviado;
import mx.mc.model.SurtimientoEnviado_Extend;
import mx.mc.model.SurtimientoInsumo;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.SurtimientoInsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import mx.mc.util.FechaUtil;
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
public class JobConfirmaPrescripcionesUlises extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobConfirmaPrescripcionesUlises.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private transient ExpExpedicionesService expExpedicionesService;
    
    @Autowired
    private transient ExpLineasExpedicionesService expLineasExpedicionesService;
    
    @Autowired
    private transient PrescripcionService prescripcionService;
    
    @Autowired
    private transient SurtimientoService surtimientoService;
    
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    
    @Autowired
    private transient InventarioService inventarioService;
    
    @Autowired
    private transient ConfigService configService;
    
    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    
    private Integer noDiasCaducidad;
    private List<Config> configList;
    
    /**
     * Obtiene la lista de Parámetros del sistema
     */
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.magedbean.SesionMB.obtenerDatosSistema()");
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
        LOGGER.trace("com.seidor.ulises.quartz.JobConfirmaPrescripcionesUlises.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        ejecutaJob();
    }

    private List<SurtimientoInsumo_Extend> obtenerListaSurtimientoInsumo(String idSurtimiento,String idEstructura) throws Exception {
        LOGGER.trace("com.seidor.ulises.quartz.JobConfirmaPrescripcionesUlises.obtenerListaSurtimientoInsumo()");
        LOGGER.debug("idSurtimiento: "+idSurtimiento+"idEstructura:"+idEstructura);
        List<Integer> listEstatusSurtimientoInsumo = new ArrayList<>();
        listEstatusSurtimientoInsumo.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        List<Integer> listEstatusSurtimiento = new ArrayList<>();
        listEstatusSurtimiento.add(EstatusSurtimiento_Enum.PROGRAMADO.getValue());
        //Pasar el idEstructura sirve para obtener el lote sugerido
        return surtimientoInsumoService.obtenerSurtimientosProgramados(new Date(), idSurtimiento, null, listEstatusSurtimientoInsumo, listEstatusSurtimiento, idEstructura, false);
    }
    
    private List<SurtimientoEnviado_Extend> generaListaSurtimientoEnviado(List<ExpLineasExpediciones> expLineasExpedicionesList, String idEstructura) throws Exception {
        LOGGER.debug("com.seidor.ulises.quartz.JobConfirmaPrescripcionesUlises.generaListaSurtimientoEnviado()");
        LOGGER.debug("idEstructura:"+idEstructura);
        List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList = new ArrayList<>();
        for (ExpLineasExpediciones expLineasExpediciones : expLineasExpedicionesList) {
            SurtimientoEnviado_Extend surtimientoEnviadoExtend = new SurtimientoEnviado_Extend();
            surtimientoEnviadoExtend.setIdSurtimientoEnviado(Comunes.getUUID());
            Medicamento insumo = medicamentoService.obtenerMedicaByClave(expLineasExpediciones.getArticulo());            
            Inventario inventario = new Inventario();
            inventario.setIdInsumo(insumo.getIdMedicamento());
            inventario.setIdEstructura(idEstructura);
            inventario.setLote(expLineasExpediciones.getLote());
            inventario = inventarioService.obtener(inventario);
            if (inventario != null) {
                surtimientoEnviadoExtend.setIdInventarioSurtido(inventario.getIdInventario());
                surtimientoEnviadoExtend.setCantidadEnviado(expLineasExpediciones.getCantidadConfirmada().intValue());
                surtimientoEnviadoExtend.setLote(inventario.getLote());
                surtimientoEnviadoExtend.setCaducidad(inventario.getFechaCaducidad());
                surtimientoEnviadoExtend.setIdInsumo(insumo.getIdMedicamento());
                surtimientoEnviadoExtendList.add(surtimientoEnviadoExtend);
                expLineasExpediciones.setTratado("S");
                expLineasExpediciones.setFechaTratado(new Date());
            }
        }
        return surtimientoEnviadoExtendList;
    }
    
    public boolean validaSurtimiento(Surtimiento surtimiento, List<SurtimientoInsumo_Extend> surtimientoInsumoExtendedList, Usuario usuario, String servicioSolicita, String almacenSurte) {
        LOGGER.trace("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento()");
        boolean estatus = false;
        try {
            Integer numeroMedicamentosSurtidos = 0;
            List<SurtimientoInsumo> surtimientoInsumoList = new ArrayList<>();
            List<SurtimientoEnviado> surtimientoEnviadoList = new ArrayList<>();
            Inventario inventarAfectado;
            List<Inventario> inventarioList = new ArrayList<>();
            List<MovimientoInventario> movimientoInventarioList = new ArrayList<>();
            MovimientoInventario movInventarioAfectado;
            for (SurtimientoInsumo_Extend surtimientoInsumo_Ext : surtimientoInsumoExtendedList) {
                if (surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList() != null && !surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList().isEmpty()) {
                    if (surtimientoInsumo_Ext.getCantidadEnviada().intValue() != surtimientoInsumo_Ext.getCantidadSolicitada().intValue() && surtimientoInsumo_Ext.getIdTipoJustificante() == null) {
                        LOGGER.error("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento() - " + RESOURCES.getString("dispensacion.err.surtmedicamento"));
                        return estatus;
                    }
                    Inventario inventarioSurtido;
                    for (SurtimientoEnviado_Extend surtimientoEnviadoExt : surtimientoInsumo_Ext.getSurtimientoEnviadoExtendList()) {
                        if (surtimientoEnviadoExt.getIdInventarioSurtido() != null) {
                            inventarioSurtido = inventarioService.obtener(new Inventario(surtimientoEnviadoExt.getIdInventarioSurtido()));
                            if (inventarioSurtido == null) {
                                LOGGER.error("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento() - " + RESOURCES.getString("sur.loteincorrecto"));
                                return estatus;
                            } else if (inventarioSurtido.getActivo() != 1) {
                                LOGGER.error("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento() - " + RESOURCES.getString("sur.lotebloqueado"));
                                return estatus;
                            } else if (FechaUtil.sumarRestarDiasFecha(new java.util.Date(), noDiasCaducidad).after(inventarioSurtido.getFechaCaducidad())) {
                                LOGGER.error("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento() - " + RESOURCES.getString("sur.caducidadvencida"));
                                return estatus;
                            // Se elimina esta validacion porque ya hay un job que actualiza el inventario 
//                            } else if (inventarioSurtido.getCantidadActual() < surtimientoEnviadoExt.getCantidadEnviado()) {
//                                LOGGER.error("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento() - " + RESOURCES.getString("sur.cantidadinsuficiente"));
//                                return estatus;
                            } else {
                                numeroMedicamentosSurtidos++;
                                inventarAfectado = new Inventario();
                                inventarAfectado.setIdInventario(surtimientoEnviadoExt.getIdInventarioSurtido());
                                inventarAfectado.setCantidadActual(surtimientoEnviadoExt.getCantidadEnviado());
                                inventarioList.add(inventarAfectado);

                                movInventarioAfectado = new MovimientoInventario();
                                movInventarioAfectado.setIdMovimientoInventario(Comunes.getUUID());
                                Integer idTipoMotivo = TipoMotivo_Enum.SAL_PRESC_POR_SURTIMIENTO_DE_PRESCRIPCION.getMotivoValue();
                                movInventarioAfectado.setIdTipoMotivo(idTipoMotivo);
                                movInventarioAfectado.setFecha(new java.util.Date());
                                movInventarioAfectado.setIdUsuarioMovimiento(usuario.getIdUsuario());
                                movInventarioAfectado.setIdEstrutcuraOrigen(almacenSurte);
                                movInventarioAfectado.setIdEstrutcuraDestino(servicioSolicita);
                                movInventarioAfectado.setIdInventario(surtimientoEnviadoExt.getIdInventarioSurtido());
                                movInventarioAfectado.setCantidad(surtimientoEnviadoExt.getCantidadEnviado());
                                movInventarioAfectado.setFolioDocumento(surtimiento.getFolio());
                                movimientoInventarioList.add(movInventarioAfectado);
                            }
                        }
                        surtimientoEnviadoExt.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                        surtimientoEnviadoExt.setInsertFecha(new java.util.Date());
                        surtimientoEnviadoExt.setInsertIdUsuario(usuario.getIdUsuario());
                        if (surtimientoEnviadoExt.getCantidadRecibido() == null) {
                            surtimientoEnviadoExt.setCantidadRecibido(0);
                        }
                        surtimientoEnviadoList.add((SurtimientoEnviado) surtimientoEnviadoExt);
                    }

                SurtimientoInsumo surtInsumo = new SurtimientoInsumo();
                surtInsumo.setIdSurtimientoInsumo(surtimientoInsumo_Ext.getIdSurtimientoInsumo());
                surtInsumo.setIdUsuarioAutCanRazn(surtimientoInsumo_Ext.getIdUsuarioAutCanRazn());
                surtInsumo.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                surtInsumo.setUpdateFecha(new java.util.Date());
                surtInsumo.setUpdateIdUsuario(usuario.getIdUsuario());
                surtInsumo.setFechaEnviada(surtimientoInsumo_Ext.getFechaEnviada());
                surtInsumo.setIdUsuarioEnviada(usuario.getIdUsuario());
                surtInsumo.setCantidadEnviada(surtimientoInsumo_Ext.getCantidadEnviada());
                surtInsumo.setCantidadVale(0);
                surtInsumo.setIdTipoJustificante(surtimientoInsumo_Ext.getIdTipoJustificante());
                surtInsumo.setNotas(surtimientoInsumo_Ext.getNotas());
                surtimientoInsumoList.add(surtInsumo);
                }
            }
            if (numeroMedicamentosSurtidos == 0) {
                LOGGER.error("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento() - " + RESOURCES.getString("sur.error"));
                return estatus;
            } else {
                surtimiento.setIdEstatusSurtimiento(EstatusSurtimiento_Enum.SURTIDO.getValue());
                surtimiento.setUpdateFecha(new java.util.Date());
                surtimiento.setUpdateIdUsuario(usuario.getIdUsuario());
                estatus = surtimientoService.surtirPrescripcion(surtimiento, surtimientoInsumoList, surtimientoEnviadoList, inventarioList, movimientoInventarioList);
                if (!estatus) {
                    LOGGER.error("mx.mc.magedbean.JobConfirmaPrescripcionesUlises.validaSurtimiento() - " + RESOURCES.getString("sur.error"));
                }
            }
        } catch (Exception ex) {
            LOGGER.error("Error en el metodo validaSurtimiento :: {}", ex.getMessage());
        }
        return estatus;
    }
    
    private SurtimientoInsumo_Extend buscaSurtimientoInsumoEnLista(List<SurtimientoInsumo_Extend> surtimientoInsumoList, String idInsumo) {
        SurtimientoInsumo_Extend found = null;
        for (SurtimientoInsumo_Extend surtimientoInsumo : surtimientoInsumoList) {
            if (surtimientoInsumo.getIdInsumo().equalsIgnoreCase(idInsumo)) {
                found = surtimientoInsumo;
                break;
            }
        }
        return found;
    }
    
    /**
     * Llama la ejecución del Job
     */
    private void ejecutaJob() {
        LOGGER.trace("com.seidor.ulises.quartz.JobConfirmaPrescripcionesUlises.ejecutaJob()");
        obtenerDatosSistema();
        boolean interfaseUlises = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_ULISES) == 1;
        if (!interfaseUlises) return;
        noDiasCaducidad = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_DIASPARACADUCIDAD);
        List<ExpExpediciones> listaExpExpediciones;
        try {
            Usuario usuario = usuarioService.obtenerNombreUsuarioCorreoElectronico("motion");
            if (usuario == null)
                usuario = usuarioService.obtenerNombreUsuarioCorreoElectronico("admin");
            listaExpExpediciones = expExpedicionesService.obtenerExpExpediciones();
            for (ExpExpediciones expExpediciones : listaExpExpediciones) {
                Surtimiento surtimiento = surtimientoService.obtenerPorFolio(expExpediciones.getExpedicion());
                LOGGER.debug("IdSurtimiento:"+surtimiento.getIdSurtimiento());
                List<ExpLineasExpediciones> expLineasExpedicionesList = expLineasExpedicionesService.obtenerExpLineasExpediciones(expExpediciones.getId());
                if(expLineasExpedicionesList.isEmpty()){
                    expLineasExpedicionesList = expLineasExpedicionesService.obtenerExpLineasExpedicion(expExpediciones.getExpedicion()); 
                    for(ExpLineasExpediciones linea : expLineasExpedicionesList){
                        Medicamento insumo = medicamentoService.obtenerMedicaByClave(linea.getArticulo());
                        List<Inventario> inventarioList = inventarioService.obtenerExistenciasPorIdEstructuraIdInsumo(surtimiento.getIdEstructuraAlmacen(),insumo.getIdMedicamento());
                        if(!inventarioList.isEmpty())
                            linea.setLote(inventarioList.get(0).getLote());
                    }
                }
                Prescripcion prescripcion = prescripcionService.obtenerByFolioPrescripcion(expExpediciones.getDocumento());
                List<SurtimientoInsumo_Extend> surtimientoInsumoList = obtenerListaSurtimientoInsumo(surtimiento.getIdSurtimiento(), surtimiento.getIdEstructuraAlmacen());
                LOGGER.debug("IdEstructuraAlmacen:"+surtimiento.getIdEstructuraAlmacen());
                List<SurtimientoEnviado_Extend> surtimientoEnviadoExtendList = generaListaSurtimientoEnviado(expLineasExpedicionesList, surtimiento.getIdEstructuraAlmacen());
                for (SurtimientoEnviado_Extend surtimientoEnviado : surtimientoEnviadoExtendList) {
                    SurtimientoInsumo_Extend surtimientoInsumo = buscaSurtimientoInsumoEnLista(surtimientoInsumoList, surtimientoEnviado.getIdInsumo());
                    if (surtimientoInsumo != null) {
                        surtimientoEnviado.setIdSurtimientoInsumo(surtimientoInsumo.getIdSurtimientoInsumo());
                        if (surtimientoInsumo.getSurtimientoEnviadoExtendList() == null)
                            surtimientoInsumo.setSurtimientoEnviadoExtendList(new ArrayList<>());
                        surtimientoInsumo.getSurtimientoEnviadoExtendList().add(surtimientoEnviado);
                        if (surtimientoInsumo.getCantidadEnviada() == null)
                            surtimientoInsumo.setCantidadEnviada(0);
                        surtimientoInsumo.setCantidadEnviada(surtimientoInsumo.getCantidadEnviada() + surtimientoEnviado.getCantidadEnviado());
                        if (Objects.equals(surtimientoInsumo.getCantidadSolicitada(), surtimientoInsumo.getCantidadEnviada())) {
                            surtimientoInsumo.setRequiereJustificante(Constantes.INACTIVO);
                            surtimientoInsumo.setIdTipoJustificante(null);
                        } else {
                            surtimientoInsumo.setRequiereJustificante(Constantes.ACTIVO);
                            surtimientoInsumo.setIdTipoJustificante(TipoJustificacion_Enum.INSUFICIENTE.getValue());
                        }
                    }
                }
                if (validaSurtimiento(surtimiento, surtimientoInsumoList, usuario, prescripcion.getIdEstructura(), surtimiento.getIdEstructuraAlmacen())) {
                    expExpediciones.setTratado("S");
                    expExpediciones.setFechaTratado(new Date());
                    expExpedicionesService.actualizarLista(listaExpExpediciones, expLineasExpedicionesList);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en JobConfirmaPrescripcionesUlises.ejecutaJob(): {}", e.getMessage());
        }
    }
}