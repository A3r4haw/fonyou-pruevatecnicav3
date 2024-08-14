/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;

/**
 * @author aortiz
 */
public class DevolucionMinistracionExtended extends DevolucionMinistracion {  

    private static final long serialVersionUID = 1L;
    private String cadenaBusqueda;
    private String nombreCompleto;
    private String apellidoPaterno;
    private String apellidoMaterno; 
    private String nombreCama;
    private String estatusDevolicion;
    private Date fechaDevolucion;
    private String servicio;
    private String numPaciente;
    private String folioPrescripcion;
    private String idSurtimiento;
    private String idPrescripcion;

    public String getCadenaBusqueda() {
        return cadenaBusqueda;
    }

    public void setCadenaBusqueda(String cadenaBusqueda) {
        this.cadenaBusqueda = cadenaBusqueda;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombreCama() {
        return nombreCama;
    }

    public void setNombreCama(String nombreCama) {
        this.nombreCama = nombreCama;
    }

    public String getEstatusDevolicion() {
        return estatusDevolicion;
    }

    public void setEstatusDevolicion(String estatusDevolicion) {
        this.estatusDevolicion = estatusDevolicion;
    }

    @Override
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    @Override
    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getNumPaciente() {
        return numPaciente;
    }

    public void setNumPaciente(String numPaciente) {
        this.numPaciente = numPaciente;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    @Override
    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    @Override
    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }
}
