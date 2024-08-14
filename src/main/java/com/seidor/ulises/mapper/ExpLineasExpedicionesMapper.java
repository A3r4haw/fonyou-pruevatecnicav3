package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ExpLineasExpediciones;
import mx.mc.mapper.*;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author apalacios
 */
public interface ExpLineasExpedicionesMapper extends GenericCrudMapper<ExpLineasExpediciones, Long> {

    @Override
    List<ExpLineasExpediciones> obtenerLista(ExpLineasExpediciones expLineasExpediciones);
    boolean actualizarLista(@Param("listExpLineasExpediciones") List<ExpLineasExpediciones> listExpLineasExpediciones);
    List<ExpLineasExpediciones> obtenerExpLineasExpedicion(@Param("expedicion") String expedicion);
}
