/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.text.SimpleDateFormat;
import java.util.List;
import mx.com.avg.mapper.DoveTrackMapper;
import mx.com.avg.model.DoveTrack;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Estructura;
import mx.mc.model.ParamBusquedaReporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ulai
 */
@Service
public class DoveTrackServiceImpl extends GenericCrudServiceImpl<DoveTrack, String> implements DoveTrackService {
    
//    @Autowired
    private DoveTrackMapper doveMapper;

    @Autowired
    public DoveTrackServiceImpl(GenericCrudMapper<DoveTrack, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<DoveTrack> getDoveTrack(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            paramBusquedaReporte.setFechaConvertInicio(format.format(paramBusquedaReporte.getFechaInicio()));
            paramBusquedaReporte.setFechaConvertFin(format.format(paramBusquedaReporte.getFechaFin()));
            return doveMapper.getDoveTrack(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo :: obtener    " + ex.getMessage());
        }
    };
    
    
    @Override
    public List<Estructura> getAllEstructura(List<Integer> listTiposAreaEstructura) throws Exception{
        try {
            return doveMapper.getAllEstructura(listTiposAreaEstructura);
        } catch (Exception ex) {
            throw new Exception("Error en el metodo :: obtener lista de estructuras  " + ex.getMessage());
        }
    
    }
    
}
