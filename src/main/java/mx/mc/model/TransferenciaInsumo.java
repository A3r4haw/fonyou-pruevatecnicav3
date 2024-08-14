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
public class TransferenciaInsumo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String  idTransferenciaInsumo;
    private String  idTransferencia;
    private String  idInsumo;
    private Integer cantidadEnviada;
    private Integer idEstatusTransferencia;
    private Date    insertFecha;
    private String  insertIdUsuario;
    private Date    updateFecha;
    private String  updateIdUsuario;
    private String  lote;

    private String clave;
    private String nombreComercial;    
    private Date fechaCaducidad;
    private Integer tipoInsumo;
    
    public TransferenciaInsumo() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.idTransferenciaInsumo);
        hash = 11 * hash + Objects.hashCode(this.idTransferencia);
        hash = 11 * hash + Objects.hashCode(this.idInsumo);
        hash = 11 * hash + Objects.hashCode(this.cantidadEnviada);
        hash = 11 * hash + Objects.hashCode(this.idEstatusTransferencia);
        hash = 11 * hash + Objects.hashCode(this.insertFecha);
        hash = 11 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 11 * hash + Objects.hashCode(this.updateFecha);
        hash = 11 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 11 * hash + Objects.hashCode(this.lote);
        hash = 11 * hash + Objects.hashCode(this.clave);
        hash = 11 * hash + Objects.hashCode(this.nombreComercial);
        hash = 11 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 11 * hash + Objects.hashCode(this.tipoInsumo);
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
        final TransferenciaInsumo other = (TransferenciaInsumo) obj;
        if (!Objects.equals(this.idTransferenciaInsumo, other.idTransferenciaInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idTransferencia, other.idTransferencia)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.nombreComercial, other.nombreComercial)) {
            return false;
        }
        if (!Objects.equals(this.cantidadEnviada, other.cantidadEnviada)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusTransferencia, other.idEstatusTransferencia)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        return Objects.equals(this.tipoInsumo, other.tipoInsumo);
    }

    @Override
    public String toString() {
        return "TransferenciaInsumo{" + "idTransferenciaInsumo=" + idTransferenciaInsumo + ", idTransferencia=" + idTransferencia + ", idInsumo=" + idInsumo + ", cantidadEnviada=" + cantidadEnviada + ", idEstatusTransferencia=" + idEstatusTransferencia + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", lote=" + lote + ", clave=" + clave + ", nombreComercial=" + nombreComercial + ", fechaCaducidad=" + fechaCaducidad + ", tipoInsumo=" + tipoInsumo + '}';
    }

    public Integer getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(Integer tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

   
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }    
   
    public String getIdTransferenciaInsumo() {
        return idTransferenciaInsumo;
    }

    public void setIdTransferenciaInsumo(String idTransferenciaInsumo) {
        this.idTransferenciaInsumo = idTransferenciaInsumo;
    }

    public String getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(String idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getCantidadEnviada() {
        return cantidadEnviada;
    }

    public void setCantidadEnviada(Integer cantidadEnviada) {
        this.cantidadEnviada = cantidadEnviada;
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
