/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;

import mx.mc.model.PresentacionMedicamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.PresentacionMedicamentoMapper;
/**
 *
 * @author bbautista
 */
@Service
public class PresentacionMedicamentoServiceImpl extends GenericCrudServiceImpl<PresentacionMedicamento,String> implements PresentacionMedicamentoService {
    
    @Autowired
    private PresentacionMedicamentoMapper presentacionMedicamentoMapper;
    
    @Autowired
    public PresentacionMedicamentoServiceImpl(GenericCrudMapper<PresentacionMedicamento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<PresentacionMedicamento> obtenerTodo() throws Exception{
        List<PresentacionMedicamento> presentacionMedicamentoList =  new ArrayList<>();
        try{
            presentacionMedicamentoList = presentacionMedicamentoMapper.obtenerTodo();
        }catch(Exception ex){
            throw new Exception("Error al obtener PresentacionMedicamento. "+ex.getMessage());
        }
        return presentacionMedicamentoList;
    }
    
    @Override
    public PresentacionMedicamento obtenerPorId(int idPresentacion) throws Exception{
        PresentacionMedicamento presentacionSelect = new PresentacionMedicamento();
        try{
            presentacionSelect= presentacionMedicamentoMapper.obtenerPorId(idPresentacion);
        }catch(Exception ex){
            throw new Exception("Error al obtener Medicamento"+ex.getMessage());
        }
        return presentacionSelect;
    }

    @Override
    public Integer obtenerSiguienteId() throws Exception {
        try {
            return presentacionMedicamentoMapper.obtenerSiguienteId();
        }catch(Exception ex) {
            throw new Exception("Error al momento de obtener el siguiente id consecutivo de presentación de medicamento " + ex.getMessage());
        }
    }
}
