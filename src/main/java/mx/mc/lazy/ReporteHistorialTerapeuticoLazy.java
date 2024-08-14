package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ReporteTerapeutico;
import mx.mc.service.ReporteHistorialTerapeuticoService;

/**
 *
 * @author olozada
 */
public class ReporteHistorialTerapeuticoLazy extends LazyDataModel<ReporteTerapeutico> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteHistorialTerapeuticoLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteHistorialTerapeuticoService reporteHistorialTerapeuticoService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<ReporteTerapeutico> listReporteTerapeutico;
    private int totalReg;

    public ReporteHistorialTerapeuticoLazy() {
        //No code needed in constructor
    }

    public ReporteHistorialTerapeuticoLazy(ReporteHistorialTerapeuticoService reporteHistorialTerapeuticoService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteHistorialTerapeuticoService = reporteHistorialTerapeuticoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listReporteTerapeutico = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<ReporteTerapeutico> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listReporteTerapeutico != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();   
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listReporteTerapeutico = reporteHistorialTerapeuticoService.constultaRepTerapeutico(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta. {}", ex.getMessage());
                listReporteTerapeutico = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listReporteTerapeutico = new ArrayList<>();

        }
        return listReporteTerapeutico;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteHistorialTerapeuticoService.obtenerRepTerapeutico(paramBusquedaReporte);

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
    public ReporteTerapeutico getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ReporteTerapeutico object) {
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
