/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.ws.movil.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import mx.mc.init.Constantes;
import mx.mc.model.AplicationMovil;
import mx.mc.service.AppMovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Ulai
 */
@Path("update")
public class UpdateAplication extends SpringBeanAutowiringSupport {
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private AplicationMovil appMovil = new AplicationMovil();
    private final ObjectMapper appMapper = new ObjectMapper();
    
    @Autowired
    private AppMovilService appMovilService;
    
    @Context ServletContext servletContext;
    
    @POST
    @Path("downloadUpdate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response updateDownload(String filtrosJson)  {
        try {
            appMovil = appMovilService.obtenerAPK();
        } catch (Exception ex) {
            Logger.getLogger(UpdateAplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        String path = servletContext.getRealPath("/");
        String name = appMovil.getNombre().replaceAll(" ", "_");
        String version = appMovil.getVersionName().replaceAll(" ", "_");
        String pathDefinition = path + Constantes.PATH_APP + name +"_"+ version + "_vc" + appMovil.getVersionCode().toString();
        File file = new File(pathDefinition);
        ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment; filename=unidosis.apk");
        response.header("Content-Length", file.length()).header("Connection","keep-alive");
        return response.build();
    }
    
    @POST
    @Path("checkVersion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces( MediaType.APPLICATION_JSON)
    public Response checkversion(String filtrosJson) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        JsonNode app = null;
       
        try {
            appMovil = appMovilService.obtenerAPK();
            app = appMapper.valueToTree(appMovil);
        } catch (Exception ex) {
            Logger.getLogger(UpdateAplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(appMovil != null && appMovil.getVersionName() != null && !appMovil.getVersionName().equals("")){
            resultado.put("estatus", "OK");
            resultado.set("dataApp", app);            
        }
        return Response.ok(resultado.toString()).build();
    }
}
