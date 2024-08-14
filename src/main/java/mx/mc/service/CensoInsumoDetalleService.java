package mx.mc.service;

import java.util.List;
import mx.mc.model.CensoInsumoDetalle;
import mx.mc.model.CensoInsumoDetalleExtended;

/**
 * @author apalacios
 */
public interface CensoInsumoDetalleService extends GenericCrudService<CensoInsumoDetalle, String>{
    public List<CensoInsumoDetalle> obtenerListaCensoInsumoDetalle(String idCensoInsumo) throws Exception;
    
    public CensoInsumoDetalleExtended getCensoInsumoDetalleByMedicamentoCensoInsumo(String idCensoInsumo, String idMedicamento, int numeroSurtimiento)throws Exception;
}
