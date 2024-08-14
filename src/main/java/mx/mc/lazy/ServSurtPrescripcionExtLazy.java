package mx.mc.lazy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.Surtimiento_Extend;

import mx.mc.service.SurtimientoService;


/**
 *
 * @author mcalderon
 *
 */
public class ServSurtPrescripcionExtLazy extends LazyDataModel<Surtimiento_Extend> {
    static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServSurtPrescripcionExtLazy.class);
    private SurtimientoService surtimientoService;  
    Date fechaProgramada = new java.util.Date();
    List<String> tipoPrescripcionSelectedList;    
    List<Integer> listEstatusPrescripcion = new ArrayList<>();
    List<Integer> listEstatusSurtimiento = new ArrayList<>();
    List<String> listServiciosQueSurte = new ArrayList<>();
    String cadenaBusqueda;
    Integer numHorPrevReceta;
    Integer numHorPostReceta;    
    private List<Surtimiento_Extend> servSurtimientoExtendedList;
    
    private int totalReg;

    public ServSurtPrescripcionExtLazy() {

    }

    public ServSurtPrescripcionExtLazy(SurtimientoService surtimientoService,
            Date fechaProgramada, String cadenaBusqueda,List<String> tipoPrescripcionSelectedList,Integer numHorPrevReceta, 
            Integer numHorPostReceta,List<Integer> listEstatusPrescripcion,List<Integer> listEstatusSurtimiento, List<String> listServiciosQueSurte) {
        this.surtimientoService = surtimientoService;                
        this.fechaProgramada = fechaProgramada;
        this.cadenaBusqueda = cadenaBusqueda;
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
        this.numHorPrevReceta = numHorPrevReceta;
        this.numHorPostReceta = numHorPostReceta;        
        this.listEstatusPrescripcion = listEstatusPrescripcion;
        this.listEstatusSurtimiento = listEstatusSurtimiento;
        this.listServiciosQueSurte = listServiciosQueSurte;
        servSurtimientoExtendedList = new ArrayList<>();
    }

    @Override
    public List<Surtimiento_Extend> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {        
        if (servSurtimientoExtendedList != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {           
                servSurtimientoExtendedList = surtimientoService.obtenerPorFechaEstructuraPacientePrescripcionExt(fechaProgramada, cadenaBusqueda, 
                        tipoPrescripcionSelectedList, numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento, 
                        listServiciosQueSurte, startingAt, maxPerPage, sortField, sortOrder);
                
            } catch (Exception ex) {                
                LOGGER.error("Error al realizar la consulta de Prescripciones. {}", ex.getMessage());
                servSurtimientoExtendedList = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            servSurtimientoExtendedList = new ArrayList<>();
        }
        return servSurtimientoExtendedList;
    }

    
    private int obtenerTotalResultados() {
        try {
            
            Long total = surtimientoService.obtenerTotalPorFechaEstructuraPacientePrescripcionExt(fechaProgramada, cadenaBusqueda, tipoPrescripcionSelectedList,
                    numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
            totalReg = total.intValue();
                      
        } catch (Exception ex) {
            LOGGER.info("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public Surtimiento_Extend getRowData(String rowDataKey) {
        for (Surtimiento_Extend surt: servSurtimientoExtendedList ) {
            if(surt.getIdSurtimiento().equals(rowDataKey)){
                return surt;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Surtimiento_Extend object) {
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
