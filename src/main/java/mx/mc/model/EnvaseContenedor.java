/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.util.Date;

/**
 *
 * @author bbautista
 */
public class EnvaseContenedor {

    private Integer idEnvaseContenedor;
    private String descripcion;
    private Integer activo;
    private String instruccionPreparacion;
    private String requiereDiluyente;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public EnvaseContenedor() {
    }

    public EnvaseContenedor(Integer idEnvaseContenedor) {
        this.idEnvaseContenedor = idEnvaseContenedor;
    }

    public Integer getIdEnvaseContenedor() {
        return idEnvaseContenedor;
    }

    public void setIdEnvaseContenedor(Integer idEnvaseContenedor) {
        this.idEnvaseContenedor = idEnvaseContenedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public String getInstruccionPreparacion() {
        return instruccionPreparacion;
    }

    public void setInstruccionPreparacion(String instruccionPreparacion) {
        this.instruccionPreparacion = instruccionPreparacion;
    }

    public String getRequiereDiluyente() {
        return requiereDiluyente;
    }

    public void setRequiereDiluyente(String requiereDiluyente) {
        this.requiereDiluyente = requiereDiluyente;
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

    @Override
    public String toString() {
        return "EnvaseContenedor{" + "idEnvaseContenedor=" + idEnvaseContenedor + ", descripcion=" + descripcion + ", activo=" + activo + ", instruccionPreparacion=" + instruccionPreparacion + ", requiereDiluyente=" + requiereDiluyente + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

}
