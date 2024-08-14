package mx.mc.service;

import java.util.Date;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TurnoMapper;
import mx.mc.model.Turno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 *
 */
@Service
public class TurnoServiceImpl extends GenericCrudServiceImpl<Turno, String> implements TurnoService {

    @Autowired
    private TurnoMapper turnoMapper;
    
    @Autowired
    public TurnoServiceImpl(GenericCrudMapper<Turno, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public Turno obtenerPorHora(Date hora) throws Exception {
        try {
            return turnoMapper.obtenerPorHora(hora);
        } catch (Exception ex) {
            throw new Exception("Error al buscar turno por hora :: " + ex.getMessage());
        }
    }
}
