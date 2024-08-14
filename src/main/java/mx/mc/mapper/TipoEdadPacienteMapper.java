/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.TipoEdadPaciente;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface TipoEdadPacienteMapper extends GenericCrudMapper<TipoEdadPaciente, String> {
    
    List<TipoEdadPaciente> obtenerTodo();
    
    TipoEdadPaciente obtenerTipoPorId(@Param("idTipoEdadPaciente") Integer idTipoEdadPaciente);
    
}
