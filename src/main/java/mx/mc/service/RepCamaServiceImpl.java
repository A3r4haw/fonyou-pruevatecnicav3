/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.RepCamaMapper;
import mx.mc.model.DataResultReport;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.RepCama;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class RepCamaServiceImpl extends GenericCrudServiceImpl<RepCama, String> implements RepCamaService {
   
    @Autowired
    private RepCamaMapper camaMapper;
    
    @Autowired
    public RepCamaServiceImpl(GenericCrudMapper<RepCama, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<DataResultReport> consultarDisponibilidadCamas(ParamBusquedaReporte paramReporte, List<String> listaEstructuras, int startingAt, int maxPerPage,
                                                                                    String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
           return camaMapper.consultarDisponibilidadCamas(paramReporte, listaEstructuras, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
                throw new Exception("Error al obtener total de registros de reporte disponibilidad de Camas" +e.getMessage());
        }
    }
    
    @Override
    public Long obtenerTotalRegistrosCamas(ParamBusquedaReporte paramReporte, List<String> listaEstructuras) throws Exception {
        try {
            return camaMapper.obtenerTotalRegistrosCamas(paramReporte, listaEstructuras);
        } catch(Exception ex) {
                throw new Exception("Error al obtener total de registros de reporte disponibilidad de Camas" +ex.getMessage());
        }
    }
    
}
