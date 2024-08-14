/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.dto.MedicamentoDTO;
import mx.mc.model.AdjuntoExtended;
import mx.mc.model.Folios;
import mx.mc.model.Hipersensibilidad;
import mx.mc.model.HipersensibilidadAdjunto;
import mx.mc.model.HipersensibilidadExtended;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 */
public interface HipersensibilidadService extends GenericCrudService<Hipersensibilidad, String> {
    
    List<HipersensibilidadExtended> obtenerReaccionesHipersensibilidad(String cadenaBusqueda, 
            int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception;
    
    Long obtenerTotalReacionesHipersensibilidad(String cadenaBusqueda) throws Exception;
    
    boolean registrarReaccionHipersensibilidad(Folios folioHipersensibilidad, HipersensibilidadExtended hipersensibilidadExtended,
                    List<AdjuntoExtended> listaAdjuntos, List<HipersensibilidadAdjunto> listaHiperAdjunto) throws Exception;
    
    HipersensibilidadExtended obtenerHipersensibilidadPorIdHiper(String idHipersensibilidad) throws Exception;
    
    boolean actualizarReaccionHipersensibilidad(HipersensibilidadExtended hipersensibilidadExtended, 
                    List<AdjuntoExtended> listaAdjuntos, List<HipersensibilidadAdjunto> listaHiperAdjunto) throws Exception;
    
    boolean eliminarHipersensibilidad(String idHipersensibilidad) throws Exception;
    
    List<HipersensibilidadExtended> obtenerListaReacHiperPorIdPaciente(String idPaciente, List<MedicamentoDTO> listaMedicamentos) throws Exception;
    
}
