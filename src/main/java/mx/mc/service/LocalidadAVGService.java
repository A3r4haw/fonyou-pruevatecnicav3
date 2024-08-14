/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.LocalidadAVG;
import mx.mc.model.Medicamento;

/**
 *
 * @author gcruz
 */
public interface LocalidadAVGService extends GenericCrudService<LocalidadAVG, String>{
    
    List<LocalidadAVG> obtenerTodo () throws Exception; 
    
    boolean asignarLocalidadAVG(Medicamento medicamento, LocalidadAVG localidad) throws Exception;
    
}
