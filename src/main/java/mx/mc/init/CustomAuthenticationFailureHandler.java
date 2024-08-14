package mx.mc.init;

import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.mc.enums.NivelLog_Enum;
import mx.mc.enums.Transaccion_Enum;
import mx.mc.model.Log;
import mx.mc.model.Usuario;
import mx.mc.service.LogService;
import mx.mc.service.UsuarioService;
import mx.mc.util.ClientInfo;
import mx.mc.util.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 *
 *
 */
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);

    @Autowired
    private transient LogService logService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException ae) throws IOException, ServletException {
        LOGGER.trace("mx.mc.init.CustomAuthenticationFailureHandler.onAuthenticationFailure()");
        
        String msg;
        String username = req.getParameter("j_username");
        LOGGER.debug("Error al iniciar sesión: {}: {}", username, ae.getMessage());

        
        if (ae instanceof AuthenticationServiceException) {
            msg = ae.getMessage();
            
        } else if (ae.getCause() instanceof InternalAuthenticationServiceException) {
            msg = ae.getMessage();
        
        } else if (ae instanceof DisabledException) {
            msg = RESOURCES.getString("ini.ses.err.usuarioInactivo");
        
        } else if (ae instanceof LockedException) {
            msg = RESOURCES.getString("ini.ses.err.usuarioBloqueado");
        
        } else if (ae instanceof CredentialsExpiredException) {
            msg = RESOURCES.getString("ini.ses.err.usuarioContrasenaExpirada");
        
        } else if (ae instanceof AccountExpiredException) {
            msg = RESOURCES.getString("ini.ses.err.usuarioNoIntentosAccesoFallidos");
        
        } else if (ae instanceof BadCredentialsException) {
            msg = RESOURCES.getString("ini.ses.err.credencialesIncorrectas");
            
        } else if (ae instanceof AuthenticationCredentialsNotFoundException) {
            msg = ae.getMessage();
        
        } else if (ae instanceof InsufficientAuthenticationException) {
            msg = ae.getMessage();
        } else if (ae instanceof UsernameNotFoundException) {
            msg = ae.getMessage();
            
        } else if (ae.getMessage().equals("Clave de acceso incorrecta.")
                || ae.getMessage().equals("Error al validar Clave de acceso.") ) {
            msg = ae.getMessage();
        } else {
            msg = "Error Desconocido.";
        }

        registroLog(username, req, msg);
        registraIntento(username);
        req.getSession().setAttribute("error", msg);
        super.setDefaultFailureUrl("/index.xhtml");
        super.onAuthenticationFailure(req, resp, ae);

    }

    private void registroLog(String nombreUsuario, HttpServletRequest req, String msg) {
        LOGGER.trace("mx.mc.init.CustomAuthenticationFailureHandler.registroLog()");
        try {
            Usuario u = usuarioService.obtenerNombreUsuarioCorreoElectronico(nombreUsuario);
            Log l = new Log();
            l.setIdUsuario(nombreUsuario);
            l.setFechaHora(new java.util.Date());
            l.setIdTransaccion(Transaccion_Enum.LOGIN.getSufijo());
            l.setIp(ServerInfo.getIpAddress());
            l.setCliente(ClientInfo.getClientIpAddr(req));
            l.setSo(ClientInfo.getClientOS(req));
            l.setNivel(NivelLog_Enum.ERROR.getValue());
            l.setTexto(msg);
            l.setBrowser(ClientInfo.getClientBrowser(req));
            l.setUserAgent(ClientInfo.getUserAgent(req));
            l.setUrl(ClientInfo.getFullURL(req));            
            l.setIdEstructura( (u != null) ? u.getPathEstructura() : "NO DEFINIDO");
            
            logService.insertar(l);
            
        } catch (Exception ex) {
            LOGGER.error("Error al registrar Log: {}", ex.getMessage());
        }
    }
    
    /**
     * Reistra el intneto fallido de inicio de sesión
     * @param u 
     */
    private void registraIntento(String nombreUsuario){
        LOGGER.trace("mx.mc.init.CustomAuthenticationFailureHandler.registraIntento()");
        try {
            Usuario usuario = usuarioService.obtenerNombreUsuarioCorreoElectronico(nombreUsuario);
            if (usuario!= null) {
                Usuario u = new Usuario();
                u.setIdUsuario(usuario.getIdUsuario());
                u.setActivo(usuario.isActivo());
                u.setUsuarioBloqueado(usuario.isUsuarioBloqueado());
                u.setUpdateFecha(new java.util.Date());
                u.setUpdateIdUsuario(usuario.getIdUsuario());
                u.setNumErrorAcceso(usuario.getNumErrorAcceso() + 1);
                boolean resp = usuarioService.actualizar(u);
            }
        } catch (Exception ex) {
            LOGGER.error("Error al registrar Intento fallido de inicio de sesión: {}", ex.getMessage());
        }   
    }

}
