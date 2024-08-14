package mx.mc.service;

import java.util.List;
import mx.mc.model.DimesaRecetaMaterial;

/**
 *
 * @author hramirez
 */
public interface DimesaRecetaMaterialService extends GenericCrudService<DimesaRecetaMaterial, String> {
    
    public List<DimesaRecetaMaterial> obtenerListaVale(
            DimesaRecetaMaterial dimesaRecetaMaterial) throws Exception;
    
}
