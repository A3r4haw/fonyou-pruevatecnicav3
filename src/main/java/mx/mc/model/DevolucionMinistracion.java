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
public class DevolucionMinistracion implements Serializable{
    private static final long serialVersionUID = 1L;
	
    private String idDevolucionMinistracion;
    private String folio;
    private String idAlmacenOrigen;
    private String idAlmacenDestino;
    private Date fechaDevolucion;
    private String idUsuarioDevolucion;
    private Integer idEstatusDevolucion;
    private Integer idMotivoDevolucion;
    private String idSurtimiento;
    private String comentarios;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public DevolucionMinistracion() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.idDevolucionMinistracion);
        hash = 89 * hash + Objects.hashCode(this.folio);
        hash = 89 * hash + Objects.hashCode(this.idAlmacenOrigen);
        hash = 89 * hash + Objects.hashCode(this.idAlmacenDestino);
        hash = 89 * hash + Objects.hashCode(this.fechaDevolucion);
        hash = 89 * hash + Objects.hashCode(this.idUsuarioDevolucion);
        hash = 89 * hash + Objects.hashCode(this.idEstatusDevolucion);
        hash = 89 * hash + Objects.hashCode(this.idMotivoDevolucion);
        hash = 89 * hash + Objects.hashCode(this.idSurtimiento);
        hash = 89 * hash + Objects.hashCode(this.comentarios);
        hash = 89 * hash + Objects.hashCode(this.insertFecha);
        hash = 89 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 89 * hash + Objects.hashCode(this.updateFecha);
        hash = 89 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final DevolucionMinistracion other = (DevolucionMinistracion) obj;
        if (!Objects.equals(this.idDevolucionMinistracion, other.idDevolucionMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.idAlmacenOrigen, other.idAlmacenOrigen)) {
            return false;
        }
        if (!Objects.equals(this.idAlmacenDestino, other.idAlmacenDestino)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioDevolucion, other.idUsuarioDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimiento, other.idSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaDevolucion, other.fechaDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusDevolucion, other.idEstatusDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoDevolucion, other.idMotivoDevolucion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    @Override
    public String toString() {
        return "DevolucionMinistracion{" + "idDevolucionMinistracion=" + idDevolucionMinistracion + ", folio=" + folio + ", idAlmacenOrigen=" + idAlmacenOrigen + ", idAlmacenDestino=" + idAlmacenDestino + ", fechaDevolucion=" + fechaDevolucion + ", idUsuarioDevolucion=" + idUsuarioDevolucion + ", idEstatusDevolucion=" + idEstatusDevolucion + ", idMotivoDevolucion=" + idMotivoDevolucion + ", idSurtimiento=" + idSurtimiento + ", comentarios=" + comentarios + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }


    public String getIdDevolucionMinistracion() {
        return idDevolucionMinistracion;
    }

    public void setIdDevolucionMinistracion(String idDevolucionMinistracion) {
        this.idDevolucionMinistracion = idDevolucionMinistracion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getIdAlmacenOrigen() {
        return idAlmacenOrigen;
    }

    public void setIdAlmacenOrigen(String idAlmacenOrigen) {
        this.idAlmacenOrigen = idAlmacenOrigen;
    }

    public String getIdAlmacenDestino() {
        return idAlmacenDestino;
    }

    public void setIdAlmacenDestino(String idAlmacenDestino) {
        this.idAlmacenDestino = idAlmacenDestino;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getIdUsuarioDevolucion() {
        return idUsuarioDevolucion;
    }

    public void setIdUsuarioDevolucion(String idUsuarioDevolucion) {
        this.idUsuarioDevolucion = idUsuarioDevolucion;
    }

    public Integer getIdEstatusDevolucion() {
        return idEstatusDevolucion;
    }

    public void setIdEstatusDevolucion(Integer idEstatusDevolucion) {
        this.idEstatusDevolucion = idEstatusDevolucion;
    }

    public Integer getIdMotivoDevolucion() {
        return idMotivoDevolucion;
    }

    public void setIdMotivoDevolucion(Integer idMotivoDevolucion) {
        this.idMotivoDevolucion = idMotivoDevolucion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
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

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }
    
}
