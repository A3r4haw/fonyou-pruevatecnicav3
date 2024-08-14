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
 * @author bbautista
 */
public class SolucionExtended extends Solucion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombrePaciente;
    private String tipoPrescripcion;
    private Date fechaProgramada;
    private String folio;
    private String nombreEstructura;
    private String infusion;
    private String estabilidad;
//    private Double velocidad;
//    private Double ritmo;
    private String tipoEnvase;
    private String tipoSolucion;
    private Date fechaMinistracion;
    private String ministro;
    private String nombreProtocolo;
    private String estatusSolucion;
    private String nombreViaAdministracion;
    private String nombrePresentacion;
    private String descripcion;

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getTipoPrescripcion() {
        return tipoPrescripcion;
    }

    public void setTipoPrescripcion(String tipoPrescripcion) {
        this.tipoPrescripcion = tipoPrescripcion;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getInfusion() {
        return infusion;
    }

    public void setInfusion(String infusion) {
        this.infusion = infusion;
    }

    public String getEstabilidad() {
        return estabilidad;
    }

    public void setEstabilidad(String estabilidad) {
        this.estabilidad = estabilidad;
    }

//    public Double getVelocidad() {
//        return velocidad;
//    }
//
//    public void setVelocidad(Double velocidad) {
//        this.velocidad = velocidad;
//    }
//
//    public Double getRitmo() {
//        return ritmo;
//    }
//
//    public void setRitmo(Double ritmo) {
//        this.ritmo = ritmo;
//    }
    public String getTipoEnvase() {
        return tipoEnvase;
    }

    public void setTipoEnvase(String tipoEnvase) {
        this.tipoEnvase = tipoEnvase;
    }

    public String getTipoSolucion() {
        return tipoSolucion;
    }

    public void setTipoSolucion(String tipoSolucion) {
        this.tipoSolucion = tipoSolucion;
    }

    public Date getFechaMinistracion() {
        return fechaMinistracion;
    }

    public void setFechaMinistracion(Date fechaMinistracion) {
        this.fechaMinistracion = fechaMinistracion;
    }

    public String getMinistro() {
        return ministro;
    }

    public void setMinistro(String ministro) {
        this.ministro = ministro;
    }

    public String getNombreProtocolo() {
        return nombreProtocolo;
    }

    public void setNombreProtocolo(String nombreProtocolo) {
        this.nombreProtocolo = nombreProtocolo;
    }

    public String getEstatusSolucion() {
        return estatusSolucion;
    }

    public void setEstatusSolucion(String estatusSolucion) {
        this.estatusSolucion = estatusSolucion;
    }

    public String getNombreViaAdministracion() {
        return nombreViaAdministracion;
    }

    public void setNombreViaAdministracion(String nombreViaAdministracion) {
        this.nombreViaAdministracion = nombreViaAdministracion;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
