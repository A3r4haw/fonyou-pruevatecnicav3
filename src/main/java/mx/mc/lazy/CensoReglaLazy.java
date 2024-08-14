package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.CensoReglaExtended;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.CensoReglaService;


/**
 *
 * @author apalacios
 *
 */
public class CensoReglaLazy extends LazyDataModel<CensoReglaExtended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CensoReglaLazy.class);
    private static final long serialVersionUID = 1L;

    private ParamBusquedaReporte paramBusquedaReporte;    
    private int totalReg;    
    private transient CensoReglaService censoReglaService;
    private List<CensoReglaExtended> listaCensoReglas;

    public CensoReglaLazy() {
        //No code needed in constructor
    }

    public CensoReglaLazy(CensoReglaService censoReglaService, ParamBusquedaReporte paramBusquedaReporte) {
        this.censoReglaService = censoReglaService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listaCensoReglas = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<CensoReglaExtended> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {
        if (listaCensoReglas != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                  listaCensoReglas = censoReglaService.obtenerRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte, startingAt, maxPerPage);
                  paramBusquedaReporte.setNuevaBusqueda(true);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Reglas de Censo de Pacientes. {}", ex.getMessage());
                listaCensoReglas = new ArrayList<>();
            }
            setPageSize(maxPerPage);
        } else {
            listaCensoReglas = new ArrayList<>();
        }
        return listaCensoReglas;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = censoReglaService.obtenerTotalRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte);

                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total de Censo Pacientes, {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public CensoReglaExtended getRowData(String rowKey) {
        for (CensoReglaExtended rule : listaCensoReglas) {
            if (rule.getIdCensoRegla().equals(rowKey)) {
                return rule;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(CensoReglaExtended object) {
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
