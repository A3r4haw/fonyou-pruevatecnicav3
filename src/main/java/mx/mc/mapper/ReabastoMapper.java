/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Reabasto;
import mx.mc.model.ReabastoEnviado;
import mx.mc.model.ReabastoExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface ReabastoMapper extends GenericCrudMapper<Reabasto,String> {
    
    List<ReabastoExtended> obtenerReabastoExtends( 
    		@Param("reabasto") Reabasto reabasto
    		, @Param("listEstatusReabasto") List<Integer> listEstatusReabasto
    		, @Param("idTipoAlmacen") Integer idTipoAlmacen
    		);
        
    List<ReabastoExtended> obtenerRegistrosPorCriterioDeBusqueda(
            @Param("textoBusqueda") String textoBusqueda
            , @Param("numRegistros") int numRegistros
            , @Param("listEstatusReabasto") List<Integer> listEstatusReabasto
            , @Param("idEstructuraPadre") String idEstructuraPadre
            , @Param("idEstructura") String idEstructura
            , @Param("idTipoAlmacen") Integer idTipoAlmacen );
    
    List<ReabastoExtended> obtenerRegistrosPorCriterioDeBusquedas(
            @Param("criterioBusqueda") String criterioBusqueda
            , @Param("numRegistros") int numRegistros
            , @Param("listEstatusReabasto") List<Integer> listEstatusReabasto
            , @Param("idEstructuraPadre") String idEstructuraPadre
            , @Param("idEstructura") String idEstructura
            , @Param("idTipoAlmacen") Integer idTipoAlmacen );
    
    List<Reabasto> obtenerBusqueda(@Param("cadena") String cadena,@Param("idEstructura") String almacen); 
    Reabasto obtenerReabastoPorID(@Param("idReabasto") String idReabasto);
    boolean eliminarPorId(@Param("idReabasto") String idReabasto);
    
    List<ReabastoExtended> obtenerReabastoExtendsAdmin( 
    		@Param("reabasto") Reabasto reabasto
    		, @Param("listEstatusReabasto") List<Integer> listEstatusReabasto
    		, @Param("idTipoAlmacen") Integer idTipoAlmacen
    		);
    
    Reabasto getReabastoByFolio(@Param("folio") String folio);
    
    List<Reabasto> obtenerTransferencias(@Param("cadena") String cadena, @Param("idEstructura") String idEstructura);
    
    List<Reabasto> obtenerBusquedaLazy(@Param("cadena") String cadena,@Param("idEstructura") String almacen,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
                        ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder); 
    Long obtenerBusquedaTotalLazy(@Param("cadena") String cadena,@Param("idEstructura") String almacen); 
    
    List<ReabastoExtended> obtenerBusquedaRecepcionLazy(@Param("criterioBusqueda") String cadena,@Param("idEstructura") String almacen,@Param("idEstructuraPadre") String almacenPadre,
                            @Param("tipoAlmacen") int tipoAlmacen,@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
                            , @Param("sortField") String sortField,@Param("sortOrder") String sortOrder); 
    Long obtenerBusquedaRecepcionTotalLazy(@Param("criterioBusqueda") String cadena,@Param("idEstructura") String almacen,@Param("idEstructuraPadre") String almacenPadre,@Param("tipoAlmacen") int tipoAlmacen); 
    
    boolean insertForRefill1(
            @Param("reabasto") Reabasto reabasto,
            @Param("reabastoExtendList") List<ReabastoExtended> reabastoExtendList,
            @Param("reabastoEnviadoList") List<ReabastoEnviado> reabastoEnviadoList
    );
    
    List<ReabastoExtended> obtenerReabastoByFolio(@Param("folio") String folio);      
    
    Long  obtenerBusquedaTotalSurtimientoLazy(@Param("cadena") String cadena, @Param("reabasto") Reabasto reabasto, @Param("listEstatusReabasto") List<Integer> listEstatusReabasto
    		, @Param("idTipoAlmacen") Integer idTipoAlmacen);
    
    List<ReabastoExtended> obtenerBusquedaSurtimientoLazy(@Param("cadena") String cadena, @Param("reabasto") Reabasto reabasto, @Param("listEstatusReabasto") List<Integer> listEstatusReabasto
    		, @Param("idTipoAlmacen") Integer idTipoAlmacen, @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
                , @Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
    
    ReabastoExtended obtenerReabastoExtendedPorID(@Param("idReabasto") String idReabasto);
    
    Long obtenerBusquedaIngresoTotalLazy(@Param("criterioBusqueda") String cadena,@Param("idEstructura") String almacen,@Param("idEstructuraPadre") String almacenPadre,
                @Param("tipoAlmacen") int tipoAlmacen, @Param("listEstatusReabasto") List<Integer> listEstatusReabasto); 
    
    List<ReabastoExtended> obtenerBusquedaIngresoLazy(@Param("criterioBusqueda") String cadena,@Param("idEstructura") String almacen,@Param("idEstructuraPadre") String almacenPadre,
                            @Param("tipoAlmacen") int tipoAlmacen,@Param("listEstatusReabasto") List<Integer> listEstatusReabasto, @Param("startingAt") int startingAt, 
                            @Param("maxPerPage") int maxPerPage, @Param("sortField") String sortField,@Param("sortOrder") String sortOrder); 
    
    ReabastoExtended obtenerReabastoByFolioAlternativo(@Param("folioAlternativo") String folioAlternativo);      
}
