/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ExpStocks;
import java.util.List;
import mx.mc.service.GenericCrudService;

/**
 *
 * @author bernabe
 */
public interface ExpStocksService extends GenericCrudService<ExpStocks, String>{
    public List<ExpStocks> inventarioUlises() throws Exception;
}
