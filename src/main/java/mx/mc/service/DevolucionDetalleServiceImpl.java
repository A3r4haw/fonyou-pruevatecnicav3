package mx.mc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.DevolucionDetalleMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.DevolucionDetalle;
import mx.mc.model.DevolucionDetalleExtended;

/**
 * 
 * @author gcruz
 *
 */
@Service
public class DevolucionDetalleServiceImpl extends GenericCrudServiceImpl<DevolucionDetalle, String> 
						implements DevolucionDetalleService {
    
    @Autowired
    private DevolucionDetalleMapper devolucionDetalleMapper;
	
	@Autowired
	public DevolucionDetalleServiceImpl(GenericCrudMapper<DevolucionDetalle, String> genericCrudMapper) {
		super(genericCrudMapper);
	}

	@Override
	public boolean insertListDevolucionDetalle(List<DevolucionDetalleExtended> listDetalleDevolucion) throws Exception {
		try {
			return devolucionDetalleMapper.insertListDevolucionDetalle(listDetalleDevolucion);
		} catch (Exception ex) {
			throw new Exception("Ocurrio un error al momento de insertar la lista de detalle devolucion!!" + ex.getMessage());
		}
	}

	@Override
	public List<DevolucionDetalleExtended> obtenerListDetalleExtended(String idDevolucion) throws Exception {
		try {
			return devolucionDetalleMapper.obtenerListDetalleExtended(idDevolucion);
		} catch (Exception ex) {
			throw new Exception("Ocurrio un error al momento de obtener la lista de detalle devolucion!!" + ex.getMessage());
		}
	}

	@Override
	public boolean actualizarListDetalleDevolucion(List<DevolucionDetalleExtended> listDetalleDevolucion)
			throws Exception {
		try {
			return devolucionDetalleMapper.actualizarListDetalleDevolucion(listDetalleDevolucion);
		} catch (Exception ex) {
			throw new Exception("Ocurrio un error al momento de actualizar la lista de detalle devolucion!!" + ex.getMessage());
		}
	}

}
