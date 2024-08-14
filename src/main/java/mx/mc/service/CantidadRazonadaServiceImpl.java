/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.CantidadRazonadaMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.CantidadRazonada;
import mx.mc.model.CantidadRazonadaExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class CantidadRazonadaServiceImpl extends GenericCrudServiceImpl<CantidadRazonada, String> implements CantidadRazonadaService {
    
   @Autowired
   private CantidadRazonadaMapper cantidadRazonadaMapper;
    
    @Autowired
    public CantidadRazonadaServiceImpl(GenericCrudMapper<CantidadRazonada, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public CantidadRazonada cantidadRazonadaInsumo(String claveInstitucional) throws Exception {
        try{
            return cantidadRazonadaMapper.cantidadRazonadaInsumo(claveInstitucional);
        }catch(Exception ex){
            throw new Exception("Error al obtener la cantidad Razonada en cantidadRazonadaInsumo!! " + ex.getMessage());
        }        
    }
    
    @Override
    public CantidadRazonadaExtended cantidadRazonadaInsumoPaciente(String idPaciente, String idInsumo) throws Exception {
        try{
            return cantidadRazonadaMapper.cantidadRazonadaInsumoPaciente(idPaciente, idInsumo);
        }catch(Exception ex){
            throw new Exception("Error al obtener la cantidad Razonada en cantidadRazonadaInsumoPaciente!! " + ex.getMessage());
        }        
    }
    
    @Override
    public CantidadRazonadaExtended cantidadRazonadaInsumoPacienteExt(String idPaciente, String idInsumo) throws Exception {
        try{
            return cantidadRazonadaMapper.cantidadRazonadaInsumoPacienteExt(idPaciente, idInsumo);
        }catch(Exception ex){
            throw new Exception("Error al obtener la cantidad Razonada en cantidadRazonadaInsumoPacienteExt!! " + ex.getMessage());
        }        
    }
    
    @Override
    public List<CantidadRazonadaExtended>  obtenerCantidadRazonada(Integer idTipo) throws Exception{        
        try{
            return cantidadRazonadaMapper.obtenerListaInsumos(idTipo);
        }catch(Exception ex){
            throw new Exception("Error al obterner la lista de insumos"+ex.getMessage());
        }        
    }
            
}
