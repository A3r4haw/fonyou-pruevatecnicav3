/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.CategoriaMedicamento;
/**
 *
 * @author bbautista
 */
public interface CategoriaMedicamentoMapper  extends GenericCrudMapper<CategoriaMedicamento,Long> {
 
    List<CategoriaMedicamento> obtenerTodo();
}
