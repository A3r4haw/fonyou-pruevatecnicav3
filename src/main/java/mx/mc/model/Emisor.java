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
public class Emisor implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idEmisor;
    private String nombreEmisor;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;
    
    public Emisor() {
        
    }

    public Emisor(Integer idEmisor, String nombreEmisor, String insertIdUsuario, Date insertFecha, String updateIdUsuario, Date updateFecha) {
        this.idEmisor = idEmisor;
        this.nombreEmisor = nombreEmisor;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.updateFecha = updateFecha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idEmisor);
        hash = 83 * hash + Objects.hashCode(this.nombreEmisor);
        hash = 83 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 83 * hash + Objects.hashCode(this.insertFecha);
        hash = 83 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 83 * hash + Objects.hashCode(this.updateFecha);
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
        final Emisor other = (Emisor) obj;
        if (!Objects.equals(this.nombreEmisor, other.nombreEmisor)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEmisor, other.idEmisor)) {
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
        return "Emisor{" + "idEmisor=" + idEmisor + ", nombreEmisor=" + nombreEmisor + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + '}';
    }

    public Integer getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Integer idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
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
