/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.NutricionParenteralDetalle;
import mx.mc.model.NutricionParenteralDetalleExtended;

/**
 *
 * @author gcruz
 */
public interface NutricionParenteralDetalleService extends GenericCrudService<NutricionParenteralDetalle, String> {
    
    List<NutricionParenteralDetalleExtended> obtenerNutricionParenteralDetalleById(String idNutricionParenteral) throws Exception;
    
}
