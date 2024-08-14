/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author olozada
 */
public class TipoBloqueoMotivos implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idTipoBloqueoMotivos;
    private String tipoBloqueoMotivos;
    private Integer activo;

    public TipoBloqueoMotivos() {
    }

    public TipoBloqueoMotivos(Integer idTipoBloqueoMotivos, String tipoBloqueoMotivos, Integer activo) {
        this.idTipoBloqueoMotivos = idTipoBloqueoMotivos;
        this.tipoBloqueoMotivos = tipoBloqueoMotivos;
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idTipoBloqueoMotivos);
        hash = 53 * hash + Objects.hashCode(this.tipoBloqueoMotivos);
        hash = 53 * hash + Objects.hashCode(this.activo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoBloqueoMotivos other = (TipoBloqueoMotivos) obj;
        if (!Objects.equals(this.idTipoBloqueoMotivos, other.idTipoBloqueoMotivos)) {
            return false;
        }
        if (!Objects.equals(this.tipoBloqueoMotivos, other.tipoBloqueoMotivos)) {
            return false;
        }
        return Objects.equals(this.activo, other.activo);
    }

    @Override
    public String toString() {
        return "TipoBloqueoMotivos{" + "idTipoBloqueoMotivos=" + idTipoBloqueoMotivos + ", tipoBloqueoMotivos=" + tipoBloqueoMotivos + ", activo=" + activo + '}';
    }

    public Integer getIdTipoBloqueoMotivos() {
        return idTipoBloqueoMotivos;
    }

    public void setIdTipoBloqueoMotivos(Integer idTipoBloqueoMotivos) {
        this.idTipoBloqueoMotivos = idTipoBloqueoMotivos;
    }

   

    public String getTipoBloqueoMotivos() {
        return tipoBloqueoMotivos;
    }

    public void setTipoBloqueoMotivos(String tipoBloqueoMotivos) {
        this.tipoBloqueoMotivos = tipoBloqueoMotivos;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    
}
