package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.HorarioEntregaMapper;
import mx.mc.model.HorarioEntrega;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cervanets
 */
@Service
public class HorarioEntregaServiceImpl extends GenericCrudServiceImpl<HorarioEntrega, String> implements HorarioEntregaService {
    
    @Autowired
    HorarioEntregaMapper HorarioEntregaMapper;
    
    public HorarioEntregaServiceImpl(GenericCrudMapper<HorarioEntrega, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
}
