package mx.mc.service;

import java.util.List;
import mx.mc.model.TipoJustificacion;

/**
 *
 * @author hramirez
 *
 */
public interface TipoJustificacionService extends GenericCrudService<TipoJustificacion, String> {

    List<TipoJustificacion> obtenerActivosPorListId (boolean activa, List<Integer> listTipoJustificacion) throws Exception;
}
