/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author apalacios
 */
public class CensoInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idCensoInsumo;
    private String idCensoPaciente;
    private String idMedicamento;
    private String idDiagnostico;
    private String idUsuarioRegistra;
    private Date fechaRegistro;
    private Integer idEstatusCensoInsumo;
    private String idDelegacion;
    private char sexo;
    private boolean cantidadMinima;
    private BigDecimal dosis;
    private Integer frecuencia;
    private Integer periodo;
    private Date vigenciaInicio;
    private Date vigenciaFin;
    private byte[] oficio;
    private String fileName;
    private String autorizacion;
    private String idEntidadHospitalaria;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public String getIdCensoInsumo() {
        return idCensoInsumo;
    }

    public void setIdCensoInsumo(String idCensoInsumo) {
        this.idCensoInsumo = idCensoInsumo;
    }

    public String getIdCensoPaciente() {
        return idCensoPaciente;
    }

    public void setIdCensoPaciente(String idCensoPaciente) {
        this.idCensoPaciente = idCensoPaciente;
    }

    public String getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(String idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(String idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public String getIdUsuarioRegistra() {
        return idUsuarioRegistra;
    }

    public void setIdUsuarioRegistra(String idUsuarioRegistra) {
        this.idUsuarioRegistra = idUsuarioRegistra;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdEstatusCensoInsumo() {
        return idEstatusCensoInsumo;
    }

    public void setIdEstatusCensoInsumo(Integer idEstatusCensoInsumo) {
        this.idEstatusCensoInsumo = idEstatusCensoInsumo;
    }

    public String getIdDelegacion() {
        return idDelegacion;
    }

    public void setIdDelegacion(String idDelegacion) {
        this.idDelegacion = idDelegacion;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public boolean isCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(boolean cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public BigDecimal getDosis() {
        return dosis;
    }

    public void setDosis(BigDecimal dosis) {
        this.dosis = dosis;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Date getVigenciaInicio() {
        return vigenciaInicio;
    }

    public void setVigenciaInicio(Date vigenciaInicio) {
        this.vigenciaInicio = vigenciaInicio;
    }

    public Date getVigenciaFin() {
        return vigenciaFin;
    }

    public void setVigenciaFin(Date vigenciaFin) {
        this.vigenciaFin = vigenciaFin;
    }

    public byte[] getOficio() {
        return oficio;
    }

    public void setOficio(byte[] oficio) {
        this.oficio = oficio;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getIdEntidadHospitalaria() {
        return idEntidadHospitalaria;
    }

    public void setIdEntidadHospitalaria(String idEntidadHospitalaria) {
        this.idEntidadHospitalaria = idEntidadHospitalaria;
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
