package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.mc.enums.Acumulados_Enum;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.DataResultReport;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ReporteMovimientosService;

/**
 * 
 * @author mcalderon
 *
 */
public class ReporteAcumuladosLazy extends LazyDataModel<DataResultReport> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteAcumuladosLazy.class);
    private static final long serialVersionUID = 1L;
	
	private transient ReporteMovimientosService reporteMovimientosService;
	private ParamBusquedaReporte paramBusquedaReporte;
        private List<DataResultReport> listDataResultReporteAcumulado;
	private int totalReg;
        private Long total;
	
	public ReporteAcumuladosLazy() {
            //No code needed in constructor
	}
	
	public ReporteAcumuladosLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
		this.reporteMovimientosService = reporteMovimientosService;
		this.paramBusquedaReporte = paramBusquedaReporte;
		listDataResultReporteAcumulado = new ArrayList<>();
	}
	
    @Autowired
    @Override
    public List<DataResultReport> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

        if (listDataResultReporteAcumulado != null) {
            if (getRowCount() <= 0) {
                LOGGER.debug("Obtener total resultados");
                setRowCount(obtenerTotalResultados()); 
            }
            try {
                if (paramBusquedaReporte.isNuevaBusqueda()) {
                    startingAt = 0;
                }
                
                if (paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_CLAVE.getValue()) {
                    listDataResultReporteAcumulado = reporteMovimientosService.consultarAcumulados(paramBusquedaReporte, startingAt, maxPerPage);
                } else if (paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_MEDICO.getValue()) {
                    listDataResultReporteAcumulado = reporteMovimientosService.consultarAcumuladosMedico(paramBusquedaReporte, startingAt, maxPerPage);
                } else if (paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_PACIENTE.getValue()) {
                    listDataResultReporteAcumulado = reporteMovimientosService.consultarAcumuladosPaciente(paramBusquedaReporte, startingAt, maxPerPage);
                } else if (paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_COLECTIVO.getValue()) {
                    listDataResultReporteAcumulado = reporteMovimientosService.consultarAcumuladosColectivo(paramBusquedaReporte, startingAt, maxPerPage);
                }

               paramBusquedaReporte.setNuevaBusqueda(false);
               paramBusquedaReporte.setIdMedico(null);
            } catch (Exception ex) {
                LOGGER.error("Error al realizar la consulta de reporteAcumulados. {}", ex.getMessage());
                listDataResultReporteAcumulado = new ArrayList<>();
            }

            setPageSize(maxPerPage);
        } else {
            listDataResultReporteAcumulado = new ArrayList<>();

        }
        return listDataResultReporteAcumulado;
    }

    private int obtenerTotalResultados() {
        try {
            if (paramBusquedaReporte != null) {
                if (paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_CLAVE.getValue()) {
                    total = reporteMovimientosService.obtenerTotalAcumulados(paramBusquedaReporte);
                } else if (paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_MEDICO.getValue()) {
                    total = reporteMovimientosService.obtenerTotalAcumuladosMedico(paramBusquedaReporte);                    
                } else if (paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_PACIENTE.getValue()) {
                    total = reporteMovimientosService.obtenerTotalAcumuladosPaciente(paramBusquedaReporte);
                }else if(paramBusquedaReporte.getTipoAcumulado() == Acumulados_Enum.ACUMULADO_COLECTIVO.getValue()){
                    total = reporteMovimientosService.obtenerTotalAcumuladosColectivo(paramBusquedaReporte);
                }
                totalReg = total.intValue();                
            } else {
                totalReg = 0;
            }
        } catch (Exception ex) {
            totalReg = 0;
            LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
        }
        return totalReg;
    }

    @Override
    public DataResultReport getRowData() {
        if (isRowAvailable())
            return super.getRowData();
	return null;
    }

    @Override
    public Object getRowKey(DataResultReport acumulado) {
        if (acumulado == null)
            return null;
        return acumulado;
    }

    public int getTotalReg() {
        return totalReg;
    }

	public void setTotalReg(int totalReg) {
		this.totalReg = totalReg;
	}
        
}
