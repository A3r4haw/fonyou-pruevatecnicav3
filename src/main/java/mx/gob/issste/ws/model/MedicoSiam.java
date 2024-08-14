/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class MedicoSiam  implements Serializable{

    private static final long serialVersionUID = 1L;
 
    private Integer id;
    private String clave;
    private String nombre;
    private String especialidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "MedicoSiam{" + "id=" + id + ", clave=" + clave + ", nombre=" + nombre + ", especialidad=" + especialidad + '}';
    }
    
    
    
}
