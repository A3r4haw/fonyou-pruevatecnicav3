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
import mx.mc.model.Medicamento;

import org.primefaces.component.autocomplete.AutoComplete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author bbautista
 */
@FacesConverter(value = "medicamentoConverter")
public class MedicamentoConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicamentoConverter.class);
    
     @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value)  {
       
        try{  
            if(value.trim()==null || value.trim().isEmpty()){
               return null;
           }else
           {                   
                if(uic instanceof AutoComplete){                                        
                    Medicamento sa = new Medicamento();
                    sa.setIdMedicamento(value);                    
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
       
        if(entity instanceof Medicamento){
            Medicamento sa = (Medicamento)entity;
            if(sa.getIdMedicamento()!=null)
                return sa.getIdMedicamento();
        }
        return entity.toString();
    }          
    
    
}
