/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author mcalderon
 */
public class EntidadHospitalaria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idEntidadHospitalaria;
    private String nombre;
    private boolean estatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private String domicilio;
    private String claveEntidad;
    private String codigoUmf;
    
    public EntidadHospitalaria(){
        //No code needed in constructor
    }

    public String getIdEntidadHospitalaria() {
        return idEntidadHospitalaria;
    }

    public void setIdEntidadHospitalaria(String idEntidadHospitalaria) {
        this.idEntidadHospitalaria = idEntidadHospitalaria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getClaveEntidad() {
        return claveEntidad;
    }

    public void setClaveEntidad(String claveEntidad) {
        this.claveEntidad = claveEntidad;
    }

    public String getCodigoUmf() {
        return codigoUmf;
    }

    public void setCodigoUmf(String codigoUmf) {
        this.codigoUmf = codigoUmf;
    }

    @Override
    public String toString() {
        return "EntidadHospitalaria{" + "idEntidadHospitalaria=" + idEntidadHospitalaria + ", nombre=" + nombre + ", estatus=" + estatus + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", domicilio=" + domicilio + ", claveEntidad=" + claveEntidad + ", codigoUmf=" + codigoUmf + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.idEntidadHospitalaria);
        hash = 13 * hash + Objects.hashCode(this.nombre);
        hash = 13 * hash + Objects.hashCode(this.estatus);
        hash = 13 * hash + Objects.hashCode(this.insertFecha);
        hash = 13 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 13 * hash + Objects.hashCode(this.updateFecha);
        hash = 13 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 13 * hash + Objects.hashCode(this.domicilio);
        hash = 13 * hash + Objects.hashCode(this.claveEntidad);
        hash = 13 * hash + Objects.hashCode(this.codigoUmf);
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
        final EntidadHospitalaria other = (EntidadHospitalaria) obj;
        if (!Objects.equals(this.idEntidadHospitalaria, other.idEntidadHospitalaria)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.domicilio, other.domicilio)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.claveEntidad)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.codigoUmf);
    }
}
