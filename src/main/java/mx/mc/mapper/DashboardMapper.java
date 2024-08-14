package mx.mc.mapper;

import java.util.List;

import mx.mc.model.DashboardResult;

/**
 * 
 * @author gcruz
 *
 */
public interface DashboardMapper {
	
	List<DashboardResult> getIndicadoresGenerales() throws Exception;
	
	List<DashboardResult> getTopTenMedicaments() throws Exception;
	
	List<DashboardResult> getPrescripciones() throws Exception;
        
        List<DashboardResult> getTopMedicos() throws Exception;
        
        List<DashboardResult> getTopPacientes() throws Exception;
        
        List<DashboardResult> getTopServicios() throws Exception;
          
        List<DashboardResult> getTopNivel() throws Exception;
}
