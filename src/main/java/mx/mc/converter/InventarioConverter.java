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
@FacesConverter(value = "inventarioConverter")
public class InventarioConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventarioConverter.class);
    private static final WeakHashMap<String, Object> INSUMOS = new WeakHashMap<>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object o) {
        LOGGER.trace("mx.mc.converter.InventarioConverter.getAsString()");

        synchronized (INSUMOS) {
            if (o instanceof Medicamento_Extended) {
                Medicamento_Extended m = (Medicamento_Extended) o;
                if (m.getIdInventario() != null) {
                    if (INSUMOS.containsKey(m.getIdInventario())) {
                        INSUMOS.remove(m.getIdInventario());
                    }
                    INSUMOS.put(m.getIdInventario(), m);
                    return m.getIdInventario();
                }
            }
            return null;
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String idInventario) {
        LOGGER.trace("mx.mc.converter.InventarioConverter.getAsObject()");
        Object o = INSUMOS.get(idInventario);
        return o;
    }

}
