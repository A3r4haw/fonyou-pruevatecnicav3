package mx.mc.quartz;

import java.util.ArrayList;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import java.util.List;
import java.util.ResourceBundle;
import mx.com.dimesa.ws.client.model.RecetaSAFWSResultado;
import mx.mc.enums.EstatusDimesaRegistroConsumo_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.DimesaReceta;
import mx.mc.model.DimesaRecetaMaterial;
import mx.mc.model.DimesaUsuario;
import mx.mc.model.RespuestaConsumo;
import mx.mc.service.ConfigService;
import mx.mc.service.DimesaRecetaMaterialService;
import mx.mc.service.DimesaRecetaService;
import mx.mc.service.RespuestaConsumoService;
import mx.mc.service.SurtimientoService;
import mx.mc.util.Comunes;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author hramirez
 */
public class JobRecetaConsumoDimesa extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobRecetaConsumoDimesa.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private DimesaRecetaService dimesaRecetaService;
    @Autowired
    private DimesaRecetaMaterialService dimesaRecetaMaterialServ;
    @Autowired
    private RespuestaConsumoService respuestaConsumoService;
    @Autowired
    private ConfigService configService;

    /**
     * Clave de instanciación de Cron
     *
     * @param jec
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.trace("mx.mc.quartz.JobRecetaConsumoDimesa.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        //TODO 19/08/2020 incluir parametro de configuración para habilitar su ejecución
        ejecutaJobConsumo();

    }

    private List<Config> obtenerConfigSistema() {
        LOGGER.debug("mx.mc.quartz.JobRecetaConsumoDimesa.obtenerConfigSistema()");

        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            return configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
        return null;
    }

    /**
     * 1.- Consulta Los surtimientos PENDIENTES de las recetas realizados en la
     * Farmacia Externa por Unidad 2.- Genera un objeto de receta surtida con
     * cantidades solicitadas y cantidades entregadas 3.- Coloca el surtimiento
     * con estatus de PROCESANDO 4.- Lo envía al WS de DIMESA para el registro
     * de consumo mediante una Llamada al WS 5.- Si se procesa con éxito cambia
     * el estatus a PROCESADO 6.- Si se presenta un error deja el surtimiento de
     * receta como ERROR
     */
    private void ejecutaJobConsumo() {
        LOGGER.trace("mx.mc.quartz.JobRecetaConsumoDimesa.ejecutaJob()");

        List<Config> configList = obtenerConfigSistema();
        if (configList != null && !configList.isEmpty()) {
            try {
                DimesaUsuario dUsuario = new DimesaUsuario();
                String nombre = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_USUARIO);
                String contrasenia = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_CONTRASENIA);
                String url = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_URL);
                String hospital = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_FARMACIA_HOSPITAL);
                String clinica = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_FARMACIA_CLINICA);
                String farmacia;
                DimesaRecetaMaterial drm;

                List<DimesaReceta> recetasList;
                List<DimesaRecetaMaterial> materialList;

                recetasList = dimesaRecetaService.obtenerLista(new DimesaReceta());

                if (recetasList != null) {
                    for (DimesaReceta item : recetasList) {

                        if (item.getIdEstructuraHospitalaria().equals(Constantes.IDESTRUCTURA_CONSULTA_EXTERNA_CLINICA)) {
                            farmacia = clinica;
                        } else {
                            farmacia = hospital;
                        }

                        dUsuario.setContrasenia(contrasenia);
                        dUsuario.setFarmacia(farmacia);
                        dUsuario.setNombre(nombre);
                        dUsuario.setUrlWsdl(url);

                        surtimientoService.actualizarPorFolio(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.PROCESANDO.getValue());

                        materialList = dimesaRecetaMaterialServ.obtenerLista(new DimesaRecetaMaterial(item.getFolio(), item.getFolioPago()));
                        Integer posiscion = 1;
                        if (materialList != null) {
                            List<DimesaRecetaMaterial> drmList = new ArrayList<>();
                            for (DimesaRecetaMaterial item2 : materialList) {
                                drm = new DimesaRecetaMaterial();
                                drm.setCantidadEntregada(item2.getCantidadEntregada().divide(item2.getCantidadXCaja()));
                                drm.setCantidadSolicitada(item2.getCantidadEntregada().divide(item2.getCantidadXCaja()));
                                drm.setClave(item2.getClave());
                                drm.setClaveInterna(item2.getClaveInterna());
                                drm.setClaveSap(item2.getClaveSap());
                                drm.setDescripcionSAP(item2.getDescripcionSAP());
                                drm.setDosis(item2.getDosis());
                                drm.setPosision(posiscion);
                                drm.setTratamiento(item2.getTratamiento());
                                posiscion++;
                                drmList.add(drm);
                            }

                            item.setMateriales(drmList);

// TODO:    Se agrega un tipo de dato en Padecimiento
                            item.setPadecimiento(Constantes.DIMESA_ID_PADECIMIENTO_NO_DEFINIDO);
                            item.setPrograma(Constantes.DIMESA_DESCRIPCION_PROGRAMA);

                            enviaRecetaConsumo(dUsuario, item);
                            boolean resp = surtimientoService.actualizarPorFolio(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.PROCESADO.getValue());

                            if(!resp) {
                                LOGGER.error("Error al enviar receta a DIMESA " );
                                surtimientoService.actualizarPorFolio(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.ERROR.getValue());
                            }

                        } else {
                            LOGGER.error("No hay recetas con materiales pendientes de enviar DIMESA ");
                        }
                    }
                } else {
                    LOGGER.error("No hay recetas pendientes de enviar DIMESA ");
                }
            } catch (Exception e) {
                LOGGER.error("Error al buscar Prescripciones: {}", e.getMessage());
            }
        }
    }

    /**
     * Método que envía el Colectivo al proveedor
     */
    public void enviaRecetaConsumo(DimesaUsuario du, DimesaReceta dr) {
        LOGGER.trace("mx.mc.quartz.JobRecetaConsumoDimesa.enviaRecetaConsumo()");

        try {
            mx.com.dimesa.ws.client.service.SAFWSService_Client wsc = new mx.com.dimesa.ws.client.service.SAFWSService_Client();
            RecetaSAFWSResultado result = wsc.createRecetaIssemym(du, dr);
            RespuestaConsumo respuestaDimesa = new RespuestaConsumo();
            respuestaDimesa.setIdRespuestaConsumo(dr.getFolio());

            if (result != null) {
                respuestaDimesa.setCodigo(result.getCodigo());
                respuestaDimesa.setMensaje(result.getMensaje().getValue());
                respuestaDimesa.setEstatus(1);

            } else {
                respuestaDimesa.setCodigo(9);
                respuestaDimesa.setMensaje("No se obtuvo respuesta por parte del webService");
                respuestaDimesa.setEstatus(0);

            }
            respuestaConsumoService.insertar(respuestaDimesa);
            LOGGER.info("Ejecución de Consumo");

        } catch (Exception ex) {
            LOGGER.error("Error al registrar Receta de Consumo: {}", ex.getMessage());
        }
        LOGGER.trace("Termina JobRecetaConsumoDimesa.enviaRecetaConsumo.");
    }

}
