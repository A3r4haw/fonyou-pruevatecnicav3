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
public class ProtocoloDetalleDia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer idProtocoloDetalleDia;
    private Integer idProtocoloDetalle;
    private Integer numeroDia;
    private Integer idEstatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public ProtocoloDetalleDia() {
        
    }

    public ProtocoloDetalleDia(Integer idProtocoloDetalleDia, Integer idProtocoloDetalle, Integer numeroDia, Integer idEstatus, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idProtocoloDetalleDia = idProtocoloDetalleDia;
        this.idProtocoloDetalle = idProtocoloDetalle;
        this.numeroDia = numeroDia;
        this.idEstatus = idEstatus;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.idProtocoloDetalleDia);
        hash = 23 * hash + Objects.hashCode(this.idProtocoloDetalle);
        hash = 23 * hash + Objects.hashCode(this.numeroDia);
        hash = 23 * hash + Objects.hashCode(this.idEstatus);
        hash = 23 * hash + Objects.hashCode(this.insertFecha);
        hash = 23 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 23 * hash + Objects.hashCode(this.updateFecha);
        hash = 23 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final ProtocoloDetalleDia other = (ProtocoloDetalleDia) obj;
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idProtocoloDetalleDia, other.idProtocoloDetalleDia)) {
            return false;
        }
        if (!Objects.equals(this.idProtocoloDetalle, other.idProtocoloDetalle)) {
            return false;
        }
        if (!Objects.equals(this.numeroDia, other.numeroDia)) {
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

    public Integer getIdProtocoloDetalleDia() {
        return idProtocoloDetalleDia;
    }

    public void setIdProtocoloDetalleDia(Integer idProtocoloDetalleDia) {
        this.idProtocoloDetalleDia = idProtocoloDetalleDia;
    }

    public Integer getIdProtocoloDetalle() {
        return idProtocoloDetalle;
    }

    public void setIdProtocoloDetalle(Integer idProtocoloDetalle) {
        this.idProtocoloDetalle = idProtocoloDetalle;
    }

    public Integer getNumeroDia() {
        return numeroDia;
    }

    public void setNuemroDia(Integer nuemroDia) {
        this.numeroDia = numeroDia;
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
    
}
