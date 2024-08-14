package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.init.Constantes;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.VistaMedicamento;
import mx.mc.service.MedicamentoService;

/**
 *
 * @author mcalderon
 *
 */
public class MedicamentosLazy extends LazyDataModel<VistaMedicamento> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicamentosLazy.class);
    private static final long serialVersionUID = 1L;

    private ParamBusquedaReporte paramBusquedaReporte;
    private int totalReg;
    
    private transient MedicamentoService medicamentoService;
    private List<VistaMedicamento> medicamentoList;
    private int tipoInsumo;
    //private int ordenAscClave;

    public MedicamentosLazy() {
        //No code needed in constructor
    }

    public MedicamentosLazy(MedicamentoService medicamentoService, ParamBusquedaReporte paramBusquedaReporte,int tipoInsumo
            //,int ordenAscClave
    ) {
        this.medicamentoService = medicamentoService;
        this.paramBusquedaReporte = paramBusquedaReporte;
        medicamentoList = new ArrayList<>();
        this.tipoInsumo = tipoInsumo;
        //this.ordenAscClave = ordenAscClave;
    }

    @Override
    public List<VistaMedicamento> load(int startingAt, int maxPerPage,String sortField, SortOrder sortOrder, Map<String, Object> map) {
        //ordenAscClave ++;
        if (medicamentoList != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                int total = obtenerTotalResultados();
                setRowCount(total);
            }
            try {
                String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;                          
                    paramBusquedaReporte.setSortOrder(order);                
                    paramBusquedaReporte.setSortField(sortField);
                    paramBusquedaReporte.setSortOrder(order);                                
                        
                medicamentoList = medicamentoService.obtenerBusquedaMedicamento(paramBusquedaReporte, tipoInsumo, startingAt, maxPerPage, sortField, sortOrder);
                paramBusquedaReporte.setNuevaBusqueda(false);

            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteEmisionVales. {}", ex.getMessage());
                medicamentoList = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            medicamentoList = new ArrayList<>();

        }
        return medicamentoList;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                Long total = medicamentoService.obtenerTotalBusquedaMedicamento(paramBusquedaReporte, tipoInsumo);
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
    public VistaMedicamento getRowData() {
        if (isRowAvailable()) {
            return super.getRowData();
        }
        return null;
    }

    @Override
    public Object getRowKey(VistaMedicamento object) {
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
