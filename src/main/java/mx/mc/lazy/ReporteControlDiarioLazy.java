package mx.mc.lazy; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.ReporteLibroControlados;
import mx.mc.service.ReporteMovimientosService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author mcalderon
 *
 */
public class ReporteControlDiarioLazy extends LazyDataModel<ReporteLibroControlados> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteControlDiarioLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;    
    private ParamLibMedControlados paramLibMedControlados;
    private List<ReporteLibroControlados> listControlDiario;
    private int totalReg;

    public ReporteControlDiarioLazy() {
        //No code needed in constructor
    }

    public ReporteControlDiarioLazy(ReporteMovimientosService reporteMovimientosService, ParamLibMedControlados paramLibMedControlados) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramLibMedControlados = paramLibMedControlados;
        listControlDiario = new ArrayList<>();
     }

    
    @Autowired
    @Override
    public List<ReporteLibroControlados> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listControlDiario != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramLibMedControlados.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listControlDiario = reporteMovimientosService.obtenerReporteMedicamentosControlDiario(paramLibMedControlados,
                        startingAt, maxPerPage);
                paramLibMedControlados.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listControlDiario = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listControlDiario = new ArrayList<>();

        }
        return listControlDiario;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramLibMedControlados != null) {
                Long total = reporteMovimientosService.obtenerTotalReporteMedicamentosControlDiario(paramLibMedControlados);

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
    public ReporteLibroControlados getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ReporteLibroControlados object) {
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
