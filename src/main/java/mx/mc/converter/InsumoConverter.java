package mx.mc.converter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author hramirez
 */
@FacesConverter(value = "insumoConverter")
public class InsumoConverter implements Converter {

    private static final Map<Object, String> INSUMOS = new WeakHashMap<>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object insumo) {
        synchronized (INSUMOS) {
            if (!INSUMOS.containsKey(insumo)) {
                String uuid = UUID.randomUUID().toString();
                INSUMOS.put(insumo, uuid);
                return uuid;
            } else {
                return INSUMOS.get(insumo);
            }
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        for (Entry<Object, String> entry : INSUMOS.entrySet()) {
            if (entry.getValue().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
