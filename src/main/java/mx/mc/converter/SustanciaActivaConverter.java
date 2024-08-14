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

import mx.mc.model.SustanciaActiva;
import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author bbautista
 */

@FacesConverter(value = "sustanciaActivaConverter")
public class SustanciaActivaConverter implements Converter {        
    private static final Logger LOGGER = LoggerFactory.getLogger(SustanciaActivaConverter.class);
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
       
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){
                    SustanciaActiva sa= new SustanciaActiva();
                    sa.setIdSustanciaActiva(Integer.parseInt(value));
                    return sa;
                }else{
                    return value;
                }
           }
        }catch(NumberFormatException ex){
            LOGGER.trace("Error en SustanciaActivaConverter: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object entity) {

        if(entity instanceof SustanciaActiva){
            SustanciaActiva sa = (SustanciaActiva)entity;
            if(sa.getIdSustanciaActiva()!=null)
                return sa.getIdSustanciaActiva().toString();
        }
        return entity.toString();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }        
    
}
