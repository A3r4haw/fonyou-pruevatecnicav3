package mx.mc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import mx.mc.init.Constantes;
import mx.mc.model.ClaveProveedorBarras;
import mx.mc.service.ClaveProveedorBarrasService;

/**
 *
 * @author hramirez
 */
public class Mensaje {
    
    private static ClaveProveedorBarrasService skuService;
    
    public static void showMessage(String severity, String message, String detail) {
        FacesMessage fm = new FacesMessage();
        switch (severity) {
            case Constantes.MENSAJE_ERROR:
                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                break;
            case Constantes.MENSAJE_FATAL:
                fm.setSeverity(FacesMessage.SEVERITY_FATAL);
                break;
            case Constantes.MENSAJE_INFO:
                fm.setSeverity(FacesMessage.SEVERITY_INFO);
                break;
            case Constantes.MENSAJE_WARN:
                fm.setSeverity(FacesMessage.SEVERITY_WARN);
                break;
            default:
                fm.setSeverity(null); 
                break;
        }
        fm.setSummary(message);
        if (detail != null)
            fm.setDetail(detail);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

   /* public static ClaveProveedorBarras buscaClaveSKU(String sku ) throws Exception{
        ClaveProveedorBarras clave = null;
        try{
            clave = skuService.obtenerClave(sku);        
        }catch(Exception ex){
            throw new Exception("Error al obener clave SKU " + ex.getMessage());
        }        
        return clave;
    }
    */
    public static String generaLoteSKU(){
        return "";
    }
    
    public static Date generaCaducidadSKU(String sku ){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date caducidad = new Date();
        try{
            caducidad = df.parse("31-12-9999");        
        }catch(ParseException ex){}        
        return caducidad;
    }

    public static void showMessage(String error, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
