/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EstatusReaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class EstatusReaccionServiceImpl extends GenericCrudServiceImpl<EstatusReaccion, String> implements EstatusReaccionService{
    
    
    
    @Autowired
    public EstatusReaccionServiceImpl(GenericCrudMapper<EstatusReaccion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    
}
