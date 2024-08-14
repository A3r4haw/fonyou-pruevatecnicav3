package mx.mc.converter;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


/**
 * 
 * @author gcruz
 *
 */
@FacesConverter(value = "musGenericConverter")
public class MusGenericConverter  implements Converter {

    private static final Map<Object, String> ENTITIES = new WeakHashMap<>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        synchronized (ENTITIES) {
            if (!ENTITIES.containsKey(entity)) {
                String uuid = UUID.randomUUID().toString();
                ENTITIES.put(entity, uuid);
                return uuid;
            } else {
                return ENTITIES.get(entity);
            }
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        for (Entry<Object, String> entry : ENTITIES.entrySet()) {
            if (entry.getValue().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }

}

