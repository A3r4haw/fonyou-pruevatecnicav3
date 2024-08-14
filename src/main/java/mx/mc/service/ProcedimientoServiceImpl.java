package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ProcedimientoMapper;
import mx.mc.model.ParamBusquedaReporte;
import mx.mc.model.Procedimiento;
import mx.mc.model.ProcedimientoExtended;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedimientoServiceImpl extends GenericCrudServiceImpl<Procedimiento, String> implements ProcedimientoService{

    @Autowired
    private ProcedimientoMapper estudioMapper;
    
    @Autowired
    public ProcedimientoServiceImpl(GenericCrudMapper<Procedimiento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<ProcedimientoExtended> obtenerBusquedaProcedimiento(ParamBusquedaReporte paramBusquedaReporte, int tipoProcedimiento, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
            return estudioMapper.obtenerBusquedaProcedimiento(paramBusquedaReporte, tipoProcedimiento, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
            throw new Exception("Error al momento de buscar Procedimientos " + e.getMessage()); 
        }
    }

    @Override
    public Long obtenerTotalBusquedaProcedimiento(ParamBusquedaReporte paramBusquedaReporte, int tipoProcedimiento) throws Exception {
        try {
            return estudioMapper.obtenerTotalBusquedaProcedimiento(paramBusquedaReporte, tipoProcedimiento);
        } catch (Exception e) {
            throw new Exception("Error al momento de obtener total de Procedimientos " + e.getMessage());  
        }
    }
}
