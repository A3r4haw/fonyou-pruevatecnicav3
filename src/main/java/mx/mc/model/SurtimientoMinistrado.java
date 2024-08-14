/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bbautista
 */
public class SurtimientoMinistrado implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String idMinistrado;
    private String idSurtimientoEnviado;
    private Integer orden;
    private Date fechaProgramado;
    private Date fechaMinistrado;
    private String idUsuario;
    private Integer cantidad;
    private BigDecimal dosis;
    private Integer idTipoReaccion;
    private Integer alergia;
    private String comentarios;
    private Integer idEstatusSurtimiento;
    private Integer idEstatusMinistracion;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer ministracionConfirmada;
    private Integer enviadoHIS;
    private Integer cantidadMinistrada;
    private Integer idMotivoNoMinistrado;
    private Date fechaInicio;

    public SurtimientoMinistrado() {
        //No code needed in constructor
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.idMinistrado);
        hash = 67 * hash + Objects.hashCode(this.idSurtimientoEnviado);
        hash = 67 * hash + Objects.hashCode(this.orden);
        hash = 67 * hash + Objects.hashCode(this.fechaProgramado);
        hash = 67 * hash + Objects.hashCode(this.fechaMinistrado);
        hash = 67 * hash + Objects.hashCode(this.idUsuario);
        hash = 67 * hash + Objects.hashCode(this.cantidad);
        hash = 67 * hash + Objects.hashCode(this.dosis);
        hash = 67 * hash + Objects.hashCode(this.idTipoReaccion);
        hash = 67 * hash + Objects.hashCode(this.alergia);
        hash = 67 * hash + Objects.hashCode(this.comentarios);
        hash = 67 * hash + Objects.hashCode(this.idEstatusSurtimiento);
        hash = 67 * hash + Objects.hashCode(this.idEstatusMinistracion);
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.ministracionConfirmada);
        hash = 67 * hash + Objects.hashCode(this.enviadoHIS);
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
        final SurtimientoMinistrado other = (SurtimientoMinistrado) obj;
        if (!Objects.equals(this.idMinistrado, other.idMinistrado)) {
            return false;
        }
        if (!Objects.equals(this.idSurtimientoEnviado, other.idSurtimientoEnviado)) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
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
        if (!Objects.equals(this.orden, other.orden)) {
            return false;
        }
        if (!Objects.equals(this.fechaProgramado, other.fechaProgramado)) {
            return false;
        }
        if (!Objects.equals(this.fechaMinistrado, other.fechaMinistrado)) {
            return false;
        }
        if (!Objects.equals(this.cantidad, other.cantidad)) {
            return false;
        }
        if (!Objects.equals(this.dosis, other.dosis)) {
            return false;
        }
        if (!Objects.equals(this.idTipoReaccion, other.idTipoReaccion)) {
            return false;
        }
        if (!Objects.equals(this.alergia, other.alergia)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSurtimiento, other.idEstatusSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusMinistracion, other.idEstatusMinistracion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        if (!Objects.equals(this.ministracionConfirmada, other.ministracionConfirmada)) {
            return false;
        }
        return Objects.equals(this.enviadoHIS, other.enviadoHIS);
    }

    @Override
    public String toString() {
        return "SurtimientoMinistrado{" + "idMinistrado=" + idMinistrado + ", idSurtimientoEnviado=" + idSurtimientoEnviado + ", orden=" + orden + ", fechaProgramado=" + fechaProgramado + ", fechaMinistrado=" + fechaMinistrado + ", idUsuario=" + idUsuario + ", cantidad=" + cantidad + ", dosis=" + dosis + ", idTipoReaccion=" + idTipoReaccion + ", alergia=" + alergia + ", comentarios=" + comentarios + ", idEstatusSurtimiento=" + idEstatusSurtimiento + ", idEstatusMinistracion=" + idEstatusMinistracion + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", ministracionConfirmada=" + ministracionConfirmada + ", enviadoHIS=" + enviadoHIS +'}';
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public BigDecimal getDosis() {
        return dosis;
    }

    public void setDosis(BigDecimal dosis) {
        this.dosis = dosis;
    }
   
    public String getIdMinistrado() {
        return idMinistrado;
    }

    public void setIdMinistrado(String idMinistrado) {
        this.idMinistrado = idMinistrado;
    }

    public String getIdSurtimientoEnviado() {
        return idSurtimientoEnviado;
    }

    public void setIdSurtimientoEnviado(String idSurtimientoEnviado) {
        this.idSurtimientoEnviado = idSurtimientoEnviado;
    }

    public Date getFechaProgramado() {
        return fechaProgramado;
    }

    public void setFechaProgramado(Date fechaProgramado) {
        this.fechaProgramado = fechaProgramado;
    }

    public Date getFechaMinistrado() {
        return fechaMinistrado;
    }

    public void setFechaMinistrado(Date fechaMinistrado) {
        this.fechaMinistrado = fechaMinistrado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdTipoReaccion() {
        return idTipoReaccion;
    }

    public void setIdTipoReaccion(Integer idTipoReaccion) {
        this.idTipoReaccion = idTipoReaccion;
    }

    public Integer getAlergia() {
        return alergia;
    }

    public void setAlergia(Integer alergia) {
        this.alergia = alergia;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getIdEstatusSurtimiento() {
        return idEstatusSurtimiento;
    }

    public void setIdEstatusSurtimiento(Integer idEstatusSurtimiento) {
        this.idEstatusSurtimiento = idEstatusSurtimiento;
    }

    public Integer getIdEstatusMinistracion() {
        return idEstatusMinistracion;
    }

    public void setIdEstatusMinistracion(Integer idEstatusMinistracion) {
        this.idEstatusMinistracion = idEstatusMinistracion;
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

    public Integer getMinistracionConfirmada() {
        return ministracionConfirmada;
    }

    public void setMinistracionConfirmada(Integer ministracionConfirmada) {
        this.ministracionConfirmada = ministracionConfirmada;
    }

    public Integer getEnviadoHIS() {
        return enviadoHIS;
    }

    public void setEnviadoHIS(Integer enviadoHIS) {
        this.enviadoHIS = enviadoHIS;
    }

    public Integer getCantidadMinistrada() {
        return cantidadMinistrada;
    }

    public void setCantidadMinistrada(Integer cantidadMinistrada) {
        this.cantidadMinistrada = cantidadMinistrada;
    }
    
    public Integer getIdMotivoNoMinistrado() {
        return idMotivoNoMinistrado;
    }

    public void setIdMotivoNoMinistrado(Integer idMotivoNoMinistrado) {
        this.idMotivoNoMinistrado = idMotivoNoMinistrado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
