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
public class JobColectivoDimesa extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobColectivoDimesa.class);


    /**
     * Clave de instanciación de Cron
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.trace("mx.mc.quartz.JobColectivoDimesa.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ejecutaJob();
    }

    /**
     * Llama la ejecución del WS
     */
    private void ejecutaJob() {
        LOGGER.trace("mx.mc.quartz.JobColectivoDimesa.ejecuta()");
        try {
            enviaColectivo();
        } catch (Exception e) {
            LOGGER.error("Error al buscar Prescripciones: {}", e.getMessage());
        }
    }

    /**
     * Método que envía el Colectivo al proveedor
     */
    public void enviaColectivo() {
        LOGGER.trace("mx.mc.quartz.JobColectivoDimesa.enviaColectivo()");
        try {
            LOGGER.info("Ejecución de Colectivo");
        } catch (Exception ex) {
            LOGGER.error("Error al ejecutar el colectivo: {}", ex.getMessage());
        }
        LOGGER.trace("Termina enviaColectivo.");
    }

}
