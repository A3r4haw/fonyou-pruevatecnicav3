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
public class IntipharmPrescriptionDispense implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idAlmacen;
    private String fechaDispensacion;
    private String claveInstitucional;
    private int cantidadDispensada;
    private String lote;
    private String fechaCaducidad;
    private int numeroMedicacion;
    private boolean banderaInventario;

    public IntipharmPrescriptionDispense() {
    }

    public IntipharmPrescriptionDispense(String idAlmacen, String fechaDispensacion, String claveInstitucional, int cantidadDispensada, String lote, String fechaCaducidad,int numeroMedicacion) {
        this.idAlmacen = idAlmacen;
        this.fechaDispensacion = fechaDispensacion;
        this.claveInstitucional = claveInstitucional;
        this.cantidadDispensada = cantidadDispensada;
        this.lote = lote;
        this.fechaCaducidad = fechaCaducidad;
        this.numeroMedicacion = numeroMedicacion;
    }    

    public String getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(String idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getFechaDispensacion() {
        return fechaDispensacion;
    }

    public void setFechaDispensacion(String fechaDispensacion) {
        this.fechaDispensacion = fechaDispensacion;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public int getCantidadDispensada() {
        return cantidadDispensada;
    }

    public void setCantidadDispensada(int cantidadDispensada) {
        this.cantidadDispensada = cantidadDispensada;
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

    public int getNumeroMedicacion() {
        return numeroMedicacion;
    }

    public void setNumeroMedicacion(int numeroMedicacion) {
        this.numeroMedicacion = numeroMedicacion;
    }

    public boolean isBanderaInventario() {
        return banderaInventario;
    }

    public void setBanderaInventario(boolean banderaInventario) {
        this.banderaInventario = banderaInventario;
    }
        
}
