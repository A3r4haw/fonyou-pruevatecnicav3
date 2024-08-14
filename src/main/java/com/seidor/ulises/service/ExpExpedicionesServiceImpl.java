/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.mapper.ExpExpedicionesMapper;
import com.seidor.ulises.mapper.ExpLineasExpedicionesMapper;
import com.seidor.ulises.mapper.ExpPackingMapper;
import com.seidor.ulises.model.ExpExpediciones;
import com.seidor.ulises.model.ExpLineasExpediciones;
import com.seidor.ulises.model.ExpPacking;
import java.util.Date;
import mx.mc.service.*;
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
public class ExpExpedicionesServiceImpl extends GenericCrudServiceImpl<ExpExpediciones, String> implements ExpExpedicionesService {
    
    @Autowired
    private ExpExpedicionesMapper expExpedicionesMapper;
    
    @Autowired
    private ExpLineasExpedicionesMapper expLineasExpedicionesMapper;
    
    @Autowired
    private ExpPackingMapper expPackingMapper;

    @Autowired
    public ExpExpedicionesServiceImpl(GenericCrudMapper<ExpExpediciones, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ExpExpediciones> obtenerExpExpediciones() throws Exception {
        try {
            ExpExpediciones expExpediciones = new ExpExpediciones();
            expExpediciones.setTratado("N");
            expExpediciones.setVersion(1);
            expExpediciones.setAnulada(0);
            return expExpedicionesMapper.obtenerLista(expExpediciones);
        } catch (Exception e) {
            throw new Exception("Error al obtener ExpExpediciones de Ulises", e);
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean actualizarLista(List<ExpExpediciones> listExpExpediciones, List<ExpLineasExpediciones> expLineasExpedicionesList) throws Exception {
        boolean res = false;
        try {
            res = expExpedicionesMapper.actualizarLista(listExpExpediciones);
            if (res) {
                res = expLineasExpedicionesMapper.actualizarLista(expLineasExpedicionesList);
                if (res) {
                    for (ExpLineasExpediciones expLineasExpediciones : expLineasExpedicionesList) {
                        ExpPacking expPacking = new ExpPacking();
                        expPacking.setIdExped(expLineasExpediciones.getIdExped());
                        expPacking.setArticulo(expLineasExpediciones.getArticulo());
                        expPacking.setLinea(expLineasExpediciones.getLinea());
                        expPacking.setTratado("S");
                        expPacking.setFechaTratado(new Date());
                        expPackingMapper.actualizar(expPacking);
                    }
                    res = true;
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al actualizar Lista de ExpExpediciones en Ulises", e);
        }
        return res;
    }
}
