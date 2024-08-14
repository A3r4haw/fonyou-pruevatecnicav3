package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Estudio;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.EstudioService;

public class EstudiosLazy extends LazyDataModel<Estudio> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EstudiosLazy.class);

    private ParamBusquedaReporte paramBusquedaReporte;
    private int totalReg;
    
    private transient EstudioService estudioService;
    private List<Estudio> estudioList;
    private int tipoEstudio;

    public EstudiosLazy() {
        //No code needed in constructor
    }

    public EstudiosLazy(EstudioService estudioService, ParamBusquedaReporte paramBusquedaReporte,int tipoEstudio) {
        this.estudioService = estudioService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        estudioList = new ArrayList<>();
        this.tipoEstudio = tipoEstudio;
    }

    @Override
    public List<Estudio> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {
        if (estudioList != null) {
            if (getRowCount() <= 0) {
                LOGGER.info("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;                          
                paramBusquedaReporte.setSortOrder(order);                
                paramBusquedaReporte.setSortField(sortField);
                paramBusquedaReporte.setSortOrder(order);
                estudioList = estudioService.obtenerBusquedaEstudio(paramBusquedaReporte, tipoEstudio, startingAt, maxPerPage, sortField, sortOrder);
                paramBusquedaReporte.setNuevaBusqueda(false);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Estudios. {}", ex.getMessage());
                estudioList = new ArrayList<>();
            }
            setPageSize(maxPerPage);
        } else {
            estudioList = new ArrayList<>();
        }
        return estudioList;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = estudioService.obtenerTotalBusquedaEstudio(paramBusquedaReporte, tipoEstudio);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total de Estudios. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public Estudio getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(Estudio object) {
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
