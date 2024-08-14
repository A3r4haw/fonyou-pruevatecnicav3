package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.EnvioNeumatico;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.EnvioNeumaticoService;


/**
 *
 * @author olozada
 */
public class EnvioNeumaticoLazy extends LazyDataModel<EnvioNeumatico> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvioNeumaticoLazy.class);
    private static final long serialVersionUID = 1L;

    private transient EnvioNeumaticoService envioNeumaticoService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<EnvioNeumatico> listEnvioNeumatico;
    private int totalReg;

    public EnvioNeumaticoLazy() {
        //No code needed in constructor
    }

    public EnvioNeumaticoLazy(EnvioNeumaticoService envioNeumaticoService, ParamBusquedaReporte paramBusquedaReporte) {
        this.envioNeumaticoService = envioNeumaticoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listEnvioNeumatico = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<EnvioNeumatico> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listEnvioNeumatico != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();   
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listEnvioNeumatico = envioNeumaticoService.consultaRepNeumatico(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta. {}", ex.getMessage());
                listEnvioNeumatico = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listEnvioNeumatico = new ArrayList<>();

        }
        return listEnvioNeumatico;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = envioNeumaticoService.obtenerRepNeumatico(paramBusquedaReporte);

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
    public EnvioNeumatico getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(EnvioNeumatico object) {
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
