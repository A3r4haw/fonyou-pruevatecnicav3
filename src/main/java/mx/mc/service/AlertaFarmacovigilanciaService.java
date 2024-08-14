/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.dto.ParamBusquedaAlertaDTO;
import mx.mc.dto.RespuestaAlertasDTO;
import mx.mc.model.AlertaFarmacovigilancia;

/**
 *
 * @author gcruz
 */
public interface AlertaFarmacovigilanciaService extends GenericCrudService<AlertaFarmacovigilancia, String> {
    
    RespuestaAlertasDTO buscarAlertasFarmacovigilancia(ParamBusquedaAlertaDTO paramBusquedaAlerta) throws Exception;
    
}
