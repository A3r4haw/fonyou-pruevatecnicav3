package mx.mc.service;

import java.util.List;
import mx.mc.model.DimesaReceta;

/**
 *
 * @author hramirez
 */
public interface DimesaRecetaService extends GenericCrudService<DimesaReceta, String> {
    
    public List<DimesaReceta> obtenerListaCancelados() throws Exception ;
    
    public List<DimesaReceta> obtenerListaVales(
            DimesaReceta dimesaReceta) throws Exception;
}
