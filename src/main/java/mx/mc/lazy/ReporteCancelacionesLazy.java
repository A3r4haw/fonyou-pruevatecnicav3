package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.DataResultVales;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ReporteMovimientosService;

/**
 *
 * @author gcruz
 *
 */
public class ReporteCancelacionesLazy extends LazyDataModel<DataResultVales> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteCancelacionesLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<DataResultVales> listCancelaciones;
    private int totalReg;

    public ReporteCancelacionesLazy() {
        //No code needed in constructor
    }

    public ReporteCancelacionesLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listCancelaciones = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<DataResultVales> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listCancelaciones != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listCancelaciones = reporteMovimientosService.consultarCancelaciones(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteCancelaciones. {}", ex.getMessage());
                listCancelaciones = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listCancelaciones = new ArrayList<>();

        }
        return listCancelaciones;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteMovimientosService.obtenerTotalCancelaciones(paramBusquedaReporte);

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
    public Object getRowKey(DataResultVales cancelacion) {
        if (cancelacion == null) {
            return null;
        }
        return cancelacion;
    }
    
    @Override
    public DataResultVales getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

    
}
