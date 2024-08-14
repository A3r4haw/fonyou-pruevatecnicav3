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
public class ViaAdministracion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idViaAdministracion;
    private String nombreViaAdministracion;
    private boolean activa;
    private Date insertFecha;
    private String insertUsuarioId;
    private Date updateFecha;
    private String updateUsuarioId;

    public ViaAdministracion() {
    }

    public ViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public ViaAdministracion(Integer idViaAdministracion, String nombreViaAdministracion,  boolean activa, Date insertFecha, String insertUsuarioId, Date updateFecha, String updateUsuarioId) {
        this.idViaAdministracion = idViaAdministracion;
        this.nombreViaAdministracion = nombreViaAdministracion;
        this.activa = activa;
        this.insertFecha = insertFecha;
        this.insertUsuarioId = insertUsuarioId;
        this.updateFecha = updateFecha;
        this.updateUsuarioId = updateUsuarioId;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getNombreViaAdministracion() {
        return nombreViaAdministracion;
    }

    public void setNombreViaAdministracion(String nombreViaAdministracion) {
        this.nombreViaAdministracion = nombreViaAdministracion;
    }

    public boolean getActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
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

    @Override
    public String toString() {
        return "ViaAdministracion{" + "idViaAdministracion=" + idViaAdministracion + ", nombreViaAdministracion=" + nombreViaAdministracion + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertUsuarioId=" + insertUsuarioId + ", updateFecha=" + updateFecha + ", updateUsuarioId=" + updateUsuarioId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 53 * hash + Objects.hashCode(this.nombreViaAdministracion);
        hash = 53 * hash + Objects.hashCode(this.activa);
        hash = 53 * hash + Objects.hashCode(this.insertFecha);
        hash = 53 * hash + Objects.hashCode(this.insertUsuarioId);
        hash = 53 * hash + Objects.hashCode(this.updateFecha);
        hash = 53 * hash + Objects.hashCode(this.updateUsuarioId);
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
        final ViaAdministracion other = (ViaAdministracion) obj;
        if (!Objects.equals(this.nombreViaAdministracion, other.nombreViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.insertUsuarioId, other.insertUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.updateUsuarioId, other.updateUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
