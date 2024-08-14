package mx.mc.mapper;

import java.util.List;
import mx.mc.model.OrdenCompra;
import mx.mc.model.OrdenCompra_Extended;
import org.apache.ibatis.annotations.Param;

/**
 * @author AORTIZ
 */
public interface OrdenCompraMapper extends GenericCrudMapper<OrdenCompra, String>{
    
    List<OrdenCompra_Extended> obtenerUltimosRegistrosOrdenCompra(
            @Param("numRegistros") int numRegistros);
    
    List<OrdenCompra_Extended> obtenerRegistrosPorCriterioDeBusqueda(
            @Param("criterioBusqueda") String criterioBusqueda
            , @Param("numRegistros") int numRegistros);
    
    OrdenCompra_Extended obtenerOrdenCompra(@Param("idOrdenCompra") String idOrdenCompra);
    
    boolean actualizarOrdenCompra(OrdenCompra_Extended ordenCompra);
    
    List<OrdenCompra_Extended> obtenerOrdenesCompraLazzy(@Param("cadenaBusqueda") String cadena, @Param("idEstructura") String idEstructura,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
                        ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder); 
    
    Long obtenerBusquedaTotalLazy(@Param("cadenaBusqueda") String cadena,  @Param("idEstructura") String idEstructura); 
    
}
