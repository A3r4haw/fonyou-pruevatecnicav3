package mx.mc.service;

import java.util.List;
import java.util.Map;

import mx.mc.model.Cama;
import mx.mc.model.CamaExtended;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author gcruz
 *
 */
public interface CamaService extends GenericCrudService<Cama, String>{

	List<CamaExtended> obtenerCamasByServicio(String idEstructura) throws Exception;
        
	Cama obtenerCama(String idCama) throws Exception;
        
        List<CamaExtended> obtenerCamaByEstructuraAndEstatus(
                String idEstructura , List<Integer> listaEstatusCama) throws Exception;
        
        Cama obtenerPorNombre(Cama cama) throws Exception;
        
        CamaExtended obtenerCamaNombreEstructura(String idPaciente) throws Exception;
        
        Cama obterCamaPorNombreYEstructura(String nombreCama, String idEstructura) throws Exception;
        
        List<CamaExtended> obtenerServicioCamas(String idEntidadHosp,int startingAt,int maxPerPage,String sortField,SortOrder sortOrder,Map<String, Object> map) throws Exception;
        
        Long obtenerTotalServicioCamas(String idEntidadHosp,Map<String, Object> map) throws Exception;
}
