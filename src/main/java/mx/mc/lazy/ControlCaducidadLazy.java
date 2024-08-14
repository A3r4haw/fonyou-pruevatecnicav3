package mx.mc.lazy; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.ControlCaducidad;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ControlCaducidadService;


/**
 * 
 * @author gcruz
 *
 */
public class ControlCaducidadLazy extends LazyDataModel<ControlCaducidad> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlCaducidadLazy.class);

    private transient ControlCaducidadService controlCaducidadService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<ControlCaducidad> listControlCaducidad;
    private int totalReg;

    public ControlCaducidadLazy() {
        //No code needed in constructor
    }

    public ControlCaducidadLazy(ControlCaducidadService controlCaducidadService, ParamBusquedaReporte paramBusquedaReporte) {
        this.controlCaducidadService = controlCaducidadService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listControlCaducidad = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<ControlCaducidad> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listControlCaducidad != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                   startingAt = 0;
                }
                listControlCaducidad = controlCaducidadService.consultarControlCaducidad(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de ConsultaCaducidad. {}", ex.getMessage());
                listControlCaducidad = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listControlCaducidad = new ArrayList<>();

        }
        return listControlCaducidad;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = controlCaducidadService.obtenerTotalRegistros(paramBusquedaReporte);

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
    public ControlCaducidad getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ControlCaducidad object) {
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
