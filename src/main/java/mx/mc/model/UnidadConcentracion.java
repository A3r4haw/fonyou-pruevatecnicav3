/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;
import java.util.Objects;
import java.io.Serializable;

/**
 *
 * @author bbautista
 */
public class UnidadConcentracion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idUnidadConcentracion;
    private String nombreUnidadConcentracion;
    private Date insertFecha;
    private String insertUsuarioId;
    private Date updateFecha;
    private String updateUsuarioId;

    public UnidadConcentracion() {
    }

    public UnidadConcentracion(Integer idUnidadConcentracion) {
        this.idUnidadConcentracion = idUnidadConcentracion;
    }

    public UnidadConcentracion(Integer idUnidadConcentracion, String nombreUnidadConcentracion, Date insertFecha, String insertUsuarioId, Date updateFecha, String updateUsuarioId) {
        this.idUnidadConcentracion = idUnidadConcentracion;
        this.nombreUnidadConcentracion = nombreUnidadConcentracion;
        this.insertFecha = insertFecha;
        this.insertUsuarioId = insertUsuarioId;
        this.updateFecha = updateFecha;
        this.updateUsuarioId = updateUsuarioId;
    }

    @Override
    public String toString() {
        return "UnidadConcentracion{" + "idUnidadConcentracion=" + idUnidadConcentracion + ", nombreUnidadConcentracion=" + nombreUnidadConcentracion + ", insertFecha=" + insertFecha + ", insertUsuarioId=" + insertUsuarioId + ", updateFecha=" + updateFecha + ", updateUsuarioId=" + updateUsuarioId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.idUnidadConcentracion);
        hash = 17 * hash + Objects.hashCode(this.nombreUnidadConcentracion);
        hash = 17 * hash + Objects.hashCode(this.insertFecha);
        hash = 17 * hash + Objects.hashCode(this.insertUsuarioId);
        hash = 17 * hash + Objects.hashCode(this.updateFecha);
        hash = 17 * hash + Objects.hashCode(this.updateUsuarioId);
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
        final UnidadConcentracion other = (UnidadConcentracion) obj;
        if (!Objects.equals(this.idUnidadConcentracion, other.idUnidadConcentracion)) {
            return false;
        }
        if (!Objects.equals(this.insertUsuarioId, other.insertUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.updateUsuarioId, other.updateUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.nombreUnidadConcentracion, other.nombreUnidadConcentracion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    public Integer getIdUnidadConcentracion() {
        return idUnidadConcentracion;
    }

    public void setIdUnidadConcentracion(Integer idUnidadConcentracion) {
        this.idUnidadConcentracion = idUnidadConcentracion;
    }

    public String getNombreUnidadConcentracion() {
        return nombreUnidadConcentracion;
    }

    public void setNombreUnidadConcentracion(String nombreUnidadConcentracion) {
        this.nombreUnidadConcentracion = nombreUnidadConcentracion;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertUsuarioId() {
        return insertUsuarioId;
    }

    public void setInsertUsuarioId(String insertUsuarioId) {
        this.insertUsuarioId = insertUsuarioId;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateUsuarioId() {
        return updateUsuarioId;
    }

    public void setUpdateUsuarioId(String updateUsuarioId) {
        this.updateUsuarioId = updateUsuarioId;
    }

}
