package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.MovimientoInventarioMapper;
import mx.mc.model.MovimientoInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AORTIZ
 */
@Service
public class MovimientoInventarioServiceImplements extends GenericCrudServiceImpl<MovimientoInventario, String> implements MovimientoInventarioService {
    
    @Autowired
    private MovimientoInventarioMapper movimientoInventarioMapper;    

    @Autowired
    public MovimientoInventarioServiceImplements(GenericCrudMapper<MovimientoInventario, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean insertarMovimientosInventarios(
            List<MovimientoInventario> listaMovimientos) throws Exception {
        try {
            return movimientoInventarioMapper.
                    insertarMovimientosInventarios(listaMovimientos);
        } catch (Exception e) {
            throw new Exception("Error obtenerCantidadMedicamento. " + e.getMessage());
        }
    }
}
