package mx.mc.service;

import java.util.List;
import mx.mc.model.Fabricante;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface FabricanteService extends GenericCrudService<Fabricante, String> {

    public List<Fabricante> obtenerListaPorIdInsumo(String idInsumo) throws Exception;
    
    public Integer obtenerSiguienteId() throws Exception;
    
    public List<Fabricante> obtenerListaFabricantes(String cadenaBusqueda, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    public Long obtenerTotalFabricantes(String cadenaBusqueda) throws Exception;
    
}
