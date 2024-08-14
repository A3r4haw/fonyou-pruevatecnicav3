/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mcalderon
 */
public class DataResultConsolidado implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private String nombreAlmacen;
    private Date fechaEnviada;
    private Integer completa;
    private double completaPor;
    private Integer parcial;
    private double  parcialPor;
    private Integer negada;
    private double  negadaPor;
    private Integer enCeros;
    private double enCerosPor;
    private Integer enNulo;
    private double enNuloPor;    
    private Integer total;

    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public Date getFechaEnviada() {
        return fechaEnviada;
    }

    public void setFechaEnviada(Date fechaEnviada) {
        this.fechaEnviada = fechaEnviada;
    }

    public Integer getCompleta() {
        return completa;
    }

    public void setCompleta(Integer completa) {
        this.completa = completa;
    }

    public double getCompletaPor() {
        return completaPor;
    }

    public void setCompletaPor(double completaPor) {
        this.completaPor = completaPor;
    }    

    public Integer getParcial() {
        return parcial;
    }

    public void setParcial(Integer parcial) {
        this.parcial = parcial;
    }

    public double getParcialPor() {
        return parcialPor;
    }

    public void setParcialPor(double parcialPor) {
        this.parcialPor = parcialPor;
    }

    public Integer getNegada() {
        return negada;
    }

    public void setNegada(Integer negada) {
        this.negada = negada;
    }

    public double getNegadaPor() {
        return negadaPor;
    }

    public void setNegadaPor(double negadaPor) {
        this.negadaPor = negadaPor;
    }

    public Integer getEnCeros() {
        return enCeros;
    }

    public void setEnCeros(Integer enCeros) {
        this.enCeros = enCeros;
    }

    public double getEnCerosPor() {
        return enCerosPor;
    }

    public void setEnCerosPor(double enCerosPor) {
        this.enCerosPor = enCerosPor;
    }

    public Integer getEnNulo() {
        return enNulo;
    }

    public void setEnNulo(Integer enNulo) {
        this.enNulo = enNulo;
    }

    public double getEnNuloPor() {
        return enNuloPor;
    }

    public void setEnNuloPor(double enNuloPor) {
        this.enNuloPor = enNuloPor;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }        
    
}
