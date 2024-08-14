/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.MedicamentoConcomitanteMapper;
import mx.mc.model.MedicamentoConcomitante;
import mx.mc.model.MedicamentoConcomitanteExtended;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class MedicamentoConcomitanteServiceImpl 
        extends GenericCrudServiceImpl<MedicamentoConcomitante, String> 
        implements MedicamentoConcomitanteService{

    @Autowired
    public MedicamentoConcomitanteMapper concomitanteMapper;
    
    @Override
    public List<MedicamentoConcomitanteExtended> obtenerListaByIdReaccion(String idReaccion) throws Exception{
        try{
            return concomitanteMapper.obtenerListaByIdReaccion(idReaccion);
        }catch(Exception ex){
            throw new Exception("Ocurrio un error al obtener la lista"+ex);
        }
    }
    
    @Override
    public boolean insertarLista(List<MedicamentoConcomitante> mediList) throws Exception{
        try{
            return concomitanteMapper.insertarListaInsumos(mediList);
        }catch(Exception ex){
            throw new Exception("Ocurrio un error al insertar la lista"+ex);
        }    
    }
    
    @Override
    public boolean actualizarListaInsumos(List<MedicamentoConcomitante> mediList) throws Exception{
        try{
            return concomitanteMapper.actualizarListaInsumos(mediList);
        }catch(Exception ex){
            throw new Exception("Ocurrio un error al insertar la lista"+ex);
        }
    }
}
