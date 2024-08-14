/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.UsuarioImpresora;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface UsuarioImpresoraMapper extends GenericCrudMapper<UsuarioImpresora, String> {
	
    boolean insertarListaImpresoras(@Param("list") List<UsuarioImpresora> listaImpresoras); 
    
    boolean eliminarByIdUsuario(@Param("idUsuario") String idUsuario);
    
}
