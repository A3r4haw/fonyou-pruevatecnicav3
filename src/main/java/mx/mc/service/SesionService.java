package mx.mc.service;

import java.util.Collection;
import mx.mc.model.Usuario;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author hramirez
 */
public interface SesionService extends GenericCrudService<Usuario, String> {

    Collection<GrantedAuthority> cargarAutorizaciones() throws Exception;
    
    boolean permisoTransaccion(String transaccion) throws Exception;
    
    String obtenerNombreUsuario() throws Exception;

}
