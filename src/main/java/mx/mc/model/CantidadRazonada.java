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
 * @author bbautista
 */
public class CantidadRazonada  implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String idcantidadRazonada;
    private String claveInstitucional;
    private Integer cantidadPresentacionComercial;
    private Integer periodoPresentacionComercial;
    private Integer cantidadDosisUnitaria;
    private Integer periodoDosisUnitaira;
    private Integer restrictiva;
    private String comentarios;
    private boolean activo;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;
    private String idInsumo;

    public CantidadRazonada() {
        //No code needed in constructor
    }

    @Override
    public String toString() {
        return "CantidadRazonada{" + "idcantidadRazonada=" + idcantidadRazonada + ", claveInstitucional=" + claveInstitucional + ", cantidadPresentacionComercial=" + cantidadPresentacionComercial + ", periodoPresentacionComercial=" + periodoPresentacionComercial + ", cantidadDosisUnitaria=" + cantidadDosisUnitaria + ", periodoDosisUnitaira=" + periodoDosisUnitaira + ", restrictiva=" + restrictiva + ", comentarios=" + comentarios + ", activo=" + activo + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + ", idInsumo=" + idInsumo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idcantidadRazonada);
        hash = 17 * hash + Objects.hashCode(this.claveInstitucional);
        hash = 17 * hash + Objects.hashCode(this.cantidadPresentacionComercial);
        hash = 17 * hash + Objects.hashCode(this.periodoPresentacionComercial);
        hash = 17 * hash + Objects.hashCode(this.cantidadDosisUnitaria);
        hash = 17 * hash + Objects.hashCode(this.periodoDosisUnitaira);
        hash = 17 * hash + Objects.hashCode(this.restrictiva);
        hash = 17 * hash + Objects.hashCode(this.comentarios);
        hash = 17 * hash + (this.activo ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 17 * hash + Objects.hashCode(this.insertFecha);
        hash = 17 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 17 * hash + Objects.hashCode(this.updateFecha);
        hash = 17 * hash + Objects.hashCode(this.idInsumo);
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
        final CantidadRazonada other = (CantidadRazonada) obj;
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.idcantidadRazonada, other.idcantidadRazonada)) {
            return false;
        }
        if (!Objects.equals(this.claveInstitucional, other.claveInstitucional)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.cantidadPresentacionComercial, other.cantidadPresentacionComercial)) {
            return false;
        }
        if (!Objects.equals(this.periodoPresentacionComercial, other.periodoPresentacionComercial)) {
            return false;
        }
        if (!Objects.equals(this.cantidadDosisUnitaria, other.cantidadDosisUnitaria)) {
            return false;
        }
        if (!Objects.equals(this.periodoDosisUnitaira, other.periodoDosisUnitaira)) {
            return false;
        }
        if (!Objects.equals(this.restrictiva, other.restrictiva)) {
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
   
    public String getIdcantidadRazonada() {
        return idcantidadRazonada;
    }

    public void setIdcantidadRazonada(String idCantidadRazonada) {
        this.idcantidadRazonada = idCantidadRazonada;
    }

    public String getClaveInstitucional() {
        return claveInstitucional;
    }

    public void setClaveInstitucional(String claveInstitucional) {
        this.claveInstitucional = claveInstitucional;
    }

    public Integer getCantidadPresentacionComercial() {
        return cantidadPresentacionComercial;
    }

    public void setCantidadPresentacionComercial(Integer cantidadPresentacionComercial) {
        this.cantidadPresentacionComercial = cantidadPresentacionComercial;
    }

    public Integer getPeriodoPresentacionComercial() {
        return periodoPresentacionComercial;
    }

    public void setPeriodoPresentacionComercial(Integer periodoPresentacionComercial) {
        this.periodoPresentacionComercial = periodoPresentacionComercial;
    }

    public Integer getCantidadDosisUnitaria() {
        return cantidadDosisUnitaria;
    }

    public void setCantidadDosisUnitaria(Integer cantidadDosisUnitaria) {
        this.cantidadDosisUnitaria = cantidadDosisUnitaria;
    }

    public Integer getPeriodoDosisUnitaira() {
        return periodoDosisUnitaira;
    }

    public void setPeriodoDosisUnitaira(Integer periodoDosisUnitaira) {
        this.periodoDosisUnitaira = periodoDosisUnitaira;
    }

    public Integer getRestrictiva() {
        return restrictiva;
    }

    public void setRestrictiva(Integer restrictiva) {
        this.restrictiva = restrictiva;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }   

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }        
    
}
