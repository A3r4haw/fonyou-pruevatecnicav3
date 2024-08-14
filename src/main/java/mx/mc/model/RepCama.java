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
public class RepCama implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idEstatusCama;
    private String cama;
    private String ubicacion;
    private String clave;
    private String paciente;
    private String idEstructura;
    private String nombre;

    public Integer getIdEstatusCama() {
        return idEstatusCama;
    }

    public void setIdEstatusCama(Integer idEstatusCama) {
        this.idEstatusCama = idEstatusCama;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbiacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
