/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.Fabricante;
import mx.mc.service.FabricanteService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class FabricanteLazy extends LazyDataModel<Fabricante> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EstabilidadLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient FabricanteService fabricanteService;
    
    private String cadenaBusqueda;
    private List<Fabricante> listaFabricantes;
    
    private int totalReg;
    
    public FabricanteLazy() {
        
    }
    
    public FabricanteLazy(FabricanteService fabricanteService, String cadenaBusqueda) {
        this.fabricanteService = fabricanteService;
        this.cadenaBusqueda = cadenaBusqueda;
        this.listaFabricantes = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<Fabricante> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {
         listaFabricantes = new ArrayList<>();

        if (listaFabricantes != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                listaFabricantes = fabricanteService.obtenerListaFabricantes(cadenaBusqueda, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {
                LOGGER.error("Error al obtener la lista de estabilidades", ex.getMessage());
            }
        } else {
            listaFabricantes = new ArrayList<>();
        }
        return listaFabricantes;
    }
    
    private int obtenerTotalResultados() {
        Long total = 0L;
        try {
            total = fabricanteService.obtenerTotalFabricantes(cadenaBusqueda);
            totalReg = total.intValue();

        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }
}
