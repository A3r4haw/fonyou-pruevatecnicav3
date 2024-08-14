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
public class DispensacionMaterialInsumo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idDispensacionInsumo;
    private String idDispensacion;
    private String idInventario;
    private Integer cantidad;
    private String notas;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    
    public DispensacionMaterialInsumo() {
        //No code needed in constructor
    }

    public String getIdDispensacionInsumo() {
        return idDispensacionInsumo;
    }

    public void setIdDispensacionInsumo(String idDispensacionInsumo) {
        this.idDispensacionInsumo = idDispensacionInsumo;
    }

    public String getIdDispensacion() {
        return idDispensacion;
    }

    public void setIdDispensacion(String idDispensacion) {
        this.idDispensacion = idDispensacion;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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
