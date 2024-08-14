/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.HipersensibilidadAdjuntoMapper;
import mx.mc.model.HipersensibilidadAdjunto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class HipersensibilidadAdjuntoServiceImpl extends GenericCrudServiceImpl<HipersensibilidadAdjunto, String> implements HipersensibilidadAdjuntoService {
    
    @Autowired
    HipersensibilidadAdjuntoMapper hipersensibilidadAdjuntoMapper;
    
    public HipersensibilidadAdjuntoServiceImpl(GenericCrudMapper<HipersensibilidadAdjunto, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public boolean insertarListaHiperAdjuntos(List<HipersensibilidadAdjunto> listaHiperAdjuntos) throws Exception {
        try {
            return hipersensibilidadAdjuntoMapper.insertarListaHiperAdjuntos(listaHiperAdjuntos);
        } catch(Exception ex) {
            throw new Exception("Error al insertar la lista intermedia hipersensibilidadAdjunto  " + ex.getMessage());
        }
    }
    
}
