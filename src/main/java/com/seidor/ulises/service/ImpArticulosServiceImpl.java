/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import mx.mc.service.*;
import com.seidor.ulises.mapper.ImpArticulosMapper;
import com.seidor.ulises.model.ImpArticulos;
import mx.mc.mapper.GenericCrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class ImpArticulosServiceImpl extends GenericCrudServiceImpl<ImpArticulos, String> implements ImpArticulosService {
    
    @Autowired
    private ImpArticulosMapper impArticulosMapper;

    @Autowired
    public ImpArticulosServiceImpl(GenericCrudMapper<ImpArticulos, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean insertar(ImpArticulos impArticulos) throws Exception {
        try {
            return impArticulosMapper.insertar(impArticulos);
        } catch (Exception e) {
            return false;
            //throw new Exception("Error al agregar el Medicamento en Ulises");
        }
    }
}
