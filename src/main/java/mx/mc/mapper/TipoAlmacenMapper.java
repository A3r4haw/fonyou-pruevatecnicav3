package mx.mc.mapper;

import java.util.List;

import mx.mc.model.TipoAlmacen;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author gcruz
 *
 */
public interface TipoAlmacenMapper extends GenericCrudMapper<TipoAlmacen, String> {

	List<TipoAlmacen> obtenerTiposAlmacen();
	TipoAlmacen obtenerPorNombre(@Param("nombre") String nombre);
}
