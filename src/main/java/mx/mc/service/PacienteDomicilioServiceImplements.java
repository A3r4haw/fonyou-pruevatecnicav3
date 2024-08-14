package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.PacienteDomicilioMapper;
import mx.mc.model.PacienteDomicilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORTIZ
 */
@Service
public class PacienteDomicilioServiceImplements extends GenericCrudServiceImpl<PacienteDomicilio, String> implements PacienteDomicilioService {
    
    @Autowired
    private PacienteDomicilioMapper pacienteDomicilioMapper;

    @Autowired
    public PacienteDomicilioServiceImplements(GenericCrudMapper<PacienteDomicilio, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public PacienteDomicilio obtenerPacienteDomicilioByIdPaciente(String idPaciente) throws Exception {
        try {
                return pacienteDomicilioMapper.
                        obtenerPacienteDomicilioByIdPaciente(idPaciente);
        } catch (Exception ex) {
            throw new Exception("Error obtener. " + ex.getMessage());
        }
    }

   
}
