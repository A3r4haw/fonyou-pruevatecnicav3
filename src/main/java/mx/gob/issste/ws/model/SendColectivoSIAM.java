/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.issste.ws.model;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbProperty;

/**
 *
 * @author Admin
 */
public class SendColectivoSIAM implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @JsonbProperty("tipoInsumo")
    private String tipoInsumo;
    
    @JsonbProperty("especialidad")
    private String especialidad;
    
    @JsonbProperty("appQueSolicita")
    private String appQueSolicita;
    
    @JsonbProperty("claveCentroTrabajo")
    private String claveCentroTrabajo;
        
    @JsonbProperty("folioExterno")
    private String folioExterno;
    
    @JsonbProperty("nombreUsuario")
    private String nombreUsuario;
    
    @JsonbProperty("insumos")
    private List<InsumoSIAM> insumos;

    public String getFolioExterno() {
        return folioExterno;
    }

    public void setFolioExterno(String folioExterno) {
        this.folioExterno = folioExterno;
    }

    public List<InsumoSIAM> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<InsumoSIAM> insumos) {
        this.insumos = insumos;
    }

    public String getTipoInsumo() {
        return tipoInsumo;
    }

    public void setTipoInsumo(String tipoInsumo) {
        this.tipoInsumo = tipoInsumo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getAppQueSolicita() {
        return appQueSolicita;
    }

    public void setAppQueSolicita(String appQueSolicita) {
        this.appQueSolicita = appQueSolicita;
    }

    public String getClaveCentroTrabajo() {
        return claveCentroTrabajo;
    }

    public void setClaveCentroTrabajo(String claveCentroTrabajo) {
        this.claveCentroTrabajo = claveCentroTrabajo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

   
    
    
}
