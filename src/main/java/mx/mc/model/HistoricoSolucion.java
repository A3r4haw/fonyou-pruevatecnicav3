/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class HistoricoSolucion {
    
    private String fase;
    private Date fecha;
    private String folio;
    private String estatus;
    private String usuario;
    private String nota;
    private String servicio;

    public HistoricoSolucion() {
        
    }

    public HistoricoSolucion(String fase, Date fecha, String folio, String estatus, String usuario, String nota, String servicio) {
        this.fase = fase;
        this.fecha = fecha;
        this.folio = folio;
        this.estatus = estatus;
        this.usuario = usuario;
        this.nota = nota;
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.fase);
        hash = 53 * hash + Objects.hashCode(this.fecha);
        hash = 53 * hash + Objects.hashCode(this.folio);
        hash = 53 * hash + Objects.hashCode(this.estatus);
        hash = 53 * hash + Objects.hashCode(this.usuario);
        hash = 53 * hash + Objects.hashCode(this.nota);
        hash = 53 * hash + Objects.hashCode(this.servicio);
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
        final HistoricoSolucion other = (HistoricoSolucion) obj;
        if (!Objects.equals(this.fase, other.fase)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.nota, other.nota)) {
            return false;
        }
        if (!Objects.equals(this.servicio, other.servicio)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HistoricoSolucion{" + "fase=" + fase + ", fecha=" + fecha + ", folio=" + folio + ", estatus=" + estatus + ", usuario=" + usuario + ", nota=" + nota + ", servicio=" + servicio + '}';
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }         
    
}
