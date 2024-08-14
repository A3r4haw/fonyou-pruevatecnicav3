package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author olozada
 */
public class Capsula implements Serializable {
    private static final long serialVersionUID = 1L;
    private String idCapsula;
    private String idEstructura;
    private String codigoCapsula;
    private String nombre;
    private int activo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    private String idSurtimiento;

    public Capsula() {
        //No code needed in constructor
    }

    @Override
    public String toString() {
        return "Capsula{" + "idCapsula=" + idCapsula + ", idEstructura=" + idEstructura + ", codigoCapsula=" + codigoCapsula + ", nombre=" + nombre + ", activo=" + activo + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.idCapsula);
        hash = 37 * hash + Objects.hashCode(this.idEstructura);
        hash = 37 * hash + Objects.hashCode(this.codigoCapsula);
        hash = 37 * hash + Objects.hashCode(this.nombre);
        hash = 37 * hash + this.activo;
        hash = 37 * hash + Objects.hashCode(this.insertFecha);
        hash = 37 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 37 * hash + Objects.hashCode(this.updateFecha);
        hash = 37 * hash + Objects.hashCode(this.updateIdUsuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Capsula other = (Capsula) obj;
        if (!Objects.equals(this.idCapsula, other.idCapsula)) {
            return false;
        }
        if (!Objects.equals(this.idEstructura, other.idEstructura)) {
            return false;
        }
        if (!Objects.equals(this.codigoCapsula, other.codigoCapsula)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (this.activo != other.activo) {
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

    public String getIdCapsula() {
        return idCapsula;
    }

    public void setIdCapsula(String idCapsula) {
        this.idCapsula = idCapsula;
    }

    public String getIdEstructura() {
        return idEstructura;
    }

    public void setIdEstructura(String idEstructura) {
        this.idEstructura = idEstructura;
    }

    public String getCodigoCapsula() {
        return codigoCapsula;
    }

    public void setCodigoCapsula(String codigoCapsula) {
        this.codigoCapsula = codigoCapsula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
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

    public String getIdSurtimiento() {
        return idSurtimiento;
    }

    public void setIdSurtimiento(String idSurtimiento) {
        this.idSurtimiento = idSurtimiento;
    }

   
    
}
