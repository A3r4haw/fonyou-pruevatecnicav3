package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class DimesaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contrasenia;
    private String farmacia;
    private String nombre;
    private String urlWsdl;

    public DimesaUsuario() {
    }

    public DimesaUsuario(String contrasenia, String farmacia, String nombre, String urlWsdl) {
        this.contrasenia = contrasenia;
        this.farmacia = farmacia;
        this.nombre = nombre;
        this.urlWsdl = urlWsdl;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(String farmacia) {
        this.farmacia = farmacia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlWsdl() {
        return urlWsdl;
    }

    public void setUrlWsdl(String urlWsdl) {
        this.urlWsdl = urlWsdl;
    }

    @Override
    public String toString() {
        return "DimesaUsuario{" + "contrasenia=" + contrasenia + ", farmacia=" + farmacia + ", nombre=" + nombre + ", urlWsdl=" + urlWsdl + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.contrasenia);
        hash = 13 * hash + Objects.hashCode(this.farmacia);
        hash = 13 * hash + Objects.hashCode(this.nombre);
        hash = 13 * hash + Objects.hashCode(this.urlWsdl);
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
        final DimesaUsuario other = (DimesaUsuario) obj;
        if (!Objects.equals(this.contrasenia, other.contrasenia)) {
            return false;
        }
        if (!Objects.equals(this.farmacia, other.farmacia)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return Objects.equals(this.urlWsdl, other.urlWsdl);
    }

}
