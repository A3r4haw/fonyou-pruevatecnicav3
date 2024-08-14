/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import mx.mc.model.EnvaseContenedor;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface EnvaseContenedorMapper extends GenericCrudMapper<EnvaseContenedor, String> {
    public EnvaseContenedor obtenerXidEnvase(@Param("idEnvase") Integer idEnvase);
}
