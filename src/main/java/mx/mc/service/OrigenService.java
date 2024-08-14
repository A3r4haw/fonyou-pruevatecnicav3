/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.Origen;
/**
 *
 * @author bbautista
 */
public interface OrigenService extends  GenericCrudService<Origen, String>{
    List<Origen> obtenerLista(boolean activo) throws Exception;
}
