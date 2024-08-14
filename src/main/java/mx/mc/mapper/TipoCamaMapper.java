package mx.mc.mapper;

import java.util.List;

import mx.mc.model.TipoCama;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author gcruz
 *
 */
public interface TipoCamaMapper extends GenericCrudMapper<TipoCama, String> {
	
    List<TipoCama> obtenerTodo();
    TipoCama    obtenerPorNombre(@Param("nombre") String nombre);
}
