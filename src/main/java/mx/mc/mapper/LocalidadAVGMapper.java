/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.LocalidadAVG;

/**
 *
 * @author gcruz
 */
public interface LocalidadAVGMapper extends GenericCrudMapper<LocalidadAVG, String> {
    
    List<LocalidadAVG> obtenerTodo();
    
    boolean actualizar(LocalidadAVG localidadAVG);
}
