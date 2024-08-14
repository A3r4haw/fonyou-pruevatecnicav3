package mx.mc.lazy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.mc.model.Estructura;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Surtimiento_Extend;

import mx.mc.service.SurtimientoService;


/**
 *
 * @author mcalderon
 *
 */
public class DispensacionLazy extends LazyDataModel<Surtimiento_Extend> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DispensacionLazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient SurtimientoService surtimientoService;
    private ParamBusquedaReporte paramBusquedaReporte;      
    private Date fechaProgramada = new java.util.Date();
    private List<String> tipoPrescripcionSelectedList;
    private List<Integer> listEstatusPaciente = new ArrayList<>();
    private List<Integer> listEstatusPrescripcion = new ArrayList<>();
    private List<Integer> listEstatusSurtimiento = new ArrayList<>();
    private List<Estructura> listServiciosQueSurte = new ArrayList<>();
    
    private List<Surtimiento_Extend> surtimientoExtendedList;
    
    private int totalReg;

    public DispensacionLazy() {

    }

    public DispensacionLazy(SurtimientoService surtimientoService, ParamBusquedaReporte paramBusquedaReporte,
        Date fechaProgramada, List<String> tipoPrescripcionSelectedList, List<Integer> listEstatusPaciente, List<Integer> listEstatusPrescripcion,
        List<Integer> listEstatusSurtimiento, List<Estructura> listServiciosQueSurte) {
        this.surtimientoService = surtimientoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        this.fechaProgramada = fechaProgramada;
        this.tipoPrescripcionSelectedList = tipoPrescripcionSelectedList;
        this.listEstatusPaciente = listEstatusPaciente;
        this.listEstatusPrescripcion = listEstatusPrescripcion;
        this.listEstatusSurtimiento = listEstatusSurtimiento;
        this.listServiciosQueSurte = listServiciosQueSurte;
        surtimientoExtendedList = new ArrayList<>();
    }

    @Override
    public List<Surtimiento_Extend> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {        
        if (surtimientoExtendedList != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                surtimientoExtendedList = surtimientoService.obtenerPorFechaEstructuraPacienteCamaPrescripcionOrderLazy(fechaProgramada, paramBusquedaReporte, startingAt, maxPerPage, tipoPrescripcionSelectedList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte, sortField, sortOrder);
            } catch (Exception ex) {                
                LOGGER.error("Error al realizar la consulta de reporteAlmacenes. {}", ex.getMessage());
                surtimientoExtendedList = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            surtimientoExtendedList = new ArrayList<>();

        }
        return surtimientoExtendedList;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = surtimientoService.obtenerTotalPorFechaEstructuraPacienteCamaPrescripcionLazy(fechaProgramada, paramBusquedaReporte, tipoPrescripcionSelectedList, listEstatusPaciente, listEstatusPrescripcion, listEstatusSurtimiento, listServiciosQueSurte);
                totalReg = total.intValue();
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            LOGGER.info("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public Surtimiento_Extend getRowData(String rowKey) {
        for (Surtimiento_Extend surt: surtimientoExtendedList ) {
            if(surt.getIdSurtimiento().equals(rowKey)){
                return surt;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Surtimiento_Extend surtimiento) {
        if (surtimiento == null) {
            return null;
        }
        return surtimiento;
    }

    public int getTotalReg() {
        return totalReg;
    }

    public void setTotalReg(int totalReg) {
        this.totalReg = totalReg;
    }
    
}
