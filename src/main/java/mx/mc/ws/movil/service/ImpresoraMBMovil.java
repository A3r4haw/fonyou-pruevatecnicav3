package mx.mc.ws.movil.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.mc.enums.TipoImpresora_Enum;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.Impresora;
import mx.mc.model.Usuario;
import mx.mc.service.ImpresoraService;
import mx.mc.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Implementa las interfaces REST para la Impresión de Prescripciones
 *
 * @author Alberto Palacios
 * @version 1.0
 * @since 2018-12-11
 */
@Path("impresora")
public class ImpresoraMBMovil extends SpringBeanAutowiringSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImpresoraMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ImpresoraService impresoraService;
   
   @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    /**
     * Crea una nueva instancia de la clase
     */
    public ImpresoraMBMovil() {
        //No code needed in constructor
    }
    
    /**
      * Obtiene la lista de Impresoras asignadas al Usuario
     * 
     * @param filtrosJson Cadena JSON con las credenciales del usuario
     * @return Respuesta en formato JSON con la lista de Impresoras
     * @throws java.io.IOException
     */
    @POST
    @Path("obtenerImpresoras")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerImpresoras(String filtrosJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resImpr = mapper.createObjectNode();
        JsonNode params = mapper.readTree(filtrosJson);
        if (!params.hasNonNull("usuario") || !params.hasNonNull("pass")) {
            resImpr.put("estatus", "ERROR");
            resImpr.put("mensaje", RESOURCES.getString("webservice.err.invalidRequest"));
            return Response.ok(resImpr.toString()).build();
        }
        Usuario usuarioParam = new Usuario();
        Usuario usuarioSelected = null;
        String usuario = params.get("usuario").asText();
        String pass = params.get("pass").asText();
        usuarioParam.setNombreUsuario(usuario);
        try {
            usuarioSelected = this.usuarioService.verificaSiExisteUser(usuarioParam);            
        } catch (Exception ex) {
            resImpr.put("estatus", "EXCEPCION");
            resImpr.put("mensaje", ex.getMessage());
            LOGGER.error("{}.obtenerSurtimientos(): {}", this.getClass().getCanonicalName(), ex.getMessage());
        }
        if (usuarioSelected != null) {
            if (!CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioSelected.getClaveAcceso())) {
                resImpr.put("estatus", "ERROR");
                resImpr.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
            }
            else {
                try {
                    List<Impresora> listaImpresoras = impresoraService.obtenerListaImpresoraByUsuario(usuarioSelected.getIdUsuario());
                    listaImpresoras = listaImpresoras.stream()
                            .filter(i -> i.getTipo().compareTo(TipoImpresora_Enum.NORMAL.getValue()) == 0).collect(Collectors.toList());
                    resImpr.put("estatus", "OK");
                    resImpr.put("totalRegistros", listaImpresoras.size());
                    ArrayNode registrosNode = mapper.valueToTree(listaImpresoras);
                    resImpr.set("registros", registrosNode);
                } catch (Exception ex) {
                    resImpr.put("estatus", "EXCEPCION");
                    resImpr.put("mensaje", ex.getMessage());
                    LOGGER.error("{}.obtenerImpresoras(): {}", this.getClass().getCanonicalName(), ex.getMessage());
                }
            }
        }
        else {
            resImpr.put("estatus", "ERROR");
            resImpr.put("mensaje", RESOURCES.getString("usuario.err.usuInvalido"));
        }
        return Response.ok(resImpr.toString()).build();
    }
}