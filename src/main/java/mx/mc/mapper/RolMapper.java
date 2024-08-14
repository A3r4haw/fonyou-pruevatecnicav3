package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Rol;
import mx.mc.model.TransaccionAccion;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface RolMapper extends GenericCrudMapper<Rol, String> {

    List<Rol> obtenerPorIdUsuario(@Param("idUsuario") String idUsuario);
    
    boolean insertarTransaccionAccion(TransaccionAccion transaccionAccion);
    
    boolean eliminarTransaccionAccionPorIdRol(@Param("idRol") String idRol);
    
    Rol getRolByIdUsuario(@Param("idUsuario") String idUsuario);
    
    String getRolByName(@Param("nombre") String nombre);

}
