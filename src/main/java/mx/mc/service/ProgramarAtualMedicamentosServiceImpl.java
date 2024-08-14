/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.ProgramarAtualMedicamentosMapper;
import mx.mc.model.ProgramarActualizaMedicamentos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class ProgramarAtualMedicamentosServiceImpl extends GenericCrudServiceImpl<ProgramarActualizaMedicamentos, String> 
        implements ProgramarAtualMedicamentosService {
    
    @Autowired
    private ProgramarAtualMedicamentosMapper programarAtualMedicamentosMapper;
    
    @Autowired
    public ProgramarAtualMedicamentosServiceImpl(GenericCrudMapper<ProgramarActualizaMedicamentos, String> genericCrudMapper) {
        super(genericCrudMapper);
    }    
    
    @Override
    public List<ProgramarActualizaMedicamentos> obtenerFechasProgramadas() throws Exception {
        
        try {
            return programarAtualMedicamentosMapper.obtenerFechasProgramadas();
            
        } catch(Exception ex) {
            throw new Exception("Error al obtener lista programadas de actualización automatica de medicamentos:  " + ex.getMessage()); 
        }
    }
    
    @Override
    public boolean cancelarFechaProgramada(String idProgramarActualizaMedicamentos) throws Exception {
        
        try {
            return programarAtualMedicamentosMapper.cancelarFechaProgramada(idProgramarActualizaMedicamentos);
        } catch(Exception ex) {
            throw new Exception("Error al cancelar la fecha programada de medicamentos:  " + ex.getMessage());
        }
    }
}
