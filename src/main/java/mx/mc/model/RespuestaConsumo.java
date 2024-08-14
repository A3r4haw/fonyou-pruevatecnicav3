/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gcruz
 */
public class RespuestaConsumo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String idRespuestaConsumo;
    private Integer codigo;
    private String mensaje;
    private Integer estatus;

    public RespuestaConsumo() {
        
    }

    public RespuestaConsumo(String idRespuestaConsumo, Integer codigo, String mensaje, Integer estatus) {
        this.idRespuestaConsumo = idRespuestaConsumo;
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.estatus = estatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idRespuestaConsumo);
        hash = 29 * hash + Objects.hashCode(this.codigo);
        hash = 29 * hash + Objects.hashCode(this.mensaje);
        hash = 29 * hash + Objects.hashCode(this.estatus);
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
        final RespuestaConsumo other = (RespuestaConsumo) obj;
        if (!Objects.equals(this.idRespuestaConsumo, other.idRespuestaConsumo)) {
            return false;
        }
        if (!Objects.equals(this.mensaje, other.mensaje)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return Objects.equals(this.estatus, other.estatus);
    }

    @Override
    public String toString() {
        return "RespuestaConsumo{" + "idRespuestaConsumo=" + idRespuestaConsumo + ", codigo=" + codigo + ", mensaje=" + mensaje + ", estatus=" + estatus + '}';
    }
    
    public String getIdRespuestaConsumo() {
        return idRespuestaConsumo;
    }

    public void setIdRespuestaConsumo(String idRespuestaConsumo) {
        this.idRespuestaConsumo = idRespuestaConsumo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }
    
    
}
