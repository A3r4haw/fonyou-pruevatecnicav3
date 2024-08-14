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
 * @author aortiz
 */
public class RepSurtimientoPresc implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String claveDocumento;
    private String claveArticulo;
    private String unidadHospitalaria;
    private String direccionHospital;
    private String tituloReceta;
    private String tipoReceta;
    private String folioPrescripcion;
    private String folioSurtimiento;
    private String hospital;
    private Date fechaActual;
    private String nombrePaciente;
    private String clavePaciente;
    private String sexo;
    private String nombreMedico;
    private String servicio;
    private String cama;
    private String piso;
    private String turno;
    private String cedulaMedico;
    private String medicacion;
    private String clasificacionPresupuestal;
    private Date fechaAtendido;
    private Date fechaSolicitado;
    private Integer idEstatusSurtimiento;
    private String nombreCopia;

    public RepSurtimientoPresc() {
        //No code needed in constructor
    }

    public String getClaveDocumento() {
        return claveDocumento;
    }

    public void setClaveDocumento(String claveDocumento) {
        this.claveDocumento = claveDocumento;
    }

    public String getClaveArticulo() {
        return claveArticulo;
    }

    public void setClaveArticulo(String claveArticulo) {
        this.claveArticulo = claveArticulo;
    }

    public String getUnidadHospitalaria() {
        return unidadHospitalaria;
    }

    public void setUnidadHospitalaria(String unidadHospitalaria) {
        this.unidadHospitalaria = unidadHospitalaria;
    }

    public String getDireccionHospital() {
        return direccionHospital;
    }

    public void setDireccionHospital(String direccionHospital) {
        this.direccionHospital = direccionHospital;
    }

    public String getTituloReceta() {
        return tituloReceta;
    }

    public void setTituloReceta(String tituloReceta) {
        this.tituloReceta = tituloReceta;
    }

    public String getTipoReceta() {
        return tipoReceta;
    }

    public void setTipoReceta(String tipoReceta) {
        this.tipoReceta = tipoReceta;
    }

    public String getFolioPrescripcion() {
        return folioPrescripcion;
    }

    public void setFolioPrescripcion(String folioPrescripcion) {
        this.folioPrescripcion = folioPrescripcion;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getClavePaciente() {
        return clavePaciente;
    }

    public void setClavePaciente(String clavePaciente) {
        this.clavePaciente = clavePaciente;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getCedulaMedico() {
        return cedulaMedico;
    }

    public void setCedulaMedico(String cedulaMedico) {
        this.cedulaMedico = cedulaMedico;
    }

    public String getMedicacion() {
        return medicacion;
    }

    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }

    public String getClasificacionPresupuestal() {
        return clasificacionPresupuestal;
    }

    public void setClasificacionPresupuestal(String clasificacionPresupuestal) {
        this.clasificacionPresupuestal = clasificacionPresupuestal;
    }

    public Date getFechaAtendido() {
        return fechaAtendido;
    }

    public void setFechaAtendido(Date fechaAtendido) {
        this.fechaAtendido = fechaAtendido;
    }

    public Date getFechaSolicitado() {
        return fechaSolicitado;
    }

    public void setFechaSolicitado(Date fechaSolicitado) {
        this.fechaSolicitado = fechaSolicitado;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
    }

    public String getNombreCopia() {
        return nombreCopia;
    }

    public void setNombreCopia(String nombreCopia) {
        this.nombreCopia = nombreCopia;
    }

}
