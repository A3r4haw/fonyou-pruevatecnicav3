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
public class DevolucionMinistracionDetalle implements Serializable {
    private static final long serialVersionUID = 1L;	
 
    private String idDevolucionDetalle;
    private String idDevolucionMinistracion;
    private String idInsumo;
    private String idInventario;
    private Integer idMotivoDevolucion;
    private Integer cantidad;
    private Integer idEstatusDevolucion;
    private boolean conforme;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private boolean merma;

    public DevolucionMinistracionDetalle() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.idDevolucionDetalle);
        hash = 13 * hash + Objects.hashCode(this.idDevolucionMinistracion);
        hash = 13 * hash + Objects.hashCode(this.idInsumo);
        hash = 13 * hash + Objects.hashCode(this.idInventario);
        hash = 13 * hash + Objects.hashCode(this.idMotivoDevolucion);
        hash = 13 * hash + Objects.hashCode(this.cantidad);
        hash = 13 * hash + Objects.hashCode(this.idEstatusDevolucion);
        hash = 13 * hash + (this.conforme ? 1 : 0);
        hash = 13 * hash + Objects.hashCode(this.insertFecha);
        hash = 13 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 13 * hash + Objects.hashCode(this.updateFecha);
        hash = 13 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 13 * hash + (this.merma ? 1 : 0);
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
        final DevolucionMinistracionDetalle other = (DevolucionMinistracionDetalle) obj;
        if (this.conforme != other.conforme) {
            return false;
        }
        if (this.merma != other.merma) {
            return false;
        }
        if (!Objects.equals(this.idDevolucionDetalle, other.idDevolucionDetalle)) {
            return false;
        }
        if (!Objects.equals(this.idDevolucionMinistracion, other.idDevolucionMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idInventario, other.idInventario)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoDevolucion, other.idMotivoDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusDevolucion, other.idEstatusDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    
    
    

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }        

    public String getIdDevolucionDetalle() {
        return idDevolucionDetalle;
    }

    public void setIdDevolucionDetalle(String idDevolucionDetalle) {
        this.idDevolucionDetalle = idDevolucionDetalle;
    }

    public String getIdDevolucionMinistracion() {
        return idDevolucionMinistracion;
    }

    public void setIdDevolucionMinistracion(String idDevolucionMinistracion) {
        this.idDevolucionMinistracion = idDevolucionMinistracion;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdMotivoDevolucion() {
        return idMotivoDevolucion;
    }

    public void setIdMotivoDevolucion(Integer idMotivoDevolucion) {
        this.idMotivoDevolucion = idMotivoDevolucion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdEstatusDevolucion() {
        return idEstatusDevolucion;
    }

    public void setIdEstatusDevolucion(Integer idEstatusDevolucion) {
        this.idEstatusDevolucion = idEstatusDevolucion;
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

    public boolean isConforme() {
        return conforme;
    }

    public void setConforme(boolean conforme) {
        this.conforme = conforme;
    }

    public boolean isMerma() {
        return merma;
    }

    public void setMerma(boolean merma) {
        this.merma = merma;
    }
    
    
}
