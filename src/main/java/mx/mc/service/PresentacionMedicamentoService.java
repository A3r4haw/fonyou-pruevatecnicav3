/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.PresentacionMedicamento;
/**
 *
 * @author bbautista
 */
public interface PresentacionMedicamentoService extends GenericCrudService<PresentacionMedicamento,String>  {
    public List<PresentacionMedicamento> obtenerTodo() throws Exception;
    public PresentacionMedicamento obtenerPorId(int idPresentacion) throws Exception;
    
    public Integer obtenerSiguienteId() throws Exception;
}
