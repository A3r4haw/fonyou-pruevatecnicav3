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
public class ClaveProveedorBarras implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String claveProveedor;
    private String codigoBarras;
    private String claveInstitucional;
    private Integer cantidadXCaja;
    private String insertUsuario;
    private Date insertFecha;
    private String updateUsuario;
    private Date updateFecha;

    public ClaveProveedorBarras() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.claveProveedor);
        hash = 89 * hash + Objects.hashCode(this.codigoBarras);
        hash = 89 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 89 * hash + Objects.hashCode(this.cantidadXCaja);
        hash = 89 * hash + Objects.hashCode(this.insertUsuario);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.updateUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
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
        final ClaveProveedorBarras other = (ClaveProveedorBarras) obj;
        if (!Objects.equals(this.claveProveedor, other.claveProveedor)) {
            return false;
        }
        if (!Objects.equals(this.codigoBarras, other.codigoBarras)) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.cantidadXCaja, other.cantidadXCaja)) {
            return false;
        }
        if (!Objects.equals(this.insertUsuario, other.insertUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateUsuario, other.updateUsuario)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    @Override
    public String toString() {
        return "ClaveProveedorBarras{" + "claveProveedor=" + claveProveedor + ", codigoBarras=" + codigoBarras + ", claveInstitucional=" + claveInstitucional + ", cantidadXCaja=" + cantidadXCaja + ", insertUsuario=" + insertUsuario + ", insertFecha=" + insertFecha + ", updateUsuario=" + updateUsuario + ", updateFecha=" + updateFecha + '}';
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public Integer getCantidadXCaja() {
        return cantidadXCaja;
    }

    public void setCantidadXCaja(Integer cantidadXCaja) {
        this.cantidadXCaja = cantidadXCaja;
    }

    public String getInsertUsuario() {
        return insertUsuario;
    }

    public void setInsertUsuario(String insertUsuario) {
        this.insertUsuario = insertUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateUsuario() {
        return updateUsuario;
    }

    public void setUpdateUsuario(String updateUsuario) {
        this.updateUsuario = updateUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }
    
    
}
