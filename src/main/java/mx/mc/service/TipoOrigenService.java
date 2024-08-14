/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;


import mx.mc.model.TipoOrigen;
/**
 *
 * @author mcalderon
 */
public interface TipoOrigenService extends GenericCrudService <TipoOrigen,String> {
  
     public TipoOrigen existeOrigen(int idTipoOrigen) throws Exception;

}
