/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.DispensacionMaterialExtended;
import mx.mc.service.DispensacionMaterialService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author apalacios
 */
public class DispensacionMaterialLazy extends LazyDataModel<DispensacionMaterialExtended> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionMaterialLazy.class);

    private transient DispensacionMaterialService dispensacionService;
    private String idEstructura;
    private String idUsuario;
    private List<DispensacionMaterialExtended> listaDispensaciones;
    private int totalReg;
    private String cadenaBusqueda;
    
    public DispensacionMaterialLazy() {
        cadenaBusqueda = "";
    }
    
    public DispensacionMaterialLazy(DispensacionMaterialService dispensacionService, String idEstructura, String idUsuario, String cadenaBusqueda) {
        this.dispensacionService = dispensacionService;
        this.idEstructura = idEstructura;
        this.idUsuario = idUsuario;
        this.listaDispensaciones = new ArrayList<>();
        this.cadenaBusqueda = cadenaBusqueda;
    }
    
    @Override
    public List<DispensacionMaterialExtended> load(int startingAt, int maxPerPage, String sortBy, SortOrder so, Map<String, Object> map) {
        if (listaDispensaciones != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                String sortOrder = "ASC";
                if (so == SortOrder.DESCENDING)
                    sortOrder = "DESC";
                listaDispensaciones = dispensacionService.obtenerDispensacionesLazzy(idEstructura, idUsuario, cadenaBusqueda, startingAt, maxPerPage, sortBy, sortOrder);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de Dispensaciones. {}", ex.getMessage());
                listaDispensaciones = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listaDispensaciones = new ArrayList<>();

        }
        return listaDispensaciones;
    }
    
    private int obtenerTotalResultados() {
        try {
            Long total = dispensacionService.obtenerTotalDispensacionesLazzy(idEstructura, idUsuario, cadenaBusqueda);

            totalReg = total.intValue();
            
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }
    
    @Override
    public DispensacionMaterialExtended getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(DispensacionMaterialExtended object) {
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
