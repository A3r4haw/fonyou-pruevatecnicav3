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
public class OrdenCompraDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String idOrdenCompraDetalle;
    private String idOrdenCompra;
    private String idInsumo;
    private Integer idPresentacionEntrada;
    private Integer cantidad;
    private Double precio;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public OrdenCompraDetalle() {
        //No code needed in constructor
    }

    public String getIdOrdenCompraDetalle() {
        return idOrdenCompraDetalle;
    }

    public void setIdOrdenCompraDetalle(String idOrdenCompraDetalle) {
        this.idOrdenCompraDetalle = idOrdenCompraDetalle;
    }

    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdPresentacionEntrada() {
        return idPresentacionEntrada;
    }

    public void setIdPresentacionEntrada(Integer idPresentacionEntrada) {
        this.idPresentacionEntrada = idPresentacionEntrada;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    @Override
    public String toString() {
        return "OrdenCompraDetalle{" + "idOrdenCompraDetalle=" + idOrdenCompraDetalle + ", idOrdenCompra=" + idOrdenCompra + ", idInsumo=" + idInsumo + ", idPresentacionEntrada=" + idPresentacionEntrada + ", cantidad=" + cantidad + ", precio=" + precio + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.idOrdenCompraDetalle);
        hash = 67 * hash + Objects.hashCode(this.idOrdenCompra);
        hash = 67 * hash + Objects.hashCode(this.idInsumo);
        hash = 67 * hash + Objects.hashCode(this.idPresentacionEntrada);
        hash = 67 * hash + Objects.hashCode(this.cantidad);
        hash = 67 * hash + Objects.hashCode(this.precio);
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final OrdenCompraDetalle other = (OrdenCompraDetalle) obj;
        if (!Objects.equals(this.idOrdenCompraDetalle, other.idOrdenCompraDetalle)) {
            return false;
        }
        if (!Objects.equals(this.idOrdenCompra, other.idOrdenCompra)) {
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
        if (!Objects.equals(this.idPresentacionEntrada, other.idPresentacionEntrada)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.precio, other.precio)) {
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
    
}