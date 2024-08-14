/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Diagnostico;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.DiagnosticoService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gcruz
 */
public class DiagnosticosLazy extends LazyDataModel<Diagnostico> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticosLazy.class);
    private static final long serialVersionUID = 1L;
    
     private ParamBusquedaReporte paramBusquedaReporte;
    private int totalReg;
    
    private transient DiagnosticoService diagnosticoService;
    
    private List<Diagnostico> listaDiagnosticos;

    public DiagnosticosLazy() {
        
    }

    public DiagnosticosLazy(DiagnosticoService diagnosticoService, ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.diagnosticoService = diagnosticoService;
        listaDiagnosticos  = new ArrayList<>();
    }
    
    
    
    @Override
    public List<Diagnostico> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {
        //ordenAscClave ++;
        if (listaDiagnosticos != null) {
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
                        
                listaDiagnosticos = diagnosticoService.obtenerBusquedaDiagnosticos(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortOrder);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de diagnosticoService.obtenerBusquedaDiagnosticos en clase DiagnosticosLazy, linea 52. {}", ex.getMessage());
                listaDiagnosticos = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listaDiagnosticos = new ArrayList<>();

        }
        return listaDiagnosticos;
    }
    
    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = diagnosticoService.obtenerTotalBusquedaDiagnosticos(paramBusquedaReporte);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. diagnosticoService.obtenerTotalBusquedaDiagnosticos {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public Diagnostico getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(Diagnostico object) {
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
