/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.CamaExtended;
import mx.mc.model.EstrucAlmacenServicio_Extend;
import mx.mc.service.CamaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bbautista
 */
public class ServicioCamasLazy extends LazyDataModel<CamaExtended>  {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteMovimientosLazy.class);
    private static final long serialVersionUID = 1L;
    private List<CamaExtended> camaExtendedList;
    private int totalReg;
    private String idEntidadHosp;
    private CamaService camaService;
    
    public ServicioCamasLazy() {
    }

    public ServicioCamasLazy(CamaService camaService,String idEntidad) {
        this.camaService = camaService;
        camaExtendedList=new ArrayList<>();
        idEntidadHosp = idEntidad;
    }
    
    @Override
    public List<CamaExtended> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {  
        if(camaExtendedList!=null){
            //if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados(map);
                setRowCount(total);
            //}
            try {
                camaExtendedList=  camaService.obtenerServicioCamas(idEntidadHosp,startingAt, maxPerPage,sortField, sortOrder,map);
            } catch (Exception ex) {
                LOGGER.error("Error en obtenerAlmacenServicios. {}", ex.getMessage());
            }

            setPageSize(maxPerPage);
        }else{
            camaExtendedList = new ArrayList<>();
        }
        return camaExtendedList;
    }
    
    private int obtenerTotalResultados(Map<String, Object> map) {
        try {
            Long total = Long.MIN_VALUE;            
            total = camaService.obtenerTotalServicioCamas(idEntidadHosp,map);
            totalReg = total.intValue();
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public CamaExtended getRowData(String rowKey) {
        for (CamaExtended cama: camaExtendedList ) {
            if(cama.getIdCama().equals(rowKey)){
                return cama;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(CamaExtended camaExtend) {
        if (camaExtend == null) {
            return null;
        }
        return camaExtend;
    }
    
    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
}
