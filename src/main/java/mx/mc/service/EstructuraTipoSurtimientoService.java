/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.model.EstructuraTipoSurtimiento;

/**
 *
 * @author bbautista
 */
public interface EstructuraTipoSurtimientoService extends GenericCrudService<EstructuraTipoSurtimiento, String>{
    String[] obtenerIdAlmacen(String idEstructuraAlmacen) throws Exception ;
}
