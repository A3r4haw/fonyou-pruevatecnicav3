/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.IndTerapeutica;
import mx.mc.model.IndTerapeuticaExtended;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author gcruz
 */
public interface IndTerapeuticaMapper extends GenericCrudMapper<IndTerapeutica, String> {
    
    List<IndTerapeuticaExtended> obtenerIndicacionesTerapeuticas(@Param("cadena")String cadenaBusqueda, @Param("startingAt") int startingAt, 
                                                        @Param("maxPerPage") int maxPerPage, @Param("sortField") String sortField,
                                                        @Param("sortOrder") String sortOrder);
    
    Long obtenerTotalIndicacionesTerapeuticas(@Param("cadena")String cadenaBusqueda);
    
    boolean insertarIndicTerapeutica(IndTerapeuticaExtended indTerapeuticaExtended);
    
    IndTerapeuticaExtended obtenerIndicacionPoId(@Param("idIndTerapeutica") String idIndTerapeutica);
    
    IndTerapeuticaExtended buscarExistencia(IndTerapeuticaExtended indTerapeuticaExtended);
    
    boolean actualizarIndicacionTerap(IndTerapeuticaExtended indTerapeuticaExtended);
    
    boolean eliminarIndicacionTeraPorId(@Param("idIndTerapeutica") String idIndicacionTerapeutica);
    
    List<IndTerapeuticaExtended> buscarDiagnosYMedicamento(@Param("claveMedicamento") String claveMedicamento, @Param("claveDiagnostico") String claveDiagnostico);
    
}
