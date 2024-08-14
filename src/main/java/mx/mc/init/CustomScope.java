package mx.mc.init;

import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 *
 * @author hramirez
 */
public class CustomScope implements Serializable, Scope {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomScope.class);

    @Override
    public Object get(String name, @SuppressWarnings("rawtypes") ObjectFactory objectFactory) {
        LOGGER.trace("get Object Scope");
        try{
            Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();

            if (viewMap.containsKey(name)) {
                return viewMap.get(name);
            } else {
                Object object = objectFactory.getObject();
                viewMap.put(name, object);
                return object;
            }
        }catch(NullPointerException ex){
            LOGGER.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public Object remove(String name) {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
    }

    @Override
    public String getConversationId() {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        //Not supported
    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

}
