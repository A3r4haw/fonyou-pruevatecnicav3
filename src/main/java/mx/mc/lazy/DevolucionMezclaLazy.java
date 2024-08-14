/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.DevolucionMezclaExtended;
import mx.mc.service.DevolucionMezclaService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author bbautista
 */
public class DevolucionMezclaLazy extends LazyDataModel<DevolucionMezclaExtended> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevolucionMezclaLazy.class);
    private static final long serialVersionUID = 1L;
    private transient DevolucionMezclaService devolucionMezclaService;
    private List<DevolucionMezclaExtended> devoList;
    private String idAlmacen;
    private String cadenaBusqueda;
    private int totalReg;
    private Integer idEstatusSolucion;

    public DevolucionMezclaLazy() {
    }

    public DevolucionMezclaLazy(DevolucionMezclaService devolucionMezclaService, String busqueda, String idAlmacen, Integer idEstatus) {
        this.devolucionMezclaService = devolucionMezclaService;
        this.idAlmacen = idAlmacen;
        this.cadenaBusqueda = busqueda;
        this.idEstatusSolucion = idEstatus;
        this.devoList = new ArrayList<>();
    }

    @Autowired
    @Override
    public List<DevolucionMezclaExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        if (devoList != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                devoList = devolucionMezclaService.obtenerBusquedaLazy(cadenaBusqueda, idAlmacen, idEstatusSolucion, startingAt, maxPerPage, sortField, sortOrder);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                devoList = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            devoList = new ArrayList<>();
        }
        return devoList;
    }

    private int obtenerTotalResultados() {

        try {
            Long total = devolucionMezclaService.obtenerBusquedaTotalLazy(cadenaBusqueda, idAlmacen);
            totalReg = total.intValue();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }

    @Override
    public DevolucionMezclaExtended getRowData(String rowKey) {
        for (DevolucionMezclaExtended pac : devoList) {
            if (pac.getIdDevolucionMezcla().equals(rowKey)) {
                return pac;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(DevolucionMezclaExtended object) {
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
