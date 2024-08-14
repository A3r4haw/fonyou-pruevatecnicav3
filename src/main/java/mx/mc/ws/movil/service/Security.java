package mx.mc.ws.movil.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.siap.client.WsSIAPClient;
import mx.mc.init.Constantes;
import mx.mc.init.CustomWebSecurityConfigurerAdapter;
import mx.mc.model.Config;
import mx.mc.model.TransaccionPermisos;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Implementa las interfaces REST para el control de acceso y seguridad
 *
 * @author Alberto Palacios
 * @version 1.0
 * @since 2018-12-10
 */
@Path("security")
public class Security extends SpringBeanAutowiringSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionMedicamentoMBMovil.class);
    protected static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private List<Config> configList;

   @Autowired
    private UsuarioService usuarioService;
   
   @Autowired
    private ConfigService configService;
   
   @Autowired
    private TransaccionService transaccionService;
   
   @PostConstruct
    public void init() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    /**
     * Crea una nueva instancia de la clase
     */
    public Security() {
        //No code needed in constructor
    }
    
    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.ws.movil.service.Security.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }
    
    /**
     * Equivalente del método RecepcionMedicamentoMB.consultaPermisosUsuario()
     * El usuario no se toma de la sesion, en su lugar se pasa como parámetro
     * 
     * @param usuario POJO del Usuario
     * @param id ID del Usuario
     * @return Lista de TransaccionPermisos
     */
    public List<TransaccionPermisos> consultaPermisosUsuario(Usuario usuario, String id) {
        List<TransaccionPermisos> permisos = null;
        try {         
            if (id == null || id.isEmpty()){
                permisos = transaccionService.obtenerPermisosPorIdUsuario(usuario.getIdUsuario());
                if (permisos != null && !permisos.isEmpty()) {
                    usuario.setPermisosList(permisos);
                }
            } else {
                permisos = transaccionService.obtenerPermisosPorIdUsuario(id);
                if (permisos != null && !permisos.isEmpty()) {
                    usuario.setPermisosList(permisos);
                }
            }
        } catch (Exception ex) {
          LOGGER.error("{}.consultaPermisosUsuario(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("ses.obtener.datos"), ex.getMessage());
        }
        return permisos;
    }
    
    /**
     * Valida las credenciales del usuario y regresa un Json con el POJO del usuario
     * 
     * @param jsonString Cadena JSON con las credenciales del usuario
     * @return Respuesta en formato JSON con el resultado boleano y los datos del usuario en caso de ser válido
     */
    @POST
    @Path("validarObtenerUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarObtenerUsuario(String jsonString) {
        Boolean resp = false;
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultado = mapper.createObjectNode();
        Usuario usuarioTemp = null;
        List<Usuario> users = null;
        
        obtenerDatosSistema();
        boolean validarSIAP = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_SIAP) == 1;
        String urlSIAP = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_SIAP_WS_URL);
        String delegacion = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DELEGACION);
        try {
            JsonNode params = mapper.readTree(jsonString);           
            String usuario = params.get("usuario").asText();
            String pass = params.get("pass").asText();
            usuarioTemp = usuarioService.obtenerNombreUsuarioCorreoElectronico(usuario);
            if (usuarioTemp != null) {
                if (CustomWebSecurityConfigurerAdapter.customPasswordEncoder().matches(pass, usuarioTemp.getClaveAcceso())) {
                    if (!usuarioTemp.isActivo()) {
                        resultado.put("estatus", "EXCEPCION");
                        resultado.put("mensaje", RESOURCES.getString("login.error.cuentaInactiva"));
                    } else if (usuarioTemp.isUsuarioBloqueado()) {
                        resultado.put("estatus", "EXCEPCION");
                        resultado.put("mensaje", RESOURCES.getString("login.error.cuentaBloqueada"));
                    } else if (validarSIAP && WsSIAPClient.checkEnrollment(usuarioTemp, urlSIAP, delegacion) > 0) {
                        resultado.put("estatus", "EXCEPCION");
                        resultado.put("mensaje", RESOURCES.getString("login.error.usuarioNoActivoSIAP"));
                    } else if (usuarioTemp.isActualizarClave()) {
                        resultado.put("estatus", "EXCEPCION");
                        resultado.put("mensaje", RESOURCES.getString("login.error.cuentaExpirada"));
                    } else {
                        List<TransaccionPermisos> permisosList = consultaPermisosUsuario(usuarioTemp, null);
                        usuarioTemp.setPermisosList(permisosList);
                        users = usuarioService.obtenerListaUsuarios(usuarioTemp.getIdEstructura());
                        for (int i = 0; i < users.size(); i++) {
                            List<TransaccionPermisos> permisosListUsuarios = consultaPermisosUsuario(users.get(i), users.get(i).getIdEstructura());
                            users.get(i).setPermisosList(permisosListUsuarios);
                        }
                        resp = true;
                    }
                } else {
                    resultado.put("estatus", "EXCEPCION");
                    resultado.put("mensaje", RESOURCES.getString("login.error.datosInvalidos"));
                }
                usuarioTemp.setClaveAcceso(null);
            }
            else {
                resultado.put("estatus", "EXCEPCION");
                resultado.put("mensaje", RESOURCES.getString("login.error.usuarioNoEncontrado"));
                LOGGER.error("{}.validarObtenerUsuario(): {}", this.getClass().getCanonicalName(), RESOURCES.getString("login.error.usuarioNoEncontrado"));
            }
            resultado.put("estatus", "OK");
        } catch (JsonParseException jpe) {
            resultado.put("estatus", "EXCEPCION");
            resultado.put("mensaje", RESOURCES.getString("login.error.caracteresInvalidos"));
            LOGGER.error("{}.validarObtenerUsuario(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("login.error.caracteresInvalidos"), jpe.getMessage());
        }
        catch (Exception ex) {
            if (ex instanceof AuthenticationCredentialsNotFoundException || ex instanceof BadCredentialsException
                    || ex instanceof InternalAuthenticationServiceException) {
                resultado.put("estatus", "OK");
                resultado.put("mensaje", ex.getMessage());
                LOGGER.error("{}.validarObtenerUsuario(): " + " - " , ex.getMessage());
            }
            else {
                resultado.put("estatus", "EXCEPCION");
                resultado.put("mensaje", RESOURCES.getString("ws.error.inesperado"));
                LOGGER.error("{}.validarObtenerUsuario(): {} {}", this.getClass().getCanonicalName(), RESOURCES.getString("ws.error.inesperado"), ex.getMessage());
            }            
        }
        resultado.put("valido", resp);
        if (usuarioTemp != null) {
            ObjectNode usuarioNode = mapper.valueToTree(usuarioTemp);
            resultado.set("usuario", usuarioNode);
        }
        if (users != null) {
            ArrayNode usuariosNode = mapper.valueToTree(users);
            resultado.set("usuarios", usuariosNode);
        }
        return Response.ok(resultado.toString()).build();
    }
}

