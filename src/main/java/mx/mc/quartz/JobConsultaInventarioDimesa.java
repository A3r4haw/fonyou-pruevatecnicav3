package mx.mc.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import java.util.List;
import mx.mc.model.DimesaMedico;
import mx.mc.model.DimesaPaciente;
import mx.mc.model.DimesaReceta;
import mx.mc.model.DimesaRecetaMaterial;
import mx.mc.model.DimesaUsuario;

/**
 *
 * @author hramirez
 */
public class JobConsultaInventarioDimesa extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobConsultaInventarioDimesa.class);

    /**
     * Clave de instanciación de Cron
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.trace("mx.mc.quartz.JobColectivo.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        ejecutaJob();
    }

    /**
     * Llama la ejecución del WS
     */
    private void ejecutaJob() {
        LOGGER.trace("mx.mc.quartz.JobColectivo.ejecuta()");
        try {
            DimesaUsuario du = new DimesaUsuario();
            du.setContrasenia("");
            du.setFarmacia("");
            du.setNombre("");
            du.setUrlWsdl("");

            DimesaReceta dr;
            DimesaPaciente dp;
            DimesaMedico dm;
            DimesaRecetaMaterial drm;
            List<DimesaRecetaMaterial> drmList = new ArrayList<>();

            dr = new DimesaReceta();
            dr.setCama("");
            dr.setDescripcionServicio("");
            dr.setEstatus("");
            dr.setFecha(new java.util.Date());
            dr.setFechaReferencia(new java.util.Date());
            dr.setFolio("3");
            dr.setFolioPago("");

            drm = new DimesaRecetaMaterial();
            drm.setCantidadEntregada(BigDecimal.ONE);
            drm.setCantidadSolicitada(BigDecimal.ONE);
            drm.setClave("");
            drm.setClaveInterna("");
            drm.setClaveSap("");
            drm.setDescripcionSAP("");
            drm.setDosis("");
            drm.setPosision(1);
            drm.setTratamiento("");

            drmList.add(drm);

            dr.setMateriales(drmList);

            dm = new DimesaMedico();
            dm.setApellidoMaterno("");
            dm.setApellidoPaterno("");
            dm.setCedula("");
            dm.setNombre("");

            dp = new DimesaPaciente();
            dp.setApellidoMaterno("");
            dp.setApellidoPaterno("");
            dp.setDescripcionPadecimiento("");
            dp.setDescripcionPrograma("");
            dp.setDireccion("");
            dp.setFechaNacimiento(new java.util.Date());
            dp.setNombre("");
            dp.setNumeroAfiliacion("");
            dp.setPadecimiento("");
            dp.setPrograma("");
            dp.setSexo("");
            dp.setTelefono("");

            dr.setPiso("");
            dr.setServicio("");
            dr.setTipo("");

            enviaColectivo(du, dr);
        } catch (Exception e) {
            LOGGER.error("Error al buscar Prescripciones: {}", e.getMessage());
        }
    }

    /**
     * Método que envía el Colectivo al proveedor
     */
    public void enviaColectivo(DimesaUsuario du, DimesaReceta dr) {
        LOGGER.trace("mx.mc.quartz.JobColectivo.enviaColectivo()");

        try {
            mx.com.dimesa.ws.client.service.SAFWSService_Client wsc = new mx.com.dimesa.ws.client.service.SAFWSService_Client();
            wsc.createColectivoIssemym(du, dr);
            LOGGER.info("Ejecución de Consulta de Inventario");

        } catch (Exception ex) {
            LOGGER.error("Error al ejecutar el colectivo: {}", ex.getMessage());
        }
        LOGGER.trace("Termina enviaColectivo.");
    }

}
