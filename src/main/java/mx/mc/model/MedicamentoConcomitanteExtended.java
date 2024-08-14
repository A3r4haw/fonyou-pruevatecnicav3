/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

/**
 *
 * @author bbautista
 */
public class MedicamentoConcomitanteExtended  extends MedicamentoConcomitante{
    private String medicamento;
    private String viaAdministracion;
    private String motivoPrescripcion;

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getViaAdministracion() {
        return viaAdministracion;
    }

    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }

    public String getMotivoPrescripcion() {
        return motivoPrescripcion;
    }

    public void setMotivoPrescripcion(String motivoPrescripcion) {
        this.motivoPrescripcion = motivoPrescripcion;
    }
    
    
}
