package mx.mc.service;

import java.util.List;

import mx.mc.model.Devolucion;
import mx.mc.model.DevolucionExtended;
import mx.mc.model.Estructura;
import mx.mc.model.Inventario;
import mx.mc.model.MovimientoInventario;

/**
 * 
 * @author gcruz
 *
 */
public interface DevolucionService extends GenericCrudService<Devolucion, String>{
	
	public boolean insertarDevolucion(DevolucionExtended devolucionExtended, List<Inventario> listInventario) throws Exception;
        
        public boolean insertarDevolucionRea(DevolucionExtended devolucionExtended, List<Inventario> listInventario,List<MovimientoInventario> listMovimientos) throws Exception;

	public List<DevolucionExtended> obtenerListDevExtended(Devolucion devolucion) throws Exception;
	
	public boolean actualizarDevolucion(DevolucionExtended devolucionExtended) throws Exception;
	
	public boolean insertarDevolucionAndDetalle(DevolucionExtended devolucionExtended) throws Exception;
	
	public boolean ingresarDevolucion(DevolucionExtended devolucionExtended, List<Inventario> listInventarios,
								List<MovimientoInventario> listMovimientoInventario) throws Exception;
	public boolean insertarDevolucionInsumoAlmacenServicio(DevolucionExtended devolucionExtended, 
                List<Inventario> listInventario,
                List<MovimientoInventario> listMovimientos,
                Estructura estrOrigen) throws Exception;
}
