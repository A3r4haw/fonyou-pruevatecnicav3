package mx.mc.service;

import java.util.List;
import mx.mc.init.Constantes;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.RolMapper;
import mx.mc.model.Rol;
import mx.mc.model.TransaccionAccion;
import mx.mc.model.TransaccionPermisos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class RolServiceImpl extends GenericCrudServiceImpl<Rol, String> implements RolService {
    
    @Autowired
    private RolMapper rolMapper;

    @Autowired
    public RolServiceImpl(GenericCrudMapper<Rol, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Rol> obtenerPorIdUsuario(String idUsuario) throws Exception {
        try {
            return rolMapper.obtenerPorIdUsuario(idUsuario);
        } catch (Exception ex) {
            throw new Exception("Error listar roles. " + ex.getMessage());
        }
    }
    
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registraRolyPermisos(Rol r, List<TransaccionPermisos> listaPermisos, String tipoRegistro) throws Exception {
        
        boolean res = false;
        try {        
            if (tipoRegistro.equals(Constantes.INSERT)) {
                res = rolMapper.insertar(r);
            } else {
                res = rolMapper.actualizar(r);
                if (res) {
                    rolMapper.eliminarTransaccionAccionPorIdRol(r.getIdRol());
                }
            }
        } catch (Exception ex) {
            throw new Exception("Error al registrar registraRolyPermisos. " + ex.getMessage());
        }
        
        if (res){
            TransaccionAccion transaccionAccion;
            for (TransaccionPermisos item : listaPermisos) {
                if (item.isPermitido()) {
                    transaccionAccion = new TransaccionAccion();
                    transaccionAccion.setIdRol(r.getIdRol());
                    transaccionAccion.setIdTransaccion(item.getIdTransaccion());
                    transaccionAccion.setIdAccion(item.getIdAccion());
                    transaccionAccion.setInsertFecha(r.getInsertFecha());
                    transaccionAccion.setInsertIdUsuario(r.getInsertIdUsuario());
                    try {
                        res = rolMapper.insertarTransaccionAccion(transaccionAccion);
                    } catch (Exception ex1) {
                        throw new Exception("Error al registrar insertarTransaccionAccion " + ex1.getMessage());
                    }
                }
            }
        }
        return res;
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean eliminaRolyPermisos(Rol r) throws Exception {
        
        boolean res = false;
        try {
            rolMapper.eliminarTransaccionAccionPorIdRol(r.getIdRol());
        } catch (Exception ex1) {
            throw new Exception("Error al eliminar permisos del Rol. " + ex1.getMessage());
        }
        try {
            res = rolMapper.eliminar(r);
        } catch (Exception ex1) {
            throw new Exception("Error al eliminar Rol. " + ex1.getMessage());
        }
        return res;
    }

    @Override
    public Rol getRolByIdUsuario(String idUsuario) throws Exception {
        try {
            return rolMapper.getRolByIdUsuario(idUsuario);
        } catch (Exception ex) {
            throw new Exception("Error listar roles. " + ex.getMessage());
        }
    }
    
    @Override
    public String getRolByName(String nombre) throws Exception {
        try {
            return rolMapper.getRolByName(nombre);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el Nombre del Rol. " + ex.getMessage());
        }
    }
    
    
}
