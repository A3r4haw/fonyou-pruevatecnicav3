/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.EstatusPrescripcion;

import java.util.List;
import mx.mc.mapper.EstatusPrescripcionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mcalderon
 */
@Service
public class EstatusPrescripcionServiceImpl extends GenericCrudServiceImpl<EstatusPrescripcion, String> implements EstatusPrescripcionService {

    @Autowired
    private EstatusPrescripcionMapper estatusPrescripcionMapper;

    @Autowired
    public EstatusPrescripcionServiceImpl(GenericCrudMapper<EstatusPrescripcion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<EstatusPrescripcion> getEstructuraByLisTipoAlmacen(List<Integer> listaEstatusPrescripcion) throws Exception {
        try {
            return estatusPrescripcionMapper.getTipoPrescripcionBytipo(listaEstatusPrescripcion);
        } catch (Exception e) {
            throw  new Exception("Error al obtener los tipos Estatus de Receta Manual " + e.getMessage());
        }
    }


    
    

}
