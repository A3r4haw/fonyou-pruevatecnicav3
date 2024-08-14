package mx.mc.mapper;

import java.util.List;
import mx.mc.model.TurnoMedico;
import org.apache.ibatis.annotations.Param;

/**
 * @author aortiz
 */
public interface TurnoMedicoMapper extends GenericCrudMapper<TurnoMedico, String> {
	
    boolean insertarListaTurnos(@Param("list") List<TurnoMedico> listaTurnos); 
    
    boolean eliminarByIdUsuario(@Param("idUsuario") String idUsuario);
}
