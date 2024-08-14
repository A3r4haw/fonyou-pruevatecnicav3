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
public class TemplateEtiqueta implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idTemplate;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String contenido;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private Integer activa;
    private String idtipoSolucion;
    private String tipoSurtimiento;

    public TemplateEtiqueta() {
        //No code needed in constructor
    }

    public TemplateEtiqueta(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    public TemplateEtiqueta(String idTemplate, String nombre, String descripcion, String tipo, String contenido, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario, Integer activa, String idtipoSolucion, String tipoSurtimiento) {
        this.idTemplate = idTemplate;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.contenido = contenido;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.activa = activa;
        this.idtipoSolucion = idtipoSolucion;
        this.tipoSurtimiento = tipoSurtimiento;
    }

    public String getIdTemplate() {
        return idTemplate;
    }

    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
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

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
    }

    public String getIdtipoSolucion() {
        return idtipoSolucion;
    }

    public void setIdtipoSolucion(String idtipoSolucion) {
        this.idtipoSolucion = idtipoSolucion;
    }

    public String getTipoSurtimiento() {
        return tipoSurtimiento;
    }

    public void setTipoSurtimiento(String tipoSurtimiento) {
        this.tipoSurtimiento = tipoSurtimiento;
    }

    @Override
    public String toString() {
        return "TemplateEtiqueta{" + "idTemplate=" + idTemplate + ", nombre=" + nombre + ", descripcion=" + descripcion + ", tipo=" + tipo + ", contenido=" + contenido + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + ", activa=" + activa + ", idtipoSolucion=" + idtipoSolucion + ", tipoSurtimiento=" + tipoSurtimiento + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.idTemplate);
        hash = 67 * hash + Objects.hashCode(this.nombre);
        hash = 67 * hash + Objects.hashCode(this.descripcion);
        hash = 67 * hash + Objects.hashCode(this.tipo);
        hash = 67 * hash + Objects.hashCode(this.contenido);
        hash = 67 * hash + Objects.hashCode(this.insertFecha);
        hash = 67 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.updateFecha);
        hash = 67 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 67 * hash + Objects.hashCode(this.activa);
        hash = 67 * hash + Objects.hashCode(this.idtipoSolucion);
        hash = 67 * hash + Objects.hashCode(this.tipoSurtimiento);
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
        final TemplateEtiqueta other = (TemplateEtiqueta) obj;
        if (!Objects.equals(this.idTemplate, other.idTemplate)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idtipoSolucion, other.idtipoSolucion)) {
            return false;
        }
        if (!Objects.equals(this.tipoSurtimiento, other.tipoSurtimiento)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return Objects.equals(this.activa, other.activa);
    }

}
