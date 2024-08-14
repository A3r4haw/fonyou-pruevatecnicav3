/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.gob.issste.ws.model.RecetaSiam;
import mx.mc.model.Usuario;

/**
 *
 * @author Admin
 */
public interface RecetaSiamService extends GenericCrudService<RecetaSiam, String>{
    public String RegistrarRecetaSiam(RecetaSiam receta,Usuario usuario) throws Exception;
}
