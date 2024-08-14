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
 * @author bbautista
 */
public class Transferencia implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String idTransferencia;
    private String idEstructura;
    private String idEstructuraDestino;
    private String folio;
    private String observaciones;
    private Integer idEstatusTransferencia;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    private String origen;
    private String destino;
    private String usuario;
    private String estatus;
    
    
    
    public Transferencia() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idTransferencia);
        hash = 37 * hash + Objects.hashCode(this.idEstructura);
        hash = 37 * hash + Objects.hashCode(this.idEstructuraDestino);
        hash = 37 * hash + Objects.hashCode(this.folio);
        hash = 37 * hash + Objects.hashCode(this.observaciones);
        hash = 37 * hash + Objects.hashCode(this.idEstatusTransferencia);
        hash = 37 * hash + Objects.hashCode(this.insertFecha);
        hash = 37 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 37 * hash + Objects.hashCode(this.updateFecha);
        hash = 37 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 37 * hash + Objects.hashCode(this.origen);
        hash = 37 * hash + Objects.hashCode(this.destino);
        hash = 37 * hash + Objects.hashCode(this.usuario);
        hash = 37 * hash + Objects.hashCode(this.estatus);
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
        final Transferencia other = (Transferencia) obj;
        if (!Objects.equals(this.idTransferencia, other.idTransferencia)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraDestino, other.idEstructuraDestino)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.observaciones, other.observaciones)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.origen, other.origen)) {
            return false;
        }
        if (!Objects.equals(this.destino, other.destino)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusTransferencia, other.idEstatusTransferencia)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    @Override
    public String toString() {
        return "Transferencia{" + "idTransferencia=" + idTransferencia + ", idEstructura=" + idEstructura + ", idEstructuraDestino=" + idEstructuraDestino + ", folio=" + folio + ", observaciones=" + observaciones + ", idEstatusTransferencia=" + idEstatusTransferencia + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", origen=" + origen + ", destino=" + destino + ", usuario=" + usuario + ", estatus=" + estatus + '}';
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }    
    
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(String idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdEstructuraDestino() {
        return idEstructuraDestino;
    }

    public void setIdEstructuraDestino(String idEstructuraDestino) {
        this.idEstructuraDestino = idEstructuraDestino;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getIdEstatusTransferencia() {
        return idEstatusTransferencia;
    }

    public void setIdEstatusTransferencia(Integer idEstatusTransferencia) {
        this.idEstatusTransferencia = idEstatusTransferencia;
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
