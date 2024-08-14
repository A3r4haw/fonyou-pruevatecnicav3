package mx.mc.service;

import java.util.List;

import mx.mc.model.DataResultMensajeHL7;
import mx.mc.model.MensajeHL7;
import mx.mc.model.ParamBusquedaReporte;

/**
  * 
  * @author gcruz
  *
  */
public interface MonitorMensajeHL7Service {
	
public List<DataResultMensajeHL7> consultarMensajesHL7(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
	
	public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
	
	public List<DataResultMensajeHL7> obtenerTiposMensajeHL7() throws Exception;
	
	public MensajeHL7 obtenerDetalleMensajeHL7(String idMensaje) throws Exception;
	
	public List<String> obtenerIngresosMensaje() throws Exception;

}
