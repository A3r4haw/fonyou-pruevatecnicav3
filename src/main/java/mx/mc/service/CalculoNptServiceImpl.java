package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.CalculoNpt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class CalculoNptServiceImpl extends GenericCrudServiceImpl<CalculoNpt, String> implements CalculoNptService {

    @Autowired
    public CalculoNptServiceImpl(GenericCrudMapper<CalculoNpt, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
}
