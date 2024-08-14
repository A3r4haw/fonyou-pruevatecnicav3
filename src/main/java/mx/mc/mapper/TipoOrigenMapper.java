/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import mx.mc.model.TipoOrigen;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author mcalderon
 */
public interface TipoOrigenMapper extends GenericCrudMapper<TipoOrigen, String>{

     TipoOrigen existeOrigen(@Param("idTipoOrigen") int idTipoOrigen);
     
}
