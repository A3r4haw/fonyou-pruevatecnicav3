/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;

import mx.mc.model.EstatusPrescripcion;

/**
 *
 * @author mcalderon
 */
public interface EstatusPrescripcionService extends GenericCrudService<EstatusPrescripcion, String> {
	
    public List<EstatusPrescripcion> getEstructuraByLisTipoAlmacen(List<Integer> listaEstatusPrescripcion) throws Exception;
    
}
