/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.mapper.ExpEntradasMapper;
import com.seidor.ulises.model.ExpEntradas;
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
public class ExpEntradasServiceImpl extends GenericCrudServiceImpl<ExpEntradas, String> implements ExpEntradasService {
    
    @Autowired
    private ExpEntradasMapper expEntradasMapper;

    @Autowired
    public ExpEntradasServiceImpl(GenericCrudMapper<ExpEntradas, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ExpEntradas> obtenerExpEntradas() throws Exception {
        try {
            ExpEntradas expEntradas = new ExpEntradas();
            expEntradas.setTratado("N");
            expEntradas.setVersion(1);
            return expEntradasMapper.obtenerLista(expEntradas);
        } catch (Exception e) {
            throw new Exception("Error al obtener ExpEntradas de Ulises");
        }
    }
    
    @Override
    public boolean actualizarLista(List<ExpEntradas> listExpEntradas) throws Exception {
        try {
            return expEntradasMapper.actualizarLista(listExpEntradas);
        } catch (Exception e) {
            throw new Exception("Error al actualizar Lista de ExpEntradas en Ulises");
        }
    }
}
