package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class Sobrellenado implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idSobrellenado;
    private String descripcion;
    private boolean activo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public Sobrellenado() {
    }

    public Sobrellenado(Integer idSobrellenado) {
        this.idSobrellenado = idSobrellenado;
    }

    public Integer getIdSobrellenado() {
        return idSobrellenado;
    }

    public void setIdSobrellenado(Integer idSobrellenado) {
        this.idSobrellenado = idSobrellenado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
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
        return "Sobrellenado{" + "idSobrellenado=" + idSobrellenado + ", descripcion=" + descripcion + ", activo=" + activo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.idSobrellenado);
        hash = 47 * hash + Objects.hashCode(this.descripcion);
        hash = 47 * hash + (this.activo ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.insertFecha);
        hash = 47 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 47 * hash + Objects.hashCode(this.updateFecha);
        hash = 47 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Sobrellenado other = (Sobrellenado) obj;
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idSobrellenado, other.idSobrellenado)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
