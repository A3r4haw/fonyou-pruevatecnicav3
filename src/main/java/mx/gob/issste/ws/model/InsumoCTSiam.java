/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class InsumoCTSiam implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("id_centro_trabajo")
    private Integer idCentroTrabajo;
    @JsonProperty("claveCT")
    private String claveCT;
    @JsonProperty("id_insumo")
    private Integer idInsumo;
    @JsonProperty("cveInsumo")
    private String cveInsumo;
    @JsonProperty("existencia")
    private Integer existencia;
    @JsonProperty("existencia_apartados")
    private Integer existenciaApartados;
    @JsonProperty("tipo")
    private String tipo;
    @JsonProperty("precio_promedio")
    private double precioPromedio;
    @JsonProperty("descripcion")
    private String descripcion;

    public InsumoCTSiam() {
        
    }

    public InsumoCTSiam(Integer id, Integer idCentroTrabajo, String claveCT, Integer idInsumo, String cveInsumo, Integer existencia, Integer existenciaApartados, String tipo, double precioPromedio, String descripcion) {
        this.id = id;
        this.idCentroTrabajo = idCentroTrabajo;
        this.claveCT = claveCT;
        this.idInsumo = idInsumo;
        this.cveInsumo = cveInsumo;
        this.existencia = existencia;
        this.existenciaApartados = existenciaApartados;
        this.tipo = tipo;
        this.precioPromedio = precioPromedio;
        this.descripcion = descripcion;
    }

    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCentroTrabajo() {
        return idCentroTrabajo;
    }

    public void setIdCentroTrabajo(Integer idCentroTrabajo) {
        this.idCentroTrabajo = idCentroTrabajo;
    }

    public String getClaveCT() {
        return claveCT;
    }

    public void setClaveCT(String claveCT) {
        this.claveCT = claveCT;
    }

    public Integer getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(Integer idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getCveInsumo() {
        return cveInsumo;
    }

    public void setCveInsumo(String cveInsumo) {
        this.cveInsumo = cveInsumo;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    public Integer getExistenciaApartados() {
        return existenciaApartados;
    }

    public void setExistenciaApartados(Integer existenciaApartados) {
        this.existenciaApartados = existenciaApartados;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioPromedio() {
        return precioPromedio;
    }

    public void setPrecioPromedio(double precioPromedio) {
        this.precioPromedio = precioPromedio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
