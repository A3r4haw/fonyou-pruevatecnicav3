/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.model.AlertaFarmacovigilancia;
import mx.mc.model.Interaccion;
import mx.mc.model.InteraccionExtended;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 */
public interface InteraccionService extends GenericCrudService<Interaccion, String> {
    
    List<InteraccionExtended> obtenerInteracciones(String cadenaBusqueda, int tipoInteraccion, int startingAt,
                                                int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    Long obtenerTotalInteracciones(String cadenaBusqueda, int tipoInteraccion) throws Exception;
    
    Integer obtenerSiguienteIdInteraccion() throws Exception;
    
    Boolean insertarInteraccion(InteraccionExtended interaccion) throws Exception;
    
    InteraccionExtended buscarInteraccion(InteraccionExtended interaccionExtended) throws Exception;
    
    boolean eliminarInteraccionPorId(Integer idInteraccion) throws Exception;
    
    List<AlertaFarmacovigilancia> obtenerAlertaInteraccion(String pacienteNumero) throws Exception;
    
    InteraccionExtended obtenerInteraccionById(Integer idInteraccion) throws Exception;
    
    Boolean actualizarInteraccion(InteraccionExtended interaccion) throws Exception;
    
    List<InteraccionExtended> obtenerInteraccionesClavesMedicamento(List<MedicamentoDTO> listaClavesMedicamento)throws Exception;
    
}
