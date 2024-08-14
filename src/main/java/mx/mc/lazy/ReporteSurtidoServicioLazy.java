/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ReporteSurtidoServicio;
import org.primefaces.model.LazyDataModel;
import mx.mc.service.ReporteMovimientosService;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author unava
 */
public class ReporteSurtidoServicioLazy extends LazyDataModel<ReporteSurtidoServicio>{
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteEstatusInsumoLazy.class);
    private static final long serialVersionUID = 1L;

    private final transient ReporteMovimientosService reporteMovimientosService;
    private final ParamBusquedaReporte paramBusquedaReporte;
    private List<ReporteSurtidoServicio> listServicioSurtido;
    private int totalReg;
    private ReporteSurtidoServicio dataRepList;

    public ReporteSurtidoServicioLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.listServicioSurtido = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<ReporteSurtidoServicio> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listServicioSurtido != null) {
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listServicioSurtido = reporteMovimientosService.consultarSurtidoServicio(paramBusquedaReporte,startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);
                for(ReporteSurtidoServicio item: listServicioSurtido){
                    item.setCostoUnidosis(item.getCantidadSolicitada() * item.getCostoUnidosis());
                    String claves = item.getClaveInstitucional();
                    String[] clave = claves.split("\\.");
                    switch (clave.length) {
                        case 2:
                            item.setGen(clave[0]);
                            item.setEsp(clave[1]);
                            break;
                        case 3:
                            item.setGen(clave[0]);
                            item.setEsp(clave[1]);
                            item.setDi(clave[2]);
                            break;
                        default:
                            item.setGen(clave[0]);
                            item.setEsp(clave[1]);                    
                            item.setDi(clave[2]);
                            item.setVa(clave[3]);
                            break;
                    }
                }
                if (getRowCount() <= 0) {
                    LOGGER.debug("Obtener total resultados");
                    int total = obtenerTotalResultados();
                    setRowCount(total);
                }
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporte Surtido a Servicio. {}", ex.getMessage());
                listServicioSurtido = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listServicioSurtido = new ArrayList<>();
        }
        return listServicioSurtido;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = reporteMovimientosService.consultarSurtidoServicioTotal(paramBusquedaReporte);

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
    public ReporteSurtidoServicio getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ReporteSurtidoServicio object) {
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

    public ReporteSurtidoServicio getDataRepList() {
        return dataRepList;
    }

    public void setDataRepList(ReporteSurtidoServicio dataRepList) {
        this.dataRepList = dataRepList;
    }
    
}
