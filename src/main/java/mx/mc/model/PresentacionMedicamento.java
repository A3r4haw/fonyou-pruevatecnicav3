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
public class PresentacionMedicamento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idPresentacion;
    private String nombrePresentacion;
    private Integer activa;
    private Date insertFecha;
    private String insertUsuarioId;
    private Date updateFecha;
    private String updateUsuarioId;

    public PresentacionMedicamento() {
    }

    public PresentacionMedicamento(Integer idPresentacion, String nombrePresentacion, Integer activa, Date insertFecha, String insertUsuarioId, Date updateFecha, String updateUsuarioId) {
        this.idPresentacion = idPresentacion;
        this.nombrePresentacion = nombrePresentacion;
        this.activa = activa;
        this.insertFecha = insertFecha;
        this.insertUsuarioId = insertUsuarioId;
        this.updateFecha = updateFecha;
        this.updateUsuarioId = updateUsuarioId;
    }

    @Override
    public String toString() {
        return "PresentacionMedicamento{" + "idPresentacion=" + idPresentacion + ", nombrePresentacion=" + nombrePresentacion + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertUsuarioId=" + insertUsuarioId + ", updateFecha=" + updateFecha + ", updateUsuarioId=" + updateUsuarioId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idPresentacion);
        hash = 29 * hash + Objects.hashCode(this.nombrePresentacion);
        hash = 29 * hash + Objects.hashCode(this.activa);
        hash = 29 * hash + Objects.hashCode(this.insertFecha);
        hash = 29 * hash + Objects.hashCode(this.insertUsuarioId);
        hash = 29 * hash + Objects.hashCode(this.updateFecha);
        hash = 29 * hash + Objects.hashCode(this.updateUsuarioId);
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
        final PresentacionMedicamento other = (PresentacionMedicamento) obj;
        if (!Objects.equals(this.idPresentacion, other.idPresentacion)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertUsuarioId, other.insertUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.updateUsuarioId, other.updateUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.nombrePresentacion, other.nombrePresentacion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    public Integer getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(Integer idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
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
