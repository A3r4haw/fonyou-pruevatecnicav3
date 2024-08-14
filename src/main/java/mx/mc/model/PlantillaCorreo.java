package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class PlantillaCorreo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPlantilla;
    private String nombre;
    private String clave;
    private String contenido;
    private String asunto;
    private Integer activo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public PlantillaCorreo() {
    }

    public PlantillaCorreo(String idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public PlantillaCorreo(String idPlantilla, String nombre, String clave, String contenido, String asunto, Integer activo, Date insertFecha, String insertIdUsuario, Date updateFecha, String updateIdUsuario) {
        this.idPlantilla = idPlantilla;
        this.nombre = nombre;
        this.clave = clave;
        this.contenido = contenido;
        this.asunto = asunto;
        this.activo = activo;
        this.insertFecha = insertFecha;
        this.insertIdUsuario = insertIdUsuario;
        this.updateFecha = updateFecha;
        this.updateIdUsuario = updateIdUsuario;
    }

    public String getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(String idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
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

    @Override
    public String toString() {
        return "PlantillaCorreo{" + "idPlantilla=" + idPlantilla + ", nombre=" + nombre + ", clave=" + clave + ", contenido=" + contenido + ", asunto=" + asunto + ", activo=" + activo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.idPlantilla);
        hash = 11 * hash + Objects.hashCode(this.nombre);
        hash = 11 * hash + Objects.hashCode(this.clave);
        hash = 11 * hash + Objects.hashCode(this.contenido);
        hash = 11 * hash + Objects.hashCode(this.asunto);
        hash = 11 * hash + Objects.hashCode(this.activo);
        hash = 11 * hash + Objects.hashCode(this.insertFecha);
        hash = 11 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 11 * hash + Objects.hashCode(this.updateFecha);
        hash = 11 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final PlantillaCorreo other = (PlantillaCorreo) obj;
        if (!Objects.equals(this.idPlantilla, other.idPlantilla)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        if (!Objects.equals(this.asunto, other.asunto)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.activo, other.activo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
