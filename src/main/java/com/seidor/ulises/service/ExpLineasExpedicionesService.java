/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ExpLineasExpediciones;
import java.util.List;
import mx.mc.service.*;
/**
 *
 * @author apalacios
 */
public interface ExpLineasExpedicionesService extends GenericCrudService<ExpLineasExpediciones, String> {
    public List<ExpLineasExpediciones> obtenerExpLineasExpediciones(long idExp) throws Exception;    
    public boolean actualizarLista(List<ExpLineasExpediciones> listExpLineasExpediciones) throws Exception;
    public List<ExpLineasExpediciones> obtenerExpLineasExpedicion(String expedicion) throws Exception;
}

