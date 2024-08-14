/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author gcruz
 */
public class IndTerapeuticaExtended extends IndTerapeutica implements Serializable {

    private static final long serialVersionUID = 1L;

    private String claveInstitucional;
    private String nombreMedicamento;
    private String nombreDiagnostico;
    private String tipoPaciente;
    private String origen;
    private Integer rangoInf;
    private Integer rangoSup;

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getNombreDiagnostico() {
        return nombreDiagnostico;
    }

    public void setNombreDiagnostico(String nombreDiagnostico) {
        this.nombreDiagnostico = nombreDiagnostico;
    }

    public String getTipoPaciente() {
        return tipoPaciente;
    }

    public void setTipoPaciente(String tipoPaciente) {
        this.tipoPaciente = tipoPaciente;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getRangoInf() {
        return rangoInf;
    }

    public void setRangoInf(Integer rangoInf) {
        this.rangoInf = rangoInf;
    }

    public Integer getRangoSup() {
        return rangoSup;
    }

    public void setRangoSup(Integer rangoSup) {
        this.rangoSup = rangoSup;
    }

}
