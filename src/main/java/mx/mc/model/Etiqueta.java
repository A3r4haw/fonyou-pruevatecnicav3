package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author aortiz
 */
public class Etiqueta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombrePaciente;
    private String numeroPaciente;
    private String clavePaciente;
    private String titulo;
    private String fechaHora;
    private String ipImpresora;
    
    public Etiqueta(){}
    
    public Etiqueta(String nombrePaciente, String numeroPaciente, String clavePaciente, String titulo, String fechaHora) {
        this.nombrePaciente = nombrePaciente;
        this.numeroPaciente = numeroPaciente;
        this.clavePaciente = clavePaciente;
        this.titulo = titulo;
        this.fechaHora = fechaHora;
    }

    public Etiqueta(String nombrePaciente, String numeroPaciente, String clavePaciente, String titulo, String fechaHora, String ipImpresora) {
        this.nombrePaciente = nombrePaciente;
        this.numeroPaciente = numeroPaciente;
        this.clavePaciente = clavePaciente;
        this.titulo = titulo;
        this.fechaHora = fechaHora;
        this.ipImpresora = ipImpresora;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public String getClavePaciente() {
        return clavePaciente;
    }

    public void setClavePaciente(String clavePaciente) {
        this.clavePaciente = clavePaciente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getIpImpresora() {
        return ipImpresora;
    }

    public void setIpImpresora(String ipImpresora) {
        this.ipImpresora = ipImpresora;
    }
    
    
}
