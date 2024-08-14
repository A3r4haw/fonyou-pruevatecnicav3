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
import mx.mc.model.Paciente_Extended;


import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author bbautista
 */
@FacesConverter(value = "pacienteConverter")
public class PacienteConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteConverter.class);
     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
       
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){                                        
                    Paciente_Extended sa = new Paciente_Extended();
                    sa.setIdPaciente(value);
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
       
        if(entity instanceof Paciente_Extended){
            Paciente_Extended sa = (Paciente_Extended)entity;
            if(sa.getIdPaciente()!= null)
                return sa.getIdPaciente();
        }
        return entity.toString();
    }          
    
    
}
