/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.AdjuntoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Adjunto;
import mx.mc.model.AdjuntoExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class AdjuntoServiceImpl extends GenericCrudServiceImpl<Adjunto, String> implements AdjuntoService {
    
    @Autowired
    AdjuntoMapper adjuntoMapper;
    
    public AdjuntoServiceImpl(GenericCrudMapper<Adjunto, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public Integer obtenerSiguienteAdjunto() throws Exception {
        Integer idAdjunto = 0;
        try {
            idAdjunto = adjuntoMapper.obtenerSiguienteAdjunto();
        } catch(Exception ex) {
            throw new Exception("Ocurrio un error al buscar el siguiente registro adjunto:  " + ex.getMessage());
        }
        return idAdjunto;
    }

    @Override
    public boolean insertarListaAdjuntos(List<AdjuntoExtended> listaAdjuntos) throws Exception {
        try {
            return adjuntoMapper.insertarListaAdjuntos(listaAdjuntos);
        } catch(Exception ex) {
            throw new Exception("Error al intentar guardar lista de adjuntos:  " + ex.getMessage());
        }
    }

    @Override
    public List<AdjuntoExtended> obtenerAdjuntosByIdHipersensibilidad(String idHipersensibilidad) throws Exception {
        try {
            return adjuntoMapper.obtenerAdjuntosByIdHipersensibilidad(idHipersensibilidad);
        } catch(Exception ex) {
            throw new Exception("Error al buscar adjuntos en metodo obtenerAdjuntosByIdHipersensibilidad  " + ex.getMessage()); 
        }
    }

    @Override
    public Adjunto obtenerAdjuntoByIdAdjunto(Integer idAdjunto) throws Exception {
        try {
            return adjuntoMapper.obtenerAdjuntoByIdAdjunto(idAdjunto);
        } catch(Exception ex) {
            throw new Exception("Error al obtener adjunto por idAdjunto   " + ex.getMessage());
        }
    }

    @Override
    public boolean eliminarArchivo(Integer idAdjunto) throws Exception {
        try {
            return adjuntoMapper.eliminarArchivo(idAdjunto);
        } catch(Exception ex) {
            throw new Exception("Error al eliminar archivo adjunto por idAdjunto   " + ex.getMessage());
        }
    }
    
    @Override
    public List<AdjuntoExtended> obtenerAdjuntosByIdFichaPrevencion(String idPrevencion) throws Exception {
        try {
            return adjuntoMapper.obtenerAdjuntosByIdFichaPrevencion(idPrevencion);
        } catch(Exception ex) {
            throw new Exception("Error al buscar adjuntos en metodo obtenerAdjuntosByIdFichaPrevencion  " + ex.getMessage()); 
        }
    }
    
}
