package mx.mc.service;

import mx.mc.model.Cita;

/**
 * @author aortiz
 */
public interface CitaService extends GenericCrudService<Cita, String>{
    public Cita obtenerById(String idCita) throws Exception;
    public boolean updateCita(Cita cita) throws Exception;
    public boolean cancelCita(String idCita) throws Exception;
    public Cita existeCita(Cita cita) throws Exception;
    public boolean actualizarEstatusCitas(Cita cita)throws Exception;
}
