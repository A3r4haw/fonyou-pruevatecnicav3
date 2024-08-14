/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.Converter;
/**
 *
 * @author bbautista
 */
@FacesConverter("convertidorFecha")
public class ConverterFecha implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                return new Date(sdf.parse(value).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ConverterFecha.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Date) {
            Calendar fechaCompara = Calendar.getInstance();
            fechaCompara.setTime((Date) value);

            if (fechaCompara.get(Calendar.DATE) == 31 && fechaCompara.get(Calendar.MONTH) == 11 && fechaCompara.get(Calendar.YEAR) == 3000) {
                return null;
            }
            Date fecha = (Date) value;
            return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        } else {
            return null;
        }
    }
}