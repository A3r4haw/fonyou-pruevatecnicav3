package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.MotivoPacienteMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gcruz
 */
@Service
public class MotivoPacienteMovimientoServiceImpl extends GenericCrudServiceImpl<MotivoPacienteMovimiento, String> implements MotivoPacienteMovimientoService {

    @Autowired
    public MotivoPacienteMovimientoServiceImpl(GenericCrudMapper<MotivoPacienteMovimiento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
