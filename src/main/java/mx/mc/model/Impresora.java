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
 * @author gcruz
 */
public class Impresora implements Serializable {

    private static final long serialVersionUID = 1L;
    private String idImpresora;
    private String ipImpresora;
    private String descripcion;
    private String tipo;
    private String insertIdUsuario;
    private Date insertFecha;
    private Date updateFecha;
    private String updateIdUsuario;
    private String idTipoSolucion;
    private String tipoSurtimiento;

    public Impresora() {
        //No code needed in constructor
    }

    public Impresora(String idImpresora) {
        this.idImpresora = idImpresora;
    }

    public Impresora(String idImpresora, String ipImpresora, String descripcion, String tipo, String insertIdUsuario, Date insertFecha, Date updateFecha, String updateIdUsuario, String idTipoSolucion, String tipoSurtimiento) {
        this.idImpresora = idImpresora;
        this.ipImpresora = ipImpresora;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.idTipoSolucion = idTipoSolucion;
        this.tipoSurtimiento = tipoSurtimiento;
    }

    public String getIdImpresora() {
        return idImpresora;
    }

    public void setIdImpresora(String idImpresora) {
        this.idImpresora = idImpresora;
    }

    public String getIpImpresora() {
        return ipImpresora;
    }

    public void setIpImpresora(String ipImpresora) {
        this.ipImpresora = ipImpresora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
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

    public String getIdTipoSolucion() {
        return idTipoSolucion;
    }

    public void setIdTipoSolucion(String idTipoSolucion) {
        this.idTipoSolucion = idTipoSolucion;
    }

    public String getTipoSurtimiento() {
        return tipoSurtimiento;
    }

    public void setTipoSurtimiento(String tipoSurtimiento) {
        this.tipoSurtimiento = tipoSurtimiento;
    }

    @Override
    public String toString() {
        return "Impresora{" + "idImpresora=" + idImpresora + ", ipImpresora=" + ipImpresora + ", descripcion=" + descripcion + ", tipo=" + tipo + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", idTipoSolucion=" + idTipoSolucion + ", tipoSurtimiento=" + tipoSurtimiento + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idImpresora);
        hash = 79 * hash + Objects.hashCode(this.ipImpresora);
        hash = 79 * hash + Objects.hashCode(this.descripcion);
        hash = 79 * hash + Objects.hashCode(this.tipo);
        hash = 79 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 79 * hash + Objects.hashCode(this.insertFecha);
        hash = 79 * hash + Objects.hashCode(this.updateFecha);
        hash = 79 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 79 * hash + Objects.hashCode(this.idTipoSolucion);
        hash = 79 * hash + Objects.hashCode(this.tipoSurtimiento);
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
        final Impresora other = (Impresora) obj;
        if (!Objects.equals(this.idImpresora, other.idImpresora)) {
            return false;
        }
        if (!Objects.equals(this.ipImpresora, other.ipImpresora)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idTipoSolucion, other.idTipoSolucion)) {
            return false;
        }
        if (!Objects.equals(this.tipoSurtimiento, other.tipoSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
