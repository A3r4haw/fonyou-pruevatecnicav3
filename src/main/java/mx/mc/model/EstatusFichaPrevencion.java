package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cervanets
 */
public class EstatusFichaPrevencion implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idEstatusPrevencion;
    private String descripcion;
    private Integer activa;
    private String insertIdUsuario;
    private Date insertFecha;
    private String updateIdUsuario;
    private Date updateFecha;

    public EstatusFichaPrevencion() {
    }

    public EstatusFichaPrevencion(Integer idEstatusPrevencion) {
        this.idEstatusPrevencion = idEstatusPrevencion;
    }

    public EstatusFichaPrevencion(Integer idEstatusPrevencion, String descripcion, Integer activa, String insertIdUsuario, Date insertFecha, String updateIdUsuario, Date updateFecha) {
        this.idEstatusPrevencion = idEstatusPrevencion;
        this.descripcion = descripcion;
        this.activa = activa;
        this.insertIdUsuario = insertIdUsuario;
        this.insertFecha = insertFecha;
        this.updateIdUsuario = updateIdUsuario;
        this.updateFecha = updateFecha;
    }

    public Integer getIdEstatusPrevencion() {
        return idEstatusPrevencion;
    }

    public void setIdEstatusPrevencion(Integer idEstatusPrevencion) {
        this.idEstatusPrevencion = idEstatusPrevencion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActiva() {
        return activa;
    }

    public void setActiva(Integer activa) {
        this.activa = activa;
    }

    public String getInsertIdUsuario() {
        return insertIdUsuario;
    }

    public void setInsertIdUsuario(String insertIdUsuario) {
        this.insertIdUsuario = insertIdUsuario;
    }

    public Date getInsertFecha() {
        return insertFecha;
    }

    public void setInsertFecha(Date insertFecha) {
        this.insertFecha = insertFecha;
    }

    public String getUpdateIdUsuario() {
        return updateIdUsuario;
    }

    public void setUpdateIdUsuario(String updateIdUsuario) {
        this.updateIdUsuario = updateIdUsuario;
    }

    public Date getUpdateFecha() {
        return updateFecha;
    }

    public void setUpdateFecha(Date updateFecha) {
        this.updateFecha = updateFecha;
    }

    @Override
    public String toString() {
        return "EstatusFichaPrevencion{" + "idEstatusPrevencion=" + idEstatusPrevencion + ", descripcion=" + descripcion + ", activa=" + activa + ", insertIdUsuario=" + insertIdUsuario + ", insertFecha=" + insertFecha + ", updateIdUsuario=" + updateIdUsuario + ", updateFecha=" + updateFecha + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idEstatusPrevencion);
        hash = 41 * hash + Objects.hashCode(this.descripcion);
        hash = 41 * hash + Objects.hashCode(this.activa);
        hash = 41 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 41 * hash + Objects.hashCode(this.insertFecha);
        hash = 41 * hash + Objects.hashCode(this.updateIdUsuario);
        hash = 41 * hash + Objects.hashCode(this.updateFecha);
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
        final EstatusFichaPrevencion other = (EstatusFichaPrevencion) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusPrevencion, other.idEstatusPrevencion)) {
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
