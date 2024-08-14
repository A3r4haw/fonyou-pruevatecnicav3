/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.ControlCaducidad;
import mx.mc.model.ParamBusquedaReporte;

/**
 *
 * @author olozada
 */
public interface ControlCaducidadService {
    
    public List<ControlCaducidad> consultarControlCaducidad(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
    
    public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
}
