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
 * @author gcruz
 */
public class Alteracion  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idAlteracion;
    private String nombreAlteracion;
    private Integer activa;
    private Date insertFecha;
    private String insertIdUsuario;    
    private Date updateFecha;
    private String updateIdUsuario;
    
    public Alteracion() {
        
    }

    public Alteracion(Integer idAlteracion, String nombreAlteracion, Integer activa, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idAlteracion = idAlteracion;
        this.nombreAlteracion = nombreAlteracion;
        this.activa = activa;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idAlteracion);
        hash = 37 * hash + Objects.hashCode(this.nombreAlteracion);
        hash = 37 * hash + Objects.hashCode(this.activa);
        hash = 37 * hash + Objects.hashCode(this.insertFecha);
        hash = 37 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 37 * hash + Objects.hashCode(this.updateFecha);
        hash = 37 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Alteracion other = (Alteracion) obj;
        if (!Objects.equals(this.nombreAlteracion, other.nombreAlteracion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idAlteracion, other.idAlteracion)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Alteracion{" + "idAlteracion=" + idAlteracion + ", nombreAlteracion=" + nombreAlteracion + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public Integer getIdAlteracion() {
        return idAlteracion;
    }

    public void setIdAlteracion(Integer idAlteracion) {
        this.idAlteracion = idAlteracion;
    }

    public String getNombreAlteracion() {
        return nombreAlteracion;
    }

    public void setNombreAlteracion(String nombreAlteracion) {
        this.nombreAlteracion = nombreAlteracion;
    }

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
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
    
}
