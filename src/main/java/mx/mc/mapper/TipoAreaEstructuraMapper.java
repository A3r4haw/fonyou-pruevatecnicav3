package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.TipoAreaEstructura;

/**
 * 
 * @author gcruz
 *
 */
public interface TipoAreaEstructuraMapper extends GenericCrudMapper<TipoAreaEstructura, String> {

    List<TipoAreaEstructura> obtenerTodoByArea(@Param("listTipoArea") List<Integer> listTipoArea, @Param("area") String area);
    TipoAreaEstructura obtenerPorNombre(@Param("nombre") String nombre);
    TipoAreaEstructura obtenerPorIdTipoArea(@Param("idTipoArea") Integer idTipoArea) throws Exception;    
}
