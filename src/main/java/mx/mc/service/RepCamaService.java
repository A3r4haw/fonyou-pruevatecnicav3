/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.DataResultReport;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.RepCama;
import org.primefaces.model.SortOrder;

/**
 *
 * @author bbautista
 */
public interface RepCamaService extends GenericCrudService<RepCama, String> {

    public List<DataResultReport> consultarDisponibilidadCamas(ParamBusquedaReporte paramReporte,List<String>listaEstructuras ,int startingAt, 
            int maxPerPage,String sortField,SortOrder sortOrder) throws Exception;    
    
    public Long obtenerTotalRegistrosCamas(ParamBusquedaReporte paramReporte,List<String>listaEstructuras) throws Exception;
}
