package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public abstract class Permiso implements Comparable<Permiso>, Serializable {
    private static final long serialVersionUID = 1L;

    private String idNodo;
    private String nodo;
    private boolean activo;

    public Permiso() {
    }

    public Permiso(String idNodo, String nodo, boolean activo) {
        this.idNodo = idNodo;
        this.nodo = nodo;
        this.activo = activo;
    }

    public String getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(String idNodo) {
        this.idNodo = idNodo;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Permiso{" + "idNodo=" + idNodo + ", nodo=" + nodo + ", activo=" + activo + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idNodo);
        hash = 23 * hash + Objects.hashCode(this.nodo);
        hash = 23 * hash + (this.activo ? 1 : 0);
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
        final Permiso other = (Permiso) obj;
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.idNodo, other.idNodo)) {
            return false;
        }
        return Objects.equals(this.nodo, other.nodo);
    }

    @Override
    public int compareTo(Permiso permiso) {
        return this.getIdNodo().compareTo(permiso.getIdNodo());
    }
}
