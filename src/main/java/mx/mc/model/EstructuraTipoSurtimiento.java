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
public class EstructuraTipoSurtimiento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String idEstructuraTipoSurtimiento;
    private String idEstructuraAlmacen;
    private String idTipoSurtimiento;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.idEstructuraTipoSurtimiento);
        hash = 83 * hash + Objects.hashCode(this.idEstructuraAlmacen);
        hash = 83 * hash + Objects.hashCode(this.idTipoSurtimiento);
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
        final EstructuraTipoSurtimiento other = (EstructuraTipoSurtimiento) obj;
        if (!Objects.equals(this.idEstructuraTipoSurtimiento, other.idEstructuraTipoSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraAlmacen, other.idEstructuraAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.idTipoSurtimiento, other.idTipoSurtimiento)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EstructuraTipoSurtimiento{" + "idEstructuraTipoSurtimiento=" + idEstructuraTipoSurtimiento + ", idEstructuraAlmacen=" + idEstructuraAlmacen + ", idTipoSurtimiento=" + idTipoSurtimiento + '}';
    }

    public String getIdEstructuraTipoSurtimiento() {
        return idEstructuraTipoSurtimiento;
    }

    public void setIdEstructuraTipoSurtimiento(String idEstructuraTipoSurtimiento) {
        this.idEstructuraTipoSurtimiento = idEstructuraTipoSurtimiento;
    }

    public String getIdEstructuraAlmacen() {
        return idEstructuraAlmacen;
    }

    public void setIdEstructuraAlmacen(String idEstructuraAlmacen) {
        this.idEstructuraAlmacen = idEstructuraAlmacen;
    }

    public String getIdTipoSurtimiento() {
        return idTipoSurtimiento;
    }

    public void setIdTipoSurtimiento(String idTipoSurtimiento) {
        this.idTipoSurtimiento = idTipoSurtimiento;
    }
    
    
    
}
