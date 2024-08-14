/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.ProgramarActualizaMedicamentos;

/**
 *
 * @author gcruz
 */
public interface ProgramarAtualMedicamentosService extends GenericCrudService<ProgramarActualizaMedicamentos, String> {
    
    List<ProgramarActualizaMedicamentos> obtenerFechasProgramadas() throws Exception;
    
    boolean cancelarFechaProgramada(String idProgramarActualizaMedicamentos) throws Exception;
    
}
