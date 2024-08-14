/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.mapper.ExpLineasExpedicionesMapper;
import com.seidor.ulises.model.ExpLineasExpediciones;
import mx.mc.service.*;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class ExpLineasExpedicionesServiceImpl extends GenericCrudServiceImpl<ExpLineasExpediciones, String> implements ExpLineasExpedicionesService {
    
    @Autowired
    private ExpLineasExpedicionesMapper expLineasExpedicionesMapper;

    @Autowired
    public ExpLineasExpedicionesServiceImpl(GenericCrudMapper<ExpLineasExpediciones, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ExpLineasExpediciones> obtenerExpLineasExpediciones(long idExp) throws Exception {
        try {
            ExpLineasExpediciones expLineasExpediciones = new ExpLineasExpediciones();
            expLineasExpediciones.setIdExped(idExp);
            expLineasExpediciones.setTratado("N");
            expLineasExpediciones.setVersion(1);
            return expLineasExpedicionesMapper.obtenerLista(expLineasExpediciones);
        } catch (Exception e) {
            throw new Exception("Error al obtener ExpLineasExpediciones de Ulises");
        }
    }
    
    @Override
    public boolean actualizarLista(List<ExpLineasExpediciones> listExpLineasExpediciones) throws Exception {
        try {
            return expLineasExpedicionesMapper.actualizarLista(listExpLineasExpediciones);
        } catch (Exception e) {
            throw new Exception("Error al actualizar Lista de ExpLineasExpediciones en Ulises");
        }
    }
    
    @Override
    public List<ExpLineasExpediciones> obtenerExpLineasExpedicion(String expedicion) throws Exception {
        try{
            return expLineasExpedicionesMapper.obtenerExpLineasExpedicion(expedicion);
        }catch(Exception e){
            throw new Exception("Error al obtener Lista de ExpLineasExpediciones en Ulises");
        }
    }
}
