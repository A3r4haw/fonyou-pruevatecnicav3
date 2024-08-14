package mx.mc.service;

import java.util.List;
import mx.mc.model.TurnoMedico;

/**
 * @author aortiz
 */
public interface TurnoMedicoService extends GenericCrudService<TurnoMedico, String>{

    public boolean insertarListaTurnos(List<TurnoMedico> listaTurnos) throws Exception;
    
    public boolean actualizarListaTurnos(
            List<TurnoMedico> listaTurnos , String idUsuario)throws Exception;
        
}
