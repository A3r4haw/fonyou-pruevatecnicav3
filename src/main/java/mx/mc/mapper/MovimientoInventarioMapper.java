package mx.mc.mapper;

import java.util.List;
import mx.mc.model.MovimientoInventario;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author aortiz
 */
public interface MovimientoInventarioMapper extends GenericCrudMapper<MovimientoInventario, String> {
    public boolean insertarMovimientosInventarios(
            @Param("listaMovimientos") List<MovimientoInventario> listaMovimientos);
}
