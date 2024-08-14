/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.LocalidadAVGMapper;
import mx.mc.mapper.MedicamentoMapper;
import mx.mc.model.LocalidadAVG;
import mx.mc.model.Medicamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gcruz
 */
@Service
public class LocalidadAVGServiceImpl extends GenericCrudServiceImpl<LocalidadAVG, String> implements LocalidadAVGService {

    @Autowired
    private LocalidadAVGMapper localidadAVGMapper;
    
    @Autowired
    private MedicamentoMapper medicamentoMapper;
    
    @Autowired
    public LocalidadAVGServiceImpl(GenericCrudMapper<LocalidadAVG, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<LocalidadAVG> obtenerTodo() throws Exception {
        List<LocalidadAVG> localidadAVGList = new  ArrayList<>();
        try{
            localidadAVGList = localidadAVGMapper.obtenerTodo();
        }catch(Exception ex){
            throw new Exception("Error al obtener las localidades - "+ex.getMessage());
        }
        return localidadAVGList;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean asignarLocalidadAVG(Medicamento medicamento, LocalidadAVG localidad) throws Exception {
        boolean res = false;
        try {
            res = medicamentoMapper.actualizarLocalidadAVG(medicamento);
            if(!res) {
                throw new Exception("Ocurrio un error al actualizar la localidad de medicamento");
            } else {
                res = localidadAVGMapper.actualizar(localidad);
                if(!res) {
                    throw new Exception("Ocurrio un error al actualizar la localidad");
                }
            }            
        } catch(Exception ex) {
            throw new Exception("Error al asignar la localidad - "+ex.getMessage());
        }
        return res;
    }
    
}
