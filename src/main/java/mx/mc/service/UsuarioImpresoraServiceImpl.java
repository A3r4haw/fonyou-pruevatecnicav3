/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.UsuarioImpresoraMapper;
import mx.mc.model.UsuarioImpresora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class UsuarioImpresoraServiceImpl  extends GenericCrudServiceImpl<UsuarioImpresora, String> implements UsuarioImpresoraService {
    
    @Autowired
    private UsuarioImpresoraMapper usuarioImpresoraMapper;

    @Autowired
    public UsuarioImpresoraServiceImpl(GenericCrudMapper<UsuarioImpresora, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean insertarListaImpresoras(List<UsuarioImpresora> listaImpresoras) throws Exception {
        try {
            usuarioImpresoraMapper.insertarListaImpresoras(listaImpresoras);
            return true;
        } catch (Exception ex) {
            throw new Exception("Error insertarListaImpresoras. " + ex.getMessage());
        }
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarListaImpresoras(
            List<UsuarioImpresora> listaImpresoras , String idUsuario)throws Exception {
        try {
            usuarioImpresoraMapper.eliminarByIdUsuario(idUsuario);
            if(!listaImpresoras.isEmpty()) {
                usuarioImpresoraMapper.insertarListaImpresoras(listaImpresoras);
            }
            return true;
        } catch (Exception ex) {
            throw new Exception("Error actualizarListaImpresoras. " + ex.getMessage());
        }
    }
}
