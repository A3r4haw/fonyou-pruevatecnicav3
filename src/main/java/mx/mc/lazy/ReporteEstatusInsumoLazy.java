package mx.mc.lazy; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.ReporteEstatusInsumo;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ReporteMovimientosService;

/**
 * 
 * @author mcalderon
 *
 */
public class ReporteEstatusInsumoLazy extends LazyDataModel<ReporteEstatusInsumo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteEstatusInsumoLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<ReporteEstatusInsumo> listEstatusInsumo;
    private int totalReg;

    public ReporteEstatusInsumoLazy() {
        //No code needed in constructor
    }

    public ReporteEstatusInsumoLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listEstatusInsumo = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<ReporteEstatusInsumo> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listEstatusInsumo != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listEstatusInsumo = reporteMovimientosService.consultarEstatusInsumos(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listEstatusInsumo = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listEstatusInsumo = new ArrayList<>();

        }
        return listEstatusInsumo;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteMovimientosService.obtenerTotalEstatusInsumos(paramBusquedaReporte);

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
    public ReporteEstatusInsumo getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ReporteEstatusInsumo estatus) {
        if (estatus == null) {
            return null;
        }
        return estatus;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

    
}
