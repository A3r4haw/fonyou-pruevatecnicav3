package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ViewTopMapper;
import mx.mc.model.ViewTop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hramirez
 *
 */
@Service
public class ViewTopServiceImpl extends GenericCrudServiceImpl<ViewTop, String> implements ViewTopService {

    @Autowired
    private ViewTopMapper viewTopMapper;

    @Autowired
    public ViewTopServiceImpl(GenericCrudMapper<ViewTop, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<ViewTop> obtenerTopMedico() throws Exception {
        try {
            return viewTopMapper.obtenerTopMedico();
        } catch (Exception ex) {
            throw new Exception("Error al obtenerTopMedico. " + ex.getMessage());
        }
    }

    @Override
    public List<ViewTop> obtenerTopPaciente() throws Exception {
        try {
            return viewTopMapper.obtenerTopPaciente();
        } catch (Exception ex) {
            throw new Exception("Error al obtenerTopPaciente. " + ex.getMessage());
        }
    }

    @Override
    public List<ViewTop> obtenerTopMedicamento() throws Exception {
        try {
            return viewTopMapper.obtenerTopMedicamento();
        } catch (Exception ex) {
            throw new Exception("Error al obtenerTopMedicamento. " + ex.getMessage());
        }
    }

    @Override
    public List<ViewTop> obtenerTopServicio() throws Exception {
        try {
            return viewTopMapper.obtenerTopServicio();
        } catch (Exception ex) {
            throw new Exception("Error al obtenerTopServicio. " + ex.getMessage());
        }
    }

    @Override
    public List<ViewTop> obtenerTopNivel() throws Exception {
        try {
            return viewTopMapper.obtenerTopNivel();
        } catch (Exception ex) {
            throw new Exception("Error al obtenerTopNivel. " + ex.getMessage());
        }
    }

}
