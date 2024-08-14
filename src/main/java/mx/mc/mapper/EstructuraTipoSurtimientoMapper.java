/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.EstructuraTipoSurtimiento;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface EstructuraTipoSurtimientoMapper extends GenericCrudMapper<EstructuraTipoSurtimiento, String>{
    String[] obtenerIdAlmacen(@Param("idEstructuraAlmacen") String idEstructuraAlmacen);
    boolean insertarList(@Param("estructuraTipoSurtimientoList") List<EstructuraTipoSurtimiento> estructuraTipoSurtimientoList);
    boolean eliminarIdAlmacen(@Param("idEstructuraAlmacen") String idEstructuraAlmacen);
}
