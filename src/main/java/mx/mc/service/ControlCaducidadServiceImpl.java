/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.ControlCaducidadMapper;
import mx.mc.model.ControlCaducidad;
import mx.mc.model.ParamBusquedaReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author olozada
 */

@Service
public class ControlCaducidadServiceImpl implements ControlCaducidadService {
  
    @Autowired
    private ControlCaducidadMapper controlCaducidadMapper;
  
    public ControlCaducidadServiceImpl(){
        //No code needed in constructor
    }
  
  @Override
    public List<ControlCaducidad> consultarControlCaducidad(ParamBusquedaReporte paramBusquedaReporte, int startingAt,
            int maxPerPage) throws Exception {
        try {
            return controlCaducidadMapper.consultarControlCaducidad(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de Control Caducidades" + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return controlCaducidadMapper.obtenerTotalRegistros(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de Control Caducidades" + ex.getMessage());
        }
    } 
}