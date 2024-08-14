package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.CensoPacienteExtended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.CensoPacienteService;

/**
 *
 * @author apalacios
 *
 */
public class CensoPacientesLazy extends LazyDataModel<CensoPacienteExtended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CensoPacientesLazy.class);
    private static final long serialVersionUID = 1L;

    private ParamBusquedaReporte paramBusquedaReporte;    
    private int totalReg;    
    private transient CensoPacienteService censoPacienteService;
    private List<CensoPacienteExtended> listaCensoPacientes;

    public CensoPacientesLazy() {
        //No code needed in constructor
    }

    public CensoPacientesLazy(CensoPacienteService censoPacienteService, ParamBusquedaReporte paramBusquedaReporte) {
        this.censoPacienteService = censoPacienteService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listaCensoPacientes = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<CensoPacienteExtended> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {
        if (listaCensoPacientes != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                  listaCensoPacientes = censoPacienteService.obtenerRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte, startingAt, maxPerPage);
                  paramBusquedaReporte.setNuevaBusqueda(true);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Censo Pacientes. {}", ex.getMessage());
                listaCensoPacientes = new ArrayList<>();
            }
            setPageSize(maxPerPage);
        } else {
            listaCensoPacientes = new ArrayList<>();
        }
        return listaCensoPacientes;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = censoPacienteService.obtenerTotalRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte);

                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total de Censo Pacientes. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public CensoPacienteExtended getRowData(String rowKey) {
        for (CensoPacienteExtended pac : listaCensoPacientes) {
            if (pac.getIdCensoPaciente().equals(rowKey)) {
                return pac;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(CensoPacienteExtended object) {
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
