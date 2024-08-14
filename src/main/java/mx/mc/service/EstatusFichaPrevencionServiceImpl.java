package mx.mc.service;

import mx.mc.mapper.EstatusFichaPrevencionMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EstatusFichaPrevencion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cervanets
 */
@Service
public class EstatusFichaPrevencionServiceImpl extends GenericCrudServiceImpl<EstatusFichaPrevencion, String> implements EstatusFichaPrevencionService {

    @Autowired
    private EstatusFichaPrevencionMapper estatusFichaPrevencionMapper;

    @Autowired
    public EstatusFichaPrevencionServiceImpl(GenericCrudMapper<EstatusFichaPrevencion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
