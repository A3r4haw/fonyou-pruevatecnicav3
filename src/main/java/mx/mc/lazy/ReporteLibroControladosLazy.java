/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.List;
import java.util.Map;
import mx.mc.model.ParamLibMedControlados;
import mx.mc.model.ReporteLibroControlados;
import mx.mc.service.PrescripcionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author aortiz
 */
public class ReporteLibroControladosLazy extends LazyDataModel<ReporteLibroControlados> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteMovimientosLazy.class);
    private static final long serialVersionUID = 1L;

    private transient PrescripcionService prescripcionService;
    private ParamLibMedControlados paramBusquedaReporte;
    private List<ReporteLibroControlados> listaControlados;

    public ReporteLibroControladosLazy(PrescripcionService prescripcionService, ParamLibMedControlados paramBusquedaReporte) {
        this.prescripcionService = prescripcionService;
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    @Override
    public List<ReporteLibroControlados> load(
            int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        try {
            this.listaControlados = this.prescripcionService.
                    obtenerReporteMedicamentosControlados(this.paramBusquedaReporte);
        } catch (Exception e) {
            LOGGER.error("Error en el metodo load :: {}", e.getMessage());
        }
        this.setRowCount(this.listaControlados.size());
        return this.listaControlados;
    }

    public PrescripcionService getPrescripcionService() {
        return prescripcionService;
    }

    public void setPrescripcionService(PrescripcionService prescripcionService) {
        this.prescripcionService = prescripcionService;
    }

    public ParamLibMedControlados getParamBusquedaReporte() {
        return paramBusquedaReporte;
    }

    public void setParamBusquedaReporte(ParamLibMedControlados paramBusquedaReporte) {
        this.paramBusquedaReporte = paramBusquedaReporte;
    }

    public List<ReporteLibroControlados> getListaControlados() {
        return listaControlados;
    }

    public void setListaControlados(List<ReporteLibroControlados> listaControlados) {
        this.listaControlados = listaControlados;
    }

    
}
