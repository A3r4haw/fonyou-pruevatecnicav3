/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DevolucionMinistracion;
import mx.mc.model.DevolucionMinistracionExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface DevolucionMinistracionMapper extends GenericCrudMapper<DevolucionMinistracion,String> {
    List<DevolucionMinistracion> obtenerListaDevolucion();
    List<DevolucionMinistracion> obtenerBusquedaDevolucion(@Param("cadena") String cadena); 
    List<DevolucionMinistracionExtended> obtenerListaDevolucionExtended(
            DevolucionMinistracionExtended devolucionMinistracionExtended); 
    List<DevolucionMinistracionExtended> obtenerListaDevMedMinistracion(@Param("idEstructura") String idEstructura, @Param("cadenaBusqueda") String cadenaBusqueda, @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage, @Param("devolucion") List<String> devolucion); 
    DevolucionMinistracionExtended obtenerByFolioSurtimientoForDev(@Param("folioSurtimiento") String folioSurtimiento,@Param("idPadre") String idPadre, @Param("filter") String filter);
    List<DevolucionMinistracionExtended> obtenerByFolioSurtimientoForDevList(@Param("folioSurtimiento") String folioSurtimiento,@Param("idPadre") String idPadre, @Param("filter") String filter);
    String obteneridEstructuraAlmacenOfSurtimiento(@Param("folioSurtimiento") String idEstructura);
    String obtenerAlmacenPadreOfSurtimiento(@Param("idEstructura") String idEstructura);
    
    List<DevolucionMinistracionExtended> obtenerBusquedaDevolucionLazy(@Param("cadenaBusqueda") String cadena,@Param("idEstructura") String almacen,
                            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage, @Param("sortField") String sortField,@Param("sortOrder") String sortOrder); 
    Long obtenerBusquedaDevolucionTotalLazy(@Param("cadenaBusqueda") String cadena,@Param("idEstructura") String almacen); 
}
