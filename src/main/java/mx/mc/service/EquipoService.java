/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package mx.mc.service;

import mx.mc.model.Equipo;

/**
 *
 * @author gcruz
 */
public interface EquipoService extends GenericCrudService<Equipo, String> {
    
    Equipo obtenerByCampo(Equipo equipo) throws Exception;
    
}
