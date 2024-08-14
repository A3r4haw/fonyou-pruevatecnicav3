package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Estabilidad;
import mx.mc.model.Estabilidad_Extended;
import mx.mc.model.SurtimientoInsumo_Extend;
import org.apache.ibatis.annotations.Param;
import org.primefaces.model.SortOrder;

/**
 *
 * @author cervanets
 */
public interface EstabilidadMapper extends GenericCrudMapper<Estabilidad, String> {
    
    public List<Estabilidad_Extended> buscarEstabilidad(
            @Param("idInsumo") String idInsumo
            , @Param("claveDiluyente") String claveDiluyente
            , @Param("idViaAdministracion") Integer idViaAdministracion
            , @Param("idFabricante") Integer idFabricante
            , @Param("idContenedor") Integer idContenedor
    ) throws Exception;
    
    public List<Estabilidad_Extended> obtenerListaEstabilidades(@Param("cadenaBusqueda") String cadenaBusqueda, @Param("startingAt") int startingAt, 
            @Param("maxPerPage") int maxPerPage, @Param("sortField") String sortField, @Param("sortOrder") SortOrder sortOrder);
    
    public Long obtenerTotalEstabilidades(@Param("cadenaBusqueda") String cadenaBusqueda);
    
    public Estabilidad_Extended obtenerEstabilidad(
            @Param("idInsumo") String idInsumo
            , @Param("claveDiluyente") String claveDiluyente
            , @Param("idViaAdministracion") Integer idViaAdministracion
            , @Param("idFabricante") Integer idFabricante
    ) throws Exception;
    
    public Estabilidad_Extended obtenerEstabilidadPorId(
            @Param("idEstabilidad") String idEstabilidad
    ) throws Exception;
    
}
