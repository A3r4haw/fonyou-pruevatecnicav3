package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.DataResultMensajeHL7;
import mx.mc.model.MensajeHL7;
import mx.mc.model.ParamBusquedaReporte;

/**
 * 
 * @author gcruz
 *
 */
public interface MonitorMensajeHL7Mapper {

	List<DataResultMensajeHL7> consultarMensajesHL7(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
	
	Long obtenerTotalRegistros(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
	
	List<DataResultMensajeHL7> obtenerTiposMensajeHL7();
	
	MensajeHL7 obtenerDetalleMensajeHL7(@Param("idMensaje") String idMensaje);
	
	List<String> obtenerIngresosMensaje();
}
