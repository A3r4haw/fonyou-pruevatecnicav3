package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DimesaReceta;

/**
 *
 * @author hramirez
 */
public interface DimesaRecetaMapper extends GenericCrudMapper<DimesaReceta, String> {
    
    public List<DimesaReceta> obtenerListaCancelados();
    
    public List<DimesaReceta> obtenerListaVales(DimesaReceta dimesaReceta);
}
