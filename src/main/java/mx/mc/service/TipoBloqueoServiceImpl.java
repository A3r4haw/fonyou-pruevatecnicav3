/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;

import mx.mc.model.TipoBloqueo;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author olozada
 */
public class TipoBloqueoServiceImpl extends GenericCrudServiceImpl<TipoBloqueo, String> implements TipoBloqueoService {

    @Autowired
    public TipoBloqueoServiceImpl(GenericCrudMapper<TipoBloqueo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
