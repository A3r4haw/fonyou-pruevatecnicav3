package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ImpArticulos;
import mx.mc.mapper.*;
import java.util.List;

/**
 *
 * @author apalacios
 */
public interface ImpArticulosMapper extends GenericCrudMapper<ImpArticulos, Long> {
    boolean registraImpArticulosList (List<ImpArticulos> impArticulosList);
}
