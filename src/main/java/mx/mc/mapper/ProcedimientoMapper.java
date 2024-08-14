package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Procedimiento;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.ProcedimientoExtended;
import org.apache.ibatis.annotations.Param;

public interface ProcedimientoMapper extends GenericCrudMapper<Procedimiento, String> {
    List<ProcedimientoExtended> obtenerBusquedaProcedimiento(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte,
            @Param("tipoProcedimiento") int tipoProcedimiento, @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
            ,@Param("sortField") String sortField, @Param("sortOrder") String sortOrder); 
    
    Long obtenerTotalBusquedaProcedimiento(@Param("paramBusquedaReporte") ParamBusquedaReporte paramBusquedaReporte, @Param("tipoProcedimiento") int tipoProcedimiento);
    
    //Procedimiento obtenerPorIdProcedimiento(@Param("idProcedimiento") String idProcedimiento);
    
    //Procedimiento obtenerPorClaveProcedimiento(@Param("claveProcedimiento") String claveProcedimiento);
}

