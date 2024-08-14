package mx.mc.service;

import java.util.List;
import mx.mc.model.Config;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface ConfigService extends GenericCrudService<Config, String> {
    public List<Config> obtenerConfigOrdenadoPorCadena(Config params, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    public Long obtenerTotalConfigOrdenadoPorCadena(Config params) throws Exception;
    public Config obtenerByNombre(String nombre) throws Exception;

}
