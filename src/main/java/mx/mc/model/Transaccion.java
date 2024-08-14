package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idTransaccion;
    private String nombre;
    private String descripcion;
    private String url;
    private String codigo;
    private String icon;
    private boolean activo;
    private String idModulo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public Transaccion() {
        //No code needed in constructor
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    

    @Override
    public String toString() {
        return "Transaccion{" + "idTransaccion=" + idTransaccion + ", nombre=" + nombre + ", descripcion=" + descripcion + ", url=" + url + ", codigo=" + codigo + ", icon=" + icon + ", activo=" + activo + ", idModulo=" + idModulo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.idTransaccion);
        hash = 71 * hash + Objects.hashCode(this.nombre);
        hash = 71 * hash + Objects.hashCode(this.descripcion);
        hash = 71 * hash + Objects.hashCode(this.url);
        hash = 71 * hash + Objects.hashCode(this.codigo);
        hash = 71 * hash + Objects.hashCode(this.icon);
        hash = 71 * hash + (this.activo ? 1 : 0);
        hash = 71 * hash + Objects.hashCode(this.idModulo);
        hash = 71 * hash + Objects.hashCode(this.insertFecha);
        hash = 71 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 71 * hash + Objects.hashCode(this.updateFecha);
        hash = 71 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Transaccion other = (Transaccion) obj;
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.idTransaccion, other.idTransaccion)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.icon, other.icon)) {
            return false;
        }
        if (!Objects.equals(this.idModulo, other.idModulo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateFecha, other.updateFecha)) {
            return false;
        }
        return Objects.equals(this.updateIdUsuario, other.updateIdUsuario);
    }
}
