package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gcruz
 *
 */
public class TipoJustificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idTipoJustificacion;
    private String descripcion;
    private boolean activo;

    public TipoJustificacion() {
    }

    public TipoJustificacion(int idTipoJustificacion, String descripcion, boolean activo) {
        this.idTipoJustificacion = idTipoJustificacion;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdTipoJustificacion() {
        return idTipoJustificacion;
    }

    public void setIdTipoJustificacion(int idTipoJustificacion) {
        this.idTipoJustificacion = idTipoJustificacion;
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

    @Override
    public String toString() {
        return "tipoJustificacion{" + "idTipoJustificacion=" + idTipoJustificacion + ", descripcion=" + descripcion + ", activo=" + activo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.idTipoJustificacion;
        hash = 11 * hash + Objects.hashCode(this.descripcion);
        hash = 11 * hash + (this.activo ? 1 : 0);
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
        final TipoJustificacion other = (TipoJustificacion) obj;
        if (this.idTipoJustificacion != other.idTipoJustificacion) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        return Objects.equals(this.descripcion, other.descripcion);
    }
}
