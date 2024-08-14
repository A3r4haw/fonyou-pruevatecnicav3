package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ImpLineasExpediciones;
import mx.mc.mapper.*;
import java.util.List;

/**
 *
 * @author apalacios
 */
public interface ImpLineasExpedicionesMapper extends GenericCrudMapper<ImpLineasExpediciones, Long> {
    boolean registraImpLineasExpedicionesList (List<ImpLineasExpediciones> impLineasExpedicionesList);
}
