package mx.mc.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import mx.com.dimesa.ws.client.model.RecetaSAFWSResultado;
import mx.mc.enums.EstatusDimesaRegistroConsumo_Enum;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.model.DimesaMedico;
import mx.mc.model.DimesaPaciente;
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
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author hramirez
 */
public class JobRecetaValeDimesa extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobRecetaConsumoDimesa.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private DimesaRecetaService dimesaRecetaService;
    @Autowired
    private DimesaRecetaMaterialService dimesaRecetaMaterialService;
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
        LOGGER.trace("mx.mc.quartz.JobRecetaValeDimesa.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        ejecutaJob();
    }

    private List<Config> obtenerConfigSistema() {
        LOGGER.debug("mx.mc.quartz.JobRecetaValeDimesa.obtenerConfigSistema()");

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
    private void ejecutaJob() {
        LOGGER.trace("mx.mc.quartz.JobRecetaValeDimesa.ejecutaJob()");

        List<Config> configList = obtenerConfigSistema();
        try {
                DimesaUsuario usuarioDimesa = new DimesaUsuario();
                String nombre = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_USUARIO);
                String contrasenia = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_CONTRASENIA);
                String url = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_URL);
                String hospital = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_FARMACIA_HOSPITAL);
                String clinica = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DIMESA_WS_FARMACIA_CLINICA);
                String farmacia;
                DimesaReceta dr;
                DimesaPaciente dp;
                DimesaMedico dm;

                List<DimesaReceta> recetasList;
                List<DimesaRecetaMaterial> materialList;

                recetasList = dimesaRecetaService.obtenerListaVales(new DimesaReceta());
                if (recetasList != null) {
                    for (DimesaReceta item : recetasList) {

                        if (item.getIdEstructuraHospitalaria().equals(Constantes.IDESTRUCTURA_CONSULTA_EXTERNA_CLINICA)) {
                            farmacia = clinica;
                        } else {
                            farmacia = hospital;
                        }

                        usuarioDimesa.setContrasenia(contrasenia);
                        usuarioDimesa.setFarmacia(farmacia);
                        usuarioDimesa.setNombre(nombre);
                        usuarioDimesa.setUrlWsdl(url);
                        surtimientoService.actualizarPorFolioVale(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.PROCESANDO.getValue());

                        dr = new DimesaReceta();
                        dr.setCama(item.getCama());
                        dr.setDescripcionServicio(item.getDescripcion());
                        dr.setEstatus(item.getEstatus());
                        dr.setFecha(item.getFecha());
                        dr.setFechaReferencia(item.getFechaReferencia());
                        dr.setFolio(item.getFolio());
                        dr.setFolioPago(item.getFolioPago());

                        materialList = dimesaRecetaMaterialService.obtenerListaVale(new DimesaRecetaMaterial(item.getFolio(), item.getFolioPago()));
                        Integer posiscion = 1;
                        if (materialList != null) {
                            List<DimesaRecetaMaterial> drmList = new ArrayList<>();
                            for (DimesaRecetaMaterial item2 : materialList) {
                                DimesaRecetaMaterial dimesaReceta = new DimesaRecetaMaterial();
                                dimesaReceta.setCantidadEntregada(BigDecimal.ZERO);
                                dimesaReceta.setCantidadSolicitada(item2.getCantidadVale());
                                dimesaReceta.setClave(item2.getClave());
                                dimesaReceta.setClaveInterna(item2.getClaveInterna());
                                dimesaReceta.setClaveSap(item2.getClaveSap());
                                dimesaReceta.setDescripcionSAP(item2.getDescripcionSAP());
                                dimesaReceta.setDosis(item2.getDosis());
                                dimesaReceta.setPosision(posiscion);
                                dimesaReceta.setTratamiento(item2.getTratamiento());
                                posiscion++;
                                drmList.add(dimesaReceta);
                            }

                            item.setMateriales(drmList);

                            dm = new DimesaMedico();
                            dm.setApellidoMaterno(item.getMedicoApellidoMaterno());
                            dm.setApellidoPaterno(item.getMedicoApellidoPaterno());
                            dm.setCedula(item.getMedicoCedula());
                            dm.setNombre(item.getMedicoNombre());

                            dp = new DimesaPaciente();
                            dp.setApellidoMaterno(item.getApellidoMaterno());
                            dp.setApellidoPaterno(item.getApellidoPaterno());
                            dp.setDescripcionPadecimiento(item.getPadecimiento());
                            dp.setDescripcionPrograma(item.getDescripcionPrograma());
                            dp.setDireccion("");
                            dp.setFechaNacimiento(new java.util.Date());
                            dp.setNombre(item.getNombre());
                            dp.setNumeroAfiliacion(item.getNumeroAfiliacion());
                            // TODO:    Se agrega un tipo de dato en Padecimiento
                            item.setPadecimiento(Constantes.DIMESA_ID_PADECIMIENTO_NO_DEFINIDO);
                            item.setPrograma(Constantes.DIMESA_DESCRIPCION_PROGRAMA);

                            dp.setSexo(item.getSexo());
                            dp.setTelefono(item.getTelefono());
                            dr.setPiso(item.getPiso());
                            dr.setServicio(item.getServicio());
                            dr.setTipo(item.getTipo());
                            
                            enviaReceta(usuarioDimesa, item);
                            boolean resp = surtimientoService.actualizarPorFolioVale(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.PROCESADO.getValue());

                            if(!resp) {
                                LOGGER.error("Error al enviar receta a DIMESA ");
                                surtimientoService.actualizarPorFolioVale(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.ERROR.getValue());
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

    /**
     * Método que envía el Colectivo al proveedor
     */
    public void enviaReceta(DimesaUsuario du, DimesaReceta dr) {
        LOGGER.trace("mx.mc.quartz.JobRecetaValeDimesa.enviaReceta()");

        try {
            mx.com.dimesa.ws.client.service.SAFWSService_Client wsc = new mx.com.dimesa.ws.client.service.SAFWSService_Client();
            RecetaSAFWSResultado resultado = wsc.createRecetaIssemym(du, dr);
            RespuestaConsumo respuestaConsumo = new RespuestaConsumo();
            respuestaConsumo.setIdRespuestaConsumo(dr.getFolio());

            if (resultado != null) {
                respuestaConsumo.setCodigo(resultado.getCodigo());
                respuestaConsumo.setMensaje(resultado.getMensaje().getValue());
                respuestaConsumo.setEstatus(1);

            } else {
                respuestaConsumo.setCodigo(9);
                respuestaConsumo.setMensaje("No se obtuvo respuesta por parte del webService");
                respuestaConsumo.setEstatus(0);

            }
            respuestaConsumoService.insertar(respuestaConsumo);
            LOGGER.info("Ejecución de Consumo");

        } catch (Exception ex) {
            LOGGER.error("Error al registrar Receta de Consumo: {}", ex.getMessage());
        }
        LOGGER.trace("Termina JobRecetaValeDimesa.enviaReceta.");
    }

}
