/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DevolucionMinistracionDetalle;
import mx.mc.model.DevolucionMinistracionDetalleExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface DevolucionMinistracionDetalleMapper extends GenericCrudMapper<DevolucionMinistracionDetalle,String> {
    public boolean insertarLista(List<DevolucionMinistracionDetalle> devolucion);
    public boolean actualizarLista(List<DevolucionMinistracionDetalle> devolucion);
    public boolean eliminarRegistradas(String idDevolucionMinistracion);
    public List<DevolucionMinistracionDetalle> obtenerListaByIdDevolucionMinistracion(@Param("idDevolucionMinistracion") String idDevolucionMinistracion);
    public List<DevolucionMinistracionDetalleExtended> obtenerListaByIdDevolucionMinistracionExtended(
            DevolucionMinistracionDetalleExtended parametros);
    
    List<DevolucionMinistracionDetalleExtended> devolucionMiniDetalleExtend(@Param("idPrescripcion") String idPrescripcion);
    List<DevolucionMinistracionDetalleExtended> obtenerDevolucionDetalleExtPorIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
}
