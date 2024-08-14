/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class Reabasto implements Serializable {
    
    private static final long serialVersionUID = 1L; 
    
    private String idReabasto;
    private String idEstructura;
    private String idEstructuraPadre;
    private Integer idTipoOrden;
    private String folio;
    private Date fechaSolicitud;
    private String idUsuarioSolicitud;
    private Date fechaSurtida;
    private String idUsuarioSurtida;
    private String documentoSurtida;
    private Date fechaRecepcion;
    private String idUsuarioRecepcion;
    private Date fechaIngresoInventario;
    private String idUsuarioIngresoInventario;
    private Integer idEstatusReabasto;
    private String idProveedor;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer transferencia;
    private String domicilio;
    private String nombreEntidad;
    private Integer idTipoOrigen;
    private String destino;
    //Campos adicionales para mostrar la vista
    private String almacen;
    private String proveedor;
    private String estatus;
    private String solicitante;
    private String idMedico;  
    
    public Reabasto() {
        //No code needed in constructor
    }
   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idReabasto);
        hash = 41 * hash + Objects.hashCode(this.idEstructura);
        hash = 41 * hash + Objects.hashCode(this.idEstructuraPadre);
        hash = 41 * hash + Objects.hashCode(this.idTipoOrden);
        hash = 41 * hash + Objects.hashCode(this.folio);
        hash = 41 * hash + Objects.hashCode(this.fechaSolicitud);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioSolicitud);
        hash = 41 * hash + Objects.hashCode(this.fechaSurtida);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioSurtida);
        hash = 41 * hash + Objects.hashCode(this.documentoSurtida);
        hash = 41 * hash + Objects.hashCode(this.fechaRecepcion);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioRecepcion);
        hash = 41 * hash + Objects.hashCode(this.fechaIngresoInventario);
        hash = 41 * hash + Objects.hashCode(this.idUsuarioIngresoInventario);
        hash = 41 * hash + Objects.hashCode(this.idEstatusReabasto);
        hash = 41 * hash + Objects.hashCode(this.idProveedor);
        hash = 41 * hash + Objects.hashCode(this.insertFecha);
        hash = 41 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 41 * hash + Objects.hashCode(this.updateFecha);
        hash = 41 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 41 * hash + Objects.hashCode(this.almacen);
        hash = 41 * hash + Objects.hashCode(this.proveedor);
        hash = 41 * hash + Objects.hashCode(this.estatus);
        hash = 41 * hash + Objects.hashCode(this.solicitante);
        hash = 41 * hash + Objects.hashCode(this.transferencia);
        hash = 41 * hash + Objects.hashCode(this.idTipoOrigen);
        hash = 41 * hash + Objects.hashCode(this.idMedico);
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
        final Reabasto other = (Reabasto) obj;
        if (!Objects.equals(this.idReabasto, other.idReabasto)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.idEstructuraPadre, other.idEstructuraPadre)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioSolicitud, other.idUsuarioSolicitud)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioSurtida, other.idUsuarioSurtida)) {
            return false;
        }
        if (!Objects.equals(this.documentoSurtida, other.documentoSurtida)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioRecepcion, other.idUsuarioRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioIngresoInventario, other.idUsuarioIngresoInventario)) {
            return false;
        }
        if (!Objects.equals(this.idProveedor, other.idProveedor)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        if (!Objects.equals(this.proveedor, other.proveedor)) {
            return false;
        }
        if (!Objects.equals(this.estatus, other.estatus)) {
            return false;
        }
        if (!Objects.equals(this.solicitante, other.solicitante)) {
            return false;
        }
        if (!Objects.equals(this.idTipoOrden, other.idTipoOrden)) {
            return false;
        }
        if (!Objects.equals(this.fechaSolicitud, other.fechaSolicitud)) {
            return false;
        }
        if (!Objects.equals(this.fechaSurtida, other.fechaSurtida)) {
            return false;
        }
        if (!Objects.equals(this.fechaRecepcion, other.fechaRecepcion)) {
            return false;
        }
        if (!Objects.equals(this.fechaIngresoInventario, other.fechaIngresoInventario)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusReabasto, other.idEstatusReabasto)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if(!Objects.equals(this.transferencia, other.transferencia)){
            return false;
        }
        if(!Objects.equals(this.idTipoOrigen, other.idTipoOrigen)){
            return false;
        }
        return Objects.equals(this.idMedico, other.idMedico);
    }

    @Override
    public String toString() {
        return "Reabasto{" + "idReabasto=" + idReabasto + ", idEstructura=" + idEstructura + ", idEstructuraPadre=" + idEstructuraPadre + ", idTipoOrden=" + idTipoOrden + ", folio=" + folio + ", fechaSolicitud=" + fechaSolicitud + ", idUsuarioSolicitud=" + idUsuarioSolicitud + ", fechaSurtida=" + fechaSurtida + ", idUsuarioSurtida=" + idUsuarioSurtida + ", documentoSurtida=" + documentoSurtida + ", fechaRecepcion=" + fechaRecepcion + ", idUsuarioRecepcion=" + idUsuarioRecepcion + ", fechaIngresoInventario=" + fechaIngresoInventario + ", idUsuarioIngresoInventario=" + idUsuarioIngresoInventario + ", idEstatusReabasto=" + idEstatusReabasto + ", idProveedor=" + idProveedor + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", almacen=" + almacen + ", proveedor=" + proveedor + ", estatus=" + estatus + ", solicitante=" + solicitante +", transferencia="+transferencia + ",idTipoOrigen =" + idTipoOrigen + ",idMedico =" + idMedico +'}';
    }

    public String getIdReabasto() {
        return idReabasto;
    }

    public void setIdReabasto(String idReabasto) {
        this.idReabasto = idReabasto;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getIdEstructuraPadre() {
        return idEstructuraPadre;
    }

    public void setIdEstructuraPadre(String idEstructuraPadre) {
        this.idEstructuraPadre = idEstructuraPadre;
    }

    public Integer getIdTipoOrden() {
        return idTipoOrden;
    }

    public void setIdTipoOrden(Integer idTipoOrden) {
        this.idTipoOrden = idTipoOrden;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaSolicitud() {                
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getIdUsuarioSolicitud() {
        return idUsuarioSolicitud;
    }

    public void setIdUsuarioSolicitud(String idUsuarioSolicitud) {
        this.idUsuarioSolicitud = idUsuarioSolicitud;
    }

    public Date getFechaSurtida() {
        return fechaSurtida;
    }

    public void setFechaSurtida(Date fechaSurtida) {
        this.fechaSurtida = fechaSurtida;
    }

    public String getIdUsuarioSurtida() {
        return idUsuarioSurtida;
    }

    public void setIdUsuarioSurtida(String idUsuarioSurtida) {
        this.idUsuarioSurtida = idUsuarioSurtida;
    }

    public String getDocumentoSurtida() {
        return documentoSurtida;
    }

    public void setDocumentoSurtida(String documentoSurtida) {
        this.documentoSurtida = documentoSurtida;
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

    public Date getFechaIngresoInventario() {
        return fechaIngresoInventario;
    }

    public void setFechaIngresoInventario(Date fechaIngresoInventario) {
        this.fechaIngresoInventario = fechaIngresoInventario;
    }

    public String getIdUsuarioIngresoInventario() {
        return idUsuarioIngresoInventario;
    }

    public void setIdUsuarioIngresoInventario(String idUsuarioIngresoInventario) {
        this.idUsuarioIngresoInventario = idUsuarioIngresoInventario;
    }

    public Integer getIdEstatusReabasto() {
        return idEstatusReabasto;
    }

    public void setIdEstatusReabasto(Integer idEstatusReabasto) {
        this.idEstatusReabasto = idEstatusReabasto;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
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

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public Integer getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Integer transferencia) {
        this.transferencia = transferencia;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }   

    public Integer getIdTipoOrigen() {
        return idTipoOrigen;
    }

    public void setIdTipoOrigen(Integer idTipoOrigen) {
        this.idTipoOrigen = idTipoOrigen;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }           

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
}
