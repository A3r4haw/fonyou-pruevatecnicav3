/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seidor.ulises.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author apalacios
 */
public class ExpPeticRepo implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private Long repo;
    private String articulo;
    private String lote;
    private String formato;
    private double cantidad;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private String tipoAlmacen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getRepo() {
        return repo;
    }

    public void setRepo(Long repo) {
        this.repo = repo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getTratado() {
        return tratado;
    }

    public void setTratado(String tratado) {
        this.tratado = tratado;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getFechaTratado() {
        return fechaTratado;
    }

    public void setFechaTratado(Date fechaTratado) {
        this.fechaTratado = fechaTratado;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 71 * hash + Objects.hashCode(this.repo);
        hash = 71 * hash + Objects.hashCode(this.articulo);
        hash = 71 * hash + Objects.hashCode(this.lote);
        hash = 71 * hash + Objects.hashCode(this.formato);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.cantidad) ^ (Double.doubleToLongBits(this.cantidad) >>> 32));
        hash = 71 * hash + Objects.hashCode(this.tratado);
        hash = 71 * hash + Objects.hashCode(this.error);
        hash = 71 * hash + Objects.hashCode(this.fechaTratado);
        hash = 71 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 71 * hash + Objects.hashCode(this.tipoAlmacen);
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
        final ExpPeticRepo other = (ExpPeticRepo) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.cantidad) != Double.doubleToLongBits(other.cantidad)) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.articulo, other.articulo)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.formato, other.formato)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.repo, other.repo)) {
            return false;
        }
        return Objects.equals(this.fechaTratado, other.fechaTratado);
    }
}
