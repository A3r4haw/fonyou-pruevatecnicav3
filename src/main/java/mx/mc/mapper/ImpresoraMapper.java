/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Impresora;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface ImpresoraMapper extends GenericCrudMapper<Impresora, String> {
    
     Impresora obtenerPorIdImpresora(@Param("idImpresora") String idImpresora);
     
     @Deprecated
     List<Impresora> obtenerListaImpresora();
     
     List<Impresora> obtenerListaImpresoras(@Param("idUsuario") String idUsuario, @Param("descripcion") String descripcion, @Param("tipo") String tipo );
     
     Impresora obtenerImpresoraByIpAndDescripcion(Impresora impresora);
     
     List<Impresora> obtenerListaImpresoraByUsuario(@Param("idUsuario") String idUsuario);
    
}
