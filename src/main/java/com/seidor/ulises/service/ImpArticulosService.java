/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ImpArticulos;
import mx.mc.service.*;
/**
 *
 * @author apalacios
 */
public interface ImpArticulosService extends GenericCrudService<ImpArticulos, String> {
    @Override
    public boolean insertar(ImpArticulos impArticulos) throws Exception;
}

