/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DevolucionMezcla;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.model.SurtimientoInsumo_Extend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface DevolucionMezclaMapper extends GenericCrudMapper<DevolucionMezcla, String> {

    public DevolucionMezclaExtended obtenerDevolucionMezclaById(@Param("idDevolucionMezcla") String idDevolucionMezcla);

    DevolucionMezclaExtended obtenerSolucionByIdSolucion(@Param("idSolucion") String idSolucion);

    List<DevolucionMezclaExtended> obtenerBusquedaLazy(@Param("cadena") String cadena, @Param("idEstructura") String almacen, @Param("idEstatusSolucion") Integer idEstatusSolucion,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage,
            @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);
    
    Long obtenerBusquedaTotalLazy(@Param("cadena") String cadena, @Param("idEstructura") String almacen);

}
