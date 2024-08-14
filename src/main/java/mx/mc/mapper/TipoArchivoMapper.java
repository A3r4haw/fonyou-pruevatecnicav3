package mx.mc.mapper;

import java.util.List;
import mx.mc.model.TipoArchivo;

/**
 *
 * @author hramirez
 */
public interface TipoArchivoMapper extends GenericCrudMapper<TipoArchivo, String> {

    List<TipoArchivo> obtenerListaActivos();
            
}
