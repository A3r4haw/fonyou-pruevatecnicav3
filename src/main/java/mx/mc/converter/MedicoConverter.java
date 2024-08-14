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
import mx.mc.model.Usuario;


import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author bbautista
 */
@FacesConverter(value = "medicoConverter")
public class MedicoConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicoConverter.class);
    
     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
       
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){                                        
                    Usuario sa = new Usuario();
                    sa.setIdUsuario(value);
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
       
        if(entity instanceof Usuario){
            Usuario sa = (Usuario)entity;
            if(sa.getIdUsuario()!= null)
                return sa.getIdUsuario();
        }
        return entity.toString();
    }          
    
    
}
