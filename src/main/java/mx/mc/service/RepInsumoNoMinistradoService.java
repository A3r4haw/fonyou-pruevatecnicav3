/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;


import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.RepInsumoNoMinistrado;


/**
 *
 * @author olozada
 */

public interface RepInsumoNoMinistradoService extends GenericCrudService <RepInsumoNoMinistrado , String>{
    
public List<RepInsumoNoMinistrado> obtenerListaInsumo(String cadenaBusqueda) throws Exception;

public List<RepInsumoNoMinistrado> consultaInsumoNoMinistrados (ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;

public Long obtenerTotalInsumoNoMinistrado(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
}
