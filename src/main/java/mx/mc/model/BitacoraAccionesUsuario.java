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
 * @author gcruz
 */
public class BitacoraAccionesUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Date fecha;
    private String usuario;
    private String accion;
    private String entidad;
    private String campo;
    private String valorAnterior;
    private String valorNuevo;
    private String idActual;
    private String idNuevo;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public String getIdActual() {
        return idActual;
    }

    public void setIdActual(String idActual) {
        this.idActual = idActual;
    }

    public String getIdNuevo() {
        return idNuevo;
    }

    public void setIdNuevo(String idNuevo) {
        this.idNuevo = idNuevo;
    }
    
    
}
