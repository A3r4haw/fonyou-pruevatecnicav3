package mx.mc.mapper;

import mx.mc.model.PacienteResponsable;
import org.apache.ibatis.annotations.Param;

/**
 * @author AORTIZ
 */
public interface PacienteResponsableMapper extends GenericCrudMapper<PacienteResponsable, String>{
    PacienteResponsable obtenerPacienteResponsableByIdPaciente(@Param("idPaciente") String idUsuario);
}
