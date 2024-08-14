/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.mc.model.ReportInventarioExistencias;

import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author bbautista
 */
@FacesConverter(value = "rptInventarioConverter")
public class RptInventarioConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RptInventarioConverter.class);
     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
       
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){                                        
                    ReportInventarioExistencias sa = new ReportInventarioExistencias();
                    sa.setIdInventario(value);
                    return  sa;
                }else{
                    return value;
                }
           }
        }catch(Exception ex){
            LOGGER.trace("Error en SkuSapConverter: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object entity) {
       
        if(entity instanceof ReportInventarioExistencias){
            ReportInventarioExistencias sa = (ReportInventarioExistencias)entity;
            if(sa.getIdInventario() != null)
                return sa.getIdInventario();
        }
        return entity.toString();
    }          
    
    
}

