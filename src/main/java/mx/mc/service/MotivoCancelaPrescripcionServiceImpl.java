package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.MotivoCancelaPrescripcionMapper;
import mx.mc.model.MotivoCancelaPrescripcion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Cervanets
 */
@Service
public class MotivoCancelaPrescripcionServiceImpl extends GenericCrudServiceImpl<MotivoCancelaPrescripcion, String> implements MotivoCancelaPrescripcionService {

    @Autowired
    private MotivoCancelaPrescripcionMapper motivoCancelaPrescripcionMapper;

    @Autowired
    public MotivoCancelaPrescripcionServiceImpl(GenericCrudMapper<MotivoCancelaPrescripcion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

}
