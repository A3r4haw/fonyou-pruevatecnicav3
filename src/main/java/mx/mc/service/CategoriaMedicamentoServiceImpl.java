/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import mx.mc.model.CategoriaMedicamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.CategoriaMedicamentoMapper;
/**
 *
 * @author bbautista
 */
@Service
public class CategoriaMedicamentoServiceImpl extends GenericCrudServiceImpl<CategoriaMedicamento,String> implements CategoriaMedicamentoService {
  
    @Autowired
    private CategoriaMedicamentoMapper categoriaMedicamentoMapper;
    
    @Override
    public List<CategoriaMedicamento> obtenerTodo() throws Exception{
        List<CategoriaMedicamento> categoriaMedicamentoList = new ArrayList<>();
        try{
            categoriaMedicamentoList= categoriaMedicamentoMapper.obtenerTodo();
        }catch(Exception ex){
            throw new Exception("Error al obtener CategoriaMedicamento. "+ex.getMessage());
        }
        return categoriaMedicamentoList;
    }
}
