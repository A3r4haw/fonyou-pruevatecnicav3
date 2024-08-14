/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.model.ReporteCostosHospital;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface CostoHospitalMapper extends GenericCrudMapper<ReporteCostosHospital, String> {
    List<ReporteCostosHospital> obtenerPrimerReporte(@Param("fechaInicio") Date fechaInicio,@Param("fechaFin") Date fechafin); 
}
