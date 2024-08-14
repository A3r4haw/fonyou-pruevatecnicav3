/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Estructura;
import mx.mc.model.ReabastoExtended;
import mx.mc.service.ReabastoService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bbautista
 */
public class RecepcionReabastoLazy extends LazyDataModel<ReabastoExtended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecepcionReabastoLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient ReabastoService reabastoService;
    private List<ReabastoExtended> reabastoList;
    private String idAlmacen;
    private String idAlmacenPadre;
    private int tipoAlmacen;
    private String cadenaBusqueda;
    private int totalReg;
    
    public RecepcionReabastoLazy() {
        //No code needed in constructor
    }
    
    public RecepcionReabastoLazy (ReabastoService reabastoService, String busqueda,Estructura estructura,int tAlmacen){
        this.reabastoService=reabastoService;
        this.cadenaBusqueda=busqueda;
        this.idAlmacen=estructura.getIdEstructura();
        this.idAlmacenPadre=estructura.getIdEstructuraPadre();
        this.tipoAlmacen=tAlmacen;
        this.reabastoList= new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<ReabastoExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map){
        
        if(reabastoList != null){
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try{
                reabastoList = reabastoService.obtenerBusquedaRecepcionLazy(cadenaBusqueda, idAlmacen,idAlmacenPadre,tipoAlmacen, startingAt, maxPerPage, sortField, sortOrder);
                
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
            Long total = reabastoService.obtenerBusquedaRecepcionTotalLazy(cadenaBusqueda, idAlmacen,idAlmacenPadre,tipoAlmacen);
           totalReg = total.intValue();
           
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }
    
    @Override
    public ReabastoExtended getRowData(String rowKey) {
        for(ReabastoExtended pac : reabastoList){
            if(pac.getIdReabasto().equals(rowKey)){
                return pac;
            }
        
        }
        return null;
    }

    @Override
    public Object getRowKey(ReabastoExtended object) {
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

