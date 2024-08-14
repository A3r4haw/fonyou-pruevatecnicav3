package mx.mc.service;

import java.util.List;
import mx.mc.model.Rol;
import mx.mc.model.TransaccionPermisos;

/**
 *
 * @author hramirez
 */
public interface RolService extends GenericCrudService<Rol, String> {

    List<Rol> obtenerPorIdUsuario(String idUsuario) throws Exception;

    
    boolean registraRolyPermisos(Rol r, List<TransaccionPermisos> listaPermisos, String tipoRegistro) throws Exception;
    
    boolean eliminaRolyPermisos(Rol r) throws Exception;
    
    Rol getRolByIdUsuario(String idUsuario) throws Exception;
    
    String getRolByName(String nombre) throws Exception;
    
}
