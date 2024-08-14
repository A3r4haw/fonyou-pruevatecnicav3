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
public class SurtPrescripcionExtLazy extends LazyDataModel<Surtimiento_Extend> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurtPrescripcionExtLazy.class);
    private static final long serialVersionUID = 1L;
    private transient SurtimientoService surtimientoService;  
    private Date fechaProgramada = new java.util.Date();
    private List<String> tipoPrescripcionSelectedList;    
    private List<Integer> listEstatusPrescripcion = new ArrayList<>();
    private List<Integer> listEstatusSurtimiento = new ArrayList<>();
    private List<String> listServiciosQueSurte = new ArrayList<>();
    private String cadenaBusqueda;
    private Integer numHorPrevReceta;
    private Integer numHorPostReceta;
    private List<Surtimiento_Extend> listSurtimientoExtended;
    
    private int totalReg;

    public SurtPrescripcionExtLazy() {
        //No code needed in constructor
    }

    public SurtPrescripcionExtLazy(SurtimientoService surtimientoService,
            Date fechaProgramada, String cadenaBusqueda,List<String> tipoPrescripcionSelectedList,Integer numHorPrevReceta, 
            Integer numHorPostReceta,List<Integer> listEstatusPrescripcion,List<Integer> listEstatusSurtimiento, List<String> listServiciosQueSurte) {
        this.surtimientoService = surtimientoService;        
        this.fechaProgramada = fechaProgramada;
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;        
        this.listEstatusPrescripcion = listEstatusPrescripcion;
        this.listEstatusSurtimiento = listEstatusSurtimiento;
        this.listServiciosQueSurte = listServiciosQueSurte;        
        this.numHorPrevReceta = numHorPrevReceta;
        this.numHorPostReceta = numHorPostReceta;
        this.cadenaBusqueda = cadenaBusqueda;
        listSurtimientoExtended = new ArrayList<>();
    }

    @Override
    public List<Surtimiento_Extend> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {        
        if (listSurtimientoExtended != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                listSurtimientoExtended = surtimientoService.obtenerPorFechaEstructuraPacientePrescripcionExt(fechaProgramada, cadenaBusqueda, 
                        tipoPrescripcionSelectedList, numHorPrevReceta, numHorPostReceta, listEstatusPrescripcion, listEstatusSurtimiento, 
                        listServiciosQueSurte, startingAt, maxPerPage, sortField, sortOrder);
            } catch (Exception ex) {                
                LOGGER.error("Error al realizar la consulta de Soluciones. {}", ex.getMessage());
                listSurtimientoExtended = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listSurtimientoExtended = new ArrayList<>();
        }
        return listSurtimientoExtended;
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
    public Surtimiento_Extend getRowData(String rowKey) {
        for (Surtimiento_Extend surt: listSurtimientoExtended ) {
            if(surt.getIdSurtimiento().equals(rowKey)){
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
