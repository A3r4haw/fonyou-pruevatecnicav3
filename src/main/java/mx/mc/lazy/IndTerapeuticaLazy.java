/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.IndTerapeuticaExtended;
import mx.mc.service.IndTerapeuticaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class IndTerapeuticaLazy extends LazyDataModel<IndTerapeuticaExtended> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IndTerapeuticaLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient IndTerapeuticaService indTerapeuticaService;
    private String cadenaBusqueda;
    private List<IndTerapeuticaExtended> listaIndicacionesTerapeuticas;
    
    private int totalReg;
    
    public IndTerapeuticaLazy() {
        
    }
    
    public IndTerapeuticaLazy(IndTerapeuticaService indTerapeuticaService, String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
        this.indTerapeuticaService = indTerapeuticaService;
        this.listaIndicacionesTerapeuticas = new ArrayList<>();
        
    }
    
    @Autowired
    @Override
    public List<IndTerapeuticaExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        
        if(listaIndicacionesTerapeuticas != null) {
             if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
	        int total = obtenerTotalResultados();   
	        setRowCount(total);
	    }
	    try {
                listaIndicacionesTerapeuticas = indTerapeuticaService.obtenerIndicacionesTerapeuticas(cadenaBusqueda, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {
	        LOGGER.error("Error al obtener la lista de interacciones medicamentosas", ex.getMessage());
	        listaIndicacionesTerapeuticas = new ArrayList<>();
	    }
        } else {
             listaIndicacionesTerapeuticas = new ArrayList<>();
        }
        return listaIndicacionesTerapeuticas;
    }
    
     private int obtenerTotalResultados() {
	try {            
            Long total = indTerapeuticaService.obtenerTotalInteracciones(cadenaBusqueda);
            totalReg = total.intValue();           
               
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }	     
        return totalReg; 
    }
     
    @Override
    public IndTerapeuticaExtended getRowData(String rowKey) {
        for(IndTerapeuticaExtended indTerapeutica:listaIndicacionesTerapeuticas) {
            if(indTerapeutica.getIdIndTerapeutica().equals(rowKey)){
                return indTerapeutica;
	    }
        }
	return null;
    }

    @Override
    public Object getRowKey(IndTerapeuticaExtended object) {
        if( object == null ) {
            return null;
        }            
	return object.getIdIndTerapeutica();
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
}
