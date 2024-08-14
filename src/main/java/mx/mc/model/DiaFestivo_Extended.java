/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

/**
 *
 * @author mcalderon
 */
public class DiaFestivo_Extended extends DiaFestivo{
    
    private static final long serialVersionUID = 1L;
    private String mes;
    private int numMes;
    private String urlMes;
    private int numeroDia;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getNumMes() {
        return numMes;
    }

    public void setNumMes(int numMes) {
        this.numMes = numMes;
    }

    public String getUrlMes() {
        return urlMes;
    }

    public void setUrlMes(String urlMes) {
        this.urlMes = urlMes;
    }

    public int getNumeroDia() {
        return numeroDia;
    }

    public void setNumeroDia(int numeroDia) {
        this.numeroDia = numeroDia;
    }    
    
}
