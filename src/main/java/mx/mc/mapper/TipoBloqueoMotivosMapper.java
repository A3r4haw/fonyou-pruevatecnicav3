/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.TipoBloqueoMotivos;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author olozada
 */
public interface TipoBloqueoMotivosMapper extends GenericCrudMapper<TipoBloqueoMotivos,String> {
    
    List<TipoBloqueoMotivos> obtieneListaByIdTipoBloqueo(@Param("activo") int activo);
}
