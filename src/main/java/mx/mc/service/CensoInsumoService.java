package mx.mc.service;

import mx.mc.model.CensoInsumo;

/**
 * @author apalacios
 */
public interface CensoInsumoService extends GenericCrudService<CensoInsumo, String>{
    public CensoInsumo obtenerCensoInsumo(CensoInsumo censoInsumo) throws Exception;
}
