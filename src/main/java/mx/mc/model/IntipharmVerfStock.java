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
public class IntipharmVerfStock implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String fechaHora;    
    private String almacen;
    private String claveInstitucional;
    private String lote;
    private String fechaCaducidad;
    private int cantidad;

    public IntipharmVerfStock() {
    }

    public IntipharmVerfStock(String fechaHora, String almacen, String claveInstitucional, String lote, String fechaCaducidad, int cantidad) {
        this.fechaHora = fechaHora;
        this.almacen = almacen;
        this.claveInstitucional = claveInstitucional;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
        this.cantidad = cantidad;
    }
    
    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
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
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
