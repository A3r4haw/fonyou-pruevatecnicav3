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
public class ExpExpediciones implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String expedicion;
    private String documento;
    private String agencia;
    private String fechaServicio;
    private Integer bultos;
    private Double peso;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private int anulada;
    private Integer mesa;
    private String expedicionAgencia;
    private String centroCoste;
    private Double volumen;
    private String tipoMovimientoCliente;
    private Integer packingList;
    private String expedPadre;
    private String situacion;
    private String clasificaciones;
    private String valorClasificaciones;
    private String tipoBloqueFin;
    private String referencia;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(String expedicion) {
        this.expedicion = expedicion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(String fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public Integer getBultos() {
        return bultos;
    }

    public void setBultos(Integer bultos) {
        this.bultos = bultos;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getDatoExtra1() {
        return datoExtra1;
    }

    public void setDatoExtra1(String datoExtra1) {
        this.datoExtra1 = datoExtra1;
    }

    public String getDatoExtra2() {
        return datoExtra2;
    }

    public void setDatoExtra2(String datoExtra2) {
        this.datoExtra2 = datoExtra2;
    }

    public String getDatoExtra3() {
        return datoExtra3;
    }

    public void setDatoExtra3(String datoExtra3) {
        this.datoExtra3 = datoExtra3;
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

    public int getAnulada() {
        return anulada;
    }

    public void setAnulada(int anulada) {
        this.anulada = anulada;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public String getExpedicionAgencia() {
        return expedicionAgencia;
    }

    public void setExpedicionAgencia(String expedicionAgencia) {
        this.expedicionAgencia = expedicionAgencia;
    }

    public String getCentroCoste() {
        return centroCoste;
    }

    public void setCentroCoste(String centroCoste) {
        this.centroCoste = centroCoste;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public String getTipoMovimientoCliente() {
        return tipoMovimientoCliente;
    }

    public void setTipoMovimientoCliente(String tipoMovimientoCliente) {
        this.tipoMovimientoCliente = tipoMovimientoCliente;
    }

    public Integer getPackingList() {
        return packingList;
    }

    public void setPackingList(Integer packingList) {
        this.packingList = packingList;
    }

    public String getExpedPadre() {
        return expedPadre;
    }

    public void setExpedPadre(String expedPadre) {
        this.expedPadre = expedPadre;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getClasificaciones() {
        return clasificaciones;
    }

    public void setClasificaciones(String clasificaciones) {
        this.clasificaciones = clasificaciones;
    }

    public String getValorClasificaciones() {
        return valorClasificaciones;
    }

    public void setValorClasificaciones(String valorClasificaciones) {
        this.valorClasificaciones = valorClasificaciones;
    }

    public String getTipoBloqueFin() {
        return tipoBloqueFin;
    }

    public void setTipoBloqueFin(String tipoBloqueFin) {
        this.tipoBloqueFin = tipoBloqueFin;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 47 * hash + Objects.hashCode(this.expedicion);
        hash = 47 * hash + Objects.hashCode(this.documento);
        hash = 47 * hash + Objects.hashCode(this.agencia);
        hash = 47 * hash + Objects.hashCode(this.fechaServicio);
        hash = 47 * hash + Objects.hashCode(this.bultos);
        hash = 47 * hash + Objects.hashCode(this.peso);
        hash = 47 * hash + Objects.hashCode(this.datoExtra1);
        hash = 47 * hash + Objects.hashCode(this.datoExtra2);
        hash = 47 * hash + Objects.hashCode(this.datoExtra3);
        hash = 47 * hash + Objects.hashCode(this.tratado);
        hash = 47 * hash + Objects.hashCode(this.error);
        hash = 47 * hash + Objects.hashCode(this.fechaTratado);
        hash = 47 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 47 * hash + this.anulada;
        hash = 47 * hash + Objects.hashCode(this.mesa);
        hash = 47 * hash + Objects.hashCode(this.expedicionAgencia);
        hash = 47 * hash + Objects.hashCode(this.centroCoste);
        hash = 47 * hash + Objects.hashCode(this.volumen);
        hash = 47 * hash + Objects.hashCode(this.tipoMovimientoCliente);
        hash = 47 * hash + Objects.hashCode(this.packingList);
        hash = 47 * hash + Objects.hashCode(this.expedPadre);
        hash = 47 * hash + Objects.hashCode(this.situacion);
        hash = 47 * hash + Objects.hashCode(this.clasificaciones);
        hash = 47 * hash + Objects.hashCode(this.valorClasificaciones);
        hash = 47 * hash + Objects.hashCode(this.tipoBloqueFin);
        hash = 47 * hash + Objects.hashCode(this.referencia);
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
        final ExpExpediciones other = (ExpExpediciones) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.anulada != other.anulada) {
            return false;
        }
        if (!Objects.equals(this.expedicion, other.expedicion)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.agencia, other.agencia)) {
            return false;
        }
        if (!Objects.equals(this.fechaServicio, other.fechaServicio)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra1, other.datoExtra1)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra2, other.datoExtra2)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra3, other.datoExtra3)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.expedicionAgencia, other.expedicionAgencia)) {
            return false;
        }
        if (!Objects.equals(this.centroCoste, other.centroCoste)) {
            return false;
        }
        if (!Objects.equals(this.tipoMovimientoCliente, other.tipoMovimientoCliente)) {
            return false;
        }
        if (!Objects.equals(this.expedPadre, other.expedPadre)) {
            return false;
        }
        if (!Objects.equals(this.situacion, other.situacion)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valorClasificaciones, other.valorClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.tipoBloqueFin, other.tipoBloqueFin)) {
            return false;
        }
        if (!Objects.equals(this.referencia, other.referencia)) {
            return false;
        }
        if (!Objects.equals(this.bultos, other.bultos)) {
            return false;
        }
        if (!Objects.equals(this.peso, other.peso)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.mesa, other.mesa)) {
            return false;
        }
        if (!Objects.equals(this.volumen, other.volumen)) {
            return false;
        }
        return Objects.equals(this.packingList, other.packingList);
    }
}
