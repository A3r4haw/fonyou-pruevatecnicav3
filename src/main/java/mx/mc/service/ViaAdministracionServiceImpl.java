/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;

import mx.mc.model.ViaAdministracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.mc.mapper.ViaAdministracionMapper;
import mx.mc.model.ParamBusquedaReporte;
import org.primefaces.model.SortOrder;
/**
 *
 * @author bbautista
 */
@Service
public class ViaAdministracionServiceImpl extends GenericCrudServiceImpl<ViaAdministracion,String> implements ViaAdministracionService {
    
    @Autowired
    private ViaAdministracionMapper viaAdministracionMapper;
    
    @Autowired
    public ViaAdministracionServiceImpl(GenericCrudMapper<ViaAdministracion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<ViaAdministracion> obtenerTodo() throws Exception{        
        List<ViaAdministracion> viaAdministracionList = new  ArrayList<>();
        try{
            viaAdministracionList= viaAdministracionMapper.obtenerTodo();
        }catch(Exception ex){
            throw new Exception("Error al obtener datos de Via Administración - "+ex.getMessage());
        }
        return viaAdministracionList;
    }
    
    @Override
    public ViaAdministracion obtenerPorId(Integer idViaAdministracion) throws Exception {
        ViaAdministracion viaAdministracion;
        try{
            viaAdministracion = viaAdministracionMapper.obtenerPorId(idViaAdministracion);
        } catch(Exception ex) {
            throw new Exception("Error al obtener datos de Via Administración - " + ex.getMessage());
        }
        return viaAdministracion;
    }

    @Override
    public Integer obtenerSiguienteId() throws Exception {
        try {
            return viaAdministracionMapper.obtenerSiguienteId();
        }catch(Exception ex) {
            throw new Exception("Error al momento de obtener el siguiente id consecutivo de administración " + ex.getMessage());
        }
    }

    @Override
    public String validaNombreExistenteVia(String nombreViaAdministracion) throws Exception {
        try {
            return viaAdministracionMapper.validaNombreExistenteVia(nombreViaAdministracion);
        }catch(Exception ex) {
            throw new Exception("Error al momento de validar el nombre de la vía de administración " + ex.getMessage());
}
    }

    @Override
    public List<ViaAdministracion> obtenerBusquedaViaAdministraciones(ParamBusquedaReporte paramBusquedaReporte, int startingAt, int maxPerPage, String sortField, SortOrder sortOrder) throws Exception {
        try {
            String order = sortOrder==SortOrder.ASCENDING ? "asc": sortOrder==SortOrder.DESCENDING ? "desc" : null;            
            return  viaAdministracionMapper.obtenerBusquedaViaAdministraciones(paramBusquedaReporte, startingAt, maxPerPage, sortField, order);
        } catch (Exception e) {
           throw new Exception("Error al momento de buscar Medicamento  " + e.getMessage()); 
        }
    }

    @Override
    public Long obtenerTotalBusquedaViaAdministraciones(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return viaAdministracionMapper.obtenerTotalBusquedaViaAdministraciones(paramBusquedaReporte);
        } catch (Exception e) {
           throw new Exception("Error al momento de obtenerTotalBusquedaMedicamento " + e.getMessage());  
        }
    }
}
