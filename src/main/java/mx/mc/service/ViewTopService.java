package mx.mc.service;

import java.util.List;
import mx.mc.model.ViewTop;

/**
 * @author hramirez
 */
public interface ViewTopService extends GenericCrudService<ViewTop, String>{

    public List<ViewTop> obtenerTopMedico() throws Exception;

    public List<ViewTop> obtenerTopPaciente() throws Exception;

    public List<ViewTop> obtenerTopMedicamento() throws Exception;

    public List<ViewTop> obtenerTopServicio() throws Exception;
    
    public List<ViewTop> obtenerTopNivel() throws Exception;
	
}
