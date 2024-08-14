/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.AlergenoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Alergeno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class AlergenoServiceImpl extends GenericCrudServiceImpl<Alergeno, String> implements AlergenoService{
    
    @Autowired
    AlergenoMapper alergenoMapper;
    
    public AlergenoServiceImpl(GenericCrudMapper<Alergeno, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<Alergeno> obtenerListaAlergenos() throws Exception{
        try {
            return alergenoMapper.obtenerListaAlergenos();
        } catch(Exception ex) {
            throw new Exception("Error al obtener la lista de Alergenos:  " + ex.getMessage());
        }
    }
}
