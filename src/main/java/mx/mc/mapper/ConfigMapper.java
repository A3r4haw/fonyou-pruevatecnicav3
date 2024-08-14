package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Config;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface ConfigMapper extends GenericCrudMapper<Config, String> {
     List<Config> obtenerConfigOrdenadoPorCadena(@Param("paramConfig") Config params,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage,
             @Param("sortField") String sortField, @Param("sortOrder") String sortOrder);

    Long obtenerTotalConfigOrdenadoPorCadena(@Param("paramConfig") Config params);
    
    Config obtenerByNombre(@Param("nombre") String nombre);
}
