/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.Emisor;

/**
 *
 * @author gcruz
 */
public interface EmisorService extends GenericCrudService<Emisor, String> {
    
    List<Emisor> obtenerTodo() throws Exception;
    
}
