package mx.gob.imss.siap.client;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import mx.mc.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WsSIAPClient implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(WsSIAPClient.class);
    private static final QName SERVICE_NAME = new QName("http://tempuri.org/", "wsCatalogoEmpleados");
    private static final String CLAVE_MATRICULA = "MATRICULA";
    private static final String CLAVE_ESTATUS = "ESTATUS";
    private static final String ESTATUS_ACTIVO = "1";

    private static String getElementValue(String name, ConsultaCatalogoTrabajadoresResponse.ConsultaCatalogoTrabajadoresResult response) {
        String text = "";
        if (response.getContent() != null && !response.getContent().isEmpty()) {
            ElementNSImpl nsEmpleados = (ElementNSImpl)response.getContent().get(0);
            if (nsEmpleados.getFirstChild() != null) {
                NodeList nodos = nsEmpleados.getFirstChild().getChildNodes();
                for (int i = 0; i < nodos.getLength(); i++) {
                    Node node = nodos.item(i);
                    if (node.getNodeName().equalsIgnoreCase(name)) {
                        text = node.getTextContent();
                        if (text != null)
                            text = text.trim();
                        break;
                    }
                }
            }
        }
        return text;
    }
    
    public static int checkEnrollment(Usuario user, String urlSIAP, String delegacion) {
        int result = 0;
        String matricula = user.getMatriculaPersonal();
        if (matricula != null && !matricula.isEmpty()) {
            try {
                URL wsdlURL = new URL(urlSIAP);
                WsCatalogoEmpleados ss = new WsCatalogoEmpleados(wsdlURL, SERVICE_NAME);
                WsCatalogoEmpleadosSoap port = ss.getWsCatalogoEmpleadosSoap();
                
                ConsultaCatalogoTrabajadoresResponse.ConsultaCatalogoTrabajadoresResult response = port.consultaCatalogoTrabajadores(delegacion, matricula, "", "", "");
                if (response != null) {
                    String matriculaSIAP = getElementValue(CLAVE_MATRICULA, response);
                    String estatusSIAP = getElementValue(CLAVE_ESTATUS, response);
                    if (matriculaSIAP != null && matriculaSIAP.equalsIgnoreCase(matricula)) {
                        if (estatusSIAP != null && estatusSIAP.equalsIgnoreCase(ESTATUS_ACTIVO)) {
                            LOGGER.info("WsSIAPClient.isActive(): Se validó el usuario en SIAP - {}, con matrícula: {}" , user.getNombreUsuario(), matricula);
                        }
                        else {
                            result = 1; // Estatus no es ACTIVO
                            LOGGER.info("WsSIAPClient.isActive(): El estatus del usuario en SIAP no es ACTIVO - {}, matrícula: {}", user.getNombreUsuario(), matricula);
                        }
                    }
                    else {
                        result = 2; // No se encontró la Matrícula
                        LOGGER.info("WsSIAPClient.isActive(): SIAP no se encontró al usuario - {}, con matrícula: {}", user.getNombreUsuario(), matricula);
                    }
                }
                else {
                    //TODO: Validar con el IMSS cual será la respuesta cuando el endpoint no responda
                    LOGGER.info("WsSIAPClient.isActive(): No se obtuvo respuesta del endpoint, no se puede validar en SIAP");
                }
            }
            catch (MalformedURLException ex) {
                result = 3; // URL inválida, error inesperado
                LOGGER.error("WsSIAPClient.isActive(): No se pudo validar en SIAP - ", ex);
            }
        }
        else
            LOGGER.info("WsSIAPClient.isActive(): El Usuario no tiene Matrícula, no se puede validar en SIAP");
        return result;
    }
}