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
 * @author mcalderon
 */
public class EstatusPrescripcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer idEstatusPrescripcion;
    private String estatus;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    
    
    public EstatusPrescripcion() {
    }    
    
    public EstatusPrescripcion(Integer idEstatusPrescripcion) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
    }

    public EstatusPrescripcion(Integer idEstatusPrescripcion, String estatus, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
        this.estatus = estatus;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }
            
    public Integer getIdEstatusPrescripcion() {
        return idEstatusPrescripcion;
    }

    public void setIdEstatusPrescripcion(Integer idEstatusPrescripcion) {
        this.idEstatusPrescripcion = idEstatusPrescripcion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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
