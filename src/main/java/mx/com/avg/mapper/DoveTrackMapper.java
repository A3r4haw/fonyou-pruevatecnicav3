/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.avg.mapper;


import java.util.List;
import mx.com.avg.model.DoveTrack;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Ulai
 */
public interface DoveTrackMapper extends GenericCrudMapper<DoveTrack, String> {
    
    
    List<DoveTrack> getDoveTrack(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    List<Estructura> getAllEstructura(@Param("listTiposAreaEstructura") List<Integer> listTiposAreaEstructura);
        
}