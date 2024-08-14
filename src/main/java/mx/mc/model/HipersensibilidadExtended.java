/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

/**
 *
 * @author gcruz
 */
public class HipersensibilidadExtended extends Hipersensibilidad {

    private static final long serialVersionUID = 1L;
    
    public String nombrePaciente;
    public String tipoAlergia;
    public String nombreAlteracion;
    public String nombreRegistro;
    public String idMedicamento;

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getTipoAlergia() {
        return tipoAlergia;
    }

    public void setTipoAlergia(String tipoAlergia) {
        this.tipoAlergia = tipoAlergia;
    }

    public String getNombreAlteracion() {
        return nombreAlteracion;
    }

    public void setNombreAlteracion(String nombreAlteracion) {
        this.nombreAlteracion = nombreAlteracion;
    }

    public String getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(String nombreRegistro) {
        this.nombreRegistro = nombreRegistro;
    }   

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }
    
}
