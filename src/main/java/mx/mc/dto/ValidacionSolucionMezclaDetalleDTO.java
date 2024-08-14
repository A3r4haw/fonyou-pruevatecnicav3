/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.dto;

/**
 *
 * @author gcruz
 */
public class ValidacionSolucionMezclaDetalleDTO {
    
    private String nombre;
    private String indicada;
    private String prescrita;
    private String causa;
    
    public ValidacionSolucionMezclaDetalleDTO() {
        
    }

    public ValidacionSolucionMezclaDetalleDTO(String nombre, String indicada, String prescrita, String causa) {
        this.nombre = nombre;
        this.indicada = indicada;
        this.prescrita = prescrita;
        this.causa = causa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIndicada() {
        return indicada;
    }

    public void setIndicada(String indicada) {
        this.indicada = indicada;
    }

    public String getPrescrita() {
        return prescrita;
    }

    public void setPrescrita(String prescrita) {
        this.prescrita = prescrita;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }
    
}
