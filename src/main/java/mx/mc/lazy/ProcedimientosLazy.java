package mx.mc.lazy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ProcedimientoExtended;
import mx.mc.service.ProcedimientoService;

public class ProcedimientosLazy extends LazyDataModel<ProcedimientoExtended> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcedimientosLazy.class);

    private ParamBusquedaReporte paramBusquedaReporte;
    private int totalReg;
    
    private transient ProcedimientoService procedimientoService;
    private List<ProcedimientoExtended> procedimientoList;
    private int tipoProcedimiento;

    public ProcedimientosLazy() {
        //No code needed in constructor
    }

    public ProcedimientosLazy(ProcedimientoService procedimientoService, ParamBusquedaReporte paramBusquedaReporte, int tipoProcedimiento) {
        this.procedimientoService = procedimientoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        procedimientoList = new ArrayList<>();
        this.tipoProcedimiento = tipoProcedimiento;
    }

    @Override
    public List<ProcedimientoExtended> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {
        if (procedimientoList != null) {
            if (getRowCount() <= 0) {
                LOGGER.info("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
                paramBusquedaReporte.setSortOrder(order);
                paramBusquedaReporte.setSortField(sortField);
                paramBusquedaReporte.setSortOrder(order);
                procedimientoList = procedimientoService.obtenerBusquedaProcedimiento(paramBusquedaReporte, tipoProcedimiento, startingAt, maxPerPage, sortField, sortOrder);
                paramBusquedaReporte.setNuevaBusqueda(false);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Procedimientos. {}", ex.getMessage());
                procedimientoList = new ArrayList<>();
            }
            setPageSize(maxPerPage);
        } else {
            procedimientoList = new ArrayList<>();
        }
        return procedimientoList;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = procedimientoService.obtenerTotalBusquedaProcedimiento(paramBusquedaReporte, tipoProcedimiento);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total de Procedimientos. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public ProcedimientoExtended getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ProcedimientoExtended object) {
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
