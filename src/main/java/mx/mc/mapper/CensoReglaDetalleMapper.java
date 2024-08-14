package mx.mc.mapper;

import java.util.List;
import mx.mc.model.CensoReglaDetalle;
import org.apache.ibatis.annotations.Param;

/**
 * @author apalacios
 */
public interface CensoReglaDetalleMapper extends GenericCrudMapper<CensoReglaDetalle, String>{
    List<CensoReglaDetalle> obtenerReglaDetalle(@Param("idCensoRegla") String idCensoRegla);
    
    boolean insertarListaCensoReglaDetalle(List<CensoReglaDetalle> listaCensoReglaDetalle);
}
