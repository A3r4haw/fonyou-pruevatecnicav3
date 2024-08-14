/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.ReporteConcentracionArticulos;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ReporteMovimientosService;
/**
 *
 * @author unava
 */
public class ReporteConcentracionArticulosLazy extends LazyDataModel<ReporteConcentracionArticulos> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteEstatusInsumoLazy.class);

    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<ReporteConcentracionArticulos> listConcentracion;
    private int totalReg; 

    public ReporteConcentracionArticulosLazy() {
        //No code needed in constructor
    }

    public ReporteConcentracionArticulosLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listConcentracion = new ArrayList<>();
    }

    @Override
    public List<ReporteConcentracionArticulos> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listConcentracion != null) {
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listConcentracion = reporteMovimientosService.consultarEstatusInsumosConce(paramBusquedaReporte,startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);
                for(ReporteConcentracionArticulos item: listConcentracion){
                    String claves = item.getClaveInstitucional();
                    String[] clave = claves.split("\\.");
                    item.setGen(clave[0]);
                    item.setEsp(clave[1]);
                    item.setDif(clave[2]);
                    item.setVar(clave[3]);
                }
                if (getRowCount() <= 0) {
                    LOGGER.debug("Obtener total resultados");
                    int total = obtenerTotalResultados();
                    setRowCount(total);
                }
                //paramBusquedaReporte.setListConcentracion(listConcentracion);                
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listConcentracion = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listConcentracion = new ArrayList<>();
        }
        return listConcentracion;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteMovimientosService.obtenerTotalEstatusInsumosConce(paramBusquedaReporte);
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
    public ReporteConcentracionArticulos getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ReporteConcentracionArticulos articulo) {
        if (articulo == null) {
            return null;
        }
        return articulo;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
}
