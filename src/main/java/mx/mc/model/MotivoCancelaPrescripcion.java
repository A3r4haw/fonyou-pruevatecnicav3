package mx.mc.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class MotivoCancelaPrescripcion {

    private Integer idMotivoCancela;
    private String descripcion;
    private Integer validaPrescripcion;
    private Integer validaMezcla;
    private Integer activa;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public MotivoCancelaPrescripcion() {
    }

    public MotivoCancelaPrescripcion(Integer idMotivoCancela) {
        this.idMotivoCancela = idMotivoCancela;
    }

    public Integer getIdMotivoCancela() {
        return idMotivoCancela;
    }

    public void setIdMotivoCancela(Integer idMotivoCancela) {
        this.idMotivoCancela = idMotivoCancela;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValidaPrescripcion() {
        return validaPrescripcion;
    }

    public void setValidaPrescripcion(Integer validaPrescripcion) {
        this.validaPrescripcion = validaPrescripcion;
    }

    public Integer getValidaMezcla() {
        return validaMezcla;
    }

    public void setValidaMezcla(Integer validaMezcla) {
        this.validaMezcla = validaMezcla;
    }

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
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
        return "MotivoCancelaPrescripcion{" + "idMotivoCancela=" + idMotivoCancela + ", descripcion=" + descripcion + ", validaPrescripcion=" + validaPrescripcion + ", validaMezcla=" + validaMezcla + ", activa=" + activa + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.idMotivoCancela);
        hash = 47 * hash + Objects.hashCode(this.descripcion);
        hash = 47 * hash + Objects.hashCode(this.validaPrescripcion);
        hash = 47 * hash + Objects.hashCode(this.validaMezcla);
        hash = 47 * hash + Objects.hashCode(this.activa);
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
        final MotivoCancelaPrescripcion other = (MotivoCancelaPrescripcion) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idMotivoCancela, other.idMotivoCancela)) {
            return false;
        }
        if (!Objects.equals(this.validaPrescripcion, other.validaPrescripcion)) {
            return false;
        }
        if (!Objects.equals(this.validaMezcla, other.validaMezcla)) {
            return false;
        }
        if (!Objects.equals(this.activa, other.activa)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

}
