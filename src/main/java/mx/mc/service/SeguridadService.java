package mx.mc.service;

import java.util.List;
import mx.mc.model.Rol;
import mx.mc.model.Usuario;

/**
 *
 * @author hramirez
 */
public interface SeguridadService extends GenericCrudService<Usuario, String> {

    Usuario obtenerNombreUsuarioCorreoElectronico(String cadena) throws Exception;
    
    boolean registrarUsuario(Usuario u , List<Rol> l) throws Exception;
        

}
