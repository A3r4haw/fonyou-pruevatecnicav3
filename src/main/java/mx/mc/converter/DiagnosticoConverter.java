package mx.mc.converter;

import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.mc.model.Diagnostico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hramirez
 */
@FacesConverter(value = "diagnosticoConverter")
public class DiagnosticoConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosticoConverter.class);

    @Override
    public String getAsString(FacesContext fc, UIComponent c, Object o) {
        String temp = (o == null) ? "null" : o.toString();
        LOGGER.debug("mx.mc.converter.DiagnosticoConverter.getAsString(), objeto: {}" , temp);
        if (o != null) {
            if (o instanceof Diagnostico) {
                Diagnostico medicamento = (Diagnostico) o;
                if (medicamento.getIdDiagnostico() != null) {
                    return medicamento.getIdDiagnostico();
                }
            } else if (UUID.fromString((String) o) instanceof UUID) {
                return (String) o;

            }
        }
        return "";
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent c, String v) {
        LOGGER.debug("mx.mc.converter.DiagnosticoConverter.getAsObject() {}", v);

        if (v != null && !v.trim().isEmpty() && UUID.fromString(v) instanceof UUID) {
            Diagnostico o = new Diagnostico();
            o.setIdDiagnostico(v);
            return o;
        }
        return null;
    }

}
