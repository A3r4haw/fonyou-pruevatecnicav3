/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.service.EstructuraAlmacenServicioService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bbautista
 */
public class AlmacenServicioLazy extends LazyDataModel<EstrucAlmacenServicio_Extend>  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlmacenServicioLazy.class);
    private static final long serialVersionUID = 1L;
    
    private EstructuraAlmacenServicioService almacenServicioService;
    private List<EstrucAlmacenServicio_Extend> almacenServicioList;
    private String idEntidadHosp;
    private int totalReg;
    
    
    public AlmacenServicioLazy(EstructuraAlmacenServicioService almacenService,String idEntidad) {
        almacenServicioService=almacenService;  
        almacenServicioList= new ArrayList<>();
        idEntidadHosp=idEntidad;
    }
    
    @Override
    public List<EstrucAlmacenServicio_Extend> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {  
        if(almacenServicioList!=null){
            //if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados(map);
                setRowCount(total);
            //}
            try {                                    
                almacenServicioList=  almacenServicioService.obtenerAlmacenServicios(idEntidadHosp,startingAt,maxPerPage,sortField, sortOrder,map);
            } catch (Exception ex) {
                LOGGER.error("Error en obtenerAlmacenServicios. {}", ex.getMessage());
            }

            setPageSize(maxPerPage);
        }else{
            almacenServicioList= new ArrayList<>();
        }
        
        return almacenServicioList;
    }
    
    private int obtenerTotalResultados(Map<String, Object> map) {
        try {
            Long total = Long.MIN_VALUE;            
            total = almacenServicioService.obtenerTotalAlmacenServicios(idEntidadHosp,map);
            totalReg = total.intValue();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public EstrucAlmacenServicio_Extend getRowData(String rowKey) {
        for (EstrucAlmacenServicio_Extend surt: almacenServicioList ) {
            if(surt.getIdAlmacenServicio().equals(rowKey)){
                return surt;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(EstrucAlmacenServicio_Extend almacenServicio) {
        if (almacenServicio == null) {
            return null;
        }
        return almacenServicio;
    }
    
    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
    
}
