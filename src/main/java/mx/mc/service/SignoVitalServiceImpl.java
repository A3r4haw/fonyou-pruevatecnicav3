package mx.mc.service;

import mx.mc.mapper.SignoVitalMapper;
import mx.mc.model.SignoVital;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Cervanets
 */
public class SignoVitalServiceImpl extends GenericCrudServiceImpl<SignoVital, String> implements SignoVitalService {
    
    @Autowired
    private SignoVitalMapper signoVitallMapper;
    
    @Override
    public SignoVital obtenerUltimo (SignoVital sv) throws Exception {
        try {
            return signoVitallMapper.obtenerUltimo(sv);
        } catch (Exception ex) {
            throw new Exception("Error obtener ultimo signo vital. " + ex.getMessage());
        }
    }
    
}
