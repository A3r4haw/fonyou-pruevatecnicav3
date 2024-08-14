package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Consumo;
import mx.mc.model.ParamBusquedaReporte;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.service.ReporteMovimientosService;

/**
 *
 * @author cervanets
 *
 */
public class ConsumosLazy extends LazyDataModel<Consumo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumosLazy.class);
    private static final long serialVersionUID = 1L;

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;

    private transient List<Consumo> listaConsumos;
    private int totalReg;

    public ConsumosLazy() {
        //No code needed in constructor
    }

    public ConsumosLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.listaConsumos = new ArrayList<>();
    }

    @Override
    public List<Consumo> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        LOGGER.trace("mx.mc.lazy.ConsumosLazy.load()");
        if (this.listaConsumos != null) {
            if (getRowCount() <= 0) {
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (this.paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                this.paramBusquedaReporte.setOrden(sortOrder);
                this.paramBusquedaReporte.setSortField(sortField);
                this.paramBusquedaReporte.setStartingAt(startingAt);
                this.paramBusquedaReporte.setMaxPerPage(maxPerPage);
                this.listaConsumos = this.reporteMovimientosService
                        .obtenerConsumos(this.paramBusquedaReporte);
                this.paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de consumos. :: {} ", ex.getMessage());
                this.listaConsumos = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            this.listaConsumos = new ArrayList<>();

        }
        return this.listaConsumos;
    }

    private int obtenerTotalResultados() {
        try {
            if (this.paramBusquedaReporte != null) {
                Long total = this.reporteMovimientosService.obtenerTotalConsumos(paramBusquedaReporte);
                this.totalReg = total.intValue();
            } else {
                this.totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Error al obtener el total de consumos. :: {} ", ex.getMessage());
        }
        return this.totalReg;
    }

    @Override
    public Consumo getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(Consumo consumo) {
        if (consumo == null) {
            return null;
        }
        return consumo;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }

}
