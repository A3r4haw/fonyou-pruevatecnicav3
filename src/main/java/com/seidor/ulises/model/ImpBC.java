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
public class ImpBC implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String OPERACION = "A";
    private static final String UNIDAD_MEDIDA_EAN = "CODE128";
    private long id;
    private String articulo;
    private String bc;
    private String operacion;
    private String tratado;
    private String error;
    private long version;
    private Date fechaTratado;
    private Integer orden;
    private Integer nivelEmbalaje;
    private Date fechaCreacion;
    private String unidadMedidaEan;

    public ImpBC(String articulo, String lote, String caducidad) {
        this.articulo = articulo;
        caducidad = caducidad.replace("/", "");
        this.bc = articulo + "," + lote + "," + caducidad;
        this.operacion = OPERACION;
        this.tratado = "N";
        this.error = null;
        this.fechaTratado = null;
        this.version = 1;
        this.orden = 0;
        this.fechaCreacion = new Date();
        this.unidadMedidaEan = UNIDAD_MEDIDA_EAN;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getFechaTratado() {
        return fechaTratado;
    }

    public void setFechaTratado(Date fechaTratado) {
        this.fechaTratado = fechaTratado;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getNivelEmbalaje() {
        return nivelEmbalaje;
    }

    public void setNivelEmbalaje(Integer nivelEmbalaje) {
        this.nivelEmbalaje = nivelEmbalaje;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUnidadMedidaEan() {
        return unidadMedidaEan;
    }

    public void setUnidadMedidaEan(String unidadMedidaEan) {
        this.unidadMedidaEan = unidadMedidaEan;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.articulo);
        hash = 97 * hash + Objects.hashCode(this.bc);
        hash = 97 * hash + Objects.hashCode(this.operacion);
        hash = 97 * hash + Objects.hashCode(this.tratado);
        hash = 97 * hash + Objects.hashCode(this.error);
        hash = 97 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 97 * hash + Objects.hashCode(this.fechaTratado);
        hash = 97 * hash + Objects.hashCode(this.orden);
        hash = 97 * hash + Objects.hashCode(this.nivelEmbalaje);
        hash = 97 * hash + Objects.hashCode(this.fechaCreacion);
        hash = 97 * hash + Objects.hashCode(this.unidadMedidaEan);
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
        final ImpBC other = (ImpBC) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.articulo, other.articulo)) {
            return false;
        }
        if (!Objects.equals(this.bc, other.bc)) {
            return false;
        }
        if (!Objects.equals(this.operacion, other.operacion)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.unidadMedidaEan, other.unidadMedidaEan)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        if (!Objects.equals(this.nivelEmbalaje, other.nivelEmbalaje)) {
            return false;
        }
        return Objects.equals(this.fechaCreacion, other.fechaCreacion);
    }
}
