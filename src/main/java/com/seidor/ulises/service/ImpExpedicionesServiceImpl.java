/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.mapper.ImpExpedicionesMapper;
import com.seidor.ulises.mapper.ImpLineasExpedicionesMapper;
import mx.mc.service.*;
import com.seidor.ulises.model.ImpExpediciones;
import com.seidor.ulises.model.ImpLineasExpediciones;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author apalacios
 */
@Service
public class ImpExpedicionesServiceImpl extends GenericCrudServiceImpl<ImpExpediciones, String> implements ImpExpedicionesService {
    
    @Autowired
    private ImpExpedicionesMapper impExpedicionesMapper;
    @Autowired
    private ImpLineasExpedicionesMapper impLineasExpedicionesMapper;

    @Autowired
    public ImpExpedicionesServiceImpl(GenericCrudMapper<ImpExpediciones, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insertarExpediciones(ImpExpediciones impExpediciones, List<ImpLineasExpediciones> listImpLineasExpediciones) throws Exception {
        boolean res = false;
        try {
            res = impExpedicionesMapper.insertar(impExpediciones);
            if (res) {
                res = impLineasExpedicionesMapper.registraImpLineasExpedicionesList(listImpLineasExpediciones);
            }
        } catch (Exception e) {
            throw new Exception("Error al agregar las Prescripciones en Ulises");
        }
        return res;
    }
}
