/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.model.Prescripcion;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.Surtimiento;
import mx.mc.model.SurtimientoInsumo;

/**
 *
 * @author gcruz
 */
public interface DispensacionDirectaService {
    
    boolean registrarPrescripcion(Prescripcion prescripcion, PrescripcionInsumo prescripcionInsumo, Surtimiento surtimiento, SurtimientoInsumo surtimientoInsumo) throws Exception;
    
    String obtenerclaveEstructuraPeriferico(String idEstructura) throws Exception;
            
    String obtenerFolioPrescripcion() throws Exception;
}
