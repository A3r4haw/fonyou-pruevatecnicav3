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
public class MotivosRechazo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idMotivoRechazo;
    private String motivo;
    private Integer activo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public MotivosRechazo() {

    }

    public MotivosRechazo(Integer idMotivoRechazo) {
        this.idMotivoRechazo = idMotivoRechazo;
    }

    public MotivosRechazo(Integer idMotivoRechazo, String motivo, Integer activo, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idMotivoRechazo = idMotivoRechazo;
        this.motivo = motivo;
        this.activo = activo;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public String toString() {
        return "MotivosRechazo{" + "idMotivoRechazo=" + idMotivoRechazo + ", motivo=" + motivo + ", activo=" + activo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.idMotivoRechazo);
        hash = 61 * hash + Objects.hashCode(this.motivo);
        hash = 61 * hash + Objects.hashCode(this.activo);
        hash = 61 * hash + Objects.hashCode(this.insertFecha);
        hash = 61 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 61 * hash + Objects.hashCode(this.updateFecha);
        hash = 61 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final MotivosRechazo other = (MotivosRechazo) obj;
        if (!Objects.equals(this.motivo, other.motivo)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoRechazo, other.idMotivoRechazo)) {
            return false;
        }
        if (!Objects.equals(this.activo, other.activo)) {
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

    public Integer getIdMotivoRechazo() {
        return idMotivoRechazo;
    }

    public void setIdMotivoRechazo(Integer idMotivoRechazo) {
        this.idMotivoRechazo = idMotivoRechazo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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
