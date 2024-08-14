/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import mx.mc.service.*;
import com.seidor.ulises.mapper.ImpBCMapper;
import com.seidor.ulises.model.ImpBC;
import mx.mc.mapper.GenericCrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class ImpBCServiceImpl extends GenericCrudServiceImpl<ImpBC, String> implements ImpBCService {
    
    @Autowired
    private ImpBCMapper impBCMapper;

    @Autowired
    public ImpBCServiceImpl(GenericCrudMapper<ImpBC, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean insertar(ImpBC impBC) throws Exception {
        try {
            return impBCMapper.insertar(impBC);
        } catch (Exception e) {
            return false;
            //throw new Exception("Error al agregar el Medicamento en Ulises");
        }
    }
}
