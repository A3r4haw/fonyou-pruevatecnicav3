package mx.mc.converter;

import java.util.WeakHashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.mc.model.Medicamento_Extended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hcervantes
 */
@FacesConverter(value = "insumoExtConverter")
public class InsumoExtConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsumoExtConverter.class);
    private static final WeakHashMap<String, Object> INSUMOS = new WeakHashMap<>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        LOGGER.trace("mx.mc.converter.InsumoExtConverter.getAsString()");
        
        synchronized (INSUMOS) {
            if (o instanceof Medicamento_Extended) {
                Medicamento_Extended m = (Medicamento_Extended) o;
                if (m.getClaveInstitucional() != null) {
                    if (INSUMOS.containsKey(m.getClaveInstitucional())) {
                        INSUMOS.remove(m.getClaveInstitucional());
                    }
                    INSUMOS.put(m.getClaveInstitucional(), o);
                    return m.getClaveInstitucional();
                }
            }
            return null;
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String claveInstitucional) {
        LOGGER.trace("mx.mc.converter.InsumoExtConverter.getAsObject()");
        return INSUMOS.get(claveInstitucional);
    }

}
