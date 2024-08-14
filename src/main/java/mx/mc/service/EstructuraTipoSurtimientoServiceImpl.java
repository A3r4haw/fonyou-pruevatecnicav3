/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.EstructuraTipoSurtimientoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EstructuraTipoSurtimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class EstructuraTipoSurtimientoServiceImpl extends GenericCrudServiceImpl<EstructuraTipoSurtimiento, String> implements EstructuraTipoSurtimientoService{
    
    @Autowired
    EstructuraTipoSurtimientoMapper estructuraTipoSurtiminetoMapper;
    
    @Autowired
    public EstructuraTipoSurtimientoServiceImpl(GenericCrudMapper<EstructuraTipoSurtimiento, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public String[] obtenerIdAlmacen(String idEstructuraAlmacen) throws Exception {
        try {
            return estructuraTipoSurtiminetoMapper.obtenerIdAlmacen(idEstructuraAlmacen);
        } catch (Exception ex) {
            throw new Exception("Error al obtener obtenerIdAlmacen - " + ex.getMessage());
        }
    }
}
