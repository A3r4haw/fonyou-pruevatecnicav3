package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.DevolucionDetalle;
import mx.mc.model.DevolucionDetalleExtended;

/**
 * 
 * @author gcruz
 *
 */
public interface DevolucionDetalleMapper extends GenericCrudMapper<DevolucionDetalle, String> {
	
	public boolean insertListDevolucionDetalle(List<DevolucionDetalleExtended> listDetalleDevolucion);
	
	public List<DevolucionDetalleExtended> obtenerListDetalleExtended(@Param("idDevolucion") String idDevolucion);
	
	public boolean actualizarListDetalleDevolucion(List<DevolucionDetalleExtended> listDetalleDevolucion);
}
