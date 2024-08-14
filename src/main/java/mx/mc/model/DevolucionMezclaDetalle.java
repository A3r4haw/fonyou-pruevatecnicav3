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
 * @author bbautista
 */
public class DevolucionMezclaDetalle implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    private String idDevolucionMezclaDetalle;
    private String idDevolucionMezcla;
    private String idInsumo;
    private String idInventario;
    private Integer merma;
    private Integer piezas;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public DevolucionMezclaDetalle() {
    }
        
    @Override
    public String toString() {
        return "DevolucionMezclaDetalle{" + "idDevolucionMezclaDetalle=" + idDevolucionMezclaDetalle + ", idDevolucionMezcla=" + idDevolucionMezcla + ", idInsumo=" + idInsumo + ", idInventario=" + idInventario + ", merma=" + merma + ", piezas=" + piezas + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    public String getIdDevolucionMezclaDetalle() {
        return idDevolucionMezclaDetalle;
    }

    public void setIdDevolucionMezclaDetalle(String idDevolucionMezclaDetalle) {
        this.idDevolucionMezclaDetalle = idDevolucionMezclaDetalle;
    }

    public String getIdDevolucionMezcla() {
        return idDevolucionMezcla;
    }

    public void setIdDevolucionMezcla(String idDevolucionMezcla) {
        this.idDevolucionMezcla = idDevolucionMezcla;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public Integer getMerma() {
        return merma;
    }

    public void setMerma(Integer merma) {
        this.merma = merma;
    }

    public Integer getPiezas() {
        return piezas;
    }

    public void setPiezas(Integer piezas) {
        this.piezas = piezas;
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
