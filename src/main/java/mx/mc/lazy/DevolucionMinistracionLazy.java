/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.DevolucionMinistracionExtended;
import mx.mc.model.Estructura;
import mx.mc.service.DevolucionMinistracionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bernabe
 */
public class DevolucionMinistracionLazy extends LazyDataModel<DevolucionMinistracionExtended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DevolucionMinistracionLazy.class);
    private static final long serialVersionUID = 1L;
 
    private transient DevolucionMinistracionService devolucioService;
    private String cadenaBusqueda;
    private String idAlmacen;
    private List<DevolucionMinistracionExtended> devolucionMinisList;
    private int totalReg;
    
    public DevolucionMinistracionLazy(){
        //No code needed in constructor
    }
    
    public DevolucionMinistracionLazy (DevolucionMinistracionService devolucionMinisService, String busqueda,Estructura estructura){
        this.devolucioService=devolucionMinisService;
        this.cadenaBusqueda=busqueda;
        this.idAlmacen=estructura.getIdEstructura();
        this.devolucionMinisList= new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<DevolucionMinistracionExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String,Object> map){
        
        if(devolucionMinisList!=null){
            if(getRowCount()<=0){
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try{
                devolucionMinisList = devolucioService.obtenerBusquedaDevolucionLazy(cadenaBusqueda,idAlmacen,startingAt,maxPerPage, sortField, sortOrder);
            }catch(Exception ex){
                LOGGER.error("Error al realizar la consulta de devolucionMinistracion. {}", ex.getMessage());
                devolucionMinisList= new ArrayList<>();
            }
            setPageSize(maxPerPage);
        }else{
            devolucionMinisList= new ArrayList<>();
        }
        return devolucionMinisList;
    }
    
    private int obtenerTotalResultados(){
        try{
            Long total= devolucioService.obtenerBusquedaDevolucionTotalLazy(cadenaBusqueda,idAlmacen);
            totalReg = total.intValue();
        }catch(Exception ex){
            LOGGER.error("Error al realizar la consulta de devolucionMinistracionTotal. {}", ex.getMessage());
        }
        return totalReg;
    }
    
    @Override
    public DevolucionMinistracionExtended getRowData(String rowKey){
        for(DevolucionMinistracionExtended devo: devolucionMinisList){
            if(devo.getIdDevolucionMinistracion().equals(rowKey)){
                return devo;
            }
        }
        return null;
    }
    
    @Override
    public Object getRowKey(DevolucionMinistracionExtended object){
        if(object == null){
            return null;
        }
        return object;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
    
}
