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
public class TipoEdadPaciente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idTipoEdadPaciente;
    private String descripcion;
    private Integer rangoInf;
    private Integer rangoSup;
    private Integer idEstatus;
    private String insertIdUsuario;
    private Date insertFecha;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public TipoEdadPaciente() {
        
    }

    public TipoEdadPaciente(Integer idTipoEdadPaciente, String descripcion, Integer rangoInf, Integer rangoSup, Integer idEstatus, String insertIdUsuario, Date insertFecha, Date updateFecha, String updateIdUsuario) {
        this.idTipoEdadPaciente = idTipoEdadPaciente;
        this.descripcion = descripcion;
        this.rangoInf = rangoInf;
        this.rangoSup = rangoSup;
        this.idEstatus = idEstatus;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idTipoEdadPaciente);
        hash = 53 * hash + Objects.hashCode(this.descripcion);
        hash = 53 * hash + Objects.hashCode(this.rangoInf);
        hash = 53 * hash + Objects.hashCode(this.rangoSup);
        hash = 53 * hash + Objects.hashCode(this.idEstatus);
        hash = 53 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 53 * hash + Objects.hashCode(this.insertFecha);
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
        final TipoEdadPaciente other = (TipoEdadPaciente) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idTipoEdadPaciente, other.idTipoEdadPaciente)) {
            return false;
        }
        if (!Objects.equals(this.rangoInf, other.rangoInf)) {
            return false;
        }
        if (!Objects.equals(this.rangoSup, other.rangoSup)) {
            return false;
        }
        if (!Objects.equals(this.idEstatus, other.idEstatus)) {
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
        return "TipoEdadPaciente{" + "idTipoEdadPaciente=" + idTipoEdadPaciente + ", descripcion=" + descripcion + ", rangoInf=" + rangoInf + ", rangoSup=" + rangoSup + ", idEstatus=" + idEstatus + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public Integer getIdTipoEdadPaciente() {
        return idTipoEdadPaciente;
    }

    public void setIdTipoEdadPaciente(Integer idTipoEdadPaciente) {
        this.idTipoEdadPaciente = idTipoEdadPaciente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getRangoInf() {
        return rangoInf;
    }

    public void setRangoInf(Integer rangoInf) {
        this.rangoInf = rangoInf;
    }

    public Integer getRangoSup() {
        return rangoSup;
    }

    public void setRangoSup(Integer rangoSup) {
        this.rangoSup = rangoSup;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
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
