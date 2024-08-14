package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Fabricante;
import org.apache.ibatis.annotations.Param;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface FabricanteMapper extends GenericCrudMapper<Fabricante, String> {
    
    List<Fabricante> obtenerListaPorIdInsumo(@Param("idInsumo") String idInsumo);
    
    Integer obtenerSiguienteId();
    
    List<Fabricante> obtenerListaFabricantes(@Param("cadenaBusqueda") String cadenaBusqueda, @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage,
            @Param("sortField") String sortField, @Param("sortOrder") SortOrder sortOrder);
    
    Long obtenerTotalFabricantes(@Param("cadenaBusqueda") String cadenaBusqueda);
    
}
