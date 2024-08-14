/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class IndTerapeutica implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idIndTerapeutica;
    private String idInsumo;
    private String idDiagnostico;
    private Integer idTipoEdadPaciente;
    private BigDecimal dosisMin;
    private BigDecimal dosisMax;
    private Integer idUnidad;
    private Integer frecuenciaInferior;
    private Integer frecuenciaSuperior;
    private Integer duracionMinima;
    private Integer duracionMaxima;
    private Integer idViaAdministracion;
    private String descripcion;
    private Integer restrictiva;
    private Integer idEstatus;
    private String riesgo;
    private String insertIdUsuario;
    private Date insertFecha;
    private Date updateFecha;
    private String updateIdUsuario;

    public IndTerapeutica() {
        
    }

    public IndTerapeutica(String idIndTerapeutica, String idInsumo, String idDiagnostico, Integer idTipoEdadPaciente, BigDecimal dosisMin, BigDecimal dosisMax, Integer idUnidad, Integer frecuenciaInferior, Integer frecuenciaSuperior, Integer duracionMinima, Integer duracionMaxima, Integer idViaAdministracion, String descripcion, Integer restrictiva, Integer idEstatus, String riesgo, String insertIdUsuario, Date insertFecha, Date updateFecha, String updateIdUsuario) {
        this.idIndTerapeutica = idIndTerapeutica;
        this.idInsumo = idInsumo;
        this.idDiagnostico = idDiagnostico;
        this.idTipoEdadPaciente = idTipoEdadPaciente;
        this.dosisMin = dosisMin;
        this.dosisMax = dosisMax;
        this.idUnidad = idUnidad;
        this.frecuenciaInferior = frecuenciaInferior;
        this.frecuenciaSuperior = frecuenciaSuperior;
        this.duracionMinima = duracionMinima;
        this.duracionMaxima = duracionMaxima;
        this.descripcion = descripcion;
        this.restrictiva = restrictiva;
        this.idEstatus = idEstatus;
        this.riesgo = riesgo;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.idViaAdministracion = idViaAdministracion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idIndTerapeutica);
        hash = 67 * hash + Objects.hashCode(this.idInsumo);
        hash = 67 * hash + Objects.hashCode(this.idDiagnostico);
        hash = 67 * hash + Objects.hashCode(this.idTipoEdadPaciente);
        hash = 67 * hash + Objects.hashCode(this.dosisMin);
        hash = 67 * hash + Objects.hashCode(this.dosisMax);
        hash = 67 * hash + Objects.hashCode(this.idUnidad);
        hash = 67 * hash + Objects.hashCode(this.frecuenciaInferior);
        hash = 67 * hash + Objects.hashCode(this.frecuenciaSuperior);
        hash = 67 * hash + Objects.hashCode(this.duracionMinima);
        hash = 67 * hash + Objects.hashCode(this.duracionMaxima);
        hash = 67 * hash + Objects.hashCode(this.idViaAdministracion);
        hash = 67 * hash + Objects.hashCode(this.descripcion);
        hash = 67 * hash + Objects.hashCode(this.restrictiva);
        hash = 67 * hash + Objects.hashCode(this.idEstatus);
        hash = 67 * hash + Objects.hashCode(this.riesgo);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final IndTerapeutica other = (IndTerapeutica) obj;
        if (!Objects.equals(this.idIndTerapeutica, other.idIndTerapeutica)) {
            return false;
        }
        if (!Objects.equals(this.idInsumo, other.idInsumo)) {
            return false;
        }
        if (!Objects.equals(this.idDiagnostico, other.idDiagnostico)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idTipoEdadPaciente, other.idTipoEdadPaciente)) {
            return false;
        }
        if (!Objects.equals(this.dosisMin, other.dosisMin)) {
            return false;
        }
        if (!Objects.equals(this.dosisMax, other.dosisMax)) {
            return false;
        }
        if (!Objects.equals(this.idUnidad, other.idUnidad)) {
            return false;
        }
        if (!Objects.equals(this.frecuenciaInferior, other.frecuenciaInferior)) {
            return false;
        }
        if (!Objects.equals(this.frecuenciaSuperior, other.frecuenciaSuperior)) {
            return false;
        }
        if (!Objects.equals(this.duracionMinima, other.duracionMinima)) {
            return false;
        }
        if (!Objects.equals(this.duracionMaxima, other.duracionMaxima)) {
            return false;
        }
        if (!Objects.equals(this.idViaAdministracion, other.idViaAdministracion)) {
            return false;
        }
        if (!Objects.equals(this.restrictiva, other.restrictiva)) {
            return false;
        }
        if (!Objects.equals(this.idEstatus, other.idEstatus)) {
            return false;
        }
        if (!Objects.equals(this.riesgo, other.riesgo)) {
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
        return "IndTerapeutica{" + "idIndTerapeutica=" + idIndTerapeutica + ", idInsumo=" + idInsumo + ", idDiagnostico=" + idDiagnostico + ", idTipoEdadPaciente=" + idTipoEdadPaciente + ", dosisMin=" + dosisMin + ", dosisMax=" + dosisMax + ", idUnidad=" + idUnidad + ", frecuenciaInferior=" + frecuenciaInferior + ", frecuenciaSuperior=" + frecuenciaSuperior + ", duracionMinima=" + duracionMinima + ", duracionMaxima=" + duracionMaxima + ", idViaAdministracion" + idViaAdministracion + ", descripcion=" + descripcion + ", restrictiva=" + restrictiva + ", idEstatus=" + idEstatus + ", riesgo=" + riesgo + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public String getIdIndTerapeutica() {
        return idIndTerapeutica;
    }

    public void setIdIndTerapeutica(String idIndTerapeutica) {
        this.idIndTerapeutica = idIndTerapeutica;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public Integer getIdTipoEdadPaciente() {
        return idTipoEdadPaciente;
    }

    public void setIdTipoEdadPaciente(Integer idTipoEdadPaciente) {
        this.idTipoEdadPaciente = idTipoEdadPaciente;
    }

    public BigDecimal getDosisMin() {
        return dosisMin;
    }

    public void setDosisMin(BigDecimal dosisMin) {
        this.dosisMin = dosisMin;
    }

    public BigDecimal getDosisMax() {
        return dosisMax;
    }

    public void setDosisMax(BigDecimal dosisMax) {
        this.dosisMax = dosisMax;
    }

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    public Integer getFrecuenciaInferior() {
        return frecuenciaInferior;
    }

    public void setFrecuenciaInferior(Integer frecuenciaInferior) {
        this.frecuenciaInferior = frecuenciaInferior;
    }

    public Integer getFrecuenciaSuperior() {
        return frecuenciaSuperior;
    }

    public void setFrecuenciaSuperior(Integer frecuenciaSuperior) {
        this.frecuenciaSuperior = frecuenciaSuperior;
    }

    public Integer getDuracionMinima() {
        return duracionMinima;
    }

    public void setDuracionMinima(Integer duracionMinima) {
        this.duracionMinima = duracionMinima;
    }

    public Integer getDuracionMaxima() {
        return duracionMaxima;
    }

    public void setDuracionMaxima(Integer duracionMaxima) {
        this.duracionMaxima = duracionMaxima;
    }

    public Integer getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(Integer idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getRestrictiva() {
        return restrictiva;
    }

    public void setRestrictiva(Integer restrictiva) {
        this.restrictiva = restrictiva;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(String riesgo) {
        this.riesgo = riesgo;
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
