/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoInteraccionMapper;
import mx.mc.model.TipoInteraccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class TipoInteraccionServiceImpl extends GenericCrudServiceImpl<TipoInteraccion, String> implements TipoInteraccionService {
    
    @Autowired
    TipoInteraccionMapper tipoInteaccionMapper;
    
    public TipoInteraccionServiceImpl(GenericCrudMapper<TipoInteraccion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<TipoInteraccion> obtenerTodo() throws Exception {
        List<TipoInteraccion> listaTipoInteraccion = new ArrayList<>();
        try {
            listaTipoInteraccion = tipoInteaccionMapper.obetnerTipoInteracciones();
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al obetner la lista de tipos de interacción  " + ex.getMessage());
        }
        return listaTipoInteraccion;
    }
}
