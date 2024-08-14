package mx.mc.service;

import java.util.List;

import mx.mc.model.TipoAlmacen;

/**
 * 
 * @author gcruz
 *
 */
public interface TipoAlmacenService extends GenericCrudService<TipoAlmacen, String>{

	List<TipoAlmacen> obtenerTiposAlmacen()throws Exception;
        TipoAlmacen obtenerPorNombre(String nombre) throws Exception;
}
