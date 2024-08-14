package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.EstatusCamaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EstatusCama;

/**
 * 
 * @author gcruz
 *
 */

@Service
public class EstatusCamaServiceImpl extends GenericCrudServiceImpl<EstatusCama, String> implements EstatusCamaService {
    
    @Autowired
    private EstatusCamaMapper estatusCamaMapper;
    
    @Autowired
    public EstatusCamaServiceImpl(GenericCrudMapper<EstatusCama, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
	 
	public List<EstatusCama> obtenerTodo() throws Exception {
		List<EstatusCama> listEstatusCama = new ArrayList<>();
		try {
			listEstatusCama = estatusCamaMapper.obtenerTodo();
		} catch (Exception ex) {
			throw new Exception("Error al obtener los estatus de cama: "+ex.getMessage());
		}
		return listEstatusCama;
	}

    @Override
    public EstatusCama obtenerPorNombre(String nombreCama) throws Exception {
        try{
            return estatusCamaMapper.obtenerPorNombre(nombreCama);
        }catch(Exception ex){
            throw new Exception("Error al obtener los datos del estatus de la cama: "+ex.getMessage());
        }
    }
	 
}
