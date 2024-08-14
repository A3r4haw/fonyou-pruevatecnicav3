package mx.mc.mapper;

import mx.mc.model.TipoMovimiento;
import org.apache.ibatis.annotations.Param;

public interface TipoMovimientoMapper extends GenericCrudMapper<TipoMovimiento, String>{

	 public TipoMovimiento obtenerTipoMovmientoById(@Param("idTipoMovimiento") int idTipoMovimiento);
}
