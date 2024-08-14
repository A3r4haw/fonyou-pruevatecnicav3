/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.ReabastoRecepcion;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class ReabastoRecepcionServiceImpl extends GenericCrudServiceImpl<ReabastoRecepcion, String> implements ReabastoRecepcionService {
    
    @Autowired
    public ReabastoRecepcionServiceImpl(GenericCrudMapper<ReabastoRecepcion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
