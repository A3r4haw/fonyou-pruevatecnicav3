/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ExpEntradas;
import java.util.List;
import mx.mc.service.*;
/**
 *
 * @author apalacios
 */
public interface ExpEntradasService extends GenericCrudService<ExpEntradas, String> {
    public List<ExpEntradas> obtenerExpEntradas() throws Exception;
    public boolean actualizarLista(List<ExpEntradas> listExpEntradas) throws Exception;
}

