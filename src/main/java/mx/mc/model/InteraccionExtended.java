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
public class InteraccionExtended extends Interaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String medicamento;
    private String medicamentoInteraccion;
    private String tipoInteraccion;
    private String nombreEmisor;
    private String claveMedicamentoUno;
    private String claveMedicamentoDos;

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getMedicamentoInteraccion() {
        return medicamentoInteraccion;
    }

    public void setMedicamentoInteraccion(String medicamentoInteraccion) {
        this.medicamentoInteraccion = medicamentoInteraccion;
    }

    public String getTipoInteraccion() {
        return tipoInteraccion;
    }

    public void setTipoInteraccion(String tipoInteraccion) {
        this.tipoInteraccion = tipoInteraccion;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getClaveMedicamentoUno() {
        return claveMedicamentoUno;
    }

    public void setClaveMedicamentoUno(String claveMedicamentoUno) {
        this.claveMedicamentoUno = claveMedicamentoUno;
    }

    public String getClaveMedicamentoDos() {
        return claveMedicamentoDos;
    }

    public void setClaveMedicamentoDos(String claveMedicamentoDos) {
        this.claveMedicamentoDos = claveMedicamentoDos;
    }                
    
}
