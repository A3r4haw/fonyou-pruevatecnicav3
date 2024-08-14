package mx.mc.init;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.mc.enums.NivelLog_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.Config;
import mx.mc.model.Log;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.LogService;
import mx.mc.service.UsuarioService;
import mx.mc.util.ClientInfo;
import mx.mc.util.Comunes;
import mx.mc.util.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private transient LogService logService;

    @Autowired
    private transient UsuarioService usuarioService;
    
    @Autowired
    private transient ConfigService configService;
    
    private int sessionTimeout;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication a) throws IOException, ServletException {
        LOGGER.trace("mx.mc.init.CustomAuthenticationSuccessHandler.onAuthenticationSuccess()");
        try {
            consultaParametrosConfiguracion();
            req.getSession().setMaxInactiveInterval(sessionTimeout*Constantes.HTTP_SESSION_TIMEOUT_60_SEGUNDOS);
            LOGGER.info("sessionTimeout: {} minutos.", sessionTimeout*Constantes.HTTP_SESSION_TIMEOUT_60_SEGUNDOS);
        
            req.getSession().removeAttribute("error");
            setDefaultTargetUrl("/secure/inicio.xhtml");
            registroLog(a.getName(), req);
            limpiaIntento(a.getName());
        } catch (Exception ex) {
            LOGGER.error("Error al iniciar seción. {}", ex.getMessage());
        }
        super.onAuthenticationSuccess(req, res, a);
    }

    private void consultaParametrosConfiguracion () {
        LOGGER.trace("mx.mc.init.CustomAuthenticationSuccessHandler.consultaParametrosConfiguracion()");
        sessionTimeout = Constantes.SESION_LIMITE_MINUTOS;
        List<Config> configList;
        try {
            Config config = new Config();
            config.setNombre(Constantes.PARAM_SYSTEM_SESSION_TIMEOUT);
            config.setActiva(Constantes.ACTIVO);
            configList = new ArrayList<>();
            configList.addAll(configService.obtenerLista(config));
            if (!configList.isEmpty()) {
                sessionTimeout = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_SESSION_TIMEOUT);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener parametros del sistema. {}", ex.getMessage());
        }
    }
    
    private void registroLog(String nombreUsuario, HttpServletRequest req) {
        LOGGER.trace("mx.mc.init.CustomAuthenticationSuccessHandler.registroLog()");
        try {       
            Usuario u = usuarioService.obtenerNombreUsuarioCorreoElectronico(nombreUsuario);

            Log l = new Log();
            l.setIdEstructura(u.getPathEstructura());
            l.setIdTransaccion(Transaccion_Enum.LOGIN.getSufijo());
            l.setIdUsuario(u.getNombreUsuario());
            l.setFechaHora(new java.util.Date());
            l.setIp(ServerInfo.getIpAddress());
            l.setCliente(ClientInfo.getClientIpAddr(req));
            l.setSo(ClientInfo.getClientOS(req));
            l.setNivel(NivelLog_Enum.INFO.getValue());
            l.setTexto(Transaccion_Enum.LOGIN.getValue());
            l.setBrowser(ClientInfo.getClientBrowser(req));
            l.setUserAgent(ClientInfo.getUserAgent(req));
            l.setUrl(ClientInfo.getFullURL(req));
//            logService.insertar(l);

        } catch (Exception ex) {
            LOGGER.error("Error al registrar Log: {}", ex.getMessage());
        }
    }
    
    /**
     * Limpia el valor del número de intento fallido de inicio de sesión
     * @param u 
     */
    private void limpiaIntento(String nombreUsuario){
        LOGGER.trace("mx.mc.init.CustomAuthenticationFailureHandler.limpiaIntento()");
        try {
            Usuario usuario = usuarioService.obtenerNombreUsuarioCorreoElectronico(nombreUsuario);
            if (usuario!= null) {
                Usuario u = new Usuario();
                u.setIdUsuario(usuario.getIdUsuario());
                u.setActivo(usuario.isActivo());
                u.setUsuarioBloqueado(usuario.isUsuarioBloqueado());
                u.setUpdateFecha(new java.util.Date());
                u.setUpdateIdUsuario(usuario.getIdUsuario());
                u.setUltimoIngreso(new java.util.Date());
                u.setNumErrorAcceso(0);
                boolean resp = usuarioService.actualizar(u);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al registrar Intento fallido de inicio de sesión: {}", ex.getMessage());
        }   
    }

}
