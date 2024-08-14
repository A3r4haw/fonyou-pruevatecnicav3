package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.Paciente_Extended;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.PacienteService;

/**
 *
 * @author mcalderon
 *
 */
public class PacientesLazy extends LazyDataModel<Paciente_Extended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacientesLazy.class);
    private static final long serialVersionUID = 1L;

    private ParamBusquedaReporte paramBusquedaReporte;    
    private int totalReg;    
    private transient PacienteService pacienteService;
    private List<Paciente_Extended> listaPacientes;

    public PacientesLazy() {
        //No code needed in constructor
    }

    public PacientesLazy(PacienteService pacienteService, ParamBusquedaReporte paramBusquedaReporte) {
        this.pacienteService = pacienteService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listaPacientes = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<Paciente_Extended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {

        if (listaPacientes != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;                          
                    paramBusquedaReporte.setSortOrder(order);                
                    paramBusquedaReporte.setSortField(sortField);
                    paramBusquedaReporte.setSortOrder(order);   
                    
                  listaPacientes = pacienteService.obtenerRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortOrder);
                  paramBusquedaReporte.setNuevaBusqueda(true);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Pacientes. {}", ex.getMessage());
                listaPacientes = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listaPacientes = new ArrayList<>();

        }
        return listaPacientes;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = pacienteService.obtenerTotalRegistrosPorCriterioDeBusquedaLazy(paramBusquedaReporte);

                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total de Pacientes. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public Paciente_Extended getRowData(String rowKey) {
        for (Paciente_Extended pac : listaPacientes) {
            if (pac.getIdPaciente().equals(rowKey)) {
                return pac;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Paciente_Extended object) {
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
