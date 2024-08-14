/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

/**
 *
 * @author gcruz
 */
public class AlertaFarmacovigilancia {
    
    private Integer numero;
    private String factor1;
    private String factor2;
    private String riesgo;
    private String tipo;
    private String origen;
    private String clasificacion;
    private String motivo;
    private String colorRiesgo;

    @Override
    public String toString() {
        return "AlertaFarmacovigilancia{" + "numero=" + numero + ", factor1=" + factor1 + ", factor2=" + factor2 + ", riesgo=" + riesgo + ", tipo=" + tipo + ", origen=" + origen + ", clasificacion=" + clasificacion + ", motivo=" + motivo + ", colorRiesgo=" + colorRiesgo + '}';
    }

   

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getFactor1() {
        return factor1;
    }

    public void setFactor1(String factor1) {
        this.factor1 = factor1;
    }

    public String getFactor2() {
        return factor2;
    }

    public void setFactor2(String factor2) {
        this.factor2 = factor2;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }        

    public String getColorRiesgo() {
        return colorRiesgo;
    }

    public void setColorRiesgo(String colorRiesgo) {
        this.colorRiesgo = colorRiesgo;
    }
    
}
