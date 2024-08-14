package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class DimesaPaciente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String apellidoMaterno;
    private String apellidoPaterno;
    private String descripcionPadecimiento;
    private String descripcionPrograma;
    private String direccion;
    private Date fechaNacimiento;
    private String nombre;
    private String numeroAfiliacion;
    private String padecimiento;
    private String programa;
    private String sexo;
    private String telefono;

    public DimesaPaciente() {
        //No code needed in constructor
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

    public String getDescripcionPadecimiento() {
        return descripcionPadecimiento;
    }

    public void setDescripcionPadecimiento(String descripcionPadecimiento) {
        this.descripcionPadecimiento = descripcionPadecimiento;
    }

    public String getDescripcionPrograma() {
        return descripcionPrograma;
    }

    public void setDescripcionPrograma(String descripcionPrograma) {
        this.descripcionPrograma = descripcionPrograma;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroAfiliacion() {
        return numeroAfiliacion;
    }

    public void setNumeroAfiliacion(String numeroAfiliacion) {
        this.numeroAfiliacion = numeroAfiliacion;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "DimesaPaciente{" + "apellidoMaterno=" + apellidoMaterno + ", apellidoPaterno=" + apellidoPaterno + ", descripcionPadecimiento=" + descripcionPadecimiento + ", descripcionPrograma=" + descripcionPrograma + ", direccion=" + direccion + ", fechaNacimiento=" + fechaNacimiento + ", nombre=" + nombre + ", numeroAfiliacion=" + numeroAfiliacion + ", padecimiento=" + padecimiento + ", programa=" + programa + ", sexo=" + sexo + ", telefono=" + telefono + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.apellidoMaterno);
        hash = 89 * hash + Objects.hashCode(this.apellidoPaterno);
        hash = 89 * hash + Objects.hashCode(this.descripcionPadecimiento);
        hash = 89 * hash + Objects.hashCode(this.descripcionPrograma);
        hash = 89 * hash + Objects.hashCode(this.direccion);
        hash = 89 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 89 * hash + Objects.hashCode(this.nombre);
        hash = 89 * hash + Objects.hashCode(this.numeroAfiliacion);
        hash = 89 * hash + Objects.hashCode(this.padecimiento);
        hash = 89 * hash + Objects.hashCode(this.programa);
        hash = 89 * hash + Objects.hashCode(this.sexo);
        hash = 89 * hash + Objects.hashCode(this.telefono);
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
        final DimesaPaciente other = (DimesaPaciente) obj;
        if (!Objects.equals(this.apellidoMaterno, other.apellidoMaterno)) {
            return false;
        }
        if (!Objects.equals(this.apellidoPaterno, other.apellidoPaterno)) {
            return false;
        }
        if (!Objects.equals(this.descripcionPadecimiento, other.descripcionPadecimiento)) {
            return false;
        }
        if (!Objects.equals(this.descripcionPrograma, other.descripcionPrograma)) {
            return false;
        }
        if (!Objects.equals(this.direccion, other.direccion)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.numeroAfiliacion, other.numeroAfiliacion)) {
            return false;
        }
        if (!Objects.equals(this.padecimiento, other.padecimiento)) {
            return false;
        }
        if (!Objects.equals(this.programa, other.programa)) {
            return false;
        }
        if (!Objects.equals(this.sexo, other.sexo)) {
            return false;
        }
        if (!Objects.equals(this.telefono, other.telefono)) {
            return false;
        }
        return Objects.equals(this.fechaNacimiento, other.fechaNacimiento);
    }

}
