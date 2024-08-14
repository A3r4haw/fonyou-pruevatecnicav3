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
public class TipoInteraccion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idTipoInteraccion;
    private String tipoInteraccion;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;
    
    public TipoInteraccion() {
        
    }

    public TipoInteraccion(Integer idTipoInteraccion, String tipoInteraccion, String insertIdUsuario, Date insertFecha, String updateIdUsuario, Date updateFecha) {
        this.idTipoInteraccion = idTipoInteraccion;
        this.tipoInteraccion = tipoInteraccion;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.updateFecha = updateFecha;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.idTipoInteraccion);
        hash = 19 * hash + Objects.hashCode(this.tipoInteraccion);
        hash = 19 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 19 * hash + Objects.hashCode(this.insertFecha);
        hash = 19 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 19 * hash + Objects.hashCode(this.updateFecha);
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
        final TipoInteraccion other = (TipoInteraccion) obj;
        if (!Objects.equals(this.tipoInteraccion, other.tipoInteraccion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idTipoInteraccion, other.idTipoInteraccion)) {
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
        return "TipoInteraccion{" + "idTipoInteraccion=" + idTipoInteraccion + ", tipoInteraccion=" + tipoInteraccion + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + '}';
    }

    public Integer getIdTipoInteraccion() {
        return idTipoInteraccion;
    }

    public void setIdTipoInteraccion(Integer idTipoInteraccion) {
        this.idTipoInteraccion = idTipoInteraccion;
    }

    public String getTipoInteraccion() {
        return tipoInteraccion;
    }

    public void setTipoInteraccion(String tipoInteraccion) {
        this.tipoInteraccion = tipoInteraccion;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }
    
    
    
}
