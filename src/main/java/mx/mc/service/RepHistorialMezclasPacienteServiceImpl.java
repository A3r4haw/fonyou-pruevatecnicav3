/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.RepHistorialMezclasPacienteMapper;
import mx.mc.model.RepHistoricoMezclasPaciente;
import mx.mc.model.ParamBusquedaReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class RepHistorialMezclasPacienteServiceImpl implements RepHistorialMezclasPacienteService {
    
    @Autowired
    private RepHistorialMezclasPacienteMapper repHistorialMezclasPacienteMapper;

    public RepHistorialMezclasPacienteServiceImpl() {
        
    }
    
    @Override
    public List<RepHistoricoMezclasPaciente> consultarHistoricoMezclasPaciente(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception {
        try {
            return repHistorialMezclasPacienteMapper.consultarHistoricoMezclasPaciente(paramBusquedaReporte, startingAt, maxPerPage);
        } catch(Exception ex) {
            throw new Exception("Error al obtener consulta de reporte historico de mezclas de paciente" + ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return repHistorialMezclasPacienteMapper.obtenerTotalRegistros(paramBusquedaReporte);
        } catch(Exception ex) {
            throw new Exception("Error al obtener total de historico de mezclas de paciente" + ex.getMessage());
        }
    }
    
}
