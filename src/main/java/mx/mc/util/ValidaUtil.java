package mx.mc.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import mx.mc.init.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cervanets
 */
public class ValidaUtil implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidaUtil.class);

    public static boolean correoValido(String correo){
        LOGGER.trace("mx.mc.commons.SolucionUtils.validaCorreo()");
        boolean res = false;
        if (correo!= null && !correo.trim().isEmpty()) {
            Matcher mat = Constantes.regexMail.matcher(correo);
            if (mat.find()) {
                res = true;
            }
        }
        return res;
    }
    
}
