/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.SurtimientoInsumo_Extend;
import mx.mc.service.ReporteMovimientosService;

/**
 *
 * @author bernabe
 */
public class ReporteSurtimientoInsumoLazy extends LazyDataModel<SurtimientoInsumo_Extend>  {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteSurtimientoInsumoLazy.class);
    private transient ReporteMovimientosService reporteMovimientosService;
    private ParamBusquedaReporte paramBusquedaReporte;
    
    private List<SurtimientoInsumo_Extend> listReporteSurtimientoInsumo; 
    private List<String> estructuraList;
    private int totalReg;

    public ReporteSurtimientoInsumoLazy() {
        
    }

    public ReporteSurtimientoInsumoLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte, List<String> estructuraList) {
        this.reporteMovimientosService = reporteMovimientosService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listReporteSurtimientoInsumo = new ArrayList<>();
        this.estructuraList = estructuraList;
    }
    
    @Override
    public List<SurtimientoInsumo_Extend> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {

        if (listReporteSurtimientoInsumo != null) {
            if (getRowCount() <= 0 || map.size() > 0) {
                LOGGER.info("Obtener total resultados");
                HashMap map1 = replaceMap(map);
                int total = obtenerTotalResultados(map1);
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                HashMap map2 = replaceMap(map);
                String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
                paramBusquedaReporte.setSortField(sortField);
                paramBusquedaReporte.setSortOrder(order);
//                listReporteSurtimientoInsumo = reporteMovimientosService.consultarSurtimientoInsumo(paramBusquedaReporte, estructuraList, startingAt, maxPerPage,map2);
                paramBusquedaReporte.setNuevaBusqueda(false);
                
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de ReporteSurtimientoInsumoLazy. {}", ex.getMessage());
                listReporteSurtimientoInsumo = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listReporteSurtimientoInsumo = new ArrayList<>();

        }
        return listReporteSurtimientoInsumo;
    }
    
    
    private int obtenerTotalResultados( HashMap<String, Object> map) {
        try {
            if (paramBusquedaReporte != null) {
                Long total = 1L; // reporteMovimientosService.obtenerTotalRegistrosSurtimientoInsumo(paramBusquedaReporte, estructuraList,map);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }
    
    private HashMap replaceMap(Map<String,Object> map){
        try {            
            if (map.size() > 0) {
                if (map.containsKey("servicio")) {
                    String aux = map.get("servicio").toString();
                    map.put("ep.nombre",aux);
                    map.remove("servicio");
                }

                if (map.containsKey("folioPrescripcion")) {
                    String aux = map.get("folioPrescripcion").toString();
                    map.put("p.folio",aux);
                    map.remove("folioPrescripcion");
                }

                if (map.containsKey("paciente")) {
                    String aux = map.get("paciente").toString();
                    map.put("concat(pa.nombreCompleto,' ',pa.apellidoPaterno,' ',pa.apellidoMaterno)",aux);
                    map.remove("paciente");
                }

                if (map.containsKey("diagnostico")) {
                    String aux = map.get("diagnostico").toString();
                    map.put("d.nombre",aux);
                    map.remove("diagnostico");
                }

                if (map.containsKey("folioSurtimiento")) {
                    String aux = map.get("folioSurtimiento").toString();
                    map.put("s.folio",aux);
                    map.remove("folioSurtimiento");
                }

                if (map.containsKey("claveInstitucional")) {
                    String aux = map.get("claveInstitucional").toString();
                    map.put("m.claveInstitucional",aux);
                    map.remove("claveInstitucional");
                }

                if (map.containsKey("nombreCorto")) {
                    String aux = map.get("nombreCorto").toString();
                    map.put("m.nombreCorto",aux);
                    map.remove("nombreCorto");
                }

                if (map.containsKey("lote")) {
                    String aux = map.get("lote").toString();
                    map.put("i.lote",aux);
                    map.remove("lote");
                }

                if (map.containsKey("caducidad")) {
                    String aux = map.get("caducidad").toString();
                    map.put("i.fechaCaducidad",aux);
                    map.remove("caducidad");
                }

                if (map.containsKey("nombreMedico")) {
                    String aux = map.get("nombreMedico").toString();
                    map.put("concat(u.nombre,' ',u.apellidoPaterno,' ',u.apellidoMaterno)",aux);
                    map.remove("nombreMedico");
                }
            }
        } catch (Exception e) {
        }
        
        return (HashMap)map;
    }
    
    @Override
    public SurtimientoInsumo_Extend getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(SurtimientoInsumo_Extend surtimiento) {
        if (surtimiento == null) {
            return null;
        }
        return surtimiento;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
}
