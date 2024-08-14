package mx.mc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.MonitorMensajeHL7Mapper;
import mx.mc.model.DataResultMensajeHL7;
import mx.mc.model.MensajeHL7;
import mx.mc.model.ParamBusquedaReporte;

/**
 * 
 * @author gcruz
 *
 */
@Service
public class MonitorMensajeHL7ServiceImpl implements MonitorMensajeHL7Service {
    
    @Autowired
    private MonitorMensajeHL7Mapper monitorMensajeHL7Mapper;
    
    public MonitorMensajeHL7ServiceImpl() {
    	//No code needed in constructor
    }
    
    @Override
	public List<DataResultMensajeHL7> consultarMensajesHL7(ParamBusquedaReporte paramBusquedaReporte, int startingAt,
			int maxPerPage) throws Exception {
    	try {
    		return monitorMensajeHL7Mapper.consultarMensajesHL7(paramBusquedaReporte, startingAt, maxPerPage);
    	} catch (Exception ex) {
    		throw new Exception("Error al obtener consulta de mensajes HL7" +ex.getMessage());
		}
		
	}  

	@Override
	public Long obtenerTotalRegistros(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
		try {
			return monitorMensajeHL7Mapper.obtenerTotalRegistros(paramBusquedaReporte);
		} catch (Exception ex) {
			throw new Exception("Error al obtener total de mensajes HL7" +ex.getMessage());
		}
	}

	@Override
	public List<DataResultMensajeHL7> obtenerTiposMensajeHL7() throws Exception {
		try {
			return monitorMensajeHL7Mapper.obtenerTiposMensajeHL7();
		} catch (Exception ex) {
			throw new Exception("Error al obtener lista de tipos de Mensaje  " + ex.getMessage());
		}
		
	}

	@Override
	public MensajeHL7 obtenerDetalleMensajeHL7(String idMensaje) throws Exception {
		try {
			return monitorMensajeHL7Mapper.obtenerDetalleMensajeHL7(idMensaje);
		} catch (Exception ex) {
			throw new Exception("Ocurrio un error al momento de obtener el detalle del mensaje  " + ex.getMessage());
		}
	}

	@Override
	public List<String> obtenerIngresosMensaje() throws Exception {
		try {
			return monitorMensajeHL7Mapper.obtenerIngresosMensaje();
		} catch (Exception ex) {
			throw new Exception("Ocurrio un error al momento de obtener la lista de ingresos para filtro" + ex.getMessage());
		}		
	}



	
}
