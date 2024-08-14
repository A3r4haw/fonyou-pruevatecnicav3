package mx.mc.init;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.mc.enums.NivelLog_Enum;
import mx.mc.model.Log;
import mx.mc.model.Usuario;
import mx.mc.service.LogService;
import mx.mc.service.UsuarioService;
import mx.mc.util.ClientInfo;
import mx.mc.util.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 *
 * @author hramirez
 */
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements Serializable, LogoutSuccessHandler {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);
    private static final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private transient LogService logService;

    @Autowired
    private transient UsuarioService usuarioService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LOGGER.trace("mx.mc.init.CustomLogoutSuccessHandler.onLogoutSuccess()");

        try {
            if (authentication != null) {
                String username = authentication.getName();
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                request.getSession(true);
                SecurityContextHolder.clearContext();

                String msg = "Cierra sesión";
                registroLog(username, request, msg);
            }
            
            StringBuilder uri = new StringBuilder();
            StringBuilder redirect = new StringBuilder();

            redirect.append(uri).append("/index.xhtml");
            redirectStrategy.sendRedirect(request, response, redirect.toString());
        } catch (IOException ex) {
            LOGGER.error("Error al cerrar sesión. {}", ex.getMessage());
        }
    }

    private void registroLog(String nombreUsuario, HttpServletRequest req, String msg) {
        LOGGER.trace("mx.mc.init.CustomLogoutSuccessHandler.registroLog()");

        try {
            Usuario u = usuarioService.obtenerNombreUsuarioCorreoElectronico(nombreUsuario);
            Log l = new Log();
            l.setIdEstructura(u.getPathEstructura());
            l.setIdTransaccion("Cierre de Sesión");
            l.setIdUsuario(u.getNombreUsuario());
            l.setFechaHora(new java.util.Date());
            l.setIp(ServerInfo.getIpAddress());
            l.setCliente(ClientInfo.getClientIpAddr(req));
            l.setSo(ClientInfo.getClientOS(req));
            l.setNivel(NivelLog_Enum.INFO.getValue());
            l.setTexto(msg);
            l.setBrowser(ClientInfo.getClientBrowser(req));
            l.setUserAgent(ClientInfo.getUserAgent(req));
            l.setUrl(ClientInfo.getFullURL(req));
            logService.insertar(l);

        } catch (Exception ex) {
            LOGGER.error("Error al registrar Log: {}", ex.getMessage());
        }
    }

}
