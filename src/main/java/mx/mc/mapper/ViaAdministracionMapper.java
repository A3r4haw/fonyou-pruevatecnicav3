/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ViaAdministracion;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author bbautista
 */
public interface ViaAdministracionMapper extends GenericCrudMapper<ViaAdministracion, String> {
    
    List<ViaAdministracion> obtenerTodo();
    ViaAdministracion obtenerPorId(Integer idViaAdministracion);
    Integer obtenerSiguienteId();
    
    String validaNombreExistenteVia(@Param("nombreViaAdministracion") String nombreViaAdministracion);
    
    List<ViaAdministracion> obtenerBusquedaViaAdministraciones(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
    
    Long obtenerTotalBusquedaViaAdministraciones(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
}
