package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hramirez
 */
public class CodigoInsumo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String clave;
    private String lote;
    private Date fecha;
    private Integer cantidad;

    public CodigoInsumo() {
    }

    public CodigoInsumo(String clave, String lote, Date fecha, Integer cantidad) {
        this.clave = clave;
        this.lote = lote;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
