/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class TipoMotivo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idTipoMotivo;
    private Integer idTipoMovimiento;
    private String descripcion;
    private Integer activo;
    private Integer permiteResurtimiento;

    public TipoMotivo() {
    }

    public TipoMotivo(Integer idTipoMotivo, Integer idTipoMovimiento, String descripcion, Integer activo, Integer permiteResurtimiento) {
        this.idTipoMotivo = idTipoMotivo;
        this.idTipoMovimiento = idTipoMovimiento;
        this.descripcion = descripcion;
        this.activo = activo;
        this.permiteResurtimiento = permiteResurtimiento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idTipoMotivo);
        hash = 89 * hash + Objects.hashCode(this.idTipoMovimiento);
        hash = 89 * hash + Objects.hashCode(this.descripcion);
        hash = 89 * hash + Objects.hashCode(this.activo);
        hash = 89 * hash + Objects.hashCode(this.permiteResurtimiento);
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
        final TipoMotivo other = (TipoMotivo) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.idTipoMotivo, other.idTipoMotivo)) {
            return false;
        }
        if (!Objects.equals(this.idTipoMovimiento, other.idTipoMovimiento)) {
            return false;
        }
        if (!Objects.equals(this.activo, other.activo)) {
            return false;
        }
        return Objects.equals(this.permiteResurtimiento, other.permiteResurtimiento);
    }

    @Override
    public String toString() {
        return "TipoMotivo{" + "idTipoMotivo=" + idTipoMotivo + ", idTipoMovimiento=" + idTipoMovimiento + ", descripcion=" + descripcion + ", activo=" + activo + ", permiteResurtimiento=" + permiteResurtimiento + '}';
    }

    public Integer getIdTipoMotivo() {
        return idTipoMotivo;
    }

    public void setIdTipoMotivo(Integer idTipoMotivo) {
        this.idTipoMotivo = idTipoMotivo;
    }

    public Integer getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    public void setIdTipoMovimiento(Integer idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getPermiteResurtimiento() {
        return permiteResurtimiento;
    }

    public void setPermiteResurtimiento(Integer permiteResurtimiento) {
        this.permiteResurtimiento = permiteResurtimiento;
    }
    
}
