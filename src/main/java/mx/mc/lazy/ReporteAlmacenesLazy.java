package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.DataResultAlmacenes;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ReporteMovimientosService;

public class ReporteAlmacenesLazy extends LazyDataModel<DataResultAlmacenes> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteAlmacenesLazy.class);

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<DataResultAlmacenes> listDataResultAlmacenes;
    private int totalReg;

    public ReporteAlmacenesLazy() {
        //No code needed in constructor
    }

    public ReporteAlmacenesLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listDataResultAlmacenes = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<DataResultAlmacenes> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listDataResultAlmacenes != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                    listDataResultAlmacenes = reporteMovimientosService.consultarAlmacenes(paramBusquedaReporte, startingAt, maxPerPage);
            } catch (Exception ex) {                
                LOGGER.error("Error al realizar la consulta de reporteAlmacenes. {}", ex.getMessage());
                listDataResultAlmacenes = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listDataResultAlmacenes = new ArrayList<>();

        }
        return listDataResultAlmacenes;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteMovimientosService.obtenerTotalAlmacenes(paramBusquedaReporte);

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
    public DataResultAlmacenes getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(DataResultAlmacenes object) {
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
