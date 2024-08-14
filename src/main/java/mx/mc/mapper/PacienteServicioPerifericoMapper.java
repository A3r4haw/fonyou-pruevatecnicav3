/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import mx.mc.model.PacienteServicioPeriferico;

/**
 *
 * @author mcalderon
 */
public interface PacienteServicioPerifericoMapper extends GenericCrudMapper<PacienteServicioPeriferico, String> {
    
    public PacienteServicioPeriferico obtenerDatos(PacienteServicioPeriferico pacienteServicioPeriferico);
    
}
