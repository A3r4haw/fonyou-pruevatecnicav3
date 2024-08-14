/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;

/**
 *
 * @author apalacios
 */
public class TipoCancelacion implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer idTipoCancelacion;
    private String descripcion;
    private boolean resurtir;
    private boolean activo;

    public Integer getIdTipoCancelacion() {
        return idTipoCancelacion;
    }

    public void setIdTipoCancelacion(Integer idTipoCancelacion) {
        this.idTipoCancelacion = idTipoCancelacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isResurtir() {
        return resurtir;
    }

    public void setResurtir(boolean resurtir) {
        this.resurtir = resurtir;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    @Override
    public String toString() {
        return descripcion;
    }
}
