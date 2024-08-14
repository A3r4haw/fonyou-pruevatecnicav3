/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;

import mx.mc.mapper.SustanciaActivaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.SustanciaActiva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class SustanciaActivaServiceImpl extends GenericCrudServiceImpl<SustanciaActiva,String> implements SustanciaActivaService {
    
    @Autowired
    private SustanciaActivaMapper sustanciaActivaMapper;
    
    @Autowired
    public SustanciaActivaServiceImpl(GenericCrudMapper<SustanciaActiva, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<SustanciaActiva> obtenerTodo() throws Exception{
        List<SustanciaActiva> sustanciaList= new ArrayList<>();
        try{
            sustanciaList= sustanciaActivaMapper.obtenerTodo();
        }catch(Exception ex){
            throw new Exception("Error al obtener Sustancia Activa "+ex.getMessage());
        }
        return sustanciaList;
    }
    
    @Override
    public List<SustanciaActiva> obtenerListaPorNombre(String nombreSuatanciaActiva) throws Exception {
        List<SustanciaActiva> sustanciaActivaList= new ArrayList<>();
        try{
            sustanciaActivaList= sustanciaActivaMapper.obtenerPorNombre(nombreSuatanciaActiva);
        }catch(Exception ex){
            throw new Exception("Error al obtenerListaPorNombre: "+ex.getMessage());
        }
        return sustanciaActivaList;
    }
    @Override
    public SustanciaActiva obtenerPorId(int idSustanciaActiva) throws Exception {
        SustanciaActiva sustanciaActiva= new SustanciaActiva();
        try{
            sustanciaActiva= sustanciaActivaMapper.obtenerPorId(idSustanciaActiva);
        }catch(Exception ex){
            throw new Exception("Error al obtenerPorId: "+ex.getMessage());
        }
        return sustanciaActiva;
    }

    @Override
    public SustanciaActiva obtenerSustanciaPorNombre(String nombreSuatanciaActiva) throws Exception {        
        try{
            return  sustanciaActivaMapper.obtenerSustanciaPorNombre(nombreSuatanciaActiva);
        }catch(Exception ex){
            throw new Exception("Error al obtenerSustanciaPorNombre: "+ex.getMessage());
        }
    }

    @Override
    public Integer obtenerSiguienteId() throws Exception {
        try {
            return sustanciaActivaMapper.obtenerSiguienteId();
        }catch(Exception ex) {
            throw new Exception("Error al momento de obtener el siguiente id consecutivo de sustancia activa " + ex.getMessage());
        }
    }
        
}
