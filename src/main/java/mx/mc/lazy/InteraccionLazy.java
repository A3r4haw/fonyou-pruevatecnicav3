/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.InteraccionExtended;
import mx.mc.service.InteraccionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author gcruz
 */
public class InteraccionLazy extends LazyDataModel<InteraccionExtended> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InteraccionLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient InteraccionService interaccionService;
    private Integer idTipoInteraccion;
    private String cadenaBusqueda;
    private List<InteraccionExtended> listaInteracciones;
    
            
    private int totalReg;
    
    public InteraccionLazy() {
        
    }
    
    public InteraccionLazy(InteraccionService interaccionService, Integer idTipoInteraccion, String cadenaBusqueda) {
        this.interaccionService = interaccionService;
        this.idTipoInteraccion = idTipoInteraccion;
        this.cadenaBusqueda = cadenaBusqueda;
        this.listaInteracciones = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<InteraccionExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {

	if (listaInteracciones != null) {			 
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
	        int total = obtenerTotalResultados();   
	        setRowCount(total);
	    }
	    try {
	        
	        listaInteracciones = interaccionService.obtenerInteracciones(cadenaBusqueda, idTipoInteraccion, startingAt, maxPerPage, sortField, sortOrder);	                	 
	             
	    } catch (Exception ex) {
	        LOGGER.error("Error al obtener la lista de interacciones medicamentosas", ex.getMessage());
	        listaInteracciones = new ArrayList<>();
	    }

	    setPageSize(maxPerPage);
	} else {
	    listaInteracciones = new ArrayList<>();

	}
	return listaInteracciones;
    }
    
    private int obtenerTotalResultados() {
	try {            
            Long total = interaccionService.obtenerTotalInteracciones(cadenaBusqueda, idTipoInteraccion);
            totalReg = total.intValue();           
               
        } catch(Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }	     
        return totalReg; 
    }
    
    @Override
    public InteraccionExtended getRowData(String rowKey) {
        for(InteraccionExtended interaccion:listaInteracciones) {
            if(interaccion.getIdInteraccion().equals(rowKey)){
                return interaccion;
	    }
        }
	return null;
    }

    @Override
    public Object getRowKey(InteraccionExtended object) {
        if( object == null ) {
            return null;
        }            
	return object.getIdInteraccion();
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
    
}