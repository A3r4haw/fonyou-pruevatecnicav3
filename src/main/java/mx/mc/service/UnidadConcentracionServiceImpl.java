/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;

import mx.mc.model.UnidadConcentracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.UnidadConcentracionMapper;
/**
 *
 * @author bbautista
 */
@Service
public class UnidadConcentracionServiceImpl extends GenericCrudServiceImpl<UnidadConcentracion,String> implements UnidadConcentracionService {
    
    @Autowired
    private UnidadConcentracionMapper unidadConcentracionMapper;
    
    @Autowired
    public UnidadConcentracionServiceImpl(GenericCrudMapper<UnidadConcentracion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<UnidadConcentracion>  obtenerTodo() throws Exception{
        List<UnidadConcentracion> unidadConcentracionList = new ArrayList<>();        
        try{
            unidadConcentracionList = unidadConcentracionMapper.obtenerTodo();
        }catch(Exception ex){
            throw new Exception("Error al obtener UnidadConcentracion." +ex.getMessage());
}
       return unidadConcentracionList;
    
    }

    @Override
    public Integer obtenerSiguienteId() throws Exception {
        try {
            return unidadConcentracionMapper.obtenerSiguienteId();
        }catch(Exception ex) {
            throw new Exception("Error al momento de obtener el siguiente id consecutivo de concentración " + ex.getMessage());
        }
    }

    @Override
    public UnidadConcentracion obtenerUnidadNombre(String nombreUnidadConcentracion) throws Exception {
        
        try {
            return unidadConcentracionMapper.obtenerUnidadNombre(nombreUnidadConcentracion);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener la unidad por nombre: " + nombreUnidadConcentracion + "  " + ex.getMessage());
        }
    }
}
