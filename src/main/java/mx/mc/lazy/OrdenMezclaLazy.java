/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.model.NutricionParenteralExtended;
import mx.mc.service.NutricionParenteralService;
import mx.mc.service.SolucionService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author gcruz
 */
public class OrdenMezclaLazy extends LazyDataModel<NutricionParenteralExtended> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenMezclaLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient NutricionParenteralService nutricionParenteralService;
    private List<NutricionParenteralExtended> nutricionParenteralList;
    private String cadenaBusqueda;
    private String idEstructura;
    private transient List<Integer> estatusSolucionLista;
    private int totalReg;
    
    public OrdenMezclaLazy() {
        
    }
    
    public OrdenMezclaLazy(NutricionParenteralService nutricionParenteralService, String cadenaBusqueda, String idEstructura, List<Integer> estatusSolucionLista) {
        this.nutricionParenteralService = nutricionParenteralService;
        this.cadenaBusqueda = cadenaBusqueda;
        this.idEstructura = idEstructura;
        this.nutricionParenteralList = new ArrayList<>();
        this.estatusSolucionLista = estatusSolucionLista;
    }
    
    
    
     @Autowired
    @Override
    public List<NutricionParenteralExtended> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map){
         LOGGER.trace("mx.mc.lazy.OrdenMezclaLazy.load()");
        if(nutricionParenteralList != null){
            
            String tipoPrescripcion = null;
            String estatusSolucion = null;
            String nombreEstructura = null;
            String tipoSolucion = null;
            String nombreMedico = null;
            String folio = null;
            String fechaProgramada2 = null;
            String nombrePaciente = null;
            String cama = null;
            String folioPrescripcion = null;

            for (String key : map.keySet()) {
                LOGGER.debug(key + ":" + map.get(key));
                switch (key) {
                    case "tipoPrescripcion":
                        tipoPrescripcion = map.get(key).toString();
                        break;
                    case "Estatus":
                        estatusSolucion = map.get(key).toString();
                        break;
                    case "nombreEstructura":
                        nombreEstructura = map.get(key).toString();
                        break; // nombreEstructura:medici
                    case "tipoSolucion":
                        tipoSolucion = map.get(key).toString();
                        break;     // tipoSolucion:on
                    case "prescribio":
                        nombreMedico = map.get(key).toString();
                        break;     // nombreMedico:De prueba
                    case "folioPreparacion":
                        folio = map.get(key).toString();
                        break;            // folio:94
                    case "fechaProgramada":
                        fechaProgramada2 = map.get(key).toString();
                        break;            // fechaProgramada:2024-02-18
                    case "nombrePaciente":
                        nombrePaciente = map.get(key).toString();
                        break;            // nombrePaciente:banda
                    case "ubicacion":
                        cama = map.get(key).toString();
                        break;            // cama:123
                    case "folio":
                        folioPrescripcion = map.get(key).toString();
                        break;            // folioPrescripcion:63
                    }
            }            
            
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados(tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico
                        , folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion);
                setRowCount(total);
            }
            try{
                nutricionParenteralList = nutricionParenteralService.obtenerBusquedaLazy(
                        cadenaBusqueda, idEstructura, startingAt, maxPerPage, sortField, sortOrder, estatusSolucionLista
                        , tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico
                        , folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion
                );
            }catch(Exception ex){
                LOGGER.error("Error al realizar la consulta de solucionService.obtenerBusquedaLazy {}", ex.getMessage());
                nutricionParenteralList = new ArrayList<>();
            }
            setPageSize(maxPerPage);
        }else{
            nutricionParenteralList = new ArrayList<>();
        }
        return nutricionParenteralList;
    }

    private int obtenerTotalResultados(String tipoPrescripcion, String estatusSolucion, String nombreEstructura, String tipoSolucion, String nombreMedico
            , String folio, String fechaProgramada2, String nombrePaciente, String cama, String folioPrescripcion){
        LOGGER.trace("mx.mc.lazy.OrdenMezclaLazy.obtenerTotalResultados()");
        
        try{
           Long total = nutricionParenteralService.obtenerBusquedaTotalLazy(cadenaBusqueda, idEstructura, estatusSolucionLista
           , tipoPrescripcion, estatusSolucion, nombreEstructura, tipoSolucion, nombreMedico
                        , folio, fechaProgramada2, nombrePaciente, cama, folioPrescripcion);
           totalReg = total.intValue();
           
        } catch (Exception ex) {
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            totalReg = 0;
        }
        return totalReg;
    }
    
    @Override
    public NutricionParenteralExtended getRowData(String rowKey) {
        for(NutricionParenteralExtended sol : nutricionParenteralList) {
            if(sol.getIdNutricionParenteral().equals(rowKey)){
                return sol;
            }
        }       
        return null;
    }

    @Override
    public Object getRowKey(NutricionParenteralExtended object) {
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
