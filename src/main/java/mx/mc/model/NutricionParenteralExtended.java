package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gcruz
 */
public class NutricionParenteralExtended extends NutricionParenteral implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreEstructura;
    private String nombrePaciente;
    private String ubicacion;
    private String prescribio;
    private String capturoValido;
    private String estatus;
    //private Usuario medico;
    //private Paciente paciente;
    private String claveDerechoHabiencia;
    private String pacienteNumero;
    private String numeroVisita;
    private Date fechaIngreso;
    private List<Diagnostico> listaDiagnosticos;
    private List<HipersensibilidadExtended> listaReaccionesHipersensibilidad;
    private List<ReaccionExtend> listaReacciones;
    private List<NutricionParenteralDetalleExtended> listaMezclaMedicamentos;

    private boolean tempAmbiente;
    private boolean tempRefrigeracion;
    private boolean proteccionLuz;
    private Date fechaNacimiento;
    private String estatusSurtimiento;
    private String estatusPrescripcion;
    private Integer idEstatusSurtimiento;

    public String getNombreEstructura() {
        return nombreEstructura;
    }

    public void setNombreEstructura(String nombreEstructura) {
        this.nombreEstructura = nombreEstructura;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPrescribio() {
        return prescribio;
    }

    public void setPrescribio(String prescribio) {
        this.prescribio = prescribio;
    }

    public String getCapturoValido() {
        return capturoValido;
    }

    public void setCapturoValido(String capturoValido) {
        this.capturoValido = capturoValido;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    /* public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }*/
    public String getClaveDerechoHabiencia() {
        return claveDerechoHabiencia;
    }

    public void setClaveDerechoHabiencia(String claveDerechoHabiencia) {
        this.claveDerechoHabiencia = claveDerechoHabiencia;
    }

    public String getPacienteNumero() {
        return pacienteNumero;
    }

    public void setPacienteNumero(String pacienteNumero) {
        this.pacienteNumero = pacienteNumero;
    }

    public String getNumeroVisita() {
        return numeroVisita;
    }

    public void setNumeroVisita(String numeroVisita) {
        this.numeroVisita = numeroVisita;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }

    public List<HipersensibilidadExtended> getListaReaccionesHipersensibilidad() {
        return listaReaccionesHipersensibilidad;
    }

    public void setListaReaccionesHipersensibilidad(List<HipersensibilidadExtended> listaReaccionesHipersensibilidad) {
        this.listaReaccionesHipersensibilidad = listaReaccionesHipersensibilidad;
    }

    public List<ReaccionExtend> getListaReacciones() {
        return listaReacciones;
    }

    public void setListaReacciones(List<ReaccionExtend> listaReacciones) {
        this.listaReacciones = listaReacciones;
    }

    public List<NutricionParenteralDetalleExtended> getListaMezclaMedicamentos() {
        return listaMezclaMedicamentos;
    }

    public void setListaMezclaMedicamentos(List<NutricionParenteralDetalleExtended> listaMezclaMedicamentos) {
        this.listaMezclaMedicamentos = listaMezclaMedicamentos;
    }

    public boolean isTempAmbiente() {
        return tempAmbiente;
    }

    public void setTempAmbiente(boolean tempAmbiente) {
        this.tempAmbiente = tempAmbiente;
    }

    public boolean isTempRefrigeracion() {
        return tempRefrigeracion;
    }

    public void setTempRefrigeracion(boolean tempRefrigeracion) {
        this.tempRefrigeracion = tempRefrigeracion;
    }

    public boolean isProteccionLuz() {
        return proteccionLuz;
    }

    public void setProteccionLuz(boolean proteccionLuz) {
        this.proteccionLuz = proteccionLuz;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstatusSurtimiento() {
        return estatusSurtimiento;
    }

    public void setEstatusSurtimiento(String estatusSurtimiento) {
        this.estatusSurtimiento = estatusSurtimiento;
    }

    public String getEstatusPrescripcion() {
        return estatusPrescripcion;
    }

    public void setEstatusPrescripcion(String estatusPrescripcion) {
        this.estatusPrescripcion = estatusPrescripcion;
    }

    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
    }

}
