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
import mx.mc.model.SolucionExtended;

import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author bbautista
 */
@FacesConverter(value = "solucionExtendedConverter")
public class SolucionExtendedConverter implements Converter{
    private static final Logger LOGGER = LoggerFactory.getLogger(SolucionExtendedConverter.class);

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){                                        
                    SolucionExtended sa = new SolucionExtended();
                    sa.setIdSolucion(value);                    
                    return  sa;
                }else{
                    return value;
                }
           }
        }catch(Exception ex){
            LOGGER.trace("Error en MedicamentoConverter: {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object entity) {
        
        if(entity instanceof SolucionExtended){
            SolucionExtended sa = (SolucionExtended)entity;
            if(sa.getIdSolucion()!=null)
                return sa.getIdSolucion();
        }
        return entity!= null ? entity.toString() : "";
    }
    
}
