/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.Date;
import java.util.List;
import mx.mc.dto.SurtimientoValidarDTO;
import mx.mc.model.SurtimientoMinistrado;
import mx.mc.model.SurtimientoMinistrado_Extend;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface SurtimientoMinistradoMapper extends GenericCrudMapper<SurtimientoMinistradoMapper,String> {
    boolean insertarSurtimientoMinistradoList(List<SurtimientoMinistrado> ministradoList);
    
    boolean insertListSurtimientosMinistrados(@Param("surtMinistradoList") List<SurtimientoMinistrado> surtMinistradoList);
    
    boolean actualizarSurtimientoMinistrado(@Param("listSurtMinistrado") List<SurtimientoMinistrado> listSurtMinistrado);
    
    List<SurtimientoMinistrado> obtenerSurtimientosMinistrados(@Param("listaSurtimientoInsumos") List<String> listaSurtimientoInsumos);
    
    boolean actualizarSurtMinistradoConfirmado(@Param("listMinistracion") List<SurtimientoMinistrado> listMinistracion);
    
    List<SurtimientoMinistrado_Extend> obtenerSurtimientoMinistrado(@Param("idEstructura") String idEstructura);
    
    List<SurtimientoMinistrado_Extend> obtenerSurtimientoMinistradoLazzy(@Param("idEstructura") String idEstructura, @Param("idUsuario") String idUsuario, 
            @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage);
    
    Long obtenerTotalSurtimientoMinistradoLazzy(@Param("idEstructura") String idEstructura, @Param("idUsuario") String idUsuario);
    
    List<SurtimientoMinistrado_Extend> obtenerListSurtimientoMinistrado(@Param("idSurtimientoEnviado") String idSurtimientoEnviado);
    
    boolean updateSurtMinistradoForDevolucion(@Param("listSurtMinistrado") List<SurtimientoMinistrado_Extend> listSurtMinistrado);
    
    List<SurtimientoMinistrado_Extend> obtenerListSurtimientoMinistradoSolucion(@Param("claveAgrupada") String claveAgrupada);
    
    List<SurtimientoMinistrado_Extend> obtenerMinistracionesPaciente(@Param("idPaciente") String idPaciente, @Param("fechaInicio") Date fechaInicio,@Param("fechaFin") Date fechafin);
    
    List<SurtimientoMinistrado_Extend> obtenerUltimasMinistracionesPac(@Param("idPaciente") String idPaciente);
    
    List<SurtimientoValidarDTO> obtenerSurtimientosAValidar(@Param("idPrescripcionInsumo") String idPrescripcionInsumo);
    
    List<SurtimientoValidarDTO> obtenerSurtimientosAValidarOrdenadoASC(@Param("idPrescripcionInsumo") String idPrescripcionInsumo);
    
    SurtimientoMinistrado_Extend obtenerSurtimientoMinistradoByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);
    
}
