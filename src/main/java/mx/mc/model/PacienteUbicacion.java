package mx.mc.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author hramirez
 */
public class PacienteUbicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idPacienteUbicacion;
    private String idPacienteServicio;
    private String idCama;
    private Date fechaUbicaInicio;
    private String idUsuarioUbicaInicio;
    private Date fechaUbicaFin;
    private String idUsuarioUbicaFin;
    private Integer idEstatusUbicacion;
    private Date insertFecha;
    private String insertIdUsuario;
    private Date updateFecha;
    private String updateIdUsuario;

    public PacienteUbicacion() {
    }

    public PacienteUbicacion(String idPacienteServicio) {
        this.idPacienteServicio = idPacienteServicio;
    }

    public String getIdPacienteUbicacion() {
        return idPacienteUbicacion;
    }

    public void setIdPacienteUbicacion(String idPacienteUbicacion) {
        this.idPacienteUbicacion = idPacienteUbicacion;
    }

    public String getIdPacienteServicio() {
        return idPacienteServicio;
    }

    public void setIdPacienteServicio(String idPacienteServicio) {
        this.idPacienteServicio = idPacienteServicio;
    }

    public String getIdCama() {
        return idCama;
    }

    public void setIdCama(String idCama) {
        this.idCama = idCama;
    }

    public Date getFechaUbicaInicio() {
        return fechaUbicaInicio;
    }

    public void setFechaUbicaInicio(Date fechaUbicaInicio) {
        this.fechaUbicaInicio = fechaUbicaInicio;
    }

    public String getIdUsuarioUbicaInicio() {
        return idUsuarioUbicaInicio;
    }

    public void setIdUsuarioUbicaInicio(String idUsuarioUbicaInicio) {
        this.idUsuarioUbicaInicio = idUsuarioUbicaInicio;
    }

    public Date getFechaUbicaFin() {
        return fechaUbicaFin;
    }

    public void setFechaUbicaFin(Date fechaUbicaFin) {
        this.fechaUbicaFin = fechaUbicaFin;
    }

    public String getIdUsuarioUbicaFin() {
        return idUsuarioUbicaFin;
    }

    public void setIdUsuarioUbicaFin(String idUsuarioUbicaFin) {
        this.idUsuarioUbicaFin = idUsuarioUbicaFin;
    }

    public Integer getIdEstatusUbicacion() {
        return idEstatusUbicacion;
    }

    public void setIdEstatusUbicacion(Integer idEstatusUbicacion) {
        this.idEstatusUbicacion = idEstatusUbicacion;
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
        return "PacienteUbicacion{" + "idPacienteUbicacion=" + idPacienteUbicacion + ", idPacienteServicio=" + idPacienteServicio + ", idCama=" + idCama + ", fechaUbicaInicio=" + fechaUbicaInicio + ", idUsuarioUbicaInicio=" + idUsuarioUbicaInicio + ", fechaUbicaFin=" + fechaUbicaFin + ", idUsuarioUbicaFin=" + idUsuarioUbicaFin + ", idEstatusUbicacion=" + idEstatusUbicacion + ", insertFecha=" + insertFecha + ", insertIdUsuario=" + insertIdUsuario + ", updateFecha=" + updateFecha + ", updateIdUsuario=" + updateIdUsuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.idPacienteUbicacion);
        hash = 97 * hash + Objects.hashCode(this.idPacienteServicio);
        hash = 97 * hash + Objects.hashCode(this.idCama);
        hash = 97 * hash + Objects.hashCode(this.fechaUbicaInicio);
        hash = 97 * hash + Objects.hashCode(this.idUsuarioUbicaInicio);
        hash = 97 * hash + Objects.hashCode(this.fechaUbicaFin);
        hash = 97 * hash + Objects.hashCode(this.idUsuarioUbicaFin);
        hash = 97 * hash + Objects.hashCode(this.idEstatusUbicacion);
        hash = 97 * hash + Objects.hashCode(this.insertFecha);
        hash = 97 * hash + Objects.hashCode(this.insertIdUsuario);
        hash = 97 * hash + Objects.hashCode(this.updateFecha);
        hash = 97 * hash + Objects.hashCode(this.updateIdUsuario);
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
        final PacienteUbicacion other = (PacienteUbicacion) obj;
        if (!Objects.equals(this.idPacienteUbicacion, other.idPacienteUbicacion)) {
            return false;
        }
        if (!Objects.equals(this.idPacienteServicio, other.idPacienteServicio)) {
            return false;
        }
        if (!Objects.equals(this.idCama, other.idCama)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioUbicaInicio, other.idUsuarioUbicaInicio)) {
            return false;
        }
        if (!Objects.equals(this.idUsuarioUbicaFin, other.idUsuarioUbicaFin)) {
            return false;
        }
        if (!Objects.equals(this.insertIdUsuario, other.insertIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.updateIdUsuario, other.updateIdUsuario)) {
            return false;
        }
        if (!Objects.equals(this.fechaUbicaInicio, other.fechaUbicaInicio)) {
            return false;
        }
        if (!Objects.equals(this.fechaUbicaFin, other.fechaUbicaFin)) {
            return false;
        }
        if (!Objects.equals(this.idEstatusUbicacion, other.idEstatusUbicacion)) {
            return false;
        }
        if (!Objects.equals(this.insertFecha, other.insertFecha)) {
            return false;
        }
        return Objects.equals(this.updateFecha, other.updateFecha);
    }
}
