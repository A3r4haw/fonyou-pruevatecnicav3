package mx.mc.service;

import java.util.List;

import mx.mc.model.TipoAreaEstructura;

/**
 * 
 * @author gcruz
 *
 */
public interface TipoAreaEstructuraService extends GenericCrudService<TipoAreaEstructura, String>  {

    public List<TipoAreaEstructura> obtenerTodoByArea(List<Integer> listTipoArea, String area) throws Exception;
    public TipoAreaEstructura obtenerPorNombre(String nombre) throws Exception;
    
    public TipoAreaEstructura obtenerPorIdTipoArea(Integer idTipoArea) throws Exception;
}
