package mx.mc.mapper;

import java.util.List;


/**
 *
 * @auohor hramirez
 */
public interface GenericCrudMapper<O, K> {

    public O obtener(O o);
    
    public List<O> obtenerLista(O o);
    
    public boolean insertar(O o);

    public boolean actualizar(O o);

    public boolean eliminar(O o);
    
    public List<O> obtenerListaTotal(O o);
    
    
    public boolean insertarLista(List<O> o);
    
    public int obtenerSiguiente();
    
    public boolean actualizarPorId(O o);

}
