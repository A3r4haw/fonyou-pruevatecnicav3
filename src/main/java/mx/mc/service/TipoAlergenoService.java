/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.TipoAlergeno;

/**
 *
 * @author gcruz
 */
public interface TipoAlergenoService extends GenericCrudService<TipoAlergeno, String> {
    
    List<TipoAlergeno> obtenerListaTiposAlergeno() throws Exception;
}
