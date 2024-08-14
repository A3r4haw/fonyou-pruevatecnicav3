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
public class ExpStocks implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String articulo;
    private String lote;
    private double cantidad;
    private double cantEntr;
    private double cantSali;
    private String tipoAlmacen;
    private String almacen;
    private String pasillo;
    private String columna;
    private String nivel;
    private String posicion;
    private long version;
    private String tratado;
    private Date fechaTratado;
    private String error;
    private String formato;
    private String fechaCaducidad;
    private String centro;
    private String almacenLogico;
    private String descripcion;
    private String rotacion;
    private String estatico;
    private String multiR;
    private Double cantPendEnt;
    private Double cantPendSal;
    private String estado;
    private Double stockErp;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private String loteProveedor;
    private String loteFabricacion;
    private String propietario;
    private String matricula;
    private String sscc;

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

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantEntr() {
        return cantEntr;
    }

    public void setCantEntr(double cantEntr) {
        this.cantEntr = cantEntr;
    }

    public double getCantSali() {
        return cantSali;
    }

    public void setCantSali(double cantSali) {
        this.cantSali = cantSali;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getPasillo() {
        return pasillo;
    }

    public void setPasillo(String pasillo) {
        this.pasillo = pasillo;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getTratado() {
        return tratado;
    }

    public void setTratado(String tratado) {
        this.tratado = tratado;
    }

    public Date getFechaTratado() {
        return fechaTratado;
    }

    public void setFechaTratado(Date fechaTratado) {
        this.fechaTratado = fechaTratado;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getAlmacenLogico() {
        return almacenLogico;
    }

    public void setAlmacenLogico(String almacenLogico) {
        this.almacenLogico = almacenLogico;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRotacion() {
        return rotacion;
    }

    public void setRotacion(String rotacion) {
        this.rotacion = rotacion;
    }

    public String getEstatico() {
        return estatico;
    }

    public void setEstatico(String estatico) {
        this.estatico = estatico;
    }

    public String getMultiR() {
        return multiR;
    }

    public void setMultiR(String multiR) {
        this.multiR = multiR;
    }

    public Double getCantPendEnt() {
        return cantPendEnt;
    }

    public void setCantPendEnt(Double cantPendEnt) {
        this.cantPendEnt = cantPendEnt;
    }

    public Double getCantPendSal() {
        return cantPendSal;
    }

    public void setCantPendSal(Double cantPendSal) {
        this.cantPendSal = cantPendSal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getStockErp() {
        return stockErp;
    }

    public void setStockErp(Double stockErp) {
        this.stockErp = stockErp;
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

    public String getLoteProveedor() {
        return loteProveedor;
    }

    public void setLoteProveedor(String loteProveedor) {
        this.loteProveedor = loteProveedor;
    }

    public String getLoteFabricacion() {
        return loteFabricacion;
    }

    public void setLoteFabricacion(String loteFabricacion) {
        this.loteFabricacion = loteFabricacion;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSscc() {
        return sscc;
    }

    public void setSscc(String sscc) {
        this.sscc = sscc;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 37 * hash + Objects.hashCode(this.articulo);
        hash = 37 * hash + Objects.hashCode(this.lote);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.cantidad) ^ (Double.doubleToLongBits(this.cantidad) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.cantEntr) ^ (Double.doubleToLongBits(this.cantEntr) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.cantSali) ^ (Double.doubleToLongBits(this.cantSali) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 37 * hash + Objects.hashCode(this.almacen);
        hash = 37 * hash + Objects.hashCode(this.pasillo);
        hash = 37 * hash + Objects.hashCode(this.columna);
        hash = 37 * hash + Objects.hashCode(this.nivel);
        hash = 37 * hash + Objects.hashCode(this.posicion);
        hash = 37 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 37 * hash + Objects.hashCode(this.tratado);
        hash = 37 * hash + Objects.hashCode(this.fechaTratado);
        hash = 37 * hash + Objects.hashCode(this.error);
        hash = 37 * hash + Objects.hashCode(this.formato);
        hash = 37 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 37 * hash + Objects.hashCode(this.centro);
        hash = 37 * hash + Objects.hashCode(this.almacenLogico);
        hash = 37 * hash + Objects.hashCode(this.descripcion);
        hash = 37 * hash + Objects.hashCode(this.rotacion);
        hash = 37 * hash + Objects.hashCode(this.estatico);
        hash = 37 * hash + Objects.hashCode(this.multiR);
        hash = 37 * hash + Objects.hashCode(this.cantPendEnt);
        hash = 37 * hash + Objects.hashCode(this.cantPendSal);
        hash = 37 * hash + Objects.hashCode(this.estado);
        hash = 37 * hash + Objects.hashCode(this.stockErp);
        hash = 37 * hash + Objects.hashCode(this.datoExtra1);
        hash = 37 * hash + Objects.hashCode(this.datoExtra2);
        hash = 37 * hash + Objects.hashCode(this.datoExtra3);
        hash = 37 * hash + Objects.hashCode(this.loteProveedor);
        hash = 37 * hash + Objects.hashCode(this.loteFabricacion);
        hash = 37 * hash + Objects.hashCode(this.propietario);
        hash = 37 * hash + Objects.hashCode(this.matricula);
        hash = 37 * hash + Objects.hashCode(this.sscc);
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
        final ExpStocks other = (ExpStocks) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.cantidad) != Double.doubleToLongBits(other.cantidad)) {
            return false;
        }
        if (Double.doubleToLongBits(this.cantEntr) != Double.doubleToLongBits(other.cantEntr)) {
            return false;
        }
        if (Double.doubleToLongBits(this.cantSali) != Double.doubleToLongBits(other.cantSali)) {
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
        if (!Objects.equals(this.tipoAlmacen, other.tipoAlmacen)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        if (!Objects.equals(this.pasillo, other.pasillo)) {
            return false;
        }
        if (!Objects.equals(this.columna, other.columna)) {
            return false;
        }
        if (!Objects.equals(this.nivel, other.nivel)) {
            return false;
        }
        if (!Objects.equals(this.posicion, other.posicion)) {
            return false;
        }
        if (!Objects.equals(this.tratado, other.tratado)) {
            return false;
        }
        if (!Objects.equals(this.error, other.error)) {
            return false;
        }
        if (!Objects.equals(this.formato, other.formato)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.centro, other.centro)) {
            return false;
        }
        if (!Objects.equals(this.almacenLogico, other.almacenLogico)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.rotacion, other.rotacion)) {
            return false;
        }
        if (!Objects.equals(this.estatico, other.estatico)) {
            return false;
        }
        if (!Objects.equals(this.multiR, other.multiR)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
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
        if (!Objects.equals(this.loteProveedor, other.loteProveedor)) {
            return false;
        }
        if (!Objects.equals(this.loteFabricacion, other.loteFabricacion)) {
            return false;
        }
        if (!Objects.equals(this.propietario, other.propietario)) {
            return false;
        }
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Objects.equals(this.sscc, other.sscc)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.cantPendEnt, other.cantPendEnt)) {
            return false;
        }
        if (!Objects.equals(this.cantPendSal, other.cantPendSal)) {
            return false;
        }
        return Objects.equals(this.stockErp, other.stockErp);
    }
}
