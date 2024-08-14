/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author olozada
 */
public interface EnvioNeumaticoMapper extends GenericCrudMapper<EnvioNeumatico , String>{
    
    public boolean insertNeumaticTable(EnvioNeumatico neumaticap);

    List<EnvioNeumatico> consultaRepNeumatico(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);

    Long obtenerRepNeumatico(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    List<EnvioNeumatico> obtenerCapsulas(@Param("cadenaBusqueda") String cadenaBusqueda) throws Exception;

}
