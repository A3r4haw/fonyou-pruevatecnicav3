package mx.mc.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.mc.model.DataResultMensajeHL7;
import mx.mc.model.MensajeHL7;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.service.MonitorMensajeHL7Service;

/**
 * 
 * @author gcruz
 *
 */
public class MonitorMensajeHL7Lazy extends LazyDataModel<DataResultMensajeHL7> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorMensajeHL7Lazy.class);
    private static final long serialVersionUID = 1L;
    
    private transient MonitorMensajeHL7Service monitorMensajeHL7Service; 
    private ParamBusquedaReporte paramBusquedaReporte;
    private List<DataResultMensajeHL7> listMensajesHL7;
    private MensajeHL7 mensajeHL7;
    private int totalReg;
    
    public MonitorMensajeHL7Lazy() {
    	//No code needed in constructor
    }
    
    public MonitorMensajeHL7Lazy(MonitorMensajeHL7Service monitorMensajeHL7Service, ParamBusquedaReporte paramBusquedaReporte) {
    	this.monitorMensajeHL7Service = monitorMensajeHL7Service;
    	this.paramBusquedaReporte = paramBusquedaReporte;
    	this.listMensajesHL7 = new ArrayList<>();
    }
    
    @Autowired
    @Override
    public List<DataResultMensajeHL7> load(int startingAt, int maxPerPage, String string, SortOrder so, Map<String, Object> map) {

		 if (listMensajesHL7 != null) {			 
			 if (getRowCount() <= 0) {
				 LOGGER.debug("Obtener total resultados");
	             int total = obtenerTotalResultados();   
	             setRowCount(total);
	         }
	         try {
	        	 if(paramBusquedaReporte.isNuevaBusqueda()) {
	        		 startingAt = 0;
	        	 }
	        	 listMensajesHL7 = monitorMensajeHL7Service.consultarMensajesHL7(paramBusquedaReporte,         
	        			 					startingAt, maxPerPage);
	        	 paramBusquedaReporte.setNuevaBusqueda(false);	        	 
	             
	         } catch (Exception ex) {
	             LOGGER.error("Error al realizar la consulta de mensajes HL7. {}", ex.getMessage());
	             listMensajesHL7 = new ArrayList<>();
	         }

	         setPageSize(maxPerPage);
	     } else {
	    	 listMensajesHL7 = new ArrayList<>();

	     }
	     return listMensajesHL7;
	}
    
    private int obtenerTotalResultados() {
		 try {
			 if (paramBusquedaReporte != null) {
		            Long total = monitorMensajeHL7Service.obtenerTotalRegistros(paramBusquedaReporte);

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
	 public DataResultMensajeHL7 getRowData(String rowKey) {
	    	for(DataResultMensajeHL7 mensaje:listMensajesHL7) {
	    		if(mensaje.getIdMensaje().equals(rowKey)){
	    			return mensaje;
	    		}
	    	}
	    	return null;
	  }

	  @Override
	  public Object getRowKey(DataResultMensajeHL7 object) {
	    if( object == null )
	        return null;
	        return object.getIdMensaje();
	  }

	public int getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(int totalReg) {
		this.totalReg = totalReg;
	}

	public MensajeHL7 getMensajeHL7() {
		return mensajeHL7;
	}

	public void setMensajeHL7(MensajeHL7 mensajeHL7) {
		this.mensajeHL7 = mensajeHL7;
	}
        
}
