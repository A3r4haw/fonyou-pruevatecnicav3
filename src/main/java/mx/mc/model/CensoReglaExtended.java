/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.List;

/**
 *
 * @author apalacios
 */
public class CensoReglaExtended extends CensoRegla {

    private static final long serialVersionUID = 1L;
    private String claveInstitucional;
    private String nombreMedicamento;
    private String claveDiagnostico;
    private String nombreDiagnostico;
    private List<CensoReglaDetalle> listaCensoReglaDetalle;

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

    public String getClaveDiagnostico() {
        return claveDiagnostico;
    }

    public void setClaveDiagnostico(String claveDiagnostico) {
        this.claveDiagnostico = claveDiagnostico;
    }

    public String getNombreDiagnostico() {
        return nombreDiagnostico;
    }

    public void setNombreDiagnostico(String nombreDiagnostico) {
        this.nombreDiagnostico = nombreDiagnostico;
    }

    public List<CensoReglaDetalle> getListaCensoReglaDetalle() {
        return listaCensoReglaDetalle;
    }

    public void setListaCensoReglaDetalle(List<CensoReglaDetalle> listaCensoReglaDetalle) {
        this.listaCensoReglaDetalle = listaCensoReglaDetalle;
    }
}
