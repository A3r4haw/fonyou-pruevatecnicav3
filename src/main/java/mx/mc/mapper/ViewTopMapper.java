package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ViewTop;

/**
 * @author hramirez
 */
public interface ViewTopMapper extends GenericCrudMapper<ViewTop, String> {

    public List<ViewTop> obtenerTopMedico();

    public List<ViewTop> obtenerTopPaciente();

    public List<ViewTop> obtenerTopMedicamento();

    public List<ViewTop> obtenerTopServicio();
    
    public List<ViewTop> obtenerTopNivel();

}
