/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.SubcategoriaMedicamentoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.SubcategoriaMedicamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class SubcategoriaMedicamentoServiceImpl extends GenericCrudServiceImpl<SubcategoriaMedicamento,String> implements SubcategoriaMedicamentoService {
    
    @Autowired
    private SubcategoriaMedicamentoMapper subcategoriaMedicamentoMapper;

    @Autowired
    public SubcategoriaMedicamentoServiceImpl(GenericCrudMapper<SubcategoriaMedicamento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
     @Override
    public SubcategoriaMedicamento obtenerSubPorNombre(Integer idSubcategoria, String medicamento) throws Exception {
        try{
            return subcategoriaMedicamentoMapper.obtenerSubPorNombre(idSubcategoria,medicamento);
        }catch(Exception ex){
            throw new Exception("Error al obtener SubcategoriaMedicamento. " +ex.getMessage());
        }
    }
     
    @Override
    public SubcategoriaMedicamento obtenerPorIdSubcategoriaMedicamento(Integer idSubcategoria) throws Exception {
        try{
            return subcategoriaMedicamentoMapper.obtenerPorIdSubcategoriaMedicamento(idSubcategoria);
        }catch(Exception ex){
            throw new Exception("Error al obtener obtenerPorIdSubcategoriaMedicamento. " +ex.getMessage());
        }
    }
    
    @Override
    public SubcategoriaMedicamento obtenerPorIdSubcategoriaMedicamentos(String idMedicamento) throws Exception {
        try{
            return subcategoriaMedicamentoMapper.obtenerPorIdSubcategoriaMedicamentos(idMedicamento);
        }catch(Exception ex){
            throw new Exception("Error al obtener obtenerPorIdSubcategoriaMedicamento. " +ex.getMessage());
        }
    }
}
