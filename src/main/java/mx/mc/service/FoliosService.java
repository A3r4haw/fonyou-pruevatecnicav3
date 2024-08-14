/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.model.Folios;

/**
 *
 * @author bbautista
 */
public interface FoliosService extends GenericCrudService<Folios, String>{
    public Folios  obtenerPrefixPorDocument(Integer document)  throws Exception;   
    public boolean actualizaFolios(Folios folio) throws Exception;
}