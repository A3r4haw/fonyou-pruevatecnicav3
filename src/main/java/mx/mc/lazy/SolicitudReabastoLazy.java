/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Reabasto;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import mx.mc.service.ReabastoService;

/**
 *
 * @author bbautista
 */
public class SolicitudReabastoLazy extends LazyDataModel<Reabasto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudReabastoLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient ReabastoService reabastoService;
    private List<Reabasto> reabastoList;
    private String idAlmacen;
    private String cadenaBusqueda;
    private int totalReg;
    
    public SolicitudReabastoLazy() {
        //No code needed in constructor
    }
    
    public SolicitudReabastoLazy (ReabastoService reabastoService, String busqueda,String idAlmacen){
        this.reabastoService=reabastoService;
        this.idAlmacen=idAlmacen;
        this.cadenaBusqueda=busqueda;
        this.reabastoList= new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<Reabasto> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map){
        
        if(reabastoList != null){
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try{
                reabastoList = reabastoService.obtenerBusquedaLazy(cadenaBusqueda, idAlmacen, startingAt, maxPerPage, sortField, sortOrder);
                
            }catch(Exception ex){
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                reabastoList= new ArrayList<>();
            }
        
            setPageSize(maxPerPage);
        }else{
            reabastoList= new ArrayList<>();
        }
        return reabastoList;
    } 
    private int obtenerTotalResultados(){
        
        try{
            Long total = reabastoService.obtenerBusquedaTotalLazy(cadenaBusqueda, idAlmacen);
           totalReg = total.intValue();
           
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }
    
    @Override
    public Reabasto getRowData(String rowKey) {
        for(Reabasto pac : reabastoList) {
            if(pac.getIdReabasto().equals(rowKey)){
                return pac;
            }
        }       
        return null;
    }

    @Override
    public Object getRowKey(Reabasto object) {
        if (object == null) {
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
