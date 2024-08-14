/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.ArrayList;
import java.util.List;
import mx.mc.mapper.EmisorMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Emisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gcruz
 */
@Service
public class EmisorServiceImpl extends GenericCrudServiceImpl<Emisor, String> implements EmisorService {
    
    @Autowired
    EmisorMapper emisorMapper;
    
    public EmisorServiceImpl(GenericCrudMapper<Emisor, String> genericCrudMapper) {
        super(genericCrudMapper);
    }
    
    @Override
    public List<Emisor> obtenerTodo() throws Exception {
        List<Emisor> listaEmisores = new ArrayList<>();
        try {
            listaEmisores = emisorMapper.obtenerTodo();
        } catch(Exception ex) {
            throw new Exception("Ocuurio un error al obtener la lista de emisores " + ex.getMessage());
        }
        return listaEmisores;
    }
}
