package mx.mc.service;

import java.util.Date;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;

/**
 *
 * @author hramirez
 */
public interface PacienteServicioService extends GenericCrudService<PacienteServicio, String> {
    
    public PacienteServicio obtenerPacienteServicioAbierto(
            PacienteServicio pacienteServicio) throws Exception;
    
    public boolean cerrarServicioYasigarOtro(PacienteServicio pacienteServicio, 
            Paciente paciente , PacienteServicio nuevoServicio , 
            Integer idEstatusCama , PacienteUbicacion pacienteUbicacion) throws Exception;
    
    public boolean registrarPacienteServicio(PacienteServicio pacienteServicio,
            Paciente paciente) throws Exception;
    
    public boolean cerrarAsignacionesporIdVisita(PacienteServicio pacienteServicio) throws Exception;
    
    public boolean cierraAsignacionesAbiertas(
            Date fechaAsignacionFin
            , Integer idMotivoPacienteMovimiento
            , String idUsuarioAsignacionFin
            , Date updateFecha
            , String updateIdUsuario
            , String idPaciente) throws Exception;
    
}
