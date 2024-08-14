package mx.mc.service;

import java.util.List;

import mx.mc.model.DashboardResult;

/**
 * 
 * @author gcruz
 *
 */
public interface DashboardService {

	public List<DashboardResult> getIndicadoresGenerales() throws Exception;
	
	public List<DashboardResult> getTopTenMedicaments() throws Exception;
	
	public List<DashboardResult> getPrescripciones() throws Exception;
        
        public List<DashboardResult> getTopMedicos() throws Exception;
        
        public List<DashboardResult> getTopPacientes() throws Exception;
        
        public List<DashboardResult> getTopServicios() throws Exception;
        
        public List<DashboardResult> getTopNivel() throws Exception;
}
