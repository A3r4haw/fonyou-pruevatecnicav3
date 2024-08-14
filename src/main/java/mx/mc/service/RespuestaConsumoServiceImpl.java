/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.RespuestaConsumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class RespuestaConsumoServiceImpl extends GenericCrudServiceImpl<RespuestaConsumo, String> implements RespuestaConsumoService {
    
    @Autowired
    public RespuestaConsumoServiceImpl(GenericCrudMapper<RespuestaConsumo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
}
