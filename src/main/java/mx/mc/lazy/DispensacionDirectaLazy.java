/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.SurtimientoMinistrado_Extend;
import mx.mc.service.SurtimientoMinistradoService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gcruz
 */
public class DispensacionDirectaLazy extends LazyDataModel<SurtimientoMinistrado_Extend> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionDirectaLazy.class);
    private static final long serialVersionUID = 1L;
        
    private transient SurtimientoMinistradoService surtimientoMinistradoService;
    private String idEstructura;
    private String idUsuario;
    private List<SurtimientoMinistrado_Extend> listaSurtmientoMinistrado;
    private int totalReg;
    
    public DispensacionDirectaLazy() {
        
    }
    
    public DispensacionDirectaLazy(SurtimientoMinistradoService surtimientoMinistradoService, String idEstructura, String idUsuario) {
        this.surtimientoMinistradoService = surtimientoMinistradoService;
        this.idEstructura = idEstructura;
        this.idUsuario =idUsuario;
        this.listaSurtmientoMinistrado = new ArrayList<>();
    }
    
    @Override
    public List<SurtimientoMinistrado_Extend> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {
        if (listaSurtmientoMinistrado != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {                
                listaSurtmientoMinistrado = surtimientoMinistradoService.obtenerSurtimientoMinistradoLazzy(idEstructura, idUsuario, startingAt, maxPerPage);                

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de surtimientos ministrados. {}", ex.getMessage());
                listaSurtmientoMinistrado = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listaSurtmientoMinistrado = new ArrayList<>();

        }
        return listaSurtmientoMinistrado;
    }
    
    private int obtenerTotalResultados() {
        try {
            Long total = surtimientoMinistradoService.obtenerTotalSurtimientoMinistradoLazzy(idEstructura, idUsuario);

            totalReg = total.intValue();
            
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }
    
    @Override
    public SurtimientoMinistrado_Extend getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(SurtimientoMinistrado_Extend object) {
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
