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
import mx.mc.model.ReabastoInsumoExtended;

import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author mcalderon
 */
@FacesConverter(value = "reabastoInsumoExtendedConverter")
public class ReabastoInsumoExtendedConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReabastoInsumoExtendedConverter.class);
     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
       
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){                                        
                    ReabastoInsumoExtended sa = new ReabastoInsumoExtended();
                    sa.setIdInventario(value);
                    return  sa;
                }else{
                    return value;
                }
           }
        }catch(Exception ex){
            LOGGER.trace("Error en reabastoInsumoExtendedConverter: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object entity) {
       
        if(entity instanceof ReabastoInsumoExtended){
            ReabastoInsumoExtended sa = (ReabastoInsumoExtended)entity;
            if(sa.getIdInventario() != null)
                return sa.getIdInventario();
        }
        return entity.toString();
    }          
    
    
}
