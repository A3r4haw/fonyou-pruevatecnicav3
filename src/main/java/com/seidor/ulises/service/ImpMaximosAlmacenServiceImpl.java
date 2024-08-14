/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import mx.mc.service.*;
import com.seidor.ulises.mapper.ImpMaximosAlmacenMapper;
import com.seidor.ulises.model.ImpMaximosAlmacen;
import mx.mc.mapper.GenericCrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class ImpMaximosAlmacenServiceImpl extends GenericCrudServiceImpl<ImpMaximosAlmacen, String> implements ImpMaximosAlmacenService {
    
    @Autowired
    private ImpMaximosAlmacenMapper impMaximosAlmacenMapper;

    @Autowired
    public ImpMaximosAlmacenServiceImpl(GenericCrudMapper<ImpMaximosAlmacen, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean insertar(ImpMaximosAlmacen impMaximosAlmacen) throws Exception {
        try {
            return impMaximosAlmacenMapper.insertar(impMaximosAlmacen);
        } catch (Exception e) {
            throw new Exception("Error al agregar el Punto de Control en Ulises", e);
        }
    }
}
