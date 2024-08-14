/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.DevolucionMezclaDetalle;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.model.SurtimientoInsumo_Extend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface DevolucionMezclaDetalleMapper  extends GenericCrudMapper<DevolucionMezclaDetalle,String>{
    public boolean insertListDetalleMezcla(List<DevolucionMezclaDetalle> listDetalleDevolucion);
    public List<SurtimientoInsumo_Extend> detalleDevolucionMezcla(@Param("idDevolucionMezcla") String idDevolucionMezcla);    
}
