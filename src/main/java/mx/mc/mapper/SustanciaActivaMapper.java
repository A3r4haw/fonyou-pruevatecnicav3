/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.SustanciaActiva;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author bbautista
 */
public interface SustanciaActivaMapper extends GenericCrudMapper<SustanciaActiva, String> {
    List<SustanciaActiva> obtenerTodo();
    List<SustanciaActiva> obtenerPorNombre(@Param("nombreSustanciaActiva") String nombreSustanciaActiva);
    SustanciaActiva obtenerSustanciaPorNombre(@Param("nombreSustanciaActiva") String nombreSustanciaActiva);
    SustanciaActiva obtenerPorId(@Param("idSustanciaActiva") int idSustanciaActiva);
    
    Integer obtenerSiguienteId();
}
