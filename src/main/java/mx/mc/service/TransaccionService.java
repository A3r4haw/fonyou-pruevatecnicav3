package mx.mc.service;

import java.util.List;
import mx.mc.model.Transaccion;
import mx.mc.model.TransaccionPermisos;

/**
 *
 * @author hramirez
 */
public interface TransaccionService extends GenericCrudService<Transaccion, String> {

    List<Transaccion> obtenerPorIdUsuario(String idUsuario) throws Exception;
    List<TransaccionPermisos> obtenerPermisosPorIdUsuario(String idUsuario) throws Exception;
    List<TransaccionPermisos> obtenerPermisosDisponibles(String idUsuario, String idRol) throws Exception;
    List<TransaccionPermisos> obtenerPermisosAsignadosyDisponibles(String idUsuario, String idRol) throws Exception;    
    List<TransaccionPermisos> obtenerTraccionesAll() throws Exception;
    List<TransaccionPermisos> obtenerPermisosAsignadosyDisponiblesRol(String idRol) throws Exception;    
    List<TransaccionPermisos> permisosUsuarioTransaccion(String idUsuario, String code)throws Exception;
}
