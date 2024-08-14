/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TipoSolucionMapper;
import mx.mc.model.TipoSolucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author apalacios
 */
@Service
public class TipoSolucionServiceImpl extends GenericCrudServiceImpl<TipoSolucion,String> implements TipoSolucionService {
    @Autowired
    private TipoSolucionMapper tipoSolucionMapper;
    
    public TipoSolucionServiceImpl(GenericCrudMapper<TipoSolucion, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<TipoSolucion> obtenerByListaClaves(List<String> listaClaves) throws Exception {
        List<TipoSolucion> tipoSolucionList = new ArrayList<>();
        try {
            tipoSolucionList = tipoSolucionMapper.obtenerByListaClaves(listaClaves);
        } catch (Exception ex) {
            throw new Exception("Error al obtener la lista de Tipos de Solución: " + ex.getMessage());
        }
        return tipoSolucionList;
    }
}
