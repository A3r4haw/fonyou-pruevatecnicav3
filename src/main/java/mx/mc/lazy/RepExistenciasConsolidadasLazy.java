package mx.mc.lazy; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Medicamento_Extended;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.mc.service.RepExistenciasConsolidadasService;

/**
 * 
 * @author olozada
 *
 */
public class RepExistenciasConsolidadasLazy extends LazyDataModel<Medicamento_Extended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepExistenciasConsolidadasLazy.class);
    private static final long serialVersionUID = 1L;

    private transient RepExistenciasConsolidadasService repExistenciasConsolidadasService;    
    private Medicamento_Extended medicamento_Extended;
    private List<Medicamento_Extended> listmedicamento_Extended;
    private int totalReg;

    public RepExistenciasConsolidadasLazy() {
        //No code needed in constructor
    }

    public RepExistenciasConsolidadasLazy(RepExistenciasConsolidadasService repExistenciasConsolidadasService, Medicamento_Extended medicamento_Extended) {
        this.repExistenciasConsolidadasService = repExistenciasConsolidadasService;
        this.medicamento_Extended = medicamento_Extended;
        listmedicamento_Extended = new ArrayList<>();
    }

    @Override
    public List<Medicamento_Extended> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listmedicamento_Extended != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (medicamento_Extended.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listmedicamento_Extended = repExistenciasConsolidadasService.obtenerReporteEntregaExist_Consolidadas(medicamento_Extended,
                        startingAt, maxPerPage);
                medicamento_Extended.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listmedicamento_Extended = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listmedicamento_Extended = new ArrayList<>();

        }
        return listmedicamento_Extended;
    }

    private int obtenerTotalResultados() {
        try {
            if (medicamento_Extended != null) {
                Long total = repExistenciasConsolidadasService.obtenerTotalReporteEntregaExist_Consolidadas(medicamento_Extended);

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
    public Medicamento_Extended getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(Medicamento_Extended object) {
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
