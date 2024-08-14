/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.UsuarioImpresora;

/**
 *
 * @author gcruz
 */
public interface UsuarioImpresoraService extends GenericCrudService<UsuarioImpresora, String> {
    
    public boolean insertarListaImpresoras(List<UsuarioImpresora> listaImpresoras) throws Exception;
    
    public boolean actualizarListaImpresoras(List<UsuarioImpresora> listaImpresoras , String idUsuario)throws Exception;
    
}
