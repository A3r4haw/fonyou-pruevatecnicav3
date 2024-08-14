package mx.mc.init;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import mx.gob.imss.siap.client.WsSIAPClient;
import mx.mc.model.Config;
import mx.mc.model.Rol;
import mx.mc.model.Transaccion;
import mx.mc.model.Usuario;
import mx.mc.service.ConfigService;
import mx.mc.service.RolService;
import mx.mc.service.TransaccionService;
import mx.mc.service.UsuarioService;
import mx.mc.util.Comunes;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException; //siap
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements Serializable, UserDetailsService {

    private static final long serialVersionUID = 1;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    private List<Config> configList;

    @Autowired
    private transient UsuarioService usuarioService;

    @Autowired
    private transient TransaccionService transaccionService;

    @Autowired
    private transient ConfigService configService;

    @Autowired
    private transient RolService rolService;

    private void obtenerDatosSistema() {
        LOGGER.debug("mx.mc.init.CustomUserDetailsService.obtenerDatosSistema()");
        Config c = new Config();
        c.setActiva(Constantes.ACTIVO);
        try {
            configList = configService.obtenerLista(c);
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }
    
    private Integer obtenerMaxNumIntentosAccesoFallidos (String value){
        Integer maxNumIntentAccesoFallido = null;
        if (StringUtils.isNumeric(value)) {
            try {
                maxNumIntentAccesoFallido = Integer.parseInt(value);
            } catch (NumberFormatException ex){
                LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
            }
        }
        return maxNumIntentAccesoFallido;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LOGGER.trace("mx.mc.init.CustomUserDetailsService.loadUserByUsername()");
        LOGGER.trace("Usuario: {}", username);
     
        if (username == null || username.trim().equals("") ) {
            throw new AuthenticationCredentialsNotFoundException(RESOURCES.getString("ini.ses.err.nombreUsuarioVacio"));

        } else {
            Usuario u = null;
            try {
                u = obtenerNombreUsuarioCorreoElectronico(username);

            } catch (Exception ex) {
                String msg = RESOURCES.getString("ini.ses.err.conultaUsuario");
                LOGGER.error(msg + " - " + username + " - " + ex.getCause());
                throw new InternalAuthenticationServiceException(msg);
            }

            if (u == null) {
                String msg = RESOURCES.getString("ini.ses.err.usuarioNoEncontrado");
                LOGGER.error(msg + " - " + username);
                throw new AuthenticationCredentialsNotFoundException(msg);

            } else if (!u.isActivo()) {
                String msg = RESOURCES.getString("ini.ses.err.usuarioInactivo");
                LOGGER.error(msg + " - " + username);
                throw new DisabledException(msg);

            } else if (u.isUsuarioBloqueado()) {
                String msg = RESOURCES.getString("ini.ses.err.usuarioBloqueado");
                LOGGER.error(msg + " - " + username);
                throw new LockedException(msg);
            
            } else if (u.getFechaVigencia().before(new java.util.Date())) {
                String msg = RESOURCES.getString("ini.ses.err.usuarioContrasenaExpirada");
                LOGGER.error(msg + " - " + username);
                throw new CredentialsExpiredException(msg);

            } else {
                obtenerDatosSistema();
                String tmpNumIntAccFall = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_MAXIMO_NUMERO_INTENTOS_ACCESO_FALLIDOS);
                Integer maxNumIntentAccesoFallido = (tmpNumIntAccFall.equals("") ) ? null : obtenerMaxNumIntentosAccesoFallidos(tmpNumIntAccFall);
                
                if (maxNumIntentAccesoFallido != null && u.getNumErrorAcceso() >= maxNumIntentAccesoFallido) {
                    String msg = RESOURCES.getString("ini.ses.err.usuarioNoIntentosAccesoFallidos");
                    LOGGER.error(msg + " - " + username);
                    throw new InsufficientAuthenticationException(msg);
                    
                } else {
                    
                    boolean validarSIAP = Comunes.obtenValorConfiguracionInt(configList, Constantes.PARAM_SYSTEM_FUNC_SIAP) == 1;
                    String urlSIAP = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_SIAP_WS_URL);
                    String delegacion = Comunes.obtenValorConfiguracion(configList, Constantes.PARAM_SYSTEM_DELEGACION);

                    int res = 0;
                    if (validarSIAP) {
                        res = WsSIAPClient.checkEnrollment(u, urlSIAP, delegacion);
                    }
                    if (res > 0) {
                        if (res == 1) {
                            LOGGER.error(RESOURCES.getString("login.error.usuarioNoActivoSIAP"), " - ", username);
                            throw new AuthenticationServiceException(RESOURCES.getString("login.error.usuarioNoActivoSIAP"));
                        } else {
                            LOGGER.error(RESOURCES.getString("login.error.usuarioNoRegistradoSIAP"), " - ", username);
                            throw new AuthenticationServiceException(RESOURCES.getString("login.error.usuarioNoRegistradoSIAP"));
                        }

                    } else {
                        try {
                            Rol rol = rolService.getRolByIdUsuario(u.getIdUsuario());
                            if (rol == null){
                                String msg = RESOURCES.getString("ini.ses.err.usuarioRolConsulta");
                                throw new InsufficientAuthenticationException (msg);
                                
                            } else if (rol.getIdRol() == null){
                                String msg = RESOURCES.getString("ini.ses.err.usuarioRolConsulta");
                                throw new InsufficientAuthenticationException(msg);
                                
                            } else if (!rol.isActivo()) {
                                String msg = RESOURCES.getString("ini.ses.err.usuarioRolInactivo");
                                LOGGER.error(msg + " - " + username);
                                throw new AccountExpiredException(msg);
                                
                            } else {
                                Collection<GrantedAuthority> authorities = obtenerTransacciones(u.getIdUsuario());
                                return new User(u.getNombreUsuario(), u.getClaveAcceso(), authorities);
                            }
                        } catch (Exception ex) {
                            String msg = RESOURCES.getString("ini.ses.err.usuarioRolConsulta");
                            LOGGER.error(msg + " - " + username + " - " + ex.getMessage());
                            throw new AccountExpiredException(msg);
                        }
                    }
                }
            }
        }
    }

    private Usuario obtenerNombreUsuarioCorreoElectronico(String username) throws Exception {
        LOGGER.trace("mx.mc.init.CustomUserDetailsService.obtenerNombreUsuarioCorreoElectronico()");
        try {
            return usuarioService.obtenerNombreUsuarioCorreoElectronico(username);
        } catch (Exception ex) {
            String msg = RESOURCES.getString("ini.ses.err.conultaUsuario");
            LOGGER.error(msg + " - " + username + " - " + ex.getCause());
            throw new InternalAuthenticationServiceException(msg);
        }
    }

    private Collection<GrantedAuthority> obtenerTransacciones(String idUsuario) throws Exception {
        LOGGER.trace("mx.mc.init.CustomUserDetailsService.obtenerTransacciones()");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("NONE"));

        try {
            List<Transaccion> transaccionList = transaccionService.obtenerPorIdUsuario(idUsuario);
            if (transaccionList != null) {
                for (Transaccion item : transaccionList) {
                    if (item.isActivo()) {
                        authorities.add(new SimpleGrantedAuthority(item.getCodigo()));
                    }
                }
            }
        } catch (Exception ex) {
            String msg = RESOURCES.getString("ini.ses.err.usuarioRolConsulta");
            LOGGER.error(msg + " - " + ex.getCause());
            throw new InternalAuthenticationServiceException(msg);
        }
        return authorities;
    }

}
