package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ExpPacking;
import mx.mc.mapper.*;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author apalacios
 */
public interface ExpPackingMapper extends GenericCrudMapper<ExpPacking, Long> {
    @Override
    boolean actualizar(@Param("expPacking") ExpPacking expPacking);
}
