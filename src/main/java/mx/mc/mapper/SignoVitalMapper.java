package mx.mc.mapper;

import mx.mc.model.SignoVital;

/**
 *
 * @author Cervanets
 */
public  interface SignoVitalMapper extends GenericCrudMapper<SignoVital, String> {
    
    SignoVital obtenerUltimo (SignoVital sv);
    
}
