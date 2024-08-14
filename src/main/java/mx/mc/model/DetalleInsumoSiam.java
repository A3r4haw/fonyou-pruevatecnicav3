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
 * @author Admin
 */
public class DetalleInsumoSiam  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idDetalleInsumoSiam;
    private String idFolioAlternativo;
    private String idInsumo;
    private Integer cantidadSolicitada;
    private Integer cantidadAprobada;
    private Integer cantidadSurtida;
    private String idEstructura;
    private Integer cantidadActual;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date fechaRecepcion;
    private String idUsuarioRecepcion;
    private Date updateFecha;
    private String updateIdUsuario;

    public DetalleInsumoSiam() {
    }

    public DetalleInsumoSiam(String idDetalleInsumoSiam, String idFolioAlternativo, String idInsumo, Integer cantidadSolicitada, Integer cantidadAprobada, Integer cantidadSurtida, String idEstructura, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idDetalleInsumoSiam = idDetalleInsumoSiam;
        this.idFolioAlternativo = idFolioAlternativo;
        this.idInsumo = idInsumo;
        this.cantidadSolicitada = cantidadSolicitada;
        this.cantidadAprobada = cantidadAprobada;
        this.cantidadSurtida = cantidadSurtida;
        this.idEstructura = idEstructura;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    @Override
    public String toString() {
        return "DetalleInsumoSiam{" + "idFolioAlternativo=" + idFolioAlternativo + ", idInsumo=" + idInsumo + ", cantidadSolicitada=" + cantidadSolicitada + ", cantidadAprobada=" + cantidadAprobada + ", cantidadSurtida=" + cantidadSurtida + ", idEstructura=" + idEstructura + ", cantidadActual=" + cantidadActual + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", fechaRecepcion=" + fechaRecepcion + ", idUsuarioRecepcion=" + idUsuarioRecepcion + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }        
    
    public String getIdDetalleInsumoSiam() {
        return idDetalleInsumoSiam;
    }

    public void setIdDetalleInsumoSiam(String idDetalleInsumoSiam) {
        this.idDetalleInsumoSiam = idDetalleInsumoSiam;
    }

    public String getIdFolioAlternativo() {
        return idFolioAlternativo;
    }

    public void setIdFolioAlternativo(String idFolioAlternativo) {
        this.idFolioAlternativo = idFolioAlternativo;
    }

    public String getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(String idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Integer getCantidadAprobada() {
        return cantidadAprobada;
    }

    public void setCantidadAprobada(Integer cantidadAprobada) {
        this.cantidadAprobada = cantidadAprobada;
    }

    public Integer getCantidadSurtida() {
        return cantidadSurtida;
    }

    public void setCantidadSurtida(Integer cantidadSurtida) {
        this.cantidadSurtida = cantidadSurtida;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public Integer getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Integer cantidadActual) {
        this.cantidadActual = cantidadActual;
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

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public String getIdUsuarioRecepcion() {
        return idUsuarioRecepcion;
    }

    public void setIdUsuarioRecepcion(String idUsuarioRecepcion) {
        this.idUsuarioRecepcion = idUsuarioRecepcion;
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
