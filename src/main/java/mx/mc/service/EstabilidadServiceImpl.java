/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.EstabilidadMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Estabilidad;
import mx.mc.model.Estabilidad_Extended;
import mx.mc.model.SurtimientoInsumo_Extend;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class EstabilidadServiceImpl extends GenericCrudServiceImpl<Estabilidad, String> implements EstabilidadService {
    
    @Autowired
    EstabilidadMapper estabilidadMapper;
    
    @Autowired
    public EstabilidadServiceImpl (GenericCrudMapper<Estabilidad, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<Estabilidad_Extended> buscarEstabilidad(
            String idInsumo
            , String claveDiluyente
            , Integer idViaAdministracion
            , Integer idFabricante
            , Integer idContenedor
    ) throws Exception {
        try {
            return estabilidadMapper.buscarEstabilidad(idInsumo, claveDiluyente, idViaAdministracion, idFabricante, idContenedor);
        } catch(Exception ex) {
            throw new Exception("Error al buscar la información de la estabilidad :: " + ex.getMessage());
        }
    }

    @Override
    public List<Estabilidad_Extended> obtenerListaEstabilidades(String cadenaBusqueda, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            return estabilidadMapper.obtenerListaEstabilidades(cadenaBusqueda, startingAt, maxPerPage, sortField, sortOrder);
        } catch(Exception ex) {
            throw new Exception("Eror al buscar las estabilidades en metodo: obtenerListaEstabilidades : " +ex.getMessage());
        }
    }

    @Override
    public Long obtenerTotalEstabilidades(String cadenaBusqueda) throws Exception {
        try {
            return estabilidadMapper.obtenerTotalEstabilidades(cadenaBusqueda);
        } catch(Exception ex) {
            throw new Exception("Error al buscar el total de la busqueda de estabilidades en metodo: obtenerTotalEstabilidades : " + ex.getMessage());
        }
    }

    @Override
    public Estabilidad_Extended obtenerEstabilidad(
            String idInsumo
            , String claveDiluyente
            , Integer idViaAdministracion
            , Integer idFabricante
    ) throws Exception {
        try {
            return estabilidadMapper.obtenerEstabilidad(idInsumo, claveDiluyente, idViaAdministracion, idFabricante);
        } catch(Exception ex) {
            throw new Exception("Error al buscar la información de la estabilidad :: " + ex.getMessage());
        }
    }
    
    @Override
    public Estabilidad_Extended obtenerEstabilidadPorId(String idEstabilidad) throws Exception {
        try {
            return estabilidadMapper.obtenerEstabilidadPorId(idEstabilidad);
        } catch (Exception ex) {
            throw new Exception("Error al buscar la información de la estabilidad :: " + ex.getMessage());
        }
    }
}
