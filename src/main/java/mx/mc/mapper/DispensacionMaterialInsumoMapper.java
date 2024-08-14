/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.DispensacionMaterialInsumo;
import mx.mc.model.DispensacionMaterialInsumoExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author apalacios
 */
public interface DispensacionMaterialInsumoMapper extends GenericCrudMapper<DispensacionMaterialInsumo,String> {
    List<DispensacionMaterialInsumoExtended> obtenerInsumosDispensacion(@Param("cadenaBusqueda") String cadenaBusqueda, @Param("idEstructura") String idEstructura, @Param("tipo") Integer tipo);
    DispensacionMaterialInsumoExtended obtenerInsumoPorQR(@Param("claveInstitucional") String claveInstitucional, @Param("lote") String lote, @Param("fechaCaducidad") Date fechaCaducidad, @Param("idEstructura") String idEstructura, @Param("tipo") Integer tipo);
    boolean insertarDispensacionInsumoList(List<DispensacionMaterialInsumo> listaDispensacionInsumo);
    List<DispensacionMaterialInsumoExtended> obtenerInsumosPorIdDispensacion(@Param("idDispensacion") String idDispensacion);
  
}
