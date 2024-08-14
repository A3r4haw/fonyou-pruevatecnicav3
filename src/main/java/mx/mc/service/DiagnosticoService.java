package mx.mc.service;

import java.util.List;
import mx.mc.model.Diagnostico;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hramirez
 */
public interface DiagnosticoService extends GenericCrudService<Diagnostico, String> {

    public List<Diagnostico> obtenerPorIdPacienteIdVisitaIdPrescripcion (String idPaciente, String idVisita, String idPrescripcion) throws Exception;
    
    public List<Diagnostico> obtenerDiagnosticoByIdPaciente (String idPaciente) throws Exception;
    
    public List<Diagnostico> obtenerListaAutoComplete (String cadena) throws Exception;
    
    public List<Diagnostico> obtenerListaChiconcuac(Diagnostico diagnostico , Integer numRegistros) throws Exception;
    
    Diagnostico obtenerDiagnosticoPorIdDiag(String idDiagnostico) throws Exception;
    
    Diagnostico obtenerDiagnosticoPorNombre(String nombreDiagnostico) throws Exception;
    
    public List<Diagnostico> obtenerBusquedaDiagnosticos(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage
    ,String sortField,SortOrder sortOrder) throws Exception; 
    
    public Long obtenerTotalBusquedaDiagnosticos(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
    
    public String validaClaveExistente(String clave) throws Exception;
    
    public List<Diagnostico> obtenerByIdPrescripcion (String idPrescripcion) throws Exception;
    
}
