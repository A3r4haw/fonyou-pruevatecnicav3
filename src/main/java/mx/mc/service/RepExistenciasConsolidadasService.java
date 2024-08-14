package mx.mc.service; 

import java.util.List;

import mx.mc.model.Medicamento_Extended;

/**
 * 
 * @author olozada
 *
 */
public interface RepExistenciasConsolidadasService {
	
	public List<Medicamento_Extended> obtenerReporteExist_Consolidadas(
            Medicamento_Extended medicamento_Extended, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalReporteExist_Consolidadas(Medicamento_Extended medicamento_Extended) throws Exception;
	
	public List<Medicamento_Extended> obtenerReporteEntregaExist_Consolidadas(
            Medicamento_Extended medicamento_Extended, int startingAt, int maxPerPage) throws Exception;
        
        public Long obtenerTotalReporteEntregaExist_Consolidadas(Medicamento_Extended medicamento_Extended) throws Exception;
        
}
