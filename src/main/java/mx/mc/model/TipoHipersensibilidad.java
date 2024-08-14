/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class TipoHipersensibilidad  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idTipoHipersensibilidad;
    private String nombreTipoHiper;
    private Integer activa;
    private Date insertFecha;
    private String insertIdUsuario;    
    private Date updateFecha;
    private String updateIdUsuario;
    
    public TipoHipersensibilidad() {
        
    }

    public TipoHipersensibilidad(Integer idTipoHipersensibilidad, String nombreTipoHiper, Integer activa, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idTipoHipersensibilidad = idTipoHipersensibilidad;
        this.nombreTipoHiper = nombreTipoHiper;
        this.activa = activa;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idTipoHipersensibilidad);
        hash = 53 * hash + Objects.hashCode(this.nombreTipoHiper);
        hash = 53 * hash + Objects.hashCode(this.activa);
        hash = 53 * hash + Objects.hashCode(this.insertFecha);
        hash = 53 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.updateFecha);
        hash = 53 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final TipoHipersensibilidad other = (TipoHipersensibilidad) obj;
        if (!Objects.equals(this.nombreTipoHiper, other.nombreTipoHiper)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idTipoHipersensibilidad, other.idTipoHipersensibilidad)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoHipersensibilidad{" + "idTipoHipersensibilidad=" + idTipoHipersensibilidad + ", nombreTipoHiper=" + nombreTipoHiper + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public Integer getIdTipoHipersensibilidad() {
        return idTipoHipersensibilidad;
    }

    public void setIdTipoHipersensibilidad(Integer idTipoHipersensibilidad) {
        this.idTipoHipersensibilidad = idTipoHipersensibilidad;
    }

    public String getNombreTipoHiper() {
        return nombreTipoHiper;
    }

    public void setNombreTipoHiper(String nombreTipoHiper) {
        this.nombreTipoHiper = nombreTipoHiper;
    }

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }        
    
}
