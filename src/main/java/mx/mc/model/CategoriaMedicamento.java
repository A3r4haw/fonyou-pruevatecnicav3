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
public class CategoriaMedicamento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer     idCategoriaMedicamento;
    private String  nombreCategoriaMedicamento;
    private Integer     activa;
    private Date    insertFecha;
    private String     insertUsuarioId;
    private Date    updateFecha;
    private String     updateUsuarioId;

    public CategoriaMedicamento(){

    }

    public CategoriaMedicamento(Integer idCategoriaMedicamento, String nombreCategoriaMedicamento, Integer activa, Date insertFecha, String insertUsuarioId, Date updateFecha, String updateUsuarioId) {
        this.idCategoriaMedicamento = idCategoriaMedicamento;
        this.nombreCategoriaMedicamento = nombreCategoriaMedicamento;
        this.activa = activa;
        this.insertFecha = insertFecha;
        this.insertUsuarioId = insertUsuarioId;
        this.updateFecha = updateFecha;
        this.updateUsuarioId = updateUsuarioId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idCategoriaMedicamento);
        hash = 79 * hash + Objects.hashCode(this.nombreCategoriaMedicamento);
        hash = 79 * hash + Objects.hashCode(this.activa);
        hash = 79 * hash + Objects.hashCode(this.insertFecha);
        hash = 79 * hash + Objects.hashCode(this.insertUsuarioId);
        hash = 79 * hash + Objects.hashCode(this.updateFecha);
        hash = 79 * hash + Objects.hashCode(this.updateUsuarioId);
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
        final CategoriaMedicamento other = (CategoriaMedicamento) obj;
        if (!Objects.equals(this.idCategoriaMedicamento, other.idCategoriaMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertUsuarioId, other.insertUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.updateUsuarioId, other.updateUsuarioId)) {
            return false;
        }
        if (!Objects.equals(this.nombreCategoriaMedicamento, other.nombreCategoriaMedicamento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
    
    @Override
    public String toString() {
        return "CategoriaMedicamento{" + "idCategoriaMedicamento=" + idCategoriaMedicamento + ", nombreCategoriaMedicamento=" + nombreCategoriaMedicamento + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertUsuarioId=" + insertUsuarioId + ", updateFecha=" + updateFecha + ", updateUsuarioId=" + updateUsuarioId + '}';
    }

    public Integer getIdCategoriaMedicamento() {
        return idCategoriaMedicamento;
    }

    public void setIdCategoriaMedicamento(Integer idCategoriaMedicamento) {
        this.idCategoriaMedicamento = idCategoriaMedicamento;
    }

    public String getNombreCategoriaMedicamento() {
        return nombreCategoriaMedicamento;
    }

    public void setNombreCategoriaMedicamento(String nombreCategoriaMedicamento) {
        this.nombreCategoriaMedicamento = nombreCategoriaMedicamento;
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
