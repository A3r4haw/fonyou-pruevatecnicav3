/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.IndTerapeuticaMapper;
import mx.mc.model.IndTerapeutica;
import mx.mc.model.IndTerapeuticaExtended;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class IndTerapeuticaServiceImpl extends GenericCrudServiceImpl<IndTerapeutica, String> implements IndTerapeuticaService {
 
    @Autowired
    IndTerapeuticaMapper indTerapeuticaMapper;
    
    public IndTerapeuticaServiceImpl(GenericCrudMapper<IndTerapeutica, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<IndTerapeuticaExtended> obtenerIndicacionesTerapeuticas(String cadenaBusqueda, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        List<IndTerapeuticaExtended> listaInteracciones = new ArrayList<>();
        try {
            String order = sortOrder == SortOrder.ASCENDING ? "asc" : sortOrder == SortOrder.DESCENDING ? "desc" : null;
            listaInteracciones = indTerapeuticaMapper.obtenerIndicacionesTerapeuticas(cadenaBusqueda, startingAt, maxPerPage, sortField, order);
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al buscar la lista de indicaciones terapeuticas:  " + ex.getMessage());
        }
        return listaInteracciones;
    }

    
    @Override
    public Long obtenerTotalInteracciones(String cadenaBusqueda) throws Exception {
        try {
            return indTerapeuticaMapper.obtenerTotalIndicacionesTerapeuticas(cadenaBusqueda);
        }catch(Exception ex) {
            throw new Exception("Ocurrio un error al obtener el total de indicaciones terapeuticas:  " +ex.getMessage());
        }
    }

    @Override
    public boolean insertarIndicTerapeutica(IndTerapeuticaExtended indTerapeuticaExtended) throws Exception {        
        try {
            return indTerapeuticaMapper.insertarIndicTerapeutica(indTerapeuticaExtended);
        } catch(Exception ex) {
            throw new Exception("Error al insertar la indicación terapeutica" + ex.getMessage());
        }
    }

    @Override
    public IndTerapeuticaExtended obtenerIndicacionPoId(String idIndicacionTerapeutica) throws Exception {        
        try {
            return indTerapeuticaMapper.obtenerIndicacionPoId(idIndicacionTerapeutica);
        } catch(Exception ex) {
            throw new Exception("Error al obtener la indicación terapeutica por id: " + idIndicacionTerapeutica + "  " + ex.getMessage());
        }
        
    }

    @Override
    public IndTerapeuticaExtended buscarExistencia(IndTerapeuticaExtended indTerapeuticaExtended) throws Exception {
        try {
            return indTerapeuticaMapper.buscarExistencia(indTerapeuticaExtended);
        } catch(Exception ex) {
            throw new Exception("Error al buscar existencia de interacción " + ex.getMessage());
        }
    }

    @Override
    public boolean actualizarIndicacionTerap(IndTerapeuticaExtended indTerapeuticaExtended) throws Exception {
        try {
            return indTerapeuticaMapper.actualizarIndicacionTerap(indTerapeuticaExtended);
        } catch(Exception ex) {
            throw new Exception("Error al actualizar la interacción terapeutica " + ex.getMessage());
        }
    }

    @Override
    public boolean eliminarIndicacionTeraPorId(String idIndicacionTerapeutica) throws Exception {
        try {
            return indTerapeuticaMapper.eliminarIndicacionTeraPorId(idIndicacionTerapeutica);
        } catch(Exception ex) {
            throw new Exception("Error al eliminar la interacción terapeutica " + ex.getMessage());
        }
    }
}
