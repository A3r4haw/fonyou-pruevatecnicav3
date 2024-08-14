/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
/**
 *
 * @author gcruz
 */
public interface AdjuntoService extends GenericCrudService<Adjunto, String> {
    
    Integer obtenerSiguienteAdjunto() throws Exception;
    
    boolean insertarListaAdjuntos(List<AdjuntoExtended> listaAdjuntos) throws Exception;
    
    List<AdjuntoExtended> obtenerAdjuntosByIdHipersensibilidad(String idHipersensibilidad) throws Exception;
    
    Adjunto obtenerAdjuntoByIdAdjunto(Integer idAdjunto) throws Exception;
    
    boolean eliminarArchivo(Integer idAdjunto) throws Exception;
    
    public List<AdjuntoExtended> obtenerAdjuntosByIdFichaPrevencion(String idPrevencion) throws Exception;
    
}
