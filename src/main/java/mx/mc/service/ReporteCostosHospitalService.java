/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.Date;
import mx.mc.model.ReporteCostosHospital;
import java.util.List;
/**
 *
 * @author bbautista
 */
public interface ReporteCostosHospitalService extends GenericCrudService<ReporteCostosHospital, String> {
    public List<ReporteCostosHospital> obtenerDatosReporte(Date fechaInicio,Date fechafin) throws Exception;
    
}
