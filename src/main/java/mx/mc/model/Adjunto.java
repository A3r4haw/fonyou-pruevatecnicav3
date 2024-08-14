/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class Adjunto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idAdjunto;
    private String nombreAdjunto;
    private byte[] adjunto;
    private Integer eliminado;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;    

    public Adjunto() {
        
    }
    
    public Adjunto(Integer idAdjunto, String nombreAdjunto, byte[] adjunto, Integer eliminado, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idAdjunto = idAdjunto;
        this.nombreAdjunto = nombreAdjunto;
        this.adjunto = adjunto;
        this.eliminado = eliminado;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.idAdjunto);
        hash = 71 * hash + Objects.hashCode(this.nombreAdjunto);
        hash = 71 * hash + Arrays.hashCode(this.adjunto);
        hash = 71 * hash + Objects.hashCode(this.eliminado);
        hash = 71 * hash + Objects.hashCode(this.insertFecha);
        hash = 71 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 71 * hash + Objects.hashCode(this.updateFecha);
        hash = 71 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Adjunto other = (Adjunto) obj;
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idAdjunto, other.idAdjunto)) {
            return false;
        }
        if (!Objects.equals(this.eliminado, other.eliminado)) {
            return false;
        }
        if (!Objects.equals(this.nombreAdjunto, other.nombreAdjunto)) {
            return false;
        }
        if (!Arrays.equals(this.adjunto, other.adjunto)) {
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
        return "Adjunto{" + "idAdjunto=" + idAdjunto + ", nombreAdjunto=" + nombreAdjunto + ", adjunto=" + adjunto + ", eliminado=" + eliminado + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public Integer getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Integer idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public byte[] getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(byte[] adjunto) {
        this.adjunto = adjunto;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
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
