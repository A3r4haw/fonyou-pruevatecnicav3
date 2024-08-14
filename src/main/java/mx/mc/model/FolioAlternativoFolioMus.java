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
public class FolioAlternativoFolioMus implements Serializable {
    
    private static final long serialVersionUID = 1L; 
    
    private String idFolioAlternativo;
    private String folioAlternativo;
    private String folioMUS;
    private String estatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public FolioAlternativoFolioMus() {
        
    }
    
    public FolioAlternativoFolioMus(String folioAlternativo) {
        this.folioAlternativo = folioAlternativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.idFolioAlternativo);
        hash = 43 * hash + Objects.hashCode(this.folioAlternativo);
        hash = 43 * hash + Objects.hashCode(this.folioMUS);
        hash = 43 * hash + Objects.hashCode(this.estatus);
        hash = 43 * hash + Objects.hashCode(this.insertFecha);
        hash = 43 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 43 * hash + Objects.hashCode(this.updateFecha);
        hash = 43 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final FolioAlternativoFolioMus other = (FolioAlternativoFolioMus) obj;
        if (!Objects.equals(this.idFolioAlternativo, other.idFolioAlternativo)) {
            return false;
        }
        if (!Objects.equals(this.folioAlternativo, other.folioAlternativo)) {
            return false;
        }
        if (!Objects.equals(this.folioMUS, other.folioMUS)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
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

    @Override
    public String toString() {
        return "FolioAlternativoFolioMus{" + "idFolioAlternativo=" + idFolioAlternativo + ", folioAlternativo=" + folioAlternativo + ", folioMUS=" + folioMUS + ", estatus=" + estatus + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }   

    public String getIdFolioAlternativo() {
        return idFolioAlternativo;
    }

    public void setIdFolioAlternativo(String idFolioAlternativo) {
        this.idFolioAlternativo = idFolioAlternativo;
    }

    public String getFolioAlternativo() {
        return folioAlternativo;
    }

    public void setFolioAlternativo(String folioAlternativo) {
        this.folioAlternativo = folioAlternativo;
    }

    public String getFolioMUS() {
        return folioMUS;
    }

    public void setFolioMUS(String folioMUS) {
        this.folioMUS = folioMUS;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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
