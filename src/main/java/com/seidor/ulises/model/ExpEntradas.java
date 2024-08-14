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
public class ExpEntradas implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String albaran;
    private String linea;
    private String articulo;
    private String lote;
    private String formato;
    private Double cantidadOriginal;
    private Double cantidadConfirmada;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private String tipoMovimientoCliente;
    private String documento;
    private String fechaEntrada;
    private String fechaCaducidad;
    private String medico;
    private String tipoStock;
    private String documentoTransporte;
    private String fechaFabricacion;
    private String clasificaciones;
    private String valorClasificaciones;
    private String clasificacionesLin;
    private String valorClasificacionesLin;
    private String ua;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlbaran() {
        return albaran;
    }

    public void setAlbaran(String albaran) {
        this.albaran = albaran;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
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

    public Double getCantidadOriginal() {
        return cantidadOriginal;
    }

    public void setCantidadOriginal(Double cantidadOriginal) {
        this.cantidadOriginal = cantidadOriginal;
    }

    public Double getCantidadConfirmada() {
        return cantidadConfirmada;
    }

    public void setCantidadConfirmada(Double cantidadConfirmada) {
        this.cantidadConfirmada = cantidadConfirmada;
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

    public String getTipoMovimientoCliente() {
        return tipoMovimientoCliente;
    }

    public void setTipoMovimientoCliente(String tipoMovimientoCliente) {
        this.tipoMovimientoCliente = tipoMovimientoCliente;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getTipoStock() {
        return tipoStock;
    }

    public void setTipoStock(String tipoStock) {
        this.tipoStock = tipoStock;
    }

    public String getDocumentoTransporte() {
        return documentoTransporte;
    }

    public void setDocumentoTransporte(String documentoTransporte) {
        this.documentoTransporte = documentoTransporte;
    }

    public String getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(String fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
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

    public String getClasificacionesLin() {
        return clasificacionesLin;
    }

    public void setClasificacionesLin(String clasificacionesLin) {
        this.clasificacionesLin = clasificacionesLin;
    }

    public String getValorClasificacionesLin() {
        return valorClasificacionesLin;
    }

    public void setValorClasificacionesLin(String valorClasificacionesLin) {
        this.valorClasificacionesLin = valorClasificacionesLin;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.albaran);
        hash = 83 * hash + Objects.hashCode(this.linea);
        hash = 83 * hash + Objects.hashCode(this.articulo);
        hash = 83 * hash + Objects.hashCode(this.lote);
        hash = 83 * hash + Objects.hashCode(this.formato);
        hash = 83 * hash + Objects.hashCode(this.cantidadOriginal);
        hash = 83 * hash + Objects.hashCode(this.cantidadConfirmada);
        hash = 83 * hash + Objects.hashCode(this.datoExtra1);
        hash = 83 * hash + Objects.hashCode(this.datoExtra2);
        hash = 83 * hash + Objects.hashCode(this.datoExtra3);
        hash = 83 * hash + Objects.hashCode(this.tratado);
        hash = 83 * hash + Objects.hashCode(this.error);
        hash = 83 * hash + Objects.hashCode(this.fechaTratado);
        hash = 83 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 83 * hash + Objects.hashCode(this.tipoMovimientoCliente);
        hash = 83 * hash + Objects.hashCode(this.documento);
        hash = 83 * hash + Objects.hashCode(this.fechaEntrada);
        hash = 83 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 83 * hash + Objects.hashCode(this.medico);
        hash = 83 * hash + Objects.hashCode(this.tipoStock);
        hash = 83 * hash + Objects.hashCode(this.documentoTransporte);
        hash = 83 * hash + Objects.hashCode(this.fechaFabricacion);
        hash = 83 * hash + Objects.hashCode(this.clasificaciones);
        hash = 83 * hash + Objects.hashCode(this.valorClasificaciones);
        hash = 83 * hash + Objects.hashCode(this.clasificacionesLin);
        hash = 83 * hash + Objects.hashCode(this.valorClasificacionesLin);
        hash = 83 * hash + Objects.hashCode(this.ua);
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
        final ExpEntradas other = (ExpEntradas) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.albaran, other.albaran)) {
            return false;
        }
        if (!Objects.equals(this.linea, other.linea)) {
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
        if (!Objects.equals(this.tipoMovimientoCliente, other.tipoMovimientoCliente)) {
            return false;
        }
        if (!Objects.equals(this.documento, other.documento)) {
            return false;
        }
        if (!Objects.equals(this.fechaEntrada, other.fechaEntrada)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.medico, other.medico)) {
            return false;
        }
        if (!Objects.equals(this.tipoStock, other.tipoStock)) {
            return false;
        }
        if (!Objects.equals(this.documentoTransporte, other.documentoTransporte)) {
            return false;
        }
        if (!Objects.equals(this.fechaFabricacion, other.fechaFabricacion)) {
            return false;
        }
        if (!Objects.equals(this.clasificaciones, other.clasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.valorClasificaciones, other.valorClasificaciones)) {
            return false;
        }
        if (!Objects.equals(this.clasificacionesLin, other.clasificacionesLin)) {
            return false;
        }
        if (!Objects.equals(this.valorClasificacionesLin, other.valorClasificacionesLin)) {
            return false;
        }
        if (!Objects.equals(this.ua, other.ua)) {
            return false;
        }
        if (!Objects.equals(this.cantidadOriginal, other.cantidadOriginal)) {
            return false;
        }
        if (!Objects.equals(this.cantidadConfirmada, other.cantidadConfirmada)) {
            return false;
        }
        return Objects.equals(this.fechaTratado, other.fechaTratado);
    }
}
