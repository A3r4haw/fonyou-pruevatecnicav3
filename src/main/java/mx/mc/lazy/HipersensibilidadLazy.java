/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.HipersensibilidadExtended;
import mx.mc.service.HipersensibilidadService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class HipersensibilidadLazy extends LazyDataModel<HipersensibilidadExtended> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(HipersensibilidadLazy.class);
    
    private transient HipersensibilidadService hipersensibilidadService;
    private String cadenaBusqueda;
    private List<HipersensibilidadExtended> listaHipersensibilidad;
    
    private int totalReg;
     
    public HipersensibilidadLazy() {
         
    }
    
    public HipersensibilidadLazy(HipersensibilidadService hipersensibilidadService, String cadenaBusqueda) {
        this.hipersensibilidadService = hipersensibilidadService;
        this.cadenaBusqueda = cadenaBusqueda;
        this.listaHipersensibilidad = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<HipersensibilidadExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        if(listaHipersensibilidad != null) {
             if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
	        int total = obtenerTotalResultados();   
	        setRowCount(total);
	    }
	    try {
                listaHipersensibilidad = hipersensibilidadService.obtenerReaccionesHipersensibilidad(cadenaBusqueda, startingAt, maxPerPage, sortField, sortOrder);
            }catch (Exception ex) {
	        LOGGER.error("Error al obtener la lista de reacciones de hipersensibilidad:  ", ex.getMessage());
	        listaHipersensibilidad = new ArrayList<>();
	    }
        } else {
            listaHipersensibilidad = new ArrayList<>();
        }
        return listaHipersensibilidad;
    }
    
    
     private int obtenerTotalResultados() {
	try {      
            Long total = hipersensibilidadService.obtenerTotalReacionesHipersensibilidad(cadenaBusqueda);
            totalReg = total.intValue();
            
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
     }
    
    @Override
    public HipersensibilidadExtended getRowData(String rowKey) {
        for(HipersensibilidadExtended hipersensibilidad:listaHipersensibilidad) {
            if(hipersensibilidad.getIdHipersensibilidad().equals(rowKey)){
                return hipersensibilidad;
	    }
        }
	return null;
    } 
     
    @Override
    public Object getRowKey(HipersensibilidadExtended object) {
        if( object == null ) {
            return null;
        }            
	return object.getIdHipersensibilidad();
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
    
}
