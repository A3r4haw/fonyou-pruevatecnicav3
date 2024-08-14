/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ViaAdministracion;
import mx.mc.service.ViaAdministracionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gcruz
 */
public class ViaAdministracionLazy extends LazyDataModel<ViaAdministracion> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViaAdministracionLazy.class);
    private static final long serialVersionUID = 1L;
    
     private ParamBusquedaReporte paramBusquedaReporte;
    private int totalReg;
    
    private transient ViaAdministracionService viaAdministracionService;
    
    private List<ViaAdministracion> listaViaAdministraciones;

    public ViaAdministracionLazy() {
        
    }

    public ViaAdministracionLazy(ViaAdministracionService viaAdministracionService, ParamBusquedaReporte paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.viaAdministracionService = viaAdministracionService;
        listaViaAdministraciones  = new ArrayList<>();
    }
    
    
    
    @Override
    public List<ViaAdministracion> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {
        //ordenAscClave ++;
        if (listaViaAdministraciones != null) {
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
                        
                listaViaAdministraciones = viaAdministracionService.obtenerBusquedaViaAdministraciones(paramBusquedaReporte, startingAt, maxPerPage, sortField, sortOrder);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de viaAdministracionService.obtenerBusquedaViaAdministraciones en clase ViaAdministracionLazy, linea 52. {}", ex.getMessage());
                listaViaAdministraciones = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listaViaAdministraciones = new ArrayList<>();

        }
        return listaViaAdministraciones;
    }
    
    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = viaAdministracionService.obtenerTotalBusquedaViaAdministraciones(paramBusquedaReporte);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. viaAdministracionService.obtenerTotalBusquedaViaAdministraciones {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public ViaAdministracion getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(ViaAdministracion object) {
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
