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
public class ExpMovimientos implements Serializable {    
    private static final long serialVersionUID = 1L;
    private long id;
    private Double cantidad;
    private String articulo;
    private String lote;
    private String estado;
    private String ubicacion;
    private Double saldoConfirmacion;
    private Double saldoEntr;
    private Double saldoSali;
    private String tipoMovimiento;
    private String tipoMovimientoCliente;
    private String usuarioConfirmacion;
    private long version;
    private String observacion;
    private String documento1;
    private String documento2;
    private String linea;
    private String tratado;
    private Date fechaTratado;
    private String error;
    private String tipoAlmacen;
    private String almacen;
    private String pasillo;
    private String columna;
    private String nivel;
    private String posicion;
    private String fechaConfirmacion;
    private String formato;
    private String incidencia;
    private Double cantIncidencia;
    private String obsIncidencia;
    private String fechaCaducidad;
    private String almacenLogico;
    private String documentoTransporte;
    private String tipoStock;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private Long moviRelacion;
    private String loteFabricacion;
    private String loteProveedor;
    private String ua;
    private String reserva;
    private String numeroSerie;
    private String datoExtra4;
    private String propietario;
    private String tipoMov;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getSaldoConfirmacion() {
        return saldoConfirmacion;
    }

    public void setSaldoConfirmacion(Double saldoConfirmacion) {
        this.saldoConfirmacion = saldoConfirmacion;
    }

    public Double getSaldoEntr() {
        return saldoEntr;
    }

    public void setSaldoEntr(Double saldoEntr) {
        this.saldoEntr = saldoEntr;
    }

    public Double getSaldoSali() {
        return saldoSali;
    }

    public void setSaldoSali(Double saldoSali) {
        this.saldoSali = saldoSali;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getTipoMovimientoCliente() {
        return tipoMovimientoCliente;
    }

    public void setTipoMovimientoCliente(String tipoMovimientoCliente) {
        this.tipoMovimientoCliente = tipoMovimientoCliente;
    }

    public String getUsuarioConfirmacion() {
        return usuarioConfirmacion;
    }

    public void setUsuarioConfirmacion(String usuarioConfirmacion) {
        this.usuarioConfirmacion = usuarioConfirmacion;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDocumento1() {
        return documento1;
    }

    public void setDocumento1(String documento1) {
        this.documento1 = documento1;
    }

    public String getDocumento2() {
        return documento2;
    }

    public void setDocumento2(String documento2) {
        this.documento2 = documento2;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
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

    public String getFechaConfirmacion() {
        return fechaConfirmacion;
    }

    public void setFechaConfirmacion(String fechaConfirmacion) {
        this.fechaConfirmacion = fechaConfirmacion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    public Double getCantIncidencia() {
        return cantIncidencia;
    }

    public void setCantIncidencia(Double cantIncidencia) {
        this.cantIncidencia = cantIncidencia;
    }

    public String getObsIncidencia() {
        return obsIncidencia;
    }

    public void setObsIncidencia(String obsIncidencia) {
        this.obsIncidencia = obsIncidencia;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getAlmacenLogico() {
        return almacenLogico;
    }

    public void setAlmacenLogico(String almacenLogico) {
        this.almacenLogico = almacenLogico;
    }

    public String getDocumentoTransporte() {
        return documentoTransporte;
    }

    public void setDocumentoTransporte(String documentoTransporte) {
        this.documentoTransporte = documentoTransporte;
    }

    public String getTipoStock() {
        return tipoStock;
    }

    public void setTipoStock(String tipoStock) {
        this.tipoStock = tipoStock;
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

    public Long getMoviRelacion() {
        return moviRelacion;
    }

    public void setMoviRelacion(Long moviRelacion) {
        this.moviRelacion = moviRelacion;
    }

    public String getLoteFabricacion() {
        return loteFabricacion;
    }

    public void setLoteFabricacion(String loteFabricacion) {
        this.loteFabricacion = loteFabricacion;
    }

    public String getLoteProveedor() {
        return loteProveedor;
    }

    public void setLoteProveedor(String loteProveedor) {
        this.loteProveedor = loteProveedor;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getReserva() {
        return reserva;
    }

    public void setReserva(String reserva) {
        this.reserva = reserva;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getDatoExtra4() {
        return datoExtra4;
    }

    public void setDatoExtra4(String datoExtra4) {
        this.datoExtra4 = datoExtra4;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 61 * hash + Objects.hashCode(this.cantidad);
        hash = 61 * hash + Objects.hashCode(this.articulo);
        hash = 61 * hash + Objects.hashCode(this.lote);
        hash = 61 * hash + Objects.hashCode(this.estado);
        hash = 61 * hash + Objects.hashCode(this.ubicacion);
        hash = 61 * hash + Objects.hashCode(this.saldoConfirmacion);
        hash = 61 * hash + Objects.hashCode(this.saldoEntr);
        hash = 61 * hash + Objects.hashCode(this.saldoSali);
        hash = 61 * hash + Objects.hashCode(this.tipoMovimiento);
        hash = 61 * hash + Objects.hashCode(this.tipoMovimientoCliente);
        hash = 61 * hash + Objects.hashCode(this.usuarioConfirmacion);
        hash = 61 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 61 * hash + Objects.hashCode(this.observacion);
        hash = 61 * hash + Objects.hashCode(this.documento1);
        hash = 61 * hash + Objects.hashCode(this.documento2);
        hash = 61 * hash + Objects.hashCode(this.linea);
        hash = 61 * hash + Objects.hashCode(this.tratado);
        hash = 61 * hash + Objects.hashCode(this.fechaTratado);
        hash = 61 * hash + Objects.hashCode(this.error);
        hash = 61 * hash + Objects.hashCode(this.tipoAlmacen);
        hash = 61 * hash + Objects.hashCode(this.almacen);
        hash = 61 * hash + Objects.hashCode(this.pasillo);
        hash = 61 * hash + Objects.hashCode(this.columna);
        hash = 61 * hash + Objects.hashCode(this.nivel);
        hash = 61 * hash + Objects.hashCode(this.posicion);
        hash = 61 * hash + Objects.hashCode(this.fechaConfirmacion);
        hash = 61 * hash + Objects.hashCode(this.formato);
        hash = 61 * hash + Objects.hashCode(this.incidencia);
        hash = 61 * hash + Objects.hashCode(this.cantIncidencia);
        hash = 61 * hash + Objects.hashCode(this.obsIncidencia);
        hash = 61 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 61 * hash + Objects.hashCode(this.almacenLogico);
        hash = 61 * hash + Objects.hashCode(this.documentoTransporte);
        hash = 61 * hash + Objects.hashCode(this.tipoStock);
        hash = 61 * hash + Objects.hashCode(this.datoExtra1);
        hash = 61 * hash + Objects.hashCode(this.datoExtra2);
        hash = 61 * hash + Objects.hashCode(this.datoExtra3);
        hash = 61 * hash + Objects.hashCode(this.moviRelacion);
        hash = 61 * hash + Objects.hashCode(this.loteFabricacion);
        hash = 61 * hash + Objects.hashCode(this.loteProveedor);
        hash = 61 * hash + Objects.hashCode(this.ua);
        hash = 61 * hash + Objects.hashCode(this.reserva);
        hash = 61 * hash + Objects.hashCode(this.numeroSerie);
        hash = 61 * hash + Objects.hashCode(this.datoExtra4);
        hash = 61 * hash + Objects.hashCode(this.propietario);
        hash = 61 * hash + Objects.hashCode(this.tipoMov);
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
        final ExpMovimientos other = (ExpMovimientos) obj;
        if (this.id != other.id) {
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
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.ubicacion, other.ubicacion)) {
            return false;
        }
        if (!Objects.equals(this.tipoMovimiento, other.tipoMovimiento)) {
            return false;
        }
        if (!Objects.equals(this.tipoMovimientoCliente, other.tipoMovimientoCliente)) {
            return false;
        }
        if (!Objects.equals(this.usuarioConfirmacion, other.usuarioConfirmacion)) {
            return false;
        }
        if (!Objects.equals(this.observacion, other.observacion)) {
            return false;
        }
        if (!Objects.equals(this.documento1, other.documento1)) {
            return false;
        }
        if (!Objects.equals(this.documento2, other.documento2)) {
            return false;
        }
        if (!Objects.equals(this.linea, other.linea)) {
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
        if (!Objects.equals(this.fechaConfirmacion, other.fechaConfirmacion)) {
            return false;
        }
        if (!Objects.equals(this.formato, other.formato)) {
            return false;
        }
        if (!Objects.equals(this.incidencia, other.incidencia)) {
            return false;
        }
        if (!Objects.equals(this.obsIncidencia, other.obsIncidencia)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.almacenLogico, other.almacenLogico)) {
            return false;
        }
        if (!Objects.equals(this.documentoTransporte, other.documentoTransporte)) {
            return false;
        }
        if (!Objects.equals(this.tipoStock, other.tipoStock)) {
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
        if (!Objects.equals(this.loteFabricacion, other.loteFabricacion)) {
            return false;
        }
        if (!Objects.equals(this.loteProveedor, other.loteProveedor)) {
            return false;
        }
        if (!Objects.equals(this.ua, other.ua)) {
            return false;
        }
        if (!Objects.equals(this.reserva, other.reserva)) {
            return false;
        }
        if (!Objects.equals(this.numeroSerie, other.numeroSerie)) {
            return false;
        }
        if (!Objects.equals(this.datoExtra4, other.datoExtra4)) {
            return false;
        }
        if (!Objects.equals(this.propietario, other.propietario)) {
            return false;
        }
        if (!Objects.equals(this.tipoMov, other.tipoMov)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.saldoConfirmacion, other.saldoConfirmacion)) {
            return false;
        }
        if (!Objects.equals(this.saldoEntr, other.saldoEntr)) {
            return false;
        }
        if (!Objects.equals(this.saldoSali, other.saldoSali)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.cantIncidencia, other.cantIncidencia)) {
            return false;
        }
        return Objects.equals(this.moviRelacion, other.moviRelacion);
    }
}
