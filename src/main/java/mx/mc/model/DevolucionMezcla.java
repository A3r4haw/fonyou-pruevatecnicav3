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
 * 
 */
public class DevolucionMezcla  implements Serializable{

    private static final long serialVersionUID = 1L;
    private String idDevolucionMezcla;
    private String folio;
    private Date creacion;
    private String orden;
    private String idOrigen;
    private String idDestino;
    private String idSolucion;
    private String nombre;
    private String idPresentacion;
    private String idVia;
    private String idUsuario;
    private Integer idMotivo;
    private Integer retencion;
    private Date fechaRetiro;
    private Integer reutilizar;
    private Date fechaLimite;
    private String comentarios;
    private Integer merma;
    private Integer destruir;
    private String sugerencia;
    private String idDestinoFinal;
    private String idCalsificacion;
    private Integer riesgoPaciente;
    private Integer idCuales;
    private Integer idEstatusSolucion;    
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    @Override
    public String toString() {
        return "DevolucionMezcla{" + "idDevolucionMezcla=" + idDevolucionMezcla + ", folio=" + folio + ", idSolucion=" + idSolucion + ", nombre=" + nombre + ", idPresentacion=" + idPresentacion + ", idVia=" + idVia + ", idUsuario=" + idUsuario + ", idMotivo=" + idMotivo + ", retencion=" + retencion + ", fechaRetiro=" + fechaRetiro + ", reutilizar=" + reutilizar + ", fechaLimite=" + fechaLimite + ", comentarios=" + comentarios + ", merma=" + merma + ", destruir=" + destruir + ", sugerencia=" + sugerencia + ", idDestinoFinal=" + idDestinoFinal + ", idCalsificacion=" + idCalsificacion + ", riesgoPaciente=" + riesgoPaciente + ", idCuales=" + idCuales + ", idEstatusSolucion=" + idEstatusSolucion + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idDevolucionMezcla);
        hash = 17 * hash + Objects.hashCode(this.folio);
        hash = 17 * hash + Objects.hashCode(this.idSolucion);
        hash = 17 * hash + Objects.hashCode(this.nombre);
        hash = 17 * hash + Objects.hashCode(this.idPresentacion);
        hash = 17 * hash + Objects.hashCode(this.idVia);
        hash = 17 * hash + Objects.hashCode(this.idUsuario);
        hash = 17 * hash + Objects.hashCode(this.idMotivo);
        hash = 17 * hash + Objects.hashCode(this.retencion);
        hash = 17 * hash + Objects.hashCode(this.fechaRetiro);
        hash = 17 * hash + Objects.hashCode(this.reutilizar);
        hash = 17 * hash + Objects.hashCode(this.fechaLimite);
        hash = 17 * hash + Objects.hashCode(this.comentarios);
        hash = 17 * hash + Objects.hashCode(this.merma);
        hash = 17 * hash + Objects.hashCode(this.destruir);
        hash = 17 * hash + Objects.hashCode(this.sugerencia);
        hash = 17 * hash + Objects.hashCode(this.idDestinoFinal);
        hash = 17 * hash + Objects.hashCode(this.idCalsificacion);
        hash = 17 * hash + Objects.hashCode(this.riesgoPaciente);
        hash = 17 * hash + Objects.hashCode(this.idCuales);
        hash = 17 * hash + Objects.hashCode(this.idEstatusSolucion);
        hash = 17 * hash + Objects.hashCode(this.insertFecha);
        hash = 17 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 17 * hash + Objects.hashCode(this.updateFecha);
        hash = 17 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final DevolucionMezcla other = (DevolucionMezcla) obj;
        if (!Objects.equals(this.idDevolucionMezcla, other.idDevolucionMezcla)) {
            return false;
        }
        if (!Objects.equals(this.folio, other.folio)) {
            return false;
        }
        if (!Objects.equals(this.idSolucion, other.idSolucion)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.idPresentacion, other.idPresentacion)) {
            return false;
        }
        if (!Objects.equals(this.idVia, other.idVia)) {
            return false;
        }
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.sugerencia, other.sugerencia)) {
            return false;
        }
        if (!Objects.equals(this.idDestinoFinal, other.idDestinoFinal)) {
            return false;
        }
        if (!Objects.equals(this.idCalsificacion, other.idCalsificacion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idMotivo, other.idMotivo)) {
            return false;
        }
        if (!Objects.equals(this.retencion, other.retencion)) {
            return false;
        }
        if (!Objects.equals(this.fechaRetiro, other.fechaRetiro)) {
            return false;
        }
        if (!Objects.equals(this.reutilizar, other.reutilizar)) {
            return false;
        }
        if (!Objects.equals(this.fechaLimite, other.fechaLimite)) {
            return false;
        }
        if (!Objects.equals(this.merma, other.merma)) {
            return false;
        }
        if (!Objects.equals(this.destruir, other.destruir)) {
            return false;
        }
        if (!Objects.equals(this.riesgoPaciente, other.riesgoPaciente)) {
            return false;
        }
        if (!Objects.equals(this.idCuales, other.idCuales)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusSolucion, other.idEstatusSolucion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return true;
    }

    public Integer getMerma() {
        return merma;
    }

    public void setMerma(Integer merma) {
        this.merma = merma;
    }
    
    public Integer getIdEstatusSolucion() {
        return idEstatusSolucion;
    }

    public void setIdEstatusSolucion(Integer idEstatusSolucion) {
        this.idEstatusSolucion = idEstatusSolucion;
    }

    public String getIdDevolucionMezcla() {
        return idDevolucionMezcla;
    }

    public void setIdDevolucionMezcla(String idDevolucionMezcla) {
        this.idDevolucionMezcla = idDevolucionMezcla;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getIdOrigen() {
        return idOrigen;
    }

    public void setIdOrigen(String idOrigen) {
        this.idOrigen = idOrigen;
    }

    public String getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(String idDestino) {
        this.idDestino = idDestino;
    }
    
    public String getIdSolucion() {
        return idSolucion;
    }

    public void setIdSolucion(String idSolucion) {
        this.idSolucion = idSolucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdPresentacion() {
        return idPresentacion;
    }

    public void setIdPresentacion(String idPresentacion) {
        this.idPresentacion = idPresentacion;
    }

    public String getIdVia() {
        return idVia;
    }

    public void setIdVia(String idVia) {
        this.idVia = idVia;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(Integer idMotivo) {
        this.idMotivo = idMotivo;
    }

    public Integer getRetencion() {
        return retencion;
    }

    public void setRetencion(Integer retencion) {
        this.retencion = retencion;
    }

    public Date getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(Date fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public Integer getReutilizar() {
        return reutilizar;
    }

    public void setReutilizar(Integer reutilizar) {
        this.reutilizar = reutilizar;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getDestruir() {
        return destruir;
    }

    public void setDestruir(Integer destruir) {
        this.destruir = destruir;
    }

    public String getSugerencia() {
        return sugerencia;
    }

    public void setSugerencia(String sugerencia) {
        this.sugerencia = sugerencia;
    }

    public String getIdDestinoFinal() {
        return idDestinoFinal;
    }

    public void setIdDestinoFinal(String idDestinoFinal) {
        this.idDestinoFinal = idDestinoFinal;
    }

    public String getIdCalsificacion() {
        return idCalsificacion;
    }

    public void setIdCalsificacion(String idCalsificacion) {
        this.idCalsificacion = idCalsificacion;
    }

    public Integer getRiesgoPaciente() {
        return riesgoPaciente;
    }

    public void setRiesgoPaciente(Integer riesgoPaciente) {
        this.riesgoPaciente = riesgoPaciente;
    }

    public Integer getIdCuales() {
        return idCuales;
    }

    public void setIdCuales(Integer idCuales) {
        this.idCuales = idCuales;
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

