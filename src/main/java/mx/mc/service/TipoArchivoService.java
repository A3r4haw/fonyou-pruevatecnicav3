package mx.mc.service;

import java.util.List;
import mx.mc.model.TipoArchivo;

/**
 *
 * @author hramirez
 */
public interface TipoArchivoService extends GenericCrudService<TipoArchivo, String> {

    public List<TipoArchivo> obtenerListaActivos() throws Exception;

}
