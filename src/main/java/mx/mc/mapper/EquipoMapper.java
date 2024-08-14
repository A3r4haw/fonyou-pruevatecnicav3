/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.mc.mapper;

import mx.mc.model.Equipo;

/**
 *
 * @author gcruz
 */
public interface EquipoMapper extends GenericCrudMapper<Equipo, String> {
    
    Equipo obtenerByCampo(Equipo equipo);
    
}
