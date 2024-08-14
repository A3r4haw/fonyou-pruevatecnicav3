package mx.mc.service;

import java.util.Collection;
import mx.mc.model.Usuario;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class SesionServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements SesionService {

    @Override
    public Collection<GrantedAuthority> cargarAutorizaciones()  throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (Collection<GrantedAuthority>) authentication.getAuthorities();
        } catch (Exception ex) {
            throw new Exception("Error obtener Datos de Usuario de Sesion. " + ex.getMessage());
        }
    }

    @Override
    public boolean permisoTransaccion(String transaccion) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(transaccion)) {
                    return true;
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error obtener Datos de Usuario de Sesion. " + ex.getMessage());
        }
        return false;
    }

    @Override
    public String obtenerNombreUsuario() throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                return authentication.getName();
            }
        } catch (Exception ex) {
            throw new Exception("Error al obtener Usuario en Sesion. " + ex.getMessage());
        }
        return null;
    }

}
