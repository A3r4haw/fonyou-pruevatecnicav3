/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class ReabastoRecepcion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idReabastoRecepcion;
    private String lote;
    private String claveProveedor;
    private Date fechaCaducidad;
    private BigInteger cantidad;
    private BigInteger cantidadEnviada;
    private String codigoBarras;
    private String folioRecepcion;
    private Date insertFecha;
    private String idUsuarioInsert;
    private Date updateFecha;
    private String idUsuarioUpdate;
    
    public ReabastoRecepcion(){
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idReabastoRecepcion);
        hash = 23 * hash + Objects.hashCode(this.lote);
        hash = 23 * hash + Objects.hashCode(this.claveProveedor);
        hash = 23 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 23 * hash + Objects.hashCode(this.cantidad);
        hash = 23 * hash + Objects.hashCode(this.cantidadEnviada);
        hash = 23 * hash + Objects.hashCode(this.codigoBarras);
        hash = 23 * hash + Objects.hashCode(this.folioRecepcion);
        hash = 23 * hash + Objects.hashCode(this.insertFecha);
        hash = 23 * hash + Objects.hashCode(this.idUsuarioInsert);
        hash = 23 * hash + Objects.hashCode(this.updateFecha);
        hash = 23 * hash + Objects.hashCode(this.idUsuarioUpdate);
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
        final ReabastoRecepcion other = (ReabastoRecepcion) obj;
        if (!Objects.equals(this.idReabastoRecepcion, other.idReabastoRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.claveProveedor, other.claveProveedor)) {
            return false;
        }
        if (!Objects.equals(this.codigoBarras, other.codigoBarras)) {
            return false;
        }
        if (!Objects.equals(this.folioRecepcion, other.folioRecepcion)) {
            return false;
        }        
        if (!Objects.equals(this.idUsuarioInsert, other.idUsuarioInsert)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioUpdate, other.idUsuarioUpdate)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.cantidadEnviada, other.cantidadEnviada)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
    

    public String getIdReabastoRecepcion() {
        return idReabastoRecepcion;
    }

    public void setIdReabastoRecepcion(String idReabastoRecepcion) {
        this.idReabastoRecepcion = idReabastoRecepcion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getClaveProveedor() {
        return claveProveedor;
    }

    public void setClaveProveedor(String claveProveedor) {
        this.claveProveedor = claveProveedor;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public BigInteger getCantidad() {
        return cantidad;
    }

    public BigInteger getCantidadEnviada() {
        return cantidadEnviada;
    }

    public void setCantidadEnviada(BigInteger cantidadEnviada) {
        this.cantidadEnviada = cantidadEnviada;
    }

    public void setCantidad(BigInteger cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getFolioRecepcion() {
        return folioRecepcion;
    }

    public void setFolioRecepcion(String folioRecepcion) {
        this.folioRecepcion = folioRecepcion;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getIdUsuarioInsert() {
        return idUsuarioInsert;
    }

    public void setIdUsuarioInsert(String idUsuarioInsert) {
        this.idUsuarioInsert = idUsuarioInsert;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getIdUsuarioUpdate() {
        return idUsuarioUpdate;
    }

    public void setIdUsuarioUpdate(String idUsuarioUpdate) {
        this.idUsuarioUpdate = idUsuarioUpdate;
    }
    
    
}
