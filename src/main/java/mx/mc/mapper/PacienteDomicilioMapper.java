package mx.mc.mapper;

import mx.mc.model.PacienteDomicilio;
import org.apache.ibatis.annotations.Param;

/**
 * @author AORTIZ
 */
public interface PacienteDomicilioMapper extends GenericCrudMapper<PacienteDomicilio, String>{
    PacienteDomicilio obtenerPacienteDomicilioByIdPaciente(@Param("idPaciente") String idPaciente) throws Exception;
}
