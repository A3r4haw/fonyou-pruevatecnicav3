/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;

/**
 *
 * @author bbautista
 */
public interface CantidadRazonadaService extends GenericCrudService<CantidadRazonada, String>{
    CantidadRazonada cantidadRazonadaInsumo(String claveInstitucional) throws Exception;
    CantidadRazonadaExtended cantidadRazonadaInsumoPaciente(String idPaciente,String idInsumo) throws Exception;    
    CantidadRazonadaExtended cantidadRazonadaInsumoPacienteExt(String idPaciente,String idInsumo) throws Exception;
    List<CantidadRazonadaExtended> obtenerCantidadRazonada(Integer idTipo) throws Exception;
}
