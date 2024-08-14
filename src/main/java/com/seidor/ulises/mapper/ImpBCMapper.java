package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ImpBC;
import mx.mc.mapper.*;
import java.util.List;

/**
 *
 * @author apalacios
 */
public interface ImpBCMapper extends GenericCrudMapper<ImpBC, Long> {
    boolean registraImpBCList (List<ImpBC> impBCList);
}
