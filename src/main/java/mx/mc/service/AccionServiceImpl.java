package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Accion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class AccionServiceImpl extends GenericCrudServiceImpl<Accion, String> implements AccionService {

    @Autowired
    public AccionServiceImpl(GenericCrudMapper<Accion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
}
