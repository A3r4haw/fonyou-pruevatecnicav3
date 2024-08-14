/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.avg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Ulai
 */
public class DoveTrack implements Serializable{

    private static final long serialVersionUID = 1L;
    private String folio;
    private Date fechaSolicitada;
    private String servicio;
    private String cama;
    private String paciente;
    private String nombre;
    private String clave;
    private String medicamento;
    private Integer cantidadSolicitada;
    private Date fechaSurtida;
    private String usuario;
    private Integer cantidadSurtida;

    public DoveTrack() {
    //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.folio);
        hash = 97 * hash + Objects.hashCode(this.fechaSolicitada);
        hash = 97 * hash + Objects.hashCode(this.servicio);
        hash = 97 * hash + Objects.hashCode(this.cama);
        hash = 97 * hash + Objects.hashCode(this.paciente);
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.clave);
        hash = 97 * hash + Objects.hashCode(this.medicamento);
        hash = 97 * hash + Objects.hashCode(this.cantidadSolicitada);
        hash = 97 * hash + Objects.hashCode(this.fechaSurtida);
        hash = 97 * hash + Objects.hashCode(this.usuario);
        hash = 97 * hash + Objects.hashCode(this.cantidadSurtida);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DoveTrack other = (DoveTrack) obj;
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.servicio, other.servicio)) {
            return false;
        }
        if (!Objects.equals(this.cama, other.cama)) {
            return false;
        }
        if (!Objects.equals(this.paciente, other.paciente)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.medicamento, other.medicamento)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaSolicitada, other.fechaSolicitada)) {
            return false;
        }
        if (!Objects.equals(this.cantidadSolicitada, other.cantidadSolicitada)) {
            return false;
        }
        if (!Objects.equals(this.fechaSurtida, other.fechaSurtida)) {
            return false;
        }
        else if (!Objects.equals(this.cantidadSurtida, other.cantidadSurtida)) {
            return false;
        }        
        return true;
    }
    
    

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(Date fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Date getFechaSurtida() {
        return fechaSurtida;
    }

    public void setFechaSurtida(Date fechaSurtida) {
        this.fechaSurtida = fechaSurtida;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(Integer cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    
    
    
}
