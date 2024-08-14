/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.mapper.ExpStocksMapper;
import com.seidor.ulises.model.ExpStocks;
import java.util.List;
import mx.mc.service.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

/**
 *
 * @author bernabe
 */
@Service
public class ExpStocksServiceImpl extends GenericCrudServiceImpl<ExpStocks,String> implements ExpStocksService{
    
    @Autowired
    private ExpStocksMapper expStocksMapper;
    
    @Override
    public List<ExpStocks> inventarioUlises() throws Exception {
        try {
            return expStocksMapper.inventarioUlises();
        } catch (Exception e) {
            throw new Exception("Ocurrio un error al obtener el inventario de Ulises");
        }
            
    }
}
