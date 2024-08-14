package mx.mc.service;

import java.util.List;
import mx.mc.model.CensoRegla;
import mx.mc.model.CensoReglaDetalle;
import mx.mc.model.CensoReglaExtended;
import mx.mc.model.ParamBusquedaReporte;

/**
 * @author apalacios
 */
public interface CensoReglaService extends GenericCrudService<CensoRegla, String>{
    public CensoReglaExtended obtenerRegla(String idMedicamento, String idDiagnostico) throws Exception;
    public List<CensoReglaDetalle> obtenerReglaDetalle(String idCensoRegla) throws Exception;
    
    public List<CensoReglaExtended> obtenerRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage) throws Exception;
    public Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(ParamBusquedaReporte paramBusquedaReporte) throws Exception;
    
    public boolean registrarCensoRegla(CensoRegla censoRegla, List<CensoReglaDetalle> listaCensoReglaDetalle) throws Exception;
}
