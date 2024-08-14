package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ExpEntradas;
import mx.mc.mapper.*;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author apalacios
 */
public interface ExpEntradasMapper extends GenericCrudMapper<ExpEntradas, Long> {
    @Override
    List<ExpEntradas> obtenerLista(ExpEntradas expEntradas);
    boolean actualizarLista(@Param("listExpEntradas") List<ExpEntradas> listExpEntradas);
}
