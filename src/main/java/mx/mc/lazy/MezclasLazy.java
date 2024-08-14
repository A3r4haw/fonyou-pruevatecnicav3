package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Mezcla;
import mx.mc.model.ParamBusquedaReporte;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.service.ReporteMovimientosService;

/**
 *
 * @author cervanets
 *
 */
public class MezclasLazy extends LazyDataModel<Mezcla> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MezclasLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;

    private transient List<Mezcla> listaMeclas;
    private int totalReg;

    public MezclasLazy() {
        //No code needed in constructor
    }

    public MezclasLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.listaMeclas = new ArrayList<>();
    }

    @Override
    public List<Mezcla> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        LOGGER.trace("mx.mc.lazy.ConsumosLazy.load()");
        if (this.listaMeclas != null) {
            if (getRowCount() <= 0) {
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (this.paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                this.paramBusquedaReporte.setOrden(sortOrder);
                this.paramBusquedaReporte.setSortField(sortField);
                this.paramBusquedaReporte.setStartingAt(startingAt);
                this.paramBusquedaReporte.setMaxPerPage(maxPerPage);
                if(paramBusquedaReporte.getIdEstatusSolucion() != null) {
                    switch (paramBusquedaReporte.getIdEstatusSolucion()) {
                        case 2:
                        case 4:
                        case 5:    
                        case 6:
                        case 7:  
                        case 9:    
                        case 10:   
                        case 11: 
                        case 12: 
                        case 13:    
                        case 14:
                        case 15:    
                        case 16: 
                        case 20:     
                        case 26:    
                            this.listaMeclas = this.reporteMovimientosService
                                .obtenerMezclasByEstatusSol(this.paramBusquedaReporte);
                            break;
                        default:
                            this.listaMeclas = this.reporteMovimientosService
                                .obtenerMezclas(this.paramBusquedaReporte);
                    }
                } else
                    this.listaMeclas = this.reporteMovimientosService
                                .obtenerMezclas(this.paramBusquedaReporte);
                
                this.paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de consumos. :: {} ", ex.getMessage());
                this.listaMeclas = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            this.listaMeclas = new ArrayList<>();

        }
        return this.listaMeclas;
    }

    private int obtenerTotalResultados() {
        try {
            Long total = 0L;
            if (this.paramBusquedaReporte != null) {
                if(paramBusquedaReporte.getIdEstatusSolucion() != null) {
                    switch (paramBusquedaReporte.getIdEstatusSolucion()) {
                        case 2:
                        case 4:
                        case 5: 
                        case 6: 
                        case 7:   
                        case 9:    
                        case 10:
                        case 11: 
                        case 12: 
                        case 13:    
                        case 14:    
                        case 15:    
                        case 16: 
                        case 20:     
                        case 26:    
                            total = this.reporteMovimientosService
                                .obtenerTotalMezclasEstatusSol(this.paramBusquedaReporte);
                            break;
                        default:
                            total = this.reporteMovimientosService
                                .obtenerTotalMezclas(this.paramBusquedaReporte);
                    }
                } else
                    total = this.reporteMovimientosService.obtenerTotalMezclas(paramBusquedaReporte);
                this.totalReg = total.intValue();
            } else {
                this.totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el total de consumos. :: {} ", ex.getMessage());
        }
        return this.totalReg;
    }

    @Override
    public Mezcla getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(Mezcla mezcla) {
        if (mezcla == null) {
            return null;
        }
        return mezcla;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

}
