package mx.mc.mapper;

import java.util.List;
import mx.mc.model.TipoJustificacion;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 *
 */
public interface TipoJustificacionMapper extends GenericCrudMapper<TipoJustificacion, String> {

    List<TipoJustificacion> obtenerActivosPorListId(
            @Param("activo") boolean activo, @Param("listTipoJustificacion") List<Integer> listTipoJustificacion
    );
    
}
