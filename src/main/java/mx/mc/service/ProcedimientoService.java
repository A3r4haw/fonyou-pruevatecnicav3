package mx.mc.service;

import java.util.List;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Procedimiento;
import mx.mc.model.ProcedimientoExtended;
import org.primefaces.model.SortOrder;

public interface ProcedimientoService extends GenericCrudService<Procedimiento, String>{
    public List<ProcedimientoExtended> obtenerBusquedaProcedimiento(ParamBusquedaReporte paramBusquedaReporte,int tipoProcedimiento, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    public Long obtenerTotalBusquedaProcedimiento(ParamBusquedaReporte paramBusquedaReporte, int tipoProcedimiento) throws Exception;
}
