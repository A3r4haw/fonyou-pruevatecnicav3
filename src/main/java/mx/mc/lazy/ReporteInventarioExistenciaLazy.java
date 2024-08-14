package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ReportInventarioExistencias;
import mx.mc.service.ReporteMovimientosService;

public class ReporteInventarioExistenciaLazy extends LazyDataModel<ReportInventarioExistencias> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteInventarioExistenciaLazy.class);
	
	private transient ReporteMovimientosService reporteMovimientosService;
	private ParamBusquedaReporte paramBusquedaReporte;
	private List<ReportInventarioExistencias> listReportInventarioExistencias;
	private int totalReg;
	
	public ReporteInventarioExistenciaLazy() {
            //No code needed in constructor
	}
	
	public ReporteInventarioExistenciaLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
		this.reporteMovimientosService = reporteMovimientosService;
		this.paramBusquedaReporte = paramBusquedaReporte;
		listReportInventarioExistencias = new ArrayList<>();
	}
	
    @Autowired
    @Override
    public List<ReportInventarioExistencias> load(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder, Map<String, Object> map) {

		 if (listReportInventarioExistencias != null) {			 
			 if (getRowCount() <= 0) {
				 LOGGER.debug("Obtener total resultados");
	             int total = obtenerTotalResultados();
	             setRowCount(total);
	         }
	         try {
                        String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
                        paramBusquedaReporte.setSortField(sortField);
                        paramBusquedaReporte.setSortOrder(order);
	        	listReportInventarioExistencias = reporteMovimientosService.consultarInventarioExistencias(paramBusquedaReporte,         
	        			 					startingAt, maxPerPage,sortField,sortOrder);	        	 	             
	         } catch (Exception ex) {
	             LOGGER.error("Error al realizar la consulta de reporteMovimientosGrales. {}", ex.getMessage());
	             listReportInventarioExistencias = new ArrayList<>();
	         }

	         setPageSize(maxPerPage);
	     } else {
	    	 listReportInventarioExistencias = new ArrayList<>();

	     }
	     return listReportInventarioExistencias;
	}
	 
	 private int obtenerTotalResultados() {
		 try {
			 if (paramBusquedaReporte != null) {
		            Long total = reporteMovimientosService.getTotalRegistrosInventarioExistencias(paramBusquedaReporte);

		            totalReg = total.intValue();
		        } else {
		            totalReg = 0;
		        }		        
		 } catch(Exception ex) {
			 LOGGER.info("Ocurrio un error al obtener el total. {}", ex.getMessage());
		 }	     
		 return totalReg; 
	 }
	     
	 @Override
	 public ReportInventarioExistencias getRowData() {
	    if( isRowAvailable() )
	      return super.getRowData();
	      return null;
	  }

	  @Override
	  public Object getRowKey(ReportInventarioExistencias object) {
	    if( object == null )
	        return null;
	        return object;
	  }

	public int getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(int totalReg) {
		this.totalReg = totalReg;
	} 
        
}
