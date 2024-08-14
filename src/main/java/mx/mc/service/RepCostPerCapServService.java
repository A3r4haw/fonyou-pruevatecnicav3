package mx.mc.service;

import java.util.Date;
import java.util.List;
import mx.mc.model.DataResultReport;

/**
 * 
 * @author aortiz
 *
 */
public interface RepCostPerCapServService extends GenericCrudService<DataResultReport, String> {
    
    public List<DataResultReport> obtenerDatosRepCostPerCapServ(
            String idEstructura , Date fechaInicio , Date fechaFin ) throws Exception;
	
}
