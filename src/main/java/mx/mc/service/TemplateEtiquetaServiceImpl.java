/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.mapper.GenericCrudMapper;
import mx.mc.mapper.TemplateEtiquetaMapper;
import mx.mc.model.TemplateEtiqueta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bbautista
 */
@Service
public class TemplateEtiquetaServiceImpl extends GenericCrudServiceImpl<TemplateEtiqueta, String> implements TemplateEtiquetaService {
    
    @Autowired
    private TemplateEtiquetaMapper templateMapper;

    @Autowired
    public TemplateEtiquetaServiceImpl(GenericCrudMapper<TemplateEtiqueta, String> genericCrudMapper) {
        super(genericCrudMapper);
    }

    @Override
    public TemplateEtiqueta obtenerById(String idTemplate) throws Exception {
        try{
            return templateMapper.obtenerById(idTemplate);
        }catch(Exception ex){
            throw new Exception("No se pudo obtener el Template");
        }
    }        
    
    @Override
    public List<TemplateEtiqueta> obtenerListaAll() throws Exception {
        try{
            return templateMapper.obtenerListaAll();
        }catch(Exception ex){
            throw new Exception("No se pudo obtenerListaAll");
        }
    }

    @Override
    public List<TemplateEtiqueta> obtenerTemplateName(String cadena) throws Exception {
        try{
            return templateMapper.obtenerTemplateName(cadena);
        }catch(Exception ex){
            throw new Exception("No se pudo obtenerTemplateName");
        }
    }

    @Override
    public boolean delete(String idTemp) throws Exception {
        try{
            return templateMapper.delete(idTemp);
        }catch(Exception ex){
            throw new Exception("No se pudo eliminar el Template");
        }
    }

    @Override
    public List<TemplateEtiqueta> obtenerListaTipo(String cadena) throws Exception {
        try{
            return templateMapper.obtenerListaTipo(cadena);
        }catch(Exception ex){
            throw new Exception("No se pudo obtenerListaTipo");
        }
    }
}
