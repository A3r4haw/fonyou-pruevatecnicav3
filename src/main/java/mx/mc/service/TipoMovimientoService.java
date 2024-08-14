package mx.mc.service;

import mx.mc.model.TipoMovimiento;

/**
 * 
 * @author gcruz
 *
 */
public interface TipoMovimientoService extends GenericCrudService<TipoMovimiento, String> {
    
      public TipoMovimiento obtenerTipoMovmientoById(int idTipoMovimiento) throws Exception;
}
