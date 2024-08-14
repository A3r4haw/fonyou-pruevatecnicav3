/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class HipersensibilidadAdjunto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idHipersensibilidad;
    private Integer idAdjunto;
    
    public HipersensibilidadAdjunto() {
        
    }

    public HipersensibilidadAdjunto(String idHipersensibilidad, Integer idAdjunto) {
        this.idHipersensibilidad = idHipersensibilidad;
        this.idAdjunto = idAdjunto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idHipersensibilidad);
        hash = 79 * hash + Objects.hashCode(this.idAdjunto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HipersensibilidadAdjunto other = (HipersensibilidadAdjunto) obj;
        if (!Objects.equals(this.idHipersensibilidad, other.idHipersensibilidad)) {
            return false;
        }
        if (!Objects.equals(this.idAdjunto, other.idAdjunto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HipersensibilidadAdjunto{" + "idHipersensibilidad=" + idHipersensibilidad + ", idAdjunto=" + idAdjunto + '}';
    }

    public String getIdHipersensibilidad() {
        return idHipersensibilidad;
    }

    public void setIdHipersensibilidad(String idHipersensibilidad) {
        this.idHipersensibilidad = idHipersensibilidad;
    }

    public Integer getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Integer idAdjunto) {
        this.idAdjunto = idAdjunto;
    }
        
}
