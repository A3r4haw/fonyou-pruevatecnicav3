package mx.mc.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author hramirez
 */
public class JobTransferenciaDimesa extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobTransferenciaDimesa.class);

    /**
     * Clave de instanciación de Cron
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.trace("mx.mc.quartz.JobTransferenciaDimesa.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        ejecutaJobTransferencia();
    }

    /**
     * Llama la ejecución del WS
     */
    private void ejecutaJobTransferencia() {
        LOGGER.trace("mx.mc.quartz.JobTransferenciaDimesa.ejecutaJob()");
        try {
            enviaColectivoTrans();
        } catch (Exception e) {
            LOGGER.error("Error al buscar Prescripciones: {}", e.getMessage());
        }
    }

    /**
     * Método que envía el Colectivo al proveedor
     */
    public void enviaColectivoTrans() {
        LOGGER.trace("mx.mc.quartz.JobTransferenciaDimesa.enviaColectivo()");
        try {
            LOGGER.info("Ejecución de Transferencia");
        } catch (Exception ex) {
            LOGGER.error("Error al ejecutar el colectivo: {}", ex.getMessage());
        }
        LOGGER.trace("Termina enviaColectivo.");
    }
}
