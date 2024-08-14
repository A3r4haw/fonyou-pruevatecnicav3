package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Transaccion;
import mx.mc.model.TransaccionPermisos;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface TransaccionMapper extends GenericCrudMapper<Transaccion, String> {

    List<Transaccion> obtenerPorIdUsuario(@Param("idUsuario") String idUsuario);
    
    List<TransaccionPermisos> obtenerPermisosPorIdUsuario(@Param("idUsuario") String idUsuario);
    
    List<TransaccionPermisos> obtenerPermisosDisponibles(@Param("idUsuario") String idUsuario, @Param("idRol") String idRol);
    
    List<TransaccionPermisos> obtenerPermisosAsignadosyDisponibles(@Param("idUsuario") String idUsuario, @Param("idRol") String idRol);
    
    List<TransaccionPermisos> obtenerTraccionesAll();
    
    List<TransaccionPermisos> obtenerPermisosAsignadosyDisponiblesRol(@Param("idRol") String idRol);
    
    List<TransaccionPermisos> permisosUsuarioTransaccion(@Param("idUsuario")String idUsuario,@Param("codeTrans")String codeTrans) ;
    
    String buscarTotal(@Param("idUsuario")String idUsuario,@Param("codeTrans")String codeTrans);
}
