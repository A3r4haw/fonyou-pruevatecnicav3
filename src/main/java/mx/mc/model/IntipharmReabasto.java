/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mcalderon
 */
public class IntipharmReabasto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String folio;
    private String nombreUsuario;
    private String fecha;
    private String codigoArea;

    private List<IntipharmMedicine> listMedicamentos;

    public IntipharmReabasto() {
    }

    public IntipharmReabasto(String folio, String nombreUsuario, String fecha, String codigoArea,List<IntipharmMedicine> listMedicamentos) {
        this.folio = folio;
        this.nombreUsuario = nombreUsuario;
        this.fecha = fecha;
        this.codigoArea = codigoArea;        
        this.listMedicamentos = listMedicamentos;
    }      

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }
    
    public List<IntipharmMedicine> getListMedicamentos() {
        return listMedicamentos;
    }

    public void setListMedicamentos(List<IntipharmMedicine> listMedicamentos) {
        this.listMedicamentos = listMedicamentos;
    }
    
}
