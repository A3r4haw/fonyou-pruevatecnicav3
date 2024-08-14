package mx.mc.mapper;

import java.util.List;
import mx.mc.model.FichaPrevencionAdjunto;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Cervanets
 */
public interface FichaPrevencionAdjuntoMapper extends GenericCrudMapper<FichaPrevencionAdjunto, String> {

    boolean insertarListaAdjuntos(@Param("fichaPrevencionAdjuntoLista") List<FichaPrevencionAdjunto> fichaPrevencionAdjuntoLista);

}
