/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;


import java.util.List;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.ParamBusquedaReporte;

/**
 *
 * @author olozada
 */

public interface EnvioNeumaticoService extends GenericCrudService <EnvioNeumatico , String>{
    
    public boolean insertNeumaticTable (EnvioNeumatico neumaticap)throws Exception;
    
    public List<EnvioNeumatico> consultaRepNeumatico (ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
    
    public Long obtenerRepNeumatico(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
    
    List<EnvioNeumatico> obtenerCapsulas(String cadenaBusqueda) throws Exception;
}
