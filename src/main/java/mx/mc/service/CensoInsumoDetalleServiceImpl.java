package mx.mc.service;

import java.util.List;
import mx.mc.mapper.CensoInsumoDetalleMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.CensoInsumoDetalle;
import mx.mc.model.CensoInsumoDetalleExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author apalacios
 */
@Service
public class CensoInsumoDetalleServiceImpl extends GenericCrudServiceImpl<CensoInsumoDetalle, String> implements CensoInsumoDetalleService{
    
    @Autowired
    private CensoInsumoDetalleMapper censoInsumoDetalleMapper;
    
    @Autowired
    public CensoInsumoDetalleServiceImpl(GenericCrudMapper<CensoInsumoDetalle, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<CensoInsumoDetalle> obtenerListaCensoInsumoDetalle(String idCensoInsumo) throws Exception {
        try {
            return censoInsumoDetalleMapper.obtenerListaCensoInsumoDetalle(idCensoInsumo);
        } catch (Exception e) {
            throw  new Exception("Error al obtener lista de CensoInsumoDetalle. " + e.getMessage());
        }
    }

    @Override
    public CensoInsumoDetalleExtended getCensoInsumoDetalleByMedicamentoCensoInsumo(String idCensoInsumo, String idMedicamento, int numeroSurtimiento) throws Exception {
        try {
            return censoInsumoDetalleMapper.getCensoInsumoDetalleByMedicamentoCensoInsumo(idCensoInsumo, idMedicamento, numeroSurtimiento);
        } catch (Exception e) {
            throw new Exception("Error al obtener el CensoDetalleInsumo por Medicamento, numeroSurtimiento y idCensoInsumo"+ e.getMessage());
        }
    }
}
