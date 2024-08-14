package mx.mc.service;

import java.util.List;
import mx.mc.model.Estructura;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.PacienteServicio;
import mx.mc.model.PacienteUbicacion;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.TurnoMedico;
import mx.mc.model.Visita;
import org.primefaces.model.SortOrder;

/**
 * @author AORTIZ
 */
public interface PacienteService extends GenericCrudService<Paciente, String>{
    
    public List<Paciente> obtenerUltimosPacientes(int numeroRegistros) throws Exception;
    
    public List<Paciente_Extended> obtenerUltimosPacientesPacienteExtended(
            int numeroRegistros) throws Exception;
    
    public List<Paciente_Extended> obtenerPacientesYCamas(
            String idEstructura , Integer numRegistros, List<Estructura> listaidEstructura) throws Exception;
    
    public List<Paciente_Extended>  obtenerPacientesYCamasPorCriterioBusqueda(
        String idEstructura , Integer numRegistros , String criterioBusqueda) throws Exception;
    
    boolean insertarPaciente(
            Paciente paciente,PacienteDomicilio pacienteDom, 
            PacienteResponsable pacienteResp , List<TurnoMedico> listaTurnos)throws Exception;
    
    public boolean actualizarPaciente(
            Paciente paciente,PacienteDomicilio pacienteDom, 
            PacienteResponsable pacienteResp , List<TurnoMedico> listaTurnos)throws Exception;
    
    public Paciente obtenerPacienteByIdPaciente(String idPaciente) throws Exception;
    
    public List<Paciente_Extended> obtenerPacientesPorIdUnidad(
            String idUnidadMedica
            , String idUnidadMedicaPeriferico
            , String cadenaBusqueda
            , int numRegistros
            , List<Integer> listEstatusPaciente
    )throws Exception;
    
    public List<Paciente_Extended> obtenerPacientePorIdUnidadAct(
            List<Estructura> listaEstructuras
            , String idUnidadMedicaPeriferico
            , String cadenaBusqueda
            , int numRegistros
            , List<Integer> listEstatusPaciente
    )throws Exception;
    
    public List<Paciente_Extended> obtenerPacientePorIdUnidadActLazy(
            ParamBusquedaReporte paramBusquedaReporte,
            int startingAt,
            int maxPerPage,
            List<Estructura> listaEstructuras
            , String idUnidadMedicaPeriferico
            , List<Integer> listEstatusPaciente
    )throws Exception;
    
    public Long obtenerTotalPacientePorIdUnidadActLazy(
            ParamBusquedaReporte paramBusquedaReporte,            
            List<Estructura> listaEstructuras
            , String idUnidadMedicaPeriferico
            , List<Integer> listEstatusPaciente
    )throws Exception;
        
    public List<Paciente_Extended> obtenerPacientesPorIdUnidadConsExt(
            String idUnidadMedica
            , String idUnidadMedicaPeriferico
            , String cadenaBusqueda
            , int numRegistros
            ,List<Integer> listEstatusPaciente
            ,List<TurnoMedico> listaIdTurno
    ) throws Exception;
    
    public List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusqueda(
            String criterioBusqueda, int numRegistros) throws Exception;
    
    public List<Paciente> obtenerRegistrosPorCriterioDeBusqueda2(
            String criterioBusqueda, int numRegistros) throws Exception;
    
    public List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    public Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
    
    public Paciente_Extended obtenerPacienteCompletoPorId(String idPaciente) throws Exception;
    
    public List<Paciente_Extended> obtenerPacietesConPrescripcion(
            int numRegistros ,  List<String> idsEstructura , String textoBusqueda , 
            List<Integer> listaEstatusPresc) throws Exception;
    
    public List<Paciente> obtenerPacietesConPrescripcionList(
            int numRegistros ,  List<String> idsEstructura , String textoBusqueda , 
            List<Integer> listaEstatusPresc) throws Exception;
    
    public Paciente_Extended obtenerNombreEstructurabyIdpaciente(String idPaciente) throws Exception;
    
    public List<Paciente> obtenerPacientes(String cadenaBusqueda) throws Exception;
    
    public Integer obtenerNumeroMaximoPaciente() throws Exception;
    
    public List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusquedaYEstructura(String criterioBusqueda,
                                    String idEstructura, int numRegistros) throws Exception;
    
    public Paciente obtenerPacienteByNumeroPaciente(String numeroPaciente) throws Exception;
    
    public List<Paciente_Extended> searchPacienteAutoComplete(String cadena) throws Exception;
    
    public List<Paciente> obtenerDerechohabientes(String claveDerechohabiencia, String idPaciente) throws Exception;
    
    public Paciente obtenerPacienteByRfcCvDehCurp(String rfc, int claveDerechoHabiencia,String curp) throws Exception;
    
    Paciente_Extended obtenerPacienteByIdPrescripcion(String idPrescripcion) throws Exception;
    
    public boolean insertarPacienteVisitaServicioUbicacion(
            Paciente paciente,
            PacienteDomicilio pacienteDom,
            PacienteResponsable pacienteResp,
            List<TurnoMedico> listaTurnos
            , Visita v
            , PacienteServicio ps
            , PacienteUbicacion pu
    ) throws Exception;
    
    boolean insertarPacienteVisitaServicio(
            Paciente paciente,PacienteDomicilio pacienteDom, 
            PacienteResponsable pacienteResp , List<TurnoMedico> listaTurnos,
            Visita v, PacienteServicio ps
    )throws Exception;
    
}
