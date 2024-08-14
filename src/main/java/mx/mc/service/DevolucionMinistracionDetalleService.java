/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DevolucionMinistracionDetalle;
import mx.mc.model.DevolucionMinistracionDetalleExtended;

/**
 *
 * @author bbautista
 */
public interface DevolucionMinistracionDetalleService 
        extends GenericCrudService<DevolucionMinistracionDetalle,String> {
    List<DevolucionMinistracionDetalle> obtenerListaDetalle(String idDevolucionMinistracion) throws Exception;
    
    List<DevolucionMinistracionDetalleExtended> obtenerListaDetalleExtended(
           DevolucionMinistracionDetalleExtended parametros,String idSurtimiento) throws Exception;
    
    List<DevolucionMinistracionDetalleExtended> obtenerListaDetalleExt(String idDevolucionMinistracion) throws Exception;
    List<DevolucionMinistracionDetalleExtended> devolucionMiniDetallePorIdPrescripcion(String idPrescripcion) throws Exception;
}
