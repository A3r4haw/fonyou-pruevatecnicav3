package com.seidor.ulises.mapper;

import com.seidor.ulises.model.ImpAlbEntradas;
import mx.mc.mapper.*;
import java.util.List;

/**
 *
 * @author apalacios
 */
public interface ImpAlbEntradasMapper extends GenericCrudMapper<ImpAlbEntradas, Long> {
    boolean registraImpAlbEntradasList (List<ImpAlbEntradas> impAlbEntradasList);
}
