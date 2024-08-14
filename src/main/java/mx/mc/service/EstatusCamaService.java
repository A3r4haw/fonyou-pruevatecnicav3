package mx.mc.service;

import java.util.List;

import mx.mc.model.EstatusCama;

public interface EstatusCamaService extends GenericCrudService<EstatusCama, String> {
	
	public List<EstatusCama> obtenerTodo() throws Exception;
        public EstatusCama obtenerPorNombre(String nombreCama) throws Exception;
        
}
