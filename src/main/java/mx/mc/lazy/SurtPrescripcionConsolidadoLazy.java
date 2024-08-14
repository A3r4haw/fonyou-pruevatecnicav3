package mx.mc.lazy; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.DataResultConsolidado;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ReporteMovimientosService;

/**
 * 
 * @author mcalderon
 *
 */
public class SurtPrescripcionConsolidadoLazy extends LazyDataModel<DataResultConsolidado> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurtPrescripcionConsolidadoLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<DataResultConsolidado> listReporteConsolidados;
    private int totalReg;

    public SurtPrescripcionConsolidadoLazy() {
        //No code needed in constructor
    }

    public SurtPrescripcionConsolidadoLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listReporteConsolidados = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<DataResultConsolidado> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listReporteConsolidados != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listReporteConsolidados = reporteMovimientosService.obtenerSurtPrescripcionConsolidado(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Surtimiento de Prescripción Consolidado. {}", ex.getMessage());
                listReporteConsolidados = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listReporteConsolidados = new ArrayList<>();

        }
        return listReporteConsolidados;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteMovimientosService.obtenerTotalSurtPrescripcionConsolidado(paramBusquedaReporte);

                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total de Consolidado. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public DataResultConsolidado getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(DataResultConsolidado movimiento) {
        if (movimiento == null) {
            return null;
        }
        return movimiento;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

    
}
