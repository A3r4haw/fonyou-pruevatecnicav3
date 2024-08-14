package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ImpLineasAlbEntradas;
import mx.mc.mapper.*;
import java.util.List;

/**
 *
 * @author apalacios
 */
public interface ImpLineasAlbEntradasMapper extends GenericCrudMapper<ImpLineasAlbEntradas, Long> {
    boolean registraImpLineasAlbEntradasList (List<ImpLineasAlbEntradas> impLineasAlbEntradasList);
}
