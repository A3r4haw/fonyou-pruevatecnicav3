package mx.mc.service;

import java.util.List;
import mx.mc.model.ImpresionEtiqueta;

/**
 *
 * @author Cervanets
 */
public interface ImpresionEtiquetaService extends GenericCrudService<ImpresionEtiqueta, String> {

    public List<ImpresionEtiqueta> obtenerImpresiones(ImpresionEtiqueta o) throws Exception;
    
}
