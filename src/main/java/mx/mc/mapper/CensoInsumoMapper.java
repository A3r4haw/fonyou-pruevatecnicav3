package mx.mc.mapper;

import mx.mc.model.CensoInsumo;

/**
 * @author apalacios
 */
public interface CensoInsumoMapper extends GenericCrudMapper<CensoInsumo, String> {
    CensoInsumo obtenerCensoInsumo(CensoInsumo censoInsumo);
    int insertarCensoInsumo(CensoInsumo censoInsumo);
    int actualizarCensoInsumo(CensoInsumo censoInsumo);
}
