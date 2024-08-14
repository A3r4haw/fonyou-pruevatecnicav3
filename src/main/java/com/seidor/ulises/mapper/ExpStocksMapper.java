/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ExpStocks;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;

/**
 *
 * @author bernabe
 */
public interface ExpStocksMapper extends GenericCrudMapper<ExpStocks,String>{
    List<ExpStocks> inventarioUlises(); 
}
