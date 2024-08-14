/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.mapper;

import java.util.List;
import mx.mc.model.TemplateEtiqueta;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author bbautista
 */
public interface TemplateEtiquetaMapper extends GenericCrudMapper<TemplateEtiqueta, String>{
    
    TemplateEtiqueta obtenerById(@Param("idTemplate") String idTemplate);
    TemplateEtiqueta obtenerByIpImpresora(@Param("ipImpresora") String ipImpresora);
    List<TemplateEtiqueta> obtenerListaAll();
    List<TemplateEtiqueta> obtenerTemplateName(@Param("nombre") String nombre);
    boolean delete(@Param("idTemplate") String idTemplate);
    List<TemplateEtiqueta> obtenerListaTipo(@Param("tipo") String tipo);
}
