/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;
import java.util.Objects;
import java.io.Serializable;

/**
 *
 * @author bbautista
 */
public class SubcategoriaMedicamento implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Integer idSubcategoriaMedicamento;
    private Integer idCategoriaMedicamento;
    private String nombreSubcategoriaMedicamento;
    private Integer activa;
    private Date insertFecha;
    private String insertUsuarioId;
    private Date updateFecha;
    private String updateUsuarioId;

    public SubcategoriaMedicamento() {
        //No code needed in constructor
    }

    @Override
    public String toString() {
        return "SubcategoriaMedicamento{" + "idSubcategoriaMedicamento=" + idSubcategoriaMedicamento + ", idCategoriaMedicamento=" + idCategoriaMedicamento + ", nombreSubcategoriaMedicamento=" + nombreSubcategoriaMedicamento + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertUsuarioId=" + insertUsuarioId + ", updateFecha=" + updateFecha + ", updateUsuarioId=" + updateUsuarioId + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idSubcategoriaMedicamento);
        hash = 83 * hash + Objects.hashCode(this.idCategoriaMedicamento);
        hash = 83 * hash + Objects.hashCode(this.nombreSubcategoriaMedicamento);
        hash = 83 * hash + Objects.hashCode(this.activa);
        hash = 83 * hash + Objects.hashCode(this.insertFecha);
        hash = 83 * hash + Objects.hashCode(this.insertUsuarioId);
        hash = 83 * hash + Objects.hashCode(this.updateFecha);
        hash = 83 * hash + Objects.hashCode(this.updateUsuarioId);
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
        final SubcategoriaMedicamento other = (SubcategoriaMedicamento) obj;
        if (!Objects.equals(this.nombreSubcategoriaMedicamento, other.nombreSubcategoriaMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.insertUsuarioId, other.insertUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.updateUsuarioId, other.updateUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.idSubcategoriaMedicamento, other.idSubcategoriaMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.idCategoriaMedicamento, other.idCategoriaMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    public Integer getIdSubcategoriaMedicamento() {
        return idSubcategoriaMedicamento;
    }

    public void setIdSubcategoriaMedicamento(Integer idSubcategoriaMedicamento) {
        this.idSubcategoriaMedicamento = idSubcategoriaMedicamento;
    }

    public Integer getIdCategoriaMedicamento() {
        return idCategoriaMedicamento;
    }

    public void setIdCategoriaMedicamento(Integer idCategoriaMedicamento) {
        this.idCategoriaMedicamento = idCategoriaMedicamento;
    }

    

    public String getNombreSubcategoriaMedicamento() {
        return nombreSubcategoriaMedicamento;
    }

    public void setNombreSubcategoriaMedicamento(String nombreSubcategoriaMedicamento) {
        this.nombreSubcategoriaMedicamento = nombreSubcategoriaMedicamento;
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

    public String getInsertUsuarioId() {
        return insertUsuarioId;
    }

    public void setInsertUsuarioId(String insertUsuarioId) {
        this.insertUsuarioId = insertUsuarioId;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    public String getUpdateUsuarioId() {
        return updateUsuarioId;
    }

    public void setUpdateUsuarioId(String updateUsuarioId) {
        this.updateUsuarioId = updateUsuarioId;
    }
    
    
    
}
