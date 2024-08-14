package mx.mc.converter;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author aortiz
 */
@FacesConverter(value = "sepomexConverter")
public class SepomexConverter implements Converter {
    
    private static Map<Object, String> entities = new WeakHashMap<>();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
           for (Map.Entry<Object, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        synchronized (entities) {
            if (!entities.containsKey(object)) {
                String uuid = UUID.randomUUID().toString();
                entities.put(object, uuid);
                return  uuid;
            } else {
                return entities.get(object);
            }
        }
    }
        
}
