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
public class ExpPacking implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String expedicion;
    private double bulto;
    private Double nivelEmbalaje;
    private String barcodeAgencia;
    private String articulo;
    private String lote;
    private String formato;
    private Double cantidad;
    private String datoExtra1;
    private String datoExtra2;
    private String datoExtra3;
    private String tratado;
    private String error;
    private Date fechaTratado;
    private long version;
    private long idExped;
    private Double pesoBrutoBulto;
    private Double pesoNetoBulto;
    private Double pesoMov;
    private String linea;
    private String barcodepick;
    private String fechaCaducidad;
    private String matricula;
    private Double volumen;
    private String numeroSerie;
    private String barcodesscc;
    private String eanValidado;
    private String barcodeaux;
    private Double idBulto;
    private String barcodeauxiliar2;
    private long idPacking;
    private String barcodeauxiliar3;
    private Double pesoBascula;
    private String tipoStock;
    private String loteProveedor;
    private String loteFabricacion;
    private String tipoEmbalaje;
    private Double alto;
    private Double ancho;
    private Double largo;
    private long packingList;

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

    public double getBulto() {
        return bulto;
    }

    public void setBulto(double bulto) {
        this.bulto = bulto;
    }

    public Double getNivelEmbalaje() {
        return nivelEmbalaje;
    }

    public void setNivelEmbalaje(Double nivelEmbalaje) {
        this.nivelEmbalaje = nivelEmbalaje;
    }

    public String getBarcodeAgencia() {
        return barcodeAgencia;
    }

    public void setBarcodeAgencia(String barcodeAgencia) {
        this.barcodeAgencia = barcodeAgencia;
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

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
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

    public long getIdExped() {
        return idExped;
    }

    public void setIdExped(long idExped) {
        this.idExped = idExped;
    }

    public Double getPesoBrutoBulto() {
        return pesoBrutoBulto;
    }

    public void setPesoBrutoBulto(Double pesoBrutoBulto) {
        this.pesoBrutoBulto = pesoBrutoBulto;
    }

    public Double getPesoNetoBulto() {
        return pesoNetoBulto;
    }

    public void setPesoNetoBulto(Double pesoNetoBulto) {
        this.pesoNetoBulto = pesoNetoBulto;
    }

    public Double getPesoMov() {
        return pesoMov;
    }

    public void setPesoMov(Double pesoMov) {
        this.pesoMov = pesoMov;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getBarcodepick() {
        return barcodepick;
    }

    public void setBarcodepick(String barcodepick) {
        this.barcodepick = barcodepick;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Double getVolumen() {
        return volumen;
    }

    public void setVolumen(Double volumen) {
        this.volumen = volumen;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getBarcodesscc() {
        return barcodesscc;
    }

    public void setBarcodesscc(String barcodesscc) {
        this.barcodesscc = barcodesscc;
    }

    public String getEanValidado() {
        return eanValidado;
    }

    public void setEanValidado(String eanValidado) {
        this.eanValidado = eanValidado;
    }

    public String getBarcodeaux() {
        return barcodeaux;
    }

    public void setBarcodeaux(String barcodeaux) {
        this.barcodeaux = barcodeaux;
    }

    public Double getIdBulto() {
        return idBulto;
    }

    public void setIdBulto(Double idBulto) {
        this.idBulto = idBulto;
    }

    public String getBarcodeauxiliar2() {
        return barcodeauxiliar2;
    }

    public void setBarcodeauxiliar2(String barcodeauxiliar2) {
        this.barcodeauxiliar2 = barcodeauxiliar2;
    }

    public long getIdPacking() {
        return idPacking;
    }

    public void setIdPacking(long idPacking) {
        this.idPacking = idPacking;
    }

    public String getBarcodeauxiliar3() {
        return barcodeauxiliar3;
    }

    public void setBarcodeauxiliar3(String barcodeauxiliar3) {
        this.barcodeauxiliar3 = barcodeauxiliar3;
    }

    public Double getPesoBascula() {
        return pesoBascula;
    }

    public void setPesoBascula(Double pesoBascula) {
        this.pesoBascula = pesoBascula;
    }

    public String getTipoStock() {
        return tipoStock;
    }

    public void setTipoStock(String tipoStock) {
        this.tipoStock = tipoStock;
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

    public String getTipoEmbalaje() {
        return tipoEmbalaje;
    }

    public void setTipoEmbalaje(String tipoEmbalaje) {
        this.tipoEmbalaje = tipoEmbalaje;
    }

    public Double getAlto() {
        return alto;
    }

    public void setAlto(Double alto) {
        this.alto = alto;
    }

    public Double getAncho() {
        return ancho;
    }

    public void setAncho(Double ancho) {
        this.ancho = ancho;
    }

    public Double getLargo() {
        return largo;
    }

    public void setLargo(Double largo) {
        this.largo = largo;
    }

    public long getPackingList() {
        return packingList;
    }

    public void setPackingList(long packingList) {
        this.packingList = packingList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 59 * hash + Objects.hashCode(this.expedicion);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.bulto) ^ (Double.doubleToLongBits(this.bulto) >>> 32));
        hash = 59 * hash + Objects.hashCode(this.nivelEmbalaje);
        hash = 59 * hash + Objects.hashCode(this.barcodeAgencia);
        hash = 59 * hash + Objects.hashCode(this.articulo);
        hash = 59 * hash + Objects.hashCode(this.lote);
        hash = 59 * hash + Objects.hashCode(this.formato);
        hash = 59 * hash + Objects.hashCode(this.cantidad);
        hash = 59 * hash + Objects.hashCode(this.datoExtra1);
        hash = 59 * hash + Objects.hashCode(this.datoExtra2);
        hash = 59 * hash + Objects.hashCode(this.datoExtra3);
        hash = 59 * hash + Objects.hashCode(this.tratado);
        hash = 59 * hash + Objects.hashCode(this.error);
        hash = 59 * hash + Objects.hashCode(this.fechaTratado);
        hash = 59 * hash + (int) (this.version ^ (this.version >>> 32));
        hash = 59 * hash + (int) (this.idExped ^ (this.idExped >>> 32));
        hash = 59 * hash + Objects.hashCode(this.pesoBrutoBulto);
        hash = 59 * hash + Objects.hashCode(this.pesoNetoBulto);
        hash = 59 * hash + Objects.hashCode(this.pesoMov);
        hash = 59 * hash + Objects.hashCode(this.linea);
        hash = 59 * hash + Objects.hashCode(this.barcodepick);
        hash = 59 * hash + Objects.hashCode(this.fechaCaducidad);
        hash = 59 * hash + Objects.hashCode(this.matricula);
        hash = 59 * hash + Objects.hashCode(this.volumen);
        hash = 59 * hash + Objects.hashCode(this.numeroSerie);
        hash = 59 * hash + Objects.hashCode(this.barcodesscc);
        hash = 59 * hash + Objects.hashCode(this.eanValidado);
        hash = 59 * hash + Objects.hashCode(this.barcodeaux);
        hash = 59 * hash + Objects.hashCode(this.idBulto);
        hash = 59 * hash + Objects.hashCode(this.barcodeauxiliar2);
        hash = 59 * hash + (int) (this.idPacking ^ (this.idPacking >>> 32));
        hash = 59 * hash + Objects.hashCode(this.barcodeauxiliar3);
        hash = 59 * hash + Objects.hashCode(this.pesoBascula);
        hash = 59 * hash + Objects.hashCode(this.tipoStock);
        hash = 59 * hash + Objects.hashCode(this.loteProveedor);
        hash = 59 * hash + Objects.hashCode(this.loteFabricacion);
        hash = 59 * hash + Objects.hashCode(this.tipoEmbalaje);
        hash = 59 * hash + Objects.hashCode(this.alto);
        hash = 59 * hash + Objects.hashCode(this.ancho);
        hash = 59 * hash + Objects.hashCode(this.largo);
        hash = 59 * hash + (int) (this.packingList ^ (this.packingList >>> 32));
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
        final ExpPacking other = (ExpPacking) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.bulto) != Double.doubleToLongBits(other.bulto)) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (this.idExped != other.idExped) {
            return false;
        }
        if (this.idPacking != other.idPacking) {
            return false;
        }
        if (this.packingList != other.packingList) {
            return false;
        }
        if (!Objects.equals(this.expedicion, other.expedicion)) {
            return false;
        }
        if (!Objects.equals(this.barcodeAgencia, other.barcodeAgencia)) {
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
        if (!Objects.equals(this.linea, other.linea)) {
            return false;
        }
        if (!Objects.equals(this.barcodepick, other.barcodepick)) {
            return false;
        }
        if (!Objects.equals(this.fechaCaducidad, other.fechaCaducidad)) {
            return false;
        }
        if (!Objects.equals(this.matricula, other.matricula)) {
            return false;
        }
        if (!Objects.equals(this.numeroSerie, other.numeroSerie)) {
            return false;
        }
        if (!Objects.equals(this.barcodesscc, other.barcodesscc)) {
            return false;
        }
        if (!Objects.equals(this.eanValidado, other.eanValidado)) {
            return false;
        }
        if (!Objects.equals(this.barcodeaux, other.barcodeaux)) {
            return false;
        }
        if (!Objects.equals(this.barcodeauxiliar2, other.barcodeauxiliar2)) {
            return false;
        }
        if (!Objects.equals(this.barcodeauxiliar3, other.barcodeauxiliar3)) {
            return false;
        }
        if (!Objects.equals(this.tipoStock, other.tipoStock)) {
            return false;
        }
        if (!Objects.equals(this.loteProveedor, other.loteProveedor)) {
            return false;
        }
        if (!Objects.equals(this.loteFabricacion, other.loteFabricacion)) {
            return false;
        }
        if (!Objects.equals(this.tipoEmbalaje, other.tipoEmbalaje)) {
            return false;
        }
        if (!Objects.equals(this.nivelEmbalaje, other.nivelEmbalaje)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.fechaTratado, other.fechaTratado)) {
            return false;
        }
        if (!Objects.equals(this.pesoBrutoBulto, other.pesoBrutoBulto)) {
            return false;
        }
        if (!Objects.equals(this.pesoNetoBulto, other.pesoNetoBulto)) {
            return false;
        }
        if (!Objects.equals(this.pesoMov, other.pesoMov)) {
            return false;
        }
        if (!Objects.equals(this.volumen, other.volumen)) {
            return false;
        }
        if (!Objects.equals(this.idBulto, other.idBulto)) {
            return false;
        }
        if (!Objects.equals(this.pesoBascula, other.pesoBascula)) {
            return false;
        }
        if (!Objects.equals(this.alto, other.alto)) {
            return false;
        }
        if (!Objects.equals(this.ancho, other.ancho)) {
            return false;
        }
        return Objects.equals(this.largo, other.largo);
    }
}
