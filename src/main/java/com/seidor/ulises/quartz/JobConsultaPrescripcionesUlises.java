package com.seidor.ulises.quartz;

import com.seidor.ulises.model.ImpExpediciones;
import com.seidor.ulises.model.ImpLineasExpediciones;
import com.seidor.ulises.service.ImpExpedicionesService;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.Medicamento_Extended;
import mx.mc.model.Surtimiento_Extend;
import mx.mc.service.ConfigService;
import mx.mc.service.PrescripcionService;
import mx.mc.service.SurtimientoInsumoService;
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
 * @author apalacios
 */
public class JobConsultaPrescripcionesUlises extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobConsultaPrescripcionesUlises.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private transient PrescripcionService prescripcionService;
    
    @Autowired
    private transient SurtimientoInsumoService surtimientoInsumoService;
    
    @Autowired
    private transient ImpExpedicionesService impExpedicionesService;
    
    @Autowired
    private transient ConfigService configService;
    
    private List<Config> configList;
    
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
        LOGGER.trace("com.seidor.ulises.quartz.JobConsultaPrescripcionesUlises.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        ejecutaJob();
    }

    /**
     * Llama la ejecución del Job
     */
    private void ejecutaJob() {
        LOGGER.trace("com.seidor.ulises.quartz.JobConsultaPrescripcionesUlises.ejecutaJob()");
        obtenerDatosSistema();
        boolean interfaseUlises = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_ULISES) == 1;
        if (!interfaseUlises) return;
        try {
            List<Surtimiento_Extend> surtimientos = new ArrayList<>();
//            List<Surtimiento_Extend> surtimientos = prescripcionService.obtenerListaSurtimientosUlises();
            for (Surtimiento_Extend surtimiento : surtimientos) {
                List<Medicamento_Extended> insumos = new ArrayList<>();
//                List<Medicamento_Extended> insumos = prescripcionService.obtenerListaInsumosUlises(surtimiento.getFolio());
                ImpExpediciones impExpediciones = new ImpExpediciones(surtimiento, com.seidor.ulises.init.Constantes.TIPO_EXPEDICION_UNIDOSIS, com.seidor.ulises.init.Constantes.TIPO_ALMACEN_UNIDOSIS);
                List<ImpLineasExpediciones> listExpediciones = new ArrayList<>();
                int numMedicamento = 1;
                for (Medicamento_Extended medicamento : insumos) {
                    ImpLineasExpediciones impLineasExpediciones = new ImpLineasExpediciones(surtimiento, medicamento, numMedicamento++);
                    listExpediciones.add(impLineasExpediciones);
                }
                if (impExpedicionesService.insertarExpediciones(impExpediciones, listExpediciones)) {
                    for (Medicamento_Extended medicamento : insumos) {
//                        surtimientoInsumoService.actualizarEnviadoUlises(medicamento.getIdSurtimientoInsumo(), 1);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en JobConsultaPrescripcionesUlises.ejecutaJob(): {}", e.getMessage());
        }
    }
}
