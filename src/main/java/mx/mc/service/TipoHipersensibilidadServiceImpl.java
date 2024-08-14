/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoHipersensibilidadMapper;
import mx.mc.model.TipoHipersensibilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class TipoHipersensibilidadServiceImpl extends GenericCrudServiceImpl<TipoHipersensibilidad, String> implements TipoHipersensibilidadService {
    
    @Autowired
    TipoHipersensibilidadMapper tipoHipersensibilidadMapper;
    
    public TipoHipersensibilidadServiceImpl(GenericCrudMapper<TipoHipersensibilidad, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<TipoHipersensibilidad> obtenerListaTiposHipersensibilidad() throws Exception {
        try {
           return tipoHipersensibilidadMapper.obtenerListaTiposHipersensibilidad();
        } catch(Exception ex) {
            throw new Exception("Error al buscar la lista de tipos de hipersensibilidad:  " + ex.getMessage());
        }
    }
    
}
