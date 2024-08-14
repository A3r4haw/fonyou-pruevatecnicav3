package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DimesaRecetaMaterial;

/**
 *
 * @author hramirez
 */
public interface DimesaRecetaMaterialMapper extends GenericCrudMapper<DimesaRecetaMaterial, String> {
    
    public List<DimesaRecetaMaterial> obtenerListaVale(DimesaRecetaMaterial dimesaRecetaMaterial);
    
}
