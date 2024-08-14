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
import mx.mc.model.Medicamento_Extended;
import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@FacesConverter(value = "medicamentoExtendedConverter")
public class Medicamento_ExtendedConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Medicamento_ExtendedConverter.class);
    
     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
       
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){                                        
                    Medicamento_Extended sa = new Medicamento_Extended();
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
       
        if(entity instanceof Medicamento_Extended){
            Medicamento_Extended sa = (Medicamento_Extended)entity;
            if(sa.getIdInventario() != null)
                return sa.getIdInventario();
        }
        return entity.toString();
    }    
    
}