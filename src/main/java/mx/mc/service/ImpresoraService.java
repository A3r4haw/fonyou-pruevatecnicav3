/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.Impresora;

/**
 *
 * @author gcruz
 */
public interface ImpresoraService extends GenericCrudService<Impresora, String> {
    
     public Impresora obtenerPorIdImpresora(String idImpresora) throws Exception;

     public List<Impresora> obtenerListaImpresora() throws Exception;

     public List<Impresora> obtenerListaImpresoras(String idUsuario, String descripcion, String tipo) throws Exception;
     
     public Impresora obtenerImpresoraByIpAndDescripcion(Impresora impresora) throws Exception;
     
     public List<Impresora> obtenerListaImpresoraByUsuario(String idUsuario) throws Exception;
         
}
