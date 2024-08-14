package mx.mc.converter;

import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.mc.model.EntidadHospitalaria;

/**
 *
 * @author hramirez
 */
@FacesConverter(value = "entidadHospitalariaConverter")
public class EntidadHospitalariaConverter implements Converter {

    @Override
    public String getAsString(FacesContext fc, UIComponent c, Object o) {
        if (o != null) {
            if (o instanceof EntidadHospitalaria) {
                EntidadHospitalaria ent = (EntidadHospitalaria) o;
                if (ent.getIdEntidadHospitalaria() != null) {
                    return ent.getIdEntidadHospitalaria();
                }
            } else if (UUID.fromString((String) o) instanceof UUID) {
                return (String) o;
            }
        }
        return "";
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent c, String v) {
        if (v != null && !v.trim().isEmpty() && UUID.fromString(v) instanceof UUID) {
            EntidadHospitalaria o = new EntidadHospitalaria();
            o.setIdEntidadHospitalaria(v);
            return o;
        }
        return null;
    }

}
