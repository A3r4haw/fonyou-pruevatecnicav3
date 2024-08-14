package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TransaccionMapper;
import mx.mc.model.Transaccion;
import mx.mc.model.TransaccionPermisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class TransaccionServiceImpl extends GenericCrudServiceImpl<Transaccion, String> implements TransaccionService {
    
    @Autowired
    private TransaccionMapper transaccionMapper;

    @Autowired
    public TransaccionServiceImpl(GenericCrudMapper<Transaccion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Transaccion> obtenerPorIdUsuario(String idUsuario) throws Exception {
        try {
            return transaccionMapper.obtenerPorIdUsuario(idUsuario);
        } catch (Exception ex) {    
            throw new Exception("Error obtener Transacción. " + ex.getMessage());
        }
    }
    
    @Override
    public List<TransaccionPermisos> obtenerPermisosPorIdUsuario(String idUsuario) throws Exception {
        try {
            return transaccionMapper.obtenerPermisosPorIdUsuario(idUsuario);
        } catch (Exception ex) {    
            throw new Exception("Error obtener Permisos de Transaccines de Usuario. " + ex.getMessage());
        }
    }
    
    @Override
    public List<TransaccionPermisos> obtenerPermisosDisponibles(String idUsuario, String idRol) throws Exception {
        try {
            return transaccionMapper.obtenerPermisosDisponibles(idUsuario, idRol);
        } catch (Exception ex) {    
            throw new Exception("Error obtener Permisos de Transaccines Disponibles. " + ex.getMessage());
        }
    }
    
    @Override
    public List<TransaccionPermisos> obtenerPermisosAsignadosyDisponibles(String idUsuario, String idRol) throws Exception {
        try {
            return transaccionMapper.obtenerPermisosAsignadosyDisponibles(idUsuario, idRol);
        } catch (Exception ex) {    
            throw new Exception("Error obtenerPermisosAsignadosyDisponibles. " + ex.getMessage());
        }
    }
    @Override
    public List<TransaccionPermisos> obtenerTraccionesAll() throws Exception {
        try {
            return transaccionMapper.obtenerTraccionesAll();
        } catch (Exception ex) {    
            throw new Exception("Error obtenerTraccionesAll. " + ex.getMessage());
        }
    }
    
    @Override
    public List<TransaccionPermisos> obtenerPermisosAsignadosyDisponiblesRol(String idRol) throws Exception {
        try {
            return transaccionMapper.obtenerPermisosAsignadosyDisponiblesRol(idRol);
        } catch (Exception ex) {    
            throw new Exception("Error obtenerPermisosAsignadosyDisponiblesRol. " + ex.getMessage());
        }
    }
    
    
    @Override
    public List<TransaccionPermisos> permisosUsuarioTransaccion(String idUsuario, String code) throws Exception {
        try{
            return transaccionMapper.permisosUsuarioTransaccion(idUsuario, code);
        }catch(Exception ex){
            throw new Exception("Error al obtener las acciones de la transaccion: " + ex.getMessage());
        }
    }
}
