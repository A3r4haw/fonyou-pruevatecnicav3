/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.FrecuenciaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Frecuencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class FrecuenciaServiceImpl extends GenericCrudServiceImpl<Frecuencia, String> implements FrecuenciaService {
    
    @Autowired
    private FrecuenciaMapper frecuenciaMapper;
            
    
    @Autowired
    public FrecuenciaServiceImpl(GenericCrudMapper<Frecuencia, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<Frecuencia> obtenerListaFrecuencia(Frecuencia f) throws Exception {
        try {
            return frecuenciaMapper.obtenerLista(f);
        } catch(Exception ex) {
            throw new Exception("Error al buscar la lista de Frecuencias:  " + ex.getMessage());
        }
    }
}
