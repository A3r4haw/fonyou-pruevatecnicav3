/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Almacen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class AlmacenServiceImpl extends GenericCrudServiceImpl<Almacen,String> implements AlmacenService {
    
    @Autowired
    public AlmacenServiceImpl(GenericCrudMapper<Almacen, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
