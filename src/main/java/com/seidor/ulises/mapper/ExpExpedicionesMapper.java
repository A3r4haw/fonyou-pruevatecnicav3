package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ExpExpediciones;
import mx.mc.mapper.*;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author apalacios
 */
public interface ExpExpedicionesMapper extends GenericCrudMapper<ExpExpediciones, Long> {

    @Override
    List<ExpExpediciones> obtenerLista(ExpExpediciones expExpediciones);
    boolean actualizarLista(@Param("listExpExpediciones") List<ExpExpediciones> listExpExpediciones);
}
