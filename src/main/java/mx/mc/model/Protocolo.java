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
public class Protocolo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idProtocolo;
    private Integer idTipoProtocolo;
    private String claveProtocolo;
    private String descripcionProtocolo;
    private Integer idViaAdministracion;
    private String idDiagnostico;
    private Integer idEstatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private boolean validaProtocolo;

    public Protocolo() {
    }

    public Protocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Protocolo(String claveProtocolo) {
        this.claveProtocolo = claveProtocolo;
    }

    public Protocolo(Integer idProtocolo, Integer idTipoProtocolo, String claveProtocolo, String descripcionProtocolo, Integer idViaAdministracion, String idDiagnostico, Integer idEstatus, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario, boolean validaProtocolo) {
        this.idProtocolo = idProtocolo;
        this.idTipoProtocolo = idTipoProtocolo;
        this.claveProtocolo = claveProtocolo;
        this.descripcionProtocolo = descripcionProtocolo;
        this.idViaAdministracion = idViaAdministracion;
        this.idDiagnostico = idDiagnostico;
        this.idEstatus = idEstatus;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.validaProtocolo = validaProtocolo;
    }

    public Integer getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Integer getIdTipoProtocolo() {
        return idTipoProtocolo;
    }

    public void setIdTipoProtocolo(Integer idTipoProtocolo) {
        this.idTipoProtocolo = idTipoProtocolo;
    }

    public String getClaveProtocolo() {
        return claveProtocolo;
    }

    public void setClaveProtocolo(String claveProtocolo) {
        this.claveProtocolo = claveProtocolo;
    }

    public String getDescripcionProtocolo() {
        return descripcionProtocolo;
    }

    public void setDescripcionProtocolo(String descripcionProtocolo) {
        this.descripcionProtocolo = descripcionProtocolo;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
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

    public boolean isValidaProtocolo() {
        return validaProtocolo;
    }

    public void setValidaProtocolo(boolean validaProtocolo) {
        this.validaProtocolo = validaProtocolo;
    }

    @Override
    public String toString() {
        return "Protocolo{" + "idProtocolo=" + idProtocolo + ", idTipoProtocolo=" + idTipoProtocolo + ", claveProtocolo=" + claveProtocolo + ", descripcionProtocolo=" + descripcionProtocolo + ", idViaAdministracion=" + idViaAdministracion + ", idDiagnostico=" + idDiagnostico + ", idEstatus=" + idEstatus + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", validaProtocolo=" + validaProtocolo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idProtocolo);
        hash = 67 * hash + Objects.hashCode(this.idTipoProtocolo);
        hash = 67 * hash + Objects.hashCode(this.claveProtocolo);
        hash = 67 * hash + Objects.hashCode(this.descripcionProtocolo);
        hash = 67 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 67 * hash + Objects.hashCode(this.idDiagnostico);
        hash = 67 * hash + Objects.hashCode(this.idEstatus);
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 67 * hash + (this.validaProtocolo ? 1 : 0);
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
        final Protocolo other = (Protocolo) obj;
        if (this.validaProtocolo != other.validaProtocolo) {
            return false;
        }
        if (!Objects.equals(this.claveProtocolo, other.claveProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.descripcionProtocolo, other.descripcionProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.idDiagnostico, other.idDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idProtocolo, other.idProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.idTipoProtocolo, other.idTipoProtocolo)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.idEstatus, other.idEstatus)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
