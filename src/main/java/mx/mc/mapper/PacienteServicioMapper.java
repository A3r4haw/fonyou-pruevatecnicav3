package mx.mc.mapper;

import java.util.Date;
import mx.mc.model.PacienteServicio;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface PacienteServicioMapper extends GenericCrudMapper<PacienteServicio, String> {
    
    public PacienteServicio obtenerPacienteServicioAbierto(PacienteServicio pacienteServicio);
    
    public PacienteServicio obtenerPacienteServicioDia(@Param("idVisita") String idVisita);

    public boolean cerrarAsignacionesporIdVisita(PacienteServicio pacienteServicio);
    
    public boolean cierraAsignacionesAbiertas(
            @Param("fechaAsignacionFin") Date fechaAsignacionFin
            , @Param("idMotivoPacienteMovimiento") Integer idMotivoPacienteMovimiento
            , @Param("idUsuarioAsignacionFin") String idUsuarioAsignacionFin
            , @Param("updateFecha") Date updateFecha
            , @Param("updateIdUsuario") String updateIdUsuario
            , @Param("idPaciente") String idPaciente);
    
}
