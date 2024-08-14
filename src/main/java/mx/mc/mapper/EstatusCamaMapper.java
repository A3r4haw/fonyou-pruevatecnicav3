package mx.mc.mapper;

import java.util.List;

import mx.mc.model.EstatusCama;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author gcruz
 *
 */
public interface EstatusCamaMapper extends GenericCrudMapper<EstatusCama, String> {
	
    List<EstatusCama> obtenerTodo();
    EstatusCama obtenerPorNombre(@Param("nombreCama") String nombreCama);
}
