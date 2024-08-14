package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoCamaMapper;
import mx.mc.model.TipoCama;

 /**
  * 
  * @author gcruz
  *
  */
@Service
public class TipoCamaServiceImpl extends GenericCrudServiceImpl<TipoCama, String> implements TipoCamaService {
	
	@Autowired
	private TipoCamaMapper tipoCamaMapper;
        
	@Autowired
	public TipoCamaServiceImpl(GenericCrudMapper<TipoCama, String> genericCrudMapper) {
		super(genericCrudMapper);
	}

	public List<TipoCama> obtenerTodo() throws Exception {
		List<TipoCama> listTipoCama = new ArrayList<>();
		try {
			listTipoCama = tipoCamaMapper.obtenerTodo();
		} catch (Exception e) {
			throw new Exception("Error al obtener los tipos de camas: "+e.getMessage());
		}
		return listTipoCama;
	}

    @Override
    public TipoCama obteerPorNombre(String nombre) throws Exception {
        try {
            return tipoCamaMapper.obtenerPorNombre(nombre);
        } catch (Exception e) {
                throw new Exception("Error al obtener los tipos de camas: "+e.getMessage());
        }
    }

}
