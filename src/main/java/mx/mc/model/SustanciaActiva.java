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
public class SustanciaActiva implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Integer idSustanciaActiva;
    private String nombreSustanciaActiva;
    private Integer activa;
    private Date insertFecha;
    private String insertUsuarioId;
    private Date updateFecha;
    private String updateUsuarioId;

    
    public SustanciaActiva() {
    }

    public SustanciaActiva(Integer idSustanciaActiva, String nombreSustanciaActiva, Integer activa, Date insertFecha, String insertUsuarioId, Date updateFecha, String updateUsuarioId) {
        this.idSustanciaActiva = idSustanciaActiva;
        this.nombreSustanciaActiva = nombreSustanciaActiva;
        this.activa = activa;
        this.insertFecha = insertFecha;
        this.insertUsuarioId = insertUsuarioId;
        this.updateFecha = updateFecha;
        this.updateUsuarioId = updateUsuarioId;
    }

    @Override
    public String toString() {
        return "SustanciaActiva{" + "idSustanciaActiva=" + idSustanciaActiva + ", nombreSustanciaActiva=" + nombreSustanciaActiva + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertUsuarioId=" + insertUsuarioId + ", updateFecha=" + updateFecha + ", updateUsuarioId=" + updateUsuarioId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idSustanciaActiva);
        hash = 97 * hash + Objects.hashCode(this.nombreSustanciaActiva);
        hash = 97 * hash + Objects.hashCode(this.activa);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertUsuarioId);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateUsuarioId);
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
        final SustanciaActiva other = (SustanciaActiva) obj;
        if (!Objects.equals(this.nombreSustanciaActiva, other.nombreSustanciaActiva)) {
            return false;
        }
        if (!Objects.equals(this.insertUsuarioId, other.insertUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.updateUsuarioId, other.updateUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.idSustanciaActiva, other.idSustanciaActiva)) {
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

    public Integer getIdSustanciaActiva() {
        return idSustanciaActiva;
    }

    public void setIdSustanciaActiva(Integer idSustanciaActiva) {
        this.idSustanciaActiva = idSustanciaActiva;
    }

    public String getNombreSustanciaActiva() {
        return nombreSustanciaActiva;
    }

    public void setNombreSustanciaActiva(String nombreSustanciaActiva) {
        this.nombreSustanciaActiva = nombreSustanciaActiva;
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
