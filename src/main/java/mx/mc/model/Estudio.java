package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Transient;

public class Estudio implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idEstudio;
    private String claveEstudio;
    private int idTipoEstudio;
    private String descripcion;
    private Integer activo;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;
    @Transient
    private String descTipoEstudio;
    @Transient
    private String motivo;

    public String getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(String idEstudio) {
        this.idEstudio = idEstudio;
    }

    public int getIdTipoEstudio() {
        return idTipoEstudio;
    }

    public String getClaveEstudio() {
        return claveEstudio;
    }

    public void setClaveEstudio(String claveEstudio) {
        this.claveEstudio = claveEstudio;
    }

    public void setIdTipoEstudio(int idTipoEstudio) {
        this.idTipoEstudio = idTipoEstudio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
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
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idEstudio);
        hash = 79 * hash + Objects.hashCode(this.claveEstudio);
        hash = 79 * hash + this.idTipoEstudio;
        hash = 79 * hash + Objects.hashCode(this.descripcion);
        hash = 79 * hash + Objects.hashCode(this.activo);
        hash = 79 * hash + Objects.hashCode(this.insertFecha);
        hash = 79 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 79 * hash + Objects.hashCode(this.updateFecha);
        hash = 79 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final Estudio other = (Estudio) obj;
        if (this.idTipoEstudio != other.idTipoEstudio) {
            return false;
        }
        if (!Objects.equals(this.idEstudio, other.idEstudio)) {
            return false;
        }
        if (!Objects.equals(this.claveEstudio, other.claveEstudio)) {
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
        if (!Objects.equals(this.activo, other.activo)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }

    @Override
    public String toString() {
        return descripcion;
    }

    public String getDescTipoEstudio() {
        return descTipoEstudio;
    }

    public void setDescTipoEstudio(String descTipoEstudio) {
        this.descTipoEstudio = descTipoEstudio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    
}
