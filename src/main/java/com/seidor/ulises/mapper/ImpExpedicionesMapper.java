package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ImpExpediciones;
import mx.mc.mapper.*;
import java.util.List;

/**
 *
 * @author apalacios
 */
public interface ImpExpedicionesMapper extends GenericCrudMapper<ImpExpediciones, Long> {
    boolean registraImpExpedicionesList (List<ImpExpediciones> impExpedicionesList);
}
