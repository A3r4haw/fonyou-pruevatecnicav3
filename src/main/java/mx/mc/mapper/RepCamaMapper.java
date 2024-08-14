/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DataResultReport;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.RepCama;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface RepCamaMapper extends GenericCrudMapper<RepCama, String>{
    
    List<DataResultReport> consultarDisponibilidadCamas(@Param("paramReporte") ParamBusquedaReporte paramReporte,
            @Param("listaEstructuras") List<String> listaEstructuras,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
    
    Long obtenerTotalRegistrosCamas(@Param("paramReporte") ParamBusquedaReporte paramReporte, @Param("listaEstructuras") List<String> listaEstructuras);
}
