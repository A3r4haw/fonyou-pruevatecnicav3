package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoAlmacenMapper;
import mx.mc.model.TipoAlmacen;
/**
 * 
 * @author gcruz
 *
 */
@Service
public class TipoAlmacenServiceImpl extends GenericCrudServiceImpl<TipoAlmacen, String> implements TipoAlmacenService {

	@Autowired
	private TipoAlmacenMapper tipoAlmacenMapper;
	
	@Autowired
	private TipoAlmacenServiceImpl(GenericCrudMapper<TipoAlmacen, String> genericCrudMapper) {
		super(genericCrudMapper);
	}
	
	@Override
	public List<TipoAlmacen> obtenerTiposAlmacen() throws Exception {
		List<TipoAlmacen> listTipoAlmacen = new ArrayList<>();
		try {
			listTipoAlmacen = tipoAlmacenMapper.obtenerTiposAlmacen();
		} catch (Exception e) {
			throw new Exception("Ocurrio un error al listar los tipos de almacenes " + e.getMessage());
		}
		return listTipoAlmacen;
	}

    @Override
    public TipoAlmacen obtenerPorNombre(String nombre) throws Exception {
        try {
            return tipoAlmacenMapper.obtenerPorNombre(nombre);
        } catch (Exception e) {
                throw new Exception("Ocurrio un error al listar los tipos de almacenes " + e.getMessage());
        }
    }

}
