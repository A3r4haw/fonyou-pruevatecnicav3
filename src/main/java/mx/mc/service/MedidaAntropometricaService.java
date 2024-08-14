package mx.mc.service;

import mx.mc.model.MedidaAntropometrica;

/**
 *
 * @author Cervanets
 */
public interface MedidaAntropometricaService extends GenericCrudService<MedidaAntropometrica, String> {

    MedidaAntropometrica obtenerUltimo(MedidaAntropometrica sv) throws Exception;

}
