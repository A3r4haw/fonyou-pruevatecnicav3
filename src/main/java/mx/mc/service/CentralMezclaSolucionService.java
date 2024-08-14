/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.dto.ParamBusquedaProtocoloDTO;
import mx.mc.dto.RespuestaValidacionSolucionDTO;
import mx.mc.model.ProtocoloExtended;

/**
 *
 * @author gcruz
 */
public interface CentralMezclaSolucionService extends GenericCrudService<ProtocoloExtended, String> {
    
    RespuestaValidacionSolucionDTO buscarProtocosMezclaSolucion(ParamBusquedaProtocoloDTO paramBusquedaProtocoloDTO) throws Exception;;
    
}
