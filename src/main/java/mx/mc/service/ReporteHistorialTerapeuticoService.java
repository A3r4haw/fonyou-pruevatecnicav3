/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ReporteTerapeutico;
/**
 *
 * @author olozada
 */
public interface ReporteHistorialTerapeuticoService {
    
    public List<ReporteTerapeutico> constultaRepTerapeutico (ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
    
    public Long obtenerRepTerapeutico(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
        

}
