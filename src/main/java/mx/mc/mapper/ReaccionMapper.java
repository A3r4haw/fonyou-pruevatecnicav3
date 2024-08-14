/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Reaccion;
import mx.mc.model.ReaccionExtend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface ReaccionMapper extends GenericCrudMapper<Reaccion,String> {
    
    List<ReaccionExtend> obtenerReaccionesListLazy(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("estatusReaccionAdv") Integer  estatusReaccionAdv
            ,@Param("startingAt") int startingAt,@Param("maxPerPage") int maxPerPage
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder
    );
    
    Long obtenerTotalReaccionesListLazy(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte
            , @Param("estatusReaccionAdv") Integer estatusReaccionAdv
    );
    
    List<ReaccionExtend> obtenerReaccionesByIdPacienteIdInsumos(@Param("idPaciente") String idPaciente, @Param("listaInsumos") List<String> listaInsumos);
    
}
