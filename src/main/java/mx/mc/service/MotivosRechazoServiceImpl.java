/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.MotivosRechazoMapper;
import mx.mc.model.MotivosRechazo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class MotivosRechazoServiceImpl extends GenericCrudServiceImpl<MotivosRechazo, String> implements MotivosRechazoService {
    
    @Autowired
    private MotivosRechazoMapper motivosRechazoMapper;
        
    @Autowired
    public MotivosRechazoServiceImpl(GenericCrudMapper<MotivosRechazo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<MotivosRechazo> obtenerMotivosRechazoActivos() throws Exception {
        try {
            return motivosRechazoMapper.obtenerMotivosRechazoActivos();
        } catch(Exception ex) {
            throw new Exception("Error al buscar los motivos de rechazo activos  " + ex.getMessage());
        }
    }
    
}
