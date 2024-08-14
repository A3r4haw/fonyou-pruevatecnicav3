/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author bbautista
 */
public class VistaMedicamento implements Serializable {
    private static final long serialVersionUID = 1L;
   
   private String idMedicamento;
   private String claveInstitucional;
   private String sustanciaActiva; 
   private String nombreCorto;
   private String nombreLargo;
   private String cuadroBasico;
   private Integer activo;
   private Integer tipo;

    private String viaAdministracion;
    private String concentracion;
    private String unidadConcentracion;
    private String presentacionLaboratorio;
    private String laboratorio;
    private String categoria;
    private String subcategoria;
    private String presentacionEntrada;
    private String presentacionSalida;
    private Integer factorTransformacion;
    private String grupo;
    private String idLocalidadAVG;
    private int refrigeracion;
    private String claveAlterna;
    private String codigoBarrasAlterno;
    private int mezclaParental;
        
    public VistaMedicamento() {
        //No code needed in constructor
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getUnidadConcentracion() {
        return unidadConcentracion;
    }

    public void setUnidadConcentracion(String unidadConcentracion) {
        this.unidadConcentracion = unidadConcentracion;
    }

    public String getPresentacionLaboratorio() {
        return presentacionLaboratorio;
    }

    public void setPresentacionLaboratorio(String presentacionLaboratorio) {
        this.presentacionLaboratorio = presentacionLaboratorio;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getPresentacionEntrada() {
        return presentacionEntrada;
    }

    public void setPresentacionEntrada(String presentacionEntrada) {
        this.presentacionEntrada = presentacionEntrada;
    }

    public String getPresentacionSalida() {
        return presentacionSalida;
    }

    public void setPresentacionSalida(String presentacionSalida) {
        this.presentacionSalida = presentacionSalida;
    }

    public Integer getFactorTransformacion() {
        return factorTransformacion;
    }

    public void setFactorTransformacion(Integer factorTransformacion) {
        this.factorTransformacion = factorTransformacion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getSustanciaActiva() {
        return sustanciaActiva;
    }

    public void setSustanciaActiva(String sustanciaActiva) {
        this.sustanciaActiva = sustanciaActiva;
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

    public Integer getActivo() {
        return activo;
    }

    public String getCuadroBasico() {
        return cuadroBasico;
    }

    public void setCuadroBasico(String cuadroBasico) {
        this.cuadroBasico = cuadroBasico;
    }    

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getIdLocalidadAVG() {
        return idLocalidadAVG;
    }

    public void setIdLocalidadAVG(String idLocalidadAVG) {
        this.idLocalidadAVG = idLocalidadAVG;
    }

    public int getRefrigeracion() {
        return refrigeracion;
    }

    public void setRefrigeracion(int refrigeracion) {
        this.refrigeracion = refrigeracion;
    }

    public String getClaveAlterna() {
        return claveAlterna;
    }

    public void setClaveAlterna(String claveAlterna) {
        this.claveAlterna = claveAlterna;
    }

    public String getCodigoBarrasAlterno() {
        return codigoBarrasAlterno;
    }

    public void setCodigoBarrasAlterno(String codigoBarrasAlterno) {
        this.codigoBarrasAlterno = codigoBarrasAlterno;
    }

    public int getMezclaParental() {
        return mezclaParental;
    }

    public void setMezclaParental(int mezclaParental) {
        this.mezclaParental = mezclaParental;
    }
   
}
