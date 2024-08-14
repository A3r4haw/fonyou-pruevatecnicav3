/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.UnidadConcentracion;
/**
 *
 * @author bbautista
 */
public interface UnidadConcentracionService extends GenericCrudService<UnidadConcentracion,String>  {
    
    public List<UnidadConcentracion> obtenerTodo() throws Exception;
    
    public Integer obtenerSiguienteId() throws Exception;
    
    UnidadConcentracion obtenerUnidadNombre(String nombreUnidadConcentracion) throws Exception;
}
