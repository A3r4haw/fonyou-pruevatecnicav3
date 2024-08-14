/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author aortiz
 */
public class MotivoPacienteMovimiento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer idmotivoPacienteMovimiento;
    private String nombreMotivoPacienteMovimiento;
    private String tipoMovimiento;
    private Integer activo;

    public MotivoPacienteMovimiento() {
    }

    public MotivoPacienteMovimiento(Integer idmotivoPacienteMovimiento, String nombreMotivoPacienteMovimiento, String tipoMovimiento, Integer activo) {
        this.idmotivoPacienteMovimiento = idmotivoPacienteMovimiento;
        this.nombreMotivoPacienteMovimiento = nombreMotivoPacienteMovimiento;
        this.tipoMovimiento = tipoMovimiento;
        this.activo = activo;
    }

    public Integer getIdmotivoPacienteMovimiento() {
        return idmotivoPacienteMovimiento;
    }

    public void setIdmotivoPacienteMovimiento(Integer idmotivoPacienteMovimiento) {
        this.idmotivoPacienteMovimiento = idmotivoPacienteMovimiento;
    }

    public String getNombreMotivoPacienteMovimiento() {
        return nombreMotivoPacienteMovimiento;
    }

    public void setNombreMotivoPacienteMovimiento(String nombreMotivoPacienteMovimiento) {
        this.nombreMotivoPacienteMovimiento = nombreMotivoPacienteMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    
}
