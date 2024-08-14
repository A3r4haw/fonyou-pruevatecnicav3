/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.Solucion;
import mx.mc.model.SolucionExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface SolucionMapper extends GenericCrudMapper<Solucion, String> {

    public SolucionExtended obtenerDatosSolucionByIdSurtimiento(@Param("idSurtimiento") String idSurtimiento);

    List<SolucionExtended> obtenerAutoCompSolucion(@Param("cadena") String cadena, @Param("idEstructura") String almacen);

    SolucionExtended obtenerSolucion(@Param("idSolucion") String idSolucion, @Param("idSurtimiento") String idSurtimiento);

    boolean actualizarEstatus(Solucion s);
    
    List<Solucion> obtenerSolucionesByIdPrescripcion(@Param("idPrescripcion") String idPrescripcion); 
    
    boolean actualizarListaSoluciones(List<Solucion> listaSoluciones);
    
    Integer obtenerNumeroSolucionesProcesadas(@Param("idPrescripcion") String idPrescripcion);

    String obtenerDescripcionMezcla(@Param("idSurtimiento") String idSurtimiento);

}
