/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import mx.mc.model.EstatusPrescripcion;

/**
 *
 * @author mcalderon
 */
public interface EstatusPrescripcionMapper extends GenericCrudMapper<EstatusPrescripcion, String> {
      
    List<EstatusPrescripcion> getTipoPrescripcionBytipo(@Param("listaEstatusPrescripcion") List<Integer> listaEstatusPrescripcion);
    
}
