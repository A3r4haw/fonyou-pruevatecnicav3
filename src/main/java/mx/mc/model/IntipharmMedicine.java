/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author mcalderon
 */
public class IntipharmMedicine implements Serializable {
    private static final long serialVersionUID = 1L;

    private String claveInstitucional;
    private String lote;
    private String fechaCaducidad;
    private int cantidaIngresada;

    public IntipharmMedicine() {
    }

    public IntipharmMedicine(String claveInstitucional, String lote, String fechaCaducidad, int cantidaIngresada) {
        this.claveInstitucional = claveInstitucional;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidaIngresada = cantidaIngresada;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCantidaIngresada() {
        return cantidaIngresada;
    }

    public void setCantidaIngresada(int cantidaIngresada) {
        this.cantidaIngresada = cantidaIngresada;
    }
    
    
}
