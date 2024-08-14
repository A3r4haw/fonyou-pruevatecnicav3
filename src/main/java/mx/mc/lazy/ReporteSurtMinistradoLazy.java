package mx.mc.lazy; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.service.ReporteMovimientosService;

/**
 * 
 * @author mcalderon
 *
 */
public class ReporteSurtMinistradoLazy extends LazyDataModel<SurtimientoMinistrado_Extend> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteSurtMinistradoLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    
    private List<SurtimientoMinistrado_Extend> listReporteMinistrado;
    private List<String> estructuraList;
    private int totalReg;

    public ReporteSurtMinistradoLazy() {
        //No code needed in constructor
    }

    public ReporteSurtMinistradoLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte,List<String> estructuraList) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listReporteMinistrado = new ArrayList<>();
        this.estructuraList = estructuraList;
    }

    @Override
    public List<SurtimientoMinistrado_Extend> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listReporteMinistrado != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listReporteMinistrado = reporteMovimientosService.consultarMinistraciones(paramBusquedaReporte, estructuraList, startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);
                
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listReporteMinistrado = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listReporteMinistrado = new ArrayList<>();

        }
        return listReporteMinistrado;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteMovimientosService.obtenerTotalRegistrosMinistraciones(paramBusquedaReporte, estructuraList);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public SurtimientoMinistrado_Extend getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(SurtimientoMinistrado_Extend ministrado) {
        if (ministrado == null) {
            return null;
        }
        return ministrado;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

    
}
