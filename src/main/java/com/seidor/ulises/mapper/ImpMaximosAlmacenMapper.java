package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ImpMaximosAlmacen;
import mx.mc.mapper.*;
import java.util.List;

/**
 *
 * @author apalacios
 */
public interface ImpMaximosAlmacenMapper extends GenericCrudMapper<ImpMaximosAlmacen, Long> {
    boolean registraImpMaximosAlmacenList (List<ImpMaximosAlmacen> impMaximosAlmacenList);
}
