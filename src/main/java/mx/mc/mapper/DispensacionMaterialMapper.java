/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DispensacionMaterial;
import mx.mc.model.DispensacionMaterialExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author apalacios
 */
public interface DispensacionMaterialMapper extends GenericCrudMapper<DispensacionMaterial,String> {
   
    List<DispensacionMaterialExtended> obtenerDispensacionesLazzy(@Param("idEstructura") String idEstructura, @Param("idUsuario") String idUsuario, 
            @Param("cadenaBusqueda") String cadenaBusqueda, @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage,
            @Param("sortBy") String sortBy, @Param("sortOrder") String sortOrder);
    
    List<DispensacionMaterialExtended> obtenerDispensacionesReporte(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    Long obtenerTotalDispensacionesLazzy(@Param("idEstructura") String idEstructura, @Param("idUsuario") String idUsuario, @Param("cadenaBusqueda") String cadenaBusqueda);
}
