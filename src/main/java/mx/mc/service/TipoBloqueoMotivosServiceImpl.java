/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoBloqueoMotivosMapper;
import mx.mc.model.TipoBloqueoMotivos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author olozada
 */
@Service
public class TipoBloqueoMotivosServiceImpl extends GenericCrudServiceImpl <TipoBloqueoMotivos, String> implements TipoBloqueoMotivosService {
    
    @Autowired
    private TipoBloqueoMotivosMapper tipoBloqueoMotivosMapper;

    @Autowired
    public TipoBloqueoMotivosServiceImpl(GenericCrudMapper<TipoBloqueoMotivos, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public List<TipoBloqueoMotivos> obtieneListaByIdTipoBloqueo(int activo) throws Exception {
        List<TipoBloqueoMotivos> listTipobloqueosMotivos = new ArrayList<>();
        try {
            listTipobloqueosMotivos = tipoBloqueoMotivosMapper.obtieneListaByIdTipoBloqueo(activo);

        } catch (Exception ex) {
            throw new Exception("Error al obtener Bloqueoso!! " + ex.getMessage());
        }
        return listTipobloqueosMotivos;
    }
        
    
}
