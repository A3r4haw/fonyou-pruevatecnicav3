/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Entity;
import mx.gob.issste.ws.model.ResponseColectivoSIAM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import mx.gob.issste.ws.model.SendColectivoSIAM;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import mx.gob.issste.ws.model.ColectivoSurtidoDetalle;
import mx.gob.issste.ws.model.InsumoCTSiam;
import mx.gob.issste.ws.model.InsumoSIAM;
import mx.gob.issste.ws.model.ResponseDetalleSIAM;
import mx.mc.init.Constantes;
import mx.mc.util.Mensaje;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ColectivoSIAMClient implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ColectivoSIAMClient.class);
    
    public ColectivoSIAMClient() {
    }

    public ResponseColectivoSIAM generaColectivo(SendColectivoSIAM colectivo,String uri) {
        ResponseColectivoSIAM responseColectivo = null;
        try {            
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(uri);                         
            Response response = target.request(MediaType.APPLICATION_JSON)                   
                    .post(Entity.json(colectivo));
            LOGGER.info("Json {}",Entity.json(colectivo));
            LOGGER.info("Status {}", response.getStatus());
            Integer status = response.getStatus();
            if (Status.OK.getStatusCode() == status) {
                responseColectivo = response.readEntity(ResponseColectivoSIAM.class);
                if(responseColectivo.getMensaje().toUpperCase().contains("ERROR")){
                    responseColectivo.setGenerado(0);
                }
            }else{
                ResponseColectivoSIAM auxColectivo = response.readEntity(ResponseColectivoSIAM.class);
                responseColectivo = new ResponseColectivoSIAM();
                responseColectivo.setNuevoFolio("");
                responseColectivo.setInsumos(new ArrayList<>());
                responseColectivo.setGenerado(0);
                responseColectivo.setMensaje(auxColectivo.getMensaje());
            }
        } catch (Exception ex) {
            
            LOGGER.error("Ocurrio un error al conectarse con SIAM", ex);
        }

        return responseColectivo;
    }

    public ResponseDetalleSIAM consultaColectivoSIAM(ColectivoSurtidoDetalle detalle ,String uri){
        ResponseDetalleSIAM responseSIAM =null;
        try{
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(uri);
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(detalle));
            
            LOGGER.info("Status {}", response.getStatus());
            Integer status = response.getStatus();
            if (Status.OK.getStatusCode() == status) {
                responseSIAM = response.readEntity(ResponseDetalleSIAM.class);
            }else{
                responseSIAM = new ResponseDetalleSIAM();
                responseSIAM.setRespuestaCodigo(status);
                responseSIAM.setRespuestaMensaje(""+response.getStatusInfo());
                responseSIAM.setDatosExtra(null);
            }
        }catch(Exception ex){
            LOGGER.error("Ocurrio un error al conectarse con SIAM", ex);
        }
        return responseSIAM;
    }
    
    public List<InsumoCTSiam> consultaListaExistensiaSIAM(String uri){
        List<InsumoCTSiam> insumosList = new ArrayList<>();
        try {            
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode send = mapper.createObjectNode();
            send.put("claveCT", "027-204-00");
            send.put("tipo", 0);
            send.put("claveInsumo", "");
            send.put("descripcion", "");
                        
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(uri);
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(send.toString()));
            if(Status.OK.getStatusCode()==response.getStatus()){
                String request = response.readEntity(String.class);
                mapper = new ObjectMapper();
                JsonNode params = mapper.readTree(request);
                
                insumosList = mapper.readValue(params.toString(), new TypeReference<List<InsumoCTSiam>>(){});
            }
            
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al conectarse con SIAM", e);
        }
        
        return insumosList;
    }
    
    public InsumoCTSiam consultaExistensiaInsumoSIAM(String uri,String claveInsumo){
        InsumoCTSiam insumoSiam = null;
        try {            
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode send = mapper.createObjectNode();
            send.put("claveCT", "027-204-00");
            send.put("tipo", 0);
            send.put("claveInsumo",claveInsumo);
            send.put("descripcion", "");
                        
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(uri);
            Response response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(send.toString()));
            if(Status.OK.getStatusCode()==response.getStatus()){                       
                String request = response.readEntity(String.class);
                mapper = new ObjectMapper();
                JsonNode params = mapper.readTree(request);
                ArrayNode items = (ArrayNode)params;
                for(JsonNode item : items ){
                    insumoSiam = new InsumoCTSiam();
                    insumoSiam.setCveInsumo(item.get("cveInsumo").asText());
                    insumoSiam.setExistencia(item.get("existencia").asInt());
                    insumoSiam.setExistenciaApartados(item.get("existencia_apartados").asInt());
                    break;
                }                 
            }
            
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al conectarse con SIAM", e);
        }
        
        return insumoSiam;
    }
}

