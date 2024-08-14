/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;

/**
 *
 * @author mcalderon
 */
public class SurtimientoMinistrado_Extend extends SurtimientoMinistrado{

    private static final long serialVersionUID = 1L;
    private String folio;
    private Date fechaPrescripcion;
    private String pacienteNumero;
    private String nombrePaciente;
    private String cedProfesional; 
    private String cedEspecialidad;
    private String nombreMedico;
    private String claveInstitucional;
    private String nombreCorto;
    private Date fechaInicioTratamiento;
    private String nombrePresentacion;
    private int concentracion;
    private String nombreUnidadConcentracion;
    private int frecuencia;
    private int duracion;
    private String lote;
    private Date fechaCaducidad;
    private String estatus;
    private String nombreUsuarioMinistro;
    private String nombre;
    private String idInventario;
    private Integer idViaAdministracion;
    private String idSurtimientoInsumo;
    private String idPrescripcionInsumo;
    private String idPrescripcion;
    private String idPacienteServicio;
    private String idVisita;
    private String idPaciente;
    private String idInsumo;
    private String idUsuario;
    private String nombreFabricante;

    public SurtimientoMinistrado_Extend() {
        //No code needed in constructor
    }

    @Override
    public String toString() {
        return "SurtimientoMinistrado_Extend{" + "folio=" + folio + ", fechaPrescripcion=" + fechaPrescripcion + ", pacienteNumero=" + pacienteNumero + ", nombrePaciente=" + nombrePaciente + ", cedProfesional=" + cedProfesional + ", cedEspecialidad=" + cedEspecialidad + ", nombreMedico=" + nombreMedico + ", claveInstitucional=" + claveInstitucional + ", nombreCorto=" + nombreCorto + ", fechaInicioTratamiento=" + fechaInicioTratamiento + ", nombrePresentacion=" + nombrePresentacion + ", concentracion=" + concentracion + ", nombreUnidadConcentracion=" + nombreUnidadConcentracion + ", frecuencia=" + frecuencia + ", duracion=" + duracion + ", lote=" + lote + ", fechaCaducidad=" + fechaCaducidad + ", estatus=" + estatus + ", nombreUsuarioMinistro=" + nombreUsuarioMinistro + ", nombre=" + nombre + ", nombreFabricante=" + nombreFabricante + '}';
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaPrescripcion() {
        return fechaPrescripcion;
    }

    public void setFechaPrescripcion(Date fechaPrescripcion) {
        this.fechaPrescripcion = fechaPrescripcion;
    }

    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getCedProfesional() {
        return cedProfesional;
    }

    public void setCedProfesional(String cedProfesional) {
        this.cedProfesional = cedProfesional;
    }

    public String getCedEspecialidad() {
        return cedEspecialidad;
    }

    public void setCedEspecialidad(String cedEspecialidad) {
        this.cedEspecialidad = cedEspecialidad;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public Date getFechaInicioTratamiento() {
        return fechaInicioTratamiento;
    }

    public void setFechaInicioTratamiento(Date fechaInicioTratamiento) {
        this.fechaInicioTratamiento = fechaInicioTratamiento;
    }

    public String getNombrePresentacion() {
        return nombrePresentacion;
    }

    public void setNombrePresentacion(String nombrePresentacion) {
        this.nombrePresentacion = nombrePresentacion;
    }

    public int getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(int concentracion) {
        this.concentracion = concentracion;
    }

    public String getNombreUnidadConcentracion() {
        return nombreUnidadConcentracion;
    }

    public void setNombreUnidadConcentracion(String nombreUnidadConcentracion) {
        this.nombreUnidadConcentracion = nombreUnidadConcentracion;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getNombreUsuarioMinistro() {
        return nombreUsuarioMinistro;
    }

    public void setNombreUsuarioMinistro(String nombreUsuarioMinistro) {
        this.nombreUsuarioMinistro = nombreUsuarioMinistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }        

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getIdSurtimientoInsumo() {
        return idSurtimientoInsumo;
    }

    public void setIdSurtimientoInsumo(String idSurtimientoInsumo) {
        this.idSurtimientoInsumo = idSurtimientoInsumo;
    }

    public String getIdPrescripcionInsumo() {
        return idPrescripcionInsumo;
    }

    public void setIdPrescripcionInsumo(String idPrescripcionInsumo) {
        this.idPrescripcionInsumo = idPrescripcionInsumo;
    }

    public String getIdPrescripcion() {
        return idPrescripcion;
    }

    public void setIdPrescripcion(String idPrescripcion) {
        this.idPrescripcion = idPrescripcion;
    }

    public String getIdPacienteServicio() {
        return idPacienteServicio;
    }

    public void setIdPacienteServicio(String idPacienteServicio) {
        this.idPacienteServicio = idPacienteServicio;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getNombreFabricante() {
        return nombreFabricante;
    }

    public void setNombreFabricante(String nombreFabricante) {
        this.nombreFabricante = nombreFabricante;
    }
    
}
