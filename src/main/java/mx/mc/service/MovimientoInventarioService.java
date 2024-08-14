package mx.mc.service;

import java.util.List;
import mx.mc.model.MovimientoInventario;

/**
 * @author AORTIZ
 */
public interface MovimientoInventarioService extends GenericCrudService<MovimientoInventario, String>{
    public boolean insertarMovimientosInventarios(
            List<MovimientoInventario> listaMovimientos) throws Exception;
}
