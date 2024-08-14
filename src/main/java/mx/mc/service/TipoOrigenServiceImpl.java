/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.TipoOrigen;
import mx.mc.mapper.TipoOrigenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author bbautista
 */
@Service
public class TipoOrigenServiceImpl extends GenericCrudServiceImpl<TipoOrigen, String> implements TipoOrigenService {
    
    @Autowired
    private TipoOrigenMapper tipoOrigenMapper;

    @Autowired
    public TipoOrigenServiceImpl(GenericCrudMapper<TipoOrigen, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public TipoOrigen existeOrigen(int idTipoOrigen) throws Exception {
         try {
            return tipoOrigenMapper.existeOrigen(idTipoOrigen);
        } catch (Exception e) {
              throw new Exception("Error al obtener el TipoOrigen" + e.getMessage());
        }       
    }
    
    
    
}
