/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.NutricionParenteral;
import mx.mc.model.NutricionParenteralExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface NutricionParenteralMapper extends GenericCrudMapper<NutricionParenteral, String> {
    
   boolean actualizarNutricion(@Param("nutricionParenteral") NutricionParenteralExtended nutricionParenteral); 
    
   List<NutricionParenteralExtended> obtenerBusquedaLazy(@Param("cadena") String cadena
           , @Param("idEstructura") String idEstructura
           , @Param("startingAt") int startingAt, @Param("maxPerPage") int maxPerPage
           , @Param("sortField") String sortField,@Param("sortOrder") String sortOrder
           , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
           , @Param("tipoPrescripcion")String tipoPrescripcion
            , @Param("estatusSolucion")String estatusSolucion
            , @Param("nombreEstructura") String nombreEstructura
            , @Param("tipoSolucion")String tipoSolucion
            , @Param("nombreMedico")String nombreMedico
            , @Param("folio")String folio
            , @Param("fechaProgramada2")String fechaProgramada2
            , @Param("nombrePaciente")String nombrePaciente
            , @Param("cama")String cama
            , @Param("folioPrescripcion")String folioPrescripcion
   );
   
   Long obtenerBusquedaTotalLazy(@Param("cadena") String cadena
           , @Param("idEstructura") String idEstructura
           , @Param("estatusSolucionLista") List<Integer> estatusSolucionLista
           , @Param("tipoPrescripcion")String tipoPrescripcion
            , @Param("estatusSolucion")String estatusSolucion
            , @Param("nombreEstructura") String nombreEstructura
            , @Param("tipoSolucion")String tipoSolucion
            , @Param("nombreMedico")String nombreMedico
            , @Param("folio")String folio
            , @Param("fechaProgramada2")String fechaProgramada2
            , @Param("nombrePaciente")String nombrePaciente
            , @Param("cama")String cama
            , @Param("folioPrescripcion")String folioPrescripcion
   ); 
   
   NutricionParenteralExtended obtenerNutricionParenteralById(@Param("idNutricionParenteral") String idNutricionParenteral);
   
}
