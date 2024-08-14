/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import mx.mc.mapper.FoliosMapper;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.model.Folios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class FoliosServiceImpl extends GenericCrudServiceImpl<Folios, String> implements FoliosService {
    
    @Autowired
    private FoliosMapper foliosMapper;
    
    @Autowired
    public FoliosServiceImpl(GenericCrudMapper<Folios, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public Folios obtenerPrefixPorDocument(Integer document) throws Exception {        
        try{
            return foliosMapper.obtenerPrefixPorDocument(document);            
        }catch(Exception ex){
            throw new Exception("Ocurrio un error al obtener el prefix del documento"+ex.getMessage());
        }
    }
        
    @Override
    public boolean actualizaFolios(Folios folio) throws Exception{
        try{
            return foliosMapper.actualizaFolios(folio);
        }catch(Exception ex){
            throw new Exception("Ocurrio una excepcion al actualizar el folio"+ex);
        }
    }
    
}
