/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author bbautista
 */
public class TipoSurtimiento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String idTipoSurtimiento;
    private String clave;
    private String nombre;
    private String descripcion;

    public String getIdTipoSurtimiento() {
        return idTipoSurtimiento;
    }

    public void setIdTipoSurtimiento(String idTipoSurtimiento) {
        this.idTipoSurtimiento = idTipoSurtimiento;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
