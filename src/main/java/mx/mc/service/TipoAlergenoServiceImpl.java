/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoAlergenoMapper;
import mx.mc.model.TipoAlergeno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class TipoAlergenoServiceImpl extends GenericCrudServiceImpl<TipoAlergeno, String> implements TipoAlergenoService{
    
    @Autowired
    TipoAlergenoMapper tipoAlergenoMapper;
    
    public TipoAlergenoServiceImpl(GenericCrudMapper<TipoAlergeno, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<TipoAlergeno> obtenerListaTiposAlergeno() throws Exception {
        try {
            return tipoAlergenoMapper.obtenerListaTiposAlergeno();
        } catch(Exception ex) {
            throw new Exception("Error al buscar lista de tipos de Alergeno: " + ex.getMessage());
        }
    }
    
}
