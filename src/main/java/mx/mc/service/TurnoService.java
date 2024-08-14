package mx.mc.service;

import java.util.Date;
import mx.mc.model.Turno;

/**
 * @author aortiz
 */
public interface TurnoService extends GenericCrudService<Turno, String>{

    public Turno obtenerPorHora(Date hora) throws Exception;
    
}
