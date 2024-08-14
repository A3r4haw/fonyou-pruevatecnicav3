/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.MedControlSurYReab;
import mx.mc.model.ReporteLibroControlados;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface MedControlSurYReabMapper extends GenericCrudMapper<MedControlSurYReab, String> {
    
    List<ReporteLibroControlados> obtenerReporteMedicamentosControlados(
            @Param("parametrosBusqueda") ParamLibMedControlados parametrosBusqueda);
    
}
