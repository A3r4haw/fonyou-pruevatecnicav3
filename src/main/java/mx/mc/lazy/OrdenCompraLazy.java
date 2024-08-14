/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.OrdenCompra_Extended;
import mx.mc.service.OrdenCompraService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class OrdenCompraLazy extends LazyDataModel<OrdenCompra_Extended> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenCompraLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient OrdenCompraService ordenCompraService;
    private List<OrdenCompra_Extended> listaOrdenesCompra;
    private String cadenaBusqueda;
    private int totalReg;
    private String idEstructura;
    
    public OrdenCompraLazy() {
        
    }
    
    public OrdenCompraLazy(OrdenCompraService ordenCompraService, String cadenaBusqueda, String idEstructura) {
        this.ordenCompraService = ordenCompraService;
        this.cadenaBusqueda = cadenaBusqueda;
        this.listaOrdenesCompra = new ArrayList<>();
        this.idEstructura = idEstructura;
    }
    
    @Autowired
    @Override
    public List<OrdenCompra_Extended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
        if(listaOrdenesCompra != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try{
                listaOrdenesCompra = ordenCompraService.obtenerOrdenesCompraLazzy(cadenaBusqueda, idEstructura, startingAt, maxPerPage, sortField, sortOrder);
                
            }catch(Exception ex){
                LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
                listaOrdenesCompra= new ArrayList<>();
            }
        
            setPageSize(maxPerPage);
        } else {
            listaOrdenesCompra = new ArrayList<>();    
        }
        return listaOrdenesCompra;
    }
    
    private int obtenerTotalResultados(){
        
        try{
            Long total = ordenCompraService.obtenerBusquedaTotalLazy(cadenaBusqueda, idEstructura);
            totalReg = total.intValue();
        }catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total en OrdenCompraLazy. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
     }
    
    @Override
    public OrdenCompra_Extended getRowData(String rowKey) {
        for(OrdenCompra_Extended orden : listaOrdenesCompra) {
            if(orden.getIdOrdenCompra().equals(rowKey)){
                return orden;
            }
        }       
        return null;
    }

    @Override
    public Object getRowKey(OrdenCompra_Extended object) {
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
