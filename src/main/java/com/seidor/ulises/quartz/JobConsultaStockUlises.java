/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.quartz;

import com.seidor.ulises.model.ExpStocks;
import com.seidor.ulises.service.ExpStocksService;
import java.util.List;
import java.util.ResourceBundle;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.Medicamento;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.EstructuraService;
import mx.mc.service.InventarioService;
import mx.mc.service.MedicamentoService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author bernabe
 */
public class JobConsultaStockUlises extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobConsultaStockUlises.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    
    private List<Config> configList;
    private Estructura almacenVertical;
    
    @Autowired
    private transient EstructuraService estructuraService;
    
    @Autowired
    private transient ConfigService configService;
    
    @Autowired
    private transient InventarioService inventarioService;
    
    @Autowired
    private transient MedicamentoService medicamentoService;
    
    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired
    private transient ExpStocksService expStocksService;
    
    
    
    /**
     * Clave de instanciación de Cron
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.trace("com.seidor.ulises.quartz.JobConsultaStockUlises.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        ejecutaJob();
    }
    
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
    
    private void ejecutaJob(){    
        LOGGER.trace("com.seidor.ulises.quartz.JobConsultaStockUlises.ejecutaJob()");        
        obtenerDatosSistema();        
        boolean interfaseUlises = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_ULISES) == 1;
        if (!interfaseUlises) return;
        List<ExpStocks> stockUlisesList;
        List<Estructura> almacenesList;
        try{            
            Usuario usuario = usuarioService.obtenerNombreUsuarioCorreoElectronico("motion");
            if (usuario == null)
                usuario = usuarioService.obtenerNombreUsuarioCorreoElectronico("admin");
            
            almacenesList = estructuraService.obtenerAlmacenesActivos();
            almacenVertical = almacenesList.stream().filter(p -> p.getEnvioHL7() == true).findFirst().orElse(null);            
            stockUlisesList = expStocksService.inventarioUlises();
            for(ExpStocks item : stockUlisesList){
                Medicamento insumo = medicamentoService.obtenerMedicaByClave(item.getArticulo());
                if(insumo!= null){
                    Inventario inventarioItem = inventarioService.obtenerInventariosPorInsumoEstructuraYLote(insumo.getIdMedicamento(), almacenVertical.getIdEstructura(),item.getLote());
                    if(inventarioItem!= null && inventarioItem.getCantidadActual() != (int)item.getCantidad()){                        
                        inventarioItem.setCantidadActual((int)item.getCantidad());                        
                        inventarioService.actualizarInventarioCantidadActual(inventarioItem,null,null);
                        LOGGER.info("Se actualizo de forma correcta el inventario: {}",item.getArticulo());
                    }
                }else
                    LOGGER.error("El articulo no se encontro en MUS: {}",item.getArticulo());
            }
                        
        }catch(Exception ex){
        
        }                     
    }
}
