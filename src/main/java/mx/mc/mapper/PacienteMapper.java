package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Estructura;
import mx.mc.model.Paciente;
import mx.mc.model.PacienteDomicilio;
import mx.mc.model.Paciente_Extended;
import mx.mc.model.PacienteResponsable;
import mx.mc.model.PacienteServicioPeriferico;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.TurnoMedico;
import org.apache.ibatis.annotations.Param;

/**
 * @author AORTIZ
 */
public interface PacienteMapper extends GenericCrudMapper<Paciente, String>{
    List<Paciente> obtenerUltimosRegistros(@Param("numRegistros") int numRegistros);
    
    List<Paciente_Extended> obtenerUltimosRegistrosPacienteExtended(
            @Param("numRegistros") int numRegistros);
    
    List<Paciente_Extended> obtenerPacientesYCamas(
            @Param("idEstructura") String idEstructura , 
            @Param("numRegistros") Integer numRegistros ,
            @Param("listaidEstructura") List<Estructura> listaidEstructura);
    
    public List<Paciente_Extended> obtenerPacientesYCamasPorCriterioBusqueda(
            @Param("idEstructura") String idEstructura , 
            @Param("numRegistros") Integer numRegistros , 
            @Param("criterioBusqueda") String criterioBusqueda);
    
    Paciente obtenerPacienteByIdPaciente(@Param("idPaciente") String idPaciente);
    
    int insertarPaciente(Paciente paciente);
    
    int insertarPacienteDomicilio(PacienteDomicilio pacienteDom);
    
    int insertarPacienteResponsable(PacienteResponsable pacienteResp);
    
    int actualizarPaciente(Paciente paciente);
    
    int actualizarPacienteDomicilio(PacienteDomicilio pacienteDom);
    
    int actualizarPacienteResponsable(PacienteResponsable pacienteResp);
    
    List<Paciente_Extended> obtenerPacientesPorIdUnidad(
            @Param("idEstructura") String idEstructura
            , @Param("idEstructuraPeriferico") String idEstructuraPeriferico
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("numRegistros") int numRegistros
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
    );
    
    List<Paciente_Extended> obtenerPacientePorIdUnidadAct(
            @Param("listaEstructuras") List<Estructura> listaEstructuras
            , @Param("idEstructuraPeriferico") String idEstructuraPeriferico
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("numRegistros") int numRegistros
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
    );
    
    List<Paciente_Extended> obtenerPacientePorIdUnidadActLazy(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("startingAt") int startingAt,
            @Param("maxPerPage") int maxPerPage,
            @Param("listaEstructuras") List<Estructura> listaEstructuras
            , @Param("idEstructuraPeriferico") String idEstructuraPeriferico                        
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
    );
    
    Long obtenerTotalPacientePorIdUnidadActLazy(
            @Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,      
            @Param("listaEstructuras") List<Estructura> listaEstructuras
            , @Param("idEstructuraPeriferico") String idEstructuraPeriferico                        
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
    );
    
    
    List<Paciente_Extended> obtenerPacientesPorIdUnidadConsExt(
            @Param("idEstructura") String idEstructura
            , @Param("idEstructuraPeriferico") String idEstructuraPeriferico
            , @Param("cadenaBusqueda") String cadenaBusqueda
            , @Param("numRegistros") int numRegistros
            , @Param("listEstatusPaciente") List<Integer> listEstatusPaciente
            , @Param("listIdTurno") List<TurnoMedico> idTurno
    );
    
    List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusqueda(
            @Param("criterioBusqueda") String criterioBusqueda
            , @Param("numRegistros") int numRegistros);
    
    List<Paciente> obtenerRegistrosPorCriterioDeBusqueda2(
            @Param("criterioBusqueda") String criterioBusqueda
            , @Param("numRegistros") int numRegistros);
    
    
    Paciente_Extended obtenerPacienteCompletoPorId(String idPaciente);
    
    List<Paciente_Extended> obtenerPacietesConPrescripcion(
            @Param("numRegistros") int numRegistros , 
            @Param("idsEstructura") List<String> idsEstructura,
            @Param("textoBusqueda") String textoBusqueda ,
            @Param("listaEstatusPresc") List<Integer> listaEstatusPresc);
    
    List<Paciente> obtenerPacietesConPrescripcionList(
            @Param("numRegistros") int numRegistros , 
            @Param("idsEstructura") List<String> idsEstructura,
            @Param("textoBusqueda") String textoBusqueda ,
            @Param("listaEstatusPresc") List<Integer> listaEstatusPresc);
    
    Paciente_Extended obtenerNombreEstructurabyIdpaciente(@Param("idPaciente") String idPaciente);
    
    public boolean actualizarIdEstructuraPeriferico(PacienteServicioPeriferico pacienteServicioPeriferico);
    
    List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusquedaLazy(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
                        ,@Param("sortField") String sortField, @Param("sortOrder") String sortOrder);
    
    Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    List<Paciente> obtenerPacientes(@Param("cadenaBusqueda") String cadenaBusqueda);
    
    Integer obtenerNumeroMaximoPaciente();
    
    List<Paciente_Extended> obtenerRegistrosPorCriterioDeBusquedaYEstructura(@Param("criterioBusqueda") String criterioBusqueda
            , @Param("idEstructura") String idEstructura, @Param("numRegistros") int numRegistros);
    
    Paciente obtenerPacienteByNumeroPaciente(@Param("numeroPaciente") String numeroPaciente);

    List<Paciente> obtenerDerechohabientes(@Param("claveDerechohabiencia") String claveDerechohabiencia, @Param("idPaciente") String idPaciente);
    
    Paciente obtenerPacienteByRfcCvDehCurp(@Param("rfc")String rfc ,@Param("claveDerechohabiencia") int claveDerechoHabiencia,@Param("curp")String curp);
    
    Paciente_Extended obtenerPacienteByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion);
    
}
