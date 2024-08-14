/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author gcruz
 */
public class ClaveProveedorBarras_Extend extends ClaveProveedorBarras{

    private static final long serialVersionUID = 1L;
    
    private BigInteger cantidadRecibida;
    private String lote;
    private Date fechaCaducidad;
    private String idEstructura;
    private String idMedicamento;    
    private String nombreCorto;
    private String nombreLargo;
    private String idInventario;            
    private Integer cantidadActual;
    private int presentacionComercial;
    private String folio;    
    private String idReabastoEnviado;
    private Integer idPresentacionSalida;
    private String calculoCajaUnidosis;
    private Integer factorTransformacion;
    private String presentacion;
    private double cajas;
    
    public BigInteger getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(BigInteger cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
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

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getPresentacionComercial() {
        return presentacionComercial;
    }

    public void setPresentacionComercial(int presentacionComercial) {
        this.presentacionComercial = presentacionComercial;
    }    

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }    

    public String getIdReabastoEnviado() {
        return idReabastoEnviado;
    }

    public void setIdReabastoEnviado(String idReabastoEnviado) {
        this.idReabastoEnviado = idReabastoEnviado;
    }

    public Integer getIdPresentacionSalida() {
        return idPresentacionSalida;
    }

    public void setIdPresentacionSalida(Integer idPresentacionSalida) {
        this.idPresentacionSalida = idPresentacionSalida;
    }

    public String getCalculoCajaUnidosis() {
        return calculoCajaUnidosis;
    }

    public void setCalculoCajaUnidosis(String calculoCajaUnidosis) {
        this.calculoCajaUnidosis = calculoCajaUnidosis;
    }

    public Integer getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(Integer factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public double getCajas() {
        return cajas;
    }

    public void setCajas(double cajas) {
        this.cajas = cajas;
    }
    
}
