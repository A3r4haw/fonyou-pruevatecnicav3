package mx.mc.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoJustificacionMapper;
import mx.mc.model.TipoJustificacion;

/**
 *
 * @author hramirez
 *
 */
@Service
public class TipoJustificacionServiceImpl extends GenericCrudServiceImpl<TipoJustificacion, String> implements TipoJustificacionService {

    @Autowired
    private TipoJustificacionMapper tipoJustificacionMapper;

    @Autowired
    public TipoJustificacionServiceImpl(GenericCrudMapper<TipoJustificacion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<TipoJustificacion> obtenerActivosPorListId (boolean activa, List<Integer> listTipoJustificacion) throws Exception {
        try {
            return tipoJustificacionMapper.obtenerActivosPorListId(activa, listTipoJustificacion);
        } catch (Exception ex) {
            throw new Exception("Error listar Justificacion de no surtimiento de prescripciones. " + ex.getMessage());
        }
    }
}
