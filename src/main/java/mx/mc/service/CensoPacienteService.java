package mx.mc.service;

import java.util.List;
import mx.mc.model.CensoInsumo;
import mx.mc.model.CensoInsumoDetalle;
import mx.mc.model.CensoPaciente;
import mx.mc.model.CensoPacienteExtended;
import mx.mc.model.ParamBusquedaReporte;

/**
 * @author apalacios
 */
public interface CensoPacienteService extends GenericCrudService<CensoPaciente, String>{
    public List<CensoPacienteExtended> obtenerRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
    public Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
    
    public boolean registrarCensoPaciente(CensoPaciente censoPaciente, CensoInsumo censoInsumo, List<CensoInsumoDetalle> listaCensoInsumoDetalle) throws Exception;
    public List<CensoPacienteExtended> obtenerRegistrosHistorico(String idCensoPaciente, String idPaciente, String claveDerechohabiencia) throws Exception;
    public boolean actualizarCensoPaciente(CensoPaciente censoPaciente, CensoInsumo censoInsumo, List<CensoInsumoDetalle> listaCensoInsumoDetalle) throws Exception;
}
