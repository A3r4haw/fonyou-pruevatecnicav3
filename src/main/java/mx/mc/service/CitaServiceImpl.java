package mx.mc.service;

import mx.mc.mapper.CitaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Cita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author aortiz
 */
@Service
public class CitaServiceImpl extends GenericCrudServiceImpl<Cita, String> implements CitaService {

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    public CitaServiceImpl(GenericCrudMapper<Cita, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public Cita obtenerById(String idCita) throws Exception {
        try {
            return citaMapper.obtenerById(idCita);
        } catch (Exception e) {
            throw new Exception("Error al obtener la cita. en obtenerById " + e.getMessage());
        }
    }

    @Override
    public boolean updateCita(Cita cita) throws Exception {
        try {
            return citaMapper.updateCita(cita);
        } catch (Exception e) {
            throw new Exception("Error al Actualizar la cita. en updateCita" + e.getMessage());
        }
    }

    @Override
    public boolean cancelCita(String idCita) throws Exception {
        try {
            return citaMapper.cancelCitaById(idCita);
        } catch (Exception e) {
            throw new Exception("Error al Cancelar la cita. " + e.getMessage());
        }
    }

    @Override
    public Cita existeCita(Cita cita) throws Exception {
        try {
            return citaMapper.existeCita(cita);
        } catch (Exception e) {
            throw new Exception("Error al Actualizar la cita. en existeCita" + e.getMessage());
        }
    }

    @Override
    public boolean actualizarEstatusCitas(Cita cita) throws Exception {
        try {
            return citaMapper.actualizarEstatusCitas(cita);
        } catch (Exception e) {
            throw new Exception("Error al Actualizar la cita. en actualizarEstatusCitas" + e.getMessage());
        }
    }
}
