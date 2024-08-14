package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.mc.model.DataResultVales;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.ReporteMovimientosService;

/**
 * 
 * @author gcruz
 *
 */
public class ReporteRecetasLazy extends LazyDataModel<DataResultVales> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReporteRecetasLazy.class);
    private static final long serialVersionUID = 1L;
	
	private transient ReporteMovimientosService reporteMovimientosService;
	private ParamBusquedaReporte paramBusquedaReporte;
	private List<DataResultVales> listDataRecetas;
        private boolean permiteAjusteInventarioGlobal;
	private int totalReg;
	
	public ReporteRecetasLazy() {
            //No code needed in constructor
	}
	
	public ReporteRecetasLazy(ReporteMovimientosService reporteMovimientosService, ParamBusquedaReporte paramBusquedaReporte) {
		this.reporteMovimientosService = reporteMovimientosService;
		this.paramBusquedaReporte = paramBusquedaReporte;
		listDataRecetas = new ArrayList<>();
	}
	  
    @Override
    public List<DataResultVales> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

		 if (listDataRecetas != null) {			 
			 if (getRowCount() <= 0) {
				 LOGGER.debug("Obtener total resultados");
	             setRowCount(obtenerTotalResultados());
	         }
	         try {
	        	 if(paramBusquedaReporte.isNuevaBusqueda()) {
	        		 startingAt = 0;
	        	 }
                     if (paramBusquedaReporte.isActivaCamposRepEmisionRecetas()) {
                         if (paramBusquedaReporte.getTipoPrescripcion() == null) {
                             listDataRecetas = reporteMovimientosService.consultarRecetasColectivas(paramBusquedaReporte,
                                     startingAt, maxPerPage);
                         } else {
                             listDataRecetas = reporteMovimientosService.consultarEmisionRecetas(paramBusquedaReporte,
                                     startingAt, maxPerPage);
                         }
                     } else {
                         listDataRecetas = reporteMovimientosService.consultarEmisionRecetas(paramBusquedaReporte,
                                 startingAt, maxPerPage);
                     }
	        	 paramBusquedaReporte.setNuevaBusqueda(false);	        	 
	             
	         } catch (Exception ex) {
	             LOGGER.error("Error al realizar la consulta de reporteRecetas. {}", ex.getMessage());
	             listDataRecetas = new ArrayList<>();
	         }

	         setPageSize(maxPerPage);
	     } else {
	      	listDataRecetas = new ArrayList<>();

	     }
	     return listDataRecetas;
	}
	 
	 private int obtenerTotalResultados() {
            Long total;
            try {
                if (paramBusquedaReporte != null) {
                    if(paramBusquedaReporte.isActivaCamposRepEmisionRecetas()){
                        if (paramBusquedaReporte.getTipoPrescripcion() == null) {
                            total = reporteMovimientosService.obtenerTotalReceColectivas(paramBusquedaReporte);
                        } else {
                            total = reporteMovimientosService.obtenerTotalRegistrosRecetas(paramBusquedaReporte);
                        }
                    } else {
                        total = reporteMovimientosService.obtenerTotalRegistrosRecetas(paramBusquedaReporte);
                    }                             
                   totalReg = total.intValue();
               } else {
                   totalReg = 0;
               }		        
            } catch(Exception ex) {
                    LOGGER.error("Ocurrio un error al obtener el total. {}", ex.getMessage());
            }	     
            return totalReg; 
	 }
	     
	 @Override
	 public DataResultVales getRowData() {
	    if( isRowAvailable() )
	      return super.getRowData();
	    return null;
	  }

	  @Override
	  public Object getRowKey(DataResultVales receta) {
	    if( receta == null )
	        return null;
	    return receta;
	  }

	public int getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(int totalReg) {
		this.totalReg = totalReg;
	} 

    public boolean isPermiteAjusteInventarioGlobal() {
        return permiteAjusteInventarioGlobal;
    }

    public void setPermiteAjusteInventarioGlobal(boolean permiteAjusteInventarioGlobal) {
        this.permiteAjusteInventarioGlobal = permiteAjusteInventarioGlobal;
    }
    
}
