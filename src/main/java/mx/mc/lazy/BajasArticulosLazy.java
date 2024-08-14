package mx.mc.lazy; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.BajasArticulos;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.BajasArticulosService;



/**
 * 
 * @author gcruz
 *
 */
public class BajasArticulosLazy extends LazyDataModel<BajasArticulos> {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(BajasArticulosLazy.class);

    private transient BajasArticulosService bajasArticulosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<BajasArticulos> listBajasArticulos;
    private int totalReg;

    public BajasArticulosLazy() {

    }

    public BajasArticulosLazy(BajasArticulosService bajasArticulosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.bajasArticulosService = bajasArticulosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listBajasArticulos = new ArrayList<>();  
    }

    @Autowired
    @Override
    public List<BajasArticulos> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {
        
        if (listBajasArticulos != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listBajasArticulos = bajasArticulosService.consultarBajasArticulos(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de BajasArticulos. {}", ex.getMessage());
                listBajasArticulos = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listBajasArticulos = new ArrayList<>();

        }
        return listBajasArticulos;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = bajasArticulosService.obtenerTotalRegistros(paramBusquedaReporte);

                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public BajasArticulos getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(BajasArticulos object) {
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
