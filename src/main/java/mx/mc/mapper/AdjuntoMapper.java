/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface AdjuntoMapper extends GenericCrudMapper<Adjunto, String> {
    
    Integer obtenerSiguienteAdjunto();
    
    boolean insertarListaAdjuntos(@Param("listaAdjuntos") List<AdjuntoExtended> listaAdjuntos);
    
    List<AdjuntoExtended> obtenerAdjuntosByIdHipersensibilidad(@Param("idHipersensibilidad") String idHipersensibilidad);
    
    Adjunto obtenerAdjuntoByIdAdjunto(@Param("idAdjunto") Integer idAdjunto);
    
    boolean eliminarArchivo(@Param("idAdjunto") Integer idAdjunto);
    
    List<AdjuntoExtended> obtenerAdjuntosByIdFichaPrevencion(String idPrevencion);
    
    Integer obtenerNoRegistrosEncontrados (@Param("idAdjunto") Integer idAdjunto);
    
}
