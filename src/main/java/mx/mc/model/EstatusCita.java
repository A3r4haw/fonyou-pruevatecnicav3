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
 * @author mcalderon
 */
public class EstatusCita implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idEstatusCita;
    private Integer estatusCita;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public EstatusCita() {
    }

    public EstatusCita(String idEstatusCita, Integer estatusCita, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idEstatusCita = idEstatusCita;
        this.estatusCita = estatusCita;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idEstatusCita);
        hash = 97 * hash + Objects.hashCode(this.estatusCita);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final EstatusCita other = (EstatusCita) obj;
        if (!Objects.equals(this.idEstatusCita, other.idEstatusCita)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.estatusCita, other.estatusCita)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    
    
    public String getIdEstatusCita() {
        return idEstatusCita;
    }

    public void setIdEstatusCita(String idEstatusCita) {
        this.idEstatusCita = idEstatusCita;
    }

    public Integer getEstatusCita() {
        return estatusCita;
    }

    public void setEstatusCita(Integer estatusCita) {
        this.estatusCita = estatusCita;
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
