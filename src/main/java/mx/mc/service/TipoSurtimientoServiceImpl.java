/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.TipoSurtimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class TipoSurtimientoServiceImpl extends GenericCrudServiceImpl<TipoSurtimiento, String> implements TipoSurtimientoService{

    @Autowired
    public TipoSurtimientoServiceImpl(GenericCrudMapper<TipoSurtimiento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
}
