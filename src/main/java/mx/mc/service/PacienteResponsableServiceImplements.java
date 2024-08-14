package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PacienteResponsableMapper;
import mx.mc.model.PacienteResponsable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORTIZ
 */
@Service
public class PacienteResponsableServiceImplements extends GenericCrudServiceImpl<PacienteResponsable, String> implements PacienteResponsableService {
    
    @Autowired
    private PacienteResponsableMapper pacienteResponsableMapper;

    @Autowired
    public PacienteResponsableServiceImplements(GenericCrudMapper<PacienteResponsable, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public PacienteResponsable obtenerPacienteResponsableByIdPaciente(String idPaciente) throws Exception {
        try {
                return pacienteResponsableMapper.
                        obtenerPacienteResponsableByIdPaciente(idPaciente);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }
}
