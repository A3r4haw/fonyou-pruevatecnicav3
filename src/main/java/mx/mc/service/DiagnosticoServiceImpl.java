package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.DiagnosticoMapper;
import mx.mc.model.Diagnostico;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author hramirez
 */
@Service
public class DiagnosticoServiceImpl extends GenericCrudServiceImpl<Diagnostico, String> implements DiagnosticoService {
    
    @Autowired
    private DiagnosticoMapper diagnosticoMapper;

    @Autowired
    public DiagnosticoServiceImpl(GenericCrudMapper<Diagnostico, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Diagnostico> obtenerPorIdPacienteIdVisitaIdPrescripcion(String idPaciente, String idVisita, String idPrescripcion) throws Exception {
        try {
            return diagnosticoMapper.obtenerPorIdPacienteIdVisitaIdPrescripcion(idPaciente, idVisita, idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error obtener Diagnosticos. " + ex.getMessage());
        }
    }

    @Override
    public List<Diagnostico> obtenerDiagnosticoByIdPaciente(String idPaciente) throws Exception {
        try {
            return diagnosticoMapper.obtenerByIdPaciente(idPaciente);
        } catch (Exception ex) {
            throw new Exception("Error obtener Diagnosticos. " + ex.getMessage());
        }
    }

    @Override
    public List<Diagnostico> obtenerListaAutoComplete(String cadena) throws Exception {
        try {
            return diagnosticoMapper.obtenerListaAutoComplete(cadena);
        } catch (Exception ex) {
            throw new Exception("Error obtener obtenerListaAutoComplete. " + ex.getMessage());
        }
    }
    
    @Override
    public List<Diagnostico> obtenerListaChiconcuac(Diagnostico diagnostico , Integer numRegistros) throws Exception {
        try {
            return diagnosticoMapper.obtenerListaChiconcuac(diagnostico.getIdDiagnostico()
                    , diagnostico.getNombre() , diagnostico.isActivo() , numRegistros);
        } catch (Exception ex) {
            throw new Exception("Error obtener obtenerListaAutoComplete. " + ex.getMessage());
        }
    }

    @Override
    public Diagnostico obtenerDiagnosticoPorIdDiag(String idDiagnostico) throws Exception {
        
        try {
            return diagnosticoMapper.obtenerDiagnosticoPorIdDiag(idDiagnostico);
        } catch(Exception ex) {
            throw new Exception("Error al momento de buscar el diagnostico por id: " + idDiagnostico + " " + ex.getMessage());
        }
    }

    @Override
    public Diagnostico obtenerDiagnosticoPorNombre(String nombreDiagnostico) throws Exception {
        try {
            return diagnosticoMapper.obtenerDiagnosticoPorNombre(nombreDiagnostico);
        } catch(Exception ex) {
            throw new Exception("Error al buscar el diagnostico por nombre:  " + ex.getMessage());
        }
    }

    @Override
    public List<Diagnostico> obtenerBusquedaDiagnosticos(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
            return  diagnosticoMapper.obtenerBusquedaDiagnosticos(paramBusquedaReporte, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
           throw new Exception("Error al momento de buscar Medicamento  " + e.getMessage()); 
}
    }

    @Override
    public Long obtenerTotalBusquedaDiagnosticos(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        
        try {
            return diagnosticoMapper.obtenerTotalBusquedaDiagnosticos(paramBusquedaReporte);
        } catch (Exception e) {
           throw new Exception("Error al momento de obtenerTotalBusquedaMedicamento " + e.getMessage());  
        }
    }
    
    public String validaClaveExistente(String clave) throws Exception {
        
        try {
             return diagnosticoMapper.validaClaveExistente(clave);
        } catch(Exception ex) {
            throw new Exception("Error al momento de validaClaveExistente " + ex.getMessage());
        }
    }
    
    @Override
    public List<Diagnostico> obtenerByIdPrescripcion (String idPrescripcion) throws Exception {
        try {
            return diagnosticoMapper.obtenerByIdPrescripcion(idPrescripcion);
        } catch (Exception ex) {
            throw new Exception("Error obtener Diagnosticos por idPrescripcion. " + ex.getMessage());
        }
    }

    
}
