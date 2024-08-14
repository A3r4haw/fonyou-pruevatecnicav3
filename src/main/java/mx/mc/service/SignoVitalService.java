package mx.mc.service;

import mx.mc.model.SignoVital;

/**
 *
 * @author Cervanets
 */
public interface SignoVitalService  extends GenericCrudService<SignoVital, String> {

    SignoVital obtenerUltimo (SignoVital sv) throws Exception;
    
}
