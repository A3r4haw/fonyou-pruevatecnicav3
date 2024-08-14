package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Diagnostico;
import mx.mc.model.DiagnosticoPaciente;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author hramirez
 */
public interface DiagnosticoMapper extends GenericCrudMapper<Diagnostico, String> {

    List<Diagnostico> obtenerPorIdPacienteIdVisitaIdPrescripcion(@Param("idPaciente") String idPaciente, @Param("idVisita") String idVisita, @Param("idPrescripcion") String idPrescripcion);

    boolean registraDiagnosticoList (List<DiagnosticoPaciente> diagnoticoList);
    
    boolean eliminaDiagnosticoList (@Param("idPrescripcion") String idPrescripcion );
    
    List<Diagnostico> obtenerByIdPaciente (@Param("idPaciente") String idPaciente); 
    
    List<Diagnostico> obtenerListaAutoComplete (@Param("cadena") String cadena); 
    
    List<Diagnostico> obtenerListaChiconcuac(@Param("idDiagnostico")String idDiagnostico 
            , @Param("nombre") String nombre
            , @Param("activo") boolean activo
            , @Param("numRegistros") Integer numRegistros);
    
    Diagnostico obtenerDiagnosticoPorIdDiag(@Param("idDiagnostico") String idDiagnostico);
    
    Diagnostico obtenerDiagnosticoPorClave(@Param("clave") String clave);
    
    Diagnostico obtenerDiagnosticoPorNombre(@Param("nombreDiagnostico") String nombreDiagnostico);
    
    List<Diagnostico> obtenerBusquedaDiagnosticos(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
            ,@Param("sortField") String sortField,@Param("sortOrder") String sortOrder);
    
    Long obtenerTotalBusquedaDiagnosticos(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    public String validaClaveExistente(@Param("clave") String clave);
    
    List<Diagnostico> obtenerByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
}
