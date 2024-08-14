/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.PresentacionMedicamento;
import org.apache.ibatis.annotations.Param;
/**
 *
 * @author bbautista
 */
public interface PresentacionMedicamentoMapper extends GenericCrudMapper<PresentacionMedicamento, String> {
 
    List<PresentacionMedicamento> obtenerTodo();
    PresentacionMedicamento obtenerPorId(@Param("idPresentacion") int idPresentacion);
    
    Integer obtenerSiguienteId();
}
