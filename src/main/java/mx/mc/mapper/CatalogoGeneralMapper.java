package mx.mc.mapper;

import java.util.List;
import mx.mc.model.CatalogoGeneral;
import org.apache.ibatis.annotations.Param;

/**
 * @author AORTIZ
 */
public interface CatalogoGeneralMapper extends GenericCrudMapper<CatalogoGeneral, String>{
    public List<CatalogoGeneral> obtenerCatalogosPorGrupo(@Param("idGrupo") int idGrupo) throws Exception;
    List<CatalogoGeneral> obtenerTiposInsumos(@Param("idGrupo") int idGrupo,@Param("idEstructura") String idEstructura) throws Exception;
}   
