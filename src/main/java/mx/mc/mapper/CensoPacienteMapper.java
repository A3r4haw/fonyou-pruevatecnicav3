package mx.mc.mapper;

import java.util.List;
import mx.mc.model.CensoPaciente;
import mx.mc.model.CensoPacienteExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;

/**
 * @author apalacios
 */
public interface CensoPacienteMapper extends GenericCrudMapper<CensoPaciente, String>{
    List<CensoPacienteExtended> obtenerRegistrosPorCriterioDeBusquedaLazy(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    int insertarCensoPaciente(CensoPaciente censoPaciente);
    
    List<CensoPacienteExtended> obtenerRegistrosHistorico(@Param("idCensoPaciente") String idCensoPaciente, @Param("idPaciente") String idPaciente, @Param("claveDerechohabiencia") String claveDerechohabiencia);
    
    int actualizarCensoPaciente(CensoPaciente censoPaciente);
}
