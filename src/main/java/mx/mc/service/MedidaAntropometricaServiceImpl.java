package mx.mc.service;

import mx.mc.mapper.MedidaAntropometricaMapper;
import mx.mc.model.MedidaAntropometrica;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Cervanets
 */
public class MedidaAntropometricaServiceImpl extends GenericCrudServiceImpl<MedidaAntropometrica, String> implements MedidaAntropometricaService {
    
    @Autowired
    private MedidaAntropometricaMapper signoVitallMapper;
    
    @Override
    public MedidaAntropometrica obtenerUltimo (MedidaAntropometrica sv) throws Exception {
        try {
            return signoVitallMapper.obtenerUltimo(sv);
        } catch (Exception ex) {
            throw new Exception("Error obtener ultima medida Antropometrica. " + ex.getMessage());
        }
    }
    
}
