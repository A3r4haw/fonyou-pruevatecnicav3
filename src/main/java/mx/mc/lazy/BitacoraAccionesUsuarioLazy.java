/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.BitacoraAccionesUsuario;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.BitacoraAccionUsuarioService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class BitacoraAccionesUsuarioLazy extends LazyDataModel<BitacoraAccionesUsuario> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteMovimientosLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient BitacoraAccionUsuarioService bitacoraAccionUsuarioService;
    private ParamBusquedaReporte paramBusquedaReporte;
    
    private List<BitacoraAccionesUsuario> listaBitacoraAccionesUsuario;
    private int totalReg;
    
    public void BitacoraAccionesUsuarioLazy() {
        
    }
    
    public BitacoraAccionesUsuarioLazy(BitacoraAccionUsuarioService bitacoraAccionUsuarioService, ParamBusquedaReporte paramBusquedaReporte) {
        this.bitacoraAccionUsuarioService = bitacoraAccionUsuarioService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        listaBitacoraAccionesUsuario = new ArrayList<>();
    }
    
     @Autowired
    @Override
    public List<BitacoraAccionesUsuario> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {
         if (listaBitacoraAccionesUsuario != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                listaBitacoraAccionesUsuario = bitacoraAccionUsuarioService.consultarAccionesUsuario(paramBusquedaReporte,
                        startingAt, maxPerPage);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de bitacora acciones de usuario. {}", ex.getMessage());
                listaBitacoraAccionesUsuario = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listaBitacoraAccionesUsuario = new ArrayList<>();

        }
        return listaBitacoraAccionesUsuario;
    }
    
    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = bitacoraAccionUsuarioService.obtenerTotalRegistros(paramBusquedaReporte);

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
    public BitacoraAccionesUsuario getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(BitacoraAccionesUsuario usuarioAccion) {
        if (usuarioAccion == null) {
            return null;
        }
        return usuarioAccion;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
}
