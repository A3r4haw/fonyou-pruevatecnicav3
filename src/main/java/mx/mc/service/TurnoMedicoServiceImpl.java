package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TurnoMedicoMapper;
import mx.mc.model.TurnoMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 *
 */
@Service
public class TurnoMedicoServiceImpl extends GenericCrudServiceImpl<TurnoMedico, String> implements TurnoMedicoService {

    @Autowired
    private TurnoMedicoMapper turnoMedicoMapper;

    @Autowired
    public TurnoMedicoServiceImpl(GenericCrudMapper<TurnoMedico, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean insertarListaTurnos(List<TurnoMedico> listaTurnos) throws Exception {
        try {
            turnoMedicoMapper.insertarListaTurnos(listaTurnos);
            return true;
        } catch (Exception ex) {
            throw new Exception("Error insertarListaTurnos. " + ex.getMessage());
        }
    }
    
    @Transactional (propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean actualizarListaTurnos(
            List<TurnoMedico> listaTurnos , String idUsuario)throws Exception {
        try {
            turnoMedicoMapper.eliminarByIdUsuario(idUsuario);
            turnoMedicoMapper.insertarListaTurnos(listaTurnos);
            return true;
        } catch (Exception ex) {
            throw new Exception("Error actualizarListaTurnos. " + ex.getMessage());
        }
    }
    
}
