/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ImpExpediciones;
import com.seidor.ulises.model.ImpLineasExpediciones;
import java.util.List;
import mx.mc.service.*;
/**
 *
 * @author apalacios
 */
public interface ImpExpedicionesService extends GenericCrudService<ImpExpediciones, String> {
    public boolean insertarExpediciones(ImpExpediciones impExpediciones, List<ImpLineasExpediciones> listImpLineasExpediciones) throws Exception;
}

