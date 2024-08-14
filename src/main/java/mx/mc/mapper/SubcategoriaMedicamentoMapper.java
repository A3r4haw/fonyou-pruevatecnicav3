/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import mx.mc.model.SubcategoriaMedicamento;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author bbautista
 */
public interface SubcategoriaMedicamentoMapper extends GenericCrudMapper<SubcategoriaMedicamento,String> {
   SubcategoriaMedicamento obtenerSubPorNombre(@Param("idCategoriaMedicamento")Integer idSubcategoria,@Param("nombreSubcategoriaMedicamento") String medicamento);
   
   SubcategoriaMedicamento obtenerPorIdSubcategoriaMedicamento(@Param("idSubCategoriaMedicamento")Integer idSubcategoria);
   
   SubcategoriaMedicamento obtenerPorIdSubcategoriaMedicamentos(@Param("idMedicamento")String idMedicamento);
   
}
