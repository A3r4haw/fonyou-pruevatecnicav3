/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ReaccionExtend;
import mx.mc.service.ReaccionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bbautista
 */
public class ReaccionAdversaLazy extends LazyDataModel<ReaccionExtend> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionSolucionLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReaccionService reaccionService;
    private ParamBusquedaReporte paramBusquedaReporte;      
    private Date fechaProgramada = new java.util.Date();
    private Integer estatusReaccionAdv;
    
    private List<ReaccionExtend> ReaccionExtendList;
    
    private int totalReg;

    public ReaccionAdversaLazy() {
        //No code needed in constructor
    }

    public ReaccionAdversaLazy(ReaccionService reaccionService, ParamBusquedaReporte paramBusquedaReporte, Integer EstatusReaccionAdv) {
        this.reaccionService = reaccionService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.estatusReaccionAdv = EstatusReaccionAdv;        
        ReaccionExtendList = new ArrayList<>();
    }

    @Override
    public List<ReaccionExtend> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {    
        if (ReaccionExtendList != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                ReaccionExtendList = reaccionService.obtenerReaccionesListLazy(paramBusquedaReporte, estatusReaccionAdv, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {                
                LOGGER.error("Error al realizar la consulta de Soluciones. {}", ex.getMessage());
                ReaccionExtendList = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            ReaccionExtendList = new ArrayList<>();

        }
        return ReaccionExtendList;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {                              
                Long total = reaccionService.obtenerTotalReaccionesListLazy(paramBusquedaReporte, estatusReaccionAdv);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.info("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public ReaccionExtend getRowData(String rowKey) {
        for (ReaccionExtend surt: ReaccionExtendList ) {
            if(surt.getIdReaccion().equals(rowKey)){
                return surt;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(ReaccionExtend reaccion) {
        if (reaccion == null) {
            return null;
        }
        return reaccion;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
}
