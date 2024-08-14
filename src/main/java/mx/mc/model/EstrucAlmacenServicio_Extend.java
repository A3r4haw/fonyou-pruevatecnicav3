package mx.mc.model;

import java.io.Serializable;

public class EstrucAlmacenServicio_Extend extends EstructuraAlmacenServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private String almacen;
    private String servicio;
    private String tipo;
    private String estado;
    private Integer total;
    
    public EstrucAlmacenServicio_Extend() {
    	//No code needed in constructor
    }

    public String getNombre() {
            return nombre;
    }

    public void setNombre(String nombre) {
            this.nombre = nombre;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    
    
    
}
