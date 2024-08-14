package mx.mc.model;

import java.util.Date;

/**
 * @author AORTIZ
 */
public class Prescripcion_Extended extends Prescripcion {

    private static final long serialVersionUID = 1L;

    private String estatusPrescripcion;
    private String nombreMedico;
    private String appMedico;
    private String apmMedico;
    private String nombrePaciente;
    private String appPaciente;
    private String apmPaciente;
    private String ubicacion;
    private String idPaciente;
    private String folioSurtimiento;
    private Date fechaProgramada;
    private String pacienteNumero;

    public String getEstatusPrescripcion() {
        return estatusPrescripcion;
    }

    public void setEstatusPrescripcion(String estatusPrescripcion) {
        this.estatusPrescripcion = estatusPrescripcion;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getAppMedico() {
        return appMedico;
    }

    public void setAppMedico(String appMedico) {
        this.appMedico = appMedico;
    }

    public String getApmMedico() {
        return apmMedico;
    }

    public void setApmMedico(String apmMedico) {
        this.apmMedico = apmMedico;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getAppPaciente() {
        return appPaciente;
    }

    public void setAppPaciente(String appPaciente) {
        this.appPaciente = appPaciente;
    }

    public String getApmPaciente() {
        return apmPaciente;
    }

    public void setApmPaciente(String apmPaciente) {
        this.apmPaciente = apmPaciente;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(String idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    

}
