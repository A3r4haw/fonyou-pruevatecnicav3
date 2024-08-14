/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.IndTerapeutica;
import mx.mc.model.IndTerapeuticaExtended;
import org.primefaces.model.SortOrder;

/**
 *
 * @author gcruz
 */
public interface IndTerapeuticaService extends GenericCrudService<IndTerapeutica, String> {
    
    List<IndTerapeuticaExtended> obtenerIndicacionesTerapeuticas(String cadenaBusqueda, 
                                        int startingAt, int maxPerPage, String sortField,
                                        SortOrder sortOrder) throws Exception;
    
    Long obtenerTotalInteracciones(String cadenaBusqueda) throws Exception;
    
    boolean insertarIndicTerapeutica(IndTerapeuticaExtended indTerapeuticaExtended) throws Exception;
    
    IndTerapeuticaExtended obtenerIndicacionPoId(String idIndicacionTerapeutica) throws Exception;
    
    IndTerapeuticaExtended buscarExistencia(IndTerapeuticaExtended indTerapeuticaExtended) throws Exception;
    
    boolean actualizarIndicacionTerap(IndTerapeuticaExtended indTerapeuticaExtended) throws Exception;
    
    boolean eliminarIndicacionTeraPorId(String idIndicacionTerapeutica) throws Exception;
            
}
