/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.UnidadConcentracion;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author bbautista
 */
public interface UnidadConcentracionMapper extends GenericCrudMapper<UnidadConcentracion, String> {
    
    List<UnidadConcentracion> obtenerTodo();
    
    Integer obtenerSiguienteId();
    
    UnidadConcentracion obtenerUnidadNombre(@Param("nombreUnidadConcentracion") String nombreUnidadConcentracion);
}
