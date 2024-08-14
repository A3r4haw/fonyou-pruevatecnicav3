/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.TipoEdadPaciente;

/**
 *
 * @author gcruz
 */
public interface TipoEdadPacienteService extends GenericCrudService<TipoEdadPaciente, String> {
    
    List<TipoEdadPaciente> obtenerTodo() throws Exception;
    
    TipoEdadPaciente obtenerTipoPorId(Integer idTipoEdadPaciente) throws Exception;
    
}
