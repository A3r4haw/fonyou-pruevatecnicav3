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
public class IntipharmPrescriptionMedication implements Serializable {
    private static final long serialVersionUID = 1L;

    private String institutionalCode;
    private String nombreCorto;
    private String nombreLargo;
    private String unidadMedida;
    private BigDecimal concentracion;
    private String presentacion;
    private Integer tipoMedicamento;
    private BigDecimal precio;   
    private String folio;
    private String nombreSubcategoriaMedicamento;
    private int idSubcategoriaMedicamento;
    private int cantidadSolicitada;
    private int  numeroMedicacion;
    private String estatusMedicacion;
    

    public IntipharmPrescriptionMedication() {
        //No code needed in constructor
    }

    public String getInstitutionalCode() {
        return institutionalCode;
    }

    public void setInstitutionalCode(String institutionalCode) {
        this.institutionalCode = institutionalCode;
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

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(BigDecimal concentracion) {
        this.concentracion = concentracion;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public Integer getTipoMedicamento() {
        return tipoMedicamento;
    }

    public void setTipoMedicamento(Integer tipoMedicamento) {
        this.tipoMedicamento = tipoMedicamento;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreSubcategoriaMedicamento() {
        return nombreSubcategoriaMedicamento;
    }

    public void setNombreSubcategoriaMedicamento(String nombreSubcategoriaMedicamento) {
        this.nombreSubcategoriaMedicamento = nombreSubcategoriaMedicamento;
    }

    public int getIdSubcategoriaMedicamento() {
        return idSubcategoriaMedicamento;
    }

    public void setIdSubcategoriaMedicamento(int idSubcategoriaMedicamento) {
        this.idSubcategoriaMedicamento = idSubcategoriaMedicamento;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getNumeroMedicacion() {
        return numeroMedicacion;
    }

    public void setNumeroMedicacion(int numeroMedicacion) {
        this.numeroMedicacion = numeroMedicacion;
    }

    public String getEstatusMedicacion() {
        return estatusMedicacion;
    }

    public void setEstatusMedicacion(String estatusMedicacion) {
        this.estatusMedicacion = estatusMedicacion;
    }
    
    
      
}
