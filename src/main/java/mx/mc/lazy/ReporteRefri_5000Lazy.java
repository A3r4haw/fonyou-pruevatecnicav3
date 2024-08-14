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

/**
 * 
 * @author mcalderon
 *
 */
public class ReporteRefri_5000Lazy extends LazyDataModel<ReporteLibroControlados> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteRefri_5000Lazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;    
    private ParamLibMedControlados paramLibMedControlados;
    private List<ReporteLibroControlados> listClaves5000Refrig;
    private int totalReg;

    public ReporteRefri_5000Lazy() {
        //No code needed in constructor
    }

    public ReporteRefri_5000Lazy(ReporteMovimientosService reporteMovimientosService, ParamLibMedControlados paramLibMedControlados) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramLibMedControlados = paramLibMedControlados;
        listClaves5000Refrig = new ArrayList<>();
    }

    @Override
    public List<ReporteLibroControlados> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listClaves5000Refrig != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramLibMedControlados.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listClaves5000Refrig = reporteMovimientosService.obtenerReporteMedicamentosRefri5000(paramLibMedControlados,
                        startingAt, maxPerPage);
                paramLibMedControlados.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listClaves5000Refrig = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listClaves5000Refrig = new ArrayList<>();

        }
        return listClaves5000Refrig;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramLibMedControlados != null) {
                Long total = reporteMovimientosService.obtenerTotalReporteMedicamentosRefri5000(paramLibMedControlados);

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
