package mx.mc.init;

import java.util.Collections;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.util.Constants;

/**
 * Clase utilizada para anexar informacion al request ajax
 * @author
 */
public final class PrimeFacesRequestContext {

  
    public static void addCallBackParam(boolean exito) {
        PrimeFaces.current().ajax().addCallbackParam("exito", exito);
    }
    
    /**
     * Agrega la cookie al external context para indicar a monitorDownload que ya terminó la respuesta
     */
    public static void addDownloadCookie(){
        ExternalContext externalContext = FacesContext.getCurrentInstance()
                            .getExternalContext();
        externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());
    }

}