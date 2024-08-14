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
public class UsuarioImpresora implements Serializable {    
    private static final long serialVersionUID = 1L;
    
    private String idUsuario;
    private String idImpresora;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;
    
    public UsuarioImpresora() {
        
    }

    public UsuarioImpresora(String idUsuario, String idImpresora, String insertIdUsuario, Date insertFecha, String updateIdUsuario, Date updateFecha) {
        this.idUsuario = idUsuario;
        this.idImpresora = idImpresora;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.updateFecha = updateFecha;
    }
    
     public UsuarioImpresora(String idUsuario, String idImpresora) {
        this.idUsuario = idUsuario;
        this.idImpresora = idImpresora;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdImpresora() {
        return idImpresora;
    }

    public void setIdImpresora(String idImpresora) {
        this.idImpresora = idImpresora;
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

    @Override
    public String toString() {
        return "usuarioImpresora{" + "idUsuario=" + idUsuario + ", idImpresora=" + idImpresora + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.idUsuario);
        hash = 13 * hash + Objects.hashCode(this.idImpresora);
        hash = 13 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 13 * hash + Objects.hashCode(this.insertFecha);
        hash = 13 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 13 * hash + Objects.hashCode(this.updateFecha);
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
        final UsuarioImpresora other = (UsuarioImpresora) obj;
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idImpresora, other.idImpresora)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }   
}