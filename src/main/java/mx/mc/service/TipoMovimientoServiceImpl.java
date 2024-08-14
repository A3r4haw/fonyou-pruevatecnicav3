package mx.mc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoMovimientoMapper;
import mx.mc.model.TipoMovimiento;

/**
 *
 * @author gcruz
 *
 */
@Service
public class TipoMovimientoServiceImpl extends GenericCrudServiceImpl<TipoMovimiento, String> implements TipoMovimientoService {
    
    @Autowired
    private TipoMovimientoMapper tipoMovimientoMapper;

    @Autowired
    public TipoMovimientoServiceImpl(GenericCrudMapper<TipoMovimiento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public TipoMovimiento obtenerTipoMovmientoById(int idTipoMovimiento) throws Exception {
        try {
            return tipoMovimientoMapper.obtenerTipoMovmientoById(idTipoMovimiento);
        } catch (Exception e) {
            throw  new Exception("Error al obtener el tipoMovimiento " + e.getMessage());
        }
    }

    
}
