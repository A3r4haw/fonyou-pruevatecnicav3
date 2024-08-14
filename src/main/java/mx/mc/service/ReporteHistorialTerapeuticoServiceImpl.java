/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.ReporteHistorialTerapeuticoMapper;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ReporteTerapeutico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author olozada
 */
@Service
public class ReporteHistorialTerapeuticoServiceImpl implements ReporteHistorialTerapeuticoService {
  
    @Autowired
    private ReporteHistorialTerapeuticoMapper reporteHistorialTerapeuticoMapper;
  
    public ReporteHistorialTerapeuticoServiceImpl(){
        //No code needed in constructor
    }
    

    @Override
    public List<ReporteTerapeutico> constultaRepTerapeutico(ParamBusquedaReporte paramBusquedaReporte, int startingAt, 
            int maxPerPage) throws Exception {
        try {
            return reporteHistorialTerapeuticoMapper.constultaRepTerapeutico(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de reporte " + ex.getMessage());
        }
    }


    @Override
    public Long obtenerRepTerapeutico(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return reporteHistorialTerapeuticoMapper.obtenerRepTerapeutico(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de registros " + ex.getMessage());
        }
    }

  
    }
    

