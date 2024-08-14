/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.ReporteHistoricoSolucionesMapper;
import mx.mc.model.HistoricoSolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class ReporteHistoricoSolucionesServiceImpl implements ReporteHistoricoSolucionesService {

    @Autowired
    private ReporteHistoricoSolucionesMapper reporteHistoricoSolucionesMapper;
    
    @Override
    public List<HistoricoSolucion> obtenerHistoricoSoluciones(String idPrescripcion, String idSurtimiento) throws Exception {
        try {
            return reporteHistoricoSolucionesMapper.obtenerHistoricoSoluciones(idPrescripcion, idSurtimiento);
            
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al buscar historico de soluciones:  " + ex.getMessage());
        }
    }
    
}
