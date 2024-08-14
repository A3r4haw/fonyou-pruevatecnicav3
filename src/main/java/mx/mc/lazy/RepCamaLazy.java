/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.DataResultReport;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.RepCamaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bbautista
 */
public class RepCamaLazy extends LazyDataModel<DataResultReport> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteMovimientosLazy.class);
    private static final long serialVersionUID = 1L;
    
    private ParamBusquedaReporte paramReporte;
    private List<DataResultReport> listDataResultReporte;
    private transient RepCamaService repCamaService;         
    private List<String> listaEst;
    private int totalReg;
    
    public RepCamaLazy() {
        //No code needed in constructor
    }
    
    public RepCamaLazy(RepCamaService camaService ,ParamBusquedaReporte param, List<String> estructuraList){
        repCamaService = camaService;
        paramReporte= param;
        listDataResultReporte= new ArrayList<>();
        listaEst = estructuraList;
    }
    
    
    @Override
    public List<DataResultReport> load(int startingAt, int maxPerPage, String sortField, SortOrder so, Map<String, Object> map){
        if(listDataResultReporte != null){
            if(getRowCount() <=0 ){
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if(paramReporte.isNuevaBusqueda()) {
                        startingAt = 0;
                }
                String order = so == SortOrder.ASCENDING ? "asc" : so == SortOrder.DESCENDING ? "desc" : null;
                paramReporte.setSortField(sortField);
                paramReporte.setSortOrder(order);
                listDataResultReporte = repCamaService.consultarDisponibilidadCamas(paramReporte, listaEst, startingAt, maxPerPage, sortField, so);
                paramReporte.setNuevaBusqueda(false);	        	 

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteCamas. {}", ex.getMessage());
                listDataResultReporte = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        }else{
            listDataResultReporte = new ArrayList<>();
        }
        return listDataResultReporte;
    }
    
    private int obtenerTotalResultados() {
        try {
                if (paramReporte != null) {
                   Long total = repCamaService.obtenerTotalRegistrosCamas(paramReporte,listaEst);                   

                   totalReg = total.intValue();
               } else {
                   totalReg = 0;
               }		        
        } catch(Exception ex) {
                LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }	     
        return totalReg; 
    }
   
    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
        
}
