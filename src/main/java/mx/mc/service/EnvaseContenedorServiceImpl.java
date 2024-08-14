/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.EnvaseContenedorMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EnvaseContenedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class EnvaseContenedorServiceImpl extends GenericCrudServiceImpl<EnvaseContenedor, String> implements EnvaseContenedorService{

    @Autowired
    EnvaseContenedorMapper envaseMapper; 
    
    @Autowired
    public EnvaseContenedorServiceImpl(GenericCrudMapper<EnvaseContenedor, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public EnvaseContenedor obtenerXidEnvase(Integer idEnvase) throws Exception{
        try {
            return envaseMapper.obtenerXidEnvase(idEnvase);
        } catch (Exception ex) {
            throw new Exception("Error al obtener el envase. " + ex.getMessage());
        }
    }
}
