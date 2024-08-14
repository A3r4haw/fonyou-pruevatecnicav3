package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ImpresionEtiqueta;
/**
 *
 * @author Cervanets
 */
public interface ImpresionEtiquetaMapper extends GenericCrudMapper<ImpresionEtiqueta, String> {
    
    public List<ImpresionEtiqueta> obtenerImpresiones(ImpresionEtiqueta o);
    
}
