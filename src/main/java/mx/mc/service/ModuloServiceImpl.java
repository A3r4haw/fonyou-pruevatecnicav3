package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Modulo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class ModuloServiceImpl extends GenericCrudServiceImpl<Modulo, String> implements ModuloService {

    @Autowired
    public ModuloServiceImpl(GenericCrudMapper<Modulo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
