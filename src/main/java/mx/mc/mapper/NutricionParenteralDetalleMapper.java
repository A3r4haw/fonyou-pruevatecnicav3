/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.NutricionParenteralDetalle;
import mx.mc.model.NutricionParenteralDetalleExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface NutricionParenteralDetalleMapper extends GenericCrudMapper<NutricionParenteralDetalle, String> {
    
    public boolean guardarListaNutricionParenteralDetalle(@Param("lista") List<NutricionParenteralDetalleExtended> listaNutricionParenteralDetalle);
    
    public boolean actualizarListaNutricionParenteralDetalle(@Param("lista") List<NutricionParenteralDetalleExtended> listaNutricionParenteralDetalle);
    
    public List<NutricionParenteralDetalleExtended> obtenerNutricionParenteralDetalleById(@Param("idNutricionParenteral") String idNutricionParenteral);
    
    public boolean borrarListaNutricionParanDetalle(@Param("lista") List<NutricionParenteralDetalleExtended> listaNutricionParenteralDetalle);
    
}
