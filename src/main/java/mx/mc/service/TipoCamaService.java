package mx.mc.service;

import java.util.List;

import mx.mc.model.TipoCama;

/**
 * 
 * @author gcruz
 *
 */
public interface TipoCamaService extends GenericCrudService<TipoCama, String>{
	
    public List<TipoCama> obtenerTodo() throws Exception;
    public TipoCama obteerPorNombre(String nombre) throws Exception;
}
