/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.quartz;

import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import mx.com.dimesa.ws.client.model.RecetaSAFWSResultado;
import mx.mc.enums.EstatusDimesaRegistroConsumo_Enum;
import mx.mc.init.Constantes;
import mx.mc.magedbean.SesionMB;
import mx.mc.model.DimesaReceta;
import mx.mc.model.DimesaRecetaMaterial;
import mx.mc.model.DimesaUsuario;
import mx.mc.model.RespuestaConsumo;
import mx.mc.service.DimesaRecetaMaterialService;
import mx.mc.service.DimesaRecetaService;
import mx.mc.service.RespuestaConsumoService;
import mx.mc.service.SurtimientoService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author bbautista
 */
public class JobCancelRecetaDimesa extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCancelRecetaDimesa.class);

    @Autowired
    private SurtimientoService surtimientoService;

    @Autowired
    private DimesaRecetaService dimesaRecetaService;
    @Autowired
    private DimesaRecetaMaterialService dimesaRecetaMaterialService;
    
    @Autowired
    private RespuestaConsumoService respuestaConsumoService;

    
    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.trace("mx.mc.quartz.JobColectivo.executeInternal()");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    private void  ejecutaJob(){
        LOGGER.trace("mx.mc.quartz.JobCancelRecetaDimesa.ejecutaJob()");
        try{
            DimesaUsuario du = new DimesaUsuario();
            
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            SesionMB sesion = (SesionMB) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "sesionMB");
            
            String contrasenia = sesion.getDimesaClave();
            String nombre = sesion.getDimesaUsuario();
            String url = sesion.getDimesaUrl();
            
            String farmacia = sesion.getDimesaFarmaciaHospital();
            DimesaRecetaMaterial drmCancel;

            List<DimesaReceta> recetasList;
            List<DimesaRecetaMaterial> materialList;

            recetasList = dimesaRecetaService.obtenerListaCancelados();

            if (recetasList != null) {
                for(DimesaReceta item: recetasList){
                    if (item.getIdEstructuraHospitalaria() .equals(Constantes.IDESTRUCTURA_CONSULTA_EXTERNA_CLINICA)) {
                        farmacia = sesion.getDimesaFarmaciaClinica();
                    }
                    
                    du.setContrasenia(contrasenia);
                    du.setFarmacia(farmacia);
                    du.setNombre(nombre);
                    du.setUrlWsdl(url);
                    
                    surtimientoService.actualizarPorFolio(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.PROCESANDO.getValue());
                    
                    materialList = dimesaRecetaMaterialService.obtenerLista(new DimesaRecetaMaterial(item.getFolio(), item.getFolioPago()));
                    Integer posiscion = 1;
                    if (materialList != null) {
                        List<DimesaRecetaMaterial> drmList = new ArrayList<>();
                        for (DimesaRecetaMaterial item2 : materialList) {
                            drmCancel = new DimesaRecetaMaterial();
                            drmCancel.setCantidadEntregada(item2.getCantidadEntregada());
                            drmCancel.setCantidadSolicitada(item2.getCantidadEntregada());
                            drmCancel.setClave(item2.getClave());
                            drmCancel.setClaveInterna(item2.getClaveInterna());
                            drmCancel.setClaveSap(item2.getClaveSap());
                            drmCancel.setDescripcionSAP(item2.getDescripcionSAP());
                            drmCancel.setDosis(item2.getDosis());
                            drmCancel.setPosision(posiscion);
                            drmCancel.setTratamiento(item2.getTratamiento());
                            posiscion++;
                            drmList.add(drmCancel);
                        }

                        item.setMateriales(drmList);
                        item.setPadecimiento("999");
                        item.setPrograma("ISMYM");
                        
                        enviaReceta(du, item);
                        boolean resp = surtimientoService.actualizarPorFolio(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.PROCESADO.getValue());
                        
                        if(!resp) {
                            LOGGER.error("Error al enviar receta a DIMESA ");
                            surtimientoService.actualizarPorFolio(item.getFolioPago(), EstatusDimesaRegistroConsumo_Enum.ERROR.getValue());
                        }

                    } else {
                        LOGGER.error("No hay recetas con materiales pendientes de enviar DIMESA ");

                    }                
                }
            }else
                LOGGER.error("No hay recetas canceladas pendientes de enviar DIMESA ");
        }catch(Exception ex){
            LOGGER.error("Error en método ejecutaJob: {}", ex.getMessage());
        }
    }
    
    /**
     * Método que envía el Colectivo al proveedor
     */
    public void enviaReceta(DimesaUsuario du, DimesaReceta dr) {
        LOGGER.trace("mx.mc.quartz.JobCancelRecetaDimesa.enviaReceta()");

        try {
            mx.com.dimesa.ws.client.service.SAFWSService_Client wsc = new mx.com.dimesa.ws.client.service.SAFWSService_Client();
            RecetaSAFWSResultado result = wsc.cancelReceta(du, dr);
            RespuestaConsumo unaRespuestaConsumo = new RespuestaConsumo();
            unaRespuestaConsumo.setIdRespuestaConsumo(dr.getFolio());
            
            if(result != null) {
                unaRespuestaConsumo.setCodigo(result.getCodigo());
                unaRespuestaConsumo.setMensaje(result.getMensaje().getValue());
                unaRespuestaConsumo.setEstatus(1);
                
            } else {
                unaRespuestaConsumo.setCodigo(9);
                unaRespuestaConsumo.setMensaje("No se obtuvo respuesta por parte del webService");
                unaRespuestaConsumo.setEstatus(0);
                
            }                                    
            respuestaConsumoService.insertar(unaRespuestaConsumo);
            LOGGER.info("Ejecución de Consumo");

        } catch (Exception ex) {
            LOGGER.error("Error al registrar Receta de Consumo: {}", ex.getMessage());
        }
        LOGGER.trace("Termina WS cancelReceta.");
    }
}
