/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.model.SubcategoriaMedicamento;
/**
 *
 * @author bbautista
 */
public interface SubcategoriaMedicamentoService extends GenericCrudService<SubcategoriaMedicamento,String> {
   SubcategoriaMedicamento obtenerSubPorNombre(Integer idSubcategoria,String medicamento) throws Exception; 
   
   SubcategoriaMedicamento obtenerPorIdSubcategoriaMedicamento(Integer idSubcategoria) throws Exception;
   
   SubcategoriaMedicamento obtenerPorIdSubcategoriaMedicamentos(String idMedicamento) throws Exception;
}
