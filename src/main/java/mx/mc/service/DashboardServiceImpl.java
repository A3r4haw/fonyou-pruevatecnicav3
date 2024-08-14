package mx.mc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.mc.mapper.DashboardMapper;
import mx.mc.model.DashboardResult;

/**
 * 
 * @author gcruz
 *
 */
@Service
public class DashboardServiceImpl implements DashboardService {
    
	@Autowired
	private DashboardMapper dashboardMapper;
	
	@Override
	public List<DashboardResult> getIndicadoresGenerales() throws Exception {
		try {
			return dashboardMapper.getIndicadoresGenerales();
		} catch (Exception ex) {
			throw new Exception("Error al obtener indicadores generales del Dashboard  " + ex.getMessage());
		}
	}

	@Override
	public List<DashboardResult> getTopTenMedicaments() throws Exception {
		try {
			return dashboardMapper.getTopTenMedicaments();
		} catch (Exception ex) {
			throw new Exception("Error al obtener los 10 medicamentos del Dashboard  " + ex.getMessage());
		}
	}

	@Override
	public List<DashboardResult> getPrescripciones() throws Exception {
		try {
			return dashboardMapper.getPrescripciones();
		} catch (Exception ex) {
			throw new Exception("Error al obtener las prescripciones del Dashboard  " + ex.getMessage());
		}
	}

        @Override
        public List<DashboardResult> getTopMedicos() throws Exception {
            try {
                return dashboardMapper.getTopMedicos();
            } catch (Exception ex) {
		throw new Exception("Error al obtener los 10 medicamentos del Dashboard  " + ex.getMessage());
            }
        }

        @Override
        public List<DashboardResult> getTopPacientes() throws Exception {
         try {
                return dashboardMapper.getTopPacientes();
            } catch (Exception ex) {
		throw new Exception("Error al obtener los 10 pacientes del Dashboard  " + ex.getMessage());
            }
        }

        @Override
        public List<DashboardResult> getTopServicios() throws Exception {
            try {
                return dashboardMapper.getTopServicios();
            } catch (Exception ex) {
		throw new Exception("Error al obtener los 10 servicios del Dashboard  " + ex.getMessage());
            }
        }

        @Override
        public List<DashboardResult> getTopNivel() throws Exception {
        try {
                return dashboardMapper.getTopNivel();
            } catch (Exception ex) {
		throw new Exception("Error al obtener los 10 niveles socieconomicos del Dashboard  " + ex.getMessage());
            }
        }

}
