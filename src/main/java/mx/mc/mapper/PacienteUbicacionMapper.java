package mx.mc.mapper;

import java.util.Date;
import mx.mc.model.PacienteUbicacion;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface PacienteUbicacionMapper extends GenericCrudMapper<PacienteUbicacion, String> {
    
    public boolean actualizarPacienteUbicacion(PacienteUbicacion pacienteUbicacion);
    
    public PacienteUbicacion obtenerCamaAsignada(PacienteUbicacion pu);

    public boolean cierraAsigCamaAbiertas(@Param("fecha") Date fecha, @Param("idUsuario") String idUsuario, @Param("idPaciente") String idPaciente);
    
}
