/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.TipoCancelacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class TipoCancelacionServiceImpl extends GenericCrudServiceImpl<TipoCancelacion, String> implements TipoCancelacionService {
    
    @Autowired
    public TipoCancelacionServiceImpl(GenericCrudMapper<TipoCancelacion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
