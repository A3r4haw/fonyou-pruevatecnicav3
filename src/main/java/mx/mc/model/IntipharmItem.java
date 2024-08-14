/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author mcalderon
 */
public class IntipharmItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String claveInstitucional;
    private String nombreCorto;
    private String nombreLargo;
    private String nombrePresentacion;
    private Integer factorTransformacion;
    private String viaAdministracion;
    private String claveAlmacen;
    private BigDecimal precioUnitario;
    private Integer tipoInsumo;
    private String sku;
    private Integer activo;
    private String concentracion;
    private String unidadMedida;

    public IntipharmItem() {
    //No code needed in constructor
    }
    
    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNombreLargo() {
        return nombreLargo;
    }

    public void setNombreLargo(String nombreLargo) {
        this.nombreLargo = nombreLargo;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public Integer getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(Integer factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public String getClaveAlmacen() {
        return claveAlmacen;
    }

    public void setClaveAlmacen(String claveAlmacen) {
        this.claveAlmacen = claveAlmacen;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(Integer tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
            
}
