/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import org.springframework.stereotype.Service;
import mx.mc.model.TipoUsuario;

/**
 *
 * @author bbautista
 */
@Service
public class TipoUsuarioServiceImpl  extends GenericCrudServiceImpl<TipoUsuario,String> implements TipoUsuarioService {
    
    public TipoUsuarioServiceImpl(GenericCrudMapper<TipoUsuario, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
