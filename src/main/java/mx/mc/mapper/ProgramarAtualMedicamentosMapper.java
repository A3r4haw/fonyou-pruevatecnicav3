/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.ProgramarActualizaMedicamentos;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface ProgramarAtualMedicamentosMapper extends GenericCrudMapper<ProgramarActualizaMedicamentos, String> {
    
    List<ProgramarActualizaMedicamentos> obtenerFechasProgramadas();    
    /**
     * @param idProgramarActualizaMedicamentos
     * @return boolean
     */
    boolean cancelarFechaProgramada(@Param("idProgramarActualizaMedicamentos") String idProgramarActualizaMedicamentos);
    
}
