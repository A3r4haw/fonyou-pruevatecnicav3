/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seidor.ulises.service;

import com.seidor.ulises.model.ImpAlbEntradas;
import com.seidor.ulises.model.ImpLineasAlbEntradas;
import java.util.List;
import mx.mc.service.*;
/**
 *
 * @author apalacios
 */
public interface ImpAlbEntradasService extends GenericCrudService<ImpAlbEntradas, String> {
    public boolean insertarAlbaran(ImpAlbEntradas impAlbEntradas, List<ImpLineasAlbEntradas> listImpLineasAlbEntradas) throws Exception;
}

