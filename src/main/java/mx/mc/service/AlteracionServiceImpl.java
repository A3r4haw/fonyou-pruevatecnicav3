/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.AlteracionMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Alteracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class AlteracionServiceImpl extends GenericCrudServiceImpl<Alteracion, String> implements AlteracionService {
    
    @Autowired
    AlteracionMapper alteracionMapper;
    
    public AlteracionServiceImpl(GenericCrudMapper<Alteracion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Alteracion> obtenerListaAlteraciones() throws Exception {
        try {
            return alteracionMapper.obtenerListaAlteraciones();
        } catch(Exception ex) {
            throw new Exception("Error al obtener lista de alteraciones: " + ex.getMessage()); 
        }
    }
    
}
