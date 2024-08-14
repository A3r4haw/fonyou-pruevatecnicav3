package mx.mc.service;

import mx.mc.mapper.CensoInsumoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.CensoInsumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author apalacios
 */
@Service
public class CensoInsumoServiceImpl extends GenericCrudServiceImpl<CensoInsumo, String> implements CensoInsumoService{
    
    @Autowired
    private CensoInsumoMapper censoInsumoMapper;
    
    @Autowired
    public CensoInsumoServiceImpl(GenericCrudMapper<CensoInsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public CensoInsumo obtenerCensoInsumo(CensoInsumo censoInsumo) throws Exception {
        try {
            return censoInsumoMapper.obtenerCensoInsumo(censoInsumo);
        } catch (Exception e) {
            throw  new Exception("Error al obtener el Registro de CensoInsumo. " + e.getMessage());
        }
    }
}
