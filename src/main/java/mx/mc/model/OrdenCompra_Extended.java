package mx.mc.model;

import java.io.Serializable;

/**
 * @author AORTIZ
 */
public class OrdenCompra_Extended extends OrdenCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombreProveedor;
    private String nombreEstatus;
    private String nombreEstructura;
    private String folioReabasto;

    public OrdenCompra_Extended() {
        //No code needed in constructor
    }
 
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getFolioReabasto() {
        return folioReabasto;
    }

    public void setFolioReabasto(String folioReabasto) {
        this.folioReabasto = folioReabasto;
    }
    
}
