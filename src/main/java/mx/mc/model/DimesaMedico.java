package mx.mc.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class DimesaMedico implements Serializable {

    private static final long serialVersionUID = 1L;

    private String apellidoMaterno;
    private String apellidoPaterno;
    private String cedula;
    private String nombre;

    public DimesaMedico() {
    }

    public DimesaMedico(String apellidoMaterno, String apellidoPaterno, String cedula, String nombre) {
        this.apellidoMaterno = apellidoMaterno;
        this.apellidoPaterno = apellidoPaterno;
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "DimesaMedico{" + "apellidoMaterno=" + apellidoMaterno + ", apellidoPaterno=" + apellidoPaterno + ", cedula=" + cedula + ", nombre=" + nombre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.apellidoMaterno);
        hash = 53 * hash + Objects.hashCode(this.apellidoPaterno);
        hash = 53 * hash + Objects.hashCode(this.cedula);
        hash = 53 * hash + Objects.hashCode(this.nombre);
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
        final DimesaMedico other = (DimesaMedico) obj;
        if (!Objects.equals(this.apellidoMaterno, other.apellidoMaterno)) {
            return false;
        }
        if (!Objects.equals(this.apellidoPaterno, other.apellidoPaterno)) {
            return false;
        }
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        return Objects.equals(this.nombre, other.nombre);
    }

}
