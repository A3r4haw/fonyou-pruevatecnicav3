/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ExpExpediciones;
import com.seidor.ulises.model.ExpLineasExpediciones;
import java.util.List;
import mx.mc.service.*;
/**
 *
 * @author apalacios
 */
public interface ExpExpedicionesService extends GenericCrudService<ExpExpediciones, String> {
    public List<ExpExpediciones> obtenerExpExpediciones() throws Exception;
    public boolean actualizarLista(List<ExpExpediciones> listExpExpediciones, List<ExpLineasExpediciones> expLineasExpedicionesList) throws Exception;
}

