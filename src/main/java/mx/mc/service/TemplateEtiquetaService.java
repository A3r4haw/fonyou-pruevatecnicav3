/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.service;

import java.util.List;
import mx.mc.model.TemplateEtiqueta;

/**
 *
 * @author bbautista
 */
public interface TemplateEtiquetaService extends GenericCrudService<TemplateEtiqueta, String>{
    public TemplateEtiqueta obtenerById(String idTemplate) throws Exception;
    public List<TemplateEtiqueta> obtenerListaAll() throws Exception;
    public List<TemplateEtiqueta> obtenerTemplateName(String cadena) throws Exception;
    public boolean delete(String idTemp) throws Exception;
    public List<TemplateEtiqueta> obtenerListaTipo(String cadena) throws Exception;
}
