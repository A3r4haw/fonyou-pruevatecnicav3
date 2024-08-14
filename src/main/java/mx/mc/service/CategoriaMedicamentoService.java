/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.CategoriaMedicamento;
/**
 *
 * @author bbautista
 */
public interface CategoriaMedicamentoService extends GenericCrudService<CategoriaMedicamento,String>  {
 
    public List<CategoriaMedicamento> obtenerTodo() throws Exception;
}
