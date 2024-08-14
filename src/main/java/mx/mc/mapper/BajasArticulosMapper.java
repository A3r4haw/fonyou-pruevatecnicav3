/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.BajasArticulos;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;



/**
 *
 * @author olozada
 */
public interface BajasArticulosMapper {
    
    List<BajasArticulos> consultarBajasArticulos(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    Long obtenerTotalRegistros(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
}
