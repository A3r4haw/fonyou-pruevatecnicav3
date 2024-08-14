package mx.mc.mapper;

import java.util.List;
import mx.mc.model.CensoRegla;
import mx.mc.model.CensoReglaExtended;
import mx.mc.model.ParamBusquedaReporte;
import org.apache.ibatis.annotations.Param;

/**
 * @author apalacios
 */
public interface CensoReglaMapper extends GenericCrudMapper<CensoRegla, String>{
    CensoReglaExtended obtenerRegla(@Param("idMedicamento") String idMedicamento, @Param("idDiagnostico") String idDiagnostico);
    
    List<CensoReglaExtended> obtenerRegistrosPorCriterioDeBusquedaLazy(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
			@Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    Long obtenerTotalRegistrosPorCriterioDeBusquedaLazy(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte);
    
    int insertarCensoRegla(CensoRegla censoRegla);
}
