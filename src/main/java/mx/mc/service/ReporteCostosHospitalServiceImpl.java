/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.mc.mapper.CostoHospitalMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.ReporteCostosHospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class ReporteCostosHospitalServiceImpl extends GenericCrudServiceImpl<ReporteCostosHospital, String> implements ReporteCostosHospitalService {
    
    @Autowired
    private CostoHospitalMapper costoHospitalMap;

    @Autowired
    public ReporteCostosHospitalServiceImpl(GenericCrudMapper<ReporteCostosHospital, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
        
    
    @Override
    public List<ReporteCostosHospital> obtenerDatosReporte(Date fechaInicio,Date fechafin) throws Exception {
        List<ReporteCostosHospital> costoHospitalList = new ArrayList<>();        
        List<ReporteCostosHospital> auxList = new ArrayList<>();
        ReporteCostosHospital reporte= new ReporteCostosHospital();
        try{
            auxList = costoHospitalMap.obtenerPrimerReporte(fechaInicio,fechafin);
            int periodo=0;
            int i=0;
            double suma=0;
            for(ReporteCostosHospital repo: auxList){
                                
                switch(repo.getMes().split("-")[1]){
                    case "01": reporte.setMes1(repo.getMontoporservicio()); break;
                    case "02": reporte.setMes2(repo.getMontoporservicio()); break;
                    case "03": reporte.setMes3(repo.getMontoporservicio()); break;
                    case "04": reporte.setMes4(repo.getMontoporservicio()); break;
                    case "05": reporte.setMes5(repo.getMontoporservicio()); break;
                    case "06": reporte.setMes6(repo.getMontoporservicio()); break;
                    case "07": reporte.setMes7(repo.getMontoporservicio()); break;
                    case "08": reporte.setMes8(repo.getMontoporservicio()); break;
                    case "09": reporte.setMes9(repo.getMontoporservicio()); break;
                    case "10": reporte.setMes10(repo.getMontoporservicio()); break;
                    case "11": reporte.setMes11(repo.getMontoporservicio()); break;
                    case "12": reporte.setMes12(repo.getMontoporservicio()); break;
                    default:
                }
                
                periodo = repo.getPeriodo();
                suma += repo.getMontoporservicio();
                
                if(!repo.getPeriodo().equals(periodo) && i > 0){
                    reporte.setPeriodo(periodo);
                    reporte.setMontoporservicio(suma);
                    costoHospitalList.add(reporte);
                    reporte= new ReporteCostosHospital();
                    suma=0;
                    periodo=0;
                }
                i++;
            }
            if(reporte!=null){
                reporte.setPeriodo(periodo);
                reporte.setMontoporservicio(suma);
                costoHospitalList.add(reporte);
            }
        }catch(Exception ex){
            throw new Exception("Error al obtener los datos del Reporte");
        }
        return costoHospitalList;
    }
    
}
