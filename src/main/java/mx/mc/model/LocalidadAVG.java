/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author gcruz
 */
public class LocalidadAVG implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idLocalidadAVG;
    @Size(min = 5, max = 9, message = "Localidad: Debe introducir un valor no mayor de 9 caracteres")
    @NotNull(message = "Localidad: Debe introducir un valor")
    private String localidad;
    @NotNull(message = "estatus Debe introducir un valor")
    private Integer estatus;
    private Date insertFecha;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public LocalidadAVG() {
        localidad = "";
        estatus = 0;
    }

    public LocalidadAVG(String idLocalidadAVG, String localidad, Integer estatus, Date insertFecha, Date updateFecha, String updateIdUsuario) {
        this.idLocalidadAVG = idLocalidadAVG;
        this.localidad = localidad;
        this.estatus = estatus;
        this.insertFecha = insertFecha;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.idLocalidadAVG);
        hash = 41 * hash + Objects.hashCode(this.localidad);
        hash = 41 * hash + Objects.hashCode(this.estatus);
        hash = 41 * hash + Objects.hashCode(this.insertFecha);
        hash = 41 * hash + Objects.hashCode(this.updateFecha);
        hash = 41 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final LocalidadAVG other = (LocalidadAVG) obj;
        if (!Objects.equals(this.idLocalidadAVG, other.idLocalidadAVG)) {
            return false;
        }
        if (!Objects.equals(this.localidad, other.localidad)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    @Override
    public String toString() {
        return "LocalidadAVG{" + "idLocalidadAVG=" + idLocalidadAVG + ", localidad=" + localidad + ", estatus=" + estatus + ", insertFecha=" + insertFecha + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public String getIdLocalidadAVG() {
        return idLocalidadAVG;
    }

    public void setIdLocalidadAVG(String idLocalidadAVG) {
        this.idLocalidadAVG = idLocalidadAVG;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
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
