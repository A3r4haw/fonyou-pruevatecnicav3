package mx.mc.service;

import java.util.List;

import mx.mc.model.DevolucionDetalle;
import mx.mc.model.DevolucionDetalleExtended;

/**
 * 
 * @author gcruz
 *
 */
public interface DevolucionDetalleService extends GenericCrudService<DevolucionDetalle, String> {
	
	public boolean insertListDevolucionDetalle(List<DevolucionDetalleExtended> listDetalleDevolucion) throws Exception;
	
	public List<DevolucionDetalleExtended> obtenerListDetalleExtended(String idDevolucion) throws Exception;
	
	public boolean actualizarListDetalleDevolucion(List<DevolucionDetalleExtended> listDetalleDevolucion) throws Exception;

}
