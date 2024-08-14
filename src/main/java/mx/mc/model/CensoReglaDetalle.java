/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author apalacios
 */
public class CensoReglaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idCensoReglaDetalle;
    private String idCensoRegla;
    private Integer numeroSurtimiento;
    private Integer minimo;
    private Integer maximo;
    private Integer numeroDias;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public String getIdCensoReglaDetalle() {
        return idCensoReglaDetalle;
    }

    public void setIdCensoReglaDetalle(String idCensoReglaDetalle) {
        this.idCensoReglaDetalle = idCensoReglaDetalle;
    }

    public String getIdCensoRegla() {
        return idCensoRegla;
    }

    public void setIdCensoRegla(String idCensoRegla) {
        this.idCensoRegla = idCensoRegla;
    }

    public Integer getNumeroSurtimiento() {
        return numeroSurtimiento;
    }

    public void setNumeroSurtimiento(Integer numeroSurtimiento) {
        this.numeroSurtimiento = numeroSurtimiento;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Integer getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
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
