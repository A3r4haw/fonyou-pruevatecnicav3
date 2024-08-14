/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.PrescripcionInsumo;
import mx.mc.model.PrescripcionInsumo_Extended;

/**
 *
 * @author gcruz
 */
public interface PrescripcionInsumoService extends GenericCrudService<PrescripcionInsumo, String> {
    
    
    List<PrescripcionInsumo> obtenerPrescripcionInsumosPorIdPrescripcion(String idPrescripcion) throws Exception;
    
    List<PrescripcionInsumo_Extended> obtenerPrescripcionesByIdPaciente(String idPaciente, String folioSurtimiento) throws Exception;
    boolean registraMedicamentoList (List<PrescripcionInsumo> insumoList) throws Exception;
}
