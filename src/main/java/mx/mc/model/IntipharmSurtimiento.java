/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mcalderon
 */
@XmlRootElement(name = "intipharmSurtimiento")
public class IntipharmSurtimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String folioSurtimiento;
    private String numeroPaciente;
    private String nombrePaciente;
    private String nombreUsuarioDispensacion;
    private String prescriptionType;
    private List<IntipharmPrescriptionDispense> listPrescriptions;

    public IntipharmSurtimiento() {
    }

    public IntipharmSurtimiento(String folioSurtimiento, String numeroPaciente, String nombrePaciente, String nombreUsuarioDispensacion, List<IntipharmPrescriptionDispense> listPrescriptions) {
        this.folioSurtimiento = folioSurtimiento;
        this.numeroPaciente = numeroPaciente;
        this.nombrePaciente = nombrePaciente;
        this.nombreUsuarioDispensacion = nombreUsuarioDispensacion;
        this.listPrescriptions = listPrescriptions;
    }

    public String getFolioSurtimiento() {
        return folioSurtimiento;
    }

    public void setFolioSurtimiento(String folioSurtimiento) {
        this.folioSurtimiento = folioSurtimiento;
    }

    public String getNumeroPaciente() {
        return numeroPaciente;
    }

    public void setNumeroPaciente(String numeroPaciente) {
        this.numeroPaciente = numeroPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getNombreUsuarioDispensacion() {
        return nombreUsuarioDispensacion;
    }

    public void setNombreUsuarioDispensacion(String nombreUsuarioDispensacion) {
        this.nombreUsuarioDispensacion = nombreUsuarioDispensacion;
    }

    public List<IntipharmPrescriptionDispense> getListPrescriptions() {
        return listPrescriptions;
    }

    public void setListPrescriptions(List<IntipharmPrescriptionDispense> listPrescriptions) {
        this.listPrescriptions = listPrescriptions;
    }

    public String getPrescriptionType() {
        return prescriptionType;
    }

    public void setPrescriptionType(String prescriptionType) {
        this.prescriptionType = prescriptionType;
    }
   
}
