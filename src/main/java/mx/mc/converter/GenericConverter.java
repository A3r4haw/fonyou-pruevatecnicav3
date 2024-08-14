package mx.mc.converter;

import java.util.Map;
import java.util.WeakHashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import mx.mc.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hrcervantes
 */
@FacesConverter(value = "genericConverter")
public class GenericConverter implements Converter {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericConverter.class);
    private static final Map<String, Object> entitiesConverter = new WeakHashMap<>();
    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        if (obj == null || obj.equals("")){
            return "";
        } else {
            synchronized (entitiesConverter) {
                Usuario objeto = (Usuario) obj;
                if (!entitiesConverter.containsKey(objeto.getIdUsuario())) {
                    entitiesConverter.put(objeto.getIdUsuario(), obj);
                    return objeto.getIdUsuario();
                } else {
                    return entitiesConverter.get(objeto.getIdUsuario()).toString();
                }
            }
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String valor) {
        try {
            valor = valor.replace("Almacen{almacenId=", "").replace("}", "");
            return entitiesConverter.get(valor);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return null;
    }
}

