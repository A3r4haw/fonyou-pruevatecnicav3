package mx.mc.service;

import mx.mc.model.Paciente;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.Visita;

/**
 *
 * @author hramirez
 */
public interface VisitaService extends GenericCrudService<Visita, String> {
    
    public boolean registrarVisitaPaciente(Visita visita, 
            PacienteServicio pacienteServicio , Paciente paciente) throws Exception;
    
    public Visita obtenerVisitaAbierta(Visita visita) throws Exception;
    
    public boolean cerrarVisitaYServicio(
            Visita visita, PacienteServicio pacienteServicio  , 
            Paciente paciente , Integer idEstatusCama , 
            PacienteUbicacion pacienteUbicacion) throws Exception;
 
    public boolean insertarVisitaYServicioGenericos(Visita v, PacienteServicio ps) throws Exception;
    
    public Visita obtenerVisitaPorNumero(String numeroVisita) throws Exception;
    
    public boolean insertarVisitaServicioUbicacinoGenericos(Visita v, PacienteServicio ps, PacienteUbicacion pu) throws Exception;
 
}
