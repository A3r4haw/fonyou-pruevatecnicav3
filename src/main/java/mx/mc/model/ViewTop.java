package mx.mc.model;

import java.io.Serializable;

/**
 * @author aortiz
 */
public class ViewTop implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clave;
    private String nombre;
    private Integer numero;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

}
