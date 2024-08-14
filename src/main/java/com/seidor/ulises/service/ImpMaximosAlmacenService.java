/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ImpMaximosAlmacen;
import mx.mc.service.*;
/**
 *
 * @author apalacios
 */
public interface ImpMaximosAlmacenService extends GenericCrudService<ImpMaximosAlmacen, String> {
    @Override
    public boolean insertar(ImpMaximosAlmacen impMaximosAlmacen) throws Exception;
}

