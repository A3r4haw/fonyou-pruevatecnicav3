package mx.mc.mapper;

import java.util.List;

import mx.mc.model.Devolucion;
import mx.mc.model.DevolucionExtended;

/**
 * 
 * @author gcruz
 *
 */
public interface DevolucionMapper extends GenericCrudMapper<Devolucion, String> {

	public List<DevolucionExtended> obtenerListDevExtended(Devolucion devolucion);
	
	public boolean actualizarEstatusDevolucion(Devolucion devolucion);
}
