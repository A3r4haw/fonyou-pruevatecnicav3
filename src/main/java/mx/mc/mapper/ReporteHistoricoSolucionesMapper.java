/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.HistoricoSolucion;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface ReporteHistoricoSolucionesMapper {
    
    List<HistoricoSolucion> obtenerHistoricoSoluciones(@Param("idPrescripcion") String idPrescripcion, @Param("idSurtimiento") String idSurtimiento);
    
}
