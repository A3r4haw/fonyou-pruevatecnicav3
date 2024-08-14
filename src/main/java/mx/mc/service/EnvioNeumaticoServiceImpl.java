/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.EnvioNeumaticoMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EnvioNeumatico;
import mx.mc.model.ParamBusquedaReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author olozada
 */
@Service
public class EnvioNeumaticoServiceImpl extends GenericCrudServiceImpl<EnvioNeumatico , String> implements EnvioNeumaticoService{
    
    public EnvioNeumaticoServiceImpl(){
        //No code needed in constructor
    }
    
    @Autowired
    private EnvioNeumaticoMapper envioNeumaticoMapper;
    
    @Autowired
    public EnvioNeumaticoServiceImpl(GenericCrudMapper<EnvioNeumatico, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public boolean insertNeumaticTable(EnvioNeumatico neumaticap) throws Exception {
        boolean resp = false;
        try {
            resp = envioNeumaticoMapper.insertNeumaticTable(neumaticap);
        } catch (Exception e) {
            throw new Exception("Error al registar Capsula Neumatico " + e);
        }
        return resp;
    }
    
    @Override
    public List<EnvioNeumatico> consultaRepNeumatico(ParamBusquedaReporte paramBusquedaReporte, int startingAt, 
            int maxPerPage) throws Exception {
        try {
            return envioNeumaticoMapper.consultaRepNeumatico(paramBusquedaReporte, startingAt, maxPerPage);
        } catch (Exception ex) {
            throw new Exception("Error al obtener consulta de reporte " + ex.getMessage());
        }
    }
    
    @Override
    public Long obtenerRepNeumatico(ParamBusquedaReporte paramBusquedaReporte) throws Exception {
        try {
            return envioNeumaticoMapper.obtenerRepNeumatico(paramBusquedaReporte);
        } catch (Exception ex) {
            throw new Exception("Error al obtener total de registros " + ex.getMessage());
        }
    }
    
    @Override
    public List<EnvioNeumatico> obtenerCapsulas(String cadenaBusqueda) throws Exception {
        try {
            return envioNeumaticoMapper.obtenerCapsulas(cadenaBusqueda);
        } catch (Exception ex) {
            throw new Exception("Ocurrio un error al obtener usuarios   " + ex.getMessage());
        }
    }
}
