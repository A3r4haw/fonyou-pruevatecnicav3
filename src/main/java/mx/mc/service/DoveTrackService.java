/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.com.avg.model.DoveTrack;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;
/**
 *
 * @author Ulai
 */
public interface DoveTrackService extends GenericCrudService<DoveTrack,String> {
    
    List<DoveTrack> getDoveTrack(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
    
    List<Estructura> getAllEstructura(List<Integer> listTiposAreaEstructura) throws Exception;
    
}