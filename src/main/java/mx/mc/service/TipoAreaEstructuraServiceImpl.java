package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoAreaEstructuraMapper;
import mx.mc.model.TipoAreaEstructura;

/**
 * 
 * @author gcruz
 *
 */

@Service
public class TipoAreaEstructuraServiceImpl extends GenericCrudServiceImpl<TipoAreaEstructura, String> implements TipoAreaEstructuraService {
    
	@Autowired
	private TipoAreaEstructuraMapper tipoAreaEstructuraMapper;
	
	@Autowired
	 public TipoAreaEstructuraServiceImpl(GenericCrudMapper<TipoAreaEstructura, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<TipoAreaEstructura> obtenerTodoByArea(List<Integer> listTipoArea, String area) throws Exception {
        List<TipoAreaEstructura> listaTiposArea = new ArrayList<>();
        try {
                listaTiposArea = tipoAreaEstructuraMapper.obtenerTodoByArea(listTipoArea, area);
        } catch(Exception ex) {
                throw new Exception("Error al obtener los tipos de areas "+ex.getMessage());
        }
        return listaTiposArea;
    }

    @Override
    public TipoAreaEstructura obtenerPorNombre(String nombre) throws Exception {
        try {
                return tipoAreaEstructuraMapper.obtenerPorNombre(nombre);
        } catch(Exception ex) {
                throw new Exception("Error al obtener el tipo de areas "+ex.getMessage());
        }
    }

	@Override
	public TipoAreaEstructura obtenerPorIdTipoArea(Integer idTipoArea) throws Exception {
		try {
            return tipoAreaEstructuraMapper.obtenerPorIdTipoArea(idTipoArea);
		} catch(Exception ex) {
            throw new Exception("Error al obtener el tipo de areas "+ex.getMessage());
		}
	}
	
}
