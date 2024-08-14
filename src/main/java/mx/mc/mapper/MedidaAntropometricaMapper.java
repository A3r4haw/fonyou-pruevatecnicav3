package mx.mc.mapper;

import mx.mc.model.MedidaAntropometrica;

/**
 *
 * @author Cervanets
 */
public interface MedidaAntropometricaMapper extends GenericCrudMapper<MedidaAntropometrica, String> {
    
    MedidaAntropometrica obtenerUltimo (MedidaAntropometrica ma);
    
}
