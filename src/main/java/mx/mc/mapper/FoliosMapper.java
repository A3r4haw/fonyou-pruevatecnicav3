/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import mx.mc.model.Folios;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface FoliosMapper extends GenericCrudMapper<Folios, String> {
    Folios obtenerPrefixPorDocument(@Param("document") Integer document);
    boolean actualizaFolios(Folios folio);
}
