/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Estructura;
import mx.mc.model.VistaRecepcionMedicamento;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface VistaRecepcionMedicamentoMapper  extends GenericCrudMapper<VistaRecepcionMedicamento,String> {
    List<VistaRecepcionMedicamento> obtenerBusqueda(@Param("cadena") String cadena);  
    VistaRecepcionMedicamento obtenerByFolioPrescripcion(@Param("folioPrescripcion") String  folioPrescripcion);
    VistaRecepcionMedicamento obtenerPrescripcionForDev(@Param("folioPrescripcion") String  folioPrescripcion);
    
    List<VistaRecepcionMedicamento> obtenerSurtimientosPorRecibir(@Param("idsEstructura") List<String> idsEstructura);
    List<VistaRecepcionMedicamento> obtenerSurtimientosRecibidos(@Param("idsEstructura") List<String> idsEstructura);
    List<VistaRecepcionMedicamento> obtenerSurtimientosCancelados(@Param("idsEstructura") List<String> idsEstructura);
    List<VistaRecepcionMedicamento> obtenerSurtimientosPorRecibirV2(@Param("lista") List<Estructura> lista);
    
    List<VistaRecepcionMedicamento> obtenerSurtimientosGabinetes(@Param("lista") List<Estructura> lista);
}
