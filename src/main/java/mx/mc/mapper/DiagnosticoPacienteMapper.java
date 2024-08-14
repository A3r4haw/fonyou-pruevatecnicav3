package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface DiagnosticoPacienteMapper extends GenericCrudMapper<DiagnosticoPaciente, String> {    
    DiagnosticoPaciente obtenerPorPrescripcionDiagnostico(@Param("idPrescripcion") String idPrescripcion, @Param("idDiagnostico") String idDiagnostico);    
}
