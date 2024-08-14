package mx.mc.mapper;

import java.util.List;
import mx.mc.model.CensoInsumoDetalle;
import mx.mc.model.CensoInsumoDetalleExtended;
import org.apache.ibatis.annotations.Param;

/**
 * @author apalacios
 */
public interface CensoInsumoDetalleMapper extends GenericCrudMapper<CensoInsumoDetalle, String>{
    boolean insertarListaCensoInsumoDetalle(List<CensoInsumoDetalle> listaCensoInsumoDetalle);
    boolean eliminarListaCensoInsumoDetalle(List<CensoInsumoDetalle> listaCensoInsumoDetalle);
    boolean actualizarListaCensoInsumoDetalle(List<CensoInsumoDetalle> listaCensoInsumoDetalle);
    List<CensoInsumoDetalle> obtenerListaCensoInsumoDetalle(@Param("idCensoInsumo") String idCensoInsumo);
    CensoInsumoDetalleExtended getCensoInsumoDetalleByMedicamentoCensoInsumo(@Param("idCensoInsumo")String idCensoInsumo,@Param("idMedicamento")String idMedicamento,@Param("numeroSurtimiento")int numeroSurtimiento);
}
