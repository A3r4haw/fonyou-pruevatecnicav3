/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.RepHistoricoMezclasPaciente;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.RepHistorialMezclasPacienteService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class RepHistorialMezclasPacienteLazy extends LazyDataModel<RepHistoricoMezclasPaciente> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteMovimientosLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient RepHistorialMezclasPacienteService repHistorialMezclasPacienteService;
    
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<RepHistoricoMezclasPaciente> listaPrescripciones;
    private int totalReg;
    
    
    public void RepHistorialMezclasPacienteLazy() {
        
    }
    
    public RepHistorialMezclasPacienteLazy(RepHistorialMezclasPacienteService repHistorialMezclasPacienteService, ParamBusquedaReporte paramBusquedaReporte) {
        this.repHistorialMezclasPacienteService = repHistorialMezclasPacienteService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listaPrescripciones = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<RepHistoricoMezclasPaciente> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {
         if (listaPrescripciones != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listaPrescripciones = repHistorialMezclasPacienteService.consultarHistoricoMezclasPaciente(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listaPrescripciones = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listaPrescripciones = new ArrayList<>();

        }
        return listaPrescripciones;
    }
    
    
     private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = repHistorialMezclasPacienteService.obtenerTotalRegistros(paramBusquedaReporte);

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
    public RepHistoricoMezclasPaciente getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(RepHistoricoMezclasPaciente mezclaPaciente) {
        if (mezclaPaciente == null) {
            return null;
        }
        return mezclaPaciente;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
    
}
