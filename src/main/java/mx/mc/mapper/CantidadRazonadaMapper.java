/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface CantidadRazonadaMapper extends GenericCrudMapper<CantidadRazonada, String>{
    CantidadRazonada cantidadRazonadaInsumo(@Param("claveInstitucional")String claveInstitucional) ; 
    CantidadRazonadaExtended cantidadRazonadaInsumoPaciente(@Param("idPaciente")String idPaciente,@Param("idInsumo")String idInsumo) ; 
    CantidadRazonadaExtended cantidadRazonadaInsumoPacienteExt(@Param("idPaciente")String idPaciente,@Param("idInsumo")String idInsumo); 
    List<CantidadRazonadaExtended> obtenerListaInsumos(@Param("idTipoInsumo") Integer idTipoInsumo);
}
