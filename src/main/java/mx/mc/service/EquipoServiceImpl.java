/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.service;

import mx.mc.mapper.EquipoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Equipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class EquipoServiceImpl extends GenericCrudServiceImpl<Equipo, String> implements EquipoService {
    
    @Autowired
    EquipoMapper equipoMapper;
    
    @Autowired
    public EquipoServiceImpl (GenericCrudMapper<Equipo, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public Equipo obtenerByCampo(Equipo equipo) throws Exception {
        try {
            return equipoMapper.obtenerByCampo(equipo);
        } catch(Exception ex) {
            throw new Exception("Error al buscar la información del equipo :: " + ex.getMessage());
        }
    }
    
    
    
}
