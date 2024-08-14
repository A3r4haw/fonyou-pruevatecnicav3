/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.siam.client;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import mx.gob.issste.ws.model.RecetaSiamNO;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mcalderon
 */
public class RecetaSiamCL{
    
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RecetaSiamCL.class);
    
    public static RecetaSiamNO getRecetaSiam(String folio, String claveCt, String urlSiamFolioReceta) {
        RecetaSiamNO recetaS = null;        
        try {
            Client cliente = ClientBuilder.newClient();
            WebTarget webTarget = cliente.target(urlSiamFolioReceta).path(claveCt).path(folio);
            Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() == Status.OK.getStatusCode()) {
                recetaS = response.readEntity(RecetaSiamNO.class);
            }
        } catch (Exception e) {
            LOGGER.error("Error al Consultar RecetaSIAM " + e.getMessage());
        }
        return recetaS;
    }


    
}
