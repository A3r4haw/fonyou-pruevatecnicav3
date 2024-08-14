package mx.mc.service;

import java.util.List;
import mx.mc.mapper.UsuarioMapper;
import mx.mc.model.Rol;
import mx.mc.model.Usuario;
import mx.mc.model.UsuarioRol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.UsuariosRolesMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author hramirez
 */
@Service
public class SeguridadServiceImpl extends GenericCrudServiceImpl<Usuario, String> implements SeguridadService {

    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private UsuariosRolesMapper usuarioRolMapper;

    @Override
    public Usuario obtenerNombreUsuarioCorreoElectronico(String cadena) throws Exception {
        try {
            return usuarioMapper.obtenerNombreUsuarioCorreoElectronico(cadena);
        } catch (Exception ex) {
            throw new Exception("Error obtener usuario. " + ex.getMessage());
        }
    }

    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean registrarUsuario(Usuario usuario , List<Rol> rolList) throws Exception {
        boolean res = false;
        try {
            if (usuario.getIdUsuario() == null){
                res = super.insertar(usuario);
                if (res) {
                    UsuarioRol ur;
                    for (Rol item : rolList) {
                        ur = new UsuarioRol();
                        ur.setIdRol(item.getIdRol());
                        ur.setIdUsuario(usuario.getIdUsuario());
                        ur.setInsertFecha(new java.util.Date());
                        ur.setInsertIdUsuario(usuario.getInsertIdUsuario());
                        res = usuarioRolMapper.insertar(ur);
                        if (!res){
                            throw new Exception("Error Registrar roles de Usuario. " );
                        }
                    }
                }
            } else {
                
                res = super.actualizar(usuario);
                
                if ( res ) {
                    UsuarioRol ur = new UsuarioRol();
                    ur.setIdUsuario(usuario.getIdUsuario());
                    res = usuarioRolMapper.eliminar(ur);

                    if (res) {
                        for (Rol item : rolList) {
                            ur = new UsuarioRol();
                            ur.setIdRol(item.getIdRol());
                            ur.setIdUsuario(usuario.getIdUsuario());
                            ur.setInsertFecha(new java.util.Date());
                            ur.setInsertIdUsuario(usuario.getInsertIdUsuario());
                            res = usuarioRolMapper.insertar(ur);
                            if (!res){
                                throw new Exception("Error Registrar roles de Usuario. " );
                            }
                        }
                    }
                    res = true;
                }
            }

        } catch (Exception ex) {
            throw new Exception("Error Registrar Usuario. " + ex.getMessage());
        }
        return res;
    }
    
    

}
