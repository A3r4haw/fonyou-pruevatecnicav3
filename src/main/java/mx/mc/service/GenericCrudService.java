package mx.mc.service;

import java.util.Date;
import java.util.List;

/**
 *
 * @author hramirez
 */
public interface GenericCrudService<O, K> {

    public O obtener(O o) throws Exception;

    public List<O> obtenerLista(O o) throws Exception;

    public boolean insertar(O o) throws Exception;

    public boolean actualizar(O o) throws Exception;

    public boolean eliminar(O o) throws Exception;
    
    public List<O> obtenerListaTotal(O o) throws Exception;
        
    public boolean insertarLista(List<O> o) throws Exception;
    
    public int obtenerSiguiente() throws Exception;
    
    public boolean actualizarPorId(O o) throws Exception;
    
}
