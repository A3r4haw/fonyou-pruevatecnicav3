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
public class EntidadHospitalariaExtended extends EntidadHospitalaria{

    private static final long serialVersionUID = 1L;
    
    private String clavePresupuestal;
    private String centroCostos;
    
    public EntidadHospitalariaExtended() {
        //No code needed in constructor
    }

    public String getClavePresupuestal() {
        return clavePresupuestal;
    }

    public void setClavePresupuestal(String clavePresupuestal) {
        this.clavePresupuestal = clavePresupuestal;
    }

    public String getCentroCostos() {
        return centroCostos;
    }

    public void setCentroCostos(String centroCostos) {
        this.centroCostos = centroCostos;
    }
    
    
}
