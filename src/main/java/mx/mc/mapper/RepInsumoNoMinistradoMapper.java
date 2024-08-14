/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.RepInsumoNoMinistrado;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author olozada
 */
public interface RepInsumoNoMinistradoMapper extends GenericCrudMapper<RepInsumoNoMinistrado , String>{
    
List<RepInsumoNoMinistrado> obtenerListaInsumo(@Param("cadenaBusqueda") String cadenaBusqueda) throws Exception;  

List<RepInsumoNoMinistrado> consultaInsumoNoMinistrados(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
Long obtenerTotalInsumoNoMinistrado(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);


}
