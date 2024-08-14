/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.MotivosRechazo;

/**
 *
 * @author gcruz
 */
public interface MotivosRechazoMapper extends GenericCrudMapper<MotivosRechazo, String> {
    
    List<MotivosRechazo> obtenerMotivosRechazoActivos();
    
}
