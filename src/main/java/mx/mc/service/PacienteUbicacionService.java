package mx.mc.service;

import java.util.Date;
import mx.mc.model.Cama;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteUbicacion;

/**
 *
 * @author hramirez
 */
public interface PacienteUbicacionService extends GenericCrudService<PacienteUbicacion, String> {
    
    public boolean asignarCamaPaciente(PacienteUbicacion pacienteUbicacion , 
            Cama cama , Paciente paciente) throws Exception;
    
    public boolean liberarCamaPaciente(PacienteUbicacion pacienteUbicacion , 
            Paciente paciente , Integer idEstatusCama) throws Exception;
    
    public PacienteUbicacion obtenerCamaAsignada(PacienteUbicacion pu) throws Exception;
    
    public boolean actualizarPacienteUbicacion(PacienteUbicacion pacienteUbicacion) throws Exception;
    
    public boolean cierraAsigCamaAbiertas(Date fecha, String idUsuario, String idPaciente) throws Exception;

}
